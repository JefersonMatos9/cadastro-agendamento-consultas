package mapper.util;


import model.Aluno;
import model.Funcionario;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapperUtils {

    //Mapeamento do ResultSet para o objeto funcionarios
    public Funcionario mapearFuncionarioCompleto(ResultSet rs) throws SQLException {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(rs.getString("nome"));
        funcionario.setCpf(rs.getString("cpf"));
        funcionario.setTelefone(rs.getString("telefone"));
        funcionario.setDataNasc(rs.getDate("data_nascimento").toLocalDate());
        funcionario.setRua(rs.getString("rua"));
        funcionario.setBairro(rs.getString("bairro"));
        funcionario.setCidade(rs.getString("cidade"));
        funcionario.setEstado(rs.getString("estado"));
        funcionario.setFuncao(rs.getString("funcao"));
        funcionario.setHoraTrabalhada(rs.getInt("hora_trabalhada"));
        funcionario.setSalario(rs.getDouble("salario"));
        funcionario.setTotalAReceber(rs.getDouble("total_a_receber"));
        return funcionario; // Retorna o objeto com os dados
    }

    // Método para mapear os dados do ResultSet para um objeto Aluno
    public Aluno mapearAlunoCompleto(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setNome(rs.getString("nome"));
        aluno.setCpf(rs.getString("cpf"));
        aluno.setTelefone(rs.getString("telefone"));
        aluno.setDataNasc(rs.getDate("data_nascimento").toLocalDate());
        aluno.setRua(rs.getString("rua"));
        aluno.setBairro(rs.getString("bairro"));
        aluno.setCidade(rs.getString("cidade"));
        aluno.setEstado(rs.getString("estado"));
        aluno.setQuantidadeSessoes(rs.getInt("quantidade_sessoes"));
        aluno.setPrecoPorHora(rs.getDouble("preco_por_hora"));
        aluno.setTotalAPagar(rs.getDouble("total_a_pagar"));
        return aluno; //Retorna o aluno mapeado
    }

    // Mapeamento reduzido do ResultSet para o objeto Funcionario (somente ID e nome)
    public Funcionario mapearFuncionarioReduzido(ResultSet rs) throws SQLException {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(rs.getInt("id"));
        funcionario.setNome(rs.getString("nome"));
        return funcionario;
    }

    // Mapeamento reduzido do ResultSet para o objeto Aluno (somente ID e nome)
    public Aluno mapearAlunoReduzido(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setId(rs.getInt("id"));
        aluno.setNome(rs.getString("nome"));
        return aluno;
    }
}
