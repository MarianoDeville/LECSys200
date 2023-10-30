package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

public class NuevoUsuario extends VentanaModelo{

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	public JScrollPane scrollTabla;
	public JTextField txtUsuario;
	public JPasswordField txtContrase�a;
	public JPasswordField txtReContrase�a;
	public JComboBox<String> comboBox1;
	public JTable tabla;
	public JLabel lblNombre;
	public JLabel lblContrase�a;
	public JLabel lblReContrase�a;
	public JLabel lblcomboBox1;
	public JLabel lblNivelAcceso;
	public JLabel lblMsgError;
	public JButton btnGuardar;
	public JButton btnBorrar;
	public JButton btnVolver;

	public NuevoUsuario(String nombreVentana, int x, int y) {
		
		super(nombreVentana);
		panel = new JPanel();
		setContentPane(panel);
		SpringLayout contenedor = new SpringLayout();
		setMinimumSize(new Dimension(460, 300));
		setBounds(x + 5, y + 5, 460, 460);
		panel.setLayout(contenedor);
		
		lblNombre = new JLabel("Usuario:");
		contenedor.putConstraint(SpringLayout.NORTH, lblNombre, 30, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblNombre, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblNombre, 115, SpringLayout.WEST, lblNombre);
		panel.add(lblNombre);
		
		txtUsuario = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtUsuario, -2, SpringLayout.NORTH, lblNombre);
		contenedor.putConstraint(SpringLayout.WEST, txtUsuario, 5, SpringLayout.EAST, lblNombre);
		txtUsuario.setColumns(20);
		panel.add(txtUsuario);
		
		lblContrase�a = new JLabel("Contrase�a:");
		contenedor.putConstraint(SpringLayout.NORTH, lblContrase�a, 15, SpringLayout.SOUTH, lblNombre);
		contenedor.putConstraint(SpringLayout.WEST, lblContrase�a, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblContrase�a, 115, SpringLayout.WEST, lblContrase�a);
		panel.add(lblContrase�a);
		
		txtContrase�a = new JPasswordField();
		contenedor.putConstraint(SpringLayout.NORTH, txtContrase�a, -2, SpringLayout.NORTH, lblContrase�a);
		contenedor.putConstraint(SpringLayout.WEST, txtContrase�a, 5, SpringLayout.EAST, lblContrase�a);
		panel.add(txtContrase�a);
		configurarJTextField(txtContrase�a, 30);
		
		lblReContrase�a = new JLabel("Repetir contrase�a:");
		contenedor.putConstraint(SpringLayout.NORTH, lblReContrase�a, 15, SpringLayout.SOUTH, txtContrase�a);
		contenedor.putConstraint(SpringLayout.WEST, lblReContrase�a, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblReContrase�a, 115, SpringLayout.WEST, lblReContrase�a);
		panel.add(lblReContrase�a);
		
		txtReContrase�a = new JPasswordField();
		contenedor.putConstraint(SpringLayout.NORTH, txtReContrase�a, -2, SpringLayout.NORTH, lblReContrase�a);
		contenedor.putConstraint(SpringLayout.WEST, txtReContrase�a, 5, SpringLayout.EAST, lblReContrase�a);
		panel.add(txtReContrase�a);
		configurarJTextField(txtReContrase�a, 30);
		
		lblcomboBox1 = new JLabel("Empleado:");
		contenedor.putConstraint(SpringLayout.NORTH, lblcomboBox1, 15, SpringLayout.SOUTH, lblReContrase�a);
		contenedor.putConstraint(SpringLayout.WEST, lblcomboBox1, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblcomboBox1, 115, SpringLayout.WEST, lblcomboBox1);
		panel.add(lblcomboBox1);
		
		comboBox1 = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBox1, -2, SpringLayout.NORTH, lblcomboBox1);
		contenedor.putConstraint(SpringLayout.WEST, comboBox1, 5, SpringLayout.EAST, lblcomboBox1);
		contenedor.putConstraint(SpringLayout.EAST, comboBox1, 250, SpringLayout.WEST, comboBox1);
		panel.add(comboBox1);

		lblNivelAcceso = new JLabel("Nivel de acceso:");
		lblNivelAcceso.setHorizontalAlignment(SwingConstants.CENTER);
		contenedor.putConstraint(SpringLayout.NORTH, lblNivelAcceso, 15, SpringLayout.SOUTH, lblcomboBox1);
		contenedor.putConstraint(SpringLayout.WEST, lblNivelAcceso, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblNivelAcceso, 400, SpringLayout.WEST, lblNivelAcceso);
		lblNivelAcceso.setVisible(false);
		panel.add(lblNivelAcceso);
		
		scrollTabla = new JScrollPane();
		contenedor.putConstraint(SpringLayout.NORTH, scrollTabla, 15, SpringLayout.SOUTH, lblNivelAcceso);
		contenedor.putConstraint(SpringLayout.WEST, scrollTabla, 0, SpringLayout.WEST, lblNivelAcceso);
		contenedor.putConstraint(SpringLayout.SOUTH, scrollTabla, 151, SpringLayout.NORTH, scrollTabla);
		contenedor.putConstraint(SpringLayout.EAST, scrollTabla, -50, SpringLayout.EAST, panel);
		panel.add(scrollTabla);
		scrollTabla.setVisible(false);
		tabla = new JTable();
		scrollTabla.setViewportView(tabla);

		lblMsgError = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblMsgError, 15, SpringLayout.SOUTH, scrollTabla);
		contenedor.putConstraint(SpringLayout.WEST, lblMsgError, 15, SpringLayout.WEST, panel);
		lblMsgError.setForeground(Color.RED);
		panel.add(lblMsgError);
		
		btnGuardar = new JButton("Guardar");
		contenedor.putConstraint(SpringLayout.SOUTH, btnGuardar, -15, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, btnGuardar, 40, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, btnGuardar, 85, SpringLayout.WEST, btnGuardar);
		panel.add(btnGuardar);
		
		btnBorrar = new JButton("Borrar");
		contenedor.putConstraint(SpringLayout.NORTH, btnBorrar, 0, SpringLayout.NORTH, btnGuardar);
		contenedor.putConstraint(SpringLayout.WEST, btnBorrar, 60, SpringLayout.EAST, btnGuardar);
		contenedor.putConstraint(SpringLayout.EAST, btnBorrar, 85, SpringLayout.WEST, btnBorrar);
		btnBorrar.setVisible(false);
		panel.add(btnBorrar);

		btnVolver = new JButton("Volver");
		contenedor.putConstraint(SpringLayout.NORTH, btnVolver, 0, SpringLayout.NORTH, btnBorrar);
		contenedor.putConstraint(SpringLayout.WEST, btnVolver, 60, SpringLayout.EAST, btnBorrar);
		contenedor.putConstraint(SpringLayout.EAST, btnVolver, 85, SpringLayout.WEST, btnVolver);
		panel.add(btnVolver);
	}
	
	private void configurarJTextField(Component nombre, int cantidadCaracteres) {
		
		((JTextField) nombre).setColumns(20);
		nombre.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				
				if(((JTextField) nombre).getText().length() >= cantidadCaracteres)
					e.consume();
			}
		});
	}
}