package modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import dao.ComprasDAO;
import dao.EmpleadoMySQL;
import dao.InsumosDAO;
import dao.PagosDAO;

public class DtosPagos {

	private PagosDAO pagosDAO = new PagosDAO();
	private static Empleado empleado;
	private static float suma;
//	private static String id;
	private static String seleccionados[][];
	private static String metodoPago;

	private static String comentario;
	private static String factura;
	private Calendar fechaSistema;
	private String tabla[][];
	private String msgError;
	private String concepto;

//	private static String fecha;
//	private static String cuit;
//	private static String nombre;
//	private String apellido;
//	private String direccion;
//	private String telefono;
//	private String email;
	
	
	public DefaultTableModel getTablaHistorial(String año, int mes, boolean empleados, boolean prov) {
		
		suma = 0;
		String titulo[] = new String[] {"Fecha", "Nombre", "Concepto", "Monto", "Factura", "Forma pago", "Comentario"};
		tabla = pagosDAO.getHistorialPagos(año, mes, empleados, prov);
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		for(String temp[]:tabla) {
			
			suma += Float.parseFloat(temp[3].replace(",", "."));
		}
		return respuesta;
	}
	
	public String [] getAños() {
		
		fechaSistema = new GregorianCalendar();
		String respuesta[] = new String[5];
		
		for(int i = 4 ; i >= 0 ; i--) {
			
			respuesta[i] = fechaSistema.get(Calendar.YEAR) - i +""; 
		}
		return respuesta;
	}
	
	public int getMesActual() {
		
		fechaSistema = new GregorianCalendar();
		return fechaSistema.get(Calendar.MONTH) + 1;
	}
	
	public String getComentario(int pos) {
		
		return tabla[pos][5];
	}
	
	public DefaultTableModel getTablaPagosEmpleado(String año, int mes) {
		
		String titulo[] = new String[] {"Fecha", "Concepto", "Monto", "Factura", "Forma de pago"};
		tabla = pagosDAO.getPagosEmpleado(id, año, mes);
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}
		
	public boolean registrarPagoEmpleado() {
		
		String argumento[] = new String[] {id, concepto, String.format("%.2f", suma), factura, comentario, metodoPago};

		if(pagosDAO.setPagoEmpleado(argumento)) {

			msgError = "Guardado en la base se datos correctamente.";
			return true;
		} else {
			
			msgError = "Error al intentar guardar la información.";
			return false;
		}
	}
	
	public String [] listadoConcepto() {
		
		return new String[] {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", 
							 "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre", "Otro"};
	}
	
	public String [] formasPago() {
		
		return new String[] {"Transferencia", "Efectivo", "Cheque"};
	}
	
	public void setInfoEmpleado() {
		
		EmpleadoMySQL empleadosDAO = new EmpleadoMySQL();
		empleado = empleadosDAO.getEmpleado(id);
		suma = Float.parseFloat(respuesta[6].replace(",", "."));
	}
	
	public DefaultTableModel getTablaEmpleados(boolean estado, String filtro) {
		
		EmpleadoMySQL empleados = new EmpleadoMySQL();
		String titulo[] = {"Leg.", "Nombre", "Apellido", "Sector", "Cargo", "Tipo"};
		tabla = empleados.getEmpleados(estado, filtro);
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		return tablaModelo;
	}
	
	public DefaultTableModel getTablaPagosProveedor(String año, int mes) {
		
		String titulo[] = new String[] {"O.P.", "Fecha", "Monto", "Factura", "Forma de pago"};
		tabla = pagosDAO.getPagosProveedor(id, año, mes);
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}
	
	public boolean registrarPago() {
		
		String listaOC[] = new String[seleccionados.length];
		String argumento[] = new String[6];
		int i = 0;
		
		for(String algo[]:seleccionados) {
		
			listaOC[i++] = algo[0];
		}
		argumento[0] = id;
		argumento[1] = "Pago proveedor.";
		argumento[2] = String.format("%.2f", suma);
		argumento[3] = factura;
		argumento[4] = comentario;
		argumento[5] = metodoPago;

		if(pagosDAO.setPagoProveedor(argumento, listaOC)) {
			
			InsumosDAO insumosDAO = new InsumosDAO();
			insumosDAO.setAgregarStock(listaOC);
			msgError = "Guardado en la base se datos correctamente.";
			return true;
		} else {
			
			msgError = "Error al intentar guardar la información.";
			return false;
		}
	}
	
	public DefaultTableModel getSeleccionados() {
		
		String titulo[] = new String[] {"O.C.", "Fecha", "Autorizó", "Monto"};
		DefaultTableModel respuesta = new DefaultTableModel(seleccionados,titulo);
		return respuesta;
	}

