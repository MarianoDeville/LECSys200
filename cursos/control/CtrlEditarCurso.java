package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.DtosCurso;
import vista.NuevoCurso;

public class CtrlEditarCurso implements ActionListener{
	
	private NuevoCurso ventana;
	private DtosCurso dtosCurso;
		
	public CtrlEditarCurso(NuevoCurso vista) {
		
		this.ventana = vista;
		this.dtosCurso = new DtosCurso();
		this.ventana.comboBoxAula.addActionListener(this);
		this.ventana.comboBoxNivel.addActionListener(this);
		this.ventana.comboBoxAula.addActionListener(this);
		this.ventana.comboBoxProfesor.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnBorrar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btnValidar.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				if (e.getClickCount() == 2)
					
					selección(ventana.tabla.getSelectedRow(), ventana.tabla.getSelectedColumn());
			}
		});
	}
	
	public void iniciar() {
		
		ventana.btnBorrar.setVisible(true);
		ventana.btnValidar.setEnabled(false);
		ventana.lblDescripción.setForeground(Color.GRAY);
		ventana.lblDescripción.setText("X: No disponible. - O: Asignado al curso actual. - C: Comienzo. - F: Fin. - CE: Comienzo eliminación. - FE: Fin eliminación.");
		ventana.comboBoxAño.setModel(new DefaultComboBoxModel<String>(new String [] {dtosCurso.getAño()}));
		ventana.comboBoxNivel.setModel(new DefaultComboBoxModel<String>(new String [] {dtosCurso.getNivel()}));
		ventana.comboBoxProfesor.setModel(new DefaultComboBoxModel<String>(dtosCurso.getListaProfesores()));
		ventana.comboBoxAula.setModel(new DefaultComboBoxModel<String>(dtosCurso.getListaAulas()));
		ventana.txtCuota.setText(dtosCurso.getValorCuota());
		ventana.comboBoxAula.setSelectedIndex(dtosCurso.getAula());
		ventana.comboBoxAño.setSelectedItem(dtosCurso.getAño());
		ventana.comboBoxProfesor.setSelectedItem(dtosCurso.getNombreProfesor());
		ventana.comboBoxNivel.setSelectedItem(dtosCurso.getNivel());
		ventana.setTamaño(1200, 330);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.comboBoxAula && ventana.isVisible()) {
			
			actualizar();
		}	
		
		if(e.getSource() == ventana.comboBoxProfesor && ventana.isVisible()) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.btnGuardar) {
		
			guardar();
		}
		
		if(e.getSource() == ventana.btnBorrar) {
			
			borrar();
		}
		
		if(e.getSource() == ventana.btnValidar) {
			
			if(!dtosCurso.autocompletar(ventana.tabla)) {
				
				ventana.lblMensageError.setForeground(Color.RED);
				ventana.lblMensageError.setText(dtosCurso.getMsgError());
			}
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}

	private void actualizar() {

		ventana.tabla.setModel(dtosCurso.getHorariosCurso(ventana.comboBoxAula.getSelectedIndex(),
														  ventana.comboBoxProfesor.getSelectedIndex()));
		ventana.tabla.setDefaultEditor(Object.class, null);
		DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
		centrado.setHorizontalAlignment(JLabel.CENTER);
		
		for(int i = 0 ; i < ventana.tabla.getColumnCount() ; i++) {
			
			ventana.tabla.getColumnModel().getColumn(i).setPreferredWidth(40);
			ventana.tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
		}
		ventana.tabla.setRowHeight(25);
	}
	
	private void borrar() {
		
		dtosCurso.setEstado(0);
		
		if(JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el curso?", "Alerta!", JOptionPane.YES_NO_OPTION) == 0) {
			
			if(dtosCurso.setActualizarCurso()) {
				
				ventana.comboBoxAño.setModel(new DefaultComboBoxModel<String>(new String [] {}));
				ventana.comboBoxNivel.setModel(new DefaultComboBoxModel<String>(new String [] {}));		
				ventana.comboBoxProfesor.setModel(new DefaultComboBoxModel<String>(new String [] {}));
				ventana.comboBoxAula.setModel(new DefaultComboBoxModel<String>(new String [] {}));
				ventana.txtCuota.setText("");
				ventana.lblMensageError.setForeground(Color.BLUE);
				ventana.lblMensageError.setText("El registro se borro exitosamente.");
			} else {
				
				ventana.lblMensageError.setForeground(Color.RED);
				ventana.lblMensageError.setText("Error al acceder a la base de datos.");
			}
		}
	}
	
	private void selección(int fila, int columna) {
		
		ventana.btnValidar.setEnabled(true);
		ventana.lblMensageError.setText("");
		boolean comienzo;
		boolean comienzoEliminar;
		int cont = 0;
		int contE = 0;
		
		for(int i = 0; i < ventana.tabla.getColumnCount();i++) {
	
			switch((String)ventana.tabla.getValueAt(fila, i)) {
			
				case "C":
				case "C ":
					
					cont++;	
					break;
	
				case "F":
				case "F ":
					
					cont--;	
					break;	
					
				case "CE":
				case "CE ":
					
					contE++;	
					break;	
							
				case "FE":
				case "FE ":
					
					contE--;	
					break;
					
				default:
					break;
			}
		}
		comienzo = cont == 0? true:false;
		comienzoEliminar = contE == 0? true:false;

		switch((String)ventana.tabla.getValueAt(fila, columna)) {
		
			case " ":
				ventana.tabla.setValueAt(comienzo?"C":"F", fila, columna);
				break;
	
			case "X ":
				ventana.tabla.setValueAt(comienzo?"C ":"F ", fila, columna);
				break;
				
			case "O":
				ventana.tabla.setValueAt(comienzoEliminar?"CE":"FE", fila, columna);
				break;
			
			case "O ":
				ventana.tabla.setValueAt(comienzoEliminar?"CE ":"FE ", fila, columna);
				break;
				
			case "C":
			case "F":
				ventana.tabla.setValueAt(" ", fila, columna);
				break;
				
			case "CE":
			case "FE":
				ventana.tabla.setValueAt("O", fila, columna);
				break;
			
			case "C ":
			case "F ":
				ventana.tabla.setValueAt("X ", fila, columna);
				break;
				
			case "CE ":
			case "FE ":
				ventana.tabla.setValueAt("O ", fila, columna);
				break;
		}
	}
	
	private void guardar() {
		
		dtosCurso.setAño((String)ventana.comboBoxAño.getSelectedItem());
		dtosCurso.setNivel((String)ventana.comboBoxNivel.getSelectedItem());
		dtosCurso.setIdProfesor(ventana.comboBoxProfesor.getSelectedIndex());
		dtosCurso.setValorCuota(ventana.txtCuota.getText());
		dtosCurso.setAula(ventana.comboBoxAula.getSelectedIndex());
		dtosCurso.setHorarios(ventana.tabla);

		if(!dtosCurso.isCheckInfo()) {
		
			ventana.lblMensageError.setForeground(Color.RED);
			ventana.lblMensageError.setText(dtosCurso.getMsgError());
			return;
		}		

		if(dtosCurso.setActualizarCurso()) {
			
			ventana.lblMensageError.setForeground(Color.BLUE);
			ventana.lblMensageError.setText("Registro guardado con éxito.");
			ventana.btnBorrar.setEnabled(false);
			ventana.btnGuardar.setEnabled(false);
			ventana.btnValidar.setEnabled(false);
		} else {
			
			ventana.lblMensageError.setForeground(Color.RED);
			ventana.lblMensageError.setText("No se pudo guardar en la base de datos.");
		}
	}
}