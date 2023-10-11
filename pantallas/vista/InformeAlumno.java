package vista;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import modelo.DtosConfiguracion;

public class InformeAlumno extends VentanaModelo {

	private static final long serialVersionUID = 1L;
	public JPanel panel;
	public JTextField txtLegajo;
	public JTextField txtNombre;
	public JTextField txtApellido;
	public JTextField txtCurso;
	public JTextField txtProfesor;
	public JTextField txtPresentismo;
	public JTextField txtFaltas;
	public JTextField txtTarde;
	public JTextField txtEscrito1;
	public JTextField txtEscrito2;
	public JTextField txtOral1;
	public JTextField txtOral2;
	public JTextField txtComportamiento1;
	public JTextField txtComportamiento2;
	public JTextField txtFinalEscrito;
	public JTextField txtFinalOral;
	public JTextField txtFinalComportamiento;
	public JButton btnImprimir;
	public JButton btnVolver;

	public InformeAlumno(String nombreVentana) {
		
		super(nombreVentana);
		panel = new JPanel();
		setContentPane(panel);
		SpringLayout contenedor = new SpringLayout();
		panel.setLayout(contenedor);
		setMinimumSize(new Dimension(500, 600));
		setBounds(10, 10, 400, 600);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\LEC - Min.png"));
		contenedor.putConstraint(SpringLayout.NORTH, btnNewButton, 25, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, btnNewButton, -20, SpringLayout.EAST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, btnNewButton, 150, SpringLayout.NORTH, btnNewButton);
		contenedor.putConstraint(SpringLayout.WEST, btnNewButton, -150, SpringLayout.EAST, btnNewButton);
		panel.add(btnNewButton);
		
		JLabel lblLegajo = new JLabel("Legajo:");
		contenedor.putConstraint(SpringLayout.NORTH, lblLegajo, 25, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblLegajo, 20, SpringLayout.WEST, panel);
		panel.add(lblLegajo);
		
		txtLegajo = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtLegajo, -2, SpringLayout.NORTH, lblLegajo);
		contenedor.putConstraint(SpringLayout.WEST, txtLegajo, 20, SpringLayout.EAST, lblLegajo);
		panel.add(txtLegajo);
		txtLegajo.setColumns(4);
				
		JLabel lblNombre = new JLabel("Nombres:");
		contenedor.putConstraint(SpringLayout.NORTH, lblNombre, 25, SpringLayout.SOUTH, lblLegajo);
		contenedor.putConstraint(SpringLayout.WEST, lblNombre, 0, SpringLayout.WEST, lblLegajo);
		panel.add(lblNombre);
		
