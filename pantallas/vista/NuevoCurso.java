package vista;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

public class NuevoCurso extends VentanaModelo{

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private int posx;
	private int posy;
	public JScrollPane scrollTabla;
	public JTable tabla;
	public JTextField txtNombre;
	public JTextField txtDirección;
	public JTextField Cuit;
	public JTextField txtCuota;
	public JTextField txtComentario;
	public JComboBox<String> comboBoxNivel;
	public JComboBox<String> comboBoxAño;
	public JComboBox<String> comboBoxProfesor;
	public JComboBox<String> comboBoxAula;
	public JCheckBox checkBoxServicios;
	public JCheckBox checkBoxActivo;
	public JLabel lblNivel;
	public JLabel lblAño;
	public JLabel lblProfesor;
	public JLabel lblAula;
	public JLabel lblCuota;
	public JLabel lblDescripción;
	public JLabel lblMensageError;
	public JLabel lblHorario;
	public JLabel lblLunes;
	public JLabel lblMartes;
	public JLabel lblMiercoles;
	public JLabel lblJueves;
	public JLabel lblViernes;
	public JLabel lblSabado;
	public JButton btnGuardar;
	public JButton btnValidar;
	public JButton btnBorrar;
	public JButton btnVolver;
		
	public NuevoCurso(String nombreVentana, int x, int y) {
		
		super(nombreVentana);
		setResizable(true);
		setBounds(x + 5, y + 5, 800, 330);
		panel = new JPanel();
		panel.setBorder(null);
		setContentPane(panel);
		SpringLayout contenedor = new SpringLayout();
		panel.setLayout(contenedor);
		posx = x + 5;
		posy = y + 5;

		lblNivel = new JLabel("Nivel:");
		contenedor.putConstraint(SpringLayout.NORTH, lblNivel, 15, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblNivel, 25, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, lblNivel, 35, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblNivel, 95, SpringLayout.WEST, panel);
		panel.add(lblNivel);
		
		comboBoxNivel = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxNivel, 15, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxNivel, 95, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, comboBoxNivel, 35, SpringLayout.NORTH, panel);
		panel.add(comboBoxNivel);

