package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import javax.swing.JOptionPane;
import modelo.DtosInsumos;
import vista.Listado;

public class CtrlHistorico  implements ActionListener {

	private Listado ventana;
	private DtosInsumos dtosInsumos;
	
	public CtrlHistorico(Listado vista) {
		
		this.ventana = vista;
		this.dtosInsumos = new DtosInsumos();
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {
		
		ventana.lblComboBox1.setVisible(true);
		ventana.lblComboBox1.setText("ID: " + dtosInsumos.getId());
		ventana.lblComboBox2.setVisible(true);
		ventana.lblComboBox2.setText("Producto: " + dtosInsumos.getNombre());
		ventana.lblTxt1.setVisible(true);
		ventana.lblTxt1.setText("Descripción: " + dtosInsumos.getDescripción());
		actualizar();
		ventana.setVisible(true);
	}
	
	private void actualizar( ) {
		
		ventana.tabla.setModel(dtosInsumos.getHistoricoInsumo());
		ventana.tabla.setDefaultEditor(Object.class, null);
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(50);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(75);
		ventana.tabla.getColumnModel().getColumn(3).setMinWidth(50);
		ventana.tabla.getColumnModel().getColumn(3).setMaxWidth(100);
		ventana.tabla.getColumnModel().getColumn(4).setMinWidth(50);
		ventana.tabla.getColumnModel().getColumn(4).setMaxWidth(70);
		ventana.tabla.getColumnModel().getColumn(5).setMinWidth(70);
		ventana.tabla.getColumnModel().getColumn(5).setMaxWidth(100);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch (PrinterException f) {
				
				JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
				CtrlLogErrores.guardarError(f.getMessage());
			}
		}

		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
}