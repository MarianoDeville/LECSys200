package modelo;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import dao.EstadisticasDAO;
import dao.EstadisticasMySQL;

public class DtosEstadisticas {
	
	private static Estadisticas estadistica;
	private Estadisticas estadisticas[];

	public TableModel getTablaCobros(String año) {
		
		EstadisticasDAO estadisticasDAO = new EstadisticasMySQL();
		estadisticas = estadisticasDAO.getEstadisticasAnuales(año);;
		Object tabla[][] = new Object[estadisticas.length + 1][10];
		String titulo[] = new String[] {"Mes", "Estud.", "Faltas", "Empl.", "Faltas", "Ingresos", "Sueldos", "Compras", "Servicios", "Utilidad neta"};
		float sumaIngresos = 0;
		float sumaSueldos = 0;
		float sumaCompras = 0;
		float sumaServicios = 0;
		float acumulado = 0;
		
		for(int i = 0; i < tabla.length - 1; i++) {
			
			tabla[i][0] = estadisticas[i].getMes();
			tabla[i][1] = estadisticas[i].getCantidadEstudientas();
			tabla[i][2] = estadisticas[i].getFaltasEstudiantes();
			tabla[i][3] = estadisticas[i].getCantidadEmpleados();
			tabla[i][4] = estadisticas[i].getFaltasEmpleados();
			tabla[i][5] = String.format("%.2f", estadisticas[i].getIngresos());
			tabla[i][6] = String.format("%.2f", estadisticas[i].getSueldos());
			tabla[i][7] = String.format("%.2f", estadisticas[i].getCompras());
			tabla[i][8] = String.format("%.2f", estadisticas[i].getServicios());
			tabla[i][9] = String.format("%.2f", estadisticas[i].getIngresos() - estadisticas[i].getSueldos() - estadisticas[i].getCompras());
			sumaIngresos += estadisticas[i].getIngresos();
			sumaSueldos += estadisticas[i].getSueldos();
			sumaCompras += estadisticas[i].getCompras();
			sumaServicios += estadisticas[i].getServicios();
			acumulado += estadisticas[i].getIngresos() - estadisticas[i].getSueldos() - estadisticas[i].getCompras() - estadisticas[i].getServicios();
		}
		tabla[tabla.length - 1][0] = "";
		tabla[tabla.length - 1][1] = "";
		tabla[tabla.length - 1][2] = "";
		tabla[tabla.length - 1][3] = "";
		tabla[tabla.length - 1][4] = "";
		tabla[tabla.length - 1][5] = String.format("%.2f", sumaIngresos);
		tabla[tabla.length - 1][6] = String.format("%.2f", sumaSueldos);
		tabla[tabla.length - 1][7] = String.format("%.2f", sumaCompras);
		tabla[tabla.length - 1][8] = String.format("%.2f", sumaServicios);
		tabla[tabla.length - 1][9] = String.format("%.2f", acumulado);
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		return tablaModelo;
	}
	
	public String [] getAños() {
		
		EstadisticasDAO estadisticasDAO = new EstadisticasMySQL();
		return estadisticasDAO.getListadoAños();
	}
	
	public void setSeleccion(int pos) {
		
		estadistica = estadisticas[pos];
	}
	
	public int getId() {
		
		return estadistica.getId();
	}
	
	public int getMes() {
		
		return estadistica.getMes();
	}

	public int getCantidadEstudientas() {
		
		return estadistica.getCantidadEstudientas();
	}

	public int getFaltasEstudiantes() {
		
		return estadistica.getFaltasEstudiantes();
	}

	public int getCantidadEmpleados() {
		
		return estadistica.getCantidadEmpleados();
	}

	public int getFaltasEmpleados() {
		
		return estadistica.getFaltasEmpleados();
	}

	public float getIngresos() {
		
		return estadistica.getIngresos();
	}

	public float getSueldos() {
		
		return estadistica.getSueldos();
	}

	public float getCompras() {
		
		return estadistica.getCompras();
	}

	public float getServicios() {
		
		return estadistica.getServicios();
	}

	public String getFecha() {
		
		return estadistica.getFecha();
	}
}