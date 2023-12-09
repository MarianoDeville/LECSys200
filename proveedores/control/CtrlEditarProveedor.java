package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import modelo.DtosProveedores;
import vista.NuevoCurso;

public class CtrlEditarProveedor implements ActionListener{
	
	private NuevoCurso ventana;
	private DtosProveedores dtosProveedores;
	private int elemento;
	
	public CtrlEditarProveedor(NuevoCurso vista) {
		
		this.ventana = vista;
		this.dtosProveedores = new DtosProveedores();
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btnValidar.addActionListener(this);
		this.ventana.btnBorrar.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				ventana.lblMensageError.setText("");
			}
		});
		this.ventana.txtDirección.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				ventana.lblMensageError.setText("");
			}
		});
		this.ventana.txtCuota.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				ventana.lblMensageError.setText("");
			}
		});
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {

					elemento = ventana.tabla.getSelectedRow();
					ventana.lblMensageError.setText("");
		        }
		    }
		});
	}
	
	public void iniciar() {

		ventana.comboBoxNivel.setVisible(false);
		ventana.comboBoxAño.setVisible(false);
		ventana.comboBoxAula.setVisible(false);
		ventana.checkBoxServicios.setVisible(true);
		ventana.checkBoxServicios.setText("Proveedor de servicios");
		ventana.lblAula.setVisible(true);
		ventana.lblAula.setText("Comentario:");
		ventana.txtComentario.setVisible(true);
		ventana.btnBorrar.setVisible(true);
		ventana.txtNombre.setVisible(true);
		ventana.txtDirección.setVisible(true);
		ventana.checkBoxActivo.setVisible(true);
		ventana.checkBoxActivo.setText("Activo");
		ventana.lblNivel.setText("Nombre:");
		ventana.lblAño.setText("Dirección:");
		ventana.lblProfesor.setText("Condición:");
		ventana.lblCuota.setText("CUIT:");
		ventana.lblHorario.setText("");
		ventana.lblLunes.setText("");
		ventana.lblMartes.setText("");
		ventana.lblMiercoles.setText("");
		ventana.lblJueves.setText("");
		ventana.lblViernes.setText("");
		ventana.lblSabado.setText("");
		ventana.btnValidar.setText("Agregar");
		ventana.comboBoxProfesor.setModel(new DefaultComboBoxModel<String>(dtosProveedores.getListaCondiciones()));
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
	
		if(e.getSource() == ventana.btnValidar) {
			
			dtosProveedores.setNuevoContacto("+");
			actualizar();
		}
		
		if(e.getSource() == ventana.btnBorrar) {
			
			if(elemento == -1) {
				
				ventana.lblMensageError.setForeground(Color.RED);
				ventana.lblMensageError.setText("Debe seleccionar un contacto para eliminar.");
				return;
			}
			dtosProveedores.setNuevoContacto("-");
			actualizar();
		}
	
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	
		if(e.getSource() == ventana.btnGuardar) {
			
			guardar();
		}
	}
	
	private void actualizar() {
		
		ventana.txtNombre.setText(dtosProveedores.getNombre());
		ventana.txtDirección.setText(dtosProveedores.getDirección());
		ventana.txtCuota.setText(dtosProveedores.getCuit());
		ventana.txtComentario.setText(dtosProveedores.getComentario());
		ventana.comboBoxProfesor.setSelectedItem(dtosProveedores.getSituaciónFiscal());
		ventana.checkBoxServicios.setSelected(dtosProveedores.getServicio());
		ventana.checkBoxActivo.setSelected(dtosProveedores.isEstado());
		ventana.tabla.setModel(dtosProveedores.getTablaContactos(ventana.tabla, elemento));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(100);
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(150);
		ventana.tabla.getColumnModel().getColumn(1).setMinWidth(100);
		ventana.tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
		ventana.tabla.getColumnModel().getColumn(2).setMinWidth(100);
		ventana.tabla.getColumnModel().getColumn(2).setPreferredWidth(170);
		ventana.tabla.getColumnModel().getColumn(3).setMinWidth(100);
		ventana.tabla.getColumnModel().getColumn(3).setPreferredWidth(257);
		ventana.lblMensageError.setForeground(Color.RED);
		ventana.lblMensageError.setText(dtosProveedores.getMensageError());
		elemento = -1;
	}
	
	private void guardar() {
		
		dtosProveedores.setNombre(ventana.txtNombre.getText());
		dtosProveedores.setDirección(ventana.txtDirección.getText());
		dtosProveedores.setSituaciónFiscal((String)ventana.comboBoxProfesor.getSelectedItem());
		dtosProveedores.setCuit(ventana.txtCuota.getText());
		dtosProveedores.setComentario(ventana.txtComentario.getText());
		dtosProveedores.setServicio(ventana.checkBoxServicios.isSelected());
		dtosProveedores.setEstado(ventana.checkBoxActivo.isSelected());
		
		if(dtosProveedores.setActualizar(ventana.tabla)) {
			
			ventana.lblMensageError.setForeground(Color.BLUE);
			ventana.btnGuardar.setEnabled(false);
			ventana.btnValidar.setEnabled(false);
			ventana.btnBorrar.setEnabled(false);
		} else {

			ventana.lblMensageError.setForeground(Color.RED);
		}
		ventana.lblMensageError.setText(dtosProveedores.getMensageError());
	}
}