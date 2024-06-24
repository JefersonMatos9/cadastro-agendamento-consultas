package classePrincipal;

import cadastro.alunos.CadastroAlunos;
import cadastro.cadastro.Cadastro;
import cadastro.cadastroFuncionarios.CadastroFuncionarias;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        CadastroAlunos cadastroAlunos = new CadastroAlunos();
        // Cadastrando um novo aluno
        try {
            cadastroAlunos.cadastrarAluno("Miguel Araujo Matos", LocalDate.of(2018, 12, 2), "Rua João Bernardo Muniz", "Santa Terezinha", "Américo Brasiliense", "SP");
            cadastroAlunos.cadastrarAluno("Jeferson Matos", LocalDate.of(2018, 12, 2), "Rua João Bernardo Muniz", "Santa Terezinha", "Américo Brasiliense", "SP");
            cadastroAlunos.cadastrarAluno("Josiane Matos", LocalDate.of(2018, 12, 2), "Rua João Bernardo Muniz", "Santa Terezinha", "Américo Brasiliense", "SP");
            // Pesquisando aluno
            Cadastro aluno = cadastroAlunos.buscarAluno("Miguel Araujo Matos");
            System.out.println("Aluno encontrado");
            System.out.println("Nome: " + aluno.getNome());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // Cadastrando uma nova funcionária
        try {
            CadastroFuncionarias cadastro = new CadastroFuncionarias();
            System.out.println("Cadastrando Bruna...");
            cadastro.cadastrarFuncionaria("Bruna", "123456", LocalDate.of(1996, 5, 15), "Rua Joao Jose", "Centro", "Araraquara", "SP", "Psicopedagogia", 0, 25.95);
            System.out.println("Cadastrando Daniela...");
            cadastro.cadastrarFuncionaria("Daniela", "1234567", LocalDate.of(1996, 5, 15), "Rua Joao Jose", "Centro", "Araraquara", "SP", "Fonoaudiologa", 0, 25.95);
            System.out.println("Cadastrando Amanda...");
            cadastro.cadastrarFuncionaria("Amanda", "12345678", LocalDate.of(1996, 5, 15), "Rua Joao Jose", "Centro", "Araraquara", "SP", "Psicologa", 0, 25.95);

            System.out.println("Lista Funcionarios");
            cadastro.exibirLista();
            // Tentando excluir funcionária
            System.out.println("Tentando excluir Amanda...");
            try {
                cadastro.excluirFuncionaria("Amanda");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            // Exibindo lista de funcionárias
            System.out.println("Lista de Funcionárias:");
            cadastro.exibirLista();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
