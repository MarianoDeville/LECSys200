package dao;

import modelo.Estadisticas;

public interface EstadisticasDAO {

	public boolean isNuevoMes();
	public Estadisticas [] getEstadisticasAnuales(String a�o);
	public String[] getListadoA�os();
	public Estadisticas getResumenMensual();
}
