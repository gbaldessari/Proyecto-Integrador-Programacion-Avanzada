package cl.ucn.PIPA.dominio;
import java.awt.Point;

public class Nodo {
    String dato;
    double id;
    Point.Double coordenadas;

    public Nodo(int id, double posX, double posY){
        id = 0;
        coordenadas = new Point.Double(posX,posY);
    }
    public String getDato(){
        return dato;
    }
    public double getId(){
        return id;
    }
    public double getX(){
        return coordenadas.x;
    }
    public double getY(){
        return coordenadas.y;
    }
    public void setDato(String dato){
        this.dato = dato;
    }
    public void setId(int id){
        this.id = id;
    }
}