package modelo;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;
import control.CtrlLogErrores;
import dao.ActividadDAO;
import dao.ActividadMySQL;
import dao.OperadorSistema;

public class DtosActividad {
	
	private Calendar fechaSistema;
	private ActividadDAO actividadDAO;
	
	public void registrarActividad(String accion, String modulo, long tiempo) {
		
		actividadDAO = new ActividadMySQL();
		OperadorSistema identificacion = new OperadorSistema();
		String miIP = null;

		try {
			
			miIP = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
		} 
		Actividad actividad = new Actividad(identificacion.getFichaEmpleado(), accion, modulo, miIP, tiempo);
		actividadDAO.setActividad(actividad);
	}
	
	public DefaultTableModel getTablaActividad(String mes, String año) {
		
		if(año == null)
			año ="";
		
		if(mes.equals("") || año.equals("")) {
			
			fechaSistema = new GregorianCalendar();
			año = fechaSistema.get(Calendar.YEAR) + "";
			mes = fechaSistema.get(Calendar.MONTH) + 1 + "";
		}
		actividadDAO = new ActividadMySQL();
		Actividad actividad[] = actividadDAO.getActividad(mes, año);
		Object tabla[][] = new Object[actividad.length][7];
		String título[] = new String [] {"Id:","Usuario:","Fecha:","Hora:","Acción:","Módulo:","IP:"};
		
		for(int i = 0; i < tabla.length; i++) {
		
			tabla[i][0] = actividad[i].getId();
			tabla[i][1] = actividad[i].getNombre();
			tabla[i][2] = actividad[i].getFecha();
			tabla[i][3] = actividad[i].getHora();
			tabla[i][4] = actividad[i].getAccion();
			tabla[i][5] = actividad[i].getModulo();
			tabla[i][6] = actividad[i].getIp();
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, título);
		return respuesta;
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