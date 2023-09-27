package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cl.ucn.PIPA.dominio.Paleta;
import cl.ucn.PIPA.logica.Sistema;
/*
* Clase que administra las ventanas
*/
public class AdministradorDeVentanas {
    private Sistema sistema;
	private Paleta paletaSeleccionada;
	/*
	* Constructor de la clase
	* @param sistema, la superclase del sistema
	*/
	public AdministradorDeVentanas(Sistema sistema,LinkedList<Paleta> paletas) {
		this.sistema = sistema;
		paletaSeleccionada = paletas.get(0);
	}

	public void ventanaPrueba(AdministradorDeVentanas administradorDeVentanas){
		Ventana ventana = new VentanaPrueba(administradorDeVentanas);
		ventana.iniciarVentana();
	}

	/*
	* Metodo que despliega la ventana menu
	* @param administradorDeVentanas, la ventana inicializada
	*/
    public void menu(AdministradorDeVentanas administradorDeVentanas) {
		Ventana ventana = new VentanaMenu(administradorDeVentanas,paletaSeleccionada);
		ventana.iniciarVentana();
	}
	/*
	* Metodo que despliega la ventana de lectura de archivo
	* @param administradorDeVentanas, la ventana inicializada
	*/
	public void leerArchivo(AdministradorDeVentanas administradorDeVentanas){
		Ventana ventana = new VentanaLectura(administradorDeVentanas,sistema,paletaSeleccionada);
		ventana.iniciarVentana();
	}
	/*
	* Metodo que despliega la ventana que muestra el mapa
	* @param administradorDeVentanas, la ventana inicializada
	*/
	public void mostrarMapa(AdministradorDeVentanas administradorDeVentanas){
		Ventana ventana = new VentanaMapa(administradorDeVentanas,sistema,paletaSeleccionada);
		ventana.iniciarVentana();
	}
	/*
	* Metodo que despliega la ventana error
	* @param administradorDeVentanas, la ventana inicializada
	*/
	public void mostrarError(String mensajeError){
		JOptionPane.showMessageDialog(null, mensajeError,"Error", 0);
	}
    public void limpiarVentana(JFrame ventana) {
		ventana.getContentPane().removeAll();
		ventana.revalidate();
		ventana.repaint();
    }
	
	public void mostrarIDNodo(String ID){
		JOptionPane.showMessageDialog(null,ID,"ID Nodo",JOptionPane.INFORMATION_MESSAGE);
	}

	public void vaciarLista(){
		this.sistema.getGrafo().getNodos().clear();
		this.sistema.getGrafo().getArcos().clear();
	}
	
}