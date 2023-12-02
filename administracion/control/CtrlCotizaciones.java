package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import modelo.DtosInsumos;
import vista.Listado;

public class CtrlCotizaciones implements ActionListener {

	private Listado ventana;
	private DtosInsumos dtosInsumos;
	private int elemento = -1;
	
	public CtrlCotizaciones(Listado vista) {
		
		this.ventana = vista;
		this.dtosInsumos = new DtosInsumos();
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {

					elemento = ventana.tabla.getSelectedRow();
					ventana.btnImprimir.setEnabled(true);
		        }
		    }
		});
	}
	
	public void iniciar() {
	
		ventana.btnImprimir.setText("Aprobar");
		ventana.btnImprimir.setEnabled(false);
		actualizar();
		
		if(ventana.tabla.getRowCount() == 0) {
			
			JOptionPane.showMessageDialog(null, "No hay cotizaciones cargadas para este pedido.");
			ventana.dispose();
			return;
		}
		ventana.setVisible(true);
	}
	
	private void actualizar() {

		ventana.tabla.setModel(dtosInsumos.getTablaCotizaciones());
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(20);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(40);
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(30);
		int ultimo = ventana.tabla.getColumnCount() - 1;
		ventana.tabla.getColumnModel().getColumn(ultimo).setMinWidth(60);
		ventana.tabla.getColumnModel().getColumn(ultimo).setMaxWidth(120);
		ventana.tabla.getColumnModel().getColumn(ultimo).setPreferredWidth(80);	
		ventana.tabla.setDefaultEditor(Object.class, null);
	}
	
	private void elementoSeleccionado() {
		
		ventana.tabla.clearSelection();
		
		if(elemento == -1) {

			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento para editar.");
			return;
		}
		
		if(dtosInsumos.setAprobarCotización(elemento))
			ventana.btnImprimir.setEnabled(false);
		
		JOptionPane.showMessageDialog(null, dtosInsumos.getMsgError());	
		elemento = -1;
		actualizar();
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnImprimir) {
			
			elementoSeleccionado();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
}