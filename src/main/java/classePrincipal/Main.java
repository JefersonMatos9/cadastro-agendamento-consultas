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
        CadastroAlunos cadastroAlunos = new CadastroAlunos();
        CadastroFuncionarias cadastroFuncionarias = new CadastroFuncionarias();
        AgendarHorario agendarHorario = new AgendarHorario();

        // Cadastrando novos alunos
        try {
            cadastroAlunos.cadastrarAluno("Miguel Araujo Matos", LocalDate.of(2018, 12, 2), "Rua João Bernardo Muniz", "Santa Terezinha", "Américo Brasiliense", "SP");
            cadastroAlunos.cadastrarAluno("Jeferson Matos", LocalDate.of(2018, 12, 2), "Rua João Bernardo Muniz", "Santa Terezinha", "Américo Brasiliense", "SP");
            cadastroAlunos.cadastrarAluno("Josiane Matos", LocalDate.of(2018, 12, 2), "Rua João Bernardo Muniz", "Santa Terezinha", "Américo Brasiliense", "SP");

            // Pesquisando aluno
            Cadastro aluno1 = cadastroAlunos.buscarAluno("Miguel Araujo Matos");
            Cadastro aluno2 = cadastroAlunos.buscarAluno("Jeferson Matos" );
            Cadastro aluno3 = cadastroAlunos.buscarAluno("Josiane Matos");
            System.out.println("Aluno encontrado");
            System.out.println("Nome: " + aluno1.getNome());
            System.out.println("Nome: " + aluno2.getNome());
            System.out.println("Nome: " + aluno3.getNome());

            // Pesquisando e adicionando aluno ao agendamento
            agendarHorario.adicionarAluno((Alunos) aluno1);
            agendarHorario.adicionarAluno((Alunos) aluno2);
            agendarHorario.adicionarAluno((Alunos) aluno3);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // Cadastrando novas funcionárias
        try {
            cadastroFuncionarias.cadastrarFuncionaria("Bruna", "123456", LocalDate.of(1996, 5, 15), "Rua Joao Jose", "Centro", "Araraquara", "SP", "Psicopedagogia", 0, 25.95);
            cadastroFuncionarias.cadastrarFuncionaria("Daniela", "1234567", LocalDate.of(1996, 5, 15), "Rua Joao Jose", "Centro", "Araraquara", "SP", "Fonoaudiologa", 0, 25.95);
            cadastroFuncionarias.cadastrarFuncionaria("Amanda", "12345678", LocalDate.of(1996, 5, 15), "Rua Joao Jose", "Centro", "Araraquara", "SP", "Psicologa", 0, 25.95);

            System.out.println("Lista Funcionários:");
            cadastroFuncionarias.exibirLista();

            // Adicionando funcionárias ao agendamento
            agendarHorario.adicionarFuncionaria(cadastroFuncionarias.pesquisarFuncionaria("Bruna"));
            agendarHorario.adicionarFuncionaria(cadastroFuncionarias.pesquisarFuncionaria("Daniela"));
            agendarHorario.adicionarFuncionaria(cadastroFuncionarias.pesquisarFuncionaria("Amanda"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // Tentando agendar um horário
        try {
            String agendamento1 = agendarHorario.agendarHorario("Miguel Araujo Matos", "Bruna", LocalDate.of(2024, 7, 8), "10:00");
            System.out.println(agendamento1);
        }catch (HorarioIndisponivelException e){
            System.err.println(e.getMessage());}
        //Tentando Agendar outro Horario
        try {
            String agendamento2 = agendarHorario.agendarHorario("Jeferson Matos", "Bruna", LocalDate.of(2024, 7, 8), "10:00");
            System.out.println(agendamento2);
        }catch (HorarioIndisponivelException e){
            System.err.println(e.getMessage());}
        //Tentando Agendar outro horario
        try{
            String agendamento3 = agendarHorario.agendarHorario("Jeferson Matos","Bruna",LocalDate.of(2024,7,9),"10:00");
            System.out.println(agendamento3);
        } catch (HorarioIndisponivelException e) {
            System.err.println(e.getMessage());
        }
    }
}
