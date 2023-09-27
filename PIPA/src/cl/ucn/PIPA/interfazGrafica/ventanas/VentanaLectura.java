package cl.ucn.PIPA.interfazGrafica.ventanas;
import cl.ucn.PIPA.dominio.Paleta;
import cl.ucn.PIPA.dominio.Panel;
import cl.ucn.PIPA.logica.Sistema;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class VentanaLectura implements Ventana{
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    private boolean leyendo;
    private Thread hiloArchivo;
    private JButton botonInicio;
    private JProgressBar barraProgreso;
    private int progreso;
    private JFrame ventana;
    private Panel panel;
    private Paleta paleta;
    
    public VentanaLectura(AdministradorDeVentanas administradorDeVentanas, Sistema sistema, JFrame ventana, Paleta paleta){
        this.ventana = ventana;
        this.paleta = paleta;
        this.ventana.setTitle("Lectura de archivo");
        this.panel = new Panel("lectura");
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        progreso = 0;
        
        ventana.setSize(300,140);
        ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
    }
    public void iniciarVentana() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(paleta.getFondo());
        ventana.getContentPane().add(panel, BorderLayout.CENTER);

        barraProgreso = new JProgressBar(0, obtenerLineasTotales());
        barraProgreso.setBackground(paleta.getFondo());
        barraProgreso.setForeground(paleta.getPuntos());
        barraProgreso.setStringPainted(true);
        barraProgreso.setBounds(0, 0, 300, 25);
        panel.add(barraProgreso);

        botonInicio = new JButton("Iniciar Lectura");
        botonInicio.setBackground(paleta.getBoton());
        botonInicio.setForeground(paleta.getLetra());
        botonInicio.setBounds(70, 40, 140, 25);
        panel.add(botonInicio);
        botonInicio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!leyendo) {
                    leyendo = true;
                    botonInicio.setEnabled(false);
                    hiloArchivo = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            leerXML(true);
                            leerXML(false);
                            administradorDeVentanas.limpiarVentana(ventana);   
                            administradorDeVentanas.menu(administradorDeVentanas);
                            //ventana.setVisible(false);
                        }
                    });
                    hiloArchivo.start();
                }
            }
        });
        /* 
        JButton botonVolver = new JButton("Volver");
        botonVolver.setBounds(70,70,140,25);
        panel.add(botonVolver);
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                leyendo = false;
                try {
                    // Esperar a que el hilo termine antes de continuar
                    hiloArchivo.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                administradorDeVentanas.vaciarLista();
                administradorDeVentanas.limpiarVentana(ventana);
                administradorDeVentanas.ventanaPrueba(administradorDeVentanas);
            }
        });
        */
        this.panel.getPaneles().add(panel);
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
                archivo = "nodes.xml";
                nombre = "row";
            }
            else{
                archivo = "edges.xml";
                nombre = "edge";
            }
            Document documento = builder.parse(archivo);
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
    
    private int obtenerLineasTotales(){
        int lineas = 0;
        try (LineNumberReader reader = new LineNumberReader(new FileReader("nodes.xml"))) {
            reader.skip(Long.MAX_VALUE); // Saltar al final del archivo
            lineas = (reader.getLineNumber()-3)/8; // El número de líneas es el número de línea actual más 1
        } catch (IOException e) {
            administradorDeVentanas.mostrarError("No se encontro el archivo 'nodes.xml'");
            System.exit(0);
        }
        try (LineNumberReader reader = new LineNumberReader(new FileReader("edges.xml"))) {
            reader.skip(Long.MAX_VALUE); // Saltar al final del archivo
            lineas += (reader.getLineNumber()-3)/7; // El número de líneas es el número de línea actual más 1
        } catch (IOException e) {
            administradorDeVentanas.mostrarError("No se encontro el archivo 'edges.xml'");
            System.exit(0);
        }
        return lineas;
    }

    public Panel getPanel() {
        return this.panel;
    }
}