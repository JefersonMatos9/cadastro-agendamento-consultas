package agendamento;

import java.time.LocalDate;
public interface Agendador {
    boolean isHorarioDisponivel(LocalDate data ,String hora);
    void agendarhorario(LocalDate data , String hora);
}
