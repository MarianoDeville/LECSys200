package vista;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

public class Listado extends VentanaModelo {

	private static final long serialVersionUID = 1L;
	private JPanel listado;
	private JScrollPane scrollTabla;
	public JLabel lblComboBox1;
	public JLabel lblComboBox2;
	public JLabel lblTxt1;
	public JLabel lblTxt2;
	public JLabel lblTxt1Der;
	public JLabel lblChckbx2;
	public JLabel lblHorario;
	public JLabel lblLunes;
	public JLabel lblMartes;
	public JLabel lblMiercoles;
	public JLabel lblJueves;
	public JLabel lblViernes;
	public JLabel lblSabado;
	public JTextField txt1;
	public JTextField txt2;
	public JTextField txt3;
	public JTable tabla;
	public JCheckBox chckbx1;
	public JCheckBox chckbx2;
	public JButton btn1A;
	public JButton btn1B;
	public JButton btnImprimir;
	public JButton btnVolver;
	public JComboBox<String> comboBox1;
	public JComboBox<String> comboBox2;
	
	public Listado(String nombreVentana) {
		
		super(nombreVentana);
		listado = new JPanel();
		setContentPane(listado);
		SpringLayout contenedor = new SpringLayout();
		listado.setLayout(contenedor);
		
		lblComboBox1 = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblComboBox1, 15, SpringLayout.NORTH, listado);
		contenedor.putConstraint(SpringLayout.WEST, lblComboBox1, 15, SpringLayout.WEST, listado);
		lblComboBox1.setVisible(false);
		listado.add(lblComboBox1);
		
