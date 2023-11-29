package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.DtosCompras;
import vista.Cobro;
import vista.Listado;

public class CtrlListadoCompras implements ActionListener {

	private Listado ventana;
	private Cobro ventanaDetalleCompra;
	private DtosCompras dtosComras;
	private int elemento = -1;
	
	public CtrlListadoCompras(Listado vista) {
		
		this.ventana = vista;
		this.dtosComras = new DtosCompras();
		this.ventana.btn1A.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.comboBox2.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		    	
		    	if(e.getClickCount() == 1) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
		        } else if(e.getClickCount() == 2) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
		        	abrirSeleccionado();
		        }
		    }
		});
	}
	
	public void iniciar() {

		ventana.btn1A.setText("Ver");
		ventana.btn1A.setVisible(true);
		ventana.lblComboBox1.setVisible(true);
		ventana.lblComboBox1.setText("Año:");
		ventana.comboBox1.setVisible(true);
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosComras.listadoAños()));
		ventana.lblComboBox2.setVisible(true);
		ventana.lblComboBox2.setText("Mes:");
		ventana.comboBox2.setVisible(true);
		ventana.comboBox2.setModel(new DefaultComboBoxModel<String>(dtosComras.listadoMeses()));
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.comboBox1) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.comboBox2) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch (PrinterException f) {
				
				JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
				CtrlLogErrores.guardarError(f.getMessage());
			}
		}
		
		if(e.getSource() == ventana.btn1A) {
			
			abrirSeleccionado();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}

	private void actualizar() {
		
		elemento = -1;
		dtosComras.setAño((String)ventana.comboBox1.getSelectedItem());
		dtosComras.setMes(ventana.comboBox2.getSelectedIndex());
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		derecha.setHorizontalAlignment(JLabel.RIGHT);
		ventana.tabla.setModel(dtosComras.getTablaCompras());
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(50);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(70);
		ventana.tabla.getColumnModel().getColumn(1).setMinWidth(20);
		ventana.tabla.getColumnModel().getColumn(1).setMaxWidth(50);
		ventana.tabla.getColumnModel().getColumn(4).setMinWidth(40);
		ventana.tabla.getColumnModel().getColumn(4).setMaxWidth(100);
		ventana.tabla.getColumnModel().getColumn(5).setMinWidth(40);
		ventana.tabla.getColumnModel().getColumn(5).setMaxWidth(75);
		ventana.tabla.getColumnModel().getColumn(5).setCellRenderer(derecha);
		ventana.tabla.setDefaultEditor(Object.class, null);
	}
	
	private void abrirSeleccionado() {
		
		if(elemento == -1) {
			
			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
			return;
		}
		
		if(ventanaDetalleCompra != null && ventanaDetalleCompra.isVisible())
			ventanaDetalleCompra.dispose();
		dtosComras.setOCSleccionada(elemento);
		elemento = -1;
		ventanaDetalleCompra = new Cobro("Detalle de la compra", ventana.getX(), ventana.getY());
		CtrlDetalleCompra ctrlDetalleCompra = new CtrlDetalleCompra(ventanaDetalleCompra);
		ctrlDetalleCompra.iniciar();
	}
}