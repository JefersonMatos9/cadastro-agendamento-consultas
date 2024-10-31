package memory.service;

import exception.AlunoNaoEncontradoException;
import exception.CadastroExistenteException;
import exception.FuncionariaNaoEncontradaException;
import exception.HorarioIndisponivelException;
import model.Aluno;
import model.Funcionario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoMemoryService {

    private AlunoMemoryService alunoMemoryService;
    private FuncionarioMemoryService funcionarioMemoryService;
    private List<String> agendamento = new ArrayList<>();

    public AgendamentoMemoryService(AlunoMemoryService alunoMemoryService, FuncionarioMemoryService funcionarioMemoryService) {
        this.alunoMemoryService = alunoMemoryService;
        this.funcionarioMemoryService = funcionarioMemoryService;
    }

    public boolean verificarDisponibilidade(LocalDate data, LocalTime hora) throws AlunoNaoEncontradoException, FuncionariaNaoEncontradaException {
        // Verifica se a data e hora já estão reservadas para algum aluno
        for (Aluno aluno : alunoMemoryService.getAlunosCadastrados()) {
            if (aluno.getHoraReservada() != null && aluno.getDataReservada() != null) {
                if (aluno.getHoraReservada().equals(hora) && aluno.getDataReservada().equals(data)) {
                    return false; // Indisponível se o aluno já tiver a data e hora reservadas
                }
            }
        }

        // Verifica se a data e hora já estão reservadas para algum funcionário
        for (Funcionario funcionario : funcionarioMemoryService.getFuncionarioCadastrado()) {
            if (funcionario.getHoraReservada() != null && funcionario.getDataReservada() != null) {
                if (funcionario.getHoraReservada().equals(hora) && funcionario.getDataReservada().equals(data)) {
                    return false; // Indisponível se o funcionário já tiver a data e hora reservadas
                }
            }
        }

        return true; // Disponível se não houver conflitos
    }

    public void agendarConsulta(String cpfAluno, String nomeFuncionario, LocalDate data, LocalTime hora) throws HorarioIndisponivelException, FuncionariaNaoEncontradaException, AlunoNaoEncontradoException {
        Aluno aluno = alunoMemoryService.pesquisarAluno(cpfAluno);
        Funcionario funcionario = funcionarioMemoryService.pesquisarFuncionario(nomeFuncionario);

        if (verificarDisponibilidade(data, hora)) {
            agendamento.add("Consulta agendada para " + data + " às " + hora + " com " +
                    aluno.getNome() + " e " + funcionario.getNome());

            aluno.setHoraReservada(hora);
            aluno.setDataReservada(data);
            funcionario.setHoraReservada(hora);
            funcionario.setDataReservada(data);

            System.out.println("Horário reservado com sucesso, para o aluno: " + aluno.getNome() + ",com a funcionaria: " + funcionario.getNome());
        } else {
            throw new HorarioIndisponivelException("Horário indisponível para essa data.");
        }
    }

    public void listarAgendamento() {
        System.out.println("Agendamentos");
        for (String ag : agendamento) {
            System.out.println(ag);
        }
    }
}