		comboBox1 = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBox1, -2, SpringLayout.NORTH, lblComboBox1);
		contenedor.putConstraint(SpringLayout.WEST, comboBox1, 5, SpringLayout.EAST, lblComboBox1);
		comboBox1.setVisible(false);
		listado.add(comboBox1);
		
		lblComboBox2 = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblComboBox2, 15, SpringLayout.NORTH, listado);
		contenedor.putConstraint(SpringLayout.WEST, lblComboBox2, 25, SpringLayout.EAST, comboBox1);
		lblComboBox2.setVisible(false);
		listado.add(lblComboBox2);
		
		comboBox2 = new JComboBox<String>();
		contenedor.putConstraint(SpringLayout.NORTH, comboBox2, -2, SpringLayout.NORTH, lblComboBox2);
		contenedor.putConstraint(SpringLayout.WEST, comboBox2, 5, SpringLayout.EAST, lblComboBox2);
		comboBox2.setVisible(false);
		listado.add(comboBox2);
		
		lblTxt1 = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblTxt1, 17, SpringLayout.NORTH, listado);
		contenedor.putConstraint(SpringLayout.WEST, lblTxt1, 25, SpringLayout.EAST, comboBox2);
		lblTxt1.setVisible(false);
		listado.add(lblTxt1);
		
		txt1 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txt1, 15, SpringLayout.NORTH, listado);
		contenedor.putConstraint(SpringLayout.WEST, txt1, 5, SpringLayout.EAST, lblTxt1);
		txt1.setColumns(10);
		txt1.setEditable(false);
		txt1.setVisible(false);
		listado.add(txt1);
		
		lblTxt1Der = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblTxt1Der, 17, SpringLayout.NORTH, listado);
		contenedor.putConstraint(SpringLayout.WEST, lblTxt1Der, 5, SpringLayout.EAST, txt1);
		lblTxt1Der.setVisible(false);
		listado.add(lblTxt1Der);
		
		lblTxt2 = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblTxt2, 17, SpringLayout.NORTH, listado);
		contenedor.putConstraint(SpringLayout.WEST, lblTxt2, 25, SpringLayout.EAST, lblTxt1Der);
		lblTxt2.setVisible(false);
		listado.add(lblTxt2);
		
		txt2 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txt2, 15, SpringLayout.NORTH, listado);
		contenedor.putConstraint(SpringLayout.WEST, txt2, 5, SpringLayout.EAST, lblTxt2);
		txt2.setColumns(10);
		txt2.setEditable(false);
		txt2.setVisible(false);
		listado.add(txt2);
	
		lblHorario = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblHorario, 48, SpringLayout.NORTH, listado);
		contenedor.putConstraint(SpringLayout.WEST, lblHorario, 20, SpringLayout.WEST, listado);
		contenedor.putConstraint(SpringLayout.SOUTH, lblHorario, 25, SpringLayout.NORTH, lblHorario);
		lblHorario.setVisible(false);
		listado.add(lblHorario);
		
		lblLunes = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblLunes, 0, SpringLayout.SOUTH, lblHorario);
		contenedor.putConstraint(SpringLayout.EAST, lblLunes, 0, SpringLayout.EAST, lblHorario);
		contenedor.putConstraint(SpringLayout.SOUTH, lblLunes, 24, SpringLayout.NORTH, lblLunes);
		lblLunes.setHorizontalAlignment(SwingConstants.LEFT);
		lblLunes.setVisible(false);
		listado.add(lblLunes);
		
		lblMartes = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblMartes, 0, SpringLayout.SOUTH, lblLunes);
		contenedor.putConstraint(SpringLayout.EAST, lblMartes, 0, SpringLayout.EAST, lblHorario);
		contenedor.putConstraint(SpringLayout.SOUTH, lblMartes, 24, SpringLayout.NORTH, lblMartes);
		lblMartes.setHorizontalAlignment(SwingConstants.LEFT);
		lblMartes.setVisible(false);
		listado.add(lblMartes);
		
		lblMiercoles = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblMiercoles, 0, SpringLayout.SOUTH, lblMartes);
		contenedor.putConstraint(SpringLayout.EAST, lblMiercoles, 0, SpringLayout.EAST, lblHorario);
		contenedor.putConstraint(SpringLayout.SOUTH, lblMiercoles, 24, SpringLayout.NORTH, lblMiercoles);
		lblMiercoles.setHorizontalAlignment(SwingConstants.LEFT);
		lblMiercoles.setVisible(false);
		listado.add(lblMiercoles);
		
		lblJueves = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblJueves, 0, SpringLayout.SOUTH, lblMiercoles);
		contenedor.putConstraint(SpringLayout.EAST, lblJueves, 0, SpringLayout.EAST, lblHorario);
		contenedor.putConstraint(SpringLayout.SOUTH, lblJueves, 24, SpringLayout.NORTH, lblJueves);
		lblJueves.setHorizontalAlignment(SwingConstants.LEFT);
		lblJueves.setVisible(false);
		listado.add(lblJueves);
		
		lblViernes = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblViernes, 0, SpringLayout.SOUTH, lblJueves);
		contenedor.putConstraint(SpringLayout.EAST, lblViernes, 0, SpringLayout.EAST, lblHorario);
		contenedor.putConstraint(SpringLayout.SOUTH, lblViernes, 24, SpringLayout.NORTH, lblViernes);
		lblViernes.setHorizontalAlignment(SwingConstants.LEFT);
		lblViernes.setVisible(false);
		listado.add(lblViernes);
		
		lblSabado = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblSabado, 0, SpringLayout.SOUTH, lblViernes);
		contenedor.putConstraint(SpringLayout.EAST, lblSabado, 0, SpringLayout.EAST, lblHorario);
		contenedor.putConstraint(SpringLayout.SOUTH, lblSabado, 24, SpringLayout.NORTH, lblSabado);
		lblSabado.setHorizontalAlignment(SwingConstants.LEFT);
		lblSabado.setVisible(false);
		listado.add(lblSabado);
		
		scrollTabla = new JScrollPane();
		contenedor.putConstraint(SpringLayout.NORTH, scrollTabla, 50, SpringLayout.NORTH, listado);
		contenedor.putConstraint(SpringLayout.WEST, scrollTabla, 5, SpringLayout.EAST, lblLunes);
		contenedor.putConstraint(SpringLayout.SOUTH, scrollTabla, -20, SpringLayout.SOUTH, listado);
		contenedor.putConstraint(SpringLayout.EAST, scrollTabla, -120, SpringLayout.EAST, listado);
		listado.add(scrollTabla);
		tabla = new JTable();
		scrollTabla.setViewportView(tabla);
		
		btnImprimir = new JButton("Imprimir");
		contenedor.putConstraint(SpringLayout.NORTH, btnImprimir, 50, SpringLayout.NORTH, listado);
		contenedor.putConstraint(SpringLayout.EAST, btnImprimir, -20, SpringLayout.EAST, listado);
		contenedor.putConstraint(SpringLayout.WEST, btnImprimir, -90, SpringLayout.EAST, btnImprimir);
		listado.add(btnImprimir);
		
		btn1A = new JButton("");
		contenedor.putConstraint(SpringLayout.NORTH, btn1A, 25, SpringLayout.SOUTH, btnImprimir);
		contenedor.putConstraint(SpringLayout.EAST, btn1A, 0, SpringLayout.EAST, btnImprimir);
		contenedor.putConstraint(SpringLayout.WEST, btn1A, -90, SpringLayout.EAST, btn1A);
		btn1A.setVisible(false);
		listado.add(btn1A);
		
		chckbx1 = new JCheckBox();
		contenedor.putConstraint(SpringLayout.NORTH, chckbx1, 25, SpringLayout.SOUTH, btn1A);
		contenedor.putConstraint(SpringLayout.EAST, chckbx1, 0, SpringLayout.EAST, btn1A);
		contenedor.putConstraint(SpringLayout.WEST, chckbx1, -90, SpringLayout.EAST, chckbx1);
		chckbx1.setSelected(true);
		chckbx1.setVisible(false);
		listado.add(chckbx1);
		
		chckbx2 = new JCheckBox();
		contenedor.putConstraint(SpringLayout.NORTH, chckbx2, 25, SpringLayout.SOUTH, chckbx1);
		contenedor.putConstraint(SpringLayout.EAST, chckbx2, 0, SpringLayout.EAST, chckbx1);
		contenedor.putConstraint(SpringLayout.WEST, chckbx2, -90, SpringLayout.EAST, chckbx2);
		chckbx2.setSelected(true);
		chckbx2.setVisible(false);
		listado.add(chckbx2);
		
		lblChckbx2 = new JLabel();
		contenedor.putConstraint(SpringLayout.NORTH, lblChckbx2, 25, SpringLayout.SOUTH, chckbx1);
		contenedor.putConstraint(SpringLayout.EAST, lblChckbx2, 0, SpringLayout.EAST, chckbx1);
		contenedor.putConstraint(SpringLayout.WEST, lblChckbx2, -90, SpringLayout.EAST, chckbx2);
		lblChckbx2.setVisible(false);
		listado.add(lblChckbx2);
		
		txt3 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txt3, 25, SpringLayout.SOUTH, chckbx2);
		contenedor.putConstraint(SpringLayout.WEST, txt3, 0, SpringLayout.WEST, chckbx2);
		txt3.setColumns(9);
		txt3.setEditable(false);
		txt3.setVisible(false);
		listado.add(txt3);
		
		btnVolver = new JButton("Volver");
		contenedor.putConstraint(SpringLayout.SOUTH, btnVolver, -20, SpringLayout.SOUTH, listado);
		contenedor.putConstraint(SpringLayout.EAST, btnVolver, -20, SpringLayout.EAST, listado);
		contenedor.putConstraint(SpringLayout.WEST, btnVolver, -90, SpringLayout.EAST, btnVolver);
		listado.add(btnVolver);
		
		btn1B = new JButton("");
		contenedor.putConstraint(SpringLayout.SOUTH, btn1B, -25, SpringLayout.NORTH, btnVolver);
		contenedor.putConstraint(SpringLayout.EAST, btn1B, 0, SpringLayout.EAST, btnVolver);
		contenedor.putConstraint(SpringLayout.WEST, btn1B, -90, SpringLayout.EAST, btn1B);
		btn1B.setVisible(false);
		listado.add(btn1B);	
	}
}