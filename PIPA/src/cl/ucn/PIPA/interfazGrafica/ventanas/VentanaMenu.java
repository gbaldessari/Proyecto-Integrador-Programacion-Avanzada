package cl.ucn.PIPA.interfazGrafica.ventanas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
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
    /** */
    private AdministradorDeVentanas administradorDeVentanas;
    /** */
    private JFrame ventana;
    /** */
    private Tema tema;
    /** */
    private Sistema sistema;

    /**
     * Constructor de la clase VentanaMenu.
     *
     * @param administradorDeVentanasEntregado Administrador de ventanas.
     * @param sistemaEntregado Sistema.
     * @param temaEntregado Tema de la ventana.
     */
    public VentanaMenu(
    final AdministradorDeVentanas administradorDeVentanasEntregado,
    final Sistema sistemaEntregado, final Tema temaEntregado) {
        sistema = sistemaEntregado;
        this.ventana = new JFrame("Menú");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.ventana.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent we) {
                administradorDeVentanas.ventanaCierre(ventana);
                ventana.setEnabled(false);
            }
        });
        tema = temaEntregado;
        administradorDeVentanas = administradorDeVentanasEntregado;
        final Dimension dim = new Dimension(350, 250);
        ventana.setSize(dim);
        ventana.setMaximumSize(dim);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
    }

    /**
     * Método para iniciar la ventana y mostrarla al usuario.
     */
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
        final Rectangle rectTituloLocal = new Rectangle(150, 0, 250, 50);
        tituloLocal.setBounds(rectTituloLocal);
        panelLocal.add(tituloLocal);

        JButton botonSeleccionArchivos = new JButton("Seleccionar archivos");
        botonSeleccionArchivos.setBackground(tema.getBoton());
        botonSeleccionArchivos.setForeground(tema.getTexto());
        final Rectangle rectSeleccionArchivos = new Rectangle(85, 50, 165, 25);
        botonSeleccionArchivos.setBounds(rectSeleccionArchivos);
        panelLocal.add(botonSeleccionArchivos);
        botonSeleccionArchivos.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                administradorDeVentanas.ventanaArchivosLocales(
                administradorDeVentanas);
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
        final Rectangle rectTituloOnline = new Rectangle(150, 0, 250, 50);
        tituloOnline.setBounds(rectTituloOnline);
        panelOnline.add(tituloOnline);

        JButton botonSeleccionArchivosOnline = new JButton(
        "Seleccionar archivos");
        botonSeleccionArchivosOnline.setBackground(tema.getBoton());
        botonSeleccionArchivosOnline.setForeground(tema.getTexto());
        final Rectangle rectSeleccionArchivosOnline
        = new Rectangle(85, 50, 165, 25);
        botonSeleccionArchivosOnline.setBounds(rectSeleccionArchivosOnline);
        panelOnline.add(botonSeleccionArchivosOnline);
        botonSeleccionArchivosOnline.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                administradorDeVentanas.ventanaArchivosOnline(
                administradorDeVentanas);
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
        final Rectangle rectTituloConfig = new Rectangle(120, 0, 250, 50);
        tituloConfig.setBounds(rectTituloConfig);
        panelConfig.add(tituloConfig);

        JButton botonSeleccionTema = new JButton("Cambiar tema");
        botonSeleccionTema.setBackground(tema.getBoton());
        botonSeleccionTema.setForeground(tema.getTexto());
        final Rectangle rectSeleccionTema = new Rectangle(85, 50, 165, 25);
        botonSeleccionTema.setBounds(rectSeleccionTema);
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
        final Rectangle rectMostrarMapa = new Rectangle(85, 0, 165, 25);
        botonMostrarMapa.setBounds(rectMostrarMapa);
        botonMostrarMapa.setEnabled(!sistema.getGrafo().getNodos().isEmpty());
        panel.add(botonMostrarMapa);

        botonMostrarMapa.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                administradorDeVentanas.mostrarMapa(administradorDeVentanas);
                ventana.setVisible(false);
                ventana.dispose();
            }
        });

        ventana.add(panelPestanas, BorderLayout.CENTER);
        ventana.add(panel, BorderLayout.SOUTH);
        ventana.setVisible(true);
    }
}
