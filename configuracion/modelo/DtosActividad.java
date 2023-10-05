package modelo;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;
import controlador.CtrlLogErrores;
import dao.ActividadDAO;
import dao.OperadorSistema;

public class DtosActividad {
	
	private Calendar fechaSistema;
	private ActividadDAO actividadDAO;
	
	public void registrarActividad(String accion, String modulo, long tiempo) {
		
		actividadDAO = new ActividadDAO();
		OperadorSistema identificacion = new OperadorSistema();
		String miIP = null;

		try {
			
			miIP = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
		} 
		actividadDAO.setActividad(identificacion.getFichaEmpleado(), accion, modulo, miIP, tiempo);
	}
	
	public DefaultTableModel getTablaActividad(String mes, String año) {
		
		actividadDAO = new ActividadDAO();
		String título[] = new String [] {"Id:","Usuario:","Fecha:","Hora:","Acción:","Módulo:","IP:"};
		
		if(año == null)
			año ="";
		
		if(mes.equals("") || año.equals("")) {
			
			fechaSistema = new GregorianCalendar();
			año = fechaSistema.get(Calendar.YEAR) + "";
			mes = fechaSistema.get(Calendar.MONTH) + 1 + "";
		}
		DefaultTableModel tabla = new DefaultTableModel(actividadDAO.getActividad(mes, año), título);
		return tabla;
	}
	
	public int getMesActual() {
		
		fechaSistema = new GregorianCalendar();
		return fechaSistema.get(Calendar.MONTH);
	}
	
	public String [] getMeses() {
		
		return new String[] {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	}
	
	public String [] getAños() {
		
		fechaSistema = new GregorianCalendar();
		String respuesta[] = new String[5];
		
		for(int i = 4 ; i >= 0 ; i--) {
			
			respuesta[i] = fechaSistema.get(Calendar.YEAR) - i +""; 
		}
		return respuesta;
	}
}