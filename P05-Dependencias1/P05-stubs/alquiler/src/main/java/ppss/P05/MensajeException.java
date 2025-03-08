package ppss.P05;

public class MensajeException extends Exception {
    public MensajeException(String mensaje) {
        super(mensaje);
    }

    public String getMensaje() {
        return super.getMessage();
    }
}
