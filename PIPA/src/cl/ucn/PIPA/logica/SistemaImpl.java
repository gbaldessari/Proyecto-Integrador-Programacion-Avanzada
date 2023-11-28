package cl.ucn.PIPA.logica;
import java.awt.Color;
import java.util.ArrayList;
import cl.ucn.PIPA.dominio.Grafo;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.interfazGrafica.ventanas.AdministradorDeVentanas;

/**
* Clase Sistema Implementado.
*/
public class SistemaImpl implements Sistema {
    /** */
    private AdministradorDeVentanas administradorDeVentanas;
    /** */
    private Grafo grafo;
    /** */
    private ArrayList<Tema> temas;
    /** */
    private ArrayList<Color> coloresCalles;
    /** */
    private ArrayList<String> tiposCarreteras;

    /**
     * Funcion que inicia la aplicacion.
     *
     * @param sistema sistema de la aplicacion
    */
    public final void iniciarApp(final Sistema sistema) {
        grafo = new Grafo();
        temas = new ArrayList<>();
        tiposCarreteras = new ArrayList<>();
        coloresCalles = new ArrayList<>();
        obtenerTemas();
        obtenerColoresCalles();
        administradorDeVentanas = new AdministradorDeVentanas(sistema, temas);
        administradorDeVentanas.menu(administradorDeVentanas);
    }

    /**
     * Funcion que retorna el grafo del sistema.
     *
     * @return el grafo del sistema.
     */
    public final Grafo getGrafo() {
        return grafo;
    }

    /**
     * Funcion que retorna el administrador de ventanas.
     *
     * @return el administrador de ventanas
     */
    public final AdministradorDeVentanas getAdministradorDeVentanas() {
        return this.administradorDeVentanas;
    }

    /**
     * Funcion que retorna la lista de temas.
     *
     * @param administradorDeVentanasEntregado el administrador de ventanas.
     * @return la lista de temas
     */
    public final String[] getListaTemas(
        final AdministradorDeVentanas administradorDeVentanasEntregado) {
        String[] lista = new String[temas.size() + 1];
        lista[0]
        = administradorDeVentanasEntregado.getTemaSeleccionado().getNombre();
        for (int i = 1; i < temas.size() + 1; i++) {
            lista[i] = temas.get(i - 1).getNombre();
        }
        return lista;
    }

    private void obtenerColoresCalles() {
        coloresCalles.add(Color.decode("#FF3333"));
        coloresCalles.add(Color.decode("#FF9933"));
        coloresCalles.add(Color.decode("#FFFF33"));
        coloresCalles.add(Color.decode("#99FF33"));
        coloresCalles.add(Color.decode("#00FF80"));
        coloresCalles.add(Color.decode("#00FFFF"));
        coloresCalles.add(Color.decode("#0080FF"));
        coloresCalles.add(Color.decode("#0000FF"));
        coloresCalles.add(Color.decode("#7F00FF"));
        coloresCalles.add(Color.decode("#FF00FF"));
        coloresCalles.add(Color.decode("#FF00FF"));
        coloresCalles.add(Color.decode("#FF007F"));
        coloresCalles.add(Color.decode("#99FF99"));
        coloresCalles.add(Color.decode("#006600"));
        coloresCalles.add(Color.decode("#009999"));
        coloresCalles.add(Color.decode("#80FF00"));
        coloresCalles.add(Color.decode("#FF9999"));
        coloresCalles.add(Color.decode("#990000"));
        coloresCalles.add(Color.decode("#990099"));
        coloresCalles.add(Color.decode("#660066"));
        coloresCalles.add(Color.decode("#006666"));
        coloresCalles.add(Color.decode("#006633"));
        coloresCalles.add(Color.decode("#663300"));
        coloresCalles.add(Color.decode("#CC99FF"));
        coloresCalles.add(Color.decode("#CC99FF"));
        coloresCalles.add(Color.decode("#999900"));
        coloresCalles.add(Color.decode("#CCFFFF"));
        coloresCalles.add(Color.decode("#CCFFE5"));
        coloresCalles.add(Color.decode("#FFCCCC"));
        coloresCalles.add(Color.decode("#FFCCE5"));
    }

    private void obtenerTemas() {
        Tema claro = new Tema("Claro",
        "#FFFFFF", // Fondo
        "#A0A0A0", // UI
        "#C8C8C8", // Botones
        "#000000", // Texto
        "#FF0000", // Puntos
        "#0000FF"); // Puntos seleccionados
        temas.add(claro);
        Tema oscuro = new Tema("Oscuro",
        "#2C2F33", // Fondo
        "#23272A", // UI
        "#99AAB5", // Botones
        "#FFFFFF", // Texto
        "#A0A0A0", // Puntos
        "#FF0000"); // Puntos seleccionados
        temas.add(oscuro);
    }

    /**
     * Funcion que guarda los tipos de carretera.
     *
     * @param tiposCarreterasEntregado tipos de carretera entregado.
     */
    public final void setTiposCarreteras(
        final ArrayList<String> tiposCarreterasEntregado) {
        tiposCarreteras = tiposCarreterasEntregado;
    }

    /**
     * Funcion que retorna los tipos de carretera.
     *
     * @return los tipos de carretera.
     */
    public final ArrayList<String> getTiposCarreteras() {
        return tiposCarreteras;
    }

    /**
     * Funcion que retorna los temas.
     *
     * @return los temas.
     */
    public final ArrayList<Tema> getTemas() {
        return temas;
    }

    /**
     * Funcion que retorna los colores de las calles.
     *
     * @return los colores de las calles.
     */
    public final ArrayList<Color> getColoresCalles() {
        return coloresCalles;
    }
}
