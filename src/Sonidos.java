

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Sonidos {
private Clip musica;	
	public  void reproducirSonido(URL ubicacion) {
		try {
	
				AudioInputStream iS=AudioSystem.getAudioInputStream(ubicacion);
				 DataLine.Info info = new DataLine.Info(Clip.class, iS.getFormat());
				musica=(Clip) AudioSystem.getLine(info);
				musica.open(iS);
				musica.loop(0);
				musica.start();
			
		}catch(Exception e) {
			
		}
	}
	public void reset() {
		musica.close();
	}
	public void parar() {
		musica.stop();
	}
}
