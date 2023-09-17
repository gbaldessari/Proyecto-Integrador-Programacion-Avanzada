package cl.ucn.PIPA.interfazGrafica.paneles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import cl.ucn.PIPA.logica.Sistema;

public class PanelMapa extends JPanel{
    private Sistema sistema;
    private double mayorX;
    private double menorX;
    private double mayorY;
    private double menorY;
    private double scale = 1;
    private int offsetX = -5000;
    private int offsetY = -50;
    private int minPosX;
    private int maxPosX;
    private int minPosY;
    private int maxPosY;
    private Point lastDragPoint;
    private Graphics2D graphics2d;
    
    public PanelMapa(Sistema sistema){
        this.sistema = sistema;
        mayorX = Double.MIN_VALUE;
        menorX = Double.MAX_VALUE;
        mayorY = Double.MIN_VALUE;
        menorY = Double.MAX_VALUE;
        minPosX=-2000;
        maxPosX=14000;
        minPosY=-2000;
        maxPosY=14000;
        getLimites();

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
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                // Calcula el factor de escala según el movimiento de la rueda del mouse
                double scaleFactor = e.getWheelRotation() < 0 ? 1.1 : 1 / 1.1;

                // Calcula la nueva escala
                double newScale = scale * scaleFactor;
                boolean canScale = setScale(newScale);
                if(canScale){
                    // Ajusta el desplazamiento para que el centro de la ventana permanezca centrado
                    int deltaX = (int) ((getWidth() / 2) * (1 - 1 / scaleFactor));
                    int deltaY = (int) ((getHeight() / 2) * (1 - 1 / scaleFactor));
                    offsetX -= deltaX;
                    offsetY -= deltaY;
                    repaint();
                }
            }
        });
    }
    public void paint(Graphics g){
        super.paint(g);
        
        //Para poder modificar más propiedades con Graphics 2d
        graphics2d = (Graphics2D) g;
        graphics2d.translate(offsetX, offsetY);
        graphics2d.scale(scale, scale);

        graphics2d.setColor(Color.BLACK);
        graphics2d.setStroke(new BasicStroke(5));
        graphics2d.drawLine(maxPosX,maxPosY,maxPosX,minPosY);
        graphics2d.drawLine(maxPosX,maxPosY,minPosX,maxPosY);
        graphics2d.drawLine(minPosX,minPosY,minPosX,maxPosY);
        graphics2d.drawLine(minPosX,minPosY,maxPosX,minPosY);

        graphics2d.setColor(Color.RED);
        graphics2d.setStroke(new BasicStroke(1));
        
        for(int i  =0;i<sistema.getGrafo().getArcos().size();i++){
            graphics2d.drawLine(
                        valorNormalizado(mayorX,menorX,sistema.getGrafo().getArcos().get(i).getOrigen().getX()*-1,true),
                        valorNormalizado(mayorY,menorY,sistema.getGrafo().getArcos().get(i).getOrigen().getY()*-1,false),
                        valorNormalizado(mayorX,menorX,sistema.getGrafo().getArcos().get(i).getDestino().getX()*-1,true),
                        valorNormalizado(mayorY,menorY,sistema.getGrafo().getArcos().get(i).getDestino().getY()*-1,false));
        }
        
        graphics2d.setColor(Color.BLUE);
        for(int i =0;i<sistema.getGrafo().getNodos().size();i++){
            graphics2d.fillOval(
                        valorNormalizado(mayorX,menorX,sistema.getGrafo().getNodos().get(i).getX()*-1,true)-1,
                        valorNormalizado(mayorY,menorY,sistema.getGrafo().getNodos().get(i).getY()*-1,false)-1,
                        2, 2);
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
    private int valorNormalizado(double mayor,double menor,double valor,boolean x){
        double v = 0;
        if(x){
            v = (1-(valor-menor)/(mayor-menor))*10000;
        }
        else{
            v = (valor-menor)/(mayor-menor)*10000;
        }
        return (int)v;
    }
    public boolean setScale(double newScale) {
        // Limita el zoom mínimo y máximo según tus necesidades
        double minScale = 0.5;
        double maxScale = 3.0;
        if (newScale >= minScale && newScale <= maxScale) {
            scale = newScale;
            repaint();
            return true;
        }
        return false;
    }
}