package cl.ucn.PIPA.dominio;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Panel {
    private String nombre;
    private List<JPanel> paneles;
    private String titulo;
    private int alto,ancho;

    public Panel(String nombre){
        this.nombre = nombre;
        this.paneles = new LinkedList<>();
    }

    public LinkedList<JPanel> getPaneles(){
        return (LinkedList<JPanel>) this.paneles;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setAncho(int ancho){
        this.ancho = ancho;
    }

    public void setAlto(int alto){
        this.alto = alto;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getTitulo(){
        return this.titulo;
    }

    public int getAlto(){
        return this.alto;
    }

    public int getAncho(){
        return this.ancho;
    }

    public void setDimensiones(JFrame ventana){
        ventana.setTitle(this.titulo);
        ventana.setSize(this.alto,this.ancho);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
