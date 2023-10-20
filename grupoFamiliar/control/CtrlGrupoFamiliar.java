package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.JOptionPane;
import vista.ABML;
import vista.ListadoDoble;
import modelo.DtosGrupoFamiliar;

public class CtrlGrupoFamiliar implements ActionListener {

	private ABML ventana;
	private DtosGrupoFamiliar dtos;
	private ListadoDoble ventanaEditarGrupo;
	private int elemento = -1;
	
	public CtrlGrupoFamiliar(ABML vista) {
		
		this.ventana = vista;
		this.dtos = new DtosGrupoFamiliar();
		this.ventana.chckbx1.addActionListener(this);
		this.ventana.btnEditar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
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
	
		elemento = -1;
		ventana.panelABML.remove(ventana.btnNuevo);
		ventana.chckbx1.setVisible(false);
		ventana.txt1.setVisible(true);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.chckbx1 && ventana.isVisible()) {
			
			actualizar();
		}
	
		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch (PrinterException f) {
				
				f.printStackTrace();
			}
		}
		
		if(e.getSource() == ventana.btnEditar) {

			editar();
		}
		
		if(ventanaEditarGrupo != null) {
			
			if(e.getSource() == ventanaEditarGrupo.btnVolver) {
				
				actualizar();
			}
		}

		if(e.getSource() == ventana.btnVolver) {

			ventana.dispose();
		}
	}
	
	private void actualizar() {

		elemento = -1;
		ventana.tabla.setModel(dtos.getTablaFamilias(ventana.chckbx1.isSelected(), ventana.txt1.getText()));
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(200);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(250);
		ventana.tabla.setDefaultEditor(Object.class, null);
		ventana.tabla.clearSelection();
	}
	
	private void editar() {
		
		if(elemento == -1) {
			
			JOptionPane.showMessageDialog(null, "No ha seleccionado ning�n elemento.");
			return;
		}
		dtos.setFamiliaSeleccionada(elemento);
		elemento = -1;
		ventanaEditarGrupo = new ListadoDoble("Edicion de la informaci�n del grupo.");
		CtrlEditarGrupoFamiliar ctrlEditarGrupo = new CtrlEditarGrupoFamiliar(ventanaEditarGrupo);
		ctrlEditarGrupo.iniciar();
		ventanaEditarGrupo.btnVolver.addActionListener(this);
	}
}