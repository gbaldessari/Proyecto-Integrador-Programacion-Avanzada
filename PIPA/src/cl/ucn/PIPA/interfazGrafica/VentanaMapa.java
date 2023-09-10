package cl.ucn.PIPA.interfazGrafica;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import cl.ucn.PIPA.logica.Sistema;

public class VentanaMapa extends JFrame {
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    public VentanaMapa(AdministradorDeVentanas administradorDeVentanas, Sistema sistema){
        setTitle("Mapa");
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        setSize(1000,1000);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		iniciarComponentes();
    }
    public void iniciarComponentes() {
        JPanel panel = new JPanel();
        getContentPane().add(panel,BorderLayout.CENTER);
		panel.setLayout(null);
    }
    public void paint(Graphics g){
        super.paint(g);
        //Para poder modificar más propiedades con Graphics 2d
        Graphics2D g2d = (Graphics2D) g;
        
        //Línea
        for(int i  =0;i<sistema.getGrafo().getArcos().size();i++){

            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawLine((int)(sistema.getGrafo().getArcos().get(i).getOrigen().getX()*-10-200),
                        (int)(sistema.getGrafo().getArcos().get(i).getOrigen().getY()*-10-200), 
                        (int)(sistema.getGrafo().getArcos().get(i).getDestino().getX()*-10-200), 
                        (int)(sistema.getGrafo().getArcos().get(i).getDestino().getY()*-10-200));
        }
    } 
}
