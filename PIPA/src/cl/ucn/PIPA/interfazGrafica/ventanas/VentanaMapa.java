package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import cl.ucn.PIPA.dominio.Paleta;
import cl.ucn.PIPA.dominio.Panel;
import cl.ucn.PIPA.interfazGrafica.paneles.PanelMapa;
import cl.ucn.PIPA.logica.Sistema;


public class VentanaMapa implements Ventana{
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    private JFrame ventana;
    private Panel panel;
    private Paleta paleta;

    public VentanaMapa(AdministradorDeVentanas administradorDeVentanas, Sistema sistema, JFrame ventana, Paleta paleta){
        this.ventana = ventana;
        this.paleta = paleta;
        ventana.setTitle("Mapa");
        this.panel = new Panel("mapa");
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        ventana.setSize(1200,700);
        ventana.setMinimumSize(new Dimension(1200,700));
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(true);
    }
    public void iniciarVentana() {
        PanelMapa panelMapa = new PanelMapa(sistema,paleta);
        ventana.getContentPane().add(panelMapa,BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        panel.setBackground(paleta.getUi());
        JButton botonMenu = new JButton("Volver");
        botonMenu.setForeground(paleta.getLetra());
        botonMenu.setBackground(paleta.getBoton());
        panel.add(botonMenu);
        ventana.getContentPane().add(panel,BorderLayout.SOUTH);
		botonMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventana.setMinimumSize(new Dimension(1,1));
                administradorDeVentanas.limpiarVentana(ventana); 
                administradorDeVentanas.menu(administradorDeVentanas);
            }
        });

        JPanel panelInfo = new JPanel();
        JLabel informacion = new JLabel("Informacion");
        informacion.setBackground(paleta.getLetra());
        panelInfo.setBackground(paleta.getUi());
        panelInfo.add(informacion);
        panelInfo.setPreferredSize(new Dimension(this.ventana.getWidth()/5, this.ventana.getHeight()));
        ventana.getContentPane().add(panelInfo,BorderLayout.EAST);

        ventana.setVisible(true);
        this.panel.getPaneles().add(panelMapa);
        this.panel.getPaneles().add(panel);
    }

    public Panel getPanel(){
        return this.panel;
    }
}