package jsf.correos;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class conectarAMail {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Message[] mensajes;
		Session session = Session.getInstance(new Properties());
        try {
            Store store = session.getStore("pop3");
            store.connect("mail.colpedagogicocampestre.edu.co", "candy.quintero@colpedagogicocampestre.edu.co", "admin123");
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

}
