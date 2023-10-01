package cl.ucn.PIPA.dominio;
import java.awt.Color;

public class Tema {
    private String nombre;
    private Color fondo;
    private Color ui;
    private Color boton;
    private Color letra;
    private Color lineas;
    private Color puntos;
    private Color puntoSeleccionado;

    public Tema(String nombre,float[] hsbFondo,float[] hsbUI,float[] hsbBoton,float[] hsbLetra,float[] hsbLineas,float[] hsbPuntos,float[] hsbPuntoSeleccionado){
        this.nombre =nombre;
        fondo = Color.getHSBColor(hsbFondo[0],hsbFondo[1],hsbFondo[2]);
        ui = Color.getHSBColor(hsbUI[0],hsbUI[1],hsbUI[2]);
        boton = Color.getHSBColor(hsbBoton[0],hsbBoton[1],hsbBoton[2]);
        letra = Color.getHSBColor(hsbLetra[0],hsbLetra[1],hsbLetra[2]);
        lineas = Color.getHSBColor(hsbLineas[0],hsbLineas[1],hsbLineas[2]);
        puntos = Color.getHSBColor(hsbPuntos[0],hsbPuntos[1],hsbPuntos[2]);
        puntoSeleccionado = Color.getHSBColor(hsbPuntoSeleccionado[0],hsbPuntoSeleccionado[1],hsbPuntoSeleccionado[2]);
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
    public Color getLineas() {
        return lineas;
    }
    public Color getPuntoSeleccionado() {
        return puntoSeleccionado;
    }
    public Color getPuntos() {
        return puntos;
    }
}