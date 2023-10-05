package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.DtosActividad;
import modelo.DtosCobros;

public class AdministracionDAO extends Conexion {

	public boolean setActualizarFacturas(String info[][]) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		int i = 0;
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("UPDATE cobros SET factura = ? WHERE idCobros = ?");
			
			while(i < info.length) {

				stm.setString(1, info[i][1]);
				stm.setString(2, info[i][0]);
				stm.executeUpdate();
				i++;
			}
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AdministracionDAO, setActualizarFacturas()");
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Actualización del número de factura en cobros.", "Administración", tiempo);
		return bandera;
	}
	
	public String[][] getTablaCobros(int año, int mes) {

		String respuesta[][] = null;
		String comandoStatement = "SELECT DATE_FORMAT(fecha, '%d/%m/%Y'), nombre, concepto, hora, TRUNCATE(monto, 2), factura, idCobros "
								+ "FROM  cobros WHERE (YEAR(fecha) = " + año;
								
		if(mes != 0)
			comandoStatement += " AND MONTH(fecha) = " + mes;
		
		comandoStatement += ") ORDER BY idCobros DESC";
		
		try {

			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(comandoStatement);
			rs.last();	
			respuesta = new String[rs.getRow()][7];
			rs.beforeFirst();
			int i=0;
			
			while(rs.next()) { 
				
				respuesta[i][0] = rs.getString(1);
				respuesta[i][1] = rs.getString(2);
				respuesta[i][2] = rs.getString(3);
				respuesta[i][3] = rs.getString(4);
				respuesta[i][4] = String.format("%.2f", rs.getFloat(5));
				respuesta[i][5] = rs.getString(6);
				respuesta[i][6] = rs.getString(7);
				i++;
			}
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AdministracionDAO, getTablaCobros()");
			CtrlLogErrores.guardarError(comandoStatement);
		} finally {
			
			this.cerrar();
		}
		return respuesta;
	}
	
	public boolean setCobro() {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosCobros dtosNuevoCobro = new DtosCobros();
		DtosActividad dtosActividad = new DtosActividad();
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement("INSERT INTO cobros (idGrupoFamiliar, nombre, concepto, fecha, hora, monto, factura) "
																 + "VALUES (?, ?, ?, ?, ?, ?, ?)");
			stm.setInt(1, dtosNuevoCobro.getIdFamilia());
			stm.setString(2, dtosNuevoCobro.getNombre());
			stm.setString(3, dtosNuevoCobro.getDescripcion());
			stm.setString(4, dtosNuevoCobro.getFechaActual("A"));
			stm.setString(5, dtosNuevoCobro.getHoraActual());
			stm.setFloat(6, dtosNuevoCobro.getMontoTotal());
			stm.setString(7, dtosNuevoCobro.getFactura());
			stm.executeUpdate();
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AdministracionDAO, setCobro()");
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registro cobro de inscripción.", "Administración", tiempo);
		return bandera;
	}
	
	public int getUltimoRegistro() {

		int valor = 0;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement();
			ResultSet rs = stm.executeQuery("SELECT MAX(idCobros) FROM cobros");

			if(rs.next())
				valor = rs.getInt(1);

		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("AdministracionDAO, getUltimoRegistro()");
		} finally {
			
			this.cerrar();
		}
		return valor;
	}
}