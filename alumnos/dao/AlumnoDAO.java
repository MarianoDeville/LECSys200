package dao;

import modelo.Alumno;

public interface AlumnoDAO {

	public Alumno [] getListado(String campo, String valor, boolean estado, String orden, String busqueda);
	public boolean setNuevo(Alumno alumno);
	public boolean update(Alumno alumno);
	public int getLegajoLibre();
	public Alumno [] getListado( boolean estado, int grupo, String busqueda);	
	
	

	public boolean resetEstado();
}