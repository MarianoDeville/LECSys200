package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.Alumno;
import modelo.DtosActividad;

public class AlumnoMySQL extends Conexion implements AlumnoDAO {

	@Override
	public boolean setNuevo(Alumno alumno) {

		long tiempo = System.currentTimeMillis();
		boolean bandera = true;
		DtosActividad dtosActividad = new DtosActividad();

		try {
			
			this.conectar();
			
			PreparedStatement stm = this.conexion.prepareStatement("INSERT INTO persona "
					+ "(nombre, apellido, dni, dirección, fechaNacimiento, teléfono, email)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?)");
			stm.setString(1, alumno.getNombre());
			stm.setString(2, alumno.getApellido());
			stm.setString(3, alumno.getDni());
			stm.setString(4, alumno.getDireccion());
			stm.setString(5, alumno.getFechaNacimiento());
			stm.setString(6, alumno.getTelefono());
			stm.setString(7, alumno.getEmail());
			stm.executeUpdate();
			stm = this.conexion.prepareStatement("INSERT INTO alumnos "
					+ "(idCurso, dni, fechaIngreso, estado) "
					+ "VALUES (?, ?, DATE(NOW()), ?)");
			stm.setInt(1, alumno.getIdCurso());
			stm.setString(2, alumno.getDni());
			stm.setInt(3, 0);
			stm.executeUpdate();
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AlumnosDAO, setAlumno()");
			bandera = false;
		} finally {
			
			this.cerrar();
		}

		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registro nuevo alumno.", "Alumnos", tiempo);
		return bandera;
	}
	
	@Override
	public boolean update(Alumno alumno) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		
		try {

			this.conectar();
			
			PreparedStatement stm = this.conexion.prepareStatement("UPDATE persona SET "
					 + "nombre = ?, apellido = ?, dni = ?, dirección = ?, fechaNacimiento = ?, teléfono = ?, email = ? "
					 + "WHERE idPersona = ?");
			stm.setString(1, alumno.getNombre());
			stm.setString(2, alumno.getApellido());
			stm.setString(3, alumno.getDni());
			stm.setString(4, alumno.getDireccion());
			stm.setString(5, alumno.getFechaNacimiento());
			stm.setString(6, alumno.getTelefono());
			stm.setString(7, alumno.getEmail());
			stm.executeUpdate();
			stm = this.conexion.prepareStatement("UPDATE alumnos SET idCurso = ?, estado = ?, fechaBaja = ? "
					+ "WHERE legajo = ?");
			stm.setInt(1, alumno.getIdCurso());
			stm.setInt(2, alumno.getEstado());
			stm.setString(3, alumno.getFechaBaja());
			stm.setInt(4, alumno.getLegajo());
			stm.executeUpdate();
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AlumnosDAO, setActualizarAlumno()");
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Actualizacion de datos de alumno.", "Alumnos", tiempo);
		return bandera;
	}
	
	@Override
	public Alumno [] getListado( boolean estado, int grupo, String busqueda) {

		Alumno alumnos[]=null;
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("SELECT legajo, nombre, apellido, dirección, nivel, año, idGrupoFamiliar "
					+ "FROM alumnos JOIN persona on alumnos.idPersona = persona.idPersona "
					+ "JOIN curso ON curso.idCurso = alumnos.idCurso "
					+ "WHERE (alumnos.estado = ? AND idGrupoFamiliar != ? AND (apellido LIKE '?%' OR nombre LIKE '?%')) "
					+ "ORDER BY idAlumno",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stm.setInt(1, (estado? 1:0));
			stm.setInt(2, grupo);
			stm.setString(3, busqueda);
			stm.setString(4, busqueda);
			ResultSet rs = stm.executeQuery();
			rs.last();	
			alumnos = new Alumno[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
					
				alumnos[i].setLegajo(rs.getInt(1));
				alumnos[i].setNombre(rs.getString(2));
				alumnos[i].setApellido(rs.getString(3));
				alumnos[i].setDireccion(rs.getString(4));
				alumnos[i].setNivel(rs.getString(5));
				alumnos[i].setAño(rs.getString(6));	
				alumnos[i].setGrupoFamiliar(rs.getInt(7));
				i++;
			}
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AlumnosDAO, getListadoAlumnos()");
		} finally {
			
			this.cerrar();
		}
		return alumnos;
	}
	
