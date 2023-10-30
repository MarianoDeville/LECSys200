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
	
	public CtrlPagos(InterfaceBotones vista) {
		
		this.ventana = vista;
		this.ventana.btn1A.addActionListener(this);
		this.ventana.btn1B.addActionListener(this);
		this.ventana.btn1C.addActionListener(this);
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
		ventana.lbl1C.setText("Listado pagos");
		ventana.btn1C.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Listado cobros.png"));
		ventana.btn1C.setVisible(true);
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btn1A) {

			ABML ventanaEmpleados = new ABML("Pago a empleados", ventana.getX(), ventana.getY());
			CtrlPagoEmpleados ctrlEmpleados = new CtrlPagoEmpleados(ventanaEmpleados);
			ctrlEmpleados.iniciar();			
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			ABML ventanaProveedores = new ABML("Pago a proveedores", ventana.getX(), ventana.getY());
			CtrlProveedores ctrlProveedores = new CtrlProveedores(ventanaProveedores);
			ctrlProveedores.iniciar();
		}
		
		if(e.getSource() == ventana.btn1C) {
			
			Listado ventanaHistorial = new Listado("Historial de pagos", ventana.getX(), ventana.getY());
			CtrlListadoPagos ctrlListadoPagos = new CtrlListadoPagos(ventanaHistorial);
			ctrlListadoPagos.iniciar();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
}