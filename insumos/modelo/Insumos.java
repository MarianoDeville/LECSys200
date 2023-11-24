package modelo;

public class Insumos {

	private int id;
	private String nombre;
	private String descripcion;
	private String presentacion;
	private int estado;
	private int cant;
	private PedidoInsumo pedido[];
	
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

	public PedidoInsumo[] getPedido() {
		
		return pedido;
	}

	public void setPedido(PedidoInsumo pedido[]) {
		
		this.pedido = pedido;
	}
}