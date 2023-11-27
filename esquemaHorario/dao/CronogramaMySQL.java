package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	public boolean [][] getTablaSemanal(int legajo){

		if(legajo == 0)
			return null;
		boolean matrizDiasHorarios[][] = null;
		String cmsStm = "SELECT día, HOUR(hora), MINUTE(hora), duración, granularidad "
						+ "FROM `lecsys2.00`.horarios "
						+ "WHERE idPertenece =  ?";

		try {

			this.conectar();
			PreparedStatement ppstm = this.conexion.prepareStatement(cmsStm,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ppstm.setInt(1, legajo);
			ResultSet rs = ppstm.executeQuery();

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
			CtrlLogErrores.guardarError("CronogramaMySQL, getTablaSemanal()");
			CtrlLogErrores.guardarError(cmsStm);
		} finally {
			
			this.cerrar();
		}
		return matrizDiasHorarios;
	}
	
	@Override
	public boolean setCronograma(Horarios horarios[]) {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String cmsStm = "DELETE FROM `lecsys2.00`.horarios WHERE idPertenece = ?";
				
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmsStm);
			stm.setInt(1, horarios[0].getIdPertenece());
			stm.executeUpdate();
			cmsStm = "INSERT INTO `lecsys2.00`.horarios (día, hora, duración, idPertenece, granularidad) VALUES (?, ?, ?, ?, ?)";
			stm = this.conexion.prepareStatement(cmsStm);
			
			for(int i = 0 ; i < horarios.length ; i++) {
								
				stm.setInt(1, horarios[i].getDia());
				stm.setString(2, horarios[i].getHora());
				stm.setInt(3, horarios[i].getDuración());
				stm.setInt(4, horarios[i].getIdPertenece());
				stm.setInt(5, horarios[i].getGranularidad());
				stm.executeUpdate();				
			}
		} catch (Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CronogramaMySQL, setCronograma()");
			CtrlLogErrores.guardarError(cmsStm);
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registrar horarios de empleado.", "Empleados.", tiempo);
		return bandera;
	}
}