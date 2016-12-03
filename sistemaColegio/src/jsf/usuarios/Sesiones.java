/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.usuarios;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import clasesAyuda.HttpSessionCollector;
import ejb.EstadoevaluacioninstitucionalFacade;
import ejb.EstudiantesFacade;
import ejb.ProfesoresFacade;
import ejb.RegistromatriculasFacade;
import ejb.RelacionusuariosrolesFacade;
import ejb.TareasFacade;
import ejb.UsuariosFacade;
import entities.Registromatriculas;
import entities.Usuarios;

/**
 *
 * @author juannoguera
 */
@ManagedBean(name = "sesiones")
@SessionScoped
public class Sesiones implements Serializable {

    @EJB
    private TareasFacade tareasFacade;
    @EJB
    private UsuariosFacade usuariosFacade;
    @EJB
    private ProfesoresFacade profesoresFacade;
    @EJB
    private EstudiantesFacade estudiantesFacade;
    @EJB
    private RegistromatriculasFacade registromatriculasFacade;
    @EJB
    private EstadoevaluacioninstitucionalFacade estadoevaluacioninstitucionalFacade;
    @EJB
    private RelacionusuariosrolesFacade relacionusuariosrolesFacade;
    
    
    private String login;
    private String password;
    private boolean sesion = false;
    private Usuarios usuarios;
    private Registromatriculas registromatriculas;
    private int TipoUsuario;
    private List<Object[]> dataListPermisosModulos;
    private List<Object[]> dataListPermisos;
    private boolean estadoEvalucionInsttitucional = true;
    private boolean administrador = false;

