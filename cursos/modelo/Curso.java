package modelo;

public class Curso {

	private int id;
	private String año;
	private String nivel;
	private int legajoProfesor;
	private int estado;
	private int aula;
	
	public Curso() {
		
	}

	public Curso(int id, String año, String nivel, int profesor, int estado, int aula) {
		
		this.id = id;
		this.año = año;
		this.nivel = nivel;
		this.legajoProfesor = profesor;
		this.estado = estado;
		this.aula = aula;
	}
	
	public int getId() {
		
		return id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}
	
	public String getAño() {
		
		return año;
	}
	
	public void setAño(String año) {
		
		this.año = año;
	}
	
	public String getNivel() {
		
		return nivel;
	}
	
	public void setNivel(String nivel) {
		
		this.nivel = nivel;
	}
	
	public int getLegajoProfesor() {
		
		return legajoProfesor;
	}
	
	public void setLegajoProfesor(int legajoProfesor) {
		
		this.legajoProfesor = legajoProfesor;
	}
	
	public int getEstado() {
		
		return estado;
	}
	
	public void setEstado(int estado) {
		
		this.estado = estado;
	}
	
	public int getAula() {
		
		return aula;
	}
	
	public void setAula(int aula) {
		
		this.aula = aula;
	}
}
