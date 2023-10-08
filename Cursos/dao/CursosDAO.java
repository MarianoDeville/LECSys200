package dao;

import modelo.CursoXtnd;

public interface CursosDAO {

	public CursoXtnd [] getListado(String idCurso);
	public boolean [][] getTablaSemanal(int idCurso, int legajo, int aula);
	public boolean setCurso(CursoXtnd curso);
	public boolean update(CursoXtnd curso);
	
	
	public String [][] buscarDiasCurso(String idCurso);
	
	public boolean isExamenCargado(String idCurso, String examen);
	
	
	public String getValorCuota(String idCurso);
}