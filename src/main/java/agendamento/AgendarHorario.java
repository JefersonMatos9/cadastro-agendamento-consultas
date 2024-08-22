package agendamento;

import cadastro.alunos.Alunos;
import cadastro.cadastroFuncionarios.Funcionarias;
import cadastro.excessoes.HorarioIndisponivelException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AgendarHorario {
    private List<Alunos> listaAlunos = new ArrayList<>();
    private List<Funcionarias> listaFuncionarias = new ArrayList<>();

    public void adicionarAluno(Alunos aluno) {
        listaAlunos.add(aluno);
    }

    public void adicionarFuncionaria(Funcionarias funcionaria) {
        listaFuncionarias.add(funcionaria);
    }

    public String agendarHorario(String alunoNome, String funcionariaNome, LocalDate data, String hora) throws HorarioIndisponivelException {
        Alunos aluno = buscarAlunoPorNome(alunoNome);
        Funcionarias funcionaria = buscarFuncionariaPorNome(funcionariaNome);

        if (funcionaria == null) {
            throw new HorarioIndisponivelException("Funcionaria não encontrada.");
        }
        if (funcionaria.isHorarioDisponivel(data, hora) && aluno.isHorarioDisponivel(data, hora)) {
            funcionaria.agendarhorario(data, hora);
            aluno.agendarhorario(data, hora);
            return String.format("Horário agendado para %s com %s no dia %s às %s", alunoNome, funcionariaNome, data, hora);
        } else {
            throw new HorarioIndisponivelException("Horário indisponível para a funcionária " + funcionariaNome + ", ou para o aluno " + alunoNome);
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
