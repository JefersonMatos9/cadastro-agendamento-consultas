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
            cadastroAlunos.cadastrarAluno("Miguel", LocalDate.of(2018, 12, 2), "Rua A", "Bairro A", "Cidade A", "Estado A",0,70,0);
            cadastroAlunos.cadastrarAluno("Jeferson", LocalDate.of(2018, 12, 2), "Rua B", "Bairro B", "Cidade B", "Estado B",0,70,0);
            cadastroAlunos.cadastrarAluno("Josiane", LocalDate.of(2018, 12, 2), "Rua C", "Bairro c", "Cidade C", "Estado C",0,70,0);

            // Cadastrando novas funcionárias
            cadastroFuncionarias.cadastrarFuncionaria("Amanda", "12345678", LocalDate.of(1996, 5, 15), "Rua C", "Bairro C", "Cidade C", "Estado C", "Profissão C", 0, 25.95,0);
            cadastroFuncionarias.cadastrarFuncionaria("Bruna", "123456", LocalDate.of(1996, 5, 15), "Rua A", "Bairro A", "Cidade A", "Estado A", "Profissão A", 0, 25.95,0);
            cadastroFuncionarias.cadastrarFuncionaria("Daniela", "1234567", LocalDate.of(1996, 5, 15), "Rua B", "Bairro B", "Cidade B", "Estado B", "Profissão B", 0, 25.95,0);

            // Adicionar funcionarios a agenda
            agendarHorario.adicionarFuncionaria(cadastroFuncionarias.pesquisarFuncionaria("Bruna"));
            agendarHorario.adicionarFuncionaria(cadastroFuncionarias.pesquisarFuncionaria("Amanda"));
            //Adicionar aluno a agenda
            agendarHorario.adicionarAluno(cadastroAlunos.buscarAluno("Jeferson"));

            //Agendar Horario
            String resultado = agendarHorario.agendarHorario("Jeferson","Bruna",LocalDate.of(2024,8,10),"10:00");
            //Tentando o mesmo Horario com paciente diferente
            try {
                String resultado1 = agendarHorario.agendarHorario("Miguel", "Bruna", LocalDate.of(2024, 8, 10), "10:00");
            }catch (HorarioIndisponivelException e){
                System.out.println(e.getMessage());
            }
            //Agendando outro horario para o aluno
            String resultado3 = agendarHorario.agendarHorario("Jeferson","Amanda",LocalDate.of(2024,8,10),"11:00");

            // Pesquisando aluno
            Alunos aluno1 = cadastroAlunos.buscarAluno("Miguel");
            Alunos aluno2 = cadastroAlunos.buscarAluno("Jeferson" );
            Alunos aluno3 = cadastroAlunos.buscarAluno("Josiane");

            cadastroFuncionarias.excluirFuncionaria("Daniela");
            Funcionarias pesquisarFuncionaria = cadastroFuncionarias.pesquisarFuncionaria("Daniela");

            try{
                Funcionarias funcionariaPesquisada = cadastroFuncionarias.pesquisarFuncionaria("Daniela");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
