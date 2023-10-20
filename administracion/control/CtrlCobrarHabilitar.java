package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import modelo.DtosCobros;
import vista.Cobro;
import vista.Listado;

public class CtrlCobrarHabilitar implements ActionListener {

	private Listado ventana;
	private Cobro cobrarInscripci�n;
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
		ventana.chckbx1.setText("Reinscripci�n");
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

			dtosCobros.setSeleccionados(itemsSeleccionados());
			cobrarInscripci�n = new Cobro("Cobrar inscripci�n y habilitar");
			CtrlCobrarInscripcion ctrlCobrarInscripci�n = new CtrlCobrarInscripcion(cobrarInscripci�n);
			cobrarInscripci�n.btnVolver.addActionListener(this);
			cobrarInscripci�n.btnCobrar.addActionListener(this);
			ctrlCobrarInscripci�n.iniciar();
		}
		
		if(cobrarInscripci�n != null) {
			
			if(e.getSource() == cobrarInscripci�n.btnVolver) {
				
				ventana.txt3.setText("");
				ventana.chckbx1.setSelected(false);
				ventana.chckbx2.setSelected(false);
				actualizar();
			}
			
			if(e.getSource() == cobrarInscripci�n.btnCobrar) {
				
				ventana.txt3.setText("");
				ventana.chckbx1.setSelected(false);
				ventana.chckbx2.setSelected(false);
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
	
	private boolean[] itemsSeleccionados() {
		
		boolean seleccionados[] = new boolean[ventana.tabla.getRowCount()];
		
		for(int i = 0; i < seleccionados.length; i++) {

			seleccionados[i] = (boolean)ventana.tabla.getValueAt(i, 4);
		}
		return seleccionados;
	}
}