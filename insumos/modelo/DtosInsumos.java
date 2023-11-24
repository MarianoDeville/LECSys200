/*****************************************************************************************************************************************************************/
//										LISTADO DE MÉTODOS
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------*/
//	public void getInfoPedido()
//	public void setId(String id)
//	public void setCuerpoEmail()
//	public void setEmail(String email)
//	public void setIdProveedor(int pos)
//	public void setEstado(String estado)
//	public void setAsunto(String asunto)
//	public void setNombre(String nombre)
//	public void setIdSolicitante(int pos)
//	public void setIdProveedor(String id)
//	public void getInfoPedidoPresupuesto()
//	public void setMensaje(String mensaje)
//	public void setIdOrdenCompra(int posición)
//	public void setElementoSeleccionado(int pos)
//	public void setDescripción(String descripción)
//	public void setSectorSolicitante(String sector)
//	public void setPresentación(String presentación)
//	public void setIdPresupuesto(String idPresupuesto)
//	public void actualizarLista(char operación, int pos, String cantidad)
//	public boolean enviarEmail()
//	public boolean setGuardarNuevo()
//	public boolean setActualizarInfo()
//	public boolean setEliminarPedido()
//	public boolean setAprobarCotización()
//	public boolean actualizarStockInsumo()
//	public boolean isFechaOK(String valor)
//	public boolean isCheckInfo(String operacion)
//	public boolean setAgregarStock(JTable insumos)
//	public boolean setGuardarSolicitudCotizacion()
//	public boolean setStockAgregar(String aDescontar)
//	public boolean setStockDescontar(String aDescontar)
//	public boolean setGuardarPedido(JTable tablaPedido)
//	public boolean setGuardarCotización(JTable valores)
//	public boolean setActualizarPedido(JTable tablaPedido)
//	public String getId()
//	public String getStock()
//	public String getNombre()
//	public String getMsgError()
//	public String getCuerpoEmail()
//	public String getDescripción()
//	public String getPresentación()
//	public String getFechaSolicitud()
//	public String getSectorSolicitante()
//	public String getInfoInsumo(int pos)
//	public String [] getListaEmpleados()
//	public String getEmailSeleccionado(int pos)
//	public DefaultTableModel getTablaOrdenes()
//	public DefaultTableModel getTablaInsumos()
//	public DefaultTableModel getHistoricoInsumo()
//	public DefaultTableModel getTablaCotizaciones()
//	public DefaultTableModel getTablaSeleccionados()
//	public DefaultTableModel getProveedoresCotización()
//	public DefaultTableModel getTablaInsumos(String filtro)
//	public DefaultTableModel getStockInsumos(String filtro)
//	public DefaultTableModel getListadoProveedores(String filtro)
//	public DefaultTableModel getTablaPedidos(String filtro, boolean estado)
//	private boolean isFloat(String valor)
//	private boolean isNumerico(String valor)
//	private void ResetearDatos()
/*****************************************************************************************************************************************************************/
package modelo;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controlador.EmailSenderService;
import dao.ComprasDAO;
import dao.EmpleadoMySQL;
import dao.InsumosMySQL;
import dao.OperadorSistema;
import dao.ProveedoresDAO;

public class DtosInsumos {

	private InsumosMySQL insumosDAO;
	static private String id;
	static private String nombre;
	static private String descripción;
	static private String formato;
	static private int stock;
	private String estado;
	private int idSolicitante;
	private int cantElementos = 0;
	private int nuevoStock;
	private String idInsumos[];
	private String tabla[][];
	private String tablaSeleccionados[][];
	private String respuesta[][];
	private String sectorSolicitante;
	private String fechaSolicitud;
	private String msgError;
	private String destinatario;
	private String asunto;
	private String cuerpoEmail;
	private String mensaje;
	private String idCotización;
	private String idProveedor = null;
	private String idPresupuesto;
	private String idOrdenCompra;
	private String fechaValidez;

