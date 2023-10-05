package control;

import javax.swing.JOptionPane;
import modelo.DtosConfiguracion;

public class CtrlArgumentos {

	
	public boolean procesar(String argumentos[]) {
		
		if(argumentos.length == 0)
			return false;

		if(argumentos[0].equals("-setup")) {
			
			if(argumentos.length < 4) {
				
				JOptionPane.showMessageDialog(null, "Faltan argumentos. \nEj. LECSys -setup localhost nombreUsuarioBD contraseñaBD");
				return true;
			}
			DtosConfiguracion dtosConfig = new DtosConfiguracion();
			dtosConfig.setServidor(argumentos[1]);
			dtosConfig.setUsuarioBD(argumentos[2]);
			dtosConfig.setPassBD(argumentos[3]);
			dtosConfig.setDirectorio("C:\\LECSys 1.0\\");
			dtosConfig.setConfiguracion();
		}else {
			
			JOptionPane.showMessageDialog(null, "Argumento no reconocido.\nEj. LECSys -setup localhost nombreUsuarioBD contraseñaBD");
		}
		return true;
	}
}
