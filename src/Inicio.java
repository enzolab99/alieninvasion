

import java.awt.Toolkit;

import javax.swing.JFrame;

public class Inicio extends JFrame{

	
	public Inicio() {
		
		setSize(700,700);
		setTitle("Alien Invasion");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Inicio.class.getResource("icono.png")));
		setResizable(false);
		setLocationRelativeTo(null);
		add(new Tablero());
		setVisible(true);
		
	}

	public static void main(String[] args) {
		
		Inicio juego=new Inicio();
		juego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
}
