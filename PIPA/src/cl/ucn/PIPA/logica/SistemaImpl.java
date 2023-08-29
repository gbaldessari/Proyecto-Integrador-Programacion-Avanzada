package cl.ucn.PIPA.logica;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import cl.ucn.PIPA.interfazGrafica.AdministradorDeVentanas;
public class SistemaImpl implements Sistema{
    private AdministradorDeVentanas administradorDeVentanas;
    private LinkedList<String> lista;
    public void iniciarApp(Sistema sistema) {
        lista = new LinkedList<>();
        administradorDeVentanas = new AdministradorDeVentanas(sistema);
        administradorDeVentanas.menu(administradorDeVentanas);
    }
    public boolean leerArchivo(Sistema sistema, String nombre) {
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
            return false;
        }
        while (scan.hasNextLine()) {
			String linea = scan.nextLine();
            lista.add(linea);
		}
        scan.close();
        for(String i : lista){
            System.out.println(i);
        }
        return true;
    }
    
}