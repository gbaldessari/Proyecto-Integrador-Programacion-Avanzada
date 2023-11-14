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
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JLabel;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.dominio.Arco;
import cl.ucn.PIPA.dominio.Linea;
import cl.ucn.PIPA.dominio.Nodo;
import cl.ucn.PIPA.dominio.Punto;
import cl.ucn.PIPA.logica.Sistema;
import cl.ucn.PIPA.utils.Utils;

/**
 * Panel gráfico que muestra un mapa con nodos y arcos geográficos.
 */
public class PanelMapa extends JPanel{
    private Sistema sistema;
    private double deltaCords;
    private Point2D maxPoint;
    private Point2D minPoint;
    private double minX;
    private double minY;
    private double scale;
    private int offsetX;
    private int offsetY;
    double visibleWidth;
    double visibleHeight;
    double visibleX;
    double visibleY;
    private Point lastDragPoint;
    private Graphics2D graphics2d;
    private ArrayList<Punto> puntos;
    private ArrayList<Linea> lineas;
    private Punto puntoPartida;
    private Punto puntoDestino;
    private ImageIcon imageIcon;
    private Tema tema;
    private JLabel c1;
    private JLabel c2;
    private JLabel id1;
    private JLabel id2;
    private JLabel km;
    private double escalador;

