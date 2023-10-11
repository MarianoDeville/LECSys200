package modelo;

public class GrupoFamiliar {

	private int id;
	private String nombre;
	private int cantIntegrantes;
	private int deuda;
	private int estado;
	private int descuento;
	private String email;
	
	public GrupoFamiliar(int id, String nombre, int cantIntegrantes, int deuda, int estado, int descuento, String email) {
		
		this.id = id;
		this.nombre = nombre;
		this.cantIntegrantes = cantIntegrantes;
		this.deuda = deuda;
		this.estado = estado;
		this.descuento = descuento;
		this.email = email;
	}
	
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
	
	public int getCantIntegrantes() {
		
		return cantIntegrantes;
	}
	
	public void setCantIntegrantes(int cantIntegrantes) {
		
		this.cantIntegrantes = cantIntegrantes;
	}
	
	public int getDeuda() {
		
		return deuda;
	}
	
	public void setDeuda(int deuda) {
		
		this.deuda = deuda;
	}
	
	public int getEstado() {
		
		return estado;
	}
	
	public void setEstado(int estado) {
		
		this.estado = estado;
	}
	
	public int getDescuento() {
		
		return descuento;
	}
	
	public void setDescuento(int descuento) {
		
		this.descuento = descuento;
	}
	
	public String getEmail() {
		
		return email;
	}
	
	public void setEmail(String email) {
		
		this.email = email;
	}
}
