package modelo;

import javax.swing.table.DefaultTableModel;
import dao.ComprasMySQL;

public class DtosCompras {

	private ComprasMySQL comprasDAO;
	private static String idPresupuesto;
	private static String autorizo;
	private static String proveedor;
	private static String solicitante;
	private static String monto;
	private static String idOrdenCompra;
	private String tabla[][];
	private int mes;
	private float montoTotal;
	private String año;
	private String fechaPedido;
	private String fechaAutorizacion;
	private String fechaCompra;
	private String factura;
	
	public DefaultTableModel getDetalleCompra() {
		
		comprasDAO = new ComprasMySQL();
		tabla = null;
		String titulo[] = new String[] {"Id", "Producto", "Cant.", "Precio", "Monto"};
		tabla = comprasDAO.getDetalleCompra(idPresupuesto);
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		montoTotal = 0;
		
		for(String fila[]: tabla) {
			
			montoTotal += Float.parseFloat(fila[4].replace(",", "."));
		}
		fechaPedido = tabla[0][5];
		fechaAutorizacion = tabla[0][6];
		fechaCompra = tabla[0][7];
		factura = tabla[1][8];
		return respuesta;
	}

	public DefaultTableModel getDetalleOrdenCompra() {
		
		comprasDAO = new ComprasMySQL();
		tabla = null;
		String titulo[] = new String[] {"Id", "Producto", "Cant.", "Precio", "Monto"};
		tabla = comprasDAO.getDetalleOrdenCompra(idPresupuesto);
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		montoTotal = 0;
		
		for(String fila[]: tabla) {
			
			montoTotal += Float.parseFloat(fila[4].replace(",", "."));
		}
		fechaPedido = tabla[0][5];
		fechaAutorizacion = tabla[0][6];
		return respuesta;
	}
	
	public void setInfoSelOC(int lugar) {
		
		idOrdenCompra = tabla[lugar][0];
		idPresupuesto = tabla[lugar][2];
		solicitante = tabla[lugar][3];
		autorizo = tabla[lugar][4];
		proveedor = tabla[lugar][5];
	}
	
	public void setInfoSel(int lugar) {
		
		idPresupuesto = tabla[lugar][1];
		proveedor = tabla[lugar][2];
		solicitante = tabla[lugar][3];
		autorizo = tabla[lugar][4];
		monto = tabla[lugar][5];
	}
	
	public void borrarInfo() {
		
		idPresupuesto = null;
		proveedor = null;
		solicitante = null;
		autorizo = null;
		monto = null;
		idOrdenCompra = null;
	}

	public DefaultTableModel getTablaCompras() {
		
		comprasDAO = new ComprasMySQL();
		tabla = null;
		String titulo[] = new String[] {"Fecha", "Ppto.", "Proveedor", "Solicitante", "Autorizó", "Monto"};
		tabla = comprasDAO.getListadoCompras(año, mes);
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
	
	public DefaultTableModel getTablaOrdenesCompra(boolean estado) {
		
		comprasDAO = new ComprasMySQL();
		tabla = null;
		String titulo[] = new String[] {"ID", "Fecha", "Ppto.", "Solicitante", "Autorizó", "Proveedor"};
		tabla = comprasDAO.getOrdenesCompra(estado?1:2);
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}

	public void setMes(int mes) {
		
		this.mes = mes;
	}

	public void setAño(String año) {
		
		this.año = año;
	}

	public String getAutorizo() {
		
		return autorizo;
	}

	public String getFechaPedido() {
		
		return fechaPedido;
	}

	public String getProveedor() {
		
		return proveedor;
	}

	public String getSolicitante() {
		
		return solicitante;
	}

	public String getFechaAutorizacion() {
		
		return fechaAutorizacion;
	}

	public String getFechaCompra() {
		
		return fechaCompra;
	}

	public String getFactura() {
		
		return factura;
	}

	public String getMonto() {
		
		return monto;
	}

	public String getIdOrdenCompra() {
		
		return idOrdenCompra;
	}

	public String getMontoTotal() {
		
		return String.format("%.2f", montoTotal);
	}
}