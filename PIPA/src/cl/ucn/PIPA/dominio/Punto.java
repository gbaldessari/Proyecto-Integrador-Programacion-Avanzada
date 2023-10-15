package cl.ucn.PIPA.dominio;
import java.awt.geom.Point2D;

public class Punto {
    private Point2D point;
    private Nodo nodo;
    public Punto(Point2D point,Nodo nodo){
        this.point = point;
        this.nodo = nodo;
    }
    public Nodo getNodo() {
        return nodo;
    }
    public Point2D getPoint() {
        return point;
    }
}