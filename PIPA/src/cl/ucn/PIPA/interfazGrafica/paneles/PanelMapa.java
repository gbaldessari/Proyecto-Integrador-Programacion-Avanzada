package cl.ucn.PIPA.interfazGrafica.paneles;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.util.LinkedList;

import cl.ucn.PIPA.dominio.Paleta;
import cl.ucn.PIPA.dominio.Punto;
import cl.ucn.PIPA.logica.Sistema;

public class PanelMapa extends JPanel{
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
    private Graphics2D graphics2d;
    private LinkedList<Punto> puntos;
    private Punto selectedPoint = null;
    private ImageIcon imageIcon;
    
    public PanelMapa(Sistema sistema, Paleta paleta){
        this.sistema = sistema;
        puntos = new LinkedList<>();
        mayorX = Double.MIN_VALUE;
        menorX = Double.MAX_VALUE;
        mayorY = Double.MIN_VALUE;
        menorY = Double.MAX_VALUE;
        scale = 1;
        offsetX = -5000;
        offsetY = -50;
        minPosX=-2000;
        maxPosX=14000;
        minPosY=-2000;
        maxPosY=14000;
        // Carga la imagen desde un archivo (ajusta la ruta de acuerdo a tu imagen)
        imageIcon = new ImageIcon("images.jpeg");
        getLimites();
        this.setBackground(paleta.getFondo());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastDragPoint = e.getPoint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                lastDragPoint = null;
            }
            public void mouseClicked(MouseEvent e) {
                Point mousePoint = e.getPoint();
                Point2D.Double mousePointScaled = new Point2D.Double((mousePoint.x - offsetX) / scale, (mousePoint.y - offsetY) / scale);

                // Encuentra el punto más cercano al punto de clic
                double minDistance = Double.MAX_VALUE;
                selectedPoint = null;

                for (Punto punto : puntos) {
                    double distance = calculateDistance(mousePointScaled, punto);
                    if (distance < minDistance) {
                        minDistance = distance;
                        selectedPoint = punto;
                    }
                }

                repaint();
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
                int notches = e.getWheelRotation();
                double scaleFactor = (notches < 0) ? 1.1 : 0.9;  // Ajusta según la dirección del zoom

                // Obtiene la posición del mouse en el sistema de coordenadas no escalado
                Point mouse = e.getPoint();
                Point2D.Double mouseScaled = new Point2D.Double((mouse.x - offsetX) / scale, (mouse.y - offsetY) / scale);
                
                // Ajusta el desplazamiento para que el punto bajo el mouse permanezca fijo
                if(canScale(scale * scaleFactor)){
                    offsetX = (int) (mouse.x - mouseScaled.x * scale);
                    offsetY = (int) (mouse.y - mouseScaled.y * scale);
                    repaint();
                }
            }
        });
    }
    public void paint(Graphics g){
        super.paint(g);
        puntos.clear();
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

        graphics2d.setColor(Color.BLACK);
        graphics2d.setStroke(new BasicStroke(1));
        
        for(int i  =0;i<sistema.getGrafo().getArcos().size();i++){
            graphics2d.drawLine(
                        valorNormalizado(mayorX,menorX,sistema.getGrafo().getArcos().get(i).getOrigen().getX()*-1,true),
                        valorNormalizado(mayorY,menorY,sistema.getGrafo().getArcos().get(i).getOrigen().getY()*-1,false),
                        valorNormalizado(mayorX,menorX,sistema.getGrafo().getArcos().get(i).getDestino().getX()*-1,true),
                        valorNormalizado(mayorY,menorY,sistema.getGrafo().getArcos().get(i).getDestino().getY()*-1,false));
        }
        
        graphics2d.setColor(Color.RED);
        for(int i =0;i<sistema.getGrafo().getNodos().size();i++){
            int x = valorNormalizado(mayorX,menorX,sistema.getGrafo().getNodos().get(i).getX()*-1,true)-1;
            int y = valorNormalizado(mayorY,menorY,sistema.getGrafo().getNodos().get(i).getY()*-1,false)-1;
            graphics2d.fillOval(x,y,2, 2);
            Punto punto = new Punto( new Point(x,y), sistema.getGrafo().getNodos().get(i));
            puntos.add(punto);
        }
        if(selectedPoint!=null){
            graphics2d.setColor(Color.BLUE);
            graphics2d.fillOval(selectedPoint.getPoint().x,selectedPoint.getPoint().y,2, 2);
            System.out.println(selectedPoint.getNodo().getId());
            selectedPoint = null;
        }

        Image image = imageIcon.getImage();
        graphics2d.drawImage(image, maxPosX,maxPosY, this);
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
        double valorfinal = 0;
        if(x){
            valorfinal = (1-(valor-menor)/(mayor-menor))*10000;
        }
        else{
            valorfinal = (valor-menor)/(mayor-menor)*10000;
        }
        return (int)valorfinal;
    }
    public boolean canScale(double newScale) {
        // Limita el zoom mínimo y máximo según tus necesidades
        double minScale = 0.4;
        double maxScale = 15.0;
        if (newScale >= minScale && newScale <= maxScale) {
            scale = newScale;
            return true;
        }
        return false;
    }
    private double calculateDistance(Point2D.Double p1, Punto p2) {
        return p1.distance(p2.getPoint().x, p2.getPoint().y);
    }
}