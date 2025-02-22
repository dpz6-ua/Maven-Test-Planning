package ppss.practica3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DataArrayTest {

    @Test
    void C1_DataArray_should_return_3_elements_when_erasing_num() throws DataException {
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
    void C2_DataArray_should_return_4_elements_when_erasing_num_once() throws DataException {
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
    void C3_DataArray_should_return_9_elements_when_erasing_1_num_from_10() throws DataException {
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
    void C4_DataArray_shall_return_Exception_when_collection_is_empty() throws DataException {
        int[] datos = {};
        DataArray da = new DataArray(datos);

        DataException de = assertThrows(DataException.class, () -> da.delete(8));
        assertEquals("No hay elementos en la colección", de.getMessage());
    }

    @Test
    void C5_DataArray_shall_return_Exception_when_num_to_erase_lower_than_0() throws DataException {
        int[] datos = {1,3,5,7};
        DataArray da = new DataArray(datos);

        DataException de = assertThrows(DataException.class, () -> da.delete(-5));
        assertEquals("El valor a borrar debe ser > 0", de.getMessage());
    }

    @Test
    void C6_DataArray_shall_return_Exception_when_empty_array_and_0_num() throws DataException {
        int[] datos = {};
        DataArray da = new DataArray(datos);

        DataException de = assertThrows(DataException.class, () -> da.delete(0));
        assertEquals("Colección vacía. Y el valor a borrar debe ser > 0", de.getMessage());
    }

    @Test
    void C7_DataArray_shall_return_Exception_when_element_not_found() throws DataException {
        int[] datos = {1,3,5,7};
        DataArray da = new DataArray(datos);

        DataException de = assertThrows(DataException.class, () -> da.delete(8));
        assertEquals("Elemento no encontrado", de.getMessage());
    }

}