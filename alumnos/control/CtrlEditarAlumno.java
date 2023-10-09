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
	private DtosAlumno dtosEditarAlumno;

	public CtrlEditarAlumno(Nuevo vista) {
		
		this.ventana = vista;
		this.dtosEditarAlumno = new DtosAlumno();
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.btnImprimir.setVisible(true);
		ventana.scrollTabla.setVisible(true);
		ventana.chkbox1.setVisible(true);
		ventana.lblTxt1.setVisible(true);
		ventana.txt1.setVisible(true);
		ventana.txt1.setEditable(false);
		ventana.txt1.setColumns(7);
		dtosEditarAlumno.recuperarInformacionAlumno(dtosEditarAlumno.getEstado());
		ventana.txtLegajo.setText(dtosEditarAlumno.getLegajo());
		ventana.txtNombre.setText(dtosEditarAlumno.getNombre());
		ventana.txtApellido.setText(dtosEditarAlumno.getApellido());
		ventana.txtDNI.setText(dtosEditarAlumno.getDni());
		ventana.txtDireccion.setText(dtosEditarAlumno.getDireccion());
		ventana.txtTelefono.setText(dtosEditarAlumno.getTelefono());
		ventana.txtEmail.setText(dtosEditarAlumno.getEmail());
		ventana.txtAño.setText(dtosEditarAlumno.getFechaAño());
		ventana.txtMes.setText(dtosEditarAlumno.getFechaMes());
		ventana.txtDia.setText(dtosEditarAlumno.getFechaDia());
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosEditarAlumno.getListaCursos()));
		ventana.comboBox1.setSelectedIndex(dtosEditarAlumno.getCursoSeleccionado());
		ventana.lblTxt1.setText("Ingreso:");
		ventana.txt1.setText(dtosEditarAlumno.getFechaIngreso());
		ventana.chkbox1.setSelected(dtosEditarAlumno.getEstado());
		actualizar();
		ventana.setVisible(true);
	}
	
	private void actualizar() {
		
		ventana.tabla.setModel(dtosEditarAlumno.getTablaDias(ventana.comboBox1.getSelectedIndex()));
		ventana.tabla.setEnabled(false);
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.comboBox1) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardar();
		}
		
		if(e.getSource() == ventana.btnImprimir) {

			imprimir();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			dtosEditarAlumno.limpiarInformacion();
			ventana.dispose();
		}
	}
	
	private void guardar() {
		
		dtosEditarAlumno.setNombre(ventana.txtNombre.getText());
		dtosEditarAlumno.setApellido(ventana.txtApellido.getText());
		dtosEditarAlumno.setDni(ventana.txtDNI.getText());
		dtosEditarAlumno.setDireccion(ventana.txtDireccion.getText());
		dtosEditarAlumno.setTelefono(ventana.txtTelefono.getText());
		dtosEditarAlumno.setEmail(ventana.txtEmail.getText());
		dtosEditarAlumno.setFechaAño(ventana.txtAño.getText());
		dtosEditarAlumno.setFechaMes(ventana.txtMes.getText());
		dtosEditarAlumno.setFechaDia(ventana.txtDia.getText());
		dtosEditarAlumno.setEstado(ventana.chkbox1.isSelected());
		dtosEditarAlumno.setCurso(ventana.comboBox1.getSelectedIndex());
		String msgError = dtosEditarAlumno.checkInformacion(false); 
		ventana.lblMsgError.setForeground(Color.RED);
		ventana.lblMsgError.setText(msgError);

		if(msgError.equals("")) {
			
			if(dtosEditarAlumno.setActualizarAlumno()) {
				
				ventana.lblMsgError.setForeground(Color.BLUE);
				ventana.lblMsgError.setText("Los datos se guardaron correctamente.");
			} else {
			
				ventana.lblMsgError.setForeground(Color.RED);
				ventana.lblMsgError.setText("Error al intentar guardar la información.");
			}
		}
	}
	private void imprimir() {
		
		Color colorPanel = ventana.panel.getBackground();
		Color colorChckbox = ventana.chkbox1.getBackground();
		Color colorComboBox = ventana.comboBox1.getBackground();
		ventana.panel.setBackground(Color.WHITE);
		ventana.chkbox1.setBackground(Color.WHITE);
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
		ventana.chkbox1.setBackground(colorChckbox);
		ventana.comboBox1.setBackground(colorComboBox);
		ventana.btnGuardar.setVisible(true);
		ventana.btnImprimir.setVisible(true);
		ventana.btnVolver.setVisible(true);
	}
}