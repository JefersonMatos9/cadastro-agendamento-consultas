package cadastro.cadastroFuncionarios;

import agendamento.Agendador;
import cadastro.cadastro.Cadastro;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Funcionarias extends Cadastro implements Agendador {
    private String funcao;
    private int horaTrabalhada;
    private double salario;
    private Map<LocalDate,Set<String>> horariosAgendados = new HashMap<>();

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
    public LocalDate getDataDisponivel(){
        return null;
    }

    @Override
    public boolean isHorarioDisponivel(LocalDate data,String hora){
     Set<String>horarios = horariosAgendados.get(data);
     return horarios == null || !horarios.contains(hora);
    }

    @Override
    public void agendarhorario(LocalDate data, String hora) {
        Set<String> horarios = horariosAgendados.get(data);
        if (horarios == null){
            horarios = new HashSet<>();
            horariosAgendados.put(data,horarios);
        }
        horarios.add(hora);
        horaTrabalhada++;
    }


}
