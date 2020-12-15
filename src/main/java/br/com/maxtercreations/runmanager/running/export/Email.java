package br.com.maxtercreations.runmanager.running.export;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

import br.com.maxtercreations.runmanager.displays.StartDisplay;
import br.com.maxtercreations.runmanager.utilitaries.callback.Callback;

public class Email {

	public static void createEmailWithAttachment(String to, String from, String subject, String bodyText, File file, Callback error) {
		try {
			MultiPartEmail email = new MultiPartEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(587);
			email.setAuthenticator(new DefaultAuthenticator("shoppingmanagersystem@gmail.com", "Maxter_shopping"));
			email.setSSL(true);
			email.setFrom("shoppingmanagersystem@gmail.com");
			email.setSubject(subject);
			email.setMsg(bodyText);
			email.addTo(to);

			EmailAttachment attachment = new EmailAttachment();
			attachment.setPath(file.getAbsolutePath());
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setName(file.getName());

			email.attach(attachment);
			email.send();
		} catch (Exception e) {
			error.done();
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void send(String to, File file, Callback error) {
		String subject = "Relatório de Corredores (" + new SimpleDateFormat("dd/MM").format(new Date()) + ")";
		String bodyText = "<time>, segue em anexo o relatório de corredores do dia <date>.\n\nPara quaisquer dúvidas contate-me no WhatsApp. (19) 99838-4331.\n\nObrigado.\nEssa é uma mensagem gerada automaticamente pelo sistema.";

		Date date = new Date();
		
		String time = "Bom dia";
		
		if (date.getHours() >= 12)
			time = "Boa tarde";
		if (date.getHours() >= 19)
			time = "Boa noite";
		
		String dateF = new SimpleDateFormat("dd/MM/yyyy").format(date);
		
		String body = bodyText.replaceAll("<time>", time).replaceAll("<date>", dateF);
		
		new Thread(() -> {
			createEmailWithAttachment(to, "shoppingmanagersystem@gmail.com", subject, body, file, error);
						
			new StartDisplay();
		}).start();
	}

}