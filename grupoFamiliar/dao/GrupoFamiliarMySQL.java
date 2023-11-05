package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.Alumno;
import modelo.CursoXtnd;
import modelo.DtosActividad;
import modelo.GrupoFamiliar;

public class GrupoFamiliarMySQL extends Conexion implements GrupoFamiliarDAO{

	@Override
	public GrupoFamiliar [] getListado(String campo, String valor, boolean sinDeuda, String campoBusqueda) {

		GrupoFamiliar familias[] = null;
		String armoWhere = null;
		
		if(campo.equals("ID")) {
			
			armoWhere = "WHERE (grupoFamiliar.idGrupoFamiliar = " + valor + " AND deuda " + (sinDeuda? "< 1":"> 0") + ") ";
		} else if(campo.equals("IDALUMNO")) {
			
			armoWhere = "WHERE (idAlumno = " + valor + " AND deuda " + (sinDeuda? "< 1":"> 0") + ") ";
		} else if(campo.equals("ESTADO")) {
			
			armoWhere = "WHERE (grupoFamiliar.estado = " + valor + " AND deuda " + (sinDeuda? "< 1":"> 0") + " AND nombreFamilia LIKE '%" + campoBusqueda + "%') ";
		} else {
		
			armoWhere = "WHERE (grupoFamiliar.estado = 1 AND deuda " + (sinDeuda? "< 1":"> 0") + " AND nombreFamilia LIKE '%" + campoBusqueda + "%') ";
		}
		String cmdStm = "SELECT grupoFamiliar.idGrupoFamiliar, nombreFamilia, deuda, SUM(precio), descuento, grupoFamiliar.email, grupoFamiliar.estado "
						+ "FROM `lecsys2.00`.grupoFamiliar "
						+ "JOIN `lecsys2.00`.alumnos ON alumnos.idGrupoFamiliar = grupoFamiliar.idGrupoFamiliar "
						+ "JOIN `lecsys2.00`.curso ON alumnos.idCurso = curso.idCurso "
						+ "JOIN `lecsys2.00`.valorCuota ON curso.idCurso = valorCuota.idCurso " 
						+ armoWhere 
						+ "GROUP BY grupoFamiliar.idGrupoFamiliar "
						+ "ORDER BY grupoFamiliar.nombreFamilia";
		
		try {

			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			familias = new GrupoFamiliar[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
					
				familias[i] = new GrupoFamiliar();
				familias[i].setId(rs.getInt(1));
				familias[i].setNombre(rs.getString(2));
				familias[i].setDeuda(rs.getInt(3));
				familias[i].setSumaPrecioCuotas(rs.getFloat(4));
				familias[i].setDescuento(rs.getInt(5));
				familias[i].setEmail(rs.getString(6));
				familias[i].setEstado(rs.getInt(7));
				i++;
			}
			for(int e = 0; e < familias.length; e++) {
			
				cmdStm = "SELECT legajo, apellido, nombre, dirección, alumnos.dni, año, nivel, precio "
						+ "FROM `lecsys2.00`.alumnos "
						+ "JOIN `lecsys2.00`.persona ON persona.dni = alumnos.dni "
						+ "JOIN `lecsys2.00`.curso ON alumnos.idCurso = curso.idCurso "
						+ "JOIN `lecsys2.00`.valorCuota ON alumnos.idCurso = valorCuota.idCurso " 
						+ "WHERE alumnos.idGrupoFamiliar = " + familias[e].getId();
				rs = stm.executeQuery(cmdStm);
				rs.last();	
				familias[e].setIntegrantes(new Alumno[rs.getRow()]);
				rs.beforeFirst();
				i=0;
				
				while (rs.next()) {
	
					familias[e].getIntegrantes()[i] = new Alumno(); 
					familias[e].getIntegrantes()[i].setLegajo(rs.getInt(1));
					familias[e].getIntegrantes()[i].setApellido(rs.getString(2));
					familias[e].getIntegrantes()[i].setNombre(rs.getString(3));
					familias[e].getIntegrantes()[i].setDireccion(rs.getString(4));
					familias[e].getIntegrantes()[i].setDni(rs.getString(5));
					familias[e].getIntegrantes()[i].setCurso(new CursoXtnd());
					familias[e].getIntegrantes()[i].getCurso().setAño(rs.getString(6));
					familias[e].getIntegrantes()[i].getCurso().setNivel(rs.getString(7));
					familias[e].getIntegrantes()[i].getCurso().setPrecio(rs.getFloat(8));
					i++;
				}
				familias[e].setCantIntegrantes(i);
			}
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("GrupoFamiliarMySQL, getListado()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return familias;
	}
	
	@Override
	public GrupoFamiliar getGrupoFamiliar(int id) {

		GrupoFamiliar familia = new GrupoFamiliar();
		String cmdStm = "SELECT nombreFamilia, deuda, grupoFamiliar.estado, descuento, grupoFamiliar.email, legajo, apellido, "
						+ "persona.nombre, dirección, alumnos.dni, año, nivel, precio "
						+ "FROM `lecsys2.00`.grupoFamiliar " 
						+ "JOIN `lecsys2.00`.alumnos ON alumnos.idGrupoFamiliar = grupoFamiliar.idGrupoFamiliar "
						+ "JOIN `lecsys2.00`.persona ON persona.dni = alumnos.dni "
						+ "JOIN `lecsys2.00`.curso ON alumnos.idCurso = curso.idCurso "
						+ "JOIN `lecsys2.00`.valorCuota ON alumnos.idCurso = valorCuota.idCurso " 
						+ "WHERE grupoFamiliar.idGrupoFamiliar = " + id;
				  
		try {
		
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			familia.setIntegrantes(new Alumno[rs.getRow()]);
			rs.beforeFirst();
			int i=0;
			
			while (rs.next()) {

				familia.setId(id);
				familia.setNombre(rs.getString(1));
				familia.setDeuda(rs.getInt(2));
				familia.setEstado(rs.getInt(3));
				familia.setDescuento(rs.getInt(4));
				familia.setEmail(rs.getString(5));
				familia.getIntegrantes()[i] = new Alumno();
				familia.getIntegrantes()[i].setLegajo(rs.getInt(6));
				familia.getIntegrantes()[i].setApellido(rs.getString(7));
				familia.getIntegrantes()[i].setNombre(rs.getString(8));
				familia.getIntegrantes()[i].setDireccion(rs.getString(9));
				familia.getIntegrantes()[i].setDni(rs.getString(10));
				familia.getIntegrantes()[i].setCurso(new CursoXtnd());
				familia.getIntegrantes()[i].getCurso().setAño(rs.getString(11));
				familia.getIntegrantes()[i].getCurso().setNivel(rs.getString(12));
				familia.getIntegrantes()[i].getCurso().setPrecio(rs.getFloat(13));
				i++;
			}
			familia.setCantIntegrantes(i);
		}catch (Exception e) {
		
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("GrupoFamiliarMySQL, getGrupoFamiliar()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
		
			this.cerrar();
		}
		return familia;
	}

	@Override
	public int create(GrupoFamiliar familia) {

		int id = 0;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String pprStm = "INSERT INTO `lecsys2.00`.grupoFamiliar (nombreFamilia, deuda, estado, descuento, email) "
				 		+ "VALUES (?, 0, 1, ?, ?)";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(pprStm);
			stm.setString(1, familia.getNombre());
			stm.setInt(2, familia.getDescuento());
			stm.setString(3, familia.getEmail());
			stm.executeUpdate();
			pprStm = "SELECT MAX(idGrupoFamiliar) FROM `lecsys2.00`.grupoFamiliar";
			ResultSet rs = stm.executeQuery(pprStm);
			
			if(rs.next())
				familia.setId(rs.getInt(1));
			pprStm = "UPDATE `lecsys2.00`.alumnos SET idGrupoFamiliar = ?, estado = 1 WHERE (legajo = ?)";
			
			for(int i = 0 ; i < familia.getIntegrantes().length ; i++) {
				
				stm = this.conexion.prepareStatement(pprStm);
				stm.setInt(1, familia.getId());
				stm.setInt(2, familia.getIntegrantes()[i].getLegajo());
				stm.executeUpdate();
			}
			id = familia.getId();
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("GrupoFamiliarMySQL, create()");
			CtrlLogErrores.guardarError(pprStm);
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registro nuevo grupo familiar.", "Administración", tiempo);
		return id;
	}
	
	@Override
	public boolean update(GrupoFamiliar familia) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String pprStm = "UPDATE `lecsys2.00`.grupoFamiliar "
					 + "SET nombreFamilia = ?, estado = ?, descuento = ?, email = ? "
					 + "WHERE idGrupoFamiliar = ?";

		try {

			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(pprStm);
			stm.setString(1, familia.getNombre());
			stm.setInt(2, familia.getEstado());
			stm.setInt(3, familia.getDescuento());
			stm.setString(4, familia.getEmail());
			stm.setInt(5, familia.getId());
			stm.executeUpdate();
			pprStm = "UPDATE `lecsys2.00`.alumnos SET estado = 0, idGrupoFamiliar = NULL WHERE (estado = 1 AND idGrupoFamiliar = ?)";
			stm = this.conexion.prepareStatement(pprStm);
			stm.setInt(1, familia.getId());
			stm.executeUpdate();
			pprStm = "UPDATE `lecsys2.00`.alumnos SET estado = 1, idGrupoFamiliar = ? WHERE legajo = ?";
			stm = this.conexion.prepareStatement(pprStm);
			stm.setInt(1, familia.getId());				
			
			for(int i = 0; i < familia.getIntegrantes().length; i++) {
			
				stm.setInt(2, familia.getIntegrantes()[i].getLegajo());
				stm.executeUpdate();
			}
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("GrupoFamiliarMySQL, update()");
			CtrlLogErrores.guardarError(pprStm);
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Actualización datos grupo familiar.", "Administración", tiempo);
		return bandera;
	}
	
	@Override
	public boolean updateDeuda(int idGrupo, int modificarDeuda) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		int nuevaDeuda = 0;
		DtosActividad dtosActividad = new DtosActividad();
		String where = idGrupo == 0? "WHERE estado = '1'" : "WHERE idGrupoFamiliar = '" + idGrupo + "'";
		String cmdStm = "SELECT deuda FROM grupoFamiliar ";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			ResultSet rs = stm.executeQuery();
			
			if(rs.next()) 
				nuevaDeuda = rs.getInt(1) + modificarDeuda;
			cmdStm = "UPDATE `lecsys2.00`.grupoFamiliar SET deuda = ? " + where;
			stm = this.conexion.prepareStatement(cmdStm);
			stm.setInt(1, nuevaDeuda);
			stm.executeUpdate();
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("GrupoFamiliarMySQL, setActualizarDeuda()");
			CtrlLogErrores.guardarError(cmdStm);
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Actualización de la deuda del grupo familiar.", "Adminnistración", tiempo);
		return bandera;
	}

	@Override
	public boolean isNombreFamilia(String nombre) {
		
		boolean bandera = false;
		String cmdStm = "SELECT idGrupoFamiliar FROM `lecsys2.00`.grupoFamiliar WHERE nombreFamilia = '" + nombre + "'";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);

			if(rs.next())
				bandera = true;
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("GrupoFamiliarMySQL, isNombreFamilia()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return bandera;
	}
}