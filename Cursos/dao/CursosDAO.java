package dao;

public interface CursosDAO {

	public boolean getCronogramaDias(String idCurso, int idProfesor, int aula);
	public String [][] buscarDiasCurso(String idCurso);
	public String [][] getListado(String idCurso);
	public boolean isExamenCargado(String idCurso, String examen);
	public boolean setCurso(String año, String nivel, String idProfesor, int aula, String valorCuota, String horarios[][]);
	public boolean setActualizarCurso(String idCurso, String idProfesor, int aula, String valorCuota, int estado, String horarios[][]);
	public String getValorCuota(String idCurso);
}
