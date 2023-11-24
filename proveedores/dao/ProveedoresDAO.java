package dao;

import modelo.Proveedor;

public interface ProveedoresDAO {

	public Proveedor [] getListado(String filtrado, boolean estado);
	public boolean setNuevo(Proveedor proveedor);
	public boolean update(Proveedor proveedor);
	public boolean isCUITExistente(String cuit);
	
	
	public String[][] getListadoEmail(String filtrado);
	
	
	
//	public String [][] getListadoContactos(String idProveedor);
	
}
