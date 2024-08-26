package atualizadados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.DataBaseConnection;

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

    public static void atualizarSessaoFuncionaria(int funcionariaId, int novasSessoes) throws SQLException {
        String sql = "UPDATE funcionarias SET horaTrabalhada = ? WHERE id = ?";
        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, novasSessoes);
            pst.setInt(2, funcionariaId);
            pst.executeUpdate();
        }
    }

    public static void atualizarValores(int funcionarioId, double totalReceber, int alunoId, double totalPagar) throws SQLException {
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
            pstAluno.setDouble(1, totalPagar);
            pstAluno.setInt(2, alunoId);
            pstAluno.executeUpdate();
        }
    }

    public static int obterSessoesAtualizadas(int id) {
        // Implementação para obter o número atualizado de sessões
        return 0;
    }

    public static double calcularTotalPagar(int alunoId) {
        // Implementação para calcular o total a pagar
        return 0.0;
    }

    public static double calcularTotalReceber(int funcionariaId) {
        // Implementação para calcular o total a receber
        return 0.0;
    }
}
