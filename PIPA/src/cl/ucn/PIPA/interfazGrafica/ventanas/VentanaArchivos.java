package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.logica.Sistema;

public class VentanaArchivos implements Ventana{
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    private Tema tema;
    private JFrame ventana;

    public VentanaArchivos(AdministradorDeVentanas administradorDeVentanas,Sistema sistema, Tema tema){
        this.ventana = new JFrame("Seleccionar archivos");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.ventana.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
                administradorDeVentanas.ventanaCierre(ventana);
                ventana.setEnabled(false);
			}
		});
        this.tema = tema;
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        ventana.setSize(300,150);
        ventana.setMaximumSize(new Dimension(300,150));
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
    }
    public void iniciarVentana() {
        JPanel panel = new JPanel(null);
        panel.setBackground(tema.getFondo());
        ventana.getContentPane().add(panel);

        JPanel panelBotones = new JPanel(null);
        panelBotones.setBackground(tema.getUi());
        panelBotones.setPreferredSize(new Dimension(this.ventana.getWidth(), 30));
        ventana.getContentPane().add(panelBotones,BorderLayout.SOUTH);

        JButton volver = new JButton("Volver");
        volver.setBounds(30,5,100,20);
        volver.setBackground(tema.getBoton());
        volver.setForeground(tema.getLetra());
        volver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.limpiarVentana(ventana); 
                administradorDeVentanas.menu(administradorDeVentanas);
                ventana.setVisible(false);
            }
        });

        JButton confirmar = new JButton("Confirmar");
        confirmar.setBounds(160,5,100,20);
        confirmar.setBackground(tema.getBoton());
        confirmar.setForeground(tema.getLetra());
        confirmar.setEnabled(false);
        confirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.limpiarVentana(ventana); 
                administradorDeVentanas.menu(administradorDeVentanas);
                ventana.setVisible(false);
            }
        });

        JLabel carpeta = new JLabel("Seleccione una ciudad: ");
        carpeta.setForeground(tema.getLetra());
		carpeta.setBounds(20, 0, 250, 50);
		panel.add(carpeta);

        JLabel ciudadSeleccionada = new JLabel("");
        ciudadSeleccionada.setForeground(tema.getLetra());
		ciudadSeleccionada.setBounds(20, 35, 250, 50);
		panel.add(ciudadSeleccionada);
		
        JButton ciudad = new JButton("Seleccionar");
        ciudad.setBackground(tema.getBoton());
        ciudad.setForeground(tema.getLetra());
		ciudad.setBounds(160, 15, 110, 20);
		panel.add(ciudad);
        ciudad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String carpetaInicio = "ciudades";
                String directorioWorkspace = System.getProperty("user.dir");
                String rutaCarpetaInicio = directorioWorkspace + File.separator + carpetaInicio;
                System.setProperty("user.dir",rutaCarpetaInicio);
                JFileChooser seleccion = new JFileChooser(System.getProperty("user.dir"));                
                seleccion.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int valor = seleccion.showOpenDialog(null);
                if(valor==JFileChooser.APPROVE_OPTION){
                    File carpSelec = seleccion.getSelectedFile();
                    ciudadSeleccionada.setText(carpSelec.getAbsolutePath());
                    sistema.setDireccion(carpSelec.getAbsolutePath());
                    confirmar.setEnabled(true);
                }
                System.setProperty("user.dir",directorioWorkspace);
            }
        });
        
        panelBotones.add(volver);
        panelBotones.add(confirmar);
        ventana.setVisible(true);
    }

}