package cl.ucn.PIPA.interfazGrafica;
import cl.ucn.PIPA.logica.Sistema;

public class AdministradorDeVentanas {
    private Sistema sistema;
	public AdministradorDeVentanas(Sistema sistema) {
		this.sistema = sistema;
	}
    public void menu(AdministradorDeVentanas administradorVentanas) {
		Ventana v = new VentanaMenu();
        v.iniciarVentana(administradorVentanas,sistema);
		v.getFrame().setVisible(true);
	}
}