	@Override
	public String [][] getListado(String campo, String valor, boolean estado, String orden, String busqueda) {

		String matriz[][]=null;
		String armoWhere = null;
		
		if(campo.equals("ID")) {
			
			armoWhere = "WHERE (alumnos.estado = " + (estado? "1 ":"0 ") 
					  + " AND idAlumno = " + valor + ")";
		} else if(campo.equals("Docente")) {
		
			armoWhere = "WHERE (alumnos.estado = " + (estado? "1 ":"0 ") 
					  + " AND idProfesor = " + valor + ")";
		} else if(campo.equals("Curso")) {
			
			armoWhere = "WHERE (alumnos.estado = " + (estado? "1 ":"0 ") 
					  + " AND alumnos.idCurso = " + valor + ")";
		} else if(campo.equals("GF")) {
			
			armoWhere = "WHERE (alumnos.estado = " + (estado? "1 ":"0 ") 
					  + " AND idGrupoFamiliar " + (valor.length()==0? "IS NULL":"= " + valor) 
					  + " AND (apellido LIKE '" + busqueda
					  + "%' OR nombre LIKE '" + busqueda
					  + "%')) ORDER BY " + orden;
		} else {
			
			armoWhere = "WHERE (alumnos.estado = " + (estado? "1 ":"0 ")
					  + "AND (apellido LIKE '" + busqueda
					  + "%' OR nombre LIKE '" + busqueda
					  + "%')) ORDER BY " + orden ;
		}
		
		String comandoStatement = "SELECT idAlumno, nombre, apellido, dni, dirección, teléfono, email, nivel, año, "
								+ "idGrupoFamiliar, alumnos.estado, alumnos.idCurso, alumnos.estado, fechaNacimiento , "
								+ "date_format(fechaIngreso, '%d/%m/%Y'), año, nivel, precio "
								+ "FROM alumnos "
				 				+ "JOIN persona on alumnos.idPersona = persona.idPersona "
				 				+ "JOIN curso ON curso.idCurso = alumnos.idCurso " 
				 				+ "JOIN valorCuota ON curso.idCurso = valorCuota.idCurso "
						 		+ armoWhere;

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);
			rs.last();	
			matriz = new String[rs.getRow()][17];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
					
				matriz[i][0] = rs.getString(1);	
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);	
				matriz[i][3] = rs.getString(4);	
				matriz[i][4] = rs.getString(5);	
				matriz[i][5] = rs.getString(6);	
				matriz[i][6] = rs.getString(7);	
				matriz[i][7] = rs.getString(8) + " " + rs.getString(9);
				matriz[i][8] = rs.getString(10);
				matriz[i][9] = rs.getString(11);
				matriz[i][10] = rs.getString(12);
				matriz[i][11] = (rs.getInt(13) == 1)? "Activo": "Inactivo";
				matriz[i][12] = rs.getString(14);
				matriz[i][13] = rs.getString(15);
				matriz[i][14] = rs.getString(16) + " " + rs.getString(17);
				matriz[i][15] = rs.getString(18);
				i++;
			}
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AlumnosDAO, getAlumnos()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return matriz;
	}
	
	@Override
	public boolean setActualizarFamila(String idFamilia, String idAlumnos[], String estado) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		
		try {

			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("UPDATE alumnos "
																 + "SET estado = ?, idGrupoFamiliar = ? "
																 + "WHERE idAlumno = ?");
			stm.setString(1, estado);
			stm.setString(2, idFamilia);
			
			for(int i = 0; i < idAlumnos.length; i++) {
				
				stm.setString(3, idAlumnos[i]);
				stm.executeUpdate();
			}
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AlumnosDAO, setActualizarIdFamila()");
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Actualización del grupo familiar de un grupo de alumnos.", "Administración", tiempo);
		return bandera;
	}

	@Override
	public boolean resetEstado() {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		
		try {

			this.conectar();
			PreparedStatement stm;
			stm = this.conexion.prepareStatement("UPDATE alumnos "
											   + "SET estado = 0, idGrupoFamiliar = NULL "
											   + "WHERE estado = 1");
			stm.executeUpdate();
			stm = this.conexion.prepareStatement("UPDATE grupoFamiliar "
											   + "SET estado = 0 "
											   + "WHERE estado = 1");
			stm.executeUpdate();
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AlumnosDAO, setInactivos()");
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Cierre anual. Todos los alumnos pasan a incativos", "Administración", tiempo);
		return bandera;
	}
}