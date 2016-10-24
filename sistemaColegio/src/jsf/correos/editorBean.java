/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.correos;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.servlet.http.HttpSession;

import jsf.usuarios.Sesiones;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ejb.CorreosFacade;
import ejb.CursosFacade;
import ejb.RegistromatriculasFacade;
import ejb.RelacioncorreousuariosFacade;
import ejb.RelacionprofesoresasignaturaperiodoFacade;
import ejb.UsuariosFacade;
import entities.Correos;
import entities.Cursos;
import entities.Relacioncorreousuarios;
import entities.Usuarios;

/**
 * 
 * @author juannoguera
 */
@ManagedBean
@ViewScoped
public class editorBean implements Serializable {
	
	public editorBean(){
		
	}
	
	String value = "";

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void prueba(){
		System.out.print(value +  " Ola");
	}

}