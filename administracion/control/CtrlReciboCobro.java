package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JOptionPane;
import modelo.DtosCobros;
import vista.ReciboCobro;

public class CtrlReciboCobro implements ActionListener {
	
	private ReciboCobro ventanaReciboInscripcion;
	private DtosCobros dtosCobro;
		
	public CtrlReciboCobro(ReciboCobro vista) {
		
		this.ventanaReciboInscripcion = vista;
		this.dtosCobro = new DtosCobros();
		this.ventanaReciboInscripcion.btnVolver.addActionListener(this);
		this.ventanaReciboInscripcion.btnImprimir.addActionListener(this);
	}
	
	public void iniciar() {
		
		ventanaReciboInscripcion.txtTítulo_1.setText("LEARNING ENGLISH CENTRE");
		ventanaReciboInscripcion.txtTítulo_2.setText("ENGLISH INSTITUTE");
		ventanaReciboInscripcion.txtNombre.setText("Recibí de " + dtosCobro.getNombre());
		ventanaReciboInscripcion.txtMontoTotal.setText("La suma de pesos " + dtosCobro.getMontoTotal());
		ventanaReciboInscripcion.txtConcepto.setText("<html>En concepto de: " + dtosCobro.getDescripcion() + "</html>");
		ventanaReciboInscripcion.txtNroRecibo.setText(dtosCobro.getNumeroRecibo());
		ventanaReciboInscripcion.txtFecha.setText(dtosCobro.getFechaActual(""));
		ventanaReciboInscripcion.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventanaReciboInscripcion.btnVolver) {
			
			ventanaReciboInscripcion.dispose();
		}

		if(e.getSource() == ventanaReciboInscripcion.btnImprimir) {
			
			ventanaReciboInscripcion.btnVolver.setVisible(false);
			ventanaReciboInscripcion.btnImprimir.setVisible(false);
			PrinterJob imprimir = PrinterJob.getPrinterJob();
			PageFormat preformat = imprimir.defaultPage();
			PageFormat postformat = imprimir.pageDialog(preformat);
			imprimir.setPrintable(new Printer(ventanaReciboInscripcion.panel), postformat);
							
			if (imprimir.printDialog()) {
				
				try {
					imprimir.print();
				} catch (PrinterException d) {
					
					CtrlLogErrores.guardarError(d.getMessage());
					CtrlLogErrores.guardarError("Error al intentar imprimir.");
					JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
				}
			}
			ventanaReciboInscripcion.dispose();
		}
	}
}