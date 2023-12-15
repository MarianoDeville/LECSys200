package modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;
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

		if(estadisticasDAO.isNuevoMes(false)) {
			
			JOptionPane.showMessageDialog(null, "generando el resumen mensual");
			mandarInforme();
			GrupoFamiliarDAO grupoFamiliarDAO = new GrupoFamiliarMySQL();
			UsuariosDAO usuariosDAO = new UsuariosMySQL();
			Calendar fecha = new GregorianCalendar();
			
			if(fecha.get(Calendar.MONTH) >=config.getMesComienzoClases())
				grupoFamiliarDAO.updateDeuda(0, 1);
			usuariosDAO.updateTiempoPass();
			BackupDB.iniciar();
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
		cuerpoEmail += "\n\n                                                    Detalle situación alumnos con deuda vencida\n";
		cuerpoEmail += "\n                                           Sin deuda     Un mes de deuda     Más de un mes     Suma adeudada";
		cuerpoEmail += "\nAlumnos con descuento    " + estadiscica.getConDescuento().getAlDía() + "                    " 
													 + estadiscica.getConDescuento().getUnMesDeuda() + "                               " 
													 + estadiscica.getConDescuento().getMasDeUnMes() + "                             " 
													 + String.format("%.2f", estadiscica.getConDescuento().getSumaDeuda());
		cuerpoEmail += "\nAlumnos sin descuento      " + estadiscica.getSinDescuento().getAlDía() + "                    " 
													 + estadiscica.getSinDescuento().getUnMesDeuda() + "                              " 
													 + estadiscica.getSinDescuento().getMasDeUnMes() + "                             " 
													 + String.format("%.2f", estadiscica.getSinDescuento().getSumaDeuda());
		cuerpoEmail += "\n\nIngresos: " + String.format("%.2f", estadiscica.getIngresos());
		cuerpoEmail += "\nSueldos:    " + String.format("%.2f", estadiscica.getSueldos());
		cuerpoEmail += "\nCompras:    " + String.format("%.2f", estadiscica.getCompras());
		cuerpoEmail += "\nServicios:  " + String.format("%.2f", estadiscica.getServicios());
		cuerpoEmail += "\nUtilidad del mes: " + String.format("%.2f", (estadiscica.getIngresos() - estadiscica.getSueldos() - estadiscica.getCompras()));
		emailService.mandarCorreo(config.getEmailInforme(), "Resumen mensual", cuerpoEmail);
	}
}