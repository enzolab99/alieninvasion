



public class Aliens extends Nave {
private boolean izquierda;
private boolean derecha;
private long tiempoDisparo=0;
private final long tiempoRecarga=900;
private boolean disparado;
 	public Aliens(int x, int y, int vel) {
		super(x, y, vel,30,30);
		derecha=true;
		setVida(1);
		
	}
	public boolean direccionIzq() {
		return izquierda;
	}
	public boolean direccionDer() {
		return derecha;
	}
	public void direccion(int dir) {
		if(dir==0) {
			izquierda=true;
			derecha=false;
		}else if(dir==1){
			derecha=true;
			izquierda=false;
		}
	}
	@Override
	public void disparar() {
		long tiempoAhora=System.currentTimeMillis();
		Sonidos reproductor=new Sonidos();
		if(tiempoAhora-tiempoDisparo>=100) {			
			proyectil=new Proyectil(this.dameX(),this.dameY());
			proyectil.lanzar("Abajo");
			disparado=true;		
			reproductor.reproducirSonido(Aliens.class.getResource("aliensbeam.wav"));			
			tiempoDisparo=System.currentTimeMillis();		
		}else {
			reproductor.reset();
			disparado=false;		
			return;
		}
		
		
	}
}
