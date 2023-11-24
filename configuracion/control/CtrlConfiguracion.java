package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import modelo.DtosConfiguracion;
import vista.ABML;
import vista.CambioContraseña;
import vista.InterfaceBotones;
import vista.Listado;
import vista.NuevoUsuario;

public class CtrlConfiguracion implements ActionListener {

	private InterfaceBotones ventana;
	private Listado ventanaActividad;
	private ABML ventanaUsuarios;
	private CambioContraseña ventanaCambioPass;
	private NuevoUsuario ventanaEmail;

	public CtrlConfiguracion(InterfaceBotones vista) {
		
		this.ventana = vista;
		this.ventana.btn1A.addActionListener(this);
		this.ventana.btn1B.addActionListener(this);
		this.ventana.btn1C.addActionListener(this);
		this.ventana.btn2A.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.lbl1A.setText("Actividad");
		ventana.btn1A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Actividades.png"));
		ventana.btn1A.setVisible(true);
		ventana.lbl1B.setText("ABML usuarios");
		ventana.btn1B.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\ABML.png"));
		ventana.btn1B.setVisible(true);
		ventana.lbl1C.setText("Cambiar contraseña");
		ventana.btn1C.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Cambio contraseña.png"));
		ventana.btn1C.setVisible(true);
		ventana.lbl2A.setText("Email");
		ventana.btn2A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Email.png"));
		ventana.btn2A.setVisible(true);
		
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btn1A) {
			
			actividad();
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			usuariosABML();
		}
		
		if(e.getSource() == ventana.btn1C) {
			
			cambioPass();
		}
		
		if(e.getSource() == ventana.btn2A) {
			
			email();
		}

		if(e.getSource() == ventana.btnVolver) {
			
			if(ventanaActividad != null && ventanaActividad.isVisible())
				ventanaActividad.dispose();
			
			if(ventanaUsuarios != null && ventanaUsuarios.isVisible())
				ventanaUsuarios.dispose();
			
			if(ventanaCambioPass != null && ventanaCambioPass.isVisible())
				ventanaCambioPass.dispose();
			
			if(ventanaEmail != null && ventanaEmail.isVisible())
				ventanaEmail.dispose();
			ventana.dispose();
		}
	}
	
	private void actividad() {
		
		if(ventanaActividad != null && ventanaActividad.isVisible()) {
			
			ventanaActividad.setVisible(true);
			return;
		}
		ventanaActividad = new Listado("Listado de actividad", ventana.getX(), ventana.getY());
		CtrlActividad ctrlActividad = new CtrlActividad(ventanaActividad);
		ctrlActividad.iniciar();
	}
	
	private void usuariosABML() {
		
		if(ventanaUsuarios != null && ventanaUsuarios.isVisible()) {
			
			ventanaUsuarios.setVisible(true);
			return;
		}
		ventanaUsuarios = new ABML("Alta Baja Modificación de usuarios", ventana.getX(), ventana.getY());
		CtrlABMLUsuarios ctrlABMLUsuarios = new CtrlABMLUsuarios(ventanaUsuarios);
		ctrlABMLUsuarios.iniciar();
	}
	
	private void cambioPass() {
		
		if(ventanaCambioPass != null && ventanaCambioPass.isVisible()) {
			
			ventanaCambioPass.setVisible(true);
			return;
		}
		ventanaCambioPass = new CambioContraseña();
		CtrlCambioPassword ctrlCambioPass = new CtrlCambioPassword(ventanaCambioPass);
		ctrlCambioPass.iniciar();
	}
	
	private void email() {
		
		if(ventanaEmail != null && ventanaEmail.isVisible()) {
			
			ventanaEmail.setVisible(true);
			return;
		}
		ventanaEmail = new NuevoUsuario("Edición email del sistema", ventana.getX(), ventana.getY());
		CtrlEditarEmail ctrolEditarEmail = new CtrlEditarEmail(ventanaEmail);
		ctrolEditarEmail.iniciar();
	}
}