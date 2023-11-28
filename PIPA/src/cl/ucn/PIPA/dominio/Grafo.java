package cl.ucn.PIPA.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import cl.ucn.PIPA.utils.Funciones;

/**
 * Clase que contiene la estructura de datos de un grafo.
 */
public class Grafo {
    /**
     * Lista de nodos en el grafo.
     */
    private ArrayList<Nodo> nodos;
    /**
     * Lista de arcos en el grafo.
     */
    private ArrayList<Arco> arcos;

    /**
     * Constructor de la clase Grafo.
     * Inicializa las listas de nodos y arcos.
     */
    public Grafo() {
        nodos = new ArrayList<>();
        arcos = new ArrayList<>();
    }

    /**
     * Agrega un nodo al grafo.
     *
     * @param id   ID del nodo.
     * @param posX Posición X del nodo.
     * @param posY Posición Y del nodo.
     */
    public void addNodo(final String id, final double posX, final double posY) {
        Nodo nodo = new Nodo(id, posX, posY);
        int insertIndex = binaryAdd(nodo);
        if (insertIndex != -1) {
            nodos.add(insertIndex, nodo);
        }
    }

    /**
     * Realiza una búsqueda binaria para agregar un nodo
     * en la posición correcta en la lista de nodos.
     *
     * @param nodo Nodo a agregar.
     * @return Índice donde se debe agregar el nodo, -1
     * si ya existe un nodo con el mismo ID.
     */
    private int binaryAdd(final Nodo nodo) {
        // Búsqueda binaria
        int posIzq = 0;
        int posDer = nodos.size() - 1;

        while (posIzq <= posDer) {
            int posMid = posIzq + (posDer - posIzq) / 2;
            int comparacion = nodo.getId().compareTo(nodos.get(posMid).getId());

            if (comparacion == 0) {
                return -1; // Nodo con el mismo ID ya existe
            } else if (comparacion < 0) {
                posDer = posMid - 1;
            } else {
                posIzq = posMid + 1;
            }
        }
        return posIzq;
    }

    /**
     * Agrega un arco al grafo.
     *
     * @param id      Lista de identificadores del arco.
     * @param nombre  Lista de nombres del arco.
     * @param tipo    Lista de tipos del arco.
     * @param origen  ID del nodo de origen del arco.
     * @param destino ID del nodo de destino del arco.
     * @return true si se pudo agregar el arco, false si
     * no se encontraron los nodos de origen y destino.
     */
    public boolean addArco(final ArrayList<String> id, final ArrayList<String>
    nombre, final ArrayList<String> tipo,
            final String origen, final String destino) {
        Nodo nodoOrigen = binarySearch(origen);
        Nodo nodoDestino = binarySearch(destino);

        if (nodoOrigen != null && nodoDestino != null) {
            // Si el nodo origen y destino existen, se crea un arco
            Arco arco = new Arco(id, nombre, tipo, nodoOrigen, nodoDestino);

            arcos.add(arco);
            nodoOrigen.agregarArco(arco);
            return true;
        }
        return false;
    }

    /**
     * Retorna la lista con todos los nodos en el grafo.
     *
     * @return Una ArrayList con todos los nodos.
     */
    public ArrayList<Nodo> getNodos() {
        return nodos;
    }

    /**
     * Retorna la lista con todos los arcos en el grafo.
     *
     * @return Una ArrayList con todos los arcos.
     */
    public ArrayList<Arco> getArcos() {
        return arcos;
    }

    /**
     * Busca un nodo por su ID en el grafo utilizando búsqueda binaria.
     *
     * @param id ID del nodo a buscar.
     * @return El nodo con el ID especificado, null si no se encontró.
     */
    private Nodo binarySearch(final String id) {
        // Búsqueda binaria
        int posIzq = 0;
        int posDer = nodos.size() - 1;

        while (posIzq <= posDer) {
            int posMid = posIzq + (posDer - posIzq) / 2;
            int comparacion = id.compareTo(nodos.get(posMid).getId());

            if (comparacion == 0) {
                return nodos.get(posMid);
            } else if (comparacion < 0) {
                posDer = posMid - 1;
            } else {
                posIzq = posMid + 1;
            }
        }
        return null; // Nodo no encontrado
    }

