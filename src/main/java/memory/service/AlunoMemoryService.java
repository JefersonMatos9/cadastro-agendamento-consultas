package memory.service;

import exception.AlunoNaoEncontradoException;
import exception.CadastroExistenteException;
import model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoMemoryService {

    private List<Aluno> alunoCadastrado = new ArrayList<>();

    // Método para cadastrar aluno
    public void cadastrarAluno(Aluno aluno) throws CadastroExistenteException {
        // Verifica se o aluno já está cadastrado
        if (pesquisarAluno(aluno)) {
            throw new CadastroExistenteException("O CPF " + aluno.getCpf() + " já possui cadastro.");
        }

        alunoCadastrado.add(aluno);
        System.out.println("Aluno cadastrado com sucesso.");
    }

    // Método para remover aluno
    public void removerAluno(Aluno aluno) throws AlunoNaoEncontradoException {
        // Verifica se o aluno existe antes de tentar removê-lo
        if (!pesquisarAluno(aluno)) {
            throw new AlunoNaoEncontradoException("Aluno não encontrado na lista.");
        }

        alunoCadastrado.remove(aluno);
        System.out.println("Aluno removido com sucesso.");
    }

    // Método para pesquisar aluno, retorna verdadeiro se encontrado
    public boolean pesquisarAluno(Aluno aluno) {
        if (aluno.getCpf() == null) {
            throw new IllegalArgumentException("O CPF não pode ser nulo.");
        }

        // Itera sobre a lista de alunos cadastrados para verificar se existe
        for (Aluno verificarCadastro : alunoCadastrado) {
            if (verificarCadastro.getCpf().equals(aluno.getCpf())) {
                return true; // Aluno encontrado
            }
        }
        return false; // Aluno não encontrado
    }

    public void listarAlunos() {
        if (alunoCadastrado.isEmpty()) {
            throw new RuntimeException("A lista de alunos está vazia.");
        } else {
            System.out.println("=================");
            System.out.println("Lista de Alunos:");
            for (Aluno listarAluno : alunoCadastrado) {
                System.out.println("Nome: " + listarAluno.getNome() + " ,CPF: " + listarAluno.getCpf());
            }
            System.out.println("=================");
        }
    }

    public void editarAluno(Aluno alunoEditado) throws AlunoNaoEncontradoException {
        for (Aluno aluno : alunoCadastrado) {
            if (aluno.getCpf().equals(alunoEditado.getCpf())) {
                aluno.setNome(alunoEditado.getNome());
                aluno.setCpf(alunoEditado.getCpf());

                System.out.println("Aluno editado com sucesso.");
                return;  // Encerra o método após a edição
            }
        }
        throw new AlunoNaoEncontradoException("Aluno não encontrado.");
    }
}