		txtNombre = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtNombre, -2, SpringLayout.NORTH, lblNombre);
		contenedor.putConstraint(SpringLayout.WEST, txtNombre, 0, SpringLayout.WEST, txtLegajo);
		panel.add(txtNombre);
		txtNombre.setColumns(15);
				
		JLabel lblApellido = new JLabel("Apellidos:");
		contenedor.putConstraint(SpringLayout.NORTH, lblApellido, 25, SpringLayout.SOUTH, lblNombre);
		contenedor.putConstraint(SpringLayout.WEST, lblApellido, 0, SpringLayout.WEST, lblLegajo);
		panel.add(lblApellido);
		
		txtApellido = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtApellido, -2, SpringLayout.NORTH, lblApellido);
		contenedor.putConstraint(SpringLayout.WEST, txtApellido, 0, SpringLayout.WEST, txtLegajo);
		panel.add(txtApellido);
		txtApellido.setColumns(15);
		
		JLabel lblCurso = new JLabel("Curso:");
		contenedor.putConstraint(SpringLayout.NORTH, lblCurso, 25, SpringLayout.SOUTH, lblApellido);
		contenedor.putConstraint(SpringLayout.WEST, lblCurso, 0, SpringLayout.WEST, lblLegajo);
		panel.add(lblCurso);
		
		txtCurso = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtCurso, -2, SpringLayout.NORTH, lblCurso);
		contenedor.putConstraint(SpringLayout.WEST, txtCurso, 0, SpringLayout.WEST, txtLegajo);
		panel.add(txtCurso);
		txtCurso.setColumns(15);
		
		JLabel lblProfesor = new JLabel("Profesor:");
		contenedor.putConstraint(SpringLayout.NORTH, lblProfesor, 25, SpringLayout.SOUTH, lblCurso);
		contenedor.putConstraint(SpringLayout.WEST, lblProfesor, 0, SpringLayout.WEST, lblLegajo);
		panel.add(lblProfesor);
		
		txtProfesor = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtProfesor, -2, SpringLayout.NORTH, lblProfesor);
		contenedor.putConstraint(SpringLayout.WEST, txtProfesor, 0, SpringLayout.WEST, txtLegajo);
		panel.add(txtProfesor);
		txtProfesor.setColumns(15);
		
		JLabel lblAsistencia = new JLabel("Asistencia");
		contenedor.putConstraint(SpringLayout.NORTH, lblAsistencia, 30, SpringLayout.SOUTH, lblProfesor);
		contenedor.putConstraint(SpringLayout.WEST, lblAsistencia, 175, SpringLayout.WEST, panel);
		panel.add(lblAsistencia);
		
		JLabel lblPresentismo = new JLabel("Presentismo:");
		contenedor.putConstraint(SpringLayout.NORTH, lblPresentismo, 25, SpringLayout.SOUTH, lblAsistencia);
		contenedor.putConstraint(SpringLayout.WEST, lblPresentismo, 0, SpringLayout.WEST, lblLegajo);
		panel.add(lblPresentismo);
			
		txtPresentismo = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtPresentismo, -2, SpringLayout.NORTH, lblPresentismo);
		contenedor.putConstraint(SpringLayout.WEST, txtPresentismo, 5, SpringLayout.EAST, lblPresentismo);
		panel.add(txtPresentismo);
		txtPresentismo.setColumns(5);

		JLabel lblFaltas = new JLabel("Faltas:");
		contenedor.putConstraint(SpringLayout.NORTH, lblFaltas, 0, SpringLayout.NORTH, lblPresentismo);
		contenedor.putConstraint(SpringLayout.WEST, lblFaltas, 5, SpringLayout.EAST, txtPresentismo);
		panel.add(lblFaltas);
			
		txtFaltas = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtFaltas, -2, SpringLayout.NORTH, lblFaltas);
		contenedor.putConstraint(SpringLayout.WEST, txtFaltas, 5, SpringLayout.EAST, lblFaltas);
		panel.add(txtFaltas);
		txtFaltas.setColumns(5);
		
		JLabel lblTarde = new JLabel("Llegadas tarde:");
		contenedor.putConstraint(SpringLayout.NORTH, lblTarde, 0, SpringLayout.NORTH, lblFaltas);
		contenedor.putConstraint(SpringLayout.WEST, lblTarde, 5, SpringLayout.EAST, txtFaltas);
		panel.add(lblTarde);
			
		txtTarde = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtTarde, -2, SpringLayout.NORTH, lblTarde);
		contenedor.putConstraint(SpringLayout.WEST, txtTarde, 5, SpringLayout.EAST, lblTarde);
		panel.add(txtTarde);
		txtTarde.setColumns(5);
		
		JLabel lblNotas = new JLabel("Notas");
		contenedor.putConstraint(SpringLayout.NORTH, lblNotas, 30, SpringLayout.SOUTH, lblTarde);
		contenedor.putConstraint(SpringLayout.WEST, lblNotas, 0, SpringLayout.WEST, lblAsistencia);
		panel.add(lblNotas);

		JLabel lblEscrito = new JLabel("Escrito:");
		contenedor.putConstraint(SpringLayout.NORTH, lblEscrito, 25, SpringLayout.SOUTH, lblNotas);
		contenedor.putConstraint(SpringLayout.WEST, lblEscrito, 100, SpringLayout.WEST, panel);
		panel.add(lblEscrito);
		
		JLabel lblOral = new JLabel("Oral:");
		contenedor.putConstraint(SpringLayout.NORTH, lblOral, 0, SpringLayout.NORTH, lblEscrito);
		contenedor.putConstraint(SpringLayout.WEST, lblOral, 70, SpringLayout.WEST, lblEscrito);
		panel.add(lblOral);

		JLabel lblComportamiento = new JLabel("Comportamiento");
		contenedor.putConstraint(SpringLayout.NORTH, lblComportamiento, 0, SpringLayout.NORTH, lblOral);
		contenedor.putConstraint(SpringLayout.WEST, lblComportamiento, 70, SpringLayout.WEST, lblOral);
		panel.add(lblComportamiento);
		
		JLabel lblPeriodo1 = new JLabel("1º Trimestre:");
		contenedor.putConstraint(SpringLayout.NORTH, lblPeriodo1, 25, SpringLayout.SOUTH, lblEscrito);
		contenedor.putConstraint(SpringLayout.WEST, lblPeriodo1, 0, SpringLayout.WEST, lblPresentismo);
		panel.add(lblPeriodo1);

		txtEscrito1 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtEscrito1, -2, SpringLayout.NORTH, lblPeriodo1);
		contenedor.putConstraint(SpringLayout.WEST, txtEscrito1, 0, SpringLayout.WEST, lblEscrito);
		panel.add(txtEscrito1);
		txtEscrito1.setColumns(3);

		txtOral1 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtOral1, 0, SpringLayout.NORTH, txtEscrito1);
		contenedor.putConstraint(SpringLayout.WEST, txtOral1, 0, SpringLayout.WEST, lblOral);
		panel.add(txtOral1);
		txtOral1.setColumns(3);
	
		txtComportamiento1 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtComportamiento1, 0, SpringLayout.NORTH, txtOral1);
		contenedor.putConstraint(SpringLayout.WEST, txtComportamiento1, 0, SpringLayout.WEST, lblComportamiento);
		panel.add(txtComportamiento1);
		txtComportamiento1.setColumns(3);
		
		JLabel lblPeriodo2 = new JLabel("2º Trimestre:");
		contenedor.putConstraint(SpringLayout.NORTH, lblPeriodo2, 25, SpringLayout.SOUTH, lblPeriodo1);
		contenedor.putConstraint(SpringLayout.WEST, lblPeriodo2, 0, SpringLayout.WEST, lblPeriodo1);
		panel.add(lblPeriodo2);
	
		txtEscrito2 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtEscrito2, -2, SpringLayout.NORTH, lblPeriodo2);
		contenedor.putConstraint(SpringLayout.WEST, txtEscrito2, 0, SpringLayout.WEST, lblEscrito);
		panel.add(txtEscrito2);
		txtEscrito2.setColumns(3);

		txtOral2 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtOral2, 0, SpringLayout.NORTH, txtEscrito2);
		contenedor.putConstraint(SpringLayout.WEST, txtOral2, 0, SpringLayout.WEST, lblOral);
		panel.add(txtOral2);
		txtOral2.setColumns(3);
		
		txtComportamiento2 = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtComportamiento2, 0, SpringLayout.NORTH, txtOral2);
		contenedor.putConstraint(SpringLayout.WEST, txtComportamiento2, 0, SpringLayout.WEST, lblComportamiento);
		panel.add(txtComportamiento2);
		txtComportamiento2.setColumns(3);

		JLabel lblFinal = new JLabel("Final:");
		contenedor.putConstraint(SpringLayout.NORTH, lblFinal, 25, SpringLayout.SOUTH, lblPeriodo2);
		contenedor.putConstraint(SpringLayout.WEST, lblFinal, 0, SpringLayout.WEST, lblPeriodo2);
		panel.add(lblFinal);

		txtFinalEscrito = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtFinalEscrito, -2, SpringLayout.NORTH, lblFinal);
		contenedor.putConstraint(SpringLayout.WEST, txtFinalEscrito, 0, SpringLayout.WEST, lblEscrito);
		panel.add(txtFinalEscrito);
		txtFinalEscrito.setColumns(3);
		
		txtFinalOral = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtFinalOral, 0, SpringLayout.NORTH, txtFinalEscrito);
		contenedor.putConstraint(SpringLayout.WEST, txtFinalOral, 0, SpringLayout.WEST, lblOral);
		panel.add(txtFinalOral);
		txtFinalOral.setColumns(3);
		
		txtFinalComportamiento = new JTextField();
		contenedor.putConstraint(SpringLayout.NORTH, txtFinalComportamiento, 0, SpringLayout.NORTH, txtFinalOral);
		contenedor.putConstraint(SpringLayout.WEST, txtFinalComportamiento, 0, SpringLayout.WEST, lblComportamiento);
		panel.add(txtFinalComportamiento);
		txtFinalComportamiento.setColumns(3);

		btnVolver = new JButton("Volver");
		contenedor.putConstraint(SpringLayout.SOUTH, btnVolver, -10, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, btnVolver, -150, SpringLayout.EAST, panel);
		contenedor.putConstraint(SpringLayout.WEST, btnVolver, -90, SpringLayout.EAST, btnVolver);
		panel.add(btnVolver);
		
		btnImprimir = new JButton("Imprimir");
		contenedor.putConstraint(SpringLayout.SOUTH, btnImprimir, 0, SpringLayout.SOUTH, btnVolver);
		contenedor.putConstraint(SpringLayout.EAST, btnImprimir, -50, SpringLayout.WEST, btnVolver);
		contenedor.putConstraint(SpringLayout.WEST, btnImprimir, -90, SpringLayout.EAST, btnImprimir);
		panel.add(btnImprimir);
	}
}