    /**
     * Creates a new instance of Sesiones
     */
    public Sesiones() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String validarSubmit() throws IOException {
        if (login != null && password != null && !usuariosFacade.findByLike("SELECT U FROM Usuarios U WHERE U.nombredeusuario = '" + login + "' AND U.password = '" + password + "'").isEmpty()) {
            sesion = true;
            List<Usuarios> tmp = usuariosFacade.findByLike("SELECT U FROM Usuarios U WHERE U.nombredeusuario = '" + login + "' AND U.password = '" + password + "' ");
            if(tmp.isEmpty()){
            	FacesMessage fm = new FacesMessage("El password y el login no coinciden");
                FacesContext.getCurrentInstance().addMessage(null, fm);
                password = "";
                login = "";
                return "index";
            }
            
            if(!tmp.get(0).isEstado_activo()){
            	FacesMessage fm = new FacesMessage("El usuario se encuentra bloqueado");
                FacesContext.getCurrentInstance().addMessage(null, fm);
                password = "";
                login = "";
                return "index";
            }
            
            String queryPermisosModulos = "select distinct m.modulo, m.descripcion from modulos m "
            		+ "join interfaz i on m.modulo = i.modulo "
            		+ "join relacionrolesinterfaz r on r.interfaz = i.idinterfaz "
            		+ "join relacionrolesinterfaz rui on rui.interfaz = i.idinterfaz "
            		+ "join relacionusuariosroles rur on rur.idroles = rui.roles "
            		+ "where rur.idusuarios = " + tmp.get(0).getIdusuarios();
            
            String queryPermisos = "select distinct i.descripcion, i.ruta, i.modulo from modulos m "
            		+ "join interfaz i on m.modulo = i.modulo "
            		+ "join relacionrolesinterfaz r on r.interfaz = i.idinterfaz "
            		+ "join relacionusuariosroles rur on rur.idroles = r.roles "
            		+ "where rur.idusuarios = " + tmp.get(0).getIdusuarios();
            
            dataListPermisosModulos = usuariosFacade.findByNative(queryPermisosModulos);
            
            dataListPermisos = usuariosFacade.findByNative(queryPermisos);
            
            usuarios = usuariosFacade.findByLike("SELECT U FROM Usuarios U WHERE U.nombredeusuario = '" + login + "' AND U.password = '" + password + "' "
            		+ "AND U.estado_activo = 'true'").get(0);
            
            //Se valida que el usuario sea administrador
            if (!profesoresFacade.findByLike("SELECT P FROM Profesores P WHERE P.usuarios.idusuarios = " + usuarios.getIdusuarios())
                            .isEmpty()) {
                TipoUsuario = 1;
            }else 
            	//Se valida que el usuario sea estudiante
            	if(!profesoresFacade.findByLike("SELECT P FROM Estudiantes P WHERE P.usuarios.idusuarios = " + usuarios.getIdusuarios())
                            .isEmpty()){
                registromatriculas = registromatriculasFacade.
                        findByLike("SELECT R FROM Registromatriculas R WHERE R.fecharetiro is null "
                        		+ "AND R.estudiantes.usuarios.idusuarios = " + usuarios.getIdusuarios()).get(0);
                TipoUsuario = 2;
                
                //Buscamos si todavía le falta algo de la encuesta
                if(!profesoresFacade.findByLikeAll("SELECT DISTINCT R.profesores FROM Relacionprofesoresasignaturaperiodo R "
						+ "WHERE R.cursos.idcursos = " 
						+ registromatriculas.getCursos().getIdcursos() + " " 
						+ "AND R.profesores.idprofesores not in "
						+ "(SELECT E.profesor.idprofesores  FROM Estadoevaluacioninstitucional E "
						+ "WHERE E.profesor.idprofesores = R.profesores.idprofesores AND E.registromatriculas.idregistromatriculas = "+ 
						registromatriculas.getIdregistromatriculas()+ ") ").isEmpty()){
                	//Colocamos la variable en falso para decir que todavía le falta acabar la evaluacion
                	estadoEvalucionInsttitucional = false;
                }
                
                if(estadoevaluacioninstitucionalFacade.findByLike("SELECT E FROM Estadoevaluacioninstitucional E WHERE E.profesor is null "
						+ " AND E.registromatriculas.idregistromatriculas = "+ registromatriculas.getIdregistromatriculas()).isEmpty()){
                	estadoEvalucionInsttitucional = false;
             	}
            }else{
                TipoUsuario = 0;
            }
            
            if(!relacionusuariosrolesFacade.findByLike("SELECT R FROM Relacionusuariosroles R WHERE R.usuarios.idusuarios = " + usuarios.getIdusuarios()
            + " AND R.roles.idroles = 1").isEmpty()){
            	administrador = true;
            }
            
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            session.setAttribute("USUARIO", usuarios);
            if (usuarios.getSessionid() != null
                    && HttpSessionCollector.find(usuarios.getSessionid()) != null) {
                HttpSessionCollector.find(usuarios.getSessionid()).invalidate();
            }
            //Colocamos el id del usuario
            usuarios.setSessionid(session.getId());
            //Guardamos el usuario en la base de datos
            usuariosFacade.edit(usuarios);
            
            if(!estadoEvalucionInsttitucional){
            	FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + 
            			"/faces/interfaces/usuarios/estudiantes/evaluacionInstitucional.xhtml");
            }
            
            return "miUsuario";
        } else {
            FacesMessage fm = new FacesMessage("El password y el login no coinciden");
            FacesContext.getCurrentInstance().addMessage(null, fm);
            password = "";
            login = "";
        }
        return "index";
    }

    public boolean verificar() throws IOException {

//        for (int i = 0; i < 1000; i++) {
//            URL oracle = new URL("http://190.147.199.240:45725/sistemaColegio/faces/interfaces/notas/administracionNotas/notasAcademicas.xhtml");
//            URLConnection yc = oracle.openConnection();
//            
//            
//            
//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    yc.getInputStream()));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                System.out.println(inputLine);
//            }
//            in.close();
//        }
        if (sesion) {
            //Bloquea la respuesta normal de jsf
            FacesContext.getCurrentInstance().responseComplete();;
            //Redirecciona la p��gina
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/interfaces/usuarios/miUsuario/miUsuario.xhtml");
        }
        return true;
    }

    public boolean isSesion() {
        return sesion;
    }

    public void setSesion(boolean sesion) {
        this.sesion = sesion;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public void cerrarSesion() throws IOException {
        sesion = false;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        //Redirecciona la p��gina
        //request.contextPath
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/faces/index.xhtml");

    }

    //M��todo para saber si el men�� est�� activo o no
    public boolean menuActivo(int tipo) {
        //Revisamos si no hay nadie logueado
        if (usuarios == null) {
            return false;
        }
        return false;
    }

    //###M��todo para obtener las im��genes
    public StreamedContent getImage() throws IOException {
        byte[] byteArr = tareasFacade.find(new Long(77)).getArchivo();
        InputStream input = new ByteArrayInputStream(byteArr);
        return new DefaultStreamedContent(input);
    }

    
    //metodo para saber el tipo de usuario que se esta logueando
    public int getTipoUsuario() {
        return TipoUsuario;
    }

    public Registromatriculas getRegistromatriculas() {
        return registromatriculas;
    }

    
    //###PROPIEDADES DE LOS PERMISOS
	public List<Object[]> getDataListPermisosModulos() {
		return dataListPermisosModulos;
	}

	public void setDataListPermisosModulos(List<Object[]> dataListPermisosModulos) {
		this.dataListPermisosModulos = dataListPermisosModulos;
	}

	public List<Object[]> getDataListPermisos() {
		return dataListPermisos;
	}

	public void setDataListPermisos(List<Object[]> dataListPermisos) {
		this.dataListPermisos = dataListPermisos;
	}
	

    //Opción para devolver el usuario logueado
    public static Usuarios getUsuarioLogueado() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        return (Usuarios) session.getAttribute("USUARIO");
    }
    
    //Propiedades del estado de la evaluacion institucional
	public boolean isEstadoEvalucionInsttitucional() {
		return estadoEvalucionInsttitucional;
	}

	public void setEstadoEvalucionInsttitucional(boolean estadoEvalucionInsttitucional) {
		this.estadoEvalucionInsttitucional = estadoEvalucionInsttitucional;
	}
	
	//Metodo para saber si es administrador
	public boolean isAdministrador(){
		if(usuarios == null){
			return false;
		}
		
		return administrador;
	}

}
