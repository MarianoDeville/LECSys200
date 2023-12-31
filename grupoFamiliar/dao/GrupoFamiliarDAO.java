package dao;

import modelo.GrupoFamiliar;

public interface GrupoFamiliarDAO {

	public GrupoFamiliar [] getListado(boolean sinDeuda, String campoBusqueda, int cantIntegrantes);
	public GrupoFamiliar [] getListado(String campo, String valor, boolean sinDeuda, String campoBusqueda);
	public GrupoFamiliar getGrupoFamiliar(int id);
	public int create(GrupoFamiliar familia);
	public boolean update(GrupoFamiliar familia);
	public boolean updateDeuda(int idGrupo, int modificarDeuda);
	public boolean isNombreFamilia(String nombre);
}