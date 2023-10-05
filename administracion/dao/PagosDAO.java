package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;

public class PagosDAO extends Conexion {

	public String [][] getHistorialPagos(String año, int mes, boolean empl, boolean prov) {
		
		String matriz[][] = null;
		String comandoStatement = "SELECT DATE_FORMAT(Fecha, '%d/%m/%y') AS fecha, proveedores.nombre, persona.nombre, "
								+ "apellido, concepto, monto, factura, formaPago, comentario, TIME_FORMAT(hora, '%H:%i') "
								+ "FROM pagos "
								+ "LEFT JOIN proveedores ON proveedores.idProveedores = pagos.idProveedor "
								+ "LEFT JOIN empleados ON empleados.idEmpleado = pagos.idEmpleados "
								+ "LEFT JOIN persona ON empleados.idPersona = persona.idPersona "
								+ "WHERE((pagos.idProveedor " + (prov?"!":"") + "= 'null' OR pagos.idEmpleados " + (empl?"!":"") + "= 'null')";
		
		if(!año.equals("Todos"))
			comandoStatement += " AND YEAR(fecha) = " + año;
		
		if(mes != 0)
			comandoStatement += " AND MONTH(fecha) = " + mes;
		comandoStatement += ") GROUP BY idPagos ORDER BY idPagos DESC";

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);
			rs.last();	
			matriz = new String[rs.getRow()][8];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2)==null? rs.getString(3) + " " + rs.getString(4): rs.getString(2);
				matriz[i][2] = rs.getString(5);
				matriz[i][3] = String.format("%.2f", rs.getFloat(6));
				matriz[i][4] = rs.getString(7);
				matriz[i][5] = rs.getString(8);
				matriz[i][6] = rs.getString(9);
				matriz[i][7] = rs.getString(10);
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosDAO, getHistorialPagos()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matriz;	
	}

	public String [][] getPagosEmpleado(String idEmpleado, String año, int mes) {
		
		String matriz[][] = null;
		String comandoStatement = "SELECT DATE_FORMAT(fecha, '%d/%m/%y'), concepto, monto, factura, formaPago, comentario "
								+ "FROM pagos ";
		String armoWhere = "WHERE(idEmpleados = " + idEmpleado;
		
		if(!año.equals("Todos"))
			armoWhere += " AND YEAR(fecha) = " + año;
		
		if(mes != 0)
			armoWhere += " AND MONTH(fecha) = " + mes;
		comandoStatement += armoWhere + ") ORDER BY idPagos DESC";

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);
			rs.last();	
			matriz = new String[rs.getRow()][6];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = String.format("%.2f", rs.getFloat(3));
				matriz[i][3] = rs.getString(4);
				matriz[i][4] = rs.getString(5);
				matriz[i][5] = rs.getString(6);
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosDAO, getPagosEmpleado()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matriz;	
	}

	public boolean setPagoEmpleado(String informacion[]) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("INSERT INTO pagos "
																+ "(idEmpleados, concepto, fecha, hora, monto, factura, comentario, formaPago) "
																+ "VALUES (?, ?, DATE(NOW()), TIME(NOW()), ?, ?, ?, ?)");
			stm.setString(1, informacion[0]);
			stm.setString(2, informacion[1]);
			stm.setFloat(3, Float.parseFloat(informacion[2].replace(",", ".")));
			stm.setString(4, informacion[3]);
			stm.setString(5, informacion[4]);
			stm.setString(6, informacion[5]);
			stm.executeUpdate();
		} catch(Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosDAO, setPagoEmpleado()");
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registro pago a empleados.", "Administración", tiempo);
		return bandera;
	}

	public String [][] getDetallePagoProveedor(String idPagos) {
		
		String matriz[][] = null;
		String comandoStatement = "SELECT ordenCompra.idOrdenCompra, ordenCompra.fecha, persona.nombre, apellido, FORMAT(SUM(pedido.cant * precio) ,2) "
								+ "FROM pedidoCompra "
								+ "JOIN pedido ON pedidoCompra.idPedidoCompra = pedido.idSolicitud "
								+ "JOIN presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra "
								+ "JOIN ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
								+ "JOIN cotizaciones ON presupuesto.idPresupuesto = cotizaciones.idPresupuesto "
								+ "JOIN usuarios ON ordenCompra.idAutorizante = usuarios.idUsuarios "
								+ "JOIN empleados ON usuarios.idEmpleado = empleados.idEmpleado "
								+ "JOIN persona ON empleados.idPersona = persona.idPersona "
								+ "WHERE ordenCompra.idPago = " + idPagos
								+ " GROUP BY idOrdenCompra "
								+ "ORDER BY ordenCompra.idOrdenCompra";

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);
			rs.last();	
			matriz = new String[rs.getRow()][5];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3) + " " + rs.getString(4);
				matriz[i][3] = String.format("%.2f", rs.getString(5));
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosDAO, getDetallePagoProveedos()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matriz;	
	}	

	public String [][] getPagosProveedor(String idProveedor, String año, int mes) {
		
		String matriz[][] = null;
		String comandoStatement = "SELECT idPagos, DATE_FORMAT(fecha, '%d/%m/%y'), monto, factura, formaPago, comentario "
								+ "FROM pagos ";
		String armoWhere = "WHERE(idProveedor = " + idProveedor;
		
		if(!año.equals("Todos"))
			armoWhere += " AND YEAR(fecha) = " + año;
		
		if(mes != 0)
			armoWhere += " AND MONTH(fecha) = " + mes;
		comandoStatement += armoWhere + ") ORDER BY idPagos DESC";

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);
			rs.last();	
			matriz = new String[rs.getRow()][6];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = String.format("%.2f", rs.getFloat(3));
				matriz[i][3] = rs.getString(4);
				matriz[i][4] = rs.getString(5);
				matriz[i][5] = rs.getString(6);
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosDAO, getPagosProveedor()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matriz;	
	}

	public boolean setPagoProveedor(String informacion[], String listadoOC[]) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("INSERT INTO pagos "
																+ "(idProveedor, concepto, fecha, hora, monto, factura, comentario, formaPago) "
																+ "VALUES (?, ?, DATE(NOW()), TIME(NOW()), ?, ?, ?, ?)");
			stm.setString(1, informacion[0]);
			stm.setString(2, informacion[1]);
			stm.setFloat(3, Float.parseFloat(informacion[2].replace(",", ".")));
			stm.setString(4, informacion[3]);
			stm.setString(5, informacion[4]);
			stm.setString(6, informacion[5]);
			stm.executeUpdate();
			String idPago = "";
			String comandoStatement = "SELECT MAX(idPagos) FROM pagos";
			stm = this.conexion.prepareStatement(comandoStatement);
			ResultSet rs = stm.executeQuery(comandoStatement);

			if(rs.next())
				idPago = rs.getString(1);

			for(String idOC: listadoOC) {
		
				stm= this.conexion.prepareStatement("UPDATE ordenCompra SET idPago = ? WHERE idOrdenCompra = ?");
				stm.setString(1, idPago);
				stm.setString(2, idOC);
				stm.executeUpdate();
				stm= this.conexion.prepareStatement("UPDATE pedidoCompra "
												  + "JOIN presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra "
												  + "JOIN ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
												  + "SET pedidoCompra.estado = 2 "
												  + "WHERE ordenCompra.idOrdenCompra = ?");
				stm.setString(1, idOC);
				stm.executeUpdate();
			}
		} catch(Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosDAO, setPagoProveedor()");
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registro pago a proveedor.", "Administración", tiempo);
		return bandera;
	}
	
	public String [][] getDeudaProveedores(String idProveedor) {

		String matriz[][] = null;
		String comandoStatement = "SELECT ordenCompra.idOrdenCompra, ordenCompra.fecha, persona.nombre, apellido, FORMAT(SUM(pedido.cant * precio) ,2) "
								+ "FROM pedidoCompra "
								+ "JOIN pedido ON pedidoCompra.idPedidoCompra = pedido.idSolicitud "
								+ "JOIN insumos ON pedido.idInsumo = insumos.idInsumos "
								+ "JOIN presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra "
								+ "JOIN ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
								+ "JOIN cotizaciones ON presupuesto.idPresupuesto = cotizaciones.idPresupuesto "
								+ "JOIN usuarios ON ordenCompra.idAutorizante = usuarios.idUsuarios "
								+ "JOIN empleados ON usuarios.idEmpleado = empleados.idEmpleado "
								+ "JOIN persona ON empleados.idPersona = persona.idPersona "
								+ "JOIN proveedores ON presupuesto.idProveedor = proveedores.idProveedores "
								+ "WHERE(proveedores.idProveedores = " + idProveedor + " AND pedidoCompra.estado = 1 "
										+ "AND presupuesto.estado = 2 AND insumos.idInsumos = cotizaciones.idInsumo) "
								+ "GROUP BY idOrdenCompra ORDER BY fecha";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);
			rs.last();	
			matriz = new String[rs.getRow()][5];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3) + " " + rs.getString(4);
				matriz[i][3] = rs.getString(5);
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosDAO, getDeudaProveedores()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}
	
	public String [][] getListadoProveedores(String filtrado, boolean deuda) {

		String matriz[][] = null;
		String comandoStatement = "SELECT idProveedor, nombre, cuit, direccion "
								+ "FROM proveedores "
								+ "JOIN presupuesto ON proveedores.idProveedores = presupuesto.idProveedor "
								+ "JOIN pedidocompra ON presupuesto.idPedidoCompra = pedidoCompra.idPedidoCompra "
								+ "WHERE (proveedores.estado = 1 AND presupuesto.estado = 2 AND pedidoCompra.estado = " + (deuda?"1 ":"2 ") 
								+"AND (nombre LIKE '%%' OR cuit LIKE'%')) GROUP BY cuit ORDER BY nombre";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);
			rs.last();	
			matriz = new String[rs.getRow()][4];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);
				matriz[i][3] = rs.getString(4);
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosDAO, getListadoProveedores()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}
}