package cl.ucn.PIPA.interfazGrafica.ventanas;

import java.awt.BorderLayout;
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
    public VentanaMenu(final AdministradorDeVentanas administradorDeVentanas, final Sistema sistema, final Tema tema) {
        this.sistema = sistema;
        this.ventana = new JFrame("Menú");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.ventana.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent we) {
                administradorDeVentanas.ventanaCierre(ventana);
                ventana.setEnabled(false);
            }
        });
        this.tema = tema;
        this.administradorDeVentanas = administradorDeVentanas;
        ventana.setSize(350, 250);
        ventana.setMaximumSize(new Dimension(350, 250));
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
    }

    public final void iniciarVentana() {
        JTabbedPane panelPestanas = new JTabbedPane();
        panelPestanas.setBackground(tema.getFondo());
        panelPestanas.setForeground(tema.getTexto());

        // Local
        // ----------------------------------
        JPanel panelLocal = new JPanel();
        panelLocal.setBackground(tema.getFondo());
        panelLocal.setLayout(null);
        panelPestanas.addTab("Local", panelLocal);

        JLabel tituloLocal = new JLabel("Local");
        tituloLocal.setForeground(tema.getTexto());
        tituloLocal.setBounds(150, 0, 250, 50);
        panelLocal.add(tituloLocal);

        JButton botonSeleccionArchivos = new JButton("Seleccionar archivos");
        botonSeleccionArchivos.setBackground(tema.getBoton());
        botonSeleccionArchivos.setForeground(tema.getTexto());
        botonSeleccionArchivos.setBounds(85, 50, 165, 25);
        panelLocal.add(botonSeleccionArchivos);
        botonSeleccionArchivos.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                administradorDeVentanas.ventanaArchivosLocales(administradorDeVentanas);
                ventana.setVisible(false);
                ventana.dispose();
            }
        });
        // Online----------------------------------------------------

        JPanel panelOnline = new JPanel();
        panelOnline.setBackground(tema.getFondo());
        panelOnline.setLayout(null);
        panelPestanas.addTab("Online", panelOnline);

        JLabel tituloOnline = new JLabel("Online");
        tituloOnline.setForeground(tema.getTexto());
        tituloOnline.setBounds(150, 0, 250, 50);
        panelOnline.add(tituloOnline);

        JButton botonSeleccionArchivosOnline = new JButton("Seleccionar archivos");
        botonSeleccionArchivosOnline.setBackground(tema.getBoton());
        botonSeleccionArchivosOnline.setForeground(tema.getTexto());
        botonSeleccionArchivosOnline.setBounds(85, 50, 165, 25);
        panelOnline.add(botonSeleccionArchivosOnline);
        botonSeleccionArchivosOnline.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                administradorDeVentanas.ventanaArchivosOnline(administradorDeVentanas);
                ventana.setVisible(false);
                ventana.dispose();
            }
        });

        // Config--------------------------------------

        JPanel panelConfig = new JPanel();
        panelConfig.setBackground(tema.getFondo());
        panelConfig.setLayout(null);
        panelPestanas.addTab("Configuraciones", panelConfig);

        JLabel tituloConfig = new JLabel("Configuraciones");
        tituloConfig.setForeground(tema.getTexto());
        tituloConfig.setBounds(120, 0, 250, 50);
        panelConfig.add(tituloConfig);

        JButton botonSeleccionTema = new JButton("Cambiar tema");
        botonSeleccionTema.setBackground(tema.getBoton());
        botonSeleccionTema.setForeground(tema.getTexto());
        botonSeleccionTema.setBounds(85, 50, 165, 25);
        panelConfig.add(botonSeleccionTema);

        botonSeleccionTema.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                administradorDeVentanas.ventanaTema(administradorDeVentanas);
                ventana.setVisible(false);
                ventana.dispose();
            }
        });

        // -------------------------------------------------
        JPanel panel = new JPanel();
        panel.setBackground(tema.getUi());

        JButton botonMostrarMapa = new JButton("Ver mapa");
        botonMostrarMapa.setBackground(tema.getBoton());
        botonMostrarMapa.setForeground(tema.getTexto());
        botonMostrarMapa.setBounds(85, 0, 165, 25);
        botonMostrarMapa.setEnabled(!sistema.getGrafo().getNodos().isEmpty());
        panel.add(botonMostrarMapa);

        botonMostrarMapa.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                administradorDeVentanas.mostrarMapa(administradorDeVentanas);
                ventana.setVisible(false);
                ventana.dispose();
            }
        });
        
        ventana.add(panelPestanas,BorderLayout.CENTER);
        ventana.add(panel, BorderLayout.SOUTH);
        ventana.setVisible(true);
    }
}
