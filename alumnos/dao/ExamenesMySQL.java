package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;
import modelo.DtosAlumno;

public class ExamenesMySQL extends Conexion implements ExamenesDAO{

	@Override
	public String [][] getExamen(String idAlumno) {

		String matriz[][]=null;

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT fecha, tipo, nota FROM examenes WHERE idAlumno = " + idAlumno);
			rs.last();	
			matriz = new String[rs.getRow()][3];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
					
				matriz[i][0] = rs.getString(1);	
				matriz[i][1] = rs.getString(2);	
				matriz[i][2] = rs.getString(3);	
				i++;
			}
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AlumnosDAO, getExamen()");
		} finally {
			
			this.cerrar();
		}
		return matriz;	
	}
	
	@Override
	public boolean setExamen() {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosAlumno dtosAlumno = new DtosAlumno();
		DtosActividad dtosActividad = new DtosActividad();
		String fecha = dtosAlumno.getFechaAño() + "-"
					 + dtosAlumno.getFechaMes() + "-"
					 + dtosAlumno.getFechaDia();
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("INSERT INTO examenes "
																+ "(idAlumno, fecha, tipo, nota, idProfesor, idCurso) "
																+ "VALUES (?, ?, ?, ?, ?, ?)");
			stm.setInt(1, Integer.parseInt(dtosAlumno.getLegajo()));
			stm.setString(2, fecha);
			stm.setString(3, dtosAlumno.getTipoExamen());
			stm.setInt(4, Integer.parseInt(dtosAlumno.getResultadoExamen()));
			stm.setInt(5, Integer.parseInt(dtosAlumno.getIdProfesor()));
			stm.setInt(6, Integer.parseInt(dtosAlumno.getCurso()));
			stm.executeUpdate();
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AlumnosDAO, setExamen()");
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Carga de exámenes.", "Alumnos", tiempo);
		return bandera;
	}
}
