package modelo;

public class Usuario {

	private int id;
	private int dni;
	private String usrName;
	private String nombre;
	private String contrase�a;
	private String nivelAcceso;
	private int estado;
	private int cambioContrase�a;
	
    public Usuario() {

    	id = 0;
    	dni = 0;
    	usrName = "";
    	contrase�a = "";
    	nivelAcceso = "";
    	estado = 0;
    	cambioContrase�a = 0;
    }
    
	public Usuario(int id, int dni, String nombre, String contrase�a, String nivelAcceso, int estado, int cambioContrase�a) {
		
		this.id = id;
		this.dni = dni;
		this.usrName = nombre;
		this.contrase�a = contrase�a;
		this.nivelAcceso = nivelAcceso;
		this.estado = estado;
		this.cambioContrase�a = cambioContrase�a;
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

	public String getContrase�a() {
		
		return contrase�a;
	}
	
	public void setContrase�a(String contrase�a) {
		
		this.contrase�a = contrase�a;
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

	public int getCambioContrase�a() {
		
		return cambioContrase�a;
	}

	public void setCambioContrase�a(int cambioContrase�a) {
		
		this.cambioContrase�a = cambioContrase�a;
	}
}
