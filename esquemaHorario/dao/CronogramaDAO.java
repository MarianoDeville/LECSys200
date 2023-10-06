package dao;

import modelo.Horarios;

public interface CronogramaDAO {

	public int getGranularidad();
	public boolean [][] getCronogramaDias(int idEmpleado);
	public boolean [][] getCronogramaDias(String idCurso, int idProfesor, int aula);
	public boolean setCronogramaDias(Horarios horarios[]);
}
