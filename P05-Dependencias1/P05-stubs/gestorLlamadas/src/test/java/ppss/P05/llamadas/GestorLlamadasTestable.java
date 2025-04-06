package ppss.P05.llamadas;

public class GestorLlamadasTestable extends GestorLlamadas {
    protected CalendarioSTUB calendarioSTUB;

    public void setCalendarioSTUB(CalendarioSTUB calendarioSTUB) {
        this.calendarioSTUB = calendarioSTUB;
    }

    @Override
    public Calendario getCalendario() {
        return calendarioSTUB;
    }
}
