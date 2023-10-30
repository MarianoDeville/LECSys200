package vista;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

public class InterfaceBotones extends VentanaModelo {

	private static final long serialVersionUID = 1L;
	private JPanel panelGeneral;
	public JLabel lbl1A;
	public JLabel lbl1B;
	public JLabel lbl1C;
	public JLabel lbl1D;
	public JLabel lbl2A;
	public JLabel lbl2B;
	public JLabel lbl2C;
	public JLabel lbl2D;
	public JButton btn1A;
	public JButton btn1B;
	public JButton btn1C;
	public JButton btn1D;
	public JButton btn2A;
	public JButton btn2B;
	public JButton btn2C;
	public JButton btn2D;
	public JButton btnVolver;

	public InterfaceBotones(String nombreVentana, int x, int y) {
		
		super(nombreVentana);
		panelGeneral = new JPanel();
		setContentPane(panelGeneral);
		setLocation(x + 5, y + 5);
		SpringLayout contenedor = new SpringLayout();
		panelGeneral.setLayout(contenedor);
				
		lbl1A = new JLabel();
		lbl1A.setHorizontalAlignment(SwingConstants.CENTER);
		contenedor.putConstraint(SpringLayout.NORTH, lbl1A, 100, SpringLayout.NORTH, panelGeneral);
		contenedor.putConstraint(SpringLayout.WEST, lbl1A, 50, SpringLayout.WEST, panelGeneral);
		contenedor.putConstraint(SpringLayout.EAST, lbl1A, 116, SpringLayout.WEST, lbl1A);
		panelGeneral.add(lbl1A);
		
		btn1A = new JButton();
		contenedor.putConstraint(SpringLayout.NORTH, btn1A, 15, SpringLayout.NORTH, lbl1A);
		contenedor.putConstraint(SpringLayout.WEST, btn1A, 6, SpringLayout.WEST, lbl1A);
		contenedor.putConstraint(SpringLayout.SOUTH, btn1A, 94, SpringLayout.NORTH, btn1A);
		contenedor.putConstraint(SpringLayout.EAST, btn1A, 104, SpringLayout.WEST, btn1A);
		btn1A.setVisible(false);
		panelGeneral.add(btn1A);
		
		lbl1B = new JLabel();
		lbl1B.setHorizontalAlignment(SwingConstants.CENTER);
		contenedor.putConstraint(SpringLayout.NORTH, lbl1B, 100, SpringLayout.NORTH, panelGeneral);
		contenedor.putConstraint(SpringLayout.WEST, lbl1B, 75, SpringLayout.EAST, lbl1A);
		contenedor.putConstraint(SpringLayout.EAST, lbl1B, 116, SpringLayout.WEST, lbl1B);
		panelGeneral.add(lbl1B);
		
		btn1B = new JButton();
		contenedor.putConstraint(SpringLayout.NORTH, btn1B, 15, SpringLayout.NORTH, lbl1B);
		contenedor.putConstraint(SpringLayout.WEST, btn1B, 6, SpringLayout.WEST, lbl1B);
		contenedor.putConstraint(SpringLayout.SOUTH, btn1B, 94, SpringLayout.NORTH, btn1B);
		contenedor.putConstraint(SpringLayout.EAST, btn1B, 104, SpringLayout.WEST, btn1B);
		btn1B.setVisible(false);
		panelGeneral.add(btn1B);
		
		lbl1C = new JLabel();
		lbl1C.setHorizontalAlignment(SwingConstants.CENTER);
		contenedor.putConstraint(SpringLayout.NORTH, lbl1C, 100, SpringLayout.NORTH, panelGeneral);
		contenedor.putConstraint(SpringLayout.WEST, lbl1C, 75, SpringLayout.EAST, lbl1B);
		contenedor.putConstraint(SpringLayout.EAST, lbl1C, 116, SpringLayout.WEST, lbl1C);
		panelGeneral.add(lbl1C);
		
		btn1C = new JButton();
		contenedor.putConstraint(SpringLayout.NORTH, btn1C, 15, SpringLayout.NORTH, lbl1C);
		contenedor.putConstraint(SpringLayout.WEST, btn1C, 6, SpringLayout.WEST, lbl1C);
		contenedor.putConstraint(SpringLayout.SOUTH, btn1C, 94, SpringLayout.NORTH, btn1C);
		contenedor.putConstraint(SpringLayout.EAST, btn1C, 104, SpringLayout.WEST, btn1C);
		btn1C.setVisible(false);
		panelGeneral.add(btn1C);
		
		lbl1D = new JLabel();
		lbl1D.setHorizontalAlignment(SwingConstants.CENTER);
		contenedor.putConstraint(SpringLayout.NORTH, lbl1D, 100, SpringLayout.NORTH, panelGeneral);
		contenedor.putConstraint(SpringLayout.WEST, lbl1D, 75, SpringLayout.EAST, lbl1C);
		contenedor.putConstraint(SpringLayout.EAST, lbl1D, 116, SpringLayout.WEST, lbl1D);
		panelGeneral.add(lbl1D);
		
		btn1D = new JButton();
		contenedor.putConstraint(SpringLayout.NORTH, btn1D, 15, SpringLayout.NORTH, lbl1D);
		contenedor.putConstraint(SpringLayout.WEST, btn1D, 6, SpringLayout.WEST, lbl1D);
		contenedor.putConstraint(SpringLayout.SOUTH, btn1D, 94, SpringLayout.NORTH, btn1D);
		contenedor.putConstraint(SpringLayout.EAST, btn1D, 104, SpringLayout.WEST, btn1D);
		btn1D.setVisible(false);
		panelGeneral.add(btn1D);
		
		lbl2A = new JLabel();
		lbl2A.setHorizontalAlignment(SwingConstants.CENTER);
		contenedor.putConstraint(SpringLayout.NORTH, lbl2A, 100, SpringLayout.SOUTH, btn1A);
		contenedor.putConstraint(SpringLayout.WEST, lbl2A, 50, SpringLayout.WEST, panelGeneral);
		contenedor.putConstraint(SpringLayout.EAST, lbl2A, 116, SpringLayout.WEST, lbl2A);
		panelGeneral.add(lbl2A);
		
		btn2A = new JButton();
		contenedor.putConstraint(SpringLayout.NORTH, btn2A, 15, SpringLayout.NORTH, lbl2A);
		contenedor.putConstraint(SpringLayout.WEST, btn2A, 6, SpringLayout.WEST, lbl2A);
		contenedor.putConstraint(SpringLayout.SOUTH, btn2A, 94, SpringLayout.NORTH, btn2A);
		contenedor.putConstraint(SpringLayout.EAST, btn2A, 104, SpringLayout.WEST, btn2A);
		btn2A.setVisible(false);
		panelGeneral.add(btn2A);
		
		lbl2B = new JLabel();
		lbl2B.setHorizontalAlignment(SwingConstants.CENTER);
		contenedor.putConstraint(SpringLayout.NORTH, lbl2B, 100, SpringLayout.SOUTH, btn1B);
		contenedor.putConstraint(SpringLayout.WEST, lbl2B, 75, SpringLayout.EAST, lbl2A);
		contenedor.putConstraint(SpringLayout.EAST, lbl2B, 116, SpringLayout.WEST, lbl2B);
		panelGeneral.add(lbl2B);
		
		btn2B = new JButton();
		contenedor.putConstraint(SpringLayout.NORTH, btn2B, 15, SpringLayout.NORTH, lbl2B);
		contenedor.putConstraint(SpringLayout.WEST, btn2B, 6, SpringLayout.WEST, lbl2B);
		contenedor.putConstraint(SpringLayout.SOUTH, btn2B, 94, SpringLayout.NORTH, btn2B);
		contenedor.putConstraint(SpringLayout.EAST, btn2B, 104, SpringLayout.WEST, btn2B);
		btn2B.setVisible(false);
		panelGeneral.add(btn2B);
		
		lbl2C = new JLabel();
		lbl2C.setHorizontalAlignment(SwingConstants.CENTER);
		contenedor.putConstraint(SpringLayout.NORTH, lbl2C, 100, SpringLayout.SOUTH, btn1C);
		contenedor.putConstraint(SpringLayout.WEST, lbl2C, 75, SpringLayout.EAST, lbl2B);
		contenedor.putConstraint(SpringLayout.EAST, lbl2C, 116, SpringLayout.WEST, lbl2C);
		panelGeneral.add(lbl2C);
		
		btn2C = new JButton();
		contenedor.putConstraint(SpringLayout.NORTH, btn2C, 15, SpringLayout.NORTH, lbl2C);
		contenedor.putConstraint(SpringLayout.WEST, btn2C, 6, SpringLayout.WEST, lbl2C);
		contenedor.putConstraint(SpringLayout.SOUTH, btn2C, 94, SpringLayout.NORTH, btn2C);
		contenedor.putConstraint(SpringLayout.EAST, btn2C, 104, SpringLayout.WEST, btn2C);
		btn2C.setVisible(false);
		panelGeneral.add(btn2C);
		
		lbl2D = new JLabel();
		lbl2D.setHorizontalAlignment(SwingConstants.CENTER);
		contenedor.putConstraint(SpringLayout.NORTH, lbl2D, 100, SpringLayout.SOUTH, btn1D);
		contenedor.putConstraint(SpringLayout.WEST, lbl2D, 75, SpringLayout.EAST, lbl2C);
		contenedor.putConstraint(SpringLayout.EAST, lbl2D, 116, SpringLayout.WEST, lbl2D);
		panelGeneral.add(lbl2D);
		
		btn2D = new JButton();
		contenedor.putConstraint(SpringLayout.NORTH, btn2D, 15, SpringLayout.NORTH, lbl2D);
		contenedor.putConstraint(SpringLayout.WEST, btn2D, 6, SpringLayout.WEST, lbl2D);
		contenedor.putConstraint(SpringLayout.SOUTH, btn2D, 94, SpringLayout.NORTH, btn2D);
		contenedor.putConstraint(SpringLayout.EAST, btn2D, 104, SpringLayout.WEST, btn2D);
		btn2D.setVisible(false);
		panelGeneral.add(btn2D);
		
		btnVolver = new JButton("Volver");
		contenedor.putConstraint(SpringLayout.SOUTH, btnVolver, -15, SpringLayout.SOUTH, panelGeneral);
		contenedor.putConstraint(SpringLayout.EAST, btnVolver, -15, SpringLayout.EAST, panelGeneral);
		contenedor.putConstraint(SpringLayout.WEST, btnVolver, -90, SpringLayout.EAST, btnVolver);
		panelGeneral.add(btnVolver);
	}
}