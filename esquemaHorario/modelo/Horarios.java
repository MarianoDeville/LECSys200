package modelo;

import java.sql.Time;

public class Horarios {
	
	private int id;
	private int dia;
	private Time hora;
	private int duración;
	private int idPertenece;
	private int granularidad;
	
	public Horarios() {
		
	}
	
	public Horarios(int id, int dia, Time hora, int duracion, int pertenece, int granularidad) {
		
		this.id = id;
		this.dia = dia;
		this.hora = hora;
		this.duración = duracion;
		this.idPertenece = pertenece;
		this.granularidad = granularidad;
	}

	public int getId() {
		
		return id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}
	
	public int getDia() {
		
		return dia;
	}
	
	public void setDia(int dia) {
		
		this.dia = dia;
	}
	
	public Time getHora() {
		
		return hora;
	}
	
	public void setHora(Time hora) {
		
		this.hora = hora;
	}
	
	public int getDuración() {
		
		return duración;
	}
	
	public void setDuración(int duración) {
		
		this.duración = duración;
	}
	
	public int getIdPertenece() {
		
		return idPertenece;
	}
	
	public void setIdPertenece(int idPertenece) {
		
		this.idPertenece = idPertenece;
	}
	
	public int getGranularidad() {
		
		return granularidad;
	}
	
	public void setGranularidad(int granularidad) {
		
		this.granularidad = granularidad;
	}
}
