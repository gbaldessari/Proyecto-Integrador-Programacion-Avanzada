package cl.ucn.PIPA.interfazGrafica.ventanas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
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

/**
 * Clase que representa una ventana que muestra un mapa.
 */
public class VentanaMapa implements Ventana {
    /** */
    private AdministradorDeVentanas administradorDeVentanas;
    /** */
    private Sistema sistema;
    /** */
    private JFrame ventana;
    /** */
    private Tema tema;

    /**
     * Constructor de la clase VentanaMapa.
     *
     * @param administradorDeVentanasEntregado Administrador de ventanas.
     * @param sistemaEntregado Sistema.
     * @param temaEntregado Tema de la ventana.
     */
    public VentanaMapa(
    final AdministradorDeVentanas administradorDeVentanasEntregado,
    final Sistema sistemaEntregado, final Tema temaEntregado) {
        this.ventana = new JFrame("Mapa");
        this.ventana.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.ventana.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent we) {
                administradorDeVentanas.ventanaCierre(ventana);
                ventana.setEnabled(false);
            }
        });
        tema = temaEntregado;
        administradorDeVentanas = administradorDeVentanasEntregado;
        sistema = sistemaEntregado;
        final Dimension dim = new Dimension(1200, 700);
        ventana.setSize(dim);
        ventana.setMinimumSize(dim);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(true);
    }

    /**
     * Método para iniciar la ventana y mostrarla al usuario.
     */
    public final void iniciarVentana() {
        PanelMapa panelMapa = new PanelMapa(sistema, tema);
        ventana.getContentPane().add(panelMapa, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.setBackground(tema.getUi());

        JButton botonMenu = new JButton("Volver");
        botonMenu.setForeground(tema.getTexto());
        botonMenu.setBackground(tema.getBoton());
        panel.add(botonMenu);
        ventana.getContentPane().add(panel, BorderLayout.SOUTH);
        botonMenu.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                administradorDeVentanas.menu(administradorDeVentanas);
                ventana.setVisible(false);
                ventana.dispose();
            }
        });

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(null);
        panelInfo.setBackground(tema.getUi());
        final Dimension dimPanelInfo = new Dimension(
        this.ventana.getWidth() / 5, this.ventana.getHeight());
        panelInfo.setPreferredSize(dimPanelInfo);
        ventana.getContentPane().add(panelInfo, BorderLayout.EAST);

        JLabel informacion = new JLabel("Informacion");
        informacion.setForeground(tema.getTexto());
        final Rectangle rectInformacion = new Rectangle(65, 10, 200, 50);
        informacion.setBounds(rectInformacion);
        final float fontSizeInfo = 19f;
        informacion.setFont(informacion.getFont().deriveFont(fontSizeInfo));
        panelInfo.add(informacion);

        final float fontSizeInfoNodo = 14f;

        JLabel infoNodo1 = new JLabel("Punto de partida: ");
        final Rectangle rectInfoNodo1 = new Rectangle(10, 60, 200, 50);
        infoNodo1.setBounds(rectInfoNodo1);
        infoNodo1.setForeground(tema.getTexto());
        infoNodo1.setFont(infoNodo1.getFont().deriveFont(fontSizeInfoNodo));
        panelInfo.add(infoNodo1);

        JLabel id1 = new JLabel("");
        final Rectangle rectId1 = new Rectangle(10, 80, 200, 50);
        id1.setBounds(rectId1);
        id1.setForeground(tema.getTexto());
        id1.setFont(id1.getFont().deriveFont(fontSizeInfoNodo));
        panelMapa.setid1(id1);
        panelInfo.add(id1);

        JLabel c1 = new JLabel("");
        final Rectangle rectC1 = new Rectangle(10, 100, 200, 50);
        c1.setBounds(rectC1);
        c1.setForeground(tema.getTexto());
        c1.setFont(c1.getFont().deriveFont(fontSizeInfoNodo));
        panelMapa.setC1(c1);
        panelInfo.add(c1);

        JLabel infoNodo2 = new JLabel("Punto de llegada: ");
        final Rectangle rectInfoNodo2 = new Rectangle(10, 130, 200, 50);
        infoNodo2.setBounds(rectInfoNodo2);
        infoNodo2.setForeground(tema.getTexto());
        infoNodo2.setFont(infoNodo2.getFont().deriveFont(fontSizeInfoNodo));
        panelInfo.add(infoNodo2);

        JLabel id2 = new JLabel("");
        final Rectangle rectId2 = new Rectangle(10, 150, 200, 50);
        id2.setBounds(rectId2);
        id2.setForeground(tema.getTexto());
        id2.setFont(id2.getFont().deriveFont(fontSizeInfoNodo));
        panelMapa.setid2(id2);
        panelInfo.add(id2);

        JLabel c2 = new JLabel("");
        final Rectangle rectC2 = new Rectangle(10, 170, 200, 50);
        c2.setBounds(rectC2);
        c2.setForeground(tema.getTexto());
        c2.setFont(c2.getFont().deriveFont(fontSizeInfoNodo));
        panelMapa.setC2(c2);
        panelInfo.add(c2);

        JLabel distancia = new JLabel("Distancia entre puntos: ");
        final Rectangle rectDistancia = new Rectangle(10, 200, 200, 50);
        distancia.setBounds(rectDistancia);
        distancia.setForeground(tema.getTexto());
        distancia.setFont(distancia.getFont().deriveFont(fontSizeInfoNodo));
        panelInfo.add(distancia);

        JLabel km = new JLabel("");
        final Rectangle rectKm = new Rectangle(10, 220, 200, 50);
        km.setBounds(rectKm);
        km.setForeground(tema.getTexto());
        km.setFont(km.getFont().deriveFont(fontSizeInfoNodo));
        panelInfo.add(km);
        panelMapa.setKm(km);

        JButton botonRuta = new JButton("Calcular ruta");
        final Rectangle rectRuta = new Rectangle(10, 320, 160, 25);
        botonRuta.setBounds(rectRuta);
        botonRuta.setBackground(tema.getBoton());
        botonRuta.setForeground(tema.getTexto());
        panelInfo.add(botonRuta);
        botonRuta.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (!id2.getText().equals("")) {
                    botonRuta.setEnabled(false);
                    panelMapa.caminoMasCorto();
                }
            }
        });

        JButton botonBorrar = new JButton("Limpiar");
        final Rectangle rectBorrar = new Rectangle(10, 350, 160, 25);
        botonBorrar.setBounds(rectBorrar);
        botonBorrar.setBackground(tema.getBoton());
        botonBorrar.setForeground(tema.getTexto());
        panelInfo.add(botonBorrar);
        botonBorrar.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                km.setText("");
                panelMapa.borrarOrigenDestino();
                botonRuta.setEnabled(true);
            }
        });
        ventana.setVisible(true);
    }
}
