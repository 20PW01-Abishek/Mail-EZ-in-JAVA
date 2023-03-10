package com.javapackage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;

public class SendEmailServlet extends HttpServlet{
	static String from_email;
	static String to_email;
	static String from_password;
	static String content;
	static String subject;
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		from_email = req.getParameter("from_email");
		from_password = req.getParameter("from_password");
		to_email = req.getParameter("to_email");
		content = req.getParameter("content");
		subject = req.getParameter("subject");
		send();
		
		res.setContentType("text/html");
		String site= "http://localhost:8080/javapackage/index.html";
		res.setStatus(res.SC_ACCEPTED);
		res.setHeader("Location",site);
		res.sendRedirect(site);
		return;
		
//		req.setAttribute("from_email",from_email);
//		req.setAttribute("from_password",from_password);
//		
//		RequestDispatcher rd = req.getRequestDispatcher("/inbox");
//		rd.forward(req,res);
	}
	public static void send() {
		final String username = from_email;
        final String password = from_password;

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to_email)
            );
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
        } catch (MessagingException e) {
        	System.out.println(e.getMessage());
        }
	}
}
