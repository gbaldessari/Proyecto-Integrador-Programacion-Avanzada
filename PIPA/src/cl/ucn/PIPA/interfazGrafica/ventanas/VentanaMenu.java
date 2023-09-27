package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cl.ucn.PIPA.dominio.Paleta;
import cl.ucn.PIPA.dominio.Panel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/* 
 * Subclase ventana menu
*/
public class VentanaMenu implements Ventana {
    private AdministradorDeVentanas administradorDeVentanas;
    private Panel panel;
    private JFrame ventana;
    private Paleta paleta;

    /**
     * Constructor de la clase
     * @param administradorDeVentanas, herramienta para inicializar la ventana
     */
    public VentanaMenu(AdministradorDeVentanas administradorDeVentanas, JFrame ventana, Paleta paleta) {
        this.ventana = ventana;
        this.paleta = paleta;
        this.panel = new Panel("menu");
        ventana.setTitle("Menú");
        this.administradorDeVentanas = administradorDeVentanas;
        ventana.setSize(300,150);
        ventana.setMaximumSize(new Dimension(300,150));
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
        this.panel.setAlto(300);
        this.panel.setAncho(150);
        this.panel.setTitulo("Menú");
    }

    
    public void iniciarVentana() {
        JPanel panel = new JPanel();
        panel.setBackground(paleta.getFondo());
		panel.setLayout(null);
		ventana.getContentPane().add(panel,BorderLayout.CENTER);
		
		JLabel mensaje = new JLabel("Menú principal");
        mensaje.setBackground(paleta.getLetra());
		mensaje.setBounds(100, 0, 250, 50);
		panel.add(mensaje);
		
		JButton botonMostrarMapa = new JButton("Ver mapa");
        botonMostrarMapa.setBackground(paleta.getBoton());
        botonMostrarMapa.setForeground(paleta.getLetra());
		botonMostrarMapa.setBounds(70, 50, 140, 25);
		panel.add(botonMostrarMapa);
		
		botonMostrarMapa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.limpiarVentana(ventana);
                administradorDeVentanas.mostrarMapa(administradorDeVentanas);
                //ventana.setVisible(false);
            }
        });
        this.panel.getPaneles().add(panel);
        ventana.setVisible(true);
    }

    public Panel getPanel(){
        return this.panel;
    }
}