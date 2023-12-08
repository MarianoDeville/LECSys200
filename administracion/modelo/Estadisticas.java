package modelo;

public class Estadisticas {

	private int id;
	private int mes;
	private int cantidadEstudientas;
	private int faltasEstudiantes;
	private int cantidadEmpleados;
	private int faltasEmpleados;
	private float ingresos;
	private float sueldos;
	private float compras;
	private float servicios;
	private String fecha;
	private Situacion conDescuento;
	private Situacion sinDescuento;

	public Estadisticas() {
		
		this.id = 0;
		this.mes = 0;
		this.cantidadEstudientas = 0;
		this.faltasEstudiantes = 0;
		this.cantidadEmpleados = 0;
		this.faltasEmpleados = 0;
		this.ingresos = 0;
		this.sueldos = 0;
		this.compras = 0;
		this.servicios = 0;
	}
	
	public int getId() {
		
		return id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}
	
	public int getMes() {
		
		return mes;
	}
	
	public void setMes(int mes) {
		
		this.mes = mes;
	}

	public int getCantidadEstudientas() {
		
		return cantidadEstudientas;
	}

	public void setCantidadEstudientas(int cantidadEstudientas) {
		
		this.cantidadEstudientas = cantidadEstudientas;
	}

	public int getFaltasEstudiantes() {
		
		return faltasEstudiantes;
	}

	public void setFaltasEstudiantes(int faltasEstudiantes) {
		
		this.faltasEstudiantes = faltasEstudiantes;
	}

	public int getCantidadEmpleados() {
		
		return cantidadEmpleados;
	}

	public void setCantidadEmpleados(int cantidadEmpleados) {
		
		this.cantidadEmpleados = cantidadEmpleados;
	}

	public int getFaltasEmpleados() {
		
		return faltasEmpleados;
	}

	public void setFaltasEmpleados(int faltasEmpleados) {
		
		this.faltasEmpleados = faltasEmpleados;
	}

	public float getIngresos() {
		
		return ingresos;
	}

	public void setIngresos(float ingresos) {
		
		this.ingresos = ingresos;
	}

	public float getSueldos() {
		
		return sueldos;
	}

	public void setSueldos(float sueldos) {
		
		this.sueldos = sueldos;
	}

	public float getCompras() {
		
		return compras;
	}

	public void setCompras(float compras) {
		
		this.compras = compras;
	}

	public float getServicios() {
		
		return servicios;
	}

	public void setServicios(float servicios) {
		
		this.servicios = servicios;
	}

	public String getFecha() {
		
		return fecha;
	}

	public void setFecha(String fecha) {
		
		this.fecha = fecha;
	}

	public Situacion getConDescuento() {
		
		return conDescuento;
	}

	public void setConDescuento(Situacion conDescuento) {
		
		this.conDescuento = conDescuento;
	}

	public Situacion getSinDescuento() {
		
		return sinDescuento;
	}

	public void setSinDescuento(Situacion sinDescuento) {
		
		this.sinDescuento = sinDescuento;
	}
}