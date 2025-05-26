package ppss.ejercicio1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Test_Sample_countValues {
    Sample sut;

    @BeforeEach
    void setUp() {
        sut = new Sample();
    }

    @Test
    public void C1_countValues_should_return_40_when_all_values_are_10() {
        int[] datos = {10, 10, 10, 10};
        int resultado_esperado = 40;

        int resultado_real = sut.countValues(datos);
        Assertions.assertEquals(resultado_esperado, resultado_real);
    }

    @Test
    public void C2_countValues_should_return_0_when_first_value_is_less_than_min() {
        int[] datos = {5, 10, 20};
        int resultado_esperado = 0;

        int resultado_real = sut.countValues(datos);
        Assertions.assertEquals(resultado_esperado, resultado_real);
    }

    @Test
    public void C3_countValues_should_return_0_when_middle_value_is_out_of_range() {
        int[] datos = {15, 50, 85, 20};
        int resultado_esperado = 0;

        int resultado_real = sut.countValues(datos);
        Assertions.assertEquals(resultado_esperado, resultado_real);
    }

    @Test
    public void C4_countValues_should_sum_until_out_of_range_value() {
        int[] datos = {10, 20, 30, 90, 10};
        int resultado_esperado = 0; // porque al encontrar 90 (fuera de rango), resetea a 0

        int resultado_real = sut.countValues(datos);
        Assertions.assertEquals(resultado_esperado, resultado_real);
    }

    @Test
    public void C5_countValues_should_return_sum_when_all_values_in_range() {
        int[] datos = {10, 20, 30, 40};
        int resultado_esperado = 100;

        int resultado_real = sut.countValues(datos);
        Assertions.assertEquals(resultado_esperado, resultado_real);
    }

    @Test
    public void C6_countValues_should_return_0_when_empty_array() {
        int[] datos = {};
        int resultado_esperado = 0;

        int resultado_real = sut.countValues(datos);
        Assertions.assertEquals(resultado_esperado, resultado_real);
    }
}
