package memoria.principal;

import exception.AlunoNaoEncontradoException;
import exception.CadastroExistenteException;
import exception.FuncionariaNaoEncontradaException;
import exception.HorarioIndisponivelException;
import memory.service.AgendamentoMemoryService;
import memory.service.AlunoMemoryService;
import memory.service.FuncionarioMemoryService;
import model.Aluno;
import model.Funcionario;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        AlunoMemoryService alunoMemoryService = new AlunoMemoryService();
        FuncionarioMemoryService funcionarioMemoryService = new FuncionarioMemoryService();
        AgendamentoMemoryService agendamentoMemoryService = new AgendamentoMemoryService(alunoMemoryService,funcionarioMemoryService);

        Aluno aluno = new Aluno();
        aluno.setNome("Deco");
        aluno.setCpf("1234");
        // Configurar outros atributos do aluno...

        Aluno aluno1 = new Aluno();
        aluno1.setNome("HAHAHA");
        aluno1.setCpf("12346");

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Outro Deco");
        aluno2.setCpf("12345");


        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Bru");
        funcionario.setCpf("122");
        //configurar outros atributos do funcionario...

        Funcionario funcionario1 = new Funcionario();
        funcionario1.setNome("Gege");
        funcionario1.setCpf("222");

        Funcionario funcionario2 = new Funcionario();
        funcionario2.setNome("Dodo");
        funcionario2.setCpf("555");

        try {
            // Cadastro dos alunos
            alunoMemoryService.cadastrarAluno(aluno);
            alunoMemoryService.cadastrarAluno(aluno1);
            alunoMemoryService.cadastrarAluno(aluno2);

            // Listar alunos
            alunoMemoryService.listarAlunos();

            // Remover aluno
            alunoMemoryService.removerAluno(aluno1);

            //Editar ALuno
            aluno.setNome("Miguel");
            alunoMemoryService.editarAluno(aluno2);


            //Cadastrar Funcionario
            funcionarioMemoryService.cadastrarFuncionario(funcionario);
            funcionarioMemoryService.cadastrarFuncionario(funcionario1);
            funcionarioMemoryService.cadastrarFuncionario(funcionario2);

            //Listar Funcionario
            funcionarioMemoryService.listarFuncionario();

            //Remover Funcionario
            funcionarioMemoryService.removerFuncionario(funcionario2);

            //Editar Funcionario
            funcionario1.setNome("GgGg");
            funcionario1.setCpf("555");
            funcionarioMemoryService.editarFuncionario(funcionario1);

            //AGENDAMENTO DE HORARIOS
            LocalDate data = LocalDate.of(2024, 12, 13);
            LocalTime hora = LocalTime.of(11, 30);
            agendamentoMemoryService.agendarConsulta("12345", "GgGg", data, hora);

            LocalDate data1 = LocalDate.of(2024, 12, 13);
            LocalTime hora1 = LocalTime.of(12, 30);
            agendamentoMemoryService.agendarConsulta("12345", "Bru", data1, hora1);

        } catch (AlunoNaoEncontradoException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        } catch (CadastroExistenteException e) {
            throw new RuntimeException(e);
        } catch (FuncionariaNaoEncontradaException e) {
            throw new RuntimeException(e);
        } catch (HorarioIndisponivelException e) {
            throw new RuntimeException(e);
        }
    }
}
