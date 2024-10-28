package memoria.principal;

import exception.AlunoNaoEncontradoException;
import exception.CadastroExistenteException;
import memory.service.AlunoMemoryService;
import model.Aluno;

public class Main {
    public static void main(String[] args) {
        AlunoMemoryService alunoMemoryService = new AlunoMemoryService();

        Aluno aluno = new Aluno();
        aluno.setNome("Deco");
        aluno.setCpf("1234");
        // Configurar outros atributos do aluno...

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Outro Deco");
        aluno2.setCpf("12345"); // Mesmo CPF

        try {
            // Cadastro dos alunos
            alunoMemoryService.cadastrarAluno(aluno);
            System.out.println("Aluno cadastrado com sucesso!");

            aluno.setNome("Miguel");
            aluno.setCpf("0000");
            alunoMemoryService.editarAluno(aluno);
            // alunoMemoryService.cadastrarAluno(aluno2); // Tentativa de cadastro duplicado

            // Listar alunos
            alunoMemoryService.listarAlunos();

            // Remover aluno
            //alunoMemoryService.removerAluno(aluno);
            // System.out.println("Aluno removido com sucesso!");

        } catch (AlunoNaoEncontradoException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        } catch (CadastroExistenteException e) {
            throw new RuntimeException(e);
        }
    }
}
