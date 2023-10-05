/*****************************************************************************************************************************************************************/
//										LISTADO DE MÉTODOS
/*---------------------------------------------------------------------------------------------------------------------------------------------------------------*/
//	public void cargarAsistencia()
//	public void cargarNotas()
//	private int conviertoInt(String valor)
//	public String getNombreProfesor()
//	public int getAsistencia(String campo, int fila)
//	public int getCursoSeleccionado()
//	public boolean getEstado()
//	public DefaultTableModel getTablaAsistencia(int cursoSeleccionado)
//	public DefaultTableModel getListadoAlumnos(String campo, String valor)
//	public DefaultTableModel getTablaAlumnos(String campo, String valor, boolean estado, int pos)
//	public DefaultTableModel getTablaDias(int curso)
//	public DefaultTableModel getTablaRegistroAsistencia(int cursoSeleccionado, boolean reduciodo)
//	public DefaultTableModel getTablaExamenes(int curso)
//	public String getFechaActual(boolean formato)
//	public String getIdValorCriterio(String criterio, int valor)
//	public String getMsg()
//	public String getLegajo()
//	public String getNombre()
//	public String getApellido()
//	public String getFechaAño()
//	public String getFechaMes()
//	public String getFechaDia()
//	public String getDni()
//	public String getTelefono()
//	public String getDireccion()
//	public String getEmail()
//	public String getCurso()
//	public String getNombreCurso()
//	public String getIdPersona()
//	public String getCantAlumnos()
//	public String getResultadoExamen()
//	public String getIdProfesor()
//	public String getTipoExamen()
//	public String getFechaIngreso()
//	public String [] getListaMeses()
//	public String [] getOrdenamiento()
//	public String [] getListaCursos()
//	public String [] getCriterio() 
//	public String [] getListadoProfesores()
//	public String [] getListadoValorCriterio(String criterio)
//	public String [] getListaTipoExamen()
//	public String [][] getHorariosCursos()
//	public void setTablaAsistencia(int fila, int columna, boolean valor)
//	public void setLegajo(String legajo)
//	public void setNombre(String nombre)
//	public void setApellido(String apellido)
//	public void setFechaAño(String fechaNacimientoAño)
//	public void setFechaMes(String fechaNacimientoMes)
//	public void setFechaDia(String fechaNacimientoDia)
//	public void setDni(String dni)
//	public void setTelefono(String telefono)
//	public void setDireccion(String direccion)
//	public void setEmail(String email)
//	public void setHorariosCursos(String [][] horariosCursos)
//	public void setEstado(boolean estado)
//	public void setCurso(int curso)
//	public void setIdPersona(String idPersona)
//	public void setTipoExamen(String tipoExamen)
//	public boolean setNuevoAlumno() 
//	public boolean setActualizarAlumno()
//	public void limpiarInformacion()
//	public void recuperarInformacionAlumno(String nroLegajo, boolean estadoAlumno)
//	public void guardarResultados(String [][] tablaResultados)
//	public String checkInformacion(boolean checDNI)
//	public boolean guardoAsistencia()
//	private boolean isNumeric(String cadena)
//	public String getEscrito1()
//	public String getEscrito2()
//	public String getOral1()
//	public String getOral2()
//	public String getComportamiento1()
//	public String getComportamiento2()
//	public String getFinalEscrito()
//	public String getFinalComportamiento()
//	public String getFinalOral()
//	public String getAusente()
//	public String getPresente()
//	public String getTarde()
/*****************************************************************************************************************************************************************/

package modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;
import control.CtrlLogErrores;
import dao.AlumnoMySQL;
import dao.CursosMySQL;
import dao.EmpleadoMySQL;
import dao.PersonaDAO;

public class DtosAlumno {
	
