package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.logica.Sistema;

public class AdministradorDeVentanas {
    private Sistema sistema;
	private Tema temaSeleccionado;

	public AdministradorDeVentanas(Sistema sistema,ArrayList<Tema> temas) {
		this.sistema = sistema;
		temaSeleccionado = temas.get(0);
	}
	public void ventanaTema(AdministradorDeVentanas administradorDeVentanas){
		Ventana ventana = new VentanaTema(administradorDeVentanas,sistema,temaSeleccionado);
		ventana.iniciarVentana();
	}
    public void menu(AdministradorDeVentanas administradorDeVentanas) {
		Ventana ventana = new VentanaMenu(administradorDeVentanas,sistema,temaSeleccionado);
		ventana.iniciarVentana();
	}
	public void mostrarMapa(AdministradorDeVentanas administradorDeVentanas){
		Ventana ventana = new VentanaMapa(administradorDeVentanas,sistema,temaSeleccionado);
		ventana.iniciarVentana();
	}
	public void mostrarError(String mensajeError){
		JOptionPane.showMessageDialog(null, mensajeError,"Error", 0);
	}
	public void ventanaCierre(JFrame ventanaActiva){
		Ventana ventana = new VentanaCierre(ventanaActiva,temaSeleccionado);
		ventana.iniciarVentana();
	}
	public void ventanaArchivos(AdministradorDeVentanas administradorDeVentanas){
		Ventana ventana = new VentanaArchivos(administradorDeVentanas, sistema, temaSeleccionado);
		ventana.iniciarVentana();
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