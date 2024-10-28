package check.availability;

import database.DataBaseConnection;

import java.sql.*;
import java.time.LocalDate;

public class VerificandoAgendamento {

    public static boolean isHorarioDisponivelParaAluno(String alunoNome, LocalDate data, String hora) {
        String query = "SELECT * FROM agendamentos WHERE nome_aluno = ? AND data = ? AND hora = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, alunoNome);
            pstmt.setDate(2, Date.valueOf(data));
            pstmt.setString(3, hora);
            try (ResultSet rs = pstmt.executeQuery()) {
                return !rs.next(); // Retorna true se o horário está disponível para o aluno
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false se ocorrer um erro
    }

    public static boolean isHorarioDisponivelParaFuncionaria(String funcionariaNome, LocalDate data, String hora) {
        String query = "SELECT * FROM agendamentos WHERE nome_funcionaria = ? AND data = ? AND hora = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, funcionariaNome);
            pstmt.setDate(2, Date.valueOf(data));
            pstmt.setString(3, hora);
            try (ResultSet rs = pstmt.executeQuery()) {
                return !rs.next(); // Retorna true se o horário está disponível para a funcionária
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false se ocorrer um erro
    }
}
