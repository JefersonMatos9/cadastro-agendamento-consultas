package memory.service;

import exception.CadastroExistenteException;
import exception.FuncionariaNaoEncontradaException;
import model.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioMemoryService {
    private final List<Funcionario> funcionarioCadastrado = new ArrayList<>();

    // Cadastra um funcionário verificando duplicidade pelo CPF
    public void cadastrarFuncionario(Funcionario funcionario) throws CadastroExistenteException, FuncionariaNaoEncontradaException {
        if (pesquisarFuncionario(funcionario.getCpf()) != null) {
            throw new CadastroExistenteException("Funcionário com o CPF " + funcionario.getCpf() + " já possui cadastro.");
        }
        funcionarioCadastrado.add(funcionario);
        System.out.println("Funcionário cadastrado com sucesso.");
    }

    // Remove um funcionário da lista
    public void removerFuncionario(Funcionario funcionario) throws FuncionariaNaoEncontradaException {
        Funcionario existente = pesquisarFuncionario(funcionario.getCpf());
        if (existente == null) {
            throw new FuncionariaNaoEncontradaException("Funcionário não encontrado na lista.");
        }
        funcionarioCadastrado.remove(existente);
        System.out.println("Funcionário removido com sucesso.");
    }

    // Lista todos os funcionários cadastrados
    public List<String> listarFuncionario() {
        if (funcionarioCadastrado.isEmpty()) {
            throw new RuntimeException("A lista de funcionarios está vazia.");
        }
        List<String> listaFuncionarios = new ArrayList<>();
        System.out.println("Lista de Funcionários:");
        for (Funcionario funcionario : funcionarioCadastrado) {
            System.out.println("Nome: " + funcionario.getNome() + ", CPF: " + funcionario.getCpf());
        }
        return listaFuncionarios;
    }

    // Pesquisa funcionário pelo nome ou cpf
    public Funcionario pesquisarFuncionario(String identificador) throws FuncionariaNaoEncontradaException {
        for (Funcionario funcionario : funcionarioCadastrado) {
            if (funcionario.getCpf().equals(identificador) || (funcionario.getNome().equalsIgnoreCase(identificador))) {
                return funcionario;
            }
        }
        return null;
    }

    // Edita informações do funcionário, apenas nome, mantendo o CPF
    public void editarFuncionario(Funcionario funcionarioEditado) throws FuncionariaNaoEncontradaException {
        Funcionario funcionario = pesquisarFuncionario(funcionarioEditado.getCpf());
        if (funcionario != null) {
            funcionario.setNome(funcionarioEditado.getNome());
            System.out.println("Funcionário editado com sucesso.");
        } else {
            throw new FuncionariaNaoEncontradaException("Funcionário não encontrado.");
        }
    }

    public List<Funcionario> getFuncionarioCadastrado() {
        return new ArrayList<>(funcionarioCadastrado);
    }
}
