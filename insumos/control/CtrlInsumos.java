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
	private ABML ventanaABMLInsumos;
	private ABML ventanaABMLPedidos;
	private ABML ventanaPresupuestos;
	private ABML ventanaStock;
	
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
			
			abml();
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			pedido();
		}
		
		if(e.getSource() == ventana.btn1C) {
			
			presupuesto();
		}

		if(e.getSource() == ventana.btn2A) {
			
			stock();
		}

		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void abml() {
		
		if(ventanaABMLInsumos != null && ventanaABMLInsumos.isVisible()) {
			
			ventanaABMLInsumos.setVisible(true);
			return;
		}
		ventanaABMLInsumos = new ABML("Insumos", ventana.getX(), ventana.getY());
		CtrlABMLInsumos ctrlABMLInsumos = new CtrlABMLInsumos(ventanaABMLInsumos);
		ctrlABMLInsumos.iniciar();
	}
	
	private void pedido() {
		
		if(ventanaABMLPedidos != null && ventanaABMLPedidos.isVisible()) {
			
			ventanaABMLPedidos.setVisible(true);
			return;
		}
		ventanaABMLPedidos = new ABML("Pedido de insumos", ventana.getX(), ventana.getY());
		CtrlABMLPedidos ctrlPedidos = new CtrlABMLPedidos(ventanaABMLPedidos);
		ctrlPedidos.iniciar();
	}
	
	private void presupuesto() {
		
		if(ventanaPresupuestos != null && ventanaPresupuestos.isVisible()) {
			
			ventanaPresupuestos.setVisible(true);
			return;
		}
		ventanaPresupuestos = new ABML("Presupuestos", ventana.getX(), ventana.getY());
		CtrlGestionPresupuestos ctrlPresupuestos = new CtrlGestionPresupuestos(ventanaPresupuestos);
		ctrlPresupuestos.iniciar();
	}
	
	private void stock() {
		
		if(ventanaStock != null && ventanaStock.isVisible()) {
			
			ventanaStock.setVisible(true);
			return;
		}
		ventanaStock = new ABML("Control de stock", ventana.getX(), ventana.getY());
		CtrlStock ctrlStock = new CtrlStock(ventanaStock);
		ctrlStock.iniciar();
	}
}