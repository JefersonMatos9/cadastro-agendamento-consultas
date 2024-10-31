package memory.service;

import exception.AlunoNaoEncontradoException;
import exception.CadastroExistenteException;
import model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoMemoryService {


    private final List<Aluno> alunoCadastrado = new ArrayList<>();


    public void cadastrarAluno(Aluno aluno) throws CadastroExistenteException, AlunoNaoEncontradoException {
        // Verifica se o aluno já está cadastrado
        if (pesquisarAluno(aluno.getCpf()) != null) {
            throw new CadastroExistenteException("O CPF " + aluno.getCpf() + " já possui cadastro.");
        }
        alunoCadastrado.add(aluno);
        System.out.println("Aluno cadastrado com sucesso.");
    }


    public void removerAluno(Aluno aluno) throws AlunoNaoEncontradoException {
        Aluno existente = pesquisarAluno(aluno.getCpf());
        if (existente == null) {
            throw new AlunoNaoEncontradoException("Aluno não encontrado na lista.");
        }
        alunoCadastrado.remove(existente);
        System.out.println("Aluno removido com sucesso.");
    }

    public List<String>listarAlunos() {
        if (alunoCadastrado.isEmpty()) {
            throw new RuntimeException("A lista de alunos está vazia.");
        }
        List<String>listaAlunos = new ArrayList<>();
            System.out.println("Lista de Alunos:");
            for (Aluno aluno : alunoCadastrado) {
                listaAlunos.add("Nome: " + aluno.getNome() + " ,CPF: " + aluno.getCpf());
            }
        return listaAlunos;
    }

    // Método para pesquisar aluno, retorna verdadeiro se encontrado
    public Aluno pesquisarAluno(String identificador) throws AlunoNaoEncontradoException {
        // Itera sobre a lista de alunos cadastrados para verificar se existe
        for (Aluno verificarAluno : alunoCadastrado) {
            if (verificarAluno.getCpf().equals(identificador) || verificarAluno.getNome().equalsIgnoreCase(identificador)) {
                return verificarAluno; // Aluno encontrado
            }
        }
        return null;
    }


    public void editarAluno(Aluno alunoEditado) throws AlunoNaoEncontradoException {
        Aluno aluno = pesquisarAluno(alunoEditado.getCpf());
        if (aluno != null) {
            aluno.setNome(alunoEditado.getNome());
            aluno.setCpf(alunoEditado.getCpf());

            System.out.println("Aluno editado com sucesso.");
        } else {
            throw new AlunoNaoEncontradoException("Aluno não encontrado.");
        }
    }

    // Novo método para retornar a lista de alunos cadastrados
    public List<Aluno> getAlunosCadastrados() {
        return new ArrayList<>(alunoCadastrado); // Retorna a lista de alunos cadastrados
    }
}
