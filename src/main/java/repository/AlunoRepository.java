package repository;

import exception.AlunoNaoEncontradoException;
import exception.CadastroExistenteException;
import model.Aluno;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AlunoRepository {

    // Método de cadastro de alunos
    void cadastrarAluno(Aluno aluno) throws CadastroExistenteException, SQLException;

    // Método para excluir aluno
    void excluirAluno(Aluno aluno) throws AlunoNaoEncontradoException, SQLException;

    // Método para pesquisar aluno por CPF
    Aluno pesquisarAluno(String cpf, Connection conn) throws SQLException;

    // Método para atualizar informações do aluno
    void atualizarAluno(Aluno aluno) throws AlunoNaoEncontradoException, SQLException;

    // Método para listar alunos pelo nome
    List<Aluno> listarAlunos(String nome) throws SQLException;

    // Método para pesquisar aluno por ID
    Aluno pesquisarAlunoPorId(int id) throws SQLException;
}