    /**
     * Busca un nodo por su ID en el grafo utilizando búsqueda binaria.
     *
     * @param inicio ID del nodo a buscar.
     * @param destino ID del nodo a buscar.
     * @return El nodo con el ID especificado, null si no se encontró.
     */
    public final ArrayList<Nodo> encontrarCaminoMasCorto(
    final String inicio, final String destino) {
        return encontrarMejorCaminoAStar(binarySearch(inicio),
        binarySearch(destino));

    }

    /**
     * Método para encontrar el mejor camino entre dos nodos
     * utilizando el algoritmo A*.
     *
     * @param inicio El inicio del camino
     * @param destino El final del camino
     * @return El mejor camino entre los 2 nodos
     */
    public final ArrayList<Nodo> encontrarMejorCaminoAStar(
        final Nodo inicio, final Nodo destino) {
        if (inicio == null || destino == null) {
            return null;
        }

        Map<Nodo, Double> costoAcumulado = inicializarCostos();
        Map<Nodo, Nodo> padre = new HashMap<>();
        PriorityQueue<Nodo> listaAbierta = new PriorityQueue<>(
        comparadorPorCosto(costoAcumulado, destino));
        Set<Nodo> listaCerrada = new HashSet<>();

        costoAcumulado.put(inicio, 0.0);
        listaAbierta.add(inicio);

        while (!listaAbierta.isEmpty()) {
            Nodo nodoActual = listaAbierta.poll();
            listaCerrada.add(nodoActual);

            if (nodoActual.equals(destino)) {
                break;
            }

            actualizarCostosYListaAbierta(nodoActual, destino,
            costoAcumulado, padre, listaAbierta, listaCerrada);
        }

        return construirCamino(destino, padre);
    }

    private Map<Nodo, Double> inicializarCostos() {
        Map<Nodo, Double> costoAcumulado = new HashMap<>();
        for (Nodo nodo : nodos) {
            costoAcumulado.put(nodo, Double.MAX_VALUE);
        }
        return costoAcumulado;
    }

    private Comparator<Nodo> comparadorPorCosto(final Map<Nodo, Double>
        costoAcumulado, final Nodo destino) {
            return Comparator.comparingDouble(nodo -> costoAcumulado.get(nodo)
            + Funciones.haversine(
                    nodo.getY(), nodo.getX(), destino.getY(), destino.getX()));
        }

        private void actualizarCostosYListaAbierta(final Nodo nodoActual,
            final Nodo destino, final Map<Nodo, Double> costoAcumulado,
            final Map<Nodo, Nodo> padre, final PriorityQueue<Nodo> listaAbierta,
            final Set<Nodo> listaCerrada) {
        for (Arco arco : nodoActual.getArcos()) {
            actualizarCostosSiNecesario(arco, nodoActual,
            costoAcumulado, padre, listaAbierta, listaCerrada);
        }
    }

    private void actualizarCostosSiNecesario(final Arco arco,
    final Nodo nodoActual, final Map<Nodo, Double> costoAcumulado,
    final Map<Nodo, Nodo> padre, final PriorityQueue<Nodo> listaAbierta,
    final Set<Nodo> listaCerrada) {
        Nodo vecino = arco.getDestino();

        if (listaCerrada.contains(vecino)) {
            return;
        }

        double nuevoCosto = costoAcumulado.get(nodoActual) + arco.getPeso();

        if (!listaAbierta.contains(vecino) || nuevoCosto
        < costoAcumulado.get(vecino)) {
            costoAcumulado.put(vecino, nuevoCosto);
            padre.put(vecino, nodoActual);

            if (!listaAbierta.contains(vecino)) {
                listaAbierta.add(vecino);
            }
        }
    }

    private ArrayList<Nodo> construirCamino(final Nodo destino,
    final Map<Nodo, Nodo> padre) {
        ArrayList<Nodo> camino = new ArrayList<>();
        for (Nodo nodo = destino; nodo != null; nodo = padre.get(nodo)) {
            camino.add(nodo);
        }
        Collections.reverse(camino);
        return camino;
    }
}
