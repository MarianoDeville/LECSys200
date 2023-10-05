package modelo;

import javax.swing.table.DefaultTableModel;
import dao.PersonaDAO;
import dao.PersonaMySQL;

public class DtosCumplea�os {
	
	private static boolean bandera = false;
	private PersonaDAO personasDAO;
	
	public DefaultTableModel getTablaCumplea�os() {
		
		personasDAO = new PersonaMySQL();
		String titulo[] = {"Nombre", "Apellido"};
		String respuesta[][] = personasDAO.getListadoCumpleA�os();
		
		if(respuesta != null) 
			bandera = true;

		DefaultTableModel tablaModelo = new DefaultTableModel(respuesta, titulo);
		return tablaModelo;
	}

	public boolean isBandera() {
		
		return bandera;
	}
}