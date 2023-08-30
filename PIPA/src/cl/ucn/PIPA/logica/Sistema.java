package cl.ucn.PIPA.logica;

public interface Sistema {
    public void iniciarApp(Sistema sistema);
    public boolean existeArchivo(Sistema sistema, String nombre);
    public void leerArchivo(Sistema sistema, String nombre);
}