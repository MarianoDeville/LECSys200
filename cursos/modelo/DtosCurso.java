package modelo;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import dao.CursosMySQL;
import dao.CursosDAO;
import dao.EmpleadoMySQL;

public class DtosCurso {
	
	private CursosDAO cursosDAO;
	private CursoXtnd cursos[];
	private static CursoXtnd curso;
	private Empleado profesores[];
	private boolean ocupado[][];
	private Horarios horarios[];
	private String cantHoras;
	private String msgError;
		
	public DefaultTableModel getTablaCursos() {
		
		cursosDAO = new CursosMySQL();
		String titulo[] = {"", "Curso", "Profesor", "Aula", "Cuota", "Días"};
		cursos = cursosDAO.getListado("");
		String cuerpo[][]=null;
		
		try {
			cuerpo = new String[cursos.length][6];
			
			for(int i = 0 ; i < cursos.length ; i++) {
				
				cuerpo[i][0] = cursos[i].getId() + "";
				cuerpo[i][1] = cursos[i].getAño() + " " + cursos[i].getNivel();
				cuerpo[i][2] = cursos[i].getNombreProfesor();
				cuerpo[i][3] = getListaAulas()[cursos[i].getAula()];
				cuerpo[i][4] = String.format("%.2f", cursos[i].getPrecio());
				cuerpo[i][5] = cursos[i].getDiasCursado();
			}
		} catch (Exception e) {

			cuerpo = null;
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo);
		return tablaModelo;
	}
	
	public String [] getListaNivel() {
		
		return new String [] {"Kinder", "Children", "Junior", "Teens", "Adults"};
	}
	
	public String [] getListaProfesores() {
		
		EmpleadoMySQL empleadosDAO = new EmpleadoMySQL();
		profesores = empleadosDAO.getListado("Docente", true, "");
		String respuesta[] = new String[profesores.length];
		
		for(int i = 0 ; i < profesores.length ; i++) {
			
			respuesta[i] = profesores[i].getNombre() + " " + profesores[i].getApellido();
		}
		return respuesta;
	}
	
	public String [] getListaAulas() {
		
		return new String [] {"A1", "A2", "A3", "B1", "B2", "B3"};
	}
	
	public String [] getListaAños(String nivel) {
		
		if(nivel.equals("Kinder")) {
			
			return new String [] {" ", "PRE"};
		} else if(nivel.equals("Children")) {
			
			return new String [] {"First", "Second", "Third"};
		}else if(nivel.equals("Junior") || nivel.equals("Adults")) {
			
			return new String [] {"First", "Second", "Third", "Fourth", "Fifth", "Sixth"};
		}else if(nivel.equals("Teens")) {
			
			return new String [] {"I", "II", "III"};
		}
		return null;
	}
	
	public DefaultTableModel getHorariosCurso(int aula, int profesor) {
		
		cursosDAO = new CursosMySQL();
		ocupado = cursosDAO.getTablaSemanal(curso.getId(), profesores[profesor].getLegajo(), aula);
		String cronograma[][] = new String[6][getListadoHorarios().length];

		for(int i = 0 ; i < 6 ; i++) {
			
			for(int e = 0 ; e < getListadoHorarios().length ; e++) {
				
				if(ocupado[i][e]) {
					
					cronograma[i][e] = "X";
					
					if(e > 0 && e < getListadoHorarios().length - 1) { 
						
						if(!ocupado[i][e-1] || !ocupado[i][e+1])
							cronograma[i][e] = "X ";
					}

				} else {
					
					cronograma[i][e] = " ";
				}
			}
		}

		if(curso.getAula() == aula && curso.getId() != 0) {
		
			ocupado = cursosDAO.getTablaSemanal(curso.getId(), 0, 0);
	
			for(int i = 0 ; i < 6 ; i++) {
				
				for(int e = 0 ; e < getListadoHorarios().length ; e++) {
					
					if(ocupado[i][e]) {
						
						cronograma[i][e] = "O";
						
						if(e > 0 && e < getListadoHorarios().length - 1) { 
							
							if(!ocupado[i][e-1] || !ocupado[i][e+1])
								cronograma[i][e] = "O ";
						}
					}
				}
			}
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cronograma, getListadoHorarios());
		return tablaModelo;
	}
	
