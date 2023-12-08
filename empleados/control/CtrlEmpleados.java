package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import dao.OperadorSistema;
import modelo.DtosConfiguracion;
import vista.ABML;
import vista.InterfaceBotones;
import vista.ListadoDoble2;

public class CtrlEmpleados implements ActionListener {

	private InterfaceBotones ventana;
	private ABML ventanaABML;
	private ListadoDoble2 ventanaHorarios;
	
	public CtrlEmpleados(InterfaceBotones vista) {
		
		this.ventana = vista;
		this.ventana.btn1A.addActionListener(this);
		this.ventana.btn1B.addActionListener(this);
		this.ventana.btn2A.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {
		
		if(!OperadorSistema.isAcceso()) {
			
			ventana.dispose();
			return;
		}
		ventana.lbl1A.setText("ABML");
		ventana.btn1A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\ABML.png"));
		ventana.btn1A.setVisible(true);
		ventana.lbl1B.setText("Asistencia");
		ventana.btn1B.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Asistencia.png"));
		ventana.btn1B.setVisible(true);
		ventana.lbl2A.setText("Horarios");
		ventana.btn2A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Horarios.png"));
		ventana.btn2A.setVisible(true);
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btn1A) {
			
			abml();
		}

		if(e.getSource() == ventana.btn1B) {
			
			asistencia();
		}
		
		if(e.getSource() == ventana.btn2A) {
			
			horarios();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			if(ventanaABML != null && ventanaABML.isVisible())
				ventanaABML.dispose();
			
			if(ventanaHorarios != null && ventanaHorarios.isVisible())
				ventanaHorarios.dispose();
			ventana.dispose();
		}
	}
	
	private void abml() {
		
		if(ventanaABML != null && ventanaABML.isVisible()) {
			
			ventanaABML.setVisible(true);
			return;
		}
		ventanaABML = new ABML("ABML del personal", ventana.getX(), ventana.getY());
		CtrlABMLEmpleado ctrlABMLEmpleados = new CtrlABMLEmpleado(ventanaABML);
		ctrlABMLEmpleados.iniciar();
	}
	
	private void horarios() {
		
		if(ventanaHorarios != null && ventanaHorarios.isVisible()) {
			
			ventanaHorarios.setVisible(true);
			return;
		}
		ventanaHorarios = new ListadoDoble2("Horarios Personal", ventana.getX(), ventana.getY());
		CtrlHorariosEmpleado ctrlHorariosEmpleados = new CtrlHorariosEmpleado(ventanaHorarios);
		ctrlHorariosEmpleados.iniciar();
	}
	
	private void asistencia() {
		
		JOptionPane.showMessageDialog(null, "Próximamente.");
	}
}