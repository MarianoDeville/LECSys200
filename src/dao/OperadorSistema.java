package dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import control.CtrlLogErrores;
import modelo.DtosActividad;

public class OperadorSistema extends Conexion {

	private static String nombreUsuario;
	private static String nivelAcceso;
	private static String idUsuarioActual;
	private static int cambiarPass;
	private static boolean permisos[][];
	private String nombre;
	private String pass;
	
	protected static boolean isAcceso(String clase) {

		boolean bandera;
		bandera = getPermiso(clase);
		if(!bandera) {
			
			DtosActividad dtosActividad = new DtosActividad();
			JOptionPane.showMessageDialog(null, "No posee permisos para realizar esta operación.");
			dtosActividad.registrarActividad("Intento de ingreso no autorizado a la base de datos. ", clase + "()", 0);
		}
		return bandera;
	}

	public static boolean isAcceso() {

		boolean bandera;
		String método = new String(Thread.currentThread().getStackTrace()[2].getMethodName());
		String clase[] = Thread.currentThread().getStackTrace()[2].getClassName().split("\\.");
		bandera = getPermiso(clase[1] + "." + método);

		if(!bandera) {
			
			DtosActividad dtosActividad = new DtosActividad();
			JOptionPane.showMessageDialog(null, "No posee permisos para realizar esta operación.");
			dtosActividad.registrarActividad("Intento de ingreso no autorizado. ", clase[1] + "." + método + "()", 0);
		}
		return bandera;
	}
	
