package modelo;

public class Pago {

	private long id;
	private String concepto;
	private String fecha;
	private String hora;
	private float monto;
	private String factura;
	private String comentario;
	private String formaPago;
	private Empleado empleado;

	public long getId() {
		
		return id;
	}

	public void setId(long id) {
		
		this.id = id;
	}
	
	public String getConcepto() {
		
		return concepto;
	}

	public void setConcepto(String concepto) {
		
		this.concepto = concepto;
	}

	public String getFecha() {
		
		return fecha;
	}

	public void setFecha(String fecha) {
		
		this.fecha = fecha;
	}

	public String getHora() {
		
		return hora;
	}

	public void setHora(String hora) {
		
		this.hora = hora;
	}

	public float getMonto() {
		
		return monto;
	}

	public void setMonto(float monto) {
		
		this.monto = monto;
	}

	public String getFactura() {
		
		return factura;
	}

	public void setFactura(String factura) {
		
		this.factura = factura;
	}

	public String getComentario() {
		
		return comentario;
	}

	public void setComentario(String comentario) {
		
		this.comentario = comentario;
	}

	public String getFormaPago() {
		
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		
		this.formaPago = formaPago;
	}

	public Empleado getEmpleado() {
		
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		
		this.empleado = empleado;
	}
}
