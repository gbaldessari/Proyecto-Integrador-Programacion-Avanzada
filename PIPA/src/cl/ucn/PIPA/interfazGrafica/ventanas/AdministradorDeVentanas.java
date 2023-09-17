package cl.ucn.PIPA.interfazGrafica.ventanas;
import javax.swing.JOptionPane;

import cl.ucn.PIPA.logica.Sistema;

/*
* Clase que administra las ventanas
*/
public class AdministradorDeVentanas {
    private Sistema sistema;
	/*
	* Constructor de la clase
	* @param sistema, la superclase del sistema
	*/
	public AdministradorDeVentanas(Sistema sistema) {
		this.sistema = sistema;
	}
	/*
	* Metodo que despliega la ventana menu
	* @param administradorDeVentanas, la ventana inicializada
	*/
    public void menu(AdministradorDeVentanas administradorDeVentanas) {
		VentanaMenu ventana = new VentanaMenu(administradorDeVentanas);
		ventana.iniciarVentana();
	}
	/*
	* Metodo que despliega la ventana de lectura de archivo
	* @param administradorDeVentanas, la ventana inicializada
	*/
	public void leerArchivo(AdministradorDeVentanas administradorDeVentanas){
		VentanaLectura ventana = new VentanaLectura(administradorDeVentanas,sistema);
		ventana.iniciarVentana();
	}
	/*
	* Metodo que despliega la ventana que muestra el mapa
	* @param administradorDeVentanas, la ventana inicializada
	*/
	public void mostrarMapa(AdministradorDeVentanas administradorDeVentanas){
		VentanaMapa ventana = new VentanaMapa(administradorDeVentanas,sistema);
		ventana.iniciarVentana();
	}
	/*
	* Metodo que despliega la ventana error
	* @param administradorDeVentanas, la ventana inicializada
	*/
	public void mostrarError(String mensajeError){
		JOptionPane.showMessageDialog(null, mensajeError,"Error", 0);
	}
}