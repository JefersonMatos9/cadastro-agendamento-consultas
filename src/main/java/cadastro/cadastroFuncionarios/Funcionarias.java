package cadastro.cadastroFuncionarios;

import cadastro.cadastro.Cadastro;

public class Funcionarias extends Cadastro {
    private String funcao;
    private int horaTrabalhada;
    private double salario;
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
    }
}
