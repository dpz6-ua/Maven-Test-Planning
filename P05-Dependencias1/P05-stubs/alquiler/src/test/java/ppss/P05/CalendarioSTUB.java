package ppss.P05;

import java.time.LocalDate;

public class CalendarioSTUB extends Calendario {
    @Override
    public boolean es_festivo(LocalDate otroDia) throws CalendarioException{
        if (otroDia.equals(LocalDate.of(2024, 4, 18)) ||
                otroDia.equals(LocalDate.of(2024, 4, 21)) ||
                otroDia.equals(LocalDate.of(2024, 4, 22))) {
            throw new CalendarioException();
        }

        if (otroDia.equals(LocalDate.of(2024, 6, 20)) || otroDia.equals(LocalDate.of(2024, 6, 24))) {
            return true;  // Es festivo
        }

        return false;
    }
}
