package modelo;

import javax.swing.table.DefaultTableModel;
import dao.AlumnoDAO;
import dao.AlumnoMySQL;
import dao.GrupoFamiliarDAO;
import dao.GrupoFamiliarMySQL;

public class DtosGrupoFamiliar {

	private GrupoFamiliarDAO grupoFamiliarDAO;
	private static GrupoFamiliar familia;
	private GrupoFamiliar familias[];
	private String msgError;
	private Alumno listaAlumnos[];
	private Alumno intAgregados[] = new Alumno[0];
	private Alumno intEliminados[] = new Alumno[0];
	private String listaAcciones[] = new String[0];
	
	public DefaultTableModel getTablaFamilias(boolean deuda, String busqueda, int cantIntegrantes) {
		
		grupoFamiliarDAO = new GrupoFamiliarMySQL();
		familias = grupoFamiliarDAO.getListado(deuda, busqueda, cantIntegrantes);
		String titulo[] = new String[] {"Nombre", "Integrantes"};
		String cuerpo[][] = new String[familias.length][3];

		for(int i = 0 ; i < familias.length ; i++) {
			
			cuerpo[i][0] = familias[i].getNombre();
			cuerpo[i][1] = "";

			for(int e = 0; e < familias[i].getIntegrantes().length ; e++) {
			
				cuerpo[i][1] += familias[i].getIntegrantes()[e].getNombre() + " " + familias[i].getIntegrantes()[e].getApellido();
				
				if(e < familias[i].getIntegrantes().length - 1)
					cuerpo[i][1] += ", ";
			}
		}
		DefaultTableModel tablaFamilia = new DefaultTableModel(cuerpo, titulo);
		return tablaFamilia;
	}
	
	public void setFamiliaSeleccionada(int pos) {
		
		familia = familias[pos];
	}

	public DefaultTableModel getTablaFamilia() {

		int tamaño = familia.getIntegrantes().length + intAgregados.length;

		if(listaAcciones.length != tamaño) {
		
			String temp[] = listaAcciones;
			listaAcciones = new String[tamaño];
			System.arraycopy(temp, 0, listaAcciones, 0, temp.length);
		}
		String titulo[] = {"Leg.", "Apellido, nombre", "Curso", ""};
		Object cuerpo[][] = new Object[tamaño][4];	
		
		for(int i = 0; i < familia.getIntegrantes().length; i++) {

			listaAcciones[i] = listaAcciones[i] == null? "": listaAcciones[i];
			cuerpo[i][0] = familia.getIntegrantes()[i].getLegajo();
			cuerpo[i][1] = familia.getIntegrantes()[i].getApellido() + " " +
							familia.getIntegrantes()[i].getNombre();
			cuerpo[i][2] = familia.getIntegrantes()[i].getCurso().getAño() + " " +
							familia.getIntegrantes()[i].getCurso().getNivel();
			cuerpo[i][3] = listaAcciones[i];
		}
		
		if(intAgregados.length > 0) {
			
			int e = 0;
			
			for(int i = familia.getIntegrantes().length; i < cuerpo.length; i++) {
				
				cuerpo[i][0] = intAgregados[e].getLegajo();
				cuerpo[i][1] = intAgregados[e].getApellido() + " " +
								intAgregados[e].getNombre();
				cuerpo[i][2] = intAgregados[e].getCurso().getAño() + " " +
								intAgregados[e].getCurso().getNivel();
				cuerpo[i][3] = "A";
				e++;
			}
		}	
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo);
		return tablaModelo;
	}
	
	public DefaultTableModel getTablaAlumnos(String valor, boolean estado) {
		
		AlumnoDAO alumnosDAO = new AlumnoMySQL();
		listaAlumnos = alumnosDAO.getListado(estado, familia.getId(), valor);
		String titulo[] = {"Leg.", "Apellido, nombre", "Dirección", "Curso"};
		String alumnos[][] = null;
		
		if(listaAlumnos != null) {		

			alumnos = new String[listaAlumnos.length][4];
			
			for(int i = 0; i < alumnos.length; i++) {
				
				alumnos[i][0] = listaAlumnos[i].getLegajo() + "";
				alumnos[i][1] = listaAlumnos[i].getApellido() + " " +
								listaAlumnos[i].getNombre();
				alumnos[i][2] = listaAlumnos[i].getDireccion(); 
				alumnos[i][3] = listaAlumnos[i].getCurso().getNivel() + " " +
								listaAlumnos[i].getCurso().getAño();
			}
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(alumnos, titulo);
		return tablaModelo;
	}
	
	public boolean isRepetido(int pos) {
		
		for(int i = 0; i < familia.getIntegrantes().length; i++) {
			
			if(familia.getIntegrantes()[i].getLegajo() == listaAlumnos[pos].getLegajo())
				return true;
		}
		return false;
	}

	public void setAgregarElementos(int pos) {

		if(intAgregados.length == 0) {
			
			intAgregados = new Alumno[] {listaAlumnos[pos]};
		} else {

			Alumno temp[] = intAgregados;
			intAgregados = new Alumno[temp.length + 1];
			System.arraycopy(temp, 0, intAgregados, 0, temp.length);
			intAgregados[intAgregados.length - 1] = listaAlumnos[pos];
		}
	}

	public void setEliminarElementos(int pos) {

		if(intEliminados.length == 0) {

			intEliminados = new Alumno[] {familia.getIntegrantes()[pos]};
		} else if(intEliminados.length > 0) {
		
			Alumno temp[] = new Alumno[intEliminados.length + 1];
			System.arraycopy(intEliminados, 0, temp, 0, intEliminados.length);
			temp[intEliminados.length - 1] = familia.getIntegrantes()[pos];
			intEliminados = temp;
		}			
		listaAcciones[pos] = "E";
	}
	
	public boolean guardarCambios() {
		
		grupoFamiliarDAO = new GrupoFamiliarMySQL();
		Alumno temp[] = new Alumno[familia.getIntegrantes().length + intAgregados.length - intEliminados.length];
		int e = 0;

		for(int i = 0; i < familia.getIntegrantes().length; i++) {
	
			if(!listaAcciones[i].equals("E")) {
				
				temp[e] = familia.getIntegrantes()[i];
				e++;
			}
		}
		System.arraycopy(intAgregados, 0, temp, e, intAgregados.length);
		familia.setIntegrantes(temp);
	
		if(!grupoFamiliarDAO.update(familia)) {
			
			msgError = "Error al guardar las modificaciones.";
			return false;
		}
		intAgregados = null;	
		intEliminados = null;
		familia = null;
		return true;
	}

	public String getNombreFamilia() {
		
		return familia.getNombre();
	}
	
	public void setNombreFamilia(String nombre) {
		
		if(familia == null)
			familia = new GrupoFamiliar();
		familia.setNombre(nombre);
	}
	
	public String getDescuento() {
		
		return familia.getDescuento() + "";
	}
	
	public void setDescuento(String descuento) {
		
		familia.setDescuento(Integer.parseInt(descuento));
	}
	
	public String getMsgError() {
		
		return msgError;
	}
}