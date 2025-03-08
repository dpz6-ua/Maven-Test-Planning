package ppss.P05.llamadas;

public class CalendarioSTUB extends Calendario {
    private int horaActual;

    public void setHoraActual(int horaActual) {
        this.horaActual = horaActual;
    }

    @Override
    public int getHoraActual() {
        return horaActual;
    }
}