package ppss.practica3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FicheroTextoTest {
    @Test
    void C1_contarCaracteres_should_return_Exception_when_file_does_not_exist(){
        String ficheroNoExiste = "ficheroC1.txt";
        FicheroTexto texto = new FicheroTexto();

        FicheroException fe = assertThrows(FicheroException.class, () -> texto.contarCaracteres(ficheroNoExiste));
        assertEquals("ficheroC1.txt (No existe el archivo o el directorio)", fe.getMessage());
    }

    @Test
    void C2_contarCaracteres_should_return_3_when_file_has_3_chars() {
        String ficheroC2 = "src/test/resources/ficheroCorrecto.txt";
        FicheroTexto texto = new FicheroTexto();
        int caracteresEsperados = 3;

        int resultado = assertDoesNotThrow(()-> texto.contarCaracteres(ficheroC2));
        assertEquals(caracteresEsperados, resultado);
    }

    @Test
    @Tag("excluido")
    void C3_contarCaracteres_should_return_Exception_when_file_cannot_be_read(){
        Assertions.fail();
    }

    @Test
    @Tag("excluido")
    void C4_contarCaracteres_should_return_Exception_when_file_cannot_be_closed(){
        Assertions.fail();
    }
}