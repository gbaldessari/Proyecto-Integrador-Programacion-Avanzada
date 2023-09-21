package cl.ucn.PIPA.interfazGrafica.ventanas;

import cl.ucn.PIPA.dominio.Panel;

public interface Ventana {
    /**
     * Inicia la ventana
     */
    public void iniciarVentana();
    public Panel getPanel();
}
