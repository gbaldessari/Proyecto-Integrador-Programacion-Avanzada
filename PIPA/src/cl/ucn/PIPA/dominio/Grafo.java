package cl.ucn.PIPA.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import cl.ucn.PIPA.utils.Utils;

/**
 * Clase que contiene la estructura de datos de un grafo.
 */
public class Grafo {
    private ArrayList<Nodo> nodos;  // Lista de nodos en el grafo
    private ArrayList<Arco> arcos;  // Lista de arcos en el grafo
    private int distanciaRecorrida;

    /**
     * Constructor de la clase Grafo.
     * Inicializa las listas de nodos y arcos.
     */
    public Grafo() {
        nodos = new ArrayList<>();
        arcos = new ArrayList<>();
        distanciaRecorrida = 0;
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
            arco.setPeso();
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

    public ArrayList<Nodo> encontrarCaminoMasCorto(String inicio,String destino){
        return encontrarMejorCaminoAStar(binarySearch(inicio), binarySearch(destino));
        
    }
    // Método para encontrar el mejor camino entre dos nodos utilizando el algoritmo A*
    public ArrayList<Nodo> encontrarMejorCaminoAStar(Nodo inicio, Nodo destino) {
        if (inicio == null || destino == null) {
            return null;
        }

        // Estructuras de datos necesarias
        Map<Nodo, Double> costoAcumulado = new HashMap<>();
        Map<Nodo, Nodo> padre = new HashMap<>();
        /* Funcion lambda que toma un nodo y calcula un valor que se utilizará para comparar nodos en la cola de prioridad. */
        PriorityQueue<Nodo> listaAbierta = new PriorityQueue<>(Comparator.comparingDouble(
                nodo -> costoAcumulado.get(nodo) + Utils.haversine(nodo.getY(),nodo.getX(), destino.getY(),destino.getX())));

        Set<Nodo> listaCerrada = new HashSet<>();

        // Inicializar costos a infinito, excepto para el nodo de inicio
        for (Nodo nodo : nodos) {
            costoAcumulado.put(nodo, Double.MAX_VALUE);
            padre.put(nodo, null);
        }
        costoAcumulado.put(inicio, 0.0);

        listaAbierta.add(inicio);

        // Aplicar el algoritmo A*
        while (!listaAbierta.isEmpty()) {
            Nodo nodoActual = listaAbierta.poll();


            // Mover el nodo actual a la lista cerrada
            listaCerrada.add(nodoActual);

            // Verificar si hemos llegado al nodo de destino
            if (nodoActual.equals(destino)) {
                break;
            }

            for (Arco arco : nodoActual.getArcos()) {
                Nodo vecino = arco.getDestino();

                // Ignorar nodos en la lista cerrada
                if (listaCerrada.contains(vecino)) {
                    continue;
                }

                double nuevoCosto = costoAcumulado.get(nodoActual) + arco.getPeso();

                if (!listaAbierta.contains(vecino) || nuevoCosto < costoAcumulado.get(vecino)) {
                    costoAcumulado.put(vecino, nuevoCosto);
                    padre.put(vecino, nodoActual);
                    distanciaRecorrida += arco.getPeso();

                    // Si el vecino no está en la lista abierta, agrégalo
                    if (!listaAbierta.contains(vecino)) {
                        listaAbierta.add(vecino);
                    }
                }
            }
        }

        // Reconstruir el camino desde el nodo de destino hasta el nodo de inicio
        ArrayList<Nodo> camino = new ArrayList<>();
        for (Nodo nodo = destino; nodo != null; nodo = padre.get(nodo)) {
            camino.add(nodo);
            
        }
        Collections.reverse(camino);

        return camino;
    }

    public int getDistanciaRecorrida() {
        return distanciaRecorrida;
    }
}