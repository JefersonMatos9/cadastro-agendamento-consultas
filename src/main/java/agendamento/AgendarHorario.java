package agendamento;

import cadastro.alunos.Alunos;
import cadastro.cadastroFuncionarios.Funcionarias;
import cadastro.excessoes.HorarioIndisponivelException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgendarHorario {
    private List<Alunos> listaAlunos;
    private List<Funcionarias> listaFuncionarias;
    public AgendarHorario() {
        listaAlunos = new ArrayList<>();
        listaFuncionarias = new ArrayList<>();
    }
    public void adicionarAluno(Alunos aluno) {
        listaAlunos.add(aluno);
    }
    public void adicionarFuncionaria(Funcionarias funcionaria) {
        listaFuncionarias.add(funcionaria);
    }
    public String agendarHorario(String alunoNome, String funcionariaNome, LocalDate data, String hora) throws HorarioIndisponivelException {
        Alunos aluno = buscarAlunoPorNome(alunoNome);
        Funcionarias funcionaria = buscarFuncionariaPorNome(funcionariaNome);

        if (aluno == null) {
            throw new IllegalArgumentException("Aluno " + alunoNome + " não encontrado.");
        }
        if (funcionaria == null){
            throw new IllegalArgumentException("Funcionaria " + funcionariaNome + " não encontrada.");
        }
            if (funcionaria.isHorarioDisponivel(data,hora)){
                funcionaria.agendarHorario(data,hora);
            return "Horário agendado para " + alunoNome + " com " + funcionariaNome + " para o dia " + data + " às " + hora;
        } else {
            throw new HorarioIndisponivelException("Horário indisponível para a funcionária " + funcionariaNome);
        }
    }
    private Alunos buscarAlunoPorNome(String nome) {
        for (Alunos aluno : listaAlunos) {
            if (aluno.getNome().equalsIgnoreCase(nome)) {
                return aluno;
            }
        }
        return null; // Retorna Null caso o aluno não seja encontrado
    }
    private Funcionarias buscarFuncionariaPorNome(String nome) {
        for (Funcionarias funcionaria : listaFuncionarias) {
            if (funcionaria.getNome().equalsIgnoreCase(nome)) {
                return funcionaria;
            }
        }
        return null; // Retorna null caso a funcionária não seja encontrada
    }
}
