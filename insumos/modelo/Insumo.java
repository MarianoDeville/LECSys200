package modelo;

public class Insumo {

	private long id;
	private String nombre;
	private String descripcion;
	private String presentacion;
	private int estado;
	private int cant;
	private int cantSolicitada;
	private float precio;
	
	public Insumo() {
		
	}
	
	public Insumo(Insumo insumo) {
	
		id = insumo.getId();
		nombre = insumo.getNombre();
		descripcion = insumo.getDescripcion();
		presentacion = insumo.getPresentacion();
		estado = insumo.getEstado();
		cant = insumo.getCant();
		cantSolicitada = insumo.getCantSolicitada();
		precio = insumo.getPrecio();
	}
	
	public long getId() {
		
		return id;
	}
	
	public void setId(long id) {
		
		this.id = id;
	}
	
	public String getNombre() {
		
		return nombre;
	}
	
	public void setNombre(String nombre) {
		
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		
		this.descripcion = descripcion;
	}
	
	public String getPresentacion() {
		
		return presentacion;
	}
	
	public void setPresentacion(String presentacion) {
		
		this.presentacion = presentacion;
	}
	
	public int getEstado() {
		
		return estado;
	}
	
	public void setEstado(int estado) {
		
		this.estado = estado;
	}
	
	public int getCant() {
		
		return cant;
	}
	
	public void setCant(int cant) {
		
		this.cant = cant;
	}

	public int getCantSolicitada() {
		
		return cantSolicitada;
	}

	public void setCantSolicitada(int cantSolicitada) {
		
		this.cantSolicitada = cantSolicitada;
	}

	public float getPrecio() {
		
		return precio;
	}

	public void setPrecio(float precio) {
		
		this.precio = precio;
	}
}