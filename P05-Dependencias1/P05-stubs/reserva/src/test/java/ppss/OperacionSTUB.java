package ppss;

import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.SocioInvalidoException;

import java.util.ArrayList;
import java.util.Arrays;

public class OperacionSTUB extends Operacion {
    private ArrayList<String> isbns_validos = new ArrayList<>(Arrays.asList("11111", "22222"));
    private boolean romperDB = false;

    @Override
    public void operacionReserva(String socio, String isbn) throws IsbnInvalidoException, JDBCException, SocioInvalidoException {
        if (!socio.equals("Luis")){
            throw new SocioInvalidoException();
        }

        if (!isbns_validos.contains(isbn)){
            throw new IsbnInvalidoException();
        }

        if (romperDB && isbn.equals("22222")){
            throw new JDBCException();
        }
    }

    public void setRomperDB(boolean romperDB) {
        this.romperDB = romperDB;
    }
}
