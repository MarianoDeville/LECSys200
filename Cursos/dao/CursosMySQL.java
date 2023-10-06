package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;

public class CursosMySQL extends Conexion {
	
	public String [][] buscarDiasCurso(String idCurso) {
		
		String matriz[][] = null;
		String comandoStatement = "SELECT día, hora, duración FROM `lecsys2.00`.diasCursado WHERE idCurso = " + idCurso;
		
		try {
		
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);
			rs.last();	
			matriz = new String[rs.getRow()][3];
			rs.beforeFirst();
			int i=0; 
			
			while(rs.next()) {
			
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);
				i++;
			}
		} catch (Exception e) {
		
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CursosMySQL, buscarDiasCurso()");
		} finally {
		
			this.cerrar();
		}
		return matriz;
	}
	
	public String [][] getListado(String idCurso) {
		
		String matriz[][] = null;
		String where = null;
		
		if(idCurso.equals(""))
			where = "WHERE curso.estado = 1 ";
		else
			where = "WHERE (curso.estado = 1 AND curso.idCurso = " + idCurso + ")";

		String comandoStatement = "SELECT curso.idCurso, año, nivel, nombre, apellido, precio, curso.idProfesor, aula "
								+ "FROM `lecsys2.00`.curso "
								+ "JOIN empleados ON curso.idProfesor = empleados.idEmpleado "
								+ "JOIN persona ON empleados.idPersona = persona.idPersona "
								+ "JOIN valorCuota on curso.idCurso = valorCuota.idCurso "
								+ where
								+ "GROUP BY curso.idCurso";

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);
			rs.last();	
			matriz = new String[rs.getRow()][9];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
				
				matriz[i][0] = rs.getString(2);
				matriz[i][1] = rs.getString(3);
				matriz[i][2] = rs.getString(4) + " " + rs.getString(5);
				matriz[i][3] = String.format("%.2f", rs.getFloat(6));
				matriz[i][5] = rs.getString(1);
				matriz[i][6] = rs.getString(1);
				matriz[i][7] = rs.getString(7);
				matriz[i][8] = rs.getString(8);
				i++;
			}
			
			String dia[] = new String[] {"Lunes", "Martes", "Miercoles", "Jueves", "viernes", "Sábado"};
			i = 0;		
			
			while(i < matriz.length) {		
				
				comandoStatement = "SELECT día FROM `lecsys2.00`.diasCursado WHERE idCurso = " + matriz[i][6];
				rs = stm.executeQuery(comandoStatement);
				boolean bandera = true;
	
				while (rs.next()) {
					
					if(bandera) {
						
						matriz[i][4] = dia[rs.getInt(1)];
						bandera = false;
					} else {
						
						matriz[i][4] += ", " + dia[rs.getInt(1)];
					}
				}
				i++;
			}			
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CursosMySQL, getListado()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}
	
	public boolean isExamenCargado(String idCurso, String examen) {

		boolean bandera = false;
		String comandoStatement = "SELECT curso.idCurso FROM `lecsys2.00`.curso "
								+ "JOIN `lecsys2.00`.examenes ON curso.idCurso = examenes.idCurso "
								+ "WHERE (estado = 1 AND curso.idCurso = " + idCurso + " AND tipo = '" + examen + "' AND YEAR(fecha)=YEAR(NOW())) "
								+ "GROUP BY curso.idCurso";

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);

			if(rs.next())
				bandera =true;
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CursosMySQL, isExamenCargado()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return bandera;
	}
	
	public boolean setCurso(String año, String nivel, String idProfesor, int aula, String valorCuota, String horarios[][]) {

		boolean bandera = true;
		int idCurso = 0;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.curso (año, nivel, idProfesor, estado, aula)"
																 + " VALUES (?, ?, ?, 1, ?)");
			stm.setString(1, año);
			stm.setString(2, nivel);
			stm.setString(3, idProfesor);
			stm.setInt(4, aula);
			stm.executeUpdate();
			ResultSet rs = stm.executeQuery("SELECT MAX(idCurso) FROM curso");
			
			if(rs.next())
				idCurso = rs.getInt(1);
			
			stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.valorCuota (idCurso, precio) VALUES (?, ?)");
			stm.setInt(1, idCurso);
			stm.setString(2, valorCuota);
			stm.executeUpdate();
			
			for(int i = 0 ; i < horarios.length ; i++) {
				
				stm = this.conexion.prepareStatement("INSERT INTO diasCursado (día, hora, duración, idCurso) VALUES (?, ?, ?, ?)");
				stm.setString(1, horarios[i][0]);
				stm.setString(2, horarios[i][1]);
				stm.setString(3, horarios[i][2]);
				stm.setInt(4, idCurso);
				stm.executeUpdate();				
			}
		} catch (Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CursosDAO, setCurso()");
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registrar nuevo curso.", "Cursos.", tiempo);
		return bandera;
	}
	
	public boolean setActualizarCurso(String idCurso, String idProfesor, int aula, String valorCuota, int estado, String horarios[][]) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("UPDATE `lecsys2.00`.curso SET idProfesor = ?, estado = ?, aula = ? WHERE idCurso = ?");
			stm.setString(1, idProfesor);
			stm.setInt(2, estado);
			stm.setInt(3, aula);
			stm.setString(4, idCurso);
			stm.executeUpdate();
			stm = this.conexion.prepareStatement("UPDATE `lecsys2.00`.valorCuota SET precio = ? WHERE idCurso = ?");
			stm.setString(1, valorCuota);
			stm.setString(2, idCurso);
			stm.executeUpdate();
			stm = this.conexion.prepareStatement("DELETE FROM `lecsys2.00`.diasCursado WHERE idCurso = ?");
			stm.setString(1, idCurso);
			stm.executeUpdate();
			
			if(estado == 1) {
				
				for(int i = 0 ; i < horarios.length ; i++) {
					
					stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.diasCursado (día, hora, duración, idCurso) VALUES (?, ?, ?, ?)");
					stm.setString(1, horarios[i][0]);
					stm.setString(2, horarios[i][1]);
					stm.setString(3, horarios[i][2]);
					stm.setString(4, idCurso);
					stm.executeUpdate();				
				}
			}
		} catch (Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CursosMySQL, setActualizarCurso()");
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Editar un curso.", "Cursos.", tiempo);
		return bandera;
	}
	
	public String getValorCuota(String idCurso) {
		
		String respuesta = null;
		String comandoStatement = "SELECT precio FROM `lecsys2.00`.curso "
								+ "JOIN `lecsys2.00`.valorCuota ON valorCuota.idCurso = curso.idCurso "
								+ "WHERE curso.idCurso = " + idCurso;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);

			if(rs.next()) {
				
				respuesta = String.format("%.2f", rs.getFloat(1));
			}
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CursosMySQL, getValorCuota()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
}