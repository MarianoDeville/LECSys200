package modelo;

import javax.swing.JOptionPane;
import control.EmailSenderService;
import dao.BackupDB;
import dao.EstadisticasDAO;
import dao.GrupoFamiliarDAO;
import dao.GrupoFamiliarMySQL;
import dao.UsuariosDAO;
import dao.UsuariosMySQL;

public class DtosPrincipal {
	
	private EstadisticasDAO estadisticasDAO = new EstadisticasDAO();
	DtosConfiguracion config = new DtosConfiguracion();
	
	public void inicializar() {

		if(estadisticasDAO.isNuevoMes()) {
JOptionPane.showMessageDialog(null, "generando el resumen mensual");
			GrupoFamiliarDAO grupoFamiliarDAO = new GrupoFamiliarMySQL();
			UsuariosDAO usuariosDAO = new UsuariosMySQL();
			grupoFamiliarDAO.updateDeuda(0, 1);
			usuariosDAO.updateTiempoPass();
			BackupDB.iniciar();
			mandarInforme();
		}
	}
	
	private void mandarInforme() {

		EmailSenderService emailService = new EmailSenderService();
		String estadiscica[] = estadisticasDAO.getUltima();		
		String cuerpoEmail = null;
		cuerpoEmail = "Informe mensual\n";
		cuerpoEmail += "\nFecha de generacion del informe: " + estadiscica[0];
		cuerpoEmail += "\n\nCantidad de estudiantes: " + estadiscica[1];
		cuerpoEmail += "\n       Cantidad de faltas en el mes: " + estadiscica[2];
		cuerpoEmail += "\n\nCantidsa de empleados: " + estadiscica[3];
		cuerpoEmail += "\n       Cantidad de faltas en el mes: " + estadiscica[4];
		cuerpoEmail += "\n\nIngresos: " + estadiscica[5];
		cuerpoEmail += "\nSueldos: " + estadiscica[6];
		cuerpoEmail += "\nCompras: " + estadiscica[7];
		cuerpoEmail += "\nUtilidad del mes: " + estadiscica[8];
		emailService.mandarCorreo(config.getEmailInforme(), "Resumen mensual", cuerpoEmail);
	}
}