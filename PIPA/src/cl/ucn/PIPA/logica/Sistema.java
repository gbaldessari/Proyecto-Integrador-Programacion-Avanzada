package cl.ucn.PIPA.logica;
import cl.ucn.PIPA.dominio.Grafo;

/*
* Superclase sistema
*/
public interface Sistema {
    public void iniciarApp(Sistema sistema);
    public Grafo getGrafo();
}