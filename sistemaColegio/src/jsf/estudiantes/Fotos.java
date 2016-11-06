/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.estudiantes;

import ejb.UsuariosFacade;
import entities.Usuarios;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jsf.usuarios.Sesiones;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author juannoguera
 */
@ManagedBean
@Stateless
public class Fotos {

    @EJB
    UsuariosFacade usuariosFacade;

    /**
     * Creates a new instance of Fotos
     */
    public Fotos() {
    }

    //###Método para obtener las imágenes
    public StreamedContent getFoto() throws IOException {

        byte[] byteArr = usuariosFacade.find(new Long(120)).getFoto();
        String mimeType = "image/jpg";
        StreamedContent file;
        InputStream input = new ByteArrayInputStream(byteArr);
        file = new DefaultStreamedContent(input, mimeType, "nada");




        return file;
    }

    public StreamedContent imagen() throws IOException {
        
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        String imageID = (String) myRequest.getParameter("imageID");
        System.out.print(imageID);
        
        Usuarios student = null;
        
        if(imageID == null){
//            imageID = "1";
            student = usuariosFacade.find(new Long(1));
        }else{
            student = usuariosFacade.find(new Long(imageID));
        }
        
        
        if(student.getFoto() == null){
            return new DefaultStreamedContent(new ByteArrayInputStream(student.getFoto()));
        }
        
        return new DefaultStreamedContent(new ByteArrayInputStream(student.getFoto()));
    }
    
    private List<Usuarios> dataListUsuarios;

    public List<Usuarios> getDataListUsuarios() {
        if(dataListUsuarios == null){
            dataListUsuarios = usuariosFacade.findByLikeAll("SELECT U FROM Usuarios U ORDER BY U.idusuarios ASC");
        }
        return dataListUsuarios;
    }
    
    
    String usuario = "";

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public void buscarUsuarios(){
        dataListUsuarios = usuariosFacade.findByLike("SELECT U FROM Usuarios U WHERE U.idusuarios = "+ usuario+ " ORDER BY U.idusuarios ASC");
    }
    
    private StreamedContent graphicText;
    public StreamedContent getGraphicText() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        Usuarios student =  (Usuarios) session.getAttribute("usuariosescogido");
        if(student != null){
        	if(student.getFoto() == null){
		        ByteArrayOutputStream os = null;
			    byte[] bytes = null;
			    File file = new File(System.getProperty("user.dir") + "/prueba.png");
//		    	URL url = new URL("http://localhost:8080/sistemaColegio/resources/imagenes/header_boletin.png");
		    	BufferedImage image = null;
		    	
		    	image = ImageIO.read(file);
		//        BufferedImage image = ImageIO.read(url);
		        os = new ByteArrayOutputStream();
		        ImageIO.write(image, "png", os);
		        bytes = os.toByteArray();
		         // So, browser is requesting the image. Get ID value from actual request param.
		        graphicText =  new DefaultStreamedContent(new ByteArrayInputStream(bytes));
        	}else{
        		graphicText =  new DefaultStreamedContent(new ByteArrayInputStream(student.getFoto()));
        	}
        }
        
//    	File file = new File("http://localhost:8080/sistemaColegio/resources/imagenes/header_boletin.png");
    	return graphicText;
    }
    
}
