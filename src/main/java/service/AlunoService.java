package service;

import exception.AlunoNaoEncontradoException;
import exception.CadastroExistenteException;
import data.populate.AgendamentoDatePopulator;
import database.DataBaseConnection;
import mapper.util.MapperUtils;
import model.Aluno;
import repository.AlunoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoService implements AlunoRepository {

    private MapperUtils mapperUtils = new MapperUtils();
    private AgendamentoDatePopulator agendamentoDatePopulator = new AgendamentoDatePopulator();

    // Método de Cadastrar Alunos
    @Override
    public void cadastrarAluno(Aluno aluno) throws CadastroExistenteException, SQLException {
        // Verifica se o aluno já possui cadastro usando seu cpf
        if (pesquisarAluno(aluno.getCpf()) != null) {
            throw new CadastroExistenteException("Aluno(a) com o CPF: " + aluno.getCpf() + " já possui cadastro.");
        }
        // Cadastra novo aluno ao banco de dados
        String query = "INSERT INTO aluno(nome, cpf, telefone, data_nascimento, rua, bairro, cidade, estado, quantidade_sessoes, preco_por_hora, total_a_pagar) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection(); //Conecta ao banco de dados
             PreparedStatement pstmt = conn.prepareStatement(query)) { // Prepara a consulta ao banco de dados

            agendamentoDatePopulator.preencherDadosAluno(pstmt, aluno); // Preenche os dados do aluno na PreparedStatement

            int rowsAffected = pstmt.executeUpdate(); //Executa a atualização
            System.out.println("Aluno(a) cadastrado com sucesso.");
            if (rowsAffected == 0) {
                throw new SQLException("Erro ao cadastrar aluno(a): Nenhuma linha afetada.");
            }
        }
    }

    // Método para Excluir Aluno
    @Override
    public void excluirAluno(Aluno aluno) throws AlunoNaoEncontradoException, SQLException {
        // Verifica se o aluno existe antes de tentar excluí-lo
        if (pesquisarAluno(aluno.getCpf()) == null) {
            throw new AlunoNaoEncontradoException("Aluno com o CPF: " + aluno.getCpf() + " não encontrado.");
        }
        // Exclui o aluno caso seja encontrado
        String query = "DELETE FROM aluno WHERE cpf = ?"; //Consulta para exclusão
        try (Connection conn = DataBaseConnection.getConnection(); //Conecta ao banco de dados
             PreparedStatement pstmt = conn.prepareStatement(query)) { // Prepara a Consulta ao banco
            pstmt.setString(1, aluno.getCpf()); // Define o cpf do aluno a ser excluido.

            // Executa a consulta e verifica se o aluno foi excluído com sucesso
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Aluno(a) removido com súcesso do banco de dados.");
            if (rowsAffected == 0) {
                throw new SQLException("Erro ao excluir aluno: Nenhuma linha afetada.");
            }
        }
    }

    // Método para Pesquisar Aluno
    @Override
    public Aluno pesquisarAluno(String cpf) throws SQLException {
        String query = "SELECT * FROM aluno WHERE cpf = ?";

        try (Connection conn = DataBaseConnection.getConnection(); // Conecta ao banco de dados
             PreparedStatement pstmt = conn.prepareStatement(query)) { //Prepara a consulta

            pstmt.setString(1, cpf); //define o cpf a ser pesquisado
            try (ResultSet rs = pstmt.executeQuery()) { // Executa a consulta
                if (rs.next()) { //Se ouver resultado
                    return mapperUtils.mapearAlunoCompleto(rs); // Mapeia o resultado para o objeto Aluno
                }
            }
        }
        return null; // Retorna null se o aluno não for encontrado
    }

    @Override
    public void atualizarAluno(Aluno aluno) throws AlunoNaoEncontradoException, SQLException {
        if (pesquisarAlunoPorId(aluno.getId()) == null) {
            throw new AlunoNaoEncontradoException("Aluno(a) com o id: " + aluno.getId() + " não foi encontrado.");
        }

        String query = "UPDATE aluno SET nome = ?, telefone = ?, data_nascimento = ?, rua = ?, bairro = ?, cidade = ?, estado = ?" +
                (aluno.getCpf() != null ? ", cpf = ?" : "") +
                ", quantidade_sessoes = ?, preco_por_hora = ?, total_a_pagar = ? WHERE id = ?";  // SO ALTERA O CPF SE ELE FOR NULO
        try (Connection conn = DataBaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            agendamentoDatePopulator.atualizarDadosAluno(pstmt, aluno);
            int rowsAffected = pstmt.executeUpdate(); //Executa a atualização
            System.out.println("Dados do aluno(a) atualizado com sucesso.");
            if (rowsAffected == 0) {
                throw new SQLException("Erro ao atualizar aluno(a): Nenhuma linha afetada.");
            }
        }
    }

    @Override
    public List<Aluno> listarAlunos(String nome) throws SQLException {
        String query = "SELECT * FROM aluno WHERE nome = ?";
        List<Aluno> listaDeAlunos = new ArrayList<>();// Cria uma lista para armazenar os alunos

        try (Connection conn = DataBaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nome);// Usa setString para definir o parâmetro `nome`

            try (ResultSet rs = pstmt.executeQuery()) {// Usa executeQuery para consultas SELECT
                while (rs.next()) {
                    Aluno aluno = mapperUtils.mapearAlunoCompleto(rs); // Usa o método mapearAlunos para criar o objeto Aluno
                    listaDeAlunos.add(aluno);// Adiciona o objeto `Aluno` na lista
                }
            }
        }
        return listaDeAlunos;
    }


    @Override
    public Aluno pesquisarAlunoPorId(int id) throws SQLException {
        String query = "SELECT * FROM aluno WHERE id = ?";

        try (Connection conn = DataBaseConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapperUtils.mapearAlunoCompleto(rs); // chama o metodo mapearAlunos da classe MapperUtils
                }
            }
        }
        return null;
    }
}
