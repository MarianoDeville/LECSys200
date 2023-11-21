package modelo;

public class Alumno extends Persona {

	private int legajo;
	private int idCurso;
	private String fechaIngreso;
	private int estado;
	private int grupoFamiliar;
	private String fechaBaja;
	private CursoXtnd curso;
	private Asistencia asistencias[];
	private Examenes examenes[];
	private ResumenAsistencia resAsistencia;

	public Alumno() {
        
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
	
    public CursoXtnd getCurso() {
    	
		return curso;
	}

	public void setCurso(CursoXtnd curso) {
		
		this.curso = curso;
	}

	public Asistencia[] getAsistencias() {
		
		return asistencias;
	}

	public void setAsistencias(Asistencia asistencias[]) {
		
		this.asistencias = asistencias;
	}

	public Examenes[] getExamenes() {
		
		return examenes;
	}

	public void setExamenes(Examenes examenes[]) {
		
		this.examenes = examenes;
	}

	public ResumenAsistencia getResAsistencia() {
		
		return resAsistencia;
	}

	public void setResAsistencia(ResumenAsistencia resAsistencia) {
		
		this.resAsistencia = resAsistencia;
	}
}