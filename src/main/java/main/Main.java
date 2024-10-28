package main;

import service.AgendamentoService;
import model.Aluno;
import service.AlunoService;
import service.FuncionarioService;
import model.Funcionario;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        //CRIANDO OS OBJETOS DAS CLASSES
        Aluno aluno = new Aluno();
        Funcionario funcionario = new Funcionario();
        AlunoService alunoService = new AlunoService();
        FuncionarioService funcionarioService = new FuncionarioService();
        AgendamentoService sistemaDeAgendamento = new AgendamentoService();


        try {
            //CADASTRANDO NOVOS ALUNOS
            aluno.setNome("Miguel Araujo Matos");
            aluno.setCpf("123422223");
            aluno.setTelefone("1699");
            aluno.setDataNasc(LocalDate.of(2018, 12, 2));
            aluno.setRua("Rua Y");
            aluno.setBairro("Bairro B");
            aluno.setCidade("Cidade U");
            aluno.setEstado("SP");
            aluno.setPrecoPorHora(70);
            //alunoService.cadastrarAluno(aluno); //REALIZA O CADASTRO

            //CADASTRANDO NOVAS FUNCIONARIA
            funcionario.setNome("Georgia Onofre");
            funcionario.setCpf("1234556");
            funcionario.setTelefone("12345684");
            funcionario.setDataNasc(LocalDate.of(2018, 1, 15));
            funcionario.setRua("Rua A");
            funcionario.setBairro("Bairro X");
            funcionario.setCidade("Cidade C");
            funcionario.setEstado("SP");
            funcionario.setFuncao("Terapeuta Ocupacional");
            funcionario.setSalario(25.5);
            //funcionarioService.inserirFuncionario(funcionario); //REALIZA O CADASTRO

            //ATUALIZANDO DADOS DO FUNCIONARIO
            //funcionario.setId(2);
            //funcionarioService.atualizarFuncionario(funcionario);

            //PESQUISANDO FUNCIONARIA USANDO CPF
            funcionarioService.pesquisarFuncionario("78945625");

            //EXCLUINDO UMA FUNCIONARIA USANDO O CPF
            //funcionario.setCpf("123455");
            //funcionarioService.removerFuncionario(funcionario);

            //ATUALIZANDO DADOS DO ALUNO USANDO ID
            //aluno.setId(1);
            //alunoService.atualizarAluno(aluno);

            //PESQUISANDO UM ALUNO USANDO CPF
            //cadastroAlunos.pesquisarAluno("123456");

            //EXCLUINDO UM ALUNO USANDO CPF
            //aluno.setCpf("12342222");
            //alunoService.excluirAluno(aluno);


            //AGENDAR HORARIO
            //sistemaDeAgendamento.agendarHorario("Helena","Bruna",LocalDate.of(2024,8,27),"10:00");

            //CANCELAR AGENDAMENTO
            //sistemaDeAgendamento.cancelarAgendamentoPorId(2);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
// VERIFICAR SE ESTA ATUALIZANDO OS HORARIOS E OS VALORES