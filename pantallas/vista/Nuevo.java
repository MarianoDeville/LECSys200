package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class Nuevo extends VentanaModelo {

	private static final long serialVersionUID = 1L;
	public JPanel panel;
	public JScrollPane scrollTabla;
	public JTextField txtLegajo;
	public JTextField txtNombre;
	public JTextField txtApellido;
	public JTextField txtDNI;
	public JTextField txtDia;
	public JTextField txtMes;
	public JTextField txtAño;
	public JTextField txtDireccion;
	public JTextField txtEmail;
	public JTextField txtTelefono;
	public JTextField txt1;
	public JTextField txt2;
	public JTextField txt3;
	public JLabel lblLegajo;
	public JLabel lblNombre;
	public JLabel lblApellido;
	public JLabel lblDNI;
	public JLabel lblNacimiento;
	public JLabel separador1;
	public JLabel separador2;
	public JLabel formato;
	public JLabel lblDireccion;
	public JLabel lblEmail;
	public JLabel lblTelefono;
	public JLabel lblcomboBox1;
	public JLabel lblcomboBox2;
	public JLabel lblTxt1;
	public JLabel lblTxt2;
	public JLabel lblTxt3;
	public JLabel lblMsgError;
	public JCheckBox chkbox1;
	public JComboBox<String> comboBox1;
	public JComboBox<String> comboBox2;
	public JTable tabla;
	public JButton btnImprimir;
	public JButton btnGuardar;
	public JButton btnVolver;
	
	public Nuevo(String nombreVentana) {
		
		super(nombreVentana);
		panel = new JPanel();
		setContentPane(panel);
		SpringLayout contenedor = new SpringLayout();
		setMinimumSize(new Dimension(450, 500));
		setBounds(10, 10, 460, 530);
		panel.setLayout(contenedor);
		
		lblLegajo = new JLabel("Legajo:");
		contenedor.putConstraint(SpringLayout.NORTH, lblLegajo, 30, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblLegajo, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblLegajo, 70, SpringLayout.WEST, lblLegajo);
		panel.add(lblLegajo);
		
		txtLegajo = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtLegajo, -2, SpringLayout.NORTH, lblLegajo);
		contenedor.putConstraint(SpringLayout.WEST, txtLegajo, 5, SpringLayout.EAST, lblLegajo);
		contenedor.putConstraint(SpringLayout.EAST, txtLegajo, 150, SpringLayout.WEST, txtLegajo);
		txtLegajo.setEditable(false);
		panel.add(txtLegajo);
		
		chkbox1 = new JCheckBox("Activo");
		contenedor.putConstraint(SpringLayout.NORTH, chkbox1, -2, SpringLayout.NORTH, lblLegajo);
		contenedor.putConstraint(SpringLayout.WEST, chkbox1, 15, SpringLayout.EAST, txtLegajo);
		chkbox1.setVisible(false);
		panel.add(chkbox1);
				
		lblNombre = new JLabel("Nombres:");
		contenedor.putConstraint(SpringLayout.NORTH, lblNombre, 15, SpringLayout.SOUTH, lblLegajo);
		contenedor.putConstraint(SpringLayout.WEST, lblNombre, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblNombre, 70, SpringLayout.WEST, lblNombre);
		panel.add(lblNombre);
		
		txtNombre = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtNombre, -2, SpringLayout.NORTH, lblNombre);
		contenedor.putConstraint(SpringLayout.WEST, txtNombre, 5, SpringLayout.EAST, lblNombre);
		contenedor.putConstraint(SpringLayout.EAST, txtNombre, 150, SpringLayout.WEST, txtNombre);
		panel.add(txtNombre);
		configurarJTextField(txtNombre, 20);
		
		lblApellido = new JLabel("Apellidos:");
		contenedor.putConstraint(SpringLayout.NORTH, lblApellido, 15, SpringLayout.SOUTH, lblNombre);
		contenedor.putConstraint(SpringLayout.WEST, lblApellido, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblApellido, 70, SpringLayout.WEST, lblApellido);
		panel.add(lblApellido);
		
		txtApellido = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtApellido, -2, SpringLayout.NORTH, lblApellido);
		contenedor.putConstraint(SpringLayout.WEST, txtApellido, 5, SpringLayout.EAST, lblApellido);
		contenedor.putConstraint(SpringLayout.EAST, txtApellido, 150, SpringLayout.WEST, txtApellido);
		panel.add(txtApellido);
		configurarJTextField(txtApellido, 20);
		
		lblDNI = new JLabel("DNI:");
		contenedor.putConstraint(SpringLayout.NORTH, lblDNI, 15, SpringLayout.SOUTH, lblApellido);
		contenedor.putConstraint(SpringLayout.WEST, lblDNI, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblDNI, 70, SpringLayout.WEST, lblDNI);
		panel.add(lblDNI);
		
		txtDNI = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtDNI, -2, SpringLayout.NORTH, lblDNI);
		contenedor.putConstraint(SpringLayout.WEST, txtDNI, 5, SpringLayout.EAST, lblDNI);
		contenedor.putConstraint(SpringLayout.EAST, txtDNI, 150, SpringLayout.WEST, txtDNI);
		panel.add(txtDNI);
		configurarJTextField(txtDNI, 10);
		
		lblNacimiento = new JLabel("Nacimiento:");
		contenedor.putConstraint(SpringLayout.NORTH, lblNacimiento, 15, SpringLayout.SOUTH, lblDNI);
		contenedor.putConstraint(SpringLayout.WEST, lblNacimiento, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblNacimiento, 70, SpringLayout.WEST, lblNacimiento);
		panel.add(lblNacimiento);
		
		txtDia = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtDia, -2, SpringLayout.NORTH, lblNacimiento);
		contenedor.putConstraint(SpringLayout.WEST, txtDia, 5, SpringLayout.EAST, lblNacimiento);
		panel.add(txtDia);
		configurarJTextField(txtDia, 2);
		
		separador1 = new JLabel("/");
		contenedor.putConstraint(SpringLayout.NORTH, separador1, 2, SpringLayout.NORTH, txtDia);
		contenedor.putConstraint(SpringLayout.WEST, separador1, 5, SpringLayout.EAST, txtDia);
		panel.add(separador1);

		txtMes = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtMes, -2, SpringLayout.NORTH, separador1);
		contenedor.putConstraint(SpringLayout.WEST, txtMes, 5, SpringLayout.EAST, separador1);
		panel.add(txtMes);
		configurarJTextField(txtMes, 2);
		
		separador2 = new JLabel("/");
		contenedor.putConstraint(SpringLayout.NORTH, separador2, 2, SpringLayout.NORTH, txtMes);
		contenedor.putConstraint(SpringLayout.WEST, separador2, 5, SpringLayout.EAST, txtMes);
		panel.add(separador2);
		
		txtAño = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtAño, -2, SpringLayout.NORTH, separador2);
		contenedor.putConstraint(SpringLayout.WEST, txtAño, 5, SpringLayout.EAST, separador2);
		panel.add(txtAño);
		configurarJTextField(txtAño, 4);
		
		formato = new JLabel("DD / MM / AAAA");
		contenedor.putConstraint(SpringLayout.NORTH, formato, 2, SpringLayout.NORTH, txtAño);
		contenedor.putConstraint(SpringLayout.WEST, formato, 5, SpringLayout.EAST, txtAño);
		panel.add(formato);
		
		lblDireccion = new JLabel("Dirección:");
		contenedor.putConstraint(SpringLayout.NORTH, lblDireccion, 15, SpringLayout.SOUTH, lblNacimiento);
		contenedor.putConstraint(SpringLayout.WEST, lblDireccion, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblDireccion, 70, SpringLayout.WEST, lblDireccion);
		panel.add(lblDireccion);
		
		txtDireccion = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtDireccion, -2, SpringLayout.NORTH, lblDireccion);
		contenedor.putConstraint(SpringLayout.WEST, txtDireccion, 5, SpringLayout.EAST, lblDireccion);
		contenedor.putConstraint(SpringLayout.EAST, txtDireccion, 285, SpringLayout.WEST, txtDireccion);
		panel.add(txtDireccion);
		configurarJTextField(txtDireccion, 45);
		
		lblEmail = new JLabel("E-mail:");
		contenedor.putConstraint(SpringLayout.NORTH, lblEmail, 15, SpringLayout.SOUTH, lblDireccion);
		contenedor.putConstraint(SpringLayout.WEST, lblEmail, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblEmail, 70, SpringLayout.WEST, lblEmail);
		panel.add(lblEmail);
		
		txtEmail = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtEmail, -2, SpringLayout.NORTH, lblEmail);
		contenedor.putConstraint(SpringLayout.WEST, txtEmail, 5, SpringLayout.EAST, lblEmail);
		contenedor.putConstraint(SpringLayout.EAST, txtEmail, 285, SpringLayout.WEST, txtEmail);
		panel.add(txtEmail);
		configurarJTextField(txtEmail, 40);
		
		lblTelefono = new JLabel("Teléfono:");
		contenedor.putConstraint(SpringLayout.NORTH, lblTelefono, 15, SpringLayout.SOUTH, lblEmail);
		contenedor.putConstraint(SpringLayout.WEST, lblTelefono, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblTelefono, 70, SpringLayout.WEST, lblTelefono);
		panel.add(lblTelefono);
		
		txtTelefono = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtTelefono, -2, SpringLayout.NORTH, lblTelefono);
		contenedor.putConstraint(SpringLayout.WEST, txtTelefono, 5, SpringLayout.EAST, lblTelefono);
		contenedor.putConstraint(SpringLayout.EAST, txtTelefono, 150, SpringLayout.WEST, txtTelefono);
		panel.add(txtTelefono);
		configurarJTextField(txtTelefono, 20);
		
		lblcomboBox1 = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblcomboBox1, 15, SpringLayout.SOUTH, lblTelefono);
		contenedor.putConstraint(SpringLayout.WEST, lblcomboBox1, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblcomboBox1, 70, SpringLayout.WEST, lblcomboBox1);
		panel.add(lblcomboBox1);
		
		comboBox1 = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBox1, -2, SpringLayout.NORTH, lblcomboBox1);
		contenedor.putConstraint(SpringLayout.WEST, comboBox1, 5, SpringLayout.EAST, lblcomboBox1);
		contenedor.putConstraint(SpringLayout.EAST, comboBox1, 250, SpringLayout.WEST, comboBox1);
		panel.add(comboBox1);

		lblcomboBox2 = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblcomboBox2, 15, SpringLayout.SOUTH, lblcomboBox1);
		contenedor.putConstraint(SpringLayout.WEST, lblcomboBox2, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblcomboBox2, 70, SpringLayout.WEST, lblcomboBox2);
		lblcomboBox2.setVisible(false);
		panel.add(lblcomboBox2);
		
		comboBox2 = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBox2, -2, SpringLayout.NORTH, lblcomboBox2);
		contenedor.putConstraint(SpringLayout.WEST, comboBox2, 5, SpringLayout.EAST, lblcomboBox2);
		contenedor.putConstraint(SpringLayout.EAST, comboBox2, 250, SpringLayout.WEST, comboBox2);
		comboBox2.setVisible(false);
		panel.add(comboBox2);
		
		lblTxt1 = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblTxt1, 15, SpringLayout.SOUTH, lblcomboBox2);
		contenedor.putConstraint(SpringLayout.WEST, lblTxt1, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblTxt1, 70, SpringLayout.WEST, lblTxt1);
		lblTxt1.setVisible(false);
		panel.add(lblTxt1);
		
		txt1 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txt1, -2, SpringLayout.NORTH, lblTxt1);
		contenedor.putConstraint(SpringLayout.WEST, txt1, 5, SpringLayout.EAST, lblTxt1);
		txt1.setVisible(false);
		panel.add(txt1);
		configurarJTextField(txt1, 40);
		
		lblTxt2 = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblTxt2, 15, SpringLayout.SOUTH, lblTxt1);
		contenedor.putConstraint(SpringLayout.WEST, lblTxt2, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblTxt2, 70, SpringLayout.WEST, lblTxt2);
		lblTxt2.setVisible(false);
		panel.add(lblTxt2);
		
		txt2 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txt2, -2, SpringLayout.NORTH, lblTxt2);
		contenedor.putConstraint(SpringLayout.WEST, txt2, 5, SpringLayout.EAST, lblTxt2);
		contenedor.putConstraint(SpringLayout.EAST, txt2, 150, SpringLayout.WEST, txt2);
		txt2.setVisible(false);
		panel.add(txt2);
		configurarJTextField(txt2, 20);

		lblTxt3 = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblTxt3, 15, SpringLayout.SOUTH, lblTxt2);
		contenedor.putConstraint(SpringLayout.WEST, lblTxt3, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblTxt3, 70, SpringLayout.WEST, lblTxt3);
		lblTxt3.setVisible(false);
		panel.add(lblTxt3);
		
		txt3 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txt3, -2, SpringLayout.NORTH, lblTxt3);
		contenedor.putConstraint(SpringLayout.WEST, txt3, 5, SpringLayout.EAST, lblTxt3);
		txt3.setVisible(false);
		panel.add(txt3);

		scrollTabla = new JScrollPane();
		contenedor.putConstraint(SpringLayout.NORTH, scrollTabla, 15, SpringLayout.SOUTH, lblTxt2);
		contenedor.putConstraint(SpringLayout.WEST, scrollTabla, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, scrollTabla, 55, SpringLayout.NORTH, scrollTabla);
		contenedor.putConstraint(SpringLayout.EAST, scrollTabla, -20, SpringLayout.EAST, panel);
		panel.add(scrollTabla);
		tabla = new JTable();
		scrollTabla.setVisible(false);
		scrollTabla.setViewportView(tabla);
			
		lblMsgError = new JLabel();
		contenedor.putConstraint(SpringLayout.SOUTH, lblMsgError, -50, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblMsgError, 15, SpringLayout.WEST, panel);
		lblMsgError.setForeground(Color.RED);
		panel.add(lblMsgError);
	
		btnGuardar = new JButton("Guardar");
		contenedor.putConstraint(SpringLayout.SOUTH, btnGuardar, -15, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, btnGuardar, 40, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, btnGuardar, 85, SpringLayout.WEST, btnGuardar);
		panel.add(btnGuardar);
		
		btnImprimir = new JButton("Imprimir");
		contenedor.putConstraint(SpringLayout.SOUTH, btnImprimir, 0, SpringLayout.SOUTH, btnGuardar);
		contenedor.putConstraint(SpringLayout.WEST, btnImprimir, 40, SpringLayout.EAST, btnGuardar);
		contenedor.putConstraint(SpringLayout.EAST, btnImprimir, 85, SpringLayout.WEST, btnImprimir);
		btnImprimir.setVisible(false);
		panel.add(btnImprimir);
		
		btnVolver = new JButton("Volver");
		contenedor.putConstraint(SpringLayout.NORTH, btnVolver, 0, SpringLayout.NORTH, btnImprimir);
		contenedor.putConstraint(SpringLayout.WEST, btnVolver, 40, SpringLayout.EAST, btnImprimir);
		contenedor.putConstraint(SpringLayout.EAST, btnVolver, 85, SpringLayout.WEST, btnVolver);
		panel.add(btnVolver);
	}
	
	private void configurarJTextField(Component nombre, int cantidadCaracteres) {
		
		((JTextField) nombre).setColumns(cantidadCaracteres);
		nombre.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				
				if(((JTextField) nombre).getText().length() >= cantidadCaracteres)
					e.consume();
			}
		});
	}
}