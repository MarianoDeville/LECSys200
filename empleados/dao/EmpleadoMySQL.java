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

		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("INSERT INTO `lecsys2.00`.persona "
					+ "(nombre, apellido, dni, dirección, fechaNacimiento, teléfono, email)"
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
			stm.setInt(3, 1);
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
		String pprStm = "";
		try {
			
			this.conectar();
			pprStm = "UPDATE `lecsys2.00`.persona SET "
					 + "nombre = ?, apellido = ?, dirección = ?, fechaNacimiento = ?, teléfono = ?, email = ? "
					 + "WHERE dni = ?";
			PreparedStatement stm = this.conexion.prepareStatement(pprStm);
			stm.setString(1, empleado.getNombre());
			stm.setString(2, empleado.getApellido());
			stm.setString(3, empleado.getDireccion());
			stm.setString(4, empleado.getFechaNacimiento());
			stm.setString(5, empleado.getTelefono());
			stm.setString(6, empleado.getEmail());
			stm.setString(7, empleado.getDni());
			stm.executeUpdate();
			pprStm = "UPDATE `lecsys2.00`.empleados SET sueldo = ?, estado = ?, sector = ?, cargo = ?, tipo = ? ";
			pprStm += " ,fechaBaja = " + empleado.getFechaBaja() != null? "DATE(NOW()) ": "NULL "; 
			pprStm += "WHERE legajo = ?";
			stm = this.conexion.prepareStatement(pprStm);
			stm.setFloat(1, empleado.getSalario());
			stm.setInt(2, empleado.getEstado());
			stm.setString(3, empleado.getSector());
			stm.setString(4, empleado.getCargo());
			stm.setString(5, empleado.getRelacion());
			stm.setInt(6, empleado.getLegajo());
			stm.executeUpdate();
		} catch (Exception e) {

			bandera = false;
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("EmpleadoMySQL, update()");
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Edición de los datos de un empleado empleado", "Empleados", tiempo);
		return bandera;
	}
	
	@Override
	public Empleado getEmpleado(String legajo) {
		
		Empleado empleado = new Empleado();
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("SELECT persona.nombre, apellido, empleados.dni, dirección, "
					+ "teléfono, email, sueldo, DATE_FORMAT(fechaNacimiento, '%d/%m/%Y') "
					+ "FROM `lecsys2.00`.empleados "
					+ "JOIN `lecsys2.00`.persona on empleados.dni = persona.dni "
					+ "WHERE empleados.legajo = ?");
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
	public Empleado [] getListado(String tipo, boolean estado, String filtrado) {
		
		Empleado empleados[] = null;
		String where = " AND (apellido LIKE '" + filtrado + "%' OR nombre LIKE '" + filtrado + "%')) ";
		
		switch (tipo) {
		
			case "Todos":
				where = "WHERE (empleados.estado = " + (estado? "1 ":"0 ") + where;
				break;
				
			case "ID":
				where = "WHERE empleados.idEmpleado = '" + filtrado + "'";
				break;
			
			default:
				where = "WHERE (empleados.estado = " + (estado? "1":"0") + " AND sector = '" + tipo + "'" + where;

		}
		String comandoStatement = "SELECT empleados.legajo, persona.nombre, apellido, empleados.dni, dirección, teléfono, "
								+ "email, sector, cargo , sueldo, tipo, estado, fechaNacimiento, fechaBaja "
								+ "FROM `lecsys2.00`.empleados "
				 				+ "JOIN `lecsys2.00`.persona on empleados.dni = persona.dni "
				 				+ where
				 				+ "ORDER BY apellido, nombre";
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);
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
			CtrlLogErrores.guardarError(comandoStatement);
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