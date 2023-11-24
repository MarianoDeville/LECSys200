package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.Contacto;
import modelo.DtosActividad;
import modelo.Proveedor;

public class ProveedoresMySQL extends Conexion implements ProveedoresDAO {

	public Proveedor [] getListado(String filtrado, boolean estado) {

		Proveedor proveedores[] = null;
		String cmdStm = "SELECT idProveedores, nombre, cuit, direccion, tipo, estado "
					+ "FROM `lecsys2.00`.proveedores "
	 				+ "WHERE (estado = " + (estado?"1":"0") +  " AND (nombre LIKE '%" + filtrado + "%' OR cuit LIKE'" + filtrado + "%')) "
	 				+ "ORDER BY nombre";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			proveedores = new Proveedor[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
				
				proveedores[i] = new Proveedor();
				proveedores[i].setId(rs.getInt(1));
				proveedores[i].setNombre(rs.getString(2));
				proveedores[i].setCuit(rs.getString(3));
				proveedores[i].setDireccion(rs.getString(4));
				proveedores[i].setTipo(rs.getString(5));
				proveedores[i].setEstado(rs.getInt(6));
				i++;
			}
			
			i = 0;
			while(i < proveedores.length) {		

				cmdStm = "SELECT nombre, teléfono, email, sector "
						+ "FROM `lecsys2.00`.contacto WHERE idProveedores = " + proveedores[i].getId();
				rs = stm.executeQuery(cmdStm);
				rs.last();
				proveedores[i].setContactos(new Contacto[rs.getRow()]);
				rs.beforeFirst();
				int e = 0;
				
				while (rs.next()) {
					
					proveedores[i].getContactos()[e] = new Contacto();
					proveedores[i].getContactos()[e].setNombre(rs.getString(1));
					proveedores[i].getContactos()[e].setTelefono(rs.getString(2));
					proveedores[i].getContactos()[e].setEmail(rs.getString(3));
					proveedores[i].getContactos()[e].setSector(rs.getString(4));
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
		return proveedores;
	}
	
	public boolean setNuevo(Proveedor proveedor) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		String cmdStm = "INSERT INTO `lecsys2.00`.proveedores (nombre, direccion, cuit, tipo, estado)"
				 	+ " VALUES (?, ?, ?, ?, 1)";
		DtosActividad dtosActividad = new DtosActividad();

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setString(1, proveedor.getNombre());
			stm.setString(2, proveedor.getDireccion());
			stm.setString(3, proveedor.getCuit());
			stm.setString(4, proveedor.getTipo());
			stm.executeUpdate();
			ResultSet rs = stm.executeQuery("SELECT MAX(idProveedores) FROM proveedores");
			
			if(rs.next())
				proveedor.setId(rs.getInt(1));
			cmdStm = "INSERT INTO `lecsys2.00`.contacto (nombre, teléfono, email, idProveedores, sector) "
					+ "VALUES (?, ?, ?, ?, ?)";
			stm = this.conexion.prepareStatement(cmdStm);
			
			for(int i = 0 ; i < proveedor.getContactos().length; i++) {
				
				stm.setString(1, proveedor.getContactos()[i].getNombre());
				stm.setString(2, proveedor.getContactos()[i].getTelefono());
				stm.setString(3, proveedor.getContactos()[i].getEmail());
				stm.setInt(4, proveedor.getId());
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
	
	public boolean update(Proveedor proveedor) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String cmdStm = "UPDATE `lecsys2.00`.proveedores "
					+ "SET nombre = ?, direccion = ?, cuit = ?, tipo = ?, estado = ? "
					+ "WHERE idProveedores = ?";
				
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setString(1, proveedor.getNombre());
			stm.setString(2, proveedor.getDireccion());
			stm.setString(3, proveedor.getCuit());
			stm.setString(4, proveedor.getTipo());
			stm.setInt(5, proveedor.getEstado());
			stm.setInt(6, proveedor.getId());
			stm.executeUpdate();
			
			if(proveedor.getEstado() == 1) {		
				
				stm = this.conexion.prepareStatement("DELETE FROM `lecsys2.00`.contacto WHERE idProveedores = ?");
				stm.setInt(1, proveedor.getId());
				stm.executeUpdate();
				cmdStm = "INSERT INTO `lecsys2.00`.contacto (nombre, teléfono, email, idProveedores, sector) "
						+ "VALUES (?, ?, ?, ?, ?)";
				stm = this.conexion.prepareStatement(cmdStm);
				
				for(int i = 0; i < proveedor.getContactos().length; i++) {
					
					stm.setString(1, proveedor.getContactos()[i].getNombre());
					stm.setString(2, proveedor.getContactos()[i].getTelefono());
					stm.setString(3, proveedor.getContactos()[i].getEmail());
					stm.setInt(4, proveedor.getId());
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
		dtosActividad.registrarActividad("Actualizar información del proveedor.", "Proveedores.", tiempo);
		return bandera;
	}
	
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
	
	public String[][] getListadoEmail(String filtrado){

		String matriz[][] = null;
		String cmdStm = "SELECT proveedores.nombre, sector, contacto.nombre, email, proveedores.idProveedores "
						+ "FROM `lecsys2.00`.contacto "
						+ "JOIN `lecsys2.00`.proveedores ON contacto.idProveedores = proveedores.IdProveedores "
		 				+ "WHERE (proveedores.estado = 1 AND (proveedores.nombre LIKE '%" + filtrado + "%' "
		 											 + "OR contacto.nombre LIKE'%" + filtrado + "%' "
		 											 + "OR email LIKE'%" + filtrado + "%')) "
		 				+ "ORDER BY proveedores.nombre";
		
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
	
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ProveedoresDAO, getListadoEmail()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}
}