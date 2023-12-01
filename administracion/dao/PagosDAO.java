package dao;

import modelo.Empleado;
import modelo.OrdenCompra;
import modelo.Pago;
import modelo.Proveedor;

public interface PagosDAO {

	public Proveedor [] getProveedores(String filtrado, boolean deuda);
	public OrdenCompra [] getDeudaProveedores(Proveedor proveedor);
	public boolean setPagoProveedor(Pago infoPago, OrdenCompra ordenesCompra[]);
	public Pago [] getPagosProveedor(Proveedor proveedor, String a�o, int mes);
	public OrdenCompra [] getDetallePagoProveedor(Pago pago);
	public boolean setPagoEmpleado(Pago infoPago);
	public Pago [] getPagosEmpleado(Empleado empleado, String a�o, int mes);
	public String[] getListadoA�os();
	public Pago [] getHistorialPagos(String a�o, int mes, boolean empl, boolean prov);
}
