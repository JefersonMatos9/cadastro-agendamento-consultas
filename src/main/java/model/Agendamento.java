package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agendamento {
    private LocalTime hora;
    private LocalDate data;
    private boolean status;
    private String cpfAluno;
    private String cpfFuncionario;

    public Agendamento(LocalTime hora, LocalDate data, String cpfAluno, String cpfFuncionario) {
        this.hora = hora;
        this.data = data;
        this.cpfAluno = cpfAluno;
        this.cpfFuncionario = cpfFuncionario;
    }

    public String getCpfAluno() {
        return cpfAluno;
    }

    public void setCpfAluno(String cpfAluno) {
        this.cpfAluno = cpfAluno;
    }

    public String getCpfFuncionario() {
        return cpfFuncionario;
    }

    public void setCpfFuncionario(String cpfFuncionario) {
        this.cpfFuncionario = cpfFuncionario;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
