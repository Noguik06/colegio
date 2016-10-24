/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.usuarios;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import ejb.AcudientesFacade;
import ejb.AnosacademicosFacade;
import ejb.CiudadesFacade;
import ejb.CursosFacade;
import ejb.EstudiantesFacade;
import ejb.GradosFacade;
import ejb.PreinscritosFacade;
import ejb.ProfesoresFacade;
import ejb.RegistromatriculasFacade;
import ejb.RelacionestudiantesacudientesFacade;
import ejb.RelacionusuariosrolesFacade;
import ejb.RolesFacade;
import ejb.UsuariosFacade;
import entities.Acudientes;
import entities.Anosacademicos;
import entities.Ciudades;
import entities.Cursos;
import entities.Estudiantes;
import entities.Grados;
import entities.Preinscritos;
import entities.Profesores;
import entities.Registromatriculas;
import entities.Relacionestudiantesacudientes;
import entities.Relacionusuariosroles;
import entities.Roles;
import entities.Usuarios;

 

/**
 *
 * @author juannoguera
 */
@ManagedBean(name = "registroUsuarios")
@ViewScoped
public class RegistroUsuarios implements Serializable {

    @EJB
    private EstudiantesFacade estudiantesFacade;
    @EJB
    private AcudientesFacade acudientesFacade;
    @EJB
    private RelacionestudiantesacudientesFacade relacionestudiantesacudientesFacade;
    @EJB
    private GradosFacade gradosFacade;
    @EJB
    private CiudadesFacade ciudadesFacade;
    @EJB
    private UsuariosFacade usuariosFacade;
    @EJB
    private ProfesoresFacade profesoresFacade;
    @EJB
    private RegistromatriculasFacade registromatriculasFacade;
    @EJB
    private CursosFacade cursosFacade;
    @EJB
    private AnosacademicosFacade anosacademicosFacade;
    @EJB
    private PreinscritosFacade preinscritosFacade;
    @EJB
    private RolesFacade rolesFacade;
    @EJB
    private RelacionusuariosrolesFacade relacionusuariosrolesFacade;
    ///##USUARIOS
    //Tipo Estudiantes
    private int tipoUsuario = 0;
    //Lista de busqueda de los estudiante
    private List<Usuarios> dataListUsuarios;
    //Usuario escogido
    private Usuarios usuarioEscogido;
    //Usuario preinscripción
    private Preinscritos usuarioPreinscripcion;
    //Lista de busqueda de los preinscritos
    private List<Preinscritos> dataListUsuariosPreinscritos;
    //formato para comprobar si es fecha
//    private static final String  DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
    //##QUERY
    String query = "";
    //Lista de los permisos de cada usuario
    private List<Object[]> dataListPermisos;
    
    @Resource
    private UserTransaction userTransaction;
    //Lista de los acudientes por estudiante
    private List<Relacionestudiantesacudientes> dataListAcudientesEstudiantes;

    /**
     * Creates a new instance of Estudiantes
     */
    public RegistroUsuarios() {
    }

    public void nuevoUsuario() {
        this.dataListAcudientesEstudiantes = null;
        this.usuarioEscogido = new Usuarios(new Long(0));
        this.usuarioEscogido.setSexo("Masculino");
        this.usuarioEscogido.setCiudadidentificacion("Bucaramanga");
        this.usuarioEscogido.setLugarnacimiento("Bucaramanga");
        this.usuarioEscogido.setGruposanguineo("A+");
        this.usuarioEscogido.setTipoidentificacion("T.I");
        this.usuarioEscogido.setProfesoresList(null);
        this.usuarioEscogido.setEstudiantesList(null);

    }

    //Metodo para seleccionar el usuarios escogido
    public void seleccionarUsuario(Usuarios usuarios) {
        this.usuarioEscogido = usuarios;
        dataListAcudientesEstudiantes = null;
        List<Estudiantes> dataListEstudiantes;
        List<Profesores> dataListProfesores;
        dataListEstudiantes = estudiantesFacade.findByLike("SELECT E FROM Estudiantes E WHERE E.usuarios.idusuarios = " + usuarioEscogido.getIdusuarios());
        dataListProfesores = profesoresFacade.findByLike("SELECT P FROM Profesores P WHERE P.usuarios.idusuarios = " + usuarioEscogido.getIdusuarios());
        if (!dataListEstudiantes.isEmpty()) {
            dataListAcudientesEstudiantes = relacionestudiantesacudientesFacade.findByLike("SELECT RE FROM Relacionestudiantesacudientes RE WHERE RE.estudiantes.idestudiantes = " + dataListEstudiantes.get(0).getIdestudiantes());
        }
        usuarioEscogido.setEstudiantesList(dataListEstudiantes);
        usuarioEscogido.setProfesoresList(dataListProfesores);
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.removeAttribute("usuariosescogido");
        session.setAttribute("usuariosescogido", usuarios);
        
        String query = "select r.idroles, r.nombre, case when rur.idusuarios is null then false else true end  "
        		+ "from  "
        		+ "roles r left join relacionusuariosroles rur on r.idroles = rur.idroles and rur.idusuarios = " + usuarios.getIdusuarios();
        
        dataListPermisos = rolesFacade.findByNative(query);
    }
    
    public List<Object[]> getDataListPermisos() {
		return dataListPermisos;
	}

