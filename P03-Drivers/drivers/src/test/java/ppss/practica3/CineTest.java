package ppss.practica3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests asociados a la clase Cine")
class CineTest {
    @Test
    void C1_reservaButacas_should_return_Exception_when_fila_empty_and_want_3() throws ButacasException{
        boolean[] asientos = {};
        int solicitados = 3;
        Cine cine = new Cine();

        ButacasException exception = assertThrows(ButacasException.class, () -> cine.reservaButacas(asientos, solicitados));
        assertEquals("No se puede procesar la solicitud", exception.getMessage());
    }

    @Test
    void C2_reservaButacas_should_return_false_when_fila_empty_and_want_zero() throws ButacasException{
        boolean[] asientos = {};
        boolean[] arrayEsperado = {};
        int solicitados = 0;

        Cine cine = new Cine();
        boolean actual = cine.reservaButacas(asientos, solicitados);

        assertAll("C2 should not complete the reservation due to empty array and 0 ordered seats",
                () -> assertFalse(actual),
                () -> assertArrayEquals(arrayEsperado, asientos));
    }

    @Test
    void C3_reservaButacas_should_return_true_when_fila_has_3_seats_free_and_want_2() throws ButacasException{
        boolean[] asientos = {false, false, false, true, true};
        boolean[] arrayEsperado = {true, true, false, true, true};
        int solicitados = 2;

        Cine cine = new Cine();
        boolean actual = cine.reservaButacas(asientos, solicitados);

        assertAll("C3 should make the reservation and change the status of the reserved seats",
                () -> assertTrue(actual),
                () -> assertArrayEquals(arrayEsperado, asientos));
    }

    @Test
    void C4_reservaButacas_should_return_false_when_no_free_seats_and_want_1() throws ButacasException{
        boolean[] asientos = {true, true, true};
        boolean[] arrayEsperado = {true, true, true};
        int solicitados = 1;

        Cine cine = new Cine();
        boolean actual = cine.reservaButacas(asientos, solicitados);

        assertAll("C4 shall refuse the reservation due to lack of available space",
                () -> assertFalse(actual),
                () -> assertArrayEquals(arrayEsperado, asientos));
    }

}