package agendamento;

import cadastro.alunos.Alunos;
import cadastro.cadastroFuncionarios.Funcionarias;
import cadastro.excessoes.HorarioIndisponivelException;
import database.DataBaseConnection;

import java.sql.*;
import java.time.LocalDate;

import static agendamento.VerificandoAgendamento.isHorarioDisponivelParaAluno;
import static agendamento.VerificandoAgendamento.isHorarioDisponivelParaFuncionaria;
import static atualizadados.AtualizandoDados.*;

public class SistemaDeAgendamento {

    public void agendarHorario(String alunoNome, String funcionariaNome, LocalDate data, String hora) throws HorarioIndisponivelException {
        Alunos aluno = buscarAlunoPorNome(alunoNome);
        Funcionarias funcionaria = buscarFuncionariaPorNome(funcionariaNome);

        if (aluno != null && funcionaria != null) {
            if (isHorarioDisponivelParaAluno(aluno.getNome(), data, hora) &&
                    isHorarioDisponivelParaFuncionaria(funcionaria.getNome(), data, hora)) {

                String query = "INSERT INTO agendamentos(nome_aluno, nome_funcionaria, data, hora) VALUES (?, ?, ?, ?)";
                try (Connection conn = DataBaseConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(query)) {

                    pstmt.setString(1, aluno.getNome());
                    pstmt.setString(2, funcionaria.getNome());
                    pstmt.setDate(3, Date.valueOf(data));
                    pstmt.setString(4, hora);
                    pstmt.executeUpdate();

                    // Atualizar sessões do aluno e funcionária
                    int novaSessaoAluno = obterSessoesAtualizadas(aluno.getId());
                    atualizarSessaoAluno(aluno.getId(), novaSessaoAluno);

                    int novaSessaoFuncionaria = obterSessoesAtualizadas(funcionaria.getId());
                    atualizarSessaoFuncionaria(funcionaria.getId(), novaSessaoFuncionaria);

                    // Atualizar total a pagar e total a receber
                    double totalPagar = calcularTotalPagar(aluno.getId());
                    double totalReceber = calcularTotalReceber(funcionaria.getId());
                    atualizarValores(funcionaria.getId(), totalReceber, aluno.getId(), totalPagar);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new HorarioIndisponivelException("Horário indisponível para a funcionária " + funcionariaNome + " ou para o aluno " + alunoNome);
            }
        } else {
            throw new HorarioIndisponivelException("Aluno ou funcionária não encontrados.");
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
