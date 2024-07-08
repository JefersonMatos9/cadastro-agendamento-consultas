package cadastro.cadastroFuncionarios;

import cadastro.cadastro.Cadastro;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Funcionarias extends Cadastro {
    private String funcao;
    private int horaTrabalhada;
    private double salario;
    private Map<LocalDate,Set<String>> horariosAgendados;

    public Funcionarias(){
        this.horariosAgendados = new HashMap<>();
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
    }
    public LocalDate getDataDisponivel(){
        return null;
    }

    public boolean isHorarioDisponivel(LocalDate data,String hora){
        if (!horariosAgendados.containsKey(data)){  // containsKey verifica se a data ja tem entrada no map
            return true;
        }
        return !horariosAgendados.get(data).contains(hora);// se a data estiver no mapa Contains verifica se o horario esta disponivel
    }

    public void agendarHorario(LocalDate data,String hora){
        if (!horariosAgendados.containsKey(data)){
            horariosAgendados.put(data,new HashSet<>()); // se a nao existir entrada no map para a data, inicia um novo conjunto hashSet para armazenar os horarios agendados
        }
        horariosAgendados.get(data).add(hora); // adiciona a hora especifica ao conjunto de horarios agendados para a data fornecida
        this.horaTrabalhada++; // incrementa as horas trabalhadas dos funcionarios no mes
    }
}
