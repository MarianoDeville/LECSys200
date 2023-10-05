package dao;

public interface AsistenciaDAO {

	public boolean setAsistencia(int fila);
	public String getFaltas();
	public String getPresente();
	public String getTarde();
	public void getInfoAsistencia(String idAlumno);
	public boolean isAsistenciaTomada(String idCurso, boolean reducido);
	public String [][] getAsistencias(String idCurso, boolean reducido, int mes);
}
