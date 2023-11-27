package cl.ucn.PIPA.dominio;

import java.awt.geom.Point2D;

/**
 * Clase que representa un punto en un sistema de coordenadas 2D.
 */
public class Punto {
    /**
     * Punto representado por un objeto Point2D.
     */
    private Point2D point2d;
    /**
     * Nodo asociado al punto.
     */
    private Nodo nodo;

    /**
     * Constructor de la clase Punto.
     *
     * @param point Punto representado por un objeto Point2D.
     * @param node  Nodo asociado al punto.
     */
    public Punto(final Point2D point, final Nodo node) {
        point2d = point;
        nodo = node;
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
        return point2d;
    }
}
