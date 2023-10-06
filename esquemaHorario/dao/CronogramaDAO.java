package dao;

import modelo.Horarios;

public interface CronogramaDAO {

	public int getGranularidad();
	public boolean [][] getTablaSemanal(int legajo);
	public boolean setCronograma(Horarios horarios[]);
}
