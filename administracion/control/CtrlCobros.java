package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

import modelo.DtosConfiguracion;
import vista.ABML;
import vista.InterfaceBotones;
import vista.Listado;

public class CtrlCobros implements ActionListener {
	
	private InterfaceBotones ventana;
	
	public CtrlCobros(InterfaceBotones vista) {
		
		this.ventana = vista;
		this.ventana.btn1A.addActionListener(this);
		this.ventana.btn1B.addActionListener(this);
		this.ventana.btn2A.addActionListener(this);
		this.ventana.btn2B.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.lbl1A.setText("Cobrar cuota");
		ventana.btn1A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Cobrar.png"));
		ventana.btn1A.setVisible(true);
		ventana.lbl1B.setText("Listado cobros");
		ventana.btn1B.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Listado cobros.png"));
		ventana.btn1B.setVisible(true);
		ventana.lbl2A.setText("Habilitar y cobrar");
		ventana.btn2A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Habilitar y cobrar.png"));
		ventana.btn2A.setVisible(true);
		ventana.lbl2B.setText("Grupo familiar");
		ventana.btn2B.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Descuento.png"));
		ventana.btn2B.setVisible(true);		
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btn1A) {
			
			Listado ventanaCobrarCuota = new Listado("Cobro de cuota");
			CtrlCobrarCuota ctrlCobrarCuota = new CtrlCobrarCuota(ventanaCobrarCuota);
			ctrlCobrarCuota.iniciar();
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			Listado ventanaListadoCobros = new Listado("Listado de cobros");
			CtrlListadoCobros ctrolListadoCobros = new CtrlListadoCobros(ventanaListadoCobros);
			ctrolListadoCobros.iniciar();
		}
		
		if(e.getSource() == ventana.btn2A) {
			
			Listado ventanaCobrarHabilitar = new Listado("Cobro y habilitación");
			CtrlCobrarHabilitar ctrolCobrarHabilitar = new CtrlCobrarHabilitar(ventanaCobrarHabilitar);
			ctrolCobrarHabilitar.iniciar();
		}
		
		if(e.getSource() == ventana.btn2B) {
			
			ABML ventanaGrupoFamiliar = new ABML("Gestión de grupos familiares");
			CtrlGrupoFamiliar ctrlGrupoFamiliar = new CtrlGrupoFamiliar(ventanaGrupoFamiliar);
			ctrlGrupoFamiliar.iniciar();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
}