package dao;

import modelo.Estadisticas;

public interface EstadisticasDAO {

	public boolean isNuevoMes(boolean forzar);
	public Estadisticas [] getEstadisticasAnuales(String a�o);
	public String[] getListadoA�os();
	public Estadisticas getResumenMensual();
	public Estadisticas getResumenAnual();
}