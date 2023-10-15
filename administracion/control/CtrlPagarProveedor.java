package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import modelo.DtosPagos;
import vista.Cobro;

public class CtrlPagarProveedor implements ActionListener {

	private Cobro ventana;
	private DtosPagos dtosPagos;
	
	public CtrlPagarProveedor(Cobro vista) {
		
		this.ventana = vista;
		this.dtosPagos = new DtosPagos();
		this.ventana.btnCobrar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.btnCobrar.setVisible(true);
		ventana.btnCobrar.setText("Pagar");
		ventana.scrollTabla2.setVisible(false);
		ventana.chckbxEnviarEmail.setVisible(false);
		ventana.chckbxTabla2.setVisible(false);
		ventana.txtTabla2.setVisible(false);
		ventana.lblFactura.setVisible(false);
		ventana.txtFactura.setVisible(false);
		ventana.lblNombre.setText("Nombre:");
		ventana.txtNombre.setColumns(20);
		ventana.lbl1.setVisible(true);
		ventana.lbl1.setText("Monto total:");
		ventana.txt1.setEditable(false);
		ventana.txt1.setVisible(true);
		ventana.txt1.setColumns(10);
		ventana.lbl2.setVisible(true);
		ventana.lbl2.setText("Factura:");
		ventana.txt2.setEditable(true);
		ventana.txt2.setColumns(10);
		ventana.lbl3.setVisible(true);
		ventana.lbl3.setText("Forma de pago:");
		ventana.txt3.setEditable(true);
		ventana.txt3.setColumns(20);
		ventana.lbl4.setVisible(true);
		ventana.lbl4.setText("Comentario:");
		ventana.txt4.setEditable(true);
		ventana.txt4.setColumns(40);
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		derecha.setHorizontalAlignment(JLabel.RIGHT);
		ventana.tabla1.setModel(dtosPagos.getSeleccionados());
		ventana.tabla1.getColumnModel().getColumn(0).setMinWidth(30);
		ventana.tabla1.getColumnModel().getColumn(0).setMaxWidth(60);
		ventana.tabla1.getColumnModel().getColumn(1).setMinWidth(60);
		ventana.tabla1.getColumnModel().getColumn(1).setMaxWidth(100);
		ventana.tabla1.getColumnModel().getColumn(2).setMinWidth(60);
		ventana.tabla1.getColumnModel().getColumn(2).setMaxWidth(450);
		ventana.tabla1.getColumnModel().getColumn(3).setMinWidth(60);
		ventana.tabla1.getColumnModel().getColumn(3).setMaxWidth(150);
		ventana.tabla1.getColumnModel().getColumn(3).setCellRenderer(derecha);
		ventana.tabla1.setDefaultEditor(Object.class, null);
		ventana.txtNombre.setText(dtosPagos.getNombre());
		ventana.txt1.setText(dtosPagos.getSuma());
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnCobrar) {
			
			procesarPago();
		}
		
		if(e.getSource() == ventana.btnVolver) {

			ventana.dispose();
		}
	}
	
	private void procesarPago() {
		
		dtosPagos.setFactura(ventana.txt2.getText());
		dtosPagos.setMetodoPago(ventana.txt3.getText());
		dtosPagos.setComentario(ventana.txt4.getText());
		
		if(!dtosPagos.registrarPago()) {
			
			ventana.lblMsgError.setForeground(Color.RED);
		} else {
			
			ventana.lblMsgError.setForeground(Color.BLUE);
			ventana.btnCobrar.setEnabled(false);
		}
		ventana.lblMsgError.setText(dtosPagos.getMsgError());
	}
}