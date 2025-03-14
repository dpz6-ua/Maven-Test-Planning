package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ppss.exceptions.IsbnInvalidoException;
import ppss.exceptions.JDBCException;
import ppss.exceptions.ReservaException;
import ppss.exceptions.SocioInvalidoException;

public class ReservaStubTest {
    @Test
    public void C1_realizarReserva_should_throw_exception1_when_no_privileges(){
        String expected = "ERROR de permisos; ";
        String login = "xxx";
        String password = "xxx";
        String socio = "Pepe";
        String[] isbns = new String[] {"33333"};
        Usuario usuario = Usuario.BIBLIOTECARIO;

        Reserva testable = EasyMock.partialMockBuilder(Reserva.class)
                .addMockedMethod("compruebaPermisos")
                .niceMock();

        EasyMock.expect(testable.compruebaPermisos(login,password,usuario)).andStubReturn(false);
        EasyMock.replay(testable);

        ReservaException re = Assertions.assertThrows(ReservaException.class, () -> testable.realizaReserva(login,password,socio,isbns));
        Assertions.assertEquals(expected, re.getMessage());
        EasyMock.verify(testable);
    }

    @Test
    public void C2_realizarReserva_should_not_throw_exception_when_correct(){
        String login = "ppss";
        String password = "ppss";
        String socio = "Pepe";
        String[] isbns = new String[] {"22222", "33333"};
        Usuario usuario = Usuario.BIBLIOTECARIO;

        Reserva testable = EasyMock.partialMockBuilder(Reserva.class)
                .addMockedMethod("compruebaPermisos")
                .niceMock();

        FactoriaBOs factoriaMock = EasyMock.niceMock(FactoriaBOs.class);
        IOperacionBO operacionMock = EasyMock.niceMock(IOperacionBO.class);

        EasyMock.expect(testable.compruebaPermisos(login,password,usuario)).andStubReturn(true);
        EasyMock.expect(factoriaMock.getOperacionBO()).andStubReturn(operacionMock);

        Assertions.assertDoesNotThrow(
                () -> {
                    operacionMock.operacionReserva(socio, "22222");
                    EasyMock.expectLastCall().anyTimes();
                    operacionMock.operacionReserva(socio, "33333");
                    EasyMock.expectLastCall().anyTimes();
                }
        );
        EasyMock.replay(testable,factoriaMock,operacionMock);
        testable.fact = factoriaMock;
        Assertions.assertDoesNotThrow(() -> testable.realizaReserva(login,password,socio,isbns));
        EasyMock.verify(testable,factoriaMock,operacionMock);
    }

    @Test
    public void C3_realizarReserva_should_throw_exception2_when_invalid_ISBMs(){
        String expected = "ISBN invalido:11111; ISBN invalido:55555; ";
        String login = "ppss";
        String password = "ppss";
        String socio = "Pepe";
        String[] isbns = new String[] {"11111", "22222", "55555"};
        Usuario usuario = Usuario.BIBLIOTECARIO;

        Reserva testable = EasyMock.partialMockBuilder(Reserva.class)
                .addMockedMethod("compruebaPermisos")
                .niceMock();

        FactoriaBOs factoriaMock = EasyMock.createMock(FactoriaBOs.class);
        IOperacionBO operacionMock = EasyMock.createMock(IOperacionBO.class);

        EasyMock.expect(testable.compruebaPermisos(login,password,usuario)).andStubReturn(true);
        EasyMock.expect(factoriaMock.getOperacionBO()).andStubReturn(operacionMock);

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
        EasyMock.replay(testable,factoriaMock,operacionMock);
        testable.fact = factoriaMock;

        ReservaException re = Assertions.assertThrows(ReservaException.class, () -> testable.realizaReserva(login,password,socio,isbns));
        Assertions.assertEquals(expected, re.getMessage());
        EasyMock.verify(testable,factoriaMock,operacionMock);
    }

    @Test
    public void C4_realizarReserva_should_throw_exception3_when_invalid_member(){
        String expected = "SOCIO invalido; ";
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String[] isbns = new String[] {"22222"};
        Usuario usuario = Usuario.BIBLIOTECARIO;

        Reserva testable = EasyMock.partialMockBuilder(Reserva.class)
                .addMockedMethod("compruebaPermisos")
                .niceMock();

        FactoriaBOs factoriaMock = EasyMock.createMock(FactoriaBOs.class);
        IOperacionBO operacionMock = EasyMock.createMock(IOperacionBO.class);

        EasyMock.expect(testable.compruebaPermisos(login,password,usuario)).andStubReturn(true);
        EasyMock.expect(factoriaMock.getOperacionBO()).andStubReturn(operacionMock);

        Assertions.assertDoesNotThrow(
                () -> {
                    operacionMock.operacionReserva(socio, "22222");
                    EasyMock.expectLastCall().andThrow(new SocioInvalidoException());
                }
        );
        EasyMock.replay(testable,factoriaMock,operacionMock);
        testable.fact = factoriaMock;

        ReservaException re = Assertions.assertThrows(ReservaException.class, () -> testable.realizaReserva(login,password,socio,isbns));
        Assertions.assertEquals(expected, re.getMessage());
        EasyMock.verify(testable,factoriaMock,operacionMock);
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
                .niceMock();

        FactoriaBOs factoriaMock = EasyMock.createMock(FactoriaBOs.class);
        IOperacionBO operacionMock = EasyMock.createMock(IOperacionBO.class);

        EasyMock.expect(testable.compruebaPermisos(login,password,usuario)).andStubReturn(true);
        EasyMock.expect(factoriaMock.getOperacionBO()).andStubReturn(operacionMock);

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
        EasyMock.replay(testable,factoriaMock,operacionMock);
        testable.fact = factoriaMock;

        ReservaException re = Assertions.assertThrows(ReservaException.class, () -> testable.realizaReserva(login,password,socio,isbns));
        Assertions.assertEquals(expected, re.getMessage());
        EasyMock.verify(testable,factoriaMock,operacionMock);
    }
}
