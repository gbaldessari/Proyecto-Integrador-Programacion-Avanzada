package cl.ucn.PIPA.interfazGrafica;
import javax.swing.JOptionPane;
import cl.ucn.PIPA.logica.Sistema;

public class AdministradorDeVentanas {
    private Sistema sistema;
	public AdministradorDeVentanas(Sistema sistema) {
		this.sistema = sistema;
	}
    public void menu(AdministradorDeVentanas administradorVentanas) {
		Ventana v = new VentanaMenu(administradorVentanas,sistema);
		v.getFrame().setVisible(true);
	}

	public void archivo(AdministradorDeVentanas administradorVentanas) {
		Ventana v = new VentanaArchivo(administradorVentanas,sistema);
		v.getFrame().setVisible(true);
	}
	public void mostrarError(String mensaje){
		JOptionPane.showMessageDialog(null, mensaje,"Error", 0);
	}
}