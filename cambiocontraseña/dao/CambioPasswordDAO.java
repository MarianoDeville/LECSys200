package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;
import modelo.DtosCambioPassword;

public class CambioPasswordDAO extends Conexion {
	
	private DtosCambioPassword dtosCambioPass;
	
	public boolean checkContrase�a() {
		
		boolean bandera = false;
		dtosCambioPass = new DtosCambioPassword();
		
		try {

			this.conectar();
			Statement stm = this.conexion.createStatement();
			ResultSet rs = stm.executeQuery("SELECT idUsuario FROM `lecsys2.00`.usuarios "
										  + "WHERE(nombre = BINARY'" + dtosCambioPass.getNombreUsuario() + "' "
										  + "AND contrase�a = SHA('" + dtosCambioPass.getContrase�aOld() + "'))");
	
			if(rs.next())
				bandera = true;
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
		}finally {
			
			this.cerrar();
		}
		return bandera;
	}
	
	public boolean guardarNuevaContrase�a() {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("UPDATE `lecsys2.00`.usuarios "
																 + "SET contrase�a = SHA(?), cambioContrase�a = 3 "
																 + "WHERE nombre = ?");
			stm.setString(1, dtosCambioPass.getContrase�aNew());
			stm.setString(2, dtosCambioPass.getNombreUsuario());
			stm.executeUpdate();
		} catch (Exception e) {

			CtrlLogErrores.guardarError(e.getMessage());
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Cambio contrase�a.", "Principal", tiempo);
		return bandera;
	}
}