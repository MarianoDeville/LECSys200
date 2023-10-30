package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import dao.OperadorSistema;
import modelo.DtosConfiguracion;
import vista.ABML;
import vista.InterfaceBotones;
import vista.Listado;

public class CtrlCursos implements ActionListener {
	
	private InterfaceBotones ventana;
	private ABML ventanaABML;
	private Listado ventanaDiagramacion;

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

			abml();
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			diagramacion();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			if(ventanaABML != null && ventanaABML.isVisible())
				ventanaABML.dispose();
			
			if(ventanaDiagramacion != null && ventanaDiagramacion.isVisible())
				ventanaDiagramacion.dispose();
			ventana.dispose();
		}
	}
	
	private void abml() {
		
		if(ventanaABML != null && ventanaABML.isVisible()) {
			
			JOptionPane.showMessageDialog(null, "La ventana \"ABML\" ya se encuentra abierta.");
			return;
		}
		ventanaABML = new ABML("Gestionar los cursos", ventana.getX(), ventana.getY());
		CtrlABMLCursos ctrlABMLCursos = new CtrlABMLCursos(ventanaABML);
		ctrlABMLCursos.iniciar();
	}
	
	private void diagramacion() {
		
		if(ventanaDiagramacion != null && ventanaDiagramacion.isVisible()) {
			
			JOptionPane.showMessageDialog(null, "La ventana \"Diagramación\" ya se encuentra abierta.");
			return;
		}
		ventanaDiagramacion = new Listado("Diagramación de cursos", ventana.getX(), ventana.getY());
		CtrlDiagramaCursos ctrlDiagramaCursos = new CtrlDiagramaCursos(ventanaDiagramacion);
		ctrlDiagramaCursos.iniciar();
	}
}