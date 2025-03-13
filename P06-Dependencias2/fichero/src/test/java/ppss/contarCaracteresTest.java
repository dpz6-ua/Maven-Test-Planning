package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.Test;
import ppss.exceptions.FicheroException;

import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class contarCaracteresTest {
    @Test
    public void C1_contarCaracteres_should_throw_read_exception_when_reading_file() {
        String fichero1 = "src/test/resources/ficheroC1.txt";
        String expected = "src/test/resources/ficheroC1.txt (Error al leer el archivo)";

        IMocksControl control = EasyMock.createStrictControl();
        FicheroTexto texto = EasyMock.partialMockBuilder(FicheroTexto.class)
                                     .addMockedMethod("createFileReader")
                                     .createMock(control);
        FileReader fr = control.createMock(FileReader.class);

        assertDoesNotThrow(() ->
                {
                EasyMock.expect(texto.createFileReader(fichero1)).andReturn(fr);
                EasyMock.expect(fr.read())
                        .andReturn((int) 'a')
                        .andReturn((int) 'b')
                        .andThrow(new IOException());
        });

        control.replay();

        FicheroException fe = assertThrows(FicheroException.class, () -> texto.contarCaracteres(fichero1));
        assertEquals(expected, fe.getMessage());
        control.verify();
    }

    @Test
    public void C2_contarCaracteres_should_throw_read_exception_when_closing_file() {
        String fichero2 = "src/test/resources/ficheroC2.txt";
        String expected = "src/test/resources/ficheroC2.txt (Error al cerrar el archivo)";

        IMocksControl control = EasyMock.createStrictControl();
        FicheroTexto texto = EasyMock.partialMockBuilder(FicheroTexto.class)
                                     .addMockedMethod("createFileReader")
                                     .createMock(control);
        FileReader fr = control.createMock(FileReader.class);

        assertDoesNotThrow(
                () -> {
                    EasyMock.expect(texto.createFileReader(fichero2)).andReturn(fr);
                    EasyMock.expect(fr.read()).andReturn((int) 'a')
                                              .andReturn((int) 'b')
                                              .andReturn((int) 'c')
                                              .andReturn(-1);
                    fr.close();
                    EasyMock.expectLastCall().andThrow(new IOException());
                }
        );
        control.replay();
        FicheroException fe = assertThrows(FicheroException.class, () -> texto.contarCaracteres(fichero2));
        assertEquals(expected, fe.getMessage());
        control.verify();
    }
}
