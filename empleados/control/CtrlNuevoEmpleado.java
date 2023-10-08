package control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import modelo.DtosEmpleado;
import vista.Nuevo;

public class CtrlNuevoEmpleado implements ActionListener {

	private Nuevo ventana;
	private DtosEmpleado dtosEmpleado;
	
	public CtrlNuevoEmpleado(Nuevo vista) {
		
		this.ventana = vista;
		this.dtosEmpleado = new DtosEmpleado();
		this.ventana.btnGuardar.addActionListener(this);
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
		ventana.setMinimumSize(new Dimension(450, 580));
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardar();
		}

		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void guardar() {
		
		String msgError = "";

		try {

			dtosEmpleado.setNombre(ventana.txtNombre.getText());
			dtosEmpleado.setApellido(ventana.txtApellido.getText());
			dtosEmpleado.setDni(ventana.txtDNI.getText());
			dtosEmpleado.setAñoNacimiento(ventana.txtAño.getText()); 
			dtosEmpleado.setMesNacimiento(ventana.txtMes.getText()); 
			dtosEmpleado.setDiaNacimiento(ventana.txtDia.getText());
			dtosEmpleado.setDireccion(ventana.txtDireccion.getText());
			dtosEmpleado.setEmail(ventana.txtEmail.getText());
			dtosEmpleado.setTelefono(ventana.txtTelefono.getText());
			dtosEmpleado.setSector((String) ventana.comboBox1.getSelectedItem());
			dtosEmpleado.setRelacion((String) ventana.comboBox2.getSelectedItem());
			dtosEmpleado.setCargo(ventana.txt1.getText());
			dtosEmpleado.setSalario(ventana.txt2.getText());
		} finally {
		
			msgError = dtosEmpleado.checkInformacion();
		}
		ventana.lblMsgError.setForeground(Color.RED);
		ventana.lblMsgError.setText(msgError);

		if(msgError.equals("")) {
			
			if(dtosEmpleado.setNuevoEmpleado()) {
				
				ventana.lblMsgError.setForeground(Color.BLUE);
				ventana.lblMsgError.setText("Registro guardado con éxito.");
				bloquear();
			} else {
				
				ventana.lblMsgError.setForeground(Color.RED);
				ventana.lblMsgError.setText("No se pudo guardar la información.");
			}
		}
	}
	
	private void bloquear() {
		
		ventana.btnGuardar.setEnabled(false);
		ventana.comboBox1.setEnabled(false);
		ventana.comboBox2.setEnabled(false);
		ventana.txtNombre.setEnabled(false);
		ventana.txtApellido.setEnabled(false);
		ventana.txtDNI.setEnabled(false);
		ventana.txtAño.setEnabled(false); 
		ventana.txtMes.setEnabled(false); 
		ventana.txtDia.setEnabled(false);
		ventana.txtDireccion.setEnabled(false);
		ventana.txtEmail.setEnabled(false);
		ventana.txtTelefono.setEnabled(false);
		ventana.txt1.setEnabled(false);
		ventana.txt2.setEnabled(false);
	}
}