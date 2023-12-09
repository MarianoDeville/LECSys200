package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import dao.OperadorSistema;
import modelo.DtosProveedores;
import vista.ABML;
import vista.NuevoCurso;

public class CtrlABMLProveedores implements ActionListener{
	
	private ABML ventana;
	private NuevoCurso ventanaNuevoProveedor;
	private NuevoCurso ventanaEditarProveedor;
	private DtosProveedores dtosProveedores;
	private int elemento = -1;
	
	public CtrlABMLProveedores(ABML vista) {
		
		this.ventana = vista;
		this.dtosProveedores = new DtosProveedores();
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnEditar.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.chckbx1.addActionListener(this);
		this.ventana.comboBox1.addActionListener(this);
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
					editar();
				}
			}
		});
	}
	
	public void iniciar() {

		if(!OperadorSistema.isAcceso()) {
			
			ventana.dispose();
			return;
		}
		ventana.comboBox1.setVisible(true);
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(new String[] {"Insumos","Servicios", "Todos"}));
		ventana.comboBox1.setSelectedIndex(2);
		ventana.txt1.setVisible(true);
		ventana.chckbx1.setVisible(true);
		actualizar();
		ventana.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.chckbx1 && ventana.isVisible()) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.comboBox1 && ventana.isVisible()) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.btnNuevo) {
			
			nuevo();
		}
		
		if(ventanaNuevoProveedor != null) {
			
			if(e.getSource() == ventanaNuevoProveedor.btnVolver) {
				
				actualizar();
			}
		}
		
		if(e.getSource() == ventana.btnEditar) {
			
			editar();
		}
		
		if(ventanaEditarProveedor != null) {
			
			if(e.getSource() == ventanaEditarProveedor.btnVolver) {
				
				actualizar();
			}
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
			
			ventana.dispose();
		}
	}
	
	private void actualizar() {

		elemento = -1;
		ventana.tabla.setModel(dtosProveedores.getTablaProveedores(ventana.txt1.getText(), 
																	ventana.chckbx1.isSelected(), 
																	ventana.comboBox1.getSelectedIndex()));
		ventana.tabla.setDefaultEditor(Object.class, null);
	}
	
	private void nuevo() {
		
		if(ventanaNuevoProveedor != null && ventanaNuevoProveedor.isVisible()) {

			ventanaNuevoProveedor.setVisible(true);
			return;
		}
		ventanaNuevoProveedor = new NuevoCurso("Nueno proveedor", ventana.getX(), ventana.getY());
		CtrlNuevoProveedor ctrlNuevoProveedor = new CtrlNuevoProveedor(ventanaNuevoProveedor);
		ctrlNuevoProveedor.iniciar();
		ventanaNuevoProveedor.btnVolver.addActionListener(this);
	}
	
	private void editar() {

		if(elemento == -1) {

			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento para editar.");
			return;
		}
		
		if(ventanaEditarProveedor != null && ventanaEditarProveedor.isVisible())
			ventanaEditarProveedor.dispose();
		dtosProveedores.setProveedorSeleccionado(elemento);
		elemento = -1;
		ventanaEditarProveedor = new NuevoCurso("Editar proveedor", ventana.getX(), ventana.getY());
		CtrlEditarProveedor ctrlEditarProveedor = new CtrlEditarProveedor(ventanaEditarProveedor);
		ctrlEditarProveedor.iniciar();
		ventanaEditarProveedor.btnVolver.addActionListener(this);
	}
}