package modelo;

public class Asistencia {

	private int id;
	private String fecha;
	private int estado;

	public Asistencia() {
		
	}
	
	public Asistencia(int id, String fecha, int estado) {
		
		this.id = id;
		this.fecha = fecha;
		this.estado = estado;
	}

	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		
		this.id = id;
	}

	public String getFecha() {
		
		return fecha;
	}

	public void setFecha(String fecha) {
		
		this.fecha = fecha;
	}

	public int getEstado() {
		
		return estado;
	}

	public void setEstado(int estado) {
		
		this.estado = estado;
	}
}