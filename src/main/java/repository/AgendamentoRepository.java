package repository;

import exception.HorarioIndisponivelException;

import java.sql.SQLException;
import java.time.LocalDate;

public interface AgendamentoRepository {

    void agendarHorario(String alunoCpf, String funcionariaCpf, LocalDate data, String hora)
            throws HorarioIndisponivelException, SQLException;

    boolean cancelarAgendamentoPorId(int agendamentoId);
}

