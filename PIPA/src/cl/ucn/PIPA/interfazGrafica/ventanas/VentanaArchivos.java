package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.logica.Sistema;

public class VentanaArchivos implements Ventana{
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    private Tema tema;
    private JFrame ventana;
    
    public VentanaArchivos(AdministradorDeVentanas administradorDeVentanas,Sistema sistema, Tema tema){
        this.ventana = new JFrame("Seleccionar archivos");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.ventana.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
                administradorDeVentanas.ventanaCierre(ventana);
                ventana.setEnabled(false);
			}
		});
        this.tema = tema;
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        ventana.setSize(600,400);
        ventana.setMaximumSize(new Dimension(600,300));
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
    }
    public void iniciarVentana() {
        JPanel panelVolver = new JPanel();
        ventana.getContentPane().add(panelVolver,BorderLayout.SOUTH);
        JButton volver = new JButton("Volver");
        volver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.limpiarVentana(ventana); 
                administradorDeVentanas.menu(administradorDeVentanas);
                ventana.setVisible(false);
            }
        });

        panelVolver.add(volver,BorderLayout.CENTER);
        ventana.setVisible(true);
    }
    
}