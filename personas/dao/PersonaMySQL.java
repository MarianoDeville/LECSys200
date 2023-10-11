package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import control.CtrlLogErrores;

public class PersonaMySQL extends Conexion implements PersonaDAO {
	
	@Override
	public String [][] getListadoCumpleAños() {

		String respuesta[][] = null;
		
		String ppstm = "SELECT nombre, apellido FROM `lecsys2.00`.persona "
					  + "JOIN `lecsys2.00`.alumnos ON alumnos.dni = persona.dni "
					  + "JOIN `lecsys2.00`.empleados ON empleados.dni = persona.dni "
					  + "WHERE (DAY(fechaNacimiento) = DAY(NOW()) AND MONTH(fechaNacimiento) = MONTH(NOW()) "
					  + "AND alumnos.estado = 1 AND empleados.estado = 1) "
					  + "GROUP BY persona.dni";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(ppstm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery();
			rs.last();
			
			if(rs.getRow() > 0) {
				
				respuesta = new String[rs.getRow()][2];
				rs.beforeFirst();
				int i=0;
	
				while (rs.next()) {
						
					respuesta[i][0] = rs.getString(1);	
					respuesta[i][1] = rs.getString(2);	
					i++;
				}
			}
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PersonaMySQL, getListadoCumpleAños()");
			CtrlLogErrores.guardarError(ppstm);
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}

	@Override
	public boolean isDNIDuplicado(String dni) {

		boolean bandera = false;
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("SELECT nombre FROM persona WHERE dni = ?");
			stm.setString(1, dni);
			ResultSet rs = stm.executeQuery();

			if(rs.next())
				bandera = true;
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("PersonaMySQL, isDNIDuplicado()");
		} finally {
			
			this.cerrar();
		}
		return bandera;
	}
}