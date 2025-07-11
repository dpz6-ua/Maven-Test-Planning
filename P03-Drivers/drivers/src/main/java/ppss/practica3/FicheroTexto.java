package ppss.practica3;

import ppss.practica3.FicheroException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FicheroTexto {

    public int contarCaracteres(String nombreFichero) throws FicheroException {
        int contador = 0;
        FileReader fichero = null;
        try {
            fichero = new FileReader(nombreFichero);
            int i;
            while ((i = fichero.read()) != -1) { // Primero asigna, luego verifica
                contador++;  // Incrementa solo si no es -1
            }
        } catch (FileNotFoundException e1) {
            throw new FicheroException(nombreFichero + " (No existe el archivo o el directorio)");
        } catch (IOException e2) {
            throw new FicheroException(nombreFichero + " (Error al leer el archivo)");
        }
        try {
            fichero.close();
        } catch (IOException e) {
            throw new FicheroException(nombreFichero + " (Error al cerrar el archivo)");
        }
		return contador;
    }
}
