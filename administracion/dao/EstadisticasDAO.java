package dao;

import modelo.Estadisticas;

public interface EstadisticasDAO {

	public boolean isNuevoMes();
	public Estadisticas [] getEstadisticasAnuales(String año);
	public String[] getListadoAños();
	public Estadisticas getResumenMensual();
}
