package ppss;
import ppss.excepciones.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RealizaReservaTest {
    @Test
    public void C1_realizarReserva_should_throw_reserva_exception1_when_error_de_permisos() {
        OperacionSTUB op = new OperacionSTUB();
        Factoria.setOperacion(op);
        String login = "xxxx";
        String password = "xxxx";
        String socio = "Luis";
        String[] isbns = new String[] {"11111"};

        ReservaTestable reserva = new ReservaTestable();
        ReservaException reservaException = assertThrows(ReservaException.class, () -> {reserva.realizaReserva(login, password, socio, isbns);});
        assertEquals("ERROR de permisos; ", reservaException.getMessage());

    }

    @Test
    public void C2_realizarReserva_should_not_throw_exception_when_data_correct() {
        OperacionSTUB op = new OperacionSTUB();
        Factoria.setOperacion(op);
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String[] isbns = new String[] {"11111", "22222"};

        ReservaTestable reserva = new ReservaTestable();
        assertDoesNotThrow(() -> {reserva.realizaReserva(login, password, socio, isbns);});
    }

    @Test
    public void C3_realizarReserva_should_throw_reserva_exception2_when_ISBN_not_correct() {
        OperacionSTUB op = new OperacionSTUB();
        Factoria.setOperacion(op);
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String[] isbns = new String[] {"11111", "33333", "44444"};

        ReservaTestable reserva = new ReservaTestable();
        ReservaException reservaException = assertThrows(ReservaException.class, () -> {reserva.realizaReserva(login, password, socio, isbns);});
        assertEquals("ISBN invalido:33333; ISBN invalido:44444; ", reservaException.getMessage());
    }

    @Test
    public void C4_realizarReserva_should_throw_reserva_exception3_when_socio_invalido() {
        OperacionSTUB op = new OperacionSTUB();
        Factoria.setOperacion(op);
        String login = "ppss";
        String password = "ppss";
        String socio = "Pepe";
        String[] isbns = new String[] {"11111"};

        ReservaTestable reserva = new ReservaTestable();
        ReservaException reservaException = assertThrows(ReservaException.class, () -> {reserva.realizaReserva(login, password, socio, isbns);});
        assertEquals("SOCIO invalido; ", reservaException.getMessage());
    }

    @Test
    public void C5_realizarReserva_should_throw_reserva_exception4_when_conexion_invalida() {
        OperacionSTUB op = new OperacionSTUB();
        op.setRomperDB(true);
        Factoria.setOperacion(op);
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String[] isbns = new String[] {"11111", "22222"};

        ReservaTestable reserva = new ReservaTestable();
        ReservaException reservaException = assertThrows(ReservaException.class, () -> {reserva.realizaReserva(login, password, socio, isbns);});
        assertEquals("CONEXION invalida; ", reservaException.getMessage());
    }
}
