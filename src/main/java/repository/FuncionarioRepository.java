package repository;

import exception.CadastroExistenteException;
import exception.FuncionariaNaoEncontradaException;
import model.Funcionario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface FuncionarioRepository {

    void inserirFuncionario(Funcionario funcionario) throws CadastroExistenteException, SQLException;

    void removerFuncionario(Funcionario funcionario) throws FuncionariaNaoEncontradaException, SQLException;

    Funcionario pesquisarFuncionario(String cpf, Connection conn) throws SQLException;

    void atualizarFuncionario(Funcionario funcionario) throws FuncionariaNaoEncontradaException, SQLException;

    List<Funcionario> listarfuncionario(String nome) throws SQLException;

    Funcionario pesquisarFuncionarioPorId(int id) throws SQLException;
}