		txtNombre = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtNombre, 15, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, txtNombre, 95, SpringLayout.WEST, panel);
		panel.add(txtNombre);
		txtNombre.setVisible(false);
		txtNombre.setColumns(20);

		checkBoxServicios = new JCheckBox();
		contenedor.putConstraint(SpringLayout.NORTH, checkBoxServicios, 15, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, checkBoxServicios, 100, SpringLayout.EAST, txtNombre);
		checkBoxServicios.setVisible(false);
		panel.add(checkBoxServicios);		
		
		lblAño = new JLabel("Año:");
		contenedor.putConstraint(SpringLayout.NORTH, lblAño, 40, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblAño, 25, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, lblAño, 60, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblAño, 95, SpringLayout.WEST, panel);
		panel.add(lblAño);	
		
		comboBoxAño = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxAño, 40, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxAño, 95, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, comboBoxAño, 60, SpringLayout.NORTH, panel);
		panel.add(comboBoxAño);

		txtDirección = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtDirección, 40, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, txtDirección, 95, SpringLayout.WEST, panel);
		panel.add(txtDirección);
		txtDirección.setVisible(false);
		txtDirección.setColumns(20);

		checkBoxActivo = new JCheckBox();
		contenedor.putConstraint(SpringLayout.NORTH, checkBoxActivo, 40, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, checkBoxActivo, 100, SpringLayout.EAST, txtDirección);
		checkBoxActivo.setVisible(false);
		panel.add(checkBoxActivo);	

		lblProfesor = new JLabel("Profesor:");
		contenedor.putConstraint(SpringLayout.NORTH, lblProfesor, 65, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblProfesor, 25, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, lblProfesor, 85, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblProfesor, 95, SpringLayout.WEST, panel);
		panel.add(lblProfesor);
		
		comboBoxProfesor = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxProfesor, 65, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxProfesor, 95, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, comboBoxProfesor, 85, SpringLayout.NORTH, panel);
		panel.add(comboBoxProfesor);
		
		lblAula = new JLabel("Aula:");
		contenedor.putConstraint(SpringLayout.NORTH, lblAula, 115, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblAula, 25, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, lblAula, 135, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblAula, 95, SpringLayout.WEST, panel);
		panel.add(lblAula);
		
		comboBoxAula = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBoxAula, 115, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, comboBoxAula, 95, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, comboBoxAula, 135, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, comboBoxAula, 215, SpringLayout.WEST, panel);
		panel.add(comboBoxAula);
	
		txtComentario = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtComentario, 115, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, txtComentario, 95, SpringLayout.WEST, panel);
		panel.add(txtComentario);
		txtComentario.setVisible(false);
		txtComentario.setColumns(60);
		
		lblHorario = new JLabel("Horario:     ");
		contenedor.putConstraint(SpringLayout.NORTH, lblHorario, 150, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblHorario, 25, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, lblHorario, 170, SpringLayout.NORTH, panel);
		panel.add(lblHorario);
		
		lblLunes = new JLabel("Lunes");
		contenedor.putConstraint(SpringLayout.NORTH, lblLunes, 175, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblLunes, 25, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, lblLunes, 195, SpringLayout.NORTH, panel);
		lblLunes.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblLunes);
		
		lblMartes = new JLabel("Martes");
		contenedor.putConstraint(SpringLayout.NORTH, lblMartes, 200, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblMartes, 25, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, lblMartes, 220, SpringLayout.NORTH, panel);
		lblMartes.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblMartes);
		
		lblMiercoles = new JLabel("Miércoles");
		contenedor.putConstraint(SpringLayout.NORTH, lblMiercoles, 225, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblMiercoles, 25, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, lblMiercoles, 245, SpringLayout.NORTH, panel);
		lblMiercoles.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblMiercoles);
		
		lblJueves = new JLabel("Jueves");
		contenedor.putConstraint(SpringLayout.NORTH, lblJueves, 250, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblJueves, 25, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, lblJueves, 270, SpringLayout.NORTH, panel);
		lblJueves.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblJueves);
		
		lblViernes = new JLabel("Viernes");
		contenedor.putConstraint(SpringLayout.NORTH, lblViernes, 275, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblViernes, 25, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, lblViernes, 295, SpringLayout.NORTH, panel);
		lblViernes.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblViernes);
		
		lblSabado = new JLabel("Sábado");
		contenedor.putConstraint(SpringLayout.NORTH, lblSabado, 300, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblSabado, 25, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, lblSabado, 320, SpringLayout.NORTH, panel);
		lblSabado.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblSabado);

		lblDescripción = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblDescripción, 340, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblDescripción, 25, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, lblDescripción, 380, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblDescripción, -25, SpringLayout.EAST, panel);
		lblDescripción.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblDescripción);

		lblMensageError = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblMensageError, 370, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblMensageError, 25, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblMensageError, -25, SpringLayout.EAST, panel);
		lblMensageError.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblMensageError);
		
		lblCuota = new JLabel("Valor cuota:");
		contenedor.putConstraint(SpringLayout.NORTH, lblCuota, 90, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblCuota, 25, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, lblCuota, 110, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, lblCuota, 95, SpringLayout.WEST, panel);
		panel.add(lblCuota);
		
		txtCuota = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtCuota, 90, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, txtCuota, 95, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, txtCuota, 215, SpringLayout.WEST, panel);
		panel.add(txtCuota);
		txtCuota.setColumns(10);

		scrollTabla = new JScrollPane();
		contenedor.putConstraint(SpringLayout.NORTH, scrollTabla, 150, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, scrollTabla, 4, SpringLayout.EAST, lblHorario);
		contenedor.putConstraint(SpringLayout.SOUTH, scrollTabla, 340, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, scrollTabla, -25, SpringLayout.EAST, panel);
		panel.add(scrollTabla);
		tabla = new JTable();
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla.doLayout();
		scrollTabla.setViewportView(tabla); 
		
		btnGuardar = new JButton("Guardar");
		contenedor.putConstraint(SpringLayout.SOUTH, btnGuardar, -15, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, btnGuardar, 20, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.EAST, btnGuardar, 100, SpringLayout.WEST, btnGuardar);
		panel.add(btnGuardar);
		
		btnValidar = new JButton("Validar");
		contenedor.putConstraint(SpringLayout.NORTH, btnValidar, 0, SpringLayout.NORTH, btnGuardar);
		contenedor.putConstraint(SpringLayout.WEST, btnValidar, 115, SpringLayout.EAST, btnGuardar);
		contenedor.putConstraint(SpringLayout.EAST, btnValidar, 100, SpringLayout.WEST, btnValidar);
		panel.add(btnValidar);

		btnBorrar = new JButton("Borrar");
		contenedor.putConstraint(SpringLayout.NORTH, btnBorrar, 0, SpringLayout.NORTH, btnValidar);
		contenedor.putConstraint(SpringLayout.WEST, btnBorrar, 115, SpringLayout.EAST, btnValidar);
		contenedor.putConstraint(SpringLayout.EAST, btnBorrar, 100, SpringLayout.WEST, btnBorrar);
		btnBorrar.setVisible(false);
		panel.add(btnBorrar);
		
		btnVolver = new JButton("Volver");
		contenedor.putConstraint(SpringLayout.NORTH, btnVolver, 0, SpringLayout.NORTH, btnBorrar);
		contenedor.putConstraint(SpringLayout.WEST, btnVolver, 115, SpringLayout.EAST, btnBorrar);
		contenedor.putConstraint(SpringLayout.EAST, btnVolver, 100, SpringLayout.WEST, btnVolver);
		panel.add(btnVolver);
	}
	
	public void setTamaño(int x, int y) {
		
		setBounds(posx, posy, x, y);
	}
}