	public boolean autocompletar(JTable tablaOcupacion) {

		msgError = null;
		
		for(int i = 0; i < tablaOcupacion.getRowCount(); i++) {

			int buclesLlenado = 0;
			int buclesVaciado = 0;
			
			for(int e = 0; e < tablaOcupacion.getColumnCount(); e++) {
				
				switch ((String)tablaOcupacion.getValueAt(i, e)) {
				
					case "C":
					case "C ":
						buclesLlenado++;	
						break;

					case "F":
					case "F ":
						tablaOcupacion.setValueAt("O ", i, e);
						buclesLlenado--;	
						break;
					
					case "CE":
						buclesVaciado++;	
						break;
						
					case "FE":
						tablaOcupacion.setValueAt(" ", i, e);
						buclesVaciado--;	
						break;	
				}

				if(buclesLlenado > 0 && tablaOcupacion.getValueAt(i, e).equals("X")) {
				
					msgError = "No es posible reservar en el rango seleccionado.";
					return false;
				}
				
				if(buclesLlenado > 0) {
					
					if(e == 0)
						tablaOcupacion.setValueAt("O ", i, e);
					else
						tablaOcupacion.setValueAt(tablaOcupacion.getValueAt(i, e-1).equals(" ")?"O ":"O", i, e);
				}				
	
				if(buclesVaciado > 0)
					tablaOcupacion.setValueAt(" ", i, e);
			}
		}
		return msgError==null;
	}
	
	public boolean isCheckInfo() {
		
		if(curso.getPrecio() < 1) {
			
			msgError = "El valor cuota no puede estar vacío y debe ser un número.";
			return false;
		}
		
		if(horarios.length == 0) {
		
			msgError = "Debe elegir por lo menos un día y horario para el curso.";
			return false;
		}
		curso.setHorarios(horarios);
		return true;
	}	
	
	public void setHorarios(JTable tablaHorarios) {

		int cant = 0;
		boolean comienzo;

		for(int i = 0; i < tablaHorarios.getRowCount(); i++) {
			
			comienzo = false;
			
			for(int e = 0; e < tablaHorarios.getColumnCount(); e++) {

				if(tablaHorarios.getValueAt(i, e).equals("O ") && !comienzo) {
				
					comienzo = true;
					cant++;
				} 
				
				if(!tablaHorarios.getValueAt(i, e).equals("O") && !tablaHorarios.getValueAt(i, e).equals("O "))
					comienzo = false;
			}
		}
		horarios = new Horarios [cant];
		int pos = -1;
		
		for(int i = 0; i < tablaHorarios.getRowCount(); i++) {
		
			comienzo = false;
			
			for(int e = 0; e < tablaHorarios.getColumnCount(); e++) {

				if(tablaHorarios.getValueAt(i, e).equals("O ") && !comienzo) {
					
					pos++;
					comienzo = true;
					horarios[pos] = new Horarios();
					horarios[pos].setDia(i);
					horarios[pos].setHora(getListadoHorarios()[e]);
					cant = 0;
				}
		
				if(!tablaHorarios.getValueAt(i, e).equals("O") && !tablaHorarios.getValueAt(i, e).equals("O "))
					comienzo = false;
				
				if(comienzo) {

					cant++;
					horarios[pos].setDuración(cant);
				}
			}
		}
	}
	
	public String getMsgError() {
		
		return msgError;
	}
	
	public boolean setNuevoCurso() {
		
		cursosDAO = new CursosMySQL();
		return cursosDAO.setCurso(curso);
	}
	
	public void limpiarVariable() {
		
		curso = null;
	}

	public void setCursoElegido(int pos) {
		
		curso = cursos[pos];
	}
	
