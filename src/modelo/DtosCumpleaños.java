package modelo;

import javax.swing.table.DefaultTableModel;
import dao.PersonaDAO;
import dao.PersonaMySQL;

public class DtosCumpleaņos {
	
	private static boolean bandera = false;
	private PersonaDAO personasDAO;
	
	public DefaultTableModel getTablaCumpleaņos() {
		
		personasDAO = new PersonaMySQL();
		String titulo[] = {"Nombre", "Apellido"};
		String respuesta[][] = personasDAO.getListadoCumpleAņos();
		
		if(respuesta != null) 
			bandera = true;

		DefaultTableModel tablaModelo = new DefaultTableModel(respuesta, titulo);
		return tablaModelo;
	}

	public boolean isBandera() {
		
		return bandera;
	}
}