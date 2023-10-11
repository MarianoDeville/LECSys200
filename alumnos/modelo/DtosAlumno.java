package modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;
import control.CtrlLogErrores;
import dao.AlumnoDAO;
import dao.AlumnoMySQL;
import dao.CursosDAO;
import dao.CursosMySQL;
import dao.EmpleadoDAO;
import dao.EmpleadoMySQL;
import dao.PersonaDAO;
import dao.PersonaMySQL;

public class DtosAlumno {
	
	private AlumnoDAO alumnosDAO;
	private static Alumno alumno = new Alumno();
	private CursoXtnd cursos[];
	private Alumno alumnos[];
	private Empleado docentes[];
	private String añoNacimiento;
	private String mesNacimiento;
	private String diaNacimiento;
	private String cantAlumnos;
	private static String msg;	
	
	
	
/*	
	private static String resultadoExamen;
	private static String tipoExamen;
	private static Object [][] tablaAsistencia;
	private int escrito1;
	private int escrito2;
	private int finalEscrito;
	private int comportamiento1;
	private int comportamiento2;
	private int finalComportamiento;
	private int oral1;
	private int oral2;
	private int finalOral;
	private String ausente;
	private String presente;
	private String tarde;
*/
	public String [] getOrdenamiento() {
		
		return new String [] {"Legajo", "Nombre", "Apellido", "DNI", "Dirección", "Curso"};
	}
	
	public DefaultTableModel getTablaAlumnos(String campo, String valor, boolean estado, int pos) {
		
		alumnosDAO = new AlumnoMySQL();
		String titulo[] = {"Leg.", "Nombre", "Apellido", "DNI", "Dirección", "Teléfono", "E-mail", "Curso"};
		String ordenado[] = {"legajo", "nombre", "apellido", "alumnos.dni", "dirección", "alumnos.idCurso"};
		alumnos = alumnosDAO.getListado(campo, "", estado, ordenado[pos], valor);
		Object tabla[][] = new Object[alumnos.length][8];
		
		for(int i = 0; i < alumnos.length; i++) {
			
			tabla[i][0] = alumnos[i].getLegajo();
			tabla[i][1] = alumnos[i].getNombre();
			tabla[i][2] = alumnos[i].getApellido();
			tabla[i][3] = alumnos[i].getDni();
			tabla[i][4] = alumnos[i].getDireccion();
			tabla[i][5] = alumnos[i].getTelefono();
			tabla[i][6] = alumnos[i].getEmail();
			tabla[i][7] = alumnos[i].getCurso().getNivel() + " " + alumnos[i].getCurso().getAño();
		}
		cantAlumnos = alumnos.length + "";
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		return tablaModelo;
	}
	
	public void setAlumnoSeleccionado(int pos) {
		
		alumno = alumnos[pos];
	}
	
	public String [] getListaCursos() {
		
		CursosDAO cursosDAO = new CursosMySQL();
		cursos = cursosDAO.getListado("");
		String nombreCursos[] = new String[cursos.length];

		for(int i = 0 ; i < cursos.length; i++) {
			
			nombreCursos[i] = cursos[i].getAño() + " " + cursos[i].getNivel() + " " + cursos[i].getNombreProfesor();
		}
		return nombreCursos;
	}
	
