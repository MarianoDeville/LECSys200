package vista;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class Cobro extends VentanaModelo {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JScrollPane scrollTabla1;
	public JScrollPane scrollTabla2;
	public JTable tabla1;
	public JTable tabla2;
	public JLabel lblNombre;
	public JLabel lbl1;
	public JLabel lbl2;
	public JLabel lbl3;
	public JLabel lbl4;
	public JLabel lbl5;
	public JLabel lblComboBox;
	public JLabel lblTabla;
	public JLabel lblEmail;
	public JLabel lblFactura;
	public JLabel lblMsgError;
	public JCheckBox chckbxEnviarEmail;
	public JCheckBox chckbxTabla2;
	public JComboBox<String> comboBox;
	public JTextField txtNombre;
	public JTextField txt1;
	public JTextField txt2;
	public JTextField txt3;
	public JTextField txt4;
	public JTextField txt5;
	public JTextField txt6;
	public JTextField txtFactura;
	public JTextField txtEmail;
	public JTextField txtTabla2;
	public JButton btnCobrar;
	public JButton btnCentral;
	public JButton btnVolver;

	public Cobro(String nombreVentana) {
		
		super(nombreVentana);
		panel = new JPanel();
		setContentPane(panel);
		SpringLayout contenedor = new SpringLayout();
		panel.setLayout(contenedor);
		
		scrollTabla1 = new JScrollPane();
		contenedor.putConstraint(SpringLayout.NORTH, scrollTabla1, 20, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, scrollTabla1, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, scrollTabla1, -410, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, scrollTabla1, -20, SpringLayout.EAST, panel);
		panel.add(scrollTabla1);
		tabla1 = new JTable();
		scrollTabla1.setViewportView(tabla1);
		
		lblNombre = new JLabel("Nombre de familia:");
		contenedor.putConstraint(SpringLayout.NORTH, lblNombre, 20, SpringLayout.SOUTH, scrollTabla1);
		contenedor.putConstraint(SpringLayout.WEST, lblNombre, 35, SpringLayout.WEST, panel);
		panel.add(lblNombre);
		
		txtNombre = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtNombre, -2, SpringLayout.NORTH, lblNombre);
		contenedor.putConstraint(SpringLayout.WEST, txtNombre, 180, SpringLayout.WEST, panel);
		txtNombre.setEditable(false);
		panel.add(txtNombre);
		txtNombre.setColumns(20);
		
		lbl1 = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lbl1, 20, SpringLayout.SOUTH, lblNombre);
		contenedor.putConstraint(SpringLayout.WEST, lbl1, 0, SpringLayout.WEST, lblNombre);
		lbl1.setVisible(false);
		panel.add(lbl1);
		
		txt1 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txt1, -2, SpringLayout.NORTH, lbl1);
		contenedor.putConstraint(SpringLayout.WEST, txt1, 0, SpringLayout.WEST, txtNombre);
		txt1.setVisible(false);
		panel.add(txt1);
		txt1.setColumns(3);
		
		lbl2 = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lbl2, 20, SpringLayout.SOUTH, lbl1);
		contenedor.putConstraint(SpringLayout.WEST, lbl2, 0, SpringLayout.WEST, lblNombre);
		panel.add(lbl2);
		
		txt2 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txt2, -2, SpringLayout.NORTH, lbl2);
		contenedor.putConstraint(SpringLayout.WEST, txt2, 0, SpringLayout.WEST, txtNombre);
		panel.add(txt2);
		txt2.setColumns(7);
		
		lbl3 = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lbl3, 20, SpringLayout.SOUTH, lbl2);
		contenedor.putConstraint(SpringLayout.WEST, lbl3, 0, SpringLayout.WEST, lbl2);
		panel.add(lbl3);
		
		txt3 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txt3, -2, SpringLayout.NORTH, lbl3);
		contenedor.putConstraint(SpringLayout.WEST, txt3, 0, SpringLayout.WEST, txtNombre);
		panel.add(txt3);
		txt3.setColumns(7);
		
		lbl4 = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lbl4, 20, SpringLayout.SOUTH, lbl3);
		contenedor.putConstraint(SpringLayout.WEST, lbl4, 0, SpringLayout.WEST, lbl3);
		panel.add(lbl4);
		
		txt4 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txt4, -2, SpringLayout.NORTH, lbl4);
		contenedor.putConstraint(SpringLayout.WEST, txt4, 0, SpringLayout.WEST, txtNombre);
		txt4.setEditable(false);
		panel.add(txt4);
		txt4.setColumns(10);

		lbl5 = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lbl5, 20, SpringLayout.SOUTH, lbl4);
		contenedor.putConstraint(SpringLayout.WEST, lbl5, 0, SpringLayout.WEST, lbl4);
		lbl5.setVisible(false);
		panel.add(lbl5);
		
		txt5 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txt5, -2, SpringLayout.NORTH, lbl5);
		contenedor.putConstraint(SpringLayout.WEST, txt5, 0, SpringLayout.WEST, txtNombre);
		txt5.setVisible(false);
		panel.add(txt5);
		txt5.setColumns(10);
		
		lblFactura = new JLabel("Factura nro.:");
		contenedor.putConstraint(SpringLayout.NORTH, lblFactura, 20, SpringLayout.SOUTH, lbl5);
		contenedor.putConstraint(SpringLayout.WEST, lblFactura, 0, SpringLayout.WEST, lblNombre);
		panel.add(lblFactura);
		
		txtFactura = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtFactura, -2, SpringLayout.NORTH, lblFactura);
		contenedor.putConstraint(SpringLayout.WEST, txtFactura, 0, SpringLayout.WEST, txtNombre);
		panel.add(txtFactura);
		txtFactura.setColumns(16);	
		
		chckbxEnviarEmail = new JCheckBox("Enviar comprobante por e-mail");
		contenedor.putConstraint(SpringLayout.NORTH, chckbxEnviarEmail, 20, SpringLayout.SOUTH, lblFactura);
		contenedor.putConstraint(SpringLayout.WEST, chckbxEnviarEmail, 0, SpringLayout.WEST, lblNombre);
		panel.add(chckbxEnviarEmail);
				
		lblEmail = new JLabel("E-mail:");
		contenedor.putConstraint(SpringLayout.NORTH, lblEmail, 15, SpringLayout.SOUTH, chckbxEnviarEmail);
		contenedor.putConstraint(SpringLayout.WEST, lblEmail, 0, SpringLayout.WEST, lblNombre);
		lblEmail.setVisible(false);
		panel.add(lblEmail);
		
		txtEmail = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtEmail, -2, SpringLayout.NORTH, lblEmail);
		contenedor.putConstraint(SpringLayout.WEST, txtEmail, 10, SpringLayout.EAST, lblEmail);
		panel.add(txtEmail);
		txtEmail.setVisible(false);
		txtEmail.setColumns(25);		

		chckbxTabla2 = new JCheckBox("Agragar a grupo");
		contenedor.putConstraint(SpringLayout.NORTH, chckbxTabla2, 20, SpringLayout.SOUTH, scrollTabla1);
		contenedor.putConstraint(SpringLayout.WEST, chckbxTabla2, 425, SpringLayout.WEST, panel);
		panel.add(chckbxTabla2);
		
		lblComboBox = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblComboBox, 20, SpringLayout.SOUTH, scrollTabla1);
		contenedor.putConstraint(SpringLayout.WEST, lblComboBox, 0, SpringLayout.WEST, chckbxTabla2);
		lblComboBox.setVisible(false);
		panel.add(lblComboBox);
		
		comboBox = new JComboBox<>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBox, -2, SpringLayout.NORTH, lblComboBox);
		contenedor.putConstraint(SpringLayout.WEST, comboBox, 5, SpringLayout.EAST, lblComboBox);
		comboBox.setVisible(false);
		panel.add(comboBox);
		
		txt6 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txt6, -2, SpringLayout.NORTH, lblComboBox);
		contenedor.putConstraint(SpringLayout.WEST, txt6, 5, SpringLayout.EAST, lblComboBox);
		txt6.setVisible(false);
		panel.add(txt6);
		txt6.setColumns(10);
		
		lblTabla = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblTabla, 15, SpringLayout.SOUTH, chckbxTabla2);
		contenedor.putConstraint(SpringLayout.WEST, lblTabla, 0, SpringLayout.WEST, lblComboBox);
		lblTabla.setVisible(false);
		panel.add(lblTabla);

		txtTabla2 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtTabla2, -2, SpringLayout.NORTH, lblTabla);
		contenedor.putConstraint(SpringLayout.WEST, txtTabla2, 5, SpringLayout.WEST, txt6);
		panel.add(txtTabla2);
		txtTabla2.setColumns(15);

		scrollTabla2 = new JScrollPane();
		contenedor.putConstraint(SpringLayout.NORTH, scrollTabla2, 15, SpringLayout.SOUTH, txtTabla2);
		contenedor.putConstraint(SpringLayout.WEST, scrollTabla2, 15, SpringLayout.EAST, txtNombre);
		contenedor.putConstraint(SpringLayout.SOUTH, scrollTabla2, -50, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, scrollTabla2, -20, SpringLayout.EAST, panel);
		panel.add(scrollTabla2);
		tabla2 = new JTable();
		scrollTabla2.setViewportView(tabla2);

		lblMsgError = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblMsgError, 15, SpringLayout.SOUTH, lblEmail);
		contenedor.putConstraint(SpringLayout.WEST, lblMsgError, 0, SpringLayout.WEST, lblEmail);
		contenedor.putConstraint(SpringLayout.EAST, lblMsgError, 0, SpringLayout.EAST, txtNombre);
		panel.add(lblMsgError);
			
		btnCobrar = new JButton("Cobrar");
		contenedor.putConstraint(SpringLayout.SOUTH, btnCobrar, -15, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, btnCobrar, 20, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, btnCobrar, 90, SpringLayout.WEST, btnCobrar);
		panel.add(btnCobrar);
		
		btnCentral = new JButton();
		contenedor.putConstraint(SpringLayout.SOUTH, btnCentral, -15, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, btnCentral, 60, SpringLayout.EAST, btnCobrar);
		contenedor.putConstraint(SpringLayout.EAST, btnCentral, 90, SpringLayout.WEST, btnCentral);
		btnCentral.setVisible(false);
		panel.add(btnCentral);
		
		btnVolver = new JButton("Volver");
		contenedor.putConstraint(SpringLayout.SOUTH, btnVolver, -15, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, btnVolver, -20, SpringLayout.EAST, panel);
		contenedor.putConstraint(SpringLayout.WEST, btnVolver, -90, SpringLayout.EAST, btnVolver);
		panel.add(btnVolver);
	}
}