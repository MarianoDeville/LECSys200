package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;
import modelo.Empleado;
import modelo.OrdenCompra;
import modelo.Pago;
import modelo.Proveedor;

public class PagosMySQL extends Conexion implements PagosDAO{

	@Override
	public Proveedor [] getProveedores(String filtrado, boolean deuda) {

		Proveedor respuesta[] = null;
		String cmdStm = "SELECT idProveedor, nombre, cuit, direccion, tipo, proveedores.estado "
						+ "FROM `lecsys2.00`.proveedores "
						+ "JOIN `lecsys2.00`.presupuesto ON proveedores.idProveedores = presupuesto.idProveedor "
						+ "JOIN `lecsys2.00`.pedidocompra ON presupuesto.idPedidoCompra = pedidoCompra.idPedidoCompra "
						+ "WHERE (proveedores.estado = 1 AND presupuesto.estado = 2 AND pedidoCompra.estado = " + (deuda?"1 ":"2 ") 
						+"AND (nombre LIKE '%%' OR cuit LIKE'%')) GROUP BY cuit ORDER BY nombre";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			respuesta = new Proveedor[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				respuesta[i] = new Proveedor();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setNombre(rs.getString(2));
				respuesta[i].setCuit(rs.getString(3));
				respuesta[i].setDireccion(rs.getString(4));
				respuesta[i].setTipo(rs.getString(5));
				respuesta[i].setEstado(rs.getInt(6));
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosMySQL, getProveedores()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}

	@Override
	public OrdenCompra [] getDeudaProveedores(Proveedor proveedor) {

		OrdenCompra respuesta[] = null;
		String cmdStm = "SELECT ordenCompra.idOrdenCompra, ordenCompra.fecha, persona.nombre, apellido, SUM(pedido.cant * precio) "
						+ "FROM `lecsys2.00`.pedidoCompra "
						+ "JOIN `lecsys2.00`.pedido ON pedidoCompra.idPedidoCompra = pedido.idSolicitud "
						+ "JOIN `lecsys2.00`.insumos ON pedido.idInsumo = insumos.idInsumos "
						+ "JOIN `lecsys2.00`.presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra "
						+ "JOIN `lecsys2.00`.ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
						+ "JOIN `lecsys2.00`.cotizaciones ON presupuesto.idPresupuesto = cotizaciones.idPresupuesto "
						+ "JOIN `lecsys2.00`.persona ON ordenCompra.autoriza = persona.dni "
						+ "JOIN `lecsys2.00`.proveedores ON presupuesto.idProveedor = proveedores.idProveedores "
						+ "WHERE(proveedores.idProveedores = ? AND pedidoCompra.estado = 1 "
								+ "AND presupuesto.estado = 2 AND insumos.idInsumos = cotizaciones.idInsumo) "
						+ "GROUP BY idOrdenCompra ORDER BY fecha";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setInt(1, proveedor.getId());
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new OrdenCompra[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				respuesta[i] = new OrdenCompra();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setFecha(rs.getString(2));
				respuesta[i].setNombreAutoriza(rs.getString(3) + " " + rs.getString(4));
				respuesta[i].setMontoTotal(rs.getFloat(5));
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosMySQL, getDeudaProveedores()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
	
	@Override
	public boolean setPagoProveedor(Pago infoPago, OrdenCompra ordenesCompra[]) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String cmdStm = "INSERT INTO `lecsys2.00`.pagos "
						+ "(idProveedor, concepto, fecha, hora, monto, factura, comentario, formaPago ) "
						+ "VALUES (?, ?, DATE(NOW()), TIME(NOW()), ?, ?, ?, ?)";
	
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setInt(1, infoPago.getIdProveedor());
			stm.setString(2, infoPago.getConcepto());
			stm.setFloat(3, infoPago.getMonto());
			stm.setString(4, infoPago.getFactura());
			stm.setString(5, infoPago.getComentario());
			stm.setString(6, infoPago.getFormaPago());
			stm.executeUpdate();
			
			if(ordenesCompra != null) {
			
				cmdStm = "SELECT MAX(idPagos) FROM `lecsys2.00`.pagos";
				stm = this.conexion.prepareStatement(cmdStm);
				ResultSet rs = stm.executeQuery();
	
				if(rs.next())
					infoPago.setId(rs.getInt(1));

				for(OrdenCompra ordenes: ordenesCompra) {
			
					cmdStm = "UPDATE `lecsys2.00`.ordenCompra SET idPago = ? WHERE idOrdenCompra = ?";
					stm= this.conexion.prepareStatement(cmdStm);
					stm.setInt(1, infoPago.getId());
					stm.setInt(2, ordenes.getId());
					stm.executeUpdate();
					cmdStm = "UPDATE `lecsys2.00`.pedidoCompra "
							  + "JOIN `lecsys2.00`.presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra "
							  + "JOIN `lecsys2.00`.ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
							  + "SET pedidoCompra.estado = 2 "
							  + "WHERE ordenCompra.idOrdenCompra = ?";
					stm= this.conexion.prepareStatement(cmdStm);
					stm.setInt(1, ordenes.getId());
					stm.executeUpdate();
				}
			}
		} catch(Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosMySQL, setPagoProveedor()");
			CtrlLogErrores.guardarError(cmdStm);
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registro pago a proveedor.", "Administración", tiempo);
		return bandera;
	}
	
	@Override
	public Pago [] getPagosProveedor(Proveedor proveedor, String año, int mes) {
		
		Pago matriz[] = null;
		String cmdStm = "SELECT idPagos, DATE_FORMAT(fecha, '%d/%m/%y'), monto, factura, formaPago, comentario, concepto "
						+ "FROM `lecsys2.00`.pagos WHERE(idProveedor = ?";
		
		if(!año.equals("Todos"))
			cmdStm += " AND YEAR(fecha) = " + año;
		
		if(mes != 0)
			cmdStm += " AND MONTH(fecha) = " + mes;
		cmdStm += ") ORDER BY idPagos DESC";

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setInt(1, proveedor.getId());
			ResultSet rs = stm.executeQuery();
			rs.last();	
			matriz = new Pago[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				matriz[i] = new Pago();
				matriz[i].setId(rs.getInt(1));
				matriz[i].setFecha(rs.getString(2));
				matriz[i].setMonto(rs.getFloat(3));
				matriz[i].setFactura(rs.getString(4));
				matriz[i].setFormaPago(rs.getString(5));
				matriz[i].setComentario(rs.getString(6));
				matriz[i].setConcepto(rs.getString(7));
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosMySQL, getPagosProveedor()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return matriz;	
	}

	@Override
	public OrdenCompra [] getDetallePagoProveedor(Pago pago) {
		
		OrdenCompra respuesta[] = null;
		String cmdStm = "SELECT ordenCompra.idOrdenCompra, ordenCompra.fecha, persona.nombre, apellido, SUM(pedido.cant * precio) "
						+ "FROM `lecsys2.00`.pedidoCompra "
						+ "JOIN `lecsys2.00`.pedido ON pedidoCompra.idPedidoCompra = pedido.idSolicitud "
						+ "JOIN `lecsys2.00`.presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra "
						+ "JOIN `lecsys2.00`.ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
						+ "JOIN `lecsys2.00`.cotizaciones ON presupuesto.idPresupuesto = cotizaciones.idPresupuesto "
						+ "JOIN `lecsys2.00`.persona ON ordenCompra.autoriza = persona.dni "
						+ "WHERE ordenCompra.idPago = ? "
						+ " GROUP BY idOrdenCompra "
						+ "ORDER BY ordenCompra.idOrdenCompra";

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setInt(1, pago.getId());
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new OrdenCompra[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				respuesta[i] = new OrdenCompra();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setFecha(rs.getString(2));
				respuesta[i].setNombreAutoriza(rs.getString(3) + " " + rs.getString(4));;
				respuesta[i].setMontoTotal(rs.getFloat(5));
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosMySQL, getDetallePagoProveedor()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return respuesta;	
	}

	@Override
	public boolean setPagoEmpleado(Pago infoPago) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String cmdStm = "INSERT INTO `lecsys2.00`.pagos "
						+ "(idEmpleados, concepto, fecha, hora, monto, factura, comentario, formaPago ) "
						+ "VALUES (?, ?, DATE(NOW()), TIME(NOW()), ?, ?, ?, ?)";

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setInt(1, infoPago.getIdEmpleado());
			stm.setString(2, infoPago.getConcepto());
			stm.setFloat(3, infoPago.getMonto());
			stm.setString(4, infoPago.getFactura());
			stm.setString(5, infoPago.getComentario());
			stm.setString(6, infoPago.getFormaPago());
			stm.executeUpdate();
		} catch(Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosMySQL, setPagoEmpleado()");
			CtrlLogErrores.guardarError(cmdStm);
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registro pago a empleados.", "Administración", tiempo);
		return bandera;
	}

	@Override
	public Pago [] getPagosEmpleado(Empleado empleado, String año, int mes) {
		
		Pago respuesta[] = null;
		String cmdStm = "SELECT DATE_FORMAT(fecha, '%d/%m/%y'), concepto, monto, factura, formaPago, comentario, idPagos "
						+ "FROM `lecsys2.00`.pagos WHERE(idEmpleados = ?";
		
		if(!año.equals("Todos"))
			cmdStm += " AND YEAR(fecha) = " + año;
		
		if(mes != 0)
			cmdStm += " AND MONTH(fecha) = " + mes;
		cmdStm += ") ORDER BY idPagos DESC";

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setInt(1, empleado.getLegajo());
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new Pago[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				respuesta[i] = new Pago();
				respuesta[i].setFecha(rs.getString(1));
				respuesta[i].setConcepto(rs.getString(2));
				respuesta[i].setMonto(rs.getFloat(3));
				respuesta[i].setFactura(rs.getString(4));
				respuesta[i].setFormaPago(rs.getString(5));
				respuesta[i].setComentario(rs.getString(6));
				respuesta[i].setId(rs.getInt(7));
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosMySQL, getPagosEmpleado()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return respuesta;	
	}
	
	@Override
	public String[] getListadoAños() {
		
		String listado[] = null;
		String cmdStm = "SELECT YEAR(fecha) FROM `lecsys2.00`.pagos GROUP BY YEAR(fecha)";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);			
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			listado = new String[rs.getRow() + 1];
			rs.beforeFirst();
			listado[0] = "Todos";
			int i = 1;
			
			while(rs.next()) {

				listado[i] = rs.getString(1);
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosMySQL, getListadoAños()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return listado;
	}

	@Override
	public Pago [] getHistorialPagos(String año, int mes, boolean empl, boolean prov) {
		
		Pago matriz[] = null;
		String cmdStm = "SELECT DATE_FORMAT(Fecha, '%d/%m/%y') AS fecha, proveedores.nombre, persona.nombre, "
						+ "apellido, concepto, monto, factura, formaPago, comentario, TIME_FORMAT(hora, '%H:%i') "
						+ "FROM `lecsys2.00`.pagos "
						+ "LEFT JOIN `lecsys2.00`.proveedores ON proveedores.idProveedores = pagos.idProveedor "
						+ "LEFT JOIN `lecsys2.00`.empleados ON empleados.legajo = pagos.idEmpleados "
						+ "LEFT JOIN `lecsys2.00`.persona ON empleados.dni = persona.dni "
						+ "WHERE((pagos.idProveedor " + (prov?"!":"") + "= 'null' OR pagos.idEmpleados " + (empl?"!":"") + "= 'null')";
		
		if(!año.equals("Todos"))
			cmdStm += " AND YEAR(fecha) = " + año;
		
		if(mes != 0)
			cmdStm += " AND MONTH(fecha) = " + mes;
		cmdStm += ") GROUP BY idPagos ORDER BY idPagos DESC";

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			matriz = new Pago[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				matriz[i] = new Pago();
				matriz[i].setFecha(rs.getString(1));
				matriz[i].setNombre(rs.getString(2)==null? rs.getString(3) + " " + rs.getString(4): rs.getString(2));
				matriz[i].setConcepto(rs.getString(5));
				matriz[i].setMonto(rs.getFloat(6));
				matriz[i].setFactura(rs.getString(7));
				matriz[i].setFormaPago(rs.getString(8));
				matriz[i].setComentario(rs.getString(9));
				matriz[i].setHora(rs.getString(10));
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PagosDAO, getHistorialPagos()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return matriz;	
	}
}