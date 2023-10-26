package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import dao.OperadorSistema;
import modelo.DtosUsuarios;
import vista.ABML;
import vista.NuevoUsuario;

public class CtrlABMLUsuarios implements ActionListener {
	
	private ABML ventana;
	private DtosUsuarios dtosUsuario;
	private NuevoUsuario ventanaNuevoUsuario;
	private NuevoUsuario ventanaEditarUsuario;
	private int elemento;

	public CtrlABMLUsuarios(ABML vista) {
		
		this.ventana = vista;
		this.dtosUsuario = new DtosUsuarios();
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

		if(!OperadorSistema.isAcceso()) {
			
			ventana.dispose();
			return;
		}
		actualizar();
		ventana.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnNuevo) {
			
			nuevo();
		}
		
		if(ventanaNuevoUsuario !=null) {
		
			if(e.getSource() == ventanaNuevoUsuario.btnVolver) {
				
				ventanaNuevoUsuario = null;
				actualizar();
			}
		}
		
		if(e.getSource() == ventana.btnEditar) {
			
			editar();
		}
		
		if(ventanaEditarUsuario != null) {
			
			if(e.getSource() == ventanaEditarUsuario.btnVolver) {
				
				ventanaEditarUsuario = null;
				actualizar();
			}
		}
		
		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch (Exception f) {

				JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
				CtrlLogErrores.guardarError(f.getMessage());
			}
		}
		
		if(e.getSource() == ventana.btnVolver) {

			if(ventanaEditarUsuario != null)
				ventanaEditarUsuario.dispose();
			if(ventanaNuevoUsuario != null)
				ventanaNuevoUsuario.dispose();
			ventana.dispose();
		}
	}
	
	private void actualizar() {
		
		elemento = -1;
		ventana.tabla.setModel(dtosUsuario.getTablaUsuarios());
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(50);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(160);
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(100);
		ventana.tabla.getColumnModel().getColumn(1).setMinWidth(100);
		ventana.tabla.getColumnModel().getColumn(1).setMaxWidth(360);
		ventana.tabla.getColumnModel().getColumn(1).setPreferredWidth(260);
		ventana.tabla.setDefaultEditor(Object.class, null);
		ventana.tabla.clearSelection();
	}
	
	private void nuevo() {
	
		if(ventanaNuevoUsuario != null) {

			JOptionPane.showMessageDialog(null, "Le ventana \"Nuevo\" ya se encuentra abierta.");
			return;
		}
		
		ventanaNuevoUsuario = new NuevoUsuario("Crear usuario del sistema.");
		CtrlNuevoUsuario ctrlNuevoUsuario = new CtrlNuevoUsuario(ventanaNuevoUsuario);
		ctrlNuevoUsuario.iniciar();
		ventanaNuevoUsuario.btnVolver.addActionListener(this);
	}
	
	private void editar() {
		
		if(ventanaEditarUsuario != null) {

			JOptionPane.showMessageDialog(null, "Le ventana \"Editar\" ya se encuentra abierta.");
			return;
		}
		
		if(elemento != -1) {

			dtosUsuario.setUsuario(elemento);
			elemento = -1;
			ventanaEditarUsuario = new NuevoUsuario("Editar usuario.");
			CtrlEditarUsuario ctrlEditarUsuario = new CtrlEditarUsuario(ventanaEditarUsuario);
			ctrlEditarUsuario.iniciar();
			ventanaEditarUsuario.btnVolver.addActionListener(this);
		} else {
			
			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento para editar.");
		}
	}
}