	public DefaultTableModel getTablaDias(int pos) {
	
		String titulo[] = {"", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado"};
		Object cuerpo[][] = new Object[][] {{"Hora:","","","","","",""},{"Duración:","","","","","",""}};
		
		if(cursos != null) {
			
			for(int i = 0 ; i < cursos[pos].getHorarios().length ; i++) {

				cuerpo[0][cursos[pos].getHorarios()[i].getDia() + 1] = cursos[pos].getHorarios()[i].getHora();
				cuerpo[1][cursos[pos].getHorarios()[i].getDia() + 1] = calcularTiempo(cursos[pos].getHorarios()[i].getDuración());
			}
		} else {
			
			cuerpo = null;
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo);
		return tablaModelo;
	}

	private String calcularTiempo(int num) {
		
		String resultado =(int) num / 2 + "";
		
		if (num % 2 == 0)
			resultado += ":00";
		else
			resultado += ":30";
		return resultado;
	}
	
	public String checkInformacion(boolean checDNI) {

		if(alumno.getNombre().length() < 3)
			return "El nombre debe tener más de dos caracteres.";
		
		if(alumno.getApellido().length() < 3)
			msg ="El apellido debe tener más de dos caracteres.";
		
		if(alumno.getDni().length() < 7)
			return "Error en el formato del DNI (solamente números).";
		PersonaDAO personasDAO = new PersonaMySQL();
		
		if(personasDAO.isDNIDuplicado(alumno.getDni()) && checDNI)
			return "El DNI ya está siendo usado.";
		
		if(añoNacimiento.length() == 0 || Integer.parseInt(añoNacimiento) < 1920)
			return "Error en el formato del año.";
		
		if(mesNacimiento.length() == 0 || Integer.parseInt(mesNacimiento) < 1 || Integer.parseInt(mesNacimiento) > 12 )
			return "Error en el formato del mes.";
			
		if(diaNacimiento.length() == 0 || Integer.parseInt(diaNacimiento) < 1 || Integer.parseInt(diaNacimiento) > 31 )
			return "Error en el formato del día.";
			
		if(alumno.getDireccion().length() == 0)
			return "La dirección no puede estar vacía.";
		
		if(alumno.getTelefono().length() == 0 || !isNumeric(alumno.getTelefono()))
			return "Error en el formato del teléfono (solamente números).";

		alumno.setFechaNacimiento(añoNacimiento + "/" + mesNacimiento + "/" + diaNacimiento);
		return "";
	}
	
	public boolean setNuevoAlumno() {
		
		alumnosDAO = new AlumnoMySQL();
		return alumnosDAO.setNuevo(alumno);
	}
	
	public int getCursoSeleccionado() {
		
		int i = 0;
		while(i < cursos.length) {

			if(cursos[i].getId() == alumno.getIdCurso()) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public void limpiarInformacion() {

		alumno = null;
	}
	
	public boolean setActualizarAlumno() { 
		
		alumnosDAO = new AlumnoMySQL();
		return alumnosDAO.update(alumno);
	}

	public String [] getCriterio() {
		
		return new String [] {"Curso", "Docente"};
	}
	
	public String [] getListadoValorCriterio(String criterio) {
		
		if(criterio.equals("Curso"))
			return getListaCursos();
		
		if(criterio.equals("Docente"))
			return getListadoProfesores();
		return null;
	}
	
	public String [] getListadoProfesores() {
		
		EmpleadoDAO empleadosDAO = new EmpleadoMySQL();
		docentes = empleadosDAO.getListado("Docente", true, "");
		String respuesta[] = new String[docentes.length];

		for(int i = 0 ; i < docentes.length ; i++) {

			respuesta[i] = docentes[i].getNombre() + " " + docentes[i].getApellido();
		}
		return respuesta;
	}

	public String getIdCriterio(String criterio, int pos) {

		if(criterio.equals("Curso"))
			return cursos[pos].getId() + "";
		
		if(criterio.equals("Docente"))
			return docentes[pos].getLegajo() + "";
		return null;
	}
		
	public DefaultTableModel getListadoAlumnos(String campo, String valor) {
		
		alumnosDAO = new AlumnoMySQL();
		String titulo[] = {"Leg.", "Nombre", "Apellido", "DNI", "Dirección", "Teléfono", "E-mail", "Curso"};

		alumnos = alumnosDAO.getListado(campo, valor, true, "", "");
		Object tabla[][] = new Object[alumnos.length][8];
		
		for(int i = 0; i < alumnos.length; i++) {
			
			tabla[i][0] = alumnos[i].getLegajo();
			tabla[i][1] = alumnos[i].getNombre();
			tabla[i][2] = alumnos[i].getApellido();
			tabla[i][3] = alumnos[i].getDni();
			tabla[i][4] = alumnos[i].getDireccion();
			tabla[i][5] = alumnos[i].getTelefono();
			tabla[i][6] = alumnos[i].getEmail();
			tabla[i][7] = alumnos[i].getCurso().getNivel() + " " + alumnos[i].getCurso().getAño();
		}
		cantAlumnos = alumnos.length + "";
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		return tablaModelo;
	}

	
	
	
	
	
	
	

	
	
	

	

	

	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	public String getMsg() {
		
		return msg;
	}
	
	public String getLegajo() {
		
		return DtosAlumno.alumno.getLegajo() + "";
	}
	
	public String getNombre() {
		
		return DtosAlumno.alumno.getNombre();
	}
	
	public void setNombre(String nombre) {
		
		DtosAlumno.alumno.setNombre(nombre);
	}
	
	public String getApellido() {
		
		return DtosAlumno.alumno.getApellido();
	}

	public void setApellido(String apellido) {
		
		DtosAlumno.alumno.setApellido(apellido);
	}

	public String getFechaAño() {
		
		String[] partes = DtosAlumno.alumno.getFechaNacimiento().split("/");
		añoNacimiento = partes[2];
		return añoNacimiento;
	}
	
	public void setFechaAño(String fechaNacimientoAño) {
		
		añoNacimiento = fechaNacimientoAño;
	}
	
	public String getFechaMes() {
		
		String[] partes = DtosAlumno.alumno.getFechaNacimiento().split("/");
		mesNacimiento = partes[1];
		return mesNacimiento;
	}
	
	public void setFechaMes(String fechaNacimientoMes) {
		
		mesNacimiento = fechaNacimientoMes;
	}
	
	public String getFechaDia() {
		
		String[] partes = DtosAlumno.alumno.getFechaNacimiento().split("/");
		diaNacimiento = partes[0];
		return diaNacimiento;
	}

	public void setFechaDia(String fechaNacimientoDia) {
		
		diaNacimiento = fechaNacimientoDia;
	}

	public String getDni() {
		
		return DtosAlumno.alumno.getDni();
	}
	
	public void setDni(String dni) {
		
		if(isNumeric(dni))
			DtosAlumno.alumno.setDni(dni);
		else
			DtosAlumno.alumno.setDni("");
		
	}

	public String getTelefono() {
		
		return DtosAlumno.alumno.getTelefono();
	}
	
	public void setTelefono(String telefono) {
		
		DtosAlumno.alumno.setTelefono(telefono);
	}
	
	public String getDireccion() {
		
		return DtosAlumno.alumno.getDireccion();
	}
	
	public void setDireccion(String direccion) {
		
		DtosAlumno.alumno.setDireccion(direccion);
	}
	
	public String getEmail() {
		
		return DtosAlumno.alumno.getEmail();
	}
	
	public void setEmail(String email) {
		
		DtosAlumno.alumno.setEmail(email);
	}
	
	public void setEstado(boolean estado) {
		
		DtosAlumno.alumno.setEstado(estado?1:0);
	}
	
	public String getCurso() {
		
		return DtosAlumno.alumno.getIdCurso() + "";
	}
	
	public void setCurso(int pos) {
		
		DtosAlumno.alumno.setIdCurso(cursos[pos].getId());
	}

	public boolean getEstado() {
		
		return (DtosAlumno.alumno.getEstado() == 1? true: false);
	}
	
	public String getFechaIngreso() {
		
		return DtosAlumno.alumno.getFechaIngreso();
	}
	
	public String getFechaBaja() {
		
		return DtosAlumno.alumno.getFechaBaja();
	}
	
	public String getCantAlumnos() {
		
		return cantAlumnos;
	}

	

	

	

	

	
	
	
	
	
	
	
	
	
	
	
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////// Por revisar	////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	
	
	public void cargarAsistencia() {
		
		alumnosDAO = new AlumnoMySQL();
		alumnosDAO.getInfoAsistencia(legajo);
		ausente = alumnosDAO.getFaltas();
		presente = alumnosDAO.getPresente();
		tarde = alumnosDAO.getTarde();
	}
	
	public void cargarNotas() {
		
		alumnosDAO = new AlumnoMySQL();
		String respuest[][] = alumnosDAO.getExamen(legajo);
		
		for(int i = 0; i < respuest.length; i++) {
			
			switch (respuest[i][1]) {
			
				case "1º Escrito": {
					
					escrito1 = conviertoInt(respuest[i][2]);
					break;	
				}
				
				case "2º Escrito": {
					
					escrito2 = conviertoInt(respuest[i][2]);
					break;	
				}
				
				case "1º Oral": {
					
					oral1 = conviertoInt(respuest[i][2]);
					break;	
				}
				
				case "2º Oral": {
					
					oral2 = conviertoInt(respuest[i][2]);
					break;	
				}
				
				case "1º Comportamiento": {
					
					comportamiento1 = conviertoInt(respuest[i][2]);
					break;	
				}
				
				case "2º Comportamiento": {
					
					comportamiento2 = conviertoInt(respuest[i][2]);
					break;	
				}
			}	
			
			finalEscrito = (escrito1 + escrito2) / 2;
			finalComportamiento = (comportamiento1 + comportamiento2) / 2;
			finalOral = (oral1 + oral2) / 2;
		}
	}
	
	private int conviertoInt(String valor) {

		try {
			
			return Integer.parseInt(valor);
		} catch (Exception e) {
		
		}
		return 0;
	}
	
	public String getNombreProfesor() {

		String temp[] = nombreCursos[getCursoSeleccionado()].split(" ");
		return temp[2] + " " + temp[3];
	}
	
	public int getAsistencia(String campo, int fila) {
		
		if(campo.equals("Legajo")) {
			
			return Integer.parseInt((String)tablaAsistencia[fila][0]);
		} else if(campo.equals("Estado")) {
			
			if((boolean)tablaAsistencia[fila][4]) {
				
				return 2;
			}
			if((boolean)tablaAsistencia[fila][3]) {
				
				return 1;
			}
		}
		return 0;
	}
	

	
	public DefaultTableModel getTablaAsistencia(int cursoSeleccionado) {
		
		alumnosDAO = new AlumnoMySQL();
		String titulo[] = {"Leg.", "Nombre", "Apellido", "Presente", "Tarde"};
		String respuesta[][] = alumnosDAO.getListado("Curso", idCursos[cursoSeleccionado], true, "", "");
		tablaAsistencia = null;
		cantAlumnos = "0";
		if(respuesta != null) {
			
			tablaAsistencia = new Object[respuesta.length][5];
			cantAlumnos = respuesta.length + "";
			
			for(int i = 0 ; i < respuesta.length ; i++) {
				
				tablaAsistencia[i][0] = respuesta[i][0];
				tablaAsistencia[i][1] = respuesta[i][1];
				tablaAsistencia[i][2] = respuesta[i][2];
				tablaAsistencia[i][3] = false;
				tablaAsistencia[i][4] = false;
			}
		} else {
			
			tablaAsistencia = null;
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(tablaAsistencia, titulo){

			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				
				return column > 2? true: false;
			}
			
			public Class<?> getColumnClass(int column) {
				
				return column > 2? Boolean.class: String.class;
		    }
		};
		return tablaModelo;
	}

	public DefaultTableModel getTablaRegistroAsistencia(int cursoSeleccionado, int mesSeleccionado) {
		
		alumnosDAO = new AlumnoMySQL();
		msg = "";
		String titulo[] = {""};
		String titulo1[] = {"Leg.", "Nombre", "Apellido"};
		String cuerpo[][] = null;
		String respuesta[][] = alumnosDAO.getAsistencias(idCursos[cursoSeleccionado], true, mesSeleccionado);
		
		if(respuesta != null) {
			
			String titulo2[] = new String [respuesta.length];
			titulo = new String[titulo1.length + titulo2.length];
	
			for(int i = 0 ; i < titulo.length ; i++) {
			
				titulo[i] = i < 3? titulo1[i]: respuesta[i - 3][2];
			}
			respuesta = alumnosDAO.getListado("Curso", idCursos[cursoSeleccionado], true, "", "");
			
			if(respuesta != null) {
				
				cuerpo = new String[respuesta.length][titulo.length];
	
				for(int i = 0 ; i < respuesta.length ; i++) {
					
					cuerpo[i][0] = respuesta[i][0];
					cuerpo[i][1] = respuesta[i][1];
					cuerpo[i][2] = respuesta[i][2];
				}
			} else {
	
				cuerpo = null;
			}
			respuesta = alumnosDAO.getAsistencias(idCursos[cursoSeleccionado], false, mesSeleccionado);
			
			try {
				
				for( int e = 0 ; e < cuerpo.length ; e++) {
				
					int f = 3;
				
					for(int i = 0 ; i < respuesta.length  ; i++) {
						
						if(cuerpo[e][0].equals(respuesta[i][1])) {
							
							cuerpo[e][f] = respuesta[i][2];
					
							if(respuesta[i][3].equals("0")) {
								
								cuerpo[e][f] = "Falta";
							} else if(respuesta[i][3].equals("1")) {
								
								cuerpo[e][f] = "Presente";
							}else if(respuesta[i][3].equals("2")) {
								
								cuerpo[e][f] = "Tarde";
							}
							f++;
						}
					}
				}
			} catch (Exception e) {
				
				cuerpo = null;
				msg = "Existe un día que se ha tomado duplicada la asistencia.";
				CtrlLogErrores.guardarError(e.getMessage() + msg);
				CtrlLogErrores.guardarError("DtosAlumno, getTablaRegistroAsistencia()");
			}
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo);
		return tablaModelo;
	}
	
	public DefaultTableModel getTablaExamenes(int cursoSeleccionado) {

		alumnosDAO = new AlumnoMySQL();
		String cuerpo[][] = null;
		String titulo[] = {"Leg.", "Nombre", "Apellido", "Resultado"};
		String respuesta[][] = alumnosDAO.getListado("Curso", idCursos[cursoSeleccionado], true, "", "");

		if(respuesta != null) {
		
			cuerpo = new String [respuesta.length][4];
			
			for(int i = 0 ; i < respuesta.length ; i++) {

				cuerpo[i][0] = respuesta[i][0];
				cuerpo[i][1] = respuesta[i][1];
				cuerpo[i][2] = respuesta[i][2];
				cuerpo[i][3] = "";
			}
		} else {
			
			cuerpo = null;
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo){

			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return column > 2? true: false;
			}
		};
		return tablaModelo;
	}
	
	public String getFechaActual(boolean formato) {
		
		Calendar fechaSistema = new GregorianCalendar();
		String fecha = null;
		if(formato) {
			
			fecha = fechaSistema.get(Calendar.DAY_OF_MONTH) + "/" 
				  + (fechaSistema.get(Calendar.MONTH)+1) + "/" 
				  + fechaSistema.get(Calendar.YEAR);
		} else {
			
			fecha = fechaSistema.get(Calendar.YEAR) + "/" 
				  + (fechaSistema.get(Calendar.MONTH)+1) + "/" 
				  + fechaSistema.get(Calendar.DAY_OF_MONTH);
		}
		return fecha;
	}
	


	
	public String [] getListaMeses() {
		
		return new String[] {"Todos","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
	}
	
	public String [] getListaTipoExamen() {
		
		return new String[] {"1º Escrito","2º Escrito","1º Oral", "2º Oral", "1º Comportamiento", "2º Comportamiento"};
	}

	public String [][] getHorariosCursos() {
		
		return horariosCursos;
	}
	
	public void setTablaAsistencia(int fila, int columna, boolean valor) {
		
		if(fila < tablaAsistencia.length) {
			
			if(columna < tablaAsistencia[fila].length && columna > 2) {
				
				tablaAsistencia[fila][columna] = valor;
			}
		}
	}

	public void guardarResultados(String [][] tablaResultados) {
		
		boolean bandera = true;
		
		for(int i = 0 ; i < tablaResultados.length ; i++) {
			
			if(!isNumeric(tablaResultados[i][1])) {
				
				if(!tablaResultados[i][1].equals("-")) {

					msg = "Las notas deben ser numéricas o guión.";
					return ;
				}
			}
		}
		CursosMySQL cursosDAO = new CursosMySQL();
		
		if(cursosDAO.isExamenCargado(idCurso, tipoExamen)) {
			
			msg = "El exámen no se puede cargar, ya está cargado.";
			return;
		}
			
		for(int i = 0 ; i < tablaResultados.length ; i++) {
			
			if(!tablaResultados[i][1].equals("-")) {
				
				legajo = tablaResultados[i][0];
				resultadoExamen = tablaResultados[i][1];
				idProfesor = idProfesores[getCursoSeleccionado()];
	
				if(bandera) {
					
					msg = "Notas almacenadas correctamente.";
					bandera = alumnosDAO.setExamen();
				} else {
					
					msg = "Error al intentar almacenar las notas.";
					alumnosDAO.setExamen();
				}
			}
		}
		return; 
	}

	public boolean guardoAsistencia() {
		
		alumnosDAO = new AlumnoMySQL();

		if(alumnosDAO.isAsistenciaTomada(idCurso, true)) {
			
			msg = "La asistencia para este curso y fecha ya fue tomada.";
			return false;
		}

		for(int fila = 0 ; fila < tablaAsistencia.length ; fila++) {
			
			if(!alumnosDAO.setAsistencia(fila)) {

				msg = "Error al intentar guardar la información.";
				return false;
			}
		}
		msg = "Se actualizó la información en la base de datos.";
		return true;
	}

	private boolean isNumeric(String cadena) {
		
		try {
			
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException e){
			
			return false;
		}
	}

}