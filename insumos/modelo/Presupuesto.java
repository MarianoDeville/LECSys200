package modelo;

public class Presupuesto {

	private long idPresupuesto;
	private long idPedido;
	private String fecha;
	private String validez;
	private int estado;
	private Insumo insumos[];
	private Proveedor proveedores[];

	public long getIdPresupuesto() {
		
		return idPresupuesto;
	}

	public void setIdPresupuesto(long idPresupuesto) {
		
		this.idPresupuesto = idPresupuesto;
	}
	
	public long getIdPedido() {
		
		return idPedido;
	}
	
	public void setIdPedido(long idPedido) {
		
		this.idPedido = idPedido;
	}
	
	public String getFecha() {
		
		return fecha;
	}
	
	public void setFecha(String fecha) {
		
		this.fecha = fecha;
	}
	
	public String getValidez() {
		return validez;
	}
	
	public void setValidez(String validez) {
		
		this.validez = validez;
	}

	public int getEstado() {
		
		return estado;
	}

	public void setEstado(int estado) {
		
		this.estado = estado;
	}
	
	public Insumo[] getInsumos() {
		
		return insumos;
	}
	
	public void setInsumos(Insumo[] insumos) {
		
		this.insumos = insumos;
	}

	public Proveedor[] getProveedores() {
		
		return proveedores;
	}

	public void setProveedores(Proveedor proveedores[]) {
		
		this.proveedores = proveedores;
	}
}
