package modelo;

import javax.swing.table.DefaultTableModel;
import dao.AlumnoDAO;
import dao.GrupoFamiliarDAO;
import dao.GrupoFamiliarMySQL;

public class DtosGrupoFamiliar {

	private static GrupoFamiliar familia;
	private GrupoFamiliar familias[];
	private String msgError;
	
	
/*	
	private static String listaIntegrantes[][];
	private static String listaAlumnos[][];
	private static String eliminarElementos[];
	private static String agregarElementos[][];
	private static String listaElementosAgregar[];
	private static String nombreFamilia;
	private static String idGrupoFamiliar;
	private static String integrantes;
	private static String estado;
	private static String email;
	private static String descuento;
	private String listaAcciones[];
	private int elementoSeleccionado;
*/
	
	public DefaultTableModel getTablaGrupoFamiliar(boolean est, String busqueda) {
		
		GrupoFamiliarDAO grupoFamiliarDAO = new GrupoFamiliarMySQL();
		String titulo[] = new String[] {"Nombre", "Integrantes"};
		
		familias = grupoFamiliarDAO.getListado("", "", est, busqueda);
		
		String cuerpo[][] = new String[familias.length][2];

		for(int i = 0 ; i < familias.length ; i++) {
			
			cuerpo[i][0] = familias[i].getNombre();
			cuerpo[i][1] = "";
			String integrantes[][] = grupoFamiliarDAO.getGrupoFamiliar(listaIntegrantes[i][0]);
			
			for(int e = 0; e < integrantes.length ; e++) {
			
				cuerpo[i][1] += integrantes[e][1] + " " + integrantes[e][2];
				
				if(e < integrantes.length - 1) {
					
					cuerpo[i][1] += ", ";
				}
			}
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				
				return column > 1? true: false;
			}
			
			public Class<?> getColumnClass(int column) {
				
				return column > 1? Boolean.class: String.class;
		    }
		};
		return tablaModelo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public DefaultTableModel getTablaGrupoFamiliar(boolean est, String busqueda, boolean sel) {
		
		GrupoFamiliarDAO grupoFamiliarDAO = new GrupoFamiliarMySQL();
		String titulo[] = null;
		
		if(sel)
			titulo = new String[] {"Nombre", "Integrantes", "Sel"};
		else
			titulo = new String[] {"Nombre", "Integrantes"};
		
		familias = grupoFamiliarDAO.getListado("", "", est, busqueda);
		
		Object cuerpo[][] = new Object[listaIntegrantes.length][3];

		for(int i = 0 ; i < listaIntegrantes.length ; i++) {
			
			cuerpo[i][0] = listaIntegrantes[i][1];
			cuerpo[i][1] = "";
			cuerpo[i][2] = false;
			String integrantes[][] = grupoFamiliarDAO.getGrupoFamiliar(listaIntegrantes[i][0]);
			
			for(int e = 0; e < integrantes.length ; e++) {
			
				cuerpo[i][1] += integrantes[e][1] + " " + integrantes[e][2];
				
				if(e < integrantes.length - 1) {
					
					cuerpo[i][1] += ", ";
				}
			}
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				
				return column > 1? true: false;
			}
			
			public Class<?> getColumnClass(int column) {
				
				return column > 1? Boolean.class: String.class;
		    }
		};
		return tablaModelo;
	}
	
	
	public boolean guardarCambios() {

		boolean bandera = true;
		GrupoFamiliarDAO grupoFamiliarDAO = new GrupoFamiliarDAO();
		AlumnosDAO alumnosDAO = new AlumnosDAO();
			
		if(agregarElementos.length > 0) {

			bandera = alumnosDAO.setActualizarIdFamila(idGrupoFamiliar, listaElementosAgregar, "1");
			
			if(bandera) {
				
				integrantes = (Integer.parseInt(integrantes) + listaElementosAgregar.length) + "";				
				bandera = grupoFamiliarDAO.update(Integer.parseInt(idGrupoFamiliar),  nombreFamilia, 
															  Integer.parseInt(integrantes),  Integer.parseInt(descuento), 
															  email,  estado);
			} else {
				
				msgError = "Error al guardar los elementos agregados.";
				return false;
			}

			for(int i = 0; i < agregarElementos.length; i++) {
				
				if(!grupoFamiliarDAO.eliminarIntegrante(agregarElementos[i][8]))
					bandera = false;
			}
		}

		if(eliminarElementos != null && bandera) {

			bandera = alumnosDAO.setActualizarIdFamila(null, eliminarElementos, "0");

			if(bandera) {
				
				int i = Integer.parseInt(integrantes); 
				
				if(i > eliminarElementos.length) {
					
					estado = "1";
				} else {
					
					estado = "0";
				}
				i -= eliminarElementos.length;
				integrantes = i + "";
				bandera = grupoFamiliarDAO.update(Integer.parseInt(idGrupoFamiliar), nombreFamilia, 
															  Integer.parseInt(integrantes), Integer.parseInt(descuento), 
															  email, estado);
			} else {
				
				msgError = "Error al guardar los elementos eliminados.";
				return false;
			}	
		}
		
		if(agregarElementos.length == 0 && eliminarElementos == null) {
			
			bandera = grupoFamiliarDAO.update(Integer.parseInt(idGrupoFamiliar), nombreFamilia, 
														  Integer.parseInt(integrantes),  Integer.parseInt(descuento), 
														  email, estado);
			
			if(!bandera) {
				msgError = "Error al guardar los elementos.";
				return false;
			}
		}
		eliminarElementos = null;	
		agregarElementos = null;
		return true;
	}

	public boolean isRepetido(Object idAlumno) {
	
		for(int i = 0; i < agregarElementos.length; i++) {
			
			if(agregarElementos[i][0].equals(idAlumno))
				return true;
		}
		return false;
	}
	
	public void setAgregarElementos() {

		String temp[][] = agregarElementos;
		String temp2[] = listaAcciones;
		listaAcciones = new String[temp2.length + 1];
		System.arraycopy(temp2, 0, listaAcciones, 0, temp2.length);
		listaAcciones[temp2.length] = "A";
		agregarElementos = new String [temp.length + 1][listaIntegrantes[0].length];
		System.arraycopy(temp, 0, agregarElementos, 0, temp.length);
		agregarElementos[temp.length][0] = listaAlumnos[elementoSeleccionado][0];
		agregarElementos[temp.length][1] = "";
		agregarElementos[temp.length][2] = listaAlumnos[elementoSeleccionado][1];
		agregarElementos[temp.length][7] = listaAlumnos[elementoSeleccionado][3];
		agregarElementos[temp.length][8] = listaAlumnos[elementoSeleccionado][4];
		listaElementosAgregar = new String[agregarElementos.length];
		
		for(int i = 0; i < agregarElementos.length; i++) {

			listaElementosAgregar[i] = agregarElementos[i][0];
		}
	}
	
	public void setEliminarElementos() {

		if(eliminarElementos == null) {

			eliminarElementos = new String[] {listaIntegrantes[elementoSeleccionado][0]};
		} else if(eliminarElementos.length > 0) {

			for(int i = 0; i < eliminarElementos.length; i++) {
			
				if(eliminarElementos[i].equals(listaIntegrantes[elementoSeleccionado][0]))
					return;
			}
			String temp[] = new String[eliminarElementos.length];
			System.arraycopy(eliminarElementos, 0, temp, 0, eliminarElementos.length);
			eliminarElementos = new String[temp.length + 1];
			System.arraycopy(temp, 0, eliminarElementos, 0, temp.length);
			eliminarElementos[eliminarElementos.length-1] = listaIntegrantes[elementoSeleccionado][0];
		}
		listaAcciones[elementoSeleccionado] = "E";
	}
	
	public DefaultTableModel getTablaAlumnos(String valor, boolean estado) {
		
		AlumnosDAO alumnosDAO = new AlumnosDAO();
		String titulo[] = {"Leg.", "Apellido, nombre", "Dirección", "Curso"};
		listaAlumnos = alumnosDAO.getListadoAlumnos(estado, idGrupoFamiliar, valor);
		DefaultTableModel tablaModelo = new DefaultTableModel(listaAlumnos, titulo);
		return tablaModelo;
	}
	
	public DefaultTableModel getTablaFamilia() {

		AlumnosDAO alumnosDAO = new AlumnosDAO();
		String titulo[] = {"Leg.", "Apellido, nombre", "Curso", ""};
		listaIntegrantes = alumnosDAO.getAlumnos("GF", idGrupoFamiliar, true, "idAlumno", "");
		String temp[][];

		if(agregarElementos == null)
			agregarElementos = new String[0][0];
		
		if(agregarElementos.length > 0) {
		
			temp = new String[listaIntegrantes.length + agregarElementos.length][listaIntegrantes[0].length];
			System.arraycopy(listaIntegrantes, 0, temp, 0, listaIntegrantes.length);
			System.arraycopy(agregarElementos, 0, temp, listaIntegrantes.length, agregarElementos.length);
		} else {
			
			temp = listaIntegrantes;
		}

		if(listaAcciones == null) {
			
			listaAcciones = new String[temp.length];
			
			for(int i = 0; i < listaAcciones.length; i++) {
				
				listaAcciones[i] = "";
			}
		}
		String cuerpo[][] = new String[temp.length][5];
	
		for(int i = 0; i < cuerpo.length ; i++) {
	
			cuerpo[i][0] = temp[i][0];
			cuerpo[i][1] = temp[i][2] + ", " + temp[i][1];
			cuerpo[i][2] = temp[i][7];
			cuerpo[i][3] = listaAcciones[i];
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo);
		return tablaModelo;
	}
	
	public void setInformacionGrupo() {
		
		idGrupoFamiliar = listaIntegrantes[elementoSeleccionado][0];
		nombreFamilia = listaIntegrantes[elementoSeleccionado][1];
		integrantes = listaIntegrantes[elementoSeleccionado][2];
		email = listaIntegrantes[elementoSeleccionado][6];
		descuento = listaIntegrantes[elementoSeleccionado][5];
	}

	public void setElementoSeleccionado(int elemento) {
		
		elementoSeleccionado = elemento;
	}

	public String getIdGrupoFamiliar() {
		
		return idGrupoFamiliar;
	}

	public String getNombreFamilia() {
		
		return nombreFamilia;
	}
	
	public void setNombreFamilia(String nombre) {
		
		nombreFamilia = nombre;
	}

	public String getIntegrantes() {
		
		return integrantes;
	}

	public String getEstado() {
		
		return estado;
	}

	public String getDescuento() {
		
		return descuento;
	}
	
	public void setDescuento(String descuento) {
		
		DtosGrupoFamiliar.descuento = descuento;
	}

	public String getEmail() {
		
		return email;
	}

	public String getMsgError() {
		
		return msgError;
	}
}