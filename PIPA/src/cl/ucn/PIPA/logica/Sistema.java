package cl.ucn.PIPA.logica;

import java.awt.Color;
import java.util.ArrayList;
import cl.ucn.PIPA.dominio.Grafo;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.interfazGrafica.ventanas.AdministradorDeVentanas;

/**
 * Interfaz que define el comportamiento de un sistema.
 */
public interface Sistema {

    /**
     * Inicia la aplicación.
     *
     * @param sistema El sistema a iniciar.
     */
    public void iniciarApp(Sistema sistema);

    /**
     * Obtiene el grafo asociado al sistema.
     *
     * @return El grafo.
     */
    public Grafo getGrafo();

    /**
     * Obtiene una lista de nombres de temas disponibles.
     *
     * @param administradorDeVentanas El administrador de ventanas.
     * @return Arreglo de nombres de temas.
     */
    public String[] getListaTemas(AdministradorDeVentanas administradorDeVentanas);

    /**
     * Obtiene la lista de temas disponibles.
     *
     * @return Lista de temas.
     */
    public ArrayList<Tema> getTemas();

    /**
     * Obtiene el administrador de ventanas.
     *
     * @return El administrador de ventanas.
     */
    AdministradorDeVentanas getAdministradorDeVentanas();

    /**
     * Obtiene la lista de tipos de carreteras.
     *
     * @return Lista de tipos de carreteras.
     */
    public ArrayList<String> getTiposCarreteras();

    /**
     * Establece la lista de tipos de carreteras.
     *
     * @param tiposCarreteras Lista de tipos de carreteras.
     */
    public void setTiposCarreteras(ArrayList<String> tiposCarreteras);

    /**
     * Obtiene la lista de colores para las calles.
     *
     * @return Lista de colores para las calles.
     */
    public ArrayList<Color> getColoresCalles();
}
