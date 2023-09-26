package cl.ucn.PIPA.logica;
import cl.ucn.PIPA.dominio.Grafo;
import cl.ucn.PIPA.interfazGrafica.ventanas.AdministradorDeVentanas;

/*
* Superclase sistema
*/
public interface Sistema {
    public void iniciarApp(Sistema sistema);
    public Grafo getGrafo();
    AdministradorDeVentanas getAdministradorDeVentanas();
}