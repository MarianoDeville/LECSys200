package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import modelo.DtosPagos;
import vista.ABML;
import vista.Listado;
import vista.Nuevo;

public class CtrlPagoServicios implements ActionListener {

	private ABML ventana;
	private DtosPagos dtosPagos;
	private int elemento = -1;
	private Listado ventanaListadoPagos;
	private Nuevo ventanaPagar;

	public CtrlPagoServicios(ABML vista) {
		
		this.ventana = vista;
		this.dtosPagos = new DtosPagos();
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnEditar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.txt1.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
				
					elemento = ventana.tabla.getSelectedRow();
				} else if(e.getClickCount() == 2) {
				
					elemento = ventana.tabla.getSelectedRow();
					historial();
				}
			}
		});
	}
	
	public void iniciar() {
		
		ventana.btnNuevo.setText("Ver");
		ventana.btnEditar.setText("Pagar");
		ventana.btnImprimir.setVisible(false);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.txt1 && ventana.isVisible()) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.btnNuevo) {
			
			historial();	
		}
		
		if(e.getSource() == ventana.btnEditar) {
			
			pagar();	
		}

		if(e.getSource() == ventana.btnVolver) {
			
			if(ventanaListadoPagos != null)
				ventanaListadoPagos.dispose();
			
			if(ventanaPagar != null)
				ventanaPagar.dispose();
			ventana.dispose();
		}
	}
	
	private void actualizar() {
		
		ventana.tabla.setModel(dtosPagos.getTablaProveedores(ventana.txt1.getText()));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(30);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
		ventana.tabla.setDefaultEditor(Object.class, null);
		ventana.tabla.clearSelection();
	}
	
	private void pagar() {
		
		if(checkSeleccion()) {
			
			if(ventanaPagar != null && ventanaPagar.isVisible())
				ventanaPagar.dispose();
			ventana.tabla.clearSelection();
			elemento = -1;
			ventanaPagar = new Nuevo("Registrar pago de servicio a " + dtosPagos.getNombreEmpresa(), ventana.getX(), ventana.getY());
			CtrlPagarServicio ctrlPagarServicio = new CtrlPagarServicio(ventanaPagar);
			ctrlPagarServicio.iniciar();
		}
	}
	
	private void historial() {
		
		if(checkSeleccion()) {
			
			if(ventanaListadoPagos != null && ventanaListadoPagos.isVisible())
				ventanaListadoPagos.dispose();
			ventana.tabla.clearSelection();
			elemento = -1;
			ventanaListadoPagos = new Listado("Pagos realizados al proveedor " + dtosPagos.getNombreEmpresa(), ventana.getX(), ventana.getY());
			CtrlListadoPagosServicios ctrlHistorico = new CtrlListadoPagosServicios(ventanaListadoPagos);
			ctrlHistorico.iniciar();
		}
	}
	
	private boolean checkSeleccion() {
		
		if(elemento == -1) {
			
			JOptionPane.showMessageDialog(null, "Debe elegir un elemento.");
			return false;
		}
		dtosPagos.setProveedor(elemento);
		return true;
	}
}