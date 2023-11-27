package cl.ucn.PIPA.dominio;
import java.util.ArrayList;
import cl.ucn.PIPA.utils.Funciones;

/**
 * Clase que representa la conexión entre dos nodos.
 */
public class Arco {
    /**
     * Lista de identificadores.
     * */
    private ArrayList<String> listaIdentificadores;
    /**
     * Lista de nombres.
     * */
    private ArrayList<String> listaNombres;
    /**
     * Lista de tipos.
     * */
    private ArrayList<String> listaTipos;
    /**
     * Nodo de origen.
     * */
    private Nodo nodoOrigen;
    /**
     * Nodo de destino.
     * */
    private Nodo nodoDestino;
    /**
     * Peso del arco.
     * */
    private double peso;

    /**
     * Constructor de la clase Arco.
     *
     * @param ids Lista de identificadores del arco.
     * @param nombres Lista de nombres del arco.
     * @param tipos Lista de tipos del arco.
     * @param origen Nodo de origen del arco.
     * @param destino Nodo de destino del arco.
     */
    public Arco(final ArrayList<String> ids, final ArrayList<String> nombres,
    final ArrayList<String> tipos, final Nodo origen, final Nodo destino) {
        listaIdentificadores = ids;
        listaNombres = nombres;
        listaTipos = tipos;
        nodoOrigen = origen;
        nodoDestino = destino;
        peso = Funciones.haversine(origen.getY(), origen.getX(),
        destino.getY(), destino.getX());
    }

    /**
     * Retorna la lista de identificadores del arco.
     *
     * @return Lista de identificadores.
     */
    public ArrayList<String> getIds() {
        return listaIdentificadores;
    }

    /**
     * Retorna el peso del arco.
     *
     * @return El peso del arco.
     */
    public final double getPeso() {
        return peso;
    }

    /**
     * Retorna la lista de nombres del arco.
     *
     * @return Lista de nombres.
     */
    public ArrayList<String> getNombres() {
        return listaNombres;
    }

    /**
     * Retorna la lista de tipos del arco.
     *
     * @return Lista de tipos.
     */
    public ArrayList<String> getTipos() {
        return listaTipos;
    }

    /**
     * Retorna el Nodo de origen del arco.
     *
     * @return Nodo en el que se encuentra en el origen del arco.
     */
    public Nodo getOrigen() {
        return nodoOrigen;
    }

    /**
     * Retorna el nodo de destino del arco.
     *
     * @return Nodo en el que se encuentra en el destino del arco.
     */
    public Nodo getDestino() {
        return nodoDestino;
    }

    /**
     * Retorna el nodo de destino del arco.
     *
     * @param destino
     * @param siguiente
     * @return Nodo en el que se encuentra en el destino del arco.
     */
    public final double heuristicaAStar(final Nodo destino,
    final Nodo siguiente) {
        double distanciaHastaDestino = Funciones.haversine(nodoOrigen.getY(),
        nodoOrigen.getX(), destino.getY(), destino.getY());
        double distanciaHastaSiguiente = Funciones.haversine(nodoOrigen.getY(),
        nodoOrigen.getX(), siguiente.getY(), siguiente.getX());
        return distanciaHastaDestino + distanciaHastaSiguiente;
    }
}
