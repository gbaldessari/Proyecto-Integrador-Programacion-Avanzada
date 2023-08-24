package cl.ucn.PIPA.logica;
import cl.ucn.PIPA.interfazGrafica.AdministradorDeVentanas;
public class SistemaImpl implements Sistema{
    private AdministradorDeVentanas administradorDeVentanas;
    public void iniciarApp(Sistema sistema) {
        administradorDeVentanas = new AdministradorDeVentanas(sistema);
        administradorDeVentanas.menu(administradorDeVentanas);
    }
}