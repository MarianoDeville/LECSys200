package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.Alumno;
import modelo.DtosActividad;

public class AlumnoMySQL extends Conexion implements AlumnoDAO {

	@Override
	public Alumno [] getListado(String campo, String valor, boolean estado, String orden, String busqueda) {

		Alumno alumnos[] = null;
		String cmdStm = "SELECT legajo, nombre, apellido, alumnos.dni, dirección, teléfono, email, "
						+ "date_format(fechaNacimiento, '%d/%m/%Y'), idGrupoFamiliar, alumnos.estado , "
						+ "date_format(fechaIngreso, '%d/%m/%Y'), date_format(fechaBaja, '%d/%m/%Y'), "
						+ "alumnos.idCurso, año, nivel, precio, idProfesor "
						+ "FROM `lecsys2.00`.alumnos "
		 				+ "JOIN `lecsys2.00`.persona on alumnos.dni = persona.dni "
		 				+ "JOIN `lecsys2.00`.curso ON curso.idCurso = alumnos.idCurso " 
		 				+ "JOIN `lecsys2.00`.valorCuota ON curso.idCurso = valorCuota.idCurso ";
		
		if(campo.equals("ID")) {
			
			cmdStm += "WHERE (alumnos.estado = " + (estado? "1 ":"0 ") 
					+ " AND legajo = " + valor + ")";
		} else if(campo.equals("Docente")) {
		
			cmdStm += "WHERE (alumnos.estado = " + (estado? "1 ":"0 ") 
					+ " AND idProfesor = " + valor + ")";
		} else if(campo.equals("Curso")) {
			
			cmdStm += "WHERE (alumnos.estado = " + (estado? "1 ":"0 ") 
					+ " AND alumnos.idCurso = " + valor + ")";
		} else if(campo.equals("GF")) {
			
			cmdStm += "WHERE (alumnos.estado = " + (estado? "1 ":"0 ") 
					+ " AND idGrupoFamiliar " + (valor.length()==0? "IS NULL":"= " + valor) 
					+ " AND (apellido LIKE '" + busqueda
					+ "%' OR nombre LIKE '" + busqueda
					+ "%')) ORDER BY " + orden;
		} else {
			
			cmdStm += "WHERE (alumnos.estado = " + (estado? "1 ":"0 ")
					+ "AND (apellido LIKE '" + busqueda
					+ "%' OR nombre LIKE '" + busqueda
					+ "%')) ORDER BY " + orden ;
		}

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			alumnos = new Alumno[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
					
				alumnos[i] = new Alumno();
				alumnos[i].setLegajo(rs.getInt(1));	
				alumnos[i].setNombre(rs.getString(2));
				alumnos[i].setApellido(rs.getString(3));	
				alumnos[i].setDni(rs.getString(4));	
				alumnos[i].setDireccion(rs.getString(5));	
				alumnos[i].setTelefono(rs.getString(6));	
				alumnos[i].setEmail(rs.getString(7));
				alumnos[i].setFechaNacimiento(rs.getString(8));
				alumnos[i].setGrupoFamiliar(rs.getInt(9));
				alumnos[i].setEstado(rs.getInt(10));
				alumnos[i].setFechaIngreso(rs.getString(11));
				alumnos[i].setFechaBaja(rs.getString(12));
				alumnos[i].getCurso().setId(rs.getInt(13));
				alumnos[i].getCurso().setAño(rs.getString(14));
				alumnos[i].getCurso().setNivel(rs.getString(15));
				alumnos[i].getCurso().setPrecio(rs.getFloat(16));
				alumnos[i].getCurso().setLegajoProfesor(rs.getInt(17));
				i++;
			}
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AlumnoMySQL, getListado()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return alumnos;
	}
	
	@Override
	public boolean setNuevo(Alumno alumno) {

		long tiempo = System.currentTimeMillis();
		boolean bandera = true;
		DtosActividad dtosActividad = new DtosActividad();

		try {
			
			this.conectar();
			
			PreparedStatement stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.persona "
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
			stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.alumnos "
					+ "(idCurso, dni, fechaIngreso, estado) "
					+ "VALUES (?, ?, DATE(NOW()), ?)");
			stm.setInt(1, alumno.getIdCurso());
			stm.setString(2, alumno.getDni());
			stm.setInt(3, 0);
			stm.executeUpdate();
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AlumnoMySQL, setNuevo()");
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
			
			PreparedStatement stm = this.conexion.prepareStatement("UPDATE `lecsys2.00`.persona SET "
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
			stm = this.conexion.prepareStatement("UPDATE `lecsys2.00`.alumnos SET idCurso = ?, estado = ?, fechaBaja = ? "
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
					+ "FROM `lecsys2.00`.alumnos "
					+ "JOIN `lecsys2.00`.persona on alumnos.idPersona = persona.idPersona "
					+ "JOIN `lecsys2.00`.curso ON curso.idCurso = alumnos.idCurso "
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
				alumnos[i].getCurso().setNivel(rs.getString(5));
				alumnos[i].getCurso().setAño(rs.getString(6));	
				alumnos[i].setGrupoFamiliar(rs.getInt(7));
				i++;
			}
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AlumnoMySQL, getListadoAlumnos()");
		} finally {
			
			this.cerrar();
		}
		return alumnos;
	}
	

	
	@Override
	public boolean setActualizarFamila(String idFamilia, String idAlumnos[], String estado) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		
		try {

			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("UPDATE `lecsys2.00`.alumnos "
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
			CtrlLogErrores.guardarError("AlumnoMySQL, setActualizarIdFamila()");
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
			stm = this.conexion.prepareStatement("UPDATE `lecsys2.00`.alumnos "
											   + "SET estado = 0, idGrupoFamiliar = NULL "
											   + "WHERE estado = 1");
			stm.executeUpdate();
			stm = this.conexion.prepareStatement("UPDATE `lecsys2.00`.grupoFamiliar "
											   + "SET estado = 0 "
											   + "WHERE estado = 1");
			stm.executeUpdate();
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AlumnoMySQL, setInactivos()");
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Cierre anual. Todos los alumnos pasan a incativos", "Administración", tiempo);
		return bandera;
	}
}