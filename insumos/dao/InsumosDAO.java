package dao;

import modelo.Insumos;

public interface InsumosDAO {

	public Insumos[] getHistoriaCompras(int idInsumo);
	public boolean setAgregarStock(String idOrdenCompra[]);
	public String [][] getOrdenCompra(String idOrdenCompra);
	public String [][] getListadoOrdenesCompra(String idInsumo, String estado);
	public boolean setActualizarStock(String idInsumo, int cantidad);
	public String[][] getTablaCotizaciones(String idPedidoCompra, String idInsumos[]);
	public boolean setPrecios(String tabla[][], String validez);
	public String[][] getPedidoPresupuesto(String idPedidoCompra, String idProveedor);
	public String [][] getProveedoresPresupuesto(String idPedidoCompra);
	public String setPedidoPresupuesto(String idPedidoCompra, String idProveedor, String idInsumos[]);
	public boolean setEliminarPedido(String idPedidoCompra);
	public boolean setActualizarPedido(String idPedidoCompra, String tablaPedidos[][]);
	public String [][] getPedido(String idPedidoCompra);
	public boolean setListadoPedido(String sector, int idSolicitante, String tablaPedidos[][]);
	public String [][] getListadoPedidos(int estado, String sector);
	public boolean setActualizarInsumo(String id, String nombre, String descrip, String formato, String estado);
	public boolean setInsumo(String nombre, String descrip, String formato);
	public String [][] getListadoInsumos(String filtrado);
}
