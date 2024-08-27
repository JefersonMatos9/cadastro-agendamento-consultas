package cadastro.alunos;

import cadastro.excessoes.AlunoNaoEncontradoException;
import cadastro.excessoes.CadastroExistenteException;
import database.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoRepository {

    // Método de Cadastrar Alunos
    public void cadastrarAluno(Alunos aluno) throws CadastroExistenteException, SQLException {
        // Verifica se o aluno já possui cadastro
        if (pesquisarAluno(aluno.getCpf()) != null) {
            throw new CadastroExistenteException("Aluno com o CPF: " + aluno.getCpf() + " já possui cadastro.");
        }
        // Cadastra novo aluno ao banco de dados
        String query = "INSERT INTO alunos(nome, cpf, datanascimento, rua, bairro, cidade, estado, quantidadeSessoes, precoPorHora, totalApagar) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getCpf());
            pstmt.setDate(3, java.sql.Date.valueOf(aluno.getDataNasc()));
            pstmt.setString(4, aluno.getRua());
            pstmt.setString(5, aluno.getBairro());
            pstmt.setString(6, aluno.getCidade());
            pstmt.setString(7, aluno.getEstado());
            pstmt.setInt(8, aluno.getQuantidadeSessoes());
            pstmt.setDouble(9, aluno.getPrecoPorHora());
            pstmt.setDouble(10, aluno.getTotalAPagar());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0){
                throw new SQLException("Erro ao cadastrar aluno: Nenhuma linha afetada.");
            }
        }
    }

    // Método para Excluir Aluno
    public void excluirAluno(Alunos aluno) throws AlunoNaoEncontradoException, SQLException {
        // Verifica se o aluno existe antes de tentar excluí-lo
        if (pesquisarAluno(aluno.getCpf()) == null) {
            throw new AlunoNaoEncontradoException("Aluno com o CPF: " + aluno.getCpf() + " não encontrado.");
        }
        // Exclui o aluno caso seja encontrado
        String query = "DELETE FROM alunos WHERE cpf = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, aluno.getCpf());

            // Executa a consulta e verifica se o aluno foi excluído com sucesso
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Erro ao excluir aluno: Nenhuma linha afetada.");
            }
        }
    }

    // Método para Pesquisar Aluno
    public Alunos pesquisarAluno(String cpf) throws SQLException {
        String query = "SELECT * FROM alunos WHERE cpf = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cpf);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Alunos aluno = new Alunos();
                    aluno.setNome(rs.getString("nome"));
                    aluno.setCpf(rs.getString("cpf"));
                    aluno.setDataNasc(rs.getDate("datanascimento").toLocalDate());
                    aluno.setRua(rs.getString("rua"));
                    aluno.setBairro(rs.getString("bairro"));
                    aluno.setCidade(rs.getString("cidade"));
                    aluno.setEstado(rs.getString("estado"));
                    aluno.setQuantidadeSessoes(rs.getInt("quantidadeSessoes"));
                    aluno.setPrecoPorHora(rs.getDouble("precoPorHora"));
                    aluno.setTotalAPagar(rs.getDouble("totalApagar"));
                    return aluno;
                }
            }
        }
        return null; // Retorna null se o aluno não for encontrado
    }
}