	public boolean setActualizarCurso() {
		
		cursosDAO = new CursosMySQL();
		return cursosDAO.update(curso);
	}
	
	public String [] getListadoOpciones(String valor) {
		
		if(valor.equals("Aula")) {
			
			return getListaAulas();
		} else if(valor.equals("Profesor")) {
			
			return getListaProfesores();
		} else if(valor.equals("Curso")) {
			
			return getListaCursos();
		}
		return null;
	}
	
	public String [] getListaCursos() {
		
		cursosDAO = new CursosMySQL();
		cursos = cursosDAO.getListado("");
		
		String nombreCursos[] = new String[cursos.length];
		
		for(int i = 0 ; i < cursos.length; i++) {
			
			nombreCursos[i] = cursos[i].getNivel() + " " + cursos[i].getAño() + " " + cursos[i].getNombreProfesor();
		}
		return nombreCursos;
	}
	
	public DefaultTableModel getDiagramacion (String criterio, int valor) {
		
		cursosDAO = new CursosMySQL();
		float sumaHoras = 0;
		boolean ocupado[][] = null;
		
		if(criterio.equals("Profesor")) {
			
			ocupado = cursosDAO.getTablaSemanal(0, cursos[valor].getLegajoProfesor(), -1);
		} else if(criterio.equals("Curso")) {
			
			ocupado = cursosDAO.getTablaSemanal(cursos[valor].getId(), 0, -1);
		}else {
			
			ocupado = cursosDAO.getTablaSemanal(0, 0, valor);
		}
		String cronograma[][] = new String[6][getListadoHorarios().length];

		for(int i = 0 ; i < 6 ; i++) {
			
			boolean bandera = false;
			
			for(int e = 0 ; e < 33 ; e++) {
				
				cronograma[i][e] = ocupado[i][e]? "X":" ";

				if(cronograma[i][e].equals("X") && bandera)
					sumaHoras += 0.5;
				bandera = cronograma[i][e].equals("X")?true:false;
			}
		}
		cantHoras = sumaHoras + "";
		DefaultTableModel tablaModelo = new DefaultTableModel(cronograma, getListadoHorarios());
		return tablaModelo;	
	}

	public String [] getListadoHorarios() {

		String listado[] = new String[33];
		boolean par = true;
		int hora = 7;
		
		for(int i = 0; i < listado.length; i++) {
			
			if(par) {
				
				listado[i] = hora + ":00";
				par = false;
			} else {
				
				listado[i] = hora + ":30";
				par = true;
				hora++;
			}
		}
		return listado;
	}

	public String [] getListaCriterios() {
		
		return new String [] {"Aula", "Curso", "Profesor"};
	}

	public String getAño() {
		
		return curso.getAño();
	}

	public void setAño(String año) {
		
		curso.setAño(año);
	}

	public String getNivel() {
		
		return curso.getNivel();
	}

	public void setNivel(String nivel) {
		
		curso.setNivel(nivel);
	}

	public String getValorCuota() {
		
		return String.format("%.2f", curso.getPrecio());
	}

	public void setValorCuota(String valorCuota) {
		
		try {
		
			curso.setPrecio(Float.parseFloat(valorCuota.replace(",", ".")));
		} catch (Exception e) {

			curso.setPrecio(-1);
		}
	}

	public void setIdProfesor(int orden) {
		
		curso.setLegajoProfesor(profesores[orden].getLegajo());
	}

	public int getAula() {
		
		return curso.getAula();
	}

	public void setAula(int aula) {
		
		curso.setAula(aula);
	}

	public String getNombreProfesor() {
		
		return curso.getNombreProfesor();
	}

	public void setIdCurso(int idCurso) {
		
		if(curso == null)
			curso = new CursoXtnd();
		curso.setId(idCurso);
	}

	public void setEstado(int estado) {
		
		curso.setEstado(estado);
	}

	public String getCantHoras() {
		return cantHoras;
	}
}