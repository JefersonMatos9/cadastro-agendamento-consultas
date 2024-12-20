package model;



import java.time.LocalTime;

public class Funcionario extends Cadastro {

    private int id; // Adicionando o atributo 'id'
    private String funcao;
    private int horaTrabalhada;
    private double salario;
    private double totalAReceber;
    private LocalTime horaReservada;


    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalTime getHoraReservada() {
        return horaReservada;
    }

    public void setHoraReservada(LocalTime horaReservada) {
        this.horaReservada = horaReservada;
    }
}
