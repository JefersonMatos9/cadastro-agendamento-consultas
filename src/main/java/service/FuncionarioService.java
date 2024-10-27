package service;

import cadastro.exception.CadastroExistenteException;
import cadastro.exception.FuncionariaNaoEncontradaException;
import data.populate.AgendamentoDatePopulator;
import database.DataBaseConnection;
import mapper.util.MapperUtils;
import model.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioService {
    private MapperUtils mapperUtils = new MapperUtils();
    private AgendamentoDatePopulator agendamentoDatePopulator = new AgendamentoDatePopulator();


    public void inserirFuncionario(Funcionario funcionario) throws CadastroExistenteException, SQLException {
        try {
            if (pesquisarFuncionario(funcionario.getCpf()) != null) {
                throw new CadastroExistenteException("Funcionário(a) com o CPF: " + funcionario.getCpf() + " já possui cadastro.");
            }

            String query = "INSERT INTO funcionario (nome, cpf, telefone, data_nascimento, rua, bairro, cidade, estado, funcao, hora_trabalhada, salario, total_a_receber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DataBaseConnection.getConnection();//Conecta ao banco de dados
                 PreparedStatement pstmt = conn.prepareStatement(query)) {// Prepara a consulta ao banco de dados

                agendamentoDatePopulator.preencherDadosFuncionario(pstmt, funcionario);// Preenche os dados do funcionario na PreparedStatement

                int rowsAffected = pstmt.executeUpdate(); //Executa a atualização
                System.out.println("Funcionario(a) cadastrado com sucesso.");
                if (rowsAffected == 0) {
                    throw new SQLException("Erro ao cadastrar funcionario(a): Nenhuma linha afetada.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir funcionário: " + e.getMessage(), e);
        }
    }

    public void removerFuncionario(Funcionario funcionario) throws FuncionariaNaoEncontradaException, SQLException {
        if (pesquisarFuncionario(funcionario.getCpf()) == null) {
            throw new FuncionariaNaoEncontradaException("Funcionario(a) com o CPF: " + funcionario.getCpf() + " não encontrado.");
        }

        String query = "DELETE FROM funcionario WHERE cpf = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, funcionario.getCpf());

            // Executa a consulta e verifica se a funcionária foi excluída com sucesso
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Funcionario(a) removido do banco de dados com sucesso.");
            if (rowsAffected == 0) {
                throw new SQLException("Erro ao excluir a funcionário(a): Nenhuma linha afetada.");
            }
        }
    }


    public Funcionario pesquisarFuncionario(String cpf) throws SQLException {
        String query = "SELECT * FROM funcionario WHERE cpf = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, cpf);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapperUtils.mapearFuncionarioCompleto(rs);
                }
            }
        }
        return null; // Retorna null se não encontrar nenhum resultado
    }


    public void atualizarFuncionario(Funcionario funcionario) throws FuncionariaNaoEncontradaException, SQLException {
        if (pesquisarFuncionarioPorId(funcionario.getId()) == null) {
            throw new FuncionariaNaoEncontradaException("Funcionario(a) com o id: " + funcionario.getId() + " não foi encontrado.");
        }

        String query = "UPDATE funcionario SET nome = ?, telefone = ?, data_nascimento = ?, rua = ?, bairro = ?, cidade = ?, estado = ?" +
                (funcionario.getCpf() != null ? ", cpf = ?" : "") +
                ", funcao = ?, salario = ? WHERE id = ?"; // SÓ ATUALIZA O CPF SE ELE FOR NULO
        try (Connection conn = DataBaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            agendamentoDatePopulator.atualizarDadosFuncionario(pstmt, funcionario);

            int rowsAffected = pstmt.executeUpdate(); //Executa a atualização
            System.out.println("Dados do funcionario(a) atualizado com sucesso.");
            if (rowsAffected == 0) {
                throw new SQLException("Erro ao atualizar aluno(a): Nenhuma linha afetada.");
            }
        }
    }


    public List<Funcionario> listarfuncionario(String nome) throws SQLException {
        String query = "SELECT * FROM funcionario WHERE nome = ?";
        List<Funcionario> listaDeFuncionarios = new ArrayList<>();// Cria uma lista para armazenar os funcionarios

        try (Connection conn = DataBaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nome);// Usa setString para definir o parâmetro `nome`

            try (ResultSet rs = pstmt.executeQuery()) {// Usa executeQuery para consultas SELECT
                while (rs.next()) {
                    Funcionario funcionario = mapperUtils.mapearFuncionarioCompleto(rs); // Usa o método mapearFuncionarios para criar o objeto Funcionario
                    listaDeFuncionarios.add(funcionario);// Adiciona o objeto `Funcionario` na lista
                }
            }
        }
        return listaDeFuncionarios;
    }


    public Funcionario pesquisarFuncionarioPorId(int id) throws SQLException {
        String query = "SELECT * FROM funcionario WHERE id = ?";

        try (Connection conn = DataBaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapperUtils.mapearFuncionarioCompleto(rs); // chama o metodo de mapeamento da classe MapperUtils
                }
            }
        }
        return null;
    }
}
