package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.DtosCompras;
import vista.Cobro;

public class CtrlDetalleOrdenCompra implements ActionListener {

	private Cobro ventana;
	private DtosCompras dtosCompras;
	
	public CtrlDetalleOrdenCompra(Cobro vista) {
		
		this.ventana = vista;
		this.dtosCompras = new DtosCompras();
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.btnCobrar.setVisible(false);
		ventana.scrollTabla2.setVisible(false);
		ventana.chckbxEnviarEmail.setVisible(false);
		ventana.chckbxTabla2.setVisible(false);
		ventana.txtTabla2.setVisible(false);
		ventana.lblFactura.setVisible(false);
		ventana.txtFactura.setVisible(false);
		ventana.lblNombre.setText("Orden Compra nro:");
		ventana.txtNombre.setColumns(6);
		ventana.lbl1.setVisible(true);
		ventana.lbl1.setText("Fecha pedido:");
		ventana.txt1.setEditable(false);
		ventana.txt1.setVisible(true);
		ventana.txt1.setColumns(6);
		ventana.lbl2.setVisible(true);
		ventana.lbl2.setText("Fecha autorización:");
		ventana.txt2.setEditable(false);
		ventana.txt2.setColumns(6);
		ventana.lbl3.setVisible(true);
		ventana.lbl3.setText("Solicitante:");
		ventana.txt3.setEditable(false);
		ventana.txt3.setColumns(15);
		ventana.lbl4.setVisible(true);
		ventana.lbl4.setText("Autorizó:");
		ventana.txt4.setColumns(15);
		ventana.lblComboBox.setVisible(true);
		ventana.lblComboBox.setText("Proveedor:");
		ventana.txt6.setVisible(true);
		ventana.txt6.setEditable(false);
		ventana.txt6.setColumns(20);
		ventana.lblTabla.setVisible(true);
		ventana.lblTabla.setText("Monto total:");
		ventana.txtTabla2.setVisible(true);
		ventana.txtTabla2.setEditable(false);
		ventana.txtTabla2.setColumns(10);
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		derecha.setHorizontalAlignment(JLabel.RIGHT);
		ventana.tabla1.setModel(dtosCompras.getDetalleCompra());
		ventana.tabla1.getColumnModel().getColumn(0).setMinWidth(30);
		ventana.tabla1.getColumnModel().getColumn(0).setMaxWidth(50);
		ventana.tabla1.getColumnModel().getColumn(2).setMinWidth(30);
		ventana.tabla1.getColumnModel().getColumn(2).setMaxWidth(60);
		ventana.tabla1.getColumnModel().getColumn(2).setCellRenderer(derecha);
		ventana.tabla1.getColumnModel().getColumn(3).setMinWidth(30);
		ventana.tabla1.getColumnModel().getColumn(3).setMaxWidth(60);
		ventana.tabla1.getColumnModel().getColumn(3).setCellRenderer(derecha);
		ventana.tabla1.getColumnModel().getColumn(4).setMinWidth(70);
		ventana.tabla1.getColumnModel().getColumn(4).setMaxWidth(90);
		ventana.tabla1.getColumnModel().getColumn(4).setCellRenderer(derecha);		
		ventana.tabla1.setDefaultEditor(Object.class, null);
		ventana.txtNombre.setText(dtosCompras.getIdOrdenCompra());
		ventana.txt1.setText(dtosCompras.getFechaPedido());
		ventana.txt2.setText(dtosCompras.getFechaAutorizacion());
		ventana.txt3.setText(dtosCompras.getSolicitante());
		ventana.txt4.setText(dtosCompras.getAutorizo());
		ventana.txt6.setText(dtosCompras.getProveedor());
		ventana.txtTabla2.setText(dtosCompras.getMontoTotal());
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
}