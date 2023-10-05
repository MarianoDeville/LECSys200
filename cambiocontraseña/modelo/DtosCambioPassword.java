package modelo;

import java.util.Arrays;
import dao.CambioPasswordDAO;
import dao.OperadorSistema;

public class DtosCambioPassword {
	
	private static String nombreUsuario;
	private static String contraseñaOld;
	private static String contraseñaNew;
	private static String reContraseñaNew;
	private CambioPasswordDAO cambioPass;
		
	public DtosCambioPassword() {

		OperadorSistema acceso = new OperadorSistema();
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
		
		cambioPass = new CambioPasswordDAO();
		if(contraseñaNew.length() < 5) {
			
			return "La contraseña debe tener más de cuatro caracteres.";
		} else if(!reContraseñaNew.equals(contraseñaNew)) {
			
			return "Las contraseñas no coinciden.";
		} else if(contraseñaOld.equals(contraseñaNew)) {
			
			return "La nueva contraseña no pude ser igual a la anterior.";
		} else if(!cambioPass.checkContraseña()) {
			
			return "La contraseña actual es incorrecta.";
		}
		return "";
	}

	public boolean setNuevaContraseña() {
		
		return cambioPass.guardarNuevaContraseña();
	}

	public String getNombreUsuario() {
		
		return nombreUsuario;
	}
}