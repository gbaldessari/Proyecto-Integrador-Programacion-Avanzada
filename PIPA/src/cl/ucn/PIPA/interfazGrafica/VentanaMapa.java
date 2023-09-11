package cl.ucn.PIPA.interfazGrafica;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import cl.ucn.PIPA.logica.Sistema;

public class VentanaMapa extends JFrame {
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    private double mayorX;
    private double menorX;
    private double mayorY;
    private double menorY;
    public VentanaMapa(AdministradorDeVentanas administradorDeVentanas, Sistema sistema){
        setTitle("Mapa");
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        setSize(1000,10000);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mayorX = Double.MIN_VALUE;
        menorX = Double.MAX_VALUE;
        mayorY = Double.MIN_VALUE;
        menorY = Double.MAX_VALUE;
        getLimites();
		iniciarComponentes();
    }
    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        JButton botonMenu = new JButton("Volver al menu");
		botonMenu.setBounds(0, 0, 140, 25);
		panel.add(botonMenu);
		
		botonMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.menu(administradorDeVentanas);
                setVisible(false);
            }
        });
    }
    public void paint(Graphics g){
        super.paint(g);
        //Para poder modificar más propiedades con Graphics 2d
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2));
        //Línea
        for(int i  =0;i<sistema.getGrafo().getArcos().size();i++){ 
            g2d.drawLine(valorNormalizado(mayorX,menorX,sistema.getGrafo().getArcos().get(i).getOrigen().getX()*-1),
                        valorNormalizado(mayorY,menorY,sistema.getGrafo().getArcos().get(i).getOrigen().getY()*-1), 
                        valorNormalizado(mayorX,menorX,sistema.getGrafo().getArcos().get(i).getDestino().getX()*-1), 
                        valorNormalizado(mayorY,menorY,sistema.getGrafo().getArcos().get(i).getDestino().getY()*-1));
        }
        g2d.setColor(Color.BLUE);
        for(int i =0;i<sistema.getGrafo().getNodos().size();i++){
            g2d.fillOval(valorNormalizado(mayorX,menorX,sistema.getGrafo().getNodos().get(i).getX()*-1),
                        valorNormalizado(mayorY,menorY,sistema.getGrafo().getNodos().get(i).getY()*-1), 
                        3, 3);
        }
    }
    private void getLimites(){
        for(int i=0;i<sistema.getGrafo().getNodos().size();i++){
            if(sistema.getGrafo().getNodos().get(i).getX()*-1>mayorX){
                mayorX = sistema.getGrafo().getNodos().get(i).getX()*-1;
            }
            if(sistema.getGrafo().getNodos().get(i).getY()*-1>mayorY){
                mayorY = sistema.getGrafo().getNodos().get(i).getY()*-1;
            }
            if(sistema.getGrafo().getNodos().get(i).getX()*-1<menorX){
                menorX = sistema.getGrafo().getNodos().get(i).getX()*-1;
            }
            if(sistema.getGrafo().getNodos().get(i).getY()*-1<menorY){
                menorY = sistema.getGrafo().getNodos().get(i).getY()*-1;
            }
        }
    }
    private int valorNormalizado(double mayor,double menor,double valor){
        double v = (valor-menor)/(mayor-menor)*900+50;
        return (int)v;
    }
}