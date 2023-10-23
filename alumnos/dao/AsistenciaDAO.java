package dao;

import modelo.Asistencia;
import modelo.ResumenAsistencia;

public interface AsistenciaDAO {

	
	
	
	
	
	public boolean setAsistencia(Asistencia asistencia[]);
	public ResumenAsistencia getInfoAsistencia(String idAlumno);
	public boolean isAsistenciaTomada(String idCurso, boolean reducido);
	public String [][] getAsistencias(String idCurso, boolean reducido, int mes);
}
