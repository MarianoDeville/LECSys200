package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import dao.OperadorSistema;
import modelo.DtosConfiguracion;
import vista.ABML;
import vista.InterfaceBotones;
import vista.Listado;

public class CtrlAlumnos implements ActionListener {

	private InterfaceBotones ventana;
	private ABML ventanaABML;
	private Listado ventanaListado;
	private Listado ventanaExamenes;
	private ABML ventanaGrupoFamiliar;
	private Listado ventanaAsistencia;
	private Listado ventanaRegistroAsistencia;
	private Listado ventanaCobrarHabilitar;
	private Listado ventanaCobrarCuota;
		
	public CtrlAlumnos(InterfaceBotones vista) {
		
		this.ventana = vista;
		this.ventana.btn1A.addActionListener(this);
		this.ventana.btn1B.addActionListener(this);
		this.ventana.btn1C.addActionListener(this);
		this.ventana.btn1D.addActionListener(this);
		this.ventana.btn2A.addActionListener(this);
		this.ventana.btn2B.addActionListener(this);
		this.ventana.btn2C.addActionListener(this);
		this.ventana.btn2D.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {
		
		if(!OperadorSistema.isAcceso()) {
			
			ventana.dispose();
			return;
		}
		ventana.lbl1A.setText("ABML alumnos");
		ventana.btn1A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\ABML.png"));
		ventana.btn1A.setVisible(true);
		ventana.lbl1B.setText("Listado");
		ventana.btn1B.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Listado.png"));
		ventana.btn1B.setVisible(true);
		ventana.lbl1C.setText("Exámenes");
		ventana.btn1C.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Examenes.png"));
		ventana.btn1C.setVisible(true);
		ventana.lbl1D.setText("Grupo familiar");
		ventana.btn1D.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Grupo familiar.png"));
		ventana.btn1D.setVisible(true);
		ventana.lbl2A.setText("Tomar asistencia");
		ventana.btn2A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Asistencia.png"));
		ventana.btn2A.setVisible(true);
		ventana.lbl2B.setText("Registro asistencia");
		ventana.btn2B.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Registro asistencia.png"));
		ventana.btn2B.setVisible(true);
		ventana.lbl2C.setText("Habilitar y cobrar");
		ventana.btn2C.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Habilitar y cobrar.png"));
		ventana.btn2C.setVisible(true);
		ventana.lbl2D.setText("Cobrar cuota");
		ventana.btn2D.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Cobrar.png"));
		ventana.btn2D.setVisible(true);
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btn1A) {
			
			if(ventanaABML != null && ventanaABML.isVisible()) {
				
				ventanaABML.setVisible(true);
				return;
			}
			ventanaABML = new ABML("Alta, Baja, Modificación y Listado", ventana.getX(), ventana.getY());
			CtrlABMLAlumnos ctrlABMLPersonal = new CtrlABMLAlumnos(ventanaABML);
			ctrlABMLPersonal.iniciar();
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			if(ventanaListado != null && ventanaListado.isVisible()) {
				
				ventanaListado.setVisible(true);
				return;
			}
			ventanaListado = new Listado("Listado", ventana.getX(), ventana.getY());
			CtrlListado ctrlListado = new CtrlListado(ventanaListado);
			ctrlListado.iniciar();
		}
		
		if(e.getSource() == ventana.btn1C) {
			
			if(ventanaExamenes != null && ventanaExamenes.isVisible()) {
				
				ventanaExamenes.setVisible(true);
				return;
			}
			ventanaExamenes = new Listado("Examenes", ventana.getX(), ventana.getY());
			CtrlExamenes ctrlExamenes = new CtrlExamenes(ventanaExamenes);
			ctrlExamenes.iniciar();
		}
		
		if(e.getSource() == ventana.btn1D) {
			
			if(ventanaGrupoFamiliar != null && ventanaGrupoFamiliar.isVisible()) {
				
				ventanaGrupoFamiliar.setVisible(true);
				return;
			}
			ventanaGrupoFamiliar = new ABML("Gestión de grupos familiares", ventana.getX(), ventana.getY());
			CtrlGrupoFamiliar ctrlGrupoFamiliar = new CtrlGrupoFamiliar(ventanaGrupoFamiliar);
			ctrlGrupoFamiliar.iniciar();
		}
		
		if(e.getSource() == ventana.btn2A) {
			
			if(ventanaAsistencia != null && ventanaAsistencia.isVisible()) {
				
				ventanaAsistencia.setVisible(true);
				return;
			}
			ventanaAsistencia = new Listado("Tomar asistencia", ventana.getX(), ventana.getY());
			CtrlTomarAsistencia ctrlAsistenciaAlumnos = new CtrlTomarAsistencia(ventanaAsistencia);
			ctrlAsistenciaAlumnos.iniciar();
		}
		
		if(e.getSource() == ventana.btn2B) {
			
			if(ventanaRegistroAsistencia != null && ventanaRegistroAsistencia.isVisible()) {
				
				ventanaRegistroAsistencia.setVisible(true);
				return;
			}			
			ventanaRegistroAsistencia = new Listado("Registro asistencia", ventana.getX(), ventana.getY());
			CtrlRegistroAsistencia ctrlRegistroAsistencias = new CtrlRegistroAsistencia(ventanaRegistroAsistencia);
			ctrlRegistroAsistencias.iniciar();
		}

		if(e.getSource() == ventana.btn2C) {
			
			if(ventanaCobrarHabilitar != null && ventanaCobrarHabilitar.isVisible()) {
				
				ventanaCobrarHabilitar.setVisible(true);
				return;
			}
			ventanaCobrarHabilitar = new Listado("Cobro y habilitación", ventana.getX(), ventana.getY());
			CtrlCobrarHabilitar ctrolCobrarHabilitar = new CtrlCobrarHabilitar(ventanaCobrarHabilitar);
			ctrolCobrarHabilitar.iniciar();
		}
		
		if(e.getSource() == ventana.btn2D) {
			
			if(ventanaCobrarHabilitar != null && ventanaCobrarHabilitar.isVisible()) {
				
				ventanaCobrarHabilitar.setVisible(true);
				return;
			}
			ventanaCobrarCuota = new Listado("Cobrar cuota", ventana.getX(), ventana.getY());
			CtrlCobrarCuota ctrlCobrarCuota = new CtrlCobrarCuota(ventanaCobrarCuota);
			ctrlCobrarCuota.iniciar();
		}

		if(e.getSource() == ventana.btnVolver) {
			
			if(ventanaABML != null && ventanaABML.isVisible())
				ventanaABML.dispose();
			
			if(ventanaListado != null && ventanaListado.isVisible())
				ventanaListado.dispose();
			
			if(ventanaExamenes != null && ventanaExamenes.isVisible())
				ventanaExamenes.dispose();
			
			if(ventanaGrupoFamiliar != null && ventanaGrupoFamiliar.isVisible())
				ventanaGrupoFamiliar.dispose();
			
			if(ventanaAsistencia != null && ventanaAsistencia.isVisible())
				ventanaAsistencia.dispose();
			
			if(ventanaRegistroAsistencia != null && ventanaRegistroAsistencia.isVisible())
				ventanaRegistroAsistencia.dispose();
			
			if(ventanaCobrarHabilitar != null && ventanaCobrarHabilitar.isVisible())
				ventanaCobrarHabilitar.dispose();
			
			if(ventanaCobrarCuota != null && ventanaCobrarCuota.isVisible())
				ventanaCobrarCuota.dispose();
			ventana.dispose();
		}
	}
}