package service;

import exception.CadastroExistenteException;
import exception.FuncionariaNaoEncontradaException;
import data.populate.AgendamentoDatePopulator;
import database.DataBaseConnection;
import mapper.util.MapperUtils;
import model.Funcionario;
import repository.FuncionarioRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioService implements FuncionarioRepository {
    private MapperUtils mapperUtils = new MapperUtils();
    private AgendamentoDatePopulator agendamentoDatePopulator = new AgendamentoDatePopulator();

    @Override
    public void inserirFuncionario(Funcionario funcionario) throws CadastroExistenteException, SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {//Conecta ao banco de dados
            if (pesquisarFuncionario(funcionario.getCpf(),conn) != null) {
                throw new CadastroExistenteException("Funcionário(a) com o CPF: " + funcionario.getCpf() + " já possui cadastro.");
            }
            String insert = "INSERT INTO funcionario (nome, cpf, telefone, data_nascimento, rua, bairro, cidade, estado, funcao, hora_trabalhada, salario, total_a_receber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insert)) {// Prepara a consulta ao banco de dados
                agendamentoDatePopulator.preencherDadosFuncionario(pstmt, funcionario);// Preenche os dados do funcionario na PreparedStatement
                int rowsAffected = pstmt.executeUpdate(); //Executa a atualização
                if (rowsAffected == 0) {
                    throw new SQLException("ERRO ao cadastrar funcionario(a): Nenhuma linha afetada.");
                }
            }
            System.out.println("Funcionario(a) cadastrado com sucesso.");
        }
    }

    @Override
    public void removerFuncionario(Funcionario funcionario) throws FuncionariaNaoEncontradaException, SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            if (pesquisarFuncionario(funcionario.getCpf(),conn) == null) {
                throw new FuncionariaNaoEncontradaException("Funcionario(a) com o CPF: " + funcionario.getCpf() + " não encontrado.");
            }
            String query = "DELETE FROM funcionario WHERE cpf = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, funcionario.getCpf());
                int rowsAffected = pstmt.executeUpdate();
                System.out.println("Funcionario(a) removido do banco de dados com sucesso.");
                if (rowsAffected == 0) {
                    throw new SQLException("Erro ao excluir a funcionário(a): Nenhuma linha afetada.");
                }
                System.out.println("Funcionario(a) excluido com sucesso.");
            }
        }
    }

    @Override
    public Funcionario pesquisarFuncionario(String cpf, Connection conn) throws SQLException {
        String query = "SELECT * FROM funcionario WHERE cpf = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Definindo o CPF no PreparedStatement
            pstmt.setString(1, cpf);

            // Executando a consulta
            try (ResultSet rs = pstmt.executeQuery()) {
                // Verificando se há resultados
                if (rs.next()) {
                    // Mapeando o resultado para um objeto Funcionario
                    return mapperUtils.mapearFuncionarioCompleto(rs);
                } else {
                    // Retornar null se nenhum funcionário for encontrado
                    return null;
                }
            }
        } catch (SQLException e) {
            // Log da exceção ou tratamento adicional
            throw new SQLException("Erro ao pesquisar funcionário: " + e.getMessage(), e);
        }
    }


    @Override
    public void atualizarFuncionario(Funcionario funcionario) throws FuncionariaNaoEncontradaException, SQLException {
        try (Connection conn = DataBaseConnection.getConnection()) {
            if (pesquisarFuncionarioPorId(funcionario.getId()) == null) {
                throw new FuncionariaNaoEncontradaException("Funcionario(a) com o id: " + funcionario.getId() + " não foi encontrado.");
            }
            String query = "UPDATE funcionario SET nome = ?, telefone = ?, data_nascimento = ?, rua = ?, bairro = ?, cidade = ?, estado = ?" +
                    (funcionario.getCpf() != null ? ", cpf = ?" : "") +
                    ", funcao = ?, salario = ? WHERE id = ?"; // SÓ ATUALIZA O CPF SE ELE FOR NULO
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                agendamentoDatePopulator.atualizarDadosFuncionario(pstmt, funcionario);
                int rowsAffected = pstmt.executeUpdate(); //Executa a atualização
                if (rowsAffected == 0) {
                    throw new SQLException("Erro ao atualizar aluno(a): Nenhuma linha afetada.");
                }
                System.out.println("Dados do funcionario(a) atualizado com sucesso.");
            }
        }
    }

    @Override
    public List<Funcionario> listarfuncionario(String nome) throws SQLException {
        String query = "SELECT * FROM funcionario WHERE nome = ?";
        List<Funcionario> listaDeFuncionarios = new ArrayList<>();// Cria uma lista para armazenar os funcionarios
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
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

    @Override
    public Funcionario pesquisarFuncionarioPorId(int id) throws SQLException {
        String query = "SELECT * FROM funcionario WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
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
