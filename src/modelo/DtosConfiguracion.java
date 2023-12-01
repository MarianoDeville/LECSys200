package modelo;

import java.util.Arrays;
import dao.DiscoFS;

public class DtosConfiguracion {

	private DiscoFS discoDAO;
	private static String emailSistema;
	private static String passSistema;
	private static String servidor;
	private static String usuarioBD;
	private static String passBD;
	private static String directorio;
	private static String msgError;
	private static String serviviosMySQL;
	private static String directorioBackup;
	private static String emailInforme;
	
	public String getConfig() {
		
		discoDAO = new DiscoFS();
		return discoDAO.getConfiguracion();
	}

	public void setConfiguracion() {
		
		if(!discoDAO.isArchivoExiste())
			discoDAO.setArchivoConfiguración();
	}
	
	public String setEmail(String email, char[] contraseña, char[] recontraseña) {
		
		String msg = "";
		String pass = Arrays.toString(contraseña);
		
		if(email.length() < 6)
			return "El campo email debe estar lleno.";
		
		if(!email.contains("@") || email.contains(" "))
			return "Error en el formato del email.";
		String partes[] = email.split("@");
		
		try {
			
			if(partes[1].length() < 2 || !partes[1].contains("."))
				return "Error en el formato del email.";	
		} catch (Exception e) {
			
			return "Error en el formato del email.";	
		}	
		
		if(!pass.equals(Arrays.toString(recontraseña)))
			return "Las contraseñas son distintas";
		pass = "";
		
		for(int i = 0; i < contraseña.length; i++) {
			
			pass += contraseña[i];
		}
		String valores[] = {"MAIL", email, "MPAS", pass};
		discoDAO = new DiscoFS();
		msg = discoDAO.modificarValores(valores);
	
		if(msg.equals("")) {
			
			emailSistema = email;
			passSistema = pass;
		}
		return msg;
	}

	public String getEmailSistema() {
		
		return emailSistema;
	}
	
	public void setEmailSistema(String emailSistema) {
		
		DtosConfiguracion.emailSistema = emailSistema;
	}
	
	public String getPassSistema() {
		
		return passSistema;
	}
	
	public void setPassSistema(String passSistema) {
		
		DtosConfiguracion.passSistema = passSistema;
	}
	
	public String getServidor() {
		
		return servidor;
	}
	
	public void setServidor(String servidor) {
		
		DtosConfiguracion.servidor = servidor;
	}
	
	public String getUsuarioBD() {
		
		return usuarioBD;
	}
	
	public void setUsuarioBD(String usuarioBD) {
		
		DtosConfiguracion.usuarioBD = usuarioBD;
	}
	
	public String getPassBD() {
		
		return passBD;
	}
	
	public void setPassBD(String passBD) {
		
		DtosConfiguracion.passBD = passBD;
	}

	public String getMsgError() {
		
		return msgError;
	}

	public static String getDirectorio() {
		
		return directorio;
	}

	public void setDirectorio(String directorio) {
		
		DtosConfiguracion.directorio = directorio;
	}

	public String getServiviosMySQL() {
		
		return serviviosMySQL;
	}

	public void setServiviosMySQL(String serviviosMySQL) {
		
		DtosConfiguracion.serviviosMySQL = serviviosMySQL;
	}

	public String getDirectorioBackup() {
		
		return directorioBackup;
	}

	public void setDirectorioBackup(String directorioBackup) {
		
		DtosConfiguracion.directorioBackup = directorioBackup;
	}
	
	public void setEmailInforme(String destinatario) {
		
		emailInforme = destinatario;
	}
	
	public String getEmailInforme() {
		
		return emailInforme;
	}
}