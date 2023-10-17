package cl.ucn.PIPA.logica;

import java.util.ArrayList;
import cl.ucn.PIPA.dominio.Grafo;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.interfazGrafica.ventanas.AdministradorDeVentanas;
/*
* Superclase sistema
*/
public interface Sistema {
    /* Constructor del sistema
     * Inicializa la estructura de datos y los administradores de ventanas
     * @param sistema, la superclase del sistema
     */
    public void iniciarApp(Sistema sistema);
    /*  
    * Retorna el grafo almacenado
    * @return el grafo
    */
    public Grafo getGrafo();
    public String[] getListaTemas(AdministradorDeVentanas administradorDeVentanas);
    public ArrayList<Tema> getTemas();
    AdministradorDeVentanas getAdministradorDeVentanas();
    public ArrayList<String> getTiposCarreteras();
    public void setTiposCarreteras(ArrayList<String> tiposCarreteras);
}