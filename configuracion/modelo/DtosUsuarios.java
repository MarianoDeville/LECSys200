package modelo;

import java.util.Arrays;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import dao.EmpleadoDAO;
import dao.EmpleadoMySQL;
import dao.OperadorSistema;
import dao.UsuariosDAO;
import dao.UsuariosMySQL;

public class DtosUsuarios {
	
	private UsuariosDAO usuariosDAO;
	private static Usuario usuario;
	private Usuario listaUsuarios[];
	private String dniEmpleados[];
	private String reContraseña;
	
	public DefaultTableModel getTablaUsuarios() {

		usuariosDAO = new UsuariosMySQL();
		listaUsuarios = usuariosDAO.getListado("");
		String titulo[] = {"Usuario", "Pertenece a", "Nivel acceso"};
		String cuerpo[][] = null;
		
		try {
			
			cuerpo = new String[listaUsuarios.length][3];
			
			for(int i = 0; i < listaUsuarios.length; i++) {
				
				cuerpo[i][0] = listaUsuarios[i].getUsrName();
				cuerpo[i][1] = listaUsuarios[i].getNombre();
				String conversión[] = intToBinario(listaUsuarios[i].getNivelAcceso());
				cuerpo[i][2] = conversión[0] + "." + conversión[1] + "." + conversión[2];
			}
		} catch (Exception e) {

			cuerpo = null;
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo);
		return tablaModelo;
	}
	
	public String [] getListaEmpleados() {
		
		EmpleadoDAO empleadosDAO = new EmpleadoMySQL();
		Empleado empleados[] = empleadosDAO.getListado("Todos", true, "");
		String respuesta[] = new String[empleados.length];
		dniEmpleados = new String[empleados.length];
		
		for(int i = 0 ; i < empleados.length ; i++) {
			
			dniEmpleados[i] = empleados[i].getDni() + "";
			respuesta[i] = empleados[i].getNombre() + " " + empleados[i].getApellido(); 
		}
		return respuesta;
	}
	
	public String checkInformacion(boolean checkUsuario) {

		usuariosDAO = new UsuariosMySQL();
		
		if(usuario.getUsrName().length() < 4)
			return "El nombre debe tener más de tres caracteres.";
		
		if(usuario.getUsrName().length() > 19)
			return "Supera el largo máximo para el nombre de usuario.";

		if(usuario.getContraseña().length() < 10 && checkUsuario)
			return "La contraseña debe tener más de cuatro caracteres.";
		
		if(usuario.getContraseña().length() < 10 && !checkUsuario && usuario.getContraseña().length() != 2)
			return "La contraseña debe tener más de cuatro caracteres.";
		
		if(!usuario.getContraseña().equals(reContraseña))
			return "Las contraseñas no coinciden.";
		
		if(usuario.getNivelAcceso().equals("0.0.0"))
			return "No se le ha asignado ningún permiso al usuario.";
		
		if(usuariosDAO.isNombreUsado(usuario.getNombre()) && checkUsuario)
			return "Nombre de usuario en uso.";
		
		return "";
	}
	
	public boolean setNuevoUsuario() {
		
		return usuariosDAO.setUsuario(usuario);
	}
	
	public boolean isPrimerUsuario() {

		OperadorSistema acceso = new OperadorSistema();
		return acceso.getFichaEmpleado().equals("0")? true:false;
	}
	
	public void clearInfo() {
		
		usuario = null;
	}
	
	public boolean isCuentaPropia() {
		
		OperadorSistema acceso = new OperadorSistema();
		return usuario.getUsrName().equals(acceso.getNombreUsuario());
	}
	
	public boolean updateUsuario() { 
		
		return usuariosDAO.update(usuario);
	} 
	
	public String getNombre() {
		
		return usuario.getUsrName();
	}
	
	public void setNombre(String nombre) {
		
		if(usuario == null)
			usuario = new Usuario();
		usuario.setUsrName(nombre);
	}
	
	public String getContraseña() {
		
		return usuario.getContraseña();
	}
	
	public void setContraseña(char [] contraseña) {

		usuario.setContraseña(Arrays.toString(contraseña));
	}
	
	public void setReContraseña(char [] rePass) {
		
		reContraseña = Arrays.toString(rePass);
	}
	
	public DefaultTableModel getTablaPermisos() {
		
		String valores[] = null;
		Object cuerpo[][] = null;
		String titulo[] = {"Sector", "Leer", "Crear", "Modificar"};
		String sectores[] = {"Todos", "Administración", "Alumnos", "Configuración", "Cursos", "Insumos", "Personal", "Proveedores"};
		cuerpo = new Object[sectores.length][4];

		if(usuario == null) {

			for(int i = 0; i < sectores.length; i++) {
				
				cuerpo[i][0] = sectores[i];
				cuerpo[i][1] = false;
				cuerpo[i][2] = false;
				cuerpo[i][3] = false;
			}
		} else {

			valores = intToBinario(usuario.getNivelAcceso());
			boolean todos[] = new boolean[] {true, true, true};
			for(int i = 0; i < sectores.length; i++) {
				
				cuerpo[i][0] = sectores[i];
				cuerpo[i][1] = valores[0].charAt(i) == '1'?true:false;
				cuerpo[i][2] = valores[1].charAt(i) == '1'?true:false;
				cuerpo[i][3] = valores[2].charAt(i) == '1'?true:false;
				
				if(i > 0) {
					
					todos[0] = todos[0]? (boolean)cuerpo[i][1]: false;
					todos[1] = todos[1]? (boolean)cuerpo[i][2]: false;
					todos[2] = todos[2]? (boolean)cuerpo[i][3]: false;
				}
			}
			cuerpo[0][1] = todos[0];
			cuerpo[0][2] = todos[1];
			cuerpo[0][3] = todos[2];
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo){

			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				
				return column > 0? true: false;
			}
			public Class<?> getColumnClass(int column) {

				return column > 0?Boolean.class: String.class;
		    }
		};
		return tablaModelo;
	}
	
	public void setPermisos(JTable tablaPermisos) {
		
		String valorLeido[] = new String[] {"0","0","0"};
		
		for(int i =  1; i < tablaPermisos.getRowCount() ; i++) {
			
			valorLeido[0] += (boolean)tablaPermisos.getValueAt(i, 1)?"1":"0";
			valorLeido[1] += (boolean)tablaPermisos.getValueAt(i, 2)?"1":"0";
			valorLeido[2] += (boolean)tablaPermisos.getValueAt(i, 3)?"1":"0";
		}
		int permisos[] = new int[3];
		permisos[0] = Integer.parseInt(valorLeido[0],2);
		permisos[1] = Integer.parseInt(valorLeido[1],2);
		permisos[2] = Integer.parseInt(valorLeido[2],2);
		usuario.setNivelAcceso(permisos[0] + "." + permisos[1] + "." + permisos[2]);
	}
	
	public void setUsuario(int pos) {
		
		if(usuario == null)
			usuario = new Usuario();
		usuario = listaUsuarios[pos];
	}
	
	public String getDNIEmpleado() {
		
		return usuario.getDni() + "";
	}
	
	public void setDNIEmpleado(int pos) {

		usuario.setDni(Integer.parseInt(dniEmpleados[pos]));
	}
	
	public void setEstado(int estado) {
		
		usuario.setEstado(estado);
	}

	private String[] intToBinario(String nivelAcceso) {
		
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
		return valores;
	}
}