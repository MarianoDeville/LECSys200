import control.CtrlArgumentos;
import control.CtrlLogin;
import modelo.DtosConfiguracion;
import vista.IngresoUsuario;

/********************************************************************************/
/*				Sistema ERP ON-PREMISE para academias - LECSys					*/
/*------------------------------------------------------------------------------*/
/*		Revisión:				2.00											*/
/*		IDE:					Eclipse IDE Ver. 2023-09 (4.29.0)				*/
/*		Lenguaje:				Java SE-20										*/
/*		Versionado:				git - GitHub.com/MarianoDeville/ERP-LECSys		*/
/*		Base de Datos:			MySQL Workbench 8.00 CE							*/
/*		Plugin:					WindowBuilder 1.9.8								*/
/*								UMLet 14.3										*/
/*		Librerías:				mysql-connector-java-8.0.21.jar					*/
/*								javax.mail-1.6.2.jar							*/
/*								activation.jar									*/
/*		Estado:					BETA.											*/
/*		Autor:					Mariano Ariel Deville							*/
/*		Fecha creación:			24/09/2023										*/
/*		Última modificación:	04/12/2023										*/
/********************************************************************************/

public class LECSys {

	public static void main(String[] args) {

		CtrlArgumentos ctrlArgumentos = new CtrlArgumentos();

		if(ctrlArgumentos.procesar(args))
			System.exit(0);
		DtosConfiguracion config = new DtosConfiguracion();
		config.getConfig();
		IngresoUsuario interfaceUsuario = new IngresoUsuario();
		CtrlLogin ctrlIngreso = new CtrlLogin(interfaceUsuario);
		ctrlIngreso.iniciar();
	}
}