package modelo;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import dao.EstadisticasDAO;
import dao.EstadisticasMySQL;

public class DtosEstadisticas {

	public TableModel getTablaCobros(String año) {
		
		EstadisticasDAO estadisticasDAO = new EstadisticasMySQL();
		Estadisticas anual[] = estadisticasDAO.getEstadisticasAnuales(año);;
		Object tabla[][] = new Object[anual.length + 1][9];
		String titulo[] = new String[] {"Mes", "Estud.", "Faltas", "Empl.", "Faltas", "Ingresos", "Sueldos", "Compras", "Utilidad neta"};
		float sumaIngresos = 0;
		float sumaSueldos = 0;
		float sumaCompras = 0;
		float acumulado = 0;
		
		for(int i = 0; i < tabla.length - 1; i++) {
			
			tabla[i][0] = anual[i].getMes();
			tabla[i][1] = anual[i].getCantidadEstudientas();
			tabla[i][2] = anual[i].getFaltasEstudiantes();
			tabla[i][3] = anual[i].getCantidadEmpleados();
			tabla[i][4] = anual[i].getFaltasEmpleados();
			tabla[i][5] = String.format("%.2f", anual[i].getIngresos());
			tabla[i][6] = String.format("%.2f", anual[i].getSueldos());
			tabla[i][7] = String.format("%.2f", anual[i].getCompras());
			tabla[i][8] = String.format("%.2f", anual[i].getIngresos() - anual[i].getSueldos() - anual[i].getCompras());
			sumaIngresos += anual[i].getIngresos();
			sumaSueldos += anual[i].getSueldos();
			sumaCompras += anual[i].getCompras();
			acumulado += anual[i].getIngresos() - anual[i].getSueldos() - anual[i].getCompras();
		}
		tabla[tabla.length - 1][0] = "";
		tabla[tabla.length - 1][1] = "";
		tabla[tabla.length - 1][2] = "";
		tabla[tabla.length - 1][3] = "";
		tabla[tabla.length - 1][4] = "";
		tabla[tabla.length - 1][5] = String.format("%.2f", sumaIngresos);
		tabla[tabla.length - 1][6] = String.format("%.2f", sumaSueldos);
		tabla[tabla.length - 1][7] = String.format("%.2f", sumaCompras);
		tabla[tabla.length - 1][8] = String.format("%.2f", acumulado);
		DefaultTableModel tablaModelo = new DefaultTableModel(tabla, titulo);
		return tablaModelo;
	}
	
	public String [] getAños() {
		
		EstadisticasDAO estadisticasDAO = new EstadisticasMySQL();
		return estadisticasDAO.getListadoAños();
	}
}