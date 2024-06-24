package cadastro.cadastroFuncionarios;

import cadastro.excessoes.CadastroExistenteException;
import cadastro.excessoes.FuncionariaNaoEncontradaException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CadastroFuncionarias {
    private List<Funcionarias> listaFuncionarias = new ArrayList<>();

    public void cadastrarFuncionaria(String nome, String cpf, LocalDate dataNasc, String rua, String bairro, String cidade, String estado, String funcao, int horaTrabalhada, double salario) throws CadastroExistenteException {
        // Verifica se a funcionária já possui cadastro pelo CPF
        for (Funcionarias funcionaria : listaFuncionarias) {
            if (funcionaria.getCpf().equalsIgnoreCase(cpf)) {
                throw new CadastroExistenteException("Funcionária já possui cadastro.");
            }
        }
        // Cadastra nova funcionária
        Funcionarias novaFuncionaria = new Funcionarias();
        novaFuncionaria.setNome(nome);
        novaFuncionaria.setCpf(cpf);
        novaFuncionaria.setDataNasc(dataNasc);
        novaFuncionaria.setRua(rua);
        novaFuncionaria.setBairro(bairro);
        novaFuncionaria.setCidade(cidade);
        novaFuncionaria.setEstado(estado);
        novaFuncionaria.setFuncao(funcao);
        novaFuncionaria.setHoraTrabalhada(horaTrabalhada);
        novaFuncionaria.setSalario(salario);

        listaFuncionarias.add(novaFuncionaria);
        System.out.println("Funcionária " + nome + " cadastrada com sucesso.");
    }

    public void excluirFuncionaria(String nome) throws FuncionariaNaoEncontradaException {
        if (listaFuncionarias.isEmpty()) {
            throw new FuncionariaNaoEncontradaException("A lista está vazia.");
        }
        Funcionarias funcionariaParaRemover = null;
        for (Funcionarias funcionaria : listaFuncionarias) {
            if (funcionaria.getNome().equalsIgnoreCase(nome)) {
                funcionariaParaRemover = funcionaria;
                break;
            }
        }
        if (funcionariaParaRemover != null) {
            listaFuncionarias.remove(funcionariaParaRemover);
            System.out.println("Funcionária " + nome + " removida com sucesso.");
        } else{ throw new FuncionariaNaoEncontradaException("Funcionario não existe.");
        }
    }
    public String pesquisarFuncionaria(String nome) throws FuncionariaNaoEncontradaException {
        for (Funcionarias funcionaria : listaFuncionarias) {
            if (funcionaria.getNome().equalsIgnoreCase(nome)) {
                return funcionaria.getNome();
            }
        }
        throw new FuncionariaNaoEncontradaException("Funcionária não encontrada.");
    }
    public void exibirLista() throws FuncionariaNaoEncontradaException {
        if (listaFuncionarias.isEmpty()) {
            throw new FuncionariaNaoEncontradaException("Sem funcionárias cadastradas.");
        }
        for (Funcionarias funcionaria : listaFuncionarias) {
            System.out.println("Nome: " + funcionaria.getNome() + "Função:" + funcionaria.getFuncao());
        }
    }
}
