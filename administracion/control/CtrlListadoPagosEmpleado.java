package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import modelo.DtosPagos;
import vista.Listado;

public class CtrlListadoPagosEmpleado implements ActionListener{
	
	private Listado ventana;
	private DtosPagos dtosPagos;
	private int elemento = -1;
	
	public CtrlListadoPagosEmpleado(Listado vista) {
		
		this.ventana = vista;
		this.dtosPagos = new DtosPagos();
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.comboBox2.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btn1A.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1) {
				
					elemento = ventana.tabla.getSelectedRow();
				} else if(e.getClickCount() == 2) {
					
					elemento = ventana.tabla.getSelectedRow();
					verInformacion();
				}
			}
		});
	}
	
	public void iniciar() {

		ventana.btn1A.setText("Ver");
		ventana.btn1A.setVisible(true);
		ventana.lblComboBox1.setText("Año:");
		ventana.lblComboBox1.setVisible(true);
		ventana.comboBox1.setVisible(true);
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosPagos.listadoAños()));
		
		if(dtosPagos.listadoAños().length > 1)
			ventana.comboBox1.setSelectedIndex(1);
		ventana.lblComboBox2.setText("Mes:");
		ventana.lblComboBox2.setVisible(true);
		ventana.comboBox2.setVisible(true);
		ventana.comboBox2.setModel(new DefaultComboBoxModel<String>(dtosPagos.listadoMeses()));
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.comboBox1) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.comboBox2) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch(PrinterException f) {
				
				JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
				CtrlLogErrores.guardarError(f.getMessage());
			}
		}
		
		if(e.getSource() == ventana.btn1A) {
			
			verInformacion();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}

	private void actualizar() {
		
		elemento = -1;
		ventana.tabla.setModel(dtosPagos.getTablaPagosEmpleado((String)ventana.comboBox1.getSelectedItem(), 
																(int)ventana.comboBox2.getSelectedIndex()));
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(30);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(60);
		ventana.tabla.getColumnModel().getColumn(1).setMinWidth(60);
		ventana.tabla.getColumnModel().getColumn(1).setMaxWidth(90);
		ventana.tabla.setDefaultEditor(Object.class, null);
	}
	
	private void verInformacion() {
		
		if(elemento == -1) {
			
			JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
			return;
		}
		JOptionPane.showMessageDialog(null, dtosPagos.getComentario(elemento));
		elemento = -1;
	}
}