    /**
     * Constructor del panel de mapa.
     *
     * @param sistema El sistema que contiene el grafo.
     * @param tema    El tema de apariencia para el panel.
     */
    public PanelMapa(Sistema sistema, Tema tema){
        this.sistema = sistema;
        this.tema = tema;
        puntos = new ArrayList<>();
        lineas = new ArrayList<>();
        puntoPartida = null;
        puntoDestino = null;
        offsetX = 0;
        offsetY = 0;
        scale = 0.025;
        imageIcon = new ImageIcon("images.jpeg");
        this.setBackground(tema.getFondo());
        getLimites();
        escalador = Utils.haversine(minPoint.getY(), minPoint.getX(), maxPoint.getY(), maxPoint.getX())*1420;
        getPuntos();
        getLineas();
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
    /**
     * Establece la etiqueta para mostrar la coordenada 1.
     *
     * @param c1 La etiqueta para la coordenada 1.
     */
    public void setC1(JLabel c1){this.c1=c1;}
    /**
     * Establece la etiqueta para mostrar la coordenada 2.
     *
     * @param c2 La etiqueta para la coordenada 1.
     */
    public void setC2(JLabel c2){this.c2=c2;}
    /**
     * Establece la etiqueta para mostrar el id 1.
     *
     * @param id1 La etiqueta para el id 1.
     */
    public void setid1(JLabel id1){this.id1=id1;}
    /**
     * Establece la etiqueta para mostrar el id 2.
     *
     * @param id2 La etiqueta para el id 2.
     */
    public void setid2(JLabel id2){this.id2=id2;}
    /**
     * Establece la etiqueta para mostrar los kilometros.
     *
     * @param km La etiqueta para los kilometros.
     */
    public void setKm(JLabel km){this.km=km;}
    /**
     * Borra los puntos de origen y destino.
     */
    public void borrarOrigenDestino(){
        puntoPartida = null;
        puntoDestino = null;
        repaint();
    }
    /**
     * Método de dibujo principal que representa el contenido del panel.
     *
     * @param g El objeto Graphics para dibujar.
     */
    public void paint(Graphics g){
        super.paint(g);
        graphics2d = (Graphics2D) g;
        graphics2d.translate(offsetX, offsetY);
        graphics2d.scale(scale, scale);
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        visibleWidth = (panelWidth / scale);
        visibleHeight = (panelHeight / scale);
        visibleX = (-offsetX / scale);
        visibleY = (-offsetY / scale);
        for (Linea linea : lineas) {
            if(inLimitesLine(linea.getLine())){
                Color colorLinea = Color.decode("#606060");
                if(linea.getArco().getTipo()!=null){
                    colorLinea= sistema.getColoresCalles().get(getIndexColor(linea.getArco().getTipo().get(0)));
                }
                graphics2d.setColor(colorLinea);
                graphics2d.setStroke(new BasicStroke(1));
                graphics2d.draw(linea.getLine());
            }
        }
        // Dibuja solo los puntos que están dentro de los límites visibles
        if(scale>0.75){
            for (Punto punto : puntos) {
                int x = (int) punto.getPoint().getX();
                int y = (int) punto.getPoint().getY();
                if(inLimitesPoint(x,y)){
                    graphics2d.setColor(tema.getPuntos());
                    graphics2d.fillOval(x,y,4, 4);
                }
            }
        }
        if(puntoPartida!=null){
            graphics2d.setColor(tema.getPuntoSeleccionado());
            graphics2d.fillOval((int)puntoPartida.getPoint().getX(),(int)puntoPartida.getPoint().getY(),4, 4);
            this.id1.setText("ID: "+puntoPartida.getNodo().getId());
            this.c1.setText(puntoPartida.getNodo().getX()+", "+puntoPartida.getNodo().getY());
        }
        else{
            this.c1.setText("");
            this.id1.setText("");
        }
        if(puntoDestino!=null){
            graphics2d.setColor(tema.getPuntoSeleccionado());
            graphics2d.fillOval((int)puntoDestino.getPoint().getX(),(int)puntoDestino.getPoint().getY(),4, 4);
            this.id2.setText("ID: "+puntoDestino.getNodo().getId());
            this.c2.setText(puntoDestino.getNodo().getX()+", "+puntoDestino.getNodo().getY());
            double kilometros = Utils.haversine(puntoPartida.getNodo().getY(),puntoPartida.getNodo().getX(),puntoDestino.getNodo().getY(),puntoDestino.getNodo().getX());
            if(kilometros<1){
                this.km.setText(String.format("%." + 2 + "f",kilometros*1000) + " m");
            }else{
                this.km.setText(String.format("%." + 3 + "f",kilometros) + " km");
            }
        }
        else{
            this.c2.setText("");
            this.id2.setText("");
            this.km.setText("");
        }
        Image image = imageIcon.getImage();
        graphics2d.drawImage(image, -2000,-2000, this);
        graphics2d.dispose();
    }
    /**
     * Obtiene el índice del color asociado a un tipo de carretera.
     *
     * @param tipo El tipo de carretera.
     * @return El índice del color asociado.
     */
    private int getIndexColor(String tipo) {
        int index = 0;
        int numColores = sistema.getTiposCarreteras().size()-1;
        for (int i = 0; i<=numColores;i++) {
            if(tipo.equals(sistema.getTiposCarreteras().get(i))){
                index = i;
                break;
            }
        }
        while(index>numColores){
            index=index-numColores;
        }
        return index;
    }
    /**
     * Verifica si un punto está dentro de los límites visibles.
     *
     * @param x La coordenada x del punto.
     * @param y La coordenada y del punto.
     * @return true si el punto está dentro de los límites visibles, false de lo contrario.
     */
    private boolean inLimitesPoint(int x, int y){
        Rectangle2D rect = new Rectangle2D.Double(visibleX-10, visibleY-10, visibleWidth+20, visibleHeight+20);
        if(rect.contains(x,y)){
            return true;
        }
        return false;
    }
    /**
     * Verifica si una línea está dentro de los límites visibles.
     *
     * @param linea La línea a verificar.
     * @return true si la línea está dentro de los límites visibles, false de lo contrario.
     */
    private boolean inLimitesLine(Line2D linea){
        Rectangle2D rect = new Rectangle2D.Double(visibleX, visibleY, visibleWidth, visibleHeight);
        if(rect.intersectsLine(linea)){
            return true;
        }
        return false;
    }
    /**
     * Normaliza un valor según el rango de coordenadas y la escala.
     *
     * @param valor El valor a normalizar.
     * @param x     true si se está normalizando la coordenada x, false para la coordenada y.
     * @return El valor normalizado.
     */
    private int valorNormalizado(double valor,boolean x){
        double valorfinal = 0;
        if(x){
            valorfinal = (1-(valor-minX)/(deltaCords))*escalador;
        }
        else{
            valorfinal = (valor-minY)/(deltaCords)*escalador;
        }
        return (int)valorfinal;
    }
    /**
     * Limita el zoom dentro de ciertos rangos.
     *
     * @param newScale La nueva escala propuesta.
     * @return true si el zoom es válido y se ha aplicado, false si no es válido.
     */
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
    /**
     * Calcula la distancia entre dos puntos.
     *
     * @param p1 El primer punto.
     * @param p2 El segundo punto.
     * @return La distancia entre los dos puntos.
     */
    private double calculateDistance(Point2D.Double p1, Punto p2) {
        return p1.distance(p2.getPoint().getX(), p2.getPoint().getY());
    }
    /**
     * Obtiene información del nodo de origen.
     *
     * @return Un array de Strings con información del nodo de origen.
     */
    public String[] getDatoNodoOrigen(){
        String [] datos = new String[3];
        if(puntoPartida != null){
            datos[0] = puntoPartida.getNodo().getId();
            datos[1] = Double.toString(puntoPartida.getNodo().getX());
            datos[2] = Double.toString(puntoPartida.getNodo().getY());
        }
        return datos;
    }
    /**
     * Obtiene y normaliza los puntos del grafo.
     */
    private void getPuntos(){
        double mayX = Double.MIN_VALUE;
        double menX = Double.MAX_VALUE;
        double mayY = Double.MIN_VALUE;
        double menY = Double.MAX_VALUE;

        for(int i =0;i<sistema.getGrafo().getNodos().size();i++){
            Nodo nodo = sistema.getGrafo().getNodos().get(i);
            Punto punto = new Punto( new Point(valorNormalizado(nodo.getX()*-1,true)-2,
                                    valorNormalizado(nodo.getY()*-1,false)-2),
                                    nodo);
            if(punto.getPoint().getX()>mayX){
                mayX = punto.getPoint().getX();
            }
            if(punto.getPoint().getY()>mayY){
                mayY = punto.getPoint().getY();
            }
            if(punto.getPoint().getX()<menX){
                menX = punto.getPoint().getX();
            }
            if(punto.getPoint().getY()<menY){
                menY = punto.getPoint().getY();
            }
            puntos.add(punto);
        }
        offsetX = (int)((-(mayX+menX)/2)*scale+944/2);
        offsetY = (int)((-(mayY+menY)/2)*scale+625/2);
    }
    /**
     * Obtiene las líneas del grafo.
     */
    private void getLineas(){
        for(int i  =0;i<sistema.getGrafo().getArcos().size();i++){
            Arco arco = sistema.getGrafo().getArcos().get(i);
            Linea linea = new Linea(new Line2D.Double(
                        valorNormalizado(arco.getOrigen().getX()*-1,true),
                        valorNormalizado(arco.getOrigen().getY()*-1,false),
                        valorNormalizado(arco.getDestino().getX()*-1,true),
                        valorNormalizado(arco.getDestino().getY()*-1,false)), 
                    arco);
            lineas.add(linea);
        }
    }
    /**
     * Calcula los límites del mapa basándose en las coordenadas de los nodos.
     */
    private void getLimites(){
        double maxX = Double.MIN_VALUE;
        minX = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
        minY = Double.MAX_VALUE;

        for(int i=0;i<sistema.getGrafo().getNodos().size();i++){
            Nodo nodo = sistema.getGrafo().getNodos().get(i);
            if(nodo.getX()*-1>maxX){
                maxX = nodo.getX()*-1;
            }
            if(nodo.getY()*-1>maxY){
                maxY = nodo.getY()*-1;
            }
            if(nodo.getX()*-1<minX){
                minX = nodo.getX()*-1;
            }
            if(nodo.getY()*-1<minY){
                minY = nodo.getY()*-1;
            }
        }
        double deltaX = Math.abs(maxX-minX);
        double deltaY = Math.abs(maxY-minY);

        maxPoint = new Point2D.Double(minX*-1, minY*-1);
        minPoint = new Point2D.Double(maxX*-1, maxY*-1);

        if(deltaX>deltaY){
            deltaCords = deltaX;
        }
        else{
            deltaCords = deltaY;
        }
    }
}