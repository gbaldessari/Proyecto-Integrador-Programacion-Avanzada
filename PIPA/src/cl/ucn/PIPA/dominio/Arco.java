package cl.ucn.PIPA.dominio;

public class Arco {
	private String id;
	private String nombre;
	private Nodo origen;
	private Nodo destino;

	public Arco(String id,String nombre,Nodo origen, Nodo destino){
		this.id = id;
		this.nombre = nombre;
        this.origen= origen;
        this.destino = destino;
    }
	public String getId(){
		return id;
	}
	public String getNombre(){
		return nombre;
	}
	public Nodo getOrigen() {
		return origen;
	}
	public Nodo getDestino() {
		return destino;
	}

}