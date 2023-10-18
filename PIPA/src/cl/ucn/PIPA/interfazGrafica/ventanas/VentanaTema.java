package cl.ucn.PIPA.interfazGrafica.ventanas;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
public class VentanaTema implements Ventana{
    Sistema sistema;
    AdministradorDeVentanas administradorDeVentanas;
    JFrame ventana;
    Tema tema;

    /**
     * Constructor de la clase VentanaTema.
     *
     * @param administradorDeVentanas Administrador de ventanas.
     * @param sistema                Sistema.
     * @param tema                   Tema de la ventana.
     */
    public VentanaTema(AdministradorDeVentanas administradorDeVentanas,Sistema sistema,Tema tema){
        this.sistema = sistema;
        this.administradorDeVentanas = administradorDeVentanas;
        this.tema = tema;
        ventana = new JFrame("Seleccion de tema");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.ventana.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
                administradorDeVentanas.ventanaCierre(ventana);
                ventana.setEnabled(false);
			}
		});
        ventana.setSize(300,175);
        ventana.setMaximumSize(new Dimension(300,175));
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
    }

    public void iniciarVentana() {
        JPanel panel = new JPanel();
        panel.setBackground(tema.getFondo());
		panel.setLayout(null);
		ventana.getContentPane().add(panel,BorderLayout.CENTER);

		JLabel mensaje = new JLabel("Seleccion de tema");
        mensaje.setForeground(tema.getLetra());
		mensaje.setBounds(102, 0, 250, 50);
		panel.add(mensaje);
		
		JComboBox<String> seleccionTema = new JComboBox<>(sistema.getListaTemas(administradorDeVentanas));
        seleccionTema.setBackground(tema.getBoton());
        seleccionTema.setForeground(tema.getLetra());
		seleccionTema.setBounds(72, 50, 140, 25);
		panel.add(seleccionTema);
		
		JButton botonMenu = new JButton("Volver");
        botonMenu.setForeground(tema.getLetra());
        botonMenu.setBackground(tema.getBoton());
		botonMenu.setBounds(20, 100, 100, 25);
        panel.add(botonMenu);
		botonMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.menu(administradorDeVentanas);
                ventana.setVisible(false);
            }
        });

		JButton botonAplicar = new JButton("Aplicar");
        botonAplicar.setForeground(tema.getLetra());
        botonAplicar.setBackground(tema.getBoton());
		botonAplicar.setBounds(160, 100, 100, 25);
        panel.add(botonAplicar);
        
		botonAplicar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				administradorDeVentanas.setTema(seleccionTema.getSelectedIndex()-1);
                administradorDeVentanas.ventanaTema(administradorDeVentanas);
                ventana.setVisible(false);
            }
        });
		ventana.setVisible(true);
    }
}