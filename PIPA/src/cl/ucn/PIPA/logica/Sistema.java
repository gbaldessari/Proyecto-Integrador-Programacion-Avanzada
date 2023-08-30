package cl.ucn.PIPA.logica;
import java.util.LinkedList;

public interface Sistema {
    public void iniciarApp(Sistema sistema);
    public boolean buscarArchivo(Sistema sistema, String nombre);
    public LinkedList<String> getLista();
}