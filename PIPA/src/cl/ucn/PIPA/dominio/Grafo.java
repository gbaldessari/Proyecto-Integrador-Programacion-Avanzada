package cl.ucn.PIPA.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Clase que contiene la estructura de datos de un grafo.
 */
public class Grafo {
    private ArrayList<Nodo> nodos;  // Lista de nodos en el grafo
    private ArrayList<Arco> arcos;  // Lista de arcos en el grafo

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
    public void addNodo(String id, double posX, double posY) {
        Nodo nodo = new Nodo(id, posX, posY);
        int insertIndex = binaryAdd(nodo);
        if (insertIndex != -1) {
            nodos.add(insertIndex, nodo);
        }
    }

    /**
     * Realiza una búsqueda binaria para agregar un nodo en la posición correcta en la lista de nodos.
     *
     * @param nodo Nodo a agregar.
     * @return Índice donde se debe agregar el nodo, -1 si ya existe un nodo con el mismo ID.
     */
    private int binaryAdd(Nodo nodo) {
        // Búsqueda binaria
        int posIzq = 0;
        int posDer = nodos.size() - 1;

        while (posIzq <= posDer) {
            int posMid = posIzq + (posDer - posIzq) / 2;
            int comparacion = nodo.getId().compareTo(nodos.get(posMid).getId());

            if (comparacion == 0) {
                return -1;  // Nodo con el mismo ID ya existe
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
     * @return true si se pudo agregar el arco, false si no se encontraron los nodos de origen y destino.
     */
    public boolean addArco(ArrayList<String> id, ArrayList<String> nombre, ArrayList<String> tipo, String origen, String destino) {
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
    private Nodo binarySearch(String id) {
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
        return null;  // Nodo no encontrado
    }

    /**
     * Comprueba si existe una ruta entre dos nodos en el grafo.
     *
     * @param origen  ID del nodo de origen.
     * @param destino ID del nodo de destino.
     * @return true si existe una ruta, false si no.
     */
    public boolean existeRuta(String origen, String destino) {
        return buscarRuta(origen, destino) != null;
    }

    /**
     * Busca una ruta entre dos nodos utilizando el algoritmo de búsqueda en profundidad (DFS).
     *
     * @param origen  ID del nodo de origen.
     * @param destino ID del nodo de destino.
     * @return Lista de nodos que forman la ruta si existe, null si no se encontró una ruta.
     */
    public ArrayList<Nodo> buscarRuta(String origen, String destino) {
        Nodo nodoOrigen = binarySearch(origen);
        Nodo nodoDestino = binarySearch(destino);
        ArrayList<Nodo> nodosRuta = new ArrayList<>();

        if (nodoOrigen != null && nodoDestino != null && buscarRutaDFS(nodosRuta, nodoOrigen, nodoDestino)) {
            return nodosRuta;
        } else {
            return null;
        }
    }

    /**
     * Realiza una búsqueda de ruta utilizando el algoritmo DFS (Depth-First Search).
     *
     * @param nodosRuta   Lista donde se almacenarán los nodos de la ruta.
     * @param nodoOrigen  Nodo de origen.
     * @param nodoDestino Nodo de destino.
     * @return true si se encontró una ruta, false si no.
     */
    private boolean buscarRutaDFS(ArrayList<Nodo> nodosRuta, Nodo nodoOrigen, Nodo nodoDestino) {
        nodosRuta.add(nodoOrigen);  // Agrega el nodo de origen

        // Si el origen y destino son el mismo
        if (nodoOrigen.getId().equals(nodoDestino.getId())) {
            return true;
        }

        // Si no son el mismo, revise las rutas usando una pila
        Stack<Nodo> pilaDeNodos = new Stack<>();
        ArrayList<Nodo> nodosVisitados = new ArrayList<>();

        pilaDeNodos.add(nodoOrigen);

        while (!pilaDeNodos.isEmpty()) {
            Nodo actual = pilaDeNodos.pop();

            // Ignora los nodos ya visitados
            if (nodosVisitados.contains(actual))
                continue;

            // ¿Es el nodo que estamos buscando?
            if (actual.equals(nodoDestino)) {
                nodosRuta.addAll(pilaDeNodos);
                nodosRuta.add(nodoDestino);
                return true;
            } else {
                // Sigue buscando en las rutas no visitadas
                nodosVisitados.add(actual);
                for (Nodo nodo : actual.getNodosAdyacentes()) {
                    if (!pilaDeNodos.contains(nodo))
                        pilaDeNodos.add(nodo);
                }
            }
        }
        return false;  // No se encontró una ruta

    }
    public List<Nodo> encontrarCaminoMasCorto(String inicio,String destino){
        return encontrarCaminoMasCorto(binarySearch(inicio), binarySearch(destino));

    }
    public List<Nodo> encontrarCaminoMasCorto(Nodo inicio, Nodo destino) {
        // Inicializar estructuras de datos necesarias
        Map<Nodo, Double> distancia = new HashMap<>();
        Map<Nodo, Nodo> padre = new HashMap<>();
        PriorityQueue<Nodo> colaPrioridad = new PriorityQueue<>(Comparator.comparingDouble(distancia::get));

        // Inicializar distancias a infinito, excepto para el nodo de inicio
        for (Nodo nodo : nodos) {
            distancia.put(nodo, Double.MAX_VALUE);
            padre.put(nodo, null);
        }
        distancia.put(inicio, 0.0);

        colaPrioridad.add(inicio);

        // Aplicar el algoritmo de Dijkstra
        while (!colaPrioridad.isEmpty()) {
            Nodo nodoActual = colaPrioridad.poll();

            for (Arco arco : nodoActual.getArcos()) {
                Nodo vecino = arco.getDestino();
                double nuevaDistancia = distancia.get(nodoActual) + arco.getPeso();

                if (nuevaDistancia < distancia.get(vecino)) {
                    distancia.put(vecino, nuevaDistancia);
                    padre.put(vecino, nodoActual);
                    colaPrioridad.add(vecino);
                }
            }
        }

        // Reconstruir el camino desde el nodo de destino hasta el nodo de inicio
        List<Nodo> camino = new ArrayList<>();
        for (Nodo nodo = destino; nodo != null; nodo = padre.get(nodo)) {
            camino.add(nodo);
        }
        Collections.reverse(camino);
        verCamino(camino);
        return camino;
    }
    public void verCamino(List<Nodo> camino){
        for(Nodo i: camino){
            System.out.println(i.getId() + "-");

        }
    }
}