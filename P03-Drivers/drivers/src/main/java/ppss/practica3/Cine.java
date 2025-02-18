package ppss.practica3;

public class Cine {
    public boolean reservaButacas(boolean[] asientos, int solicitados) throws ButacasException {
        if (asientos.length == 0) {
            if (solicitados > asientos.length) {
                throw new ButacasException("No se puede procesar la solicitud");
            }
            if (solicitados == asientos.length) {
                return false;
            }
        }

        boolean reserva = false;
        int j=0;
        int sitiosLibres =0;
        int primerLibre = 0;

        while ((j < asientos.length) && (sitiosLibres < solicitados)) {
            if (!asientos[j]) {
                sitiosLibres++;
            } else {
                sitiosLibres = 0;
            }
            j++;
        }
        if (sitiosLibres == solicitados) {
            reserva = true;
            for (int k=primerLibre; k<(primerLibre+solicitados); k++) {
                asientos[k] = true;
            }
        }

        return reserva;
    }
}
