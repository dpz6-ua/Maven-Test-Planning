package ppss.practica3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DataArrayTest {

    @Test
    void C1_delete_should_return_3_elements_when_erasing_num() throws DataException {
        int[] datos = {1,3,5,7};
        int[] expected = {1,3,7};
        int expectedElements = 3;
        DataArray da = new DataArray(datos);

        da.delete(5);

        assertAll("C1 should execute well operation",
                () -> assertArrayEquals(expected, da.getColeccion()),
                () -> assertEquals(expectedElements, da.size()));
    }

    @Test
    void C2_delete_should_return_4_elements_when_erasing_num_once() throws DataException {
        int[] datos = {1,3,3,5,7};
        int[] expected = {1,3,5,7};
        int expectedElements = 4;

        DataArray da = new DataArray(datos);
        da.delete(3);

        assertAll("C2 shall erase given instance once when existing more than one",
                () -> assertArrayEquals(expected, da.getColeccion()),
                () -> assertEquals(expectedElements, da.size()));
    }

    @Test
    void C3_delete_should_return_9_elements_when_erasing_1_num_from_10() throws DataException {
        int[] datos = {1,2,3,4,5,6,7,8,9,10};
        int[] expected = {1,2,3,5,6,7,8,9,10};
        int expectedElements = 9;

        DataArray da = new DataArray(datos);
        da.delete(4);

        assertAll("C3 shall erase 1 element and decrease size to 9",
                () -> assertArrayEquals(expected,da.getColeccion()),
                () -> assertEquals(expectedElements, da.size()));
    }

    @Test
    void C4_delete_shall_return_Exception_when_collection_is_empty(){
        int[] datos = {};
        DataArray da = new DataArray(datos);

        DataException de = assertThrows(DataException.class, () -> da.delete(8));
        assertEquals("No hay elementos en la colección", de.getMessage());
    }

    @Test
    void C5_delete_shall_return_Exception_when_num_to_erase_lower_than_0(){
        int[] datos = {1,3,5,7};
        DataArray da = new DataArray(datos);

        DataException de = assertThrows(DataException.class, () -> da.delete(-5));
        assertEquals("El valor a borrar debe ser > 0", de.getMessage());
    }

    @Test
    void C6_delete_shall_return_Exception_when_empty_array_and_0_num() {
        int[] datos = {};
        DataArray da = new DataArray(datos);

        DataException de = assertThrows(DataException.class, () -> da.delete(0));
        assertEquals("Colección vacía. Y el valor a borrar debe ser > 0", de.getMessage());
    }

    @Test
    void C7_delete_shall_return_Exception_when_element_not_found() {
        int[] datos = {1,3,5,7};
        DataArray da = new DataArray(datos);

        DataException de = assertThrows(DataException.class, () -> da.delete(8));
        assertEquals("Elemento no encontrado", de.getMessage());
    }

    @ParameterizedTest(name = "[{index}] Message exception should be {2} when we want to delete {0}")
    @MethodSource("casosDePrueba")
    @Tag("parametrizado")
    @Tag("conExcepciones")
    @DisplayName("C8_delete_With_Exceptions_")
    void C8_deleteWithExceptions(int borrados, int[] datos, String mensaje) {
        DataArray da = new DataArray(datos);
        DataException de = assertThrows(DataException.class, () -> da.delete(borrados));
        assertEquals(mensaje, de.getMessage());
    }

    private static Stream<Arguments> casosDePrueba(){
        return Stream.of(
                Arguments.of(8, new int[]{}, "No hay elementos en la colección"),
                Arguments.of(-5, new int[]{1,3,5,7}, "El valor a borrar debe ser > 0"),
                Arguments.of(0, new int[]{}, "Colección vacía. Y el valor a borrar debe ser > 0"),
                Arguments.of(8, new int[]{1,3,5,7}, "Elemento no encontrado")
        );
    }

    @ParameterizedTest(name = "[{index}] should be {0} when we want to delete {1}")
    @MethodSource("casosSinExcepcion")
    @Tag("parametrizado")
    @DisplayName("C9_delete_Without_Exceptions_")
    void C9_delete_Without_Exceptions(int[] expected, int borrados, int[] datos) throws DataException {
        DataArray da = new DataArray(datos);
        da.delete(borrados);
        assertAll(() -> assertArrayEquals(expected, da.getColeccion()),
                  () -> assertEquals(expected.length, da.size()));
    }

    private static Stream<Arguments> casosSinExcepcion() {
        return Stream.of(
                Arguments.of(new int[]{1,3,7}, 5, new int[]{1,3,5,7}),
                Arguments.of(new int[]{1,3,5,7}, 3, new int[]{1,3,3,5,7}),
                Arguments.of(new int[]{1,2,3,5,6,7,8,9,10}, 4, new int[]{1,2,3,4,5,6,7,8,9,10})
        );
    }

}