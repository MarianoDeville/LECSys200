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
	
	public DefaultTableModel getTablaActividad(String mes, String a�o) {
		
		actividadDAO = new ActividadDAO();
		String t�tulo[] = new String [] {"Id:","Usuario:","Fecha:","Hora:","Acci�n:","M�dulo:","IP:"};
		
		if(a�o == null)
			a�o ="";
		
		if(mes.equals("") || a�o.equals("")) {
			
			fechaSistema = new GregorianCalendar();
			a�o = fechaSistema.get(Calendar.YEAR) + "";
			mes = fechaSistema.get(Calendar.MONTH) + 1 + "";
		}
		DefaultTableModel tabla = new DefaultTableModel(actividadDAO.getActividad(mes, a�o), t�tulo);
		return tabla;
	}
	
	public int getMesActual() {
		
		fechaSistema = new GregorianCalendar();
		return fechaSistema.get(Calendar.MONTH);
	}
	
	public String [] getMeses() {
		
		return new String[] {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	}
	
	public String [] getA�os() {
		
		fechaSistema = new GregorianCalendar();
		String respuesta[] = new String[5];
		
		for(int i = 4 ; i >= 0 ; i--) {
			
			respuesta[i] = fechaSistema.get(Calendar.YEAR) - i +""; 
		}
		return respuesta;
	}
}