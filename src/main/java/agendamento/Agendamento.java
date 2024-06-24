package agendamento;

import java.time.LocalDate;

public class Agendamento {
private String hora;
private LocalDate dia;

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }
}
