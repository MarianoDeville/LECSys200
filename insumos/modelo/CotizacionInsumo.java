package modelo;

public class CotizacionInsumo {

	private int idPresupuesto;
	private float precio;
	private String fechaPresupuesto;
	private Proveedor proveedor;
	
	public int getIdPresupuesto() {
		
		return idPresupuesto;
	}
	
	public void setIdPresupuesto(int idPresupuesto) {
		
		this.idPresupuesto = idPresupuesto;
	}
	
	public float getPrecio() {
		
		return precio;
	}
	
	public void setPrecio(float precio) {
		
		this.precio = precio;
	}
	
	public String getFechaPresupuesto() {
		
		return fechaPresupuesto;
	}
	
	public void setFechaPresupuesto(String fechaPresupuesto) {
		
		this.fechaPresupuesto = fechaPresupuesto;
	}
	
	public Proveedor getProveedor() {
		
		return proveedor;
	}
	
	public void setProveedor(Proveedor proveedor) {
		
		this.proveedor = proveedor;
	}	
}