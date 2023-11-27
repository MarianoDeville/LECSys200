package modelo;

public class PedidoInsumo {

	private int idCompra;
	private int estado;
	private String fechaSolicitud;
	private int idSolicitante;
	private String sectorSolicitante;
	private String nombreSolicitante;
	private String autoriza;
	private Insumo insumos[];
	
	public PedidoInsumo() {
		
	}
	
	public PedidoInsumo(PedidoInsumo pedido) {
	
		idCompra = pedido.getIdCompra();
		estado = pedido.getEstado();
		fechaSolicitud = pedido.getFechaSolicitud();
		idSolicitante = pedido.getIdSolicitante();
		sectorSolicitante = pedido.getSectorSolicitante();
		nombreSolicitante = pedido.getNombreSolicitante();
		insumos = pedido.getInsumos();
	}
	
	public int getIdCompra() {
		
		return idCompra;
	}
	
	public void setIdCompra(int idCompra) {
		
		this.idCompra = idCompra;
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

	public String getAutoriza() {
		
		return autoriza;
	}

	public void setAutoriza(String autoriza) {
		
		this.autoriza = autoriza;
	}
	public Insumo[] getInsumos() {
		
		return insumos;
	}

	public void setInsumos(Insumo insumos[]) {
		
		this.insumos = insumos;
	}

}