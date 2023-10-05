package dao;

public interface CronogramaDAO {

	public int getGranularidad();
	public boolean [][] getmatrizDiasHorarios();
	public boolean getCronogramaDias(int idEmpleado);
	public boolean setCronogramaDias(int idEmpleado, String horarios[][], int gran);
}
