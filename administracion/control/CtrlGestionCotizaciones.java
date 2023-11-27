package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

import modelo.DtosInsumos;
import vista.ABML;
import vista.Listado;

public class CtrlGestionCotizaciones implements ActionListener {

	private ABML ventana;
	private Listado ventanaCotizacioes;
	private DtosInsumos dtosInsumos;
	private int elemento = -1;
	
	public CtrlGestionCotizaciones(ABML vista) {
		
		this.ventana = vista;
		this.dtosInsumos = new DtosInsumos();
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {

					elemento = ventana.tabla.getSelectedRow();
		        } else if(e.getClickCount() == 2) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
		        	elementoSeleccionado();
		        }
		    }
		});
	}
	
	public void iniciar() {

		ventana.btnImprimir.setVisible(false);
		ventana.btnEditar.setVisible(false);
		ventana.btnNuevo.setText("Revisar");
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnNuevo) {
			
			elementoSeleccionado();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
		
		if(ventanaCotizacioes != null) {
			
			if(e.getSource() == ventanaCotizacioes.btnVolver) {
				
				actualizar();
			}
		}
	}
	
	private void actualizar() {
		
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
		ventana.tabla.clearSelection();
		ventana.tabla.setDefaultEditor(Object.class, null);
	}
	
	private void elementoSeleccionado() {
		
		ventana.tabla.clearSelection();
		if(elemento == -1) {

			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento para editar.");
			return;
		}
		dtosInsumos.setInsumoSeleccionado(elemento);
		elemento = -1;
		cargarCotizaciones();
	}
	
	private void cargarCotizaciones() {
		
		ventanaCotizacioes = new Listado("Revisión de cotizaciones", ventana.getX(), ventana.getY());
		CtrlCotizaciones ctrlCotizaciones = new CtrlCotizaciones(ventanaCotizacioes);
		ctrlCotizaciones.iniciar();
		ventanaCotizacioes.btnVolver.addActionListener(this);
	}
}