package ppss.ejercicio1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Test_Sample_isValid {
    Sample sut;

    @BeforeEach
    void setUp() {
        sut = new Sample();
    }

    // C1: Todos los valores son válidos => true
    @Test
    public void C1_isValid_should_return_true_when_all_data_values_are_valid(){
        int[] datos = {10, 20, 30, 40};
        boolean resultado_esperado = true;

        boolean resultado_real = sut.isValid(datos);
        Assertions.assertEquals(resultado_esperado, resultado_real);
    }

    // C2: Un valor fuera de rango al principio => false
    @Test
    public void C2_isValid_should_return_false_when_first_value_is_invalid(){
        int[] datos = {5, 20, 30};
        boolean resultado_esperado = false;

        boolean resultado_real = sut.isValid(datos);
        Assertions.assertEquals(resultado_esperado, resultado_real);
    }

    // C3: Valor inválido en el medio del array => false
    @Test
    public void C3_isValid_should_return_false_when_middle_value_is_invalid(){
        int[] datos = {10, 20, 200, 30};
        boolean resultado_esperado = false;

        boolean resultado_real = sut.isValid(datos);
        Assertions.assertEquals(resultado_esperado, resultado_real);
    }

    // C4: Valor inválido en el último elemento => false
    @Test
    public void C4_isValid_should_return_false_when_last_value_is_invalid(){
        int[] datos = {10, 20, 30, 81};
        boolean resultado_esperado = false;

        boolean resultado_real = sut.isValid(datos);
        Assertions.assertEquals(resultado_esperado, resultado_real);
    }

    // C5: Array vacío => false
    @Test
    public void C5_isValid_should_return_false_when_array_is_empty(){
        int[] datos = {};
        boolean resultado_esperado = false;

        boolean resultado_real = sut.isValid(datos);
        Assertions.assertEquals(resultado_esperado, resultado_real);
    }

    // C6: Valor igual a mínimo y máximo => true (límites)
    @Test
    public void C6_isValid_should_return_true_when_values_are_on_limits(){
        int[] datos = {10, 80};
        boolean resultado_esperado = true;

        boolean resultado_real = sut.isValid(datos);
        Assertions.assertEquals(resultado_esperado, resultado_real);
    }
}
