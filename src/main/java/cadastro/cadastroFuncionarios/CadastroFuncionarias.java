package cadastro.cadastroFuncionarios;

import cadastro.excessoes.CadastroExistenteException;
import cadastro.excessoes.FuncionariaNaoEncontradaException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.UIManager.get;

public class CadastroFuncionarias {
    private List<Funcionarias> listaFuncionarias = new ArrayList<>();
    public void cadastrarFuncionaria(String nome, String cpf, LocalDate dataNasc, String rua, String bairro, String cidade, String estado, String funcao, int horaTrabalhada, double salario,double totalAReceber) throws CadastroExistenteException {
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
    }
    public void excluirFuncionaria(String nome) throws FuncionariaNaoEncontradaException {
        boolean removed = false;
        for (int i = 0; i < listaFuncionarias.size(); i++) {
            if (listaFuncionarias.get(i).getNome().equalsIgnoreCase(nome)) {
                listaFuncionarias.remove(i);
                removed = true;
                break;
            }
        }
        if (!removed)
            throw new FuncionariaNaoEncontradaException("Funcionária não encontrado: " + nome);
        }
    public Funcionarias pesquisarFuncionaria(String nome) throws FuncionariaNaoEncontradaException {
        for (Funcionarias funcionaria : listaFuncionarias) {
            if (funcionaria.getNome().equalsIgnoreCase(nome)) {
                return funcionaria;
            }
        }
        throw new FuncionariaNaoEncontradaException("Funcionária não encontrada.");
    }
}
