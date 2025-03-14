package ppss;

import net.bytebuddy.asm.Advice;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ppss.exceptions.FailedNotifyException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.anyString;

public class notifyUsersTest {
    @Test
    public void C1_notifyUsers_should_throw_sending_failure_when_sending_emails_2_3() {
        LocalDate fecha = LocalDate.of(2025, 3, 11);
        List<String> emails = List.of("email1", "email2", "email3", "email4");
        String expected = "Failures during sending process";
        IMocksControl control = EasyMock.createStrictControl();

        NotifyCenter sut = EasyMock.partialMockBuilder(NotifyCenter.class)
                                   .addMockedMethods("getServer", "sendNotify", "getLocalDate")
                                   .createMock(control);
        MailServer mailServerMock = control.createMock(MailServer.class);

        Assertions.assertDoesNotThrow(() -> {
            EasyMock.expect(sut.getServer()).andReturn(mailServerMock);
            EasyMock.expect(sut.getLocalDate()).andReturn(fecha);
            EasyMock.expect(mailServerMock.findMailItemsWithDate(fecha)).andReturn(emails);

            sut.sendNotify("email1");
            EasyMock.expectLastCall().anyTimes();
            sut.sendNotify("email2");
            EasyMock.expectLastCall().andThrow(new FailedNotifyException());
            sut.sendNotify("email3");
            EasyMock.expectLastCall().andThrow(new FailedNotifyException());
            sut.sendNotify("email4");
            EasyMock.expectLastCall().anyTimes();
        });
        control.replay();

        FailedNotifyException fne = Assertions.assertThrows(FailedNotifyException.class, () -> sut.notifyUsers(fecha));
        Assertions.assertEquals(expected, fne.getMessage());
        control.verify();
    }

    @Test
    public void C2_notifyUsers_should_throw_date_error_when_sending_notification_12_march() {
        LocalDate fecha = LocalDate.of(2025, 3, 12);
        LocalDate actual = LocalDate.of(2025, 2, 12);
        String expected = "Date error";
        IMocksControl control = EasyMock.createStrictControl();

        NotifyCenter sut = EasyMock.partialMockBuilder(NotifyCenter.class)
                .addMockedMethods("getServer", "getLocalDate")
                .createMock(control);
        MailServer mailServerMock = control.createMock(MailServer.class);

        Assertions.assertDoesNotThrow(() -> {
            EasyMock.expect(sut.getServer()).andReturn(mailServerMock);
            EasyMock.expect(sut.getLocalDate()).andReturn(actual);
        });
        control.replay();

        FailedNotifyException fne = Assertions.assertThrows(FailedNotifyException.class, () -> sut.notifyUsers(fecha));
        Assertions.assertEquals(expected, fne.getMessage());
        control.verify();
    }

    @Test
    public void C3_notifyUsers_should_not_send_message_when_sending_emails_same_day() {
        LocalDate fecha = LocalDate.of(2025, 3, 12);
        List<String> emails = new ArrayList<>();
        IMocksControl control = EasyMock.createStrictControl();

        NotifyCenter sut = EasyMock.partialMockBuilder(NotifyCenter.class)
                .addMockedMethods("getServer", "getLocalDate")
                .createMock(control);
        MailServer mailServerMock = control.createMock(MailServer.class);

        Assertions.assertDoesNotThrow(() -> {
            EasyMock.expect(sut.getServer()).andReturn(mailServerMock);
            EasyMock.expect(sut.getLocalDate()).andReturn(fecha);
            EasyMock.expect(mailServerMock.findMailItemsWithDate(fecha)).andReturn(emails);
        });
        control.replay();

        Assertions.assertDoesNotThrow(() -> sut.notifyUsers(fecha));
        control.verify();
    }
}
