package modelo;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import dao.ProveedoresDAO;
import dao.ProveedoresMySQL;

public class DtosProveedores {

	private ProveedoresDAO proveedoresDAO = new ProveedoresMySQL();
	private static Proveedor proveedor;
	private Proveedor proveedores[];
	private String mensageError;
	private int cantidadContactos = 0;
	private Object tabla[][];
	
	public DefaultTableModel getTablaProveedores(String filtro, boolean estado) {
		
		proveedoresDAO = new ProveedoresMySQL();
		String titulo[] = new String[] {"Razón social", "CUIT", "Dirección", "Teléfonos", "E-mail"};
		tabla = null;
		proveedores = proveedoresDAO.getListado(filtro, estado);
		
		if(proveedores != null) {
			
			tabla = new Object[proveedores.length][5];
		
			for(int i = 0; i < tabla.length;i++) {
				
				tabla[i][0] = proveedores[i].getNombre();
				tabla[i][1] = proveedores[i].getCuit();
				tabla[i][2] = proveedores[i].getDireccion();
				tabla[i][3] = "";
				tabla[i][4] = "";
				int tam = proveedores[i].getContactos().length;
				
				for(int e = 0; e < tam; e++) {
					 
					if(!tabla[i][3].equals("") && !proveedores[i].getContactos()[e].getTelefono().equals(""))
						tabla[i][3] += ", ";
					tabla[i][3] += proveedores[i].getContactos()[e].getTelefono();

					if(!tabla[i][4].equals("") && !proveedores[i].getContactos()[e].getEmail().equals(""))
						tabla[i][4] += ", ";
					tabla[i][4] += proveedores[i].getContactos()[e].getEmail();
				}
			}
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla,titulo);
		return respuesta;
	}
	
	public void setProveedorSeleccionado(int pos) {

		proveedor = proveedores[pos];
	}
	
	public String[] getListaCondiciones() {
		
		return new String[] {"Resp. Inscripto", "Monotributista", "Resp. No Inscripto", "Exento"};
	}

