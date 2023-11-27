package cl.ucn.PIPA.logica;
/**
 * Proyecto Integrador Programacion Avanzada.
 * Grupo: PAPAYON
 * Integrantes:
 *  @author Giacomo Baldessari
 *  @author Eduardo Miranda
 *  @author David Rodriguez
 */
public abstract class App {
    /**
     * Funcion main de la App.
     *  @param args
     */
    public static void main(final String[] args) {
        Sistema sistema = new SistemaImpl();
        sistema.iniciarApp(sistema);
    }
}
