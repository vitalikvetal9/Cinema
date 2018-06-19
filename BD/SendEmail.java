package BD;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.qoppa.pdfWriter.PDFDocument;
import com.qoppa.pdfWriter.PDFGraphics;
import com.qoppa.pdfWriter.PDFPage;

public class SendEmail {
	final String username = "kinofan202020@gmail.com";
	final String password = "adminkino20";

	public SendEmail(String whom, String subject, String text, boolean ticket) {
		if (!ticket) {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(whom));
				message.setSubject(subject);
				message.setText(text);
				Transport.send(message);
				System.out.println("Done");
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void sendTicket(String whom, String movie, int ticketID, String ses_date, String ses_time, int hall,
			String item) throws IOException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(whom));
			message.setSubject("Квиток на фільм " + movie);
			message.setText("Роздрукуйте даний квиток або покажіть його контролеру при вході в зал\nФільм - " + movie
					+ "\nДата - " + ses_date + "\nЧас - " + ses_time + "\nЗал - " + hall + '\n' + item.split(", ")[0]
					+ '\n' + item.split(", ")[1] + '\n' + item.split(", ")[2] + "\nШтрих-код: #" + ticketID);
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			messageBodyPart = new MimeBodyPart();
			try {
				makePDF(movie, ticketID, ses_date, ses_time, hall, item);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String file = "output.pdf";
			String fileName = "ticket";
			DataSource source = new FileDataSource(file);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			System.out.println("Sending");
			Transport.send(message);
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public void makePDF(String movie, int ticketID, String ses_date, String ses_time, int hall, String item)
			throws Exception {
		PDFDocument pdfDoc = new PDFDocument();
		PDFPage newPage = pdfDoc.createPage(new PageFormat());
		Graphics2D g2d = newPage.createGraphics();
		g2d.setFont(PDFGraphics.COURIER.deriveFont(14f));
		g2d.drawString("Наше кіно", 450, 100);
		g2d.setFont(PDFGraphics.HELVETICA.deriveFont(24f));
		g2d.drawString(movie, 100, 350);
		g2d.drawString("Дата : " + ses_date, 100, 400);
		g2d.drawString("Час - " + ses_time, 100, 450);
		g2d.drawString("Зал №" + hall, 100, 500);
		g2d.drawString("Штрих-код - " + ticketID, 100, 700);
		g2d.drawString(item.split(", ")[0], 100, 600);
		g2d.drawString(item.split(", ")[1], 100, 650);
		g2d.drawString(item.split(", ")[2], 100, 550);
		g2d.setFont(PDFGraphics.HELVETICA.deriveFont(12f));
		g2d.drawString("Роздрукуйте даний квиток та покажіть його контролеру при вході в зал", 75, 750);
		pdfDoc.addPage(newPage);
		pdfDoc.saveDocument("output.pdf");
	}
}
