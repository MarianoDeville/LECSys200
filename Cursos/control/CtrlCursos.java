package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import dao.OperadorSistema;
import modelo.DtosConfiguracion;
import vista.ABML;
import vista.InterfaceBotones;
import vista.Listado;

public class CtrlCursos implements ActionListener {
	
	private InterfaceBotones ventana;

	public CtrlCursos(InterfaceBotones vista) {
		
		this.ventana = vista;
		this.ventana.btn1A.addActionListener(this);
		this.ventana.btn1B.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {
		
		if(!OperadorSistema.isAcceso()) {
			
			ventana.dispose();
			return;
		}
		ventana.lbl1A.setText("ABML");
		ventana.btn1A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Actividades.png"));
		ventana.btn1A.setVisible(true);
		ventana.lbl1B.setText("Diagramación");
		ventana.btn1B.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Diagramacion.png"));
		ventana.btn1B.setVisible(true);
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btn1A) {
			
			ABML ventanaABMLCursos = new ABML("Gestionar los cursos");
			CtrlABMLCursos ctrlABMLCursos = new CtrlABMLCursos(ventanaABMLCursos);
			ctrlABMLCursos.iniciar();
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			Listado ventanaDiagramacionCursos = new Listado("Diagramación de cursos");
			CtrlDiagramaCursos ctrlDiagramaCursos = new CtrlDiagramaCursos(ventanaDiagramacionCursos);
			ctrlDiagramaCursos.iniciar();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
}