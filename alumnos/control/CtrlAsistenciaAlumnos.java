package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import modelo.DtosAlumno;
import vista.Listado;

public class CtrlAsistenciaAlumnos implements ActionListener {

	private Listado ventana;
	private DtosAlumno dtosAlumno;
	
	public CtrlAsistenciaAlumnos(Listado vista) {
		
		this.ventana = vista;
		this.dtosAlumno = new DtosAlumno();
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btn1B.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}

	public void iniciar() {

		ventana.btn1B.setVisible(true);
		ventana.btn1B.setText("Guardar");
		ventana.lblComboBox1.setVisible(true);
		ventana.lblComboBox1.setText("Curso");
		ventana.comboBox1.setVisible(true);
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosAlumno.getListaCursos()));
		ventana.lblTxt1.setVisible(true);
		ventana.lblTxt1.setText("Fecha");
		ventana.txt1.setVisible(true);
		ventana.txt1.setText(dtosAlumno.getFechaActual(true));
		actualizar();
		ventana.setVisible(true);
	}
	
	private void actualizar() {
		
		ventana.tabla.setModel(dtosAlumno.getTablaAsistencia(ventana.comboBox1.getSelectedIndex()));
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(60);
		ventana.tabla.getColumnModel().getColumn(3).setPreferredWidth(65);
		ventana.tabla.getColumnModel().getColumn(3).setMaxWidth(70);
		ventana.tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
		ventana.tabla.getColumnModel().getColumn(4).setMaxWidth(80);
		ventana.btn1B.setEnabled(true);
	}
	
	public void actionPerformed(ActionEvent e) {
	
		if(e.getSource() == ventana.comboBox1) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			for(int i = 0 ; i < ventana.tabla.getRowCount() ; i++) {
				
				dtosAlumno.setTablaAsistencia(i, 3, (boolean)ventana.tabla.getValueAt(i, 3));
				dtosAlumno.setTablaAsistencia(i, 4, (boolean)ventana.tabla.getValueAt(i, 4));
			}
			dtosAlumno.setCurso(ventana.comboBox1.getSelectedIndex());
			
			if(dtosAlumno.guardoAsistencia()) 
				ventana.btn1B.setEnabled(false);
			JOptionPane.showMessageDialog(null, dtosAlumno.getMsg());
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