package modelo;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import dao.CronogramaDAO;
import dao.CronogramaMySQL;
import dao.EmpleadoDAO;
import dao.EmpleadoMySQL;

public class DtosEmpleado {

	private static Empleado empleado = new Empleado();
	private Empleado listaEmpleados[];
	private String añoNacimiento;
	private String mesNacimiento;
	private String diaNacimiento;
	private String msgError;
	private int granAlmacenada;
	private boolean actualizar;

	public String [] getFiltro() {
		
		return new String [] {"Todos","Docente","Administrativo","Recepcionista","Personal limpieza"};
	}
	
	public String [] getListaSectores() {
		
		return new String [] {"Docente","Administrativo","Recepcionista","Personal limpieza"};
	}
	
	public String [] getListaTipos () {
		
		return new String [] {"Relación de dependencia","Monotributista"};
	}
	
	public String [] getGranularidad() {
		
		return new String [] {"10 min.", "15 min.", "30 min.", "1 hora"};
	}
	
	public DefaultTableModel getTablaEmpleados(String tipo, boolean estado, String filtro) {
		
		EmpleadoDAO empleados = new EmpleadoMySQL();
		String titulo[] = {"Leg.", "Nombre", "Apellido", "DNI", "Dirección", "Teléfono", "E-mail", "Sector", "Cargo", "Tipo"};
		listaEmpleados = empleados.getListado(tipo, estado, filtro);
		String cuerpo[][]=null;
		
		if(listaEmpleados != null) {
			
			cuerpo = new String[listaEmpleados.length][11];
			
			for(int i = 0 ; i < listaEmpleados.length ; i++) {
				
				cuerpo[i][0] = listaEmpleados[i].getLegajo() + "";
				cuerpo[i][1] = listaEmpleados[i].getNombre();
				cuerpo[i][2] = listaEmpleados[i].getApellido();
				cuerpo[i][3] = listaEmpleados[i].getDni();
				cuerpo[i][4] = listaEmpleados[i].getDireccion();
				cuerpo[i][5] = listaEmpleados[i].getTelefono();
				cuerpo[i][6] = listaEmpleados[i].getEmail();
				cuerpo[i][7] = listaEmpleados[i].getSector();
				cuerpo[i][8] = listaEmpleados[i].getCargo();
				cuerpo[i][9] = listaEmpleados[i].getRelacion();
			}
		} else {
			
			cuerpo = null;
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo);
		return tablaModelo;
	}
	
	public void setEmpleado(int pos) {
		
		if(listaEmpleados.length > 0) {
			
			empleado = listaEmpleados[pos];
			añoNacimiento = "";
			mesNacimiento = "";
			diaNacimiento = "";
			actualizar = true;
		}
	}
	
	public String checkInformacion() {
		
		if(empleado.getNombre().length() < 3) {

			return "El nombre debe tener más de dos caracteres.";
		}else if(empleado.getApellido().length() < 3) {
			
			return "El apellido debe tener más de dos caracteres.";
		}else if(empleado.getDni().length() < 7 || !isNumeric(empleado.getDni())) {
			
			return "Error en el formato del DNI (solamente números).";
		}else if(!isNumeric(añoNacimiento) || Integer.parseInt(añoNacimiento) < 1940) {
			
			return "Error en el formato del año.";
		}else if(!isNumeric(mesNacimiento) || Integer.parseInt(mesNacimiento) < 1 || Integer.parseInt(mesNacimiento) > 12 ) {
			
			return "Error en el formato del mes.";
		}else if(!isNumeric(diaNacimiento) || Integer.parseInt(diaNacimiento) < 1 || Integer.parseInt(diaNacimiento) > 31 ) {
			
			return "Error en el formato del día.";
		}else if(empleado.getDireccion().length() < 3) {
			
			return "La dirección no puede estar vacía.";
		}else if(empleado.getTelefono().length() == 0 || !isNumeric(empleado.getTelefono())) {
			
			return "Error en el formato del teléfono (solamente números).";
		}else if(empleado.getCargo().length() < 3) {

			return "El cargo no puede estar vacío.";
		}else if(empleado.getSalario() < 1) {
			
			return "Error en el formato del salario, debe ser numérico y mayor a cero.";
		}
		empleado.setFechaNacimiento(añoNacimiento + "/" + mesNacimiento + "/" + diaNacimiento);
		return "";
	}

	public boolean setNuevoEmpleado() {
		
		boolean bandera = false;
		EmpleadoDAO empleadoDAO = new EmpleadoMySQL();
		bandera = empleadoDAO.setNuevo(empleado);
		clearVariables();
		return bandera;
	}
	
	public void clearVariables() {
		
		DtosEmpleado.empleado = null;
	}
	
