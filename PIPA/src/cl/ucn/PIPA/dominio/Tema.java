package cl.ucn.PIPA.dominio;

import java.awt.Color;

/**
 * Clase que representa un tema de colores para la interfaz de usuario.
 */
public class Tema {
    private String nombre;  // Nombre del tema
    private Color fondo;  // Color de fondo
    private Color ui;  // Color de interfaz
    private Color boton;  // Color de botones
    private Color letra;  // Color de letras
    private Color puntos;  // Color de puntos
    private Color puntoSeleccionado;  // Color de puntos seleccionados

    /**
     * Constructor de la clase Tema.
     *
     * @param nombre             Nombre del tema.
     * @param hexFondo           Representación hexadecimal del color de fondo.
     * @param hexUI              Representación hexadecimal del color de interfaz.
     * @param hexBoton           Representación hexadecimal del color de botones.
     * @param hexLetra           Representación hexadecimal del color de letras.
     * @param hexPuntos          Representación hexadecimal del color de puntos.
     * @param hexPuntoSeleccionado Representación hexadecimal del color de puntos seleccionados.
     */
    public Tema(String nombre, String hexFondo, String hexUI, String hexBoton, String hexLetra, String hexPuntos, String hexPuntoSeleccionado) {
        this.nombre = nombre;
        fondo = Color.decode(hexFondo);
        ui = Color.decode(hexUI);
        boton = Color.decode(hexBoton);
        letra = Color.decode(hexLetra);
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
    public Color getLetra() {
        return letra;
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