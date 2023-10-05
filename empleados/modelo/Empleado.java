package modelo;

public class Empleado extends Persona{

	private int legajo;
	private float salario;
	private String fechaIngreso;
	private int estado;
	private String sector;
	private String cargo;
	private String relacion;
	private String fechaBaja;
	
    public Empleado() {

    	super("", "", "", "", "", "", "");
    	legajo = 0;
    	salario = 0;
    	fechaIngreso = "";
    	sector = "";
    	cargo = "";
    	relacion = "";
    	fechaBaja = "";
    }
    
	public Empleado(int legajo, float salario, String fechaIngreso, int estado, String sector, String cargo, 
			String tipo, String fechaBaja, String dni, String nombre, String apellido, String direccion, 
			String fechaNacimiento, String telefono, String email) {
		
		super(dni, nombre, apellido, direccion, fechaNacimiento, telefono, email);
		this.legajo = legajo;
		this.salario = salario;
		this.fechaIngreso = fechaIngreso;
		this.estado = estado;
		this.sector = sector;
		this.cargo = cargo;
		this.relacion = tipo;
		this.fechaBaja = fechaBaja;
	}

	public int getLegajo() {
		
		return legajo;
	}

	public void setLegajo(int legajo) {
		
		this.legajo = legajo;
	}

	public float getSalario() {
		
		return salario;
	}

	public void setSalario(float salario) {
		
		this.salario = salario;
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

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		
		this.sector = sector;
	}

	public String getCargo() {
		
		return cargo;
	}

	public void setCargo(String cargo) {
		
		this.cargo = cargo;
	}

	public String getRelacion() {
		
		return relacion;
	}

	public void setRelacion(String tipo) {
		
		this.relacion = tipo;
	}

	public String getFechaBaja() {
		
		return fechaBaja;
	}

	public void setFechaBaja(String fechaBaja) {
		
		this.fechaBaja = fechaBaja;
	}
}
