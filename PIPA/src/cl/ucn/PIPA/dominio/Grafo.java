package cl.ucn.PIPA.dominio;
import java.util.ArrayList;
//import java.util.Stack;

public class Grafo {
	private ArrayList<Nodo> nodos;
	private ArrayList<Arco> arcos;
	
	public Grafo() {
		nodos = new ArrayList<>();
		arcos = new ArrayList<>();
	}
	public void addNodo(String id,double posX,double posY)
	{
		Nodo nodo = new Nodo(id,posX,posY);
		nodos.add(nodo);
	}
	public boolean addArco(String id, String nombre, String origen, String destino) {
		
		Nodo nodoOrigen = buscarNodo(origen);
		Nodo nodoDestino = buscarNodo(destino);
		
		if (nodoOrigen != null&&nodoDestino != null) {
			Arco arco = new Arco(id,nombre,nodoOrigen,nodoDestino);
			arcos.add(arco);
			nodoOrigen.agregarArco(arco);
			return true;
		}
		return false;
	}
	public ArrayList<Nodo> getNodos(){
		return nodos;
	}
	public ArrayList<Arco> getArcos(){
		return arcos;
	}
	public Nodo buscarNodo(String id) 
	{
		for (Nodo nodo: nodos) {
			if (nodo.getId().equals(id)) {
				return nodo;
			}
		}
		return null;
	}
	/*
	public boolean existeRuta(String origen, String destino){
		if (buscarRuta(origen, destino) != null) {
			return true;
		}
		return false;
	}
	public ArrayList<Nodo> buscarRuta(String origen, String destino) {
		
		Nodo nodoOrigen = buscarNodo(origen);
		Nodo nodoDestino = buscarNodo(destino);
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
	}*/
}