	public DefaultTableModel getElementosAutorizados() {
		
		insumosDAO = new InsumosMySQL();
		String titulo[] = new String[] {"ID", "Nombre", "Descripción", "Cantidad", "Precio", "Total", "Sel."};
		tablaSeleccionados = insumosDAO.getOrdenCompra(idOrdenCompra);
		Object cuerpo[][] = new Object[tablaSeleccionados.length][7];
		
		if(tablaSeleccionados != null) {
			
			for(int i = 0; i < tablaSeleccionados.length; i++) {
				
				cuerpo[i][0] = tablaSeleccionados[i][0];
				cuerpo[i][1] = tablaSeleccionados[i][1];
				cuerpo[i][2] = tablaSeleccionados[i][2];
				cuerpo[i][3] = tablaSeleccionados[i][3];
				cuerpo[i][4] = tablaSeleccionados[i][4];
				cuerpo[i][5] = Float.parseFloat(tablaSeleccionados[i][4]) * Float.parseFloat(tablaSeleccionados[i][3]);
				cuerpo[i][6] = true;
			}
		}
		DefaultTableModel respuesta = new DefaultTableModel(cuerpo, titulo){

			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				
				return column > 5? true: false;
			}
			
			public Class<?> getColumnClass(int column) {
				
				return column > 5? Boolean.class: String.class;
		    }
		};
		return respuesta;
	}

	public void getInfoPedido() {
		
		insumosDAO = new InsumosMySQL();
		tablaSeleccionados = insumosDAO.getPedido(id);
		cantElementos = tablaSeleccionados.length;
		idSolicitante = Integer.parseInt(insumosDAO.getIdSolicitante());
		nombre = insumosDAO.getNombreSolicitante();
		sectorSolicitante = insumosDAO.getSectorSolicitante();
		fechaSolicitud = insumosDAO.getFechaSolicitud();
	}
	

	public void setCuerpoEmail() {
		
		cuerpoEmail = mensaje + "\n\nSolicitud cotización nro : " + idCotización +"\n\nElementos a cotizar:\n\n";	
		
		for(int i = 0; i < tablaSeleccionados.length; i++) {
			
			cuerpoEmail += ((i + 1) + ") " + tablaSeleccionados[i][0] + " - " +  tablaSeleccionados[i][1] + " - " + tablaSeleccionados[i][2] + "\n");
		}
	}
	
	public void setEmail(String email) {
		
		destinatario = email;
	}
	
	public void setIdProveedor(int pos) {
		
		if(pos == -1)
			idProveedor = null;
		else
			idProveedor = tabla[pos][4];
	}
	
	public void setEstado(String estado) {
		
		this.estado = estado;
	}
	
	public void setAsunto(String asunto) {
		
		this.asunto = asunto;
	}
	
	public void setNombre(String nombre) {
		
		DtosInsumos.nombre = nombre;
	}
	
	public void setIdSolicitante(int pos) {
		
		idSolicitante = Integer.parseInt(respuesta[pos][1]);
	}
	
	public void setIdProveedor(String id) {
		
		idProveedor = id;
	}
	
	public void getInfoPedidoPresupuesto() {
		
		insumosDAO = new InsumosMySQL();
		tablaSeleccionados = insumosDAO.getPedidoPresupuesto(id, idProveedor);
		cantElementos = tablaSeleccionados.length;
	}
	
	public void setMensaje(String mensaje) {
		
		this.mensaje = mensaje;
	}

	public void setIdOrdenCompra(int posición) {
		
		idOrdenCompra = tabla[posición][0];
	}

	public void setElementoSeleccionado(int pos) {
		
		id = tabla[pos][0];
		nombre = tabla[pos][1];
		descripción = tabla[pos][2];
		formato = tabla[pos][3];
		
		try {
		
			stock = Integer.parseInt(tabla[pos][4]);
		} catch(Exception e) {

			stock = 0;
		}
		estado = "1";
	}
	
	public void setDescripción(String descripción) {
		
		DtosInsumos.descripción = descripción;
	}
	
	public void setSectorSolicitante(String sector) {
		
		sectorSolicitante = sector;
	}
	
	public void setPresentación(String presentación) {
		
		DtosInsumos.formato = presentación;
	}
	
	public void setIdPresupuesto(String idPresupuesto) {
		
		this.idPresupuesto = idPresupuesto;
	}
	
	public void actualizarLista(char operación, int pos, String cantidad) {
		
		String tablaTemp[][] = null;
		int i = 0;
		
		if(operación == '+') {
			
			cantElementos++;
			
			if(tablaSeleccionados != null) {
				
				tablaTemp = tablaSeleccionados.clone();
				tablaSeleccionados = new String[cantElementos][5];
				
				while(i < tablaTemp.length) {
					
					tablaSeleccionados[i][0] = tablaTemp[i][0];
					tablaSeleccionados[i][1] = tablaTemp[i][1];
					tablaSeleccionados[i][2] = tablaTemp[i][2];
					tablaSeleccionados[i][3] = tablaTemp[i][3];
					tablaSeleccionados[i][4] = tablaTemp[i][4];
					i++;
				}
			} else {
				
				tablaSeleccionados = new String[cantElementos][5];
			}
			tablaSeleccionados[i][0] = isNumerico(cantidad)?cantidad:"1";
			tablaSeleccionados[i][1] = tabla[pos][1];
			tablaSeleccionados[i][2] = tabla[pos][2];
			tablaSeleccionados[i][3] = tabla[pos][3];
			tablaSeleccionados[i][4] = tabla[pos][0];
			return;
		}
		
		if(operación == '-') {
			
			if(cantElementos > 0) {
				
				cantElementos--;

				if(cantElementos < 1) {
					
					tablaSeleccionados = null;
					cantElementos = 0;
					return;
				}				
				int e = 0;
				tablaTemp = tablaSeleccionados.clone();
				tablaSeleccionados = new String[cantElementos][5];
				
				while(e < tablaTemp.length) {
					
					if(e != pos) {
						
						tablaSeleccionados[i][0] = tablaTemp[e][0];
						tablaSeleccionados[i][1] = tablaTemp[e][1];
						tablaSeleccionados[i][2] = tablaTemp[e][2];
						tablaSeleccionados[i][3] = tablaTemp[e][3];
						tablaSeleccionados[i][4] = tablaTemp[e][4];
						i++;
					}
					e++;
				}
			}
		}
	}
	
	public boolean enviarEmail() {
		
		EmailSenderService emailService = new EmailSenderService();
		
		if(!emailService.mandarCorreo(destinatario, asunto, cuerpoEmail)) {
			
			msgError = "Error en el emvío del email.";
			return false;
		}
		msgError = "Pedido de cotización enviado.";	
		return true;
	}
	
	public boolean setGuardarNuevo() {
		
		boolean bandera;
		insumosDAO = new InsumosMySQL();
		bandera = insumosDAO.setInsumo(nombre, descripción, formato);
		ResetearDatos();
		return bandera;
	}
	
	public boolean setActualizarInfo() {
		
		insumosDAO = new InsumosMySQL();
		boolean bandera;
		bandera = insumosDAO.setActualizarInsumo(id, nombre, descripción, formato, estado);
		ResetearDatos();
		return bandera;
	}
	
	public boolean setEliminarPedido() {
		
		msgError = "Error en la base de datos al intentar borrar el pedido";
		return insumosDAO.setEliminarPedido(id);
	}
	
	public boolean setAprobarCotización() {
		
		OperadorSistema operador = new OperadorSistema();
		ComprasDAO comprasDAO = new ComprasDAO();
		
		if(comprasDAO.setOrdenCompra(idPresupuesto, id, operador.getFichaEmpleado()))
			msgError = "Orden de compra generada.";
		else
			msgError = "Hubo un error al intentar actualizar la base de datos.";
		return true;
	}
	
	public boolean actualizarStockInsumo() {
		
		insumosDAO = new InsumosMySQL();
		return insumosDAO.setActualizarStock(id, stock + nuevoStock);
	}
	
	public boolean isFechaOK(String valor) {
		
		String partes[] = null;
		msgError = "El formato de la fecha es incorrecto, debe ser DD/MM/AAAA";
		
		try {

			partes = valor.split(valor.contains("/")?"/":"-");
			
			for(int i = 0; i < 3; i++) {
				
				if(!isNumerico(partes[i])) 
					return false;
			}
			
			if(Integer.parseInt(partes[0]) < 1 || Integer.parseInt(partes[0]) > 31)
				return false;
			
			if(Integer.parseInt(partes[1]) < 1 || Integer.parseInt(partes[1]) > 12)
				return false;
			
			if(Integer.parseInt(partes[2]) < 2023)
				return false;
		} catch(Exception e) {
			
			return false;
		}
		fechaValidez = partes[2] + "/" + partes[1] + "/" + partes[0];
		msgError = "";
		return true;
	}
	
	public boolean isCheckInfo(String operacion) {
		
		msgError = "";
		
		if(operacion.equals("ABML Insumo")) {
			
			if(nombre.length() < 5) {
				
				msgError = "El nombre no puede faltar ni ser demasiado corto.";
				return false;
			} else if(descripción.length() < 5) {
				
				msgError = "La descripción no puede faltar ni ser demasiado corto.";
				return false;
			} else if(formato.length() < 3) {
				
				msgError = "Por favor indique en qué formato viene el producto.";
				return false;
			}
		} else {

			for(int i = 0; i < tablaSeleccionados.length; i++) {
		
				if(!isNumerico(tablaSeleccionados[i][0])){
					
					msgError = "En la lista de elementos pedidos el valor de Cant. debe ser numérico.";
					return false;	
				} else if(Integer.parseInt(tablaSeleccionados[i][0]) < 1) {
					
					msgError = "En la lista de elementos pedidos el valor de Cant. debe ser mayor a cero.";
					return false;
				}
			}
		}
		return true;
	}

	public boolean setGuardarSolicitudCotizacion() {

		if(asunto.length() < 5) {
			
			msgError = "Falta completar el asunto del email.";
			return false;
		}

		if(destinatario.length() < 6) {
			
			msgError = "Falta completar el campo destinatario con la dirección de email.";
			return false;
		}
		
		if(idProveedor == null) {
			
			msgError = "Solo puede enviar email a destinatarios que previamente estén cargados.";
			return false;
		}
		msgError = "Error en el formato del email.";
		
		if(!destinatario.contains("@") || destinatario.contains(" "))			
			return false;
		String partes[] = destinatario.split("@");
		
		try {
			
			if(partes[1].length() < 5 || !partes[1].contains("."))	
				return false;
		} catch(Exception e) {

			return false;
		}

		if(mensaje.length() < 5) {
			
			msgError = "El mensaje para el proveedor está vacío.";
			return false;
		}
		String idInsumos[] = new String[tablaSeleccionados.length];
		
		for(int i = 0; i < idInsumos.length; i++) {
			
			idInsumos[i] = tablaSeleccionados[i][4];
		}
		idCotización = insumosDAO.setPedidoPresupuesto(id, idProveedor, idInsumos);

		if(idCotización == null) {
			
			msgError = "Error al guardar el pedido de cotización en la base de datos.";
			return false;			
		}
		setCuerpoEmail();
		
		if(idCotización.equals(""))
			msgError = "";
		return true;
	}

	public boolean setStockAgregar(String aDescontar) {
		
		try {
		
			nuevoStock = Integer.parseInt(aDescontar);
		} catch(Exception e) {

			msgError = "La cantidad debe ser un número entero.";
			return false;
		}
		
		if(nuevoStock < 0) {
			
			msgError = "La cantidad debe ser un número positivo.";
			return false;
		}
		return true;
	}
	
	public boolean setStockDescontar(String aDescontar) {
		
		try {
		
			nuevoStock = -Integer.parseInt(aDescontar);
		} catch(Exception e) {

			msgError = "La cantidad debe ser un número entero.";
			return false;
		}
		
		if(nuevoStock > 0) {
			
			msgError = "La cantidad debe ser un número positivo.";
			return false;
		}
		
		if(-nuevoStock > stock) {
			
			msgError = "Quiere dar de baja más elementos de los que posee en el stock actual.";
			return false;
		}
		return true;
	}

	public boolean setGuardarPedido(JTable tablaPedido) {
		
		if(tablaPedido.getRowCount() < 1) {
			
			msgError = "La lista de pedido está vacía.";
			return false;	
		}
		
		for(int i = 0; i < tablaPedido.getRowCount(); i++) {
			
			tablaSeleccionados[i][0] = (String)tablaPedido.getValueAt(i, 0);
		}
		
		if(!isCheckInfo("Pedido insumos"))
			return false;
		msgError = "Error al intentar guardar en la base de datos.";
		return insumosDAO.setListadoPedido(sectorSolicitante, idSolicitante, tablaSeleccionados);
	}
	
	public boolean setGuardarCotización(JTable valores) {

		for(int i = 0; i < valores.getRowCount(); i++) {
			
			tablaSeleccionados[i][4] = (valores.getValueAt(i, 4) == null)?"":(String)valores.getValueAt(i, 4);

			if(!tablaSeleccionados[i][4].equals("")) {	
				
				if(!isFloat(tablaSeleccionados[i][4])) {
					
					msgError = "El Campo precio debe ser numérico";
					return false;
				}
			}
		}
		
		if(!insumosDAO.setPrecios(tablaSeleccionados, fechaValidez)) {
			
			msgError = "Hubo un problema al intentar guardar la información en la base de datos.";
			return false; 
		}
		msgError = "La información se guardó correctamente.";
		return true; 
	}
	
	public boolean setActualizarPedido(JTable tablaPedido) {
		
		if(tablaPedido.getRowCount() < 1) {
			
			msgError = "La lista de pedido está vacía.";
			return false;	
		}
		
		for(int i = 0; i < tablaPedido.getRowCount(); i++) {
			
			tablaSeleccionados[i][0] = (String)tablaPedido.getValueAt(i, 0);
		}
		
		if(!isCheckInfo("Pedido insumos"))
			return false;
		msgError = "Error al intentar guardar en la base de datos.";
		return insumosDAO.setActualizarPedido(id, tablaSeleccionados);
	}
	
	public String getId() {
		
		return id;
	}
	
	public String getStock() {
		
		return stock + "";
	}
	
	public String getNombre() {
		
		return nombre;
	}
	
	public String getMsgError() {
		
		return msgError;
	}
	
	public String getCuerpoEmail() {
		
		return cuerpoEmail;
	}
	
	public String getDescripción() {
		
		return descripción;
	}
	
	public String getPresentación() {
		
		return formato;
	}
	
	public String getFechaSolicitud() {
		
		return fechaSolicitud;
	}
	
	public String getSectorSolicitante() {
		
		return sectorSolicitante;
	}
	
	public String getInfoInsumo(int pos) {
		
		return tabla[pos][1] + ", " + tabla[pos][2] + ", " + tabla[pos][3];
	}
	
	public String [] getListaEmpleados() {
		
		EmpleadoMySQL empleadosDAO = new EmpleadoMySQL();
		String listado[] = null;
		respuesta = empleadosDAO.getEmpleadosActivos();
		
		if(respuesta != null)
			listado = new String[respuesta.length];
		
		for(int i = 0; i < respuesta.length; i++) {
			
			listado[i] = respuesta[i][0];
		}
		return listado;
	}
	
	public String getEmailSeleccionado(int pos) {
		
		return tabla[pos][3];
	}
	
	public DefaultTableModel getTablaOrdenes() {
		
		insumosDAO = new InsumosMySQL();
		String titulo[] = new String[] {"ID", "Fecha", "Sector", "Solicitante", "Autorizado"};
		tabla = insumosDAO.getListadoOrdenesCompra(id, "1");
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}

	public DefaultTableModel getTablaInsumos() {
		
		String titulo[] = new String[] {"ID", "Cant.", "Nombre", "Descripción", "Precio"};
		DefaultTableModel respuesta = new DefaultTableModel(tablaSeleccionados, titulo){

			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				
				return column > 3? true: false;
			}
		};
		return respuesta;
	}
	
	public DefaultTableModel getHistoricoInsumo() {
		
		insumosDAO = new InsumosMySQL();
		String titulo[] = new String[] {"Fecha", "Proveedor", "Solicitante / sector", "Autorizó", "Cantidad", "Precio"};
		tablaSeleccionados = insumosDAO.getHistoriaCompras(id);
		DefaultTableModel respuesta = new DefaultTableModel(tablaSeleccionados, titulo);
		return respuesta;
	}

	public DefaultTableModel getTablaCotizaciones() {
		
		insumosDAO = new InsumosMySQL();
		tabla = insumosDAO.getPedido(id);
		String titulo[] = new String[tabla.length + 4];
		titulo[0] = "ID";
		titulo[1] = "Proveedor";
		titulo[titulo.length - 2] = "Total";
		titulo[titulo.length - 1] = "Estado";
		idInsumos = new String[tabla.length];
		
		for(int i = 0; i < tabla.length; i++) {
		
			titulo[i + 2] = tabla[i][1];
			idInsumos[i] = tabla[i][4];
		}
		tablaSeleccionados = insumosDAO.getTablaCotizaciones(id, idInsumos);
		DefaultTableModel respuesta = new DefaultTableModel(tablaSeleccionados, titulo);
		return respuesta;
	}
	
	public DefaultTableModel getTablaSeleccionados() {
		
		String titulo[] = new String[] {"Cant.", "Nombre", "Descripción", "Presentación"};
		DefaultTableModel respuesta = new DefaultTableModel(tablaSeleccionados, titulo){

			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				
				return column > 0? false: true;
			}
		};
		return respuesta;
	}
	
	public DefaultTableModel getProveedoresCotización() {
		
		insumosDAO = new InsumosMySQL();
		String titulo[] = new String[] {"ID","Nombre","CUIT","Dirección"};
		tabla = insumosDAO.getProveedoresPresupuesto(id);
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}
	
	public DefaultTableModel getTablaInsumos(String filtro) {
		
		insumosDAO = new InsumosMySQL();
		String titulo[] = new String[] {"ID", "Nombre", "Descripción", "Presentacíon"};
		tabla = insumosDAO.getListadoInsumos(filtro);
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}

	public DefaultTableModel getStockInsumos(String filtro) {
		
		insumosDAO = new InsumosMySQL();
		String titulo[] = new String[] {"ID", "Nombre", "Descripción", "Presentacíon", "Cant."};
		tabla = insumosDAO.getListadoInsumos(filtro);
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}
	
	public DefaultTableModel getListadoProveedores(String filtro) {
		
		ProveedoresDAO proveedoresDAO = new ProveedoresDAO();
		String titulo[] = new String[] {"Proveedor", "Sector", "Contacto", "Email"};
		tabla = proveedoresDAO.getListadoEmail(filtro);
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}
	
	public DefaultTableModel getTablaPedidos(String filtro, int estado) {
		
		insumosDAO = new InsumosMySQL();
		String titulo[] = new String[] {"ID", "Fecha", "Sector", "Solicitante", "Elementos pedidos"};
		tabla = insumosDAO.getListadoPedidos(estado, filtro);
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}

	private boolean isFloat(String valor) {
		
		try {
			
			Float.parseFloat(valor);
		} catch(Exception e) {
			
			return false;
		}
		return true;
	}
	
	private boolean isNumerico(String valor) {
		
		try {
	
			Integer.parseInt(valor);
		} catch(Exception e) {
			
			return false;
		}
		return true;
	}
	
	private void ResetearDatos() {
		
		id = "";
		nombre = "";
		descripción = "";
		formato = "";
		msgError = "";
	}
}