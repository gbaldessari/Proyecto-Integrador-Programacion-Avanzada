package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import cl.ucn.PIPA.dominio.Paleta;
import cl.ucn.PIPA.interfazGrafica.paneles.PanelMapa;
import cl.ucn.PIPA.logica.Sistema;


public class VentanaMapa implements Ventana{
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    private JFrame ventana;
    private Paleta paleta;

    public VentanaMapa(AdministradorDeVentanas administradorDeVentanas, Sistema sistema, Paleta paleta){
        this.ventana = new JFrame("Mapa");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.ventana.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				cerrar(ventana);
			}
		});
        this.paleta = paleta;
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
                ventana.setVisible(false);
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
    }

    private void cerrar(JFrame ventana){
		String [] botones = {"Cerrar", "Cancelar"};
		int eleccion = JOptionPane.showOptionDialog(ventana, "¿Desea cerrar la aplicación", "Confirmar  ierre",
		0,JOptionPane.WARNING_MESSAGE,null,botones,ventana);
		if(eleccion==JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
}