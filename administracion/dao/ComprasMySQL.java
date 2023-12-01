package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;
import modelo.Insumo;
import modelo.OrdenCompra;
import modelo.Presupuesto;
import modelo.Proveedor;

public class ComprasMySQL extends Conexion implements ComprasDAO{

	@Override
	public boolean setOrdenCompra(Presupuesto presupuesto, String autoriza) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String cmdStm = "SELECT idPresupuesto FROM `lecsys2.00`.presupuesto WHERE idPedidoCompra = ?";
		
		try {

			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setInt(1, presupuesto.getIdPedido());
			ResultSet rs = stm.executeQuery();
			cmdStm = "DELETE FROM `lecsys2.00`.ordenCompra WHERE idPresupuesto = ? ";
			
			while(rs.next()) {
				
				stm = this.conexion.prepareStatement(cmdStm);
				stm.setString(1, rs.getString(1));
				stm.executeUpdate();
			}
			cmdStm = "SELECT dni FROM `lecsys2.00`.usuarios WHERE idUsuario = ?";
			stm = this.conexion.prepareStatement(cmdStm);
			stm.setString(1, autoriza);
			rs = stm.executeQuery();

			if(rs.next())
				autoriza = rs.getString(1);
			cmdStm = "INSERT INTO `lecsys2.00`.ordenCompra (fecha, autoriza, idPresupuesto) VALUES (DATE(NOW()), ?, ?)";
			stm = this.conexion.prepareStatement(cmdStm);
			stm.setString(1, autoriza);
			stm.setInt(2, presupuesto.getIdPresupuesto());
			stm.executeUpdate();
			cmdStm = "UPDATE `lecsys2.00`.presupuesto SET estado = 0 WHERE idPedidoCompra = ?";
			stm = this.conexion.prepareStatement(cmdStm);
			stm.setInt(1, presupuesto.getIdPedido());
			stm.executeUpdate();
			cmdStm = "UPDATE `lecsys2.00`.presupuesto SET estado = 2 WHERE idPresupuesto = ?";
			stm = this.conexion.prepareStatement(cmdStm);
			stm.setInt(1, presupuesto.getIdPresupuesto());
			stm.executeUpdate();
		} catch(Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ComprasMySQL, setOrdenCompra()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Generar orden de compra y baja de los presupuestos no seleccionados.", "Insumos.", tiempo);
		return bandera;
	}

