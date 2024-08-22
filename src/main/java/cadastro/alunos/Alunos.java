package cadastro.alunos;

import agendamento.Agendador;
import cadastro.cadastro.Cadastro;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Alunos extends Cadastro implements Agendador {
    private boolean horaReservada;
    private int quantidadeSessoes;
    private double precoPorHora;
    private double totalAPagar;

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

    private Map<LocalDate, Set<String>> horariosAgendados = new HashMap<>();

    public void calcularTotalAPagar() {
        this.totalAPagar = this.quantidadeSessoes * this.precoPorHora;
    }


    @Override
    public boolean isHorarioDisponivel(LocalDate data, String hora) {
        Set<String> horarios = horariosAgendados.get(data);
        return horarios == null || !horarios.contains(hora);
    }


    @Override
    public void agendarhorario(LocalDate data, String hora) {
        Set<String> horarios = horariosAgendados.get(data);
        if (horarios == null) {
            horarios = new HashSet<>();
            horariosAgendados.put(data, horarios);
        }
        horarios.add(hora);
        quantidadeSessoes++;
        calcularTotalAPagar();
    }
}
