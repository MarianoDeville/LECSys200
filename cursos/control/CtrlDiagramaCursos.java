package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.DtosCurso;
import vista.Listado;

public class CtrlDiagramaCursos implements ActionListener {
	
	private Listado ventana;
	private DtosCurso dtosCurso;
		
	public CtrlDiagramaCursos(Listado vista) {
		
		this.ventana = vista;
		this.dtosCurso = new DtosCurso();
		this.ventana.comboBox1.setVisible(true);
		this.ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosCurso.getListaCriterios()));
		this.ventana.comboBox2.setVisible(true);
		this.ventana.lblTxt1.setVisible(true);
		this.ventana.lblTxt1.setText("Cantidad de horas:");
		this.ventana.txt1.setVisible(true);
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.comboBox2.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}

	public void iniciar() {
		
		ventana.lblHorario.setVisible(true);
		ventana.lblHorario.setText("Horario:");
		ventana.lblLunes.setVisible(true);
		ventana.lblLunes.setText("Lunes");
		ventana.lblMartes.setVisible(true);
		ventana.lblMartes.setText("Martes");
		ventana.lblMiercoles.setVisible(true);
		ventana.lblMiercoles.setText("Miercoles");
		ventana.lblJueves.setVisible(true);
		ventana.lblJueves.setText("Jueves");
		ventana.lblViernes.setVisible(true);
		ventana.lblViernes.setText("Viernes");
		ventana.lblSabado.setVisible(true);
		ventana.lblSabado.setText("Sábado");
		ventana.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ventana.tabla.doLayout();
		actualizar();
		ventana.setVisible(true);
	}

	private void actualizar() {

		ventana.comboBox2.setModel(new DefaultComboBoxModel<>(dtosCurso.getListadoOpciones((String)ventana.comboBox1.getSelectedItem())));
		actualizarTabla();
	}
	
	private void actualizarTabla() {
		
		ventana.tabla.setModel(dtosCurso.getDiagramacion((String)ventana.comboBox1.getSelectedItem(), 
														 ventana.comboBox2.getSelectedIndex()));
		ventana.tabla.setEnabled(false);
		DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
		centrado.setHorizontalAlignment(JLabel.CENTER);
		
		for(int i = 0 ; i < ventana.tabla.getColumnCount() ; i++) {
			
			ventana.tabla.getColumnModel().getColumn(i).setPreferredWidth(40);
			ventana.tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
		}
		ventana.tabla.setRowHeight(25);
		ventana.txt1.setText(dtosCurso.getCantHoras());
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.comboBox1) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.comboBox2) {
			
			actualizarTabla();
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