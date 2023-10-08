package dao;

import modelo.Curso;
import modelo.CursoXtnd;

public interface CursosDAO {

	public CursoXtnd [] getListado(String idCurso);
	
	
	
	
	public boolean [][] getTablaSemanal(int idCurso, int legajo, int aula);
	public String [][] buscarDiasCurso(String idCurso);
	
	public boolean isExamenCargado(String idCurso, String examen);
	public boolean setCurso(String año, String nivel, String idProfesor, int aula, String valorCuota, String horarios[][]);
	public boolean setActualizarCurso(String idCurso, String idProfesor, int aula, String valorCuota, int estado, String horarios[][]);
	public String getValorCuota(String idCurso);
}