package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;

public class EstadisticasDAO extends Conexion {

	public String [] getUltima() {
		
		String respuesta[] = new String[9];
		String comandoStatement = "SELECT DATE_FORMAT(fecha, '%d/%m/%Y'), estudiantesActivos, faltasAlumnos, empleados, "
								+ "faltasEmpleados, ingresosMes, sueldos, compras "
								+ "FROM estadísticas "
								+ "ORDER BY idEstadísticas DESC LIMIT 1";

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);
	
			if(rs.next()) {
				
				respuesta[0] = rs.getString(1);
				respuesta[1] = rs.getString(2);
				respuesta[2] = rs.getString(3);
				respuesta[3] = rs.getString(4);
				respuesta[4] = rs.getString(5);
				float ingresos = rs.getFloat(6);
				float sueldos = rs.getFloat(7);
				float compras = rs.getFloat(8);
				float utilidad = ingresos - (sueldos + compras);
				respuesta[5] = String.format("%.2f", ingresos);
				respuesta[6] = String.format("%.2f", sueldos);
				respuesta[7] = String.format("%.2f", compras);
				respuesta[8] = String.format("%.2f", utilidad);
			}

		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("EstadisticasDAO, getUltima()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return respuesta;	
	}
	
	public String [][] getEstadisticasAnuales(String año) {
		
		float sumaIngresos = 0;
		float sumaSueldos = 0;
		float sumaCompras = 0;
		float sumaUtilidad = 0;
		String matriz[][] = null;
		String comandoStatement = "SELECT MONTH(fecha), estudiantesActivos, faltasAlumnos, empleados, "
								+ "faltasEmpleados, ingresosMes, sueldos, compras "
								+ "FROM estadísticas WHERE YEAR(fecha) = " + año;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);
			rs.last();
			
			if(rs.getRow() != 0) {
				
				matriz = new String[rs.getRow()+1][9];
				rs.beforeFirst();
				int i=0;
	
				while(rs.next()) {
					
					matriz[i][0] = rs.getString(1);
					matriz[i][1] = rs.getString(2);
					matriz[i][2] = rs.getString(3);
					matriz[i][3] = rs.getString(4);
					matriz[i][4] = rs.getString(5);
					float ingresos = rs.getFloat(6);
					float sueldos = rs.getFloat(7);
					float compras = rs.getFloat(8);
					float utilidad = ingresos - (sueldos + compras);
					matriz[i][5] = String.format("%.2f", ingresos);
					matriz[i][6] = String.format("%.2f", sueldos);
					matriz[i][7] = String.format("%.2f", compras);
					matriz[i][8] = String.format("%.2f", utilidad);
					sumaIngresos += ingresos;
					sumaSueldos += sueldos;
					sumaCompras += compras;
					sumaUtilidad += utilidad;
					i++;
				}
				matriz[i][0] = "";
				matriz[i][1] = "";
				matriz[i][2] = "";
				matriz[i][3] = "";
				matriz[i][4] = "";
				matriz[i][5] = String.format("%.2f", sumaIngresos);
				matriz[i][6] = String.format("%.2f", sumaSueldos);
				matriz[i][7] = String.format("%.2f", sumaCompras);
				matriz[i][8] = String.format("%.2f", sumaUtilidad);
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("EstadisticasDAO, getEstadisticasAnuales()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matriz;	
	}
	
	public boolean isNuevoMes() {

		int ultimoMesCargado = 0;

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT MONTH(fecha) FROM estadísticas "
										  + "WHERE (YEAR(fecha) = YEAR(NOW()) AND MONTH(fecha) = MONTH(NOW()))");
					
			if (rs.next())
				ultimoMesCargado = rs.getInt(1);

			if(ultimoMesCargado > 0) {
				
				this.cerrar();
				return false;
			}
			int estudiantesActivos = 0;
			int empleados = 0;
			float sueldos = 0;
			float ingresosMes = 0;
			float compras = 0;
			float servicios = 0;
			int faltasEmpleados = 0;
			int faltasAlumnos = 0;
			rs = stm.executeQuery("SELECT COUNT(*) FROM alumnos "
								+ "WHERE estado = 1");
			
			if (rs.next())
				estudiantesActivos = rs.getInt(1);
			rs = stm.executeQuery("SELECT COUNT(*) FROM empleados "
								+ "WHERE estado = 1");
			
			if (rs.next())
				empleados = rs.getInt(1);
			rs = stm.executeQuery("SELECT SUM(monto) FROM cobros "
								+ "WHERE( MONTH(fecha) = MONTH(NOW()) AND YEAR(fecha) = YEAR(NOW()) AND cobros.idEstadisticas IS NULL)");
			
			if (rs.next())
				ingresosMes = rs.getFloat(1);
			rs = stm.executeQuery("SELECT SUM(pagos.monto) FROM pagos "
								+ "WHERE( MONTH(fecha) = MONTH(NOW()) AND YEAR(fecha) = YEAR(NOW()) AND pagos.idEmpleados > 0 AND pagos.idEstadisticas IS NULL)");
			
			if (rs.next())
				sueldos = rs.getFloat(1);
			rs = stm.executeQuery("SELECT SUM(pagos.monto) FROM pagos "
								+ "WHERE( MONTH(fecha) = MONTH(NOW()) AND YEAR(fecha) = YEAR(NOW()) AND pagos.idProveedor > 0 AND pagos.idEstadisticas IS NULL)");
			
			if (rs.next())
				compras = rs.getFloat(1);
			rs = stm.executeQuery("SELECT COUNT(idFaltas) FROM faltas "
								+ "WHERE( MONTH(fecha) = MONTH(NOW()) AND YEAR(fecha) = YEAR(NOW()) AND faltas.estado = 0)");
			
			if (rs.next())
				faltasAlumnos = rs.getInt(1);			
			PreparedStatement pstm  = this.conexion.prepareStatement("INSERT INTO estadísticas (fecha, estudiantesActivos, empleados, "
																   + "ingresosMes, sueldos, compras, servicios, faltasEmpleados, faltasAlumnos) "
																   + "VALUES( DATE( NOW()), ?, ?, ?, ?, ?, ?, ?, ?)");
			pstm.setInt(1, estudiantesActivos);
			pstm.setInt(2, empleados);
			pstm.setFloat(3, ingresosMes);
			pstm.setFloat(4, sueldos);
			pstm.setFloat(5, compras);
			pstm.setFloat(6, servicios);
			pstm.setInt(7, faltasEmpleados);
			pstm.setInt(8, faltasAlumnos);
			pstm.executeUpdate();
			int ultimoID = 0;
			rs = stm.executeQuery("SELECT idEstadísticas FROM estadísticas ORDER BY idEstadísticas DESC LIMIT 1");
			
			if (rs.next())
				ultimoID = rs.getInt(1);
			
			if(ultimoID > 0) {
				
				pstm  = this.conexion.prepareStatement("UPDATE cobros SET idEstadisticas = ? WHERE idEstadisticas IS NULL");
				pstm.setInt(1, ultimoID);	
				pstm.executeUpdate();
				pstm  = this.conexion.prepareStatement("UPDATE pagos SET idEstadisticas = ? WHERE idEstadisticas IS NULL");
				pstm.setInt(1, ultimoID);	
				pstm.executeUpdate();
			}
		} catch (Exception e) {
		
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("EstadisticasDAO, isNuevoMes()");
		} finally {
			
			this.cerrar();
		}
		return true;
	}
}