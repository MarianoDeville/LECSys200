package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import dao.OperadorSistema;
import modelo.DtosActividad;
import modelo.DtosPrincipal;
import vista.ABML;
import vista.CambioContraseña;
import vista.Cumpleaños;
import vista.IngresoUsuario;
import vista.InterfaceBotones;
import vista.Principal;

public class CtrlPrincipal implements ActionListener {
	
	private Principal ventanaPrincipal;
	private DtosActividad actividad;
	private OperadorSistema acceso;
	private InterfaceBotones ventanaAdministracion;
	private InterfaceBotones ventanaAlumnos;
	private InterfaceBotones ventanaPersonal;
	private InterfaceBotones ventanaCursos;
	private ABML ventanaABMLProveedores;
	private InterfaceBotones ventanaConfiguracion;
	private InterfaceBotones ventanaInsumos;

	public CtrlPrincipal(Principal vista) {
		
		this.ventanaPrincipal = vista;
		this.actividad = new DtosActividad();
		this.acceso = new OperadorSistema();
		this.ventanaPrincipal.btnAdmin.addActionListener(this);
		this.ventanaPrincipal.btnAlumnos.addActionListener(this);
		this.ventanaPrincipal.btnPersonal.addActionListener(this);
		this.ventanaPrincipal.btnCursos.addActionListener(this);
		this.ventanaPrincipal.btnInsumos.addActionListener(this);
		this.ventanaPrincipal.btnProveedores.addActionListener(this);
		this.ventanaPrincipal.btnConfig.addActionListener(this);
		this.ventanaPrincipal.btnRelogin.addActionListener(this);
		this.ventanaPrincipal.btnSalir.addActionListener(this);
	}
	
	public void iniciar() {
int algo;
//		JOptionPane.showMessageDialog(null, "Welcome to LECSys.\nVer.2.00\nRev. 27092023.1316");//////////////////////////////////////////////////////////////////////////////
		actividad.registrarActividad("Inicio del sistema", "Principal", 0);
		ventanaPrincipal.setVisible(true);
		
		if(!acceso.getFichaEmpleado().equals("0")) {
			
			cambiarPass();
			ventanaCumpleaños();
		}
		DtosPrincipal dtosPrincipal = new DtosPrincipal();
		dtosPrincipal.inicializar();
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventanaPrincipal.btnAdmin) {
			
			if(ventanaAdministracion != null && ventanaAdministracion.isVisible()) {
				
				JOptionPane.showMessageDialog(null, "El módulo \"Administración\" ya se encuentra abierto.");
				return;
			}
			ventanaAdministracion = new InterfaceBotones("Gestión administrativa");
			CtrlAdministracion ctrlAdmin = new CtrlAdministracion(ventanaAdministracion);
			ctrlAdmin.iniciar();
		}
		
		if(e.getSource() == ventanaPrincipal.btnAlumnos) {
			
			if(ventanaAlumnos != null && ventanaAlumnos.isVisible()) {
				
				JOptionPane.showMessageDialog(null, "El módulo \"Alumnos\" ya se encuentra abierto.");
				return;
			}				
			ventanaAlumnos = new InterfaceBotones("Gestión de los alumnos");
			CtrlAlumnos ctrlAlumn = new CtrlAlumnos(ventanaAlumnos);
			ctrlAlumn.iniciar();
		}
		
		if(e.getSource() == ventanaPrincipal.btnPersonal) {
			
			if(ventanaPersonal != null && ventanaPersonal.isVisible()) {
				
				JOptionPane.showMessageDialog(null, "El módulo \"Personal\" ya se encuentra abierto.");
				return;
			}
			ventanaPersonal = new InterfaceBotones("Gestión del personal");
			CtrlEmpleados ctrlAdmin = new CtrlEmpleados(ventanaPersonal);
			ctrlAdmin.iniciar();
		}
		
		if(e.getSource() == ventanaPrincipal.btnCursos) {
			
			if(ventanaCursos != null && ventanaCursos.isVisible()) {
				
				JOptionPane.showMessageDialog(null, "El módulo \"Cursos\" ya se encuentra abierto.");
				return;
			}
			ventanaCursos = new InterfaceBotones("Gestión de los cursos");
			CtrlCursos ctrlCursos = new CtrlCursos(ventanaCursos);
			ctrlCursos.iniciar();
		}
		
		if(e.getSource() == ventanaPrincipal.btnProveedores) {
			
			if(ventanaABMLProveedores != null && ventanaABMLProveedores.isVisible()) {
				
				JOptionPane.showMessageDialog(null, "El módulo \"Proveedores\" ya se encuentra abierto.");
				return;
			}
			ventanaABMLProveedores = new ABML("ABML proveedores");
//			CtrlABMLProveedores ctrlABMLProveedores = new CtrlABMLProveedores(ventanaABMLProveedores);
//			ctrlABMLProveedores.iniciar();
		}
		
		if(e.getSource() == ventanaPrincipal.btnConfig) {	
			
			if(ventanaConfiguracion != null && ventanaConfiguracion.isVisible()) {
				
				JOptionPane.showMessageDialog(null, "El módulo \"Configuración\" ya se encuentra abierto.");
				return;
			}
			ventanaConfiguracion = new InterfaceBotones("Configuración del sistema");
			CtrlConfiguracion ctrlConfig = new CtrlConfiguracion(ventanaConfiguracion);
			ctrlConfig.iniciar();
		}
	
		if(e.getSource() == ventanaPrincipal.btnRelogin) {
			
			IngresoUsuario ventanaLogin = new IngresoUsuario();
			CtrlReLogin ctrlIngreso = new CtrlReLogin(ventanaLogin);
			ctrlIngreso.iniciar();
			ventanaPrincipal.setTitle("Sistema de gestión - LECSys - " + acceso.getNombreUsuario());
			actividad.registrarActividad("Cambio de usuario activo.", "Principal", 0);
			cambiarPass();
		}
		
		if(e.getSource() == ventanaPrincipal.btnInsumos) {
			
			if(ventanaInsumos != null && ventanaInsumos.isVisible()) {
				
				JOptionPane.showMessageDialog(null, "El módulo \"Insumos\" ya se encuentra abierto.");
				return;
			}			
			ventanaInsumos = new InterfaceBotones("Insumos");
//			CtrlInsumos ctrlInsumos = new CtrlInsumos(ventanaInsumos);
//			ctrlInsumos.iniciar();
		}
		
		if(e.getSource() == ventanaPrincipal.btnSalir) {
			
			actividad.registrarActividad("Cierre del sistema", "Principal", 0);
			System.exit(0);
		}
	}
	
	private void cambiarPass() {
		
		if(acceso.isActualizarContraseña()) {
			
			CambioContraseña ventanaCambioPass = new CambioContraseña();
//			CtrlCambioPassword ctrlCambioPass = new CtrlCambioPassword(ventanaCambioPass);
//			ctrlCambioPass.iniciar();
		}
	}
	
	private void ventanaCumpleaños() {
		
		Cumpleaños ventanaCumpleaños = new Cumpleaños("Recordatorio de cumpleaños");
		CtrlCumpleaños ctrlCumpleaños = new CtrlCumpleaños(ventanaCumpleaños);
		ctrlCumpleaños.iniciar();
	}
}