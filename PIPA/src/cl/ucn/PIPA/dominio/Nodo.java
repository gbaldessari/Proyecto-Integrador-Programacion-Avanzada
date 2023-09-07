package cl.ucn.PIPA.dominio;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Nodo {
	private String id;
    private Point.Double coordenadas;
	private List<Arco> arcos;
	
	public Nodo(String id,double posX,double posY){
        arcos = new ArrayList<>();
        coordenadas = new Point.Double(posX,posY);
		this.id = id;
    }
	public String getId() {
		return id;
	}
	public void agregarArco(Arco arco) {
		arcos.add(arco);
	}
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