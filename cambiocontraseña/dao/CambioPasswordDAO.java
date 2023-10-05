package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;
import modelo.DtosCambioPassword;

public class CambioPasswordDAO extends Conexion {
	
	private DtosCambioPassword dtosCambioPass;
	
	public boolean checkContraseña() {
		
		boolean bandera = false;
		dtosCambioPass = new DtosCambioPassword();
		
		try {

			this.conectar();
			Statement stm = this.conexion.createStatement();
			ResultSet rs = stm.executeQuery("SELECT idUsuario FROM `lecsys2.00`.usuarios "
										  + "WHERE(nombre = BINARY'" + dtosCambioPass.getNombreUsuario() + "' "
										  + "AND contraseña = SHA('" + dtosCambioPass.getContraseñaOld() + "'))");
	
			if(rs.next())
				bandera = true;
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
		}finally {
			
			this.cerrar();
		}
		return bandera;
	}
	
	public boolean guardarNuevaContraseña() {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("UPDATE `lecsys2.00`.usuarios "
																 + "SET contraseña = SHA(?), cambioContraseña = 3 "
																 + "WHERE nombre = ?");
			stm.setString(1, dtosCambioPass.getContraseñaNew());
			stm.setString(2, dtosCambioPass.getNombreUsuario());
			stm.executeUpdate();
		} catch (Exception e) {

			CtrlLogErrores.guardarError(e.getMessage());
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Cambio contraseña.", "Principal", tiempo);
		return bandera;
	}
}