package ppss.P05.llamadas;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GestorLlamadasTest {

    @Test
    public void C1_calculaConsumo_should_return_207_when_m_10_and_h_12(){
        CalendarioSTUB c = new CalendarioSTUB();
        c.setHoraActual(12);
        int minutos = 10;
        double esperado = 207;

        GestorLlamadas gestor = new GestorLlamadas();
        double resultado = gestor.calculaConsumo(minutos, c);

        assertEquals(esperado, resultado);
    }

    @Test
    public void C2_calculaConsumo_should_return_122_when_m_10_and_h_21(){
        CalendarioSTUB c = new CalendarioSTUB();
        c.setHoraActual(21);
        int minutos = 10;
        double esperado = 122;

        GestorLlamadas gestor = new GestorLlamadas();
        double resultado = gestor.calculaConsumo(minutos, c);

        assertEquals(esperado, resultado);
    }
}