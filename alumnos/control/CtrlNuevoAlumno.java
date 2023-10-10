package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import modelo.DtosAlumno;
import vista.Nuevo;

public class CtrlNuevoAlumno implements ActionListener {
	
	private Nuevo ventana;
	private DtosAlumno dtosAlumno;
	
	public CtrlNuevoAlumno(Nuevo vista) {
		
		this.ventana = vista;
		this.dtosAlumno = new DtosAlumno();
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}

	public void iniciar() {

		ventana.lblcomboBox1.setText("Curso:");
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosAlumno.getListaCursos()));
		ventana.scrollTabla.setVisible(true);
		actualizar();
		ventana.setVisible(true);
	}
	
	private void actualizar() {
		
		ventana.tabla.setModel(dtosAlumno.getTablaDias(ventana.comboBox1.getSelectedIndex()));
		ventana.tabla.setEnabled(false);
	}
	
	private void limpiarCampos() {
		
		ventana.txtNombre.setText("");
		ventana.txtApellido.setText("");
		ventana.txtDNI.setText("");
		ventana.txtAño.setText("");
		ventana.txtMes.setText("");
		ventana.txtDia.setText("");
		ventana.txtDireccion.setText("");
		ventana.txtEmail.setText("");
		ventana.txtTelefono.setText("");
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.comboBox1) {
			
			actualizar();
		}

		if(e.getSource() == ventana.btnGuardar) {
			
			guardar();
		}		
		
		if(e.getSource() == ventana.btnVolver) {

			ventana.dispose();
		}
	}
	
	private void guardar() {
		
		dtosAlumno.setNombre(ventana.txtNombre.getText());
		dtosAlumno.setApellido(ventana.txtApellido.getText());
		dtosAlumno.setDni(ventana.txtDNI.getText());
		dtosAlumno.setFechaAño(ventana.txtAño.getText());
		dtosAlumno.setFechaMes(ventana.txtMes.getText());
		dtosAlumno.setFechaDia(ventana.txtDia.getText());
		dtosAlumno.setDireccion(ventana.txtDireccion.getText());
		dtosAlumno.setEmail(ventana.txtEmail.getText());
		dtosAlumno.setTelefono(ventana.txtTelefono.getText());
		dtosAlumno.setCurso(ventana.comboBox1.getSelectedIndex());
		String msgError = dtosAlumno.checkInformacion(true); 
		ventana.lblMsgError.setForeground(Color.RED);
		ventana.lblMsgError.setText(msgError);
		
		if(msgError.equals("")) {
			
			if(dtosAlumno.setNuevoAlumno()) {
				
				limpiarCampos();
				ventana.lblMsgError.setForeground(Color.BLUE);
				ventana.lblMsgError.setText("Los datos se guardaron correctamente.");
			} else {
				
				ventana.lblMsgError.setForeground(Color.RED);
				ventana.lblMsgError.setText("Error al intentar guardar la información.");
			}
		}
	}
}