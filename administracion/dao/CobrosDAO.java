package dao;

import modelo.Cobros;

public interface CobrosDAO {

	public Cobros [] getListado(int a�o, int mes);
	public boolean setCobro(Cobros cobro);
	public boolean update(Cobros cobros[]);
	public int getUltimoRegistro();
}
