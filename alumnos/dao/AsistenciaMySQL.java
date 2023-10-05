package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;
import modelo.DtosAlumno;

public class AsistenciaMySQL extends Conexion implements AsistenciaDAO {

	private int faltas;
	private int presente;
	private int tarde;

	@Override
	public boolean setAsistencia(int fila) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosAlumno dtosAlumno = new DtosAlumno();
		DtosActividad dtosActividad = new DtosActividad();

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("INSERT INTO faltas (idAlumnos, fecha, estado, idCurso) VALUES (?, ?, ?, ?)");
			stm.setInt(1, dtosAlumno.getAsistencia("Legajo",fila));
			stm.setString(2, dtosAlumno.getFechaActual(false));
			stm.setInt(3, dtosAlumno.getAsistencia("Estado",fila));
			stm.setInt(4, Integer.parseInt(dtosAlumno.getCurso()));
			stm.executeUpdate();
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AlumnosDAO, setAsistencia()");
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
			CtrlLogErrores.guardarError("AlumnosDAO, isAsistenciaTomada()");
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
			CtrlLogErrores.guardarError("AlumnosDAO, tablaAsistenciasAlumnos()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}
	
	@Override
	public void getInfoAsistencia(String idAlumno) {
		
		String comando = "SELECT COUNT(*) FROM faltas WHERE (idAlumnos = " + idAlumno + " AND YEAR(fecha) = YEAR(NOW())";
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comando + " AND estado = 0)");
			
			if (rs.next()) 
				faltas = rs.getInt(1);	

			rs = stm.executeQuery(comando + " AND estado = 1)");
			
			if (rs.next()) 
				presente = rs.getInt(1);
			
			rs = stm.executeQuery(comando + " AND estado = 2)");
			
			if (rs.next()) 
				tarde = rs.getInt(1);
			
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AlumnosDAO, getInfoAsistencia()");
		} finally {
			
			this.cerrar();
		}
	}
	
	@Override
	public String getFaltas() {
		
		return faltas + "";
	}
	
	@Override
	public String getPresente() {
		
		return presente + "";
	}
	
	@Override
	public String getTarde() {
		
		return tarde + "";
	}
}
