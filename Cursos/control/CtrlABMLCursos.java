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
	private boolean bandera;

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
		
		bandera = false;
		actualizar();
		ventana.setVisible(true);
	}

	private void actualizar() {
		
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
			
			ventanaCrearCurso = new NuevoCurso("Crear un nuevo curso");
			CtrlNuevoCurso ctrlNuevoCurso = new CtrlNuevoCurso(ventanaCrearCurso);
			ctrlNuevoCurso.iniciar();
			ventanaCrearCurso.btnVolver.addActionListener(this);
		}
		
		if(ventanaCrearCurso != null) {
			
			if(e.getSource() == ventanaCrearCurso.btnVolver) {
				
				actualizar();
			}
		}

		if(e.getSource() == ventana.btnEditar) {

			editar();
		}
		
		if(ventanaEditarCurso != null) {
			
			if(e.getSource() == ventanaEditarCurso.btnVolver) {
				
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
		
		String idCurso = (String)ventana.tabla.getValueAt(elemento, 0);
		
		if(!bandera) {
		
			JOptionPane.showMessageDialog(null, "Debe seleccionar un curso para editar.");
			return;
		}
		bandera = false;
		ventanaEditarCurso = new NuevoCurso("Edición de curso");
		CtrlEditarCurso ctrlEditarCurso = new CtrlEditarCurso(ventanaEditarCurso);
		ctrlEditarCurso.iniciar(idCurso);
		ventanaEditarCurso.btnVolver.addActionListener(this);
	}
}