package cl.ucn.PIPA.dominio;

import java.awt.Color;

/**
 * Clase que representa un tema de colores para la interfaz de usuario.
 */
public class Tema {
    /**
     * Nombre del tema.
     */
    private String nombre;
    /**
     * Color de fondo.
     */
    private Color fondo;
    /**
     * Color de interfaz.
     */
    private Color ui;
    /**
     * Color de botones.
     */
    private Color boton;
    /**
     * Color de letras.
     */
    private Color texto;
    /**
     * Color de puntos.
     */
    private Color puntos;
    /**
     * Color de puntos seleccionados.
     */
    private Color puntoSeleccionado;

    /**
     * Constructor de la clase Tema.
     *
     * @param nombreTema Nombre del tema.
     * @param hexFondo Representación hexadecimal del color de fondo.
     * @param hexUI Representación hexadecimal del
     * color de interfaz.
     * @param hexBoton Representación hexadecimal del
     * color de botones.
     * @param hexTexto Representación hexadecimal del color de letras.
     * @param hexPuntos Representación hexadecimal del color de puntos.
     * @param hexPuntoSeleccionado Representación hexadecimal del
     * color de puntos seleccionados.
     */
    public Tema(final String nombreTema, final String hexFondo,
    final String hexUI, final String hexBoton, final String hexTexto,
    final String hexPuntos, final String hexPuntoSeleccionado) {
        nombre = nombreTema;
        fondo = Color.decode(hexFondo);
        ui = Color.decode(hexUI);
        boton = Color.decode(hexBoton);
        texto = Color.decode(hexTexto);
        puntos = Color.decode(hexPuntos);
        puntoSeleccionado = Color.decode(hexPuntoSeleccionado);
    }

    /**
     * Retorna el nombre del tema.
     *
     * @return El nombre del tema.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Retorna el color de botones.
     *
     * @return El color de botones.
     */
    public Color getBoton() {
        return boton;
    }

    /**
     * Retorna el color de fondo.
     *
     * @return El color de fondo.
     */
    public Color getFondo() {
        return fondo;
    }

    /**
     * Retorna el color de interfaz.
     *
     * @return El color de interfaz.
     */
    public Color getUi() {
        return ui;
    }

    /**
     * Retorna el color de letras.
     *
     * @return El color de letras.
     */
    public Color getTexto() {
        return texto;
    }

    /**
     * Retorna el color de puntos seleccionados.
     *
     * @return El color de puntos seleccionados.
     */
    public Color getPuntoSeleccionado() {
        return puntoSeleccionado;
    }

    /**
     * Retorna el color de puntos.
     *
     * @return El color de puntos.
     */
    public Color getPuntos() {
        return puntos;
    }
}
