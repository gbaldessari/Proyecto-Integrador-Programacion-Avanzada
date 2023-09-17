package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import cl.ucn.PIPA.interfazGrafica.paneles.PanelMapa;
import cl.ucn.PIPA.logica.Sistema;


public class VentanaMapa implements Ventana{
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    private JFrame frame;

    public VentanaMapa(AdministradorDeVentanas administradorDeVentanas, Sistema sistema){
        frame = new JFrame("Mapa");
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        frame.setSize(700,700);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void iniciarVentana() {
        PanelMapa panelMapa = new PanelMapa(sistema);
        frame.getContentPane().add(panelMapa,BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        JButton botonMenu = new JButton("Volver");
        panel.add(botonMenu);
        frame.getContentPane().add(panel,BorderLayout.SOUTH);
		botonMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.menu(administradorDeVentanas);
                frame.setVisible(false);
            }
        });
        frame.setVisible(true);
    }
}