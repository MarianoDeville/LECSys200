package dao;

import modelo.Proveedor;

public interface ProveedoresDAO {

	public Proveedor [] getListado(String filtrado, boolean estado, int servicio);
	public boolean setNuevo(Proveedor proveedor);
	public boolean update(Proveedor proveedor);
	public boolean isCUITExistente(String cuit);
	public Object[][] getListadoEmail(String filtrado);
	public Proveedor [] getProveedoresPresupuesto(int idPedidoCompra);
}
