package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;
import modelo.Horarios;

public class CronogramaMySQL extends Conexion implements CronogramaDAO{

	private int granularidad;

	
	
	@Override
	public int getGranularidad() {
		
		return granularidad;
	}
	
	
	@Override
	public boolean [][] getCronogramaDias(String idCurso, int idProfesor, int aula){

		boolean matrizDiasHorarios[][] = new boolean[6][33];
		String where = "WHERE (curso.estado = 1 AND ";
		
		if(!idCurso.equals("0") && idProfesor == 0 && aula == -1) {

			where += "curso.idCurso = " + idCurso + ")";
		}else if(idCurso.equals("0") && idProfesor != 0 && aula == -1) {
			
			where += "curso.idProfesor = " + idProfesor + ")";
		}else if(idCurso.equals("0") && idProfesor == 0 && aula != -1) {
			
			where += "curso.aula = " + aula + ")";
		}else if(!idCurso.equals("0") && idProfesor == 0 && aula != -1) {
			
			where += "curso.idCurso = " + idCurso + " AND curso.aula = " + aula + ")";
		}else if(idCurso.equals("0") && idProfesor != 0 && aula != -1) {

			where += "(curso.idProfesor = " + idProfesor + " OR curso.aula = " + aula + "))";
		}else if(!idCurso.equals("0") && idProfesor != 0 && aula != -1) {
			
			where += "(curso.aula = " + aula + " OR (curso.idProfesor = " + idProfesor + " AND curso.idCurso != " + idCurso + ")))";		
		}
		String comandoStatement = "SELECT día, HOUR(hora), MINUTE(hora), duración "
								+ "FROM `lecsys2.00`.horarios "
								+ "JOIN curso ON diasCursado.idCurso = curso.idCurso "
								+ "JOIN empleados ON horarios.idPertenece = empleados.legajo "
								+ where;

		for(int i = 0 ; i < 6 ; i++) {
			
			for(int e = 0 ; e < 32 ; e++) {
				
				matrizDiasHorarios[i][e] = false;
			}
		}

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement();
			ResultSet rs = stm.executeQuery(comandoStatement);
			
			while(rs.next()) {
				
				int dia = rs.getInt(1);
				int duracion = rs.getInt(4);
				int pos = (rs.getInt(2) - 7) * 2 + rs.getInt(3) / 30;
				
				while(0 < duracion--) {
					
					matrizDiasHorarios[dia][pos++] = true;
				}
			}
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CursosMySQL, getCronogramaDias()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matrizDiasHorarios;
	}
	
	
	
	
	
	
	
	@Override
	public boolean [][] getCronogramaDias(int idEmpleado){

		if(idEmpleado == 0)
			return null;
		boolean matrizDiasHorarios[][] = null;
		String comandoStatement = "SELECT día, HOUR(hora), MINUTE(hora), duración, granularidad "
								+ "FROM `lecsys2.00`.horariosEmpleados "
								+ "WHERE idEmpleado = " + idEmpleado;

		try {

			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);

			if(rs.next()) {
				
				granularidad = rs.getInt(5);
			} else {
				
				granularidad = -1;
				return null;
			}
			rs.beforeFirst();
			int tiempo[] = new int[] {97, 65, 33, 17};
			int granulo[] = new int[] {6, 4, 2, 1};
			matrizDiasHorarios = new boolean[6][tiempo[granularidad]];
			
			for(int i = 0 ; i < 6 ; i++) {
				
				for(int e = 0 ; e < matrizDiasHorarios[0].length ; e++) {
					
					matrizDiasHorarios[i][e] = false;
				}
			}		

			while(rs.next()) {
				
				int dia = rs.getInt(1);
				int duracion = rs.getInt(4);
				int pos = (rs.getInt(2) - 7) * granulo[granularidad] + rs.getInt(3) / (60 / granulo[granularidad]);
				
				while(0 < duracion--) {
					
					matrizDiasHorarios[dia][pos++] = true;
				}
			}
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CronogramaMySQL, getCronogramaDias()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matrizDiasHorarios;
	}
	
	
	
	
	
	
	@Override
	public boolean setCronogramaDias(Horarios horarios[]) {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("DELETE FROM `lecsys2.00`.horariosEmpleados WHERE idPertenece = ?");
			stm.setInt(1, horarios[0].getIdPertenece());
			stm.executeUpdate();

			for(int i = 0 ; i < horarios.length ; i++) {
				
				stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.horariosEmpleados (día, hora, duración, idEmpleado, granularidad) VALUES (?, ?, ?, ?, ?)");
				stm.setInt(1, horarios[i].getDia());
				stm.setTime(2, horarios[i].getHora());
				stm.setInt(3, horarios[i].getDuración());
				stm.setInt(4, horarios[i].getIdPertenece());
				stm.setInt(5, horarios[i].getGranularidad());
				stm.executeUpdate();				
			}
		} catch (Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CronogramaMySQL, setCronogramaDias()");
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registrar horarios de empleado.", "Empleados.", tiempo);
		return bandera;
	}
}
