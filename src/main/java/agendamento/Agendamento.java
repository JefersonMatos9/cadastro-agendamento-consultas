package agendamento;

import java.time.LocalDate;

public class Agendamento {
private String hora;
private LocalDate data;

    public String getHora() {
        return hora;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }
    public LocalDate getData() { return data; }
    public void setDia(LocalDate data) {
        this.data = data;
    }
}
