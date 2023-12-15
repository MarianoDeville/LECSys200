package dao;

import modelo.Estadisticas;

public interface EstadisticasDAO {

	public boolean isNuevoMes(boolean forzar);
	public Estadisticas [] getEstadisticasAnuales(String año);
	public String[] getListadoAños();
	public Estadisticas getResumenMensual();
	public Estadisticas getResumenAnual();
}