package modelo;

public class PedidoInsumo {

	private int idCompra;
	private int cantSolicitada;
	private int estado;
	private String fechaSolicitud;
	private int idSolicitante;
	private String sectorSolicitante;
	private String nombreSolicitante;
	private CotizacionInsumo cotizacion[];
	
	public int getIdCompra() {
		
		return idCompra;
	}
	
	public void setIdCompra(int idCompra) {
		
		this.idCompra = idCompra;
	}
	
	public int getCantSolicitada() {
		
		return cantSolicitada;
	}
	
	public void setCantSolicitada(int cantSolicitada) {
		
		this.cantSolicitada = cantSolicitada;
	}
	
	public int getEstado() {
		
		return estado;
	}
	
	public void setEstado(int estado) {
		
		this.estado = estado;
	}
	
	public String getFechaSolicitud() {
		
		return fechaSolicitud;
	}
	
	public void setFechaSolicitud(String fechaSolicitud) {
		
		this.fechaSolicitud = fechaSolicitud;
	}
	
	public int getIdSolicitante() {
		
		return idSolicitante;
	}
	
	public void setIdSolicitante(int idSolicitante) {
		
		this.idSolicitante = idSolicitante;
	}
	
	public String getSectorSolicitante() {
		
		return sectorSolicitante;
	}
	
	public void setSectorSolicitante(String sectorSolicitante) {
		
		this.sectorSolicitante = sectorSolicitante;
	}
	
	public String getNombreSolicitante() {
		
		return nombreSolicitante;
	}
	
	public void setNombreSolicitante(String nombreSolicitante) {
		
		this.nombreSolicitante = nombreSolicitante;
	}

	public CotizacionInsumo[] getCotizacion() {
		
		return cotizacion;
	}

	public void setCotizacion(CotizacionInsumo cotizacion[]) {
		
		this.cotizacion = cotizacion;
	}	
}