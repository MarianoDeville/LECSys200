package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.DtosAlumno;
import vista.ABML;
import vista.Nuevo;

public class CtrlABMLAlumnos implements ActionListener {

	private ABML ventana;
	private DtosAlumno dtosAlumnos;
	private Nuevo ventanaNuevoAlumno;
	private Nuevo ventanaEditarAlumno;
	private int elemento = -1;
			
	public CtrlABMLAlumnos(ABML vista) {
		
		this.ventana = vista;
		this.dtosAlumnos = new DtosAlumno();
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnEditar.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.chckbx1.addActionListener(this);
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.txt1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				actualizar();
			}
		});
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {

					elemento = ventana.tabla.getSelectedRow();
		        } else if(e.getClickCount() == 2) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
		        	editar();
		        }
		    }
		});
	}
	
	public void iniciar() {

		ventana.chckbx1.setVisible(true);
		ventana.comboBox1.setVisible(true);
		ventana.txt1.setVisible(true);
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosAlumnos.getOrdenamiento()));
		actualizar();
		ventana.setVisible(true);
	}
	
	private void actualizar() {
		
		elemento = -1;
		ventana.tabla.setModel(dtosAlumnos.getTablaAlumnos("",ventana.txt1.getText()
															 ,ventana.chckbx1.isSelected()
															 , ventana.comboBox1.getSelectedIndex()));
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
		ventana.tabla.getColumnModel().getColumn(3).setPreferredWidth(65);
		ventana.tabla.getColumnModel().getColumn(3).setMaxWidth(70);
		ventana.tabla.getColumnModel().getColumn(5).setPreferredWidth(80);
		ventana.tabla.getColumnModel().getColumn(5).setMaxWidth(100);
		ventana.tabla.getColumnModel().getColumn(7).setPreferredWidth(80);
		ventana.tabla.getColumnModel().getColumn(7).setMaxWidth(80);
		ventana.tabla.setDefaultEditor(Object.class, null);
		ventana.btnImprimir.setEnabled(ventana.tabla.getRowCount() != 0);
		ventana.tabla.clearSelection();
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnNuevo) {
			
			nuevo();
		}
		
		if(ventanaNuevoAlumno != null) {
			
			if(e.getSource() == ventanaNuevoAlumno.btnVolver) {
				
				ventanaNuevoAlumno = null;
				actualizar();
			}
		}
		
		if(e.getSource() == ventana.btnEditar) {
			
			editar();
		}
		
		if(ventanaEditarAlumno != null) {
			
			if(e.getSource() == ventanaEditarAlumno.btnVolver) {
				
				ventanaEditarAlumno = null;
				actualizar();
			}
		}
		
		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch (PrinterException f) {
				
				f.printStackTrace();
			}
		}
		
		if(e.getSource() == ventana.chckbx1 && ventana.isVisible()) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.comboBox1 && ventana.isVisible()) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			if(ventanaNuevoAlumno != null)
				ventanaNuevoAlumno.dispose();
			if(ventanaEditarAlumno != null)
				ventanaEditarAlumno.dispose();
			ventana.dispose();
		}
	}
	
	private void nuevo() {
		
		if(ventanaNuevoAlumno != null) {

			JOptionPane.showMessageDialog(null, "La ventana \"Nuevo\" ya se encuentra abierta.");
			return;
		}
		
		ventanaNuevoAlumno = new Nuevo("Nuevo alumno.");
		CtrlNuevoAlumno ctrlNuevoAlumno = new CtrlNuevoAlumno(ventanaNuevoAlumno);
		ctrlNuevoAlumno.iniciar();
		ventanaNuevoAlumno.btnVolver.addActionListener(this);
	}
	
	private void editar() {
		
		if(ventanaEditarAlumno != null) {

			JOptionPane.showMessageDialog(null, "La ventana \"Editar\" ya se encuentra abierta.");
			return;
		}
		
		if(elemento == -1) {

			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento para editar.");
			return;
		}
		dtosAlumnos.setAlumnoSeleccionado(elemento);
		elemento = -1;
		ventanaEditarAlumno = new Nuevo("Edición de alumno.");
		CtrlEditarAlumno ctrolEditarAlumno = new CtrlEditarAlumno(ventanaEditarAlumno);
		ctrolEditarAlumno.iniciar();
		ventanaEditarAlumno.btnVolver.addActionListener(this);
	}
}