package agendamento;

import atualizadados.AtualizandoDados;
import cadastro.alunos.Alunos;
import cadastro.cadastroFuncionarios.Funcionarias;
import cadastro.excessoes.HorarioIndisponivelException;
import database.DataBaseConnection;

import java.sql.*;
import java.time.LocalDate;

public class SistemaDeAgendamento {

    public void agendarHorario(String alunoNome, String funcionariaNome, LocalDate data, String hora) throws HorarioIndisponivelException {
        Alunos aluno = buscarAlunoPorNome(alunoNome);
        Funcionarias funcionaria = buscarFuncionariaPorNome(funcionariaNome);
//Verificando se aluno e funcionaria existem , e se tem horarios e datas disponiveis
        if (aluno != null && funcionaria != null) {
            if (VerificandoAgendamento.isHorarioDisponivelParaAluno(aluno.getNome(), data, hora) &&
                    VerificandoAgendamento.isHorarioDisponivelParaFuncionaria(funcionaria.getNome(), data, hora)) {

                String query = "INSERT INTO agendamentos(nome_aluno, nome_funcionaria, data, hora) VALUES (?, ?, ?, ?)";
                try (Connection conn = DataBaseConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(query)) {

                    pstmt.setString(1, aluno.getNome());
                    pstmt.setString(2, funcionaria.getNome());
                    pstmt.setDate(3, Date.valueOf(data));
                    pstmt.setString(4, hora);
                    pstmt.executeUpdate();

                    // Atualizar sessões do aluno e funcionária
                    int sessaoAtualAluno = AtualizandoDados.obterSessoesAtualizadasAluno(aluno.getId());
                    int novaSessaoAluno = sessaoAtualAluno + 1;
                    AtualizandoDados.atualizarSessaoAluno(aluno.getId(), novaSessaoAluno);

                    int sessaoAtualFuncionaria = AtualizandoDados.obterSessoesAtualizadasFuncionaria(funcionaria.getId());
                    int novaSessaoFuncionaria = sessaoAtualFuncionaria + 1;
                    AtualizandoDados.atualizarSessaoFuncionaria(funcionaria.getId(), novaSessaoFuncionaria);

                    // Atualizar total a pagar e total a receber
                    double totalPagar = AtualizandoDados.calcularTotalPagar(aluno.getId());
                    double totalReceber = AtualizandoDados.calcularTotalReceber(funcionaria.getId());
                    AtualizandoDados.atualizarValores(funcionaria.getId(), totalReceber, aluno.getId(), totalPagar);

                } catch (SQLException e) {
                    throw new RuntimeException("Erro ao agendar horário: " + e.getMessage(), e);
                }
            } else {
                throw new HorarioIndisponivelException("Horário indisponível para a funcionária " + funcionariaNome + " ou para o aluno " + alunoNome);
            }
        } else {
            throw new HorarioIndisponivelException("Aluno ou funcionária não encontrados.");
        }
    }

    public boolean cancelarAgendamentoPorId(int agendamentoId) {
        String sql = "DELETE FROM agendamentos WHERE id = ?";
        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            // Definir o parâmetro da consulta
            pst.setInt(1, agendamentoId);

            // Executar a exclusão
            int rowsAffected = pst.executeUpdate();

            // Retorna true se algum registro foi excluído, false caso contrário
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Logar o erro de forma apropriada
            System.err.println("Erro ao cancelar o agendamento: " + e.getMessage());
            return false; // Retorna false em caso de erro
        }
    }


    private Alunos buscarAlunoPorNome(String nome) {
        String query = "SELECT * FROM alunos WHERE nome = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nome);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Alunos aluno = new Alunos();
                    aluno.setId(rs.getInt("id"));
                    aluno.setNome(rs.getString("nome"));
                    return aluno;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Funcionarias buscarFuncionariaPorNome(String nome) {
        String query = "SELECT * FROM funcionarias WHERE nome = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nome);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Funcionarias funcionaria = new Funcionarias();
                    funcionaria.setId(rs.getInt("id"));
                    funcionaria.setNome(rs.getString("nome"));
                    return funcionaria;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
