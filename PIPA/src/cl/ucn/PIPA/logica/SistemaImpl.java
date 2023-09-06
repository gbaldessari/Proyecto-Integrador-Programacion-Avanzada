package cl.ucn.PIPA.logica;
//-------------------------------------------------
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;
//-------------------------------------------------
import cl.ucn.PIPA.dominio.*;
import cl.ucn.PIPA.interfazGrafica.AdministradorDeVentanas;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
public class SistemaImpl implements Sistema{
    private AdministradorDeVentanas administradorDeVentanas;
    private LinkedList<String> lista;
    private Grafo grafo;

    public void iniciarApp(Sistema sistema) {
        lista = new LinkedList<>();
        grafo = new Grafo();
        administradorDeVentanas = new AdministradorDeVentanas(sistema);
        administradorDeVentanas.menu(administradorDeVentanas);
    }

    public boolean buscarArchivo(Sistema sistema, String nombre){
        File arch;
        Scanner scan;
        boolean formato = false;
        for(int i = 0;i<nombre.length();i++){
            if(nombre.charAt(i) == '.'){
                formato  =true;
                break;
            }
        }
        if(!formato){
            nombre = nombre+".txt";
        }
        arch = new File(nombre);
		try {
            scan = new Scanner(arch);
        } catch (FileNotFoundException e) {
            administradorDeVentanas.mostrarError("Archivo no encontrado");
            return false;
        }
        scan.close();
        return true;
    }

    public LinkedList<String> getLista() {
        return lista;
    }

    public void leerXML(String nombre) throws ParserConfigurationException, SAXException, IOException{
        nombre = nombre+".txt";
        File archXML = new File(nombre);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document documento = builder.parse(archXML);
        Element raiz = documento.getDocumentElement();
        NodeList hijos = raiz.getChildNodes();
    }
}