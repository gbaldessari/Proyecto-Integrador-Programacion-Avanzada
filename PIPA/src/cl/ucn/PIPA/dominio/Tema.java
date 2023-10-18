package cl.ucn.PIPA.dominio;
import java.awt.Color;

public class Tema {
    private String nombre;
    private Color fondo;
    private Color ui;
    private Color boton;
    private Color letra;
    private Color puntos;
    private Color puntoSeleccionado;

    public Tema(String nombre,String hexFondo,String hexUI,String hexBoton,String hexLetra,String hexPuntos,String hexPuntoSeleccionado){
        this.nombre =nombre;
        fondo = Color.decode(hexFondo);
        ui = Color.decode(hexUI);
        boton = Color.decode(hexBoton);
        letra = Color.decode(hexLetra);
        puntos = Color.decode(hexPuntos);
        puntoSeleccionado = Color.decode(hexPuntoSeleccionado);
    }
    public String getNombre() {
        return nombre;
    }
    public Color getBoton() {
        return boton;
    }
    public Color getFondo() {
        return fondo;
    }
    public Color getUi() {
        return ui;
    }
    public Color getLetra() {
        return letra;
    }
    public Color getPuntoSeleccionado() {
        return puntoSeleccionado;
    }
    public Color getPuntos() {
        return puntos;
    }
}