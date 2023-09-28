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
import javax.swing.JLabel;
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
    private Punto puntoPartida;
    private Punto puntoDestino;
    private ImageIcon imageIcon;
    private Paleta paleta;
    private JLabel c1;
    private JLabel c2;
    
    public PanelMapa(Sistema sistema, Paleta paleta){
        this.sistema = sistema;
        this.paleta = paleta;
        puntos = new LinkedList<>();
        puntoPartida = null;
        puntoDestino = null;
        mayorX = Double.MIN_VALUE;
        menorX = Double.MAX_VALUE;
        mayorY = Double.MIN_VALUE;
        menorY = Double.MAX_VALUE;
        getLimites();
        scale = 0.1;
        offsetX = -5000;
        offsetY = -50;
        minPosX=-20000;
        maxPosX=140000;
        minPosY=-20000;
        maxPosY=140000;
        // Carga la imagen desde un archivo (ajusta la ruta de acuerdo a tu imagen)
        imageIcon = new ImageIcon("images.jpeg");
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
                Punto p = null;

                for (Punto punto : puntos) {
                    double distance = calculateDistance(mousePointScaled, punto);
                    if (distance < minDistance) {
                        minDistance = distance;
                        p = punto;
                    }
                }

                if(puntoPartida!=null&&p.getNodo().getId().equals(puntoPartida.getNodo().getId())){
                    puntoPartida = puntoDestino;
                    puntoDestino = null;
                    
                }
                else if(puntoDestino!=null&&p.getNodo().getId().equals(puntoDestino.getNodo().getId())){
                    puntoDestino = null;
                }
                else if(puntoPartida != null){
                    puntoDestino = p;
                }
                else{
                    puntoPartida = p;
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
    
    public void setC1(JLabel c1){this.c1=c1;}
    public void setC2(JLabel c2){this.c2=c2;}

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

        graphics2d.setColor(paleta.getLineas());
        graphics2d.setStroke(new BasicStroke(1));
        
        for(int i  =0;i<sistema.getGrafo().getArcos().size();i++){
            graphics2d.drawLine(
                        valorNormalizado(mayorX,menorX,sistema.getGrafo().getArcos().get(i).getOrigen().getX()*-1,true),
                        valorNormalizado(mayorY,menorY,sistema.getGrafo().getArcos().get(i).getOrigen().getY()*-1,false),
                        valorNormalizado(mayorX,menorX,sistema.getGrafo().getArcos().get(i).getDestino().getX()*-1,true),
                        valorNormalizado(mayorY,menorY,sistema.getGrafo().getArcos().get(i).getDestino().getY()*-1,false));
        }
        
        graphics2d.setColor(paleta.getPuntos());
        for(int i =0;i<sistema.getGrafo().getNodos().size();i++){
            int x = valorNormalizado(mayorX,menorX,sistema.getGrafo().getNodos().get(i).getX()*-1,true)-1;
            int y = valorNormalizado(mayorY,menorY,sistema.getGrafo().getNodos().get(i).getY()*-1,false)-1;
            graphics2d.fillOval(x,y,2, 2);
            Punto punto = new Punto( new Point(x,y), sistema.getGrafo().getNodos().get(i));
            puntos.add(punto);
        }
        if(puntoPartida!=null){
            graphics2d.setColor(paleta.getPuntoSeleccionado());
            graphics2d.fillOval(puntoPartida.getPoint().x,puntoPartida.getPoint().y,2, 2);
            this.c1.setText(puntoPartida.getNodo().getX()+", "+puntoPartida.getNodo().getY());
        }
        else{
            this.c1.setText("");
        }
        if(puntoDestino!=null){
            graphics2d.setColor(paleta.getPuntoSeleccionado());
            graphics2d.fillOval(puntoDestino.getPoint().x,puntoDestino.getPoint().y,2, 2);
            this.c2.setText(puntoDestino.getNodo().getX()+", "+puntoDestino.getNodo().getY());
        }
        else{
            this.c2.setText("");
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
            valorfinal = (1-(valor-menor)/(mayor-menor))*100000;
        }
        else{
            valorfinal = (valor-menor)/(mayor-menor)*100000;
        }
        return (int)valorfinal;
    }
    public boolean canScale(double newScale) {
        // Limita el zoom mínimo y máximo según tus necesidades
        double minScale = 0.025;
        double maxScale = 15;
        if (newScale >= minScale && newScale <= maxScale) {
            scale = newScale;
            return true;
        }
        return false;
    }
    private double calculateDistance(Point2D.Double p1, Punto p2) {
        return p1.distance(p2.getPoint().x, p2.getPoint().y);
    }
    public String[] getDatoNodoOrigen(){
        String [] datos = new String[3];
        if(puntoPartida != null){
            datos[0] = puntoPartida.getNodo().getId();
            datos[1] = Double.toString(puntoPartida.getNodo().getX());
            datos[2] = Double.toString(puntoPartida.getNodo().getY());
        }
        return datos;
    } 
}