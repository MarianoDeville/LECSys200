package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;

import modelo.DtosCompras;
import vista.Cobro;
import vista.Listado;

public class CtrlOrdenesCompra implements ActionListener {

	private Listado ventana;
	private DtosCompras dtosCompras;
	private int seleccion;

	public CtrlOrdenesCompra(Listado vista) {
		
		this.ventana = vista;
		this.dtosCompras = new DtosCompras();
		this.ventana.chckbx1.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btn1B.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
				
					seleccion = ventana.tabla.getSelectedRow();
				} else if(e.getClickCount() == 2) {
					
					seleccion = ventana.tabla.getSelectedRow();
					verInformacion();
				}
			}
		});
	}
	
	public void iniciar() {

		ventana.btn1A.setText("Imprimir");
		ventana.btn1A.setVisible(true);
		ventana.btnImprimir.setText("Ver");
		ventana.chckbx1.setText("pendientes");
		ventana.chckbx1.setSelected(true);
		ventana.chckbx1.setVisible(true);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.chckbx1) {

			actualizar();
		}
		
		if(e.getSource() == ventana.btnImprimir) {
			
			verInformacion();
		}
		
		if(e.getSource() == ventana.btn1A) {
			
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
	}

	private void actualizar() {
		
		seleccion = -1;
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		derecha.setHorizontalAlignment(JLabel.RIGHT);
		ventana.tabla.setModel(dtosCompras.getTablaOrdenesCompra(ventana.chckbx1.isSelected()));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(25);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(40);
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(30);
		ventana.tabla.getColumnModel().getColumn(1).setMinWidth(70);
		ventana.tabla.getColumnModel().getColumn(1).setMaxWidth(80);
		ventana.tabla.getColumnModel().getColumn(2).setMinWidth(30);
		ventana.tabla.getColumnModel().getColumn(2).setMaxWidth(60);
		ventana.tabla.getColumnModel().getColumn(3).setMinWidth(90);
		ventana.tabla.getColumnModel().getColumn(3).setMaxWidth(120);
		ventana.tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
		ventana.tabla.getColumnModel().getColumn(4).setMinWidth(90);
		ventana.tabla.getColumnModel().getColumn(4).setMaxWidth(120);
		ventana.tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
		ventana.tabla.getColumnModel().getColumn(5).setCellRenderer(derecha);
		ventana.tabla.setDefaultEditor(Object.class, null);
	}
	
	private void verInformacion() {
		
		if(seleccion == -1) {
			
			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
			return;
		}
		dtosCompras.setInfoSelOC(seleccion);
		Cobro ventanaDetalleCompra = new Cobro("Información de la orden de compra.", ventana.getX(), ventana.getY());
		CtrlDetalleOrdenCompra ctrlDetalleOrdenCompra = new CtrlDetalleOrdenCompra(ventanaDetalleCompra);
		ctrlDetalleOrdenCompra.iniciar();
	}
}