package modelo;

public class Actividad {

	private int id;
	private String idUsuario;
	private String fecha;
	private String hora;
	private String accion;
	private String modulo;
	private String ip;
	private long tiempo;
	private String nombre;
	
	public Actividad() {
		
	}
	
	public Actividad(String idUsuario, String accion, String modulo, String ip, long tiempo) {
		
		this.idUsuario = idUsuario;
		this.accion = accion;
		this.modulo = modulo;
		this.ip = ip;
		this.tiempo = tiempo;
	}
	
	public int getId() {
		
		return id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}
	
	public String getIdUsuario() {
		
		return idUsuario;
	}
	
	public void setIdUsuario(String idUsuario) {
		
		this.idUsuario = idUsuario;
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
	
	public String getAccion() {
		
		return accion;
	}
	
	public void setAccion(String accion) {
		
		this.accion = accion;
	}
	
	public String getModulo() {
		
		return modulo;
	}
	
	public void setModulo(String modulo) {
		
		this.modulo = modulo;
	}
	
	public String getIp() {
		
		return ip;
	}
	
	public void setIp(String ip) {
		
		this.ip = ip;
	}
	
	public long getTiempo() {
		
		return tiempo;
	}
	
	public void setTiempo(long tiempo) {
		
		this.tiempo = tiempo;
	}

	public String getNombre() {
		
		return nombre;
	}

	public void setNombre(String nombre) {
		
		this.nombre = nombre;
	}
}
