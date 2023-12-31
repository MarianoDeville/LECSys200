package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.Contacto;
import modelo.DtosActividad;
import modelo.Proveedor;

public class ProveedoresMySQL extends Conexion implements ProveedoresDAO {

	@Override
	public Proveedor [] getListado(String filtrado, boolean estado, int tipo) {

		Proveedor respuesta[] = null;
		String cmdStm = "SELECT idProveedores, nombre, cuit, direccion, tipo, estado, servicio, comentario "
					+ "FROM `lecsys2.00`.proveedores "
	 				+ "WHERE (estado = ? ";
					
		if(tipo < 2)			
	 			cmdStm += "AND servicio = " + (tipo == 1? 1 : 0);
		cmdStm += " AND (nombre LIKE ? OR cuit LIKE ?)) ORDER BY nombre";
		filtrado = "%" + filtrado + "%";		
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setInt(1, estado? 1 : 0);
			stm.setString(2, filtrado);
			stm.setString(3, filtrado);
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new Proveedor[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
				
				respuesta[i] = new Proveedor();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setNombre(rs.getString(2));
				respuesta[i].setCuit(rs.getString(3));
				respuesta[i].setDireccion(rs.getString(4));
				respuesta[i].setTipo(rs.getString(5));
				respuesta[i].setEstado(rs.getInt(6));
				respuesta[i].setServicio(rs.getInt(7));
				respuesta[i].setComentario(rs.getString(8));
				i++;
			}
			
			i = 0;
			while(i < respuesta.length) {		

				cmdStm = "SELECT nombre, tel�fono, email, sector "
						+ "FROM `lecsys2.00`.contacto WHERE idProveedores = " + respuesta[i].getId();
				rs = stm.executeQuery(cmdStm);
				rs.last();
				respuesta[i].setContactos(new Contacto[rs.getRow()]);
				rs.beforeFirst();
				int e = 0;
				
				while (rs.next()) {
					
					respuesta[i].getContactos()[e] = new Contacto();
					respuesta[i].getContactos()[e].setNombre(rs.getString(1));
					respuesta[i].getContactos()[e].setTelefono(rs.getString(2));
					respuesta[i].getContactos()[e].setEmail(rs.getString(3));
					respuesta[i].getContactos()[e].setSector(rs.getString(4));
					e++;
				}
				i++;
			}	
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ProveedoresMySQL, getListado()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
	
	@Override
	public boolean setNuevo(Proveedor proveedor) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		String cmdStm = "INSERT INTO `lecsys2.00`.proveedores (nombre, direccion, cuit, tipo, estado, servicio, comentario)"
				 	+ " VALUES (?, ?, ?, ?, 1, ?, ?)";
		DtosActividad dtosActividad = new DtosActividad();

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setString(1, proveedor.getNombre());
			stm.setString(2, proveedor.getDireccion());
			stm.setString(3, proveedor.getCuit());
			stm.setString(4, proveedor.getTipo());
			stm.setInt(5, proveedor.getServicio());
			stm.setString(6, proveedor.getComentario());
			stm.executeUpdate();
			ResultSet rs = stm.executeQuery("SELECT MAX(idProveedores) FROM proveedores");
			
			if(rs.next())
				proveedor.setId(rs.getInt(1));
			cmdStm = "INSERT INTO `lecsys2.00`.contacto (nombre, tel�fono, email, idProveedores, sector) "
					+ "VALUES (?, ?, ?, ?, ?)";
			stm = this.conexion.prepareStatement(cmdStm);
			
			for(int i = 0 ; i < proveedor.getContactos().length; i++) {
				
				stm.setString(1, proveedor.getContactos()[i].getNombre());
				stm.setString(2, proveedor.getContactos()[i].getTelefono());
				stm.setString(3, proveedor.getContactos()[i].getEmail());
				stm.setLong(4, proveedor.getId());
				stm.setString(5, proveedor.getContactos()[i].getSector());
				stm.executeUpdate();	
			}
		} catch (Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ProveedoresMySQL, setNuevo()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registrar nuevo proveedor.", "Proveedores.", tiempo);
		return bandera;
	}
	
