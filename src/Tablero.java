


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Iterator;
import javax.sound.sampled.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;




public class Tablero extends JPanel {

int TABLERO_ANCHO=700;
int TABLERO_LARGO=700;
private Dimension dim;	
private double tiempoDisparo_entre_Aliens=400;
private double ultimo_disparo=0;
private int ax=100;
private int yx=50;
private int delay=17;
private static int nivel=1;
private Image Imagen_nave;
private Image Imagen_enemigo;
private Sonidos reproductor=new Sonidos();
private Jugador player;
private int puntaje=0;
private ArrayList<Aliens> enemigos=new ArrayList<Aliens>();
private ArrayList<Proyectil> proyectilesJugador=new ArrayList<Proyectil>(); // Un error que tuve aca es que no habia iniciado el array y me lo marcaba como inexistente. 
private ArrayList<Proyectil> proyectilesEnemigos=new ArrayList<Proyectil>(); // SIEMPRE INICIAR si no no podes usar una variable.
private boolean pantallaInicio;
private boolean corriendo=true;

	public Tablero() {

		try {
			Imagen_nave=ImageIO.read(Tablero.class.getResource("nave.png"));
			Imagen_enemigo=ImageIO.read(Tablero.class.getResource("alien.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(new Eventos());
		pantallaInicio=true;
		if(pantallaInicio) {
			reproductor.reproducirSonido(Tablero.class.getResource("musicaInicio.wav"));
		}
		Timer loop=new Timer(delay,new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {                 
                    	colision();	
    					repaint();
    					if(!pantallaInicio&&corriendo) {
    						aliensAtaque();
    					}
				}
	        	
	        });
		 spawnObjetos();
	     loop.start();
	     
	     
		 
	     
		
	}
				
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Pantalla de Inicio
		if(pantallaInicio) {
			
			g.setColor(Color.GREEN);
			g.setFont(new Font("Indiana",Font.BOLD,32));
			g.drawString("Alien Invasion", 247, 300); 
			g.setFont(new Font("Indiana",Font.BOLD,15));
			g.setColor(Color.WHITE);
			g.drawString("Presiona ENTER para iniciar", 255, 330); 
			g.drawString("(creado por Enzo L.)", 530, 650); 
			g.drawImage(Imagen_enemigo,310,180,90,90,null);
			
			
		}else if(!pantallaInicio&&corriendo){
		
		//---- Dibuja la nave
		g.drawImage(Imagen_nave,(int)player.dameX(),(int)player.dameY(),70,70,null);

		//------ Score y Vida
		g.setColor(Color.WHITE);
		g.drawString("Vida: "+player.dameVida()+"  Puntos: "+puntaje, 12, 650);
		// 
		dibujarAliens(g);
		//-------- Dibuja los misiles
		if(!proyectilesJugador.isEmpty()) {
			for(Proyectil e:proyectilesJugador) {
				g.setColor(Color.RED);
				g.drawRect((int)e.dameX()+31, (int)e.dameY(), 2, 11);
				g.fillRect((int)e.dameX()+31, (int)e.dameY(), 2, 11);
				e.lanzar("Arriba");
				}	
		}
		if(!proyectilesEnemigos.isEmpty()) {
			for(Proyectil e:proyectilesEnemigos) {
				g.setColor(Color.GREEN);
				g.drawRect((int)e.dameX(), (int)e.dameY(), 2, 11);
				g.fillRect((int)e.dameX(), (int)e.dameY(), 2, 11);
				e.lanzar("Abajo");
				}	
			}
		
		}else if(!corriendo) { // Cuando se para el juego
			    if(player.muerto()) { // <--- Por perdida de la partida
			    	g.setColor(Color.RED);
					g.setFont(new Font("Indiana",Font.BOLD,30));
					g.drawString("DESTRUIDO", 280, 320);
					g.setFont(new Font("Indiana",Font.BOLD,14));
					g.setColor(Color.WHITE);
					g.drawString("Apreta ENTER para volver a jugar", 250, 340);
			    }else { // <--- Pantalla de victoria
			    	g.setColor(Color.YELLOW);
					g.setFont(new Font("Indiana",Font.BOLD,30));
					g.drawString("GANASTE", 280, 320);
					g.setColor(Color.WHITE);
					g.drawString("Puntaje: "+puntaje, 260, 350);
			    }
				
			
		}
	
	}
	
	public void spawnObjetos() { // Agrega los aliens correspondientes a cada nivel, funciona con el met. actualizarNivel y al inicio del programa
		
		
		
		if(nivel==1) {                               
			player=new Jugador(TABLERO_ANCHO/2-35,550,12);
			for(int i=0;i<8;i++) {		 		
				enemigos.add(new Aliens(ax,yx,5));
				if(i==3) {
					ax=100;
					yx+=70;
				}
				ax+=80;
			}
			
		}else if(nivel==2) {
			    enemigos=new ArrayList<Aliens>();
				for(int i=0;i<12;i++) {		
				enemigos.add(new Aliens(ax,yx,5));	
				if(i==5) {
					ax=50;
					yx+=70;
				}
				ax+=80;
			}
			
		}else if(nivel==3) {
		    enemigos=new ArrayList<Aliens>();
			for(int i=0;i<14;i++) {		
			enemigos.add(new Aliens(ax,yx,5));	
			if(i==6) {
				ax=50;
				yx+=70;
			}
			ax+=80;
		}
		
	}else if(nivel==4) {
	    enemigos=new ArrayList<Aliens>();
	    ax=30;
		for(int i=0;i<16;i++) {		
		enemigos.add(new Aliens(ax,yx,5));	
			if(i==7) {
				ax=30;
				yx+=70;
			}
		ax+=70;
		}
	
	}else if(nivel==5) {
	    enemigos=new ArrayList<Aliens>();
	    ax=30;
		for(int i=0;i<18;i++) {		
		
			if(i==6) {
				ax=30;
				yx+=70;
			}else if(i==12) {
				ax=30;
				yx+=70;
			}else if(i==18) {
				ax=30;
				yx+=70;
			}
		ax+=70;
		enemigos.add(new Aliens(ax,yx,5));	
		}
	}else if(nivel==6) {
	    enemigos=new ArrayList<Aliens>();
	    ax=30;
		for(int i=0;i<20;i++) {		
		
			if(i==5) {
				ax=30;
				yx+=70;
			}else if(i==10) {
				ax=30;
				yx+=70;
			}else if(i==15) {
				ax=30;
				yx+=70;
			}else if(i==20) {
				ax=30;
				yx+=70;
			}
		ax+=70;
		enemigos.add(new Aliens(ax,yx,5));	
		}
	}else if(nivel==7) {
	    enemigos=new ArrayList<Aliens>();
	    ax=30;
		for(int i=0;i<24;i++) {		
		
			if(i==6) {
				ax=30;
				yx+=70;
			}else if(i==12) {
				ax=30;
				yx+=70;
			}else if(i==18) {
				ax=30;
				yx+=70;
			}else if(i==24) {
				ax=30;
				yx+=70;
			}
		ax+=70;
		enemigos.add(new Aliens(ax,yx,5));	
		}
	}else if(nivel==8) {
	    enemigos=new ArrayList<Aliens>();
	    ax=30;
		for(int i=0;i<28;i++) {		
		
			if(i==7) {
				ax=30;
				yx+=70;
			}else if(i==14) {
				ax=30;
				yx+=70;
			}else if(i==21) {
				ax=30;
				yx+=70;
			}else if(i==28) {
				ax=30;
				yx+=70;
			}
		ax+=70;
		enemigos.add(new Aliens(ax,yx,5));	
		}
	}
		
	}
	
	public void dibujarAliens(Graphics gr){
		if(!enemigos.isEmpty()&&!pantallaInicio) { // Si ya sacamos la pantalla de Inicio y se crearon los aliens los dibuja
			for(int i=0;i<enemigos.size();i++) {
				gr.drawImage(Imagen_enemigo,(int)enemigos.get(i).dameX(),(int)enemigos.get(i).dameY(),30,30,null);
				enemigos.get(i).actualizarEntidad();
				if(enemigos.get(i).direccionDer()) {
					enemigos.get(i).moverDerecha();
					if(enemigos.get(i).dameX()==650){
						enemigos.get(i).direccion(0);
					}
				}else if(enemigos.get(i).direccionIzq()) {
					enemigos.get(i).moverIzquierda();
					if(enemigos.get(i).dameX()==0){
						enemigos.get(i).direccion(1);
					}
				}
			}
		}else if(enemigos.isEmpty()&&!pantallaInicio) { // Cuando ya no quedan mas Aliens cambia de nivel
			actualizarNivel();
			
		}
	}
	
	public void colision() {
		
		ArrayList<Proyectil> borrados1=new ArrayList<Proyectil>(); // Arrays donde se almacenan los objetos a eliminar
		ArrayList<Aliens> borrados2=new ArrayList<Aliens>(); // para eliminarlos con removeAll, evitando una excepcion IndexOutOfBounds
		for(Proyectil e:proyectilesJugador) {
			for(int i=0;i<enemigos.size();i++) {			
				if(e.dameEntidad().intersects(enemigos.get(i).dameEntidad())) {
					    enemigos.get(i).dañar();
					   	borrados1.add(e);			    
						if(enemigos.get(i).muerto()) {							
							borrados2.add(enemigos.get(i));
							puntaje+=50;
						}
					}				
				}
			}
		proyectilesJugador.removeAll(borrados1); 
		enemigos.removeAll(borrados2);
		
		///------ Proyectiels que chocan con la nave del jugador. Saca vida al jugador y destruye el misil enemigo
		ArrayList<Proyectil> borrados3=new ArrayList<Proyectil>();
		for(Proyectil p:proyectilesEnemigos) {
			if(p.dameEntidad().intersects(player.dameEntidad())) {
				player.dañar();
				borrados3.add(p);
				if(player.muerto()) {	// Si producto del metodo dañar() el jugador muere el juego se detiene.		
					corriendo=false;
				}
			}
		}
		proyectilesEnemigos.removeAll(borrados3);
	}
	public void aliensAtaque() { // Hace disparar a los aliens de forma aleatoria, siempre y cuando se respete un tiempo entre disparos
		double tiempoAhora=System.currentTimeMillis();
		if(!enemigos.isEmpty()) {
				if(tiempoAhora-ultimo_disparo>tiempoDisparo_entre_Aliens) {
					int aleatorio=(int)(Math.random()*(enemigos.size()-1+1)+0);	
					enemigos.get(aleatorio).disparar();
					proyectilesEnemigos.add(enemigos.get(aleatorio).dameProyectil());
					ultimo_disparo=System.currentTimeMillis();			
			 }
				
		 }
	 }
	public  void actualizarNivel() { // 
			    
				proyectilesEnemigos.clear();
				proyectilesJugador.clear();
				enemigos.clear();
				nivel++;
				if(nivel==9) { // Al pasar el nivel 8 (final) se detiene el juego.
					corriendo=false;
				}
				ax=100; // Reinicia las coordenadas para que el metodo spawnObjetos pueda ubicar bien los enemigos
				yx=50;
				spawnObjetos();
	}
	
	private class Eventos extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				if(pantallaInicio) { // Inicia el juego
					pantallaInicio=false;
					reproductor.parar();
					reproductor.reset();
				}else if(!pantallaInicio&&player.muerto()) { // Volver a jugar despues de perder 
					nivel=0;
					puntaje=0;
					corriendo=true;
					actualizarNivel();
				}else if(!pantallaInicio&&!corriendo) { // Despues de ganar te devuelve a la pantalla Inicio.
					nivel=0;
					pantallaInicio=true;
					reproductor.reproducirSonido(Tablero.class.getResource("musicaInicio.wav"));
					puntaje=0;
					corriendo=true;
					actualizarNivel();
				}
			}else if(e.getKeyCode()==KeyEvent.VK_D) {
				player.moverDerecha();
				player.actualizarEntidad();
			}else if(e.getKeyCode()==KeyEvent.VK_D) {
				player.moverDerecha();
				player.actualizarEntidad();
			}
			else if(e.getKeyCode()==KeyEvent.VK_A) {
				player.moverIzquierda();
				player.actualizarEntidad();
			}else if(e.getKeyCode()==KeyEvent.VK_SPACE) {
					player.disparar();
					if(player.estadoProyectil()) {					
						proyectilesJugador.add(player.dameProyectil());
						}		
					
					
			}
		}
				
	}

	
}
