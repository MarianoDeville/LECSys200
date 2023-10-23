package modelo;

public class Asistencia {

	private int id;
	private int legajo;
	private String fecha;
	private int estado;
	private int idCurso;
	private String nombre;
	private String apellido;
	
	public Asistencia() {
		
	}

	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		
		this.id = id;
	}

	public int getLegajo() {
		
		return legajo;
	}

	public void setLegajo(int legajo) {
		
		this.legajo = legajo;
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

	public int getIdCurso() {
		
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		
		this.idCurso = idCurso;
	}

	public String getNombreAlumno() {
		
		return nombre;
	}

	public void setNombreAlumno(String nombreAlumno) {
		
		this.nombre = nombreAlumno;
	}

	public String getApellido() {
		
		return apellido;
	}

	public void setApellido(String apellido) {
		
		this.apellido = apellido;
	}
}