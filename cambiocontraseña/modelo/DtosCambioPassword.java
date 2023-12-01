package modelo;

import java.util.Arrays;
import dao.CambioPasswordDAO;
import dao.CambioPasswordMySQL;
import dao.OperadorSistema;

public class DtosCambioPassword {
	
	private static String nombreUsuario;
	private static String contraseñaOld;
	private static String contraseñaNew;
	private static String reContraseñaNew;
	private CambioPasswordDAO cambioPassDAO;
	private OperadorSistema acceso = new OperadorSistema();
	
	public DtosCambioPassword() {

		nombreUsuario = acceso.getNombreUsuario();
	}
	
	public String getContraseñaOld() {
		
		return contraseñaOld;
	}
	
	public void setContraseñaOld(char [] contraseñaOld) {
		
		DtosCambioPassword.contraseñaOld = Arrays.toString(contraseñaOld);
	}
	
	public String getContraseñaNew() {
		
		return contraseñaNew;
	}
	
	public void setContraseñaNew(char [] contraseñaNew) {
		
		DtosCambioPassword.contraseñaNew = Arrays.toString(contraseñaNew);
	}
	
	public String getReContraseñaNew() {
		
		return reContraseñaNew;
	}

	public void setReContraseñaNew(char [] reContraseña) {
		
		DtosCambioPassword.reContraseñaNew = Arrays.toString(reContraseña);
	}
	
	public String checkInformacion(boolean usuarioRepetido) {
		
		cambioPassDAO = new CambioPasswordMySQL();
		if(contraseñaNew.length() < 5) {
			
			return "La contraseña debe tener más de cuatro caracteres.";
		} else if(!reContraseñaNew.equals(contraseñaNew)) {
			
			return "Las contraseñas no coinciden.";
		} else if(contraseñaOld.equals(contraseñaNew)) {
			
			return "La nueva contraseña no pude ser igual a la anterior.";
		} else if(!cambioPassDAO.checkContraseña()) {
			
			return "La contraseña actual es incorrecta.";
		}
		return "";
	}

	public boolean setNuevaContraseña() {
		
		if(!cambioPassDAO.guardarNuevaContraseña())
			return false;
		OperadorSistema.setCambiarPass(3);
		return true;
	}

	public String getNombreUsuario() {
		
		return nombreUsuario;
	}
}