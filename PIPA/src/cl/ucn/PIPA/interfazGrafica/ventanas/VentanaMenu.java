package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.logica.Sistema;
import javax.swing.JButton;
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

/* 
 * Subclase ventana menu
*/
public class VentanaMenu implements Ventana {
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    private JFrame ventana;
    private Tema tema;
    private Thread hiloArchivo;
    private JProgressBar barraProgreso;
    private int progreso;

    /**
     * Constructor de la clase
     * @param administradorDeVentanas, herramienta para inicializar la ventana
     */
    public VentanaMenu(AdministradorDeVentanas administradorDeVentanas, Sistema sistema, Tema tema) {
        this.ventana = new JFrame("Menú");
        this.sistema = sistema;
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.ventana.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
                administradorDeVentanas.ventanaCierre(ventana);
                ventana.setEnabled(false);
			}
		});
        this.tema = tema;
        this.administradorDeVentanas = administradorDeVentanas;
        ventana.setSize(350,260);
        ventana.setMaximumSize(new Dimension(350,260));
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
    }

    public void iniciarVentana() {
        JPanel panel = new JPanel();
        panel.setBackground(tema.getFondo());
		panel.setLayout(null);
		ventana.getContentPane().add(panel,BorderLayout.CENTER);
        
		JLabel mensaje = new JLabel("Menú Principal");
        mensaje.setForeground(tema.getLetra());
		mensaje.setBounds(130, 0, 250, 50);
		panel.add(mensaje);
		
		JButton botonMostrarMapa = new JButton("Ver mapa");
        botonMostrarMapa.setBackground(tema.getBoton());
        botonMostrarMapa.setForeground(tema.getLetra());
		botonMostrarMapa.setBounds(85, 50, 165, 25);
        if(sistema.getGrafo().getNodos().size()==0||sistema.getGrafo().getArcos().size()==0){botonMostrarMapa.setEnabled(false);}
		panel.add(botonMostrarMapa);
		
		botonMostrarMapa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.limpiarVentana(ventana);
                administradorDeVentanas.mostrarMapa(administradorDeVentanas);
                ventana.setVisible(false);
            }
        });

        JButton botonSeleccionTema = new JButton("Cambiar tema");
        botonSeleccionTema.setBackground(tema.getBoton());
        botonSeleccionTema.setForeground(tema.getLetra());
		botonSeleccionTema.setBounds(85, 85, 165, 25);
		panel.add(botonSeleccionTema);
		
		botonSeleccionTema.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.limpiarVentana(ventana);
                administradorDeVentanas.ventanaTema(administradorDeVentanas);
                ventana.setVisible(false);
            }
        });

        JButton cargarArchivos = new JButton("Cargar archivos");
        cargarArchivos.setBackground(tema.getBoton());
        cargarArchivos.setForeground(tema.getLetra());
		cargarArchivos.setBounds(85, 155, 165, 25);
        cargarArchivos.setEnabled(false);
		panel.add(cargarArchivos);
        cargarArchivos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.vaciarLista();
                JPanel panelB = new JPanel();
                panelB.setBackground(tema.getFondo());
                ventana.getContentPane().add(panelB,BorderLayout.SOUTH);
                barraProgreso = new JProgressBar(0, obtenerLineasTotales());
                barraProgreso.setBackground(tema.getFondo());
                barraProgreso.setForeground(tema.getPuntos());
                barraProgreso.setStringPainted(true);
                barraProgreso.setBounds(0, 0, 300, 32);
                panelB.add(barraProgreso);
                hiloArchivo = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mensaje.setText("Cargando...");
                        leerXML(true);
                        leerXML(false);
                        System.out.println(progreso);
                        mensaje.setText("Menú principal");
                        botonMostrarMapa.setEnabled(true);
                        botonSeleccionTema.setEnabled(true);
                    }
                });
                hiloArchivo.start();
            }
        });
        if(sistema.getDireccion()!=""){cargarArchivos.setEnabled(true);}
        JButton seleccionArchivos = new JButton("Seleccionar archivos");
        seleccionArchivos.setBackground(tema.getBoton());
        seleccionArchivos.setForeground(tema.getLetra());
		seleccionArchivos.setBounds(85, 120, 165, 25);
		panel.add(seleccionArchivos);
        seleccionArchivos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.limpiarVentana(ventana);
                administradorDeVentanas.ventanaArchivos(administradorDeVentanas);
                ventana.setVisible(false);
            }
        });

        ventana.setVisible(true);
    }

    public void leerXML(boolean nodo){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try { // Excepcion para abrir el documento xml
            builder = factory.newDocumentBuilder();
            String archivo;
            String nombre;
            if(nodo){
                archivo = sistema.getDireccion()+"/nodes.xml";
                nombre = "row";
            }
            else{
                archivo = sistema.getDireccion()+"/edges.xml";
                nombre = "edge";
            }
            Document documento = builder.parse(new File(archivo));
            Element raiz = documento.getDocumentElement();
            NodeList datos = raiz.getElementsByTagName(nombre);
            if(nodo){guardarNodos(datos);}
            else{guardarArcos(datos);}
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public void guardarNodos(NodeList nodos){
        for (int i = 0;i<nodos.getLength();i++) {
            Element nodo = (Element) nodos.item(i);
            String id = nodo.getElementsByTagName("osmid").item(0).getTextContent();
            double posX = Double.parseDouble(nodo.getElementsByTagName("x").item(0).getTextContent());
            double posY = Double.parseDouble(nodo.getElementsByTagName("y").item(0).getTextContent());
            sistema.getGrafo().addNodo(id, posX, posY);
            progreso++;
            barraProgreso.setValue(progreso);
        }
    }

    public void guardarArcos(NodeList arcos){
        for (int i = 0;i<arcos.getLength();i++) {
            Element arco = (Element) arcos.item(i);
            String nombre = arco.getElementsByTagName("name").item(0).getTextContent();
            String id = arco.getElementsByTagName("u").item(0).getTextContent();
            String origen = arco.getElementsByTagName("u").item(0).getTextContent();
            String destino = arco.getElementsByTagName("v").item(0).getTextContent();
            sistema.getGrafo().addArco(id, nombre, origen, destino);
            progreso++;
            barraProgreso.setValue(progreso);
        }
    }

    private int obtenerLineasTotales() {
        int lineas = 0;
        try {
            // Contar líneas en el archivo nodes.xml
            lineas += contarLineas(sistema.getDireccion() + "/nodes.xml");
            // Contar líneas en el archivo edges.xml
            lineas += contarLineas(sistema.getDireccion() + "/edges.xml");
        } catch (IOException e) {
            administradorDeVentanas.mostrarError("Error al contar líneas en archivos XML.");
        }
        return lineas;
    }

    private int contarLineas(String archivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
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
}