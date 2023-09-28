package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import cl.ucn.PIPA.dominio.Paleta;
import cl.ucn.PIPA.logica.Sistema;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    private Paleta paleta;
    private Thread hiloArchivo;
    private JProgressBar barraProgreso;
    private int progreso;

    /**
     * Constructor de la clase
     * @param administradorDeVentanas, herramienta para inicializar la ventana
     */
    public VentanaMenu(AdministradorDeVentanas administradorDeVentanas, Sistema sistema, Paleta paleta) {
        this.ventana = new JFrame("Menú");
        this.sistema = sistema;
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.ventana.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				cerrar(ventana);
			}
		});
        this.paleta = paleta;
        this.administradorDeVentanas = administradorDeVentanas;
        ventana.setSize(300,150);
        ventana.setMaximumSize(new Dimension(300,150));
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(false);
    }

    public void iniciarVentana() {
        JPanel panel = new JPanel();
        panel.setBackground(paleta.getFondo());
		panel.setLayout(null);
		ventana.getContentPane().add(panel,BorderLayout.CENTER);

		JLabel mensaje = new JLabel("Menú principal");
        mensaje.setBackground(paleta.getLetra());
		mensaje.setBounds(102, 0, 250, 50);
		panel.add(mensaje);
		
		JButton botonMostrarMapa = new JButton("Ver mapa");
        botonMostrarMapa.setBackground(paleta.getBoton());
        botonMostrarMapa.setForeground(paleta.getLetra());
		botonMostrarMapa.setBounds(72, 50, 140, 25);
		panel.add(botonMostrarMapa);
		
		botonMostrarMapa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.limpiarVentana(ventana);
                administradorDeVentanas.mostrarMapa(administradorDeVentanas);
                ventana.setVisible(false);
            }
        });

        if(sistema.getGrafo().getNodos().isEmpty()) {
            JPanel panelB = new JPanel();
            panelB.setBackground(paleta.getFondo());
            ventana.getContentPane().add(panelB,BorderLayout.SOUTH);
            barraProgreso = new JProgressBar(0, obtenerLineasTotales());
            barraProgreso.setBackground(paleta.getFondo());
            barraProgreso.setForeground(paleta.getPuntos());
            barraProgreso.setStringPainted(true);
            barraProgreso.setBounds(0, 0, 300, 32);
            panelB.add(barraProgreso);
            botonMostrarMapa.setEnabled(false);
            hiloArchivo = new Thread(new Runnable() {
                @Override
                public void run() {
                    leerXML(true);
                    leerXML(false);
                    botonMostrarMapa.setEnabled(true);
                }
            });
            hiloArchivo.start();
        }
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

    private void cerrar(JFrame ventana){
		String [] botones = {"Cerrar", "Cancelar"};
		int eleccion = JOptionPane.showOptionDialog(ventana, "¿Desea cerrar la aplicación", "Confirmar cierre",
		0,JOptionPane.WARNING_MESSAGE,null,botones,ventana);
		if(eleccion==JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
}