package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import control.CtrlLogErrores;
import modelo.DtosActividad;
import modelo.DtosCambioPassword;

public class CambioPasswordMySQL extends Conexion implements CambioPasswordDAO{
	
	private DtosCambioPassword dtosCambioPass;
	
	@Override
	public boolean checkContraseña() {
		
		boolean bandera = false;
		dtosCambioPass = new DtosCambioPassword();
		String cmdStm = "SELECT idUsuario FROM `lecsys2.00`.usuarios "
						+ "WHERE(nombre = BINARY? AND contraseña = SHA(?))";
		
		try {

			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setString(1, dtosCambioPass.getNombreUsuario());
			stm.setString(2, dtosCambioPass.getContraseñaOld());
			ResultSet rs = stm.executeQuery();
	
			if(rs.next())
				bandera = true;
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CambioPasswordMySQL, checkContraseña");
			CtrlLogErrores.guardarError(cmdStm);
		}finally {
			
			this.cerrar();
		}
		return bandera;
	}
	
	@Override
	public boolean guardarNuevaContraseña() {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String cmdStm = "UPDATE `lecsys2.00`.usuarios "
						 + "SET contraseña = SHA(?), cambioContraseña = 3 WHERE nombre = ?";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setString(1, dtosCambioPass.getContraseñaNew());
			stm.setString(2, dtosCambioPass.getNombreUsuario());
			stm.executeUpdate();
		} catch (Exception e) {

			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CambioPasswordMySQL, guardarNuevaContraseña");
			CtrlLogErrores.guardarError(cmdStm);
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Cambio contraseña.", "Principal", tiempo);
		return bandera;
	}
}