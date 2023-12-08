package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import dao.OperadorSistema;
import modelo.DtosConfiguracion;
import vista.ABML;
import vista.InterfaceBotones;
import vista.Listado;

public class CtrlPagos implements ActionListener {

	private InterfaceBotones ventana;
	private ABML ventanaEmpleados;
	private ABML ventanaProveedores;
	private ABML ventanaServicios;
	private Listado ventanaHistorial;
	
	public CtrlPagos(InterfaceBotones vista) {
		
		this.ventana = vista;
		this.ventana.btn1A.addActionListener(this);
		this.ventana.btn1B.addActionListener(this);
		this.ventana.btn1C.addActionListener(this);
		this.ventana.btn2A.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {
		
		if(!OperadorSistema.isAcceso()) {
			
			ventana.dispose();
			return;
		}
		ventana.lbl1A.setText("Empleados");
		ventana.btn1A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Pagos.png"));
		ventana.btn1A.setVisible(true);
		ventana.lbl1B.setText("Proveedores");
		ventana.btn1B.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Proveedores.png"));
		ventana.btn1B.setVisible(true);
		ventana.lbl1C.setText("Servicios");
		ventana.btn1C.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Servicios.png"));
		ventana.btn1C.setVisible(true);
		ventana.lbl2A.setText("Listado pagos");
		ventana.btn2A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Listado cobros.png"));
		ventana.btn2A.setVisible(true);
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btn1A) {

			empleados();
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			proveedores();
		}
		
		if(e.getSource() == ventana.btn1C) {
			
			servicios();
		}
		
		if(e.getSource() == ventana.btn2A) {
			
			historial();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			if(ventanaEmpleados != null)
				ventanaEmpleados.dispose();

			if(ventanaProveedores != null)
				ventanaProveedores.dispose();

			if(ventanaServicios != null)
				ventanaServicios.dispose();

			if(ventanaHistorial != null)
				ventanaHistorial.dispose();
			ventana.dispose();
		}
	}
	
	private void empleados() {

		if(ventanaEmpleados != null && ventanaEmpleados.isVisible()) {
			
			ventanaEmpleados.setVisible(true);
			return;
		}
		ventanaEmpleados = new ABML("Pago a empleados", ventana.getX(), ventana.getY());
		CtrlPagoEmpleados ctrlEmpleados = new CtrlPagoEmpleados(ventanaEmpleados);
		ctrlEmpleados.iniciar();			
	}
	
	private void proveedores() {

		if(ventanaProveedores != null && ventanaProveedores.isVisible()) {
			
			ventanaProveedores.setVisible(true);
			return;
		}
		ventanaProveedores = new ABML("Pago a proveedores", ventana.getX(), ventana.getY());
		CtrlProveedores ctrlProveedores = new CtrlProveedores(ventanaProveedores);
		ctrlProveedores.iniciar();
	}
	
	private void servicios() {
		
		if(ventanaServicios != null && ventanaServicios.isVisible()) {
			ventanaServicios.setVisible(true);
			return;
		}
		ventanaServicios = new ABML("Pago de servicios e impuestos", ventana.getX(), ventana.getY());
		CtrlPagoServicios ctrlPagarServicios = new CtrlPagoServicios(ventanaServicios);
		ctrlPagarServicios.iniciar();
	}
	
	private void historial() {
		
		if(ventanaHistorial != null && ventanaHistorial.isVisible()) {
			
			ventanaHistorial.setVisible(true);
			return;
		}
		ventanaHistorial = new Listado("Historial de pagos", ventana.getX(), ventana.getY());
		CtrlListadoPagos ctrlListadoPagos = new CtrlListadoPagos(ventanaHistorial);
		ctrlListadoPagos.iniciar();
	}
}