package cadastro.alunos;

import cadastro.cadastro.Cadastro;

public class Alunos extends Cadastro {
    private boolean horaReservada;
    private int qntdSessoes;

    public boolean isHoraReservada() {
        return horaReservada;
    }

    public void setHoraReservada(boolean horaReservada) {
        this.horaReservada = horaReservada;
    }

    public int getQntdSessoes() {
        return qntdSessoes;
    }

    public void setQntdSessoes(int qntdSessoes) {
        this.qntdSessoes = qntdSessoes;
    }
}
