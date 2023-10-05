package control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.DtosEmpleado;
import vista.Nuevo;

public class CtrlEditarEmpleado implements ActionListener {
	
	private Nuevo ventana;
	private DtosEmpleado dtosEmpleado;

	public CtrlEditarEmpleado(Nuevo vista) {
		
		this.ventana = vista;
		this.dtosEmpleado = new DtosEmpleado();
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.lblcomboBox1.setText("Sector:");
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosEmpleado.getListaSectores()));
		ventana.lblcomboBox2.setText("Relación:");
		ventana.comboBox2.setModel(new DefaultComboBoxModel<String>(dtosEmpleado.getListaTipos()));
		ventana.lblcomboBox2.setVisible(true);
		ventana.comboBox2.setVisible(true);
		ventana.lblTxt1.setText("Cargo:");
		ventana.lblTxt1.setVisible(true);
		ventana.txt1.setVisible(true);
		ventana.txt1.setColumns(13);
		ventana.lblTxt2.setText("Salario:");
		ventana.lblTxt2.setVisible(true);
		ventana.txt2.setVisible(true);
		ventana.setVisible(true);
		ventana.btnImprimir.setVisible(true);
		ventana.chkbox1.setVisible(true);
		ventana.setMinimumSize(new Dimension(450, 580));
		ventana.txtLegajo.setText(dtosEmpleado.getLegajo());
		ventana.txtNombre.setText(dtosEmpleado.getNombre());
		ventana.txtApellido.setText(dtosEmpleado.getApellido());
		ventana.txtDNI.setText(dtosEmpleado.getDni());
		ventana.txtDireccion.setText(dtosEmpleado.getDireccion());
		ventana.txtTelefono.setText(dtosEmpleado.getTelefono());
		ventana.txtEmail.setText(dtosEmpleado.getEmail());
		ventana.txtAño.setText(dtosEmpleado.getAñoNacimiento());
		ventana.txtMes.setText(dtosEmpleado.getMesNacimiento());
		ventana.txtDia.setText(dtosEmpleado.getDiaNacimiento());
		ventana.txt1.setText(dtosEmpleado.getCargo());
		ventana.txt2.setText(dtosEmpleado.getSalario());
		ventana.chkbox1.setSelected(dtosEmpleado.getEstado());
		ventana.comboBox1.setSelectedItem(dtosEmpleado.getSector());
		ventana.comboBox2.setSelectedItem(dtosEmpleado.getRelacion());
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnGuardar) {
			
			dtosEmpleado.setNombre(ventana.txtNombre.getText());
			dtosEmpleado.setApellido(ventana.txtApellido.getText());
			dtosEmpleado.setDni(ventana.txtDNI.getText());
			dtosEmpleado.setDireccion(ventana.txtDireccion.getText());
			dtosEmpleado.setTelefono(ventana.txtTelefono.getText());
			dtosEmpleado.setEmail(ventana.txtEmail.getText());
			dtosEmpleado.setAñoNacimiento(ventana.txtAño.getText());
			dtosEmpleado.setMesNacimiento(ventana.txtMes.getText());
			dtosEmpleado.setDiaNacimiento(ventana.txtDia.getText());
			dtosEmpleado.setCargo(ventana.txt1.getText());
			dtosEmpleado.setSalario(ventana.txt2.getText());
			dtosEmpleado.setSector((String)ventana.comboBox1.getSelectedItem());
			dtosEmpleado.setRelacion((String)ventana.comboBox2.getSelectedItem());
			dtosEmpleado.setEstado(ventana.chkbox1.isSelected()?1:0);
			String msgError = dtosEmpleado.checkInformacion();
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText(msgError);
			
			if(msgError.equals("")) {
			
				if(dtosEmpleado.setActualizarEmpleado()) {
					
					ventana.lblMsgError.setForeground(Color.BLUE);
					ventana.lblMsgError.setText("Registro guardado con éxito.");
					ventana.btnGuardar.setEnabled(false);
				} else {
					
					ventana.lblMsgError.setForeground(Color.RED);
					ventana.lblMsgError.setText("No se pudo guardar la información.");
				}
			}
		}
		
		if(e.getSource() == ventana.btnImprimir) {
			
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
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}	
}