package data.populate;

import model.Aluno;
import model.Funcionario;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class AgendamentoDatePopulator {

    //Preencher dados do agendamento do funcionario e do aluno
    public void preencherDadosAgendamento(PreparedStatement pstmt, Funcionario funcionario, Aluno aluno, LocalDate data, String hora) throws SQLException {
        pstmt.setString(1, aluno.getNome());
        pstmt.setString(2, funcionario.getNome());
        pstmt.setDate(3, Date.valueOf(data));
        pstmt.setString(4, hora);
        pstmt.executeUpdate();
    }

    // Método para preencher os dados do aluno no PreparedStatement
    public void preencherDadosAluno(PreparedStatement pstmt, Aluno aluno) throws SQLException {
        pstmt.setString(1, aluno.getNome());
        pstmt.setString(2, aluno.getCpf());
        pstmt.setString(3, aluno.getTelefone());
    pstmt.setDate(4, java.sql.Date.valueOf(aluno.getDataNasc()));
        pstmt.setString(5, aluno.getRua());
        pstmt.setString(6, aluno.getBairro());
        pstmt.setString(7, aluno.getCidade());
        pstmt.setString(8, aluno.getEstado());
        pstmt.setInt(9, aluno.getQuantidadeSessoes());
        pstmt.setDouble(10, aluno.getPrecoPorHora());
        pstmt.setDouble(11, aluno.getTotalAPagar());
    }

    // Método para preencher os dados do funcionario no PreparedStatement
    public void preencherDadosFuncionario(PreparedStatement pstmt, Funcionario funcionario) throws SQLException {
        pstmt.setString(1, funcionario.getNome());
        pstmt.setString(2, funcionario.getCpf());
        pstmt.setString(3,funcionario.getTelefone());
        pstmt.setDate(4, java.sql.Date.valueOf(funcionario.getDataNasc()));
        pstmt.setString(5, funcionario.getRua());
        pstmt.setString(6, funcionario.getBairro());
        pstmt.setString(7, funcionario.getCidade());
        pstmt.setString(8, funcionario.getEstado());
        pstmt.setString(9, funcionario.getFuncao());
        pstmt.setInt(10, funcionario.getHoraTrabalhada());
        pstmt.setDouble(11, funcionario.getSalario());
        pstmt.setDouble(12, funcionario.getTotalAReceber());
    }

    public void atualizarDadosFuncionario(PreparedStatement pstmt,Funcionario funcionario)throws SQLException{
        pstmt.setString(1, funcionario.getNome());
        pstmt.setString(2, funcionario.getTelefone());
        pstmt.setDate(3, java.sql.Date.valueOf(funcionario.getDataNasc()));
        pstmt.setString(4, funcionario.getRua());
        pstmt.setString(5, funcionario.getBairro());
        pstmt.setString(6, funcionario.getCidade());
        pstmt.setString(7, funcionario.getEstado());

        int parameterIndex = 8; // Índice para o próximo parâmetro
        if (funcionario.getCpf() != null) {
            pstmt.setString(parameterIndex++, funcionario.getCpf()); // Define o CPF se não for nulo
        }
        pstmt.setString(parameterIndex++, funcionario.getFuncao()); // Função
        pstmt.setDouble(parameterIndex++, funcionario.getSalario()); // Salário
        pstmt.setInt(parameterIndex, funcionario.getId()); // ID do funcionário
    }

    public void atualizarDadosAluno(PreparedStatement pstmt, Aluno aluno)throws SQLException{
        pstmt.setString(1, aluno.getNome());
        pstmt.setString(2, aluno.getTelefone());
        pstmt.setDate(3, java.sql.Date.valueOf(aluno.getDataNasc()));
        pstmt.setString(4, aluno.getRua());
        pstmt.setString(5, aluno.getBairro());
        pstmt.setString(6, aluno.getCidade());
        pstmt.setString(7, aluno.getEstado());

        // Índice para o próximo parâmetro
        int parameterIndex = 8;
        if (aluno.getCpf() != null) {
            pstmt.setString(parameterIndex++, aluno.getCpf()); // Define o CPF se não for nulo
        }
        // Preencher os outros parâmetros
        pstmt.setInt(parameterIndex++, aluno.getQuantidadeSessoes());
        pstmt.setDouble(parameterIndex++, aluno.getPrecoPorHora());
        pstmt.setDouble(parameterIndex++, aluno.getTotalAPagar());
        pstmt.setInt(parameterIndex, aluno.getId()); // ID do aluno
    }

    public void verificandoDisponibilidade(PreparedStatement pstmt,String nome,LocalDate data, String hora)throws SQLException {
        pstmt.setString(1, nome);
        pstmt.setDate(2, Date.valueOf(data));
        pstmt.setString(3, hora);
    }
}
