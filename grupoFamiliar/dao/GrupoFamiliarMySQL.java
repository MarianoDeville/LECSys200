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

		GrupoFamiliar familia[] = null;
		String armoWhere = null;
		
		if(campo.equals("ID")) {
			
			armoWhere = "WHERE (grupoFamiliar.idGrupoFamiliar = " + valor + " AND deuda " + (sinDeuda? "= 0":"> 0") + ") ";
		} else if(campo.equals("IDALUMNO")) {
			
			armoWhere = "WHERE (idAlumno = " + valor + " AND deuda " + (sinDeuda? "= 0":"> 0") + ") ";
		} else if(campo.equals("ESTADO")) {
			
			armoWhere = "WHERE (grupoFamiliar.estado = " + valor + " AND deuda " + (sinDeuda? "= 0":"> 0") + ") ";
		} else {
		
			armoWhere = "WHERE (grupoFamiliar.estado = 1 AND deuda " + (sinDeuda? "= 0":"> 0") + " AND nombreFamilia LIKE '%" + campoBusqueda + "%') ";
		}
		
		String cmdStm = "SELECT grupoFamiliar.idGrupoFamiliar, nombreFamilia, integrantes, deuda, SUM(precio), descuento , grupoFamiliar.email "
						+ "FROM grupoFamiliar "
						+ "JOIN alumnos ON alumnos.idGrupoFamiliar = grupoFamiliar.idGrupoFamiliar "
						+ "JOIN curso ON alumnos.idCurso = curso.idCurso "
						+ "JOIN valorCuota ON curso.idCurso = valorCuota.idCurso " 
						+ armoWhere 
						+ "GROUP BY grupoFamiliar.idGrupoFamiliar "
						+ "ORDER BY grupoFamiliar.nombreFamilia";
		
		try {
		
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			familia = new GrupoFamiliar[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {
					
				familia[i] = new GrupoFamiliar();
				familia[i].setId(rs.getInt(1));
				familia[i].setNombre(rs.getString(2));
				familia[i].setCantIntegrantes(rs.getInt(3));
				familia[i].setDeuda(rs.getInt(4));
				familia[i].setSumaPrecioCuotas(rs.getFloat(5));
				familia[i].setDescuento(rs.getInt(6));
				familia[i].setEmail(rs.getString(7));
				i++;
			}
			
			
			
			
			
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("GrupoFamiliarMySQL, getListado()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return familia;
	}
	
	@Override
	public GrupoFamiliar getGrupoFamiliar(int id) {

		GrupoFamiliar familia = new GrupoFamiliar();
		String cmdStm = "SELECT nombreFamilia, integrantes, deuda, estado, descuento, email, "
						+ "legajo, apellido, persona.nombre, dirección, alumno.dni"
						+ "año, nivel, precio"
						+ "FROM grupoFamiliar " 
						+ "JOIN alumnos ON alumnos.idGrupoFamiliar = grupoFamiliar.idGrupoFamiliar "
						+ "JOIN persona ON persona.dni = alumnos.dni "
						+ "JOIN valorCuota ON alumnos.idCurso = valorCuota.idCurso " 
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
				familia.setCantIntegrantes(rs.getInt(2));
				familia.setDeuda(rs.getInt(3));
				familia.setDescuento(rs.getInt(4));
				familia.setDescuento(rs.getInt(5));
				familia.setEmail(rs.getString(6));
				familia.getIntegrantes()[i] = new Alumno(); 
				familia.getIntegrantes()[i].setLegajo(rs.getInt(7));
				familia.getIntegrantes()[i].setApellido(rs.getString(8));
				familia.getIntegrantes()[i].setNombre(rs.getString(9));
				familia.getIntegrantes()[i].setDireccion(rs.getString(10));
				familia.getIntegrantes()[i].setDni(rs.getString(11));
				familia.getIntegrantes()[i].setCurso(new CursoXtnd());
				familia.getIntegrantes()[i].getCurso().setAño(rs.getString(12));
				familia.getIntegrantes()[i].getCurso().setNivel(rs.getString(13));
				familia.getIntegrantes()[i].getCurso().setPrecio(rs.getFloat(14));
				i++;
			}
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
	public boolean setGrupoFamiliar(GrupoFamiliar familia) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String pprStm = "INSERT INTO grupoFamiliar (nombreFamilia, integrantes, deuda, estado, descuento, email) "
				 		+ "VALUES (?, ?, 0, 1, ?, ?)";
		int id = 0;
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(pprStm);
			stm.setString(1, familia.getNombre());
			stm.setInt(2, familia.getCantIntegrantes());
			stm.setInt(3, familia.getDeuda());
			stm.setInt(4, familia.getDescuento());
			stm.setString(5, familia.getEmail());
			stm.executeUpdate();
			pprStm = "SELECT MAX(idGrupoFamiliar) FROM grupoFamiliar";
			ResultSet rs = stm.executeQuery(pprStm);
			
			if(rs.next())
				id = rs.getInt(1);
			familia.setId(id);
			pprStm = "UPDATE alumnos SET idGrupoFamiliar = ?, estado = 1 WHERE (legajo = ?)";
			
			for(int i = 0 ; i < familia.getIntegrantes().length ; i++) {
				
				
				stm = this.conexion.prepareStatement(pprStm);
				stm.setInt(1, id);
				stm.setInt(2, familia.getIntegrantes()[i].getLegajo());
				stm.executeUpdate();
			}
			
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("GrupoFamiliarMySQL, setGrupoFamiliar()");
			CtrlLogErrores.guardarError(pprStm);
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registro nuevo grupo familiar.", "Administración", tiempo);
		return bandera;
	}
	
	@Override
	public boolean update(GrupoFamiliar familia) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();

		try {

			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("UPDATE grupoFamiliar "
																 + "SET nombreFamilia = ?, integrantes = ?, estado = ?, descuento = ?, email = ? "
																 + "WHERE idGrupoFamiliar = ?");
			stm.setString(1, familia.getNombre());
			stm.setInt(2, familia.getCantIntegrantes());
			stm.setInt(3, familia.getEstado());
			stm.setFloat(4, familia.getDescuento());
			stm.setString(5, familia.getEmail());
			stm.setInt(6, familia.getId());
			stm.executeUpdate();
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("GrupoFamiliarMySQL, update()");
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
		String comandoStatement = "SELECT deuda FROM grupoFamiliar " + where;
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(comandoStatement);
			ResultSet rs = stm.executeQuery();
			
			if(rs.next()) 
				nuevaDeuda = rs.getInt(1) + modificarDeuda;

			comandoStatement = "UPDATE grupoFamiliar SET deuda = ? " + where;
			stm = this.conexion.prepareStatement(comandoStatement);
			stm.setInt(1, nuevaDeuda);
			stm.executeUpdate();
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("GrupoFamiliarMySQL, setActualizarDeuda()");
			CtrlLogErrores.guardarError(comandoStatement);
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Actualización de la deuda del grupo familiar.", "Adminnistración", tiempo);
		return bandera;
	}
	
	@Override
	public boolean eliminarIntegrante(int idGrupo) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String pprStm = "SELECT integrantes FROM grupoFamiliar WHERE idGrupoFamiliar = ?";
		int cant = 0;

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(pprStm);
			stm.setInt(1, idGrupo);			
			ResultSet rs = stm.executeQuery();
			
			if(rs.next())
				cant = rs.getInt(1) - 1;

			if(cant > 0) {
				
				pprStm = "UPDATE grupoFamiliar SET integrantes = ? WHERE idGrupoFamiliar = ?";
			} else {
				
				pprStm = "UPDATE grupoFamiliar SET integrantes = ?, estado = 0 WHERE idGrupoFamiliar = ?";
			}
			stm = this.conexion.prepareStatement(pprStm);
			stm.setInt(1, cant);
			stm.setInt(2, idGrupo);
			stm.executeUpdate();
			
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("GrupoFamiliarMySQL, setEliminarIntegrante()");
			CtrlLogErrores.guardarError(pprStm);
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Actualización de la cantidad de integrantes del grupo familiar.", "Alumnos", tiempo);
		return bandera;
	}

	@Override
	public boolean isNombreFamilia(String nombre) {
		
		boolean bandera = false;
		String cmdStm = "SELECT idGrupoFamiliar FROM grupoFamiliar WHERE nombreFamilia = '" + nombre + "'";
		
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