	public boolean setActualizarEmpleado() {
		
		boolean bandera = false;
		EmpleadoDAO empleadoDAO = new EmpleadoMySQL();
		bandera = empleadoDAO.update(empleado);
		clearVariables();
		return bandera;
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
					case "CE ":
						buclesVaciado++;	
						break;
						
					case "FE":
					case "FE ":
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
	
	public String getCantidadHoras(JTable tablaOcupacion) {
		
		int cant = 0;
		int tiempo[] = new int [] {6, 4, 2, 1};
		boolean bandera = false;
		
		for(int i = 0; i < tablaOcupacion.getRowCount(); i++) {

			for(int e = 0; e < tablaOcupacion.getColumnCount(); e++) {
				
				if(tablaOcupacion.getValueAt(i, e).equals("O"))
					cant++;
					
				if(tablaOcupacion.getValueAt(i, e).equals("O ") ) {
					
					bandera = !bandera;
					
					if(bandera)
						cant++;
				}
			}
		}
		int resto = cant % tiempo[granAlmacenada];
		String cantidadHoras = (cant / tiempo[granAlmacenada]) + ":";
		cantidadHoras += resto > 0 ?  resto * 60 / tiempo[granAlmacenada]:"00";
		return cantidadHoras;
	}
	
	public DefaultTableModel getListadoEmpleados(String tipo, String filtro) {
		
		EmpleadoDAO empleadosDAO = new EmpleadoMySQL();
		String titulo[] = {"Leg.", "Apellido, nombre", "Sector", "Cargo"};
		listaEmpleados = empleadosDAO.getListado(tipo, true, filtro);
		String cuerpo[][] = null;
		
		if(listaEmpleados != null) {
			
			cuerpo = new String[listaEmpleados.length][4];
			
			for(int i = 0; i < listaEmpleados.length; i++) {
				
				cuerpo[i][0] = listaEmpleados[i].getLegajo() + "";
				cuerpo[i][1] = listaEmpleados[i].getNombre() + ", " + listaEmpleados[i].getApellido();
				cuerpo[i][2] = listaEmpleados[i].getSector();
				cuerpo[i][3] = listaEmpleados[i].getCargo();
			}
		} else
			cuerpo = null;
		
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo);
		return tablaModelo;
	}
	
	public DefaultTableModel getHorarios(int granularidad) {
		
		CronogramaDAO cronogramaDAO = new CronogramaMySQL();
		boolean ocupado[][] = null;

		if(actualizar) {
		
			ocupado = cronogramaDAO.getTablaSemanal(empleado.getLegajo());
			granAlmacenada = cronogramaDAO.getGranularidad();
			
			if(granAlmacenada == -1)
				granAlmacenada = granularidad;
			actualizar = false;
		} else {
			
			granAlmacenada = granularidad;
		}
		String titulo[] = listaHorarios(granAlmacenada);
		String cronograma[][] = new String[6][titulo.length];

		for(int i = 0 ;i < 6 ;i++) {
			
			for(int e = 0 ; e< titulo.length ;e++) {

				if(ocupado != null) {
					
					if(ocupado[i][e]) {
						
						cronograma[i][e] = "O";
						
						if(e > 0 && e < titulo.length - 1) { 
						
							if(!ocupado[i][e-1] || !ocupado[i][e+1])
								cronograma[i][e] = "O ";
						}
					} else {
						
						cronograma[i][e] = " ";
					}
				} else {
					
					cronograma[i][e] = " ";
				}
			}
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cronograma, titulo);
		return tablaModelo;
	}
	
	public boolean setHorarios(int granularidad, JTable tablaOcupacion) {
		
		int cant = 0;
		boolean comienzo;
		boolean bandera = false;
		Horarios horarios[] = null;
		
		for(int i = 0; i < tablaOcupacion.getRowCount(); i++) {
			
			comienzo = false;
			
			for(int e = 0; e < tablaOcupacion.getColumnCount(); e++) {

				if(tablaOcupacion.getValueAt(i, e).equals("O ") && !comienzo) {
				
					comienzo = true;
					cant++;
				} 
				
				if(!tablaOcupacion.getValueAt(i, e).equals("O") && !tablaOcupacion.getValueAt(i, e).equals("O "))
					comienzo = false;
			}
		}
		horarios = new Horarios[cant];
		int pos = -1;

		for(int i = 0; i < tablaOcupacion.getRowCount(); i++) {
		
			comienzo = false;
			
			for(int e = 0; e < tablaOcupacion.getColumnCount(); e++) {

				if(tablaOcupacion.getValueAt(i, e).equals("O ") && !comienzo) {
					
					pos++;
					horarios[pos] = new Horarios();
					comienzo = true;
					horarios[pos].setDia(i); 
					horarios[pos].setHora(listaHorarios(granularidad)[e]);
					horarios[pos].setGranularidad(granularidad);
					horarios[pos].setIdPertenece(empleado.getLegajo());
					cant = 0;
				}
		
				if(!tablaOcupacion.getValueAt(i, e).equals("O") && !tablaOcupacion.getValueAt(i, e).equals("O "))
					comienzo = false;
				
				if(comienzo) {

					cant++;
					horarios[pos].setDuración(cant);
				}
			}
		}
		CronogramaDAO cronogramaDAO = new CronogramaMySQL();
		bandera = cronogramaDAO.setCronograma(horarios);
		return bandera;
	}
	
