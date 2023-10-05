package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;

public class ActividadDAO extends Conexion {
	
	public void setActividad(String idUsuario, String accion, String modulo, String miIP, long tiempo) {
		
		if(idUsuario.equals("0"))
			return;
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.registroActividad (idUsuario, fecha, hora, acción, modulo, ip, tiempo) "
																 + "VALUES (?, DATE(NOW()), TIME(NOW()), ?, ?, ?, ?)");
			stm.setString(1, idUsuario);
			stm.setString(2, accion);
			stm.setString(3, modulo);
			stm.setString(4, miIP);
			stm.setLong(5, tiempo);
			stm.executeUpdate();
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ActividadDAO, setActividad()");
		} finally {
			
			this.cerrar();
		}
	}
	
	public String [][] getActividad(String mes, String año) {

		String respuesta[][] = null;
		String armoStatement = "SELECT idRegistroActividad, nombre, fecha, hora, acción, modulo, ip "
							 + "FROM `lecsys2.00`.registroActividad "
							 + "JOIN `lecsys2.00`.usuarios ON registroActividad.idUsuario = usuarios.idUsuario "
							 + "WHERE MONTH(fecha)= " + mes + " AND YEAR(fecha)= " + año + " "
							 + "ORDER BY idRegistroActividad DESC";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(armoStatement);
			rs.last();	
			respuesta = new String[rs.getRow()][7];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
				
				respuesta[i][0] = rs.getInt(1) + "";
				respuesta[i][1] = rs.getString(2);
				respuesta[i][2] = rs.getString(3);
				respuesta[i][3] = rs.getString(4);
				respuesta[i][4] = rs.getString(5);
				respuesta[i][5] = rs.getString(6);
				respuesta[i][6] = rs.getString(7);
				i++;
			}
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ActividadDAO, getActividad()");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
}