package com.javapackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RetrieveInboxServlet extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String user = req.getParameter("from_email");
		String password = req.getParameter("from_password");
	    String host = "pop.gmail.com";
	    PrintWriter out= res.getWriter();
	    
	    try {
            host = "imap.gmail.com";
            Properties properties = new Properties();
            properties.setProperty("mail.imap.ssl.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("imap");
            store.connect(host, user, password);
            
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel=\"stylesheet\" href=\"style2.css\">");
            out.println("<link rel=\"icon\" type=\"image/x-icon\" href=\"logo.png\">");
            out.println("<title>email-inbox</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<a href=\"index.html\"><img style=\"margin: 20px;height: 40px;width: 40px;\" src=\"logo.png\"></a>");
            out.println("<center>");
            out.println("<h1>Total number of emails: "+emailFolder.getMessageCount()+"</h1>");
            Message messages[]=emailFolder.getMessages();
            for (int i = messages.length-1, n = messages.length-11 ; i > n; i--) {
                Message message = messages[i];
                out.println("<div class=\"center\">");
                String data[]=message.getFrom()[0].toString().split(" ");
                out.print("<b>From:</b>");
                for(int j=0;j< data.length-2;j++)
                    out.print(data[j]+" ");
                out.println("<p>\n<b>Date: </b>" + message.getSentDate()+"</p");
                out.println("<p><b>Id: </b>"+data[data.length-1]+"</p");
                if(message.getSubject().isEmpty())
                	out.println("<p><b>Subject:</b> No subject"+"</p");
                else
                    out.println("<p><b>Subject:</b> " + message.getSubject()+"</p");
                out.println("</div><br>");
            }
            out.println("</center></body></html>");
            emailFolder.close(false);
            store.close();

        } catch (Exception e) {
        	out.println("<html>");
            out.println("<head>");
            out.println("<link rel=\"stylesheet\" href=\"style2.css\">");
            out.println("<link rel=\"icon\" type=\"image/x-icon\" href=\"logo.png\">");
            out.println("<title>email-inbox</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            String s = e.getMessage();
        	out.println("<h1>"+s+"</h1>");
        	out.println("<h1>Try again!</h1>");
        	out.println("<a href=\"index.html\"><button style=\"width: 100px; height: 40px; color: black;\" type=\"submit\" value=\"ReTry\"></button><a>");
            out.println("<br>");
        	out.println("<a href=\"index.html\"><img style=\"margin: auto; height: 300px;width: 300px;\" src=\"sad.png\"></a>");
            out.println("</center></body></html>");
        } 
	}
}