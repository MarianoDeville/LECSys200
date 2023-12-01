package modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import dao.ComprasDAO;
import dao.ComprasMySQL;
import dao.EmpleadoMySQL;
import dao.InsumosDAO;
import dao.InsumosMySQL;
import dao.OperadorSistema;
import dao.PagosDAO;
import dao.PagosMySQL;

public class DtosPagos {

	private PagosDAO pagosDAO;
	private static Proveedor proveedor;
	private static Pago pago;
	private static OrdenCompra seleccionados[];
	private static Empleado empleado;
	private Proveedor proveedores[];
	private OrdenCompra ordenesCompra[];
	private Pago pagos[];
	private Empleado empleados[];
	private String msgError;
	private float suma;

	public DefaultTableModel getTablaProveedores(String filtro, boolean estado) {
		
		pagosDAO = new PagosMySQL();
		proveedores = pagosDAO.getProveedores(filtro, estado);
		Object tabla[][] = new Object[proveedores.length][4];
		String titulo[] = new String[] {"Id", "Razón social", "CUIT", "Dirección"};
		
		for(int i = 0; i < tabla.length; i++) {
			
			tabla[i][0] = proveedores[i].getId();
			tabla[i][1] = proveedores[i].getNombre();
			tabla[i][2] = proveedores[i].getCuit();
			tabla[i][3] = proveedores[i].getDireccion();
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}
	
	public void setProveedor(int pos) {
		
		proveedor = proveedores[pos];
	}
	
	public DefaultTableModel getDeudaProveedor() {
		
		pagosDAO = new PagosMySQL();
		ordenesCompra = pagosDAO.getDeudaProveedores(proveedor);
		Object tabla[][] = new Object [ordenesCompra.length][5];		
		String titulo[] = new String[] {"O.C.", "Fecha", "Autorizó", "Monto", "Sel."};

		for(int i = 0; i < tabla.length; i++) {
			
			tabla[i][0] = ordenesCompra[i].getId();
			tabla[i][1] = ordenesCompra[i].getFecha();
			tabla[i][2] = ordenesCompra[i].getNombreAutoriza();
			tabla[i][3] = ordenesCompra[i].getMontoTotal();
			tabla[i][4] = false;
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo){

			private static final long serialVersionUID = 1L;
			public Class<?> getColumnClass(int column) {
				
				return column == 4? Boolean.class: String.class;
		    }
			public boolean isCellEditable(int row, int column) {
				
				return column == 4? true: false;
			}
		};
		return respuesta;
	}
	
	public String getSumaFacturas(JTable tablaDeuda) {
		
		if(pago == null)
			pago = new Pago();
		pago.setIdProveedor(proveedor.getId());
		pago.setConcepto("Compra de insumos.");
		float temp = 0;		
		
		for(int i = 0; i < tablaDeuda.getRowCount(); i++) {
		
			if((boolean)tablaDeuda.getValueAt(i, 4)) {
				
				temp += ordenesCompra[i].getMontoTotal();
			}
		}
		pago.setMonto(temp);
		return String.format("%.2f", pago.getMonto());
	}

	public boolean setListaOC(JTable tablaSeleccionados) {
		
		int e = 0;
		msgError = "";
		
		for(int i = 0; i < tablaSeleccionados.getRowCount(); i++) {
			
			if((boolean)tablaSeleccionados.getValueAt(i, 4))
				e++;
		}
		
		if(e == 0) {
			
			msgError = "No hay elementos seleccionados";
			return false;
		}
		seleccionados = new OrdenCompra[e];
		e = 0;
		
		for(int i = 0; i < tablaSeleccionados.getRowCount(); i++) {
			
			if((boolean)tablaSeleccionados.getValueAt(i, 4)) {
			
				seleccionados[e] = ordenesCompra[i];
				e++;
			}
		}
		return true;
	}
	
	public DefaultTableModel getSeleccionados() {

		Object tabla[][] = new Object [seleccionados.length][4];
		String titulo[] = new String[] {"O.C.", "Fecha", "Autorizó", "Monto"};
		
		for(int i = 0; i < tabla.length; i++) {
			
			tabla[i][0] = seleccionados[i].getId();
			tabla[i][1] = seleccionados[i].getFecha();
			tabla[i][2] = seleccionados[i].getNombreAutoriza();
			tabla[i][3] = seleccionados[i].getMontoTotal();
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}
	
	public boolean registrarPago() {
		
		if(pago.getFormaPago().length() < 3) {
			
			msgError = "No hay forma de pago definida.";
			return false;
		}
		pagosDAO = new PagosMySQL();
		OperadorSistema operador = new OperadorSistema();
		pago.setIdEmpleado(operador.getLegajoUsuario());
		
		if(pagosDAO.setPagoProveedor(pago, seleccionados)) {
			
			InsumosDAO insumosDAO = new InsumosMySQL();
			insumosDAO.updateStock(seleccionados);
			msgError = "Guardado en la base se datos correctamente.";
			return true;
		} else {
			
			msgError = "Error al intentar guardar la información.";
			return false;
		}
	}

	public String [] listadoAños() {
		
		ComprasDAO comprasDAO = new ComprasMySQL();
		return comprasDAO.getListadoAños();
	}
	
	public String [] listadoMeses() {
		
		return new String[] {"Todos", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", 
							 "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	}
	
	public DefaultTableModel getTablaPagosProveedor(String año, int mes) {
		
		pagosDAO = new PagosMySQL();
		pagos = pagosDAO.getPagosProveedor(proveedor, año, mes);
		String titulo[] = new String[] {"O.P.", "Fecha", "Monto", "Factura", "Forma de pago"};
		Object tabla[][] = new Object[pagos.length][5] ;
	
		for(int i = 0; i < tabla.length; i++) {
			
			tabla[i][0] = pagos[i].getId();
			tabla[i][1] = pagos[i].getFecha();
			tabla[i][2] = pagos[i].getMonto();
			tabla[i][3] = pagos[i].getFactura();
			tabla[i][4] = pagos[i].getFormaPago();
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}

	public void setInfoPago(int pos) {

		pagosDAO = new PagosMySQL();
		pago = pagos[pos];
		seleccionados = pagosDAO.getDetallePagoProveedor(pago);
	}
	
	public DefaultTableModel getTablaEmpleados(boolean estado, String filtro) {
		
		EmpleadoMySQL empleadosDAO = new EmpleadoMySQL();
		empleados = empleadosDAO.getListado("Todos", estado, filtro); 
		Object tabla[][] = new Object[empleados.length][6]; 
		String titulo[] = {"Leg.", "Nombre", "Apellido", "Sector", "Cargo", "Tipo"};

		for(int i = 0; i < tabla.length; i++) {
			
			tabla[i][0] = empleados[i].getLegajo();
			tabla[i][1] = empleados[i].getNombre();
			tabla[i][2] = empleados[i].getApellido();
			tabla[i][3] = empleados[i].getSector();
			tabla[i][4] = empleados[i].getCargo();
			tabla[i][5] = empleados[i].getRelacion();
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		return tablaModelo;
	}
	
	public void setEmpleado(int pos) {
		
		empleado = empleados[pos];
		pago = new Pago();
	}
	
	public String [] listadoConcepto() {
		
		return new String[] {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", 
							 "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre", "Otro"};
	}
	
	public String [] formasPago() {
		
		return new String[] {"Transferencia", "Efectivo", "Cheque"};
	}

	public boolean setSuma(String valor) {
		
		try {
			
			pago.setMonto(Float.parseFloat(valor.replace(",", ".")));
		} catch(Exception e) {
			
			msgError = "El formato del valor de la remuneración es incorrecto.";
			return false;
		}
		return true;
	}

	public boolean registrarPagoEmpleado() {
		
		pagosDAO = new PagosMySQL();
		OperadorSistema operador = new OperadorSistema();
		pago.setIdEmpleado(operador.getLegajoUsuario());
		
		if(pagosDAO.setPagoEmpleado(pago)) {
	
			msgError = "Guardado en la base se datos correctamente.";
			return true;
		} else {
			
			msgError = "Error al intentar guardar la información.";
			return false;
		}
	}	

	public DefaultTableModel getTablaPagosEmpleado(String año, int mes) {
		
		pagosDAO = new PagosMySQL();
		pagos = pagosDAO.getPagosEmpleado(empleado, año, mes);
		Object tabla[][] = new Object[pagos.length][6]; 
		String titulo[] = new String[] {"Id", "Fecha", "Concepto", "Monto", "Factura", "Forma de pago"};
		
		for(int i = 0; i < tabla.length; i++) {
			
			tabla[i][0] = pagos[i].getId();
			tabla[i][1] = pagos[i].getFecha();
			tabla[i][2] = pagos[i].getConcepto();
			tabla[i][3] = pagos[i].getMonto();
			tabla[i][4] = pagos[i].getFactura();
			tabla[i][5] = pagos[i].getFormaPago();
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}	
	
	public int getMesActual() {
		
		GregorianCalendar fechaSistema = new GregorianCalendar();
		return fechaSistema.get(Calendar.MONTH) + 1;
	}

	public String [] getAños() {
		
		pagosDAO = new PagosMySQL();
		return pagosDAO.getListadoAños();
	}

	public DefaultTableModel getTablaHistorial(String año, int mes, boolean empleados, boolean prov) {
		
		pagosDAO = new PagosMySQL();
		pagos = pagosDAO.getHistorialPagos(año, mes, empleados, prov);
		suma = 0;
		Object tabla[][] = new Object[pagos.length][7];
		String titulo[] = new String[] {"Fecha", "Nombre", "Concepto", "Monto", "Factura", "Forma pago", "Comentario"};
		
		for(int i = 0; i < tabla.length; i++) {
			
			tabla[i][0] = pagos[i].getFecha();
			tabla[i][1] = pagos[i].getNombre();
			tabla[i][2] = pagos[i].getConcepto();
			tabla[i][3] = pagos[i].getMonto();
			tabla[i][4] = pagos[i].getFactura();
			tabla[i][5] = pagos[i].getFormaPago();
			tabla[i][6] = pagos[i].getComentario();
			suma += pagos[i].getMonto();
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}
	
	public String getNombreEmpresa() {
		
		return proveedor.getNombre();
	}
	
	public String getCUIT( ) {
		
		return proveedor.getCuit();
	}	

	public String getMsgError() {
		
		return msgError;
	}
	
	public String getMontoTotal() {
		
		return String.format("%.2f", pago.getMonto());
	}

	public void setFactura(String factura) {
		
		pago.setFactura(factura);
	}

	public void setMetodoPago(String metodo) {
		
		pago.setFormaPago(metodo);
	}

	public String getFecha() {
		
		return pago.getFecha();
	}

	public String getFactura() {
		
		return pago.getFactura();
	}

	public String getMetodoPago() {
		
		return pago.getFormaPago();
	}
	
	public String getComentario() {
		
		return pago.getComentario();
	}

	public String getLegajo() {
		
		return empleado.getLegajo() + "";
	}

	public String getNombreEmpleado() {
		
		return empleado.getNombre();
	}

	public String getApellido(){
		
		return empleado.getApellido();
	}

	public String getDNI( ) {
		
		return empleado.getDni();
	}

	public String getDireccion(){
		
		return empleado.getDireccion();
	}

	public String getTelefono(){
		
		return empleado.getTelefono();
	}

	public String getEmail(){
		
		return empleado.getEmail();
	}
				
	public String getSalario() {
		
		return String.format("%.2f", empleado.getSalario());
	}
	
	public String getFechaNac() {
		
		return empleado.getFechaNacimiento();
	}

	public void setConcepto(String concepto) {
		
		pago.setConcepto(concepto);
	}

	public void setComentario(String comentario) {
		
		pago.setComentario(comentario);
	}
		
	public String getComentario(int pos) {
		
		return pagos[pos].getComentario();
	}
	
	public String getSuma() {
		
		return String.format("%.2f", suma);
	}
}