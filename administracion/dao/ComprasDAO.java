package dao;

import modelo.OrdenCompra;
import modelo.Presupuesto;

public interface ComprasDAO {

	public boolean setOrdenCompra(Presupuesto presupuesto, String autoriza);
	public OrdenCompra [] getOrdenesCompra(int estado);
	public void getDetalleOrdenCompra(OrdenCompra ordenCompra);
	public String[] getListadoAños();
	public OrdenCompra [] getListadoCompras(String año, int mes);
}
