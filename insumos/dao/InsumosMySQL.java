package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;
import modelo.Insumo;
import modelo.PedidoInsumo;
import modelo.Presupuesto;
import modelo.Proveedor;

public class InsumosMySQL extends Conexion implements InsumosDAO {

	@Override
	public boolean nuevo(Insumo insumo) {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String cmdStm = "INSERT INTO `lecsys2.00`.insumos (nombre, descripción, presentación, estado, cant)"
					+ " VALUES (?, ?, ?, 1, 0)";

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setString(1, insumo.getNombre());
			stm.setString(2, insumo.getDescripcion());
			stm.setString(3, insumo.getPresentacion());
			stm.executeUpdate();
		} catch(Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosMySQL, nuevo()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registrar nuevo insumo.", "Insumos.", tiempo);
		return bandera;
	}

	@Override
	public boolean update(Insumo insumo) {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String cmdStm = "UPDATE `lecsys2.00`.insumos "
						+ "SET nombre = ?, descripción = ?, presentación = ?, estado = ?, cant = ? "
						+ "WHERE idInsumos = ?";
				
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setString(1, insumo.getNombre());
			stm.setString(2, insumo.getDescripcion());
			stm.setString(3, insumo.getPresentacion());
			stm.setInt(4, insumo.getEstado());
			stm.setInt(5, insumo.getCant());
			stm.setInt(6, insumo.getId());
			stm.executeUpdate();
		} catch(Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosMySQL, update()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registrar nuevo insumo.", "Insumos.", tiempo);
		return bandera;
	}

	@Override
	public Insumo [] listado(String filtrado) {
		
		Insumo respuesta[] = null;
		String cmdStm = "SELECT idInsumos, nombre, descripción, presentación, cant, estado "
						+ "FROM `lecsys2.00`.insumos "
		 				+ "WHERE (estado = 1 AND (nombre LIKE '%" + filtrado + "%' OR descripción LIKE '%" + filtrado + "%')) "
		 				+ "ORDER BY nombre";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			respuesta = new Insumo[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
				
				respuesta[i] = new Insumo();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setNombre(rs.getString(2));
				respuesta[i].setDescripcion(rs.getString(3));
				respuesta[i].setPresentacion(rs.getString(4));
				respuesta[i].setCant(rs.getInt(5));
				respuesta[i].setEstado(rs.getInt(6));
				i++;
			}
		}catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosMySQL, listado()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
	
