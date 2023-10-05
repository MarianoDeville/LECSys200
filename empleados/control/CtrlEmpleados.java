package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import dao.OperadorSistema;
import modelo.DtosConfiguracion;
import vista.ABML;
import vista.InterfaceBotones;
import vista.ListadoDoble2;

public class CtrlEmpleados implements ActionListener {

	private InterfaceBotones ventana;
	
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
			
			ABML ventanaABML = new ABML("ABML del personal");
			CtrlABMLEmpleado ctrlABMLEmpleados = new CtrlABMLEmpleado(ventanaABML);
			ctrlABMLEmpleados.iniciar();
		}
		
		if(e.getSource() == ventana.btn1B) {
			

		}
		
		if(e.getSource() == ventana.btn2A) {
			
			ListadoDoble2 ventanaHorariosPersonal = new ListadoDoble2("Horarios Personal");
			CtrlHorariosEmpleado ctrlHorariosEmpleados = new CtrlHorariosEmpleado(ventanaHorariosPersonal);
			ctrlHorariosEmpleados.iniciar();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
}