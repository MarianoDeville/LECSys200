package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import javax.swing.JOptionPane;
import modelo.DtosCobros;
import vista.Cobro;
import vista.Listado;

public class CtrlCobrarHabilitar implements ActionListener {

	private Listado ventana;
	private Cobro cobrarInscripción;
	private DtosCobros dtosCobros;
	
	public CtrlCobrarHabilitar(Listado vista) {
		
		this.ventana = vista;
		this.dtosCobros = new DtosCobros();
		this.ventana.chckbx1.addActionListener(this);
		this.ventana.chckbx2.addActionListener(this);
		this.ventana.txt3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				actualizar();
			}
		});
		this.ventana.btn1A.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.btn1A.setText("Cobrar");
		ventana.btn1A.setVisible(true);
		ventana.chckbx1.setVisible(true);
		ventana.chckbx1.setEnabled(true);
		ventana.chckbx1.setSelected(false);
		ventana.chckbx1.setText("Reinscripción");
		ventana.chckbx2.setVisible(true);
		ventana.chckbx2.setEnabled(true);
		ventana.chckbx2.setSelected(false);
		ventana.chckbx2.setText("Todos");		
		ventana.txt3.setVisible(true);
		ventana.txt3.setEnabled(true);
		ventana.txt3.setEditable(true);
		actualizar();
		ventana.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btn1A) {

			cobrar();
		}
		
		if(cobrarInscripción != null) {
			
			if(e.getSource() == cobrarInscripción.btnVolver) {
		
				ventana.txt3.setText("");
				ventana.chckbx1.setSelected(false);
				ventana.chckbx2.setSelected(false);
				cobrarInscripción = null;
				actualizar();
			}
		}

		if(e.getSource() == ventana.chckbx1 && ventana.isVisible()) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.chckbx2 && ventana.isVisible()) {
			
			actualizar();
		}

		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch (PrinterException f) {
				
				f.printStackTrace();
			}
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			if(cobrarInscripción != null)
				cobrarInscripción.dispose();
			ventana.dispose();
		}
	}
	
	private void actualizar() {
		
		ventana.tabla.setModel(dtosCobros.getTablaAlumnos(ventana.chckbx1.isSelected(), 
															 ventana.chckbx2.isSelected(), 
															 ventana.txt3.getText()));
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(45);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(55);	
		ventana.tabla.getColumnModel().getColumn(4).setPreferredWidth(45);
		ventana.tabla.getColumnModel().getColumn(4).setMaxWidth(55);	
	}
	
	private void cobrar() {
		
		if(cobrarInscripción != null) {

			JOptionPane.showMessageDialog(null, "Le ventana \"Cobrar\" ya se encuentra abierta.");
			cobrarInscripción.setVisible(true);
			return;
		}
		
		if(!dtosCobros.setSeleccionados(ventana.tabla)) {

			JOptionPane.showMessageDialog(null, "No hay elementos seleccionados para cobrar.");
			return;
		}

		cobrarInscripción = new Cobro("Cobrar inscripción y habilitar", ventana.getX(), ventana.getY());
		CtrlCobrarInscripcion ctrlCobrarInscripción = new CtrlCobrarInscripcion(cobrarInscripción);
		cobrarInscripción.btnVolver.addActionListener(this);
		ctrlCobrarInscripción.iniciar();
	}
}