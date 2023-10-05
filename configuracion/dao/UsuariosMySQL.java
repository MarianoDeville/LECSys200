package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;
import modelo.Usuario;

public class UsuariosMySQL extends Conexion implements UsuariosDAO{

	@Override
	public boolean setUsuario(Usuario usr) {

		boolean bandera = true;
		String renovarContraseña;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		OperadorSistema accseso = new OperadorSistema();
		renovarContraseña = accseso.getFichaEmpleado().equals("0")?"1":"0";
		String ppstm = "INSERT INTO `lecsys2.00`.usuarios "
					 + "(dni, nombre, contraseña, nivelAcceso, estado, cambioContraseña) "
					 + "VALUES (?, ?, SHA(?), ?, 1, ?)";

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(ppstm);
			stm.setInt(1, usr.getDni());
			stm.setString(2, usr.getUsrName());
			stm.setString(3, usr.getContraseña());
			stm.setString(4, usr.getNivelAcceso());
			stm.setString(5, renovarContraseña);
			stm.executeUpdate();
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("UsuariosMySQL, setUsuario()");
			CtrlLogErrores.guardarError(ppstm);
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registro nuevo usuario del sistema.", "Configuración", tiempo);
		return bandera;
	}
	
	@Override
	public boolean update(Usuario usr) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
	
		try {
			
			this.conectar();
			PreparedStatement stm;
			
			if(usr.getContraseña().length() > 2) {
				
				stm = this.conexion.prepareStatement("UPDATE `lecsys2.00`.usuarios SET contraseña = SHA(?), estado = ?, "
													+ "nivelAcceso = ?, cambioContraseña = 0 WHERE nombre = ?");
				stm.setString(1, usr.getContraseña());
				stm.setInt(2, usr.getEstado());
				stm.setString(3, usr.getNivelAcceso());
				stm.setString(4, usr.getUsrName());				
			} else {
				
				stm = this.conexion.prepareStatement("UPDATE `lecsys2.00`.usuarios SET estado = ?, nivelAcceso = ?, "
													+ "cambioContraseña = 0 WHERE nombre = ?");
				stm.setInt(1, usr.getEstado());
				stm.setString(2, usr.getNivelAcceso());
				stm.setString(3, usr.getUsrName());
			}
			stm.executeUpdate();
		} catch (Exception e) {

			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("UsuariosMySQL, setActualizarUsuario()");
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Actualizacion de datos de usuarios.", "Configuración", tiempo);
		return bandera;
	}

	@Override
	public void updateTiempoPass() {

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("UPDATE `lecsys2.00`.usuarios "
																 + "SET cambioContraseña = cambioContraseña - 1 "
																 + "WHERE estado = 1");
			stm.executeUpdate();
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("UsuariosMySQL, updateTiempoPass()");
		} finally {
			
			this.cerrar();
		}
	}

	@Override
	public Usuario [] getListado(String usuario) {

		Usuario listaUsr[] = null;
		String cmdStatement = "SELECT idUsuario, usuarios.nombre, persona.nombre, apellido, nivelAcceso, usuarios.dni "
							+ "FROM `lecsys2.00`.usuarios "
							+ "JOIN `lecsys2.00`.persona on usuarios.dni = persona.dni "
							+ "WHERE (usuarios.estado = 1 ";
				
		if(usuario.equals(""))
			cmdStatement += ")";
		else
			cmdStatement += "AND usuarios.nombre = '" + usuario + "')";

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStatement);
			rs.last();	
			listaUsr = new Usuario[rs.getRow()];
			rs.beforeFirst();
			int i=0;

			while (rs.next()) {

				listaUsr[i] = new Usuario();
				listaUsr[i].setId(rs.getInt(1));	
				listaUsr[i].setUsrName(rs.getString(2));	
				listaUsr[i].setNombre(rs.getString(3) + " " + rs.getString(4));	
				listaUsr[i].setNivelAcceso(rs.getString(5));
				listaUsr[i].setDni(rs.getInt(6));
				i++;
			}
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("UsuariosMySQL, getListado()");
		} finally {
			
			this.cerrar();
		}
		return listaUsr;
	}

	@Override
	public boolean isNombreUsado(String nombre) {

		boolean bandera = false;

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("SELECT idUsuario FROM `lecsys2.00`.usuarios "
																 + "WHERE (nombre = ? AND estado = 1)");
			stm.setString(1, nombre);
			ResultSet rs = stm.executeQuery();

			if(rs.next())
				bandera = true;
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("UsuariosMySQL, isNombreUsado()");
		} finally {
			
			this.cerrar();
		}
		return bandera;
	}
}