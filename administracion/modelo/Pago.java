package modelo;

public class Pago {

	private int id;
	private int idProveedor;
	private int idEmpleado;
	private String concepto;
	private String fecha;
	private String hora;
	private float monto;
	private String factura;
	private String comentario;
	private String formaPago;
	private String nombre;

	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		
		this.id = id;
	}

	public int getIdProveedor() {
		
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		
		this.idProveedor = idProveedor;
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

	public int getIdEmpleado() {
		
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		
		this.idEmpleado = idEmpleado;
	}

	public String getNombre() {
		
		return nombre;
	}

	public void setNombre(String nombre) {
		
		this.nombre = nombre;
	}
}
