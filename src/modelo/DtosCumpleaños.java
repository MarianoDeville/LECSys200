package modelo;

import javax.swing.table.DefaultTableModel;
import dao.PersonaDAO;
import dao.PersonaMySQL;

public class DtosCumpleaños {
	
	private static boolean bandera = false;
	private PersonaDAO personasDAO;
	
	public DefaultTableModel getTablaCumpleaños() {
		
		personasDAO = new PersonaMySQL();
		String titulo[] = {"Nombre", "Apellido"};
		String respuesta[][] = personasDAO.getListadoCumpleAños();
		
		if(respuesta != null) 
			bandera = true;

		DefaultTableModel tablaModelo = new DefaultTableModel(respuesta, titulo);
		return tablaModelo;
	}

	public boolean isBandera() {
		
		return bandera;
	}
}