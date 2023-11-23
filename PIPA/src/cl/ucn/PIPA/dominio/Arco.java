package cl.ucn.PIPA.dominio;

import java.util.ArrayList;

import cl.ucn.PIPA.utils.Utils;

/**
 * Clase que representa la conexión entre dos nodos.
 */
public class Arco {
    private ArrayList<String> id;  // Lista de identificadores (puede contener múltiples IDs)
    private ArrayList<String> nombre;  // Lista de nombres (puede contener múltiples nombres)
    private ArrayList<String> tipo;  // Lista de tipos (puede contener múltiples tipos)
    private Nodo origen;  // Nodo de origen
    private Nodo destino;  // Nodo de destino
    private double peso;

    /**
     * Constructor de la clase Arco.
     *
     * @param id     Lista de identificadores del arco.
     * @param nombre Lista de nombres del arco.
     * @param tipo   Lista de tipos del arco.
     * @param origen Nodo de origen del arco.
     * @param destino Nodo de destino del arco.
     */
    public Arco(ArrayList<String> id, ArrayList<String> nombre, ArrayList<String> tipo, Nodo origen, Nodo destino) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.origen = origen;
        this.destino = destino;
        peso = Utils.haversine(origen.getY(),origen.getX(),destino.getY(),destino.getX());
    }

    /**
     * Retorna la lista de identificadores del arco.
     *
     * @return Lista de identificadores.
     */
    public ArrayList<String> getId() {
        return id;
    }
    public double getPeso() {
        return peso;
    }
    /**
     * Retorna la lista de nombres del arco.
     *
     * @return Lista de nombres.
     */
    public ArrayList<String> getNombre() {
        return nombre;
    }

    /**
     * Retorna la lista de tipos del arco.
     *
     * @return Lista de tipos.
     */
    public ArrayList<String> getTipo() {
        return tipo;
    }

    /**
     * Retorna el Nodo de origen del arco.
     *
     * @return Nodo en el que se encuentra en el origen del arco.
     */
    public Nodo getOrigen() {
        return origen;
    }

    /**
     * Retorna el nodo de destino del arco.
     *
     * @return Nodo en el que se encuentra en el destino del arco.
     */
    public Nodo getDestino() {
        return destino;
    }
    public double heuristicaAStar(Nodo destino, Nodo siguiente) {
        double distanciaHastaDestino = Utils.haversine(origen.getY(), origen.getX(), destino.getY(), destino.getY());
        double distanciaHastaSiguiente = Utils.haversine(origen.getY(), origen.getX(), siguiente.getY(), siguiente.getX());
        return distanciaHastaDestino + distanciaHastaSiguiente;
    }
    
}
