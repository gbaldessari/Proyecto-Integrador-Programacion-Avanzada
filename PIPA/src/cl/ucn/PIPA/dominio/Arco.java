package cl.ucn.PIPA.dominio;

/**
 * clase que representa la union entre dos nodos.
 */
public class Arco {
	private String id;
	private String nombre;
	private Nodo origen;
	private Nodo destino;
	/**
	 * Constructor de la clase arco
	 * @param id id del arco
	 * @param nombre nombre del arco
	 * @param origen nodo de origen del arco
	 * @param destino nodo de destino del arco
	 */
	public Arco(String id,String nombre,Nodo origen, Nodo destino){
		this.id = id;
		this.nombre = nombre;
        this.origen= origen;
        this.destino = destino;
    }
	/**
	 * Retorna el id del arco
	 * @return String que contiene el id del arco
	 */
	public String getId(){
		return id;
	}
	/**
	 * Retorna el nombre del arco
	 * @return String que contiene el nombre del arco
	 */
	public String getNombre(){
		return nombre;
	}
	/**
	 * Retorna el Nodo de origen del arco
	 * @return Nodo en el que se encuentra en el origen del arco
	 */
	public Nodo getOrigen() {
		return origen;
	}
	/**
	 * Retorna el nodo de destino del arco
	 * @return Nodo en el que se encuentra en el destino del arco
	 */
	public Nodo getDestino() {
		return destino;
	}

}