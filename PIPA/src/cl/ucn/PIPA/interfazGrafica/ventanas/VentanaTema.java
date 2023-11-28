package cl.ucn.PIPA.interfazGrafica.ventanas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.logica.Sistema;

/**
 * Clase que representa la ventana de selección de tema.
 */
public class VentanaTema implements Ventana {
    /** */
    private Sistema sistema;
    /** */
    private AdministradorDeVentanas administradorDeVentanas;
    /** */
    private JFrame ventana;
    /** */
    private Tema tema;

    /**
     * Constructor de la clase VentanaTema.
     *
     * @param administradorDeVentanasEntregado Administrador de ventanas.
     * @param sistemaEntregado Sistema.
     * @param temaEntregado Tema de la ventana.
     */
    public VentanaTema(
    final AdministradorDeVentanas administradorDeVentanasEntregado,
    final Sistema sistemaEntregado, final Tema temaEntregado) {
        sistema = sistemaEntregado;
        administradorDeVentanas = administradorDeVentanasEntregado;
        tema = temaEntregado;
        ventana = new JFrame("Seleccion de tema");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.ventana.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent we) {
                administradorDeVentanas.ventanaCierre(ventana);
                ventana.setEnabled(false);
            }
        });
        final Dimension dim = new Dimension(300, 175);
        ventana.setSize(dim);
        ventana.setMaximumSize(dim);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
    }

    /**
     * Método para iniciar la ventana y mostrarla al usuario.
     */
    public final void iniciarVentana() {
        JPanel panel = new JPanel();
        panel.setBackground(tema.getFondo());
        panel.setLayout(null);
        ventana.getContentPane().add(panel, BorderLayout.CENTER);

        JLabel mensaje = new JLabel("Seleccion de tema");
        mensaje.setForeground(tema.getTexto());
        final Rectangle rectMensaje = new Rectangle(85, 0, 250, 50);
        mensaje.setBounds(rectMensaje);
        panel.add(mensaje);

        JComboBox<String> seleccionTema = new JComboBox<>(
        sistema.getListaTemas(administradorDeVentanas));
        seleccionTema.setBackground(tema.getBoton());
        seleccionTema.setForeground(tema.getTexto());
        final Rectangle rectSeleccionTema = new Rectangle(72, 50, 140, 25);
        seleccionTema.setBounds(rectSeleccionTema);
        panel.add(seleccionTema);

        JButton botonMenu = new JButton("Volver");
        botonMenu.setForeground(tema.getTexto());
        botonMenu.setBackground(tema.getBoton());
        final Rectangle rectMenu = new Rectangle(20, 100, 100, 25);
        botonMenu.setBounds(rectMenu);
        panel.add(botonMenu);
        botonMenu.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                administradorDeVentanas.menu(administradorDeVentanas);
                ventana.setVisible(false);
                ventana.dispose();
            }
        });

        JButton botonAplicar = new JButton("Aplicar");
        botonAplicar.setForeground(tema.getTexto());
        botonAplicar.setBackground(tema.getBoton());
        final Rectangle rectAplicar = new Rectangle(160, 100, 100, 25);
        botonAplicar.setBounds(rectAplicar);
        panel.add(botonAplicar);

        botonAplicar.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                administradorDeVentanas.setTema(
                seleccionTema.getSelectedIndex() - 1);
                administradorDeVentanas.ventanaTema(administradorDeVentanas);
                ventana.setVisible(false);
                ventana.dispose();
            }
        }); ventana.setVisible(true);
    }
}
