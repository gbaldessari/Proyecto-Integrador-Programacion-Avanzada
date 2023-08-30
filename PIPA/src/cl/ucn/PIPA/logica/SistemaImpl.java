package cl.ucn.PIPA.logica;
import cl.ucn.PIPA.interfazGrafica.AdministradorDeVentanas;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
public class SistemaImpl implements Sistema{
    private AdministradorDeVentanas administradorDeVentanas;
    private LinkedList<String> lista;
    public void iniciarApp(Sistema sistema) {
        lista = new LinkedList<>();
        administradorDeVentanas = new AdministradorDeVentanas(sistema);
        administradorDeVentanas.menu(administradorDeVentanas);
    }
    public boolean existeArchivo(Sistema sistema, String nombre) {
        File arch;
        Scanner scan;
        boolean formato = false;
        for(int i = 0;i<nombre.length();i++){
            if(nombre.charAt(i) == '.'){
                formato  =true;
                break;
            }
        }
        if(!formato){
            nombre = nombre+".txt";
        }
        arch = new File(nombre);
		try {
            scan = new Scanner(arch);
        } catch (FileNotFoundException e) {
            administradorDeVentanas.mostrarError("Archivo no encontrado");
            return false;
        }
        scan.close();
        return true;
    }

    public void leerArchivo(Sistema sistema, String nombre){
        File arch = new File(nombre);
        try {
            Scanner scan = new Scanner(arch);
            while(scan.hasNextLine()){
                lista.add(scan.nextLine());
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}