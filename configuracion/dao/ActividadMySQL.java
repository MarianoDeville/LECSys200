package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.Actividad;

public class ActividadMySQL extends Conexion implements ActividadDAO {
	
	@Override
	public void setActividad(Actividad actividad) {
		
		if(actividad.getIdUsuario().equals("0"))
			return;
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.registroActividad (idUsuario, fecha, hora, acción, modulo, ip, tiempo) "
																 + "VALUES (?, DATE(NOW()), TIME(NOW()), ?, ?, ?, ?)");
			stm.setString(1, actividad.getIdUsuario());
			stm.setString(2, actividad.getAccion());
			stm.setString(3, actividad.getModulo());
			stm.setString(4, actividad.getIp());
			stm.setLong(5, actividad.getTiempo());
			stm.executeUpdate();
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ActividadDAO, setActividad()");
		} finally {
			
			this.cerrar();
		}
	}
	
	@Override
	public Actividad [] getActividad(String mes, String año) {

		Actividad respuesta[] = null;
		String cmdStm = "SELECT idRegistroActividad, nombre, fecha, hora, acción, modulo, ip, registroActividad.idUsuario  "
						 + "FROM `lecsys2.00`.registroActividad "
						 + "JOIN `lecsys2.00`.usuarios ON registroActividad.idUsuario = usuarios.idUsuario "
						 + "WHERE MONTH(fecha)= " + mes + " AND YEAR(fecha)= " + año + " "
						 + "ORDER BY idRegistroActividad DESC";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			respuesta = new Actividad[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
				
				respuesta[i] = new Actividad();
				respuesta[i].setId(rs.getInt(1));
				respuesta[i].setNombre(rs.getString(2));
				respuesta[i].setFecha(rs.getString(3));
				respuesta[i].setHora(rs.getString(4));
				respuesta[i].setAccion(rs.getString(5));
				respuesta[i].setModulo(rs.getString(6));
				respuesta[i].setIp(rs.getString(7));
				respuesta[i].setIdUsuario(rs.getString(8));
				i++;
			}
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ActividadMySQL, getActividad()");
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
}