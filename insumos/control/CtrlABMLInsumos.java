package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.JOptionPane;
import modelo.DtosInsumos;
import vista.ABML;
import vista.Listado;
import vista.NuevoSimple;

public class CtrlABMLInsumos implements ActionListener{
	
	private ABML ventana;
	private DtosInsumos dtosInsumos;
	private NuevoSimple ventanaNuevoInsumo;
	private NuevoSimple ventanaEditarInsumo;
	private Listado ventanaHistorico;
	private int elemento;
	
	public CtrlABMLInsumos(ABML vista) {
		
		this.ventana = vista;
		this.dtosInsumos = new DtosInsumos();
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnEditar.addActionListener(this);
		this.ventana.btnHistorico.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.txt1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				actualizar();
			}
		});
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {

					elemento = ventana.tabla.getSelectedRow();
		        } else if(e.getClickCount() == 2) {
		        	
		        	editar();
		        }
		    }
		});
	}
	
	public void iniciar() {

		ventana.txt1.setVisible(true);
		ventana.btnHistorico.setVisible(true);
		actualizar();
		ventana.setVisible(true);
	}
	
	private void actualizar() {

		elemento = -1;
		ventana.tabla.setModel(dtosInsumos.getTablaInsumos(ventana.txt1.getText()));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(20);
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(30);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
		ventana.tabla.setDefaultEditor(Object.class, null);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnNuevo) {
			
			nuevo();
		}
		
		if(e.getSource() == ventana.btnHistorico) {
			
			historial();
		}

		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch (PrinterException f) {
				
				JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
				CtrlLogErrores.guardarError(f.getMessage());
			}
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			if(ventanaNuevoInsumo != null)
				ventanaNuevoInsumo.dispose();
			
			if(ventanaEditarInsumo != null) 
				ventanaEditarInsumo.dispose();
				
			if(ventanaHistorico != null)
				ventanaHistorico.dispose();
			ventana.dispose();
		}
		
		if(ventanaNuevoInsumo != null) {
			
			if(e.getSource() == ventanaNuevoInsumo.btnVolver) {
				
				actualizar();
			}
		}

		if(e.getSource() == ventana.btnEditar) {
			
			editar();
		}

		if(ventanaEditarInsumo != null) {
			
			if(e.getSource() == ventanaEditarInsumo.btnVolver) {
				
				actualizar();
			}
		}
	}
	
	private void nuevo() {
		
		if(ventanaNuevoInsumo != null && ventanaNuevoInsumo.isVisible()) {
			
			ventanaNuevoInsumo.setVisible(true);
			return;
		}
		ventanaNuevoInsumo = new NuevoSimple("Cargar nuevo insumo", ventana.getX(), ventana.getY());
		CtrlNuevoInsumo ctrlNuevoInsumo = new CtrlNuevoInsumo(ventanaNuevoInsumo);
		ctrlNuevoInsumo.iniciar();
		ventanaNuevoInsumo.btnVolver.addActionListener(this);
	}
	
	private void editar() {
		
		if(elemento == -1) {

			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento para editar.");
			ventana.tabla.clearSelection();
			return;
		}
		
		if(ventanaEditarInsumo != null && ventanaEditarInsumo.isVisible())
			ventanaEditarInsumo.dispose();
		dtosInsumos.setInsumoSeleccionado(elemento);
		ventanaEditarInsumo = new NuevoSimple("Editar insumo", ventana.getX(), ventana.getY());
		CtrlEditarInsumo ctrlEditarInsumo = new CtrlEditarInsumo(ventanaEditarInsumo);
		ctrlEditarInsumo.iniciar();
		ventanaEditarInsumo.btnVolver.addActionListener(this);
	}
	
	private void historial() {
		
		if(elemento == -1) {

			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento para editar.");
			ventana.tabla.clearSelection();
			return;
		}
		
		if(ventanaHistorico != null && ventanaHistorico.isVisible())
			ventanaHistorico.dispose();
		ventana.tabla.clearSelection();
		dtosInsumos.setInsumoSeleccionado(elemento);
		ventanaHistorico = new Listado("Movimientos históricos", ventana.getX(), ventana.getY());
		CtrlHistorico ctrlHistorico = new CtrlHistorico(ventanaHistorico);
		ctrlHistorico.iniciar();
	}
}