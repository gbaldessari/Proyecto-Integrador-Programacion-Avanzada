package cl.ucn.PIPA.interfazGrafica.ventanas;
import javax.swing.JFrame;

import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.logica.Sistema;

public class VentanaArchivos implements Ventana{
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    private Tema tema;
    private JFrame ventana;
    
    public VentanaArchivos(AdministradorDeVentanas administradorDeVentanas,Sistema sistema, Tema tema){
        this.sistema = sistema;
        this.tema = tema;
        this.administradorDeVentanas = administradorDeVentanas;
        iniciarVentana();
    }
    public void iniciarVentana() {
        this.ventana = new JFrame("Selleción de archivos");
        ventana.setVisible(true);
    }
    
}