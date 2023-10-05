package control;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import modelo.DtosConfiguracion;

public class EmailSenderService {

	public boolean mandarCorreo(String destinatario, String asunto, String mensaje) {
		
		DtosConfiguracion config = new DtosConfiguracion();
		Properties propiedad = new Properties();
		propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
		propiedad.setProperty("mail.smtp.port", "587");
		propiedad.setProperty("mail.smtp.auth","true");
		propiedad.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		propiedad.setProperty("mail.smtp.starttls.enable", "true");
		Session sesion = Session.getDefaultInstance(propiedad);
		MimeMessage mail = new MimeMessage(sesion);
		
		try {
		
			mail.setFrom(new InternetAddress(config.getEmailSistema()));
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			mail.setSubject(asunto);
			mail.setText(mensaje);
			Transport transporte = sesion.getTransport("smtp");
			transporte.connect(config.getEmailSistema(),config.getPassSistema());
			transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));          
			transporte.close();
		} catch (Exception e) {
			
			System.out.println("Error al intentar enviar el email.");
			CtrlLogErrores.guardarError(e.getMessage());
			return false;
		}
		return true;
	}
}