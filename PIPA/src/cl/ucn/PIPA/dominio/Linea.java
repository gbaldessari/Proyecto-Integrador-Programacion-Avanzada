package cl.ucn.PIPA.dominio;

import java.awt.geom.Line2D;

/**
 * Clase que representa una línea con su respectivo arco asociado.
 */
public class Linea {
    private Line2D line;  // Línea representada por un objeto Line2D
    private Arco arco;  // Arco asociado a la línea

    /**
     * Constructor de la clase Linea.
     *
     * @param line Línea representada por un objeto Line2D.
     * @param arco Arco asociado a la línea.
     */
    public Linea(Line2D line, Arco arco) {
        this.line = line;
        this.arco = arco;
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
        return line;
    }
}
