package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.Estadisticas;
import modelo.Situacion;

public class EstadisticasMySQL extends Conexion implements EstadisticasDAO {

	@Override
	public boolean isNuevoMes() {

		int ultimoMesCargado = 0;

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement();
			ResultSet rs = stm.executeQuery("SELECT MONTH(fecha) FROM `lecsys2.00`.estadísticas "
										  + "WHERE (YEAR(fecha) = YEAR(NOW()) AND MONTH(fecha) = MONTH(NOW()))");
			
			if (rs.next())
				ultimoMesCargado = rs.getInt(1);

			if(ultimoMesCargado > 0) {
				
				this.cerrar();
				return false;
			}
			Estadisticas estadistica = new Estadisticas();
			rs = stm.executeQuery("SELECT COUNT(*) FROM `lecsys2.00`.alumnos "
								+ "WHERE estado = 1");
			
			if (rs.next())
				estadistica.setCantidadEstudientas(rs.getInt(1));
			rs = stm.executeQuery("SELECT COUNT(*) FROM `lecsys2.00`.empleados "
								+ "WHERE estado = 1");
			
			if (rs.next())
				estadistica.setCantidadEmpleados(rs.getInt(1));
			rs = stm.executeQuery("SELECT SUM(monto) FROM `lecsys2.00`.cobros "
								+ "WHERE( MONTH(fecha) = MONTH(NOW()) AND YEAR(fecha) = YEAR(NOW()) AND cobros.idEstadisticas IS NULL)");
			
			if (rs.next())
				estadistica.setIngresos(rs.getFloat(1));
			rs = stm.executeQuery("SELECT SUM(pagos.monto) FROM `lecsys2.00`.pagos "
								+ "WHERE( MONTH(fecha) = MONTH(NOW()) AND YEAR(fecha) = YEAR(NOW()) AND pagos.idEmpleados > 0 AND pagos.idEstadisticas IS NULL)");
			
			if (rs.next())
				estadistica.setSueldos(rs.getFloat(1));
			rs = stm.executeQuery("SELECT SUM(pagos.monto) FROM `lecsys2.00`.pagos "
								+ "WHERE( MONTH(fecha) = MONTH(NOW()) AND YEAR(fecha) = YEAR(NOW()) AND pagos.idProveedor > 0 AND pagos.idEstadisticas IS NULL)");
			
			if (rs.next())
				estadistica.setCompras(rs.getFloat(1));
			rs = stm.executeQuery("SELECT COUNT(idFaltas) FROM `lecsys2.00`.faltas "
								+ "WHERE( MONTH(fecha) = MONTH(NOW()) AND YEAR(fecha) = YEAR(NOW()) AND faltas.estado = 0)");
			
			if (rs.next())
				estadistica.setFaltasEstudiantes(rs.getInt(1));			
			PreparedStatement pstm  = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.estadísticas (fecha, estudiantesActivos, empleados, "
																   + "ingresosMes, sueldos, compras, servicios, faltasEmpleados, faltasAlumnos) "
																   + "VALUES( DATE( NOW()), ?, ?, ?, ?, ?, ?, ?, ?)");
			pstm.setInt(1, estadistica.getCantidadEstudientas());
			pstm.setInt(2, estadistica.getCantidadEmpleados());
			pstm.setFloat(3, estadistica.getIngresos());
			pstm.setFloat(4, estadistica.getSueldos());
			pstm.setFloat(5, estadistica.getCompras());
			pstm.setFloat(6, estadistica.getServicios());
			pstm.setInt(7, estadistica.getFaltasEmpleados());
			pstm.setInt(8, estadistica.getFaltasEstudiantes());
			pstm.executeUpdate();
			rs = stm.executeQuery("SELECT idEstadísticas FROM `lecsys2.00`.estadísticas ORDER BY idEstadísticas DESC LIMIT 1");
			
			if (rs.next())
				estadistica.setId(rs.getInt(1));
			
			if(estadistica.getId() > 0) {
				
				pstm  = this.conexion.prepareStatement("UPDATE `lecsys2.00`.cobros SET idEstadisticas = ? WHERE idEstadisticas IS NULL");
				pstm.setInt(1, estadistica.getId());	
				pstm.executeUpdate();
				pstm  = this.conexion.prepareStatement("UPDATE `lecsys2.00`.pagos SET idEstadisticas = ? WHERE idEstadisticas IS NULL");
				pstm.setInt(1, estadistica.getId());	
				pstm.executeUpdate();
			}
		} catch (Exception e) {
		
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("EstadisticasMySQL, isNuevoMes()");
		} finally {
			
			this.cerrar();
		}
		return true;
	}
	
	@Override
	public Estadisticas [] getEstadisticasAnuales(String año) {
		
		Estadisticas respuesta[] = null;
		String cmdStm = "SELECT MONTH(fecha), estudiantesActivos, faltasAlumnos, empleados, "
						+ "faltasEmpleados, ingresosMes, sueldos, compras, servicios "
						+ "FROM `lecsys2.00`.estadísticas WHERE YEAR(fecha) = ?";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setString(1, año);
			ResultSet rs = stm.executeQuery();
			rs.last();
			
			if(rs.getRow() != 0) {
				
				respuesta = new Estadisticas[rs.getRow()];
				rs.beforeFirst();
				int i=0;
	
				while(rs.next()) {
					
					respuesta[i] = new Estadisticas();
					respuesta[i].setMes(rs.getInt(1));
					respuesta[i].setCantidadEstudientas(rs.getInt(2));
					respuesta[i].setFaltasEstudiantes(rs.getInt(3));
					respuesta[i].setCantidadEmpleados(rs.getInt(4));
					respuesta[i].setFaltasEmpleados(rs.getInt(5));
					respuesta[i].setIngresos(rs.getFloat(6));
					respuesta[i].setSueldos(rs.getFloat(7));
					respuesta[i].setCompras(rs.getFloat(8));
					respuesta[i].setServicios(rs.getFloat(9));
					i++;
				}
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("EstadisticasMySQL, getEstadisticasAnuales()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return respuesta;	
	}	
	
	@Override
	public String[] getListadoAños() {
		
		String listado[] = null;
		String cmdStm = "SELECT YEAR(fecha) FROM `lecsys2.00`.estadísticas GROUP BY YEAR(fecha) ORDER BY fecha DESC";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);			
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			listado = new String[rs.getRow()];
			rs.beforeFirst();
			int i = 0;
			
			while(rs.next()) {

				listado[i] = rs.getString(1);
				i++;
			}
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("EstadisticasMySQL, getListadoAños()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return listado;
	}
	
	@Override
	public Estadisticas getResumenMensual() {
		
		Estadisticas respuesta = new Estadisticas();
		String cmdStm = "SELECT idEstadísticas, DATE_FORMAT(fecha, '%d/%m/%Y'), estudiantesActivos, faltasAlumnos, empleados, "
								+ "faltasEmpleados, ingresosMes, sueldos, compras, servicios "
								+ "FROM `lecsys2.00`.estadísticas "
								+ "ORDER BY idEstadísticas DESC LIMIT 1";

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
	
			if(rs.next()) {

				respuesta.setId(rs.getInt(1));
				respuesta.setFecha(rs.getString(2));
				respuesta.setCantidadEstudientas(rs.getInt(3));
				respuesta.setFaltasEstudiantes(rs.getInt(4));
				respuesta.setCantidadEmpleados(rs.getInt(5));
				respuesta.setFaltasEmpleados(rs.getInt(6));
				respuesta.setIngresos(rs.getFloat(7));
				respuesta.setSueldos(rs.getFloat(8));
				respuesta.setCompras(rs.getFloat(9));
				respuesta.setServicios(rs.getFloat(10));
			}
			cmdStm = "SELECT deuda, precio FROM `lecsys2.00`.alumnos "
					+ "JOIN `lecsys2.00`.valorcuota ON alumnos.idCurso = valorcuota.idCurso "
					+ "JOIN  `lecsys2.00`.grupofamiliar ON alumnos.idGrupoFamiliar = grupofamiliar.idGrupoFamiliar "
					+ "WHERE (deuda < 1 AND descuento > 0 AND alumnos.estado = 1)";
			rs = stm.executeQuery(cmdStm);
			rs.last();
			respuesta.setConDescuento(new Situacion());
			respuesta.getConDescuento().setAlDía(rs.getRow());
			
			cmdStm = "SELECT deuda, precio FROM `lecsys2.00`.alumnos "
					+ "JOIN `lecsys2.00`.valorcuota ON alumnos.idCurso = valorcuota.idCurso "
					+ "JOIN  `lecsys2.00`.grupofamiliar ON alumnos.idGrupoFamiliar = grupofamiliar.idGrupoFamiliar "
					+ "WHERE (deuda = 1 AND descuento > 0 AND alumnos.estado = 1)";
			rs = stm.executeQuery(cmdStm);
			int i = 0;
			float parcial = 0;
			
			while(rs.next()) {
			
				parcial += rs.getInt(1) * rs.getFloat(2);
				i++;
			}
			respuesta.getConDescuento().setUnMesDeuda(i);
			cmdStm = "SELECT deuda, precio FROM `lecsys2.00`.alumnos "
					+ "JOIN `lecsys2.00`.valorcuota ON alumnos.idCurso = valorcuota.idCurso "
					+ "JOIN  `lecsys2.00`.grupofamiliar ON alumnos.idGrupoFamiliar = grupofamiliar.idGrupoFamiliar "
					+ "WHERE (deuda > 1 AND descuento > 0 AND alumnos.estado = 1)";
			rs = stm.executeQuery(cmdStm);
			i = 0;
			
			while(rs.next()) {
				
				parcial += rs.getInt(1) * rs.getFloat(2);
				i++;
			}
			respuesta.getConDescuento().setMasDeUnMes(i);
			respuesta.getConDescuento().setSumaDeuda(parcial);
			cmdStm = "SELECT deuda, precio FROM `lecsys2.00`.alumnos "
					+ "JOIN `lecsys2.00`.valorcuota ON alumnos.idCurso = valorcuota.idCurso "
					+ "JOIN  `lecsys2.00`.grupofamiliar ON alumnos.idGrupoFamiliar = grupofamiliar.idGrupoFamiliar "
					+ "WHERE (deuda < 1 AND descuento = 0 AND alumnos.estado = 1)";
			rs = stm.executeQuery(cmdStm);
			rs.last();
			respuesta.setSinDescuento(new Situacion());
			respuesta.getSinDescuento().setAlDía(rs.getRow());
			cmdStm = "SELECT deuda, precio FROM `lecsys2.00`.alumnos "
					+ "JOIN `lecsys2.00`.valorcuota ON alumnos.idCurso = valorcuota.idCurso "
					+ "JOIN  `lecsys2.00`.grupofamiliar ON alumnos.idGrupoFamiliar = grupofamiliar.idGrupoFamiliar "
					+ "WHERE (deuda = 1 AND descuento = 0 AND alumnos.estado = 1)";
			rs = stm.executeQuery(cmdStm);
			i = 0;
			parcial = 0;
			
			while(rs.next()) {
			
				parcial += rs.getInt(1) * rs.getFloat(2);
				i++;
			}
			respuesta.getSinDescuento().setUnMesDeuda(i);
			cmdStm = "SELECT deuda, precio FROM `lecsys2.00`.alumnos "
					+ "JOIN `lecsys2.00`.valorcuota ON alumnos.idCurso = valorcuota.idCurso "
					+ "JOIN  `lecsys2.00`.grupofamiliar ON alumnos.idGrupoFamiliar = grupofamiliar.idGrupoFamiliar "
					+ "WHERE (deuda > 1 AND descuento = 0 AND alumnos.estado = 1)";
			rs = stm.executeQuery(cmdStm);
			i = 0;
			
			while(rs.next()) {
				
				parcial += rs.getInt(1) * rs.getFloat(2);
				i++;
			}
			respuesta.getSinDescuento().setMasDeUnMes(i);
			respuesta.getSinDescuento().setSumaDeuda(parcial);
		} catch(Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("EstadisticasMySQL, getResumenMensual()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return respuesta;	
	}
}