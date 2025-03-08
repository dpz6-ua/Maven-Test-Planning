package ppss;

public class MatriculaAlumnoTesteable extends MatriculaAlumno {
    @Override
    protected Operacion getOperacion() {
        return new OperacionSTUB();
    }
}
