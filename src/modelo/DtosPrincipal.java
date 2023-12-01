package modelo;

import javax.swing.JOptionPane;
import control.EmailSenderService;
import dao.BackupDB;
import dao.EstadisticasDAO;
import dao.EstadisticasMySQL;
import dao.GrupoFamiliarDAO;
import dao.GrupoFamiliarMySQL;
import dao.UsuariosDAO;
import dao.UsuariosMySQL;

public class DtosPrincipal {
	
	private EstadisticasDAO estadisticasDAO = new EstadisticasMySQL();
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
		Estadisticas estadiscica = estadisticasDAO.getResumenMensual();		
		String cuerpoEmail = null;
		cuerpoEmail = "Informe mensual\n";
		cuerpoEmail += "\nFecha de generacion del informe: " + estadiscica.getFecha();
		cuerpoEmail += "\n\nCantidad de estudiantes: " + estadiscica.getCantidadEstudientas();
		cuerpoEmail += "\n       Cantidad de faltas en el mes: " + estadiscica.getFaltasEstudiantes();
		cuerpoEmail += "\n\nCantidsa de empleados: " + estadiscica.getCantidadEmpleados();
		cuerpoEmail += "\n       Cantidad de faltas en el mes: " + estadiscica.getFaltasEmpleados();
		cuerpoEmail += "\n\nIngresos: " + estadiscica.getIngresos();
		cuerpoEmail += "\nSueldos: " + estadiscica.getSueldos();
		cuerpoEmail += "\nCompras: " + estadiscica.getCompras();
		cuerpoEmail += "\nUtilidad del mes: " + (estadiscica.getIngresos() - estadiscica.getSueldos() - estadiscica.getCompras());
		emailService.mandarCorreo(config.getEmailInforme(), "Resumen mensual", cuerpoEmail);
	}
}