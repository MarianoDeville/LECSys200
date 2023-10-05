package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import modelo.DtosConfiguracion;

public class DiscoDAO {

	public static void escribirLog(String msg) {
		
		Calendar fechaSistema = new GregorianCalendar();
		String cuerpo = fechaSistema.getTime().toString() + " - " + msg;
		BufferedWriter bw = null;
	    FileWriter fw = null;

	    try {
	    	
	        File archivo = new File(DtosConfiguracion.getDirectorio() + "\\log.txt");

	        if (!archivo.exists())
	        	archivo.createNewFile();
	        fw = new FileWriter(archivo.getAbsoluteFile(), true);		// flag true, indica adjuntar información al archivo.
	        bw = new BufferedWriter(fw);
	        bw.write(cuerpo + "\r\n");
	    } catch (IOException e) {
	    	
	    	System.err.println("No se pudo escribir en el archivo.");
	    } finally {
	    	
	        try {

	            if (bw != null)
	                bw.close();
	            if (fw != null)
	                fw.close();
	        } catch (IOException ex) {
	        	
	            ex.printStackTrace();
	        }
	    }
	}
	
	public String modificarValores(String valor[]) {

		String msg = "";
		String lineaLeida;
		File archivoTemporal = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		File archivoOrigen = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
		
			archivoTemporal= new File("LECSys.nvo.tmp");
			
			if (!archivoTemporal.exists())
				archivoTemporal.createNewFile();
			fw = new FileWriter(archivoTemporal.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			archivoOrigen = new File ("LECSys.dam");
			fr = new FileReader (archivoOrigen);
			br = new BufferedReader(fr);
			int i = 0;
			
			while((lineaLeida=br.readLine())!=null) {
				
				String[] partes = lineaLeida.split("=");
				
				if(valor[i].equals(partes[0]))	{			
					
					bw.write(partes[0] + "=" + codifico(valor[i+1]) + "\n");
		
					if(i < valor.length)
						i += 2 ;
				}else {
					bw.write(lineaLeida + "\n");
				}
			}
		} catch(Exception e){
		
			msg = "No se encuentra archivo de configuración.";
			System.err.println(e.getMessage());
		} finally {
		
			try {
				
				if (bw != null)
					bw.close();
				
				if (fw != null)
					fw.close();
								
				if(fr != null)  
					fr.close();  
				borrar(archivoOrigen);
				archivoTemporal.renameTo(archivoOrigen);
			       
			} catch (Exception e2) {

				System.err.println(e2.getMessage());
			}
		}
		return msg;
	}

	public String getConfiguracion() {

		DtosConfiguracion config = new DtosConfiguracion();
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String msg = null;

		try {

			archivo = new File ("LECSys.dam");
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			String lineaLeida;
			
			while((lineaLeida=br.readLine())!=null) {
					
				String[] partes = lineaLeida.split("=");
				if(partes.length > 1) {
				
					switch(partes[0]) {
					
					case "DLOG":
						config.setDirectorio(partes[1]);
						break;
						
					case "IPBD":
						config.setServidor(partes[1]);
						break;	
						
					case "USR":
						config.setUsuarioBD(decodifico(partes[1]));
						break;
						
					case "UPAS":
						config.setPassBD(decodifico(partes[1]));
						break;
					
					case "MAIL":
						config.setEmailSistema(decodifico(partes[1]));
						break;
						
					case "MPAS":
						config.setPassSistema(decodifico(partes[1]));
						break;
						
					case "MySQL":
						config.setServiviosMySQL(partes[1]);
						break;

					case "BCKP":
						config.setDirectorioBackup(partes[1]);
						break;
						
					case "INFO":
						config.setEmailInforme(partes[1]);
						break;
					}
				}
			}
		} catch(Exception e){
			
			msg = "No se encuentra archivo de configuración.";
			e.printStackTrace();
		} finally {

			try {
				
				if(fr != null)   
					fr.close();     
			} catch (Exception e2) {
				
				System.err.println(e2.getMessage());
			}
		}
		return msg;
	}
	
	private void borrar(File Ffichero) {
		
		try {
		    
			if(Ffichero.exists())
				Ffichero.delete();
		}catch(Exception e) {
		
			System.err.println(e.getMessage());
		}
	}
	
	private String decodifico(String cuerpo) {

		char temp[] = cuerpo.toCharArray();
		String respuesta = "";
		
		for(int i = 0; i < temp.length; i++) {
			
			respuesta+=(char)((i% 2==0)?(int)temp[i]+3:(int)temp[i]-5);
		}
		return respuesta;
	}
	
	private String codifico(String cuerpo) {
		
		char temp[] = cuerpo.toCharArray();
		String respuesta = "";
		
		for(int i = 0; i < temp.length; i++) {
			
			respuesta+=(char)((i%2==0)?(int)temp[i]-3:(int)temp[i]+5);
		}
		return respuesta;
	}
	
	public boolean isArchivoExiste() {
		
		boolean bandera = true;
		FileReader fr = null;
		BufferedReader br = null;

		try {

			File archivo = new File ("LECSys.dam");
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
		} catch(Exception e){
			
			bandera = false;
		} finally {

			try {
				
				if( null != fr ) {   
					
					fr.close();
					br.close();
				}                  
			} catch (Exception e2) {
				
				System.err.println("Error al intentar cerrar el archivo..");
				System.err.print(e2.getMessage());
			}
		}
		return bandera;
	}
	
	public void setArchivoConfiguración() {
	
		DtosConfiguracion dtosConfig = new DtosConfiguracion();
		BufferedWriter bw = null;
		FileWriter fw = null;
		
		try {
		
			File archivo = new File("LECSys.dam");
			
			if (!archivo.exists())
				archivo.createNewFile();
			fw = new FileWriter(archivo.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			bw.write("LECSys - Archivo de configuración.\n\n");
			bw.write("IPBD=" + dtosConfig.getServidor() + "\n");
			bw.write("USR=" + codifico(dtosConfig.getUsuarioBD()) + "\n");
			bw.write("UPAS=" + codifico(dtosConfig.getPassBD()) + "\n");
			bw.write("DLOG=" + DtosConfiguracion.getDirectorio() + "\n");
			bw.write("MAIL=\n");
			bw.write("MPAS=\n");
			bw.write("MySQL=\"C:\\Archivos de programa\\MySQL\\MySQL Server 8.0\\bin\\mysqldump\"");
			bw.write("BCKP=\"" + DtosConfiguracion.getDirectorio() + "\"\n");
			bw.write("INFO=\n");
		} catch (IOException e) {
		
			System.err.println("No se pudo escribir en el archivo.");
			System.err.print(e.getMessage());
		} finally {
		
			try {
			
				if (bw != null)
					bw.close();
				
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
			
				System.err.println("Error al intentar cerrar el archivo..");
				System.err.print(ex.getMessage());
			}
		}
	}
}