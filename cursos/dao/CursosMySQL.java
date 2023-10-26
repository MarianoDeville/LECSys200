package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.CursoXtnd;
import modelo.DtosActividad;
import modelo.Horarios;

public class CursosMySQL extends Conexion implements CursosDAO{
	
	@Override
	public CursoXtnd [] getListado(String idCurso) {
		
		CursoXtnd cursos[] = null;
		String cmdStm = "SELECT curso.idCurso, año, nivel, nombre, apellido, precio, idProfesor, aula "
						+ "FROM `lecsys2.00`.curso "
						+ "JOIN `lecsys2.00`.empleados ON curso.idProfesor = empleados.legajo "
						+ "JOIN `lecsys2.00`.persona ON empleados.dni = persona.dni "
						+ "JOIN `lecsys2.00`.valorCuota on curso.idCurso = valorCuota.idCurso "
						+ "WHERE (curso.estado = 1" + (idCurso.equals("")?")":"AND curso.idCurso = "+ idCurso + ")")
						+ "GROUP BY curso.idCurso";

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
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
				cursos[i].setEstado(1);
				i++;
			}
			String dia[] = new String[] {"Lunes", "Martes", "Miercoles", "Jueves", "viernes", "Sábado"};
				
			for(int e = 0; e < cursos.length; e++) {

				cmdStm = "SELECT día, hora, duración FROM `lecsys2.00`.horarios WHERE idCurso = " + cursos[e].getId();
				rs = stm.executeQuery(cmdStm);
				rs.last();	
				cursos[e].setHorarios(new Horarios[rs.getRow()]);
				rs.beforeFirst();
				i=0;
				boolean bandera = true;
				String diasCursado = "";
				
				while(rs.next()) {
				
					cursos[e].getHorarios()[i] = new Horarios();
					cursos[e].getHorarios()[i].setDia(rs.getInt(1));
					cursos[e].getHorarios()[i].setHora(rs.getString(2));
					cursos[e].getHorarios()[i].setDuración(rs.getInt(3));
					
					if(bandera) {
						
						diasCursado = dia[rs.getInt(1)];
						bandera = false;
					} else {
						
						diasCursado += ", " + dia[rs.getInt(1)];
					}
					i++;
				}
				cursos[e].setDiasCursado(diasCursado);
			}
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CursosMySQL, getListado()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return cursos;
	}
	
	@Override
	public boolean [][] getTablaSemanal(int idCurso, int legajo, int aula){

		boolean matrizDiasHorarios[][] = new boolean[6][33];
		String cmdStm = "SELECT día, HOUR(hora), MINUTE(hora), duración "
						+ "FROM `lecsys2.00`.horarios "
						+ "JOIN `lecsys2.00`.curso ON horarios.idCurso = curso.idCurso "
						+ "JOIN `lecsys2.00`.empleados ON horarios.idPertenece = empleados.legajo "
						+ "WHERE (curso.estado = 1 AND ";

		if(idCurso !=0 && legajo == 0 && aula == -1) {

			cmdStm += "curso.idCurso = " + idCurso + ")";
		}else if(idCurso ==0 && legajo != 0 && aula == -1) {
			
			cmdStm += "curso.idProfesor = " + legajo + ")";
		}else if(idCurso ==0 && legajo == 0 && aula != -1) {
			
			cmdStm += "curso.aula = " + aula + ")";
		}else if(idCurso !=0 && legajo == 0 && aula != -1) {
			
			cmdStm += "curso.idCurso = " + idCurso + " AND curso.aula = " + aula + ")";
		}else if(idCurso ==0 && legajo != 0 && aula != -1) {

			cmdStm += "(curso.idProfesor = " + legajo + " OR curso.aula = " + aula + "))";
		}else if(idCurso !=0 && legajo != 0 && aula != -1) {
			
			cmdStm += "(curso.aula = " + aula + " OR (curso.idProfesor = " + legajo + " AND curso.idCurso != " + idCurso + ")))";		
		}

		for(int i = 0 ; i < 6 ; i++) {
			
			for(int e = 0 ; e < 32 ; e++) {
				
				matrizDiasHorarios[i][e] = false;
			}
		}

		try {

			this.conectar();
			Statement stm = this.conexion.createStatement();
			ResultSet rs = stm.executeQuery(cmdStm);
			
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
			CtrlLogErrores.guardarError("CursosMySQL, getTablaSemanal()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return matrizDiasHorarios;
	}
	
	@Override
	public boolean setCurso(CursoXtnd curso) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String pprStm = "INSERT INTO `lecsys2.00`.curso (año, nivel, idProfesor, estado, aula)"
				 		+ " VALUES (?, ?, ?, 1, ?)";
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(pprStm);
			stm.setString(1, curso.getAño());
			stm.setString(2, curso.getNivel());
			stm.setInt(3, curso.getLegajoProfesor());
			stm.setInt(4, curso.getAula());
			stm.executeUpdate();
			pprStm = "SELECT MAX(idCurso) FROM curso";
			ResultSet rs = stm.executeQuery(pprStm);
			
			if(rs.next())
				curso.setId(rs.getInt(1));
			pprStm = "INSERT INTO `lecsys2.00`.valorCuota (idCurso, precio) VALUES (?, ?)";
			stm = this.conexion.prepareStatement(pprStm);
			stm.setInt(1, curso.getId());
			stm.setString(2, curso.getPrecio() + "");
			stm.executeUpdate();
			
			for(int i = 0; i < curso.getHorarios().length; i++) {
			
				pprStm = "INSERT INTO `lecsys2.00`.horarios (día, hora, duración, idPertenece, granularidad, idCurso) "
						 + "VALUES (?, ?, ?, ?, 2, ?)";
				stm = this.conexion.prepareStatement(pprStm);
				stm.setInt(1, curso.getHorarios()[i].getDia());
				stm.setString(2, curso.getHorarios()[i].getHora());
				stm.setInt(3, curso.getHorarios()[i].getDuración());
				stm.setInt(4, curso.getLegajoProfesor());
				stm.setInt(5, curso.getId());
				stm.executeUpdate();				
			}
		} catch (Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CursosMySQL, setCurso()");
			CtrlLogErrores.guardarError(pprStm);
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registrar nuevo curso.", "Cursos.", tiempo);
		return bandera;
	}
	
	@Override
	public boolean update(CursoXtnd curso) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String pprStm = "UPDATE `lecsys2.00`.curso SET idProfesor = ?, estado = ?, aula = ? WHERE idCurso = ?";
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(pprStm);
			stm.setInt(1, curso.getLegajoProfesor());
			stm.setInt(2, curso.getEstado());
			stm.setInt(3, curso.getAula());
			stm.setInt(4, curso.getId());
			stm.executeUpdate();
			pprStm = "UPDATE `lecsys2.00`.valorCuota SET precio = ? WHERE idCurso = ?";
			stm = this.conexion.prepareStatement(pprStm);
			stm.setFloat(1, curso.getPrecio());
			stm.setInt(2, curso.getId());
			stm.executeUpdate();
			pprStm = "DELETE FROM `lecsys2.00`.horarios WHERE idCurso = ?";
			stm = this.conexion.prepareStatement(pprStm);
			stm.setInt(1, curso.getId());
			stm.executeUpdate();
			
			if(curso.getEstado() == 1) {
				
				for(int i = 0; i < curso.getHorarios().length; i++) {
					
					pprStm = "INSERT INTO `lecsys2.00`.horarios (día, hora, duración, idPertenece, granularidad, idCurso) "
							 + "VALUES (?, ?, ?, ?, 2, ?)";
					stm = this.conexion.prepareStatement(pprStm);
					stm.setInt(1, curso.getHorarios()[i].getDia());
					stm.setString(2, curso.getHorarios()[i].getHora());
					stm.setInt(3, curso.getHorarios()[i].getDuración());
					stm.setInt(4, curso.getLegajoProfesor());
					stm.setInt(5, curso.getId());
					stm.executeUpdate();				
				}
			}
		} catch (Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CursosMySQL, update()");
			CtrlLogErrores.guardarError(pprStm);
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Editar un curso.", "Cursos.", tiempo);
		return bandera;
	}
}