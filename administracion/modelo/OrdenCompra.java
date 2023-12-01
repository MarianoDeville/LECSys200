package modelo;

public class OrdenCompra {

	private int id;
	private String fecha;
	private int idAutoriza;
	private float montoTotal;
	private String nombreSolicitante;
	private String nombreAutoriza;
	private String fechaPedido;
	private Presupuesto presupuesto;
	private Pago pago;

	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		
		this.id = id;
	}

	public String getFecha() {
		
		return fecha;
	}

	public void setFecha(String fecha) {
		
		this.fecha = fecha;
	}

	public int getIdAutoriza() {
		
		return idAutoriza;
	}

	public void setIdAutoriza(int idAutoriza) {
		
		this.idAutoriza = idAutoriza;
	}

	public float getMontoTotal() {
		
		return montoTotal;
	}

	public void setMontoTotal(float montoTotal) {
		
		this.montoTotal = montoTotal;
	}

	public String getNombreSolicitante() {
		
		return nombreSolicitante;
	}

	public void setNombreSolicitante(String nombreSolicitante) {
		
		this.nombreSolicitante = nombreSolicitante;
	}

	public String getNombreAutoriza() {
		
		return nombreAutoriza;
	}

	public void setNombreAutoriza(String nombreAutoriza) {
		
		this.nombreAutoriza = nombreAutoriza;
	}

	public String getFechaPedido() {
		
		return fechaPedido;
	}

	public void setFechaPedido(String fechaPedido) {
		
		this.fechaPedido = fechaPedido;
	}
	
	public Presupuesto getPresupuesto() {
		
		return presupuesto;
	}

	public void setPresupuesto(Presupuesto presupuesto) {
		
		this.presupuesto = presupuesto;
	}

	public Pago getPago() {
		
		return pago;
	}

	public void setPago(Pago pago) {
		
		this.pago = pago;
	}
}
