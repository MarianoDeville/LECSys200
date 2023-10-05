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
import modelo.DtosEmpleado;
import vista.ABML;
import vista.Nuevo;

public class CtrlABMLEmpleado implements ActionListener {

	private ABML ventana;
	private DtosEmpleado dtosEmpleados;
	private Nuevo ventanaNuevoEmpleado;
	private Nuevo ventanaEditarEmpleado;
	private int elemento;
	private boolean bandera;
	
	public CtrlABMLEmpleado(ABML vista) {
		
		this.ventana = vista;
		this.dtosEmpleados = new DtosEmpleado();
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnEditar.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.chckbx1.addActionListener(this);
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.txt1.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
	
				actualizar();
			}
		});
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {

					elemento = ventana.tabla.getSelectedRow();
					bandera = true;
		        } else if(e.getClickCount() == 2) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
		        	bandera = true;
		        	editar();
		        }
		    }
		});
	}
	
	public void iniciar() {

		ventana.chckbx1.setVisible(true);
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosEmpleados.getFiltro()));
		ventana.comboBox1.setVisible(true);
		ventana.txt1.setVisible(true);
		actualizar();
		ventana.setVisible(true);
	}
	
	private void actualizar() {
		
		ventana.tabla.setModel(dtosEmpleados.getTablaEmpleados((String)ventana.comboBox1.getSelectedItem(), 
																		ventana.chckbx1.isSelected(), 
																		ventana.txt1.getText()));
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
		ventana.tabla.setDefaultEditor(Object.class, null);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.comboBox1) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.chckbx1) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.btnNuevo) {
			
			ventanaNuevoEmpleado = new Nuevo("Cargar nuevo empleado");
			CtrlNuevoEmpleado ctrlNuevoEmpleado = new CtrlNuevoEmpleado(ventanaNuevoEmpleado);
			ctrlNuevoEmpleado.iniciar();
			ventanaNuevoEmpleado.btnVolver.addActionListener(this);
		}
		
		if(ventanaNuevoEmpleado != null) {
			
			if(e.getSource() == ventanaNuevoEmpleado.btnVolver) {
	
				actualizar();
			}
		}
	
		if(e.getSource() == ventana.btnEditar) {
			
			editar();
		}
		
		if(ventanaEditarEmpleado != null) {

			if(e.getSource() == ventanaEditarEmpleado.btnVolver) {
	
				actualizar();
			}
		}
		
		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch (PrinterException f) {
				
				JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
				CtrlLogErrores.guardarError(f.getMessage());
			}
		}

		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void editar() {
		
		ventana.tabla.clearSelection();
		if(!bandera) {
			
			JOptionPane.showMessageDialog(null, "Debe seleccionar un alumno para editar.");
			return;
		}
		dtosEmpleados.setEmpleado(elemento);
		ventanaEditarEmpleado = new Nuevo("Editar datos empleado");
		CtrlEditarEmpleado ctrlEditarEmpleado = new CtrlEditarEmpleado(ventanaEditarEmpleado);
		ctrlEditarEmpleado.iniciar();
		ventanaEditarEmpleado.btnVolver.addActionListener(this);
	}
}