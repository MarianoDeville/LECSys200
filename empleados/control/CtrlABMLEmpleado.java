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
	private int elemento = -1;
	
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
		        } else if(e.getClickCount() == 2) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
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
		
		elemento = -1;
		ventana.tabla.setModel(dtosEmpleados.getTablaEmpleados((String)ventana.comboBox1.getSelectedItem(), 
																		ventana.chckbx1.isSelected(), 
																		ventana.txt1.getText()));
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
		ventana.tabla.setDefaultEditor(Object.class, null);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.comboBox1 && ventana.isVisible()) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.chckbx1 && ventana.isVisible()) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.btnNuevo) {
			
			nuevo();
		}
		
		if(ventanaNuevoEmpleado != null) {
			
			if(e.getSource() == ventanaNuevoEmpleado.btnVolver) {
	
				ventanaNuevoEmpleado = null;
				actualizar();
			}
		}
	
		if(e.getSource() == ventana.btnEditar) {
			
			editar();
		}
		
		if(ventanaEditarEmpleado != null) {

			if(e.getSource() == ventanaEditarEmpleado.btnVolver) {
	
				ventanaEditarEmpleado = null;
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

			if(ventanaNuevoEmpleado != null)
				ventanaNuevoEmpleado.dispose();
			if(ventanaEditarEmpleado != null)
				ventanaEditarEmpleado.dispose();
			ventana.dispose();
		}
	}
	private void nuevo() {
	
		if(ventanaNuevoEmpleado != null) {

			JOptionPane.showMessageDialog(null, "La ventana \"Nuevo\" ya se encuentra abierta.");
			return;
		}
		
		ventanaNuevoEmpleado = new Nuevo("Cargar nuevo empleado");
		CtrlNuevoEmpleado ctrlNuevoEmpleado = new CtrlNuevoEmpleado(ventanaNuevoEmpleado);
		ctrlNuevoEmpleado.iniciar();
		ventanaNuevoEmpleado.btnVolver.addActionListener(this);
	}
	
	private void editar() {
		
		if(ventanaEditarEmpleado != null) {

			JOptionPane.showMessageDialog(null, "La ventana \"Editar\" ya se encuentra abierta.");
			return;
		}
		
		ventana.tabla.clearSelection();
		
		if(elemento == -1) {
			
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