	@Override
	public PedidoInsumo [] getPedidos(int estado) {
		
		PedidoInsumo respuesta[] = null;
		String cmdStm = "SELECT DATE_FORMAT(fecha, '%d/%m/%Y'), sectorSolicitante, nombre, apellido, idPedidoCompra, pedidoCompra.estado "
						+ "FROM `lecsys2.00`.pedidoCompra "
						+ "JOIN `lecsys2.00`.empleados ON pedidoCompra.idSolicitante = empleados.legajo "
						+ "JOIN `lecsys2.00`.persona ON empleados.dni = persona.dni "
						+ "WHERE pedidoCompra.estado = ?";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setInt(1, estado);
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new PedidoInsumo[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				respuesta[i] = new PedidoInsumo();
				respuesta[i].setFechaSolicitud(rs.getString(1));
				respuesta[i].setSectorSolicitante(rs.getString(2));
				respuesta[i].setNombreSolicitante(rs.getString(3) + " " + rs.getString(4));
				respuesta[i].setIdCompra(rs.getInt(5));
				respuesta[i].setEstado(rs.getInt(6));
				i++;
			}
			i = 0;
			cmdStm = "SELECT nombre, descripción, estado, pedido.cant, idInsumo, presentación "
					+ "FROM `lecsys2.00`.insumos "
					+ "JOIN `lecsys2.00`.pedido ON insumos.idInsumos = pedido.idInsumo "
					+ "WHERE idSolicitud = ?";
			stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			while(i < respuesta.length) {
			
				stm.setInt(1, respuesta[i].getIdCompra());
				rs = stm.executeQuery();
				rs.last();
				respuesta[i].setInsumos(new Insumo[rs.getRow()]);
				rs.beforeFirst();
				int e = 0;
				
				while(rs.next()) {
					
					respuesta[i].getInsumos()[e] = new Insumo();
					respuesta[i].getInsumos()[e].setNombre(rs.getString(1));
					respuesta[i].getInsumos()[e].setDescripcion(rs.getString(2));
					respuesta[i].getInsumos()[e].setEstado(rs.getInt(3));
					respuesta[i].getInsumos()[e].setCantSolicitada(rs.getInt(4));
					respuesta[i].getInsumos()[e].setId(rs.getInt(5));
					respuesta[i].getInsumos()[e].setPresentacion(rs.getString(6));
					e++;
				}
				i++;
			}
		}catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosMySQL, getPedidos()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}

	@Override
	public boolean nuevoPedido(PedidoInsumo solicitud) {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String cmdStm = "INSERT INTO `lecsys2.00`.pedidoCompra (fecha, sectorSolicitante, estado, idSolicitante) "
						+ "VALUES (DATE(NOW()), ?, 1, ?)";

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setString(1, solicitud.getSectorSolicitante());
			stm.setInt(2, solicitud.getIdSolicitante());
			stm.executeUpdate();
			cmdStm = "SELECT MAX(idPedidoCompra) FROM pedidoCompra";
			ResultSet rs = stm.executeQuery(cmdStm);
			
			if(rs.next())
				solicitud.setIdCompra(rs.getInt(1));
			int i = 0;
			cmdStm = "INSERT INTO `lecsys2.00`.pedido (idInsumo, idSolicitud, cant) VALUES (?, ?, ?)";
			stm = this.conexion.prepareStatement(cmdStm);
			
			while(i < solicitud.getInsumos().length) {
				
				stm.setInt(1, solicitud.getInsumos()[i].getId());
				stm.setInt(2, solicitud.getIdCompra());
				stm.setInt(3, solicitud.getInsumos()[i].getCantSolicitada());
				stm.executeUpdate();
				i++;
			}
		} catch(Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosMySQL, nuevoPedido()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registrar nuevo pedido de compra de insumos.", "Insumos.", tiempo);
		return bandera;
	}	
	
	@Override
	public boolean updatePedido(PedidoInsumo solicitud) {
			
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String cmdStm = "UPDATE `lecsys2.00`.pedidoCompra SET fecha = DATE(NOW()), estado = ? WHERE idPedidoCompra = ?";

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setInt(1, solicitud.getEstado());
			stm.setInt(2, solicitud.getIdCompra());
			stm.executeUpdate();
			cmdStm = "DELETE FROM `lecsys2.00`.pedido WHERE idSolicitud = ?";
			stm = this.conexion.prepareStatement(cmdStm);
			stm.setInt(1, solicitud.getIdCompra());
			stm.executeUpdate();
			
			if(solicitud.getEstado() != 0) {
			
				int i = 0;
				cmdStm = "INSERT INTO `lecsys2.00`.pedido (idInsumo, idSolicitud, cant) VALUES (?, ?, ?)";
				stm = this.conexion.prepareStatement(cmdStm);
				
				while(i < solicitud.getInsumos().length) {
				
					stm.setInt(1, solicitud.getInsumos()[i].getId());
					stm.setInt(2, solicitud.getIdCompra());
					stm.setInt(3, solicitud.getInsumos()[i].getCantSolicitada());
					stm.executeUpdate();
					i++;
				}
			}
		} catch(Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosMySQL, updatePedido()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		
		if(solicitud.getEstado() == 0)
			dtosActividad.registrarActividad("Borrar pedido de compra de insumos.", "Insumos.", tiempo);	
		else
			dtosActividad.registrarActividad("Actualizar pedido de compra de insumos.", "Insumos.", tiempo);
		return bandera;
	}

	@Override
	public int setPedidoPresupuesto(Presupuesto presupuesto) {
		
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		presupuesto.setIdPresupuesto(0);
		String cmdStm = "SELECT idPresupuesto FROM `lecsys2.00`.presupuesto WHERE (idPedidoCompra = ? AND idProveedor = ?)";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setInt(1, presupuesto.getIdPedido());
			stm.setInt(2, presupuesto.getProveedores()[0].getId());
			ResultSet rs = stm.executeQuery();

			if(rs.next())
				presupuesto.setIdPresupuesto(rs.getInt(1));

			if(presupuesto.getIdPresupuesto() == 0) {
			
				stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.presupuesto (idPedidoCompra, fecha, idProveedor, estado) VALUES (?, DATE(NOW()), ?, 1)");
				stm.setInt(1, presupuesto.getIdPedido());
				stm.setInt(2, presupuesto.getProveedores()[0].getId());
				stm.executeUpdate();
				rs = stm.executeQuery("SELECT MAX(idPresupuesto) FROM `lecsys2.00`.presupuesto");
				
				if(rs.next())
					presupuesto.setIdPresupuesto(rs.getInt(1));
			}		
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosMySQL, setPedidoPresupuesto()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Creación de pedido de cotización.", "Insumos.", tiempo);
		return presupuesto.getIdPresupuesto();
	}

	@Override
	public void getPedidoPresupuesto(Presupuesto presupuesto, int proveedor) {
		
		String cmdStm = "SELECT idPresupuesto, date_format(fecha, '%d/%m/%Y'), date_format(validez, '%d/%m/%Y') "
						+ "FROM `lecsys2.00`.presupuesto "
						+ "WHERE (idPedidoCompra = ? AND idProveedor = ?)";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setInt(1, presupuesto.getIdPedido());
			stm.setInt(2, presupuesto.getProveedores()[proveedor].getId());
			ResultSet rs = stm.executeQuery();
			
			if(rs.next()) {
				
				presupuesto.setIdPresupuesto(rs.getInt(1));
				presupuesto.setFecha(rs.getString(2));
				presupuesto.setValidez(rs.getString(3));
			}
			cmdStm = "SELECT pedido.idInsumo, pedido.cant, nombre, descripción FROM `lecsys2.00`.pedido "
					 + "JOIN `lecsys2.00`.insumos ON pedido.idInsumo = insumos.idInsumos "
					 + "WHERE idSolicitud = ? ORDER BY idInsumos";
			stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setInt(1, presupuesto.getIdPedido());
			rs = stm.executeQuery();
			rs.last();	
			presupuesto.setInsumos(new Insumo[rs.getRow()]);
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {
				
				presupuesto.getInsumos()[i] = new Insumo();
				presupuesto.getInsumos()[i].setId(rs.getInt(1));
				presupuesto.getInsumos()[i].setCantSolicitada(rs.getInt(2));
				presupuesto.getInsumos()[i].setNombre(rs.getString(3));
				presupuesto.getInsumos()[i].setDescripcion(rs.getString(4));
				i++;
			}
			i = 0;
			cmdStm = "SELECT precio FROM `lecsys2.00`.cotizaciones "
					 + "WHERE (idInsumo = ? AND idPresupuesto = ?)";
			stm = this.conexion.prepareStatement(cmdStm);

			while(i < presupuesto.getInsumos().length) {
				
				stm.setInt(1, presupuesto.getInsumos()[i].getId());
				stm.setInt(2, presupuesto.getIdPresupuesto());
				rs = stm.executeQuery();
				
				if(rs.next())
					presupuesto.getInsumos()[i].setPrecio(rs.getFloat(1));
				i++;
			}			
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosMySQL, getPedidoPresupuesto()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
	}

	@Override
	public boolean setPrecios(Presupuesto presupuesto) {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String cmdStm = "UPDATE `lecsys2.00`.presupuesto SET validez = ? WHERE idPresupuesto = ?";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setString(1, presupuesto.getValidez());
			stm.setInt(2, presupuesto.getIdPresupuesto());
			stm.executeUpdate();
			
			for(int i = 0; i < presupuesto.getInsumos().length; i++) {

				cmdStm = "DELETE FROM `lecsys2.00`.cotizaciones WHERE (idInsumo = ? AND idPresupuesto = ?)";
				stm = this.conexion.prepareStatement(cmdStm);
				stm.setInt(1, presupuesto.getInsumos()[i].getId());
				stm.setInt(2, presupuesto.getIdPresupuesto());
				stm.executeUpdate();
				
				cmdStm = "INSERT INTO `lecsys2.00`.cotizaciones (precio, idInsumo, idPresupuesto) VALUES (?, ?, ?)";
				stm = this.conexion.prepareStatement(cmdStm);
				stm.setFloat(1, presupuesto.getInsumos()[i].getPrecio());
				stm.setInt(2, presupuesto.getInsumos()[i].getId());
				stm.setInt(3, presupuesto.getIdPresupuesto());
				stm.executeUpdate();

			}
		} catch(Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosMySQL, setPrecios()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Actualizar precios de insumos en la cotización.", "Insumos.", tiempo);
		return bandera;
	}

	@Override
	public String [][] getHistoriaCompras(int idInsumo) {
		
		String matriz[][] = null;
		String cmdStm = "SELECT DATE_FORMAT(ordenCompra.fecha, '%d/%m/%Y'), proveedores.nombre, persona.nombre, persona.apellido, "
							+ "pedidoCompra.sectorSolicitante, usuarios.nombre, pedido.cant, cotizaciones.precio "
						+ "FROM `lecsys2.00`.insumos "
						+ "JOIN `lecsys2.00`.cotizaciones ON insumos.idInsumos = cotizaciones.idInsumo "
						+ "JOIN `lecsys2.00`.pedido ON insumos.idInsumos = pedido.idInsumo "
						+ "JOIN `lecsys2.00`.pedidoCompra ON pedido.idSolicitud = pedidoCompra.idPedidoCompra " 
						+ "JOIN `lecsys2.00`.empleados ON pedidoCompra.idSolicitante = empleados.legajo "
						+ "JOIN `lecsys2.00`.persona ON empleados.dni = persona.dni "
						+ "JOIN `lecsys2.00`.presupuesto ON pedidoCompra.idPedidoCompra = presupuesto.idPedidoCompra " 
						+ "JOIN `lecsys2.00`.ordenCompra ON presupuesto.idPresupuesto = ordenCompra.idPresupuesto "
						+ "JOIN `lecsys2.00`.usuarios ON ordenCompra.autoriza = usuarios.idUsuario "
						+ "JOIN `lecsys2.00`.proveedores ON presupuesto.idProveedor = proveedores.idProveedores "
						+ "WHERE (insumos.idInsumos = ? AND pedidoCompra.estado = 2 AND cotizaciones.idPresupuesto = presupuesto.idPresupuesto) "
						+ "ORDER BY ordenCompra.fecha DESC LIMIT 20";
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setInt(1, idInsumo);
			ResultSet rs = stm.executeQuery();
			rs.last();	
			matriz = new String[rs.getRow()][6];
			rs.beforeFirst();
			int i=0;

			while(rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3) + " " + rs.getString(4) + " / " + rs.getString(5);
				matriz[i][3] = rs.getString(6);
				matriz[i][4] = rs.getString(7);
				matriz[i][5] = String.format("%.2f", rs.getFloat(8));
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosMySQL, getHistoriaCompras()");
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}

	@Override
	public Presupuesto [] getCotizaciones(PedidoInsumo pedido) {
		
		Presupuesto respuesta[] = null;
		String cmdStm = "SELECT idPresupuesto, nombre, presupuesto.estado FROM `lecsys2.00`.presupuesto "
						+ "JOIN `lecsys2.00`.proveedores ON presupuesto.idProveedor = proveedores.idProveedores "
						+ "WHERE idPedidoCompra = ?";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setInt(1, pedido.getIdCompra());
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new Presupuesto[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {
				
				respuesta[i] = new Presupuesto();
				respuesta[i].setIdPresupuesto(rs.getInt(1));
				respuesta[i].setProveedores(new Proveedor[1]);
				respuesta[i].getProveedores()[0] = new Proveedor();
				respuesta[i].getProveedores()[0].setNombre(rs.getString(2));
				respuesta[i].setEstado(rs.getInt(3));
				respuesta[i].setInsumos(pedido.getInsumos());
				i++;
			}
			
			for(i = 0; i < respuesta.length; i++) {
				
				for(int e = 0; e < respuesta[i].getInsumos().length; e++) {
					
					cmdStm = "SELECT precio, 2 FROM `lecsys2.00`.cotizaciones WHERE (idPresupuesto = ? AND idInsumo = ?)";
					stm = this.conexion.prepareStatement(cmdStm);
					stm.setInt(1, respuesta[i].getIdPresupuesto());
					stm.setInt(2, respuesta[i].getInsumos()[e].getId());
					rs = stm.executeQuery();
			
					if(rs.next())
						respuesta[i].getInsumos()[e].setPrecio(rs.getFloat(1));
					cmdStm = "SELECT cant FROM `lecsys2.00`.pedido WHERE (idSolicitud = ? AND idInsumo = ?)";
					stm = this.conexion.prepareStatement(cmdStm);
					stm.setInt(1, pedido.getIdCompra());
					stm.setInt(2, respuesta[i].getInsumos()[e].getId());
					rs = stm.executeQuery();
					
					if(rs.next())
						respuesta[i].getInsumos()[e].setCantSolicitada(rs.getInt(1));
				}
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("InsumosMySQL, getCotizaciones()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	int algo = 0;
/*	

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
		String cmdStm = "SELECT insumos.idInsumos, nombre, descripción, pedido.cant, precio, pedidoCompra.idPedidoCompra "
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
			
			cmdStm = "SELECT pedido.cant, nombre, descripción, presentación, idInsumos FROM `lecsys2.00`.pedido "
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
	*/
}