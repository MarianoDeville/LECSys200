package modelo;

import java.util.Arrays;
import dao.CambioPasswordDAO;
import dao.OperadorSistema;

public class DtosCambioPassword {
	
	private static String nombreUsuario;
	private static String contrase�aOld;
	private static String contrase�aNew;
	private static String reContrase�aNew;
	private CambioPasswordDAO cambioPass;
		
	public DtosCambioPassword() {

		OperadorSistema acceso = new OperadorSistema();
		nombreUsuario = acceso.getNombreUsuario();
	}
	
	public String getContrase�aOld() {
		
		return contrase�aOld;
	}
	
	public void setContrase�aOld(char [] contrase�aOld) {
		
		DtosCambioPassword.contrase�aOld = Arrays.toString(contrase�aOld);
	}
	
	public String getContrase�aNew() {
		
		return contrase�aNew;
	}
	
	public void setContrase�aNew(char [] contrase�aNew) {
		
		DtosCambioPassword.contrase�aNew = Arrays.toString(contrase�aNew);
	}
	
	public String getReContrase�aNew() {
		
		return reContrase�aNew;
	}

	public void setReContrase�aNew(char [] reContrase�a) {
		
		DtosCambioPassword.reContrase�aNew = Arrays.toString(reContrase�a);
	}
	
	public String checkInformacion(boolean usuarioRepetido) {
		
		cambioPass = new CambioPasswordDAO();
		if(contrase�aNew.length() < 5) {
			
			return "La contrase�a debe tener m�s de cuatro caracteres.";
		} else if(!reContrase�aNew.equals(contrase�aNew)) {
			
			return "Las contrase�as no coinciden.";
		} else if(contrase�aOld.equals(contrase�aNew)) {
			
			return "La nueva contrase�a no pude ser igual a la anterior.";
		} else if(!cambioPass.checkContrase�a()) {
			
			return "La contrase�a actual es incorrecta.";
		}
		return "";
	}

	public boolean setNuevaContrase�a() {
		
		return cambioPass.guardarNuevaContrase�a();
	}

	public String getNombreUsuario() {
		
		return nombreUsuario;
	}
}