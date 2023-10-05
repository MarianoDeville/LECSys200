package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;

public class ComprasDAO extends Conexion {

	public String [][] getDetalleCompra(String idPresupuesto) {

		String matriz[][] = null;
		String comandoStatement = "SELECT insumos.idInsumos, insumos.nombre, pedido.cant, TRUNCATE(precio, 2),TRUNCATE(precio * pedido.cant, 2), "
								+ "DATE_FORMAT(pedidoCompra.fecha, '%d/%m/%Y'), DATE_FORMAT(ordenCompra.fecha, '%d/%m/%Y')"
								+ ", DATE_FORMAT(pagos.fecha, '%d/%m/%Y'), factura "
								+ "FROM pedidoCompra "
								+ "JOIN pedido ON pedidoCompra.idPedidoCompra = pedido.idSolicitud "
								+ "JOIN insumos ON pedido.idInsumo = insumos.idInsumos "
								+ "JOIN presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra "
								+ "JOIN ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
								+ "JOIN cotizaciones ON presupuesto.idPresupuesto = cotizaciones.idPresupuesto "
								+ "JOIN pagos ON ordenCompra.idPago = pagos.idPagos "
								+ "WHERE (presupuesto.idPresupuesto = " + idPresupuesto + " AND insumos.idInsumos = cotizaciones.idInsumo)";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);			
			ResultSet rs = stm.executeQuery(comandoStatement);
			rs.last();	
			matriz = new String[rs.getRow()][9];
			rs.beforeFirst();
			int i = 0;

