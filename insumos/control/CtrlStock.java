package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.JOptionPane;

import modelo.DtosInsumos;
import vista.ABML;
import vista.NuevoSimple;

public class CtrlStock implements ActionListener {

	private ABML ventana;
	private NuevoSimple ventanaCambiarStock;
	private NuevoSimple ventanaCargarStoc;
	private DtosInsumos dtosInsumos;
	private int elemento;
	
	public CtrlStock(ABML vista) {
		
		this.ventana = vista;
		this.dtosInsumos = new DtosInsumos();
		this.ventana.btnEditar.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
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
		        	cambiarStock();
		        }
		    }
		});
	}
	
	public void iniciar() {

		ventana.btnNuevo.setText("Alta");
		ventana.btnEditar.setText("Baja");
		ventana.txt1.setVisible(true);
		actualizar();
		ventana.setVisible(true);
	}
	
	private void actualizar() {

		elemento = -1;
		ventana.tabla.setModel(dtosInsumos.getStockInsumos(ventana.txt1.getText()));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(20);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(35);
		ventana.tabla.getColumnModel().getColumn(4).setMinWidth(30);
		ventana.tabla.getColumnModel().getColumn(4).setMaxWidth(50);
		ventana.tabla.setDefaultEditor(Object.class, null);
		ventana.tabla.clearSelection();
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnNuevo) {
			
			ingresarStock();
		}

		if(e.getSource() == ventana.btnEditar) {
			
			cambiarStock();
		}
		
		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch (PrinterException f) {
				
				JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
				CtrlLogErrores.guardarError(f.getMessage());
			}
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
		
		if(ventanaCambiarStock != null) {

			if(e.getSource() == ventanaCambiarStock.btnVolver) {
				
				actualizar();
			}
		}
		
		if(ventanaCargarStoc != null) {

			if(e.getSource() == ventanaCargarStoc.btnVolver) {
				
				actualizar();
			}
		}
	}
	
	private void cambiarStock() {
		
		if(elemento == -1) {

			ventana.tabla.clearSelection();
			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento para cambiar el stock.");
			return;
		}
		dtosInsumos.setElementoSeleccionado(elemento);
		ventanaCambiarStock = new NuevoSimple("Baja de stock");
		CtrlBajaInsumo ctrlBajaInsumo = new CtrlBajaInsumo(ventanaCambiarStock);
		ctrlBajaInsumo.iniciar();
		ventanaCambiarStock.btnVolver.addActionListener(this);		
	}
	
	private void ingresarStock() {
		
		if(elemento == -1) {

			ventana.tabla.clearSelection();
			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento para cambiar el stock.");
			return;
		}
		dtosInsumos.setElementoSeleccionado(elemento);
		ventanaCargarStoc = new NuevoSimple("Ingreso de productos al stock.");
		CtrlCargarInsumo ctrlCargarCompra = new CtrlCargarInsumo(ventanaCargarStoc);
		ctrlCargarCompra.iniciar();
		ventanaCargarStoc.btnVolver.addActionListener(this);
	}
}