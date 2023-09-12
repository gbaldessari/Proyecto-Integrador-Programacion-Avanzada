package cl.ucn.PIPA.dominio;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase nodo
 */
public class Nodo {
	private String id;
    private Point.Double coordenadas;
	private List<Arco> arcos;
	
	/**
	 * Constructor de la clase nodo
	 * @param id, id del nodo
	 * @param posX, posicion X almacenada
	 * @param posY, posicion Y almacenada 
	 */
	public Nodo(String id,double posX,double posY){
        arcos = new ArrayList<>();
        coordenadas = new Point.Double(posX,posY);
		this.id = id;
    }
	/**
	 * Retorna el id de un nodo
	 * @return el id del nodo
	 */
	public String getId() {
		return id;
	}
	/*
	 * Agrega un arco al nodo
	 * @param arco, el arco
	 */
	public void agregarArco(Arco arco) {
		arcos.add(arco);
	}
	/**
	 * Retorna todos los arcos almacenados en el nodo
	 * @return una lista con los arcos
	 */
	public List<Arco> getArcos() {
		return arcos;
	}
    public double getX(){
        return coordenadas.x;
    }
    public double getY(){
        return coordenadas.y;
    }
    public void setX(double posX){
        coordenadas.x = posX;
    }
    public void setY(double posY){
        coordenadas.y = posY;
    }
	public List<Nodo> getNodosAdyacentes() {
		List<Nodo> nodos = new ArrayList<>();
		for (Arco arco : arcos) {
			nodos.add(arco.getDestino());
		}
		return nodos;
	}
}