package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.DtosCambioPassword;
import vista.CambioContrase�a;

public class CtrlCambioPassword implements ActionListener {
	
	private CambioContrase�a ventanaCambioPass;
	private DtosCambioPassword dtosCambioPassword;
	
	public CtrlCambioPassword(CambioContrase�a vista) {
		
		this.ventanaCambioPass = vista;
		this.dtosCambioPassword = new DtosCambioPassword();
		this.ventanaCambioPass.btnCancelar.addActionListener(this);
		this.ventanaCambioPass.btnOk.addActionListener(this);
	}

	public void iniciar() {
		
		ventanaCambioPass.txtUsuario.setText(dtosCambioPassword.getNombreUsuario());
		ventanaCambioPass.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventanaCambioPass.btnCancelar) {
			
			ventanaCambioPass.dispose();
		}
		
		if(e.getSource() == ventanaCambioPass.btnOk) {
	
			dtosCambioPassword.setContrase�aOld(ventanaCambioPass.txtPasswordOld.getPassword());
			dtosCambioPassword.setContrase�aNew(ventanaCambioPass.txtPasswordNew.getPassword());
			dtosCambioPassword.setReContrase�aNew(ventanaCambioPass.txtRePasswordNew.getPassword());
			String msgError = dtosCambioPassword.checkInformacion(true);
			ventanaCambioPass.txtError.setForeground(Color.RED);
			ventanaCambioPass.txtError.setText(msgError);
			
			if(msgError.equals("")) {
			
				if(dtosCambioPassword.setNuevaContrase�a()) {

					ventanaCambioPass.dispose();
					
				} else {
					
					ventanaCambioPass.txtError.setForeground(Color.RED);
					ventanaCambioPass.txtError.setText("Error al intentar guardar la informaci�n.");
				}
			} else {
				
				ventanaCambioPass.txtPasswordOld.setText("");
				ventanaCambioPass.txtPasswordNew.setText("");
				ventanaCambioPass.txtRePasswordNew.setText("");
			}
		}
	}
}