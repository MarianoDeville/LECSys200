package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.OperadorSistema;
import modelo.DtosConfiguracion;
import vista.NuevoUsuario;

public class CtrlEditarEmail implements ActionListener {

	private NuevoUsuario ventana;
	private DtosConfiguracion dtosEmail;

	public CtrlEditarEmail(NuevoUsuario vista) {
		
		this.ventana = vista;
		this.dtosEmail = new DtosConfiguracion();
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {
		
		if(!OperadorSistema.isAcceso()) {
			
			ventana.dispose();
			return;
		}
		ventana.btnBorrar.setVisible(false);
		ventana.comboBox1.setVisible(false);
		ventana.lblcomboBox1.setVisible(false);
		ventana.lblNombre.setText("Email: ");
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
		
		if(e.getSource() == ventana.btnGuardar) {

			guardar();
		}
	}
	
	private void guardar() {
		
		String msg = dtosEmail.setEmail(ventana.txtUsuario.getText(),
					 ventana.txtContraseña.getPassword(),
					 ventana.txtReContraseña.getPassword());
		if(msg.equals("")) {
		
		ventana.lblMsgError.setForeground(Color.BLUE);
		ventana.lblMsgError.setText("Se guardó correctamente la configuración del email.");
		} else {
		
		ventana.lblMsgError.setForeground(Color.RED);
		ventana.lblMsgError.setText(msg); 
		}
	}
}