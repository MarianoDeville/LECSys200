package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.Curso;
import modelo.CursoXtnd;
import modelo.DtosActividad;

public class CursosMySQL extends Conexion implements CursosDAO{
	
	@Override
	public CursoXtnd [] getListado(String idCurso) {
		
		CursoXtnd cursos[] = null;
		String comandoStatement = "SELECT curso.idCurso, año, nivel, nombre, apellido, precio, idProfesor, aula "
								+ "FROM `lecsys2.00`.curso "
								+ "LEFT JOIN `lecsys2.00`.empleados ON curso.idProfesor = empleados.legajo "
								+ "LEFT JOIN `lecsys2.00`.persona ON empleados.dni = persona.dni "
								+ "LEFT JOIN `lecsys2.00`.valorCuota on curso.idCurso = valorCuota.idCurso "
								+ "WHERE (curso.estado = 1" + (idCurso.equals("")?")":"AND curso.idCurso = "+ idCurso + ")")
								+ "GROUP BY curso.idCurso";

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);
			rs.last();	
			cursos = new CursoXtnd[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
				
				cursos[i] = new CursoXtnd();
				cursos[i].setId(rs.getInt(1));
				cursos[i].setAño(rs.getString(2));
				cursos[i].setNivel(rs.getString(3));
				cursos[i].setNombreProfesor(rs.getString(4) + " " + rs.getString(5));
				cursos[i].setPrecio(rs.getFloat(6));
				cursos[i].setLegajoProfesor(rs.getInt(7));
				cursos[i].setAula(rs.getInt(8));
				i++;
			}
			
			String dia[] = new String[] {"Lunes", "Martes", "Miercoles", "Jueves", "viernes", "Sábado"};
			i = 0;		
			
			while(i < cursos.length) {		
				
				comandoStatement = "SELECT día FROM `lecsys2.00`.curso "
						+ "JOIN `lecsys2.00`.empleados ON curso.idProfesor = empleados.legajo "
						+ "JOIN `lecsys2.00`.horarios ON empleados.legajo = horarios.idPertenece "
						+ "WHERE idCurso = " + cursos[i].getId() ;
				rs = stm.executeQuery(comandoStatement);
				boolean bandera = true;
				String diasCursado = "";
				while (rs.next()) {
					
					if(bandera) {
						
						diasCursado = dia[rs.getInt(1)];
						bandera = false;
					} else {
						
						diasCursado += ", " + dia[rs.getInt(1)];
					}
				}
				cursos[i].setDiasCursado(diasCursado);
				i++;
			}			
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CursosMySQL, getListado()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return cursos;
	}
	
	
	
	
	
	
	
	
	@Override
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
	

	
	@Override
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
	
	@Override
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
	
	@Override
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
	
	@Override
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
	
	@Override
	public boolean [][] getTablaSemanal(int idCurso, int legajo, int aula){

		boolean matrizDiasHorarios[][] = new boolean[6][33];
int granularidad = 2;
		
		String comandoStatement = "SELECT día, HOUR(hora), MINUTE(hora), duración "
								+ "FROM `lecsys2.00`.horarios "
								+ "JOIN curso ON horarios.idPertenece = curso.idCurso "
								+ "JOIN empleados ON horarios.idPertenece = empleados.legajo "
								+ "WHERE (curso.estado = 1 AND ";

		if(idCurso !=0 && legajo == 0 && aula == -1) {

			comandoStatement += "curso.idCurso = " + idCurso + ")";
		}else if(idCurso ==0 && legajo != 0 && aula == -1) {
			
			comandoStatement += "curso.idProfesor = " + legajo + ")";
		}else if(idCurso ==0 && legajo == 0 && aula != -1) {
			
			comandoStatement += "curso.aula = " + aula + ")";
		}else if(idCurso !=0 && legajo == 0 && aula != -1) {
			
			comandoStatement += "curso.idCurso = " + idCurso + " AND curso.aula = " + aula + ")";
		}else if(idCurso ==0 && legajo != 0 && aula != -1) {

			comandoStatement += "(curso.idProfesor = " + legajo + " OR curso.aula = " + aula + "))";
		}else if(idCurso !=0 && legajo != 0 && aula != -1) {
			
			comandoStatement += "(curso.aula = " + aula + " OR (curso.idProfesor = " + legajo + " AND curso.idCurso != " + idCurso + ")))";		
		}

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
			CtrlLogErrores.guardarError("CronogramaMySQL, getTablaSemanal()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matrizDiasHorarios;
	}
}