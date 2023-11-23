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

    public ArrayList<Nodo> encontrarCaminoMasCorto(String inicio,String destino){
        return encontrarMejorCaminoBidireccional(binarySearch(inicio), binarySearch(destino));
    }

    /* 
    Se crean dos estructuras de datos para cada dirección de búsqueda (inicio y destino). 
    Estas estructuras incluyen un mapa de costos acumulados, un mapa de padres, una cola de prioridad (lista abierta) y un conjunto de nodos evaluados (lista cerrada). 
    Además, se inicializan los costos acumulados para los nodos de inicio y destino.

    Se realiza un bucle mientras ambas listas abiertas no estén vacías. 
    En cada iteración, se extraen los nodos con el menor costo acumulado desde la lista abierta correspondiente y se marcan como evaluados.

    Se expanden los nodos vecinos del nodo actual en ambas direcciones (inicio y destino). 
    Para cada nodo vecino, se actualizan los costos acumulados y se verifica si ya se ha evaluado. 
    Si el nodo vecino no ha sido evaluado, se agrega a la lista abierta correspondiente.

    Después de expandir los nodos desde el inicio y el destino en una iteración, 
    se verifica si ha habido una intersección. Esto se hace comprobando si el nodo actual desde el inicio está en la lista cerrada del destino y viceversa.

    Si hay una intersección, se reconstruye el camino bidireccional desde el nodo de inicio hasta 
    el nodo de destino utilizando la información almacenada en los mapas de padres y se devuelve como resultado.

    Si no hay intersección y ambas listas abiertas están vacías, el algoritmo concluye y devuelve null para indicar que no se encontró un camino.
    
    */
    // Método para encontrar el mejor camino entre dos nodos utilizando búsqueda bidireccional
    public ArrayList<Nodo> encontrarMejorCaminoBidireccional(Nodo inicio, Nodo destino) {
        // Verificar si los nodos de inicio y destino son válidos
        if (inicio == null || destino == null) {
            return null;
        }

        // Estructuras de datos necesarias desde el inicio
        Map<Nodo, Double> costoAcumuladoInicio = new HashMap<>();  // Almacena el costo acumulado desde el inicio hasta cada nodo
        Map<Nodo, Nodo> padreInicio = new HashMap<>();  // Almacena el nodo padre de cada nodo en el camino desde el inicio
        /* Funcion lambda que toma un nodo y calcula un valor que se utilizará para comparar nodos en la cola de prioridad. */
        PriorityQueue<Nodo> listaAbiertaInicio = new PriorityQueue<>(Comparator.comparingDouble(
                nodo -> costoAcumuladoInicio.get(nodo) + Utils.haversine(nodo.getY(), nodo.getX(), destino.getY(), destino.getX())));
        // Cola de prioridad para nodos desde el inicio, ordenada por el costo acumulado hasta el destino

        Set<Nodo> listaCerradaInicio = new HashSet<>();  // Conjunto de nodos ya evaluados desde el inicio

        // Estructuras de datos necesarias desde el destino
        Map<Nodo, Double> costoAcumuladoDestino = new HashMap<>();  // Almacena el costo acumulado desde el destino hasta cada nodo
        Map<Nodo, Nodo> padreDestino = new HashMap<>();  // Almacena el nodo padre de cada nodo en el camino desde el destino
        /* Funcion lambda que toma un nodo y calcula un valor que se utilizará para comparar nodos en la cola de prioridad. */
        PriorityQueue<Nodo> listaAbiertaDestino = new PriorityQueue<>(Comparator.comparingDouble(
                nodo -> costoAcumuladoDestino.get(nodo) + Utils.haversine(nodo.getY(), nodo.getX(), inicio.getY(), inicio.getX())));
        // Cola de prioridad para nodos desde el destino, ordenada por el costo acumulado hasta el inicio

        Set<Nodo> listaCerradaDestino = new HashSet<>();  // Conjunto de nodos ya evaluados desde el destino

        // Inicializar costos a infinito, excepto para los nodos de inicio y destino
        for (Nodo nodo : nodos) {
            costoAcumuladoInicio.put(nodo, Double.MAX_VALUE);
            costoAcumuladoDestino.put(nodo, Double.MAX_VALUE);
            padreInicio.put(nodo, null);
            padreDestino.put(nodo, null);
        }
        costoAcumuladoInicio.put(inicio, 0.0);
        costoAcumuladoDestino.put(destino, 0.0);

        listaAbiertaInicio.add(inicio);
        listaAbiertaDestino.add(destino);

        // Aplicar la búsqueda bidireccional
        while (!listaAbiertaInicio.isEmpty() && !listaAbiertaDestino.isEmpty()) {
            // Búsqueda desde el inicio
            Nodo nodoActualInicio = listaAbiertaInicio.poll();  // Obtener el nodo con menor costo acumulado desde el inicio
            listaCerradaInicio.add(nodoActualInicio);  // Marcar el nodo como evaluado

            expandirNodos(nodoActualInicio, listaAbiertaInicio, listaCerradaInicio, costoAcumuladoInicio, padreInicio, destino);

            // Verificar intersección
            if (listaCerradaDestino.contains(nodoActualInicio)) {
                // Si hay intersección, reconstruir el camino bidireccional y retornarlo
                return reconstruirCaminoBidireccional(inicio, destino, nodoActualInicio, padreInicio, padreDestino);
            }

            // Búsqueda desde el destino
            Nodo nodoActualDestino = listaAbiertaDestino.poll();  // Obtener el nodo con menor costo acumulado desde el destino
            listaCerradaDestino.add(nodoActualDestino);  // Marcar el nodo como evaluado

            expandirNodos(nodoActualDestino, listaAbiertaDestino, listaCerradaDestino, costoAcumuladoDestino, padreDestino, inicio);

            // Verificar intersección
            if (listaCerradaInicio.contains(nodoActualDestino)) {
                // Si hay intersección, reconstruir el camino bidireccional y retornarlo
                return reconstruirCaminoBidireccional(inicio, destino, nodoActualDestino, padreInicio, padreDestino);
            }
        }

        return null; // No se encontró un camino
    }

    // Método para expandir los nodos vecinos de un nodo actual en la búsqueda A* bidireccional
    private void expandirNodos(Nodo nodoActual, PriorityQueue<Nodo> listaAbierta, Set<Nodo> listaCerrada,
                               Map<Nodo, Double> costoAcumulado, Map<Nodo, Nodo> padre, Nodo objetivo) {
        // Iterar sobre los arcos del nodo actual
        for (Arco arco : nodoActual.getArcos()) {
            Nodo vecino = arco.getDestino(); // Obtener el nodo vecino a través del arco

        // Ignorar nodos que ya han sido evaluados
        if (listaCerrada.contains(vecino)) {
            continue;
        }

        // Calcular el nuevo costo acumulado hasta el nodo vecino
        double nuevoCosto = costoAcumulado.get(nodoActual) + arco.getPeso();

        // Verificar si el nodo vecino no está en la lista abierta o si el nuevo costo es menor
        if (!listaAbierta.contains(vecino) || nuevoCosto < costoAcumulado.get(vecino)) {
            costoAcumulado.put(vecino, nuevoCosto); // Actualizar el costo acumulado
            padre.put(vecino, nodoActual); // Establecer al nodo actual como el padre del nodo vecino
        // Si el nodo vecino no está en la lista abierta, agregarlo
            if (!listaAbierta.contains(vecino)) {
                listaAbierta.add(vecino);
                }
            }
        }
    }
    

    // Método para reconstruir el camino bidireccional combinando los caminos desde el inicio y el destino hasta la intersección
    private ArrayList<Nodo> reconstruirCaminoBidireccional(Nodo inicio, Nodo destino, Nodo interseccion,
        Map<Nodo, Nodo> padreInicio, Map<Nodo, Nodo> padreDestino) {
        // Reconstruir el camino desde el inicio hasta la intersección
        ArrayList<Nodo> caminoInicio = reconstruirCamino(inicio, interseccion, padreInicio);

        // Reconstruir el camino desde el destino hasta la intersección
        ArrayList<Nodo> caminoDestino = reconstruirCamino(destino, interseccion, padreDestino);

        Collections.reverse(caminoDestino); // Revertir el camino desde el destino

        // Crear el camino final combinando los caminos desde el inicio y el destino
        ArrayList<Nodo> caminoFinal = new ArrayList<>();
        caminoFinal.addAll(caminoInicio);
        caminoFinal.addAll(caminoDestino.subList(1, caminoDestino.size())); // Excluir el nodo de intersección duplicado

        return caminoFinal;
    }

    // Método para reconstruir el camino desde el destino hasta el inicio utilizando el mapa de padres
    private ArrayList<Nodo> reconstruirCamino(Nodo inicio, Nodo destino, Map<Nodo, Nodo> padre) {
        ArrayList<Nodo> camino = new ArrayList<>();

        // Iniciar un bucle para reconstruir el camino
        for (Nodo nodo = destino; nodo != null; nodo = padre.get(nodo)) {
            camino.add(nodo); // Agregar el nodo actual al camino

            // Verificar si hemos llegado al nodo de inicio
            if (nodo.equals(inicio)) {
                break;
            }
        }

        Collections.reverse(camino); // Revertir el camino para que esté en el orden correcto
        return camino; // Devolver el camino reconstruido
    }

    /* 
    // Método para encontrar el mejor camino entre dos nodos utilizando el algoritmo A*
    public ArrayList<Nodo> encontrarMejorCaminoAStar(Nodo inicio, Nodo destino) {
        if (inicio == null || destino == null) {
            return null;
        }

        // Estructuras de datos necesarias
        Map<Nodo, Double> costoAcumulado = new HashMap<>();
        Map<Nodo, Nodo> padre = new HashMap<>();
        /* Funcion lambda que toma un nodo y calcula un valor que se utilizará para comparar nodos en la cola de prioridad. 
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
    */

}