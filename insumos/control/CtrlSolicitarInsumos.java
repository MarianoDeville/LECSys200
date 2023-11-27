package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.DtosEmpleado;
import modelo.DtosInsumos;
import vista.ListadoDoble;

public class CtrlSolicitarInsumos implements ActionListener {

	private ListadoDoble ventana;
	private DtosInsumos dtosInsumos;
	private int elemento1;
	private int elemento2;
	
	public CtrlSolicitarInsumos(ListadoDoble vista) {
		
		this.ventana = vista;
		this.dtosInsumos = new DtosInsumos();
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnAgregar.addActionListener(this);
		this.ventana.btnQuitar.addActionListener(this);
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
					ventana.lblMsgError.setText("");
					ventana.tabla1.clearSelection();
		        }
		    }
		});
	}
	
	public void iniciar() {
		
		DtosEmpleado dtosEmpleado = new DtosEmpleado();
		ventana.btnEliminar.setVisible(false);
		ventana.txt1Tabla2.setVisible(false);
		ventana.checkBoxActivos.setVisible(false);
		ventana.txt1Tabla1.setVisible(false);
		ventana.txt2Tabla1.setVisible(false);
		ventana.txt3Tabla1.setVisible(false);
		ventana.lblTituloTabla1.setVisible(false);
		ventana.lblTxt3Tabla1.setVisible(false);
		ventana.comboBox1.setVisible(true);
		ventana.comboBox1.setModel(new DefaultComboBoxModel<>(dtosInsumos.getListaEmpleados()));
		ventana.comboBox2.setVisible(true);
		ventana.comboBox2.setModel(new DefaultComboBoxModel<>(dtosEmpleado.getListaSectores()));
		ventana.txtTituloTabla1.setVisible(true);
		ventana.btnQuitar.setVisible(true);
		ventana.lblTxt1Tabla1.setText("Empleado solicitante:");
		ventana.lblTxt2Tabla1.setText("Sector solicitante:");
		ventana.lblTituloTabla2.setText("Listado elementos solicitados");
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
		if(elemento1 == -1) {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText("Debe seleccionar un elemento para agregar.");
			return;
		}
		String cantidad = JOptionPane.showInputDialog(null, "Cantidad a pedir?", (String)ventana.tabla1.getValueAt(elemento1, 2), 3);
		
		if(!dtosInsumos.actualizarLista('+', elemento1, cantidad)) {

			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText(dtosInsumos.getMsgError());
			return;
		}
		actualizarTabla2();
	}
	
	private void quitar() {
		
		ventana.tabla1.clearSelection();
		ventana.tabla2.clearSelection();
		if(elemento2 == -1) {

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
		elemento1 = -1;
		elemento2 = -1;
	}
	
	private void actualizarTabla2() {
		
		ventana.lblMsgError.setText("");
		ventana.tabla2.setModel(dtosInsumos.getTablaSeleccionados());
		ventana.tabla2.getColumnModel().getColumn(0).setMaxWidth(40);
		elemento1 = -1;
		elemento2 = -1;
	}
	
	private void guardar() {
		
		ventana.lblMsgError.setForeground(Color.BLACK);
		ventana.lblMsgError.setText("Guardando....");
		dtosInsumos.setSectorSolicitante((String)ventana.comboBox2.getSelectedItem());
		dtosInsumos.setIdSolicitante(ventana.comboBox1.getSelectedIndex());
		
		if(!dtosInsumos.setGuardarPedido()) {
			
			ventana.lblMsgError.setForeground(Color.RED);
			ventana.lblMsgError.setText(dtosInsumos.getMsgError());
			return;
		}
		ventana.lblMsgError.setForeground(Color.BLUE);
		ventana.lblMsgError.setText("La información se guardo correctamente.");
		ventana.btnGuardar.setEnabled(false);
		ventana.btnAgregar.setEnabled(false);
		ventana.btnQuitar.setEnabled(false);
	}
}