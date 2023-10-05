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
			
			InterfaceBotones ventanaCobros = new InterfaceBotones("Gestión de cobros");
//			CtrlCobros ctrlCobros = new CtrlCobros(ventanaCobros);
//			ctrlCobros.iniciar();
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			InterfaceBotones ventanaPagos = new InterfaceBotones("Gestión de pagos");
//			CtrlPagos ctrlPagos = new CtrlPagos(ventanaPagos);
//			ctrlPagos.iniciar();
		}

		if(e.getSource() == ventana.btn1C) {
			
			InterfaceBotones ventanaCompras = new InterfaceBotones("Gestión de compras");
//			CtrlCompras ctrlCompras = new CtrlCompras(ventanaCompras);
//			ctrlCompras.iniciar();
		}
		
		if(e.getSource() == ventana.btn2A) {
			
			ABML ventanaEstadisticas = new ABML("Estadísticas");
//			CtrlEstadisticas ctrlEstadisticas = new CtrlEstadisticas(ventanaEstadisticas);
//			ctrlEstadisticas.iniciar();
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
}