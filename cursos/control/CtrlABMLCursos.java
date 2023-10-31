package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.JOptionPane;
import modelo.DtosCurso;
import vista.ABML;
import vista.NuevoCurso;

public class CtrlABMLCursos implements ActionListener {
	
	private ABML ventana;
	private DtosCurso dtosCurso;
	private NuevoCurso ventanaCrearCurso;
	private NuevoCurso ventanaEditarCurso;
	private int elemento;
	
	public CtrlABMLCursos(ABML vista) {
		
		this.ventana = vista;
		this.dtosCurso = new DtosCurso();
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnEditar.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
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
		
		elemento = -1;
		actualizar();
		ventana.setVisible(true);
	}

	private void actualizar() {
		
		dtosCurso.limpiarVariable();
		ventana.tabla.setModel(dtosCurso.getTablaCursos());
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
		ventana.tabla.getColumnModel().getColumn(3).setPreferredWidth(40);
		ventana.tabla.getColumnModel().getColumn(3).setMaxWidth(50);
		ventana.tabla.getColumnModel().getColumn(4).setPreferredWidth(50);
		ventana.tabla.getColumnModel().getColumn(4).setMaxWidth(70);
		ventana.tabla.setDefaultEditor(Object.class, null);
		ventana.tabla.clearSelection();
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnNuevo) {
			
			nuevo();
		}
		
		if(ventanaCrearCurso != null) {
			
			if(e.getSource() == ventanaCrearCurso.btnVolver) {
				
				ventanaCrearCurso = null;
				actualizar();
			}
		}

		if(e.getSource() == ventana.btnEditar) {

			editar();
		}
		
		if(ventanaEditarCurso != null) {
			
			if(e.getSource() == ventanaEditarCurso.btnVolver) {
				
				ventanaEditarCurso = null;
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
	
	private void nuevo() {
		
		if(ventanaCrearCurso != null) {
			
			JOptionPane.showMessageDialog(null, "Le ventana \"Editar\" ya se encuentra abierta.");
			ventanaCrearCurso.setVisible(true);
			return;
		}
		
		ventanaCrearCurso = new NuevoCurso("Crear un nuevo curso", ventana.getX(), ventana.getY());
		CtrlNuevoCurso ctrlNuevoCurso = new CtrlNuevoCurso(ventanaCrearCurso);
		ctrlNuevoCurso.iniciar();
		ventanaCrearCurso.btnVolver.addActionListener(this);
	}
	
	private void editar() {
		
		if(ventanaEditarCurso != null) {
			
			JOptionPane.showMessageDialog(null, "Le ventana \"Editar\" ya se encuentra abierta.");
			ventanaEditarCurso.setVisible(true);
			return;
		}
		
		if(elemento == -1) {
		
			JOptionPane.showMessageDialog(null, "Debe seleccionar un curso para editar.");
			return;
		}
		dtosCurso.setCursoElegido(elemento);
		elemento = -1;
		ventanaEditarCurso = new NuevoCurso("Edición de curso", ventana.getX(), ventana.getY());
		CtrlEditarCurso ctrlEditarCurso = new CtrlEditarCurso(ventanaEditarCurso);
		ctrlEditarCurso.iniciar();
		ventanaEditarCurso.btnVolver.addActionListener(this);
	}
}