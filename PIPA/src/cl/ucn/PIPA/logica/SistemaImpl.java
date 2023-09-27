package cl.ucn.PIPA.logica;
import java.awt.Color;
import java.util.LinkedList;

import cl.ucn.PIPA.dominio.Grafo;
import cl.ucn.PIPA.dominio.Paleta;
import cl.ucn.PIPA.interfazGrafica.ventanas.AdministradorDeVentanas;

/* 
* Subclase sistema
*/
public class SistemaImpl implements Sistema{
    private AdministradorDeVentanas administradorDeVentanas;
    private Grafo grafo;
    private LinkedList<Paleta> paletas;


    /* Constructor del sistema
     * Inicializa la estructura de datos y los administradores de ventanas
     * @param sistema, la superclase del sistema
     */
    public void iniciarApp(Sistema sistema) {
        grafo = new Grafo();
        paletas = new LinkedList<>();
        obtenerPaletas(); 
        administradorDeVentanas = new AdministradorDeVentanas(sistema,paletas);
        administradorDeVentanas.leerArchivo(administradorDeVentanas);

    }

    /*  
    * Retorna el grafo almacenado
    * @return el grafo
    */
    public Grafo getGrafo() {
        return grafo;
    }

    public AdministradorDeVentanas getAdministradorDeVentanas() {
        return this.administradorDeVentanas;
    }
    
    private void obtenerPaletas(){
        Paleta claro = new Paleta("Claro"
                                ,Color.RGBtoHSB(255, 255, 255, null)
                                ,Color.RGBtoHSB(128, 128, 128, null)
                                ,Color.RGBtoHSB(191, 191, 191, null)
                                ,Color.RGBtoHSB(0, 0, 0, null)
                                ,Color.RGBtoHSB(0, 0, 0, null)
                                ,Color.RGBtoHSB(255, 0, 0, null)
                                ,Color.RGBtoHSB(0, 0, 255, null));
        //Paleta azul = new Paleta("Azul",Color.RGBtoHSB(0, 94, 191, null), Color.RGBtoHSB(0, 62, 128, null), Color.RGBtoHSB(0, 125, 255, null));
        //Paleta oscuro = new Paleta("Oscuro",Color.RGBtoHSB(64, 64, 64, null), Color.RGBtoHSB(48, 48, 48, null), Color.RGBtoHSB(96, 96, 96, null));
        //paletas.add(azul);
        //paletas.add(oscuro);
        paletas.add(claro);
    }
}