	public void setDataListPermisos(List<Object[]> dataListPermisos) {
		this.dataListPermisos = dataListPermisos;
	}

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        byte[] bytes = IOUtils.toByteArray(event.getFile().getInputstream());
		usuarioEscogido.setFoto(bytes);
		FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.removeAttribute("usuariosescogido");
        session.setAttribute("usuariosescogido",usuarioEscogido);
		FacesMessage message = new FacesMessage("Exito", "La foto fui subida exitosamente. Por favor guarde los cambios realizados");
        FacesContext.getCurrentInstance().addMessage(null, message);
        RequestContext.getCurrentInstance().update("idFormDatosUsuarios:fotoUsuario");
        RequestContext.getCurrentInstance().update(":idFormDatosUsuarios:fotoUsuario");
    }

    //Metodo para la lista de estudiantes que hemos buscado
    public List<Usuarios> getDataListUsuarios() {
        if (dataListUsuarios == null) {
            if (query.trim().replaceAll(" ", "").length() > 0) {
                try {
                    String[] valores;
                    Pattern p = Pattern.compile(" ");
                    valores = query.trim().toString().split(" ");
                    boolean banderaA = false;
                    Matcher m;
                    boolean b;
                    boolean h = false;
                    String queryPrueba = "";
                    for (int i = 0; i < valores.length; i++) {
                        m = p.matcher(valores[i]);
                        b = m.matches();
                        if (!b && !h) {

                            queryPrueba = "SELECT U FROM Usuarios U WHERE (LOWER(U.nombres) LIKE  '%" + valores[i].toLowerCase() + "%' OR LOWER(U.apellidos) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(U.numeroidentificacion) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(U.nombredeusuario) LIKE '%" + valores[i].toLowerCase() + "%')";
                            h = true;
                        }
                        if (!b && h) {
                            queryPrueba += " AND (LOWER(U.nombres) LIKE  '%" + valores[i].toLowerCase() + "%' OR LOWER(U.apellidos) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(U.numeroidentificacion) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(U.nombredeusuario) LIKE '%" + valores[i].toLowerCase() + "%')";
                        }
                    }
                    if (h) {
                        queryPrueba += " ORDER BY  U.apellidos";
                        dataListUsuarios = usuariosFacade.findByLike(queryPrueba);
                    }
                } catch (Exception e) {
                    System.out.print("Error buscando los usuarios");
                }
                return dataListUsuarios;
            }
            dataListUsuarios = new ArrayList<Usuarios>();
        }
        return dataListUsuarios;
    }

    public void setDataListUsuarios(List<Usuarios> dataListUsuarios) {
        this.dataListUsuarios = dataListUsuarios;
    }

    //Número de usuarios
    public int dataListUsuariosTotales() {
        return usuariosFacade.findAll().size();
    }

    //
    public Usuarios getUsuarioEscogido() {
        return usuarioEscogido;
    }

    public void setUsuarioEscogido(Usuarios usuarioEscogido) {
        this.usuarioEscogido = usuarioEscogido;
    }

    //Metodo para colocar la ciudad de nacimiento
    public void setCiudadIdentificacion(String ciudad) {
        this.usuarioEscogido.setCiudadidentificacion(ciudad);
    }

    //Metodo para sacar la ciudad de nacimiento
    public String getCiudadIdentificacion() {
        if (this.usuarioEscogido == null) {
            return "";
        } else {
            if (this.usuarioEscogido.getCiudadidentificacion() == null) {
                return "";
            } else {
                return this.usuarioEscogido.getCiudadidentificacion();
            }
        }
    }

    public String getTipoIdentificacion() {
        if (usuarioEscogido == null) {
            return "";
        }
        return usuarioEscogido.getTipoidentificacion();
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        usuarioEscogido.setTipoidentificacion(tipoIdentificacion);
    }

    //Metodo par las ciudades
    public List<Ciudades> getDataListCiudades() {
        return ciudadesFacade.findByLikeAll("SELECT C FROM Ciudades C ORDER BY C.nombre");
    }

    //Metodo para colocar la ciudad de nacimiento
    public void setLugarNacimiento(String ciudad) {
        this.usuarioEscogido.setLugarnacimiento(ciudad);
    }

    //Metodo para sacar la ciudad de nacimiento
    public String getLugarNacimiento() {
        if (usuarioEscogido == null) {
            return "";
        } else {
            if (usuarioEscogido.getLugarnacimiento() == null) {
                return "";
            } else {
                return usuarioEscogido.getLugarnacimiento();
            }
        }
    }

    //Metodo para colocar la ciudad de nacimiento
    public void setTipoSangre(String gruposanguineo) {
        this.usuarioEscogido.setGruposanguineo(gruposanguineo);
    }

    //Metodo para sacar la ciudad de nacimiento
    public String getTipoSangre() {
        if (usuarioEscogido == null) {
            return "A+";
        } else {
            if (usuarioEscogido == null) {
                return "A+";
            } else {
                return usuarioEscogido.getGruposanguineo();
            }
        }
    }

    //##ACUDIENTES
    //QUERY BÚSQUEDA
    public void setQuery(String query) {
        this.query = query;
        dataListUsuarios = null;
    }

    public String getQuery() {
        return query;
    }

    //Metodo para crear el estudiante
    public synchronized void crearEditarUsuario() {
        if (usuarioEscogido.getApellidos() == null || usuarioEscogido.getApellidos().trim().length() == 0) {
            throw new ValidatorException(new FacesMessage("##1//El apellido no puede quedar en blanco"));
        }
        if (usuarioEscogido.getNombres() == null || usuarioEscogido.getNombres().trim().length() == 0) {
            throw new ValidatorException(new FacesMessage("##2//El nombre no puede quedar en blanco"));
        }

        if (usuarioEscogido.getNumeroidentificacion() == null || usuarioEscogido.getNumeroidentificacion().trim().length() == 0) {
            throw new ValidatorException(new FacesMessage("##3//El numero de identificacion no puede quedar en blanco"));
        } 
        if (usuarioEscogido.getFechanacimiento() == null) {
            throw new ValidatorException(new FacesMessage("##4//La fecha de nacimiento no puede quedar en blanco"));
        }
        if (usuarioEscogido.getDireccion() == null || usuarioEscogido.getDireccion().trim().length() == 0) {
            throw new ValidatorException(new FacesMessage("##5//La direccon no puede quedar en blanco"));
        }

        if (usuarioEscogido.getTelefono() == null || usuarioEscogido.getTelefono().trim().length() == 0) {
            throw new ValidatorException(new FacesMessage("##6//El telefono no puede quedar en blanco"));
        }

        if (usuarioEscogido.getEps() == null || usuarioEscogido.getEps().trim().length() == 0) {
            throw new ValidatorException(new FacesMessage("##7//La EPS no puede quedar en blanco"));
        }

        if (usuarioEscogido.getGruposanguineo() == null || usuarioEscogido.getGruposanguineo().trim().length() == 0) {
            throw new ValidatorException(new FacesMessage("##8//El tipo de sangre no puede quedar en blanco"));
        }
        
        try {
            userTransaction.begin();
        } catch (Exception e) {
        }
        //Miramos si el usuario es nuevo
        if (usuarioEscogido.getIdusuarios() == 0) {
            try {

                String nombre = usuarioEscogido.getNombres();
                String nombreDeUsuario = nombre.trim().split(" ")[0].toLowerCase();
                String segundoNombre = "";
                for (String letra : nombre.trim().split(" ")) {
                    if (!letra.equals(nombre.trim().split(" ")[0]) && letra.trim().length() > 0) {
                        if (!letra.equals(segundoNombre)) {
                            segundoNombre = letra.split("")[1];
                        }
                    }
                }

                String apellido = usuarioEscogido.getApellidos();
                String apellidoDeUsrio = apellido.trim().split(" ")[0].toLowerCase();
//          
                nombreDeUsuario += segundoNombre + apellidoDeUsrio;
                nombreDeUsuario = nombreDeUsuario.toLowerCase();

                nombreDeUsuario = nombreDeUsuario.replaceAll("á", "a");
                nombreDeUsuario = nombreDeUsuario.replaceAll("é", "e");
                nombreDeUsuario = nombreDeUsuario.replaceAll("í", "i");
                nombreDeUsuario = nombreDeUsuario.replaceAll("ó", "o");
                nombreDeUsuario = nombreDeUsuario.replaceAll("ú", "u");
                nombreDeUsuario = nombreDeUsuario.replaceAll("n", "n");

                usuarioEscogido.setNombredeusuario(nombreDeUsuario);
                usuarioEscogido.setPassword(nombreDeUsuario);
//            System.out.print(nombre + " nombre " + nombreDeUsuario);

                final String nombreUsuarioOriginal = nombreDeUsuario;
                boolean bandera = true;
                int i = 0;
                while (bandera) {
                    List<Usuarios> tmp = usuariosFacade.findByLikeAll("SELECT U FROM Usuarios U WHERE U.nombredeusuario  like '%" + nombreDeUsuario + "%'" + "");

                    if (tmp.isEmpty()) {
                        usuarioEscogido.setNombredeusuario(nombreDeUsuario);
                        usuarioEscogido.setPassword(nombreDeUsuario);
                        break;
                    }
                    i++;

                    nombreDeUsuario = nombreUsuarioOriginal + i;
                }

                Estudiantes estudiantes = null;
                Profesores profesores = null;

                if (usuarioEscogido.getEstudiantesList() != null && !usuarioEscogido.getEstudiantesList().isEmpty()) {
                    estudiantes = usuarioEscogido.getEstudiantesList().get(0);
                    estudiantes.setUsuarios(usuarioEscogido);
                    usuarioEscogido.setEstudiantesList(null);
                }

                if (usuarioEscogido.getProfesoresList() != null && !usuarioEscogido.getProfesoresList().isEmpty()) {
                    profesores = usuarioEscogido.getProfesoresList().get(0);
                    profesores.setUsuarios(usuarioEscogido);
                    usuarioEscogido.setProfesoresList(null);
                }

                try {

                    System.out.print("Entrando a crear a el usuario");
                    usuariosFacade.create(usuarioEscogido);
                } catch (Exception e) {
                    System.out.print("Error al crear el usuario");
                    System.out.print(e.getStackTrace());
                    System.out.print(e.getCause());
                    System.out.print(e.getLocalizedMessage());
                }

                if (profesores != null) {
                    try {
                        profesoresFacade.create(profesores);
                        List<Profesores> tmp = new ArrayList<Profesores>();
                        tmp.add(profesores);
                        usuarioEscogido.setProfesoresList(tmp);
                    } catch (Exception e) {
                        System.out.print("Error al crear el profesor");
                        System.out.print(e.getStackTrace());
                        System.out.print(e.getCause());
                        System.out.print(e.getLocalizedMessage());
                    }
                }

                if (estudiantes != null) {
                    try {
                        estudiantesFacade.create(estudiantes);
                        List<Estudiantes> tmp = new ArrayList<Estudiantes>();
                        tmp.add(estudiantes);
                        usuarioEscogido.setEstudiantesList(tmp);
                    } catch (Exception e) {
                        System.out.print("Error al crear el estudiante");
                        System.out.print(e.getStackTrace());
                        System.out.print(e.getCause());
                        System.out.print(e.getLocalizedMessage());
                    }
                }

            } catch (Exception e) {
                System.out.print("Error al crear el usuario");
                System.out.print(e.getStackTrace());
                System.out.print(e.getCause());
                System.out.print(e.getLocalizedMessage());
            }
        } else {


            Estudiantes estudiantes = null;
            Profesores profesores = null;

            if (usuarioEscogido.getEstudiantesList() != null && !usuarioEscogido.getEstudiantesList().isEmpty()) {
                estudiantes = usuarioEscogido.getEstudiantesList().get(0);
                estudiantes.setUsuarios(usuarioEscogido);

                if (estudiantes.getIdestudiantes() == 0) {
                    estudiantesFacade.create(estudiantes);
                }

                usuarioEscogido.setEstudiantesList(null);
            }

            if (usuarioEscogido.getProfesoresList() != null && !usuarioEscogido.getProfesoresList().isEmpty()) {
                profesores = usuarioEscogido.getProfesoresList().get(0);
                profesores.setUsuarios(usuarioEscogido);
                System.out.print(usuarioEscogido.getPassword() + " PASS ");


                usuarioEscogido.setProfesoresList(null);
            }

            if(usuarioEscogido.getPassword() == null || usuarioEscogido.getPassword().trim().replaceAll(" ", "").length() == 0){
                usuarioEscogido.setPassword(usuariosFacade.find(usuarioEscogido.getIdusuarios()).getPassword());
            }
            
            try{
            	usuariosFacade.edit(usuarioEscogido);
            	FacesMessage msg = new FacesMessage("Exitoso!", "El usuario ha sido guardado correctamente");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }catch(Exception e){
            	System.out.print(e.getMessage());
            	FacesMessage msg = new FacesMessage("Error!", "Ha ocurrido un error guardando al usuario");
            	msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            	FacesContext.getCurrentInstance().addMessage(null, msg);
            }


            if (estudiantes != null) {
            }


            if (profesores != null) {
            }
        }

        //Revisamos la lista de los acudientes
        if (dataListAcudientesEstudiantes != null && !dataListAcudientesEstudiantes.isEmpty()) {
            for (Relacionestudiantesacudientes rl : dataListAcudientesEstudiantes) {
                if (rl.getAcudientes().getIdacudientes() == 0) {
                    try {
                        if (rl.getAcudientes().getNombres().trim().replaceAll(" ", "").length() > 0 && rl.getAcudientes().getApellidos().trim().replaceAll(" ", "").length() > 0
                                && rl.getAcudientes().getNumeroidentificacion().trim().replaceAll(" ", "").length() > 0) {
                            acudientesFacade.create(rl.getAcudientes());
                            try {
                                relacionestudiantesacudientesFacade.create(rl);
                            } catch (Exception e) {
                                System.out.print("No se pudo crear la relacion entre el estudiante y el acudiente");
                            }
                        }
                    } catch (Exception e) {
                        System.out.print("No se pudo crear el acudiente");
                    }

                } else {

                    if (rl.getAcudientes().getNombres().trim().replaceAll(" ", "").length() > 0 && rl.getAcudientes().getApellidos().trim().replaceAll(" ", "").length() > 0
                            && rl.getAcudientes().getNumeroidentificacion().trim().replaceAll(" ", "").length() > 0) {
                        acudientesFacade.edit(rl.getAcudientes());
                        if (rl.getIdrelacionestudiantesacudientes() == 0) {
                            relacionestudiantesacudientesFacade.create(rl);
                        } else {
                            relacionestudiantesacudientesFacade.edit(rl);
                        }
                    }
                }
            }

        }        

        try {
            userTransaction.commit();
            if(dataListUsuarios != null){
				dataListUsuarios.clear();
				dataListUsuarios.add(usuarioEscogido);
            }
        } catch (Exception e) {
            System.out.print("Error al al tratar de crear lo que se estaba haciendo");
            System.out.print(e.getMessage());
            System.out.print(e.getStackTrace());
            System.out.print(e.getCause());
            System.out.print(e.getLocalizedMessage());
        }
        
        if(dataListPermisos != null){
	        Iterator it = dataListPermisos.iterator();
	        while(it.hasNext()){
	        	Object[] o = (Object[]) it.next();
	        	List<Relacionusuariosroles> dataListRUR = relacionusuariosrolesFacade.findByLike("SELECT R FROM Relacionusuariosroles R "
	        			+ "WHERE R.usuarios.idusuarios = " + usuarioEscogido.getIdusuarios() + " AND R.roles.idroles = " + o[0]);
	        	if(Boolean.valueOf(o[2].toString())){
	        		if(dataListRUR.isEmpty()){
	        			//Se crea el nuevo registro
	        			Relacionusuariosroles relacionusuariosroles = new Relacionusuariosroles(new Long(0));
	        			Roles r = rolesFacade.find(new Long(o[0].toString()));
	        			relacionusuariosroles.setRoles(r);
	        			relacionusuariosroles.setUsuario(usuarioEscogido);
	        			relacionusuariosrolesFacade.create(relacionusuariosroles);
	        		}
	        	}else{
	        		if(!dataListRUR.isEmpty()){
	        			//Se elimina el anterior
	        			relacionusuariosrolesFacade.remove(dataListRUR.get(0));
	        		}
	        	}
	        }
        }


        usuarioEscogido = null;

        dataListUsuarios = null;

    }

    public void eliminarUsuarios() {


        try {
            userTransaction.begin();
        } catch (Exception e) {
        }

        List<Estudiantes> dataListTmp;
        dataListTmp = estudiantesFacade.findByLike("SELECT E FROM Estudiantes E WHERE E.usuarios.idusuarios = " + usuarioEscogido.getIdusuarios());

        if (!dataListTmp.isEmpty()) {
            System.out.print("Estoy eliminando las matriculas ");
            List<Registromatriculas> datListTmpRM = new ArrayList<Registromatriculas>();
            datListTmpRM = registromatriculasFacade.findByLike("SELECT R FROM Registromatriculas R WHERE R.estudiantes.idestudiantes = " + dataListTmp.get(0).getIdestudiantes());
            if (!datListTmpRM.isEmpty()) {
                try {
                    registromatriculasFacade.remove(datListTmpRM.get(0));
                } catch (Exception e) {
                    throw new ValidatorException(new FacesMessage("No se pudo eliminar el usuario"));
                }
            }

            try {
                estudiantesFacade.remove(dataListTmp.get(0));
            } catch (Exception e) {
                throw new ValidatorException(new FacesMessage("No se pudo eliminar el usuario"));
            }
        }

        List<Profesores> dataListTmp2;
        dataListTmp2 = profesoresFacade.findByLike("SELECT P FROM Profesores P WHERE P.usuarios.idusuarios = " + usuarioEscogido.getIdusuarios());
        if (!dataListTmp2.isEmpty()) {
            System.out.print("Estoy entrando para borrar los profesores");
            profesoresFacade.remove(dataListTmp2.get(0));
            usuarioEscogido.setProfesoresList(null);
        }

        usuariosFacade.remove(usuarioEscogido);

        usuarioEscogido = null;

        dataListUsuarios = null;
        try {
            userTransaction.commit();
        } catch (Exception e) {
            throw new ValidatorException(new FacesMessage("No se pudo eliminar el usuario"));
        }
    }

    //Metodo para desmatricular un estudiante
    public void desmatricularUsuarios() {
        List<Registromatriculas> dataListTmp;
        dataListTmp = registromatriculasFacade.findByLike("SELECT E FROM Registromatriculas E WHERE " +
        		" E.fecharetiro is null and E.estudiantes.usuarios.idusuarios = " + usuarioEscogido.getIdusuarios() +
        		" AND E.anosacademicos.estadoactivo = true");

        if (!dataListTmp.isEmpty()) {
            try {
                userTransaction.begin();
            } catch (Exception e) {
            }

            dataListTmp.get(0).setFecharetiro(new Date());
            registromatriculasFacade.edit(dataListTmp.get(0));

            try {
                userTransaction.commit();
            } catch (Exception e) {
            	System.out.print(e.getMessage());
            	System.out.print(e.getCause());
            	System.out.print(e.getMessage());
                System.out.print("El usuario no pudo ser modificado");
            }

        }
    }

    //Metodo para matricular un estudiante
    public String matricularUsuarios() {
        List<Registromatriculas> dataListRegistromatriculas = registromatriculasFacade.findByLike("SELECT R FROM Registromatriculas R " +
        		"WHERE R.anosacademicos.estadoactivo = true " +
        				"and R.estudiantes.usuarios.idusuarios = " + usuarioEscogido.getIdusuarios());
        if (!dataListRegistromatriculas.isEmpty()) {
            try {
                userTransaction.begin();
            } catch (Exception e) {
            }

            //Editamos el registro de matricula
            try {
                dataListRegistromatriculas.get(0).setFecharetiro(null);
                dataListRegistromatriculas.get(0).setGrados(usuarioEscogido.getEstudiantesList().get(0).getGrados());
                List<Cursos> tmpListCursos = cursosFacade.findByLike("SELECT C FROM Cursos C WHERE C.grados.idgrados = " + 
                usuarioEscogido.getEstudiantesList().get(0).getGrados().getIdgrados() + " AND C.anosacademicos.estadoactivo = true");
                
                dataListRegistromatriculas.get(0).setCursos(tmpListCursos.get(0));
                registromatriculasFacade.edit(dataListRegistromatriculas.get(0));
            } catch (Exception e) {
                System.out.print("Error al tratar de actualizar la matrIcula " + e.getMessage());
                return "Error";
            }

            //Editamos el estudiante por si hubo que cambiarle el grado al que esta matriculado
            try {
                estudiantesFacade.edit(usuarioEscogido.getEstudiantesList().get(0));
            } catch (Exception e) {
                System.out.print("Error al tratar de actualizar el estudiante " + e.getMessage());
                return "Error";
            }
            
            try{
                registromatriculasFacade.metodo("DELETE FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = " + dataListRegistromatriculas.get(0).getIdregistromatriculas() + " AND N.registromatriculas.anosacademicos.estadoactivo = true and N.relacionnotaslogrosdimensionboletin.relacionnotasdimension.relaciondimensionesasignaturasano.cursos.grados.idgrados !=  " + usuarioEscogido.getEstudiantesList().get(0).getGrados().getIdgrados());        
            }catch(Exception e){
                System.out.print("Error eliminando las otroas notas " + e.getMessage());
                return "Error";
            }

            try {
                userTransaction.commit();
            } catch (Exception e) {
                System.out.print("Error al tratar de matricular a este estudiante " + e.getMessage());
            }
            
        }

        //Miramos si este estudiante se puede matricular
        if (getMatriculaActiva()) {
            try {
                userTransaction.begin();
            } catch (Exception e) {
            }
            
            
            if (usuarioEscogido.getEstudiantesList().get(0).getGrados().getIdgrados() != -10) {
                List<Anosacademicos> tmpListAA = anosacademicosFacade.findByLike("SELECT A FROM Anosacademicos A WHERE A.estadoactivo = true");
                List<Cursos> tmpListCursos = cursosFacade.findByLike("SELECT C FROM Cursos C WHERE C.grados.idgrados = " + usuarioEscogido.getEstudiantesList().get(0).getGrados().getIdgrados());
                Registromatriculas registromatriculas = new Registromatriculas(new Long(0));
                registromatriculas.setAnosacademicos(tmpListAA.get(0));
                registromatriculas.setGrados(usuarioEscogido.getEstudiantesList().get(0).getGrados());
                registromatriculas.setCursos(tmpListCursos.get(0));
                registromatriculas.setFechamatricula(new Date());
                registromatriculas.setEstudiantes(usuarioEscogido.getEstudiantesList().get(0));

                //Creamos el registro de la matricula del estudiante
                try {
                    registromatriculasFacade.create(registromatriculas);
                } catch (Exception e) {
                    System.out.print("Error al tratar de crear el registro de la matricula");
                }
                
                //Actualizamos el grado del estudiante
                try{
                    estudiantesFacade.edit(usuarioEscogido.getEstudiantesList().get(0));
                }catch(Exception e){
                    System.out.print("Error al tratar de crear el estudiante");
                }
            }else{
                System.out.print("No ha escogido un grado valido para registrar el estudiante");
            }
            
            try {
                userTransaction.commit();
            } catch (Exception e) {
            }
        }
        
        return "";
//        int year = registromatriculasFacade.retornarValorInteger("SELECT EXTRACT(YEAR FROM R.fechamatricula) FROM Registromatriculas R where EXTRACT(YEAR FROM R.fechamatricula) = (SELECT  EXTRACT(YEAR FROM A.fechainicio) FROM anosacademicos  A where A.estadoactivo  = true)");
//        registromatriculasFacade.metodo("SELECT year(e.fechamatricula) FROM Registromatriculas e");
//        SELECT idregistromatriculas, EXTRACT(YEAR FROM fechamatricula) year_type FROM registromatriculas where EXTRACT(YEAR FROM fechamatricula) = 2013;
    }

    //Metodo para saber si se puede matricular el usuario
    public boolean getMatriculaActiva() {
        List<Registromatriculas> dataListRegistromatriculas = registromatriculasFacade.findByLike("SELECT R FROM Registromatriculas R WHERE R.fecharetiro is not null and R.anosacademicos.estadoactivo = true and R.estudiantes.usuarios.idusuarios = " + usuarioEscogido.getIdusuarios());
        if (!dataListRegistromatriculas.isEmpty()) {
            return true;
        }

        if (estudiantesFacade.retornarValorInteger("SELECT COUNT(E) FROM Estudiantes E WHERE E.usuarios.idusuarios = " + usuarioEscogido.getIdusuarios()) > 0
                && !getDesmatriculaActiva()) {
            return true;
        }

        return false;
    }

    //Metodo para saber si se puede matricular el usuario
    public boolean getDesmatriculaActiva() {
        List<Registromatriculas> dataListTmp;
        dataListTmp = registromatriculasFacade.findByLike("SELECT E FROM Registromatriculas E WHERE E.fecharetiro is null and E.estudiantes.usuarios.idusuarios = " + usuarioEscogido.getIdusuarios());
        if (!dataListTmp.isEmpty()) {
            return true;
        }

        return false;
    }

    //###TIPO DE USUARIO
    //Metodo para obtener los usuarios tipo estudiantes
    public boolean getTipoUsuarioEstudiante() {
        if (usuarioEscogido.getEstudiantesList() != null && !usuarioEscogido.getEstudiantesList().isEmpty()) {
            if (usuarioEscogido.getEstudiantesList().get(0).getIdestudiantes() < 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    public void setTipoUsuarioEstudiante(boolean tipoUsuario) {
        if (usuarioEscogido.getEstudiantesList() != null && !usuarioEscogido.getEstudiantesList().isEmpty()) {
            if (!tipoUsuario) {
                if (usuarioEscogido.getEstudiantesList().get(0).getIdestudiantes() > 0) {
                    usuarioEscogido.getEstudiantesList().get(0).setIdestudiantes(usuarioEscogido.getEstudiantesList().get(0).getIdestudiantes() * -1);
                } else {
                    if (usuarioEscogido.getEstudiantesList().get(0).getIdestudiantes() == 0) {
                        usuarioEscogido.getEstudiantesList().clear();
                    }
                }

            } else {
                if (usuarioEscogido.getEstudiantesList().get(0).getIdestudiantes() < 0) {
                    usuarioEscogido.getEstudiantesList().get(0).setIdestudiantes(Math.abs(usuarioEscogido.getEstudiantesList().get(0).getIdestudiantes()));
                }
            }
        } else {
            if (tipoUsuario) {
                Estudiantes estudiantes = new Estudiantes(new Long(0));
                estudiantes.setUsuarios(usuarioEscogido);
                estudiantes.setCodigo("00");
                List<Estudiantes> tmp = new ArrayList<Estudiantes>();
                tmp.add(estudiantes);
                usuarioEscogido.setEstudiantesList(tmp);
            }
        }


        if (tipoUsuario) {
        } else {
        }
    }

    //Metodo para obtener los usuarios tipo profesor
    public boolean getTipoUsuarioProfesor() {
        if (usuarioEscogido.getProfesoresList() != null && !usuarioEscogido.getProfesoresList().isEmpty()) {
            if (usuarioEscogido.getProfesoresList().get(0).getIdprofesores() < 0) {
                return false;
            }

            return true;
        }
        return false;
    }

    public void setTipoUsuarioProfesor(boolean tipoUsuario) {

        if (usuarioEscogido.getProfesoresList() != null && !usuarioEscogido.getProfesoresList().isEmpty()) {
            if (!tipoUsuario) {
                if (usuarioEscogido.getProfesoresList().get(0).getIdprofesores() == 0) {
                    usuarioEscogido.getProfesoresList().clear();
                } else {
                    usuarioEscogido.getProfesoresList().get(0).setIdprofesores(usuarioEscogido.getProfesoresList().get(0).getIdprofesores() * -1);
                }
            } else {
                if (usuarioEscogido.getProfesoresList().get(0).getIdprofesores() < 0) {
                    usuarioEscogido.getProfesoresList().get(0).setIdprofesores(Math.abs(usuarioEscogido.getProfesoresList().get(0).getIdprofesores()));
                }
            }
        } else {
            if (tipoUsuario) {
                Profesores profesor = new Profesores(new Long(0));
                List<Profesores> tmp = new ArrayList<Profesores>();
                tmp.add(profesor);
                usuarioEscogido.setProfesoresList(tmp);
            }
        }
    }

    //Metodo para obtener los usuarios tipo otros
    public boolean getTipoUsuarioOtros() {
        if ((usuarioEscogido.getEstudiantesList() == null || usuarioEscogido.getEstudiantesList().isEmpty())
                && (usuarioEscogido.getProfesoresList() == null || usuarioEscogido.getProfesoresList().isEmpty())) {
            return true;
        } else {
            if ((usuarioEscogido.getProfesoresList() == null || usuarioEscogido.getProfesoresList().isEmpty()
                    || usuarioEscogido.getProfesoresList().get(0).getIdprofesores() < 0)
                    && (usuarioEscogido.getEstudiantesList() != null && !usuarioEscogido.getEstudiantesList().isEmpty()
                    && usuarioEscogido.getEstudiantesList().get(0).getIdestudiantes() < 0)) {
                return true;
            }

            if ((usuarioEscogido.getEstudiantesList() == null || usuarioEscogido.getEstudiantesList().isEmpty()
                    || usuarioEscogido.getEstudiantesList().get(0).getIdestudiantes() < 0)
                    && (usuarioEscogido.getProfesoresList() != null && !usuarioEscogido.getProfesoresList().isEmpty()
                    && usuarioEscogido.getProfesoresList().get(0).getIdprofesores() < 0)) {
                return true;
            }
        }
        return false;
    }

//    public void colocarNomadsbresDeUsuarios() {
//        List<Usuarios> dataListUsuariosSinUsername = usuariosFacade.findByLikeAll("SELECT U FROM Usuarios U WHERE U.nombredeusuario is null");
//        System.out.print(dataListUsuariosSinUsername.size());
//        for (Usuarios us : dataListUsuariosSinUsername) {
//            String nombre = us.getNombres();
//            String nombreDeUsuario = nombre.trim().split(" ")[0].toLowerCase();
//            String segundoNombre = "";
//            for (String letra : nombre.trim().split(" ")) {
//                if (!letra.equals(nombre.trim().split(" ")[0]) && letra.trim().length() > 0) {
//                    if (!letra.equals(segundoNombre)) {
//                        segundoNombre = letra.split("")[1];
//                    }
//                }
//            }
//
//            String apellido = us.getApellidos();
//            String apellidoDeUsrio = apellido.trim().split(" ")[0].toLowerCase();
////          
//            nombreDeUsuario += segundoNombre + apellidoDeUsrio;
//            nombreDeUsuario = nombreDeUsuario.toLowerCase();
//
//            nombreDeUsuario = nombreDeUsuario.replaceAll("��", "a");
//            nombreDeUsuario = nombreDeUsuario.replaceAll("��", "e");
//            nombreDeUsuario = nombreDeUsuario.replaceAll("��", "i");
//            nombreDeUsuario = nombreDeUsuario.replaceAll("��", "o");
//            nombreDeUsuario = nombreDeUsuario.replaceAll("��", "u");
//            nombreDeUsuario = nombreDeUsuario.replaceAll("��", "n");
//
//            us.setNombredeusuario(nombreDeUsuario);
////            System.out.print(nombre + " nombre " + nombreDeUsuario);
//
//            boolean bandera = true;
//            while (bandera) {
//                List<Usuarios> tmp = usuariosFacade.findByLikeAll("SELECT U FROM Usuarios U WHERE U.nombredeusuario  like '%" + nombreDeUsuario + "%'" + "");
//                if (tmp.isEmpty()) {
//                    break;
//                }
//
//                String nombre1 = tmp.get(0).getNombres();
//                String nombre1DeUsuario = nombre1.trim().split(" ")[0].toLowerCase();
//                String segundoNombre1 = "";
//                for (String letra : nombre1.trim().split(" ")) {
//                    if (!letra.equals(nombre1.trim().split(" ")[0]) && letra.trim().length() > 0) {
//                        if (!letra.equals(segundoNombre1)) {
//                            segundoNombre1 = letra.split("")[1];
//                        }
//                    }
//                }
//
//                String apellido1 = us.getApellidos();
//                String apellidoDeUsuario1 = tmp.get(0).getApellidos().trim().split(" ")[0].toLowerCase();
////          
//                nombre1DeUsuario += segundoNombre1 + apellidoDeUsuario1;
//                nombre1DeUsuario = nombre1DeUsuario.toLowerCase();
////                System.out.print(apellidoDeUsuario1 + " username ");
//
//
//                nombre1DeUsuario = nombre1DeUsuario.replaceAll("a", "a");
//                nombre1DeUsuario = nombre1DeUsuario.replaceAll("e", "e");
//                nombre1DeUsuario = nombre1DeUsuario.replaceAll("������", "i");
//                nombre1DeUsuario = nombre1DeUsuario.replaceAll("������", "o");
//                nombre1DeUsuario = nombre1DeUsuario.replaceAll("������", "u");
//                nombre1DeUsuario = nombre1DeUsuario.replaceAll("������", "n");
//
//                if (tmp.get(0).getNombredeusuario().split(nombre1DeUsuario).length > 1) {
//                    String letraNumero = tmp.get(0).getNombredeusuario().split(nombre1DeUsuario)[1];
//                    Integer numero = Integer.parseInt(letraNumero);
//                    numero++;
//                    tmp.get(0).getNombredeusuario().split(nombre1DeUsuario)[0] += numero + "";
//                    us.setNombredeusuario(tmp.get(0).getNombredeusuario().split(nombre1DeUsuario)[0]);
//                    break;
//                } else {
////                    System.out.print("Sal������ " + nombreDeUsuario);
//                    nombreDeUsuario += "1";
//                    us.setNombredeusuario(nombreDeUsuario);
//                    break;
//                }
//
//            }
//
//            usuariosFacade.edit(us);
//
//        }
//
//        List<Estudiantes> dataListTmp = new ArrayList<Estudiantes>();
//        dataListTmp = estudiantesFacade.findAll();
//        for (Estudiantes e : dataListTmp) {
//            Registromatriculas registromatriculas = new Registromatriculas(new Long(0));
//            registromatriculas.setEstudiantes(e);
//            registromatriculas.setGrados(e.getGrados());
//            if (!cursosFacade.findByLike("SELECT C FROM Cursos C WHERE C.grados.idgrados = " + e.getGrados().getIdgrados()).isEmpty()
//                    && registromatriculasFacade.findByLike("SELECT R FROM Registromatriculas R WHERE R.estudiantes.idestudiantes = " + e.getIdestudiantes()).isEmpty()) {
//                Cursos cursos = cursosFacade.findByLike("SELECT C FROM Cursos C WHERE C.grados.idgrados = " + e.getGrados().getIdgrados()).get(0);
//                registromatriculas.setCursos(cursos);
//                registromatriculas.setAnosacademicos(new Anosacademicos(new Long(1)));
//                registromatriculasFacade.create(registromatriculas);
//            }
//        }
//    }
    public Sesiones getSesiones() {
        FacesContext FCInstance = FacesContext.getCurrentInstance();
        String theBeanName = "sesiones";
        Object bean = FCInstance.getELContext().getELResolver().getValue(
                FCInstance.getELContext(), null, theBeanName);
        Sesiones sesiones = (Sesiones) bean;
        if (sesiones != null && sesiones.getUsuarios() != null) {
            return sesiones;
        }

        return null;
    }

    //##ESTA PARTE ES PARA LOS USUARIOS ESCOGIDOS
    public boolean miUsuarioEscogido() {
        if (getSesiones() == null) {
            return false;
        }

        usuarioEscogido = getSesiones().getUsuarios();
        return true;
    }

    public List<Relacionestudiantesacudientes> getDataListAcudientesEstudiantes() {
        if (getSesiones() == null) {
            return null;
        }

        List<Estudiantes> tmpEstudiantes = estudiantesFacade.findByLike("SELECT E FROM Estudiantes E where e.usuarios.idusuarios = " + getSesiones().getUsuarios().getIdusuarios());

        if (tmpEstudiantes != null && !tmpEstudiantes.isEmpty() && dataListAcudientesEstudiantes == null) {
            dataListAcudientesEstudiantes = relacionestudiantesacudientesFacade.findByLike("SELECT RE FROM Relacionestudiantesacudientes RE WHERE RE.estudiantes.idestudiantes = " + tmpEstudiantes.get(0).getIdestudiantes());
        }

        return dataListAcudientesEstudiantes;
    }

    //####ACUDIENTES ESTUDIANTES 
    public List<Relacionestudiantesacudientes> getDataListAcudientesEstudiantesAdministracion() {
//        if (getSesiones() == null) {
//            return null;
//        }
//
//        List<Estudiantes> tmpEstudiantes = estudiantesFacade.findByLike("SELECT E FROM Estudiantes E where e.usuarios.idusuarios = " + getSesiones().getUsuarios().getIdusuarios());
//
//        if (tmpEstudiantes != null && !tmpEstudiantes.isEmpty() && dataListAcudientesEstudiantes == null) {
//            dataListAcudientesEstudiantes = relacionestudiantesacudientesFacade.findByLike("SELECT RE FROM Relacionestudiantesacudientes RE WHERE RE.estudiantes.idestudiantes = " + tmpEstudiantes.get(0).getIdestudiantes());
//        }

        return dataListAcudientesEstudiantes;
    }

    public void setDataListAcudientesEstudiantesAdministracion(List<Relacionestudiantesacudientes> dataListAcudientesEstudiantes) {
        this.dataListAcudientesEstudiantes = dataListAcudientesEstudiantes;
    }

    //Metodo para agregarle al estudiante mas acudientes
    public void agregarAcudienteEstudiante() {
        Relacionestudiantesacudientes relacionestudiantesacudientes = new Relacionestudiantesacudientes(new Long(0));
        Acudientes acudientes = new Acudientes(new Long(0));
        acudientes.setNumeroidentificacion("0");
        relacionestudiantesacudientes.setAcudientes(acudientes);
        relacionestudiantesacudientes.setEstudiantes(usuarioEscogido.getEstudiantesList().get(0));
        relacionestudiantesacudientes.setParentesco("Padre");
        if (dataListAcudientesEstudiantes == null) {
            dataListAcudientesEstudiantes = new ArrayList<Relacionestudiantesacudientes>();
        }
        dataListAcudientesEstudiantes.add(relacionestudiantesacudientes);
    }

    //Metodo para eliminar Acudientes
    public void eliminarAcudiente(Relacionestudiantesacudientes relacionestudiantesacudientes) {
        if (relacionestudiantesacudientes.getIdrelacionestudiantesacudientes() > 0) {
            relacionestudiantesacudientesFacade.remove(relacionestudiantesacudientes);
        }
        acudientesFacade.remove(relacionestudiantesacudientes.getAcudientes());
        dataListAcudientesEstudiantes.remove(relacionestudiantesacudientes);
//        dataListAcudientesEstudiantes = null;
    }
    private List<Grados> datalistGrados;

    public List<Grados> getDataListGrados() {
        if (datalistGrados == null) {
            datalistGrados = gradosFacade.findByLike("SELECT G FROM Grados G ORDER BY G.numero");
        }
        return datalistGrados;
    }

    //Propiedades del grado
    public int getGrado() {
        if (usuarioEscogido.getEstudiantesList().get(0).getGrados() == null) {
            return 0;
        }
        return usuarioEscogido.getEstudiantesList().get(0).getGrados().getIdgrados();
    }

    public void setGrado(int idgrados) {
        usuarioEscogido.getEstudiantesList().get(0).setGrados(new Grados(idgrados));
    }

    //###PREINSCRIPCIÓN
    public Preinscritos getUsuarioPreinscripcion(){
    	if(usuarioPreinscripcion  == null){
    		usuarioPreinscripcion = new Preinscritos(new Long(0));
    	}
    	return usuarioPreinscripcion;
    }
    
    public void setUsuariosPreinscripcion(Preinscritos usuarios){
    	this.usuarioPreinscripcion = usuarios;
    }
    
    //Método para crear una preinscripción
    public void crearPreinscripcion() {
    	try{
    		boolean bandera = true;
    		if(usuarioPreinscripcion == null || usuarioPreinscripcion.getNombres() == null
    				|| usuarioPreinscripcion.getNombres().trim().length() == 0){
    			FacesMessage msg = new FacesMessage("Error!", "El nombre del estudiante es obligatorio");
    			msg.setSeverity(msg.SEVERITY_ERROR);
    			FacesContext.getCurrentInstance().addMessage(null, msg);
    			bandera = false;
    		}
    		if(usuarioPreinscripcion == null || usuarioPreinscripcion.getApellidos() == null
    				|| usuarioPreinscripcion.getApellidos().trim().length() == 0){
    			FacesMessage msg = new FacesMessage("Error!", "El apellido del estudiante es obligatorio");
    			msg.setSeverity(msg.SEVERITY_ERROR);
    			FacesContext.getCurrentInstance().addMessage(null, msg);
    			bandera = false;
    		}
    		if(usuarioPreinscripcion == null || usuarioPreinscripcion.getGrado() == null
    				|| usuarioPreinscripcion.getGrado() == -10){
    			FacesMessage msg = new FacesMessage("Error!", "El grado del estudiante es obligatorio");
    			msg.setSeverity(msg.SEVERITY_ERROR);
    			FacesContext.getCurrentInstance().addMessage(null, msg);
    			bandera = false;
    		}
    		if(usuarioPreinscripcion == null || usuarioPreinscripcion.getTelefono() == null
    				|| usuarioPreinscripcion.getTelefono().trim().length() == 0){
    			FacesMessage msg = new FacesMessage("Error!", "El teléfono del estudiante es obligatorio");
    			msg.setSeverity(msg.SEVERITY_ERROR);
    			FacesContext.getCurrentInstance().addMessage(null, msg);
    			bandera = false;
    		}
    		if(usuarioPreinscripcion == null || usuarioPreinscripcion.getEdad() == null
    				|| usuarioPreinscripcion.getEdad().trim().length() == 0){
    			FacesMessage msg = new FacesMessage("Error!", "La edad del estudiante es obligatoria");
    			msg.setSeverity(msg.SEVERITY_ERROR);
    			FacesContext.getCurrentInstance().addMessage(null, msg);
    			bandera = false;
    		}
    		if(usuarioPreinscripcion == null || usuarioPreinscripcion.getAcudiente() == null
    				|| usuarioPreinscripcion.getAcudiente().trim().length() == 0){
    			FacesMessage msg = new FacesMessage("Error!", "El acudiente del estudiante es obligatorio");
    			msg.setSeverity(msg.SEVERITY_ERROR);
    			FacesContext.getCurrentInstance().addMessage(null, msg);
    			bandera = false;
    		}
    		if(bandera){
	    		preinscritosFacade.create(usuarioPreinscripcion);
	    		FacesMessage msg = new FacesMessage("Felicitaciones!", "La preinscripción se ha realizado con éxito. En breve nos comunicaremos con usted");
	//            msg.setSeverity(msg.SEVERITY_ERROR);
	            FacesContext.getCurrentInstance().addMessage(null, msg);
	            usuarioPreinscripcion = null;
    		}
    	}catch(Exception e){
    		
    	}
    }
    
    //Método para buscar a los nuevos usuarios
    public List<Preinscritos> getDataListUsuariosPreinscritos() {
        if (dataListUsuariosPreinscritos == null) {
        	dataListUsuariosPreinscritos = preinscritosFacade.findByLikeAll("SELECT P FROM Preinscritos P ORDER BY P.apellidos");
        }
//            if (query.trim().replaceAll(" ", "").length() > 0) {
//                try {
//                    String[] valores;
//                    Pattern p = Pattern.compile(" ");
//                    valores = query.trim().toString().split(" ");
//                    boolean banderaA = false;
//                    Matcher m;
//                    boolean b;
//                    boolean h = false;
//                    String queryPrueba = "";
//                    for (int i = 0; i < valores.length; i++) {
//                        m = p.matcher(valores[i]);
//                        b = m.matches();
//                        if (!b && !h) {
//
//                            queryPrueba = "SELECT U FROM Usuarios U WHERE (LOWER(U.nombres) LIKE  '%" + valores[i].toLowerCase() + "%' OR LOWER(U.apellidos) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(U.numeroidentificacion) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(U.nombredeusuario) LIKE '%" + valores[i].toLowerCase() + "%')";
//                            h = true;
//                        }
//                        if (!b && h) {
//                            queryPrueba += " AND (LOWER(U.nombres) LIKE  '%" + valores[i].toLowerCase() + "%' OR LOWER(U.apellidos) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(U.numeroidentificacion) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(U.nombredeusuario) LIKE '%" + valores[i].toLowerCase() + "%')";
//                        }
//                    }
//                    if (h) {
//                        queryPrueba += " ORDER BY  U.apellidos";
//                        dataListUsuariosPreinscritos = preinscritosFacade.findByLike(queryPrueba);
//                    }
//                } catch (Exception e) {
//                    System.out.print("Error buscando los usuarios");
//                }
//                return dataListUsuariosPreinscritos;
//            }
//            dataListUsuariosPreinscritos = new ArrayList<Preinscritos>();
//        }
        return dataListUsuariosPreinscritos;
    }
    
    public void setDataListUsuariosPreinscritos(List<Preinscritos> dataListUsuariosPreinscritos) {
        this.dataListUsuariosPreinscritos = dataListUsuariosPreinscritos;
    }
    
    public void seleccionarUsuarioPreinscrito(Preinscritos preinscritos){
    	this.usuarioPreinscripcion = preinscritos;
    }
    
    public void eliminarUsuariosPreinscritos(){
    	preinscritosFacade.remove(usuarioPreinscripcion);
    	FacesMessage msg = new FacesMessage("Felicitaciones!", "La usuario fue eliminado exitósamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    	dataListUsuariosPreinscritos.remove(usuarioPreinscripcion);
        usuarioPreinscripcion = null;
    }
}
