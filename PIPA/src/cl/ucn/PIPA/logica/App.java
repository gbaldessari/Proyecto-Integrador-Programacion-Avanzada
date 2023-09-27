/*
 * Proyecto Integrador Programacion Avanzada
 * Grupo: PAPAYON
 * Integrantes:
 *  @author Giacomo Baldessari
 *  @author Eduardo Miranda
 *  @author David Rodriguez
 * 
 *  @Version: 26/09/23
 */
package cl.ucn.PIPA.logica;
public class App {
    public static void main(String[] args) {
        Sistema sistema = new SistemaImpl();
        sistema.iniciarApp(sistema);
    }
}