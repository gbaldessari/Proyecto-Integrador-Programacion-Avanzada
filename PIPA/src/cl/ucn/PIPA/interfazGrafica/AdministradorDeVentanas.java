package cl.ucn.PIPA.interfazGrafica;
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
		VentanaMenu v = new VentanaMenu(administradorDeVentanas);
		v.setVisible(true);
	}
	/*
	* Metodo que despliega la ventana de lectura de archivo
	* @param administradorDeVentanas, la ventana inicializada
	*/
	public void leerArchivo(AdministradorDeVentanas administradorDeVentanas){
		VentanaLectura v = new VentanaLectura(administradorDeVentanas,sistema);
		v.setVisible(true);
	}
	/*
	* Metodo que despliega la ventana que muestra el mapa
	* @param administradorDeVentanas, la ventana inicializada
	*/
	public void mostrarMapa(AdministradorDeVentanas administradorDeVentanas){
		VentanaMapa v = new VentanaMapa(administradorDeVentanas,sistema);
		v.setVisible(true);
	}
	/*
	* Metodo que despliega la ventana error
	* @param administradorDeVentanas, la ventana inicializada
	*/
	public void mostrarError(String mensaje){
		JOptionPane.showMessageDialog(null, mensaje,"Error", 0);
	}
}