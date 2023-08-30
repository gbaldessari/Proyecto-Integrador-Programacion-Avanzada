/*
 * Grupo: PAPAYON
 * Integrantes:
 *  Giacomo Baldessari
 *  Eduardo Miranda
 *  David Rodriguez
 */
package cl.ucn.PIPA.logica;
public class App {
    public static void main(String[] args) {
        Sistema sistema = new SistemaImpl();
        sistema.iniciarApp(sistema);
    }
}