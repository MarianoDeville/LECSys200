package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.DtosCumpleaños;
import vista.Cumpleaños;

public class CtrlCumpleaños implements ActionListener {

	private Cumpleaños ventanaCumpleaños;
	private DtosCumpleaños dtosCumplaños;
	
	public CtrlCumpleaños(Cumpleaños vista) {
		
		this.ventanaCumpleaños = vista;
		this.dtosCumplaños = new DtosCumpleaños();
		this.ventanaCumpleaños.btnCerrar.addActionListener(this);
	}
	
	public void iniciar() {
		
		ventanaCumpleaños.tablaCumpleaños.setModel(dtosCumplaños.getTablaCumpleaños());

		if(!dtosCumplaños.isBandera()) {
			
			ventanaCumpleaños.dispose();
			return;
		}
		ventanaCumpleaños.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventanaCumpleaños.btnCerrar) {
			
			ventanaCumpleaños.dispose();
		}
	}
}
