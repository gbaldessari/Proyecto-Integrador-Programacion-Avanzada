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
    private ArrayList<Color> coloresCalles;
    private ArrayList<String> tiposCarreteras;

    public void iniciarApp(Sistema sistema) {
        grafo = new Grafo();
        temas = new ArrayList<>();
        tiposCarreteras = new ArrayList<>();
        coloresCalles = new ArrayList<>();
        obtenerTemas();
        obtenerColoresCalles();
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
    private void obtenerColoresCalles(){
        coloresCalles.add(Color.decode("#FF3333"));
        coloresCalles.add(Color.decode("#FF9933"));
        coloresCalles.add(Color.decode("#FFFF33"));
        coloresCalles.add(Color.decode("#99FF33"));
        coloresCalles.add(Color.decode("#00FF80"));
        coloresCalles.add(Color.decode("#00FFFF"));
        coloresCalles.add(Color.decode("#0080FF"));
        coloresCalles.add(Color.decode("#0000FF"));
        coloresCalles.add(Color.decode("#7F00FF"));
        coloresCalles.add(Color.decode("#FF00FF"));
        coloresCalles.add(Color.decode("#FF00FF"));
        coloresCalles.add(Color.decode("#FF007F"));
        coloresCalles.add(Color.decode("#99FF99"));
        coloresCalles.add(Color.decode("#006600"));
        coloresCalles.add(Color.decode("#009999"));
        coloresCalles.add(Color.decode("#80FF00"));
        coloresCalles.add(Color.decode("#FF9999"));
        coloresCalles.add(Color.decode("#990000"));
        coloresCalles.add(Color.decode("#990099"));
        coloresCalles.add(Color.decode("#660066"));
        coloresCalles.add(Color.decode("#006666"));
        coloresCalles.add(Color.decode("#006633"));
        coloresCalles.add(Color.decode("#663300"));
        coloresCalles.add(Color.decode("#CC99FF"));
        coloresCalles.add(Color.decode("#CC99FF"));
        coloresCalles.add(Color.decode("#999900"));
        coloresCalles.add(Color.decode("#CCFFFF"));
        coloresCalles.add(Color.decode("#CCFFE5"));
        coloresCalles.add(Color.decode("#FFCCCC"));
        coloresCalles.add(Color.decode("#FFCCE5"));
    }
    private void obtenerTemas(){
        Tema claro = new Tema("Claro"
                                ,Color.RGBtoHSB(255, 255, 255, null)//Fondo
                                ,Color.RGBtoHSB(160, 160, 160, null)//UI
                                ,Color.RGBtoHSB(200, 200, 200, null)//Botones
                                ,Color.RGBtoHSB(0, 0, 0, null)//Texto
                                ,Color.RGBtoHSB(255, 0, 0, null)//Puntos
                                ,Color.RGBtoHSB(0, 0, 255, null));//Puntos seleccionados
        temas.add(claro);
        Tema oscuro = new Tema("Oscuro"
                                ,Color.RGBtoHSB(44,47,51, null)//Fondo
                                ,Color.RGBtoHSB(35,39,42, null)//UI
                                ,Color.RGBtoHSB(153,170,181, null)//Botones
                                ,Color.RGBtoHSB(255, 255, 255, null)//Texto
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
    public ArrayList<Color> getColoresCalles(){
        return coloresCalles;
    }
}