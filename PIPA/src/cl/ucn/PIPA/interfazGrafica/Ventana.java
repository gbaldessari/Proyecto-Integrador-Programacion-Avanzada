package cl.ucn.PIPA.interfazGrafica;
import javax.swing.JFrame;
import cl.ucn.PIPA.logica.Sistema;

public interface Ventana {
    public void iniciarVentana(AdministradorDeVentanas administradorDeVentanas,Sistema sistema);
    public void iniciarComponentes();
    public JFrame getFrame();
}