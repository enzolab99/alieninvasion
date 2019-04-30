

public class Jugador extends Nave {
private long tiempoDisparo=0;
private final long tiempoRecarga=400;
private boolean disparado;
private Sonidos reproductor=new Sonidos();
	public Jugador(int x, int y, int vel) {		
		super(x, y, vel,70,70);
		setVida(5);
		disparado=false;
		
	}

	@Override
	public void disparar() {
		
		long tiempoAhora=System.currentTimeMillis();
		
		if(tiempoAhora-tiempoDisparo>=500) {
			proyectil=new Proyectil(this.dameX(),this.dameY());
			proyectil.lanzar("Arriba");
			if(!this.muerto()) { 
				reproductor.reproducirSonido(Jugador.class.getResource("8bitlaser.wav"));
			}
			disparado=true;
			tiempoDisparo=System.currentTimeMillis();
		}else {
			disparado=false;
			return;
		}
		
		
	}
	public long returnTiempo() {
		return tiempoDisparo;
	}
    public boolean estadoProyectil() {
    	return disparado;
    }
	
}
