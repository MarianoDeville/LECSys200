package dao;

import modelo.Cobros;

public interface CobrosDAO {

	public Cobros [] getListado(int año, int mes);
	public int setCobro(Cobros cobro);
	public boolean update(Cobros cobros[]);
	public String[] getListadoAños();
}
