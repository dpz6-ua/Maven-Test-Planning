package ppss;

public class Factoria {
    private static Operacion operacion=null;
    public static Operacion create() {
        if (operacion != null) {
            return operacion;
        } else {
            return new Operacion();
        }
    }

    static void setOperacion(Operacion op) {
        operacion = op;
    }
}
