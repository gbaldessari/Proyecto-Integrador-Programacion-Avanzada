package cl.ucn.PIPA.dominio;

public class Arista {
    private int id;
    private Nodo nodoOrigen;
    private Nodo nodoDestino;
     public Arista(int id, Nodo nodoOrigen, Nodo nodoDestino){
        this.id = id;
        this.nodoOrigen = nodoOrigen;
        this.nodoDestino = nodoDestino;
    }
    public int getId(){
        return id;
    }
    public Nodo getNodoOrigen(){
        return nodoOrigen;
    }
    public Nodo getNodoDestino(){
        return nodoDestino;
    }
}