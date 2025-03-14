package ppss;

import ppss.exceptions.FailedNotifyException;

import java.time.LocalDate;
import java.util.List;

public class NotifyCenter {
    private String login = "root";
    private String password = "7l65a43";

    public MailServer getServer() {
        return new MailServer(login, password);
    }

    public void sendNotify(String email) throws FailedNotifyException {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public LocalDate getLocalDate(){
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void notifyUsers(LocalDate fecha) throws FailedNotifyException {
        int failed = 0;
        MailServer server = getServer();
        List<String> emails;
        LocalDate today = getLocalDate();
        if (today.isEqual(fecha)) {
            emails = server.findMailItemsWithDate(fecha);
            for (String email : emails) {
                try {
                    sendNotify(email);
                } catch (FailedNotifyException ex) {
                    failed++;
                }
            }
        } else {
            throw new FailedNotifyException("Date error");
        }
        if (failed >0) {
            throw new FailedNotifyException("Failures during sending process");
        }
    }
}
