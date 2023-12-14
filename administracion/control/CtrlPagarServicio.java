package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.DtosPagos;
import vista.Nuevo;

public class CtrlPagarServicio implements ActionListener {
	
	private Nuevo ventana;
	private DtosPagos dtosPagos;

	public CtrlPagarServicio(Nuevo vista) {
		
		this.ventana = vista;
		this.dtosPagos = new DtosPagos();
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {
		
		ventana.comboBox1.setVisible(false);
		ventana.lblLegajo.setText("ID:");
		ventana.txtLegajo.setText(dtosPagos.getId());
		ventana.lblNombre.setText("Nombre:");
		ventana.txtNombre.setEditable(false);
		ventana.txtNombre.setText(dtosPagos.getNombreEmpresa());
		ventana.lblApellido.setText("Dirección:");
		ventana.txtApellido.setEditable(false);
		ventana.txtApellido.setText(dtosPagos.getDireccionEmpresa());
		ventana.lblDNI.setText("CUIT:");
		ventana.txtDNI.setEditable(false);
		ventana.txtDNI.setText(dtosPagos.getCUIT());
		ventana.lblNacimiento.setVisible(false);
		ventana.txtDia.setVisible(false);
		ventana.txtAño.setVisible(false);
		ventana.txtMes.setVisible(false);
		ventana.formato.setVisible(false);
		ventana.separador1.setVisible(false);
		ventana.separador2.setVisible(false);
		ventana.lblDireccion.setText("Concepto:");
		ventana.lblEmail.setText("Monto:");
		ventana.txtEmail.setColumns(6);
		ventana.lblTelefono.setText("Forma pago:");
		ventana.txtTelefono.setColumns(27);
		ventana.lblTxt1.setVisible(true);
		ventana.lblTxt1.setText("Comentario:");
		ventana.txt1.setVisible(true);
		ventana.txt1.setColumns(27);
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnGuardar) {
			
			pagar();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void pagar() {
		
		if(!dtosPagos.setSuma(ventana.txtEmail.getText())) {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText(dtosPagos.getMsgError());
			return;
		}
		dtosPagos.setConcepto(ventana.txtDireccion.getText());
		dtosPagos.setMetodoPago(ventana.txtTelefono.getText());
		dtosPagos.setComentario(ventana.txt1.getText());
		
		if(!dtosPagos.registrarPagoServicio()) {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText(dtosPagos.getMsgError());
			return;
		}
		ventana.lblMsgError.setForeground(Color.BLUE);
		ventana.lblMsgError.setText(dtosPagos.getMsgError());
		ventana.txtEmail.setEditable(false);
		ventana.txtDireccion.setEditable(false);
		ventana.txtTelefono.setEditable(false);
		ventana.txt1.setEditable(false);
		ventana.btnGuardar.setEnabled(false);
	}
}