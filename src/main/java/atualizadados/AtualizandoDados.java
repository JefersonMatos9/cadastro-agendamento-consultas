package atualizadados;
import database.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AtualizandoDados {

    public static void atualizarSessaoAluno(int alunoId, int novasSessoes) throws SQLException {
        String sql = "UPDATE alunos SET quantidadeSessoes = ? WHERE id = ?";
        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, novasSessoes);
            pst.setInt(2, alunoId);
            pst.executeUpdate();
        }
    }

    public static void atualizarSessaoFuncionario(int funcionariaId, int novasSessoes) throws SQLException {
        String sql = "UPDATE funcionarias SET horaTrabalhada = ? WHERE id = ?";
        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, novasSessoes);
            pst.setInt(2, funcionariaId);
            pst.executeUpdate();
        }
    }

    public static void atualizarValores(int funcionarioId, double totalReceber) throws SQLException {
        String sqlAtualizaFuncionario = "UPDATE funcionarias SET total_a_receber = ? WHERE id = ?";
        String sqlAtualizaAluno = "UPDATE alunos SET totalApagar = ? WHERE id = ?";

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
        String query = "SELECT quantidadeSessoes, precoPorHora FROM alunos WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, alunoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int quantidadeSessoes = rs.getInt("quantidadeSessoes");
                    double precoPorHora = rs.getDouble("precoPorHora");
                    return quantidadeSessoes * precoPorHora;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0; // Retorno padrão em caso de erro
    }

    public static double calcularTotalReceber(int funcionariaId) {
        String query = "SELECT horaTrabalhada, salario FROM funcionarias WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, funcionariaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int horaTrabalhada = rs.getInt("horaTrabalhada");
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
        String query = "SELECT quantidadeSessoes FROM alunos WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, alunoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("quantidadeSessoes");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Retorno padrão em caso de erro
    }

    public static int obterSessoesAtualizadasFuncionario(int funcionariaId) {
        String query = "SELECT horaTrabalhada FROM funcionarias WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, funcionariaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("horaTrabalhada");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Retorno padrão em caso de erro
    }
}
