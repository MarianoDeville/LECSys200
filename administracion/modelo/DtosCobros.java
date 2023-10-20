package modelo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import control.CtrlLogErrores;
import dao.CobrosMySQL;
import dao.GrupoFamiliarDAO;
import dao.AlumnoDAO;
import dao.AlumnoMySQL;
import dao.CobrosDAO;
import dao.GrupoFamiliarMySQL;

public class DtosCobros {
	
	private GregorianCalendar fechaSistema;
	private GrupoFamiliarDAO grupoFamiliarDAO;
	private CobrosDAO cobrosDAO;
	private Cobros cobros[];
	private GrupoFamiliar familias[];
	private Alumno alumnos[];
	private static GrupoFamiliar familia;
	private static Cobros cobro = new Cobros();
	private static boolean reinscripción;
	private boolean enviarEmail;
	private float descuentoContado;
	private float inscripcion;
	private float recargoMora;	

	/*
	private static String matrizSelec[][] = null;
	private static int cantidadCuotas;
	private String tablaRespuesta[][];
	private int elementoSeleccionado;
	private int cantidadCuotasSeleccionadas = 1;
	private Calendar fechaSistema;
	*/

	public TableModel getTablaAlumnos(boolean reinscripción, boolean todos, String busqueda) {
		
		String titulo[] = null;
		Object cuerpo[][] = null;
		DtosCobros.reinscripción = reinscripción;
	
		if(reinscripción) {
			
			titulo = new String[] {"Id", "Nombre familiar", "Cant. Integrantes",  "Email", "Sel"};
			grupoFamiliarDAO = new GrupoFamiliarMySQL();
			familias = grupoFamiliarDAO.getListado("ESTADO", "0", true, busqueda);
			cuerpo = new Object[familias.length][5];	
		} else {
		
			titulo = new String[] {"Leg.","Apellido","Nombre", "Dirección", "Sel"};
			AlumnoDAO alumnosDAO = new AlumnoMySQL();
			alumnos = alumnosDAO.getListado("GF", "", false, "apellido", busqueda);
			cuerpo = new Object[alumnos.length][5];
		}
		
		for(int i = 0; i < (reinscripción? familias:alumnos).length; i++) {

			cuerpo[i][0] = reinscripción? familias[i].getId(): alumnos[i].getLegajo();
			cuerpo[i][1] = reinscripción? familias[i].getNombre(): alumnos[i].getApellido();
			cuerpo[i][2] = reinscripción? familias[i].getCantIntegrantes(): alumnos[i].getNombre();
			cuerpo[i][3] = reinscripción? familias[i].getEmail(): alumnos[i].getDireccion();
			cuerpo[i][4] = todos;
		}
		DefaultTableModel tablaAlumnos = new DefaultTableModel(cuerpo, titulo){
				
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				
				return column == 4? true: false;
			}
			public Class<?> getColumnClass(int column) {
				
				return column == 4? Boolean.class: String.class;
		    }
		};
		return tablaAlumnos;
	}

	public void setSeleccionados(boolean seleccionados[]) {
		
		if(reinscripción) {

			for(int i = 0 ; i < familias.length ; i++) {
				
				if(seleccionados[i]) {
				
					familia = familias[i];
					break;
				}
			}
		} else {

			int cantSel = 0;
		
			for(int i = 0 ; i < seleccionados.length ; i++) {
				
				if(seleccionados[i])
					cantSel++;
			}

			Alumno alumnosElegidos[] = new Alumno[cantSel];
			familia = new GrupoFamiliar();
			familia.setIntegrantes(alumnosElegidos);
			int e = 0;
	
			for(int i = 0 ; i < alumnos.length ; i++) {
					
				if(seleccionados[i]) {
				
					alumnosElegidos[e] = alumnos[i];
					e++;
				}
			}
			familia.setEmail(cantSel==1? alumnosElegidos[0].getEmail(): "");
			familia.setNombre(cantSel==1? alumnosElegidos[0].getApellido() + " " + alumnosElegidos[0].getNombre(): "");
			familia.setDescuento(0);
		}
	}
	
	public TableModel getTablaSeleccionados() {
		
		String titulo[] = {"Leg.", "Apellido", "Nombre", "Drirección", "Curso", "Valor cuota"};
		Object respuesta[][] = new Object[familia.getIntegrantes().length][6];
		
		for(int i =0; i < familia.getIntegrantes().length; i++) {
			
			respuesta[i][0] = familia.getIntegrantes()[i].getLegajo();
			respuesta[i][1] = familia.getIntegrantes()[i].getApellido();
			respuesta[i][2] = familia.getIntegrantes()[i].getNombre();
			respuesta[i][3] = familia.getIntegrantes()[i].getDireccion();
			respuesta[i][4] = familia.getIntegrantes()[i].getCurso().getAño() + " " +
								familia.getIntegrantes()[i].getCurso().getNivel() + " " +
								familia.getIntegrantes()[i].getCurso().getNombreProfesor();
			respuesta[i][5] = familia.getIntegrantes()[i].getCurso().getPrecio();
		}
		DefaultTableModel tablaAlumnos = new DefaultTableModel(respuesta,titulo);
		return tablaAlumnos;
	}
	
	public DefaultTableModel getTablaFamilias(boolean est, String busqueda) {
		
		grupoFamiliarDAO = new GrupoFamiliarMySQL();
		familias = grupoFamiliarDAO.getListado("", "", est, busqueda);
		String titulo[] = new String[] {"Nombre", "Integrantes", "Sel"};
		Object cuerpo[][] = new Object[familias.length][3];

		for(int i = 0 ; i < familias.length ; i++) {
			
			cuerpo[i][0] = familias[i].getNombre();
			cuerpo[i][1] = "";
			cuerpo[i][2] = false;

			for(int e = 0; e < familias[i].getIntegrantes().length ; e++) {
			
				cuerpo[i][1] += familias[i].getIntegrantes()[e].getNombre() + " " + familias[i].getIntegrantes()[e].getApellido();
				
				if(e < familias.length - 1)
					cuerpo[i][1] += ", ";
			}
		}
		DefaultTableModel tablaFamilia = new DefaultTableModel(cuerpo, titulo) {
			
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				
				return column == 2? true: false;
			}
			public Class<?> getColumnClass(int column) {
				
				return column == 2? Boolean.class: String.class;
		    }
		};
		return tablaFamilia;
	}
	
	public void setFamiliaSeleccionada(int pos) {
		
		familia.setNombre(familias[pos].getNombre());
		familia.setEmail(familias[pos].getEmail());
		familia.setDescuento(familias[pos].getDescuento());
	}
	
	public float getCalculoMontoTotal() {

		float descuentoDeGrupo = 0;
		float montoTotal = 0;
		float sumaCuotas = 0;
		
		for(int i = 0; i < familia.getIntegrantes().length; i++) {
			
			sumaCuotas += familia.getIntegrantes()[i].getCurso().getPrecio();
		}
		String descripcion = "Inscripción: " + String.format("%.2f",inscripcion) + ", primer cuota: " + String.format("%.2f",sumaCuotas);
		
		if(descuentoContado > 0)
			descripcion += ", descuento pago contado: " + String.format("%.2f",descuentoContado);
		montoTotal = sumaCuotas + inscripcion;
		descuentoDeGrupo = sumaCuotas * familia.getDescuento() / 100;
		montoTotal -= descuentoDeGrupo; 
		
		if(familia.getDescuento() > 0)
			descripcion += ", descuento grupo familiar: " + descuentoDeGrupo;
		montoTotal -= descuentoContado;
		cobro.setConcepto(descripcion);
		cobro.setMonto(montoTotal);
		return montoTotal;
	}

	public String validarInformación(boolean validarNombre, boolean validarInscripcion) {
		
		grupoFamiliarDAO = new GrupoFamiliarMySQL();
	
		if(grupoFamiliarDAO.isNombreFamilia(familia.getNombre()) && !reinscripción && validarNombre)
			return "El nombre de familia ya está en uso.";
		
		if(familia.getNombre().length() < 5)
			return "El nombre de familia es demasiado corto.";
		
		if(enviarEmail) {
			
			if(familia.getEmail().length() < 6)
				return "Debe llenar el campo email.";
			
			if(!familia.getEmail().contains("@") || familia.getEmail().contains(" "))
				return "Error en el formato del email.";
			
			String partes[] = familia.getEmail().split("@");
			
			try {
				
				if(partes[1].length() < 3 || !partes[1].contains("."))
					return "Error en el formato del email.";	
				
			} catch (Exception e) {
				
				return "Error en el formato del email.";	
			}
		}
		
		if(inscripcion < 1 && validarInscripcion)
			return "La inscripción debe tener un valor.";
		
		return "";
	}

	public boolean guardarCobroGrupo() {
		
		cobro.setIdGrupoFamiliar(familia.getId());
		familia.setEstado(1);
		grupoFamiliarDAO = new GrupoFamiliarMySQL();
		cobrosDAO = new CobrosMySQL();

		if(reinscripción) {
			
			if(!grupoFamiliarDAO.update(familia))
				return false;
		}else {
		
			cobro.setIdGrupoFamiliar(grupoFamiliarDAO.setGrupoFamiliar(familia));
			
			if(cobro.getIdGrupoFamiliar() == 0)
				return false;
		}
		cobro.setId(cobrosDAO.setCobro(cobro));
		
		if(cobro.getId() == 0)
			return false;
		return true;
	}
	
	public String getCuerpoEmail() {
		
		String temp = "Por la presente se deja constancia del pago realizado el día " + getFechaActual("") 
					+ ", y el detalle del mismo:\n\n" + cobro.getConcepto().replaceAll(", ", "\n");
		temp += "\nTotal: " + String.format("%.2f",cobro.getMonto());
		return temp;
	}

	public void setFamiliaExistente(int pos) {
		
		Alumno integrantes[] = new Alumno[familia.getIntegrantes().length + familias[pos].getIntegrantes().length];
		System.arraycopy(familias[pos].getIntegrantes(), 0, integrantes, 0, familias[pos].getIntegrantes().length);
		System.arraycopy(familia.getIntegrantes(), 0, integrantes, familias[pos].getIntegrantes().length, familia.getIntegrantes().length);
		familia = familias[pos];
		familia.setEstado(1);
		familia.setIntegrantes(integrantes);
		familia.setCantIntegrantes(integrantes.length);
	}
	
	public boolean guardarCobroGrupoExistente() {
		
		grupoFamiliarDAO = new GrupoFamiliarMySQL();

		if(!grupoFamiliarDAO.update(familia))
			return false;
		cobrosDAO = new CobrosMySQL();
		cobro.setIdGrupoFamiliar(familia.getId());		
		cobro.setId(cobrosDAO.setCobro(cobro));
		
		if(cobro.getId() == 0)
			return false;
		return true;
	}
	
	public String getFechaActual(String formato) {
		
		fechaSistema = new GregorianCalendar();
		String fecha = null;
		if(formato.equals("A")) {
		
			fecha = fechaSistema.get(Calendar.YEAR) + "/" 
				  + (fechaSistema.get(Calendar.MONTH)+1) + "/" 
				  + fechaSistema.get(Calendar.DAY_OF_MONTH);
		} else {
			
			fecha = fechaSistema.get(Calendar.DAY_OF_MONTH) + "/"
				  + (fechaSistema.get(Calendar.MONTH)+1) + "/"
				  + fechaSistema.get(Calendar.YEAR);
		}
		return fecha;
	}

	public String getNumeroRecibo() {
		
		String numero = String.format("%012d", cobro.getId());
		StringBuilder sb = new StringBuilder(numero);
		sb.insert(4, '-');
		return sb.toString();
	}
	
	public void deleteInfo() {

		familia = null;
	}

	public TableModel getTablaDeudores(String busqueda, boolean pagoAdelantado) {
		
		grupoFamiliarDAO = new GrupoFamiliarMySQL();
		String titulo[] = new String[] {"Nombre", "Integrantes",  "Cuotas" ,"Valor cuota", "Desc.", "Total"};
		familias = grupoFamiliarDAO.getListado("", "", pagoAdelantado, busqueda);
		Object cuerpo[][] = new Object[familias.length][8];
		
		for(int i = 0; i < cuerpo.length; i ++) {

			float calculo = familias[i].getDeuda() * familias[i].getSumaPrecioCuotas();
			calculo -= calculo * familias[i].getDescuento() /100;			
			cuerpo[i][0] = familias[i].getNombre();
			cuerpo[i][1] = familias[i].getCantIntegrantes();
			cuerpo[i][2] = familias[i].getDeuda();
			cuerpo[i][3] = familias[i].getSumaPrecioCuotas();
			cuerpo[i][4] = familias[i].getDescuento();
			cuerpo[i][5] = String.format("%.2f",calculo);			
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(cuerpo, titulo);
		return tablaModelo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getNombre() {
		
		return familia.getNombre();
	}
	
	public void setNombre(String nombreFamilia) {
		
		familia.setNombre(nombreFamilia);
		cobro.setNombre(nombreFamilia);
	}
	
	public String getEmail() {
		
		return familia.getEmail();
	}
	
	public void setEmail(String mail) {
		
		familia.setEmail(mail);
	}
	
	public String getDescuentoGrupo() {
		
		return familia.getDescuento() + "";
	}
	
	public String setDescuentoGrupo(String descuento) {
		
		String mensage = null;
		familia.setDescuento(0);
		
		if(descuento.length() > 0) {
			
			try {
				
				familia.setDescuento(Integer.parseInt(descuento));
			} catch (Exception e) {

				mensage = "El valor del descuento por grupo familiar debe ser numérico.";
			}
		}
		return mensage;
	}

	public boolean getReinscripcion() {
		
		return DtosCobros.reinscripción;
	}
	
	public String setDescuentoContado(String desContado) {

		String mensage = null;
		descuentoContado = 0;
		
		if(desContado.length() > 0) {
			
			try {
				
				descuentoContado = Float.parseFloat(desContado);
			} catch (Exception e) {

				mensage = "El valor del descuento por pago en efectivo debe ser numérico.";
			}
		}
		return mensage;
	}
	
	public String setInscripcion(String inscrip) {

		String mensage = null;
		inscripcion = 0;
		
		if(inscrip.length() > 0) {
			
			try {
				
				inscripcion = Float.parseFloat(inscrip) * familia.getIntegrantes().length;
			} catch (Exception e) {

				mensage = "El valor de la inscrioción debe ser numérico.";
			}
		} else
			mensage = "Inscripción debe tener valor.";
		return mensage;
	}
	
	public void setEnviarEmail(boolean enviar) {
		
		enviarEmail = enviar;
	}

	public String getFactura() {
		
		return cobro.getFactura();
	}
	
	public void setFactura(String numeroFactura) {
		
		cobro.setFactura(numeroFactura);
	}
	
	public float getMontoTotal() {
		
		return cobro.getMonto();
	}
	
	public String getDescripcion() {
		
		return cobro.getConcepto();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean setBorrarDeuda() {
		
		long tiempo = System.currentTimeMillis();
		grupoFamiliarDAO = new GrupoFamiliarMySQL();

		if(!grupoFamiliarDAO.setActualizarDeuda(idFamilia, -cantidadCuotasSeleccionadas))
			return false;

		DtosActividad dtosActividad = new DtosActividad();
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Borrado de deuda. Familia: " + nombre, "Administración", tiempo);
		return true;
	}

	public boolean setActualizarFacturas(String listaFacturas[]) {
		
		int e = 0;
		CobrosMySQL administracionDAO = new CobrosMySQL();
		
		for(int i = 0; i < listaFacturas.length; i++) {
			
			if(!listaFacturas[i].equals(tablaRespuesta[i][5]))
				e++;
		}
		matrizSelec = new String[e][2];
		e = 0;
		
		for(int i = 0; i < tablaRespuesta.length; i++) {
			
			if(!listaFacturas[i].equals(tablaRespuesta[i][5])) {
				
				matrizSelec[e][0] = tablaRespuesta[i][6];
				matrizSelec[e][1] = listaFacturas[i];
				e++;
			}
		}
		return administracionDAO.setActualizarFacturas(matrizSelec);
	}
	
	public int getMesActual() {
		
		fechaSistema = new GregorianCalendar();
		return fechaSistema.get(Calendar.MONTH) + 1;
	}
	
	public String [] getMeses() {
		
		return new String[] {"- - - - -", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
							 "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	}
	
	public String [] getAños() {
		
		fechaSistema = new GregorianCalendar();
		String respuesta[] = new String[5];
		
		for(int i = 4 ; i >= 0 ; i--) {
			
			respuesta[i] = fechaSistema.get(Calendar.YEAR) - i +""; 
		}
		return respuesta;
	}
	
	public TableModel getTablaCobros(int mes, Object año) {
		
		String titulo[] = new String[] {"Fecha", "Nombre", "Concepto", "Hora", "Monto", "Factura"};
		CobrosMySQL administracionDAO = new CobrosMySQL();
		int temp = 0;
		montoTotal = 0;
		
		try {
			
			temp = Integer.parseInt((String)año);
		} catch (Exception e) {
			
			CtrlLogErrores.guardarError("Error al convertir 'Object' año a 'int' en la clase: DtosCobros método: getTablaCobros()");
		}
		tablaRespuesta = administracionDAO.getTablaCobros(temp, mes);
		
		if(tablaRespuesta != null) {
			
			cantElementosSel = tablaRespuesta.length;
			
			for(int i = 0; i < tablaRespuesta.length; i++) {
				montoTotal += Float.parseFloat(tablaRespuesta[i][4].replace(",", "."));
			}
		}
		DefaultTableModel tablaModelo = new DefaultTableModel(tablaRespuesta, titulo){

			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return column > 4? true: false;
			}
		};
		return tablaModelo;
	}
	
	public boolean guardarCobroCuota() {

		CobrosMySQL administracionDAO = new CobrosMySQL();
		
		if(!administracionDAO.setCobro())
			return false;
		nroCobro = administracionDAO.getUltimoRegistro();
		grupoFamiliarDAO = new GrupoFamiliarMySQL();
		
		if(!grupoFamiliarDAO.setActualizarDeuda(idFamilia, -cantidadCuotasSeleccionadas))
			return false;
		return true;
	}
	
	public String getCalculoCobro(String concepto) {
		
		float descuentoDeGrupo = 0;
		montoTotal = sumaCuotas * cantidadCuotasSeleccionadas;
		descripcion = "Cuota correspondiente a " + concepto + ": " + montoTotal;
		descuentoDeGrupo = montoTotal * descuentoGrupo / 100;
		montoTotal -= descuentoDeGrupo; 
		
		if(descuentoGrupo > 0)
			descripcion += ", descuento grupo familiar: " + descuentoDeGrupo;

		if(recargoMora > 0)
			descripcion += ", recargo por pago fuera de término: " + recargoMora;		
		
		if(descuentoContado > 0)
			descripcion += ", descuento pago contado: " + descuentoContado;
		montoTotal += recargoMora;
		montoTotal -= descuentoContado;
		descripcion += ", suma total: " + montoTotal;
		return String.format("%.2f",montoTotal);
	}
	
	public void setCantidadCuotasSeleccionadas(int cantidad) {
		
		cantidadCuotasSeleccionadas = cantidad;
	}

	public String setRecargoMora(String recargo) {
		
		String mensaje = null;
		
		try {
			
			if(recargo.length() > 0)
				recargoMora = Float.parseFloat(recargo);
		} catch (Exception e) {
			
			mensaje = "El valor debe ser numérico.";
		}
		return mensaje;
	}
	
	public String [] getListadoConceptos() {
	
		fechaSistema = new GregorianCalendar();
		int mesActual = fechaSistema.get(Calendar.MONTH);
		String meses[] = new String[] {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		String listaCuotasDeuda[] = new String[cantidadCuotas == 0? 2 : cantidadCuotas + 1];
		listaCuotasDeuda[0] = "Seleccione uno";

		if(cantidadCuotas > 0) {

			if(mesActual + 1 >= cantidadCuotas) {

				listaCuotasDeuda[1] = meses[mesActual-cantidadCuotas+1];
				
				for(int aux = 2; aux < listaCuotasDeuda.length; aux++) {
					
					for(int i = 0; i < aux; i++) {
					
						if(i == 0)
							listaCuotasDeuda[aux] = meses[mesActual - cantidadCuotas+1];
						else
							listaCuotasDeuda[aux] += ", " + meses[mesActual + 1 + i - cantidadCuotas];
					}
				}
			} else {
			
				for(int aux = 1; aux < listaCuotasDeuda.length; aux++) {
					
					String temp = (aux == 1)? " mes.":" meses."; 	
					listaCuotasDeuda[aux] =aux + temp;
				}
			}
		} else {
			
			listaCuotasDeuda[1] = meses[mesActual == 11? 0 : mesActual + 1];	
		}
		return listaCuotasDeuda;
	}

	
	public void setInfoCobro() {

		idFamilia = Integer.parseInt(tablaRespuesta[elementoSeleccionado][0]);
		nombre = tablaRespuesta[elementoSeleccionado][1];
		cantidadCuotas = Integer.parseInt(tablaRespuesta[elementoSeleccionado][3]);
		sumaCuotas = Float.parseFloat(tablaRespuesta[elementoSeleccionado][4].replace(",", "."));
		descuentoGrupo = Integer.parseInt(tablaRespuesta[elementoSeleccionado][5]);		
		grupoFamiliarDAO = new GrupoFamiliarMySQL();
		matrizSelec = grupoFamiliarDAO.getGrupoFamiliar(idFamilia + "");	
		integrantes = matrizSelec.length;
		email = matrizSelec[0][7];
	}

	public void SetIntegrantes(String integrantes) {
		
		DtosCobros.integrantes = Integer.parseInt(integrantes);
	}

	public String getHoraActual() {
		
		fechaSistema = new GregorianCalendar();
	    String hora = (fechaSistema.get(Calendar.AM_PM)==0? fechaSistema.get(Calendar.HOUR):fechaSistema.get(Calendar.HOUR)+12) + ":" 
					+ (fechaSistema.get(Calendar.MINUTE)<10? "0" + fechaSistema.get(Calendar.MINUTE):fechaSistema.get(Calendar.MINUTE)) + ":" 
					+ (fechaSistema.get(Calendar.SECOND)<10? "0" + fechaSistema.get(Calendar.SECOND):fechaSistema.get(Calendar.SECOND));		
		return hora;
	}

	public String [] getIdElementosSeleccionados() {
		
		String id[] = new String[cantElementosSel];
		
		for(int i = 0 ; i < cantElementosSel ; i++) {
			
			id[i] = matrizSelec[i][0];
		}
		return id;
	}
	
	public int getIdFamilia() {
		
		return idFamilia;
	}
	
	public float getSumaCuotas() {

		return sumaCuotas;
	}
	
	public int getCantidadElementosSeleccionados() {
		
		return cantElementosSel;
	}
	
	public void setIdFamilia(int id) {
		
		idFamilia = id;
	}

	public boolean isReinscripcion() {
		
		return reinscripción;
	}

	public void setElementoSeleccionado(int elemento) {
		
		elementoSeleccionado = elemento;
	}
}