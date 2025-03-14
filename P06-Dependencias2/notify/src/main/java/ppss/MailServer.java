package ppss;

import java.util.List;
import java.time.LocalDate;

public class MailServer {
    String login;
    String password;

    public MailServer(String login, String password){
        this.login = login;
        this.password = password;
    }

    public List<String> findMailItemsWithDate(LocalDate fecha){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
