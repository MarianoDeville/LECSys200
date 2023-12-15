package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import dao.AlumnoDAO;
import dao.AlumnoMySQL;
import dao.EstadisticasDAO;
import dao.EstadisticasMySQL;
import dao.OperadorSistema;
import modelo.DtosConfiguracion;
import modelo.Estadisticas;
import vista.ABML;
import vista.InterfaceBotones;

public class CtrlAdministracion implements ActionListener {

	private InterfaceBotones ventana;
	private InterfaceBotones ventanaCobros;
	private InterfaceBotones ventanaPagos;
	private InterfaceBotones ventanaCompras;
	private ABML ventanaEstadisticas;
	
	public CtrlAdministracion(InterfaceBotones vista) {
		
		this.ventana = vista;
		this.ventana.btn1A.addActionListener(this);
		this.ventana.btn1B.addActionListener(this);
		this.ventana.btn1C.addActionListener(this);
		this.ventana.btn2A.addActionListener(this);
		this.ventana.btn2B.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {
		
		if(!OperadorSistema.isAcceso()) {
			
			ventana.dispose();
			return;
		}
		ventana.lbl1A.setText("Cobros");
		ventana.btn1A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Cobros.png"));
		ventana.btn1A.setVisible(true);
		ventana.lbl1B.setText("Pagos");
		ventana.btn1B.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Pagos.png"));
		ventana.btn1B.setVisible(true);
		ventana.lbl1C.setText("Compras");
		ventana.btn1C.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Compras.png"));
		ventana.btn1C.setVisible(true);
		ventana.lbl2A.setText("Estad�sticas");
		ventana.btn2A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Estadisticas.png"));
		ventana.btn2A.setVisible(true);
		ventana.lbl2B.setText("Cerrar a�o");
		ventana.btn2B.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Cierre.png"));
		ventana.btn2B.setVisible(true);
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btn1A) {
			
			cobros();
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			pagos();
		}

		if(e.getSource() == ventana.btn1C) {
			
			compras();
		}
		
		if(e.getSource() == ventana.btn2A) {
			
			estadisticas();
		}
		
		if(e.getSource() == ventana.btn2B) {
			
			cerrarA�o();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void cobros() {
		
		if(ventanaCobros != null && ventanaCobros.isVisible()) {
			
			ventanaCobros.setVisible(true);
			return;
		}
		ventanaCobros = new InterfaceBotones("Gesti�n de cobros", ventana.getX(), ventana.getY());
		CtrlCobros ctrlCobros = new CtrlCobros(ventanaCobros);
		ctrlCobros.iniciar();
	}
	
	private void pagos() {
		
		if(ventanaPagos != null && ventanaPagos.isVisible()) {
			
			ventanaPagos.setVisible(true);
			return;
		}
		ventanaPagos = new InterfaceBotones("Gesti�n de pagos", ventana.getX(), ventana.getY());
		CtrlPagos ctrlPagos = new CtrlPagos(ventanaPagos);
		ctrlPagos.iniciar();
	}

	private void compras() {
		
		if(ventanaCompras != null && ventanaCompras.isVisible()) {
			
			ventanaCompras.setVisible(true);
			return;
		}
		ventanaCompras = new InterfaceBotones("Gesti�n de compras", ventana.getX(), ventana.getY());
		CtrlCompras ctrlCompras = new CtrlCompras(ventanaCompras);
		ctrlCompras.iniciar();
	}
	
	private void estadisticas() {
		
		if(ventanaEstadisticas != null && ventanaEstadisticas.isVisible()) {
			
			ventanaEstadisticas.setVisible(true);
			return;
		}
		ventanaEstadisticas = new ABML("Estad�sticas", ventana.getX(), ventana.getY());
		CtrlEstadisticas ctrlEstadisticas = new CtrlEstadisticas(ventanaEstadisticas);
		ctrlEstadisticas.iniciar();
	}
	
	private void cerrarA�o() {
		
		if(JOptionPane.showConfirmDialog(null, "�Esta seguro de cerrar el a�o actual?", "Alerta!", JOptionPane.YES_NO_OPTION) == 0) {
			
			AlumnoDAO alumnosDAO = new AlumnoMySQL();
			
			if(alumnosDAO.resetEstado()) {
				
				EstadisticasDAO estadisticasDAO = new EstadisticasMySQL();
				estadisticasDAO.isNuevoMes(true);
				Estadisticas estadiscica = estadisticasDAO.getResumenMensual();
				Estadisticas resumen = estadisticasDAO.getResumenAnual();
				DtosConfiguracion config = new DtosConfiguracion();
				EmailSenderService emailService = new EmailSenderService();
				String cuerpoEmail = "Informe cierre de a�o\n";
				cuerpoEmail += "\nFecha de generacion del informe: " + estadiscica.getFecha();
				cuerpoEmail += "\n\nCantidad de estudiantes: " + estadiscica.getCantidadEstudientas();
				cuerpoEmail += "\n       Cantidad de faltas en el mes: " + estadiscica.getFaltasEstudiantes();
				cuerpoEmail += "\n\nCantidsa de empleados: " + estadiscica.getCantidadEmpleados();
				cuerpoEmail += "\n       Cantidad de faltas en el mes: " + estadiscica.getFaltasEmpleados();
				cuerpoEmail += "\n\n                                                    Detalle situaci�n alumnos con deuda vencida\n";
				cuerpoEmail += "\n                                           Sin deuda     Un mes de deuda     M�s de un mes     Suma adeudada";
				cuerpoEmail += "\nAlumnos con descuento    " + estadiscica.getConDescuento().getAlD�a() + "                    " 
															 + estadiscica.getConDescuento().getUnMesDeuda() + "                               " 
															 + estadiscica.getConDescuento().getMasDeUnMes() + "                             " 
															 + String.format("%.2f", estadiscica.getConDescuento().getSumaDeuda());
				cuerpoEmail += "\nAlumnos sin descuento      " + estadiscica.getSinDescuento().getAlD�a() + "                    " 
															 + estadiscica.getSinDescuento().getUnMesDeuda() + "                              " 
															 + estadiscica.getSinDescuento().getMasDeUnMes() + "                             " 
															 + String.format("%.2f", estadiscica.getSinDescuento().getSumaDeuda());
				cuerpoEmail += "\n\nIngresos: " + String.format("%.2f", estadiscica.getIngresos());
				cuerpoEmail += "\nSueldos:    " + String.format("%.2f", estadiscica.getSueldos());
				cuerpoEmail += "\nCompras:    " + String.format("%.2f", estadiscica.getCompras());
				cuerpoEmail += "\nServicios:  " + String.format("%.2f", estadiscica.getServicios());
				cuerpoEmail += "\nUtilidad del mes: " + String.format("%.2f", (estadiscica.getIngresos() - estadiscica.getSueldos() - estadiscica.getCompras()));
				cuerpoEmail += "\n\nIngresos anuales: " + String.format("%.2f", resumen.getIngresos());
				cuerpoEmail += "\nSueldos anuales:   " + String.format("%.2f", resumen.getSueldos());
				cuerpoEmail += "\nCompras anuales:  " + String.format("%.2f", resumen.getCompras());
				cuerpoEmail += "\nServicios anuales: " + String.format("%.2f", resumen.getServicios());
				cuerpoEmail += "\nUtilidad anual: " + String.format("%.2f", resumen.getIngresos() + resumen.getSueldos() + resumen.getSueldos() + resumen.getSueldos());
				emailService.mandarCorreo(config.getEmailInforme(), "Resumen mensual y cierre de a�o", cuerpoEmail);
				JOptionPane.showInternalMessageDialog(null, "Operaci�n realizada con �xito.");
				return;
			}
			JOptionPane.showInternalMessageDialog(null, "No se pudo realizar la operaci�n.");
		}
	}
}