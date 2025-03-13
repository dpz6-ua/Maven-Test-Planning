package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ppss.exceptions.ClienteWebServiceException;

public class CompruebaPremioTest {
    IMocksControl control;
    Premio premio;
    ClienteWebService stub;

    @BeforeEach
    public void setUp() {
        control = EasyMock.createStrictControl();
        premio = EasyMock.partialMockBuilder(Premio.class)
                .addMockedMethod("generaNumero")
                .createMock(control);
        stub = control.createMock(ClienteWebService.class);
    }

    @Test
    public void C1_compruebPremio_should_retrieve_Premiado_champions_when_entrada_final_Champions007() {
        String expected = "Premiado con entrada final Champions";

        EasyMock.expect(premio.generaNumero()).andReturn(0.07f);
        Assertions.assertDoesNotThrow(
                () -> EasyMock.expect(stub.obtenerPremio())
                              .andStubReturn("entrada final Champions"));
        control.replay();
        premio.setcliente(stub);

        String resultado = premio.compruebaPremio();
        Assertions.assertEquals(expected, resultado);
        control.verify();
    }

    @Test
    public void C2_compruebPremio_should_not_retrieve_anything_when_throws_exception_005() {
        String expected = "No se ha podido obtener el premio";

        EasyMock.expect(premio.generaNumero()).andReturn(0.05f);
        Assertions.assertDoesNotThrow(
                () -> EasyMock.expect(stub.obtenerPremio())
                              .andStubThrow(new ClienteWebServiceException()));
        control.replay();
        premio.setcliente(stub);

        String resultado = premio.compruebaPremio();
        Assertions.assertEquals(expected, resultado);
        control.verify();
    }

    @Test
    public void C3_compruebPremio_should_retrieve_sin_premio_when_num_048() {
        String expected = "Sin premio";

        EasyMock.expect(premio.generaNumero()).andReturn(0.48f);
        control.replay();

        String resultado = premio.compruebaPremio();
        Assertions.assertEquals(expected, resultado);
        control.verify();
    }
}
