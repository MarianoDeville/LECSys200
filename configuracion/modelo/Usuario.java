package modelo;

public class Usuario {

	private int id;
	private int dni;
	private String usrName;
	private String nombre;
	private String contraseña;
	private String nivelAcceso;
	private int estado;
	private int cambioContraseña;
	
    public Usuario() {

    	id = 0;
    	dni = 0;
    	usrName = "";
    	contraseña = "";
    	nivelAcceso = "";
    	estado = 0;
    	cambioContraseña = 0;
    }
    
	public Usuario(int id, int dni, String nombre, String contraseña, String nivelAcceso, int estado, int cambioContraseña) {
		
		this.id = id;
		this.dni = dni;
		this.usrName = nombre;
		this.contraseña = contraseña;
		this.nivelAcceso = nivelAcceso;
		this.estado = estado;
		this.cambioContraseña = cambioContraseña;
	}

	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		
		this.id = id;
	}
	public int getDni() {
		
		return dni;
	}

	public void setDni(int dni) {
		
		this.dni = dni;
	}

	public String getUsrName() {
		
		return usrName;
	}

	public void setUsrName(String nombre) {
		
		this.usrName = nombre;
	}

	public String getNombre() {
		
		return nombre;
	}

	public void setNombre(String nombre) {
		
		this.nombre = nombre;
	}

	public String getContraseña() {
		
		return contraseña;
	}
	
	public void setContraseña(String contraseña) {
		
		this.contraseña = contraseña;
	}

	public String getNivelAcceso() {
		
		return nivelAcceso;
	}

	public void setNivelAcceso(String nivelAcceso) {
		
		this.nivelAcceso = nivelAcceso;
	}

	public int getEstado() {
		
		return estado;
	}

	public void setEstado(int estado) {
		
		this.estado = estado;
	}

	public int getCambioContraseña() {
		
		return cambioContraseña;
	}

	public void setCambioContraseña(int cambioContraseña) {
		
		this.cambioContraseña = cambioContraseña;
	}
}
