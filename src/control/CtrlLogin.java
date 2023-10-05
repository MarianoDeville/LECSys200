package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import dao.OperadorSistema;
import vista.IngresoUsuario;
import vista.Principal;

public class CtrlLogin implements ActionListener {
	
	private IngresoUsuario ventanaLogin;
	
	public CtrlLogin(IngresoUsuario vista) {
		
		this.ventanaLogin = vista;
		this.ventanaLogin.btnCancelar.addActionListener(this);
		this.ventanaLogin.btnOk.addActionListener(this);
		this.ventanaLogin.txtUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if (e.getKeyCode() >= 32)
					ventanaLogin.txtError.setText("");
			}
		});
		this.ventanaLogin.txtPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				if (e.getKeyCode() >= 32)
					ventanaLogin.txtError.setText("");
			}
		});
	}

	public void iniciar() {
		
		ventanaLogin.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventanaLogin.btnCancelar) {
			
			System.exit(0);
		}
		
		if(e.getSource() == ventanaLogin.btnOk) {
			
			if(ventanaLogin.txtUsuario.getText().contentEquals("")) {
				
				ventanaLogin.txtError.setText("El campo nombre se usuario no puede estar vacío.");
			} else {
				
				OperadorSistema comprobarLogin = new OperadorSistema();
				comprobarLogin.setNombre(ventanaLogin.txtUsuario.getText());
				comprobarLogin.setPass(ventanaLogin.txtPassword);
				
				if(!comprobarLogin.checkUsuario()) {
					
					ventanaLogin.txtPassword.setText("");
					ventanaLogin.txtError.setText("Usuario o contraseña incorrectos.");
				} else {
	
					ventanaLogin.dispose();
					Principal ventanaPrincipal = new Principal("Sistema de gestión - LECSys");
					CtrlPrincipal ctrlPrincipal = new CtrlPrincipal(ventanaPrincipal);
					ctrlPrincipal.iniciar();
				}
			}
		}
	}
}