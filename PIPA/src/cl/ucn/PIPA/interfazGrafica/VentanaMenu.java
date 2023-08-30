package cl.ucn.PIPA.interfazGrafica;
import cl.ucn.PIPA.logica.Sistema;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class VentanaMenu implements Ventana {
    private JFrame frame;
    private AdministradorDeVentanas administradorDeVentanas;
    public VentanaMenu(AdministradorDeVentanas administradorDeVentanas, Sistema sistema) {
        frame = new JFrame("Menu");
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
		
		JLabel mensaje = new JLabel("Bienvenido al menú principal");
		mensaje.setBounds(60, 0, 250, 50);
		panel.add(mensaje);
		
		JButton boton = new JButton("Leer Archivo");
		boton.setBounds(70, 50, 140, 25);
		panel.add(boton);
		
		boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.archivo(administradorDeVentanas); 
                frame.setVisible(false);
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }
}