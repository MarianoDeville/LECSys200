package modelo;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import control.EmailSenderService;
import dao.ComprasDAO;
import dao.EmpleadoDAO;
import dao.EmpleadoMySQL;
import dao.InsumosDAO;
import dao.InsumosMySQL;
import dao.OperadorSistema;
import dao.ProveedoresDAO;
import dao.ProveedoresMySQL;

public class DtosInsumos {

	private InsumosDAO insumosDAO;
	
	private static Insumo insumo;
	private static PedidoInsumo pedidoInsumo;
	
	private Insumo insumos[];
	private Insumo insumosSeleccionados[];
	private PedidoInsumo pedidoInsumos[];
	private Empleado listaEmpleados[];
	private Presupuesto presupuesto;
	private Object emailProveedores[][];
	
	private int cantElementos = 0;	
	private String msgError;
	private String destinatario;
	private String asunto;
	private String mensaje;
	private String cuerpoEmail;

	public DefaultTableModel getTablaInsumos(String filtro) {
		
		insumosDAO = new InsumosMySQL();
		insumos = insumosDAO.listado(filtro);
		String titulo[] = new String[] {"ID", "Nombre", "Descripci�n", "Presentac�on"};
		Object tabla[][] = new Object[insumos.length][4];
		
		for(int i = 0; i < insumos.length; i++) {
			
			tabla[i][0] = insumos[i].getId();
			tabla[i][1] = insumos[i].getNombre();
			tabla[i][2] = insumos[i].getDescripcion();
			tabla[i][3] = insumos[i].getPresentacion();
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}
	
	public void setInsumoSeleccionado(int pos) {
		
		insumo = insumos[pos];
	}
	
	public boolean isCheckInfo() {
		
		msgError = "";
		
		if(insumo.getNombre().length() < 5) {
			
			msgError = "El nombre no puede faltar ni ser demasiado corto.";
			return false;
		} else if(insumo.getDescripcion().length() < 5) {
			
			msgError = "La descripci�n no puede faltar ni ser demasiado corto.";
			return false;
		} else if(insumo.getPresentacion().length() < 3) {
			
			msgError = "Por favor indique en qu� formato viene el producto.";
			return false;
		}
		return true;
	}
	
	public boolean setGuardarNuevo() {
		
		boolean bandera;
		insumosDAO = new InsumosMySQL();
		bandera = insumosDAO.nuevo(insumo);
		insumo = new Insumo();
		return bandera;
	}
	
	public boolean setActualizarInfo() {
		
		insumosDAO = new InsumosMySQL();
		return insumosDAO.update(insumo);
	}

	public String [] getListaEmpleados() {
		
		EmpleadoDAO empleadoDAO = new EmpleadoMySQL();
		String listado[] = null;
		listaEmpleados = empleadoDAO.getListado("Todos", true, "");
		
		if(listaEmpleados != null) {
			
			listado = new String[listaEmpleados.length];
		
			for(int i = 0; i < listaEmpleados.length; i++) {
				
				listado[i] = listaEmpleados[i].getNombre() + " " + listaEmpleados[i].getApellido();
			}
		}
		return listado;
	}
	
	public DefaultTableModel getTablaPedidos(String filtro, int estado) {
	
		insumosDAO = new InsumosMySQL();
		pedidoInsumos = insumosDAO.getPedidos(estado);
		String titulo[] = new String[] {"ID", "Fecha", "Sector", "Solicitante", "Elementos pedidos"};
		Object tabla[][] = new Object[pedidoInsumos.length][5];
		
		for(int i = 0; i < pedidoInsumos.length; i++) {
			
			tabla[i][0] = pedidoInsumos[i].getIdCompra();
			tabla[i][1] = pedidoInsumos[i].getFechaSolicitud();
			tabla[i][2] = pedidoInsumos[i].getSectorSolicitante();
			tabla[i][3] = pedidoInsumos[i].getNombreSolicitante();
			tabla[i][4] = "";
			
			for(int e = 0; e < pedidoInsumos[i].getInsumos().length; e++) {
				
				if(!tabla[i][4].equals(""))
					tabla[i][4] += ", ";
				tabla[i][4] += pedidoInsumos[i].getInsumos()[e].getNombre();
			}
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}
	
	public void setPedidoSeleccionado(int pos) {
		
		pedidoInsumo = new PedidoInsumo(pedidoInsumos[pos]);
	}

	public DefaultTableModel getTablaSeleccionados() {
		
		if(insumosSeleccionados == null)
			insumosSeleccionados = new Insumo[0];
		
		if(pedidoInsumo != null && pedidoInsumo.getInsumos() != null) {

			cantElementos = pedidoInsumo.getInsumos().length;
			insumosSeleccionados = pedidoInsumo.getInsumos().clone();
			pedidoInsumo.setInsumos(null);
		}
		String titulo[] = new String[] {"Cant.", "Nombre", "Descripci�n", "Presentaci�n"};
		Object tabla[][] = new Object[insumosSeleccionados.length][4];

		for(int i = 0; i < insumosSeleccionados.length; i++) {
		
			tabla[i][0] = insumosSeleccionados[i].getCantSolicitada();
			tabla[i][1] = insumosSeleccionados[i].getNombre();
			tabla[i][2] = insumosSeleccionados[i].getDescripcion();
			tabla[i][3] = insumosSeleccionados[i].getPresentacion();
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo){

			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				
				return column > 0? false: true;
			}
		};
		return respuesta;
	}
	
	public String getInfoInsumo(int pos) {
		
		return insumos[pos].getNombre() + ", " + insumos[pos].getDescripcion() + ", " + insumos[pos].getPresentacion();
	}
	
	public boolean actualizarLista(char operaci�n, int pos, String cantidad) {
		
		Insumo temp[] = null;
		int i = 0;
	
		if(operaci�n == '+') {

			try {
				
				i = Integer.parseInt(cantidad);
			}catch (Exception e) {

				msgError = "La cantidad a pedir debe ser num�rica.";
				return false;
			}
			
			if(i < 1) {
				
				msgError = "La cantidad a pedir debe ser un n�mero positivo mayor a cero.";
				return false;				
			}
			cantElementos++;
		
			if(insumosSeleccionados != null) {

				temp = insumosSeleccionados.clone();
				System.arraycopy(insumosSeleccionados, 0, temp, 0, temp.length);
				insumosSeleccionados = new Insumo[cantElementos];
				System.arraycopy(temp, 0, insumosSeleccionados, 0, temp.length);
			} else {
				
				insumosSeleccionados = new Insumo[cantElementos];
			}
			insumosSeleccionados[cantElementos - 1] = new Insumo(insumos[pos]);
			insumosSeleccionados[cantElementos - 1].setCantSolicitada(i);
			return true;
		}
		
		if(operaci�n == '-') {
			
			if(cantElementos > 0) {
				
				cantElementos--;

				if(cantElementos < 1) {
					
					insumosSeleccionados = null;
					cantElementos = 0;
					return true;
				}				
				int e = 0;
				temp = insumosSeleccionados.clone();
				insumosSeleccionados = new Insumo[cantElementos];
				
				while(e < temp.length) {
					
					if(e != pos) {
						
						insumosSeleccionados[i] = temp[e];
						i++;
					}
					e++;
				}
			}
		}
		return true;
	}

	public boolean setGuardarPedido() {
		
		if(insumosSeleccionados.length < 1) {
			
			msgError = "La lista de pedido est� vac�a.";
			return false;	
		}
		pedidoInsumo.setInsumos(insumosSeleccionados);
		msgError = "Error al intentar guardar en la base de datos.";
		return insumosDAO.nuevoPedido(pedidoInsumo);
	}
	
	public boolean setActualizarPedido() {
		
		if(insumosSeleccionados.length < 1) {
			
			msgError = "La lista de pedido est� vac�a.";
			return false;	
		}
		pedidoInsumo.setInsumos(insumosSeleccionados);
		msgError = "Error al intentar guardar en la base de datos.";
		return insumosDAO.updatePedido(pedidoInsumo);
	}
	
	public boolean setEliminarPedido() {
		
		msgError = "Error en la base de datos al intentar borrar el pedido";
		pedidoInsumo.setEstado(0);
		return insumosDAO.updatePedido(pedidoInsumo);
	}
	
	public DefaultTableModel getListadoProveedores(String filtro) {
		
		ProveedoresDAO proveedoresDAO = new ProveedoresMySQL();
		emailProveedores = proveedoresDAO.getListadoEmail(filtro);
		String titulo[] = new String[] {"Proveedor", "Sector", "Contacto", "Email"};
		DefaultTableModel respuesta = new DefaultTableModel(emailProveedores, titulo);
		return respuesta;
	}
	
	public void setIdProveedor(int pos) {
		
		if(presupuesto == null) {
			
			presupuesto = new Presupuesto();
			presupuesto.setProveedores(new Proveedor[1]);
		}

		if(pos == -1)
			presupuesto.getProveedores()[0].setId(0);
		else
			presupuesto.getProveedores()[0].setId((int) emailProveedores[pos][4]);
		}

	public boolean setGuardarSolicitudCotizacion() {

		if(asunto.length() < 5) {
			
			msgError = "Falta completar el asunto del email.";
			return false;
		}

		if(destinatario.length() < 6) {
			
			msgError = "Falta completar el campo destinatario con la direcci�n de email.";
			return false;
		}
		
		if(presupuesto.getProveedores()[0].getId() == 0) {
			
			msgError = "Solo puede enviar email a destinatarios que previamente est�n cargados.";
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
			
			msgError = "El mensaje para el proveedor est� vac�o.";
			return false;
		}
		presupuesto.setInsumos(insumosSeleccionados);
		presupuesto.setIdPedido(pedidoInsumo.getIdCompra());
		insumosDAO = new InsumosMySQL();
		presupuesto.setIdPresupuesto(insumosDAO.setPedidoPresupuesto(presupuesto));

		if(presupuesto.getIdPresupuesto() == 0) {
			
			msgError = "Error al guardar el pedido de cotizaci�n en la base de datos.";
			return false;			
		}
		setCuerpoEmail();
		return true;
	}
	
	public void setCuerpoEmail() {
		
		cuerpoEmail = mensaje + "\n\nSolicitud cotizaci�n nro : " + presupuesto.getIdPresupuesto() +"\n\nElementos a cotizar:\n\n";	
		cuerpoEmail += "Cantidad - Nombre - Descripci�n\n";
				
		for(int i = 0; i < insumosSeleccionados.length; i++) {
			
			cuerpoEmail += ((i + 1) + ") " + insumosSeleccionados[i].getCantSolicitada() 
							+ " - " +  insumosSeleccionados[i].getNombre() 
							+ " - " + insumosSeleccionados[i].getDescripcion() + "\n");
		}
	}
		
	public boolean enviarEmail() {
		
		EmailSenderService emailService = new EmailSenderService();
		
		if(!emailService.mandarCorreo(destinatario, asunto, cuerpoEmail)) {
			
			msgError = "Error en el emv�o del email.";
			return false;
		}
		msgError = "Pedido de cotizaci�n enviado.";	
		return true;
	}
	
	public DefaultTableModel getProveedoresCotizaci�n() {
		
		if(presupuesto == null)
			presupuesto = new Presupuesto();
		presupuesto.setIdPedido(pedidoInsumo.getIdCompra());
		ProveedoresDAO proveedoresDAO = new ProveedoresMySQL();
		presupuesto.setProveedores(proveedoresDAO.getProveedoresPresupuesto(pedidoInsumo.getIdCompra()));
		String titulo[] = new String[] {"ID","Nombre","CUIT","Direcci�n"};
		Object tabla[][] = new Object[presupuesto.getProveedores().length][4];
		
		for(int i = 0; i < presupuesto.getProveedores().length; i++) {
			
			tabla[i][0] = presupuesto.getProveedores()[i].getId();
			tabla[i][1] = presupuesto.getProveedores()[i].getNombre();
			tabla[i][2] = presupuesto.getProveedores()[i].getCuit();
			tabla[i][3] = presupuesto.getProveedores()[i].getDireccion();
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
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
		presupuesto.setValidez(partes[2] + "/" + partes[1] + "/" + partes[0]);
		msgError = "";
		return true;
	}

	public DefaultTableModel getTablaInsumos(int pos) {
		
		insumosDAO = new InsumosMySQL();
		insumosDAO.getPedidoPresupuesto(presupuesto, pos);
		String titulo[] = new String[] {"ID", "Cant.", "Nombre", "Descripci�n", "Precio"};
		String tabla[][] = new String[presupuesto.getInsumos().length][5];
		
		for(int i = 0; i < presupuesto.getInsumos().length; i++) {
			
			tabla[i][0] = presupuesto.getInsumos()[i].getId() + "";
			tabla[i][1] = presupuesto.getInsumos()[i].getCantSolicitada() + "";
			tabla[i][2] = presupuesto.getInsumos()[i].getNombre();
			tabla[i][3] = presupuesto.getInsumos()[i].getDescripcion();
			tabla[i][4] = String.format("%.2f", presupuesto.getInsumos()[i].getPrecio()).replace(",", ".");
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo){

			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				
				return column > 3? true: false;
			}
		};
		return respuesta;
	}

	public boolean setGuardarCotizaci�n(JTable valores) {

		for(int i = 0; i < valores.getRowCount(); i++) {

			if(!valores.getValueAt(i, 4).equals("")) {	
					
				try {
					
					float temp = Float.parseFloat((String)valores.getValueAt(i, 4));
					presupuesto.getInsumos()[i].setPrecio(temp);
				} catch(Exception e) {

					msgError = "El Campo precio debe ser num�rico";
					return false;
				}
			} else {
				
				presupuesto.getInsumos()[i].setPrecio(0);
			}
		}
		
		if(!insumosDAO.setPrecios(presupuesto)) {
			
			msgError = "Hubo un problema al intentar guardar la informaci�n en la base de datos.";
			return false; 
		}
		msgError = "La informaci�n se guard� correctamente.";
		return true; 
	}

	public DefaultTableModel getStockInsumos(String filtro) {
		
		insumosDAO = new InsumosMySQL();
		String titulo[] = new String[] {"ID", "Nombre", "Descripci�n", "Presentac�on", "Cant."};
		insumos = insumosDAO.listado(filtro);
		
		Object tabla[][] = new Object[insumos.length][5];
		
		for(int i = 0; i < insumos.length; i++) {
			
			tabla[i][0] = insumos[i].getId();
			tabla[i][1] = insumos[i].getNombre();
			tabla[i][2] = insumos[i].getDescripcion();
			tabla[i][3] = insumos[i].getPresentacion();
			tabla[i][4] = insumos[i].getCant();
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getMsgError() {
		
		return msgError;
	}
	
	public String getId() {
		
		return insumo.getId() + "";
	}
	
	public void setNombre(String nombre) {
		
		if(insumo == null)
			insumo = new Insumo();
		insumo.setNombre(nombre);
	}
	
	public String getNombre() {
		
		return insumo.getNombre();
	}
	
	public void setDescripci�n(String descripci�n) {
		
		insumo.setDescripcion(descripci�n);
	}
	
	public String getDescripci�n() {
		
		return insumo.getDescripcion();
	}
	
	public void setPresentaci�n(String presentaci�n) {
		
		insumo.setPresentacion(presentaci�n);
	}

	public String getPresentaci�n() {
		
		return insumo.getPresentacion();
	}
	
	public void setEstado(int estado) {
		
		insumo.setEstado(estado);
	}
	
	public void setSectorSolicitante(String sector) {
		
		if(pedidoInsumo == null)
			pedidoInsumo = new PedidoInsumo();
		pedidoInsumo.setSectorSolicitante(sector);
	}

	public void setIdSolicitante(int pos) {
		
		pedidoInsumo.setIdSolicitante(listaEmpleados[pos].getLegajo());
	}

	public String getFechaSolicitud() {
		
		return pedidoInsumo.getFechaSolicitud();
	}

	public String getSectorSolicitante() {
		
		return pedidoInsumo.getSectorSolicitante();
	}
	
	public String getNombreSolicitante() {
		
		return pedidoInsumo.getNombreSolicitante();
	}

	public String getEmailSeleccionado(int pos) {
		
		return (String) emailProveedores[pos][3];
	}

	public void setAsunto(String asunto) {
		
		this.asunto = asunto;
	}
	
	public void setMensaje(String mensaje) {
		
		this.mensaje = mensaje;
	}

	
	


	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public DefaultTableModel getElementosAutorizados() {
		
		insumosDAO = new InsumosMySQL();
		String titulo[] = new String[] {"ID", "Nombre", "Descripci�n", "Cantidad", "Precio", "Total", "Sel."};
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

	public void setEmail(String email) {
		
		destinatario = email;
	}

	public void setIdOrdenCompra(int posici�n) {
		
		idOrdenCompra = tabla[posici�n][0];
	}

	public void setIdPresupuesto(String idPresupuesto) {
		
		this.idPresupuesto = idPresupuesto;
	}

	public boolean setAprobarCotizaci�n() {
		
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

	public boolean setStockAgregar(String aDescontar) {
		
		try {
		
			nuevoStock = Integer.parseInt(aDescontar);
		} catch(Exception e) {

			msgError = "La cantidad debe ser un n�mero entero.";
			return false;
		}
		
		if(nuevoStock < 0) {
			
			msgError = "La cantidad debe ser un n�mero positivo.";
			return false;
		}
		return true;
	}
	
	public boolean setStockDescontar(String aDescontar) {
		
		try {
		
			nuevoStock = -Integer.parseInt(aDescontar);
		} catch(Exception e) {

			msgError = "La cantidad debe ser un n�mero entero.";
			return false;
		}
		
		if(nuevoStock > 0) {
			
			msgError = "La cantidad debe ser un n�mero positivo.";
			return false;
		}
		
		if(-nuevoStock > stock) {
			
			msgError = "Quiere dar de baja m�s elementos de los que posee en el stock actual.";
			return false;
		}
		return true;
	}

	public String getStock() {
		
		return stock + "";
	}
	
	public String getCuerpoEmail() {
		
		return cuerpoEmail;
	}

	public DefaultTableModel getTablaOrdenes() {
		
		insumosDAO = new InsumosMySQL();
		String titulo[] = new String[] {"ID", "Fecha", "Sector", "Solicitante", "Autorizado"};
		tabla = insumosDAO.getListadoOrdenesCompra(id, "1");
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}
	
	public DefaultTableModel getHistoricoInsumo() {
		
		insumosDAO = new InsumosMySQL();
		String titulo[] = new String[] {"Fecha", "Proveedor", "Solicitante / sector", "Autoriz�", "Cantidad", "Precio"};
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

	private boolean isNumerico(String valor) {
		
		try {
	
			Integer.parseInt(valor);
		} catch(Exception e) {
			
			return false;
		}
		return true;
	}
}