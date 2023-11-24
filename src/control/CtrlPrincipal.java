package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	
	private Principal ventana;
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
		
		this.ventana = vista;
		this.actividad = new DtosActividad();
		this.acceso = new OperadorSistema();
		this.ventana.btnAdmin.addActionListener(this);
		this.ventana.btnAlumnos.addActionListener(this);
		this.ventana.btnPersonal.addActionListener(this);
		this.ventana.btnCursos.addActionListener(this);
		this.ventana.btnInsumos.addActionListener(this);
		this.ventana.btnProveedores.addActionListener(this);
		this.ventana.btnConfig.addActionListener(this);
		this.ventana.btnRelogin.addActionListener(this);
		this.ventana.btnSalir.addActionListener(this);
	}
	
	public void iniciar() {
int algo;
//		JOptionPane.showMessageDialog(null, "Welcome to LECSys.\nVer.2.00\nRev. 27092023.1316");//////////////////////////////////////////////////////////////////////////////
		actividad.registrarActividad("Inicio del sistema", "Principal", 0);
		ventana.setVisible(true);
		
		if(!acceso.getFichaEmpleado().equals("0")) {
			
			cambiarPass();
			ventanaCumpleaños();
		}
		DtosPrincipal dtosPrincipal = new DtosPrincipal();
		dtosPrincipal.inicializar();
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnAdmin) {
			
			if(ventanaAdministracion != null && ventanaAdministracion.isVisible()) {
				
				ventanaAdministracion.setVisible(true);
				return;
			}
			ventanaAdministracion = new InterfaceBotones("Gestión administrativa", ventana.getX(), ventana.getY());
			CtrlAdministracion ctrlAdmin = new CtrlAdministracion(ventanaAdministracion);
			ctrlAdmin.iniciar();
		}
		
		if(e.getSource() == ventana.btnAlumnos) {
			
			if(ventanaAlumnos != null && ventanaAlumnos.isVisible()) {
				
				ventanaAlumnos.setVisible(true);
				return;
			}				
			ventanaAlumnos = new InterfaceBotones("Gestión de los alumnos", ventana.getX(), ventana.getY());
			CtrlAlumnos ctrlAlumn = new CtrlAlumnos(ventanaAlumnos);
			ctrlAlumn.iniciar();
		}
		
		if(e.getSource() == ventana.btnPersonal) {
			
			if(ventanaPersonal != null && ventanaPersonal.isVisible()) {
				
				ventanaPersonal.setVisible(true);
				return;
			}
			ventanaPersonal = new InterfaceBotones("Gestión del personal", ventana.getX(), ventana.getY());
			CtrlEmpleados ctrlAdmin = new CtrlEmpleados(ventanaPersonal);
			ctrlAdmin.iniciar();
		}
		
		if(e.getSource() == ventana.btnCursos) {
			
			if(ventanaCursos != null && ventanaCursos.isVisible()) {
				
				ventanaCursos.setVisible(true);
				return;
			}
			ventanaCursos = new InterfaceBotones("Gestión de los cursos", ventana.getX(), ventana.getY());
			CtrlCursos ctrlCursos = new CtrlCursos(ventanaCursos);
			ctrlCursos.iniciar();
		}
		
		if(e.getSource() == ventana.btnProveedores) {
			
			if(ventanaABMLProveedores != null && ventanaABMLProveedores.isVisible()) {
				
				ventanaABMLProveedores.setVisible(true);
				return;
			}
			ventanaABMLProveedores = new ABML("ABML proveedores", ventana.getX(), ventana.getY());
			CtrlABMLProveedores ctrlABMLProveedores = new CtrlABMLProveedores(ventanaABMLProveedores);
			ctrlABMLProveedores.iniciar();
		}
		
		if(e.getSource() == ventana.btnConfig) {	
			
			if(ventanaConfiguracion != null && ventanaConfiguracion.isVisible()) {
				
				ventanaConfiguracion.setVisible(true);
				return;
			}
			ventanaConfiguracion = new InterfaceBotones("Configuración del sistema", ventana.getX(), ventana.getY());
			CtrlConfiguracion ctrlConfig = new CtrlConfiguracion(ventanaConfiguracion);
			ctrlConfig.iniciar();
		}
	
		if(e.getSource() == ventana.btnRelogin) {
			
			IngresoUsuario ventanaLogin = new IngresoUsuario();
			CtrlReLogin ctrlIngreso = new CtrlReLogin(ventanaLogin);
			ctrlIngreso.iniciar();
			ventana.setTitle("Sistema de gestión - LECSys - " + acceso.getNombreUsuario());
			actividad.registrarActividad("Cambio de usuario activo.", "Principal", 0);
			cambiarPass();
		}
		
		if(e.getSource() == ventana.btnInsumos) {
			
			if(ventanaInsumos != null && ventanaInsumos.isVisible()) {
				
				ventanaInsumos.setVisible(true);
				return;
			}			
			ventanaInsumos = new InterfaceBotones("Insumos", ventana.getX(), ventana.getY());
//			CtrlInsumos ctrlInsumos = new CtrlInsumos(ventanaInsumos);
//			ctrlInsumos.iniciar();
		}
		
		if(e.getSource() == ventana.btnSalir) {
			
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