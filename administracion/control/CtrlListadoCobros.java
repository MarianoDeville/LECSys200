package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;

import modelo.DtosCobros;
import vista.Listado;

public class CtrlListadoCobros implements ActionListener {

	private Listado ventana;
	private DtosCobros dtosCobros;
	private boolean bandera;
	
	public CtrlListadoCobros(Listado vista) {
		
		this.ventana = vista;
		this.dtosCobros = new DtosCobros();
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btn1A.addActionListener(this);
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.comboBox2.addActionListener(this);
	}
	
	public void iniciar() {

		bandera = false;
		ventana.lblComboBox1.setVisible(true);
		ventana.lblComboBox1.setText("Año:");
		ventana.comboBox1.setVisible(true);
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosCobros.getAños()));
		ventana.lblComboBox2.setVisible(true);
		ventana.lblComboBox2.setText("Mes:");
		ventana.comboBox2.setVisible(true);
		ventana.comboBox2.setModel(new DefaultComboBoxModel<String>(dtosCobros.getMeses()));
		ventana.comboBox2.setSelectedIndex(dtosCobros.getMesActual());
		ventana.lblTxt1.setVisible(true);
		ventana.lblTxt1.setText("Suma:");
		ventana.txt1.setVisible(true);
		ventana.txt1.setColumns(7);
		ventana.lblTxt2.setVisible(true);
		ventana.lblTxt2.setText("Cant. operaciones:");
		ventana.txt2.setVisible(true);
		ventana.txt2.setColumns(4);
		ventana.btn1A.setVisible(true);
		ventana.btn1A.setText("Guardar");
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

		if(e.getSource() == ventana.btn1A) {
			
			guardarCambio();
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
			
			dtosCobros.setBorrarSeleccionados();
			ventana.dispose();
		}
	}

	private void actualizar() {
		
		if(!bandera) {
			
			bandera = true;
			return;
		}
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		derecha.setHorizontalAlignment(JLabel.RIGHT);
		ventana.tabla.setModel(dtosCobros.getTablaCobros(ventana.comboBox2.getSelectedIndex(),
																	  ventana.comboBox1.getSelectedItem()));
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(90);
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(70);
		ventana.tabla.getColumnModel().getColumn(1).setMaxWidth(250);
		ventana.tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
		ventana.tabla.getColumnModel().getColumn(3).setMaxWidth(70);
		ventana.tabla.getColumnModel().getColumn(3).setPreferredWidth(60);
		ventana.tabla.getColumnModel().getColumn(4).setMaxWidth(80);
		ventana.tabla.getColumnModel().getColumn(4).setPreferredWidth(60);
		ventana.tabla.getColumnModel().getColumn(4).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(5).setMaxWidth(100);
		ventana.tabla.getColumnModel().getColumn(5).setPreferredWidth(95);
		ventana.txt2.setText(dtosCobros.getCantidadElementosSeleccionados() + "");
		ventana.txt1.setText(dtosCobros.getMontoTotal() + "");
	}
	
	private void guardarCambio() {
		
		String listaFacturas[] = new String[ventana.tabla.getRowCount()];
		
		for(int i = 0; i < listaFacturas.length; i++) {
			
			listaFacturas[i] = (String) ventana.tabla.getValueAt(i, 5);
		}
		
		if(!dtosCobros.setActualizarFacturas(listaFacturas))
			JOptionPane.showMessageDialog(null, "No se pudo actualizar la información.");
	}
}