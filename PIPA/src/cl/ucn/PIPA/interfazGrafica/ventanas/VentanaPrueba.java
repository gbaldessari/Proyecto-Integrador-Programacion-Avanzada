package cl.ucn.PIPA.interfazGrafica.ventanas;

import cl.ucn.PIPA.dominio.Panel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrueba implements Ventana{
    private AdministradorDeVentanas administradorDeVentanas;
    private Panel panel;
    private JFrame ventana;

    public VentanaPrueba(AdministradorDeVentanas administradorDeVentanas, JFrame ventana) {
        this.ventana = ventana;
        this.panel = new Panel("ventana prueba");
        ventana.setTitle("Ventana Prueba");
        this.administradorDeVentanas = administradorDeVentanas;
        ventana.setSize(300,150);
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
		ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.panel.setAlto(300);
        this.panel.setAncho(150);
        this.panel.setTitulo("Ventana Prueba");
    }

    public void iniciarVentana(){
        JPanel panel = new JPanel();
		panel.setLayout(null);
		ventana.getContentPane().add(panel,BorderLayout.CENTER);

        JButton lectura = new JButton("lectura");
		lectura.setBounds(70, 50, 140, 25);
		panel.add(lectura);

        lectura.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.limpiarVentana(ventana);
                administradorDeVentanas.leerArchivo(administradorDeVentanas);
                //ventana.setVisible(false);
            }
        });

        JTextField texto = new JTextField();
        texto.setBounds(30,50,30,50);
        panel.add(texto);

        this.panel.getPaneles().add(panel);
        ventana.setVisible(true);
    }

    public Panel getPanel(){
        return this.panel;
    }
}
