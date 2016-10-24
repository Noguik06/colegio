/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.correos;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import jsf.usuarios.Sesiones;

import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;

import converter.Convertermayuminus;
import ejb.AdjuntoscorreosFacade;
import ejb.CorreosFacade;
import ejb.CursosFacade;
import ejb.CorreosexternosFacade;
import ejb.RegistromatriculasFacade;
import ejb.RelacioncorreousuariosFacade;
import ejb.RelacionprofesoresasignaturaperiodoFacade;
import ejb.UsuariosFacade;
import entities.Adjuntoscorreos;
import entities.Correos;
import entities.Correosexternos;
import entities.Cursos;
import entities.Relacioncorreousuarios;
import entities.Tareas;
import entities.Usuarios;

/**
 * 
 * @author juannoguera
 */
@ManagedBean
@SessionScoped
public class CorreosUsuarios implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Creates a new instance of Correos
	 */
	@EJB
	private CorreosFacade correosFacade;
	@EJB
	private RelacioncorreousuariosFacade relacioncorreousuariosFacade;
	@EJB
	private UsuariosFacade usuariosFacade;
	@EJB
	private CursosFacade cursosFacade;
	@EJB
	private RegistromatriculasFacade registromatriculasFacade;
	@EJB
	private RelacionprofesoresasignaturaperiodoFacade relacionprofesoresasignaturaperiodoFacade;
	@EJB
	private CorreosexternosFacade correosexternosFacade;
	@EJB
	private AdjuntoscorreosFacade adjuntoscorreosFacade;
	// ##Correos para enviar
	private Correos correoNuevo;
	// Lista de los usuarios a los que se les va a enviar el correo
	private List<Usuarios> dataListUsuariosPara;
	// Lista de los usuarios a los que se les va a enviar el correo externo
	private List<Usuarios> dataListUsuariosParaExterno;
	// Lista de los usuarios de la busqueda
	private List<Usuarios> dataListUsuariosBusqueda;
	// +++
	private List<Cursos> dataListCursosBusqueda;
	//
	private List<String> dataListGrupos;
	// ##Correos recbidido
	private List<Relacioncorreousuarios> dataListCorreos;
	// ##Correos recbidido
	private List<Correos> dataListCorreosPara;
	// ##QUERY
	String query = "";
	// Tipo de lista de corroe
	private int tipoListaCorreo = 0;
	// Array para la lista de correos
	private Message[] mensajes;
	// Objeto para seleccionar un mensaje por defecto que es el que vamos a leer
	private Message messageSeleccionado;
	// ###SESSION
	private Session session = null;
	private Folder fldr = null;
	// ###ADJUNTOS
	private List<Part> dataListAdjuntos;
	// private List<Part> archivos = new ArrayList<Part>();
	private String mensaje = null;
	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	// ###
	Store store;

	public CorreosUsuarios() {
	}

	public Sesiones getSesiones() {
		FacesContext FCInstance = FacesContext.getCurrentInstance();
		String theBeanName = "sesiones";
		Object bean = FCInstance.getELContext().getELResolver()
				.getValue(FCInstance.getELContext(), null, theBeanName);
		Sesiones sesiones = (Sesiones) bean;
		if (sesiones != null && sesiones.getUsuarios() != null) {
			return sesiones;
		}

		return null;
	}

	// Getter and Setter del correo
	public Correos getCorreoNuevo() {
		if (correoNuevo == null) {
			correoNuevo = new Correos(new Long(0));
		}
		return correoNuevo;
	}

	public void setCorreoNuevo(Correos correoNuevo) {
		this.correoNuevo = correoNuevo;
	}

	// Lista de los usuarios a los que se les va a enviar el correo
	public List<Usuarios> getDataListUsuariosPara() {
		return dataListUsuariosPara;
	}

	public void setDataListUsuariosPara(List<Usuarios> dataListUsuariosPara) {
		this.dataListUsuariosPara = dataListUsuariosPara;
	}

	// Lista de los usuarios a los que se les va a enviar el correo
	public List<Usuarios> getDataListUsuariosParaExterno() {
		return dataListUsuariosParaExterno;
	}

	public void setDataListUsuariosParaExterno(
			List<Usuarios> dataListUsuariosPara) {
		this.dataListUsuariosParaExterno = dataListUsuariosPara;
	}

	// Lista del query de la busqueda de los usuarios
	public List<Usuarios> getDataListUsuariosBusqueda() {
		dataListUsuariosBusqueda = new ArrayList<Usuarios>();
		if (query != null && query.trim().replaceAll(" ", "").length() > 0) {
			if (getSesiones().getTipoUsuario() == 2) {
				try {
					String[] valores;
					Pattern p = Pattern.compile(" ");
					valores = query.trim().toString().split(" ");
					Matcher m;
					boolean b;
					boolean h = false;
					String queryPrueba = "";

					for (int i = 0; i < valores.length; i++) {
						m = p.matcher(valores[i]);
						b = m.matches();
						if (!b && !h) {

							queryPrueba = "SELECT U FROM Usuarios U WHERE (LOWER(U.nombres) LIKE  '%"
									+ valores[i].toLowerCase()
									+ "%' OR LOWER(U.apellidos) LIKE '%"
									+ valores[i].toLowerCase()
									+ "%' OR LOWER(U.numeroidentificacion) LIKE '%"
									+ valores[i].toLowerCase()
									+ "%' OR LOWER(U.nombredeusuario) LIKE '%"
									+ valores[i].toLowerCase() + "%')";
							h = true;
						}
						if (!b && h) {
							queryPrueba += " AND (LOWER(U.nombres) LIKE  '%"
									+ valores[i].toLowerCase()
									+ "%' OR LOWER(U.apellidos) LIKE '%"
									+ valores[i].toLowerCase()
									+ "%' OR LOWER(U.numeroidentificacion) LIKE '%"
									+ valores[i].toLowerCase()
									+ "%' OR LOWER(U.nombredeusuario) LIKE '%"
									+ valores[i].toLowerCase() + "%')";
						}
					}
					if (h) {
						queryPrueba += " AND U.idusuarios in (SELECT R.profesores.usuarios.idusuarios FROM Relacionprofesoresasignaturaperiodo R "
								+ "WHERE R.cursos.idcursos = "
								+ getSesiones().getRegistromatriculas()
										.getCursos().getIdcursos()
								+ ")"
								+ "ORDER BY  U.apellidos";
						dataListUsuariosBusqueda = usuariosFacade
								.findByLike(queryPrueba);
					}
				} catch (Exception e) {
					System.out.print("Error buscando los usuarios");
				}
			} else {
				try {
					String[] valores;
					Pattern p = Pattern.compile(" ");
					valores = query.trim().toString().split(" ");
					Matcher m;
					boolean b;
					boolean h = false;
					String queryPrueba = "";

					for (int i = 0; i < valores.length; i++) {
						m = p.matcher(valores[i]);
						b = m.matches();
						if (!b && !h) {

							queryPrueba = "SELECT U FROM Usuarios U WHERE (LOWER(U.nombres) LIKE  '%"
									+ valores[i].toLowerCase()
									+ "%' OR LOWER(U.apellidos) LIKE '%"
									+ valores[i].toLowerCase()
									+ "%' OR LOWER(U.numeroidentificacion) LIKE '%"
									+ valores[i].toLowerCase()
									+ "%' OR LOWER(U.nombredeusuario) LIKE '%"
									+ valores[i].toLowerCase() + "%')";
							h = true;
						}
						if (!b && h) {
							queryPrueba += " AND (LOWER(U.nombres) LIKE  '%"
									+ valores[i].toLowerCase()
									+ "%' OR LOWER(U.apellidos) LIKE '%"
									+ valores[i].toLowerCase()
									+ "%' OR LOWER(U.numeroidentificacion) LIKE '%"
									+ valores[i].toLowerCase()
									+ "%' OR LOWER(U.nombredeusuario) LIKE '%"
									+ valores[i].toLowerCase() + "%')";
						}
					}
					if (h) {
						queryPrueba += " ORDER BY  U.apellidos";
						dataListUsuariosBusqueda = usuariosFacade
								.findByLike(queryPrueba);
					}
				} catch (Exception e) {
					System.out.print("Error buscando los usuarios");
				}
			}
			return dataListUsuariosBusqueda;
		}
		return dataListUsuariosBusqueda;
	}

	// Metodo para agregar un nuevo usuario
	public void agregarUsuario(Usuarios usuarios) {
		if (dataListUsuariosPara == null) {
			dataListUsuariosPara = new ArrayList<Usuarios>();
		}
		dataListUsuariosPara.add(usuarios);
		query = null;
	}

	public void eliminarUsuario(Usuarios usuarios) {
		dataListUsuariosPara.remove(usuarios);
		if (usuarios.getIdusuarios() == null) {
			dataListUsuariosParaExterno.remove(usuarios);
		}

	}

	// Metodo para recoger el query
	public void setQueryUsuarios(String query) {
		this.query = query;

	}

	public String getQueryUsuarios() {
		return query;
	}

	// ##METODO PARA BUSCAR POR CURSOS
	// Lista del query de la busqueda de los usuarios
	public List<Cursos> getDataListCursosBusqueda() {
		dataListCursosBusqueda = new ArrayList<Cursos>();
		if (query != null && query.trim().replaceAll(" ", "").length() > 0
				&& getSesiones() != null && getSesiones().getTipoUsuario() != 2) {
			try {
				String[] valores;
				Pattern p = Pattern.compile(" ");
				valores = query.trim().toString().split(" ");
				Matcher m;
				boolean b;
				boolean h = false;
				String queryPrueba = "";
				for (int i = 0; i < valores.length; i++) {
					m = p.matcher(valores[i]);
					b = m.matches();
					if (!b && !h) {

						queryPrueba = "SELECT U FROM Cursos U WHERE (LOWER(U.grados.nombre) LIKE  '%"
								+ valores[i].toLowerCase() + "%')";
						h = true;
					}
					if (!b && h) {
						queryPrueba += " AND (LOWER(U.grados.nombre) LIKE  '%"
								+ valores[i].toLowerCase() + "%')";
					}
				}
				if (h) {
					queryPrueba += " AND U.anosacademicos.estadoactivo = true ORDER BY  U.grados.nombre";
					dataListCursosBusqueda = cursosFacade
							.findByLike(queryPrueba);
				}
			} catch (Exception e) {
				System.out.print("Error buscando los usuarios");
			}
			return dataListCursosBusqueda;
		}
		return dataListCursosBusqueda;
	}

	// Metodo para escoger todos los estudiantes de un curso
	public void agregarCurso(Cursos cursos) {
		if (dataListUsuariosPara == null) {
			dataListUsuariosPara = new ArrayList<Usuarios>();
		}
		dataListUsuariosPara
				.addAll(usuariosFacade
						.findByLikeAll("SELECT R.estudiantes.usuarios FROM Registromatriculas R WHERE R.cursos.idcursos = "
								+ cursos.getIdcursos()));
		query = null;
	}
	
	
	// ##METODO PARA BUSCAR POR GRUPOS
	// Lista del query de la busqueda de los usuarios
	public List<String> getDataListGrupos() {
		dataListGrupos = new ArrayList<String>();
		if (query != null && query.trim().replaceAll(" ", "").length() > 0
				&& getSesiones() != null && getSesiones().getTipoUsuario() != 2) {
			try {
				if(query!=null && query.trim().length() >0){
					if("profesores".contains(query.toLowerCase())){
						dataListGrupos.add("Profesores");
					}
					if("estudiantes".contains(query.toLowerCase())){
						dataListGrupos.add("Estudiantes");
					}
				}
				
			} catch (Exception e) {
				System.out.print("Error buscando los usuarios");
			}
		}
		return dataListGrupos;
	}

	// Metodo para escoger todos los estudiantes de un curso
	public void agregarGrupo(String grupo) {
		if (dataListUsuariosPara == null) {
			dataListUsuariosPara = new ArrayList<Usuarios>();
		}
		if(grupo.toLowerCase().equals("profesores")){
			dataListUsuariosPara
					.addAll(usuariosFacade
							.findByLikeAll("SELECT P.usuarios FROM Profesores P "
									+ "	WHERE P.usuarios.estado_activo = 'true'"));
		}
		if(grupo.toLowerCase().equals("estudiantes")){
			dataListUsuariosPara
					.addAll(usuariosFacade
							.findByLikeAll("SELECT P.usuarios FROM Estudiantes P "
									+ "	WHERE P.usuarios.estado_activo = 'true'"));
		}
		query = null;
	}

	// Metodo para valiar un correo externo
	public void validarCorreoExterno() {
		if (query != null) {
			
			String[] queryArray = query.split(" ");
			
			
			for(String correo: queryArray){
				// Compiles the given regular expression into a pattern.
				Pattern pattern = Pattern.compile(PATTERN_EMAIL);

				// Match the given input against this pattern
				Matcher matcher = pattern.matcher(correo);

				if (matcher.matches()) {
					Usuarios usuarioNuevo = new Usuarios();
					usuarioNuevo.setIdusuarios(null);
					usuarioNuevo.setNombredeusuario(correo);
					usuarioNuevo.setNombres(correo);
					usuarioNuevo.setEmail(correo);
					if (dataListUsuariosParaExterno == null) {
						dataListUsuariosParaExterno = new ArrayList<Usuarios>();
					}
					if (dataListUsuariosPara == null) {
						dataListUsuariosPara = new ArrayList<Usuarios>();
					}
					dataListUsuariosParaExterno.add(usuarioNuevo);
					dataListUsuariosPara.add(usuarioNuevo);
				}
			}

			query = null;
			// ;
		}
	}

	// Metodo para enviar el correo
	public String enviarCorreo() {
		try {
			if (correoNuevo != null) {
				correoNuevo.setContenido(value);
			}

			if (dataListUsuariosPara == null || dataListUsuariosPara.isEmpty()) {
				throw new ValidatorException(new FacesMessage(
						"No hay usuarios para enviar el correo"));
			}

			if (correoNuevo.getContenido() == null
					|| correoNuevo.getContenido().replaceAll(" ", "").length() == 0) {
				throw new ValidatorException(new FacesMessage(
						"No hay contenido para enviar en el correo"));
			}

			// Validamos si hay correos externos para enviar o solo internos
			if (dataListUsuariosParaExterno == null
					|| dataListUsuariosParaExterno.size() > 0
					|| dataListUsuariosParaExterno.size() < dataListCorreosPara
							.size()) {

				correoNuevo.setFecha(new Date());
				correoNuevo.setBanderausuariosde(true);
				correoNuevo.setIdusuariosde(getSesiones().getUsuarios());
				correosFacade.create(correoNuevo);
				
				
				if (file_adjunto != null && file_adjunto.getFileName() != null
						&& file_adjunto.getFileName().length() > 0
						&& file_adjunto.getSize() > 0) {
					if (file_adjunto.getSize() > 4710720) {
						FacesMessage msg = new FacesMessage("Error",
								"El tamaño del archivo es mayor que lo permitido");
						msg.setSeverity(FacesMessage.SEVERITY_ERROR);
						FacesContext.getCurrentInstance().addMessage(null,
								msg);
						return "";
					}
					
					Adjuntoscorreos adjuntoscorreos = new Adjuntoscorreos();
					adjuntoscorreos.setId_adjuntos_correos(new Long(0));
					adjuntoscorreos.setTipo(file_adjunto.getContentType());
					adjuntoscorreos.setCorreos(correoNuevo);
					
					
//					InputStream is;
					try {
						byte[] bytes = IOUtils.toByteArray(file_adjunto.getInputstream());
						adjuntoscorreos.setArchivo(bytes);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					String ext = file_adjunto.getFileName();
					ext = ext.substring(ext.lastIndexOf("."), ext.length());
					adjuntoscorreos.setNombre(file_adjunto.getFileName() + ext);
					adjuntoscorreosFacade.create(adjuntoscorreos);
				}

				

				for (Usuarios u : dataListUsuariosPara) {
					if (u.getIdusuarios() != null) {
						Relacioncorreousuarios relacioncorreousuarios = new Relacioncorreousuarios(
								new Long(0));
						relacioncorreousuarios.setBanderausuariospara(true);
						relacioncorreousuarios.setCorreo(correoNuevo);
						relacioncorreousuarios.setUsuariospara(u);
						relacioncorreousuariosFacade
								.create(relacioncorreousuarios);
					}
				}
			}

			if (dataListUsuariosParaExterno != null
					&& dataListUsuariosParaExterno.size() > 0) {
				Properties props = new Properties();

				// Nombre del host de correo, es smtp.gmail.com
				props.setProperty("mail.smtp.host",
						"mail.colpedagogicocampestre.edu.co");

				// Nombre del usuario
				props.setProperty("mail.smtp.user",
						getSesiones().getUsuarios().getEmail());

				// Si requiere o no usuario y password para conectarse.
				props.setProperty("mail.smtp.auth", "true");

				Session session = Session.getDefaultInstance(props);
				session.setDebug(true);

				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(
						getSesiones().getUsuarios(). getEmail()));

				List<Correosexternos> dataListCorreosExternos = new ArrayList<Correosexternos>();
				Iterator<Usuarios> iterator = dataListUsuariosParaExterno.iterator();
				while(iterator.hasNext()){
					Usuarios userRecipient = iterator.next();
					if(correosexternosFacade.findByLike("SELECT C FROM Correosexternos C WHERE C.correo = " + userRecipient.getEmail()).isEmpty()){
						Correosexternos correosexternos = new Correosexternos(new Long(0));
						correosexternos.setCorreo(userRecipient.getEmail().trim());
					}
					
					message.addRecipient(Message.RecipientType.TO,
							new InternetAddress(userRecipient.getEmail()));
				}

				message.setSubject(correoNuevo.getAsunto());
				message.setText(correoNuevo.getContenido(), "ISO-8859-1", "html");

				Transport t = session.getTransport("smtp");

				t.connect(getSesiones().getUsuarios().getEmail(), getSesiones().getUsuarios().getPassword());

				t.sendMessage(message, message.getAllRecipients());

				t.close();
			}

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Exitoso",
					"El correo ha sido enviado"));

		} catch (Exception e) {
			System.out.print("Error al enviar el mensaje " + e);
		}
		

		dataListUsuariosPara = null;
		correoNuevo = null;
		dataListCorreos = null;


		return "";
	}

	// Lista de los correos recibidos
	public List<Relacioncorreousuarios> getDataListCorreos() {
		if (dataListCorreos == null && getSesiones() != null
				&& getSesiones().getUsuarios() != null) {
			dataListCorreos = relacioncorreousuariosFacade
					.findByLikeAll("SELECT R FROM Relacioncorreousuarios R WHERE R.usuariospara.idusuarios = "
							+ getSesiones().getUsuarios().getIdusuarios()
							+ " ORDER BY R.correo.fecha DESC");

			try {
				Conectarse();
				// for(Message message:mensajes){
				// message.setFlag(Flags.Flag.DELETED, true);
				// }
			} catch (Exception e) {
				System.out
						.print("Error al conectarse al mail y traer los mensajes "
								+ e);
			}
		}
		return dataListCorreos;
	}

	public void setDataListCorreosRecibidos(
			List<Relacioncorreousuarios> dataListCorreosRecibidos) {
		this.dataListCorreos = dataListCorreosRecibidos;
	}

	// Metodo para seleccionar el correo que queremos leer
	private Relacioncorreousuarios correoSeleccionado;

	// metodo para seleccionar el correo que vamos a leer
	public void seleccionarCorreo(Relacioncorreousuarios correos) {
		this.messageSeleccionado = null;

		boolean banderaParaElminar = false;
		// Revisamos si el correo no ha sido leido aun
		if (!correos.isLeido()) {
			// Si no ha sido leido entonces entramos a modificar su estado
			// Revisamos si esta marcado para eliminar
			if (correos.getIdrelacioncorreousuarios() < 0) {
				// Si esta maracado para eliminar activamos la bandera para
				// dejarlo igual
				banderaParaElminar = true;
				// Colocamos su id en positivo
				correos.setIdrelacioncorreousuarios(Math.abs(correos
						.getIdrelacioncorreousuarios()));
			}
			// Cambiamos su estado
			correos.setLeido(true);
			// Editamos el correo
			relacioncorreousuariosFacade.edit(correos);
		}
		// Revisamos el valor de la bandera
		if (banderaParaElminar) {
			// Dejamos de nuevo el correo marcado para eliminar
			correos.setIdrelacioncorreousuarios(correos
					.getIdrelacioncorreousuarios() * -1);
		}

		this.correoSeleccionado = correos;
	}

	// Metodo para seleccionar un mensaje de un correo externo
	public void seleccionarMensaje(Message mensajes) throws IOException,
			MessagingException {
		try {
			mensajes.setFlag(Flags.Flag.SEEN, true);
			fldr.close(true);
			fldr.open(Folder.READ_WRITE);
			;
		} catch (Exception e) {
			System.out.print("No se pudo marcar como no leido");
		}

		this.messageSeleccionado = mensajes;
		this.correoSeleccionado = null;

		mensaje = null;
		dataListAdjuntos = null;
		try {
			Object content = mensajes.getContent();
			if (content instanceof Multipart) {
				// archivos = new ArrayList<Part>();
				for (int k = 0; k < ((Multipart) content).getCount(); k++) {
					Part part = ((Multipart) content).getBodyPart(k);
					String cType = part.getContentType();
					if ((cType.length() >= 10)
							&& (cType.toLowerCase().substring(0, 10)
									.equals("text/plain"))) {
						// part.writeTo(System.out);
					} else {
						if (part.getContent() instanceof Multipart) {
							for (int t = 0; t < ((Multipart) part.getContent())
									.getCount(); t++) {
								Part partTmp = ((Multipart) part.getContent())
										.getBodyPart(t);
								if (partTmp.getFileName() != null) {
									if (dataListAdjuntos == null) {
										dataListAdjuntos = new ArrayList<Part>();
									}
									dataListAdjuntos.add(partTmp);
								} else {
									if (partTmp.getContent() instanceof Multipart) {
										for (int h = 0; h < ((Multipart) partTmp
												.getContent()).getCount(); h++) {
											Part partTMp2 = ((Multipart) partTmp
													.getContent())
													.getBodyPart(h);
											if (partTMp2.getFileName() != null) {
												if (dataListAdjuntos == null) {
													dataListAdjuntos = new ArrayList<Part>();
												}
												dataListAdjuntos.add(partTMp2);
											} else {
												mensaje = partTMp2.getContent()
														.toString();
											}
										}
									} else {
										if (mensaje == null)
											mensaje = partTmp.getContent()
													.toString();
									}
								}
							}
						} else {
							if (part.getFileName() != null) {
								if (dataListAdjuntos == null) {
									dataListAdjuntos = new ArrayList<Part>();
								}
								dataListAdjuntos.add(part);
							} else {
								mensaje = part.getContent().toString();
							}
						}
					}
				}

			} else {
				mensaje = messageSeleccionado.getContent().toString();
			}
		} catch (Exception e) {
//			Conectarse();
		}

	}
		
	//Metodo para saber si tiene un archivo o no
	public boolean getAdjunto(){
		if(correoSeleccionado == null){
			return false;
		}
		List<Adjuntoscorreos> tmp = adjuntoscorreosFacade.findByLike("SELECT A FROM Adjuntoscorreos A where a.correos.idcorreos = "
				+ correoSeleccionado.getCorreo().getIdcorreos());
		if(tmp.isEmpty()){
			return false;
		}
		
		return true;
	}
	
	//Metodo para descargar un archivo
	public StreamedContent getFileDownload() {
		List<Adjuntoscorreos> tmp = adjuntoscorreosFacade.findByLike("SELECT A FROM Adjuntoscorreos A where a.correos.idcorreos = "
				+ correoSeleccionado.getCorreo().getIdcorreos());
//        if(tmp.isEmpty()){
			StreamedContent file2;
			InputStream file3;
			file3 = new ByteArrayInputStream(tmp.get(0).getArchivo());
			file2 = new DefaultStreamedContent(file3, tmp.get(0).getTipo(), tmp
					.get(0).getNombre());
			return file2;
//        }
//        return null;
    }

	// Metodo para devolver el numero de correos del usuario que no ha leido
	public int getNumeroCorreosSinLeer() {
		if (getSesiones() != null && getSesiones().getUsuarios() != null) {
			int numeroSinLeer = 0;
			try {
				if (session == null) {
					Conectarse();
				}
			} catch (Exception e) {
				System.out.print("No se pudo conectar al correo");
			}
			if (fldr != null) {
				try {

					numeroSinLeer = fldr.getUnreadMessageCount();
					System.out.print(numeroSinLeer);
				} catch (Exception e) {
					System.out.print("No se pudo contar los correos sin leer");
				}
			}

			List<Relacioncorreousuarios> tmp = relacioncorreousuariosFacade
					.findByLike("SELECT R FROM Relacioncorreousuarios R WHERE R.usuariospara.idusuarios = "
							+ getSesiones().getUsuarios().getIdusuarios()
							+ " AND R.leido = false");
			return tmp.size() + numeroSinLeer;
		}
		return 0;
	}

	public Relacioncorreousuarios getCorreoSeleccionado() {
		return correoSeleccionado;
	}

	// Metodo para ver los correos enviado
	public void verCorreosEnviados() {
		// Cambiamos el tipo de la lista de correo y lo colocamos que estamos
		// viendo los enviados
		tipoListaCorreo = 1;
		dataListCorreos = relacioncorreousuariosFacade
				.findByLikeAll("SELECT C FROM Relacioncorreousuarios C WHERE C.correo.idusuariosde.idusuarios = "
						+ getSesiones().getUsuarios().getIdusuarios());
	}

	public List<Correos> getDataListCorreosPara() {
		return dataListCorreosPara;
	}

	// Metodo para volver a cargar la lista de los correos recibidos
	public void verCorreosRecibidos() {
		// Cambiamos el tipo de la lista de correo y lo colocamos que estamos
		// viendo los recibidos
		tipoListaCorreo = 0;
		dataListCorreos = null;
	}

	// ###Metodo para obtener las imagenes
	public StreamedContent getImage() throws IOException {
		byte[] byteArr = usuariosFacade.find(new Long(123)).getFoto();
		InputStream input = new ByteArrayInputStream(byteArr);
		return new DefaultStreamedContent(input);
	}

	// #####ELIMINAR CORREOS
	// metodo para seleccionar los correos que vamos a eliminar
	public void seleccionarCorreoEliminar(
			Relacioncorreousuarios relacioncorreousuarios) {
		if (relacioncorreousuarios.getIdrelacioncorreousuarios() > 0) {
			relacioncorreousuarios
					.setIdrelacioncorreousuarios(relacioncorreousuarios
							.getIdrelacioncorreousuarios() * -1);
		} else {
			relacioncorreousuarios.setIdrelacioncorreousuarios(Math
					.abs(relacioncorreousuarios.getIdrelacioncorreousuarios()));
		}
	}

	// metodo para eliminar los correos
	public void eliminarCorreos() {
		try {
			if (dataListCorreos != null) {
				Iterator<Relacioncorreousuarios> it = dataListCorreos
						.iterator();
				while (it.hasNext()) {
					Relacioncorreousuarios relacioncorreousuariosIt = (Relacioncorreousuarios) it
							.next();
					if (relacioncorreousuariosIt.getIdrelacioncorreousuarios() < 0) {
						relacioncorreousuariosIt
								.setIdrelacioncorreousuarios(Math
										.abs(relacioncorreousuariosIt
												.getIdrelacioncorreousuarios()));
						relacioncorreousuariosFacade
								.remove(relacioncorreousuariosIt);
						if (correoSeleccionado != null
								&& relacioncorreousuariosIt
										.getIdrelacioncorreousuarios() == correoSeleccionado
										.getIdrelacioncorreousuarios()) {
							correoSeleccionado = null;
						}
					}
				}

				if (correoSeleccionado != null) {
					relacioncorreousuariosFacade.remove(correoSeleccionado);
				}

				correoSeleccionado = null;

				dataListCorreos = null;
			}

			if (dataListMessages != null) {
				Iterator<Message> it = dataListMessages.iterator();
				while (it.hasNext()) {
					Message message = (Message) it.next();
					try {
						message.setFlag(Flags.Flag.DELETED, true);
						fldr.close(true);
						fldr.open(Folder.READ_WRITE);
					} catch (MessagingException e) {
						System.out
								.print("MessagingException No se pudo eliminar el correo "
										+ e);
					} catch (Exception e) {
						System.out.print("No se pudo eliminar el correo " + e);
					}

				}
				dataListMessages = null;
				mensajes = null;
				// Nos conectamos de nuevo para
				try {
					Conectarse();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.print("Error al conectarse de nuevo " + e);
				} catch (Error e) {
					System.out.print("Error al conectarse de nuevo " + e);
				}
			}
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Exitoso",
					"El correo ha sido eliminado"));
		} catch (Exception e) {

		}
	}

	public int getTipoListaCorreo() {
		return tipoListaCorreo;
	}

	// ###MENSAJES
	// Metodo para traer correos externos, solo para los profesores
	public void Conectarse() throws NoSuchProviderException, MessagingException {
		if (getSesiones().getUsuarios().getEmail() != null) {
//			session = Session.getInstance(new Properties());
//			try {
//				store = session.getStore("imap");
//				store.connect("mail.colpedagogicocampestre.edu.co",
//						getSesiones().getUsuarios().getEmail(), getSesiones()
//								.getUsuarios().getPassword());
//				fldr = store.getFolder("INBOX");
//				fldr.open(Folder.READ_WRITE);
//				mensajes = fldr.getMessages();
//			} catch (Exception exc) {
//				System.out.println(exc + "error");
//			}
		}
	}

	public Message[] getMensajes() throws MessagingException {
		return mensajes;
	}

	// Metodo para saber quien envio el correo
	public String from(Message message) {
		try {
			String from = message.getFrom()[0].toString();
			if(from.replaceAll(">", "").length() > 1){
				return from.replaceAll(">", "").split("<")[0];
			}
			return from.replaceAll(">", "").split("<")[0];
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	private String value = "";

	public String getValue() {
		return value;
	}

	public void setValue(String value) throws UnsupportedEncodingException {
		String nombreTarea = new String(value.getBytes("ISO-8859-1"), "ISO-8859-1");
		correoNuevo.setContenido(nombreTarea);
		this.value = value;
	}

	private List<Message> dataListMessages;

	// Metodo para eliminar un mensaje
	public void seleccionarMensajeEliminar(Message message) {
		if (dataListMessages == null) {
			dataListMessages = new ArrayList<Message>();
		}
		if (dataListMessages.contains(message)) {
			dataListMessages.remove(message);
		} else {
			dataListMessages.add(message);
		}
	}

	// Metodo para reiniciar las listas
	public void reiniciarCorreo() {
		value = "";
		dataListCorreosPara = null;
		dataListUsuariosPara = null;
	}

	// Metodo para devovler el asunto
	public String getAsunto() throws MessagingException {
		if (correoSeleccionado != null) {
			if (correoSeleccionado.getCorreo() != null
					&& correoSeleccionado.getCorreo().getAsunto() != null
					&& correoSeleccionado.getCorreo().getAsunto().trim()
							.length() > 0) {
				return correoSeleccionado.getCorreo().getAsunto();
			} else {
				return "Sin Asunto";
			}
		}
		if (messageSeleccionado != null) {
			if (messageSeleccionado.getSubject() != null
					&& messageSeleccionado.getSubject().toString().trim()
							.length() > 0) {
				return messageSeleccionado.getSubject().toString();
			} else {
				return "Sin Asunto";
			}
		}
		return "";
	}

	// Metodo para obtener el remitente del correo
	public String getRemitente() throws MessagingException {
		if (correoSeleccionado != null) {
			if (correoSeleccionado.getCorreo() != null
					&& correoSeleccionado.getCorreo().getIdusuariosde() != null
					&& correoSeleccionado.getCorreo().getIdusuariosde()
							.getNombres() != null
					&& correoSeleccionado.getCorreo().getIdusuariosde()
							.getApellidos() != null) {
				Convertermayuminus converterminusculas = new Convertermayuminus();
				String remitente = correoSeleccionado.getCorreo()
						.getIdusuariosde().getNombres()
						+ " "
						+ correoSeleccionado.getCorreo().getIdusuariosde()
								.getApellidos();
				remitente = converterminusculas.getAsString(null, null,
						remitente);
				return correoSeleccionado.getCorreo().getIdusuariosde()
						.getNombres()
						+ " "
						+ correoSeleccionado.getCorreo().getIdusuariosde()
								.getApellidos();
			}
		}
		if (messageSeleccionado != null) {
			if (messageSeleccionado.getFrom() != null) {
				return from(messageSeleccionado);
			}
		}
		return "";
	}

	// Metodo para obtener el contenido del correo
	public String getContenidoCorreo() {
		if (correoSeleccionado != null) {
			return correoSeleccionado.getCorreo().getContenido();
		}
		if (messageSeleccionado != null) {
			return mensaje;
		}
		return "";
	}

	// Metodo para saber si un correo ha sido leido o no
	public boolean messageRead(Message message) throws MessagingException {
		if (message.isSet(Flags.Flag.SEEN)) {
			return true;
		}
		return false;
	}

	// ####ADJUNTOS
	// Metodo para obtener la lista de archivos adjuntos al correo
	public List<Part> getDataListAdjuntos() {
		return dataListAdjuntos;
	}

	public StreamedContent getFile() throws IOException, MessagingException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot root = facesContext.getViewRoot();
		HtmlDataTable archivo = (HtmlDataTable) root.findComponent(
				"formCorreos").findComponent("dataTableAdjuntos");
		Part part = (Part) archivo.getRowData();
		System.out.print(part.getFileName());
		String ct = part.getContentType().toString();
		System.out.print(ct.split(";")[0]);
		StreamedContent file = new DefaultStreamedContent(
				part.getInputStream(), ct.split(";")[0], part.getFileName());
		return file;
	}

	// Archivo de la tarea
	private UploadedFile file_adjunto;
	
	// TAREAS
	// Metodo para guardar el archivo de la tarea
	public UploadedFile getFileAdjunto() {
		return file_adjunto;
	}

	public void setFileAdjunto(UploadedFile file_adjunto) {
		this.file_adjunto = file_adjunto;
	}
	
	public void setContenido(String nombre)
			throws UnsupportedEncodingException {
		String nombreTarea = new String(nombre.getBytes("ISO-8859-1"), "ISO-8859-1");
		correoNuevo.setContenido(nombreTarea);
	}

	public String getContenido() {
		return "";
	}

}