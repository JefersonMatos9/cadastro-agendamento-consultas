package cadastro.alunos;

import cadastro.cadastro.Cadastro;
import database.DataBaseConnection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CadastroAlunos {
    private List<Alunos> listaAlunos = new ArrayList<>();

    // Método de Cadastrar Alunos
    public void cadastrarAluno(Alunos aluno) throws Exception {
        // Verifica se o aluno já possui cadastro
        if (isAlunoCadastrado(aluno.getNome(), aluno.getDataNasc())) {
            throw new Exception("Aluno já possui cadastro.");
        }
        //Cadastra novo aluno ao banco de dados
        String query = "INSERT INTO alunos(nome, datanascimento, rua, bairro, cidade, estado, quantidadeSessoes, precoPorHora, totalApagar) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, aluno.getNome());
            pstmt.setDate(2, java.sql.Date.valueOf(aluno.getDataNasc()));
            pstmt.setString(3, aluno.getRua());
            pstmt.setString(4, aluno.getBairro());
            pstmt.setString(5, aluno.getCidade());
            pstmt.setString(6, aluno.getEstado());
            pstmt.setInt(7, aluno.getQuantidadeSessoes());
            pstmt.setDouble(8, aluno.getPrecoPorHora());
            pstmt.setDouble(9, aluno.getTotalAPagar());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Aluno cadastrado com sucesso.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar aluno
    public Alunos buscarAluno(String nome) throws Exception {
        if (listaAlunos.isEmpty()) {
            throw new Exception("A lista está vazia.");
        }
        for (Alunos aluno : listaAlunos) {
            if (aluno.getNome().equalsIgnoreCase(nome)) {
                return aluno;
            }
        }
        throw new Exception("Aluno não encontrado: " + nome);
    }

    // Método para verificar se o aluno já possui cadastro
    private boolean isAlunoCadastrado(String nome, LocalDate dataNasc) throws SQLException {
        String query = "SELECT COUNT(*) FROM alunos WHERE nome = ? AND datanascimento = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nome);
            pstmt.setDate(2, java.sql.Date.valueOf(dataNasc));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) { // Verifica se a consulta encontrou algum resultado.
                return rs.getInt(1) > 0; //Retorna true se o contador for maior que 0, indicando que o aluno ja existe.
            }
        }
        return false; // Retorna false se não encontrar nenhum resultado.
    }
}
