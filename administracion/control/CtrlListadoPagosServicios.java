package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.DtosPagos;
import vista.Listado;

public class CtrlListadoPagosServicios implements ActionListener {
	
	private Listado ventana;
	private DtosPagos dtosPagos;

	public CtrlListadoPagosServicios(Listado vista) {
		
		this.ventana = vista;
		this.dtosPagos = new DtosPagos();
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.comboBox2.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {
		
		ventana.lblComboBox1.setText("Año:");
		ventana.lblComboBox1.setVisible(true);
		ventana.comboBox1.setVisible(true);
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosPagos.listadoAños()));
		ventana.lblComboBox2.setText("Mes:");
		ventana.lblComboBox2.setVisible(true);
		ventana.comboBox2.setVisible(true);
		ventana.comboBox2.setModel(new DefaultComboBoxModel<String>(dtosPagos.listadoMeses()));
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.comboBox1 && ventana.isVisible()) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.comboBox2 && ventana.isVisible()) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch(PrinterException f) {
				
				JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
				CtrlLogErrores.guardarError(f.getMessage());
			}
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void actualizar() {
	
		ventana.tabla.setModel(dtosPagos.getTablaPagosProveedor((String)ventana.comboBox1.getSelectedItem(), 
				(int)ventana.comboBox2.getSelectedIndex()));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(30);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(60);
		ventana.tabla.getColumnModel().getColumn(1).setMinWidth(60);
		ventana.tabla.getColumnModel().getColumn(1).setMaxWidth(90);
		ventana.tabla.setDefaultEditor(Object.class, null);
	}
}