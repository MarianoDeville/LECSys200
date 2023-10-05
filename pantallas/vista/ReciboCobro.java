package vista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import modelo.DtosConfiguracion;

public class ReciboCobro extends VentanaModelo {

	private static final long serialVersionUID = 1L;
	public JPanel panel;
	public JLabel txtNroRecibo;
	public JLabel txtFecha;
	public JLabel txtNombre;
	public JLabel txtConcepto;
	public JLabel txtConcepto_1;
	public JLabel txtMontoTotal;
	public JLabel txtTítulo_1;
	public JLabel txtTítulo_2;
	public JButton btnVolver;
	public JButton btnImprimir;

	public ReciboCobro(String nombreVentana) {
		
		super(nombreVentana);
		setBounds(100, 100, 590, 504);
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		setContentPane(panel);
		panel.setLayout(null);
		
		JLabel lblTitulo = new JLabel("RECIBO");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTitulo.setBounds(340, 15, 205, 25);
		panel.add(lblTitulo);
		
		txtFecha = new JLabel();
		txtFecha.setHorizontalAlignment(SwingConstants.CENTER);
		txtFecha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtFecha.setBounds(340, 70, 205, 25);
		panel.add(txtFecha);
		
		txtNombre = new JLabel();
		txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtNombre.setBounds(25, 250, 560, 25);
		panel.add(txtNombre);
		
		txtMontoTotal = new JLabel();
		txtMontoTotal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtMontoTotal.setBounds(25, 275, 560, 25);
		panel.add(txtMontoTotal);
		
		txtConcepto = new JLabel();
		txtConcepto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtConcepto.setBounds(25, 300, 560, 25);
		panel.add(txtConcepto);
		
		txtConcepto_1 = new JLabel();
		txtConcepto_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtConcepto_1.setBounds(25, 325, 560, 25);
		panel.add(txtConcepto_1);
		
		txtNroRecibo = new JLabel();
		txtNroRecibo.setHorizontalAlignment(SwingConstants.CENTER);
		txtNroRecibo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNroRecibo.setBounds(340, 40, 205, 25);
		panel.add(txtNroRecibo);
		
		txtTítulo_1 = new JLabel();
		txtTítulo_1.setHorizontalAlignment(SwingConstants.CENTER);
		txtTítulo_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtTítulo_1.setBounds(25, 15, 250, 25);
		panel.add(txtTítulo_1);
		
		txtTítulo_2 = new JLabel();
		txtTítulo_2.setHorizontalAlignment(SwingConstants.CENTER);
		txtTítulo_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtTítulo_2.setBounds(25, 40, 250, 25);
		panel.add(txtTítulo_2);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\LEC - Min.png"));
		btnNewButton.setBounds(40, 73, 150, 150);
		panel.add(btnNewButton);
		
		btnVolver = new JButton("Cerrar");
		btnVolver.setBounds(340, 417, 89, 23);
		panel.add(btnVolver);
		
		btnImprimir = new JButton("Imprimir");
		btnImprimir.setBounds(130, 417, 89, 23);
		panel.add(btnImprimir);
	}
}