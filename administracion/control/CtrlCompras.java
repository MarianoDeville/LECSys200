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

		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
		
		if(e.getSource() == ventana.btn1A) {
			
			ABML ventanaGestionCotizaciones = new ABML("Gestión cotizaciones");
			CtrlGestionCotizaciones ctrlGestiónCotizaciones = new CtrlGestionCotizaciones(ventanaGestionCotizaciones);
			ctrlGestiónCotizaciones.iniciar();
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			Listado ventanaOrdenesCompra = new Listado("Listado de ordenes de compra");
			CtrlOrdenesCompra ctrlOrdenesCompra = new CtrlOrdenesCompra(ventanaOrdenesCompra);
			ctrlOrdenesCompra.iniciar();
		}
		
		if(e.getSource() == ventana.btn2A) {
			
			Listado ventanaListadoCompras = new Listado("Listado de compras");
			CtrlListadoCompras ctrlListadoCompras = new CtrlListadoCompras(ventanaListadoCompras);
			ctrlListadoCompras.iniciar();			
		}
	}
}