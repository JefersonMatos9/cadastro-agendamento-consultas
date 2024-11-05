package update.service;

import database.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mysql.cj.conf.PropertyKey.logger;

public class DataUpdaterService {

    public static void atualizarSessaoAluno(Connection conn, String cpfAluno, int novasSessoes) throws SQLException {
        String sql = "UPDATE aluno SET quantidade_sessoes = ? WHERE cpf = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, novasSessoes);
            pst.setString(2, cpfAluno);
            int rowsUpDate = pst.executeUpdate();
        }
    }

    public static void atualizarSessaoFuncionario(Connection conn, String cpfFuncionaria, int novasSessoes) throws SQLException {
        String sql = "UPDATE funcionario SET hora_trabalhada = ? WHERE cpf = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, novasSessoes);
            pst.setString(2, cpfFuncionaria);
            int rowsUpDate = pst.executeUpdate();
        }
    }

    public static void atualizarValores(Connection conn, String cpf, double total, boolean isAluno) throws SQLException {
        String sql = isAluno ? "UPDATE aluno SET total_a_pagar = ? WHERE cpf = ?" :
                "UPDATE funcionario SET total_a_receber = ? WHERE cpf = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDouble(1, total);
            pst.setString(2, cpf);
            int rowsUpdated = pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro atualizar valores: " + e.getMessage());
        }
    }

    public static double calcularTotalPagar(Connection conn, String cpfAluno) {
        String query = "SELECT quantidade_sessoes, preco_por_hora FROM aluno WHERE cpf = ?";
        try ( PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, cpfAluno);
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

    public static double calcularTotalReceber(Connection conn, String cpfFuncionaria) {
        String query = "SELECT hora_trabalhada, salario FROM funcionario WHERE cpf = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, cpfFuncionaria);
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
    public static int obterSessoesAtualizadasAluno(Connection conn ,String cpfAluno) {
        String query = "SELECT quantidade_sessoes FROM aluno WHERE cpf = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, cpfAluno);
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

    public static int obterSessoesAtualizadasFuncionario(Connection conn, String cpfFuncionaria) {
        String query = "SELECT hora_trabalhada FROM funcionario WHERE cpf = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cpfFuncionaria);
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
