package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.DtosAlumno;
import vista.Nuevo;

public class CtrlEditarAlumno implements ActionListener {
	
	private Nuevo ventana;
	private DtosAlumno dtosAlumno;

	public CtrlEditarAlumno(Nuevo vista) {
		
		this.ventana = vista;
		this.dtosAlumno = new DtosAlumno();
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.chkbox1.setVisible(false);
		ventana.btnImprimir.setVisible(true);
		ventana.scrollTabla.setVisible(true);
		ventana.lblTxt1.setVisible(true);
		ventana.txt1.setVisible(true);
		ventana.txt1.setEditable(false);
		ventana.txt1.setColumns(7);		
		ventana.lblTxt2.setVisible(!dtosAlumno.getEstado());
		ventana.lblTxt2.setText("Fecha baja: ");
		ventana.txt2.setVisible(!dtosAlumno.getEstado());
		ventana.txt2.setEditable(false);
		ventana.txt2.setColumns(7);
		ventana.txtDNI.setEditable(false);
		ventana.txtLegajo.setText(dtosAlumno.getLegajo());
		ventana.txtNombre.setText(dtosAlumno.getNombre());
		ventana.txtApellido.setText(dtosAlumno.getApellido());
		ventana.txtDNI.setText(dtosAlumno.getDni());
		ventana.txtDireccion.setText(dtosAlumno.getDireccion());
		ventana.txtTelefono.setText(dtosAlumno.getTelefono());
		ventana.txtEmail.setText(dtosAlumno.getEmail());
		ventana.txtAño.setText(dtosAlumno.getFechaAño());
		ventana.txtMes.setText(dtosAlumno.getFechaMes());
		ventana.txtDia.setText(dtosAlumno.getFechaDia());
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosAlumno.getListaCursos()));
		ventana.comboBox1.setSelectedIndex(dtosAlumno.getCursoSeleccionado());
		ventana.lblTxt1.setText("Ingreso:");
		ventana.txt1.setText(dtosAlumno.getFechaIngreso());
		ventana.txt2.setText(dtosAlumno.getFechaBaja());
		actualizar();
		ventana.setVisible(true);
	}
	
	private void actualizar() {
		
		ventana.tabla.setModel(dtosAlumno.getTablaDias(ventana.comboBox1.getSelectedIndex()));
		ventana.tabla.setEnabled(false);
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.comboBox1 && ventana.isVisible()) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardar();
		}
		
		if(e.getSource() == ventana.btnImprimir) {

			imprimir();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			dtosAlumno.limpiarInformacion();
			ventana.dispose();
		}
	}
	
	private void guardar() {
		
		dtosAlumno.setNombre(ventana.txtNombre.getText());
		dtosAlumno.setApellido(ventana.txtApellido.getText());
		dtosAlumno.setDireccion(ventana.txtDireccion.getText());
		dtosAlumno.setTelefono(ventana.txtTelefono.getText());
		dtosAlumno.setEmail(ventana.txtEmail.getText());
		dtosAlumno.setFechaAño(ventana.txtAño.getText());
		dtosAlumno.setFechaMes(ventana.txtMes.getText());
		dtosAlumno.setFechaDia(ventana.txtDia.getText());
		dtosAlumno.setCurso(ventana.comboBox1.getSelectedIndex());
		String msgError = dtosAlumno.checkInformacion(false); 
		ventana.lblMsgError.setForeground(Color.RED);
		ventana.lblMsgError.setText(msgError);

		if(msgError.equals("")) {
			
			if(dtosAlumno.setActualizarAlumno()) {
				
				ventana.lblMsgError.setForeground(Color.BLUE);
				ventana.lblMsgError.setText("Los datos se guardaron correctamente.");
				ventana.btnGuardar.setEnabled(false);
				dtosAlumno.limpiarInformacion();
			} else {
			
				ventana.lblMsgError.setForeground(Color.RED);
				ventana.lblMsgError.setText("Error al intentar guardar la información.");
			}
		}
	}
	private void imprimir() {
		
		Color colorPanel = ventana.panel.getBackground();
		Color colorComboBox = ventana.comboBox1.getBackground();
		ventana.panel.setBackground(Color.WHITE);
		ventana.comboBox1.setBackground(Color.WHITE);
		ventana.btnGuardar.setVisible(false);
		ventana.btnImprimir.setVisible(false);
		ventana.btnVolver.setVisible(false);
		PrinterJob imprimir = PrinterJob.getPrinterJob();
		PageFormat preformat = imprimir.defaultPage();
		PageFormat postformat = imprimir.pageDialog(preformat);
		imprimir.setPrintable(new Printer(ventana.panel), postformat);
		
		if (imprimir.printDialog()) {
			
			try {
				
					imprimir.print();
			} catch (PrinterException f) {
				
				JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
				CtrlLogErrores.guardarError(f.getMessage());
			}
		}
		ventana.panel.setBackground(colorPanel);
		ventana.comboBox1.setBackground(colorComboBox);
		ventana.btnGuardar.setVisible(true);
		ventana.btnImprimir.setVisible(true);
		ventana.btnVolver.setVisible(true);
	}
}