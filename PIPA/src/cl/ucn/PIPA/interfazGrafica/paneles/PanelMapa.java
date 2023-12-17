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
import cl.ucn.PIPA.utils.Funciones;

/**
 * Panel gráfico que muestra un mapa con nodos y arcos geográficos.
 */
public class PanelMapa extends JPanel {
    /** Valor mínimo de escala permitido. */
    private final double escalaMinima = 0.025;

    /** Valor máximo de escala permitido. */
    private final double escalaMaxima = 3;

    /** Sistema que contiene el grafo. */
    private Sistema sistema;

    /** Diferencia entre las coordenadas máximas y mínimas. */
    private double deltaCords;

    /** Punto máximo en el mapa. */
    private Point2D maxPoint;

    /** Punto mínimo en el mapa. */
    private Point2D minPoint;

    /** Coordenada X máxima. */
    private double maxX;

    /** Coordenada Y máxima. */
    private double maxY;

    /** Coordenada X mínima. */
    private double minX;

    /** Coordenada Y mínima. */
    private double minY;

    /** Factor de escala actual. */
    private double scale;

    /** Desplazamiento horizontal. */
    private int offsetX;

    /** Desplazamiento vertical. */
    private int offsetY;

    /** Ancho visible del panel. */
    private double visibleWidth;

    /** Altura visible del panel. */
    private double visibleHeight;

    /** Altura del panel. */
    private int panelHeight;

    /** Ancho del panel. */
    private int panelWidth;

    /** Posición X visible. */
    private double visibleX;

    /** Posición Y visible. */
    private double visibleY;

    /** Último punto de arrastre del mouse. */
    private Point lastDragPoint;

    /** Objeto Graphics2D para dibujar. */
    private Graphics2D graphics2d;

    /** Lista de puntos en el mapa. */
    private ArrayList<Punto> puntos;

    /** Lista de líneas en el mapa. */
    private ArrayList<Linea> lineas;

    /** Ruta entre los puntos seleccionados. */
    private ArrayList<Line2D> ruta;

    /** Punto de partida para la ruta. */
    private Punto puntoPartida;

    /** Punto de destino para la ruta. */
    private Punto puntoDestino;

    /** Distancia total recorrida en la ruta. */
    private double distanciaRecorrida;

    /** Icono de imagen a mostrar en el mapa. */
    private ImageIcon imageIcon;

    /** Tema de apariencia para el panel. */
    private Tema tema;

    /** Etiqueta para la coordenada 1. */
    private JLabel c1;

    /** Etiqueta para la coordenada 2. */
    private JLabel c2;

    /** Etiqueta para el id 1. */
    private JLabel id1;

    /** Identificador del nodo 1. */
    private String identificador1;

    /** Etiqueta para el id 2. */
    private JLabel id2;

    /** Identificador del nodo 2. */
    private String identificador2;

    /** Etiqueta para mostrar la distancia en kilómetros. */
    private JLabel km;

    /** Factor de escalado para la distancia. */
    private double escalador;

    /** Ancho por defecto de las lineas del mapa. */
    private final int anchoPorDefecto = 11;

    /** Factor de inclinacion del mapa. */
    private double factorInclinacion = 1;

    /** Maximo factor de inclinacion del mapa. */
    private final double maxFactorInclinacion = 1;

    /** Minimo factor de inclinacion del mapa. */
    private final double minFactorInclinacion = 0.3;

    /** Último punto de inclinacion del mouse. */
    private Point lastInclinationPoint;

