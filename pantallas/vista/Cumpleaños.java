package vista;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import java.awt.Font;

public class Cumplea�os extends VentanaModelo{

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	public JButton btnCerrar;
	public JTable tablaCumplea�os;

	public Cumplea�os(String nombreVentana) {
		
		super(nombreVentana);
		setResizable(true);
		setBounds(10, 20, 480, 330);
		panel = new JPanel();
		panel.setBorder(null);
		setContentPane(panel);
		SpringLayout contenedor = new SpringLayout();
		panel.setLayout(contenedor);

		JLabel lblTitulo = new JLabel("En el d�a de hoy cumplen a�os:");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contenedor.putConstraint(SpringLayout.NORTH, lblTitulo, 15, SpringLayout.NORTH, panel);
		contenedor.putConstraint(SpringLayout.WEST, lblTitulo, 25, SpringLayout.WEST, panel);
		panel.add(lblTitulo);

		JScrollPane scrollTabla = new JScrollPane();
		contenedor.putConstraint(SpringLayout.NORTH, scrollTabla, 15, SpringLayout.NORTH, lblTitulo);
		contenedor.putConstraint(SpringLayout.WEST, scrollTabla, 15, SpringLayout.WEST, panel);
		contenedor.putConstraint(SpringLayout.SOUTH, scrollTabla, -60, SpringLayout.SOUTH, panel);
		contenedor.putConstraint(SpringLayout.EAST, scrollTabla, -25, SpringLayout.EAST, panel);
		panel.add(scrollTabla);
		tablaCumplea�os = new JTable();
		tablaCumplea�os.setEnabled(false);
		scrollTabla.setViewportView(tablaCumplea�os);
		
		btnCerrar = new JButton("Cerrar");
		contenedor.putConstraint(SpringLayout.NORTH, btnCerrar, 15, SpringLayout.SOUTH, scrollTabla);
		contenedor.putConstraint(SpringLayout.EAST, btnCerrar, 0, SpringLayout.EAST, scrollTabla);
		contenedor.putConstraint(SpringLayout.WEST, btnCerrar, -85, SpringLayout.EAST, btnCerrar);
		panel.add(btnCerrar);
	}
}