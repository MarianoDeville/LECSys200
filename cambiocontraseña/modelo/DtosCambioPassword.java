package modelo;

import java.util.Arrays;
import dao.CambioPasswordDAO;
import dao.CambioPasswordMySQL;
import dao.OperadorSistema;

public class DtosCambioPassword {
	
	private static String nombreUsuario;
	private static String contrase�aOld;
	private static String contrase�aNew;
	private static String reContrase�aNew;
	private CambioPasswordDAO cambioPassDAO;
	private OperadorSistema acceso = new OperadorSistema();
	
	public DtosCambioPassword() {

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
		
		cambioPassDAO = new CambioPasswordMySQL();
		if(contrase�aNew.length() < 5) {
			
			return "La contrase�a debe tener m�s de cuatro caracteres.";
		} else if(!reContrase�aNew.equals(contrase�aNew)) {
			
			return "Las contrase�as no coinciden.";
		} else if(contrase�aOld.equals(contrase�aNew)) {
			
			return "La nueva contrase�a no pude ser igual a la anterior.";
		} else if(!cambioPassDAO.checkContrase�a()) {
			
			return "La contrase�a actual es incorrecta.";
		}
		return "";
	}

	public boolean setNuevaContrase�a() {
		
		if(!cambioPassDAO.guardarNuevaContrase�a())
			return false;
		OperadorSistema.setCambiarPass(3);
		return true;
	}

	public String getNombreUsuario() {
		
		return nombreUsuario;
	}
}