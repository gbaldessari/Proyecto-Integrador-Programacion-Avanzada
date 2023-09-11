package cl.ucn.PIPA.interfazGrafica;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.event.MouseEvent;
import cl.ucn.PIPA.logica.Sistema;

public class VentanaMapa extends JFrame {
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    private double mayorX;
    private double menorX;
    private double mayorY;
    private double menorY;
    private double scale;
    private int offsetX;
    private int offsetY;
    private int minPosX;
    private int maxPosX;
    private int minPosY;
    private int maxPosY;
    private Point lastDragPoint;
    public VentanaMapa(AdministradorDeVentanas administradorDeVentanas, Sistema sistema){
        setTitle("Mapa");
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        setSize(1000,1000);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mayorX = Double.MIN_VALUE;
        menorX = Double.MAX_VALUE;
        mayorY = Double.MIN_VALUE;
        menorY = Double.MAX_VALUE;
        scale = 1;
        offsetX = -1000;
        offsetY = -1000;
        minPosX=-2000;
        maxPosX=7000;
        minPosY=-2000;
        maxPosY=7000;
        getLimites();
		iniciarComponentes();
    }
    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        add(panel);

        JPanel controlPanel = new JPanel();
        JButton zoomInButton = new JButton("+");
        JButton botonMenu = new JButton("Volver");
        JButton zoomOutButton = new JButton("-");
        controlPanel.add(zoomInButton);
        controlPanel.add(botonMenu);
        controlPanel.add(zoomOutButton);
        
        add(controlPanel, BorderLayout.SOUTH);

        zoomInButton.addActionListener(e -> zoomIn());
        zoomOutButton.addActionListener(e -> zoomOut());
		
		botonMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.menu(administradorDeVentanas);
                setVisible(false);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastDragPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lastDragPoint = null;
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (lastDragPoint != null) {
                    int dx = e.getX() - lastDragPoint.x;
                    int dy = e.getY() - lastDragPoint.y;
                    offsetX += dx;
                    offsetY += dy;
                    lastDragPoint = e.getPoint();
                    repaint();
                }
            }
        });
    }
    public void paint(Graphics g){
        super.paint(g);
        //Para poder modificar más propiedades con Graphics 2d
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(offsetX, offsetY);
        g2d.scale(scale, scale);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(maxPosX,maxPosY,maxPosX,minPosY);
        g2d.drawLine(maxPosX,maxPosY,minPosX,maxPosY);
        g2d.drawLine(minPosX,minPosY,minPosX,maxPosY);
        g2d.drawLine(minPosX,minPosY,maxPosX,minPosY);
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(1));
        //Línea
        for(int i  =0;i<sistema.getGrafo().getArcos().size();i++){
            g2d.drawLine(valorNormalizado(mayorX,menorX,sistema.getGrafo().getArcos().get(i).getOrigen().getX()*-1),
                        valorNormalizado(mayorY,menorY,sistema.getGrafo().getArcos().get(i).getOrigen().getY()*-1),
                        valorNormalizado(mayorX,menorX,sistema.getGrafo().getArcos().get(i).getDestino().getX()*-1),
                        valorNormalizado(mayorY,menorY,sistema.getGrafo().getArcos().get(i).getDestino().getY()*-1));
        }
        /*
        g2d.setColor(Color.BLUE);
        for(int i =0;i<sistema.getGrafo().getNodos().size();i++){
            g2d.fillOval(valorNormalizado(mayorX,menorX,sistema.getGrafo().getNodos().get(i).getX()*-1),
                        valorNormalizado(mayorY,menorY,sistema.getGrafo().getNodos().get(i).getY()*-1),
                        3, 3);
        }
        */
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
        double v = (valor-menor)/(mayor-menor)*4500+50;
        return (int)v;
    }
    public void zoomIn() {
        double newScale = scale * 1.1; // Aumenta la escala en un 10%
        
        // Aplica los límites de zoom
        if (newScale > 0.9 && newScale < 100) {
            scale = newScale;
            repaint();
        }
    }
    
    public void zoomOut() {
        double newScale = scale / 1.1; // Disminuye la escala en un 10%
        
        // Aplica los límites de zoom
        if (newScale > 0.9 && newScale < 100) {
            scale = newScale;
            repaint();
        }
    }
}