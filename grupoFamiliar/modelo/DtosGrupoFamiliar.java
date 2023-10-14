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
	private Alumno integrantesAgregados[];
	private Alumno integrantesEliminados[];
	private String listaAcciones[];
	
	public DefaultTableModel getTablaFamilias(boolean est, String busqueda) {
		
		grupoFamiliarDAO = new GrupoFamiliarMySQL();
		familias = grupoFamiliarDAO.getListado("", "", est, busqueda);
		String titulo[] = new String[] {"Nombre", "Integrantes"};
		String cuerpo[][] = new String[familias.length][2];

		for(int i = 0 ; i < familias.length ; i++) {
			
			cuerpo[i][0] = familias[i].getNombre();
			cuerpo[i][1] = "";

			for(int e = 0; e < familias.length ; e++) {
			
				cuerpo[i][1] += familias[e].getIntegrantes()[i].getNombre() + " " + familias[e].getIntegrantes()[i].getApellido();
				
				if(e < familias.length - 1)
					cuerpo[i][1] += ", ";
			}
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo);
		return tablaModelo;
	}
	
	public void setFamiliaSeleccionada(int pos) {
		
		familia = familias[pos];
	}

	public DefaultTableModel getTablaFamilia() {

		String titulo[] = {"Leg.", "Apellido, nombre", "Curso", ""};
		String listaIntegrantes[][] = new String[familia.getIntegrantes().length][4];
		
		for(int i = 0; i < listaIntegrantes.length; i++) {
			
			listaIntegrantes[i][0] = familia.getIntegrantes()[i].getLegajo() + "";
			listaIntegrantes[i][1] = familia.getIntegrantes()[i].getApellido() + " " +
									familia.getIntegrantes()[i].getNombre();
			listaIntegrantes[i][2] = familia.getIntegrantes()[i].getCurso().getAño() + " " +
									familia.getIntegrantes()[i].getCurso().getNivel() + " " + 
									familia.getIntegrantes()[i].getCurso().getNombreProfesor();
		}
		String temp[][];

		if(integrantesAgregados == null)
			integrantesAgregados = new Alumno[0];
		
		if(integrantesAgregados.length > 0) {
		
			temp = new String[listaIntegrantes.length + integrantesAgregados.length][listaIntegrantes[0].length];
			System.arraycopy(listaIntegrantes, 0, temp, 0, listaIntegrantes.length);
			System.arraycopy(integrantesAgregados, 0, temp, listaIntegrantes.length, integrantesAgregados.length);
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
	
	public DefaultTableModel getTablaAlumnos(String valor, boolean estado) {
		
		AlumnoDAO alumnosDAO = new AlumnoMySQL();
		listaAlumnos = alumnosDAO.getListado(estado, familia.getId(), valor);
		String titulo[] = {"Leg.", "Apellido, nombre", "Dirección", "Curso"};
		String alumnos[][] = new String[listaAlumnos.length][4];
		
		for(int i = 0; i < alumnos.length; i++) {
			
			alumnos[i][0] = familia.getIntegrantes()[i].getLegajo() + "";
			alumnos[i][1] = familia.getIntegrantes()[i].getApellido() + " " +
							familia.getIntegrantes()[i].getNombre();
			alumnos[i][2] = familia.getIntegrantes()[i].getDireccion(); 
			alumnos[i][3] = familia.getIntegrantes()[i].getCurso().getAño() + " " +
							familia.getIntegrantes()[i].getCurso().getNivel() + " " + 
							familia.getIntegrantes()[i].getCurso().getNombreProfesor();
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

		if(integrantesAgregados == null) {
			
			integrantesAgregados = new Alumno[] {listaAlumnos[pos]};
		} else {
			
			String temp2[] = listaAcciones;
			listaAcciones = new String[temp2.length + 1];
			System.arraycopy(temp2, 0, listaAcciones, 0, temp2.length);
			listaAcciones[temp2.length] = "A";
			System.arraycopy(temp2, 0, listaAcciones, 0, temp2.length);
			Alumno temp[] = new Alumno[integrantesAgregados.length + 1];
			System.arraycopy(integrantesAgregados, 0, temp, 0, integrantesAgregados.length);
			temp[integrantesAgregados.length - 1] = listaAlumnos[pos];
			integrantesAgregados = temp;
		}
		listaAcciones[listaAcciones.length - 1] = "A";
	}

	public void setEliminarElementos(int pos) {

		if(integrantesEliminados == null) {

			integrantesEliminados = new Alumno[] {familia.getIntegrantes()[pos]};
		} else if(integrantesEliminados.length > 0) {
		
			Alumno temp[] = new Alumno[integrantesEliminados.length + 1];
			System.arraycopy(integrantesEliminados, 0, temp, 0, integrantesEliminados.length);
			temp[integrantesEliminados.length - 1] = familia.getIntegrantes()[pos];
			integrantesEliminados = temp;
		}			
		listaAcciones[pos] = "E";
	}
	
	public boolean guardarCambios() {

		boolean bandera = true;
		grupoFamiliarDAO = new GrupoFamiliarMySQL();
		AlumnoDAO alumnosDAO = new AlumnoMySQL();

		if(integrantesAgregados != null) {
			
			bandera = alumnosDAO.updateFamilia(familia.getId(), integrantesAgregados, 1);
			
			if(bandera) {
				
				familia.setCantIntegrantes(familia.getIntegrantes().length + integrantesAgregados.length);
				bandera = grupoFamiliarDAO.update(familia);
			} else {
				
				msgError = "Error al guardar los elementos agregados.";
				return false;
			}
			familia = grupoFamiliarDAO.getGrupoFamiliar(familia.getId());			
		}
		
		if(integrantesEliminados != null && bandera) {

			bandera = alumnosDAO.updateFamilia(0, integrantesEliminados, 0);
			
			if(bandera) {
			
				familia.setCantIntegrantes(familia.getIntegrantes().length - integrantesEliminados.length);
				
				if(familia.getCantIntegrantes() < 1)
					familia.setEstado(0);
				bandera = grupoFamiliarDAO.update(familia);
			} else {
				
				msgError = "Error al guardar los elementos eliminados.";
				return false;
			}	
		}
		integrantesAgregados = null;	
		integrantesEliminados = null;
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