	public String getNuevoLegajo() {
		
		EmpleadoDAO empleados = new EmpleadoMySQL();
		return empleados.getLegajoLibre() + "";
	}

	public String getLegajo() {
		
		return empleado.getLegajo() + "";
	}
	
	public String getSalario() {
		
		return empleado.getSalario() + "";
	}
	
	public void setSalario(String salario) {
		
		try {
		
			DtosEmpleado.empleado.setSalario(Float.parseFloat(salario));
		} catch (Exception e) {

			DtosEmpleado.empleado.setSalario(0);
		}
	}
	
	public boolean getEstado() {
		
		return empleado.getEstado() == 1? true:false;
	}

	public void setEstado(int estado) {
		
		if(estado == 0 && empleado.getEstado() == 1)
			empleado.setFechaBaja("cargar baja");
		else
			empleado.setFechaBaja(null);
		DtosEmpleado.empleado.setEstado(estado);
	}
	
	public String getSector() {
		
		return empleado.getSector();
	}

	public void setSector(String sector) {
		
		DtosEmpleado.empleado.setSector(sector);
	}
	
	public String getCargo() {
		
		return empleado.getCargo();
	}

	public void setCargo(String cargo) {
		
		DtosEmpleado.empleado.setCargo(cargo);
	}
	
	public String getRelacion() {
		
		return empleado.getRelacion();
	}

	public void setRelacion(String relacion) {
		
		DtosEmpleado.empleado.setRelacion(relacion);
	}
	
	public String getDni() {
		
		return empleado.getDni();
	}

	public void setDni(String dni) {
		
		DtosEmpleado.empleado.setDni(dni);
	}
	
	public String getNombre() {
		
		return empleado.getNombre();
	}

	public void setNombre(String nombre) {
		
		if(DtosEmpleado.empleado == null)
			DtosEmpleado.empleado = new Empleado();
		DtosEmpleado.empleado.setNombre(nombre);
	}

	public String getApellido() {
		
		return (empleado != null? empleado.getApellido():null);
	}

	public void setApellido(String apellido) {
		
		DtosEmpleado.empleado.setApellido(apellido);;
	}

	public String getDireccion() {
		
		return empleado.getDireccion();
	}

	public void setDireccion(String direccion) {
		
		DtosEmpleado.empleado.setDireccion(direccion);;
	}
	
	public String getFechaNacimiento() {
		
		return empleado.getFechaNacimiento();
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		
		DtosEmpleado.empleado.setFechaNacimiento(fechaNacimiento);
	}

	public String getTelefono() {
		
		return empleado.getTelefono();
	}

	public void setTelefono(String telefono) {
		
		DtosEmpleado.empleado.setTelefono(telefono);
	}

	public String getEmail() {
		
		return empleado.getEmail();
	}

	public void setEmail(String email) {
		
		DtosEmpleado.empleado.setEmail(email);
	}

	public String getAñoNacimiento() {
		
		String[] partes = empleado.getFechaNacimiento().split("-");
		añoNacimiento = partes[0];
		return añoNacimiento;
	}
	
	public void setAñoNacimiento(String añoNacimiento) {
		
		this.añoNacimiento = añoNacimiento;
	}

	public String getMesNacimiento() {
		
		String[] partes = empleado.getFechaNacimiento().split("-");
		mesNacimiento = partes[1];
		return mesNacimiento;
	}
	
	public void setMesNacimiento(String mesNacimiento) {
		
		this.mesNacimiento = mesNacimiento;
	}

	public String getDiaNacimiento() {
		
		String[] partes = empleado.getFechaNacimiento().split("-");
		diaNacimiento = partes[2];
		return diaNacimiento;
	}
	
	public void setDiaNacimiento(String diaNacimiento) {
		
		this.diaNacimiento = diaNacimiento;
	}

	public String getMsgError() {
		
		return msgError;
	}
	
	public int getGranAlmacenada() {
		
		return granAlmacenada;
	}
	
	private boolean isNumeric(String cadena) {
		
		try {
			
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException e){
			
			return false;
		}
	}
	
	private String [] listaHorarios(int granularidad) {
		
		String listado[] = null;
		int incremento = 0;
		
		switch(granularidad) {
		
			case 0: 
				listado = new String[97];
				incremento = 10;
				break;
				
			case 1:
				listado = new String[65];
				incremento = 15;
				break;
				
			case 2:
				listado = new String[33];
				incremento = 30;
				break;	
				
			default:
				listado = new String[17];
				incremento = 60;
		}
		int hora = 7;
		int minutos = 0;
		listado[0] = "7:00";
		
		for(int i = 1; i < listado.length; i++) {
			
			if((minutos + incremento) < 60) {
				
				minutos += incremento;
			} else {
				
				minutos = 0;
				hora++;
			}
			String min = minutos == 0? "00":minutos+"";
			listado[i] = hora + ":" + min;
		}
		return listado;
	}
}