			while(rs.next()) {
	
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);
				matriz[i][3] = String.format("%.2f", rs.getFloat(4));
				matriz[i][4] = String.format("%.2f", rs.getFloat(5));
				matriz[i][5] = rs.getString(6);
				matriz[i][6] = rs.getString(7);
				matriz[i][7] = rs.getString(8);
				matriz[i][8] = rs.getString(9);
				i++;
			}
		} catch(Exception e) {

			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ComprasDAO, getDetalleCompra()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}
	
	public String [][] getDetalleOrdenCompra(String idPresupuesto) {

		String matriz[][] = null;
		String comandoStatement = "SELECT insumos.idInsumos, insumos.nombre, pedido.cant, TRUNCATE(precio, 2),TRUNCATE(precio * pedido.cant, 2), "
								+ "DATE_FORMAT(pedidoCompra.fecha, '%d/%m/%Y'), DATE_FORMAT(ordenCompra.fecha, '%d/%m/%Y') "
								+ "FROM pedidoCompra "
								+ "JOIN pedido ON pedidoCompra.idPedidoCompra = pedido.idSolicitud "
								+ "JOIN insumos ON pedido.idInsumo = insumos.idInsumos "
								+ "JOIN presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra "
								+ "JOIN ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
								+ "JOIN cotizaciones ON presupuesto.idPresupuesto = cotizaciones.idPresupuesto "
								+ "WHERE (presupuesto.idPresupuesto = " + idPresupuesto + " AND insumos.idInsumos = cotizaciones.idInsumo)";
	
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);			
			ResultSet rs = stm.executeQuery(comandoStatement);
			rs.last();	
			matriz = new String[rs.getRow()][7];
			rs.beforeFirst();
			int i = 0;
			while(rs.next()) {
	
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);
				matriz[i][3] = String.format("%.2f", rs.getFloat(4));
				matriz[i][4] = String.format("%.2f", rs.getFloat(5));
				matriz[i][5] = rs.getString(6);
				matriz[i][6] = rs.getString(7);
				i++;
			}
		} catch(Exception e) {

			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ComprasDAO, getDetalleOrdenCompra()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}
	
	public String [][] getListadoCompras(String año, int mes) {

		String matriz[][] = null;
		String idAtorizante[];
		String comandoStatement = "SELECT DATE_FORMAT(ordenCompra.fecha, '%d/%m/%Y'), ordenCompra.idPresupuesto, proveedores.nombre, persona.nombre, "
								+ "apellido, idAutorizante, TRUNCATE(SUM(pedido.cant * precio) ,2) "
								+ "FROM pedidoCompra "
								+ "JOIN pedido ON pedidoCompra.idPedidoCompra = pedido.idSolicitud "
								+ "JOIN insumos ON pedido.idInsumo = insumos.idInsumos "
								+ "JOIN presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra "
								+ "JOIN ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
								+ "JOIN cotizaciones ON presupuesto.idPresupuesto = cotizaciones.idPresupuesto "
								+ "JOIN empleados ON pedidoCompra.idSolicitante = empleados.idEmpleado "
								+ "JOIN persona ON empleados.idPersona = persona.idPersona "
								+ "JOIN proveedores ON presupuesto.idProveedor = proveedores.idProveedores ";
		String where = "WHERE (pedidoCompra.estado = 2 AND insumos.idInsumos = cotizaciones.idInsumo";
		
		if(!año.equals("Todos"))
			where += " AND YEAR(ordenCompra.fecha) = " + año;
		
		if(mes != 0)
			where += " AND MONTH(ordenCompra.fecha) = " + mes;
		
		comandoStatement += where + ") GROUP BY idOrdenCompra ORDER BY ordenCompra.fecha";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);			
			ResultSet rs = stm.executeQuery(comandoStatement);
			rs.last();	
			matriz = new String[rs.getRow()][6];
			idAtorizante = new String[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);
				matriz[i][3] = rs.getString(4) + " " + rs.getString(5);
				idAtorizante[i] = rs.getString(6);
				matriz[i][5] = String.format("%.2f", rs.getFloat(7));
				
				i++;
			}
			i = 0;
			
			while(i < matriz.length) {
			
				comandoStatement = "SELECT persona.nombre, apellido FROM usuarios "
								 + "JOIN empleados ON usuarios.idEmpleado = empleados.idEmpleado "
								 + "JOIN persona ON empleados.idPersona = persona.idPersona "
								 + "WHERE usuarios.idUsuarios = " + idAtorizante[i];
				rs = stm.executeQuery(comandoStatement);
			
				if(rs.next())
					matriz[i][4] = rs.getString(1) + " " + rs.getString(2);	// nombre del autorizante
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ComprasDAO, getListadoCompras()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}
	
	public String[] getListadoAños() {
		
		String listado[] = null;
		String comandoStatement = "SELECT YEAR(fecha) FROM ordenCompra GROUP BY YEAR(fecha)";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);			
			ResultSet rs = stm.executeQuery(comandoStatement);
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
			CtrlLogErrores.guardarError("ComprasDAO, getListadoAños()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return listado;
	}
	
	public String [][] getOrdenesCompra(int estado) {

		String matriz[][] = null;
		String idAtorizante[];
		String comandoStatement = "SELECT idOrdenCompra, DATE_FORMAT(ordenCompra.fecha, '%d/%m/%Y'), ordenCompra.idPresupuesto, "
								+ "persona.nombre, apellido, proveedores.nombre, idAutorizante "
								+ "FROM pedidoCompra "
								+ "JOIN presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra "
								+ "JOIN ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
								+ "JOIN proveedores ON presupuesto.idProveedor = proveedores.idProveedores "
								+ "JOIN empleados ON pedidoCompra.idSolicitante = empleados.idEmpleado "
								+ "JOIN persona ON empleados.idPersona = persona.idPersona "
								+ "WHERE pedidoCompra.estado = "+ estado +" GROUP BY idOrdenCompra ORDER BY ordenCompra.fecha DESC";

		try {
		
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);			
			ResultSet rs = stm.executeQuery(comandoStatement);
			rs.last();	
			matriz = new String[rs.getRow()][6];
			idAtorizante = new String[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);
				matriz[i][3] = rs.getString(4) + " " + rs.getString(5);
				matriz[i][5] = rs.getString(6);
				idAtorizante[i] = rs.getString(7);
				i++;
			}
			i = 0;
			
			while(i < matriz.length) {
			
				comandoStatement = "SELECT persona.nombre, apellido FROM usuarios "
								 + "JOIN empleados ON usuarios.idEmpleado = empleados.idEmpleado "
								 + "JOIN persona ON empleados.idPersona = persona.idPersona "
								 + "WHERE usuarios.idUsuarios = " + idAtorizante[i];
				rs = stm.executeQuery(comandoStatement);
			
				if(rs.next())
					matriz[i][4] = rs.getString(1) + " " + rs.getString(2);	// nombre del autorizante
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ComprasDAO, getOrdenesCompra()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}

	public boolean setOrdenCompra(String idPresupuesto, String idPedidoCompra, String idAutorizante) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		PreparedStatement stm;
		ResultSet rs;
		
		try {

			this.conectar();
			stm = this.conexion.prepareStatement("SELECT idPresupuesto FROM presupuesto WHERE idPedidoCompra = ?");
			stm.setString(1, idPedidoCompra);
			rs = stm.executeQuery();
			
			while(rs.next()) {
				
				stm = this.conexion.prepareStatement("DELETE FROM ordenCompra WHERE idPresupuesto = ? ");
				stm.setString(1, rs.getString(1));
				stm.executeUpdate();
			}
			stm = this.conexion.prepareStatement("INSERT INTO ordenCompra (fecha, idAutorizante, idPresupuesto) VALUES (DATE(NOW()), ?, ?)");
			stm.setString(1, idAutorizante);
			stm.setString(2, idPresupuesto);
			stm.executeUpdate();
			stm = this.conexion.prepareStatement("UPDATE presupuesto SET estado = 0 WHERE idPedidoCompra = ?");
			stm.setString(1, idPedidoCompra);
			stm.executeUpdate();			
			stm = this.conexion.prepareStatement("UPDATE presupuesto SET estado = 2 WHERE idPresupuesto = ?");
			stm.setString(1, idPresupuesto);
			stm.executeUpdate();
		} catch(Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ComprasDAO, setOrdenCompra()");
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Generar orden de compra y baja de los presupuestos no seleccionados.", "Insumos.", tiempo);
		return bandera;
	}
}