	AlumnoMySQL alumnosDAO;
	private static boolean estado;
	private static String legajo;
	private static String nombre;
	private static String apellido;
	private static String dni;
	private static String fechaAño;
	private static String fechaMes;
	private static String fechaDia;
	private static String telefono;
	private static String direccion;
	private static String fechaIngreso;
	private static String email;
	private static String idCurso;
	private static String idPersona;
	private static String idProfesor;
	private static String cantAlumnos;
	private static String resultadoExamen;
	private static String tipoExamen;
	private static String msg;
	private static String [] idCursos;
	private static String [] nombreCursos;
	private static String [] idProfesores;
	private static String [][] horariosCursos;
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
	
	public int getCursoSeleccionado() {
		
		int i = 0;
		while(i < idCursos.length) {
	
			if(idCurso.equals(idCursos[i])) {

				break;
			}
			i++;
		}
		return i;
	}
	
	public boolean getEstado() {
		
		return estado;
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
	
	public DefaultTableModel getListadoAlumnos(String campo, String valor) {
		
		alumnosDAO = new AlumnoMySQL();
		String titulo[] = {"Leg.", "Nombre", "Apellido", "DNI", "Dirección", "Teléfono", "E-mail", "Curso"};
		String respuesta[][] = alumnosDAO.getListado(campo, valor, true, "", "");
		cantAlumnos = "0";
		
		if(respuesta != null)
			cantAlumnos = respuesta.length + "";
		DefaultTableModel tablaModelo = new DefaultTableModel(respuesta, titulo);
		return tablaModelo;
	}
	
	public DefaultTableModel getTablaAlumnos(String campo, String valor, boolean estado, int pos) {
		
		alumnosDAO = new AlumnoMySQL();
		String titulo[] = {"Leg.", "Nombre", "Apellido", "DNI", "Dirección", "Teléfono", "E-mail", "Curso"};
		String ordenado[] = {"idAlumno", "nombre", "apellido", "dni", "dirección", "alumnos.idCurso"};
		String respuesta[][] = alumnosDAO.getListado(campo, "", estado, ordenado[pos], valor);
		cantAlumnos = respuesta.length + "";
		DefaultTableModel tablaModelo = new DefaultTableModel(respuesta, titulo);
		return tablaModelo;
	}
	
	public DefaultTableModel getTablaDias(int curso) {
		
		CursosMySQL cursosDAO = new CursosMySQL();
		String titulo[] = {"", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sábado"};
		String duracion[] = new String [] {"0:00","0:30","1:00","1:30","2:00","2:30","3:00","3:30","4:00"};
		String respuesta[][] = cursosDAO.buscarDiasCurso(idCursos[curso]);
		Object cuerpo[][] = new Object[][] {{"Hora:","","","","","",""},{"Duración:","","","","","",""}};
		
		if(respuesta != null) {
			
			for(int i = 0 ; i < respuesta.length ; i++) {

				cuerpo[0][Integer.parseInt(respuesta[i][0])+1] = respuesta[0][1];
				cuerpo[1][Integer.parseInt(respuesta[i][0])+1] = duracion[Integer.parseInt(respuesta[i][2])];
			}
		} else {
			
			cuerpo = null;
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo);
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
	
	public String getIdValorCriterio(String criterio, int valor) {
		
		if(criterio.equals("Curso"))
			return idCursos[valor];
		
		if(criterio.equals("Docente"))
			return idProfesores[valor];
		return null;
	}
	
	public String getMsg() {
		
		return msg;
	}
	
	public String getLegajo() {
		
		return legajo;
	}
	
	public String getNombre() {
		
		return nombre;
	}
	
	public String getApellido() {
		
		return apellido;
	}
	
	public String getFechaAño() {
		
		return fechaAño;
	}
	
	public String getFechaMes() {
		
		return fechaMes;
	}
	
	public String getFechaDia() {
		
		return fechaDia;
	}
	
	public String getDni() {
		
		return dni;
	}
	
	public String getTelefono() {
		
		return telefono;
	}
	
	public String getDireccion() {
		
		return direccion;
	}
	
	public String getEmail() {
		
		return email;
	}
	
	public String getCurso() {
		
		return idCurso;
	}
	
	public String getNombreCurso() {
	
		String temp[] = nombreCursos[getCursoSeleccionado()].split(" ");
		return temp[0] + " " + temp[1];
	}
	
	public String getIdPersona() {
		
		return idPersona;
	}
	
	public String getCantAlumnos() {
		
		return cantAlumnos;
	}
	
	public String getResultadoExamen() {
		
		return resultadoExamen;
	}
	
	public String getIdProfesor() {
		
		return idProfesor;
	}
	
	public String getTipoExamen() {
		
		return tipoExamen;
	}
	
	public String getFechaIngreso() {
		
		return fechaIngreso;
	}
	
	public String [] getListaMeses() {
		
		return new String[] {"Todos","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
	}
	
	public String [] getOrdenamiento() {
		
		return new String [] {"Legajo", "Nombre", "Apellido", "DNI", "Dirección", "Curso"};
	}
	
	public String [] getListaCursos() {
		
		CursosMySQL cursosDAO = new CursosMySQL();
		String respuesta[][] = cursosDAO.getListado("");
		idCursos = new String[respuesta.length];
		nombreCursos = new String[respuesta.length];
		idProfesores = new String[respuesta.length];

		for(int i = 0 ; i < respuesta.length; i++) {
			
			idCursos[i] = respuesta[i][5];
			nombreCursos[i] = respuesta[i][0] + " " + respuesta[i][1] + " " + respuesta[i][2];
			idProfesores[i] = respuesta[i][7];
		}
		return nombreCursos;
	}
	
	public String [] getCriterio() {
		
		return new String [] {"Curso", "Docente"};
	}
	
	public String [] getListadoProfesores() {
		
		EmpleadoMySQL empleadosDAO = new EmpleadoMySQL();
		String matriz[][] = empleadosDAO.getListado("Docente", true, "");
		String respuesta[] = new String[matriz.length];
		idProfesores = new String[matriz.length];
		for(int i = 0 ; i < matriz.length ; i++) {
			
			idProfesores[i] = matriz[i][0];
			respuesta[i] = matriz[i][1] + " " + matriz[i][2];
		}
		return respuesta;
	}
	
	public String [] getListadoValorCriterio(String criterio) {
		
		if(criterio.equals("Curso"))
			return getListaCursos();
		
		if(criterio.equals("Docente"))
			return getListadoProfesores();
		return null;
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
	
	public void setLegajo(String legajo) {

		DtosAlumno.legajo = legajo;
	}
	
	public void setNombre(String nombre) {
		
		DtosAlumno.nombre = nombre;
	}

	public void setApellido(String apellido) {
		
		DtosAlumno.apellido = apellido;
	}

	public void setFechaAño(String fechaNacimientoAño) {
		
		DtosAlumno.fechaAño = fechaNacimientoAño;
	}
	
	public void setFechaMes(String fechaNacimientoMes) {
		
		DtosAlumno.fechaMes = fechaNacimientoMes;
	}
		
	public void setFechaDia(String fechaNacimientoDia) {
		
		DtosAlumno.fechaDia = fechaNacimientoDia;
	}

	public void setDni(String dni) {
		
		DtosAlumno.dni = dni;
	}

	public void setTelefono(String telefono) {
		
		DtosAlumno.telefono = telefono;
	}
	
	public void setDireccion(String direccion) {
		
		DtosAlumno.direccion = direccion;
	}
	
	public void setEmail(String email) {
		
		DtosAlumno.email = email;
	}
	
	public void setHorariosCursos(String [][] horariosCursos) {
		
		DtosAlumno.horariosCursos = horariosCursos;
	}

	public void setEstado(boolean estado) {
		
		DtosAlumno.estado = estado;
	}
	
	public void setCurso(int curso) {
		
		DtosAlumno.idCurso = idCursos[curso];
	}

	public void setIdPersona(String idPersona) {
		
		DtosAlumno.idPersona = idPersona;
	}
	
	public void setTipoExamen(String tipoExamen) {
		
		DtosAlumno.tipoExamen = tipoExamen;
	}
	
	public boolean setNuevoAlumno() {
		
		alumnosDAO = new AlumnoMySQL();
		return alumnosDAO.setNuevo();
	}
	
	public boolean setActualizarAlumno() { 
		
		alumnosDAO = new AlumnoMySQL();
		return alumnosDAO.update();
	} 

	public void limpiarInformacion() {

		dni = "";
		email = "";
		legajo = "";
		nombre = "";
		idCurso = "";		
		apellido = "";
		fechaAño = "";
		fechaMes = "";
		fechaDia = "";
		telefono = "";
		direccion = "";
		idPersona = "";
		fechaIngreso = "";
	}

	public void recuperarInformacionAlumno(boolean estadoAlumno) {
		
		alumnosDAO = new AlumnoMySQL();
		String alumno[][] = alumnosDAO.getListado("ID", legajo, estadoAlumno, "", "");
		
		if(alumno.length > 0) {
			
			legajo = alumno[0][0];
			nombre =  alumno[0][1];
			apellido = alumno[0][2];
			dni = alumno[0][3];
			direccion = alumno[0][4];
			telefono = alumno[0][5];
			email = alumno[0][6];
			idCurso = alumno[0][10];
			estado = estadoAlumno;
			String[] fecha = alumno[0][12].split("-");
			fechaAño = fecha[0];
			fechaMes = fecha[1];
			fechaDia = fecha[2];
			idPersona = alumno[0][13];
			fechaIngreso = alumno[0][14];
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

	public String checkInformacion(boolean checDNI) {

		if(nombre.length() < 3)
			return "El nombre debe tener más de dos caracteres.";
		
		if(apellido.length() < 3)
			msg ="El apellido debe tener más de dos caracteres.";
		
		if(dni.length() < 7 || !isNumeric(dni))
			return "Error en el formato del DNI (solamente números).";
		PersonaDAO personasDAO = new PersonaDAO();
		
		if(personasDAO.getDNIDuplicado(dni) && checDNI)
			return "El DNI ya está siendo usado.";
		
		if(fechaAño.length() == 0 || Integer.parseInt(fechaAño) < 1920)
			return "Error en el formato del año.";
		
		if(fechaMes.length() == 0 || Integer.parseInt(fechaMes) < 1 || Integer.parseInt(fechaMes) > 12 )
			return "Error en el formato del mes.";
			
		if(fechaDia.length() == 0 || Integer.parseInt(fechaDia) < 1 || Integer.parseInt(fechaDia) > 31 )
			return "Error en el formato del día.";
			
		if(direccion.length() == 0)
			return "La dirección no puede estar vacía.";
		
		if(telefono.length() == 0 || !isNumeric(telefono))
			return "Error en el formato del teléfono (solamente números).";

		return "";
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
	
	public String getEscrito1() {
		
		return escrito1 + "";
	}
	
	public String getEscrito2() {
		
		return escrito2 + "";
	}
	
	public String getOral1() {
		
		return oral1 + "";
	}
	
	public String getOral2() {
		
		return oral2 + "";
	}
	
	public String getComportamiento1() {
		
		return comportamiento1 + "";
	}
	
	public String getComportamiento2() {
		
		return comportamiento2 + "";
	}
	
	public String getFinalEscrito() {
		
		return finalEscrito + "";
	}
	
	public String getFinalComportamiento() {
		
		return finalComportamiento + "";
	}
	
	public String getFinalOral() {
		
		return finalOral + "";
	}

	public String getAusente() {
		
		return ausente;
	}

	public String getPresente() {
		
		return presente;
	}

	public String getTarde() {
		
		return tarde;
	}
}