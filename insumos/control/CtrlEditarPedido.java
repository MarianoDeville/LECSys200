package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

import modelo.DtosInsumos;
import vista.ListadoDoble;

public class CtrlEditarPedido implements ActionListener {

	private ListadoDoble ventana;
	private DtosInsumos dtosInsumos;
	private int elemento1;
	private int elemento2;
	private boolean bandera1;
	private boolean bandera2;
	
	public CtrlEditarPedido(ListadoDoble vista) {
		
		this.ventana = vista;
		this.dtosInsumos = new DtosInsumos();
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnAgregar.addActionListener(this);
		this.ventana.btnQuitar.addActionListener(this);
		this.ventana.btnEliminar.addActionListener(this);
		this.ventana.txtTituloTabla1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				actualizarTabla1();
			}
		});
		this.ventana.tabla1.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {

					elemento1 = ventana.tabla1.getSelectedRow();
					bandera1 = true;
					bandera2 = false;
					ventana.lblMsgError.setForeground(Color.BLACK);
					ventana.lblMsgError.setText(dtosInsumos.getInfoInsumo(elemento1));
					ventana.tabla2.clearSelection();
		        }
		    }
		});
		this.ventana.tabla2.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {

					elemento2 = ventana.tabla2.getSelectedRow();
					bandera2 = true;
					bandera1 = false;
					ventana.lblMsgError.setText("");
					ventana.tabla1.clearSelection();
		        }
		    }
		});
	}
	
	public void iniciar() {
		
		dtosInsumos.getInfoPedido();
		ventana.txt1Tabla2.setVisible(false);
		ventana.checkBoxActivos.setVisible(false);
		ventana.txt1Tabla1.setEditable(false);
		ventana.txt2Tabla1.setEditable(false);
		ventana.txt3Tabla1.setVisible(false);
		ventana.lblTituloTabla1.setVisible(false);
		ventana.lblTxt3Tabla1.setVisible(false);
		ventana.txtTituloTabla1.setVisible(true);
		ventana.btnQuitar.setVisible(true);
		ventana.lblTituloTabla2.setText("Lista de insumos solicitados");
		ventana.lblTxt2Tabla2.setText("Fecha:");
		ventana.lblTxt1Tabla1.setText("Empleado solicitante:");
		ventana.lblTxt2Tabla1.setText("Sector solicitante:");
		ventana.txt1Tabla2.setVisible(true);
		ventana.txt1Tabla2.setEditable(false);
		ventana.txt1Tabla2.setColumns(6);
		ventana.txt1Tabla2.setText(dtosInsumos.getFechaSolicitud());
		ventana.txt1Tabla1.setColumns(15);
		ventana.txt1Tabla1.setText(dtosInsumos.getNombre());
		ventana.txt2Tabla1.setColumns(15);
		ventana.txt2Tabla1.setText(dtosInsumos.getSectorSolicitante());
		actualizarTabla1();
		actualizarTabla2();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnAgregar) {
			
			agregar();
		}
		
		if(e.getSource() == ventana.btnQuitar) {
			
			quitar();
		}
		
		if(e.getSource() == ventana.btnEliminar) {
			
			eliminar();
		}
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardar();
		}

		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void agregar() {
		
		ventana.tabla1.clearSelection();
		ventana.tabla2.clearSelection();
		if(!bandera1) {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText("Debe seleccionar un elemento para agregar.");
			return;
		}
		dtosInsumos.actualizarLista('+', elemento1, JOptionPane.showInputDialog("Cantidad a pedir?"));
		actualizarTabla2();
	}
	
	private void quitar() {
		
		ventana.tabla1.clearSelection();
		ventana.tabla2.clearSelection();
		if(!bandera2) {

			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText("Debe seleccionar un elemento para eliminar.");
			return;
		}
		dtosInsumos.actualizarLista('-', elemento2, "");
		actualizarTabla2();
	}

	private void actualizarTabla1() {
		
		ventana.tabla1.setModel(dtosInsumos.getTablaInsumos(ventana.txtTituloTabla1.getText()));
		ventana.tabla1.getColumnModel().getColumn(0).setMaxWidth(40);
		ventana.tabla1.setDefaultEditor(Object.class, null);
		ventana.tabla1.clearSelection();
		ventana.tabla2.clearSelection();
		ventana.lblMsgError.setText("");
		bandera1 = false;
		bandera2 = false;
	}
	
	private void actualizarTabla2() {
		
		ventana.lblMsgError.setText("");
		ventana.tabla2.setModel(dtosInsumos.getTablaSeleccionados());
		ventana.tabla2.getColumnModel().getColumn(0).setMaxWidth(40);
		bandera1 = false;
		bandera2 = false;
	}
	
	private void guardar() {
		
		ventana.lblMsgError.setForeground(Color.BLACK);
		ventana.lblMsgError.setText("Guardando....");
		
		if(!dtosInsumos.setActualizarPedido(ventana.tabla2)) {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText(dtosInsumos.getMsgError());
			return;
		}
		ventana.lblMsgError.setForeground(Color.BLUE);
		ventana.lblMsgError.setText("El pedido se guardo correctamente.");
		ventana.btnGuardar.setEnabled(false);
		ventana.btnEliminar.setEnabled(false);
		ventana.btnAgregar.setEnabled(false);
		ventana.btnQuitar.setEnabled(false);
	}
	
	private void eliminar() {
		
		if(JOptionPane.showConfirmDialog(null, "¿Esta seguro de borrar el pedido?", "Alerta!", JOptionPane.YES_NO_OPTION) == 0) {
			
			if(dtosInsumos.setEliminarPedido()) {
			
				ventana.lblMsgError.setForeground(Color.RED);
				ventana.lblMsgError.setText(dtosInsumos.getMsgError());
				return;
			}
			ventana.lblMsgError.setForeground(Color.BLUE);
			ventana.lblMsgError.setText("El pedido ha sido eliminado.");
			ventana.btnGuardar.setEnabled(false);
			ventana.btnEliminar.setEnabled(false);
			ventana.btnAgregar.setEnabled(false);
			ventana.btnQuitar.setEnabled(false);
		}
	}
}