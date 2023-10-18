package cl.ucn.PIPA.dominio;
import java.awt.geom.Line2D;

public class Linea {
    private Line2D line;
    private Arco arco;
    public Linea(Line2D line,Arco arco){
        this.line = line;
        this.arco = arco;
    }
    public Arco getArco() {
        return arco;
    }
    public Line2D getLine() {
        return line;
    }
}