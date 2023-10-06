package modelo;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import dao.CursosMySQL;
import dao.CursosDAO;
import dao.EmpleadoMySQL;

public class DtosCurso {
	
	private boolean ocupado[][];
	private String horarios[][];
	private int idProfesores[];
	private String idCursos[];
	private int aula;
	private int estado;
	private String año;
	private String nivel;
	private String idProfesor;
	private String valorCuota;
	private String nombreProfesor;
	private String idCurso;
	private String cantHoras;
	private String msgError;
	
	public DefaultTableModel getTablaCursos() {
		
		CursosDAO cursosDAO = new CursosMySQL();
		String titulo[] = {"", "Curso", "Profesor", "Aula", "Cuota", "Días"};
		String aulas [] = getListaAulas();
		String respuesta[][]= cursosDAO.getListado("");
		String cuerpo[][]=null;
		
		try {
			cuerpo = new String[respuesta.length][6];
			
			for(int i = 0 ; i < respuesta.length ; i++) {
				
				cuerpo[i][0] = respuesta[i][6];
				cuerpo[i][1] = respuesta[i][1] + " " + respuesta[i][0];
				cuerpo[i][2] = respuesta[i][2];
				cuerpo[i][3] = aulas[Integer.parseInt(respuesta[i][8])];
				cuerpo[i][4] = respuesta[i][3];
				cuerpo[i][5] = respuesta[i][4];
			}
		} catch (Exception e) {

			cuerpo = null;
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo);
		return tablaModelo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getMsgError() {
		
		return msgError;
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

	public DefaultTableModel getHorariosCurso(int aula, int profesor) {
		
		CursosMySQL cursoDAO = new CursosMySQL();
		cursoDAO.getTablaSemanal(idCurso, idProfesores[profesor], aula);
		ocupado = cursoDAO.getmatrizDiasHorarios();
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

		if(this.aula == aula && !idCurso.equals("0")) {
		
			cursoDAO.getCronogramaDias(idCurso, 0, 0);
			ocupado = cursoDAO.getmatrizDiasHorarios();
	
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

	public void getInformacionCurso() {
		
		CursosMySQL cursosDAO = new CursosMySQL();
		String respuesta[][] = cursosDAO.getListado(idCurso);
		
		if(respuesta.length > 0) {
			
			año = respuesta[0][0];
			nivel = respuesta[0][1];
			nombreProfesor = respuesta[0][2];
			valorCuota = respuesta[0][3];
			idProfesor = respuesta[0][7];
			aula = Integer.parseInt(respuesta[0][8]);
			estado = 1;
		}
	}
	


	public DefaultTableModel getDiagramacion (String criterio, int valor) {
		
		CursosMySQL cursoDAO = new CursosMySQL();
		int sumaHoras = 0;

		if(criterio.equals("Profesor")) {
			
			cursoDAO.getCronogramaDias("0", idProfesores[valor], -1);
		} else if(criterio.equals("Curso")) {
			
			cursoDAO.getCronogramaDias(idCursos[valor], 0, -1);
		}else {
			
			cursoDAO.getCronogramaDias("0", 0, valor);
		}
		ocupado = cursoDAO.getmatrizDiasHorarios();
		String cronograma[][] = new String[6][getListadoHorarios().length];

		for(int i = 0 ; i < 6 ; i++) {
			
			for(int e = 0 ; e < 33 ; e++) {
				
				cronograma[i][e] = ocupado[i][e]? "X":" ";

				if(cronograma[i][e].equals("X")) {
					
					sumaHoras++;
				}
			}
		}
		cantHoras = (float)((sumaHoras - 1) / 2) + "";
		DefaultTableModel tablaModelo = new DefaultTableModel(cronograma, getListadoHorarios());
		return tablaModelo;	
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
		horarios = new String [cant][3];
		int pos = -1;
		
		for(int i = 0; i < tablaHorarios.getRowCount(); i++) {
		
			comienzo = false;
			
			for(int e = 0; e < tablaHorarios.getColumnCount(); e++) {

				if(tablaHorarios.getValueAt(i, e).equals("O ") && !comienzo) {
					
					pos++;
					comienzo = true;
					horarios[pos][0] = i + "";
					horarios[pos][1] = getListadoHorarios()[e];
					cant = 0;
				}
		
				if(!tablaHorarios.getValueAt(i, e).equals("O") && !tablaHorarios.getValueAt(i, e).equals("O "))
					comienzo = false;
				
				if(comienzo) {

					cant++;
					horarios[pos][2] = cant + "";
				}
			}
		}
	}
	
	public boolean isCheckInfo() {
		
		if(valorCuota.equals("") || !isNumeric(valorCuota)) {
			
			msgError = "El valor cuota no puede estar vacío y debe ser un número.";
			return false;
		}
		
		if(horarios.length == 0) {
		
			msgError = "Debe elegir por lo menos un día y horario para el curso.";
			return false;
		}
		return true;
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
		
		CursosMySQL cursosDAO = new CursosMySQL();
		String [][] respuesta = cursosDAO.getListado("");
		idCursos = new String[respuesta.length];
		String [] nombreCursos = new String[respuesta.length];
		
		for(int i = 0 ; i < respuesta.length; i++) {
			
			idCursos[i] = respuesta[i][5];
			nombreCursos[i] = respuesta[i][0] + " " + respuesta[i][1] + " " + respuesta[i][2];
		}
		return nombreCursos;
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
	
	public String [] getListaNivel() {
		
		return new String [] {"Kinder", "Children", "Junior", "Teens", "Adults"};
	}

	public String [] getListaCriterios() {
		
		return new String [] {"Aula", "Curso", "Profesor"};
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
	
	public String [] getListaProfesores() {
		
		EmpleadoMySQL empleadosDAO = new EmpleadoMySQL();
		Empleado empleado[] = empleadosDAO.getListado("Docente", true, "");
		String respuesta[] = new String[empleado.length];
		idProfesores = new int [empleado.length];
		
		for(int i = 0 ; i < empleado.length ; i++) {
			
			respuesta[i] = empleado[i].getNombre() + " " +empleado[i].getApellido();
			idProfesores[i] = empleado[i].getLegajo();
		}

		return respuesta;
	}
	
	private static boolean isNumeric(String cadena) {
		
		try {
			
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException e){
			
			return false;
		}
	}

	public boolean setNuevoCurso() {
		
		CursosMySQL cursoDAO = new CursosMySQL();
		return cursoDAO.setCurso(año, nivel, idProfesor, aula, valorCuota, horarios);
	}
	
	public boolean setActualizarCurso() {
		
		CursosMySQL cursoDAO = new CursosMySQL();
		return cursoDAO.setActualizarCurso(idCurso, idProfesor, aula, valorCuota, estado, horarios);
	}

	public String getAño() {
		
		return año;
	}

	public void setAño(String año) {
		
		this.año = año;
	}

	public String getNivel() {
		
		return nivel;
	}

	public void setNivel(String nivel) {
		
		this.nivel = nivel;
	}

	public String getValorCuota() {
		
		return valorCuota;
	}

	public void setValorCuota(String valorCuota) {
		
		this.valorCuota = valorCuota;
	}

	public void setIdProfesor(int orden) {
		
		this.idProfesor = idProfesores[orden] + "";
	}

	public int getAula() {
		
		return aula;
	}

	public void setAula(int aula) {
		
		this.aula = aula;
	}

	public String getNombreProfesor() {
		
		return nombreProfesor;
	}

	public void setCurso(String curso) {
		
		this.idCurso = curso;
	}

	public void setEstado(int estado) {
		
		this.estado = estado;
	}

	public String getCantHoras() {
		return cantHoras;
	}
}