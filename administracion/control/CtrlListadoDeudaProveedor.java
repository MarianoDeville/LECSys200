package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;

import modelo.DtosPagos;
import vista.Cobro;
import vista.Listado;

public class CtrlListadoDeudaProveedor implements ActionListener{
	
	private Listado ventana;
	private DtosPagos dtosPagos;
	private Cobro ventanaPagarProveedor;
	
	public CtrlListadoDeudaProveedor(Listado vista) {
		
		this.ventana = vista;
		this.dtosPagos = new DtosPagos();
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
				
					seleccionar();
				}
			}
		});
	}
	
	public void iniciar() {

		ventana.btnImprimir.setText("Pagar");
		ventana.lblTxt1.setVisible(true);
		ventana.lblTxt1.setText("Empresa:");
		ventana.txt1.setVisible(true);
		ventana.txt1.setText(dtosPagos.getNombre());
		ventana.txt1.setColumns(15);
		ventana.lblTxt2.setVisible(true);
		ventana.lblTxt2.setText("CUIT:");
		ventana.txt2.setVisible(true);
		ventana.txt2.setText(dtosPagos.getDNI());
		ventana.txt2.setColumns(10);
		ventana.lblChckbx2.setVisible(true);
		ventana.lblChckbx2.setText("Monto:");
		ventana.txt3.setVisible(true);
		ventana.txt3.setEditable(false);
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		derecha.setHorizontalAlignment(JLabel.RIGHT);
		ventana.tabla.setModel(dtosPagos.getDeudaProveedor());
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(30);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
		ventana.tabla.getColumnModel().getColumn(3).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(4).setMinWidth(30);
		ventana.tabla.getColumnModel().getColumn(4).setMaxWidth(50);
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnImprimir) {
			
			pagar();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
		
		if(ventanaPagarProveedor != null) {
			
			if(e.getSource() == ventanaPagarProveedor.btnVolver) {
				
				ventana.dispose();
			}
		}
	}

	private void seleccionar() {
		
		ventana.txt3.setText(dtosPagos.getSumaFacturas(ventana.tabla));
	}
	
	private void pagar() {
		
		if(!dtosPagos.setListaOC(ventana.tabla)) {
			
			JOptionPane.showMessageDialog(null, dtosPagos.getMsgError());
			return;
		}
		ventanaPagarProveedor = new Cobro("Pagar proveedor.");
		CtrlPagarProveedor ctrlPagarProveedor = new CtrlPagarProveedor(ventanaPagarProveedor);
		ctrlPagarProveedor.iniciar();
		ventanaPagarProveedor.btnVolver.addActionListener(this);
	}
}