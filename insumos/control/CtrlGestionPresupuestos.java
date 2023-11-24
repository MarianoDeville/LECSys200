package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

import modelo.DtosInsumos;
import vista.ABML;
import vista.ListadoDoble2;

public class CtrlGestionPresupuestos implements ActionListener {

	private ABML ventana;
	private DtosInsumos dtosInsumos;
	private int elemento = -1;
	
	public CtrlGestionPresupuestos(ABML vista) {
		
		this.ventana = vista;
		this.dtosInsumos = new DtosInsumos();
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btnEditar.addActionListener(this);
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {

					elemento = ventana.tabla.getSelectedRow();
		        } else if(e.getClickCount() == 2) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
		        	elementoSeleccionado("Enviar");
		        }
		    }
		});
	}
	
	public void iniciar() {
		
		ventana.btnImprimir.setVisible(false);
		ventana.btnEditar.setText("Cargar");
		ventana.btnNuevo.setText("Enviar");
		ventana.tabla.setModel(dtosInsumos.getTablaPedidos("", 1));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(20);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(35);
		ventana.tabla.getColumnModel().getColumn(1).setMinWidth(70);
		ventana.tabla.getColumnModel().getColumn(1).setMaxWidth(80);
		ventana.tabla.getColumnModel().getColumn(2).setMinWidth(50);
		ventana.tabla.getColumnModel().getColumn(2).setPreferredWidth(70);
		ventana.tabla.getColumnModel().getColumn(2).setMaxWidth(120);
		ventana.tabla.getColumnModel().getColumn(3).setMinWidth(90);
		ventana.tabla.getColumnModel().getColumn(3).setPreferredWidth(150);
		ventana.tabla.getColumnModel().getColumn(3).setMaxWidth(220);
		ventana.tabla.setDefaultEditor(Object.class, null);
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnEditar) {
			
			elementoSeleccionado("Cargar");
		}
		
		if(e.getSource() == ventana.btnNuevo) {
			
			elementoSeleccionado("Enviar");
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void elementoSeleccionado(String acci�n) {
		
		ventana.tabla.clearSelection();
		if(elemento == -1) {

			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento para editar.");
			return;
		}
		dtosInsumos.setElementoSeleccionado(elemento);
		elemento = -1;
		
		if(acci�n.equals("Enviar")) {
			
			enviarPedidoPresupuesto();
			return;
		}
		cargarPresupuestoProveedor();
	}
	
	private void enviarPedidoPresupuesto() {
		
		ListadoDoble2 ventanaEnviarPedidoCotizaci�n = new ListadoDoble2("Solicitud de cotizaci�n");
		CtrlEnviarPedido ctrlEnviarPedidoCotizaci�n = new CtrlEnviarPedido(ventanaEnviarPedidoCotizaci�n);
		ctrlEnviarPedidoCotizaci�n.iniciar();
	}
	
	private void cargarPresupuestoProveedor() {
		
		ListadoDoble2 ventanaCargarPresupuestos = new ListadoDoble2("Carga de presupuestos");
		CtrlCargarPresupuestos ctrlCargarPresupuesto = new CtrlCargarPresupuestos(ventanaCargarPresupuestos);
		ctrlCargarPresupuesto.iniciar();
	}
}