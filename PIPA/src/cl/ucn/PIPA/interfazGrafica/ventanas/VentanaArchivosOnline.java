package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.logica.Sistema;
import cl.ucn.PIPA.utils.CiudadesProvider;
import cl.ucn.PIPA.utils.Funciones;
import cl.ucn.PIPA.utils.CiudadesProvider.Ciudad;

/**
 * Clase que representa la ventana para
 * seleccionar archivos y cargar datos desde XML.
 */
public class VentanaArchivosOnline implements Ventana {
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
     * Proveedor de ciudades.
     */
    private CiudadesProvider provider;
    /**
     * Lista de ciudades.
     */
    private String[] ciudades;

    /**
     * Constructor de la clase VentanaArchivosOnline.
     *
     * @param administradorDeVentanasEntregado Instancia de
     * AdministradorDeVentanas.
     * @param sistemaEntregado Instancia del sistema.
     * @param temaEntregado Tema para la interfaz gráfica.
     */
    public VentanaArchivosOnline(
    final AdministradorDeVentanas administradorDeVentanasEntregado,
    final Sistema sistemaEntregado, final Tema temaEntregado) {
        // Inicialización de atributos
        this.ventana = new JFrame("Seleccionar archivos");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.ventana.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent we) {
                administradorDeVentanas.ventanaCierre(ventana);
                ventana.setEnabled(false);
            }
        });
        tema = temaEntregado;
        administradorDeVentanas = administradorDeVentanasEntregado;
        sistema = sistemaEntregado;
        final Dimension dim = new Dimension(300, 200);
        ventana.setSize(dim);
        ventana.setMaximumSize(dim);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);

        provider = CiudadesProvider.instance();
        ArrayList<String> listaCiudades = new ArrayList<>();

        if (provider.list() != null) {
            administradorDeVentanas.setConexionInternet(true);
            for (String ciudad : provider.list()) {
                listaCiudades.add(ciudad);
            }
            ciudades = new String[listaCiudades.size()];
            for (int i = 0; i < listaCiudades.size(); i++) {
                ciudades[i] = listaCiudades.get(i);
            }
        } else {
            administradorDeVentanas.mostrarError("No hay conexion a internet");
            administradorDeVentanas.menu(administradorDeVentanas);
            ventana.setVisible(false);
            ventana.dispose();
        }
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

        JLabel titulo = new JLabel("Seleccione una ciudad");
        titulo.setForeground(tema.getTexto());
        final Rectangle rectTitulo = new Rectangle(80, 0, 250, 50);
        titulo.setBounds(rectTitulo);
        panel.add(titulo);

        JComboBox<String> seleccionArchivo = new JComboBox<>(ciudades);
        seleccionArchivo.setBackground(tema.getBoton());
        seleccionArchivo.setForeground(tema.getTexto());
        final Rectangle rectArchivo = new Rectangle(75, 50, 140, 25);
        seleccionArchivo.setBounds(rectArchivo);
        panel.add(seleccionArchivo);

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
        botonConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                botonConfirmar.setEnabled(false);
                botonVolver.setEnabled(false);
                seleccionArchivo.setEnabled(false);
                administradorDeVentanas.vaciarLista();

                hiloArchivo = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        leerXML(ciudades[seleccionArchivo.getSelectedIndex()]);
                        administradorDeVentanas.menu(administradorDeVentanas);
                        ventana.setVisible(false);
                        ventana.dispose();
                    }
                });
                hiloArchivo.start();
            }
        });
        ventana.setVisible(true);
    }

    /**
     * Método para leer los datos desde un archivo XML (nodos y arcos).
     *
     * @param ciudadSeleccionada nombre de la ciudad de la
     * cual se obtendra el archivo
     * @return si se puedo guardar los datos
     */
    private boolean leerXML(final String ciudadSeleccionada) {
        Ciudad ciudad = provider.ciudad(ciudadSeleccionada);
        if (ciudad == null) {
            return false;
        }

        Document documento = Funciones.convertStringBuilderToDocument(
        ciudad.getXmlNodes());
        Element raiz = documento.getDocumentElement();
        NodeList datos = raiz.getElementsByTagName("row");
        guardarNodos(datos);

        documento = Funciones.convertStringBuilderToDocument(
        ciudad.getXmlEdges());
        raiz = documento.getDocumentElement();
        datos = raiz.getElementsByTagName("edge");
        guardarArcos(datos);
        return true;
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
