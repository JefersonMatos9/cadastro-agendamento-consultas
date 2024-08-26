package cadastro.cadastroFuncionarios;

import agendamento.VerificandoAgendamento;
import cadastro.cadastro.Cadastro;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Funcionarias extends Cadastro implements VerificandoAgendamento {
    private int id; // Adicionando o atributo 'id'
    private String funcao;
    private int horaTrabalhada;
    private double salario;
    private double totalAReceber;

    // Getter e Setter para 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public int getHoraTrabalhada() {
        return horaTrabalhada;
    }

    public void setHoraTrabalhada(int horaTrabalhada) {
        this.horaTrabalhada = horaTrabalhada;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
        atualizarTotalAReceber();
    }

    public double getTotalAReceber() {
        return totalAReceber;
    }

    public void setTotalAReceber(double totalAReceber) {
        this.totalAReceber = totalAReceber;
    }

    public void atualizarTotalAReceber() {
        this.totalAReceber = this.horaTrabalhada * this.salario;
    }
}
