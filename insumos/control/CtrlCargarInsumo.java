package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import modelo.DtosInsumos;
import vista.NuevoSimple;

public class CtrlCargarInsumo implements ActionListener {

	private NuevoSimple ventana;
	private DtosInsumos dtosInsumos;
	
	public CtrlCargarInsumo(NuevoSimple vista) {
		
		this.ventana = vista;
		this.dtosInsumos = new DtosInsumos();
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.txtFormato.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				ventana.lblMsgError.setText("");
			}
		});
	}
	
	public void iniciar() {
		
		ventana.setTamaño(300, 300);
		ventana.lblDescripción.setText("Stock:");
		ventana.lblFormato.setText("Agregar:");
		ventana.txtNombre.setEditable(false);
		ventana.txtDescripción.setEditable(false);
		ventana.txtID.setText(dtosInsumos.getId());
		ventana.txtNombre.setText(dtosInsumos.getNombre());
		ventana.txtDescripción.setText(dtosInsumos.getStock());
		ventana.setVisible(true);
	}

	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnGuardar) {
			
			guardarCambios();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	void guardarCambios( ) {
		
		if(!dtosInsumos.setStockAgregar(ventana.txtFormato.getText())) {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText(dtosInsumos.getMsgError());
			return;
		}

		if(!dtosInsumos.actualizarStockInsumo()) {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText("Error al intentar actualizar la base de datos.");
			return;
		}
		ventana.lblMsgError.setForeground(Color.BLUE);
		ventana.lblMsgError.setText("Se ha actualizado la base de datos.");
		ventana.btnGuardar.setEnabled(false);
	}
}