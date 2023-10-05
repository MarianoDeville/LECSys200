package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import modelo.DtosConfiguracion;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class IngresoUsuario extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPanel = new JPanel();
	public JButton btnOk;
	public JButton btnCancelar;
	public JLabel txtError;
	public JTextField txtUsuario;
	public JPasswordField txtPassword;

	public IngresoUsuario() {
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DtosConfiguracion.getDirectorio() + "\\Imagenes\\LEC.png"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.TOOLKIT_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("LECSys - Login");
		setBounds(350, 200, 419, 220);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(74, 63, 58, 14);
		contentPanel.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(150, 60, 150, 20);
		contentPanel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblContraseña = new JLabel("Password:");
		lblContraseña.setBounds(74, 91, 76, 14);
		contentPanel.add(lblContraseña);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(150, 88, 150, 20);
		contentPanel.add(txtPassword);
		
		txtError = new JLabel("");
		txtError.setHorizontalAlignment(SwingConstants.CENTER);
		txtError.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtError.setForeground(Color.RED);
		txtError.setBounds(10, 24, 383, 25);
		contentPanel.add(txtError);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnOk = new JButton("Aceptar");
		btnOk.setActionCommand("Aceptar");
		buttonPane.add(btnOk);
		getRootPane().setDefaultButton(btnOk);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand("Cancelar");
		buttonPane.add(btnCancelar);
	}
}