package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import modelo.DtosInsumos;
import vista.ListadoDoble2;

public class CtrlEnviarPedido implements ActionListener {
	
	private ListadoDoble2 ventana;
	private DtosInsumos dtosInsumos;
	private int elemento = -1;
	
	public CtrlEnviarPedido(ListadoDoble2 vista) {
		
		this.ventana = vista;
		this.dtosInsumos = new DtosInsumos();
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.txtSuperior.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				ventana.lblMsgError.setText("");
			}
		});
		this.ventana.txtArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				ventana.lblMsgError.setText("");
			}
		});
		this.ventana.txtDestinatario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				actualizar();
				ventana.lblMsgError.setText("");
			}
		});
		this.ventana.tablaDestinatarios.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {

					elemento = ventana.tablaDestinatarios.getSelectedRow();
					elementoSeleccionado();
		        }
		    }
		});
	}
	
	public void iniciar() {
		
		ventana.scrollTabla1.setVisible(false);
		ventana.btnImprimir.setVisible(false);
		ventana.btnCompletar.setVisible(false);
		ventana.cmbBoxGranularidad.setVisible(false);
		ventana.cmbBoxSector.setVisible(false);
		ventana.txtSuperior.setVisible(false);
		ventana.txtMedio1.setVisible(false);
		ventana.txtMedio2.setVisible(false);
		ventana.txtMedio3.setVisible(false);
		ventana.lblTxtSuperior.setText("Asunto:");
		ventana.txtSuperior.setVisible(true);
		ventana.txtSuperior.setColumns(20);
		ventana.lblDestinatario.setVisible(true);
		ventana.lblDestinatario.setText("Destinatario:");
		ventana.txtDestinatario.setVisible(true);
		ventana.scrollDestinatario.setVisible(true);
		ventana.scrollTxtArea.setVisible(true);
		ventana.lblTxtArea.setVisible(true);
		ventana.lblTxtArea.setText("Mensage:");
		ventana.btnGuardar.setText("Enviar");
		dtosInsumos.getInfoPedido();
		ventana.tabla2.setModel(dtosInsumos.getTablaSeleccionados());
		ventana.tabla2.getColumnModel().getColumn(0).setMaxWidth(40);
		ventana.tabla2.setEnabled(false);
		ventana.lblTxtSuperior.setText("Asunto:");
		actualizar();
		ventana.setVisible(true);
	}
	
	private void actualizar() {
		
		ventana.tablaDestinatarios.setModel(dtosInsumos.getListadoProveedores(ventana.txtDestinatario.getText()));
		ventana.tablaDestinatarios.getColumnModel().getColumn(2).setPreferredWidth(50);
		ventana.tablaDestinatarios.setDefaultEditor(Object.class, null);
	}
	
	private void elementoSeleccionado() {
		
		ventana.lblMsgError.setText("");
		dtosInsumos.setIdProveedor(elemento);
		ventana.txtDestinatario.setText(dtosInsumos.getEmailSeleccionado(elemento));
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnVolver) {
		
			ventana.dispose();
		}
	
		if(e.getSource() == ventana.btnGuardar) {
			
			enviarEmail();
		}
	}

	private void enviarEmail() {
		
		dtosInsumos.setEmail(ventana.txtDestinatario.getText());
		dtosInsumos.setAsunto(ventana.txtSuperior.getText());
		dtosInsumos.setMensaje(ventana.txtArea.getText());
		ventana.lblMsgError.setForeground(Color.RED);
		
		if(dtosInsumos.setGuardarSolicitudCotizacion()) {
			
			if(dtosInsumos.getMsgError().length() == 0) {
				
				ventana.lblMsgError.setText("");
				if(JOptionPane.showConfirmDialog(null, 
												" Desea volver a enviarlo?", 
												"El pedido ha sido enviado al proveedor con anterioridad.", 
												JOptionPane.YES_NO_OPTION) == 0)
					return;
			}
			
			if(dtosInsumos.enviarEmail())
				ventana.lblMsgError.setForeground(Color.BLUE);
		}
		ventana.lblMsgError.setText(dtosInsumos.getMsgError());
	}
}