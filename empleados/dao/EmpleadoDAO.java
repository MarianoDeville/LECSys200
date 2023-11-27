package dao;

import modelo.Empleado;

public interface EmpleadoDAO {

	public boolean setNuevo(Empleado empleado);
	public boolean update(Empleado empleado);
	public Empleado getEmpleado(String legajo);
	public Empleado [] getListado(String tipo, boolean estado, String filtrado);
	public int getLegajoLibre();
}