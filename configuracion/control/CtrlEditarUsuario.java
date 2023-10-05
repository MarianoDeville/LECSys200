package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import modelo.DtosUsuarios;
import vista.NuevoUsuario;

public class CtrlEditarUsuario implements ActionListener {

	private NuevoUsuario ventana;
	private DtosUsuarios dtosUsuario;
	private int fila;
	private int columna;
	
	public CtrlEditarUsuario(NuevoUsuario vista) {
		
		this.ventana = vista;
		this.dtosUsuario = new DtosUsuarios();
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnBorrar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {

		        	fila = ventana.tabla.getSelectedRow();
		        	columna = ventana.tabla.getSelectedColumn();
					procesarClicks();
		        }
			}
		});
	}
	
	public void iniciar() {

		boolean cuentaPropia = dtosUsuario.isCuentaPropia();
		ventana.btnBorrar.setVisible(!cuentaPropia);
		ventana.btnGuardar.setEnabled(!cuentaPropia);
		ventana.lblMsgError.setForeground(Color.RED);
		
		if(cuentaPropia)
			ventana.lblMsgError.setText("No puede editar su propio usuario.");
		ventana.txtUsuario.setEditable(false);
		ventana.lblNivelAcceso.setVisible(true);
		ventana.scrollTabla.setVisible(true);
		ventana.tabla.setModel(dtosUsuario.getTablaPermisos());
		ventana.txtUsuario.setText(dtosUsuario.getNombre());
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosUsuario.getListaEmpleados()));
		ventana.setVisible(true);
	}
	
	private void procesarClicks() {
		
		if(fila == 0) {
			
			for(int i = 1; i < ventana.tabla.getRowCount(); i++) {
				
				ventana.tabla.setValueAt((boolean)ventana.tabla.getValueAt(fila, columna), i, columna);
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardarCambios();
		}
		
		if(e.getSource() == ventana.btnBorrar) {
			
			borrarUsuario();
		}

		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void guardarCambios() {
		
		dtosUsuario.setContraseña(ventana.txtContraseña.getPassword());
		dtosUsuario.setReContraseña(ventana.txtReContraseña.getPassword());
		dtosUsuario.setPermisos(ventana.tabla);
		String msgError = dtosUsuario.checkInformacion(false);
		ventana.lblMsgError.setForeground(Color.RED);
		ventana.lblMsgError.setText(msgError);
		
		if(msgError.equals("")) {
			
			if(dtosUsuario.updateUsuario()) {
				
				dtosUsuario.clearInfo();
				ventana.lblMsgError.setForeground(Color.BLUE);
				ventana.lblMsgError.setText("Los datos se actualizaron correctamente.");
				ventana.btnBorrar.setEnabled(false);
				ventana.btnGuardar.setEnabled(false);
			} else {
				
				ventana.lblMsgError.setForeground(Color.RED);
				ventana.lblMsgError.setText("Error al intentar guardar la información.");
			}				
		}
	}
	
	private void borrarUsuario() {
		
		if(JOptionPane.showConfirmDialog(null, "¿Seguro de borrar el usuario?", "Alerta!", JOptionPane.YES_NO_OPTION) == 1)
			return;
		dtosUsuario.setContraseña(null);
		dtosUsuario.setReContraseña(null);
		dtosUsuario.setEstado(0);
		
		if(dtosUsuario.updateUsuario()) {
			
			dtosUsuario.clearInfo();
			ventana.lblMsgError.setForeground(Color.BLUE);
			ventana.lblMsgError.setText("Usuario eliminado.");
			ventana.btnBorrar.setEnabled(false);
			ventana.btnGuardar.setEnabled(false);
		} else {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText("Error al intentar guardar la información.");
		}
	}
}