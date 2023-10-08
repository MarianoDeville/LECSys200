package modelo;

public class CursoXtnd extends Curso{

	private String nombreProfesor;
	private float precio;
	private String diasCursado;

	public CursoXtnd() {
		
	}
	
	public CursoXtnd(int id, String año, String nivel, int profesor, int estado, 
					 int aula, String nombreApellido, float precio, String dias) {
		
		super(id, año, nivel, profesor, estado, aula);
		this.nombreProfesor = nombreApellido;
		this.precio = precio;
		this.diasCursado = dias;
	}
	
	public String getNombreProfesor() {
		
		return nombreProfesor;
	}
	
	public void setNombreProfesor(String nombreProfesor) {
		
		this.nombreProfesor = nombreProfesor;
	}
	
	public float getPrecio() {
		
		return precio;
	}
	
	public void setPrecio(float precio) {
		
		this.precio = precio;
	}
	
	public String getDiasCursado() {
		
		return diasCursado;
	}

	public void setDiasCursado(String diasCursado) {
		
		this.diasCursado = diasCursado;
	}
}
