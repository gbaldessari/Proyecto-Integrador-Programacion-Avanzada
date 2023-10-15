package cl.ucn.PIPA.dominio;
import java.util.ArrayList;
import java.util.Stack;
/**
 * Clase que contiene la estructura de datos grafo
 */
public class Grafo {
	private ArrayList<Nodo> nodos;
	private ArrayList<Arco> arcos;
	/**
	 * Constructor de la clase grafo
	 */
	public Grafo() {
		nodos = new ArrayList<>();
		arcos = new ArrayList<>();
	}
	/**
	 * Agrega un nodo a la lista de nodos
	 * @param id id del nodo
	 * @param posX posicion x en la que se encuentra el nodo
	 * @param posY posicion y en la que se encuentra el nodo
	 */
	public void addNodo(String id,double posX,double posY)
	{
		Nodo nodo = new Nodo(id,posX,posY);
		int insertIndex = binaryAdd(nodo);
		if(insertIndex!=-1){
			nodos.add(insertIndex,nodo);
		}
	}

    private int binaryAdd(Nodo nodo) {
        int posIzq = 0;
        int posDer = nodos.size() - 1;

        while (posIzq <= posDer) {
            int posMid = posIzq + (posDer - posIzq) / 2;
            int comparacion = nodo.getId().compareTo(nodos.get(posMid).getId());

            if (comparacion == 0) {
                return -1;
            } else if (comparacion < 0) {
                posDer = posMid - 1;
            } else {
                posIzq = posMid + 1;
            }
        }
        return posIzq;
    }
	/**
	 * Agrega un arco a la lista de arcos
	 * @param id id del arco
	 * @param nombre nombre del arco
	 * @param origen nombre del nodo de origen del arco
	 * @param destino nombre del nodo de destino del arco
	 */
	public boolean addArco(String id, String nombre, String origen, String destino) {
		
		Nodo nodoOrigen = binarySearch(origen); 
		Nodo nodoDestino = binarySearch(destino);
		
		if (nodoOrigen != null&&nodoDestino != null) {  //Si el nodo origen y el nodo destino existen se crea un arco
			Arco arco = new Arco(id,nombre,nodoOrigen,nodoDestino);
			arcos.add(arco);
			nodoOrigen.agregarArco(arco);
			return true;
		}
		return false;
	}
	/**
	 * Retorna la lista con todos los nodos
	 * @return Una arraylist con todos los nodos
	 */
	public ArrayList<Nodo> getNodos(){
		return nodos;
	}
	/**
	 * Retorna la lista con todos los arcos
	 * @return Una arraylist con todos los arcos
	 */
	public ArrayList<Arco> getArcos(){
		return arcos;
	}
	/**
	 * Busca un nodo dentro del grafo
	 * @param id string que contiene el id del nodo
	 */
	private Nodo binarySearch(String id) {
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
        return null;
	}
	/**
	 * Comprueba si existe una ruta dentro del grafo
	 * @param origen id del nodo de origen
	 * @param destino id del nodo de destino
	 */
	public boolean existeRuta(String origen, String destino){
		if (buscarRuta(origen, destino) != null) {
			return true;
		}
		return false;
	}
	/**
	 * 
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
	private boolean buscarRutaDFS(ArrayList<Nodo> nodosRuta, Nodo nodoOrigen, Nodo nodoDestino) {
		
		// agrega el origen
	    nodosRuta.add(nodoOrigen);
		
		// origen y destino son el mismo ?
		if(nodoOrigen.getId() == nodoDestino.getId()){
            return true;
        }
		
		// si no son el mismo, revise las rutas usando una pila
        Stack<Nodo> pilaDeNodos = new Stack<>();
        ArrayList<Nodo> nodosVisitados = new ArrayList<>();

        pilaDeNodos.add(nodoOrigen);

        while(!pilaDeNodos.isEmpty()){
            Nodo actual = pilaDeNodos.pop();

            // ignore los nodos ya visitados
            if (nodosVisitados.contains(actual))
            	continue;
            
            // es el nodo que estamos buscando ?
            if (actual.equals(nodoDestino)) {
            	nodosRuta.addAll(pilaDeNodos);
            	nodosRuta.add(nodoDestino);
                return true;
            }
            else {
                // siga buscando en las rutas no visitadas
            	nodosVisitados.add(actual);
            	for(Nodo nodo: actual.getNodosAdyacentes()) {
            		if (!pilaDeNodos.contains(nodo))
            			pilaDeNodos.add(nodo);
            	}
            }
        }
        return false;
	}
}