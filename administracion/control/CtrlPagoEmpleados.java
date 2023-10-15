package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.JOptionPane;

import modelo.DtosPagos;
import vista.ABML;
import vista.Listado;
import vista.Nuevo;

public class CtrlPagoEmpleados implements ActionListener{
	
	private ABML ventana;
	private DtosPagos dtosPagos;
	private int elemento = -1;
	
	public CtrlPagoEmpleados(ABML vista) {
		
		this.ventana = vista;
		this.dtosPagos = new DtosPagos();
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnEditar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.chckbx1.addActionListener(this);
		this.ventana.txt1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				actualizar();
			}
		});
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
				
					elemento = ventana.tabla.getSelectedRow();
				} else if(e.getClickCount() == 2) {
				
					elemento = ventana.tabla.getSelectedRow();
					historialPagos();
				}
			}
		});
	}
	
	public void iniciar() {

		ventana.btnNuevo.setText("Ver");
		ventana.btnEditar.setText("Pagar");
		ventana.chckbx1.setText("Activos");
		ventana.txt1.setVisible(true);
		ventana.chckbx1.setVisible(true);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.chckbx1) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.btnNuevo) {
			
			historialPagos();
		}
		
		if(e.getSource() == ventana.btnEditar) {
			
			pagar();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
		
		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch (PrinterException f) {
				
				f.printStackTrace();
			}
		}
	}
	
	private void actualizar() {
		
		elemento = -1;
		ventana.btnEditar.setEnabled(ventana.chckbx1.isSelected());
		ventana.tabla.setModel(dtosPagos.getTablaEmpleados(ventana.chckbx1.isSelected(), ventana.txt1.getText()));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(20);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(40);
		ventana.tabla.getColumnModel().getColumn(1).setMinWidth(20);
		ventana.tabla.getColumnModel().getColumn(2).setMinWidth(20);
		ventana.tabla.getColumnModel().getColumn(3).setMinWidth(20);
		ventana.tabla.getColumnModel().getColumn(4).setMinWidth(20);
		ventana.tabla.setDefaultEditor(Object.class, null);
	}
	
	private void historialPagos() {
		
		if(checkSeleccion()) {
			
			Listado ventanaDetallePagos = new Listado("Detalle pagos empleados");
			CtrlListadoPagosEmpleado ctrlDetallePagos = new CtrlListadoPagosEmpleado(ventanaDetallePagos);
			ctrlDetallePagos.iniciar();
		}
	}
	
	private void pagar() {
		
		if(checkSeleccion()) {
		
			Nuevo ventanaPago = new Nuevo("Pago mensualidad");
			CtrlPagarEmpleado ctrlEmpleado = new CtrlPagarEmpleado(ventanaPago);
			ctrlEmpleado.iniciar();
		}
	}
	
	private boolean checkSeleccion() {
		
		if(elemento == -1) {
			
			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
			return false;
		}
		dtosPagos.setSelecionado(elemento);
		actualizar();
		return true;
	}
}