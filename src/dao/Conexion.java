package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import control.CtrlLogErrores;
import modelo.DtosConfiguracion;

class Conexion {
	
	protected Connection conexion;
	private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
	
	public void conectar() {

		String método = new String(Thread.currentThread().getStackTrace()[3].getMethodName());
		String clase[] = Thread.currentThread().getStackTrace()[2].getClassName().split("\\.");
		
		if(!OperadorSistema.isAcceso(clase[1] + "." + método))
			return;		
		DtosConfiguracion dtosConfiguracion = new DtosConfiguracion();
		String url= "jdbc:mysql://" + dtosConfiguracion.getServidor() + ":3306/lecsys2.00?serverTimezone=UTC";

		try {
			
			conexion = DriverManager.getConnection(url,dtosConfiguracion.getUsuarioBD(),dtosConfiguracion.getPassBD());
			Class.forName(CONTROLADOR);
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("Error al acceder a la base de datos.");
			CtrlLogErrores.guardarError(url);
		}
		return;
	}
	
	public void cerrar() {
		
		try {
			
			if(conexion != null && !conexion.isClosed()) {
				
				conexion.close();
			}
		} catch (Exception e) {

			CtrlLogErrores.guardarError(e.getMessage());
		}
	}
}