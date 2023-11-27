package dao;

import modelo.Presupuesto;

public interface ComprasDAO {

	public boolean setOrdenCompra(Presupuesto presupuesto, String autoriza);
}
