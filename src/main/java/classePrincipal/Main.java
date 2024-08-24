package classePrincipal;

import agendamento.AgendarHorario;
import cadastro.alunos.Alunos;
import cadastro.alunos.CadastroAlunos;
import cadastro.cadastro.Cadastro;
import cadastro.cadastroFuncionarios.CadastroFuncionarias;
import cadastro.cadastroFuncionarios.Funcionarias;
import cadastro.excessoes.HorarioIndisponivelException;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Alunos aluno = new Alunos();
        Funcionarias funcionaria = new Funcionarias();
        CadastroAlunos cadastroAlunos = new CadastroAlunos();
        CadastroFuncionarias cadastroFuncionarias = new CadastroFuncionarias();
        AgendarHorario agendarHorario = new AgendarHorario();


        try {
             //Cadastrando novos alunos
            aluno.setNome("Marly");
            aluno.setDataNasc(LocalDate.of(2019, 12, 2));
            aluno.setRua("Rua Z");
            aluno.setBairro("Bairro K");
            aluno.setCidade("Cidade L");
            aluno.setEstado("Estado O");
            aluno.setQuantidadeSessoes(0);
            aluno.setPrecoPorHora(70);
            aluno.calcularTotalAPagar(); // calcula e define o total a pagar
            //cadastroAlunos.cadastrarAluno(aluno); // cadastro aluno

            // Cadastrando novas funcionárias
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
           // cadastroFuncionarias.cadastrarFuncionaria(funcionaria); //Cadastrando a funcionaria

            //PESQUISANDO FUNCIONARIA
         //   cadastroFuncionarias.pesquisarFuncionaria("78945625");

            //PESQUISANDO UM ALUNO
            cadastroAlunos.pesquisarAluno("123");

            //Excluindo funcionaria do banco de dados utilizando o ID.
            funcionaria.setCpf("1234567891");
           // cadastroFuncionarias.excluirFuncionaria(funcionaria);

            // Adicionar funcionarios a agenda
            //   agendarHorario.adicionarFuncionaria(cadastroFuncionarias.pesquisarFuncionaria("Bruna"));
            //   agendarHorario.adicionarFuncionaria(cadastroFuncionarias.pesquisarFuncionaria("Amanda"));
            //   //Adicionar aluno a agenda
            //    agendarHorario.adicionarAluno(cadastroAlunos.buscarAluno("Jeferson"));

            //Agendar Horario
            //    String resultado = agendarHorario.agendarHorario("Jeferson","Bruna",LocalDate.of(2024,8,10),"10:00");
            //Tentando o mesmo Horario com paciente diferente
            //    try {
            //         String resultado1 = agendarHorario.agendarHorario("Miguel", "Bruna", LocalDate.of(2024, 8, 10), "10:00");
            //     }catch (HorarioIndisponivelException e){
            //       System.out.println(e.getMessage());
            //  }
            //Agendando outro horario para o aluno
            //    String resultado3 = agendarHorario.agendarHorario("Jeferson","Amanda",LocalDate.of(2024,8,10),"11:00");

            // Pesquisando aluno
            //       Alunos aluno1 = cadastroAlunos.buscarAluno("Miguel");
            //     Alunos aluno2 = cadastroAlunos.buscarAluno("Jeferson" );
            //    Alunos aluno3 = cadastroAlunos.buscarAluno("Josiane");

            //       cadastroFuncionarias.excluirFuncionaria("Daniela");
            //     Funcionarias pesquisarFuncionaria = cadastroFuncionarias.pesquisarFuncionaria("Daniela");

            //        try{
            //          Funcionarias funcionariaPesquisada = cadastroFuncionarias.pesquisarFuncionaria("Daniela");
            //      }catch (Exception e){
            //     System.out.println(e.getMessage());
            //    }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
