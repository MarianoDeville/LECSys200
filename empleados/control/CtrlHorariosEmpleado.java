package control;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.DtosEmpleado;
import vista.ListadoDoble2;

public class CtrlHorariosEmpleado implements ActionListener {

	private ListadoDoble2 ventana;
	private DtosEmpleado dtosEmpleado;
	private boolean refresco = false;
	private boolean estado = false;
	
	public CtrlHorariosEmpleado(ListadoDoble2 vista) {
		
		this.ventana = vista;
		this.dtosEmpleado = new DtosEmpleado();
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnCompletar.addActionListener(this);
		this.ventana.cmbBoxSector.addActionListener(this);
		this.ventana.cmbBoxGranularidad.addActionListener(this);
		this.ventana.tabla1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
				
					dtosEmpleado.setEmpleado(ventana.tabla1.getSelectedRow());
					actualizaInfoEmpleado();
				}
			}
		});
		this.ventana.tabla2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			
				if (e.getClickCount() == 2 && estado)
					selección(ventana.tabla2.getSelectedRow(), ventana.tabla2.getSelectedColumn());
			}
		});
		this.ventana.txtSuperior.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				actualizaEmpleados();
			}
		});
	}
	
	public void iniciar() {

		ventana.btnImprimir.setVisible(false);
		ventana.lblHorario.setVisible(true);
		ventana.lblHorario.setText("Horarios:");
		ventana.lblLunes.setVisible(true);
		ventana.lblLunes.setText("Lunes");
		ventana.lblMartes.setVisible(true);
		ventana.lblMartes.setText("Martes");
		ventana.lblMiercoles.setVisible(true);
		ventana.lblMiercoles.setText("Miércoles");
		ventana.lblJueves.setVisible(true);
		ventana.lblJueves.setText("Jueves");
		ventana.lblViernes.setVisible(true);
		ventana.lblViernes.setText("Viernes");
		ventana.lblSabado.setVisible(true);
		ventana.lblSabado.setText("Sábado");
		ventana.cmbBoxSector.setModel(new DefaultComboBoxModel<>(dtosEmpleado.getListaSectores()));
		ventana.cmbBoxGranularidad.setModel(new DefaultComboBoxModel<>(dtosEmpleado.getGranularidad()));
		ventana.cmbBoxGranularidad.setSelectedItem("30 min.");
		ventana.lblTxtMedio1.setText("Nombre:");
		ventana.lblTxtMedio2.setText("Carga horaria semanal:");
		ventana.lblTxtMedio3.setVisible(false);
		ventana.txtMedio3.setVisible(false);
		dtosEmpleado.clearVariables();
		actualizaEmpleados();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.cmbBoxSector) {
			
			actualizaEmpleados();
		}
		
		if(e.getSource() == ventana.cmbBoxGranularidad) {
		
			if(refresco)
				actualizaInfoEmpleado();
			else
				refresco = true;
		}
		
		if(e.getSource() == ventana.btnCompletar) {
			
			dtosEmpleado.autocompletar(ventana.tabla2);
			ventana.txtMedio2.setText(dtosEmpleado.getCantidadHoras(ventana.tabla2));
			
			if(ventana.txtMedio2.getText().equals("0:00")) {
				
				ventana.cmbBoxGranularidad.setEnabled(true);
				ventana.cmbBoxGranularidad.setSelectedIndex(dtosEmpleado.getGranAlmacenada());
			} else {
				
				ventana.cmbBoxGranularidad.setEnabled(false);
			}
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
		
		if(e.getSource() == ventana.btnGuardar) {
			
			if(dtosEmpleado.setHorarios(ventana.cmbBoxGranularidad.getSelectedIndex(), ventana.tabla2)) {
				
				ventana.lblMsgError.setForeground(Color.BLUE);
				ventana.lblMsgError.setText("La información se actualizó.");
			} else {
				
				ventana.lblMsgError.setForeground(Color.RED);
				ventana.lblMsgError.setText("Hubo un problema al intentar actualizar la información.");
			}
		}
	}
	
	private void actualizaEmpleados(){
		
		ventana.tabla1.setModel(dtosEmpleado.getListadoEmpleados((String)ventana.cmbBoxSector.getSelectedItem(),
																				 ventana.txtSuperior.getText()));
		ventana.tabla1.getColumnModel().getColumn(0).setPreferredWidth(40);
		ventana.tabla1.getColumnModel().getColumn(0).setMaxWidth(50);
		ventana.tabla1.setDefaultEditor(Object.class, null);
		dtosEmpleado.clearVariables();
		actualizaInfoEmpleado();
	}
	
	private void actualizaInfoEmpleado() {

		ventana.lblMsgError.setText("");
		ventana.tabla2.setModel(new DefaultTableModel());
		estado = ventana.cmbBoxSector.getSelectedItem().equals("Docente")?false:true;
		
		if(dtosEmpleado.getApellido() == null || dtosEmpleado.getApellido().length() == 0) {

			ventana.txtMedio1.setText("");	
			ventana.txtMedio2.setText("");
			ventana.cmbBoxGranularidad.setVisible(false);
			ventana.btnCompletar.setVisible(false);
			ventana.btnGuardar.setEnabled(false);
			return;
		}
		ventana.txtMedio1.setText(dtosEmpleado.getApellido() + ", " + dtosEmpleado.getNombre());
		ventana.tabla2.setEnabled(estado);
		ventana.btnGuardar.setEnabled(estado);
		ventana.btnCompletar.setVisible(estado);
		ventana.cmbBoxGranularidad.setVisible(estado);
		ventana.tabla2.setModel(dtosEmpleado.getHorarios(ventana.cmbBoxGranularidad.getSelectedIndex()));
		ventana.tabla2.setDefaultEditor(Object.class, null);
		ventana.txtMedio2.setText(dtosEmpleado.getCantidadHoras(ventana.tabla2));
		ventana.lblMsgError.setForeground(Color.GRAY);
		ventana.lblMsgError.setText("O: Ocupado - C: Comienzo - F: Fin - CE: Comienzo elimincación - FE: Fin elimincación");
		
		if(ventana.txtMedio2.getText().equals("0:00"))
			ventana.cmbBoxGranularidad.setEnabled(true);
		else
			ventana.cmbBoxGranularidad.setEnabled(false);
		ventana.tabla2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ventana.tabla2.doLayout();
		DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
		centrado.setHorizontalAlignment(JLabel.CENTER);

		for(int i = 0 ;i < ventana.tabla2.getColumnCount() ;i++) {
			
			ventana.tabla2.getColumnModel().getColumn(i).setPreferredWidth(40);
			ventana.tabla2.getColumnModel().getColumn(i).setCellRenderer(centrado);
		}
	}

	private void selección(int fila, int columna) {
		
		boolean comienzo;
		boolean comienzoEliminar;
		int cont = 0;
		int contE = 0;

		for(int i = 0; i < ventana.tabla2.getColumnCount();i++) {
			
			if(ventana.tabla2.getValueAt(fila, i).equals("C") || 
					ventana.tabla2.getValueAt(fila, i).equals("C "))
				cont++;
			
			if(ventana.tabla2.getValueAt(fila, i).equals("F") || 
					ventana.tabla2.getValueAt(fila, i).equals("F "))
				cont--;
			
			if(ventana.tabla2.getValueAt(fila, i).equals("CE") || 
					ventana.tabla2.getValueAt(fila, i).equals("CE "))
				contE++;
			
			if(ventana.tabla2.getValueAt(fila, i).equals("FE") || 
					ventana.tabla2.getValueAt(fila, i).equals("FE "))
				contE--;
		}
		comienzo = cont == 0? true:false;
		comienzoEliminar = contE == 0? true:false;
		
		switch((String)ventana.tabla2.getValueAt(fila, columna)) {
		
			case " ":
				ventana.tabla2.setValueAt(comienzo?"C":"F", fila, columna);
				break;
	
			case "X ":
				ventana.tabla2.setValueAt(comienzo?"C ":"F ", fila, columna);
				break;
				
			case "O":
				ventana.tabla2.setValueAt(comienzoEliminar?"CE":"FE", fila, columna);
				break;
			
			case "O ":
				ventana.tabla2.setValueAt(comienzoEliminar?"CE ":"FE ", fila, columna);
				break;
				
			case "C":
			case "F":
				ventana.tabla2.setValueAt(" ", fila, columna);
				break;
				
			case "CE":
			case "FE":
				ventana.tabla2.setValueAt("O", fila, columna);
				break;
			
			case "C ":
			case "F ":
				ventana.tabla2.setValueAt("X ", fila, columna);
				break;
				
			case "CE ":
			case "FE ":
				ventana.tabla2.setValueAt("O ", fila, columna);
				break;
		}
	}
}