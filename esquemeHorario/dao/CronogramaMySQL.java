package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;

public class CronogramaMySQL extends Conexion implements CronogramaDAO{

	private boolean matrizDiasHorarios[][];
	private int granularidad;

	public int getGranularidad() {
		
		return granularidad;
	}
	
	public boolean [][] getmatrizDiasHorarios(){
		
		return matrizDiasHorarios;
	}

	public boolean getCronogramaDias(int idEmpleado){

		if(idEmpleado == 0)
			return false;
		boolean bandera = true;
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
				return false;
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
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CronogramaMySQL, getCronogramaDias()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return bandera;
	}
	
	public boolean setCronogramaDias(int idEmpleado, String horarios[][], int gran) {
		
		if(idEmpleado == 0)
			return false;
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("DELETE FROM `lecsys2.00`.horariosEmpleados WHERE idEmpleado = ?");
			stm.setInt(1, idEmpleado);
			stm.executeUpdate();

			for(int i = 0 ; i < horarios.length ; i++) {
				
				stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.horariosEmpleados (día, hora, duración, idEmpleado, granularidad) VALUES (?, ?, ?, ?, ?)");
				stm.setString(1, horarios[i][0]);
				stm.setString(2, horarios[i][1]);
				stm.setString(3, horarios[i][2]);
				stm.setInt(4, idEmpleado);
				stm.setInt(5, gran);
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
