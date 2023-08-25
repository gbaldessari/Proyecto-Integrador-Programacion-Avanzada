package cl.ucn.PIPA.interfazGrafica;
import cl.ucn.PIPA.logica.Sistema;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VentanaMist implements Ventana {
    private JFrame frame;
    private AdministradorDeVentanas administradorDeVentanas;
    public void iniciarVentana(AdministradorDeVentanas administradorDeVentanas, Sistema sistema) {
        frame = new JFrame("En 3 minuto, me lo como");
        this.administradorDeVentanas = administradorDeVentanas;
        frame.setSize(300,150);
		frame.setLocationRelativeTo(null);
		frame.setMinimumSize(new Dimension(300,150));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		iniciarComponentes();
    }

    public void iniciarComponentes() {
        JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		JLabel mensaje = new JLabel("Salsa y picante");
		mensaje.setBounds(100, 0, 250, 50);
		panel.add(mensaje);
		
		JButton boton = new JButton("Y nos fuimo");
		boton.setBounds(70, 50, 140, 25);
		panel.add(boton);
		
		boton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {administradorDeVentanas.menu(administradorDeVentanas); frame.setVisible(false);}});
    }

    public JFrame getFrame() {
        return frame;
    }
}