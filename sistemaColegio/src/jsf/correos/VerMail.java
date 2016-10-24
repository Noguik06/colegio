/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.correos;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlPanelGroup;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletOutputStream;

/**
 *
 * @author juannoguera
 */
@ManagedBean
@SessionScoped
public class VerMail implements Serializable {

    private Session session = null;
    private Store store = null;
    private String username, password;
    private Folder folder;
    private Message[] mensajes;
    private HtmlPanelGroup htmlPanelGroup = new HtmlPanelGroup();

    /**
     * Creates a new instance of VerMail
     */
    public VerMail() {
    }

    public void Conectarse() throws NoSuchProviderException, MessagingException {

        Session session = Session.getInstance(new Properties());
        try {
            Store store = session.getStore("pop3");
            store.connect("mail.colpedagogicocampestre.edu.co", "candy.quintero@colpedagogicocampestre.edu.co", "candy123");
            Folder fldr = store.getFolder("INBOX");
            fldr.open(Folder.READ_WRITE);
//            Folder folder = store.getFolder("INBOX");
            mensajes = fldr.getMessages();
            for (int i = 0; i < mensajes.length; i++) {
                System.out.println("From:" + mensajes[i].getFrom()[0].toString());
                System.out.println("Subject:" + mensajes[i].getSubject());
//                System.out.println("Subject:" + mensajes[i].getContent());
                System.out.println("Id del mensaje:" + mensajes[i].getDisposition());
                System.out.println("Id del mensaje:" + mensajes[i].getSentDate());
                System.out.println("\n");
            }
//
//            int count = fldr.getMessageCount();
//            System.out.println(count);
        } catch (Exception exc) {
            System.out.println(exc + "error");
        }
    }

    public Message[] getMensajes() {
        return mensajes;
    }

    public String Contenido(Message mensaje) throws IOException, MessagingException {
        Object objRef = mensaje.getContent();
        if (!(objRef instanceof Multipart)) {
            String message = "This Message is not a multipart message!";
            return objRef.toString();
        }
        Multipart multipart = (Multipart) objRef;;
//        System.out.print(multipart.getCount() + " CUENTA ");
//        System.out.print(multipart.getBodyPart(0).getContent().toString() + " PRIMERA PARTE ");
//        System.out.print( + "PRIMERA PARTE");
        return multipart.getBodyPart(1).getContent().toString();
    }

    public void metodoEnviarEmail() {
        MimeMultipart multipart = new MimeMultipart();
        try {
            // Propiedades de la conexion
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
//            props.setProperty("mail.smtp.user", "ricardito293@gmail.com");
            props.setProperty("mail.smtp.auth", "true");
            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);
            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);

            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress("noguik@gmail.com"));
//                    JOptionPane.showMessageDialog(null, correosElectronicosVendedor.getCorreoelectronico().getCorreoelectronico());


            message.setSubject("Ninguno", "text/plain");
            MimeBodyPart mbp = new MimeBodyPart();
            String varHtmlMensaje = "<table>"
                    + "<tr><th><label style = 'text-align: left;display: block;'>CANT.</label></th>"
                    + "<th><label style = 'text-align: left;display: block;'>REFERENCIA.</label></th>"
                    + "<th><label style = 'text-align: left;display: block;'>DESCRIPCION.</label></th>"
                    + "<th><label style = 'text-align: left;display: block;'>MARCA / NAC.</label></th>"
                    + "</tr> ";

            varHtmlMensaje += "</table>";
            mbp.setContent(varHtmlMensaje, "text/html");
            Document document = new Document(PageSize.LEGAL, 20, 20, 20, 20);
            document.open();
            document.close();

            byte[] buf = new byte[1024];
            try {
                File file = new File("primero.pdf");
                long length = file.length();
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                in.close();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
//
            File fileAttachment = new File("primero" + ".pdf");


            // Part two is attachment
            DataSource source =
                    new FileDataSource(fileAttachment);
            mbp.setDataHandler(
                    new DataHandler(source));
            mbp.setFileName(fileAttachment.getName());

//            multipart.addBodyPart(messageBodyPart);


            multipart.addBodyPart(mbp);
            message.setContent(multipart);

//            DataSource source =
//                    new FileDataSource(fileAttachment);
//            message.setDataHandler(
//                    new DataHandler(source));


            // Lo enviamos.



            Transport t = session.getTransport("smtp");
            t.connect("juanfnoguera06@gmail.com", "juanfe@#$e123");
            t.sendMessage(message, message.getAllRecipients());
            // Cierre.
            t.close();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "ERROR EMAIL");
            e.printStackTrace();
        }
    }
}
