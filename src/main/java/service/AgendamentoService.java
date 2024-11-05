package service;

import exception.AgendamentoException;
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

            // Verifica disponibilidade
            if (!verificarDisponibilidade(conn, aluno, funcionario, data, hora)) {
                throw new HorarioIndisponivelException("Horário indisponível para agendamento.");
            }

            // Realiza inserção de agendamento
            String insertSql = "INSERT INTO agendamentos(cpf_aluno, cpf_funcionaria, data, hora) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                agendamentoDatePopulator.preencherDadosAgendamento(insertStmt, funcionario, aluno, data, hora);
                int rowsInserted = insertStmt.executeUpdate();
                if (rowsInserted > 0) {

                    // Atualiza sessão e valores
                    atualizarSessaoEValores(conn, aluno, funcionario);
                    conn.commit(); // Confirma a transação
                } else {
                    conn.rollback();
                    throw new HorarioIndisponivelException("Falha ao inserir agendamento.");
                }
            } catch (SQLException e) {
                conn.rollback(); //Reverte a transação caso de error
                throw new AgendamentoException("ERRO: Falha ao realizar agendamento: " + e.getMessage(), e);
            }
        } catch (HorarioIndisponivelException e) {
            throw e; // relanca excessoes especificas
        } catch (Exception e) {
            throw new AgendamentoException("Erro: Falha no agendamento: " + e.getMessage(), e);
        }
    }


    public boolean cancelarAgendamentoPorId(int agendamentoId) {
        String sql = "DELETE FROM agendamentos WHERE id = ?";
        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, agendamentoId);
            int rowsAffected = pst.executeUpdate();
            System.out.println("Agendamento cancelado com sucesso.");
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao cancelar o agendamento: " + e.getMessage());
            return false;
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
}