	private static boolean getPermiso(String clase) {
		
		boolean bandera = false;
		switch (clase) {
									// Permisos para generales para el sistema.	///////////////////////////////////////////////
			case "ActividadDAO.setActividad":
			case "CambioPasswordDAO.checkContraseña":
			case "CambioPasswordDAO.guardarNuevaContraseña":
			case "EstadisticasDAO.isNuevoMes":
			case "EstadisticasDAO.getUltima":	
			case "GrupoFamiliarDAO.setActualizarDeuda":
			case "OperadorSistema.checkUsuario":
			case "PersonaMySQL.getListadoCumpleAños":
			case "UsuariosMySQL.updateTiempoPass":
			case "UsuariosMySQL.isNombreUsado":
				bandera = true;
				break;
									// Permisos para Administración.	///////////////////////////////////////////////
			case "CtrlAdministracion.iniciar":
			case "AdministracionDAO.getTablaCobros":
			case "ComprasDAO.getOrdenesCompra":
			case "ComprasDAO.getListadoAños":
			case "ComprasDAO.getMontoCompra":
			case "ComprasDAO.getDetalleCompra":
			case "ComprasDAO.getListadoCompras":
			case "ComprasDAO.getDetalleOrdenCompra":	
			case "CtrlPagos.iniciar":
			case "PagosDAO.getDeudaProveedores":
			case "PagosDAO.getListadoProveedores":
			case "PagosDAO.getPagosProveedor":
			case "PagosDAO.getDetallePagoProveedor":
			case "PagosDAO.getPagosEmpleado":
			case "PagosDAO.getHistorialPagos":
			case "EmpleadosMySQL.getEmpleado":
			case "EstadisticasDAO.getEstadisticasAnuales":
				bandera = checkLectura(1);
				break;

			case "AdministracionDAO.getUltimoRegistro":
				bandera = checkLectura(1) | permisos[2][1];
				break;
			
			case "AdministracionDAO.setCobro":
			case "PagosDAO.setPagoProveedor":
			case "PagosDAO.setPagoEmpleado":
				bandera = permisos[1][1] | permisos[2][1];
				break;
				
			case "AdministracionDAO.setActualizarFacturas":
				bandera = permisos[1][2];
				break;
				
			case "ComprasDAO.setOrdenCompra":
			case "AlumnoMySQL.setInactivos":
				bandera = permisos[1][1] || permisos[1][2];
				break;
									// Permisos para Alumnos.	///////////////////////////////////////////////
			case "CtrlAlumnos.iniciar":
			case "AlumnoMySQL.isAsistenciaTomada":
			case "AlumnoMySQL.getAsistencias":
			case "AlumnoMySQL.getInfoAsistencia":
			case "AlumnoMySQL.getExamen":
				bandera = checkLectura(2);
				break;	

			case "AlumnoMySQL.getAlumnos":
			case "AlumnoMySQL.getListado":
			case "AlumnoMySQL.getIntegrantes":
			case "AlumnoMySQL.getGruposFamilias":
				bandera = checkLectura(1) | checkLectura(2);
				break;
				
			case "AlumnoMySQL.setGrupoFamiliar":
			case "AlumnoMySQL.setNuevo":
				bandera = permisos[1][1] | permisos[2][1];
				break;
				
			case "AlumnoMySQL.setEliminarIntegrante":
			case "AlumnoMySQL.update":
			case "AlumnoMySQL.setActualizarGrupo":
			case "AlumnoMySQL.setActualizarIdFamila":
				bandera = permisos[1][2] | permisos[2][2];
				break;
				
			case "AlumnoMySQL.setExamen":
			case "AlumnoMySQL.setAsistencia":
				bandera = permisos[2][1] | permisos[2][2];
				break;

			case "GrupoFamiliarDAO.getIntegrantes":
			case "GrupoFamiliarDAO.getGruposFamilias":
			case "GrupoFamiliarDAO.isNombreFamilia":
				bandera = checkLectura(1) | checkLectura(2);
				break;
				
			case "GrupoFamiliarDAO.setGrupoFamiliar":
				bandera = permisos[1][1] | permisos[2][1];
				break;
				
			case "GrupoFamiliarDAO.setEliminarIntegrante":
			case "GrupoFamiliarDAO.setActualizarGrupo":
				bandera = permisos[1][2] | permisos[2][2];
				break;
									// Permisos para Configuración.		///////////////////////////////////////////////		
			case "CtrlConfiguracion.iniciar":
				bandera = true;
				break;

			case "ActividadDAO.getActividad":
			case "CtrlABMLUsuarios.iniciar":
			case "CtrlActividad.iniciar":
			case "UsuariosMySQL.getListado":
				bandera = checkLectura(3);
				break;
			
			case "UsuariosMySQL.setUsuario":
				bandera = permisos[3][1];
				break;
				
			case "UsuariosMySQL.setActualizarUsuario":
				bandera = permisos[3][2];
				break;

			case "CtrlEditarEmail.iniciar":
			case "DiscoDAO.modificarValores":
				bandera = permisos[3][2];
				break;
									// Permisos para Cursos.	///////////////////////////////////////////////
			case "CtrlCursos.iniciar":
				bandera = checkLectura(4);
				break;				

			case "CursosMySQL.buscarDiasCurso":
			case "CursosMySQL.isExamenCargado":
			case "CursosMySQL.getListado":
			case "CursosMySQL.getTablaSemanal":
				bandera = checkLectura(2) | checkLectura(4);
				break;

			case "CursosMySQL.setCurso":
				bandera = permisos[4][1];
				break;
				
			case "CursosMySQL.update":
				bandera = permisos[4][2];
				break;
									// Permisos para Insumos.	///////////////////////////////////////////////
			case "CtrlInsumos.iniciar":
			case "InsumosDAO.getPedidoPresupuesto":
			case "InsumosDAO.getListadoOrdenesCompra":
			case "InsumosDAO.getHistoriaCompras":
			case "InsumosDAO.getOrdenCompra":
				bandera = checkLectura(5);
				break;
				
			case "InsumosDAO.getListadoInsumos":
			case "InsumosDAO.getListadoPedidos":
			case "InsumosDAO.getPedido":
			case "InsumosDAO.getTablaCotizaciones":
				bandera = checkLectura(1) | checkLectura(5);
				break;	
				
			case "InsumosDAO.setInsumo":
			case "InsumosDAO.setListadoPedido":
			case "InsumosDAO.setPedidoPresupuesto":
			case "InsumosDAO.getProveedoresPresupuesto":
			case "InsumosDAO.setPrecios":
				bandera = permisos[5][1];
				break;	
				
			case "InsumosDAO.setActualizarInsumo":
			case "InsumosDAO.setActualizarPedido":
			case "InsumosDAO.setActualizarStock":
				bandera = permisos[5][2];
				break;	
				
			case "InsumosDAO.setAgregarStock":
				bandera = permisos[5][1] | permisos[5][2];
				break;
									// Permisos para Empleados.	///////////////////////////////////////////////
			case "CtrlEmpleados.iniciar":
			case "CronogramaMySQL.getTablaSemanal":
				bandera = checkLectura(4) | checkLectura(6);
				break;
				
			case "EmpleadoMySQL.getEmpleadosActivos":
				bandera = checkLectura(5) | checkLectura(6);
				break;
				
			case "EmpleadoMySQL.getListado":
				bandera = checkLectura(1) | checkLectura(3) | checkLectura(4) | checkLectura(6);
				break;
				
			case "EmpleadoMySQL.setNuevo":
				bandera = permisos[6][1];
				break;
				
			case "EmpleadoMySQL.setActualizarEmpleado":
				bandera = permisos[6][2];
				break;
				
			case "CronogramaMySQL.setCronograma":
				bandera = permisos[6][1] | permisos[6][2];
				break;
									// Permisos para Proveedores.	///////////////////////////////////////////////
			case "CtrlABMLProveedores.iniciar":
			case "ProveedoresDAO.isCUITExistente":
			case "ProveedoresDAO.getListadoProveedores":
			case "ProveedoresDAO.getListadoContactos":
				bandera = checkLectura(7);
				break;
				
			case "ProveedoresDAO.getListadoEmail":
				bandera = checkLectura(5) | checkLectura(7);
				break;	
				
			case "ProveedoresDAO.setProveedorNuevo":
				bandera = permisos[7][1];
				break;	
				
			case "ProveedoresDAO.setActualizarProveedor":
				bandera = permisos[7][2];
				break;	
									// Permisos para Personas.	///////////////////////////////////////////////
			case "PersonasDAO.getDNIDuplicado":
				bandera = checkLectura(2) | checkLectura(6);
				break;

			case "PersonasDAO.registrarPersona":
				bandera = permisos[2][1] | permisos[6][1];
				break;
				
			case "PersonasDAO.actualizarPersona":
				bandera = permisos[2][2] | permisos[6][2];
				break;

			default:
				DtosActividad dtosActividad = new DtosActividad();
				dtosActividad.registrarActividad("No se encontró el permiso para la Clase.método.", clase + "()", 0);
				bandera = false;
				break;
		}
		return bandera;
	}
	
