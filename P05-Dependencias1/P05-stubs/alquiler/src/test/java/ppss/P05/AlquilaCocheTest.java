package ppss.P05;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class AlquilaCocheTest {
    @Test
    public void C1_calculaPrecio_should_return_ticket_when_all_days_are_false() {
        TipoCoche tipo = TipoCoche.TURISMO;
        LocalDate fecha = LocalDate.of(2024, 05, 18);
        int dias = 10;
        float expectedDinero = 75;

        AlquilaCochesTestable act = new AlquilaCochesTestable();
        IService ss = new ServicioSTUB();
        Calendario cal = new CalendarioSTUB();
        act.setServicio(ss);
        act.setCalendario(cal);

        assertAll(
                () -> {
                    Ticket t = assertDoesNotThrow(() -> act.calculaPrecio(tipo, fecha, dias));
                    assertEquals(expectedDinero, t.getPrecio_final());
                }
        );
    }

    @Test
    public void C2_calculaPrecio_should_return_ticket_when_20_and_24_are_false() {
        TipoCoche tipo = TipoCoche.CARAVANA;
        LocalDate fecha = LocalDate.of(2024, 06, 19);
        int dias = 7;
        float expectedDinero = (float)62.5;

        AlquilaCochesTestable act = new AlquilaCochesTestable();
        IService ss = new ServicioSTUB();
        Calendario cal = new CalendarioSTUB();
        act.setServicio(ss);
        act.setCalendario(cal);

        assertAll(
                () -> {
                    Ticket t = assertDoesNotThrow(() -> act.calculaPrecio(tipo, fecha, dias));
                    assertEquals(expectedDinero, t.getPrecio_final());
                }
        );
    }

    @Test
    public void C3_calculaPrecio_should_throw_exception_when_18_21_22_are_false() {
        TipoCoche tipo = TipoCoche.TURISMO;
        LocalDate fecha = LocalDate.of(2024, 04, 17);
        int dias = 8;
        String expectedObservaciones = "Error en dia: 2024-04-18; " +
                "Error en dia: 2024-04-21; " +
                "Error en dia: 2024-04-22; ";

        AlquilaCochesTestable act = new AlquilaCochesTestable();
        IService ss = new ServicioSTUB();
        Calendario cal = new CalendarioSTUB();
        act.setServicio(ss);
        act.setCalendario(cal);

        MensajeException t = assertThrows(MensajeException.class, () -> act.calculaPrecio(tipo, fecha, dias));
        assertEquals(expectedObservaciones, t.getMensaje());
    }
}
