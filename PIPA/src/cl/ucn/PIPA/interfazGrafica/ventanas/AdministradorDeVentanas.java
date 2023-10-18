package cl.ucn.PIPA.interfazGrafica.ventanas;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.logica.Sistema;

/**
 * Clase que gestiona las ventanas de la interfaz gráfica.
 */
public class AdministradorDeVentanas {
    private Sistema sistema;  // Instancia del sistema
    private Tema temaSeleccionado;  // Tema seleccionado para la interfaz

    /**
     * Constructor de la clase AdministradorDeVentanas.
     *
     * @param sistema Instancia del sistema.
     * @param temas   Lista de temas disponibles.
     */
    public AdministradorDeVentanas(Sistema sistema, ArrayList<Tema> temas) {
        this.sistema = sistema;
        temaSeleccionado = temas.get(0);
    }

    // Métodos para abrir diferentes ventanas

    /**
     * Abre la ventana de selección de tema.
     *
     * @param administradorDeVentanas Instancia del administrador de ventanas.
     */
    public void ventanaTema(AdministradorDeVentanas administradorDeVentanas) {
        Ventana ventana = new VentanaTema(administradorDeVentanas, sistema, temaSeleccionado);
        ventana.iniciarVentana();
    }

    /**
     * Abre la ventana del menú principal.
     *
     * @param administradorDeVentanas Instancia del administrador de ventanas.
     */
    public void menu(AdministradorDeVentanas administradorDeVentanas) {
        Ventana ventana = new VentanaMenu(administradorDeVentanas, sistema, temaSeleccionado);
        ventana.iniciarVentana();
    }

    /**
     * Abre la ventana del mapa.
     *
     * @param administradorDeVentanas Instancia del administrador de ventanas.
     */
    public void mostrarMapa(AdministradorDeVentanas administradorDeVentanas) {
        Ventana ventana = new VentanaMapa(administradorDeVentanas, sistema, temaSeleccionado);
        ventana.iniciarVentana();
    }

    // Otros métodos

    /**
     * Muestra un mensaje de error en una ventana emergente.
     *
     * @param mensajeError Mensaje de error a mostrar.
     */
    public void mostrarError(String mensajeError) {
        JOptionPane.showMessageDialog(null, mensajeError, "Error", 0);
    }

    /**
     * Abre la ventana de cierre.
     *
     * @param ventanaActiva Ventana activa a cerrar.
     */
    public void ventanaCierre(JFrame ventanaActiva) {
        Ventana ventana = new VentanaCierre(ventanaActiva, temaSeleccionado);
        ventana.iniciarVentana();
    }

    /**
     * Abre la ventana de gestión de archivos.
     *
     * @param administradorDeVentanas Instancia del administrador de ventanas.
     */
    public void ventanaArchivos(AdministradorDeVentanas administradorDeVentanas) {
        Ventana ventana = new VentanaArchivos(administradorDeVentanas, sistema, temaSeleccionado);
        ventana.iniciarVentana();
    }

    // Métodos para gestionar el tema seleccionado

    /**
     * Obtiene el tema seleccionado.
     *
     * @return Tema seleccionado.
     */
    public Tema getTemaSeleccionado() {
        return temaSeleccionado;
    }

    /**
     * Establece el tema seleccionado a partir de su índice en la lista de temas del sistema.
     *
     * @param index Índice del tema en la lista.
     */
    public void setTema(int index) {
        if (index > -1) {
            temaSeleccionado = sistema.getTemas().get(index);
        }
    }

    /**
     * Vacía la lista de nodos y arcos del grafo en el sistema.
     */
    public void vaciarLista() {
        this.sistema.getGrafo().getNodos().clear();
        this.sistema.getGrafo().getArcos().clear();
    }
}