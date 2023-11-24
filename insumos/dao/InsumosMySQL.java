package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;
import modelo.Insumos;

public class InsumosMySQL extends Conexion {

	private String nombreSolicitante;
	private String idSolicitante;
	private String sectorSolicitante;
	private String fechaSolicitud;
	private String estado;
	private String FechaValidez;
	
	
	
	
	
	
	
	public Insumos[] getHistoriaCompras(int idInsumo) {
		
		Insumos matriz[] = null;
		String cmdStm = "SELECT DATE_FORMAT(ordenCompra.fecha, '%d/%m/%Y'), proveedores.nombre, persona.nombre, persona.apellido, "
									+ "pedidoCompra.sectorSolicitante, usuarios.nombre, pedido.cant, cotizaciones.precio "
								+ "FROM `lecsys2.00`.insumos "
								+ "JOIN `lecsys2.00`.cotizaciones ON insumos.idInsumos = cotizaciones.idInsumo "
								+ "JOIN `lecsys2.00`.pedido ON insumos.idInsumos = pedido.idInsumo "
								+ "JOIN `lecsys2.00`.pedidoCompra ON pedido.idSolicitud = pedidoCompra.idPedidoCompra " 
								+ "JOIN `lecsys2.00`.empleados ON pedidoCompra.idSolicitante = empleados.idEmpleado "
								+ "JOIN `lecsys2.00`.persona ON empleados.idPersona = persona.idPersona "
								+ "JOIN `lecsys2.00`.presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra " 
								+ "JOIN `lecsys2.00`.ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
								+ "JOIN `lecsys2.00`.usuarios ON ordenCompra.idAutorizante = usuarios.idUsuarios "
								+ "JOIN `lecsys2.00`.proveedores ON presupuesto.idProveedor = proveedores.idProveedores "
								+ "WHERE (insumos.idInsumos = " + idInsumo 
									+ " AND pedidoCompra.estado = 2 AND cotizaciones.idPresupuesto = presupuesto.idPresupuesto) "
								+ "ORDER BY ordenCompra.fecha DESC LIMIT 20";
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs = stm.executeQuery(cmdStm);
			rs.last();	
			matriz = new Insumos[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				matriz[i] = rs.getString(1);
				matriz[i] = rs.getString(2);
				matriz[i] = rs.getString(3) + " " + rs.getString(4) + " / " + rs.getString(5);
				matriz[i] = rs.getString(6);
				matriz[i] = rs.getString(7);
				matriz[i] = String.format("%.2f", rs.getFloat(8));
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, getHistoriaCompras()");
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean setAgregarStock(String idOrdenCompra[]) {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		
		try {
			
			this.conectar();
			PreparedStatement stm;
			
			for(String idOC: idOrdenCompra) {
				
				stm = this.conexion.prepareStatement("UPDATE `lecsys2.00`.insumos "
												   + "JOIN `lecsys2.00`.pedido ON insumos.idInsumos = pedido.idInsumo "
												   + "JOIN `lecsys2.00`.pedidoCompra ON pedido.idSolicitud = pedidoCompra.idPedidoCompra "
												   + "JOIN `lecsys2.00`.presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra "
												   + "JOIN `lecsys2.00`.cotizaciones ON presupuesto.idPresupuesto = cotizaciones.idPresupuesto "
												   + "JOIN `lecsys2.00`.ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
												   + "SET insumos.cant = pedido.cant "
												   + "WHERE (ordenCompra.idOrdenCompra = ? AND insumos.idInsumos = cotizaciones.idInsumo)");
				stm.setString(1, idOC);
				stm.executeUpdate();
			}
		} catch(Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, setAgregarStock()");
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Carga de compras en el stock de insumos.", "Insumos.", tiempo);
		return bandera;
	}
	
	public String [][] getOrdenCompra(String idOrdenCompra){
		
		String matriz[][] = null;
		String cmdStm = "SELECT insumos.idInsumos, nombre, descripci�n, pedido.cant, precio, pedidoCompra.idPedidoCompra "
								+ "FROM `lecsys2.00`.pedidoCompra "
								+ "JOIN `lecsys2.00`.pedido ON pedidoCompra.idPedidoCompra = pedido.idSolicitud "
								+ "JOIN `lecsys2.00`.insumos ON pedido.idInsumo = insumos.idInsumos "
								+ "JOIN `lecsys2.00`.presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra "
								+ "JOIN `lecsys2.00`.ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
								+ "JOIN `lecsys2.00`.cotizaciones ON presupuesto.idPresupuesto = cotizaciones.idPresupuesto "
								+ "WHERE (ordenCompra.idOrdenCompra = " + idOrdenCompra + " AND insumos.idInsumos = cotizaciones.idInsumo)";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs = stm.executeQuery(cmdStm);
			rs.last();	
			matriz = new String[rs.getRow()][6];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);
				matriz[i][3] = rs.getString(4);
				matriz[i][4] = String.format("%.2f", rs.getFloat(5));
				matriz[i][5] = rs.getString(6);
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, getOrdenCompra()");
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}
	
	public String [][] getListadoOrdenesCompra(String idInsumo, String estado) {
		
		String matriz[][]=null;
		String cmdStm = "SELECT idOrdenCompra, date_format(ordenCompra.fecha, '%d/%m/%Y'), sectorSolicitante, "
								+ "persona.nombre, persona.apellido, usuarios.nombre, pedidoCompra.idPedidoCompra "
								+ "FROM `lecsys2.00`.ordenCompra " 
								+ "JOIN `lecsys2.00`.presupuesto ON ordenCompra.idPresupuesto = presupuesto.idPresupuesto "
								+ "JOIN `lecsys2.00`.pedidoCompra ON presupuesto.idPedidoCompra = pedidoCompra.idPedidoCompra "
								+ "JOIN `lecsys2.00`.cotizaciones ON presupuesto.idPresupuesto = cotizaciones.idPresupuesto "
								+ "JOIN `lecsys2.00`.usuarios ON ordenCompra.idAutorizante = usuarios.idUsuarios "
								+ "JOIN `lecsys2.00`.empleados ON pedidoCompra.idSolicitante = empleados.idEmpleado "
								+ "JOIN `lecsys2.00`.persona ON empleados.idPersona = persona.idPersona "
				 				+ "WHERE (presupuesto.estado = 2 AND pedidoCompra.estado = " + estado 
				 				+ " AND cotizaciones.idInsumo = " + idInsumo + ") "
				 				+ "ORDER BY idOrdenCompra";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			matriz = new String[rs.getRow()][6];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);
				matriz[i][3] = rs.getString(4) + " " + rs.getString(5);
				matriz[i][4] = rs.getString(6);
				matriz[i][5] = rs.getString(7);
				i++;
			}
		}catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, getListadoOrdenesCompra()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}
	
	public boolean setActualizarStock(String idInsumo, int cantidad) {
	
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
	
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("UPDATE insumos SET cant = ? WHERE idInsumos = ?");
			stm.setInt(1, cantidad);
			stm.setString(2, idInsumo);
			stm.executeUpdate();
		} catch(Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, setActualizarStock()");
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Baja en el stock de insumos.", "Insumos.", tiempo);
		return bandera;
	}
	
	public String[][] getTablaCotizaciones(String idPedidoCompra, String idInsumos[]){
		
		float precio = 0;
		int cant = 0;
		float acumulado;
		String matriz[][] = null;
		String cmdStm = "SELECT idPresupuesto, nombre, presupuesto.estado FROM `lecsys2.00`.presupuesto "
								+ "JOIN `lecsys2.00`.proveedores ON presupuesto.idProveedor = proveedores.idProveedores "
								+ "WHERE idPedidoCompra = " + idPedidoCompra;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			matriz = new String[rs.getRow()][idInsumos.length + 4];
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				
				switch (rs.getInt(3)) {
				
					case 0:
						matriz[i][idInsumos.length + 3] = "No aprobado";
						break;
				
					case 1:
						matriz[i][idInsumos.length + 3] = "Pendiente";
						break;
				
					case 2:
						matriz[i][idInsumos.length + 3] = "Aprobado";
						break;
						
					default:
						matriz[i][idInsumos.length + 3] = "- - - -";
				}
				i++;
			}
			
			for(int e = 0; e < matriz.length; e++) {
				
				acumulado = 0;
				
				for(int a = 0; a < idInsumos.length; a++) {
					
					cmdStm = "SELECT precio, 2 FROM `lecsys2.00`.cotizaciones "
									 + "WHERE (idPresupuesto = " + matriz[e][0] + " AND idInsumo = " + idInsumos[a] + ")";
					rs = stm.executeQuery(cmdStm);
			
					if(rs.next())
						precio = rs.getFloat(1);
					matriz[e][a + 2] = String.format("%.2f", precio);
					cmdStm = "SELECT cant FROM `lecsys2.00`.pedido "
									 + "WHERE (idSolicitud = " + idPedidoCompra + " AND idInsumo = " + idInsumos[a] + ")";
					rs = stm.executeQuery(cmdStm);
					cant = 0;
					
					if(rs.next())
						cant = rs.getInt(1);
					acumulado += cant * precio;
				}
				matriz[e][idInsumos.length + 2] = String.format("%.2f", acumulado);
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, getTablaCotizaciones()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}

	public boolean setPrecios(String tabla[][], String validez) {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		PreparedStatement stm;
		
		try {
			
			this.conectar();
			stm = this.conexion.prepareStatement("UPDATE `lecsys2.00`.presupuesto SET validez = ? WHERE idPresupuesto = ?");
			stm.setString(1, validez);
			stm.setString(2, tabla[0][5]);
			stm.executeUpdate();
			
			for(int i = 0; i < tabla.length; i++) {

				stm = this.conexion.prepareStatement("DELETE FROM `lecsys2.00`.cotizaciones WHERE (idInsumo = ? AND idPresupuesto = ?)");
				stm.setString(1, tabla[i][0]);
				stm.setString(2, tabla[i][5]);
				stm.executeUpdate();
				
				if(!tabla[i][4].equals("")) {
					
					stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.cotizaciones (precio, idInsumo, idPresupuesto) VALUES (?, ?, ?)");
					stm.setString(1, tabla[i][4]);
					stm.setString(2, tabla[i][0]);
					stm.setString(3, tabla[i][5]);
					stm.executeUpdate();
				}
			}
		} catch(Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, setPrecios()");
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Actualizar precios de insumos en la cotizaci�n.", "Insumos.", tiempo);
		return bandera;
	}

	public String[][] getPedidoPresupuesto(String idPedidoCompra, String idProveedor){
		
		String matriz[][] = null;
		String idPresupuesto = "";
		String cmdStm = "SELECT idPresupuesto, date_format(fecha, '%d/%m/%Y'), date_format(validez, '%d/%m/%Y') "
								+ "FROM `lecsys2.00`.presupuesto "
								+ "WHERE (idPedidoCompra = " + idPedidoCompra + " AND idProveedor = " + idProveedor + ")";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);			
			ResultSet rs = stm.executeQuery(cmdStm);
			
			if(rs.next()) {
				
				idPresupuesto = rs.getString(1);
				fechaSolicitud = rs.getString(2);
				FechaValidez = rs.getString(3);
			}
			cmdStm = "SELECT pedido.idInsumo, pedido.cant, nombre, descripci�n FROM `lecsys2.00`.pedido "
							 + "JOIN `lecsys2.00`.insumos ON pedido.idInsumo = insumos.idInsumos "
							 + "WHERE idSolicitud = " + idPedidoCompra + " ORDER BY idInsumos";

			rs = stm.executeQuery(cmdStm);
			rs.last();	
			matriz = new String[rs.getRow()][6];
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);
				matriz[i][3] = rs.getString(4);
				matriz[i][4] = "";
				i++;
			}
			i = 0;

			while(i < matriz.length) {
				
				cmdStm = "SELECT precio FROM `lecsys2.00`.cotizaciones "
								 + "WHERE (idInsumo = " + matriz[i][0] + " AND idPresupuesto = " + idPresupuesto + ")";
				rs = stm.executeQuery(cmdStm);
				
				if(rs.next())
					matriz[i][4] = String.format("%.2f", rs.getFloat(1));
				matriz[i][5] = idPresupuesto;
				i++;
			}			
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, getPedidoPresupuesto()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}

	public String [][] getProveedoresPresupuesto(String idPedidoCompra){
		
		String matriz[][] = null;
		String cmdStm = "SELECT presupuesto.idProveedor, nombre, cuit, direccion "
								+ "FROM `lecsys2.00`.presupuesto "
								+ "JOIN `lecsys2.00`.proveedores ON presupuesto.idProveedor = proveedores.idProveedores "
								+ "WHERE idPedidoCompra = " + idPedidoCompra;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			matriz = new String[rs.getRow()][4];
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);
				matriz[i][3] = rs.getString(4);
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, getProveedoresPresupuesto()");
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}

	public String setPedidoPresupuesto(String idPedidoCompra, String idProveedor, String idInsumos[]) {
		
		String idPresupuesto = null;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("SELECT idPresupuesto FROM `lecsys2.00`.presupuesto WHERE (idPedidoCompra = ? AND idProveedor = ?)");
			stm.setString(1, idPedidoCompra);
			stm.setString(2, idProveedor);
			ResultSet rs = stm.executeQuery();

			if(rs.next())
				idPresupuesto = rs.getString(1);

			if(idPresupuesto == null) {
			
				stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.presupuesto (idPedidoCompra, fecha, idProveedor, estado) VALUES (?, DATE(NOW()), ?, 1)");
				stm.setString(1, idPedidoCompra);
				stm.setString(2, idProveedor);
				stm.executeUpdate();
				rs = stm.executeQuery("SELECT MAX(idPresupuesto) FROM `lecsys2.00`.presupuesto");
				
				if(rs.next())
					idPresupuesto = rs.getString(1);
			} else {

				idPresupuesto = "";
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, setPedidoPresupuesto()");
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Creaci�n de pedido de cotizaci�n.", "Insumos.", tiempo);
		return idPresupuesto;
	}

	public boolean setEliminarPedido(String idPedidoCompra) {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		String commandStatement = null;
		DtosActividad dtosActividad = new DtosActividad();

		try {
			
			this.conectar();
			commandStatement = "UPDATE `lecsys2.00`.pedidoCompra SET estado = 0 WHERE idPedidoCompra = " + idPedidoCompra;
			PreparedStatement stm = this.conexion.prepareStatement(commandStatement);
			stm.executeUpdate();
			commandStatement = "DELETE FROM `lecsys2.00`.pedido WHERE idSolicitud = " + idPedidoCompra;
			stm = this.conexion.prepareStatement(commandStatement);
			stm.executeUpdate();
		} catch(Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, setActualizarPedido()");
			CtrlLogErrores.guardarError(commandStatement);
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Borrar pedido de compra de insumos.", "Insumos.", tiempo);
		return bandera;
	}
	
	public boolean setActualizarPedido(String idPedidoCompra, String tablaPedidos[][]) {
			
			boolean bandera = true;
			long tiempo = System.currentTimeMillis();
			String commandStatement = null;
			DtosActividad dtosActividad = new DtosActividad();

			try {
				
				this.conectar();
				commandStatement = "UPDATE `lecsys2.00`.pedidoCompra SET fecha = DATE(NOW()) WHERE idPedidoCompra = " + idPedidoCompra;
				PreparedStatement stm = this.conexion.prepareStatement(commandStatement);
				stm.executeUpdate();
				commandStatement = "DELETE FROM `lecsys2.00`.pedido WHERE idSolicitud = " + idPedidoCompra;
				stm = this.conexion.prepareStatement(commandStatement);
				stm.executeUpdate();
				int i = 0;

				while(i < tablaPedidos.length) {
					commandStatement = "INSERT INTO `lecsys2.00`.pedido (idInsumo, idSolicitud, cant) VALUES (?, ?, ?)";
					stm = this.conexion.prepareStatement(commandStatement);
					stm.setString(1, tablaPedidos[i][4]);
					stm.setString(2, idPedidoCompra);
					stm.setString(3, tablaPedidos[i][0]);
					stm.executeUpdate();
					i++;
				}
			} catch(Exception e) {
				
				bandera = false;
				CtrlLogErrores.guardarError(e.getMessage());
				CtrlLogErrores.guardarError("InsumosDAO, setActualizarPedido()");
				CtrlLogErrores.guardarError(commandStatement);
			} finally {
				
				this.cerrar();
			}
			tiempo = System.currentTimeMillis() - tiempo;
			dtosActividad.registrarActividad("Actualizar pedido de compra de insumos.", "Insumos.", tiempo);
			return bandera;
	}
	
	public String [][] getPedido(String idPedidoCompra){
		
		String matriz[][] = null;
		String cmdStm = "SELECT fecha, sectorSolicitante, idSolicitante, nombre, apellido, pedidoCompra.estado "
								+ "FROM `lecsys2.00`.pedidoCompra "
								+ "JOIN `lecsys2.00`.empleados ON pedidoCompra.idSolicitante = empleados.idEmpleado "
								+ "JOIN `lecsys2.00`.persona ON empleados.idPersona = persona.idPersona "
								+ "WHERE idPedidoCompra = " + idPedidoCompra;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);

			if(rs.next()) {
				
				fechaSolicitud = rs.getString(1);
				sectorSolicitante = rs.getString(2);
				idSolicitante = rs.getString(3);
				nombreSolicitante = rs.getString(4) + ", " + rs.getString(5);
				estado = rs.getString(6);
			}
			
			cmdStm = "SELECT pedido.cant, nombre, descripci�n, presentaci�n, idInsumos FROM `lecsys2.00`.pedido "
							 + "JOIN `lecsys2.00`.insumos ON pedido.idInsumo = insumos.idInsumos "
							 + "WHERE idSolicitud = " + idPedidoCompra;
			rs = stm.executeQuery(cmdStm);
			rs.last();	
			matriz = new String[rs.getRow()][5];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);
				matriz[i][3] = rs.getString(4);
				matriz[i][4] = rs.getString(5);
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, getPedido()");
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}

	public boolean setListadoPedido(String sector, int idSolicitante, String tablaPedidos[][]) {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		int idPedidoCompra = 0;
		String commandStatement = null;
		DtosActividad dtosActividad = new DtosActividad();

		try {
			
			this.conectar();
			commandStatement = "INSERT INTO `lecsys2.00`.pedidoCompra (fecha, sectorSolicitante, estado, idSolicitante) "
							 + "VALUES (DATE(NOW()), ?, 1, ?)";
			PreparedStatement stm = this.conexion.prepareStatement(commandStatement);
			stm.setString(1, sector);
			stm.setInt(2, idSolicitante);
			stm.executeUpdate();
			commandStatement = "SELECT MAX(idPedidoCompra) FROM pedidoCompra";
			ResultSet rs = stm.executeQuery(commandStatement);
			
			if(rs.next())
				idPedidoCompra = rs.getInt(1);

			int i = 0;
			
			while(i < tablaPedidos.length) {
				commandStatement = "INSERT INTO `lecsys2.00`.pedido (idInsumo, idSolicitud, cant) VALUES (?, ?, ?)";
				stm = this.conexion.prepareStatement(commandStatement);
				stm.setString(1, tablaPedidos[i][4]);
				stm.setInt(2, idPedidoCompra);
				stm.setString(3, tablaPedidos[i][0]);
				stm.executeUpdate();
				i++;
			}
		} catch(Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, setListadoPedido()");
			CtrlLogErrores.guardarError(commandStatement);
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registrar nuevo pedido de compra de insumos.", "Insumos.", tiempo);
		return bandera;
	}
	
	public String [][] getListadoPedidos(int estado, String sector) {
		
		String matriz[][]=null;
		String cmdStm = "SELECT DATE_FORMAT(fecha, '%d/%m/%Y'), sectorSolicitante, nombre, apellido, idPedidoCompra "
								+ "FROM `lecsys2.00`.pedidoCompra "
								+ "JOIN `lecsys2.00`.empleados ON pedidoCompra.idSolicitante = empleados.idEmpleado "
								+ "JOIN `lecsys2.00`.persona ON empleados.idPersona = persona.idPersona "
								+ "WHERE pedidoCompra.estado = " + estado;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			matriz = new String[rs.getRow()][5];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				matriz[i][0] = rs.getString(5);
				matriz[i][1] = rs.getString(1);
				matriz[i][2] = rs.getString(2);
				matriz[i][3] = rs.getString(3) + " " + rs.getString(4);
				i++;
			}
			i = 0;
						
			while(i < matriz.length) {
				
				cmdStm = "SELECT nombre FROM `lecsys2.00`.insumos "
								 + "JOIN `lecsys2.00`.pedido ON insumos.idInsumos = pedido.idInsumo "
								 + "WHERE idSolicitud = " + matriz[i][0];
				rs = stm.executeQuery(cmdStm);
				boolean bandera = true;
				String productos = "";
				
				while(rs.next()) {
					
					if(!bandera)
						productos += ", ";
					
					productos += rs.getString(1);
					bandera = false;
				}
				matriz[i][4] = productos;
				i++;
			}
		}catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, getListadoPedidos()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}
	
	public boolean setActualizarInsumo(String id, String nombre, String descrip, String formato, String estado) {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("UPDATE `lecsys2.00`.insumos "
																+ "SET nombre = ?, descripci�n = ?, presentaci�n = ?, estado = ? "
																+ "WHERE idInsumos = ? ");
			stm.setString(1, nombre);
			stm.setString(2, descrip);
			stm.setString(3, formato);
			stm.setString(4, estado);
			stm.setString(5, id);
			stm.executeUpdate();
		} catch(Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, setActualizarInsumo()");
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registrar nuevo insumo.", "Insumos.", tiempo);
		return bandera;
	}
	
	public boolean setInsumo(String nombre, String descrip, String formato) {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.insumos (nombre, descripci�n, presentaci�n, estado, cant)"
																 + " VALUES (?, ?, ?, 1, 0)");
			stm.setString(1, nombre);
			stm.setString(2, descrip);
			stm.setString(3, formato);
			stm.executeUpdate();
		} catch(Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, setInsumo()");
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registrar nuevo insumo.", "Insumos.", tiempo);
		return bandera;
	}
	
	public String [][] getListadoInsumos(String filtrado) {
		
		String matriz[][]=null;
		String cmdStm = "SELECT idInsumos, nombre, descripci�n, presentaci�n, cant "
								+ "FROM `lecsys2.00`.insumos "
				 				+ "WHERE (estado = 1 AND (nombre LIKE '%" + filtrado + "%' OR descripci�n LIKE '%" + filtrado + "%')) "
				 				+ "ORDER BY nombre";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			matriz = new String[rs.getRow()][5];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);
				matriz[i][3] = rs.getString(4);
				matriz[i][4] = rs.getString(5);
				i++;
			}
		}catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosDAO, getListadoInsumos()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}

	public String getNombreSolicitante() {
		
		return nombreSolicitante;
	}

	public String getIdSolicitante() {
		
		return idSolicitante;
	}

	public String getSectorSolicitante() {
		
		return sectorSolicitante;
	}

	public String getFechaSolicitud() {
		
		return fechaSolicitud;
	}
	
	public String getEstado() {
		
		return estado;
	}

	public String getFechaValidez() {
		
		return FechaValidez;
	}
}