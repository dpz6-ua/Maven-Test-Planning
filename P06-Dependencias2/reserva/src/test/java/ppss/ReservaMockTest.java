package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ppss.exceptions.IsbnInvalidoException;
import ppss.exceptions.JDBCException;
import ppss.exceptions.ReservaException;
import ppss.exceptions.SocioInvalidoException;

public class ReservaMockTest {
    @Test
    public void C1_realizarReserva_should_throw_exception1_when_no_privileges(){
        String expected = "ERROR de permisos; ";
        String login = "xxx";
        String password = "xxx";
        String socio = "Pepe";
        String[] isbns = new String[] {"33333"};
        Usuario usuario = Usuario.BIBLIOTECARIO;
        IMocksControl control = EasyMock.createStrictControl();
        Reserva testable = EasyMock.partialMockBuilder(Reserva.class)
                                   .addMockedMethod("compruebaPermisos")
                                    .createMock(control);

        EasyMock.expect(testable.compruebaPermisos(login,password,usuario)).andReturn(false);
        control.replay();

        ReservaException re = Assertions.assertThrows(ReservaException.class, () -> testable.realizaReserva(login,password,socio,isbns));
        Assertions.assertEquals(expected, re.getMessage());
        control.verify();
    }

    @Test
    public void C2_realizarReserva_should_not_throw_exception_when_correct(){
        String login = "ppss";
        String password = "ppss";
        String socio = "Pepe";
        String[] isbns = new String[] {"22222", "33333"};
        Usuario usuario = Usuario.BIBLIOTECARIO;

        IMocksControl control = EasyMock.createStrictControl();
        Reserva testable = EasyMock.partialMockBuilder(Reserva.class)
                .addMockedMethod("compruebaPermisos")
                .createMock(control);

        FactoriaBOs factoriaMock = control.createMock(FactoriaBOs.class);
        IOperacionBO operacionMock = control.createMock(IOperacionBO.class);

        EasyMock.expect(testable.compruebaPermisos(login,password,usuario)).andReturn(true);
        EasyMock.expect(factoriaMock.getOperacionBO()).andReturn(operacionMock);

        Assertions.assertDoesNotThrow(
                () -> {
                    operacionMock.operacionReserva(socio, "22222");
                    EasyMock.expectLastCall().anyTimes();
                    operacionMock.operacionReserva(socio, "33333");
                    EasyMock.expectLastCall().anyTimes();
                }
        );
        control.replay();
        testable.fact = factoriaMock;
        Assertions.assertDoesNotThrow(() -> testable.realizaReserva(login,password,socio,isbns));
        control.verify();
    }

    @Test
    public void C3_realizarReserva_should_throw_exception2_when_invalid_ISBMs(){
        String expected = "ISBN invalido:11111; ISBN invalido:55555; ";
        String login = "ppss";
        String password = "ppss";
        String socio = "Pepe";
        String[] isbns = new String[] {"11111", "22222", "55555"};
        Usuario usuario = Usuario.BIBLIOTECARIO;

        IMocksControl control = EasyMock.createStrictControl();
        Reserva testable = EasyMock.partialMockBuilder(Reserva.class)
                .addMockedMethod("compruebaPermisos")
                .createMock(control);

        FactoriaBOs factoriaMock = control.createMock(FactoriaBOs.class);
        IOperacionBO operacionMock = control.createMock(IOperacionBO.class);

        EasyMock.expect(testable.compruebaPermisos(login,password,usuario)).andReturn(true);
        EasyMock.expect(factoriaMock.getOperacionBO()).andReturn(operacionMock);

        Assertions.assertDoesNotThrow(
                () -> {
                    operacionMock.operacionReserva(socio, "11111");
                    EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());
                    operacionMock.operacionReserva(socio, "22222");
                    EasyMock.expectLastCall().anyTimes();
                    operacionMock.operacionReserva(socio, "55555");
                    EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());
                }
        );
        control.replay();
        testable.fact = factoriaMock;

        ReservaException re = Assertions.assertThrows(ReservaException.class, () -> testable.realizaReserva(login,password,socio,isbns));
        Assertions.assertEquals(expected, re.getMessage());
        control.verify();
    }

    @Test
    public void C4_realizarReserva_should_throw_exception3_when_invalid_member(){
        String expected = "SOCIO invalido; ";
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String[] isbns = new String[] {"22222"};
        Usuario usuario = Usuario.BIBLIOTECARIO;

        IMocksControl control = EasyMock.createStrictControl();
        Reserva testable = EasyMock.partialMockBuilder(Reserva.class)
                .addMockedMethod("compruebaPermisos")
                .createMock(control);

        FactoriaBOs factoriaMock = control.createMock(FactoriaBOs.class);
        IOperacionBO operacionMock = control.createMock(IOperacionBO.class);

        EasyMock.expect(testable.compruebaPermisos(login,password,usuario)).andReturn(true);
        EasyMock.expect(factoriaMock.getOperacionBO()).andReturn(operacionMock);

        Assertions.assertDoesNotThrow(
                () -> {
                    operacionMock.operacionReserva(socio, "22222");
                    EasyMock.expectLastCall().andThrow(new SocioInvalidoException());
                }
        );
        control.replay();
        testable.fact = factoriaMock;

        ReservaException re = Assertions.assertThrows(ReservaException.class, () -> testable.realizaReserva(login,password,socio,isbns));
        Assertions.assertEquals(expected, re.getMessage());
        control.verify();
    }

    @Test
    public void C1_realizarReserva_should_throw_exception4_when_invalid_ISBM_and_connection(){
        String expected = "ISBN invalido:11111; CONEXION invalida; ";
        String login = "ppss";
        String password = "ppss";
        String socio = "Pepe";
        String[] isbns = new String[] {"11111", "22222", "33333"};
        Usuario usuario = Usuario.BIBLIOTECARIO;

        IMocksControl control = EasyMock.createStrictControl();
        Reserva testable = EasyMock.partialMockBuilder(Reserva.class)
                .addMockedMethod("compruebaPermisos")
                .createMock(control);

        FactoriaBOs factoriaMock = control.createMock(FactoriaBOs.class);
        IOperacionBO operacionMock = control.createMock(IOperacionBO.class);

        EasyMock.expect(testable.compruebaPermisos(login,password,usuario)).andReturn(true);
        EasyMock.expect(factoriaMock.getOperacionBO()).andReturn(operacionMock);

        Assertions.assertDoesNotThrow(
                () -> {
                    operacionMock.operacionReserva(socio, "11111");
                    EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());
                    operacionMock.operacionReserva(socio, "22222");
                    EasyMock.expectLastCall().anyTimes();
                    operacionMock.operacionReserva(socio, "33333");
                    EasyMock.expectLastCall().andThrow(new JDBCException());
                }
        );
        control.replay();
        testable.fact = factoriaMock;

        ReservaException re = Assertions.assertThrows(ReservaException.class, () -> testable.realizaReserva(login,password,socio,isbns));
        Assertions.assertEquals(expected, re.getMessage());
        control.verify();
    }
}
