package cadastro.alunos;

import agendamento.VerificandoAgendamento;
import cadastro.cadastro.Cadastro;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Alunos extends Cadastro implements VerificandoAgendamento {
    private int id;  // Adicionando o atributo 'id'
    private boolean horaReservada;
    private int quantidadeSessoes;
    private double precoPorHora;
    private double totalAPagar;

    // Getter e Setter para 'id'
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isHoraReservada() {
        return horaReservada;
    }

    public void setHoraReservada(boolean horaReservada) {
        this.horaReservada = horaReservada;
    }

    public int getQuantidadeSessoes() {
        return quantidadeSessoes;
    }

    public void setQuantidadeSessoes(int quantidadeSessoes) {
        this.quantidadeSessoes = quantidadeSessoes;
        calcularTotalAPagar();
    }

    public double getPrecoPorHora() {
        return precoPorHora;
    }

    public void setPrecoPorHora(double precoPorHora) {
        this.precoPorHora = precoPorHora;
        calcularTotalAPagar();
    }

    public double getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(double totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    public void calcularTotalAPagar() {
        this.totalAPagar = this.quantidadeSessoes * this.precoPorHora;
    }

}
