package vista;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class NuevoSimple extends VentanaModelo {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	public JLabel lblID;
	public JLabel lblNombre;
	public JLabel lblDescripción;
	public JLabel lblFormato;
	public JLabel lblMsgError;
	public JLabel lblCantidad;
	public JTextField txtID;
	public JTextField txtNombre;
	public JTextField txtDescripción;
	public JTextField txtFormato;
	public JTextField txtCantidad;
	public JButton btnGuardar;
	public JButton btnBorrar;
	public JButton btnVolver;

	public NuevoSimple(String nombreVentana, int x, int y) {
		
		super(nombreVentana);
		panel = new JPanel();
		setContentPane(panel);
		SpringLayout contenedor = new SpringLayout();
		setMinimumSize(new Dimension(300, 300));
		setBounds(x + 5, y + 5, 800, 300);
		panel.setLayout(contenedor);
		
		lblID = new JLabel("ID:");
		contenedor.putConstraint(SpringLayout.NORTH, lblID, 30, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblID, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblID, 70, SpringLayout.WEST, lblID);
		panel.add(lblID);
		
		txtID = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtID, -2, SpringLayout.NORTH, lblID);
		contenedor.putConstraint(SpringLayout.WEST, txtID, 5, SpringLayout.EAST, lblID);
		txtID.setColumns(10);
		txtID.setEditable(false);
		panel.add(txtID);
		
		lblNombre = new JLabel("Nombre:");
		contenedor.putConstraint(SpringLayout.NORTH, lblNombre, 25, SpringLayout.SOUTH, lblID);
		contenedor.putConstraint(SpringLayout.WEST, lblNombre, 0, SpringLayout.WEST, lblID);
		panel.add(lblNombre);
		
		txtNombre = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtNombre, -2, SpringLayout.NORTH, lblNombre);
		contenedor.putConstraint(SpringLayout.WEST, txtNombre, 0, SpringLayout.WEST, txtID);
		contenedor.putConstraint(SpringLayout.EAST, txtNombre, -15, SpringLayout.EAST, panel);
		panel.add(txtNombre);
		configurarJTextField(txtNombre, 60);
		
		lblDescripción = new JLabel("Descripción:");
		contenedor.putConstraint(SpringLayout.NORTH, lblDescripción, 25, SpringLayout.SOUTH, lblNombre);
		contenedor.putConstraint(SpringLayout.WEST, lblDescripción, 0, SpringLayout.WEST, lblNombre);
		panel.add(lblDescripción);
		
		txtDescripción = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtDescripción, -2, SpringLayout.NORTH, lblDescripción);
		contenedor.putConstraint(SpringLayout.WEST, txtDescripción, 0, SpringLayout.WEST, txtID);
		contenedor.putConstraint(SpringLayout.EAST, txtDescripción, -15, SpringLayout.EAST, panel);
		panel.add(txtDescripción);
		configurarJTextField(txtDescripción, 120);
		
		lblFormato = new JLabel("Formato:");
		contenedor.putConstraint(SpringLayout.NORTH, lblFormato, 25, SpringLayout.SOUTH, lblDescripción);
		contenedor.putConstraint(SpringLayout.WEST, lblFormato, 0, SpringLayout.WEST, lblDescripción);
		panel.add(lblFormato);
		
		txtFormato = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtFormato, -2, SpringLayout.NORTH, lblFormato);
		contenedor.putConstraint(SpringLayout.WEST, txtFormato, 0, SpringLayout.WEST, txtID);
		contenedor.putConstraint(SpringLayout.EAST, txtFormato, -15, SpringLayout.EAST, panel);
		panel.add(txtFormato);
		configurarJTextField(txtFormato, 60);
		
		lblMsgError = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblMsgError, 25, SpringLayout.SOUTH, lblFormato);
		contenedor.putConstraint(SpringLayout.WEST, lblMsgError, 0, SpringLayout.WEST, lblFormato);
		panel.add(lblMsgError);
		
		btnGuardar = new JButton("Guardar");
		contenedor.putConstraint(SpringLayout.SOUTH, btnGuardar, -15, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, btnGuardar, 40, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, btnGuardar, 85, SpringLayout.WEST, btnGuardar);
		panel.add(btnGuardar);
		
		btnBorrar = new JButton("Borrar");
		contenedor.putConstraint(SpringLayout.SOUTH, btnBorrar, 0, SpringLayout.SOUTH, btnGuardar);
		contenedor.putConstraint(SpringLayout.WEST, btnBorrar, 40, SpringLayout.EAST, btnGuardar);
		contenedor.putConstraint(SpringLayout.EAST, btnBorrar, 85, SpringLayout.WEST, btnBorrar);
		btnBorrar.setVisible(false);
		panel.add(btnBorrar);
		
		btnVolver = new JButton("Volver");
		contenedor.putConstraint(SpringLayout.SOUTH, btnVolver, -15, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, btnVolver, -25, SpringLayout.EAST, panel);
		contenedor.putConstraint(SpringLayout.WEST, btnVolver, -85, SpringLayout.EAST, btnVolver);
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
	
	public void setTamaño(int x, int y) {
		
		setBounds(10, 10, x, y);
	}
}