package cl.ucn.PIPA.logica;
import java.awt.Color;
import java.util.ArrayList;
import cl.ucn.PIPA.dominio.Grafo;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.interfazGrafica.ventanas.AdministradorDeVentanas;
/**
* Interfaz Sistema
*/
public interface Sistema {
    public void iniciarApp(Sistema sistema);
    public Grafo getGrafo();
    public String[] getListaTemas(AdministradorDeVentanas administradorDeVentanas);
    public ArrayList<Tema> getTemas();
    AdministradorDeVentanas getAdministradorDeVentanas();
    public ArrayList<String> getTiposCarreteras();
    public void setTiposCarreteras(ArrayList<String> tiposCarreteras);
    public ArrayList<Color> getColoresCalles();
}