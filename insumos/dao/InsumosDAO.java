package dao;

import modelo.Insumo;
import modelo.PedidoInsumo;
import modelo.Presupuesto;

public interface InsumosDAO {

	public boolean nuevo(Insumo insumo);
	public boolean update(Insumo insumo);
	public Insumo [] listado(String filtrado);
	public PedidoInsumo [] getPedidos(int estado);
	public boolean nuevoPedido(PedidoInsumo solicitud);
	public boolean updatePedido(PedidoInsumo solicitud);
	public int setPedidoPresupuesto(Presupuesto presupuesto);
	public void getPedidoPresupuesto(Presupuesto presupuesto, int proveedor);
	public boolean setPrecios(Presupuesto presupuesto);
	
	

}
