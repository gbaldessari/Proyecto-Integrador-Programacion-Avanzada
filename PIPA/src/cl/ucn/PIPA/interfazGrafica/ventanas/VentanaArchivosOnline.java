package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
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
import cl.ucn.PIPA.utils.Utils;
import cl.ucn.PIPA.utils.CiudadesProvider.Ciudad;

/**
 * Clase que representa la ventana para seleccionar archivos y cargar datos desde XML.
 */
public class VentanaArchivosOnline implements Ventana{
    // Atributos de la clase
    private AdministradorDeVentanas administradorDeVentanas;  // Administrador de ventanas
    private Sistema sistema;  // Sistema
    private Tema tema;  // Tema para la interfaz gráfica
    private JFrame ventana;  // Ventana principal
    private Thread hiloArchivo;  // Hilo para cargar datos desde archivos XML
    private CiudadesProvider provider;
    private String[] ciudades;

    /**
     * Constructor de la clase VentanaArchivosOnline.
     *
     * @param administradorDeVentanas Instancia de AdministradorDeVentanas.
     * @param sistema                Instancia del sistema.
     * @param tema                   Tema para la interfaz gráfica.
     */
    public VentanaArchivosOnline(AdministradorDeVentanas administradorDeVentanas, Sistema sistema, Tema tema){
        // Inicialización de atributos
        this.ventana = new JFrame("Seleccionar archivos");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.ventana.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                administradorDeVentanas.ventanaCierre(ventana);
                ventana.setEnabled(false);
            }
        });
        this.tema = tema;
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        ventana.setSize(300, 200);
        ventana.setMaximumSize(new Dimension(300, 200));
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);

        provider = CiudadesProvider.instance();
        ArrayList<String> listaCiudades = new ArrayList<>();
        try {
            for (String ciudad : provider.list()) {
                listaCiudades.add(ciudad);
            }
            ciudades = new String[listaCiudades.size()];
            for (int i = 0;i< listaCiudades.size();i++) {
                ciudades[i] = listaCiudades.get(i);
            }
        } catch (IOException e) {
            administradorDeVentanas.menu(administradorDeVentanas);
            ventana.setVisible(false);
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
        titulo.setForeground(tema.getLetra());
        titulo.setBounds(30, 0, 250, 50);
        panel.add(titulo);

        JComboBox<String> seleccionArchivo = new JComboBox<>(ciudades);
        seleccionArchivo.setBackground(tema.getBoton());
        seleccionArchivo.setForeground(tema.getLetra());
		seleccionArchivo.setBounds(40, 50, 140, 25);
		panel.add(seleccionArchivo);

        JPanel panelBotones = new JPanel(null);
        panelBotones.setBackground(tema.getUi());
        panelBotones.setPreferredSize(new Dimension(this.ventana.getWidth(), 30));
        ventana.getContentPane().add(panelBotones, BorderLayout.SOUTH);

        JButton botonVolver = new JButton("Volver");
        botonVolver.setBounds(30, 5, 100, 20);
        botonVolver.setBackground(tema.getBoton());
        botonVolver.setForeground(tema.getLetra());
        panelBotones.add(botonVolver);
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.menu(administradorDeVentanas);
                ventana.setVisible(false);
            }
        });

        JButton botonConfirmar = new JButton("Confirmar");
        botonConfirmar.setBounds(160, 5, 100, 20);
        botonConfirmar.setBackground(tema.getBoton());
        botonConfirmar.setForeground(tema.getLetra());
        panelBotones.add(botonConfirmar);
        botonConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
                    }
                });
                hiloArchivo.start();
            }
        });
        ventana.setVisible(true);
    }

    /**
     * Método para leer los datos desde un archivo XML (nodos y arcos).
     */
    private void leerXML(String ciudadSeleccionada) {
        try {
            Ciudad ciudad = provider.ciudad(ciudadSeleccionada);
            
            Document documento = Utils.convertStringBuilderToDocument(ciudad.getXmlNodes());
            Element raiz = documento.getDocumentElement();
            NodeList datos = raiz.getElementsByTagName("row");
            guardarNodos(datos);

            documento = Utils.convertStringBuilderToDocument(ciudad.getXmlEdges());
            raiz = documento.getDocumentElement();
            datos = raiz.getElementsByTagName("edge");
            guardarArcos(datos);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Método para guardar nodos a partir de los datos del archivo XML.
     *
     * @param nodos NodeList con los nodos del archivo XML.
     */
    private void guardarNodos(NodeList nodos) {
        for (int i = 0; i < nodos.getLength(); i++) {
            Element nodo = (Element) nodos.item(i);
            String id = nodo.getElementsByTagName("osmid").item(0).getTextContent();
            double posX = Double.parseDouble(nodo.getElementsByTagName("x").item(0).getTextContent());
            double posY = Double.parseDouble(nodo.getElementsByTagName("y").item(0).getTextContent());
            sistema.getGrafo().addNodo(id, posX, posY);
        }
    }

    /**
     * Método para guardar arcos a partir de los datos del archivo XML.
     *
     * @param arcos NodeList con los arcos del archivo XML.
     */
    private void guardarArcos(NodeList arcos) {
        Set<String> carreteras = new HashSet<>(0);
        for (int i = 0; i < arcos.getLength(); i++) {
            Element arco = (Element) arcos.item(i);
            String nombre = arco.getElementsByTagName("name").item(0).getTextContent();
            String id = arco.getElementsByTagName("osmid").item(0).getTextContent();
            String tipo = null;
            if (arco.getElementsByTagName("highway").item(0) != null) {
                tipo = arco.getElementsByTagName("highway").item(0).getTextContent();
            }
            String origen = arco.getElementsByTagName("u").item(0).getTextContent();
            String destino = arco.getElementsByTagName("v").item(0).getTextContent();
            ArrayList<String> listaId = obtenerListaDeLinea(id);
            ArrayList<String> listaNombre = obtenerListaDeLinea(nombre);
            ArrayList<String> listaTipo = obtenerListaDeLinea(tipo);
            sistema.getGrafo().addArco(listaId, listaNombre, listaTipo, origen, destino);
            if (tipo != null) guardarTipoCarretera(listaTipo, carreteras);
        }
        sistema.getTiposCarreteras().clear();
        for (String t : carreteras) {
            sistema.getTiposCarreteras().add(t);
        }
    }

    /**
     * Método para obtener una lista de elementos a partir de una línea de texto.
     *
     * @param linea Línea de texto que contiene los elementos.
     * @return Lista de elementos obtenidos.
     */
    private ArrayList<String> obtenerListaDeLinea(String linea) {
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
    private void guardarTipoCarretera(ArrayList<String> tipo, Set<String> carreteras) {
        for (String t : tipo) {
            carreteras.add(t);
        }
    }
}