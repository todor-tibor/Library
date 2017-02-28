/**
 * 
 */
package com.edu.library.business.util;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jboss.logging.Logger;

import com.edu.library.business.exception.BusinessException;

/**
 * Provides e-mailing related tasks.
 * 
 * @author gallb
 *
 */

@Stateless
public class Mail {
	@Resource(name = "java:jboss/mail/gmail")
	private Session session;

	private Logger oLogger = Logger.getLogger(Mail.class);

	/**
	 * 
	 * @param addresses
	 *            - e-mail address
	 * @param topic
	 *            - message topic
	 * @param textMessage
	 *            - message body
	 */
	public void send(String addresses, String topic, String textMessage) {

		try {

			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addresses));
			message.setSubject(topic);
			message.setText(textMessage);

			oLogger.info("Sending mail to: " + addresses);

			Transport.send(message);

		} catch (MessagingException e) {
			oLogger.error("Cannot send mail", e);
			throw (new BusinessException("Cannot send mail"));

		}
	}
}
