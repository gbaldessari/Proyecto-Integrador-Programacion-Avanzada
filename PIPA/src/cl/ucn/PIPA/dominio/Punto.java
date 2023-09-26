package cl.ucn.PIPA.dominio;

import java.awt.Point;

public class Punto {
    private Point point;
    private Nodo nodo;
    public Punto(Point point,Nodo nodo){
        this.point = point;
        this.nodo = nodo;
    }
    public Nodo getNodo() {
        return nodo;
    }
    public Point getPoint() {
        return point;
    }
}