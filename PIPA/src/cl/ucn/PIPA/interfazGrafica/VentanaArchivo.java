package cl.ucn.PIPA.interfazGrafica;
import cl.ucn.PIPA.logica.Sistema;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class VentanaArchivo implements Ventana {
    private JFrame frame;
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    public VentanaArchivo(AdministradorDeVentanas administradorDeVentanas, Sistema sistema) {
        frame = new JFrame("Lectura de Archivos");
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        frame.setSize(300,225);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		iniciarComponentes();
    }

    public void iniciarComponentes() {
        JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.getContentPane().add(panel,BorderLayout.CENTER);
		
		JLabel mensaje = new JLabel("Ingrese el nombre del archivo");
		mensaje.setBounds(55, 0, 250, 50);
		panel.add(mensaje);

        JTextField archivo = new JTextField(null, null, 1);
        archivo.setBounds(70, 40, 140, 25);
        panel.add(archivo);
        archivo.setVisible(true);
		
		JButton botonSiguiente = new JButton("Siguiente");
		botonSiguiente.setBounds(70, 80, 140, 25);
		panel.add(botonSiguiente);

        botonSiguiente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean valido = sistema.buscarArchivo(sistema,archivo.getText());
                if(valido){
                    administradorDeVentanas.leerArchivo(administradorDeVentanas,archivo.getText());
                    frame.setVisible(false);
                }
            }
        
        });

        JButton botonMenu = new JButton("Volver");
        botonMenu.setBounds(70, 120, 140, 25);
        panel.add(botonMenu);

        botonMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.menu(administradorDeVentanas);
                frame.setVisible(false);
            }
        });
		
    }

    public JFrame getFrame() {
        return frame;
    }
}