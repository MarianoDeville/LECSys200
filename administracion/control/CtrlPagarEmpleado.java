package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;

import modelo.DtosPagos;
import vista.Nuevo;

public class CtrlPagarEmpleado implements ActionListener{
	
	private Nuevo ventana;
	private DtosPagos dtosPagos;
	
	public CtrlPagarEmpleado(Nuevo vista) {
		
		this.ventana = vista;
		this.dtosPagos = new DtosPagos();
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {
		
		ventana.separador1.setVisible(false);
		ventana.separador2.setVisible(false);
		ventana.formato.setVisible(false);
		ventana.txtMes.setVisible(false);
		ventana.txtAño.setVisible(false);
		ventana.txtNombre.setEditable(false);
		ventana.txtApellido.setEditable(false);
		ventana.txtDia.setEditable(false);
		ventana.txtDia.setColumns(7);
		ventana.txtMes.setEditable(false);
		ventana.txtAño.setEditable(false);
		ventana.txtDireccion.setEditable(false);
		ventana.txtDNI.setEditable(false);
		ventana.txtEmail.setEditable(false);
		ventana.txtTelefono.setEditable(false);
		ventana.lblcomboBox1.setText("Concepto:");
		ventana.lblcomboBox2.setText("Forma pago:");
		ventana.lblcomboBox2.setVisible(true);
		ventana.comboBox2.setVisible(true);
		ventana.lblTxt1.setText("Monto:");
		ventana.lblTxt1.setVisible(true);
		ventana.txt1.setColumns(6);
		ventana.txt1.setVisible(true);
		ventana.lblTxt2.setText("Factura:");
		ventana.lblTxt2.setVisible(true);
		ventana.txt2.setVisible(true);
		ventana.lblTxt3.setText("Comentario:");
		ventana.lblTxt3.setVisible(true);
		ventana.txt3.setColumns(30);
		ventana.txt3.setVisible(true);
		dtosPagos.setInfoEmpleado();
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosPagos.listadoConcepto()));
		ventana.comboBox2.setModel(new DefaultComboBoxModel<String>(dtosPagos.formasPago()));
		ventana.txtLegajo.setText(dtosPagos.getId());
		ventana.txtNombre.setText(dtosPagos.getNombre());
		ventana.txtApellido.setText(dtosPagos.getApellido());
		ventana.txtDNI.setText(dtosPagos.getDNI());
		ventana.txtDireccion.setText(dtosPagos.getDireccion());
		ventana.txtTelefono.setText(dtosPagos.getTelefono());
		ventana.txtEmail.setText(dtosPagos.getEmail());
		ventana.txtDia.setText(dtosPagos.getFecha());
		ventana.txt1.setText(dtosPagos.getSuma());
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnGuardar) {
		
			guardar();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			dtosPagos.borrarInfo();
			ventana.dispose();
		}
	}
	
	private void guardar() {
		
		if(!dtosPagos.setSuma(ventana.txt1.getText())) {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText(dtosPagos.getMsgError());
			return;
		}
		dtosPagos.setComentario(ventana.txt3.getText());
		dtosPagos.setConcepto((String)ventana.comboBox1.getSelectedItem());
		dtosPagos.setMetodoPago((String)ventana.comboBox2.getSelectedItem());
		
		if(dtosPagos.registrarPagoEmpleado()) {
			
			ventana.lblMsgError.setForeground(Color.BLUE);
			ventana.btnGuardar.setEnabled(false);
		} else {
			
			ventana.lblMsgError.setForeground(Color.RED);
		}
		ventana.lblMsgError.setText(dtosPagos.getMsgError());
	}
}