package control;

import dao.DiscoDAO;

public class CtrlLogErrores {

	
	public static void guardarError(String informacion) {
		
		System.err.println(informacion);
		DiscoDAO.escribirLog(informacion);
	}
}
