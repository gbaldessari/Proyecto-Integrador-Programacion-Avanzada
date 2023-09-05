package cl.ucn.PIPA.dominio;
import java.util.ArrayList;
import java.util.List;

public class Nodo {
    private int id;
    private int posX;
    private int posY;
    private List<Arista> aristas;

    public Nodo(int id,int posX,int posY){
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        aristas = new ArrayList<>();
    }
    public int getId(){
        return id;
    }
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public void addArista(Arista arista){
        aristas.add(arista);
    }
    public Arista getArista(int pos){
        return aristas.get(pos);
    }
}
