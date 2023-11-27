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
    /** */
    private final double escalaMinima = 0.025;
    /** */
    private final double escalaMaxima = 15;
    /** */
    private final int cteDeEscalacion = 1420;
    /** */
    private final double minScaleFactor = 0.9;
    /** */
    private final double maxScaleFactor = 1.1;
    /** */
    private final int cteTransformacionKm = 1000;
    /** */
    private final int cteTruncamientoKm = 3;
    /** */
    private final int cteDeEscalacionLineaRuta = 8;
    /** */
    private final double cteDeEscalacionPuntos = 0.75;
    /** */
    private final int posImagenXY = -15000;
    /** */
    private final int diametroPuntos = 4;
    /** */
    private Sistema sistema;
    /** */
    private double deltaCords;
    /** */
    private Point2D maxPoint;
    /** */
    private Point2D minPoint;
    /** */
    private double minX;
    /** */
    private double minY;
    /** */
    private double scale;
    /** */
    private int offsetX;
    /** */
    private int offsetY;
    /** */
    private double visibleWidth;
    /** */
    private double visibleHeight;
    /** */
    private double visibleX;
    /** */
    private double visibleY;
    /** */
    private Point lastDragPoint;
    /** */
    private Graphics2D graphics2d;
    /** */
    private ArrayList<Punto> puntos;
    /** */
    private ArrayList<Linea> lineas;
    /** */
    private ArrayList<Line2D> ruta;
    /** */
    private Punto puntoPartida;
    /** */
    private Punto puntoDestino;
    /** */
    private double distanciaRecorrida;
    /** */
    private ImageIcon imageIcon;
    /** */
    private Tema tema;
    /** */
    private JLabel c1;
    /** */
    private JLabel c2;
    /** */
    private JLabel id1;
    /** */
    private String identificador1;
    /** */
    private JLabel id2;
    /** */
    private String identificador2;
    /** */
    private JLabel km;
    /** */
    private double escalador;

    /**
     * Constructor del panel de mapa.
     *
     * @param sistemaEntregado El sistema que contiene el grafo.
     * @param temaEntregado El tema de apariencia para el panel.
     */
    public PanelMapa(final Sistema sistemaEntregado, final Tema temaEntregado) {
        sistema = sistemaEntregado;
        tema = temaEntregado;
        puntos = new ArrayList<>();
        lineas = new ArrayList<>();
        puntoPartida = null;
        puntoDestino = null;
        scale = escalaMinima;
        offsetX = 0;
        offsetY = 0;
        imageIcon = new ImageIcon("images.jpeg");
        this.setBackground(tema.getFondo());
        getLimites();
        escalador = Funciones.haversine(minPoint.getY(), minPoint.getX(),
        maxPoint.getY(), maxPoint.getX()) * cteDeEscalacion;
        getPuntos();
        getLineas();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
                lastDragPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
                lastDragPoint = null;
            }

            public void mouseClicked(final MouseEvent e) {
                Point mousePoint = e.getPoint();
                Point2D.Double mousePointScaled = scaleMousePoint(mousePoint);
                Punto p = findNearestPoint(mousePointScaled);
                handlePointSelection(p);
                repaint();
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
                Punto temp = puntoPartida;
                puntoPartida = puntoDestino;
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
            public void mouseWheelMoved(final MouseWheelEvent e) {
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
        puntoPartida = null;
        puntoDestino = null;
        ruta = null;
        repaint();
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

    /**
 * Método de dibujo principal que representa el contenido del panel.
 *
 * @param g El objeto Graphics para dibujar.
 */
public void paint(final Graphics g) {
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

    drawLines();
    drawRoute();
    drawPoints();
    drawSelectedPoints();
    drawImage();

    graphics2d.dispose();
}

private void drawLines() {
    for (Linea linea : lineas) {
        if (inLimitesLine(linea.getLine())) {
            Color colorLinea = getColorLinea(linea);
            graphics2d.setColor(colorLinea);
            graphics2d.setStroke(new BasicStroke(1));
            graphics2d.draw(linea.getLine());
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
        if (distanciaRecorrida < 1) {
            km.setText(String.format("%." + 2 + "f",
            distanciaRecorrida * cteTransformacionKm) + " m");
        } else {
            km.setText(String.format("%." + cteTruncamientoKm + "f",
            distanciaRecorrida) + " km");
        }
        for (Line2D linea : ruta) {
            if (inLimitesLine(linea)) {
                graphics2d.setColor(Color.decode("#FF0000"));
                graphics2d.setStroke(new BasicStroke(
                (int) (cteDeEscalacionLineaRuta / scale)));
                graphics2d.draw(linea);
            }
        }
    }
}

private void drawPoints() {
    if (scale > cteDeEscalacionPuntos) {
        for (Punto punto : puntos) {
            int x = (int) punto.getPoint().getX();
            int y = (int) punto.getPoint().getY();
            if (inLimitesPoint(x, y)) {
                graphics2d.setColor(tema.getPuntos());
                graphics2d.fillOval(x, y, diametroPuntos, diametroPuntos);
            }
        }
    }
}

private void drawSelectedPoints() {
    drawSelectedPoint(puntoPartida, id1, c1);
    drawSelectedPoint(puntoDestino, id2, c2);
}

private void drawSelectedPoint(final Punto punto,
final JLabel idLabel, final JLabel cLabel) {
    if (punto != null) {
        graphics2d.setColor(tema.getPuntoSeleccionado());
        graphics2d.fillOval((int) punto.getPoint().getX(),
        (int) punto.getPoint().getY(), diametroPuntos, diametroPuntos);
        idLabel.setText("ID: " + punto.getNodo().getId());
        cLabel.setText(punto.getNodo().getX() + ", " + punto.getNodo().getY());
    } else {
        idLabel.setText("");
        cLabel.setText("");
    }
}

private void drawImage() {
    Image image = imageIcon.getImage();
    graphics2d.drawImage(image, posImagenXY, posImagenXY, this);
}


    /**
     * Obtiene el índice del color asociado a un tipo de carretera.
     *
     * @param tipo El tipo de carretera.
     * @return El índice del color asociado.
     */
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

    /**
     * Verifica si un punto está dentro de los límites visibles.
     *
     * @param x La coordenada x del punto.
     * @param y La coordenada y del punto.
     * @return true si el punto está dentro de los límites visibles, false de lo
     * contrario.
     */
    private boolean inLimitesPoint(final int x, final int y) {
        final int minPosVisible = 10;
        final int maxPosVisible = 20;
        Rectangle2D rect = new Rectangle2D.Double(
        visibleX - minPosVisible, visibleY - minPosVisible,
        visibleWidth + maxPosVisible, visibleHeight + maxPosVisible);
        if (rect.contains(x, y)) {
            return true;
        }
        return false;
    }

    /**
     * Verifica si una línea está dentro de los límites visibles.
     *
     * @param linea La línea a verificar.
     * @return true si la línea está dentro de los límites visibles, false de lo
     *         contrario.
     */
    private boolean inLimitesLine(final Line2D linea) {
        Rectangle2D rect = new Rectangle2D.Double(
        visibleX, visibleY, visibleWidth, visibleHeight);
        if (rect.intersectsLine(linea)) {
            return true;
        }
        return false;
    }

    /**
     * Normaliza un valor según el rango de coordenadas y la escala.
     *
     * @param valor El valor a normalizar.
     * @param x     true si se está normalizando la coordenada x, false para la
     *              coordenada y.
     * @return El valor normalizado.
     */
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
        // Limita el zoom mínimo y máximo según tus necesidades
        if (newScale >= escalaMinima && newScale <= escalaMaxima) {
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
    private double calculateDistance(final Point2D.Double p1, final Punto p2) {
        return p1.distance(p2.getPoint().getX(), p2.getPoint().getY());
    }
    /**
     * Obtiene y normaliza los puntos del grafo.
     */
    private void getPuntos() {
        double mayX = Double.MIN_VALUE;
        double menX = Double.MAX_VALUE;
        double mayY = Double.MIN_VALUE;
        double menY = Double.MAX_VALUE;

        for (int i = 0; i < sistema.getGrafo().getNodos().size(); i++) {
            Nodo nodo = sistema.getGrafo().getNodos().get(i);
            Punto punto = new Punto(new Point(
            valorNormalizado(nodo.getX() * -1, true) - 2,
            valorNormalizado(nodo.getY() * -1, false) - 2), nodo);
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
            puntos.add(punto);
        }

        final double medioX = 944 / 2;
        final double medioY = 625 / 2;
        offsetX = (int) ((-(mayX + menX) / 2) * scale + medioX);
        offsetY = (int) ((-(mayY + menY) / 2) * scale + medioY);
    }

    /**
     * Obtiene las líneas del grafo.
     */
    private void getLineas() {
        for (int i = 0; i < sistema.getGrafo().getArcos().size(); i++) {
            Arco arco = sistema.getGrafo().getArcos().get(i);
            Linea linea = new Linea(new Line2D.Double(
                    valorNormalizado(arco.getOrigen().getX() * -1, true),
                    valorNormalizado(arco.getOrigen().getY() * -1, false),
                    valorNormalizado(arco.getDestino().getX() * -1, true),
                    valorNormalizado(arco.getDestino().getY() * -1, false)),
                    arco);
            lineas.add(linea);
        }
    }

    /**
     * Calcula los límites del mapa basándose en las coordenadas de los nodos.
     */
    private void getLimites() {
        double maxX = Double.MIN_VALUE;
        minX = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
        minY = Double.MAX_VALUE;

        for (int i = 0; i < sistema.getGrafo().getNodos().size(); i++) {
            Nodo nodo = sistema.getGrafo().getNodos().get(i);
            if (nodo.getX() * -1 > maxX) {
                maxX = nodo.getX() * -1;
            }
            if (nodo.getY() * -1 > maxY) {
                maxY = nodo.getY() * -1;
            }
            if (nodo.getX() * -1 < minX) {
                minX = nodo.getX() * -1;
            }
            if (nodo.getY() * -1 < minY) {
                minY = nodo.getY() * -1;
            }
        }
        double deltaX = Math.abs(maxX - minX);
        double deltaY = Math.abs(maxY - minY);

        maxPoint = new Point2D.Double(minX * -1, minY * -1);
        minPoint = new Point2D.Double(maxX * -1, maxY * -1);

        if (deltaX > deltaY) {
            deltaCords = deltaX;
        } else {
            deltaCords = deltaY;
        }
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
}
