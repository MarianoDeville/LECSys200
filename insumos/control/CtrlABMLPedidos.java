package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

import modelo.DtosInsumos;
import vista.ABML;
import vista.ListadoDoble;

public class CtrlABMLPedidos implements ActionListener {

	private ABML ventana;
	private DtosInsumos dtosInsumos;
	private ListadoDoble ventanaNuevoPedido;
	private ListadoDoble ventanaEditarPedido;
	private int elemento;
	private boolean bandera;
	
	public CtrlABMLPedidos(ABML vista) {
		
		this.ventana = vista;
		this.dtosInsumos = new DtosInsumos();
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnEditar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.chckbx1.addActionListener(this);
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
					bandera = true;
		        } else if(e.getClickCount() == 2) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
		        	bandera = true;
		        	elementoSeleccionado();
		        }
		    }
		});
	}
	
	public void iniciar() {
		
		ventana.btnImprimir.setVisible(false);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnNuevo) {
			
			ventanaNuevoPedido = new ListadoDoble("Nuevo pedido de mercaderķa");
			CtrlSolicitarInsumos ctrlSolicitarInsumos = new CtrlSolicitarInsumos(ventanaNuevoPedido);
			ctrlSolicitarInsumos.iniciar();
			ventanaNuevoPedido.btnVolver.addActionListener(this);
		}
		
		if(ventanaNuevoPedido != null) {
			
			if(e.getSource() == ventanaNuevoPedido.btnVolver) {
				
				actualizar();
			}
		}

		if(e.getSource() == ventana.btnEditar) {
			
			elementoSeleccionado();
		}
		
		if(ventanaEditarPedido != null) {
			
			if(e.getSource() == ventanaEditarPedido.btnVolver) {
				
				actualizar();
			}
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void actualizar() {
		
		ventana.tabla.setModel(dtosInsumos.getTablaPedidos(ventana.txt1.getText(), 1));
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
	}
	
	private void elementoSeleccionado() {
		
		if(!bandera) {

			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento para editar.");
			return;
		}
		dtosInsumos.setElementoSeleccionado(elemento);
		ventanaEditarPedido = new ListadoDoble("Editar pedido de insumo");
		CtrlEditarPedido ctrlEditarPedido = new CtrlEditarPedido(ventanaEditarPedido);
		ctrlEditarPedido.iniciar();
		ventanaEditarPedido.btnVolver.addActionListener(this);
	}
}