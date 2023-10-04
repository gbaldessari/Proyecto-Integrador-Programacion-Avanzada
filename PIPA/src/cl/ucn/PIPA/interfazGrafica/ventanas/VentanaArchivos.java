package cl.ucn.PIPA.interfazGrafica.ventanas;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.logica.Sistema;

public class VentanaArchivos implements Ventana{
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    private Tema tema;
    public VentanaArchivos(AdministradorDeVentanas administradorDeVentanas,Sistema sistema, Tema tema){
        this.sistema = sistema;
        this.tema = tema;
        this.administradorDeVentanas = administradorDeVentanas;
    }
    public void iniciarVentana() {
    }
    
}