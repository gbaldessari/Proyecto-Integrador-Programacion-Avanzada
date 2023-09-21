package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import cl.ucn.PIPA.dominio.Panel;
import cl.ucn.PIPA.interfazGrafica.paneles.PanelMapa;
import cl.ucn.PIPA.logica.Sistema;


public class VentanaMapa implements Ventana{
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    private JFrame ventana;
    private Panel panel;

    public VentanaMapa(AdministradorDeVentanas administradorDeVentanas, Sistema sistema, JFrame ventana){
        this.ventana = ventana;
        ventana.setTitle("Mapa");
        this.panel = new Panel("mapa");
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        ventana.setSize(700,700);
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(true);
		ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void iniciarVentana() {
        PanelMapa panelMapa = new PanelMapa(sistema);
        ventana.getContentPane().add(panelMapa,BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        JButton botonMenu = new JButton("Volver");
        panel.add(botonMenu);
        ventana.getContentPane().add(panel,BorderLayout.SOUTH);
		botonMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.limpiarVentana(ventana); 
                administradorDeVentanas.menu(administradorDeVentanas);
                //ventana.setVisible(false);
            }
        });
        ventana.setVisible(true);
        this.panel.getPaneles().add(panelMapa);
        this.panel.getPaneles().add(panel);
    }

    public Panel getPanel(){
        return this.panel;
    }
}