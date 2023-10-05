package dao;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import modelo.DtosActividad;
import modelo.DtosConfiguracion;

public class BackupDB {
	
	public static void iniciar() {
	
		long tiempo = System.currentTimeMillis();
		DtosActividad dtosActividad = new DtosActividad();
		DtosConfiguracion dtosConfiguracion = new DtosConfiguracion();
		String nombreArchivo = "Backup ";
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		nombreArchivo += dateFormat.format(Calendar.getInstance().getTime()) + ".sql";

		try {

			Process p = Runtime.getRuntime().exec(dtosConfiguracion.getServiviosMySQL()
												+ " -u " + dtosConfiguracion.getUsuarioBD() 
												+ " -p" + dtosConfiguracion.getPassBD() + " lecsys1");
			InputStream is = p.getInputStream();
			FileOutputStream fos = new FileOutputStream(dtosConfiguracion.getDirectorioBackup() + nombreArchivo);
			byte[] buffer = new byte[1000];
			int leido = is.read(buffer);

			while (leido > 0) {

				fos.write(buffer, 0, leido);
				leido = is.read(buffer);
			}
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tiempo = System.currentTimeMillis() - tiempo;
		dtosActividad.registrarActividad("Backup automático.", "Sistema", tiempo);
	}
}