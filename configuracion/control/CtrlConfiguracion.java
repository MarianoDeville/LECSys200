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
			
			Listado ventanaActividad = new Listado("Listado de actividad");
			CtrlActividad ctrlActividad = new CtrlActividad(ventanaActividad);
			ctrlActividad.iniciar();
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			ABML ventanaUsuarios = new ABML("Alta Baja Modificación de usuarios");
			CtrlABMLUsuarios ctrlABMLUsuarios = new CtrlABMLUsuarios(ventanaUsuarios);
			ctrlABMLUsuarios.iniciar();
		}
		
		if(e.getSource() == ventana.btn1C) {
			
			CambioContraseña ventanaCambioPass = new CambioContraseña();
			CtrlCambioPassword ctrlCambioPass = new CtrlCambioPassword(ventanaCambioPass);
			ctrlCambioPass.iniciar();
		}
		
		if(e.getSource() == ventana.btn2A) {
			
			NuevoUsuario ventanaEmail = new NuevoUsuario("Edición email del sistema");
			CtrlEditarEmail ctrolEditarEmail = new CtrlEditarEmail(ventanaEmail);
			ctrolEditarEmail.iniciar();
		}

		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
}