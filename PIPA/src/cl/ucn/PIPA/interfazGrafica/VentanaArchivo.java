package cl.ucn.PIPA.interfazGrafica;
import cl.ucn.PIPA.logica.Sistema;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VentanaArchivo implements Ventana {
    private JFrame frame;
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    public void iniciarVentana(AdministradorDeVentanas administradorDeVentanas, Sistema sistema) {
        frame = new JFrame("Lectura de Archivos");
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        frame.setSize(300,175);
		frame.setLocationRelativeTo(null);
		frame.setMinimumSize(new Dimension(300,175));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		iniciarComponentes();
    }

    public void iniciarComponentes() {
        JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		JLabel mensaje = new JLabel("Ingrese el nombre del archivo");
		mensaje.setBounds(60, 0, 250, 50);
		panel.add(mensaje);

        JTextField archivo = new JTextField(null, null, 1);
        archivo.setBounds(70, 40, 140, 25);
        panel.add(archivo);
        archivo.setVisible(true);
		
		JButton boton = new JButton("Siguiente");
		boton.setBounds(70, 80, 140, 25);
		panel.add(boton);
		
		boton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                boolean valido = sistema.leerArchivo(sistema,archivo.getText());
                if(valido){administradorDeVentanas.menu(administradorDeVentanas);}
                else{administradorDeVentanas.archivo(administradorDeVentanas);}
                
                frame.setVisible(false);
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }
}