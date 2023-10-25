package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import modelo.DtosAlumno;
import vista.Listado;

public class CtrlRegistroAsistencia implements ActionListener {

	private Listado ventana;
	private DtosAlumno dtosAlumno;
		
	public CtrlRegistroAsistencia(Listado vista) {
		
		this.ventana = vista;
		this.dtosAlumno = new DtosAlumno();
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.comboBox2.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.lblComboBox1.setVisible(true);
		ventana.lblComboBox1.setText("Curso:");
		ventana.comboBox1.setVisible(true);
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosAlumno.getListaCursos()));
		ventana.lblComboBox2.setVisible(true);
		ventana.lblComboBox2.setText("Mes:");
		ventana.comboBox2.setVisible(true);
		ventana.comboBox2.setModel(new DefaultComboBoxModel<String>(dtosAlumno.getListaMeses()));
		ventana.setVisible(true);
		actualizar();
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
			} catch (Exception f) {
				
				JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
				CtrlLogErrores.guardarError(f.getMessage());
			}
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}	
	
	private void actualizar() {
		
		ventana.tabla.setModel(dtosAlumno.getTablaRegistroAsistencia(ventana.comboBox1.getSelectedIndex(), 
																	ventana.comboBox2.getSelectedIndex()));
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(45);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(55);
		ventana.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ventana.tabla.doLayout();
		ventana.btnImprimir.setEnabled(ventana.tabla.getRowCount() != 0);

		if(!dtosAlumno.getMsg().equals(""))
			JOptionPane.showMessageDialog(null, dtosAlumno.getMsg());
	}
}