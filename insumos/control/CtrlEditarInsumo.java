package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import modelo.DtosInsumos;
import vista.NuevoSimple;

public class CtrlEditarInsumo implements ActionListener {
	
	private NuevoSimple ventana;
	private DtosInsumos dtosInsumos;
	
	public CtrlEditarInsumo(NuevoSimple vista) {
		
		this.ventana = vista;
		this.dtosInsumos = new DtosInsumos();
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnBorrar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}

	public void iniciar() {

		ventana.btnBorrar.setVisible(true);
		ventana.txtID.setText(dtosInsumos.getId());
		ventana.txtNombre.setText(dtosInsumos.getNombre());
		ventana.txtDescripción.setText(dtosInsumos.getDescripción());
		ventana.txtFormato.setText(dtosInsumos.getPresentación());
		ventana.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardar();
		}		
		
		if(e.getSource() == ventana.btnBorrar) {
			
			borrar();
		}
		
		if(e.getSource() == ventana.btnVolver) {

			ventana.dispose();
		}
	}
	
	private void guardar() {
		
		dtosInsumos.setNombre(ventana.txtNombre.getText());
		dtosInsumos.setDescripción(ventana.txtDescripción.getText());
		dtosInsumos.setPresentación(ventana.txtFormato.getText());
		dtosInsumos.setEstado("1");
		
		if(!dtosInsumos.isCheckInfo("ABML Insumo")) {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText(dtosInsumos.getMsgError());
			return;
		}
		
		if(!dtosInsumos.setActualizarInfo()) {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText("Error al intentar guardar la información en la base de datos.");
			return;
		}
		ventana.lblMsgError.setForeground(Color.BLUE);
		ventana.lblMsgError.setText("El producto se guardó corractamente.");
		ventana.btnGuardar.setEnabled(false);
	}
	
	private void borrar() {
		
		
		if(JOptionPane.showConfirmDialog(null, "¿Esta seguro de borrar el insumo?", "Alerta!", JOptionPane.YES_NO_OPTION) == 0) {
			
			dtosInsumos.setEstado("0");
			
			if(!dtosInsumos.setActualizarInfo()) {
				
				ventana.lblMsgError.setForeground(Color.RED);
				ventana.lblMsgError.setText("Error al intentar guardar la información en la base de datos.");
				return;
			}
			ventana.lblMsgError.setForeground(Color.BLUE);
			ventana.lblMsgError.setText("El producto eliminó corractamente.");
			ventana.btnGuardar.setEnabled(false);
		}
	}
}