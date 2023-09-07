package cl.ucn.PIPA.logica;
import cl.ucn.PIPA.dominio.*;
import cl.ucn.PIPA.interfazGrafica.AdministradorDeVentanas;

/* 
* Subclase sistema
*/
public class SistemaImpl implements Sistema{
    private AdministradorDeVentanas administradorDeVentanas;
    private Grafo grafo;


    /* Constructor del sistema
     * Inicializa la estructura de datos y los administradores de ventanas
     * @param sistema, la superclase del sistema
     */
    public void iniciarApp(Sistema sistema) {
        grafo = new Grafo();
        administradorDeVentanas = new AdministradorDeVentanas(sistema);
        administradorDeVentanas.leerArchivo(administradorDeVentanas);
    }

    /*  
    * Retorna el grafo almacenado
    * @return el grafo
    */
    public Grafo getGrafo() {
        return grafo;
    }
}