	private static boolean checkLectura(int pos) {

		return permisos[pos][0] | permisos[pos][1] | permisos[pos][2];
	}
	
	public boolean checkUsuario() {

		boolean bandera = false;
		
		try {

			this.conectar();
			Statement stm = this.conexion.createStatement();
			ResultSet rs = stm.executeQuery("SELECT COUNT(*) FROM usuarios WHERE estado = 1");

			if(rs.next()) {
				
				bandera = rs.getInt(1) == 0? true: false;
				nombreUsuario = "Sin usuario";
				idUsuarioActual = "0";
				nivelAcceso = "0.18.0";
				setMatrizPermisos();
			}
		
			if(!bandera) {
				
				rs = stm.executeQuery("SELECT idUsuario, nombre, nivelAcceso, cambioContraseña FROM `lecsys2.00`.usuarios "
									+ "WHERE(estado = 1 AND nombre = BINARY'" + nombre + "' AND contraseña = SHA('" + pass + "'))");
	
				if(rs.next()) {
					
					idUsuarioActual = rs.getString(1);
					nombreUsuario = rs.getString(2);
					nivelAcceso = rs.getString(3);
					cambiarPass = rs.getInt(4);
					setMatrizPermisos();
					bandera = true;
				}
			}
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError(e.getMessage());
		}finally {
			
			this.cerrar();
		}
		return bandera;
	}
	
	private void setMatrizPermisos() {
		
		permisos = new boolean[8][3];
		String valores[] = null;

		try {
			
			valores = nivelAcceso.split("\\.");
			for(int i = 0; i < 3; i++) {
				
				valores[i] = Integer.toBinaryString(Integer.parseInt(valores[i]));
				valores[i] = String.format("%08d", Integer.parseInt(valores[i]));
			}
		} catch (Exception e) {
			
			valores = new String[] {"00000000","00000000","00000000"};
		}
		
		for(int i = 0; i < 8; i++) {
			
			permisos[i][0] = valores[0].charAt(i) == '1'?true:false;
			permisos[i][1] = valores[1].charAt(i) == '1'?true:false;
			permisos[i][2] = valores[2].charAt(i) == '1'?true:false;
		}
	}

	public void setNombre(String nombre) {
		
		this.nombre = nombre;
	}

	public void setPass(JPasswordField pass) {
		
		this.pass =  Arrays.toString(pass.getPassword());
	}
	
	public String getNombreUsuario() {
		
		return nombreUsuario;
	}
	
	public String getNivelAcceso() {
		
		return nivelAcceso;
	}
	
	public String getFichaEmpleado() {
		
		return idUsuarioActual;
	}
	
	public boolean isActualizarContraseña() {
		
		return (cambiarPass < 1 && !idUsuarioActual.equals("0"))? true: false;
	}
}