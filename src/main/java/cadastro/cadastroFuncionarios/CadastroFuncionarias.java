package cadastro.cadastroFuncionarios;

import cadastro.excessoes.CadastroExistenteException;
import cadastro.excessoes.FuncionariaNaoEncontradaException;
import database.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CadastroFuncionarias {

    // Método para Cadastrar Funcionária
    public void cadastrarFuncionaria(Funcionarias funcionaria) throws CadastroExistenteException, SQLException {
        // Verifica se a funcionária já possui cadastro
        if (pesquisarFuncionaria(funcionaria.getCpf()) != null) {
            throw new CadastroExistenteException("Funcionária com o CPF: " + funcionaria.getCpf() + " já possui cadastro.");
        }

        // Cadastra nova funcionária ao banco de dados
        String query = "INSERT INTO funcionarias (nome, cpf, datanascimento, rua, bairro, cidade, estado, funcao, horatrabalhada, salario, total_a_receber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, funcionaria.getNome());
            pstmt.setString(2, funcionaria.getCpf());
            pstmt.setDate(3, java.sql.Date.valueOf(funcionaria.getDataNasc()));
            pstmt.setString(4, funcionaria.getRua());
            pstmt.setString(5, funcionaria.getBairro());
            pstmt.setString(6, funcionaria.getCidade());
            pstmt.setString(7, funcionaria.getEstado());
            pstmt.setString(8, funcionaria.getFuncao());
            pstmt.setInt(9, funcionaria.getHoraTrabalhada());
            pstmt.setDouble(10, funcionaria.getSalario());
            pstmt.setDouble(11, funcionaria.getTotalAReceber());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Erro ao cadastrar funcionaria:Nenhuma linha afetada.");
            }
        }
    }

    // Método para Excluir Funcionária
    public void excluirFuncionaria(Funcionarias funcionaria) throws FuncionariaNaoEncontradaException, SQLException {
        // Verifica se a funcionária existe antes de tentar excluí-la
        if (pesquisarFuncionaria(funcionaria.getCpf()) == null) {
            throw new FuncionariaNaoEncontradaException("Funcionária com o CPF: " + funcionaria.getCpf() + " não encontrada.");
        }

        // Exclui a funcionária caso seja encontrada
        String query = "DELETE FROM funcionarias WHERE cpf = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, funcionaria.getCpf());

            // Executa a consulta e verifica se a funcionária foi excluída com sucesso
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Erro ao excluir a funcionária: Nenhuma linha afetada.");
            }
        }
    }

    // Método para Pesquisar Funcionária
    public Funcionarias pesquisarFuncionaria(String cpf) throws SQLException {
        String query = "SELECT * FROM funcionarias WHERE cpf = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, cpf);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Funcionarias funcionaria = new Funcionarias();
                    funcionaria.setNome(rs.getString("nome"));
                    funcionaria.setCpf(rs.getString("cpf"));
                    funcionaria.setDataNasc(rs.getDate("datanascimento").toLocalDate());
                    funcionaria.setRua(rs.getString("rua"));
                    funcionaria.setBairro(rs.getString("bairro"));
                    funcionaria.setCidade(rs.getString("cidade"));
                    funcionaria.setEstado(rs.getString("estado"));
                    funcionaria.setFuncao(rs.getString("funcao"));
                    funcionaria.setHoraTrabalhada(rs.getInt("horatrabalhada"));
                    funcionaria.setSalario(rs.getDouble("salario"));
                    funcionaria.setTotalAReceber(rs.getDouble("total_a_receber"));
                    return funcionaria; // Retorna o objeto com os dados
                }
            }
        }
        return null; // Retorna null se não encontrar nenhum resultado
    }
}
