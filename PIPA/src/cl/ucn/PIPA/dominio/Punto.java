package cl.ucn.PIPA.dominio;

import java.awt.geom.Point2D;

/**
 * Clase que representa un punto en un sistema de coordenadas 2D.
 */
public class Punto {
    private Point2D point;  // Punto representado por un objeto Point2D
    private Nodo nodo;  // Nodo asociado al punto

    /**
     * Constructor de la clase Punto.
     *
     * @param point Punto representado por un objeto Point2D.
     * @param nodo  Nodo asociado al punto.
     */
    public Punto(Point2D point, Nodo nodo) {
        this.point = point;
        this.nodo = nodo;
    }

    /**
     * Retorna el nodo asociado al punto.
     *
     * @return El nodo asociado.
     */
    public Nodo getNodo() {
        return nodo;
    }

    /**
     * Retorna el punto representado por un objeto Point2D.
     *
     * @return El punto representado.
     */
    public Point2D getPoint() {
        return point;
    }
}
