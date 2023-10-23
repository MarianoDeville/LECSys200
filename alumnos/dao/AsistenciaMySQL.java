package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;
import modelo.ResumenAsistencia;
import modelo.Asistencia;

public class AsistenciaMySQL extends Conexion implements AsistenciaDAO {

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean setAsistencia(Asistencia asistencia[]) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("INSERT INTO faltas (idAlumnos, fecha, estado, idCurso) VALUES (?, DATE(NOW)), ?, ?)");
			
			for(int i = 0; i < asistencia.length; i++) {
				
				stm.setInt(1, asistencia[i].getLegajo());
				stm.setInt(2, asistencia[i].getEstado());
				stm.setInt(3, asistencia[i].getIdCurso());
				stm.executeUpdate();
			}
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AsistenciaMySQL, setAsistencia()");
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registro de asistencia.", "Alumnos", tiempo);
		return bandera;
	}

	@Override
	public boolean isAsistenciaTomada(String idCurso, boolean reducido) {
		
		boolean bandera = false;
		String comandoStatement = "SELECT idCurso FROM faltas WHERE (idCurso = " + idCurso + " AND DATE(fecha) = CURDATE())"; 
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);

			if(rs.next())
				bandera =true;

		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AsistenciaMySQL, isAsistenciaTomada()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return bandera;
	}

	@Override
	public String [][] getAsistencias(String idCurso, boolean reducido, int mes) {

		String matriz[][]=null;
		String comandoStatement = "SELECT idFaltas, idAlumnos, DATE_FORMAT(fecha, '%d/%m/%Y'), estado, idCurso FROM faltas WHERE "; 
		
		if(mes == 0)
			comandoStatement += "idCurso = " + idCurso;
		else
			comandoStatement += "(idCurso = " + idCurso + " AND MONTH(fecha)= " + mes + ") ";
	
		if(reducido)
			comandoStatement += " GROUP BY fecha";
		comandoStatement += " ORDER BY fecha DESC";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);
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
			CtrlLogErrores.guardarError("AsistenciaMySQL, tablaAsistenciasAlumnos()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}
	
	@Override
	public ResumenAsistencia getInfoAsistencia(String idAlumno) {
		
		ResumenAsistencia resumen = new ResumenAsistencia();
		String comando = "SELECT COUNT(*) FROM faltas WHERE (idAlumnos = " + idAlumno + " AND YEAR(fecha) = YEAR(NOW())";
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comando + " AND estado = 0)");
			
			if (rs.next()) 
				resumen.setFaltas(rs.getInt(1));	

			rs = stm.executeQuery(comando + " AND estado = 1)");
			
			if (rs.next()) 
				resumen.setPresente(rs.getInt(1));
			
			rs = stm.executeQuery(comando + " AND estado = 2)");
			
			if (rs.next()) 
				resumen.setTarde(rs.getInt(1));
			
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AsistenciaMySQL, getInfoAsistencia()");
		} finally {
			
			this.cerrar();
		}
		return resumen;
	}
}
