package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.DtosPagos;
import vista.Cobro;

public class CtrlDetallePago implements ActionListener{
	
	private Cobro ventana;
	private DtosPagos dtosPagos;

	public CtrlDetallePago(Cobro vista) {
		
		this.ventana = vista;
		this.dtosPagos = new DtosPagos();
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
		ventana.lblNombre.setText("Nombre:");
		ventana.txtNombre.setColumns(20);
		ventana.txtNombre.setText(dtosPagos.getNombreEmpresa());
		ventana.lbl1.setVisible(true);
		ventana.lbl1.setText("Fecha:");
		ventana.txt1.setEditable(false);
		ventana.txt1.setVisible(true);
		ventana.txt1.setColumns(10);
		ventana.txt1.setText(dtosPagos.getFecha());
		ventana.lbl2.setVisible(true);
		ventana.lbl2.setText("Monto total:");
		ventana.txt2.setEditable(false);
		ventana.txt2.setColumns(10);
		ventana.txt2.setText(dtosPagos.getMontoTotal());
		ventana.lbl3.setVisible(true);
		ventana.lbl3.setText("Factura:");
		ventana.txt3.setEditable(false);
		ventana.txt3.setColumns(10);
		ventana.txt3.setText(dtosPagos.getFactura());
		ventana.lbl4.setVisible(true);
		ventana.lbl4.setText("Forma de pago:");
		ventana.txt4.setEditable(false);
		ventana.txt4.setColumns(40);
		ventana.txt4.setText(dtosPagos.getMetodoPago());
		ventana.lbl5.setVisible(true);
		ventana.lbl5.setText("Comentario:");
		ventana.txt5.setVisible(true);
		ventana.txt5.setEditable(false);
		ventana.txt5.setColumns(40);
		ventana.txt5.setText(dtosPagos.getComentario());
		ventana.tabla1.setModel(dtosPagos.getSeleccionados());
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
}