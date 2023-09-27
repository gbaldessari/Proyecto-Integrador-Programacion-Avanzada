package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import cl.ucn.PIPA.dominio.Paleta;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/* 
 * Subclase ventana menu
*/
public class VentanaMenu implements Ventana {
    private AdministradorDeVentanas administradorDeVentanas;
    private JFrame ventana;
    private Paleta paleta;

    /**
     * Constructor de la clase
     * @param administradorDeVentanas, herramienta para inicializar la ventana
     */
    public VentanaMenu(AdministradorDeVentanas administradorDeVentanas, Paleta paleta) {
        this.ventana = new JFrame("Menú");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.ventana.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				cerrar(ventana);
			}
		});
        this.paleta = paleta;
        this.administradorDeVentanas = administradorDeVentanas;
        ventana.setSize(300,150);
        ventana.setMaximumSize(new Dimension(300,150));
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
    }

    public void iniciarVentana() {
        JPanel panel = new JPanel();
        panel.setBackground(paleta.getFondo());
		panel.setLayout(null);
		ventana.getContentPane().add(panel,BorderLayout.CENTER);
		
		JLabel mensaje = new JLabel("Menú principal");
        mensaje.setBackground(paleta.getLetra());
		mensaje.setBounds(100, 0, 250, 50);
		panel.add(mensaje);
		
		JButton botonMostrarMapa = new JButton("Ver mapa");
        botonMostrarMapa.setBackground(paleta.getBoton());
        botonMostrarMapa.setForeground(paleta.getLetra());
		botonMostrarMapa.setBounds(70, 50, 140, 25);
		panel.add(botonMostrarMapa);
		
		botonMostrarMapa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.limpiarVentana(ventana);
                administradorDeVentanas.mostrarMapa(administradorDeVentanas);
                ventana.setVisible(false);
            }
        });
        ventana.setVisible(true);
    }

    private void cerrar(JFrame ventana){
		String [] botones = {"Cerrar", "Cancelar"};
		int eleccion = JOptionPane.showOptionDialog(ventana, "¿Desea cerrar la aplicación", "Confirmar cierre",
		0,JOptionPane.WARNING_MESSAGE,null,botones,ventana);
		if(eleccion==JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}

}