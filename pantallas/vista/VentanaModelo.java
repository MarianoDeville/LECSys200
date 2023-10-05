package vista;

import javax.swing.JFrame;
import dao.OperadorSistema;
import modelo.DtosConfiguracion;
import java.awt.Dimension;
import java.awt.Toolkit;

public abstract class VentanaModelo extends JFrame {

	private static final long serialVersionUID = 1L;

	public VentanaModelo(String nombreVentana) {
		
		OperadorSistema identificación = new OperadorSistema();
		setTitle(nombreVentana + " - " + identificación.getNombreUsuario());
		setIconImage(Toolkit.getDefaultToolkit().getImage(DtosConfiguracion.getDirectorio() + "\\Imagenes\\LEC.png"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 10, 800, 600);
		setMinimumSize(new Dimension(640, 480));
	}
}