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

public class VentanaArchivos implements Ventana{
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    private Tema tema;
    private JFrame ventana;
    private Thread hiloArchivo;
    private JProgressBar barraProgreso;
    private int progreso;

    public VentanaArchivos(AdministradorDeVentanas administradorDeVentanas,Sistema sistema, Tema tema){
        this.ventana = new JFrame("Seleccionar archivos");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.ventana.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
                administradorDeVentanas.ventanaCierre(ventana);
                ventana.setEnabled(false);
			}
		});
        this.tema = tema;
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        ventana.setSize(300,150);
        ventana.setMaximumSize(new Dimension(300,150));
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
    }
    public void iniciarVentana() {
        JPanel panel = new JPanel(null);
        panel.setBackground(tema.getFondo());
        ventana.getContentPane().add(panel,BorderLayout.CENTER);

        JPanel panelB = new JPanel();
        panelB.setBackground(tema.getFondo());
        ventana.getContentPane().add(panelB,BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(null);
        panelBotones.setBackground(tema.getUi());
        panelBotones.setPreferredSize(new Dimension(this.ventana.getWidth(), 30));
        ventana.getContentPane().add(panelBotones,BorderLayout.SOUTH);

        JButton volver = new JButton("Volver");
        volver.setBounds(30,5,100,20);
        volver.setBackground(tema.getBoton());
        volver.setForeground(tema.getLetra());
        volver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.menu(administradorDeVentanas);
                ventana.setVisible(false);
            }
        });

        JButton confirmar = new JButton("Confirmar");
        confirmar.setBounds(160,5,100,20);
        confirmar.setBackground(tema.getBoton());
        confirmar.setForeground(tema.getLetra());
        confirmar.setEnabled(false);
        

        JLabel carpeta = new JLabel("Seleccione una ciudad: ");
        carpeta.setForeground(tema.getLetra());
		carpeta.setBounds(20, 0, 250, 50);
		panel.add(carpeta);

        JLabel ciudadSeleccionada = new JLabel("");
        ciudadSeleccionada.setForeground(tema.getLetra());
		ciudadSeleccionada.setBounds(20, 35, 250, 50);
		panel.add(ciudadSeleccionada);
		
        JButton ciudad = new JButton("Seleccionar");
        ciudad.setBackground(tema.getBoton());
        ciudad.setForeground(tema.getLetra());
		ciudad.setBounds(160, 15, 110, 20);
		panel.add(ciudad);
        ciudad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String carpetaInicio = "ciudades";
                String directorioWorkspace = System.getProperty("user.dir");
                String rutaCarpetaInicio = directorioWorkspace + File.separator + carpetaInicio;
                System.setProperty("user.dir",rutaCarpetaInicio);
                JFileChooser seleccion = new JFileChooser(System.getProperty("user.dir"));                
                seleccion.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int valor = seleccion.showOpenDialog(null);
                if(valor==JFileChooser.APPROVE_OPTION){
                    File carpSelec = seleccion.getSelectedFile();
                    ciudadSeleccionada.setText(getNombreCarpeta(carpSelec.getAbsolutePath()));
                    sistema.setDireccion(carpSelec.getAbsolutePath());
                    confirmar.setEnabled(true);
                }
                System.setProperty("user.dir",directorioWorkspace);
            }
            private String getNombreCarpeta(String ruta){
                String[] lista = ruta.split("\\\\");
                return lista[lista.length-1];
            }
        });
        confirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmar.setEnabled(false);
                volver.setEnabled(false);
                ciudad.setEnabled(false);
                administradorDeVentanas.vaciarLista();
                
                barraProgreso = new JProgressBar(0, obtenerLineasTotales());
                barraProgreso.setBackground(tema.getFondo());
                barraProgreso.setForeground(tema.getPuntos());
                barraProgreso.setStringPainted(true);
                barraProgreso.setBounds(0, 0, 300, 32);
                panelB.add(barraProgreso);
                hiloArchivo = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        leerXML(true);
                        leerXML(false);
                        administradorDeVentanas.menu(administradorDeVentanas);
                        ventana.setVisible(false);
                    }
                });
                hiloArchivo.start();
            }
        });
        
        panelBotones.add(volver);
        panelBotones.add(confirmar);
        ventana.setVisible(true);
    }

    private void leerXML(boolean nodo){
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
    
    private void guardarNodos(NodeList nodos){
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

    private void guardarArcos(NodeList arcos){
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