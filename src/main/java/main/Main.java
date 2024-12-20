package main;


import exception.AgendamentoException;
import service.AgendamentoService;
import model.Aluno;
import service.AlunoService;
import service.FuncionarioService;
import model.Funcionario;
import update.service.DataUpdaterService;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        //CRIANDO OS OBJETOS DAS CLASSES
        Aluno aluno = new Aluno();
        Funcionario funcionario = new Funcionario();
        AlunoService alunoService = new AlunoService();
        FuncionarioService funcionarioService = new FuncionarioService();
        AgendamentoService sistemaDeAgendamento = new AgendamentoService();
        DataUpdaterService dataUpdaterService = new DataUpdaterService();


        try {
            //CADASTRANDO NOVOS ALUNOS
            aluno.setNome("Paulo");
            aluno.setCpf("2222");
            aluno.setTelefone("1699");
            aluno.setDataNasc(LocalDate.of(2018, 12, 2));
            aluno.setRua("Rua Y");
            aluno.setBairro("Bairro B");
            aluno.setCidade("Cidade U");
            aluno.setEstado("SP");
            aluno.setPrecoPorHora(70);
            //alunoService.cadastrarAluno(aluno); //REALIZA O CADASTRO

            //CADASTRANDO NOVAS FUNCIONARIA
            funcionario.setNome("Amanda");
            funcionario.setCpf("3455");
            funcionario.setTelefone("12345684");
            funcionario.setDataNasc(LocalDate.of(2018, 1, 15));
            funcionario.setRua("Rua A");
            funcionario.setBairro("Bairro X");
            funcionario.setCidade("Cidade C");
            funcionario.setEstado("SP");
            funcionario.setFuncao("Fisioterapeuta");
            funcionario.setSalario(25.5);
            //funcionarioService.inserirFuncionario(funcionario); //REALIZA O CADASTRO

            //ATUALIZANDO DADOS DO FUNCIONARIO
            //funcionario.setId(2);
            //funcionarioService.atualizarFuncionario(funcionario);

            //PESQUISANDO FUNCIONARIA USANDO CPF
            //funcionarioService.pesquisarFuncionario("78945625");

            //EXCLUINDO UMA FUNCIONARIA USANDO O CPF
            funcionario.setCpf("12345");
            funcionarioService.removerFuncionario(funcionario);

            //ATUALIZANDO DADOS DO ALUNO USANDO ID
            //aluno.setId(1);
            //alunoService.atualizarAluno(aluno);

            //PESQUISANDO UM ALUNO USANDO CPF
            //cadastroAlunos.pesquisarAluno("123456");

            //EXCLUINDO UM ALUNO USANDO CPF
            aluno.setCpf("12342");
            alunoService.excluirAluno(aluno);


            //AGENDAR HORARIO
           // sistemaDeAgendamento.agendarHorario("12342","12345",LocalDate.of(2024,6,8), LocalTime.of(23,30));

            //sistemaDeAgendamento.cancelarAgendamento(11);

            //CANCELAR AGENDAMENTO
            //sistemaDeAgendamento.cancelarAgendamentoPorId(2);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
// VERIFICAR SE ESTA ATUALIZANDO OS HORARIOS E OS VALORES