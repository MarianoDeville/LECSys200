package modelo;

public class Proveedor {

	private int id;
	private String nombre;
	private String direccion;
	private String cuit;
	private String tipo;
	private int estado;
	private Contacto contactos[];
	
	public int getId() {
		
		return id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}
	
	public String getNombre() {
		
		return nombre;
	}
	
	public void setNombre(String nombre) {
		
		this.nombre = nombre;
	}
	
	public String getDireccion() {
		
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		
		this.direccion = direccion;
	}
	
	public String getCuit() {
		
		return cuit;
	}
	
	public void setCuit(String cuit) {
		
		this.cuit = cuit;
	}
	
	public String getTipo() {
		
		return tipo;
	}
	
	public void setTipo(String tipo) {
		
		this.tipo = tipo;
	}
	
	public int getEstado() {
		
		return estado;
	}
	
	public void setEstado(int estado) {
		
		this.estado = estado;
	}

	public Contacto[] getContactos() {
		
		return contactos;
	}

	public void setContactos(Contacto contactos[]) {
		
		this.contactos = contactos;
	}
}