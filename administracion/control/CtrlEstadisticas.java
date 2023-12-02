package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.DtosEstadisticas;
import vista.ABML;

public class CtrlEstadisticas implements ActionListener {

	private ABML ventana;
	private DtosEstadisticas dtosEstadisticas;
	private int elemento = -1;
	
	public CtrlEstadisticas(ABML vista) {
		
		this.ventana = vista;
		this.dtosEstadisticas = new DtosEstadisticas();
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {

					elemento = ventana.tabla.getSelectedRow();
					mostrarDetalle();
		        }
		    }
		});
	}
	
	public void iniciar() {

		ventana.btnEditar.setVisible(false);
		ventana.btnNuevo.setVisible(false);
		ventana.comboBox1.setVisible(true);
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosEstadisticas.getAños()));
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.comboBox1) {
			
			actualizar();
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
		
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		derecha.setHorizontalAlignment(JLabel.RIGHT);
		ventana.tabla.setModel(dtosEstadisticas.getTablaCobros((String)ventana.comboBox1.getSelectedItem()));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(20);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(60);
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(30);
		ventana.tabla.getColumnModel().getColumn(0).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(1).setMinWidth(30);
		ventana.tabla.getColumnModel().getColumn(1).setMaxWidth(80);
		ventana.tabla.getColumnModel().getColumn(1).setPreferredWidth(50);
		ventana.tabla.getColumnModel().getColumn(1).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(2).setMinWidth(30);
		ventana.tabla.getColumnModel().getColumn(2).setMaxWidth(80);
		ventana.tabla.getColumnModel().getColumn(2).setPreferredWidth(50);
		ventana.tabla.getColumnModel().getColumn(2).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(3).setMinWidth(30);
		ventana.tabla.getColumnModel().getColumn(3).setMaxWidth(80);
		ventana.tabla.getColumnModel().getColumn(3).setPreferredWidth(50);
		ventana.tabla.getColumnModel().getColumn(3).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(4).setMinWidth(30);
		ventana.tabla.getColumnModel().getColumn(4).setMaxWidth(80);
		ventana.tabla.getColumnModel().getColumn(4).setPreferredWidth(50);
		ventana.tabla.getColumnModel().getColumn(4).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(5).setMinWidth(30);
		ventana.tabla.getColumnModel().getColumn(5).setMaxWidth(200);
		ventana.tabla.getColumnModel().getColumn(5).setPreferredWidth(80);
		ventana.tabla.getColumnModel().getColumn(5).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(6).setMinWidth(30);
		ventana.tabla.getColumnModel().getColumn(6).setMaxWidth(200);
		ventana.tabla.getColumnModel().getColumn(6).setPreferredWidth(80);
		ventana.tabla.getColumnModel().getColumn(6).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(7).setMinWidth(30);
		ventana.tabla.getColumnModel().getColumn(7).setMaxWidth(200);
		ventana.tabla.getColumnModel().getColumn(7).setPreferredWidth(80);
		ventana.tabla.getColumnModel().getColumn(7).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(8).setMinWidth(30);
		ventana.tabla.getColumnModel().getColumn(8).setMaxWidth(200);
		ventana.tabla.getColumnModel().getColumn(8).setPreferredWidth(80);
		ventana.tabla.getColumnModel().getColumn(8).setCellRenderer(derecha);
		ventana.tabla.setDefaultEditor(Object.class, null);
	}
	
	private void mostrarDetalle() {
		
		dtosEstadisticas.setSeleccion(elemento);
		elemento = -1;
	}
}