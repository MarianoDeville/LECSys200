package control;

import modelo.DtosActividad;
import vista.Listado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import dao.OperadorSistema;

public class CtrlActividad implements ActionListener {

	private Listado ventana;
	private DtosActividad dtosActividad;
	private boolean bandera;
	
	public CtrlActividad(Listado vista) {
		
		this.ventana = vista;
		this.dtosActividad = new DtosActividad();
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.comboBox2.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
		
	public void iniciar() {

		if(!OperadorSistema.isAcceso()) {
			
			ventana.dispose();
			return;
		}
		bandera = false;
		ventana.lblComboBox1.setText("Mes:");
		ventana.lblComboBox1.setVisible(true);
		ventana.comboBox1.setVisible(true);
		ventana.lblComboBox2.setText("Año:");
		ventana.lblComboBox2.setVisible(true);
		ventana.comboBox2.setVisible(true);
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosActividad.getMeses()));
		ventana.comboBox1.setSelectedIndex(dtosActividad.getMesActual());
		ventana.comboBox2.setModel(new DefaultComboBoxModel<String>(dtosActividad.getAños()));
		actualizar();
		ventana.setVisible(true);
	}
	
	private void actualizar() {
		
		ventana.tabla.setModel(dtosActividad.getTablaActividad(ventana.comboBox1.getSelectedIndex() + 1 + "", (String)ventana.comboBox2.getSelectedItem()));
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(45);
		ventana.tabla.getColumnModel().getColumn(1).setMaxWidth(120);
		ventana.tabla.getColumnModel().getColumn(2).setMaxWidth(90);
		ventana.tabla.getColumnModel().getColumn(3).setMaxWidth(70);
		ventana.tabla.getColumnModel().getColumn(5).setMaxWidth(220);
		ventana.tabla.getColumnModel().getColumn(5).setPreferredWidth(130);
		ventana.tabla.getColumnModel().getColumn(6).setMaxWidth(110);
		ventana.tabla.setDefaultEditor(Object.class, null);
		bandera = true;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.comboBox1) {
			
			if(bandera)
				actualizar();
		}
		
		if(e.getSource() == ventana.comboBox2) {
			
			if(bandera)
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

		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
}