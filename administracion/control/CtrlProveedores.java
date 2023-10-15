package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

import modelo.DtosPagos;
import vista.ABML;
import vista.Listado;

public class CtrlProveedores implements ActionListener{
	
	private ABML ventana;
	private DtosPagos dtosPagos;
	private Listado ventanaListadoDeuda;
	private int elemento = -1;
	
	public CtrlProveedores(ABML vista) {
		
		this.ventana = vista;
		this.dtosPagos = new DtosPagos();
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnEditar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
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
					
					if(ventana.chckbx1.isSelected())
						listadoDeuda();
					else
						historialPagos();
				}
			}
		});
	}
	
	public void iniciar() {

		ventana.btnImprimir.setVisible(false);
		ventana.btnNuevo.setText("Ver");
		ventana.btnEditar.setText("Pagar");
		ventana.chckbx1.setText("Pendientes");
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
			
			listadoDeuda();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
		
		if(ventanaListadoDeuda != null) {
			
			if(e.getSource() == ventanaListadoDeuda.btnVolver) {
				
				actualizar();
			}
		}
	}
	
	private void actualizar() {
		
		elemento = -1;
		ventana.btnEditar.setEnabled(ventana.chckbx1.isSelected());
		ventana.btnNuevo.setEnabled(!ventana.chckbx1.isSelected());
		ventana.tabla.setModel(dtosPagos.getTablaProveedores(ventana.txt1.getText(), 
															 ventana.chckbx1.isSelected()));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(30);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
		ventana.tabla.setDefaultEditor(Object.class, null);
	}
	
	private boolean checkSeleccion() {
		
		if(elemento == -1) {
			
			JOptionPane.showMessageDialog(null, "Debe elegir un elemento.");
			return false;
		}
		dtosPagos.setSelecionado(elemento);
		return true;
	}
	
	private void historialPagos() {
		
		if(checkSeleccion()) {
			
			Listado ventanaDetallePagos = new Listado("Detalle pagos proveedor");
			CtrlListadoPagosProveedor ctrlDetallePagos = new CtrlListadoPagosProveedor(ventanaDetallePagos);
			ctrlDetallePagos.iniciar();
		}
	}
	
	private void listadoDeuda() {
		
		if(checkSeleccion()) {
			
			ventanaListadoDeuda = new Listado("Listado adeudado al proveedor");
			CtrlListadoDeudaProveedor ctrlListadoDeuda = new CtrlListadoDeudaProveedor(ventanaListadoDeuda);
			ctrlListadoDeuda.iniciar();
			ventanaListadoDeuda.btnVolver.addActionListener(this);
		}
	}
}