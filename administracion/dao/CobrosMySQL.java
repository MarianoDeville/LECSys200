package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import control.CtrlLogErrores;
import modelo.Cobros;
import modelo.DtosActividad;

public class CobrosMySQL extends Conexion implements CobrosDAO{

	@Override
	public Cobros [] getListado(int año, int mes) {

		Cobros cobros[] = null;
		String cmdStm = "SELECT DATE_FORMAT(fecha, '%d/%m/%Y'), nombre, concepto, hora, "
						+ "TRUNCATE(monto, 2), factura, idCobros, idEstadisticas "
						+ "FROM `lecsys2.00`.cobros WHERE (YEAR(fecha) = " + año;
								
		if(mes != 0)
			cmdStm += " AND MONTH(fecha) = " + mes;
		
		cmdStm += ") ORDER BY idCobros DESC";
		
		try {

			this.conectar();
			Statement stm = this.conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stm.executeQuery(cmdStm);
			rs.last();	
			cobros = new Cobros[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			
			while(rs.next()) { 
				
				cobros[i] = new Cobros();
				cobros[i].setFecha(rs.getString(1));
				cobros[i].setNombre(rs.getString(2));
				cobros[i].setConcepto(rs.getString(3));
				cobros[i].setHora(rs.getTime(4));
				cobros[i].setMonto(rs.getFloat(5));
				cobros[i].setFactura(rs.getString(6));
				cobros[i].setId(rs.getInt(7));
				cobros[i].setIdEstadisticas(rs.getInt(8));
				i++;
			}
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CobrosMySQL, getListado()");
			CtrlLogErrores.guardarError(cmdStm);
		} finally {
			
			this.cerrar();
		}
		return cobros;
	}
	
	@Override
	public boolean setCobro(Cobros cobro) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String pprStm ="INSERT INTO `lecsys2.00`.cobros "
						+ "(idGrupoFamiliar, nombre, concepto, fecha, hora, monto, factura) "
						+ "VALUES (?, ?, ?, DATE(NOW), TIME(NOW), ?, ?)";
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(pprStm);
			stm.setInt(1, cobro.getIdGrupoFamiliar());
			stm.setString(2, cobro.getNombre());
			stm.setString(3, cobro.getConcepto());
			stm.setFloat(4, cobro.getMonto());
			stm.setString(5, cobro.getFactura());
			stm.executeUpdate();
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CobrosMySQL, setCobro()");
			CtrlLogErrores.guardarError(pprStm);
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Registro cobro de inscripción.", "Administración", tiempo);
		return bandera;
	}
	
	@Override
	public boolean update(Cobros cobros[]) {

		boolean bandera = true;
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		String pprStm = "UPDATE `lecsys2.00`.cobros SET factura = ? WHERE idCobros = ?";
		int i = 0;
		
		try {
			
			this.conectar();
			PreparedStatement stm = this.conexion.prepareStatement(pprStm);
			
			while(i < cobros.length) {

				stm.setString(1, cobros[i].getFactura());
				stm.setInt(2, cobros[i].getId());
				stm.executeUpdate();
				i++;
			}
		} catch (Exception e) {
	
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CobrosMySQL, update()");
			bandera = false;
		} finally {
			
			this.cerrar();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Actualización del número de factura en cobros.", "Administración", tiempo);
		return bandera;
	}
	
	@Override
	public int getUltimoRegistro() {

		int valor = 0;
		
		try {
			
			this.conectar();
			Statement stm = this.conexion.createStatement();
			ResultSet rs = stm.executeQuery("SELECT MAX(idCobros) FROM `lecsys2.00`.cobros");

			if(rs.next())
				valor = rs.getInt(1);

		}catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
			CtrlLogErrores.guardarError("CobrosMySQL, getUltimoRegistro()");
		} finally {
			
			this.cerrar();
		}
		return valor;
	}
}