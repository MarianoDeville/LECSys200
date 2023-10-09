package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import modelo.DtosAlumno;
import vista.InformeAlumno;
import vista.Listado;

public class CtrlListado implements ActionListener {

	private Listado ventana;
	private DtosAlumno dtosAlumno;
	private int elemento;
	private boolean bandera;
	
	public CtrlListado(Listado vista) {
		
		this.ventana = vista;
		this.dtosAlumno = new DtosAlumno();
		this.ventana.comboBox1.addActionListener(this);
		this.ventana.comboBox2.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btn1A.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) {

					elemento = ventana.tabla.getSelectedRow();
					bandera = true;
		        } else if(e.getClickCount() == 2) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
		        	bandera = true;
		        	informe();
		        }
		    }
		});
	}

	public void iniciar() {

		ventana.btn1A.setVisible(true);
		ventana.btn1A.setText("Informe");
		ventana.comboBox1.setVisible(true);
		ventana.comboBox2.setVisible(true);
		ventana.lblTxt1.setVisible(true);
		ventana.lblTxt1.setText("Cantidad de alumnos:");
		ventana.txt1.setVisible(true);
		ventana.txt1.setEditable(false);
		ventana.comboBox1.setModel(new DefaultComboBoxModel<String>(dtosAlumno.getCriterio()));
		ventana.comboBox2.setModel(new DefaultComboBoxModel<String>(dtosAlumno.getListadoValorCriterio((String)ventana.comboBox1.getSelectedItem())));
		actualizar();
		ventana.setVisible(true);
	}
	
	private void actualizar() {
		
		ventana.tabla.setModel(dtosAlumno.getListadoAlumnos((String)ventana.comboBox1.getSelectedItem() 
																,dtosAlumno.getIdValorCriterio((String)ventana.comboBox1.getSelectedItem()
																,ventana.comboBox2.getSelectedIndex())));
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
		ventana.tabla.setDefaultEditor(Object.class, null);
		ventana.txt1.setText(dtosAlumno.getCantAlumnos());
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.comboBox1) {

			ventana.comboBox2.setModel(new DefaultComboBoxModel<String>(dtosAlumno.getListadoValorCriterio((String)ventana.comboBox1.getSelectedItem())));
			actualizar();
		}
		
		if(e.getSource() == ventana.comboBox2) {

			actualizar();
		}
		
		if(e.getSource() == ventana.btn1A) {

			informe();
		}
		
		if(e.getSource() == ventana.btnImprimir) {

			try {
				
				ventana.tabla.print();
			} catch (Exception f) {
				
				JOptionPane.showMessageDialog(null, "Error al intentar imprimir.");
				CtrlLogErrores.guardarError(f.getMessage());
			}
		}		
		
		if(e.getSource() == ventana.btnVolver) {

			ventana.dispose();
		}
	}
	
	private void informe() {
		
		if(!bandera) {
			
			JOptionPane.showMessageDialog(null, "Debe seleccionar un alumno.");
			return;
		}
		
		dtosAlumno.setLegajo((String)ventana.tabla.getValueAt(elemento, 0));
		dtosAlumno.recuperarInformacionAlumno( true);
		InformeAlumno ventanaInforme = new InformeAlumno("Informe académico");
		CtrlInformeAlumno ctrlInforme = new CtrlInformeAlumno(ventanaInforme);
		ctrlInforme.iniciar();

	}
}