	public DefaultTableModel getTablaContactos(JTable contactos, int elemento) {
		
		mensageError = "";
		String titulo[] = new String[] {"Nombre del contacto", "Sector", "Teléfono", "E-mail"};
		tabla = new Object[cantidadContactos][4];
		
		if(proveedor == null)
			proveedor = new Proveedor();
		
		if(proveedor.getContactos() != null && proveedor.getContactos().length != 0) {
			
			cantidadContactos = proveedor.getContactos().length;
			tabla = new Object[cantidadContactos][4];
			
			for(int i = 0; i < tabla.length; i++) {
				
				tabla[i][0] =  proveedor.getContactos()[i].getNombre();
				tabla[i][1] =  proveedor.getContactos()[i].getSector();
				tabla[i][2] =  proveedor.getContactos()[i].getTelefono();
				tabla[i][3] =  proveedor.getContactos()[i].getEmail();
			}
			proveedor.setContactos(null);
		}
		int e = 0;
			
		for(int i = 0; i < contactos.getRowCount(); i++) {

			if(elemento != i || cantidadContactos > contactos.getRowCount()) {		
				
				tabla[e][0] = contactos.getValueAt(i, 0);
				tabla[e][1] = contactos.getValueAt(i, 1);
				tabla[e][2] = contactos.getValueAt(i, 2);
				tabla[e][3] = contactos.getValueAt(i, 3);
				e++;
			}
		}
		DefaultTableModel respuesta = new DefaultTableModel(tabla, titulo){

			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return true;
			}
		};
		return respuesta;
	}
	
	public void setNuevoContacto(String operación) {
		
		if(operación.equals("+")) {
			
			cantidadContactos++;	
		} else if(operación.equals("-") && cantidadContactos > 0) {
			
			cantidadContactos--;
			return;
		}
		
		if(cantidadContactos == 0) {
			
			mensageError = "No existen elementos para borrar.";
			return;
		}
		mensageError = "";
	}
	
	public boolean setGuardar(JTable contactos) {
		
		if(!isInfoCorrecta(contactos, true))
			return false;
		
		if(proveedoresDAO.setNuevo(proveedor)) {
			
			mensageError = "La información se guardó correctamente.";
			return true;
		} 
		mensageError = "Error al intentar guardar la información.";
		return false;
	}

	public boolean setActualizar(JTable contactos) {
		
		if(!isInfoCorrecta(contactos, false))
			return false;

		if(proveedoresDAO.update(proveedor)) {
			
			mensageError = "La información se guardó correctamente.";
			return true;
		} 
		mensageError = "Error al intentar guardar la información.";
		return false;
	}

	public String getMensageError() {
		
		return mensageError;
	}

	public void setNombre(String nombre) {
		
		proveedor.setNombre(nombre);
	}
	
	public String getNombre() {
		
		return proveedor.getNombre();
	}
	
	public void setDirección(String dirección) {
		
		proveedor.setDireccion(dirección);;
	}

	public String getDirección() {
		
		return proveedor.getDireccion();
	}
	
	public void setSituaciónFiscal(String situaciónFiscal) {
		
		proveedor.setTipo(situaciónFiscal);;
	}
	
	public String getSituaciónFiscal() {
		
		return proveedor.getTipo();
	}

	public void setCuit(String cuit) {
		
		proveedor.setCuit(cuit);
	}

	public String getCuit() {
		
		return proveedor.getCuit();
	}
	
	public void setEstado(boolean estado) {
		
		proveedor.setEstado(estado?1:0);
	}
	
	public boolean isEstado() {
		
		return proveedor.getEstado()==1?true:false;
	}

	private boolean isNumérico(String valor) {
		
		try {
			
			Long.parseLong(valor);
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	private boolean isInfoCorrecta(JTable contactos, boolean nuevo) {

		if(proveedor.getNombre().length() < 4) {
			
			mensageError = "El nombre o razón social debe ser más largo.";
			return false;
		} else if(proveedor.getDireccion().length() < 6) {
			
			mensageError = "La dirección ingresada no es válida.";
			return false;
		} else if(!isNumérico(proveedor.getCuit()) || proveedor.getCuit().length() != 11) {

			mensageError = "El CUIT debe ser numérico, sin guiones o espacios y contener 11 dígitos.";
			return false;
		}else if(proveedoresDAO.isCUITExistente(proveedor.getCuit()) && nuevo) {

			mensageError = "El número de cuit ya se encuentra cargado en la base de datos.";
			return false;
		}

		if(contactos.getRowCount() > 0) {
			
			proveedor.setContactos(new Contacto[contactos.getRowCount()]);
			
			for(int i = 0; i < contactos.getRowCount(); i++) {

				if(contactos.getValueAt(i, 0) == null)
					contactos.setValueAt("", i, 0);

				if(contactos.getValueAt(i, 1) == null)
					contactos.setValueAt("", i, 1);
				
				if(contactos.getValueAt(i, 2) == null)
					contactos.setValueAt("", i, 2);

				if(contactos.getValueAt(i, 3) == null)
					contactos.setValueAt("", i, 3);
				
				if(contactos.getValueAt(i, 0).toString().length() < 3) {
					
					mensageError = "Nombre de contacto no válido.";
					proveedor.setContactos(null);
					return false;
				}	

				if(contactos.getValueAt(i, 1).toString().length() < 3) {
					
					mensageError = "Sector no válido.";
					proveedor.setContactos(null);
					return false;
				}	

				if(contactos.getValueAt(i, 2).toString().length() < 3 && 
					contactos.getValueAt(i, 3).toString().length() < 3) {
					
					mensageError = "Debe cargar por lo menos uno de los métodos de contacto.";
					proveedor.setContactos(null);
					return false;
				}
				proveedor.getContactos()[i] = new Contacto();
				proveedor.getContactos()[i].setNombre((String) contactos.getValueAt(i, 0));
				proveedor.getContactos()[i].setSector((String) contactos.getValueAt(i, 1));
				proveedor.getContactos()[i].setTelefono((String) contactos.getValueAt(i, 2));
				proveedor.getContactos()[i].setEmail((String) contactos.getValueAt(i, 3));
			}
		}
		return true;
	}
}
