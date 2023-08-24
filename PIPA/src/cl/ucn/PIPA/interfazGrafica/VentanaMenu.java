package cl.ucn.PIPA.interfazGrafica;
import cl.ucn.PIPA.logica.Sistema;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VentanaMenu implements Ventana{
    private JFrame frame;
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    public void iniciarVentana(AdministradorDeVentanas administradorDeVentanas, Sistema sistema) {
        frame = new JFrame("Menu");
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setMinimumSize(new Dimension(500,500));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		iniciarComponentes();
    }

    public void iniciarComponentes() {
    }

    public JFrame getFrame() {
        return frame;
    }
}