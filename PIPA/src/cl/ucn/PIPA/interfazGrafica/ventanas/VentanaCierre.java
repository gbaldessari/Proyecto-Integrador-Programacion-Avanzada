package cl.ucn.PIPA.interfazGrafica.ventanas;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaCierre implements Ventana{
    private JFrame ventana;
    private JFrame ventanaActiva;

    public VentanaCierre(JFrame ventanaActiva) {
        this.ventanaActiva = ventanaActiva;
        this.ventana = new JFrame("Cerra aplicación");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.ventana.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
                ventanaActiva.setEnabled(true);
				ventana.setVisible(false);
			}
		});
        ventana.setSize(300,150);
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
    }

    public void iniciarVentana(){
        JPanel panel = new JPanel();
		panel.setLayout(null);
		ventana.getContentPane().add(panel,BorderLayout.CENTER);

        JButton cerrar = new JButton("Cerrar");
		cerrar.setBounds(30, 50, 100, 25);
		panel.add(cerrar);

        cerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(160, 50, 100, 25);
		panel.add(cancelar);

        cancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ventanaActiva.setEnabled(true);
                ventana.setVisible(false);
            }
        });

        JLabel texto = new JLabel("¿Desea cerrar la aplicación?");
        texto.setBounds(60,20,170,25);
        panel.add(texto);

        ventana.setVisible(true);
    }

}