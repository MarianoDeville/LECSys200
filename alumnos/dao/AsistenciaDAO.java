package dao;

import modelo.Alumno;
import modelo.ResumenAsistencia;

public interface AsistenciaDAO {

	public boolean setAsistencia(Alumno alumnos[]);
	public Alumno [] getListado(int idCurso, boolean reducido, int mes);
	public ResumenAsistencia getInfoAsistencia(int legajo);
	public boolean isAsistenciaTomada(int idCurso, boolean reducido);
}
