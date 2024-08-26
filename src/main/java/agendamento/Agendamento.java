package agendamento;

import java.time.LocalDate;

public class Agendamento {
    private String hora;
    private LocalDate data;

    public Agendamento(String hora, LocalDate data) {
        this.hora = hora;
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
