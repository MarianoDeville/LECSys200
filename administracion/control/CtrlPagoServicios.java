package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import modelo.DtosPagos;
import vista.ABML;
import vista.Listado;

public class CtrlPagoServicios implements ActionListener {

	private ABML ventana;
	private DtosPagos dtosPagos;
	private int elemento = -1;
	private Listado ventanaListadoPagos;
	private Listado ventanaPagar;

	public CtrlPagoServicios(ABML vista) {
		
		this.ventana = vista;
		this.dtosPagos = new DtosPagos();
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnEditar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
				
					elemento = ventana.tabla.getSelectedRow();
				} else if(e.getClickCount() == 2) {
				
					elemento = ventana.tabla.getSelectedRow();
					histial();
				}
			}
		});
	}
	
	public void iniciar() {
		
		ventana.btnNuevo.setText("Ver");
		ventana.btnEditar.setText("Pagar");
		ventana.btnImprimir.setVisible(false);
		
		
int algo;		
		
		actualizar();
		
		
		
		
		
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnNuevo) {
			
			histial();	
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
		
		
	}
	
	private void pagar() {
		
		if(checkSeleccion()) {
			
			if(ventanaPagar != null && ventanaPagar.isVisible())
				ventanaPagar.dispose();
			
			
			
			
			
			
			
		}
	}
	
	private void histial() {
		
		if(checkSeleccion()) {
			
			if(ventanaListadoPagos != null && ventanaListadoPagos.isVisible())
				ventanaListadoPagos.dispose();
			
			
			
			
			
			
			
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
