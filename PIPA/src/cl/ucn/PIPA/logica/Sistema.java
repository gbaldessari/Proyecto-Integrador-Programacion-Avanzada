package cl.ucn.PIPA.logica;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface Sistema {
    public void iniciarApp(Sistema sistema);
    public boolean buscarArchivo(Sistema sistema, String nombre);
    public LinkedList<String> getLista();
    public void leerXML(String nombre) throws ParserConfigurationException, SAXException, IOException;
}