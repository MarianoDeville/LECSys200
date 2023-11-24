package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.DtosInsumos;
import vista.NuevoSimple;

public class CtrlNuevoInsumo implements ActionListener {
	
	private NuevoSimple ventana;
	private DtosInsumos dtosInsumos;
	
	public CtrlNuevoInsumo(NuevoSimple vista) {
		
		this.ventana = vista;
		this.dtosInsumos = new DtosInsumos();
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btnBorrar.addActionListener(this);
	}

	public void iniciar() {

		ventana.btnBorrar.setText("Nuevo");
		ventana.btnBorrar.setVisible(true);
		ventana.btnBorrar.setEnabled(false);
		ventana.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardar();
		}		
		
		if(e.getSource() == ventana.btnBorrar) {
			
			limpiar();
		}
		
		if(e.getSource() == ventana.btnVolver) {

			ventana.dispose();
		}
	}
	
	private void guardar() {
		
		dtosInsumos.setNombre(ventana.txtNombre.getText());
		dtosInsumos.setDescripción(ventana.txtDescripción.getText());
		dtosInsumos.setPresentación(ventana.txtFormato.getText());
		
		if(!dtosInsumos.isCheckInfo("ABML Insumo")) {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText(dtosInsumos.getMsgError());
			return;
		}
		
		if(!dtosInsumos.setGuardarNuevo()) {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText("Error al intentar guardar la información en la base de datos.");
			return;
		}
		ventana.lblMsgError.setForeground(Color.BLUE);
		ventana.lblMsgError.setText("El producto se guardó corractamente.");
		ventana.btnGuardar.setEnabled(false);
		ventana.btnBorrar.setEnabled(true);
	}
	
	private void limpiar() {
		
		ventana.txtNombre.setText("");
		ventana.txtDescripción.setText("");
		ventana.txtFormato.setText("");
		ventana.lblMsgError.setText("");
		ventana.btnGuardar.setEnabled(true);
		ventana.btnBorrar.setEnabled(false);
	}
}