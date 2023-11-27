package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import modelo.DtosConfiguracion;
import vista.ABML;
import vista.InterfaceBotones;
import vista.Listado;

public class CtrlCompras implements ActionListener {

	private InterfaceBotones ventana;
	private ABML ventanaGestionCotizaciones;
	private Listado ventanaOrdenesCompra;
	private Listado ventanaListadoCompras;
	
	public CtrlCompras(InterfaceBotones vista) {
		
		this.ventana = vista;
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btn1A.addActionListener(this);
		this.ventana.btn1B.addActionListener(this);
		this.ventana.btn2A.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.lbl1A.setText("Cotizaciones");
		ventana.btn1A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Presupuesto.png"));
		ventana.btn1A.setVisible(true);		
		ventana.lbl1B.setText("Ordenes de compra");
		ventana.btn1B.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Compras.png"));
		ventana.btn1B.setVisible(true);
		ventana.lbl2A.setText("Listado compras");
		ventana.btn2A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Listado.png"));
		ventana.btn2A.setVisible(true);
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btn1A) {
			
			cotizaciones();
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			ordenes();
		}
		
		if(e.getSource() == ventana.btn2A) {
			
			listado();		
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void cotizaciones() {
		
		if(ventanaGestionCotizaciones != null && ventanaGestionCotizaciones.isVisible()) {
			
			ventanaGestionCotizaciones.setVisible(true);
			return;
		}
		ventanaGestionCotizaciones = new ABML("Gestión cotizaciones", ventana.getX(), ventana.getY());
		CtrlGestionCotizaciones ctrlGestiónCotizaciones = new CtrlGestionCotizaciones(ventanaGestionCotizaciones);
		ctrlGestiónCotizaciones.iniciar();
	}
	
	private void ordenes() {
		
		if(ventanaOrdenesCompra != null && ventanaOrdenesCompra.isVisible()) {
			
			ventanaOrdenesCompra.setVisible(true);
			return;
		}
		ventanaOrdenesCompra = new Listado("Listado de ordenes de compra", ventana.getX(), ventana.getY());
		CtrlOrdenesCompra ctrlOrdenesCompra = new CtrlOrdenesCompra(ventanaOrdenesCompra);
		ctrlOrdenesCompra.iniciar();
	}
	
	private void listado() {
		
		if(ventanaListadoCompras != null && ventanaListadoCompras.isVisible()) {
			
			ventanaListadoCompras.setVisible(true);
			return;
		}
		ventanaListadoCompras = new Listado("Listado de compras", ventana.getX(), ventana.getY());
		CtrlListadoCompras ctrlListadoCompras = new CtrlListadoCompras(ventanaListadoCompras);
		ctrlListadoCompras.iniciar();			
	}
}