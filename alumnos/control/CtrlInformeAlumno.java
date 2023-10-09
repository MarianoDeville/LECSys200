package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JOptionPane;

import modelo.DtosAlumno;
import vista.InformeAlumno;

public class CtrlInformeAlumno implements ActionListener {

	private InformeAlumno ventana;
	private DtosAlumno dtosAlumno;
	
	public CtrlInformeAlumno(InformeAlumno vista) {
		
		this.ventana = vista;
		this.dtosAlumno = new DtosAlumno();
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}

	public void iniciar() {

		ventana.txtLegajo.setEditable(false);
		ventana.txtLegajo.setText(dtosAlumno.getLegajo());
		ventana.txtNombre.setEditable(false);
		ventana.txtNombre.setText(dtosAlumno.getNombre());
		ventana.txtApellido.setEditable(false);
		ventana.txtApellido.setText(dtosAlumno.getApellido());
		ventana.txtCurso.setEditable(false);
		ventana.txtCurso.setText(dtosAlumno.getNombreCurso());
		ventana.txtProfesor.setEditable(false);
		ventana.txtProfesor.setText(dtosAlumno.getNombreProfesor());
		dtosAlumno.cargarAsistencia();
		ventana.txtPresentismo.setEditable(false);
		ventana.txtPresentismo.setText(dtosAlumno.getPresente());
		ventana.txtFaltas.setEditable(false);
		ventana.txtFaltas.setText(dtosAlumno.getAusente());
		ventana.txtTarde.setEditable(false);
		ventana.txtTarde.setText(dtosAlumno.getTarde());
		dtosAlumno.cargarNotas();
		ventana.txtEscrito1.setEditable(false);
		ventana.txtEscrito1.setText(dtosAlumno.getEscrito1());
		ventana.txtEscrito2.setEditable(false);
		ventana.txtEscrito2.setText(dtosAlumno.getEscrito2());
		ventana.txtFinalEscrito.setEditable(false);
		ventana.txtFinalEscrito.setText(dtosAlumno.getFinalEscrito());
		ventana.txtComportamiento1.setEditable(false);
		ventana.txtComportamiento1.setText(dtosAlumno.getComportamiento1());
		ventana.txtComportamiento2.setEditable(false);
		ventana.txtComportamiento2.setText(dtosAlumno.getComportamiento2());
		ventana.txtFinalComportamiento.setEditable(false);
		ventana.txtFinalComportamiento.setText(dtosAlumno.getFinalComportamiento());
		ventana.txtOral1.setEditable(false);
		ventana.txtOral1.setText(dtosAlumno.getOral1());
		ventana.txtOral2.setEditable(false);
		ventana.txtOral2.setText(dtosAlumno.getOral2());
		ventana.txtFinalOral.setEditable(false);
		ventana.txtFinalOral.setText(dtosAlumno.getFinalOral());
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnImprimir) {

			Color colorPanel = ventana.panel.getBackground();
			ventana.panel.setBackground(Color.WHITE);
			ventana.btnImprimir.setVisible(false);
			ventana.btnVolver.setVisible(false);
			PrinterJob imprimir = PrinterJob.getPrinterJob();
			PageFormat preformat = imprimir.defaultPage();
			PageFormat postformat = imprimir.pageDialog(preformat);
			imprimir.setPrintable(new Printer(ventana.panel), postformat);
			
			if (imprimir.printDialog()) {
				
				try {
					
					imprimir.print();
				} catch (PrinterException f) {
					
					JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
					CtrlLogErrores.guardarError(f.getMessage());
				}
			}
			
			ventana.panel.setBackground(colorPanel);
			ventana.btnImprimir.setVisible(true);
			ventana.btnVolver.setVisible(true);
		}		
		
		if(e.getSource() == ventana.btnVolver) {

			ventana.dispose();
		}
	}
}