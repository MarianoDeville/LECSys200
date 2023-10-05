package dao;

import modelo.Usuario;

public interface UsuariosDAO {

	public boolean setUsuario(Usuario usr);
	public boolean update(Usuario usr);
	public void updateTiempoPass();
	public Usuario[] getListado(String usuario);
	public boolean isNombreUsado(String nombre);
	
}
