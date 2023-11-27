package cl.ucn.PIPA.interfazGrafica.ventanas;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import cl.ucn.PIPA.dominio.Tema;

/**
 * Clase que representa una ventana para cerrar la aplicación.
 */
public class VentanaCierre implements Ventana {

    private JFrame ventana;
    private JFrame ventanaActiva;
    private Tema tema;

    /**
     * Constructor de la clase VentanaCierre.
     *
     * @param ventanaActiva JFrame de la ventana activa.
     * @param tema         Tema de la ventana.
     */
    public VentanaCierre(final JFrame ventanaActiva, final Tema tema) {
        this.ventanaActiva = ventanaActiva;
        this.tema = tema;
        this.ventana = new JFrame("Cerrar aplicación");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.ventana.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent we) {
                ventanaActiva.setEnabled(true);
                ventana.setVisible(false);
                ventana.dispose();
            }
        });
        ventana.setSize(300, 150);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
    }

    public final void iniciarVentana() {
        JPanel panel = new JPanel();
        panel.setBackground(tema.getFondo());
        panel.setLayout(null);
        ventana.getContentPane().add(panel, BorderLayout.CENTER);

        JLabel texto = new JLabel("¿Desea cerrar la aplicación?");
        texto.setBounds(60, 20, 170, 25);
        texto.setForeground(tema.getTexto());
        panel.add(texto);

        JButton cerrar = new JButton("Cerrar");
        cerrar.setBounds(30, 50, 100, 25);
        cerrar.setBackground(tema.getBoton());
        cerrar.setForeground(tema.getTexto());
        panel.add(cerrar);

        cerrar.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(160, 50, 100, 25);
        cancelar.setBackground(tema.getBoton());
        cancelar.setForeground(tema.getTexto());
        panel.add(cancelar);

        cancelar.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                ventanaActiva.setEnabled(true);
                ventana.setVisible(false);
                ventana.dispose();
            }
        });

        ventana.setVisible(true);
    }
}
