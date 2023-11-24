package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import dao.OperadorSistema;
import modelo.DtosConfiguracion;
import vista.ABML;
import vista.InterfaceBotones;

public class CtrlInsumos implements ActionListener {

	private InterfaceBotones ventana;
	
	public CtrlInsumos(InterfaceBotones vista) {
		
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
		ventana.lbl1A.setText("ABML insumos");
		ventana.btn1A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\ABML.png"));
		ventana.btn1A.setVisible(true);
		ventana.lbl1B.setText("Pedido");
		ventana.btn1B.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Insumos.png"));
		ventana.btn1B.setVisible(true);
		ventana.lbl1C.setText("Cotizar");
		ventana.btn1C.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Presupuesto.png"));
		ventana.btn1C.setVisible(true);
		ventana.lbl2A.setText("Stock");
		ventana.btn2A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Stock.png"));
		ventana.btn2A.setVisible(true);
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btn1A) {
			
			ABML ventanaABMLInsumos = new ABML("Insumos");
			CtrlABMLInsumos ctrlABMLInsumos = new CtrlABMLInsumos(ventanaABMLInsumos);
			ctrlABMLInsumos.iniciar();
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			ABML ventanaABMLPedidos = new ABML("Pedido de insumos");
			CtrlABMLPedidos ctrlPedidos = new CtrlABMLPedidos(ventanaABMLPedidos);
			ctrlPedidos.iniciar();
		}
		
		if(e.getSource() == ventana.btn1C) {
			
			ABML ventanaPresupuestos = new ABML("Presupuestos");
			CtrlGestionPresupuestos ctrlPresupuestos = new CtrlGestionPresupuestos(ventanaPresupuestos);
			ctrlPresupuestos.iniciar();
		}

		if(e.getSource() == ventana.btn2A) {
			
			ABML ventanaStock = new ABML("Control de stock");
			CtrlStock ctrlStock = new CtrlStock(ventanaStock);
			ctrlStock.iniciar();
		}

		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
}