package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.logica.Sistema;
import cl.ucn.PIPA.utils.Funciones;

/**
 * Clase que representa la ventana para seleccionar
 * archivos y cargar datos desde XML.
 */
public class VentanaArchivosLocal implements Ventana {
    /**
     * Administrador de ventanas.
     */
    private AdministradorDeVentanas administradorDeVentanas;
    /**
     * Sistema.
     */
    private Sistema sistema;
    /**
     * Tema para la interfaz gráfica.
     */
    private Tema tema;
    /**
     * Ventana principal.
     */
    private JFrame ventana;
    /**
     * Hilo para cargar datos desde archivos XML.
     */
    private Thread hiloArchivo;
    /**
     * Barra de progreso para mostrar el progreso de la carga.
     */
    private JProgressBar barraProgreso;
    /**
     * Progreso de la carga.
     */
    private int progreso;
    /**
     * Dirección de la carpeta seleccionada.
     */
    private String direccion;

    /**
     * Constructor de la clase VentanaArchivosLocal.
     *
     * @param administradorDeVentanasEntregado Instancia de
     * AdministradorDeVentanas.
     * @param sistemaEntregado Instancia del sistema.
     * @param temaEntregado Tema para la interfaz gráfica.
     */
    public VentanaArchivosLocal(
    final AdministradorDeVentanas administradorDeVentanasEntregado,
    final Sistema sistemaEntregado, final Tema temaEntregado) {
        this.ventana = new JFrame("Seleccionar archivos");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.ventana.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent we) {
                administradorDeVentanas.ventanaCierre(ventana);
                ventana.setEnabled(false);
            }
        });
        direccion = "";
        tema = temaEntregado;
        administradorDeVentanas = administradorDeVentanasEntregado;
        sistema = sistemaEntregado;
        final Dimension dim = new Dimension(300, 150);
        ventana.setSize(dim);
        ventana.setMaximumSize(dim);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
    }

    /**
     * Método para iniciar la ventana y mostrarla al usuario.
     */
    public void iniciarVentana() {
        // Creación y configuración de paneles y componentes de la ventana
        JPanel panel = new JPanel(null);
        panel.setBackground(tema.getFondo());
        ventana.getContentPane().add(panel, BorderLayout.CENTER);

        JPanel panelBarra = new JPanel();
        panelBarra.setBackground(tema.getFondo());
        ventana.getContentPane().add(panelBarra, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(null);
        panelBotones.setBackground(tema.getUi());
        final Dimension dimPanelBotones = new Dimension(
        this.ventana.getWidth(), 30);
        panelBotones.setPreferredSize(dimPanelBotones);
        ventana.getContentPane().add(panelBotones, BorderLayout.SOUTH);

        JButton botonVolver = new JButton("Volver");
        final Rectangle rectVolver = new Rectangle(30, 5, 100, 20);
        botonVolver.setBounds(rectVolver);
        botonVolver.setBackground(tema.getBoton());
        botonVolver.setForeground(tema.getTexto());
        panelBotones.add(botonVolver);
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                administradorDeVentanas.menu(administradorDeVentanas);
                ventana.setVisible(false);
                ventana.dispose();
            }
        });

        JButton botonConfirmar = new JButton("Confirmar");
        final Rectangle rectConfirmar = new Rectangle(160, 5, 100, 20);
        botonConfirmar.setBounds(rectConfirmar);
        botonConfirmar.setBackground(tema.getBoton());
        botonConfirmar.setForeground(tema.getTexto());
        panelBotones.add(botonConfirmar);
        botonConfirmar.setEnabled(false);

        JLabel carpeta = new JLabel("Seleccione una ciudad: ");
        carpeta.setForeground(tema.getTexto());
        final Rectangle rectCarpeta = new Rectangle(20, 0, 250, 50);
        carpeta.setBounds(rectCarpeta);
        panel.add(carpeta);

        JLabel ciudadSeleccionada = new JLabel("");
        ciudadSeleccionada.setForeground(tema.getTexto());
        final Rectangle rectCiudad = new Rectangle(20, 35, 250, 50);
        ciudadSeleccionada.setBounds(rectCiudad);
        panel.add(ciudadSeleccionada);

        JButton botonSeleccionar = new JButton("Seleccionar");
        botonSeleccionar.setBackground(tema.getBoton());
        botonSeleccionar.setForeground(tema.getTexto());
        final Rectangle rectSeleccionar = new Rectangle(160, 15, 110, 20);
        botonSeleccionar.setBounds(rectSeleccionar);
        panel.add(botonSeleccionar);
        botonSeleccionar.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                String carpetaInicio = "ciudades";
                String directorioWorkspace = System.getProperty("user.dir");
                String rutaCarpetaInicio = directorioWorkspace
                + File.separator + carpetaInicio;
                System.setProperty("user.dir", rutaCarpetaInicio);
                JFileChooser seleccion = new JFileChooser(
                System.getProperty("user.dir"));
                seleccion.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int valor = seleccion.showOpenDialog(null);
                if (valor == JFileChooser.APPROVE_OPTION) {
                    File carpSelec = seleccion.getSelectedFile();
                    ciudadSeleccionada.setText("Ciudad seleccionada: "
                    + getNombreCarpeta(carpSelec.getAbsolutePath()));
                    direccion = carpSelec.getAbsolutePath();
                    botonConfirmar.setEnabled(true);
                }
                System.setProperty("user.dir", directorioWorkspace);
            }

            private String getNombreCarpeta(final String ruta) {
                String[] lista = ruta.split("\\\\");
                return lista[lista.length - 1];
            }
        });
        botonConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                botonConfirmar.setEnabled(false);
                botonVolver.setEnabled(false);
                botonSeleccionar.setEnabled(false);
                administradorDeVentanas.vaciarLista();
                barraProgreso = new JProgressBar(0, obtenerLineasTotales());
                barraProgreso.setBackground(tema.getFondo());
                barraProgreso.setForeground(tema.getPuntos());
                barraProgreso.setStringPainted(true);
                final Rectangle rectBarra = new Rectangle(0, 0, 300, 32);
                barraProgreso.setBounds(rectBarra);
                panelBarra.add(barraProgreso);
                hiloArchivo = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (direccion.equals("")) {
                            administradorDeVentanas.mostrarError(
                            "Carpeta no encontrada");
                        } else {
                            leerXML(true);
                            leerXML(false);
                            administradorDeVentanas.menu(
                            administradorDeVentanas);
                            ventana.setVisible(false);
                            ventana.dispose();
                        }
                    }
                });
                hiloArchivo.start();
            }
        });
        ventana.setVisible(true);
    }

    /**
     * Método para leer los datos desde un archivo XML (nodos o arcos).
     *
     * @param nodo Verdadero si se están leyendo nodos,
     * falso si se están leyendo arcos.
     */
    private void leerXML(final boolean nodo) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();

            String archivo;
            String nombre;

            if (nodo) {
                archivo = direccion + "/nodes.xml";
                nombre = "row";
            } else {
                archivo = direccion + "/edges.xml";
                nombre = "edge";
            }

            // Leer el contenido del archivo como cadena
            String xmlContent = new String(
            Files.readAllBytes(Paths.get(archivo)));

            // Corregir las entidades problemáticas
            xmlContent = Funciones.escapeXML(xmlContent);

            // Crear un nuevo InputStream con el contenido corregido
            InputStream inputStream = new ByteArrayInputStream(
            xmlContent.getBytes(StandardCharsets.UTF_8));

            // Parsear el documento
            Document documento = builder.parse(inputStream);
            Element raiz = documento.getDocumentElement();
            NodeList datos = raiz.getElementsByTagName(nombre);

            if (nodo) {
                guardarNodos(datos);
            } else {
                guardarArcos(datos);
            }
            inputStream.close();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para guardar nodos a partir de los datos del archivo XML.
     *
     * @param nodos NodeList con los nodos del archivo XML.
     */
    private void guardarNodos(final NodeList nodos) {
        for (int i = 0; i < nodos.getLength(); i++) {
            Element nodo = (Element) nodos.item(i);
            String id = nodo.getElementsByTagName(
            "osmid").item(0).getTextContent();
            double posX = Double.parseDouble(
            nodo.getElementsByTagName("x").item(0).getTextContent());
            double posY = Double.parseDouble(
            nodo.getElementsByTagName("y").item(0).getTextContent());
            sistema.getGrafo().addNodo(id, posX, posY);
            progreso++;
            barraProgreso.setValue(progreso);
        }
    }

    /**
     * Método para guardar arcos a partir de los datos del archivo XML.
     *
     * @param arcos NodeList con los arcos del archivo XML.
     */
    private void guardarArcos(final NodeList arcos) {
        Set<String> carreteras = new HashSet<>(0);
        for (int i = 0; i < arcos.getLength(); i++) {
            Element arco = (Element) arcos.item(i);
            String nombre = arco.getElementsByTagName(
            "name").item(0).getTextContent();
            String id = arco.getElementsByTagName(
            "osmid").item(0).getTextContent();
            String tipo = null;
            if (arco.getElementsByTagName("highway").item(0) != null) {
                tipo = arco.getElementsByTagName(
                "highway").item(0).getTextContent();
            }
            String origen = arco.getElementsByTagName(
            "u").item(0).getTextContent();
            String destino = arco.getElementsByTagName(
            "v").item(0).getTextContent();
            ArrayList<String> listaId = obtenerListaDeLinea(id);
            ArrayList<String> listaNombre = obtenerListaDeLinea(nombre);
            ArrayList<String> listaTipo = obtenerListaDeLinea(tipo);
            sistema.getGrafo().addArco(listaId,
            listaNombre, listaTipo, origen, destino);
            progreso++;
            barraProgreso.setValue(progreso);
            if (tipo != null) {
                guardarTipoCarretera(listaTipo, carreteras);
            }
        }
        sistema.getTiposCarreteras().clear();
        for (String t : carreteras) {
            sistema.getTiposCarreteras().add(t);
        }
    }

    /**
     * Método para obtener el número total de líneas
     * en los archivos XML a cargar.
     *
     * @return Número total de líneas.
     */
    private int obtenerLineasTotales() {
        int lineas = 0;
        try {
            lineas += contarLineas(direccion + "/nodes.xml");
            lineas += contarLineas(direccion + "/edges.xml");
        } catch (IOException e) {
            administradorDeVentanas.mostrarError(
            "Error al contar líneas en archivos XML.");
        }
        return lineas;
    }

    /**
     * Método para contar el número de líneas en un archivo.
     *
     * @param archivo Ruta del archivo a contar líneas.
     * @return Número de líneas en el archivo.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private int contarLineas(final String archivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(
            new FileReader(archivo))) {
            int lineas = 0;
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Verificar si la línea contiene un elemento <edge> o <row>
                if (linea.contains("<edge>") || linea.contains("<row>")) {
                    lineas++;
                }
            }
            return lineas;
        }
    }

    /**
     * Método para obtener una lista de elementos
     * a partir de una línea de texto.
     *
     * @param linea Línea de texto que contiene los elementos.
     * @return Lista de elementos obtenidos.
     */
    private ArrayList<String> obtenerListaDeLinea(final String linea) {
        if (linea != null) {
            int inicio = linea.indexOf("[") + 1;
            int fin = linea.indexOf("]");
            if (fin < inicio) {
                fin = linea.length();
            }
            String[] elementos = linea.substring(inicio, fin).split(", ");
            ArrayList<String> lista = new ArrayList<>();
            for (String elemento : elementos) {
                lista.add(elemento.replace("'", ""));
            }
            return lista;
        }
        return null;
    }

    /**
     * Método para guardar los tipos de carreteras en un conjunto.
     *
     * @param tipo       Lista de tipos de carretera.
     * @param carreteras Conjunto para almacenar los tipos de carretera únicos.
     */
    private void guardarTipoCarretera(final ArrayList<String> tipo,
    final Set<String> carreteras) {
        for (String t : tipo) {
            carreteras.add(t);
        }
    }
}
