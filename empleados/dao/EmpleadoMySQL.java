package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;
import modelo.Empleado;

public class EmpleadoMySQL extends Conexion implements EmpleadoDAO{
	
	@Override
	public boolean setNuevo(Empleado empleado) {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();

		if(empleado.getSector().equals("Docente"))
			empleado.setEstado(0);
		else
			empleado.setEstado(1);
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.persona "
					+ "(nombre, apellido, dni, direcci�n, fechaNacimiento, tel�fono, email)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?)");
			stm.setString(1, empleado.getNombre());
			stm.setString(2, empleado.getApellido());
			stm.setString(3, empleado.getDni());
			stm.setString(4, empleado.getDireccion());
			stm.setString(5, empleado.getFechaNacimiento());
			stm.setString(6, empleado.getTelefono());
			stm.setString(7, empleado.getEmail());
			stm.executeUpdate();
			stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.empleados "
					+ "(dni, sueldo, fechaIngreso, estado, sector, cargo, tipo) "
					+ "VALUES (?, ?, DATE(NOW()), ?, ?, ?, ?)");
			stm.setString(1, empleado.getDni());
			stm.setFloat(2, empleado.getSalario());
			stm.setInt(3, empleado.getEstado());
			stm.setString(4, empleado.getSector());
			stm.setString(5, empleado.getCargo());
			stm.setString(6, empleado.getRelacion());
			stm.executeUpdate();
		} catch (Exception e) {
			
			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("EmpleadoMySQL, setNuevo()");
		} finally {
			
			this.cerrar();
		}

		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registro nuevo empleado", "Empleados", tiempo);
		return bandera;
	}
	
	@Override
	public boolean update(Empleado empleado) {
		
		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String cmdStm = "UPDATE `lecsys2.00`.persona SET "
						+ "nombre = ?, apellido = ?, direcci�n = ?, fechaNacimiento = ?, tel�fono = ?, email = ? "
						+ "WHERE dni = ?";
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setString(1, empleado.getNombre());
			stm.setString(2, empleado.getApellido());
			stm.setString(3, empleado.getDireccion());
			stm.setString(4, empleado.getFechaNacimiento());
			stm.setString(5, empleado.getTelefono());
			stm.setString(6, empleado.getEmail());
			stm.setString(7, empleado.getDni());
			stm.executeUpdate();
			cmdStm = "UPDATE `lecsys2.00`.empleados SET sueldo = ?, estado = ?, sector = ?, cargo = ?, tipo = ?, fechaBaja = "
					+ (empleado.getFechaBaja() != null? "DATE(NOW()) ": "NULL ") 
					+ "WHERE legajo = ?";
			stm = this.conexion.prepareStatement(cmdStm);
			stm.setFloat(1, empleado.getSalario());
			stm.setInt(2, empleado.getEstado());
			stm.setString(3, empleado.getSector());
			stm.setString(4, empleado.getCargo());
			stm.setString(5, empleado.getRelacion());
			stm.setInt(6, empleado.getLegajo());
			stm.executeUpdate();
			cmdStm = "UPDATE `lecsys2.00`.empleados SET estado = 0 "
					+ "WHERE (sector = 'Docente' AND estado = 1 AND legajo NOT IN "
					+ "(SELECT idprofesor FROM `lecsys2.00`.curso WHERE (estado = 1)))";
			stm = this.conexion.prepareStatement(cmdStm);
			stm.executeUpdate();
		} catch (Exception e) {

			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("EmpleadoMySQL, update()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Edici�n de los datos de un empleado empleado", "Empleados", tiempo);
		return bandera;
	}
	
	@Override
	public Empleado getEmpleado(String legajo) {
		
		Empleado empleado = new Empleado();
		String cmdStm = "SELECT persona.nombre, apellido, empleados.dni, direcci�n, "
						+ "tel�fono, email, sueldo, DATE_FORMAT(fechaNacimiento, '%d/%m/%Y'), estado "
						+ "FROM `lecsys2.00`.empleados "
						+ "JOIN `lecsys2.00`.persona on empleados.dni = persona.dni "
						+ "WHERE empleados.legajo = ?";
				
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(cmdStm);
			stm.setString(1, legajo);
			ResultSet rs = stm.executeQuery();

			if(rs.next()) {
				
				empleado.setNombre(rs.getString(1));
				empleado.setApellido(rs.getString(2));
				empleado.setDni(rs.getString(3));
				empleado.setDireccion(rs.getString(4));
				empleado.setTelefono(rs.getString(5));
				empleado.setEmail(rs.getString(6));
				empleado.setSalario(rs.getFloat(7));
				empleado.setFechaNacimiento(rs.getString(8));
				empleado.setEstado(rs.getInt(9));
			}
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("EmpleadoMySQL, getEmpleado()");
		} finally {
			
			this.cerrar();
		}
		return empleado;
	}
	
	@Override
	public Empleado [] getListado(String tipo, int estado, String filtrado) {
		
		Empleado empleados[] = null;
		String filtroEstado = estado == 2? "": "empleados.estado = " + estado + " AND ";
		String cmdStm = "SELECT empleados.legajo, persona.nombre, apellido, empleados.dni, direcci�n, tel�fono, "
						+ "email, sector, cargo , sueldo, tipo, estado, DATE_FORMAT(fechaNacimiento, '%d/%m/%Y'), DATE_FORMAT(fechaBaja, '%d/%m/%Y') "
						+ "FROM `lecsys2.00`.empleados "
		 				+ "JOIN `lecsys2.00`.persona on empleados.dni = persona.dni ";

		if(tipo.equals("Todos"))
			cmdStm += "WHERE(" + filtroEstado;
		else
			cmdStm += "WHERE(" + filtroEstado + "sector = '" + tipo + "' AND ";
		cmdStm += "(apellido LIKE '" + filtrado + "%' OR nombre LIKE '" + filtrado + "%')) "
	 				+ "ORDER BY apellido, nombre";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();

			empleados = new Empleado[rs.getRow()];
			rs.beforeFirst();
			int i = 0;

			while (rs.next()) {
				
				empleados[i] = new Empleado(); 
				empleados[i].setLegajo(rs.getInt(1));
				empleados[i].setNombre(rs.getString(2));
				empleados[i].setApellido(rs.getString(3));
				empleados[i].setDni(rs.getString(4));
				empleados[i].setDireccion(rs.getString(5));
				empleados[i].setTelefono(rs.getString(6));
				empleados[i].setEmail(rs.getString(7));
				empleados[i].setSector(rs.getString(8));
				empleados[i].setCargo(rs.getString(9));
				empleados[i].setSalario(rs.getFloat(10));
				empleados[i].setRelacion(rs.getString(11));
				empleados[i].setEstado(rs.getInt(12));
				empleados[i].setFechaNacimiento(rs.getString(13));
				empleados[i].setFechaBaja(rs.getString(14));
				i++;
			}
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("EmpleadoMySQL, getListado()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return empleados;
	}
	
	@Override
	public int getLegajoLibre() {
		
		int legajo = 0;

		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery("SELECT MAX(legajo) FROM `lecsys2.00`.empleados");

			if(rs.next()) {
				
				legajo = rs.getInt(1);
			}
		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("EmpleadoMySQL, getLegajoLibre()");
		} finally {
			
			this.cerrar();
		}
		return (legajo + 1);
	}
}