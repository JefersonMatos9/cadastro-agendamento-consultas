package service;

import exception.AgendamentoException;
import model.Agendamento;
import update.service.DataUpdaterService;
import data.populate.AgendamentoDatePopulator;
import mapper.util.MapperUtils;
import model.Aluno;
import model.Funcionario;
import exception.HorarioIndisponivelException;
import database.DataBaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static update.service.DataUpdaterService.*;

public class AgendamentoService {

    private MapperUtils mapperUtils = new MapperUtils();
    private AgendamentoDatePopulator agendamentoDatePopulator = new AgendamentoDatePopulator();
    private AlunoService alunoService = new AlunoService();
    private FuncionarioService funcionarioService = new FuncionarioService();

    public void agendarHorario(String alunoCpf, String funcionarioCpf, LocalDate data, LocalTime hora) throws HorarioIndisponivelException, SQLException, AgendamentoException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Inicia transação

            Aluno aluno = alunoService.pesquisarAluno(alunoCpf, conn);
            if (aluno == null) {
                throw new HorarioIndisponivelException("Aluno com CPF: " + alunoCpf + " não encontrado.");
            }

            Funcionario funcionario = funcionarioService.pesquisarFuncionario(funcionarioCpf, conn);
            if (funcionario == null) {
                throw new HorarioIndisponivelException("Funcionário com CPF: " + funcionarioCpf + " não encontrado.");
            }
            if (!verificarDisponibilidade(conn, aluno, funcionario, data, hora)) {
                throw new HorarioIndisponivelException("Horário indisponível para agendamento.");
            }
            String insertSql = "INSERT INTO agendamentos(cpf_aluno, cpf_funcionaria, data, hora) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                agendamentoDatePopulator.preencherDadosAgendamento(insertStmt, funcionario, aluno, data, hora);
                int rowsInserted = insertStmt.executeUpdate();
                if (rowsInserted > 0) {
                    atualizarSessaoEValores(conn, aluno, funcionario);
                    conn.commit(); // Confirma a transação
                } else {
                    conn.rollback();
                    throw new HorarioIndisponivelException("Falha ao inserir agendamento.");
                }
            } catch (SQLException e) {
                conn.rollback(); //Reverte a transação caso de erro
                throw new AgendamentoException("ERRO: Falha ao realizar agendamento: " + e.getMessage());
            }
        } catch (HorarioIndisponivelException e) {
            throw e; // relanca excessoes especificas
        } catch (Exception e) {
            throw new AgendamentoException("Erro: Falha no agendamento: " + e.getMessage());
        }
    }

    public void cancelarAgendamento(int agendamentoId) throws AgendamentoException {
        Connection conn = null;
        try {
            // Validação inicial
            if (agendamentoId <= 0) {
                throw new AgendamentoException("ID do agendamento inválido: " + agendamentoId);
            }
            conn = DataBaseConnection.getConnection();
            if (conn == null) {
                throw new AgendamentoException("Não foi possível estabelecer conexão com o banco de dados");
            }
            conn.setAutoCommit(false);
            // Busca o agendamento
            Agendamento agendamento = null;
            try {
                agendamento = buscarAgendamentoPorId(conn, agendamentoId);
            } catch (Exception e) {
                throw new AgendamentoException("Erro ao buscar agendamento: " + e.getMessage());
            }
            // Busca aluno
            Aluno aluno = null;
            try {
                aluno = alunoService.pesquisarAluno(agendamento.getCpfAluno(), conn);
            } catch (Exception e) {
                throw new AgendamentoException("Erro ao buscar aluno: " + e.getMessage());
            }
            if (aluno == null) {
                throw new AgendamentoException("Aluno não encontrado para o CPF: " + agendamento.getCpfAluno());
            }
            // Busca funcionário
            Funcionario funcionario = null;
            try {
                funcionario = funcionarioService.pesquisarFuncionario(agendamento.getCpfFuncionario(), conn);
            } catch (Exception e) {
                throw new AgendamentoException("Erro ao buscar funcionário: " + e.getMessage());
            }
            if (funcionario == null) {
                throw new AgendamentoException("Funcionário não encontrado para o CPF: " + agendamento.getCpfFuncionario());
            }
            if (aluno.getQuantidadeSessoes() < 1) {
                throw new AgendamentoException("Aluno não possui sessões suficientes");
            }
            if (funcionario.getHoraTrabalhada() < 1) {
                throw new AgendamentoException("Funcionário não possui horas disponíveis");
            }
            // Realiza as operações
            try {
                decrementarSessao(conn, aluno.getCpf(), true);
                decrementarSessao(conn, funcionario.getCpf(), false);

                descontarTotal(conn, aluno.getCpf(), true);
                descontarTotal(conn, funcionario.getCpf(), false);

                String deleteSql = "DELETE FROM agendamentos WHERE id = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                deleteStmt.setInt(1, agendamentoId);
                int rowsDeleted = deleteStmt.executeUpdate();
                deleteStmt.close();

                if (rowsDeleted > 0) {
                    conn.commit();
                    System.out.println("Agendamento cancelado com sucesso.");
                } else {
                    throw new AgendamentoException("Nenhum agendamento foi deletado. ID: " + agendamentoId);
                }
            } catch (SQLException e) {
                throw new AgendamentoException("Erro durante as operações de banco: " + e.getMessage());
            }
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Realizando rollback das operações...");
                } catch (SQLException ex) {
                    System.err.println("Erro ao realizar rollback: " + ex.getMessage());
                }
            }
            throw new AgendamentoException("Erro inesperado: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }

    private void atualizarSessao(Connection conn, String cpf, boolean isAluno) throws SQLException {
        int sessaoAtual = isAluno ?
                DataUpdaterService.obterSessoesAtualizadasAluno(conn, cpf) :
                DataUpdaterService.obterSessoesAtualizadasFuncionario(conn, cpf);
        int novaSessao = sessaoAtual + 1;

        if (isAluno) {
            DataUpdaterService.atualizarSessaoAluno(conn, cpf, novaSessao);
        } else {
            atualizarSessaoFuncionario(conn, cpf, novaSessao);
        }
    }

    private void atualizarTotal(Connection conn, String cpf, boolean isAluno) throws SQLException {
        try {
            double total = isAluno ?
                    calcularTotalPagar(conn, cpf) :
                    calcularTotalReceber(conn, cpf);
            DataUpdaterService.atualizarValores(conn, cpf, total, isAluno);
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar total para o cpf: " + cpf);
        }
    }

    private void atualizarSessaoEValores(Connection conn, Aluno aluno, Funcionario funcionario) throws SQLException {
        try { // Atualiza sessões e valores em uma única transação
            atualizarSessao(conn, aluno.getCpf(), true);
            atualizarSessao(conn, funcionario.getCpf(), false);
            atualizarTotal(conn, aluno.getCpf(), true);
            atualizarTotal(conn, funcionario.getCpf(), false);
        } catch (SQLException e) {
            System.err.println("Erro durante atualização de sessao e valores " + e.getMessage());
            throw e;
        }
    }

    private boolean verificarDisponibilidade(Connection conn, Aluno aluno, Funcionario funcionario, LocalDate data, LocalTime hora) throws SQLException {
        String checkSql = "SELECT 1 FROM agendamentos WHERE (cpf_aluno = ? OR cpf_funcionaria = ?) " +
                "AND data = ? AND hora = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            agendamentoDatePopulator.preencherDadosDisponibilidade(checkStmt, aluno, funcionario, data, hora);
            // Executa a consulta e verifica se há resultados
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    // Existe um registro para o horário solicitado, então o horário não está disponível
                    System.out.println("Horário não disponível para o aluno: " + aluno.getNome() + " e funcionário: " + funcionario.getNome());
                    return false;
                } else {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao verificar disponibilidade de horário: " + e.getMessage(), e);
        }
    }

    private void descontarSessaoEValores(Connection conn, Aluno aluno, Funcionario funcionario) throws SQLException {
        System.out.println("Revertendo sessão e valores...");

        // Decrementa a sessão para o aluno e o funcionário
        decrementarSessao(conn, aluno.getCpf(), true);
        decrementarSessao(conn, funcionario.getCpf(), false);

        // Subtrai o valor da sessão do total a pagar do aluno
        descontarTotal(conn, aluno.getCpf(), true);

        // Subtrai o valor da sessão do total a receber do funcionário
        descontarTotal(conn, funcionario.getCpf(), false);
    }

    private void decrementarSessao(Connection conn, String cpf, boolean isAluno) throws SQLException {
        String table = isAluno ? "aluno" : "funcionario";
        String coluna = isAluno ? "quantidade_sessoes" : "hora_trabalhada"; // Ajuste os nomes das colunas conforme seu banco
        String updateSql = "UPDATE " + table + " SET " + coluna + " = " + coluna + " - 1 WHERE cpf = ?";

        PreparedStatement stmt = conn.prepareStatement(updateSql);
        stmt.setString(1, cpf);
        int rowsUpdated = stmt.executeUpdate();

        if (rowsUpdated == 0) {
            throw new SQLException("Nenhum registro atualizado ao decrementar sessão para CPF: " + cpf);
        }
    }

    private void descontarTotal(Connection conn, String cpf, boolean isAluno) throws SQLException {
        String table = isAluno ? "aluno" : "funcionario";
        String column = isAluno ? "total_a_pagar" : "total_a_receber";
        double valorSessao = isAluno ? 70.0 : 25.5;

        String updateSql = "UPDATE " + table + " SET " + column + " = " + column + " - ? WHERE cpf = ?";

        PreparedStatement stmt = conn.prepareStatement(updateSql);
        stmt.setDouble(1, valorSessao);
        stmt.setString(2, cpf);
        int rowsUpdated = stmt.executeUpdate();

        if (rowsUpdated == 0) {
            throw new SQLException("Nenhum registro atualizado ao descontar total para CPF: " + cpf);
        }
    }

    public Agendamento buscarAgendamentoPorId(Connection conn, int agendamentoId) throws SQLException, AgendamentoException {
        System.out.println("Conexão estabelecida: " + (conn != null));
        String sql = "SELECT hora, data, cpf_aluno, cpf_funcionaria FROM agendamentos WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, agendamentoId);
        System.out.println("Executando query: " + stmt.toString());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String cpfAluno = rs.getString("cpf_aluno");
            String cpfFuncionario = rs.getString("cpf_funcionaria");
            LocalTime hora = rs.getTime("hora").toLocalTime();
            LocalDate data = rs.getDate("data").toLocalDate();

            return new Agendamento(hora, data, cpfAluno, cpfFuncionario);
        } else {
            throw new AgendamentoException("Agendamento com ID " + agendamentoId + " não encontrado no banco de dados.");
        }
    }

}