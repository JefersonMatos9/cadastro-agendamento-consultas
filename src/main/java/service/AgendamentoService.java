package service;

import agendamento.VerificandoAgendamento;
import atualizadados.AtualizandoDados;
import data.populate.AgendamentoDatePopulator;
import mapper.util.MapperUtils;
import model.Aluno;
import model.Funcionario;
import cadastro.exception.HorarioIndisponivelException;
import database.DataBaseConnection;

import java.sql.*;
import java.time.LocalDate;

public class AgendamentoService {

    private MapperUtils mapperUtils = new MapperUtils();
    private AgendamentoDatePopulator agendamentoDatePopulator = new AgendamentoDatePopulator();
    private AlunoService alunoService = new AlunoService();
    private FuncionarioService funcionarioService = new FuncionarioService();


    public void agendarHorario(String alunoCpf, String funcionariaCpf, LocalDate data, String hora) throws HorarioIndisponivelException, SQLException {
        Aluno aluno = alunoService.pesquisarAluno(alunoCpf);
        Funcionario funcionario = funcionarioService.pesquisarFuncionario(funcionariaCpf);

        verificacaoExistencia(aluno, funcionario);

        if (!VerificandoAgendamento.isHorarioDisponivelParaAluno(aluno.getNome(), data, hora) ||
                !VerificandoAgendamento.isHorarioDisponivelParaFuncionaria(funcionario.getNome(), data, hora)) {
            throw new HorarioIndisponivelException("Horário indisponível para a funcionária " + funcionariaCpf + " ou para o aluno " + alunoCpf);
        }

        String query = "INSERT INTO agendamentos(nome_aluno, nome_funcionaria, data, hora) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            agendamentoDatePopulator.preencherDadosAgendamento(pstmt, funcionario, aluno, data, hora);

            atualizarSessaoAluno(aluno);
            atualizarSessaoFuncionario(funcionario);

            atualizarTotalAPagar(aluno);
            atualizarTotalAReceber(funcionario);

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao agendar horário: " + e.getMessage(), e);
        }
    }

    public boolean cancelarAgendamentoPorId(int agendamentoId) {
        String sql = "DELETE FROM agendamentos WHERE id = ?";
        try (Connection con = DataBaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, agendamentoId);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao cancelar o agendamento: " + e.getMessage());
            return false;
        }
    }


    private void verificacaoExistencia(Aluno aluno, Funcionario funcionario) throws HorarioIndisponivelException {
        if (aluno == null || funcionario == null) {
            throw new HorarioIndisponivelException("Aluno ou funcionária não encontrados.");
        }
    }

    private void atualizarSessaoAluno(Aluno aluno) throws SQLException {
        int sessaoAtualAluno = AtualizandoDados.obterSessoesAtualizadasAluno(aluno.getId());
        int novaSessaoAluno = sessaoAtualAluno + 1;
        AtualizandoDados.atualizarSessaoAluno(aluno.getId(), novaSessaoAluno);
    }

    private void atualizarSessaoFuncionario(Funcionario funcionario) throws SQLException {
        int sessaoAtualFuncionario = AtualizandoDados.obterSessoesAtualizadasFuncionario(funcionario.getId());
        int novaSessaoFuncionario = sessaoAtualFuncionario + 1;
        AtualizandoDados.atualizarSessaoFuncionario(funcionario.getId(), novaSessaoFuncionario);
    }

    private void atualizarTotalAPagar(Aluno aluno) throws SQLException {
        double totalPagar = AtualizandoDados.calcularTotalPagar(aluno.getId());
        AtualizandoDados.atualizarValores(aluno.getId(), totalPagar);
    }

    private void atualizarTotalAReceber(Funcionario funcionario) throws SQLException {
        double totalReceber = AtualizandoDados.calcularTotalReceber(funcionario.getId());
        AtualizandoDados.atualizarValores(funcionario.getId(), totalReceber);
    }
}