    /**
     * Constructor del panel de mapa.
     *
     * @param sistemaEntregado El sistema que contiene el grafo.
     * @param temaEntregado El tema de apariencia para el panel.
     */
    public PanelMapa(final Sistema sistemaEntregado,
    final Tema temaEntregado) {
        sistema = sistemaEntregado; tema = temaEntregado;
        puntos = new ArrayList<>(); lineas = new ArrayList<>();
        puntoPartida = null; puntoDestino = null;
        scale = escalaMinima; offsetX = 0; offsetY = 0;
        imageIcon = new ImageIcon("images.jpeg");
        this.setBackground(tema.getFondo()); getLimites();
        final int cteDeEscalacion = 1420;
        escalador = Funciones.haversine(minPoint.getY(), minPoint.getX(),
        maxPoint.getY(), maxPoint.getX()) * cteDeEscalacion;
        getPuntos(); getLineas();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    lastDragPoint = e.getPoint();
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    lastInclinationPoint = e.getPoint();
                }
            }
            @Override
            public void mouseReleased(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    lastDragPoint = null;
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    lastInclinationPoint = null;
                }
            }
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Point mousePoint = e.getPoint();
                    Point2D.Double mousePointScaled
                    = scaleMousePoint(mousePoint);
                    Punto p = findNearestPoint(mousePointScaled);
                    handlePointSelection(p); repaint();
                }
            }
            private Point2D.Double scaleMousePoint(final Point mousePoint) {
                return new Point2D.Double(
                (mousePoint.x - offsetX) / scale,
                (mousePoint.y - offsetY) / scale);
            }
            private Punto findNearestPoint(
            final Point2D.Double mousePointScaled) {
                double minDistance = Double.MAX_VALUE;
                Punto nearestPoint = null;
                for (Punto punto : puntos) {
                    double distance = calculateDistance(
                    mousePointScaled, punto);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearestPoint = punto;
                    }
                }
                return nearestPoint;
            }
            private void handlePointSelection(final Punto selectedPoint) {
                if (puntoPartida != null && puntoDestino != null) {
                    handleBothPointsSelected(selectedPoint);
                } else if (puntoDestino != null) {
                    handleDestinoSelected(selectedPoint);
                } else if (puntoPartida != null) {
                    handlePartidaSelected(selectedPoint);
                } else {
                    puntoPartida = selectedPoint;
                }
            }
            private void handleBothPointsSelected(final Punto selectedPoint) {
                if (selectedPoint != null && isSameNode(
                selectedPoint, puntoPartida)) {
                    swapPartidaAndDestino();
                }
            }
            private void handleDestinoSelected(final Punto selectedPoint) {
                if (selectedPoint != null
                && isSameNode(selectedPoint, puntoDestino)) {
                    puntoDestino = null;
                }
            }
            private void handlePartidaSelected(final Punto selectedPoint) {
                puntoDestino = selectedPoint;
            }
            private void swapPartidaAndDestino() {
                Punto temp = puntoPartida; puntoPartida = puntoDestino;
                puntoDestino = temp;
            }
            private boolean isSameNode(final Punto point1, final Punto point2) {
                return point1.getNodo().getId().equals(
                point2.getNodo().getId());
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(final MouseEvent e) {
                if (lastDragPoint != null && e.getModifiersEx()
                == MouseEvent.BUTTON1_DOWN_MASK) {
                    handleLeftButtonDrag(e);
                }
                if (lastInclinationPoint != null && e.getModifiersEx()
                == MouseEvent.BUTTON3_DOWN_MASK) {
                    handleRightButtonDrag(e);
                }
            }
            private void handleLeftButtonDrag(final MouseEvent e) {
                int dx = e.getX() - lastDragPoint.x;
                int dy = e.getY() - lastDragPoint.y;
                offsetX += dx; offsetY += dy;
                lastDragPoint = e.getPoint(); repaint();
            }
            private void handleRightButtonDrag(final MouseEvent e) {
                Double dy = (double) (e.getY() - lastInclinationPoint.y);
                final double proporcion = 0.001;
                if (!canIncline(factorInclinacion - dy * proporcion)) {
                    dy = 0.0;
                }
                offsetY += dy; lastInclinationPoint = e.getPoint();
                repaint();
            }
        });
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(final MouseWheelEvent e) {
                final double maxScaleFactor = 1.1;
                final double minScaleFactor = 0.9;
                int notches = e.getWheelRotation();
                double scaleFactor = (notches < 0)
                ? maxScaleFactor : minScaleFactor;
                Point mouse = e.getPoint();
                Point2D.Double mouseScaled = new Point2D.Double(
                (mouse.x - offsetX) / scale,
                (mouse.y - offsetY) / scale);
                if (canScale(scale * scaleFactor)) {
                    offsetX = (int) (mouse.x - mouseScaled.x * scale);
                    offsetY = (int) (mouse.y - mouseScaled.y * scale);
                    repaint();
                }
            }
        });
    }

    /**
     * Método de dibujo principal que representa el contenido del panel.
     *
     * @param g El objeto Graphics para dibujar.
     */
    public void paint(final Graphics g) {
        super.paint(g); graphics2d = (Graphics2D) g;
        graphics2d.translate(offsetX, offsetY);
        graphics2d.scale(scale, scale);
        panelWidth = getWidth(); panelHeight = getHeight();
        visibleWidth = (panelWidth / scale);
        visibleHeight = (panelHeight / scale);
        visibleX = (-offsetX / scale);
        visibleY = (-offsetY / scale);
        drawLines(); drawRoute();
        drawSelectedPoints(); drawImage();
        graphics2d.dispose();
    }

    private void drawLines() {
        for (Linea linea : lineas) {
            Point2D starPoint = new Point2D.Double(linea.getLine().getX1(),
            linea.getLine().getY1() * factorInclinacion);
            Point2D endPoint = new Point2D.Double(linea.getLine().getX2(),
            linea.getLine().getY2() * factorInclinacion);
            Line2D newLine = new Line2D.Double(starPoint, endPoint);
            if (inLimitesLine(newLine)) {
                Color colorLinea = getColorLinea(linea);
                graphics2d.setColor(colorLinea);
                graphics2d.setStroke(new BasicStroke(anchoPorDefecto));
                graphics2d.draw(newLine);
            }
        }
    }

    private Color getColorLinea(final Linea linea) {
        if (linea.getArco().getTipos() != null) {
            return sistema.getColoresCalles().get(getIndexColor(
            linea.getArco().getTipos().get(0)));
        }
        return Color.decode("#606060");
    }

    private void drawRoute() {
        if (ruta != null) {
            final int cteDeEscalacionLineaRuta = 8;
            if (distanciaRecorrida < 1) {
                final int cteTransformacionKm = 1000;
                km.setText(String.format("%." + 2 + "f",
                distanciaRecorrida * cteTransformacionKm) + " m");
            } else {
                km.setText(String.format("%." + (2 + 1) + "f",
                distanciaRecorrida) + " km");
            }
            for (Line2D linea : ruta) {
                Point2D starPoint = new Point2D.Double(linea.getX1(),
                linea.getY1() * factorInclinacion);
                Point2D endPoint = new Point2D.Double(linea.getX2(),
                linea.getY2() * factorInclinacion);
                Line2D newLine = new Line2D.Double(starPoint, endPoint);
                if (inLimitesLine(newLine)) {
                    graphics2d.setColor(Color.decode("#FF0000"));
                    graphics2d.setStroke(new BasicStroke(
                    (int) (cteDeEscalacionLineaRuta / scale)));
                    graphics2d.draw(newLine);
                }
            }
        }
    }

    private void drawSelectedPoints() {
        identificador1 = drawSelectedPoint(puntoPartida, id1, c1);
        identificador2 = drawSelectedPoint(puntoDestino, id2, c2);
    }

    private String drawSelectedPoint(final Punto punto,
    final JLabel idLabel, final JLabel cLabel) {
        final int diametroPuntos = 11;
        if (punto != null) {
            graphics2d.setColor(tema.getPuntoSeleccionado());
            graphics2d.fillOval((int) (punto.getPoint().getX()
            - (diametroPuntos / scale) / 2), (int) ((punto.getPoint().getY()
            - (diametroPuntos / scale) / 2) * factorInclinacion),
            (int) (diametroPuntos / scale),
            (int) ((diametroPuntos / scale) * factorInclinacion));
            idLabel.setText("ID: " + punto.getNodo().getId());
            cLabel.setText(punto.getNodo().getX()
            + ", " + punto.getNodo().getY());
            return punto.getNodo().getId();
        } else {
            idLabel.setText(""); cLabel.setText("");
        }
        return "";
    }

    private void drawImage() {
        final int posImagenXY = -15000;
        Image image = imageIcon.getImage();
        graphics2d.drawImage(image, posImagenXY,
        (int) (posImagenXY * factorInclinacion), this);
    }

    private int getIndexColor(final String tipo) {
        int index = 0;
        int numColores = sistema.getTiposCarreteras().size() - 1;
        for (int i = 0; i <= numColores; i++) {
            if (tipo.equals(sistema.getTiposCarreteras().get(i))) {
                index = i;
                break;
            }
        }
        while (index > numColores) {
            index = index - numColores;
        }
        return index;
    }

    private boolean inLimitesLine(final Line2D linea) {
        Rectangle2D rect = new Rectangle2D.Double(
        visibleX, visibleY, visibleWidth, visibleHeight);
        if (rect.intersectsLine(linea)) {
            return true;
        }
        return false;
    }

    private int valorNormalizado(final double valor, final boolean x) {
        double valorfinal = 0;
        if (x) {
            valorfinal = (1 - (valor - minX) / (deltaCords)) * escalador;
        } else {
            valorfinal = (valor - minY) / (deltaCords) * escalador;
        }
        return (int) valorfinal;
    }

    /**
     * Limita el zoom dentro de ciertos rangos.
     *
     * @param newScale La nueva escala propuesta.
     * @return true si el zoom es válido y se ha aplicado,
     * false si no es válido.
     */
    public boolean canScale(final double newScale) {
        if (newScale >= escalaMinima && newScale <= escalaMaxima) {
            scale = newScale; return true;
        }
        return false;
    }

    /**
     * Limita la inclinacion dentro de ciertos rangos.
     *
     * @param newInclination La nueva inclinacion propuesta.
     * @return true si la inclinacion es válida y se ha aplicado,
     * false si no es válida.
     */
    public boolean canIncline(final double newInclination) {
        if (newInclination >= minFactorInclinacion
        && newInclination <= maxFactorInclinacion) {
            factorInclinacion = newInclination;
            return true;
        }
        return false;
    }

    private double calculateDistance(final Point2D.Double p1, final Punto p2) {
        return p1.distance(p2.getPoint().getX(), p2.getPoint().getY());
    }

    private void getPuntos() {
        ArrayList<Punto> listaPuntos = new ArrayList<>();
        ArrayList<Double> lista = new ArrayList<>();
        for (Nodo nodo : sistema.getGrafo().getNodos()) {
            Punto punto = crearPuntoNormalizado(nodo);
            actualizarLimites(punto, lista);
            listaPuntos.add(punto);
        }
        ajustarOffsets(lista.get(0), lista.get(1),
        lista.get(2), lista.get(2 + 1));
        puntos = listaPuntos;
    }

    private Punto crearPuntoNormalizado(final Nodo nodo) {
        double x = valorNormalizado(nodo.getX() * -1, true) - 2;
        double y = valorNormalizado(nodo.getY() * -1, false) - 2;
        return new Punto(new Point2D.Double(x, y), nodo);
    }

    private void actualizarLimites(final Punto punto,
    final ArrayList<Double> lista) {
        double mayX = Double.MIN_VALUE;
        double menX = Double.MAX_VALUE;
        double mayY = Double.MIN_VALUE;
        double menY = Double.MAX_VALUE;
        if (punto.getPoint().getX() > mayX) {
            mayX = punto.getPoint().getX();
        }
        if (punto.getPoint().getY() > mayY) {
            mayY = punto.getPoint().getY();
        }
        if (punto.getPoint().getX() < menX) {
            menX = punto.getPoint().getX();
        }
        if (punto.getPoint().getY() < menY) {
            menY = punto.getPoint().getY();
        }
        lista.add(mayX); lista.add(menX);
        lista.add(mayY); lista.add(menY);
    }

    private void ajustarOffsets(final double mayX, final double menX,
    final double mayY, final double menY) {
        final double medioX = 944 / 2;
        final double medioY = 625 / 2;
        offsetX = (int) ((-(mayX + menX) / 2) * scale + medioX);
        offsetY = (int) ((-(mayY + menY) / 2) * scale + medioY);
    }

    private void getLineas() {
        for (int i = 0; i < sistema.getGrafo().getArcos().size(); i++) {
            Arco arco = sistema.getGrafo().getArcos().get(i);
            Linea linea = new Linea(new Line2D.Double(
                valorNormalizado(arco.getOrigen().getX() * -1, true),
                valorNormalizado(arco.getOrigen().getY() * -1, false),
                valorNormalizado(arco.getDestino().getX() * -1, true),
                valorNormalizado(arco.getDestino().getY() * -1, false)), arco);
            lineas.add(linea);
        }
    }

    private void getLimites() {
        calcularLimitesNodos();
        calcularDeltaCords();
    }

    private void calcularLimitesNodos() {
        maxX = Double.MIN_VALUE; minX = Double.MAX_VALUE;
        maxY = Double.MIN_VALUE; minY = Double.MAX_VALUE;
        for (Nodo nodo : sistema.getGrafo().getNodos()) {
            actualizarLimitesNodo(nodo);
        }
        double deltaX = Math.abs(maxX - minX);
        double deltaY = Math.abs(maxY - minY);
        maxPoint = new Point2D.Double(minX * -1, minY * -1);
        minPoint = new Point2D.Double(maxX * -1, maxY * -1);
        deltaCords = (deltaX > deltaY) ? deltaX : deltaY;
    }

    private void actualizarLimitesNodo(final Nodo nodo) {
        double x = nodo.getX() * -1;
        double y = nodo.getY() * -1;
        if (x > maxX) {
            maxX = x;
        }
        if (y > maxY) {
            maxY = y;
        }
        if (x < minX) {
            minX = x;
        }
        if (y < minY) {
            minY = y;
        }
    }

    private void calcularDeltaCords() {
        double deltaX = Math.abs(maxX - minX);
        double deltaY = Math.abs(maxY - minY);
        deltaCords = (deltaX > deltaY) ? deltaX : deltaY;
    }

    /**
     * Funcion que actualiza la lista que contiene
     * el camino mas corto entre los 2 puntos.
     */
    public final void caminoMasCorto() {
        ArrayList<Nodo> lista = sistema.getGrafo().encontrarCaminoMasCorto(
        identificador1, identificador2);
        distanciaRecorrida = 0;
        ruta = new ArrayList<>();
        for (int i = 0; i < lista.size() - 1; i++) {
            Line2D linea = new Line2D.Double(
                valorNormalizado(lista.get(i).getX() * -1, true),
                valorNormalizado(lista.get(i).getY() * -1, false),
                valorNormalizado(lista.get(i + 1).getX() * -1, true),
                valorNormalizado(lista.get(i + 1).getY() * -1, false));
            ruta.add(linea);
            distanciaRecorrida += Funciones.haversine(lista.get(i).getY(),
            lista.get(i).getX(), lista.get(i + 1).getY(),
            lista.get(i + 1).getX());
        }
        repaint();
    }

    /**
     * Establece la etiqueta para mostrar la coordenada 1.
     *
     * @param c1Label La etiqueta para la coordenada 1.
     */
    public void setC1(final JLabel c1Label) {
        c1 = c1Label;
    }

    /**
     * Establece la etiqueta para mostrar la coordenada 2.
     *
     * @param c2Label La etiqueta para la coordenada 1.
     */
    public void setC2(final JLabel c2Label) {
        c2 = c2Label;
    }

    /**
     * Establece la etiqueta para mostrar el id 1.
     *
     * @param id1Label La etiqueta para el id 1.
     */
    public void setid1(final JLabel id1Label) {
        id1 = id1Label;
    }

    /**
     * Establece la etiqueta para mostrar el id 2.
     *
     * @param id2Label La etiqueta para el id 2.
     */
    public void setid2(final JLabel id2Label) {
        id2 = id2Label;
    }

    /**
     * Establece la etiqueta para mostrar los kilometros.
     *
     * @param kmLabel La etiqueta para los kilometros.
     */
    public void setKm(final JLabel kmLabel) {
        km = kmLabel;
    }

    /**
     * Borra los puntos de origen y destino.
     */
    public void borrarOrigenDestino() {
        puntoPartida = null; puntoDestino = null;
        ruta = null; repaint();
    }

    /**
     * Funcion para obtener el id del nodo de inicio.
     *
     * @return El id del nodo de inicio
     */
    public final String getIdentificador1() {
        return identificador1;
    }

    /**
     * Funcion para obtener el id del nodo de destino.
     *
     * @return El id del nodo de destino
     */
    public final String getIdentificador2() {
        return identificador2;
    }
}
