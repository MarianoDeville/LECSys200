package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.DtosPagos;
import vista.Listado;

public class CtrlListadoPagos implements ActionListener {

	private Listado ventana;
	private DtosPagos dtosPagos;
	
	public CtrlListadoPagos(Listado vista) {
		
		this.ventana = vista;
		this.dtosPagos = new DtosPagos();
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.comboBox2.addActionListener(this);
		this.ventana.chckbx1.addActionListener(this);
		this.ventana.chckbx2.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.lblComboBox1.setVisible(true);
		ventana.lblComboBox1.setText("Año:");
		ventana.comboBox1.setVisible(true);
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosPagos.getAños()));
		
		if(dtosPagos.getAños().length > 1)
			ventana.comboBox1.setSelectedIndex(1);
		ventana.lblComboBox2.setVisible(true);
		ventana.lblComboBox2.setText("Mes:");
		ventana.comboBox2.setVisible(true);
		ventana.comboBox2.setModel(new DefaultComboBoxModel<String>(dtosPagos.listadoMeses()));
		ventana.comboBox2.setSelectedIndex(dtosPagos.getMesActual());
		ventana.lblTxt1.setVisible(true);
		ventana.lblTxt1.setText("Suma:");
		ventana.txt1.setVisible(true);
		ventana.txt1.setColumns(7);
		ventana.lblTxt2.setVisible(true);
		ventana.lblTxt2.setText("Cant. operaciones:");
		ventana.txt2.setVisible(true);
		ventana.txt2.setColumns(4);
		ventana.chckbx1.setVisible(true);
		ventana.chckbx1.setText("Empleados");
		ventana.chckbx1.setSelected(true);
		ventana.chckbx2.setVisible(true);
		ventana.chckbx2.setText("Prov.");
		ventana.chckbx2.setSelected(true);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch (PrinterException f) {
				
				JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
				CtrlLogErrores.guardarError(f.getMessage());
			}
			return;
		}

		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
			return;
		}
		actualizar();
	}

	private void actualizar() {
	
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		derecha.setHorizontalAlignment(JLabel.RIGHT);
		ventana.tabla.setModel(dtosPagos.getTablaHistorial((String)ventana.comboBox1.getSelectedItem(),
														   ventana.comboBox2.getSelectedIndex(), 
														   ventana.chckbx1.isSelected(),
														   ventana.chckbx2.isSelected()));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(55);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(90);
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(55);
		ventana.tabla.getColumnModel().getColumn(1).setMinWidth(70);
		ventana.tabla.getColumnModel().getColumn(1).setMaxWidth(150);
		ventana.tabla.getColumnModel().getColumn(1).setPreferredWidth(90);	
		ventana.tabla.getColumnModel().getColumn(2).setMinWidth(70);
		ventana.tabla.getColumnModel().getColumn(2).setMaxWidth(150);
		ventana.tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
		ventana.tabla.getColumnModel().getColumn(3).setMinWidth(60);
		ventana.tabla.getColumnModel().getColumn(3).setMaxWidth(100);
		ventana.tabla.getColumnModel().getColumn(3).setPreferredWidth(70);		
		ventana.tabla.getColumnModel().getColumn(3).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(4).setMinWidth(70);
		ventana.tabla.getColumnModel().getColumn(4).setMaxWidth(110);
		ventana.tabla.getColumnModel().getColumn(4).setPreferredWidth(95);		
		ventana.tabla.getColumnModel().getColumn(4).setCellRenderer(derecha);		
		ventana.tabla.getColumnModel().getColumn(5).setMinWidth(70);
		ventana.tabla.getColumnModel().getColumn(6).setMinWidth(70);
		ventana.tabla.setDefaultEditor(Object.class, null);
		ventana.txt2.setText(ventana.tabla.getRowCount() + "");
		ventana.txt1.setText(dtosPagos.getSuma());
	}
}