	public boolean setListaOC(JTable tablaSeleccionados) {
		
		int cant = 0;
		msgError = "";
		
		for(int i = 0; i < tablaSeleccionados.getRowCount(); i++) {
			
			if((boolean)tablaSeleccionados.getValueAt(i, 4))
				cant++;
		}
		
		if(cant == 0) {
			
			msgError = "No hay elementos seleccionados";
			return false;
		}
		seleccionados = new String[cant][4];
		cant = 0;
		
		for(int i = 0; i < tablaSeleccionados.getRowCount(); i++) {
			
			if((boolean)tablaSeleccionados.getValueAt(i, 4)) {
			
				seleccionados[cant][0] = (String)tablaSeleccionados.getValueAt(i, 0);
				seleccionados[cant][1] = (String)tablaSeleccionados.getValueAt(i, 1);
				seleccionados[cant][2] = (String)tablaSeleccionados.getValueAt(i, 2);
				seleccionados[cant][3] = (String)tablaSeleccionados.getValueAt(i, 3);
				cant++;
			}
		}
		return true;
	}
	
	public String [] listadoAños() {
		
		ComprasDAO comprasDAO = new ComprasDAO();
		return comprasDAO.getListadoAños();
	}
	
	public String [] listadoMeses() {
		
		return new String[] {"Todos", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", 
							 "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	}
	
	public String getSumaFacturas(JTable tablaDeuda) {
		
		suma = 0;
		
		for(int i = 0; i < tablaDeuda.getRowCount(); i++) {
		
			if((boolean)tablaDeuda.getValueAt(i, 4)) {
				
				String valor = (String)tablaDeuda.getValueAt(i, 3);
				tablaDeuda.setValueAt(valor.replace(",", ""), i, 3);
				suma += Float.parseFloat((String)tablaDeuda.getValueAt(i, 3));
			}
		}
		return String.format("%.2f", suma);
	}
	
	public DefaultTableModel getDeudaProveedor() {
		
		String titulo[] = new String[] {"O.C.", "Fecha", "Autorizó", "Monto", "Sel."};
		tabla = pagosDAO.getDeudaProveedores(id);
		Object tablaO[][] = new Object [tabla.length][5];
		
		for(int i = 0; i < tabla.length; i++) {
			
			tablaO[i][0] = tabla[i][0];
			tablaO[i][1] = tabla[i][1];
			tablaO[i][2] = tabla[i][2];
			tablaO[i][3] = tabla[i][3];
			tablaO[i][4] = false;
		}
		DefaultTableModel respuesta = new DefaultTableModel(tablaO,titulo){

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
	
	public void setSelecionado(int pos) {
		
		id = tabla[pos][0];
		nombre = tabla[pos][1];
		cuit = tabla[pos][2];
	}
	
	public DefaultTableModel getTablaProveedores(String filtro, boolean estado) {
		
		String titulo[] = new String[] {"Id", "Razón social", "CUIT", "Dirección"};
		tabla = pagosDAO.getListadoProveedores(filtro, estado);
		DefaultTableModel respuesta = new DefaultTableModel(tabla,titulo);
		return respuesta;
	}
	
	public void setInfoPago(int pos) {

		DtosPagos.fecha = tabla[pos][1];
		DtosPagos.suma = Float.parseFloat(tabla[pos][2].replace(",", "."));
		DtosPagos.factura = tabla[pos][3];
		DtosPagos.metodoPago = tabla[pos][4];
		seleccionados = pagosDAO.getDetallePagoProveedor(tabla[pos][0]);
		DtosPagos.comentario = tabla[pos][5];;
	}
	
	public void borrarInfo() {

		DtosPagos.id = null;
		empleado = null;
		DtosPagos.suma = 0;
		DtosPagos.factura = null;
		DtosPagos.metodoPago = null;
		DtosPagos.seleccionados = null;
		DtosPagos.comentario = null;
	}
	
	public String getNombre() {
		
		return empleado.getNombre();
	}
	
	public String getDNI( ) {
		
		return empleado.getDni();
	}

	public String getMsgError() {
		
		return msgError;
	}
	
	public boolean setSuma(String valor) {
		try {
			
			suma = Float.parseFloat(valor.replace(",", "."));
		} catch(Exception e) {
			
			msgError = "El formato del valor de la remuneración es incorrecto.";
			return false;
		}
		return true;
	}
	
	public String getSuma() {
		
		return String.format("%.2f", suma);
	}

	public void setFactura(String factura) {
		
		DtosPagos.factura = factura;
	}
	
	public String getFactura() {
		
		return factura;
	}

	public void setComentario(String comentario) {
		
		DtosPagos.comentario = comentario;
	}
	
	public String getComentario() {
		
		return comentario;
	}

	public void setMetodoPago(String metodo) {
		
		metodoPago = metodo;
	}
	
	public String getMetodoPago() {
		
		return metodoPago;
	}

	public String getFecha() {
		
		return empleado.getFechaIngreso();
	}
	
	public String getId() {
		
		return id;
	}

	public String getApellido(){
		
		return empleado.getApellido();
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
	
	public void setConcepto(String valor) {
		
		concepto = valor;
	}
}