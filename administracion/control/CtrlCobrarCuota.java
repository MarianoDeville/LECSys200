package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.DtosCobros;
import vista.Cobro;
import vista.Listado;

public class CtrlCobrarCuota implements ActionListener {
	
	private Listado ventana;
	private DtosCobros dtosCobros;
	private Cobro ventanaCobrar;
	private int elemento = -1;
		
	public CtrlCobrarCuota(Listado vista) {
		
		this.ventana = vista;
		this.dtosCobros = new DtosCobros();
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btn1A.addActionListener(this);
		this.ventana.chckbx1.addActionListener(this);
		this.ventana.txt3.addKeyListener(new KeyAdapter() {
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
		        	cobrar();
		        }
		    }
		});
	}
	
	public void iniciar() {

		elemento = -1;
		ventana.chckbx1.setText("<html>Pago adelantado</html>");
		ventana.chckbx1.setSelected(false);
		actualizar();
		ventana.lblTxt1.setVisible(true);
		ventana.lblTxt1.setText("Elementos listados:");
		ventana.txt1.setVisible(true);
		ventana.txt1.setEditable(false);
		ventana.txt3.setVisible(true);
		ventana.txt3.setEditable(true);
		ventana.btn1A.setVisible(true);
		ventana.btn1A.setText("Cobrar");
		ventana.chckbx1.setVisible(true);
		ventana.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.chckbx1 && ventana.isVisible()) {
			
			actualizar();
		}
		
		if(e.getSource() == ventana.btn1A) {
			
			cobrar();
		}
		
		if(ventanaCobrar != null) {
			
			if(e.getSource() == ventanaCobrar.btnVolver) {
	
				actualizar();
			}
		}
		
		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch (PrinterException f) {
				
				f.printStackTrace();
			}
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			if(ventanaCobrar != null)
				ventanaCobrar.dispose();
			ventana.dispose();
		}
	}
	
	private void actualizar() {
		
		ventana.tabla.setModel(dtosCobros.getTablaDeudores(ventana.txt3.getText(), ventana.chckbx1.isSelected()));
		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		derecha.setHorizontalAlignment(JLabel.RIGHT);
		ventana.tabla.getColumnModel().getColumn(1).setPreferredWidth(75);
		ventana.tabla.getColumnModel().getColumn(1).setMaxWidth(90);
		ventana.tabla.getColumnModel().getColumn(1).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(2).setPreferredWidth(50);
		ventana.tabla.getColumnModel().getColumn(2).setMaxWidth(60);
		ventana.tabla.getColumnModel().getColumn(2).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(3).setPreferredWidth(75);
		ventana.tabla.getColumnModel().getColumn(3).setMaxWidth(90);
		ventana.tabla.getColumnModel().getColumn(3).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(4).setPreferredWidth(50);
		ventana.tabla.getColumnModel().getColumn(4).setMaxWidth(60);
		ventana.tabla.getColumnModel().getColumn(4).setCellRenderer(derecha);
		ventana.tabla.getColumnModel().getColumn(5).setPreferredWidth(75);
		ventana.tabla.getColumnModel().getColumn(5).setMaxWidth(90);
		ventana.tabla.getColumnModel().getColumn(5).setCellRenderer(derecha);
		ventana.tabla.setDefaultEditor(Object.class, null);
		ventana.txt1.setText(ventana.tabla.getRowCount() + "");
	}
	
	private void cobrar() {

		ventana.tabla.clearSelection();
		
		if(ventanaCobrar != null) {

			JOptionPane.showMessageDialog(null, "Le ventana \"Cobrar\" ya se encuentra abierta.");
			return;
		}
		
		if(elemento == -1) {
			
			JOptionPane.showMessageDialog(null, "No ha seleccionado ningún elemento.");
			return;
		}
		dtosCobros.setInfoCobro(elemento);
		ventanaCobrar = new Cobro("Cobro de cuota");
		CtrlRealizarCobro ctrolCobrar = new CtrlRealizarCobro(ventanaCobrar);
		ctrolCobrar.iniciar();
		ventanaCobrar.btnVolver.addActionListener(this);
		elemento = -1;
	}
}