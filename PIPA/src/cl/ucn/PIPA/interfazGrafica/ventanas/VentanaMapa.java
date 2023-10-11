package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import cl.ucn.PIPA.dominio.Tema;
import cl.ucn.PIPA.interfazGrafica.paneles.PanelMapa;
import cl.ucn.PIPA.logica.Sistema;


public class VentanaMapa implements Ventana{
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    private JFrame ventana;
    private Tema tema;

    public VentanaMapa(AdministradorDeVentanas administradorDeVentanas, Sistema sistema, Tema tema){
        this.ventana = new JFrame("Mapa");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.ventana.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
                administradorDeVentanas.ventanaCierre(ventana);
                ventana.setEnabled(false);
			}
		});
        this.tema = tema;
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        ventana.setSize(1200,700);
        ventana.setMinimumSize(new Dimension(1200,700));
		ventana.setLocationRelativeTo(null);
		ventana.setResizable(true);
    }
    public void iniciarVentana() {
        PanelMapa panelMapa = new PanelMapa(sistema,tema);
        ventana.getContentPane().add(panelMapa,BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.setBackground(tema.getUi());

        JButton botonMenu = new JButton("Volver");
        botonMenu.setForeground(tema.getLetra());
        botonMenu.setBackground(tema.getBoton());
        panel.add(botonMenu);
        ventana.getContentPane().add(panel,BorderLayout.SOUTH);
		botonMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                administradorDeVentanas.menu(administradorDeVentanas);
                ventana.setVisible(false);
            }
        });

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(null);
        panelInfo.setBackground(tema.getUi());
        panelInfo.setPreferredSize(new Dimension(this.ventana.getWidth()/5, this.ventana.getHeight()));
        ventana.getContentPane().add(panelInfo,BorderLayout.EAST);

        JLabel informacion = new JLabel("Informacion");
        informacion.setForeground(tema.getLetra());
        informacion.setBounds(65,10, 200, 50);
        informacion.setFont(informacion.getFont().deriveFont(19f));
        panelInfo.add(informacion);

        JLabel infoNodo1 = new JLabel("Punto de partida: ");
        infoNodo1.setBounds(10, 60, 200,50);
        infoNodo1.setForeground(tema.getLetra());
        infoNodo1.setFont(infoNodo1.getFont().deriveFont(14f));
        panelInfo.add(infoNodo1);

        JLabel id1 = new JLabel("");
        id1.setBounds(10, 80, 200,50);
        id1.setForeground(tema.getLetra());
        id1.setFont(id1.getFont().deriveFont(14f));
        panelMapa.setid1(id1);
        panelInfo.add(id1);

        JLabel c1 = new JLabel("");
        c1.setBounds(10, 100, 200,50);
        c1.setForeground(tema.getLetra());
        c1.setFont(c1.getFont().deriveFont(14f));
        panelMapa.setC1(c1);
        panelInfo.add(c1);

        JLabel infoNodo2 = new JLabel("Punto de llegada: ");
        infoNodo2.setBounds(10, 130, 200, 50);
        infoNodo2.setForeground(tema.getLetra());
        infoNodo2.setFont(infoNodo2.getFont().deriveFont(14f));
        panelInfo.add(infoNodo2);

        JLabel id2 = new JLabel("");
        id2.setBounds(10, 150, 200,50);
        id2.setForeground(tema.getLetra());
        id2.setFont(id2.getFont().deriveFont(14f));
        panelMapa.setid2(id2);
        panelInfo.add(id2);

        JLabel c2 = new JLabel("");
        c2.setBounds(10, 170, 200, 50);
        c2.setForeground(tema.getLetra());
        c2.setFont(c2.getFont().deriveFont(14f));
        panelMapa.setC2(c2);
        panelInfo.add(c2);

        JLabel distancia = new JLabel("Distancia entre puntos: ");
        distancia.setBounds(10, 200, 200, 50);
        distancia.setForeground(tema.getLetra());
        distancia.setFont(distancia.getFont().deriveFont(14f));
        panelInfo.add(distancia);

        JLabel km = new JLabel("");
        km.setBounds(10, 220, 200, 50);
        km.setForeground(tema.getLetra());
        km.setFont(km.getFont().deriveFont(14f));
        panelInfo.add(km);

        panelMapa.setKm(km);

        JButton botonBorrar = new JButton("Limpiar");
        botonBorrar.setBounds(10, 290, 100, 25);
        botonBorrar.setBackground(tema.getBoton());
        botonBorrar.setForeground(tema.getLetra());
        panelInfo.add(botonBorrar);
		botonBorrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelMapa.borrarOrigenDestino();
            }
        });
        ventana.setVisible(true);
    }


}