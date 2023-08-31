package cl.ucn.PIPA.interfazGrafica;
import cl.ucn.PIPA.logica.Sistema;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class VentanaLectura implements Ventana {
    private JFrame frame;
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    private JButton botonInicio;
    private JButton botonDetener;
    private boolean leyendo;
    private BufferedReader fileReader;
    private Thread hiloArchivo;
    private String archivo;
    public VentanaLectura(AdministradorDeVentanas administradorDeVentanas, Sistema sistema, String archivo){
        frame = new JFrame("Lectura de Archivo");
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        this.archivo = archivo;
        frame.setSize(300,150);
		frame.setLocationRelativeTo(null);
		frame.setMinimumSize(new Dimension(300,150));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		iniciarComponentes();
    }
    public void iniciarComponentes() {
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        botonInicio = new JButton("Iniciar Lectura");
        panel.add(botonInicio);
        botonInicio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!leyendo) {
                    leyendo = true;
                    botonInicio.setEnabled(false);
                    botonDetener.setEnabled(true);

                    hiloArchivo = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                boolean formato = false;
                                for(int i = 0;i<archivo.length();i++){
                                    if(archivo.charAt(i) == '.'){
                                        formato  =true;
                                        break;
                                    }
                                }
                                if(!formato){
                                    archivo = archivo+".txt";
                                }
                                fileReader = new BufferedReader(new FileReader(archivo));
                                String line;
                                while (leyendo && (line = fileReader.readLine()) != null) {
                                    sistema.getLista().add(line);
                                    System.out.println(line);
                                }
                                
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } finally {
                                leyendo = false;
                                botonInicio.setEnabled(true);
                                botonDetener.setEnabled(false);
                                if (fileReader != null) {
                                    try {
                                        fileReader.close();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                administradorDeVentanas.ingresarArchivo(administradorDeVentanas);
                                frame.setVisible(false);
                            }
                        }
                    });
                    hiloArchivo.start();
                }
            }
        });

        botonDetener = new JButton("Detener Lectura");
        botonDetener.setEnabled(false);
        panel.add(botonDetener);
        botonDetener.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                leyendo = false;
                botonInicio.setEnabled(true);
                botonDetener.setEnabled(false);
                if (hiloArchivo != null) {
                    hiloArchivo.interrupt(); // Intenta interrumpir el hilo de lectura
                }
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }
    
}
