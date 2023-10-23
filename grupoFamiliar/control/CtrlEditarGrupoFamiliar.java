package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import vista.ListadoDoble;
import modelo.DtosGrupoFamiliar;

public class CtrlEditarGrupoFamiliar implements ActionListener {

	private ListadoDoble ventana;
	private DtosGrupoFamiliar dtosFamilia;
	private int elemento1 = -1;
	private int elemento2 = -1;
	
	public CtrlEditarGrupoFamiliar(ListadoDoble vista) {
		
		this.ventana = vista;
		this.dtosFamilia = new DtosGrupoFamiliar();
		this.ventana.btnAgregar.addActionListener(this);
		this.ventana.btnQuitar.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.txt1Tabla2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				actualizar();
			}
		});
		this.ventana.tabla1.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1)
					elemento1 = ventana.tabla1.getSelectedRow();
		    }
		});
		this.ventana.tabla2.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1)
					elemento2 = ventana.tabla2.getSelectedRow();
		    }
		});
	}

	public void iniciar() {
	
		ventana.btnEliminar.setVisible(false);
		ventana.btnQuitar.setVisible(true);
		ventana.txt1Tabla1.setText(dtosFamilia.getNombreFamilia());
		ventana.txt2Tabla1.setText(dtosFamilia.getDescuento());
		ventana.panelListado.remove(ventana.txt3Tabla1);
		ventana.panelListado.remove(ventana.lblTxt3Tabla1);
		ventana.checkBoxActivos.setVisible(false);
		actualizar();
		ventana.setVisible(true);
	}
	
	private void actualizar() {

		elemento1 = -1;
		elemento2 = -1;
		ventana.tabla1.setModel(dtosFamilia.getTablaFamilia());
		ventana.tabla1.getColumnModel().getColumn(0).setPreferredWidth(40);
		ventana.tabla1.getColumnModel().getColumn(0).setMaxWidth(50);
		ventana.tabla1.getColumnModel().getColumn(3).setPreferredWidth(40);
		ventana.tabla1.getColumnModel().getColumn(3).setMaxWidth(50);
		ventana.tabla1.setDefaultEditor(Object.class, null);
		ventana.tabla1.clearSelection();
		ventana.tabla2.setModel(dtosFamilia.getTablaAlumnos(ventana.txt1Tabla2.getText(),true));
		ventana.tabla2.getColumnModel().getColumn(0).setPreferredWidth(40);
		ventana.tabla2.getColumnModel().getColumn(0).setMaxWidth(50);
		ventana.tabla2.setDefaultEditor(Object.class, null);
		ventana.tabla2.clearSelection();
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnAgregar && ventana.isVisible()) {
			
			agregarElementoSeleccionado();
			actualizar();
		}
		
		if(e.getSource() == ventana.btnQuitar && ventana.isVisible()) {
			
			eliminarElemento();
			actualizar();
		}
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardarCambios();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void agregarElementoSeleccionado() {
		
		if(elemento2 == -1) {
			
			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
			return;
		}

		if(dtosFamilia.isRepetido(elemento2) ) {
			
			JOptionPane.showMessageDialog(null, "El elemento seleccionado ya fue agregado.");
			return;
		}
		dtosFamilia.setAgregarElementos(elemento2);
		elemento2 = -1;
	}
	
	private void eliminarElemento() {

		if(elemento1 == -1) {
			
			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
			return;
		}
		
		if(ventana.tabla1.getValueAt(elemento1, 3).equals("E")) {
			
			JOptionPane.showMessageDialog(null, "El elemento seleccionado ya se encuentra marcado para eliminación.");
			return;
		}
		
		if(ventana.tabla1.getValueAt(elemento1, 3).equals("A")) {
			
			JOptionPane.showMessageDialog(null, "No se puede eliminar un elemento que se acaba de guardar.");
			return;
		}
		dtosFamilia.setEliminarElementos(elemento1);
		elemento1 = -1;
	}
	
	private void guardarCambios() {
		
		if(JOptionPane.showConfirmDialog(null, "¿Esta seguro?", "Alerta!", JOptionPane.YES_NO_OPTION) == 0) {
			
			dtosFamilia.setNombreFamilia(ventana.txt1Tabla1.getText());
			dtosFamilia.setDescuento(ventana.txt2Tabla1.getText());

			if(dtosFamilia.guardarCambios()) {
				
				ventana.lblMsgError.setForeground(Color.BLUE);
				ventana.lblMsgError.setText("Se han guardado los cambios en la base de datos.");
				ventana.btnGuardar.setEnabled(false);
				ventana.btnEliminar.setEnabled(false);
				ventana.btnAgregar.setEnabled(false);
				ventana.txt1Tabla1.setEditable(false);
				ventana.txt2Tabla1.setEditable(false);
				ventana.txt3Tabla1.setEditable(false);
				ventana.txt1Tabla2.setEditable(false);
			} else {
				
				ventana.lblMsgError.setForeground(Color.RED);
				ventana.lblMsgError.setText(dtosFamilia.getMsgError());
			}
		}			
	}
}