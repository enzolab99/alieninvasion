

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Timer;

public abstract class Nave {
	
private double x;
private double y;
private int vel;
private boolean derecha;
private boolean izquierda;	
private boolean muerto;
private int vida;
protected long tiempoDisparo=0;

protected Proyectil proyectil;
private Rectangle2D entidad=new Rectangle2D.Double();
protected double ancho,largo=0;
		public Nave(int x, int y, int vel,double ancho, double largo) {
			this.x=x;
			this.y=y;
			this.vel=vel;
			this.ancho=ancho;
			this.largo=largo;
			entidad=new Rectangle2D.Double(x,y,ancho,largo);
			izquierda=false;
			derecha=false;
			muerto=false;
		}

	public abstract void disparar();
		
	public void moverDerecha() {
		x+=vel;		
	}
	public void moverIzquierda() {
		x-=vel;
	}

	public double dameX() {
		return x;
	}
	public double dameY() {
		return y;
	}
	
	
	public Proyectil dameProyectil(){
		return proyectil;
	}
	public void setVida(int vidas) {
		vida=vidas;
	}
	public int dameVida() {
		return vida;
		
	}
	public boolean muerto() {
		return muerto;
	}
	public void dañar() {
		vida-=1;
		if(vida==0) {
			muerto=true;
		}
	}
	public Rectangle2D dameEntidad() {
		return entidad;
	}
	public void actualizarEntidad() {
		entidad=new Rectangle2D.Double(this.x,this.y,ancho,largo);
	}
	public double dameTiempoDisparo() {
		return tiempoDisparo;
	}
	
}
