package cl.ucn.PIPA.logica;
import java.awt.Color;
import java.util.ArrayList;
import cl.ucn.PIPA.dominio.Grafo;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.interfazGrafica.ventanas.AdministradorDeVentanas;

/* 
* Subclase sistema
*/
public class SistemaImpl implements Sistema{
    private AdministradorDeVentanas administradorDeVentanas;
    private Grafo grafo;
    private ArrayList<Tema> temas;
    private ArrayList<String> tiposCarreteras;

    public void iniciarApp(Sistema sistema) {
        grafo = new Grafo();
        temas = new ArrayList<>();
        tiposCarreteras = new ArrayList<>();
        obtenerTemas(); 
        administradorDeVentanas = new AdministradorDeVentanas(sistema,temas);
        administradorDeVentanas.menu(administradorDeVentanas);
    }
    public Grafo getGrafo() {
        return grafo;
    }
    public AdministradorDeVentanas getAdministradorDeVentanas() {
        return this.administradorDeVentanas;
    }
    public String[] getListaTemas(AdministradorDeVentanas administradorDeVentanas){
		String[] lista = new String[temas.size()+1];
        lista[0] = administradorDeVentanas.getTemaSeleccionado().getNombre();
		for(int i =1;i<temas.size()+1;i++) {
			lista[i] = temas.get(i-1).getNombre();
		}
		return lista;
	}
    private void obtenerTemas(){
        Tema claro = new Tema("Claro"
                                ,Color.RGBtoHSB(255, 255, 255, null)//Fondo
                                ,Color.RGBtoHSB(160, 160, 160, null)//UI
                                ,Color.RGBtoHSB(200, 200, 200, null)//Botones
                                ,Color.RGBtoHSB(0, 0, 0, null)//Texto
                                ,Color.RGBtoHSB(0, 0, 0, null)//Lineas
                                ,Color.RGBtoHSB(255, 0, 0, null)//Puntos
                                ,Color.RGBtoHSB(0, 0, 255, null));//Puntos seleccionados
        temas.add(claro);
        Tema oscuro = new Tema("Oscuro"
                                ,Color.RGBtoHSB(44,47,51, null)//Fondo
                                ,Color.RGBtoHSB(35,39,42, null)//UI
                                ,Color.RGBtoHSB(153,170,181, null)//Botones
                                ,Color.RGBtoHSB(255, 255, 255, null)//Texto
                                ,Color.RGBtoHSB(153,170,181, null)//Lineas
                                ,Color.RGBtoHSB(255, 255, 255, null)//Puntos
                                ,Color.RGBtoHSB(255, 0, 0, null));//Puntos seleccionados
        temas.add(oscuro);
    }
    public void setTiposCarreteras(ArrayList<String> tiposCarreteras){
        this.tiposCarreteras = tiposCarreteras;
    }
    public ArrayList<String> getTiposCarreteras() {
        return tiposCarreteras;
    }
    public ArrayList<Tema> getTemas() {
        return temas;
    }
}