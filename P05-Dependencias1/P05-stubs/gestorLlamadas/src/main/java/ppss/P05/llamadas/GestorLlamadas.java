package ppss.P05.llamadas;

public class GestorLlamadas {
    private static final double TARIFA_NOCTURNA=12.2;
    private static final double TARIFA_DIURNA=20.7;

    public double calculaConsumo(int minutos, Calendario c) {
        int hora = c.getHoraActual();
        if(hora < 8 || hora > 20) {
            return minutos * TARIFA_NOCTURNA;
        } else {
            return minutos * TARIFA_DIURNA;
        }
    }
}