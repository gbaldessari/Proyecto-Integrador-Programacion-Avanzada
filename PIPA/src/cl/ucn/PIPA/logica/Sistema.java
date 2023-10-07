package cl.ucn.PIPA.logica;

import java.util.LinkedList;

import cl.ucn.PIPA.dominio.Grafo;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.interfazGrafica.ventanas.AdministradorDeVentanas;
/*
* Superclase sistema
*/
public interface Sistema {
    public void iniciarApp(Sistema sistema);
    public Grafo getGrafo();
    public String[] getListaTemas(AdministradorDeVentanas administradorDeVentanas);
    public LinkedList<Tema> getTemas();
    AdministradorDeVentanas getAdministradorDeVentanas();
    public void setDireccion(String direccion);
    public String getDireccion();
}