package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;
import modelo.ResumenAsistencia;
import modelo.Alumno;
import modelo.Asistencia;

public class AsistenciaMySQL extends Conexion implements AsistenciaDAO {

	@Override
	public boolean setAsistencia(Alumno alumnos[]) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.faltas (idAlumnos, fecha, estado, idCurso) VALUES (?, DATE(NOW)), ?, ?)");
			
			for(int i = 0; i < alumnos.length; i++) {
				
				stm.setInt(1, alumnos[i].getLegajo());
				stm.setInt(2, alumnos[i].getAsistencias()[0].getEstado());
				stm.setInt(3, alumnos[i].getIdCurso());
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
	public Alumno [] getListado(int idCurso, boolean reducido, int mes) {

		Alumno alumnos[] = null;
		String cmdStm = "SELECT legajo, nombre, apellido "
						+ "FROM `lecsys2.00`.alumnos "
						+ "JOIN `lecsys2.00`.persona ON alumnos.dni = persona.dni "
						+ "WHERE idCurso = ?";

		try {

			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setInt(1, idCurso);
			ResultSet rs = stm.executeQuery();
			rs.last();	
			alumnos = new Alumno[rs.getRow()];
			rs.beforeFirst();
			int i = 0;

			while (rs.next()) {
					
				alumnos[i] = new Alumno();
				alumnos[i].setLegajo(rs.getInt(1));
				alumnos[i].setNombre(rs.getString(2));
				alumnos[i].setApellido(rs.getString(3));
				alumnos[i].setIdCurso(idCurso);
				i++;
			}
			cmdStm = "SELECT idFaltas, DATE_FORMAT(fecha, '%d/%m/%Y'), estado "
					+ "FROM `lecsys2.00`.faltas "
					+ "WHERE "; 
	
			if(mes == 0)
				cmdStm += "idAlumno = ? ";
			else
				cmdStm += "(idAlumno = ? AND MONTH(fecha)= " + mes + ") ";
		
			if(reducido)
				cmdStm += "GROUP BY fecha ";
			cmdStm += "ORDER BY fecha DESC ";
			
			for(i = 0; i < alumnos.length; i++) {
				
				stm.setInt(1, alumnos[i].getLegajo());
				rs = stm.executeQuery();
				rs.last();	
				alumnos[i].setAsistencias(new Asistencia[rs.getRow()]);
				rs.beforeFirst();
				int e = 0;

				while (rs.next()) {
						
					alumnos[i].getAsistencias()[e] = new Asistencia();
					alumnos[i].getAsistencias()[e].setId(rs.getInt(1));
					alumnos[i].getAsistencias()[e].setFecha(rs.getString(2));
					alumnos[i].getAsistencias()[e].setEstado(rs.getInt(3));
					e++;
				}
			}
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AsistenciaMySQL, getListado()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return alumnos;
	}
	
	
	
	
	
	
	
	
	@Override
	public boolean isAsistenciaTomada(int idCurso, boolean reducido) {
		
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
