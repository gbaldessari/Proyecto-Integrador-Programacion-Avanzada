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
        panelInfo.setLayout(null);
        JLabel informacion = new JLabel("Informacion");
        informacion.setBounds(65,10, 200, 50);
        informacion.setFont(informacion.getFont().deriveFont(19f));

        JLabel infoNodo1 = new JLabel("Punto de partida: ");
        infoNodo1.setBounds(10, 60, 200,50);
        infoNodo1.setBackground(paleta.getLetra());
        infoNodo1.setFont(infoNodo1.getFont().deriveFont(14f));
        JLabel c1 = new JLabel("");
        c1.setBounds(10, 80, 200,50);
        c1.setBackground(paleta.getLetra());
        c1.setFont(c1.getFont().deriveFont(14f));

        JLabel infoNodo2 = new JLabel("Punto de llegada: ");
        infoNodo2.setBounds(10, 120, 200, 50);
        infoNodo2.setBackground(paleta.getLetra());
        infoNodo2.setFont(infoNodo2.getFont().deriveFont(14f));
        JLabel c2 = new JLabel("");
        c2.setBounds(10, 140, 200, 50);
        c2.setBackground(paleta.getLetra());
        c2.setFont(c2.getFont().deriveFont(14f));

        panelMapa.setC1(c1);
        panelMapa.setC2(c2);

        informacion.setBackground(paleta.getLetra());
        panelInfo.setBackground(paleta.getUi());

        panelInfo.add(informacion);
        panelInfo.add(infoNodo1);
        panelInfo.add(infoNodo2);
        panelInfo.add(c1);
        panelInfo.add(c2);
        panelInfo.setPreferredSize(new Dimension(this.ventana.getWidth()/5, this.ventana.getHeight()));
        ventana.getContentPane().add(panelInfo,BorderLayout.EAST);

        ventana.setVisible(true);
    }

    private void cerrar(JFrame ventana){
		String [] botones = {"Cerrar", "Cancelar"};
		int eleccion = JOptionPane.showOptionDialog(ventana, "¿Desea cerrar la aplicación", "Confirmar cierre",
		0,JOptionPane.WARNING_MESSAGE,null,botones,ventana);
		if(eleccion==JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
}