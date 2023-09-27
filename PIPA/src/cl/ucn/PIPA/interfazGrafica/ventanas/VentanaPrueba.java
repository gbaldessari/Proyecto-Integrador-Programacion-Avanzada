package cl.ucn.PIPA.interfazGrafica.ventanas;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaPrueba implements Ventana{
    private AdministradorDeVentanas administradorDeVentanas;
    private JFrame ventana;

    public VentanaPrueba(AdministradorDeVentanas administradorDeVentanas) {
        this.ventana = new JFrame("Ventana prueba");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.ventana.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				cerrar(ventana);
			}
		});
        this.administradorDeVentanas = administradorDeVentanas;
        ventana.setSize(300,150);
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
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
                ventana.setVisible(false);
            }
        });

        JTextField texto = new JTextField();
        texto.setBounds(70,20,140,25);
        panel.add(texto);
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
