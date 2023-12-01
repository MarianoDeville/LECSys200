package control;

import dao.DiscoFS;

public class CtrlLogErrores {

	
	public static void guardarError(String informacion) {
		
		System.err.println(informacion);
		DiscoFS.escribirLog(informacion);
	}
}
