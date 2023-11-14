package cl.ucn.PIPA.logica;
/**
 * Proyecto Integrador Programacion Avanzada
 * Grupo: PAPAYON
 * Integrantes:
 *  @author Giacomo Baldessari
 *  @author Eduardo Miranda
 *  @author David Rodriguez
 *  @Version 14/11/23
 */
public class App {
    public static void main(String[] args) {
        Sistema sistema = new SistemaImpl();
        sistema.iniciarApp(sistema);
    }
}