package modelo;

public class Alumno extends Persona {

	private int legajo;
	private int idCurso;
	private String fechaIngreso;
	private int estado;
	private int grupoFamiliar;
	private String fechaBaja;
	private String nivel;
	private String año;
	
    public Alumno() {
        
    }
    
	public Alumno(int legajo, int idCurso, String fechaIngreso, int estado, int grupoFamiliar, String fechaBaja,
			String dni, String nombre, String apellido, String direccion, String fechaNacimiento, String telefono,
			String email) {
		
		super(dni, nombre, apellido, direccion, fechaNacimiento, telefono, email);
		this.legajo = legajo;
		this.idCurso = idCurso;
		this.fechaIngreso = fechaIngreso;
		this.estado = estado;
		this.grupoFamiliar = grupoFamiliar;
		this.fechaBaja = fechaBaja;
	}

	public int getLegajo() {
		
		return legajo;
	}

	public void setLegajo(int legajo) {
		
		this.legajo = legajo;
	}

	public int getIdCurso() {
		
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		
		this.idCurso = idCurso;
	}

	public String getFechaIngreso() {
		
		return fechaIngreso;
	}

	public void setFechaIngreso(String fechaIngreso) {
		
		this.fechaIngreso = fechaIngreso;
	}

	public int getEstado() {
		
		return estado;
	}

	public void setEstado(int estado) {
		
		this.estado = estado;
	}

	public int getGrupoFamiliar() {
		
		return grupoFamiliar;
	}

	public void setGrupoFamiliar(int grupoFamiliar) {
		
		this.grupoFamiliar = grupoFamiliar;
	}

	public String getFechaBaja() {
		
		return fechaBaja;
	}

	public void setFechaBaja(String fechaBaja) {
		
		this.fechaBaja = fechaBaja;
	}

	public String getNivel() {
		
		return nivel;
	}

	public void setNivel(String nivel) {
		
		this.nivel = nivel;
	}

	public String getAño() {
		
		return año;
	}

	public void setAño(String año) {
		
		this.año = año;
	}
}
