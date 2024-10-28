package update.service;
import database.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataUpdaterService {

    public static void atualizarSessaoAluno(int alunoId, int novasSessoes) throws SQLException {
        String sql = "UPDATE aluno SET quantidade_sessoes = ? WHERE id = ?";
        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, novasSessoes);
            pst.setInt(2, alunoId);
            pst.executeUpdate();
        }
    }

    public static void atualizarSessaoFuncionario(int funcionariaId, int novasSessoes) throws SQLException {
        String sql = "UPDATE funcionario SET hora_trabalhada = ? WHERE id = ?";
        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, novasSessoes);
            pst.setInt(2, funcionariaId);
            pst.executeUpdate();
        }
    }

    public static void atualizarValores(int funcionarioId, double totalReceber) throws SQLException {
        String sqlAtualizaFuncionario = "UPDATE funcionario SET total_a_receber = ? WHERE id = ?";
        String sqlAtualizaAluno = "UPDATE aluno SET total_a_pagar = ? WHERE id = ?";

        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement pstFuncionario = con.prepareStatement(sqlAtualizaFuncionario);
             PreparedStatement pstAluno = con.prepareStatement(sqlAtualizaAluno)) {

            // Atualizar total a receber para o funcionário
            pstFuncionario.setDouble(1, totalReceber);
            pstFuncionario.setInt(2, funcionarioId);
            pstFuncionario.executeUpdate();

            // Atualizar total a pagar para o aluno
         //   pstAluno.setDouble(1, totalPagar);
        //    pstAluno.setInt(2, alunoId);
            pstAluno.executeUpdate();
        }
    }

    public static double calcularTotalPagar(int alunoId) {
        String query = "SELECT quantidade_sessoes, preco_por_hora FROM aluno WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, alunoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int quantidadeSessoes = rs.getInt("quantidade_sessoes");
                    double precoPorHora = rs.getDouble("preco_por_hora");
                    return quantidadeSessoes * precoPorHora;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0; // Retorno padrão em caso de erro
    }

    public static double calcularTotalReceber(int funcionariaId) {
        String query = "SELECT hora_trabalhada, salario FROM funcionario WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, funcionariaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int horaTrabalhada = rs.getInt("hora_trabalhada");
                    double salario = rs.getDouble("salario");
                    return horaTrabalhada * salario;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0; // Retorno padrão em caso de erro
    }

    // Adicionando métodos para obter sessões atualizadas
    public static int obterSessoesAtualizadasAluno(int alunoId) {
        String query = "SELECT quantidade_sessoes FROM aluno WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, alunoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantidade_sessoes");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Retorno padrão em caso de erro
    }

    public static int obterSessoesAtualizadasFuncionario(int funcionariaId) {
        String query = "SELECT hora_trabalhada FROM funcionario WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, funcionariaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("hora_trabalhada");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Retorno padrão em caso de erro
    }
}
