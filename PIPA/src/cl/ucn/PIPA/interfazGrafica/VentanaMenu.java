package cl.ucn.PIPA.interfazGrafica;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class VentanaMenu extends JFrame {
    private AdministradorDeVentanas administradorDeVentanas;
    public VentanaMenu(AdministradorDeVentanas administradorDeVentanas) {
        setTitle("Menu");
        this.administradorDeVentanas = administradorDeVentanas;
        setSize(300,150);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		iniciarComponentes();
    }

    public void iniciarComponentes() {
        JPanel panel = new JPanel();
		panel.setLayout(null);
		getContentPane().add(panel,BorderLayout.CENTER);
		
		JLabel mensaje = new JLabel("Menú principal");
		mensaje.setBounds(100, 0, 250, 50);
		panel.add(mensaje);
		
		JButton botonMostrarMapa = new JButton("Ver mapa");
		botonMostrarMapa.setBounds(70, 50, 140, 25);
		panel.add(botonMostrarMapa);
		
		botonMostrarMapa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.mostrarMapa(administradorDeVentanas);
                setVisible(false);
            }
        });
    }
}