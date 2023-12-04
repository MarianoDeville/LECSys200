package modelo;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import control.EmailSenderService;
import dao.ComprasMySQL;
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
	private int cantElementos = 0;	
	private String msgError;
	private String destinatario;
	private String asunto;
	private String mensaje;
	private String cuerpoEmail;	
	private Object emailProveedores[][];
	private Empleado listaEmpleados[];
	private Insumo insumos[];
	private Insumo insumosSeleccionados[];
	private PedidoInsumo pedidoInsumos[];
	private Presupuesto presupuesto;
	private Presupuesto presupuestos[];

	public DefaultTableModel getTablaInsumos(String filtro) {
		
		insumosDAO = new InsumosMySQL();
		insumos = insumosDAO.listado(filtro);
		String titulo[] = new String[] {"ID", "Nombre", "Descripción", "Presentacíon"};
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
			
			msgError = "La descripción no puede faltar ni ser demasiado corto.";
			return false;
		} else if(insumo.getPresentacion().length() < 3) {
			
			msgError = "Por favor indique en qué formato viene el producto.";
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
		listaEmpleados = empleadoDAO.getListado("Todos", 1, "");
		
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
		String titulo[] = new String[] {"Cant.", "Nombre", "Descripción", "Presentación"};
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
	
	public boolean actualizarLista(char operación, int pos, String cantidad) {
		
		Insumo temp[] = null;
		int i = 0;
	
		if(operación == '+') {

			try {
				
				i = Integer.parseInt(cantidad);
			}catch (Exception e) {

				msgError = "La cantidad a pedir debe ser numérica.";
				return false;
			}
			
			if(i < 1) {
				
				msgError = "La cantidad a pedir debe ser un número positivo mayor a cero.";
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
		
		if(operación == '-') {
			
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
			
			msgError = "La lista de pedido está vacía.";
			return false;	
		}
		pedidoInsumo.setInsumos(insumosSeleccionados);
		msgError = "Error al intentar guardar en la base de datos.";
		return insumosDAO.nuevoPedido(pedidoInsumo);
	}
	
	public boolean setActualizarPedido() {
		
		if(insumosSeleccionados.length < 1) {
			
			msgError = "La lista de pedido está vacía.";
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
		
		if(presupuesto == null)
			presupuesto = new Presupuesto();
				
		if(presupuesto.getProveedores() == null) {
			
			presupuesto.setProveedores(new Proveedor[1]);
			presupuesto.getProveedores()[0] = new Proveedor();
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
			
			msgError = "Falta completar el campo destinatario con la dirección de email.";
			return false;
		}
		
		if(presupuesto.getProveedores()[0].getId() == 0) {
			
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
		presupuesto.setInsumos(insumosSeleccionados);
		presupuesto.setIdPedido(pedidoInsumo.getIdCompra());
		insumosDAO = new InsumosMySQL();
		presupuesto.setIdPresupuesto(insumosDAO.setPedidoPresupuesto(presupuesto));

		if(presupuesto.getIdPresupuesto() == 0) {
			
			msgError = "Error al guardar el pedido de cotización en la base de datos.";
			return false;			
		}
		setCuerpoEmail();
		return true;
	}
	
	public void setCuerpoEmail() {
		
		cuerpoEmail = mensaje + "\n\nSolicitud cotización nro : " + presupuesto.getIdPresupuesto() +"\n\nElementos a cotizar:\n\n";	
		cuerpoEmail += "Cantidad - Nombre - Descripción\n";
				
		for(int i = 0; i < insumosSeleccionados.length; i++) {
			
			cuerpoEmail += ((i + 1) + ") " + insumosSeleccionados[i].getCantSolicitada() 
							+ " - " +  insumosSeleccionados[i].getNombre() 
							+ " - " + insumosSeleccionados[i].getDescripcion() + "\n");
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
	
	public DefaultTableModel getProveedoresCotización() {
		
		if(presupuesto == null)
			presupuesto = new Presupuesto();
		presupuesto.setIdPedido(pedidoInsumo.getIdCompra());
		ProveedoresDAO proveedoresDAO = new ProveedoresMySQL();
		presupuesto.setProveedores(proveedoresDAO.getProveedoresPresupuesto(pedidoInsumo.getIdCompra()));
		String titulo[] = new String[] {"ID","Nombre","CUIT","Dirección"};
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
		String titulo[] = new String[] {"ID", "Cant.", "Nombre", "Descripción", "Precio"};
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

	public boolean setGuardarCotización(JTable valores) {

		for(int i = 0; i < valores.getRowCount(); i++) {

			if(!valores.getValueAt(i, 4).equals("")) {	
					
				try {
					
					float temp = Float.parseFloat((String)valores.getValueAt(i, 4));
					presupuesto.getInsumos()[i].setPrecio(temp);
				} catch(Exception e) {

					msgError = "El Campo precio debe ser numérico";
					return false;
				}
			} else {
				
				presupuesto.getInsumos()[i].setPrecio(0);
			}
		}
		
		if(!insumosDAO.setPrecios(presupuesto)) {
			
			msgError = "Hubo un problema al intentar guardar la información en la base de datos.";
			return false; 
		}
		msgError = "La información se guardó correctamente.";
		return true; 
	}

	public DefaultTableModel getStockInsumos(String filtro) {
		
		insumosDAO = new InsumosMySQL();
		String titulo[] = new String[] {"ID", "Nombre", "Descripción", "Presentacíon", "Cant."};
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

	public boolean setStockAgregar(String ingreso) {
		
		int cantidad;
		try {
		
			cantidad = Integer.parseInt(ingreso);
		} catch(Exception e) {

			msgError = "La cantidad debe ser un número entero.";
			return false;
		}
		
		if(cantidad < 0) {
			
			msgError = "La cantidad debe ser un número positivo.";
			return false;
		}
		insumo.setCant(insumo.getCant() + cantidad);
		return true;
	}

	public boolean setStockDescontar(String egreso) {
		
		int cantidad;
		try {
		
			cantidad = Integer.parseInt(egreso);
		} catch(Exception e) {

			msgError = "La cantidad debe ser un número entero.";
			return false;
		}
		
		if(cantidad > 0) {
			
			msgError = "La cantidad debe ser un número positivo.";
			return false;
		}
		
		if(-cantidad > insumo.getCant()) {
			
			msgError = "Quiere dar de baja más elementos de los que posee en el stock actual.";
			return false;
		}
		insumo.setCant(insumo.getCant() - cantidad);
		return true;
	}
	
	public DefaultTableModel getHistoricoInsumo() {
		
		insumosDAO = new InsumosMySQL();
		String titulo[] = new String[] {"Fecha", "Proveedor", "Solicitante / sector", "Autorizó", "Cantidad", "Precio"};
		String tabla[][] = insumosDAO.getHistoriaCompras(insumo.getId());
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}

	public DefaultTableModel getTablaCotizaciones() {
		
		insumosDAO = new InsumosMySQL();
		presupuestos = insumosDAO.getCotizaciones(pedidoInsumo);
		String titulo[] = new String[pedidoInsumo.getInsumos().length + 4];
		titulo[0] = "ID";
		titulo[1] = "Proveedor";
		titulo[titulo.length - 2] = "Total";
		titulo[titulo.length - 1] = "Estado";
		Object tabla[][] = new Object[presupuestos.length][titulo.length];

		for(int i = 0; i < presupuestos.length; i++) {
		
			float sumaTotal = 0;
			String estado = null;
			
			for(int e = 0; e < presupuestos[i].getInsumos().length; e++) {
				
				titulo[e + 2] = presupuestos[i].getInsumos()[e].getNombre();
				float valorizacion = presupuestos[i].getInsumos()[e].getCantSolicitada() * presupuestos[i].getInsumos()[e].getPrecio();
				tabla[i][e + 2] = valorizacion;
				sumaTotal += valorizacion;
			}
			
			switch (presupuestos[i].getEstado()) {
			
			case 0:
				estado = "No aprobado";
				break;
		
			case 1:
				estado = "Pendiente";
				break;
		
			case 2:
				estado = "Aprobado";
				break;
				
			default:
				estado = "- - - -";
			}
			tabla[i][0] = presupuestos[i].getIdPresupuesto();
			tabla[i][1] = presupuestos[i].getProveedores()[0].getNombre();
			tabla[i][titulo.length - 1] = estado;
			tabla[i][titulo.length - 2] = sumaTotal;
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo);
		return respuesta;
	}
	
	public boolean setAprobarCotización(int pos) {
		
		OperadorSistema operador = new OperadorSistema();
		ComprasMySQL comprasDAO = new ComprasMySQL();
		
		if(comprasDAO.setOrdenCompra(presupuestos[pos], operador.getFichaEmpleado()))
			msgError = "Orden de compra generada.";
		else
			msgError = "Hubo un error al intentar actualizar la base de datos.";
		return true;
	}

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
	
	public void setDescripción(String descripción) {
		
		insumo.setDescripcion(descripción);
	}
	
	public String getDescripción() {
		
		return insumo.getDescripcion();
	}
	
	public void setPresentación(String presentación) {
		
		insumo.setPresentacion(presentación);
	}

	public String getPresentación() {
		
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

	public String getStock() {
		
		return insumo.getCant() + "";
	}

	public void setEmail(String email) {
		
		destinatario = email;
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