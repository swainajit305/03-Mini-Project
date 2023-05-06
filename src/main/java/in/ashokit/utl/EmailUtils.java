package in.ashokit.utl;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


public class EmailUtils {

	@Autowired
	private JavaMailSender sender;

	public boolean sendEmails(String to, String subject, String body) {
		boolean isSent = false;
		try {
			MimeMessage createMimeMessage = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(createMimeMessage);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);

			sender.send(createMimeMessage);
			isSent = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return isSent;

	}

}
