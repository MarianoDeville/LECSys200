package modelo;

public class Presupuesto {

	private int idPresupuesto;
	private int idPedido;
	private String fecha;
	private String Validez;
	private Insumo insumos[];
	private Proveedor proveedores[];

	public int getIdPresupuesto() {
		
		return idPresupuesto;
	}

	public void setIdPresupuesto(int idPresupuesto) {
		
		this.idPresupuesto = idPresupuesto;
	}
	
	public int getIdPedido() {
		
		return idPedido;
	}
	
	public void setIdPedido(int idPedido) {
		
		this.idPedido = idPedido;
	}
	
	public String getFecha() {
		
		return fecha;
	}
	
	public void setFecha(String fecha) {
		
		this.fecha = fecha;
	}
	
	public String getValidez() {
		return Validez;
	}
	
	public void setValidez(String validez) {
		
		Validez = validez;
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
