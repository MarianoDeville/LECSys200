package modelo;

import java.sql.Time;

public class Cobros {

	private int id;
	private int idGrupoFamiliar;
	private String nombre;
	private String concepto;
	private String fecha;
	private Time hora;
	private float monto;
	private String factura;
	private int idEstadisticas;
	
	public Cobros() {
		
	}
	
	public Cobros(int id, int idGrupoFamiliar, String nombre, String concepto, String fecha, 
					Time hora, float monto, String factura, int idEstadisticas) {
		
		this.id = id;
		this.idGrupoFamiliar = idGrupoFamiliar;
		this.nombre = nombre;
		this.concepto = concepto;
		this.fecha = fecha;
		this.hora = hora;
		this.monto = monto;
		this.factura = factura;
		this.idEstadisticas = idEstadisticas;
	}

	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		
		this.id = id;
	}

	public int getIdGrupoFamiliar() {
		
		return idGrupoFamiliar;
	}

	public void setIdGrupoFamiliar(int idGrupoFamiliar) {
		
		this.idGrupoFamiliar = idGrupoFamiliar;
	}

	public String getNombre() {
		
		return nombre;
	}

	public void setNombre(String nombre) {
		
		this.nombre = nombre;
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

	public Time getHora() {
		
		return hora;
	}

	public void setHora(Time hora) {
		
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

	public int getIdEstadisticas() {
		
		return idEstadisticas;
	}

	public void setIdEstadisticas(int idEstadisticas) {
		
		this.idEstadisticas = idEstadisticas;
	}
}