	@Override
	public OrdenCompra [] getOrdenesCompra(int estado) {

		OrdenCompra respuesta[] = null;
		String cmdStm = "SELECT idOrdenCompra, DATE_FORMAT(ordenCompra.fecha, '%d/%m/%Y'), ordenCompra.idPresupuesto, "
						+ "persona.nombre, apellido, proveedores.nombre, autoriza "
						+ "FROM `lecsys2.00`.pedidoCompra "
						+ "JOIN `lecsys2.00`.presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra "
						+ "JOIN `lecsys2.00`.ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
						+ "JOIN `lecsys2.00`.proveedores ON presupuesto.idProveedor = proveedores.idProveedores "
						+ "JOIN `lecsys2.00`.empleados ON pedidoCompra.idSolicitante = empleados.legajo "
						+ "JOIN `lecsys2.00`.persona ON empleados.dni = persona.dni "
						+ "WHERE pedidoCompra.estado = ? GROUP BY idOrdenCompra ORDER BY ordenCompra.fecha DESC";

		try {
		
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);	
			stm.setInt(1, estado);
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new OrdenCompra[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {
				
				respuesta[i] = new OrdenCompra();
				respuesta[i].setPresupuesto(new Presupuesto());
				respuesta[i].getPresupuesto().setProveedores(new Proveedor[1]);
				respuesta[i].getPresupuesto().getProveedores()[0] = new Proveedor();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setFecha(rs.getString(2));
				respuesta[i].getPresupuesto().setIdPresupuesto(rs.getInt(3));
				respuesta[i].setNombreSolicitante(rs.getString(4) + " " + rs.getString(5));
				respuesta[i].getPresupuesto().getProveedores()[0].setNombre(rs.getString(6));
				respuesta[i].setIdAutoriza(rs.getInt(7));
				i++;
			}
			i = 0;
			cmdStm = "SELECT persona.nombre, apellido FROM `lecsys2.00`.usuarios "
					 + "JOIN `lecsys2.00`.empleados ON usuarios.dni = empleados.dni "
					 + "JOIN `lecsys2.00`.persona ON empleados.dni = persona.dni "
					 + "WHERE usuarios.dni = ?";
			
			while(i < respuesta.length) {
				
				stm = this.conexion.prepareStatement(cmdStm);	
				stm.setInt(1, respuesta[i].getIdAutoriza());
				rs = stm.executeQuery();
			
				if(rs.next())
					respuesta[i].setNombreAutoriza(rs.getString(1) + " " + rs.getString(2));
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ComprasMySQL, getOrdenesCompra()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
		
	@Override
	public void getDetalleOrdenCompra(OrdenCompra ordenCompra) {

		String cmdStm = "SELECT idInsumos, nombre, pedido.cant, precio, DATE_FORMAT(pedidoCompra.fecha, '%d/%m/%Y') "
						+ "FROM `lecsys2.00`.pedidoCompra "
						+ "JOIN `lecsys2.00`.pedido ON pedidoCompra.idPedidoCompra = pedido.idSolicitud "
						+ "JOIN `lecsys2.00`.insumos ON pedido.idInsumo = insumos.idInsumos "
						+ "JOIN `lecsys2.00`.presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra "
						+ "JOIN `lecsys2.00`.ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
						+ "JOIN `lecsys2.00`.cotizaciones ON presupuesto.idPresupuesto = cotizaciones.idPresupuesto "
						+ "WHERE (presupuesto.idPresupuesto = ? AND insumos.idInsumos = cotizaciones.idInsumo)";
	
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setInt(1, ordenCompra.getPresupuesto().getIdPresupuesto());
			ResultSet rs = stm.executeQuery();
			rs.last();
			ordenCompra.getPresupuesto().setInsumos(new Insumo[rs.getRow()]);
			rs.beforeFirst();
			int i = 0;
			while(rs.next()) {
	
				ordenCompra.getPresupuesto().getInsumos()[i] = new Insumo();
				ordenCompra.getPresupuesto().getInsumos()[i].setId(rs.getInt(1));
				ordenCompra.getPresupuesto().getInsumos()[i].setNombre(rs.getString(2));
				ordenCompra.getPresupuesto().getInsumos()[i].setCantSolicitada(rs.getInt(3));
				ordenCompra.getPresupuesto().getInsumos()[i].setPrecio(rs.getFloat(4));
				ordenCompra.setFechaPedido(rs.getString(5));
				i++;
			}
		} catch(Exception e) {

			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ComprasMySQL, getDetalleOrdenCompra()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
	}

	@Override
	public String[] getListadoAños() {
		
		String listado[] = null;
		String cmdStm = "SELECT YEAR(fecha) FROM `lecsys2.00`.ordenCompra GROUP BY YEAR(fecha)";
		
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
			CtrlLogErrores.guardarError("ComprasMySQL, getListadoAños()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return listado;
	}

	@Override
	public OrdenCompra [] getListadoCompras(String año, int mes) {

		OrdenCompra respuesta[] = null;
		String cmdStm = "SELECT DATE_FORMAT(ordenCompra.fecha, '%d/%m/%Y'), ordenCompra.idPresupuesto, proveedores.nombre, persona.nombre, "
						+ "apellido, autoriza, TRUNCATE(SUM(pedido.cant * precio) ,2) "
						+ "FROM `lecsys2.00`.pedidoCompra "
						+ "JOIN `lecsys2.00`.pedido ON pedidoCompra.idPedidoCompra = pedido.idSolicitud "
						+ "JOIN `lecsys2.00`.insumos ON pedido.idInsumo = insumos.idInsumos "
						+ "JOIN `lecsys2.00`.presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra "
						+ "JOIN `lecsys2.00`.ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
						+ "JOIN `lecsys2.00`.cotizaciones ON presupuesto.idPresupuesto = cotizaciones.idPresupuesto "
						+ "JOIN `lecsys2.00`.empleados ON pedidoCompra.idSolicitante = empleados.legajo "
						+ "JOIN `lecsys2.00`.persona ON empleados.dni = persona.dni "
						+ "JOIN `lecsys2.00`.proveedores ON presupuesto.idProveedor = proveedores.idProveedores "
						+ "WHERE (pedidoCompra.estado = 2 AND insumos.idInsumos = cotizaciones.idInsumo";
		
		if(!año.equals("Todos"))
			cmdStm += " AND YEAR(ordenCompra.fecha) = " + año;
		
		if(mes != 0)
			cmdStm += " AND MONTH(ordenCompra.fecha) = " + mes;
		cmdStm += ") GROUP BY idOrdenCompra ORDER BY ordenCompra.fecha";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);			
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			respuesta = new OrdenCompra[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {
				
				respuesta[i] = new OrdenCompra();
				respuesta[i].setPresupuesto(new Presupuesto());
				respuesta[i].getPresupuesto().setProveedores(new Proveedor[1]);
				respuesta[i].getPresupuesto().getProveedores()[0] = new Proveedor();
				respuesta[i].setFecha(rs.getString(1));
				respuesta[i].getPresupuesto().setIdPresupuesto(rs.getInt(2));
				respuesta[i].getPresupuesto().getProveedores()[0].setNombre(rs.getString(3));
				respuesta[i].setNombreSolicitante(rs.getString(4) + " " + rs.getString(5));
				respuesta[i].setIdAutoriza(rs.getInt(6));
				respuesta[i].setMontoTotal(rs.getFloat(7));
				i++;
			}
			i = 0;
			
			while(i < respuesta.length) {
			
				cmdStm = "SELECT persona.nombre, apellido FROM `lecsys2.00`.usuarios "
						 + "JOIN `lecsys2.00`.empleados ON usuarios.dni = empleados.dni "
						 + "JOIN `lecsys2.00`.persona ON empleados.dni = persona.dni "
						 + "WHERE usuarios.dni = " + respuesta[i].getIdAutoriza();
				rs = stm.executeQuery(cmdStm);
			
				if(rs.next())
					respuesta[i].setNombreAutoriza(rs.getString(1) + " " + rs.getString(2));
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ComprasMySQL, getListadoCompras()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}	
}