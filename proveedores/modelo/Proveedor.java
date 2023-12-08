package modelo;

public class Proveedor {

	private int id;
	private String nombre;
	private String direccion;
	private String cuit;
	private String tipo;
	private int estado;
	private int servicio;
	private String comentario;
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
	
	public int getServicio() {
		
		return servicio;
	}

	public void setServicio(int servicio) {
		
		this.servicio = servicio;
	}

	public String getComentario() {
		
		return comentario;
	}

	public void setComentario(String comentario) {
		
		this.comentario = comentario;
	}
	
	public Contacto[] getContactos() {
		
		return contactos;
	}

	public void setContactos(Contacto contactos[]) {
		
		this.contactos = contactos;
	}
}