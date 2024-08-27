package classePrincipal;

import agendamento.SistemaDeAgendamento;
import cadastro.alunos.Alunos;
import cadastro.alunos.AlunoRepository;
import cadastro.cadastroFuncionarios.CadastroFuncionarias;
import cadastro.cadastroFuncionarios.Funcionarias;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        //CRIANDO OS OBJETOS DAS CLASSES
        Alunos aluno = new Alunos();
        Funcionarias funcionaria = new Funcionarias();
        AlunoRepository cadastroAlunos = new AlunoRepository();
        CadastroFuncionarias cadastroFuncionarias = new CadastroFuncionarias();
        SistemaDeAgendamento sistemaDeAgendamento = new SistemaDeAgendamento();


        try {
            //CADASTRANDO NOVOS ALUNOS
            aluno.setNome("Otavio");
            aluno.setCpf("252525");
            aluno.setDataNasc(LocalDate.of(2019, 12, 2));
            aluno.setRua("Rua Y");
            aluno.setBairro("Bairro B");
            aluno.setCidade("Cidade U");
            aluno.setEstado("Estado O");
            aluno.setQuantidadeSessoes(0);
            aluno.setPrecoPorHora(70);
            aluno.calcularTotalAPagar(); //CALCULA E DEFINE O TOTAL A PAGAR
            cadastroAlunos.cadastrarAluno(aluno); //REALIZA O CADASTRO

            //CADASTRANDO NOVAS FUNCIONARIAS
            funcionaria.setNome("Naiara");
            funcionaria.setCpf("78945625");
            funcionaria.setDataNasc(LocalDate.of(2018, 1, 15));
            funcionaria.setRua("Rua C");
            funcionaria.setBairro("Bairro C");
            funcionaria.setCidade("Cidade C");
            funcionaria.setEstado("Estado C");
            funcionaria.setFuncao("Terapeuta Ocupacional");
            funcionaria.setHoraTrabalhada(0);
            funcionaria.setSalario(25.5);
            funcionaria.getTotalAReceber();
            //cadastroFuncionarias.cadastrarFuncionaria(funcionaria); //REALIZA O CADASTRO

            //PESQUISANDO FUNCIONARIA USANDO CPF
            //cadastroFuncionarias.pesquisarFuncionaria("78945625");

            //EXCLUINDO UMA FUNCIONARIA USANDO O CPF
            funcionaria.setCpf("78945625");
            //cadastroFuncionarias.excluirFuncionaria(funcionaria);

            //PESQUISANDO UM ALUNO USANDO CPF
            //cadastroAlunos.pesquisarAluno("123456");

            //EXCLUINDO UM ALUNO USANDO CPF
            aluno.setCpf("123456789");
            //cadastroAlunos.excluirAluno(aluno);

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