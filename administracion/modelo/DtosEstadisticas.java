package modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import dao.EstadisticasDAO;

public class DtosEstadisticas {

	private String tabla[][];
	
	public TableModel getTablaCobros(String a�o) {
		
		EstadisticasDAO estadisticasDAO = new EstadisticasDAO();
		tabla = estadisticasDAO.getEstadisticasAnuales(a�o);
		String titulo[] = new String[] {"Mes", "Estud.", "Faltas", "Empl.", "Faltas", "Ingresos", "Sueldos", "Compras", "Utilidad neta"};
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		return tablaModelo;
	}
	
	public String [] getA�os() {
		
		Calendar fechaSistema = new GregorianCalendar();
		String respuesta[] = new String[5];
		
		for(int i = 4 ; i >= 0 ; i--) {
			
			respuesta[i] = fechaSistema.get(Calendar.YEAR) - i +""; 
		}
		return respuesta;
	}
}
