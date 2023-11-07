package cl.ucn.PIPA.interfazGrafica.ventanas;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.logica.Sistema;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Clase que representa la ventana del menú principal.
 */
public class VentanaMenu implements Ventana {
    private AdministradorDeVentanas administradorDeVentanas;
    private JFrame ventana;
    private Tema tema;
    private Sistema sistema;

    /**
     * Constructor de la clase VentanaMenu.
     *
     * @param administradorDeVentanas Administrador de ventanas.
     * @param sistema                Sistema.
     * @param tema                   Tema de la ventana.
     */
    public VentanaMenu(AdministradorDeVentanas administradorDeVentanas,Sistema sistema, Tema tema) {
        this.sistema = sistema;
        this.ventana = new JFrame("Menú");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.ventana.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
                administradorDeVentanas.ventanaCierre(ventana);
                ventana.setEnabled(false);
			}
		});
        this.tema = tema;
        this.administradorDeVentanas = administradorDeVentanas;
        ventana.setSize(350,250);
        ventana.setMaximumSize(new Dimension(350,250));
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
    }

    public void iniciarVentana() {
        JTabbedPane panelPestanas = new JTabbedPane();
        panelPestanas.setBackground(tema.getFondo());
        panelPestanas.setForeground(tema.getLetra());
        
        //-------------------------------------------------------------------------------------------------
        JPanel panelLocal = new JPanel();
        panelLocal.setBackground(tema.getFondo());
		panelLocal.setLayout(null);
        panelPestanas.addTab("Local", panelLocal);
        
		JLabel tituloLocal = new JLabel("Local");
        tituloLocal.setForeground(tema.getLetra());
		tituloLocal.setBounds(150, 0, 250, 50);
		panelLocal.add(tituloLocal);
		
		JButton botonMostrarMapa = new JButton("Ver mapa");
        botonMostrarMapa.setBackground(tema.getBoton());
        botonMostrarMapa.setForeground(tema.getLetra());
		botonMostrarMapa.setBounds(85, 50, 165, 25);
        botonMostrarMapa.setEnabled(!sistema.getGrafo().getNodos().isEmpty());
		panelLocal.add(botonMostrarMapa);

		botonMostrarMapa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.mostrarMapa(administradorDeVentanas);
                ventana.setVisible(false);
            }
        });

        JButton botonSeleccionArchivos = new JButton("Seleccionar archivos");
        botonSeleccionArchivos.setBackground(tema.getBoton());
        botonSeleccionArchivos.setForeground(tema.getLetra());
		botonSeleccionArchivos.setBounds(85, 85, 165, 25);
		panelLocal.add(botonSeleccionArchivos);
        botonSeleccionArchivos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.ventanaArchivos(administradorDeVentanas);
                ventana.setVisible(false);
            }
        });

        //-------------------------------------------------------------------------------------------------

        JPanel panelConfig = new JPanel();
        panelConfig.setBackground(tema.getFondo());
		panelConfig.setLayout(null);
        panelPestanas.addTab("Configuraciones", panelConfig);

        JLabel tituloConfig = new JLabel("Configuraciones");
        tituloConfig.setForeground(tema.getLetra());
		tituloConfig.setBounds(120, 0, 250, 50);
		panelConfig.add(tituloConfig);

        JButton botonSeleccionTema = new JButton("Cambiar tema");
        botonSeleccionTema.setBackground(tema.getBoton());
        botonSeleccionTema.setForeground(tema.getLetra());
		botonSeleccionTema.setBounds(85, 50, 165, 25);
		panelConfig.add(botonSeleccionTema);
		
		botonSeleccionTema.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.ventanaTema(administradorDeVentanas);
                ventana.setVisible(false);
            }
        });
        ventana.add(panelPestanas);
        ventana.setVisible(true);
    }
}