package cl.ucn.PIPA.interfazGrafica;
import cl.ucn.PIPA.logica.Sistema;
import java.awt.BorderLayout;
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
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		iniciarComponentes();
    }

    public void iniciarComponentes() {
        JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.getContentPane().add(panel,BorderLayout.CENTER);
		
		JLabel mensaje = new JLabel("Menú principal");
		mensaje.setBounds(100, 0, 250, 50);
		panel.add(mensaje);
		
		JButton botonLeerArchivos = new JButton("Leer Archivo");
		botonLeerArchivos.setBounds(70, 50, 140, 25);
		panel.add(botonLeerArchivos);
		
		botonLeerArchivos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.menu(administradorDeVentanas);
                frame.setVisible(false);
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }
}