

import java.awt.geom.Rectangle2D;

public  class Proyectil {
private double x;
private double y;
private int velocidad;
private Rectangle2D entidad=new Rectangle2D.Double(x,y,3,13);
	 public  Proyectil(double x, double y) {
		this.x=x;
		this.y=y;
		velocidad=6;
		
	}
	public  void lanzar(String direccion) {
		if(direccion.equalsIgnoreCase("arriba")) {
			y-=velocidad;
			actualizarEntidad();
		}else {
			y+=velocidad;
			actualizarEntidad();
		}
		

	}
	public double dameX() {
		return x;
	}
	public double dameY() {
		return y;
	}
	public void actualizarEntidad() { 
		entidad=new Rectangle2D.Double(x,y,3,13);
	}
	public Rectangle2D dameEntidad() {
		return entidad;
	}

}
