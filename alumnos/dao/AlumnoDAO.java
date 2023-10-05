package dao;

import modelo.Alumno;

public interface AlumnoDAO {

	public boolean setNuevo(Alumno alumno);
	public boolean update(Alumno alumno);
	public Alumno [] getListado( boolean estado, int grupo, String busqueda);
	public String [][] getListado(String campo, String valor, boolean estado, String orden, String busqueda);
	public boolean setActualizarFamila(String idFamilia, String idAlumnos[], String estado);
	public boolean resetEstado();
}
