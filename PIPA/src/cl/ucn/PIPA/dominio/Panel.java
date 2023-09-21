package cl.ucn.PIPA.dominio;

import java.util.LinkedList;
import java.util.List;
import javax.swing.JPanel;

public class Panel {
    private String nombre;
    private List<JPanel> paneles;

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
}
