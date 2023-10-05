package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.DtosUsuarios;
import vista.NuevoUsuario;

public class CtrlNuevoUsuario implements ActionListener {

	private NuevoUsuario ventana;
	private DtosUsuarios dtosUsuarios;
	private int fila;
	private int columna;

	public CtrlNuevoUsuario(NuevoUsuario vista) {
		
		this.ventana = vista;
		this.dtosUsuarios = new DtosUsuarios();
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.txtUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				ventana.lblMsgError.setText("");
			}
		});
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
		
		ventana.lblNivelAcceso.setVisible(true);
		ventana.scrollTabla.setVisible(true);
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosUsuarios.getListaEmpleados()));
		ventana.tabla.setModel(dtosUsuarios.getTablaPermisos());
		ventana.setVisible(true);
	}
	
	private void limpiarCampos() {
		
		ventana.txtUsuario.setText("");
		ventana.txtContraseña.setText("");
		ventana.txtReContraseña.setText("");
	}
	
	private void procesarClicks() {
		
		if(fila == 0) {
			
			for(int i = 1; i < ventana.tabla.getRowCount(); i++) {
				
				ventana.tabla.setValueAt((boolean)ventana.tabla.getValueAt(fila, columna), i, columna);
			}
		}
		ventana.lblMsgError.setText("");
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnGuardar) {
		
			guardar();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void guardar() {

		dtosUsuarios.setNombre(ventana.txtUsuario.getText());
		dtosUsuarios.setContraseña(ventana.txtContraseña.getPassword());
		dtosUsuarios.setReContraseña(ventana.txtReContraseña.getPassword());
		dtosUsuarios.setDNIEmpleado(ventana.comboBox1.getSelectedIndex());
		dtosUsuarios.setPermisos(ventana.tabla);
		String msgError = dtosUsuarios.checkInformacion(true);
		ventana.lblMsgError.setForeground(Color.RED);
		ventana.lblMsgError.setText(msgError);
		
		if(msgError.equals("")) {

			if(dtosUsuarios.setNuevoUsuario()) {
				
				limpiarCampos();
				dtosUsuarios.clearInfo();
				ventana.lblMsgError.setForeground(Color.BLUE);
				ventana.lblMsgError.setText("Los datos se guardaron correctamente.");
				
				if(dtosUsuarios.isPrimerUsuario()) {
					
					JOptionPane.showMessageDialog(null, "El Programa se cerrará para que ingrese con el nuevo usuario creado.");
					System.exit(0);
				}
			} else {
				
				ventana.lblMsgError.setForeground(Color.RED);
				ventana.lblMsgError.setText("Error al intentar guardar la información.");
			}
		}
	}
}