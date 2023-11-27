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

public class CtrlCargarPresupuestos implements ActionListener {

	private ListadoDoble2 ventana;
	private DtosInsumos dtosInsumos;
	private int elemento = -1;
	
	public CtrlCargarPresupuestos(ListadoDoble2 vista) {
		
		this.ventana = vista;
		this.dtosInsumos = new DtosInsumos();
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btnCompletar.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.txtMedio1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				ventana.lblMsgError.setText("");
			}
		});
		this.ventana.tabla1.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {

					elemento = ventana.tabla1.getSelectedRow();
					elementoSeleccionado();
		        }
			}
		});
	}
	
	public void iniciar() {
		
		ventana.txtSuperior.setVisible(false);
		ventana.tabla2.setEnabled(false);
		ventana.cmbBoxSector.setVisible(false);
		ventana.cmbBoxGranularidad.setVisible(false);
		ventana.btnImprimir.setVisible(false);
		ventana.txtMedio2.setVisible(false);
		ventana.txtMedio3.setVisible(false);
		ventana.btnGuardar.setEnabled(false);
		ventana.txtMedio1.setColumns(6);
		ventana.lblTxtMedio1.setText("Vigencia:");
		ventana.lblTxtMedio2.setText("dd/mm/aaaa");
		ventana.btnCompletar.setText("Cargar");
		ventana.tabla1.setModel(dtosInsumos.getProveedoresCotización());
		ventana.tabla1.setDefaultEditor(Object.class, null);
		
		if(ventana.tabla1.getRowCount() < 1) {
			
			JOptionPane.showMessageDialog(null, "No se han enviado pedido de cotizaciones de este pedido de compra a los proveedores.");
			ventana.dispose();
			return;
		}
		ventana.tabla1.getColumnModel().getColumn(0).setMaxWidth(30);
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnCompletar) {
			
			habilitarLlenado();
		}
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardar();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void habilitarLlenado() {
		
		if(elemento == -1) {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText("Debe seleccionar el proveedor al cual se le cargará el presupuesto.");
			return;
		}
		
		if(!dtosInsumos.isFechaOK(ventana.txtMedio1.getText())) {

			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText(dtosInsumos.getMsgError());
			return;
		}
		ventana.lblMsgError.setForeground(Color.BLUE);
		ventana.lblMsgError.setText("Puede cargar los precios.");
		ventana.tabla2.setEnabled(true);
		ventana.btnGuardar.setEnabled(true);
	}

	private void elementoSeleccionado() {

		ventana.tabla2.setEnabled(false);
		ventana.tabla2.setModel(dtosInsumos.getTablaInsumos(elemento));
		ventana.tabla2.getColumnModel().getColumn(0).setMinWidth(20);
		ventana.tabla2.getColumnModel().getColumn(0).setMaxWidth(30);
		ventana.tabla2.getColumnModel().getColumn(1).setMinWidth(30);
		ventana.tabla2.getColumnModel().getColumn(1).setMaxWidth(40);
		ventana.tabla2.getColumnModel().getColumn(2).setMinWidth(80);
		ventana.tabla2.getColumnModel().getColumn(2).setPreferredWidth(200);
		ventana.tabla2.getColumnModel().getColumn(2).setMaxWidth(300);
		ventana.tabla2.getColumnModel().getColumn(4).setMinWidth(40);
		ventana.tabla2.getColumnModel().getColumn(4).setPreferredWidth(70);
		ventana.tabla2.getColumnModel().getColumn(4).setMaxWidth(90);
		ventana.txtMedio1.setText("");
		ventana.btnCompletar.setEnabled(true);
		ventana.lblMsgError.setText("");
	}
	
	private void guardar() {
		
		boolean bandera = dtosInsumos.setGuardarCotización(ventana.tabla2);
		ventana.lblMsgError.setText(dtosInsumos.getMsgError());
		
		if(!bandera) {
		
			ventana.lblMsgError.setForeground(Color.RED);
			return;
		}
		ventana.lblMsgError.setForeground(Color.BLUE);
		ventana.btnGuardar.setEnabled(false);
		ventana.btnCompletar.setEnabled(false);
		ventana.tabla2.setEnabled(false);
	}
}