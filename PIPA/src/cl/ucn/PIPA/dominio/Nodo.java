package cl.ucn.PIPA.dominio;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un nodo.
 */
public class Nodo {
    private String id;  // ID del nodo
    private Point.Double coordenadas;  // Coordenadas del nodo
    private List<Arco> arcos;  // Lista de arcos asociados al nodo

    /**
     * Constructor de la clase Nodo.
     *
     * @param id   ID del nodo.
     * @param posX Posición X almacenada.
     * @param posY Posición Y almacenada.
     */
    public Nodo(String id, double posX, double posY) {
        arcos = new ArrayList<>();
        coordenadas = new Point.Double(posX, posY);
        this.id = id;
    }

    /**
     * Retorna el ID de un nodo.
     *
     * @return String que contiene el ID del nodo.
     */
    public String getId() {
        return id;
    }

    /**
     * Agrega un arco al nodo.
     *
     * @param arco Arco a agregar.
     */
    public void agregarArco(Arco arco) {
        arcos.add(arco);
    }

    /**
     * Retorna todos los arcos almacenados en el nodo.
     *
     * @return Una lista con los arcos.
     */
    public List<Arco> getArcos() {
        return arcos;
    }

    /**
     * Retorna la coordenada X del nodo.
     *
     * @return Coordenada X.
     */
    public double getX() {
        return coordenadas.x;
    }

    /**
     * Retorna la coordenada Y del nodo.
     *
     * @return Coordenada Y.
     */
    public double getY() {
        return coordenadas.y;
    }

    /**
     * Establece la coordenada X del nodo.
     *
     * @param posX Nueva coordenada X.
     */
    public void setX(double posX) {
        coordenadas.x = posX;
    }

    /**
     * Establece la coordenada Y del nodo.
     *
     * @param posY Nueva coordenada Y.
     */
    public void setY(double posY) {
        coordenadas.y = posY;
    }

    /**
     * Retorna una lista de nodos adyacentes al nodo actual a través de sus arcos.
     *
     * @return Lista de nodos adyacentes.
     */
    public List<Nodo> getNodosAdyacentes() {
        List<Nodo> nodos = new ArrayList<>();
        for (Arco arco : arcos) {
            nodos.add(arco.getDestino());
        }
        return nodos;
    }
}