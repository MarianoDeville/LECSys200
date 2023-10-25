package modelo;

public class Examenes {

	private int id;
	private String fecha;
	private String tipo;
	private int nota;
	private int legajoProfesor;
	private int idCruso;
	
	public Examenes() {
		
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

	public String getTipo() {
		
		return tipo;
	}

	public void setTipo(String tipo) {
		
		this.tipo = tipo;
	}

	public int getNota() {
		
		return nota;
	}

	public void setNota(int nota) {
		
		this.nota = nota;
	}

	public int getLegajoProfesor() {
		
		return legajoProfesor;
	}

	public void setLegajoProfesor(int legajoProfesor) {
		
		this.legajoProfesor = legajoProfesor;
	}

	public int getIdCruso() {
		
		return idCruso;
	}

	public void setIdCruso(int idCruso) {
		
		this.idCruso = idCruso;
	}
}