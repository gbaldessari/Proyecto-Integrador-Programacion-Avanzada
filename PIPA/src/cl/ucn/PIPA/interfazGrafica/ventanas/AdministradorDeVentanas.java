package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.logica.Sistema;
/*
* Clase que administra las ventanas
*/
public class AdministradorDeVentanas {
    private Sistema sistema;
	private Tema temaSeleccionado;
	/*
	* Constructor de la clase
	* @param sistema, la superclase del sistema
	*/
	public AdministradorDeVentanas(Sistema sistema,LinkedList<Tema> temas) {
		this.sistema = sistema;
		temaSeleccionado = temas.get(0);
	}

	public void ventanaTema(AdministradorDeVentanas administradorDeVentanas){
		Ventana ventana = new VentanaTema(administradorDeVentanas,sistema,temaSeleccionado);
		ventana.iniciarVentana();
	}

	/*
	* Metodo que despliega la ventana menu
	* @param administradorDeVentanas, la ventana inicializada
	*/
    public void menu(AdministradorDeVentanas administradorDeVentanas) {
		Ventana ventana = new VentanaMenu(administradorDeVentanas,sistema,temaSeleccionado);
		ventana.iniciarVentana();
	}
	/*
	* Metodo que despliega la ventana que muestra el mapa
	* @param administradorDeVentanas, la ventana inicializada
	*/
	public void mostrarMapa(AdministradorDeVentanas administradorDeVentanas){
		Ventana ventana = new VentanaMapa(administradorDeVentanas,sistema,temaSeleccionado);
		ventana.iniciarVentana();
	}
	/*
	* Metodo que despliega la ventana error
	* @param administradorDeVentanas, la ventana inicializada
	*/
	public void mostrarError(String mensajeError){
		JOptionPane.showMessageDialog(null, mensajeError,"Error", 0);
	}

	public void ventanaCierre(JFrame ventanaActiva){
		Ventana ventana = new VentanaCierre(ventanaActiva,temaSeleccionado);
		ventana.iniciarVentana();
	}

	public void ventanaLectura(AdministradorDeVentanas administradorDeVentanas){
		Ventana ventana = new VentanaArchivos(administradorDeVentanas,sistema,temaSeleccionado);
		ventana.iniciarVentana();
	}

    public void limpiarVentana(JFrame ventana) {
		ventana.getContentPane().removeAll();
		ventana.revalidate();
		ventana.repaint();
    }
	
	public void mostrarIDNodo(String ID){
		JOptionPane.showMessageDialog(null,ID,"ID Nodo",JOptionPane.INFORMATION_MESSAGE);
	}

	public Tema getTemaSeleccionado(){
		return temaSeleccionado;
	}
	public void setTema(int index){
		if(index>-1){
			temaSeleccionado = sistema.getTemas().get(index);
		}
	}
	public void vaciarLista(){
		this.sistema.getGrafo().getNodos().clear();
		this.sistema.getGrafo().getArcos().clear();
	}
}