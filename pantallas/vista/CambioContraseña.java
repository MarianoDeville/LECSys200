package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import modelo.DtosConfiguracion;

public class CambioContraseña extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPanel = new JPanel();
	public JButton btnOk;
	public JButton btnCancelar;
	public JLabel txtError;
	public JTextField txtUsuario;
	public JPasswordField txtPasswordOld;
	public JPasswordField txtPasswordNew;
	public JPasswordField txtRePasswordNew;

	public CambioContraseña() {

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DtosConfiguracion.getDirectorio() + "\\Imagenes\\LEC.png"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.TOOLKIT_MODAL);
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setTitle("LECSys - Cambio de contraseña");
		setBounds(350, 200, 420, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		txtError = new JLabel("");
		txtError.setHorizontalAlignment(SwingConstants.CENTER);
		txtError.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtError.setForeground(Color.RED);
		txtError.setBounds(10, 20, 380, 25);
		contentPanel.add(txtError);	
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(70, 50, 58, 20);
		contentPanel.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(150, 50, 150, 20);
		contentPanel.add(txtUsuario);
		txtUsuario.setEditable(false);
		txtUsuario.setColumns(10);
		
		JLabel lblPasswordOld = new JLabel("Pass actual:");
		lblPasswordOld.setBounds(70, 75, 76, 20);
		contentPanel.add(lblPasswordOld);
		
		txtPasswordOld = new JPasswordField();
		txtPasswordOld.setBounds(150, 75, 150, 20);
		contentPanel.add(txtPasswordOld);

		JLabel lblPasswordNew = new JLabel("Nuevo pass:");
		lblPasswordNew.setBounds(70, 100, 76, 20);
		contentPanel.add(lblPasswordNew);
		
		txtPasswordNew = new JPasswordField();
		txtPasswordNew.setBounds(150, 100, 150, 20);
		contentPanel.add(txtPasswordNew);
		
		JLabel lblRePasswordNew = new JLabel("Repetir pass:");
		lblRePasswordNew.setBounds(70, 125, 76, 20);
		contentPanel.add(lblRePasswordNew);
		
		txtRePasswordNew = new JPasswordField();
		txtRePasswordNew.setBounds(150, 125, 150, 20);
		contentPanel.add(txtRePasswordNew);
		
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