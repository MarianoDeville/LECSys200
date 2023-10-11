package dao;

import modelo.Alumno;
import modelo.GrupoFamiliar;

public interface GrupoFamiliarDAO {

	
	
	
	public GrupoFamiliar [] getListado(String campo, String valor, boolean sinDeuda, String campoBusqueda);
	public Alumno [] getIntegrantes(String id);
	public boolean setGrupoFamiliar(GrupoFamiliar familia);
	public boolean update(GrupoFamiliar familia);
	public boolean updateDeuda(int idGrupo, int modificarDeuda);
	public boolean eliminarIntegrante(int idGrupo);
	public boolean isNombreFamilia(String nombre);
}
