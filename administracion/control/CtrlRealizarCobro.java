package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import modelo.DtosCobros;
import vista.Cobro;
import vista.ReciboCobro;

public class CtrlRealizarCobro implements ActionListener {
	
	private Cobro ventana;
	private DtosCobros dtosCobros;
	private float calculoDescuentoGrupo;
	
	public CtrlRealizarCobro(Cobro vista) {
		
		this.ventana = vista;
		this.dtosCobros = new DtosCobros();
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btnCobrar.addActionListener(this);
		this.ventana.btnCentral.addActionListener(this);
		this.ventana.txt3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				actualizar();
			}
		});
		this.ventana.txt4.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				actualizar();
			}
		});
		this.ventana.comboBox.addActionListener(this);
		this.ventana.chckbxEnviarEmail.addActionListener(this);
	}

	public void iniciar() {

		ventana.chckbxTabla2.setVisible(false);
		ventana.txtTabla2.setVisible(false);
		ventana.scrollTabla2.setVisible(false);
		ventana.lbl1.setVisible(true);
		ventana.lbl1.setText("Descuento grupo:");
		ventana.lbl2.setText("Valor cuota:");		
		ventana.lbl3.setText("Recargo mora:");
		ventana.lbl4.setText("Descuento pago efectivo:");
		ventana.lbl5.setVisible(true);
		ventana.lbl5.setText("Monto a pagar:");
		ventana.lblComboBox.setVisible(true);
		ventana.lblComboBox.setText("Concepto:");
		ventana.txt1.setVisible(true);
		ventana.txt1.setEditable(false);
		ventana.txt2.setEditable(false);
		ventana.txt3.setEditable(false);
		ventana.txt4.setEditable(false);
		ventana.txt5.setVisible(true);
		ventana.txt5.setEditable(false);
		ventana.txtNombre.setText(dtosCobros.getNombre());		
		ventana.btnCobrar.setEnabled(false);
		ventana.btnCentral.setVisible(true);
		ventana.btnCentral.setText("Borrar");
		ventana.btnCentral.setEnabled(false);
		ventana.comboBox.setVisible(true);
		ventana.comboBox.setModel(new DefaultComboBoxModel<String>(dtosCobros.getListadoConceptos()));
		ventana.tabla1.setModel(dtosCobros.getTablaSeleccionados());
		ventana.tabla1.getColumnModel().getColumn(0).setPreferredWidth(45);
		ventana.tabla1.getColumnModel().getColumn(0).setMaxWidth(55);
		
		if(dtosCobros.getEmail().length() > 2) {
			
			ventana.chckbxEnviarEmail.setSelected(true);
			ventana.lblEmail.setVisible(true);
			ventana.txtEmail.setVisible(true);
			ventana.txtEmail.setText(dtosCobros.getEmail());
		}
		calculoDescuentoGrupo = dtosCobros.getDescuentoGrupo() * dtosCobros.getSumaCuotas() /100;
		ventana.txt1.setText(String.format("%.2f", calculoDescuentoGrupo));
		ventana.txt2.setText(String.format("%.2f", dtosCobros.getSumaCuotas()));
		ventana.setVisible(true);
	}
		
	private void actualizar() {
		
		String mensaje = null;
		
		if(ventana.comboBox.getSelectedIndex() == 0) {
			
			ventana.txt3.setEditable(false);
			ventana.txt4.setEditable(false);
			ventana.txt5.setText("0");
			ventana.btnCobrar.setEnabled(false);
			ventana.btnCentral.setEnabled(false);
			return;
		}
		ventana.txt3.setEditable(true);
		ventana.txt4.setEditable(true);
		ventana.btnCobrar.setEnabled(true);
		ventana.btnCentral.setEnabled(true);
		ventana.lblMsgError.setForeground(Color.RED);
		ventana.lblMsgError.setText("");
		mensaje = dtosCobros.setRecargoMora(ventana.txt3.getText());
		
		if(mensaje == null)
			mensaje = dtosCobros.setDescuentoContado(ventana.txt4.getText());
		dtosCobros.setCantidadCuotasSeleccionadas(ventana.comboBox.getSelectedIndex());
		ventana.lblMsgError.setText(mensaje);
		ventana.txt5.setText(dtosCobros.getCalculoCobro((String)ventana.comboBox.getSelectedItem()));
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.comboBox) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.chckbxEnviarEmail) {
			
			ventana.chckbxEnviarEmail.setSelected(ventana.chckbxEnviarEmail.isSelected());
			ventana.lblEmail.setVisible(ventana.chckbxEnviarEmail.isSelected());
			ventana.txtEmail.setVisible(ventana.chckbxEnviarEmail.isSelected());
			ventana.txtEmail.setText(dtosCobros.getEmail());
		}
		
		if(e.getSource() == ventana.btnCobrar) {
			
			registraCobro();
		}
		
		if(e.getSource() == ventana.btnCentral) {
			
			eliminarDeuda();	
		}

		if(e.getSource() == ventana.btnVolver) {
			
			dtosCobros.setBorrarSeleccionados();
			ventana.dispose();
		}
	}

	private void registraCobro() {

		dtosCobros.setFactura(ventana.txtFactura.getText());
		ventana.lblMsgError.setForeground(Color.BLUE);
		ventana.lblMsgError.setText("Procesando la operación.");
		dtosCobros.setEnviarEmail(ventana.chckbxEnviarEmail.isSelected());

		if(ventana.chckbxEnviarEmail.isSelected())
			dtosCobros.setEmail(ventana.txtEmail.getText());
		else 
			dtosCobros.setEmail("");
		String error = dtosCobros.validarInformación(false, false);
		
		if(error.length() > 0) {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText(error);
			return;
		}
		
		if(dtosCobros.guardarCobroCuota()) {
	
			if(ventana.chckbxEnviarEmail.isSelected()) {
				
				EmailSenderService emailService = new EmailSenderService();
				if(emailService.mandarCorreo(dtosCobros.getEmail(), "Recibo de pago", dtosCobros.getCuerpoEmail())) {
					
					ventana.lblMsgError.setForeground(Color.RED);
					ventana.lblMsgError.setText("Error en el emvío del email.");		
				}
			} else {
			
				ReciboCobro ventanaReciboPago = new ReciboCobro("Comprobante de pago");
				CtrlReciboCobrarInscripcion ctrolReciboInscripcion = new CtrlReciboCobrarInscripcion(ventanaReciboPago);
				ctrolReciboInscripcion.iniciar();
			}
			ventana.lblMsgError.setForeground(Color.BLUE);
			ventana.lblMsgError.setText("Operación almacenada en la base de datos.");
			ventana.btnCobrar.setEnabled(false);
			ventana.btnCentral.setEnabled(false);
			ventana.comboBox.setEnabled(false);
			dtosCobros.setBorrarSeleccionados();
			return;
		}
		ventana.lblMsgError.setForeground(Color.RED);
		ventana.lblMsgError.setText("Error al intentar guardar la información en la base de datos.");
	}
	
	private void eliminarDeuda() {

		if(JOptionPane.showConfirmDialog(null, "¿Esta seguro de borrar la deuda seleccionada?", "Alerta!", JOptionPane.YES_NO_OPTION) == 0) {
		
			if(dtosCobros.setBorrarDeuda()) {
				
				ventana.lblMsgError.setForeground(Color.BLUE);
				ventana.lblMsgError.setText("Operación almacenada en la base de datos.");
				ventana.btnCobrar.setEnabled(false);
				ventana.btnCentral.setEnabled(false);
				ventana.comboBox.setEnabled(false);
				dtosCobros.setBorrarSeleccionados();
				return;
			} 
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText("Error al intentar guardar la información en la base de datos.");
		}
	}
}