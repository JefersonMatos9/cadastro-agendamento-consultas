package model;

import check.availability.VerificandoAgendamento;

import java.time.LocalTime;

public class Aluno extends Cadastro  {
    private int id;  // Adicionando o atributo 'id'
    private LocalTime horaReservada;
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

    public LocalTime getHoraReservada() {
        return horaReservada;
    }

    public void setHoraReservada(LocalTime horaReservada) {
        this.horaReservada = horaReservada;
    }
}
