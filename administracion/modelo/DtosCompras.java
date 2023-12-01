package modelo;

import javax.swing.table.DefaultTableModel;
import dao.ComprasDAO;
import dao.ComprasMySQL;

public class DtosCompras {

	private ComprasDAO comprasDAO;
	private static OrdenCompra ordenCompra;
	private OrdenCompra ordenesCompra[];
	private int mes;
	private String año;
	
	public DefaultTableModel getTablaOrdenesCompra(boolean estado) {
		
		comprasDAO = new ComprasMySQL();
		ordenesCompra = comprasDAO.getOrdenesCompra(estado?1:2);
		Object tabla[][] = new Object[ordenesCompra.length][6];
		String titulo[] = new String[] {"ID", "Fecha", "Ppto.", "Solicitante", "Autorizó", "Proveedor"};

		for(int i = 0; i < ordenesCompra.length; i++) {
			
			tabla[i][0] = ordenesCompra[i].getId();
			tabla[i][1] = ordenesCompra[i].getFecha();
			tabla[i][2] = ordenesCompra[i].getPresupuesto().getIdPresupuesto();
			tabla[i][3] = ordenesCompra[i].getNombreSolicitante();
			tabla[i][4] = ordenesCompra[i].getNombreAutoriza();
			tabla[i][5] = ordenesCompra[i].getPresupuesto().getProveedores()[0].getNombre();
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}

	public void setOCSleccionada(int pos) {
		
		ordenCompra = ordenesCompra[pos];
	}

	public DefaultTableModel getDetalleCompra() {
		
		comprasDAO = new ComprasMySQL();
		comprasDAO.getDetalleOrdenCompra(ordenCompra);
		String titulo[] = new String[] {"Id", "Producto", "Cant.", "Precio", "Monto"};
		Object tabla [][] = new Object[ordenCompra.getPresupuesto().getInsumos().length][5];
		float montoTotal = 0;
		int i = 0;
		
		for(Insumo ins: ordenCompra.getPresupuesto().getInsumos()) {
			
			tabla[i][0] = ins.getId();
			tabla[i][1] = ins.getNombre();
			tabla[i][2] = ins.getCantSolicitada();
			tabla[i][3] = ins.getPrecio();
			tabla[i][4] = ins.getCantSolicitada() * ins.getPrecio();
			montoTotal += ins.getCantSolicitada() * ins.getPrecio();
			i++;
		}
		ordenCompra.setMontoTotal(montoTotal);
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}

	public String [] listadoAños() {
		
		comprasDAO = new ComprasMySQL();
		return comprasDAO.getListadoAños();
	}

	public String [] listadoMeses() {
		
		return new String[] {"Todos", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	}

	public DefaultTableModel getTablaCompras() {
		
		comprasDAO = new ComprasMySQL();
		ordenesCompra = comprasDAO.getListadoCompras(año, mes);
		Object tabla[][] = new Object[ordenesCompra.length][6];
		String titulo[] = new String[] {"Fecha", "Ppto.", "Proveedor", "Solicitante", "Autorizó", "Monto"};
		
		for(int i = 0; i < ordenesCompra.length; i++) {
			
			tabla[i][0] = ordenesCompra[i].getFecha();
			tabla[i][1] = ordenesCompra[i].getPresupuesto().getIdPresupuesto();
			tabla[i][2] = ordenesCompra[i].getPresupuesto().getProveedores()[0].getNombre();
			tabla[i][3] = ordenesCompra[i].getNombreSolicitante();
			tabla[i][4] = ordenesCompra[i].getNombreAutoriza();
			tabla[i][5] = ordenesCompra[i].getMontoTotal();
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}

	public String getIdOrdenCompra() {
		
		return ordenCompra.getId() + "";
	}

	public String getFechaPedido() {
		
		return ordenCompra.getFechaPedido();
	}

	public String getFechaAutorizacion() {
		
		return ordenCompra.getFecha();
	}

	public String getFechaCompra() {
		
		return ordenCompra.getPago().getFecha();
	}

	public String getSolicitante() {
		
		return ordenCompra.getNombreSolicitante();
	}

	public String getAutorizo() {
		
		return ordenCompra.getNombreAutoriza();
	}

	public String getProveedor() {
		
		return ordenCompra.getPresupuesto().getProveedores()[0].getNombre();
	}

	public String getMontoTotal() {
		
		return String.format("%.2f", ordenCompra.getMontoTotal());
	}

	public String getFactura() {
		
		return ordenCompra.getPago().getFactura();
	}

	public void setMes(int mes) {
		
		this.mes = mes;
	}

	public void setAño(String año) {
		
		this.año = año;
	}
}