package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import control.CtrlLogErrores;
import modelo.Alumno;
import modelo.DtosActividad;
import modelo.Examenes;

public class ExamenesMySQL extends Conexion implements ExamenesDAO{

	@Override
	public boolean setExamen(Alumno alumnos[]) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String pprStm = "INSERT INTO examenes (idAlumno, fecha, tipo, nota, idProfesor, idCurso) "
						+ "VALUES (?, ?, ?, ?, ?, ?)";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(pprStm);
			
			for(int i = 0; i < alumnos.length; i++) {
				
				stm.setInt(1, alumnos[i].getLegajo());
				stm.setString(2, alumnos[i].getExamenes()[0].getFecha());
				stm.setString(3, alumnos[i].getExamenes()[0].getTipo());
				stm.setInt(4, alumnos[i].getExamenes()[0].getNota());
				stm.setInt(5, alumnos[i].getExamenes()[0].getLegajoProfesor());
				stm.setInt(6, alumnos[i].getIdCurso());
				stm.executeUpdate();				
			}
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ExamenesMySQL, setExamen()");
			CtrlLogErrores.guardarError(pprStm);
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Carga de exámenes.", "Alumnos", tiempo);
		return bandera;
	}
	
	@Override
	public Examenes [] getExamenes(int legajo) {

		Examenes examenes[] = null;
		String cmdStm = "SELECT id, fecha, tipo, nota, idProfesor FROM examenes WHERE idAlumno = ?";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setInt(1, legajo);
			ResultSet rs = stm.executeQuery();
			rs.last();	
			examenes = new Examenes[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
				
				examenes[i] = new Examenes();
				examenes[i].setId(rs.getInt(1));
				examenes[i].setFecha(rs.getString(2));
				examenes[i].setTipo(rs.getString(3));
				examenes[i].setNota(rs.getInt(4));
				examenes[i].setLegajoProfesor(rs.getInt(5));
				i++;
			}
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("ExamenesMySQL, getExamenes()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return examenes;	
	}
}
