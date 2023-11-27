package cl.ucn.PIPA.dominio;
import java.awt.geom.Line2D;

/**
 * Clase que representa una línea con su respectivo arco asociado.
 */
public class Linea {
    /**
     * Línea representada por un objeto Line2D.
     */
    private Line2D line2d;
    /**
     * Arco asociado a la línea.
     */
    private Arco arco;

    /**
     * Constructor de la clase Linea.
     *
     * @param line Línea representada por un objeto Line2D.
     * @param arc Arco asociado a la línea.
     */
    public Linea(final Line2D line, final Arco arc) {
        line2d = line;
        arco = arc;
    }

    /**
     * Retorna el arco asociado a la línea.
     *
     * @return El arco asociado.
     */
    public Arco getArco() {
        return arco;
    }

    /**
     * Retorna la línea representada por un objeto Line2D.
     *
     * @return La línea representada.
     */
    public Line2D getLine() {
        return line2d;
    }
}
