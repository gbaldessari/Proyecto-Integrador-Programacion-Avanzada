package cl.ucn.PIPA.interfazGrafica;
import javax.swing.JOptionPane;
import cl.ucn.PIPA.logica.Sistema;

public class AdministradorDeVentanas {
    private Sistema sistema;
	
	public AdministradorDeVentanas(Sistema sistema) {
		this.sistema = sistema;
	}
    public void menu(AdministradorDeVentanas administradorDeVentanas) {
		Ventana v = new VentanaMenu(administradorDeVentanas,sistema);
		v.getFrame().setVisible(true);
	}

	public void ingresarArchivo(AdministradorDeVentanas administradorDeVentanas) {
		Ventana v = new VentanaArchivo(administradorDeVentanas,sistema);
		v.getFrame().setVisible(true);
	}
	public void leerArchivo(AdministradorDeVentanas administradorDeVentanas,String archivo){
		Ventana v = new VentanaLectura(administradorDeVentanas,sistema,archivo);
		v.getFrame().setVisible(true);
	}
	public void mostrarError(String mensaje){
		JOptionPane.showMessageDialog(null, mensaje,"Error", 0);
	}
}