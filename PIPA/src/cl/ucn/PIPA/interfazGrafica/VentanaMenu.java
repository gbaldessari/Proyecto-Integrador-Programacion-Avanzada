package cl.ucn.PIPA.interfazGrafica;
import cl.ucn.PIPA.logica.Sistema;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VentanaMenu implements Ventana {
    private JFrame frame;
    private AdministradorDeVentanas administradorDeVentanas;
    private Sistema sistema;
    public void iniciarVentana(AdministradorDeVentanas administradorDeVentanas, Sistema sistema) {
        frame = new JFrame("Menu");
        this.administradorDeVentanas = administradorDeVentanas;
        this.sistema = sistema;
        frame.setSize(500, 600);
		frame.setLocationRelativeTo(null);
		frame.setMinimumSize(new Dimension(500,600));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		iniciarComponentes();
    }

    public void iniciarComponentes() {
        JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		JLabel mensaje = new JLabel("Bienvenido al Simulador de Combates");
		mensaje.setBounds(140, 0, 250, 50);
		panel.add(mensaje);
		
		JButton botonSimulacion = new JButton("Iniciar Simulacion");
		botonSimulacion.setBounds(180, 50, 140, 25);
		panel.add(botonSimulacion);
		
		botonSimulacion.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {}});
		
		JButton botonAñadirPiezas = new JButton("Añadir Piezas");
		botonAñadirPiezas.setBounds(180, 100, 140, 25);
		panel.add(botonAñadirPiezas);
		
		botonAñadirPiezas.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {}});

		JButton botonEnsamblarRobot = new JButton("Ensamblar Robot");
		botonEnsamblarRobot.setBounds(180, 150, 140, 25);
		panel.add(botonEnsamblarRobot);
		
		botonEnsamblarRobot.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {}});

		JButton botonCambiarPiezas = new JButton("Cambiar Piezas");
		botonCambiarPiezas.setBounds(180, 200, 140, 25);
		panel.add(botonCambiarPiezas);
		
		botonCambiarPiezas.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {}});

		JButton botonCambiarArmas = new JButton("Cambiar Arma");
		botonCambiarArmas.setBounds(180, 250, 140, 25);
		panel.add(botonCambiarArmas);
		
		botonCambiarArmas.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {}});

		JButton botonEstadisticasRobots = new JButton("Estadisticas de Robots");
		botonEstadisticasRobots.setBounds(150, 300, 200, 25);
		panel.add(botonEstadisticasRobots);
		
		botonEstadisticasRobots.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {}});

		JButton botonRobotsTripulados = new JButton("Robots Tripulados");
		botonRobotsTripulados.setBounds(150, 350, 200, 25);
		panel.add(botonRobotsTripulados);
		
		botonRobotsTripulados.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {}});

		JButton botonRobotsPorEquipo = new JButton("Buscar Robots por Equipo");
		botonRobotsPorEquipo.setBounds(150, 400, 200, 25);
		panel.add(botonRobotsPorEquipo);
		
		botonRobotsPorEquipo.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {}});

		JButton botonEstadisticas = new JButton("Estadisticas");
		botonEstadisticas.setBounds(150, 450, 200, 25);
		panel.add(botonEstadisticas);
		
		botonEstadisticas.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {}});

		JButton botonGuardar = new JButton("Guardar");
		botonGuardar.setBounds(180, 500, 140, 25);
		panel.add(botonGuardar);
		
		botonGuardar.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {}});

    }

    public JFrame getFrame() {
        return frame;
    }
}