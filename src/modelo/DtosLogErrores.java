package modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import dao.DiscoDAO;

public class DtosLogErrores extends DiscoDAO{

	public static void setError(String informacion) {

		Calendar fechaSistema = new GregorianCalendar();
	    String dateEvento =  (fechaSistema.get(Calendar.DAY_OF_MONTH)<10? "0" + fechaSistema.get(Calendar.DAY_OF_MONTH):fechaSistema.get(Calendar.DAY_OF_MONTH)) + "/" 
							 + ((fechaSistema.get(Calendar.MONTH)+1)<10? "0" + (fechaSistema.get(Calendar.MONTH)+1):(fechaSistema.get(Calendar.MONTH)+1)) + "/" 
							 + fechaSistema.get(Calendar.YEAR) + " "
							 + (fechaSistema.get(Calendar.AM_PM)==0? fechaSistema.get(Calendar.HOUR):fechaSistema.get(Calendar.HOUR)+12) 
							 + ":" +(fechaSistema.get(Calendar.MINUTE)<10? "0" + fechaSistema.get(Calendar.MINUTE):fechaSistema.get(Calendar.MINUTE))
							 + ":" +(fechaSistema.get(Calendar.SECOND)<10? "0" + fechaSistema.get(Calendar.SECOND):fechaSistema.get(Calendar.SECOND));
	   
		
	    escribirLog(dateEvento + " - " + informacion);
	}
}
