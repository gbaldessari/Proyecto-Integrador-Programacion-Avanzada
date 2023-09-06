package cl.ucn.PIPA.dominio;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Grafo {
	List<Nodo> nodos;
	List<Arco> arcos;
	
	public Grafo() {
		nodos = new ArrayList<>();
		arcos = new ArrayList<>();
	}
	public void addNodo(int id)
	{
		Nodo nodo = new Nodo();
		nodo.setId(id);
		nodos.add(nodo);
	}
	public boolean addArco(int origen, int destino) {
		
		Nodo nodoOrigen = buscarNodo(origen);
		Nodo nodoDestino = buscarNodo(destino);
		
		if (nodoOrigen != null&&nodoDestino != null) {
			Arco arco = new Arco(nodoOrigen,nodoDestino);
			arcos.add(arco);
			nodoOrigen.agregarArco(arco);
			return true;
		}
		return false;
	}
	public Nodo buscarNodo(int id) 
	{
		for (Nodo nodo: nodos) {
			if (nodo.getId()== id) {
				return nodo;
			}
		}
		return null;
	}
	public boolean existeRuta(int origen, int destino){
		if (buscarRuta(origen, destino) != null) {
			return true;
		}
		return false;
	}
	public List<Nodo> buscarRuta(int origen, int destino) {
		
		Nodo nodoOrigen = buscarNodo(origen);
		Nodo nodoDestino = buscarNodo(destino);
		List<Nodo> nodosRuta = new ArrayList<>();
		
		if (nodoOrigen != null && nodoDestino != null && buscarRutaDFS(nodosRuta, nodoOrigen, nodoDestino)) {
			return nodosRuta;
		} else {
			return null;
		}
	}
	private boolean buscarRutaDFS(List<Nodo> nodosRuta, Nodo nodoOrigen, Nodo nodoDestino) {
		
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