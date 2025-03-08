package ppss;

import java.util.Arrays;
import java.util.List;

public class OperacionSTUB extends Operacion {
    private static final List<String> asignaturasCursadas = Arrays.asList("P1", "FC", "FFI");
    private static final List<String> asignaturasInvalidas = Arrays.asList("YYY", "ZZ");

    @Override
    public void compruebaMatricula(String dni, String asignatura) throws AsignaturaIncorrectaException, AsignaturaCursadaException {
        if (asignaturasInvalidas.contains(asignatura)) {
            throw new AsignaturaIncorrectaException();
        }
        if (asignaturasCursadas.contains(asignatura)) {
            throw new AsignaturaCursadaException();
        }
    }
}
