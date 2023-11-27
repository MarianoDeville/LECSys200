package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import dao.AlumnoMySQL;
import dao.OperadorSistema;
import modelo.DtosConfiguracion;
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
		ventana.lbl2A.setText("EstadÍsticas");
		ventana.btn2A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Estadisticas.png"));
		ventana.btn2A.setVisible(true);
		ventana.lbl2B.setText("Cerrar año");
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
			
			if(JOptionPane.showConfirmDialog(null, "¿Esta seguro de cerrar el año actual?", "Alerta!", JOptionPane.YES_NO_OPTION) == 0) {
				
				AlumnoMySQL alumnosDAO = new AlumnoMySQL();
				
				if(alumnosDAO.resetEstado()) {
					
					JOptionPane.showInternalMessageDialog(null, "Operación realizada con éxito.");
					return;
				}
				JOptionPane.showInternalMessageDialog(null, "No se pudo realizar la operación.");
			}
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
		ventanaCobros = new InterfaceBotones("Gestión de cobros", ventana.getX(), ventana.getY());
		CtrlCobros ctrlCobros = new CtrlCobros(ventanaCobros);
		ctrlCobros.iniciar();
	}
	
	private void pagos() {
		
		if(ventanaPagos != null && ventanaPagos.isVisible()) {
			
			ventanaPagos.setVisible(true);
			return;
		}
		ventanaPagos = new InterfaceBotones("Gestión de pagos", ventana.getX(), ventana.getY());
		CtrlPagos ctrlPagos = new CtrlPagos(ventanaPagos);
		ctrlPagos.iniciar();
	}

	private void compras() {
		
		if(ventanaCompras != null && ventanaCompras.isVisible()) {
			
			ventanaCompras.setVisible(true);
			return;
		}
		ventanaCompras = new InterfaceBotones("Gestión de compras", ventana.getX(), ventana.getY());
		CtrlCompras ctrlCompras = new CtrlCompras(ventanaCompras);
		ctrlCompras.iniciar();
	}
	
	private void estadisticas() {
		
		if(ventanaEstadisticas != null && ventanaEstadisticas.isVisible()) {
			
			ventanaEstadisticas.setVisible(true);
			return;
		}
		ventanaEstadisticas = new ABML("Estadísticas", ventana.getX(), ventana.getY());
		CtrlEstadisticas ctrlEstadisticas = new CtrlEstadisticas(ventanaEstadisticas);
		ctrlEstadisticas.iniciar();
	}
}