	@Override
	public boolean update(Proveedor proveedor) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String cmdStm = "UPDATE `lecsys2.00`.proveedores "
					+ "SET nombre = ?, direccion = ?, cuit = ?, tipo = ?, estado = ?, servicio = ?, comentario = ? "
					+ "WHERE idProveedores = ?";
				
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setString(1, proveedor.getNombre());
			stm.setString(2, proveedor.getDireccion());
			stm.setString(3, proveedor.getCuit());
			stm.setString(4, proveedor.getTipo());
			stm.setInt(5, proveedor.getEstado());
			stm.setInt(6, proveedor.getServicio());
			stm.setString(7, proveedor.getComentario());
			stm.setLong(8, proveedor.getId());
			stm.executeUpdate();
			
			if(proveedor.getEstado() == 1) {		
				
				stm = this.conexion.prepareStatement("DELETE FROM `lecsys2.00`.contacto WHERE idProveedores = ?");
				stm.setLong(1, proveedor.getId());
				stm.executeUpdate();
				cmdStm = "INSERT INTO `lecsys2.00`.contacto (nombre, tel�fono, email, idProveedores, sector) "
						+ "VALUES (?, ?, ?, ?, ?)";
				stm = this.conexion.prepareStatement(cmdStm);
				
				for(int i = 0; i < proveedor.getContactos().length; i++) {
					
					stm.setString(1, proveedor.getContactos()[i].getNombre());
					stm.setString(2, proveedor.getContactos()[i].getTelefono());
					stm.setString(3, proveedor.getContactos()[i].getEmail());
					stm.setLong(4, proveedor.getId());
					stm.setString(5, proveedor.getContactos()[i].getSector());
					stm.executeUpdate();				
				}
			}
		} catch (Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ProveedoresMySQL, update()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Actualizar informaci�n del proveedor.", "Proveedores.", tiempo);
		return bandera;
	}
	
	@Override
	public boolean isCUITExistente(String cuit) {
		
		boolean bandera = false;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement();
			ResultSet rs = stm.executeQuery("SELECT idProveedores FROM `lecsys2.00`.proveedores WHERE cuit = " + cuit);
			if(rs.next())
				bandera = true;
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ProveedoresMySQL, isCUITExistente()");
		} finally {
			
			this.cerrar();
		}
		return bandera;
	}
	
	@Override
	public Object[][] getListadoEmail(String filtrado){

		Object matriz[][] = null;
		String cmdStm = "SELECT proveedores.nombre,sector, contacto.nombre, email, proveedores.idProveedores "
						+ "FROM contacto "
						+ "JOIN proveedores ON contacto.idProveedores = proveedores.IdProveedores "
		 				+ "WHERE (proveedores.estado = 1 AND (proveedores.nombre LIKE ? "
		 											 + "OR contacto.nombre LIKE ? "
		 											 + "OR email LIKE ?)) "
		 				+ "ORDER BY proveedores.nombre";
		filtrado = "%" + filtrado + "%";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, filtrado);
			stm.setString(2, filtrado);
			stm.setString(3, filtrado);
			ResultSet rs = stm.executeQuery();
			rs.last();	
			matriz = new Object[rs.getRow()][5];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
				
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);
				matriz[i][3] = rs.getString(4);
				matriz[i][4] = rs.getInt(5);
				i++;
			}
	
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ProveedoresMySQL, getListadoEmail()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}

	@Override
	public Proveedor [] getProveedoresPresupuesto(int idPedidoCompra){
		
		Proveedor respuesta[] = null;
		String cmdStm = "SELECT presupuesto.idProveedor, nombre, cuit, direccion "
						+ "FROM `lecsys2.00`.presupuesto "
						+ "JOIN `lecsys2.00`.proveedores ON presupuesto.idProveedor = proveedores.idProveedores "
						+ "WHERE idPedidoCompra = ?";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setInt(1, idPedidoCompra);
			ResultSet rs = stm.executeQuery();
			rs.last();	
			respuesta = new Proveedor[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {
				
				respuesta[i] = new Proveedor();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setNombre(rs.getString(2));
				respuesta[i].setCuit(rs.getString(3));
				respuesta[i].setDireccion(rs.getString(4));
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ProveedoresMySQL, getProveedoresPresupuesto()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
}