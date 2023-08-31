package cl.ucn.PIPA.interfazGrafica;
import cl.ucn.PIPA.logica.Sistema;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

public class VentanaLectura implements Ventana {
    private JFrame frame;
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    private String archivo;
    private boolean leyendo;
    private BufferedReader fileReader;
    private Thread hiloArchivo;
    private JButton botonInicio;
    private JButton botonDetener;
    private JProgressBar barraProgreso;
    
    public VentanaLectura(AdministradorDeVentanas administradorDeVentanas, Sistema sistema, String archivo){
        frame = new JFrame("Lectura de Archivo");
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        this.archivo = archivo;
        frame.setSize(300,180);
        frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		iniciarComponentes();
    }
    public void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.getContentPane().add(panel, BorderLayout.CENTER);

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

        barraProgreso = new JProgressBar(0, obtenerLineasTotales(archivo));
        barraProgreso.setStringPainted(true);
        barraProgreso.setBounds(0, 0, 300, 25);
        panel.add(barraProgreso);

        botonInicio = new JButton("Iniciar Lectura");
        botonInicio.setBounds(70, 40, 140, 25);
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
                                
                                fileReader = new BufferedReader(new FileReader(archivo));
                                String linea;
                                int posicion = 0;
                                while (leyendo && (linea = fileReader.readLine()) != null) {
                                    sistema.getLista().add(linea);
                                    posicion++;
                                    barraProgreso.setValue(posicion);
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
        botonDetener.setBounds(70, 80, 140, 25);
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

    private int obtenerLineasTotales(String archivo){
        int lineas = 0;
        try (LineNumberReader reader = new LineNumberReader(new FileReader(archivo))) {
            reader.skip(Long.MAX_VALUE); // Saltar al final del archivo
            lineas = reader.getLineNumber() + 1; // El número de líneas es el número de línea actual más 1
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineas;
    }

    public JFrame getFrame() {
        return frame;
    }
    
}
