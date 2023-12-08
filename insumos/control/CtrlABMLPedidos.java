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
		        } else if(e.getClickCount() == 2) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
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
			
			nuevoPedido();
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
			
			if(ventanaNuevoPedido != null)
				ventanaNuevoPedido.dispose();
			
			if(ventanaEditarPedido != null)
				ventanaEditarPedido.dispose();
			ventana.dispose();
		}
	}
	
	private void actualizar() {
		
		elemento = -1;
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
		ventana.tabla.clearSelection();
	}
	
	private void nuevoPedido() {
		
		if(ventanaNuevoPedido != null && ventanaNuevoPedido.isVisible()) {
			
			ventanaNuevoPedido.setVisible(true);
			return;
		}
		ventanaNuevoPedido = new ListadoDoble("Nuevo pedido de mercadería", ventana.getX(), ventana.getY());
		CtrlSolicitarInsumos ctrlSolicitarInsumos = new CtrlSolicitarInsumos(ventanaNuevoPedido);
		ctrlSolicitarInsumos.iniciar();
		ventanaNuevoPedido.btnVolver.addActionListener(this);
	}
	
	private void elementoSeleccionado() {
		
		if(elemento == -1) {

			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento para editar.");
			return;
		}
		
		if(ventanaEditarPedido != null && ventanaEditarPedido.isVisible())
			ventanaEditarPedido.dispose();
		dtosInsumos.setPedidoSeleccionado(elemento);
		elemento = -1;
		ventanaEditarPedido = new ListadoDoble("Editar pedido de insumo", ventana.getX(), ventana.getY());
		CtrlEditarPedido ctrlEditarPedido = new CtrlEditarPedido(ventanaEditarPedido);
		ctrlEditarPedido.iniciar();
		ventanaEditarPedido.btnVolver.addActionListener(this);
	}
}