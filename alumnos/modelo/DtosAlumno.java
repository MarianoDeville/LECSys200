package modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import control.CtrlLogErrores;
import dao.AlumnoDAO;
import dao.AlumnoMySQL;
import dao.AsistenciaDAO;
import dao.AsistenciaMySQL;
import dao.CursosDAO;
import dao.CursosMySQL;
import dao.EmpleadoDAO;
import dao.EmpleadoMySQL;
import dao.ExamenesDAO;
import dao.ExamenesMySQL;
import dao.PersonaDAO;
import dao.PersonaMySQL;

public class DtosAlumno {
	
	private AlumnoDAO alumnosDAO;
	private AsistenciaDAO asistenciaDAO;
	private ExamenesDAO examenesDAO;
	private static Alumno alumno = new Alumno();	
	private Alumno alumnos[];
	private CursoXtnd cursos[];
	private Empleado docentes[];
	private Examenes examen;
	private ResumenAsistencia resAsis;
	private ResumenExamenes resExm;
	private String añoNacimiento;
	private String mesNacimiento;
	private String diaNacimiento;
	private String cantAlumnos;
	private String msg;

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
	
	public String getNuevoLegajo() {
		
		alumnosDAO = new AlumnoMySQL();
		return alumnosDAO.getLegajoLibre() + "";
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

		if(pos < 0)
			return null;
		
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
	
	public DefaultTableModel getTablaAsistencia(int pos) {
		
		alumnosDAO = new AlumnoMySQL();
		Object tablaAsistencia[][] = null;
		String titulo[] = {"Leg.", "Nombre", "Apellido", "Presente", "Tarde"};

		if(cursos.length > 0)
			alumnos = alumnosDAO.getListado("Curso", cursos[pos].getId() + "", true, "", "");
		
		if(alumnos != null) {
			
			tablaAsistencia = new Object[alumnos.length][5];
			
			for(int i = 0 ; i < alumnos.length ; i++) {
				
				tablaAsistencia[i][0] = alumnos[i].getLegajo();
				tablaAsistencia[i][1] = alumnos[i].getNombre();
				tablaAsistencia[i][2] = alumnos[i].getApellido();
				tablaAsistencia[i][3] = false;
				tablaAsistencia[i][4] = false;
			}
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

	public boolean guardoAsistencia(JTable tablaAsistencia) {
		
		if(tablaAsistencia.getRowCount() == 0) {
			
			msg = "No hay información para guardar.";
			return false;
		}
		asistenciaDAO = new AsistenciaMySQL();
		
		if(asistenciaDAO.isAsistenciaTomada(alumno.getIdCurso(), true)) {
			
			msg = "La asistencia para este curso y fecha ya fue tomada.";
			return false;
		}
		alumnos = new Alumno[tablaAsistencia.getRowCount()];
		
		for(int i = 0; i < alumnos.length; i++) {

			int estado = 0;
			alumnos[i] = new Alumno();
			alumnos[i].setLegajo((int)tablaAsistencia.getValueAt(i, 0));
			alumnos[i].setNombre((String)tablaAsistencia.getValueAt(i, 1));
			alumnos[i].setApellido((String)tablaAsistencia.getValueAt(i, 2));
			alumnos[i].setIdCurso(alumno.getIdCurso());
			alumnos[i].setAsistencias(new Asistencia[1]);
			
			if((boolean)tablaAsistencia.getValueAt(i, 4))
				estado = 2;
			else if((boolean)tablaAsistencia.getValueAt(i, 3))
				estado = 1;
			
			Asistencia asist[] = new Asistencia[1];
			asist[0].setEstado(estado);
			alumnos[i].setAsistencias(asist);
		}

		if(asistenciaDAO.setAsistencia(alumnos)) {

			msg = "Se actualizó la información en la base de datos.";
			return true;
		}
		msg = "Error al intentar guardar la información.";		
		return false;
	}

	public DefaultTableModel getTablaRegistroAsistencia(int pos, int mes) {
		
		asistenciaDAO = new AsistenciaMySQL();
		alumnos = null;
		msg = "";
		String titulo[] = {"Leg.", "Nombre", "Apellido"};
		String cuerpo[][] = null;

		if(cursos.length > 0)
			alumnos = asistenciaDAO.getListado(cursos[pos].getId(), true, mes);
		
		if(alumnos != null && alumnos.length > 0) {
			
			titulo = new String[alumnos[0].getAsistencias().length + 3];
			String temp[] = titulo;			
			System.arraycopy(temp, 0, titulo, 0, 3);

			for(int i = 3 ; i < titulo.length ; i++) {
			
				titulo[i] = alumnos[0].getAsistencias()[i - 3].getFecha();
			}
			cuerpo = new String[alumnos.length][titulo.length];

			try {
				
				for(int i = 0 ; i < alumnos.length ; i++) {
					
					cuerpo[i][0] = alumnos[i].getLegajo() + "";
					cuerpo[i][1] = alumnos[i].getNombre();
					cuerpo[i][2] = alumnos[i].getApellido();
					
					for(int e = 3; e < titulo.length; e++) {
						
						if(alumnos[i].getEstado() == 0) {
							
							cuerpo[i][e] = "Falta";
						} else if(alumnos[i].getEstado() == 1) {
							
							cuerpo[i][e] = "Presente";
						}else if(alumnos[i].getEstado() == 2) {
							
							cuerpo[i][e] = "Tarde";
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
	
	public DefaultTableModel getTablaExamenes(int pos) {

		alumnosDAO = new AlumnoMySQL();
		Object cuerpo[][] = null;
		String titulo[] = {"Leg.", "Nombre", "Apellido", "Resultado"};
		
		if(pos != -1)
			alumnos = alumnosDAO.getListado("Curso", cursos[pos].getId() + "", true, "", "");

		if(alumnos != null) {
		
			cuerpo = new Object [alumnos.length][4];
			
			for(int i = 0 ; i < alumnos.length ; i++) {

				cuerpo[i][0] = alumnos[i].getLegajo();
				cuerpo[i][1] = alumnos[i].getNombre();
				cuerpo[i][2] = alumnos[i].getApellido();
				cuerpo[i][3] = "";
			}
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo){

			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return column > 2? true: false;
			}
		};
		return tablaModelo;
	}

	public boolean guardarResultados(JTable tabla) {
		
		boolean bandera = true;

		for(int i = 0 ; i < tabla.getRowCount() ; i++) {
			
			String valor = (String) tabla.getValueAt(i, 3);
			
			if(!isNumeric(valor) && !valor.equals("-")) {

				msg = "Las notas deben ser numéricas o guión.";
				return false;
			}
		}

		for(int i = 0; i < tabla.getRowCount(); i++) {
			
			if(!tabla.getValueAt(i, 3).equals("-"))
				examen.setNota((int) tabla.getValueAt(i, 3));
			else 
				examen.setNota(-1);
			
			alumnos[i].setExamenes(new Examenes[1]);
			alumnos[i].getExamenes()[0] = examen;
		}
		examenesDAO = new ExamenesMySQL();		
		bandera = examenesDAO.setExamen(alumnos);
		
		if(bandera)
			msg = "Notas guardadas.";
		else
			msg = "Error al intentar guardar las notas.";
		return bandera; 
	}
	
	public void cargarResumenAsistencia() {
		
		asistenciaDAO = new AsistenciaMySQL();
		resAsis = asistenciaDAO.getInfoAsistencia(alumno.getLegajo());
	}

	public void cargarNotas() {
		
		resExm = new ResumenExamenes();
		examenesDAO = new ExamenesMySQL();
		alumno.setExamenes(examenesDAO.getExamenes(alumno.getLegajo()));
		
		for(int i = 0; i < alumno.getExamenes().length; i++) {
			
			switch (alumno.getExamenes()[i].getTipo()) {
			
				case "1º Escrito": {
					
					resExm.setEscrito1(alumno.getExamenes()[i].getNota());
					break;	
				}
				
				case "2º Escrito": {
					
					resExm.setEscrito2(alumno.getExamenes()[i].getNota());
					break;	
				}
				
				case "1º Oral": {
					
					resExm.setOral1(alumno.getExamenes()[i].getNota());
					break;	
				}
				
				case "2º Oral": {
					
					resExm.setOral2(alumno.getExamenes()[i].getNota());
					break;	
				}
				
				case "1º Comportamiento": {
					
					resExm.setComportamiento1(alumno.getExamenes()[i].getNota());
					break;	
				}
				
				case "2º Comportamiento": {
					
					resExm.setComportamiento2(alumno.getExamenes()[i].getNota());
					break;	
				}
			}	
		}
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
		
		if(pos > -1)
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
	
	public String [] getListaMeses() {
		
		return new String[] {"Todos","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
	}

	public String [] getListaTipoExamen() {
		
		return new String[] {"1º Escrito","2º Escrito","1º Oral", "2º Oral", "1º Comportamiento", "2º Comportamiento"};
	}

	public void setDatosExamen(int pos) {
		
		examen = new Examenes();
		examen.setIdCruso(cursos[pos].getId());
		examen.setLegajoProfesor(cursos[pos].getLegajoProfesor());
	}
	
	public void setTipoExamen(String tipo) {
		
		examen.setTipo(tipo);
	}
	
	public boolean setFecha(String fecha) {
		
		fecha = fecha.replaceAll("/","-");
		
		if(fecha.length() != 10 || !fecha.contains("-")) {
			
			msg = "El formato de la fecha es incorrecto. Ej. 25-10-2020";
			return false;
		}		
		examen.setFecha(fecha);
		return true;
	}
	
	public String getNombreCurso() {
		
		return alumno.getCurso().getAño() + " " + alumno.getCurso().getNivel();
	}
	
	public String getNombreProfesor() {

		return alumno.getCurso().getNombreProfesor();
	}

	public String getPresente() {
		
		return resAsis.getPresente() + "";
	}
	
	public String getAusente() {
		
		return resAsis.getFaltas() + "";
	}	
	
	public String getTarde() {
		
		return resAsis.getTarde() + "";
	}
	
	public String getEscrito1() {
		
		return resExm.getEscrito1() +"";
	}
	
	public String getEscrito2() {
		
		return resExm.getEscrito2() +"";
	}
	
	public String getComportamiento1() {
		
		return resExm.getComportamiento1() + "";
	}	
	
	public String getComportamiento2() {
		
		return resExm.getComportamiento2() + "";
	}	
	
	public String getOral1() {
		
		return resExm.getOral1() + "";
	}	
	
	public String getOral2() {
		
		return resExm.getOral2() + "";
	}
	
	public String getFinalEscrito() {

		return resExm.getEscrito1() + resExm.getEscrito2() / 2 + "";
	}
	
	public String getFinalComportamiento() {
	
		return resExm.getComportamiento1() + resExm.getComportamiento2() / 2 + "";
	}
	
	public String getFinalOral() {
	
		return resExm.getOral1() + resExm.getOral2() / 2 + "";
	}

	private String calcularTiempo(int num) {
		
		String resultado =(int) num / 2 + "";
		
		if (num % 2 == 0)
			resultado += ":00";
		else
			resultado += ":30";
		return resultado;
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