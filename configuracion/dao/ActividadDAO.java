package dao;

import modelo.Actividad;

public interface ActividadDAO {

	public void setActividad(Actividad actividad);
	public Actividad [] getActividad(String mes, String año);
}
