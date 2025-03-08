package ppss;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

public class MatriculaAlumnoTest {
    @Test
    public void C1_validaAsignaturas_should_show_errors_matricula_when_ya_cursada_o_no_existente() {
        String dni = "00000000T";
        String[] asignaturas = {"MD", "ZZ", "FBD", "P1" };

        ArrayList<String> expectedAsignaturas = new ArrayList<>(Arrays.asList("MD", "FBD"));
        ArrayList<String> expectedErrors = new ArrayList<>(Arrays.asList("Asignatura ZZ no existe", "Asignatura P1 ya cursada"));

        MatriculaAlumnoTesteable mat = new MatriculaAlumnoTesteable();
        JustificanteMatricula resultado = mat.validaAsignaturas(dni, asignaturas);

        assertAll(
                () -> assertEquals(dni, resultado.getDni()),
                () -> assertEquals(expectedAsignaturas, resultado.getAsignaturas()),
                () -> assertEquals(expectedErrors, resultado.getErrores()));
    }

    @Test
    public void C2_validaAsignaturas_should_matricularse_correctamente_when_sin_problema() {
        String dni = "00000000T";
        String[] asignaturas = {"PPSS", "ADA", "P3" };

        ArrayList<String> expectedAsignaturas = new ArrayList<>(Arrays.asList("PPSS", "ADA", "P3"));
        ArrayList<String> expectedErrors = new ArrayList<>();

        MatriculaAlumnoTesteable mat = new MatriculaAlumnoTesteable();
        JustificanteMatricula resultado = mat.validaAsignaturas(dni, asignaturas);

        assertAll(
                () -> assertEquals(dni, resultado.getDni()),
                () -> assertEquals(expectedAsignaturas, resultado.getAsignaturas()),
                () -> assertEquals(expectedErrors, resultado.getErrores()));
    }
}
