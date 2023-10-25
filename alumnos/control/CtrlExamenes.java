package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.DtosAlumno;
import vista.Listado;

public class CtrlExamenes implements ActionListener {

	private Listado ventana;
	private DtosAlumno dtosAlumno;
	
	public CtrlExamenes(Listado vista) {
		
		this.ventana = vista;
		this.dtosAlumno = new DtosAlumno();
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.comboBox2.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btn1B.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.btn1B.setVisible(true);
		ventana.btn1B.setText("Guardar");
		ventana.lblComboBox1.setVisible(true);
		ventana.lblComboBox1.setText("Curso:");
		ventana.comboBox1.setVisible(true);
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosAlumno.getListaCursos()));
		ventana.lblComboBox2.setVisible(true);
		ventana.lblComboBox2.setText("Examen");
		ventana.comboBox2.setVisible(true);
		ventana.comboBox2.setModel(new DefaultComboBoxModel<String>(dtosAlumno.getListaTipoExamen()));
		ventana.lblTxt1.setVisible(true);
		ventana.lblTxt1.setText("Fecha");
		ventana.txt1.setVisible(true);
		ventana.txt1.setEditable(true);
		ventana.lblTxt1Der.setVisible(true);
		ventana.lblTxt1Der.setText("DD-MM-AAAA");
		actualizar();
		ventana.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.comboBox1 && ventana.isVisible()) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.comboBox2) {
			
			ventana.btn1B.setEnabled(ventana.tabla.getRowCount() != 0);
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			guardar();
		}
		
		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch (PrinterException f) {
				
				f.printStackTrace();
			}
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void actualizar() {
		
		ventana.tabla.setModel(dtosAlumno.getTablaExamenes(ventana.comboBox1.getSelectedIndex()));
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
		ventana.btn1B.setEnabled(ventana.tabla.getRowCount() != 0);
		ventana.btnImprimir.setEnabled(ventana.tabla.getRowCount() != 0);
	}
	
	private void guardar() {
		
		dtosAlumno.setDatosExamen(ventana.comboBox1.getSelectedIndex());
		dtosAlumno.setTipoExamen((String)ventana.comboBox2.getSelectedItem());

		if(dtosAlumno.setFecha(ventana.txt1.getText())) {
			
			if(dtosAlumno.guardarResultados(ventana.tabla))
				ventana.btn1B.setEnabled(false);
		}
		JOptionPane.showMessageDialog(null,dtosAlumno.getMsg());
	}
}