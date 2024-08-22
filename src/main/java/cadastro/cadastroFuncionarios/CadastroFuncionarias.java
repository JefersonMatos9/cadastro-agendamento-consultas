package cadastro.cadastroFuncionarios;

import cadastro.excessoes.CadastroExistenteException;
import cadastro.excessoes.FuncionariaNaoEncontradaException;
import database.DataBaseConnection;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.UIManager.get;

public class CadastroFuncionarias {
    private List<Funcionarias> listaFuncionarias = new ArrayList<>();

    public void cadastrarFuncionaria(Funcionarias funcionaria) throws CadastroExistenteException, SQLException {
        //Verificando se a funcionaria ja possui cadastro
        if (isFuncionariaCadastrada(funcionaria.getCpf())) {
            throw new CadastroExistenteException("Funcionaria com o CPF:" + funcionaria.getCpf() + ",ja possui Cadastro.");
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
            if (rowsAffected > 0) {
                System.out.println("Funcionária Cadastrada com Sucesso.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirFuncionaria(int funcionarioId) throws FuncionariaNaoEncontradaException {
        String query = "DELETE FROM funcionarias WHERE id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, funcionarioId);

            //Executando a consulta e verificando se o produto foi excluido com sucesso.
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Funcionario excluido com sucesso.");
            } else {
                System.out.println("Não existe Funcionario com esse Id: " + funcionarioId);
            }
        } catch (SQLException e) {
            //Tratamento de excessões.
            e.printStackTrace();
        }
    }

    public Funcionarias pesquisarFuncionaria(String nome) throws FuncionariaNaoEncontradaException {
        for (Funcionarias funcionaria : listaFuncionarias) {
            if (funcionaria.getNome().equalsIgnoreCase(nome)) {
                return funcionaria;
            }
        }
        throw new FuncionariaNaoEncontradaException("Funcionária não encontrada.");
    }

    //Verifica se existe funcionaria cadastrada
    private boolean isFuncionariaCadastrada(String cpf) throws SQLException {
        String query = "SELECT COUNT(*) FROM funcionarias WHERE cpf = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, cpf);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) { //Verifica se a consulta retornou algum resultado.
                return rs.getInt(1) > 0; //Retorna true se o contador for maior que 0, indicando que a funcionaria ja existe.
            }
        }
        return false; //Retornar false se não encontrar nenhum resultado.
    }
}
