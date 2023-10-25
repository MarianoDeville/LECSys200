package dao;

import modelo.Alumno;
import modelo.Examenes;

public interface ExamenesDAO {

	public boolean setExamen(Alumno alumnos[]);
	public Examenes [] getExamenes(int legajo);
}
