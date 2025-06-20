package ppss.practica3;
//David
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests asociados a la clase Cine")
class CineTest {
    @Test
    void C1_reservaButacas_should_return_Exception_when_fila_empty_and_want_3() {
        boolean[] asientos = {};
        int solicitados = 3;
        Cine cine = new Cine();

        ButacasException exception = assertThrows(ButacasException.class, () -> cine.reservaButacas(asientos, solicitados));
        assertEquals("No se puede procesar la solicitud", exception.getMessage());
    }

    @Test
    void C2_reservaButacas_should_return_false_when_fila_empty_and_want_zero(){
        boolean[] asientos = {};
        boolean[] arrayEsperado = {};
        int solicitados = 0;

        Cine cine = new Cine();

        assertAll("C2 should not complete the reservation due to empty array and 0 ordered seats",
                () -> assertDoesNotThrow(() -> {cine.reservaButacas(asientos, solicitados);}),
                () -> assertFalse(cine.reservaButacas(asientos, solicitados)),
                () -> assertArrayEquals(arrayEsperado, asientos));
    }

    @Test
    void C3_reservaButacas_should_return_true_when_fila_has_3_seats_free_and_want_2() {
        boolean[] asientos = {false, false, false, true, true};
        boolean[] arrayEsperado = {true, true, false, true, true};
        int solicitados = 2;

        Cine cine = new Cine();
        boolean resultado = assertDoesNotThrow(() -> cine.reservaButacas(asientos, solicitados));

        assertAll("C3 should make the reservation and change the status of the reserved seats",
                () -> assertTrue(resultado),
                () -> assertArrayEquals(arrayEsperado, asientos));
    }

    @Test
    void C4_reservaButacas_should_return_false_when_no_free_seats_and_want_1() {
        boolean[] asientos = {true, true, true};
        boolean[] arrayEsperado = {true, true, true};
        int solicitados = 1;

        Cine cine = new Cine();
        boolean resultado = assertDoesNotThrow(() -> cine.reservaButacas(asientos, solicitados));

        assertAll("C4 shall refuse the reservation due to lack of available space",
                () -> assertFalse(resultado),
                () -> assertArrayEquals(arrayEsperado, asientos));
    }

    @ParameterizedTest(name = "[{index}] should be {0} when we want {2} and {3}")
    @MethodSource("casosDePrueba")
    @Tag("parametrizado")
    @DisplayName("C5_reservaButacas_")
    void C5_reservaButacas(boolean expected, boolean[] asientos, int solicitados, String mensaje){
        Cine cine = new Cine();
        boolean resultado = assertDoesNotThrow( () -> cine.reservaButacas(asientos, solicitados));
        assertEquals(expected, resultado, "No pasa el test");
    }


    private static Stream<Arguments> casosDePrueba() {
        return Stream.of(
                Arguments.of(false, new boolean[]{}, 0, "fila has no seats"),
                Arguments.of(true, new boolean[]{false, false, false, true, true}, 2, "there are 2 free seats"),
                Arguments.of(false, new boolean[]{true, true, true}, 1, "all seats are already reserved")
        );
    }

}