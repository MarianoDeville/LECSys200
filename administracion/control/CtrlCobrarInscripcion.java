package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.DtosCobros;
import vista.Cobro;
import vista.ReciboCobro;

public class CtrlCobrarInscripcion implements ActionListener {
	
	private Cobro ventana;
	private DtosCobros dtosCobros;
	private boolean checkInfo;
	private boolean haySeleccion;
	private int elemento;
		
	public CtrlCobrarInscripcion(Cobro vista) {
		
		this.ventana = vista;
		this.dtosCobros = new DtosCobros();
		this.ventana.chckbxEnviarEmail.addActionListener(this);
		this.ventana.chckbxTabla2.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btnCobrar.addActionListener(this);
		this.ventana.txt1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				actualizoSuma();
			}
		});
		this.ventana.txt3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				checkInfo = true;
				actualizoSuma();
			}
		});
		this.ventana.txt2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				checkInfo = true;
				actualizoSuma();
			}
		});
		this.ventana.txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				dtosCobros.setEmail(ventana.txtEmail.getText());
			}
		});		
		this.ventana.txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				dtosCobros.setNombre(ventana.txtNombre.getText());
			}
		});
		this.ventana.txtTabla2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				actualizarTabla();
			}
		});
		this.ventana.tabla2.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {

					elemento = ventana.tabla2.getSelectedRow();
					haySeleccion = (boolean)ventana.tabla2.getValueAt(elemento, 2);
					actualizarDatos();
		        }
			}
		});
	}

	public void iniciar() {

		checkInfo = false; 
		haySeleccion = false;
		ventana.lbl1.setText("Porcentage descuento:");
		ventana.lbl2.setText("Inscripción:");
		ventana.lbl3.setText("Descuento pago efectivo:");
		ventana.lbl4.setText("Total a pagar:");
		ventana.chckbxTabla2.setVisible(!dtosCobros.getReinscripcion());
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		derecha.setHorizontalAlignment(JLabel.RIGHT);
		ventana.tabla1.setModel(dtosCobros.getTablaSeleccionados());
		ventana.tabla1.getColumnModel().getColumn(0).setPreferredWidth(45);
		ventana.tabla1.getColumnModel().getColumn(0).setMaxWidth(55);	
		ventana.tabla1.getColumnModel().getColumn(5).setCellRenderer(derecha);
		ventana.tabla1.setDefaultEditor(Object.class, null);
		actualizarDatos();
		actualizarTabla();
		actualizoSuma();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.chckbxTabla2 && ventana.isVisible()) {
			
			actualizarTabla();
			actualizarDatos();
		}
		
		if(e.getSource() == ventana.chckbxEnviarEmail) {
			
			ventana.lblEmail.setVisible(ventana.chckbxEnviarEmail.isSelected());
			ventana.txtEmail.setVisible(ventana.chckbxEnviarEmail.isSelected());
		}
		
		if(e.getSource() == ventana.btnCobrar) {
				
			registraCobro();
		}

		if(e.getSource() == ventana.btnVolver) {
			
			dtosCobros.deleteInfo();
			ventana.dispose();
		}
	}
	
	private void actualizarDatos() {

		limpiarOtros();

		if(!haySeleccion) {
			
			ventana.lbl1.setVisible(false);
			ventana.txt1.setVisible(false);
		}

		if(ventana.tabla1.getRowCount() == 1) {
			
			ventana.txtNombre.setEditable(false);
			ventana.lbl1.setVisible(false);
			ventana.txt1.setVisible(false);
			ventana.txtEmail.setText(dtosCobros.getEmail());
		} else {

			ventana.txtNombre.setEditable(true);
			ventana.lbl1.setVisible(true);
			ventana.txt1.setVisible(true);
		}

		if(ventana.chckbxTabla2.isSelected()) {
			
			ventana.lbl1.setVisible(true);
			ventana.txt1.setVisible(true);
		}
		
		if(haySeleccion)
			dtosCobros.setFamiliaSeleccionada(elemento);
		else
			dtosCobros.recuperarInfo();

		if(dtosCobros.getEmail().length() > 2) {
			
			ventana.chckbxEnviarEmail.setSelected(true);
			ventana.lblEmail.setVisible(true);
			ventana.txtEmail.setVisible(true);
		} else {
			
			ventana.chckbxEnviarEmail.setSelected(false);
			ventana.lblEmail.setVisible(false);
			ventana.txtEmail.setVisible(false);
		}
		ventana.txtNombre.setText(dtosCobros.getNombre());
		ventana.txt1.setText(dtosCobros.getDescuentoGrupo());
		ventana.txtEmail.setText(dtosCobros.getEmail());
	}
	
	private void actualizoSuma() {
	
		String mensaje = null;
		ventana.lblMsgError.setForeground(Color.RED);
		ventana.lblMsgError.setText("");
		mensaje = dtosCobros.setDescuentoGrupo(ventana.txt1.getText());
		
		if(mensaje == null)
			mensaje = dtosCobros.setDescuentoContado(ventana.txt3.getText());
		
		if(mensaje == null)
			mensaje = dtosCobros.setInscripcion(ventana.txt2.getText());
		
		if(checkInfo)
			ventana.lblMsgError.setText(mensaje);
		ventana.txt4.setText(String.format("%.2f", dtosCobros.getCalculoMontoTotal()));
	}

	private void actualizarTabla() {

		ventana.txtTabla2.setVisible(ventana.chckbxTabla2.isSelected());
		ventana.scrollTabla2.setVisible(ventana.chckbxTabla2.isSelected());
		
		if(ventana.chckbxTabla2.isSelected()) {
			
			ventana.tabla2.setModel(dtosCobros.getTablaFamilias(true, ventana.txtTabla2.getText()));
			ventana.tabla2.getColumnModel().getColumn(2).setPreferredWidth(40);
			ventana.tabla2.getColumnModel().getColumn(2).setMaxWidth(50);
		} else {

			dtosCobros.recuperarInfo();
			haySeleccion = false;
		}
	}
	
	private void limpiarOtros() {
		
		for(int i = 0; i < ventana.tabla2.getRowCount(); i++) {
			
			if(i != elemento || !ventana.chckbxTabla2.isSelected())
				ventana.tabla2.setValueAt((boolean) false, i, 2);
		}
	}

	private void registraCobro() {
		
		boolean bandera = false;
		dtosCobros.setNombre(ventana.txtNombre.getText());
		dtosCobros.setEnviarEmail(ventana.chckbxEnviarEmail.isSelected());
		dtosCobros.setFactura(ventana.txtFactura.getText());

		if(ventana.chckbxEnviarEmail.isSelected())
			dtosCobros.setEmail(ventana.txtEmail.getText());
		else 
			dtosCobros.setEmail("");
		String error = dtosCobros.validarInformación(!haySeleccion, true);
		
		if(error.length() == 0) {

			ventana.lblMsgError.setForeground(Color.BLUE);
			ventana.lblMsgError.setText("Procesando la operación.");

			if(ventana.chckbxTabla2.isSelected()) {
				
				int i = 0;
			
				while(i < ventana.tabla2.getRowCount()) {
					
					if((boolean) ventana.tabla2.getValueAt(i, 2)) {
					
						dtosCobros.setFamiliaExistente(i);
						dtosCobros.setDescuentoGrupo(ventana.txt1.getText());
						break;
					}
					i++;
				}
				bandera = dtosCobros.guardarCobroGrupoExistente();
			} else {
				
				bandera = dtosCobros.guardarCobroGrupo();
			}
			
			if(bandera) {
				
				if(ventana.chckbxEnviarEmail.isSelected()) {
					
					EmailSenderService emailService = new EmailSenderService();
					if(!emailService.mandarCorreo(dtosCobros.getEmail(), "Comprobante de pago", dtosCobros.getCuerpoEmail())) {
						
						ventana.lblMsgError.setForeground(Color.RED);
						ventana.lblMsgError.setText("Error en el emvío del email.");
					}
				} else {
				
					ReciboCobro ventanaReciboPago = new ReciboCobro("Comprobante de pago");
					CtrlReciboCobrarInscripcion ctrolReciboInscripcion = new CtrlReciboCobrarInscripcion(ventanaReciboPago);
					ctrolReciboInscripcion.iniciar();
				}
				if(ventana.lblMsgError.getText().length() == 0) {
					
					ventana.lblMsgError.setForeground(Color.BLUE);
					ventana.lblMsgError.setText("Operación almacenada en la base de datos.");
				}
				ventana.btnCobrar.setEnabled(false);
				dtosCobros.deleteInfo();
			}else {
				
				ventana.lblMsgError.setForeground(Color.RED);
				ventana.lblMsgError.setText("Error al intentar guardar la información en la base de datos.");
			}
		} else {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText(error);
		}
	}
}