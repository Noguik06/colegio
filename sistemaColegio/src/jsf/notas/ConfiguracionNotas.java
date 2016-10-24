/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.notas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;

import net.sf.jasperreports.components.barbecue.BarcodeProviders.ShipmentIdentificationNumberProvider;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

import com.sun.faces.facelets.component.UIRepeat;

import ejb.AnosacademicosFacade;
import ejb.CursosFacade;
import ejb.DimensionesFacade;
import ejb.LogrosFacade;
import ejb.NotascalificablesFacade;
import ejb.PeriodosFacade;
import ejb.RegistromatriculasFacade;
import ejb.RelacionasignaturaperiodosFacade;
import ejb.RelaciondimensionesasignaturasanoFacade;
import ejb.RelacionlogrosdimensionesFacade;
import ejb.RelacionlogrosnotasdimensionFacade;
import ejb.RelacionnotasdimensionFacade;
import ejb.RelacionnotaslogrosdimensionboletinFacade;
import ejb.RelacionprofesoresasignaturaperiodoFacade;
import entities.Anosacademicos;
import entities.Cursos;
import entities.Dimensiones;
import entities.Estudiantes;
import entities.Logros;
import entities.Notascalificables;
import entities.Periodos;
import entities.Registromatriculas;
import entities.Relacionasignaturaperiodos;
import entities.Relaciondimensionesasignaturasano;
import entities.Relacionlogrosdimensiones;
import entities.Relacionlogrosnotasdimension;
import entities.Relacionnotasdimension;
import entities.Relacionnotaslogrosdimensionboletin;
import entities.Usuarios;

/**
 *
 * @author juannoguera
 */
@ManagedBean
@ViewScoped
public class ConfiguracionNotas implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
    private CursosFacade cursosFacade;
    @EJB
    private RelacionasignaturaperiodosFacade relacionasignaturaperiodosFacade;
    @EJB
    private DimensionesFacade dimensionesFacade;
    @EJB
    private RelaciondimensionesasignaturasanoFacade relaciondimensionesasignaturasanoFacade;
    @EJB
    private PeriodosFacade periodosFacade;
    @EJB
    private AnosacademicosFacade anosacademicosFacade;
    @EJB
    private LogrosFacade logrosFacade;
    @EJB
    private RelacionlogrosdimensionesFacade relacionlogrosdimensionesFacade;
    @EJB
    private RelacionnotasdimensionFacade relacionnotasdimensionFacade;
    @EJB
    private RelacionprofesoresasignaturaperiodoFacade relacionprofesoresasignaturaperiodoFacade;
    @EJB
    private RelacionnotaslogrosdimensionboletinFacade relacionnotaslogrosdimensionboletinFacade;
    @EJB
    private RegistromatriculasFacade registromatriculasFacade;
    @EJB
    private NotascalificablesFacade notascalificablesFacade;
    @EJB
    private RelacionlogrosnotasdimensionFacade relacionlogrosnotasdimensionFacade;
    //##ANOSACADEMICOS
    //Anoacademico actual
    private Anosacademicos anosacademicosActual;
    //###CURSOS
    //Lista de los cursos
    private List<Cursos> dataListCursos;
    //Curso seleccionado
    private Cursos cursoSeleccionado;
    //Lista de las asignaturasPorCurso
    private List<Relacionasignaturaperiodos> dataListRelacionasignaturaperiodos;
    //Asignatura escogida
    private Relacionasignaturaperiodos relacionasignaturaperiodosAsignado;
    //##DIMENSIONES
    //Lista de las dimensiones
    private List<Dimensiones> datalistDimensiones;
    //Dimension seleccionada
    private Relaciondimensionesasignaturasano relaciondimensionesasignaturasanoAsignada;
    //Dimension editar
    private Dimensiones dimensionEditar;
    //###PERIODOS
    //Lista de los periodos
    private List<Periodos> datalistPeriodos;
    //Periodo Seleccionado
    private Periodos periodoSeleccionado;
    //Periodo editar
    private Periodos periodosEditar;
    ///###LOGROS
    //Lista de los logros
    private List<Logros> datalistLogros;
    //Propieades de los otros logros
 	private List<Relacionlogrosdimensiones> otrosLogros;
    //Logros Seleccionado
    private Relacionlogrosdimensiones relacionLogrosDimensionesSeleccionado;
    //Logro a editar
    private Logros logrosEditar;
    //##NOTAS
    //Lista de las notas por Logro
    private List<Relacionnotasdimension> dataListRelacionnotasdimension;
    //Notas a Editar
    private Relacionnotasdimension notasEditar;
//    //Variable de notas seleccionado
    private Relacionnotasdimension relacionnotasdimensionAsignada;
    //##ACTIVIDADES
    //Lista de las notas que cada profesor escoge por categoria de notas
    private List<Relacionnotaslogrosdimensionboletin> dataListdataListRelacionnotaslogrosdimensionboletin;
    //Nota Calificable seleccionable
    private Relacionnotaslogrosdimensionboletin relacionnotaslogrosdimensionboletinSeleccionada;
    //Nota Calificable editar
    private Relacionnotaslogrosdimensionboletin relacionnotaslogrosdimensionboletinEditar;
    //###ESTUDIANTES
    private List<Registromatriculas> dataListRegistroMatricula;
    //Metodo utilizado para darle commit y begin a la transaccion con el fin de que no se queden transacciones por la mitad
    @Resource
    private UserTransaction userTransaction;

    /**
     * Creates a new instance of ConfiguracionNotas
     */
    public ConfiguracionNotas() {
    }

    public Anosacademicos getCurrentYear() {
        if (anosacademicosActual == null) {
            anosacademicosActual = anosacademicosFacade.findByLikeAll("SELECT A FROM Anosacademicos A WHERE A.estadoactivo = true").get(0);
        }

        return anosacademicosActual;
    }

    //###PROPIEDADES DE LA LISTA DE CURSOS
    public List<Cursos> getDataListCursos() {
        if (dataListCursos == null || dataListCursos.isEmpty()) {
            dataListCursos = cursosFacade.findByLikeAll("SELECT C FROM Cursos C WHERE C.anosacademicos.estadoactivo = 'true' ORDER BY C.grados.numero ");
        }
        return dataListCursos;
    }

    public void setDataListCursos(List<Cursos> dataListCursos) {
        this.dataListCursos = dataListCursos;
    }

    //Metodo para saber cuál es el curso seleccionado
    public boolean cursoSeleccionado(Cursos cursos) {
        if (cursoSeleccionado != null && cursos.getIdcursos() == cursoSeleccionado.getIdcursos()) {
            return true;
        }
        return false;
    }

    //Metodo para colocar el grado
    public void escogerCurso(Cursos cursos) {
        this.cursoSeleccionado = cursos;
        dataListRelacionasignaturaperiodos = relacionasignaturaperiodosFacade.findByLikeAll("SELECT RPA.relacionasignaturaperiodos FROM Relacionprofesoresasignaturaperiodo RPA WHERE RPA.cursos.anosacademicos.estadoactivo = 'true' AND RPA.cursos.grados.idgrados = " + cursos.getGrados().getIdgrados() + " ORDER BY RPA.relacionasignaturaperiodos.asignaturas.nombre ");

//        relacionnotasrelacionlogrosdimensionSeleccionada = null;
        relacionLogrosDimensionesSeleccionado = null;
        dataListdataListRelacionnotaslogrosdimensionboletin = null;
        dataListRelacionnotasdimension = null;
        relacionnotasdimensionAsignada = null;
        relacionasignaturaperiodosAsignado = null;
        periodoSeleccionado = null;
        relaciondimensionesasignaturasanoAsignada = null;
        datalistDimensiones = null;
        datalistLogros = null;
        datalistPeriodos = null;
    }

    //###ASIGNATURAS
    //PROPIEDADES DE LAS ASIGNATURAS
    public List<Relacionasignaturaperiodos> getDataListRelacionasignaturaperiodos() {
        return dataListRelacionasignaturaperiodos;
    }

    public void setDataListRelacionasignaturaperiodos(List<Relacionasignaturaperiodos> dataListRelacionasignaturaperiodos) {
        this.dataListRelacionasignaturaperiodos = dataListRelacionasignaturaperiodos;
    }

    //Escoger la asignatura
    public void escogerAsigantura(Relacionasignaturaperiodos asignatura) {
        datalistDimensiones = dimensionesFacade.findByLikeAll("SELECT D FROM Dimensiones D ORDER BY D.nombre");
        this.relacionasignaturaperiodosAsignado = asignatura;

        periodoSeleccionado = null;
        relaciondimensionesasignaturasanoAsignada = null;
//        relacionnotasrelacionlogrosdimensionSeleccionada = null;
        dataListRelacionnotasdimension = null;
        relacionnotasdimensionAsignada = null;

        dataListdataListRelacionnotaslogrosdimensionboletin = null;
        datalistLogros = null;
        datalistPeriodos = null;
    }

    //
    public boolean asignaturaSeleccionada(Relacionasignaturaperiodos asignatura) {
        if (relacionasignaturaperiodosAsignado != null && this.relacionasignaturaperiodosAsignado.getIdrelacionasignaturaperiodos() == asignatura.getIdrelacionasignaturaperiodos()) {
            return true;
        }
        return false;
    }

    //####DIMENSIONES
    //PROPIEDADES DE LA LISTA DE DIMENSIONES
    public List<Dimensiones> getDatalistDimensiones() {
        return datalistDimensiones;
    }

    public void setDatalistDimensiones(List<Dimensiones> datalistDimensiones) {
        this.datalistDimensiones = datalistDimensiones;
    }

    //Propiedades del valor del checkbox
    public void setValorDimensionAsignatura(boolean valor) {

        if (relacionasignaturaperiodosAsignado != null) {

            FacesContext facesContext = FacesContext.getCurrentInstance();
            UIViewRoot root = facesContext.getViewRoot();
            HtmlDataTable htd = (HtmlDataTable) root.findComponent("formPrincipal").findComponent("tablaDimensiones");

            Dimensiones dimensionesTmp = (Dimensiones) htd.getRowData();

            if (relaciondimensionesasignaturasanoFacade.findByLikeAll("SELECT RDAA FROM Relaciondimensionesasignaturasano RDAA WHERE RDAA.dimensiones.iddimensiones = " + dimensionesTmp.getIddimensiones() + " AND RDAA.relacionasignaturasperiodos.idrelacionasignaturaperiodos = " + relacionasignaturaperiodosAsignado.getIdrelacionasignaturaperiodos() + " AND + RDAA.cursos.idcursos = " + cursoSeleccionado.getIdcursos()).isEmpty()) {
                //Si esta vacio entonces creamos uno nuevo
                Relaciondimensionesasignaturasano tmp = new Relaciondimensionesasignaturasano(new Long(0));
                tmp.setDimensiones(dimensionesTmp);
                tmp.setCursos(cursoSeleccionado);
                tmp.setRelacionasignaturasperiodos(relacionasignaturaperiodosAsignado);
                relaciondimensionesasignaturasanoFacade.create(tmp);
            } else {
                //Si si esta vacio entonces lo borramos
                relaciondimensionesasignaturasanoFacade.remove(relaciondimensionesasignaturasanoFacade.findByLikeAll("SELECT RDAA FROM Relaciondimensionesasignaturasano RDAA WHERE RDAA.dimensiones.iddimensiones = " + dimensionesTmp.getIddimensiones() + " AND RDAA.relacionasignaturasperiodos.idrelacionasignaturaperiodos = " + relacionasignaturaperiodosAsignado.getIdrelacionasignaturaperiodos() + " AND + RDAA.cursos.idcursos = " + cursoSeleccionado.getIdcursos()).get(0));
            }
        }
    }

    public boolean getValorDimensionAsignatura() {

        if (relacionasignaturaperiodosAsignado == null) {
            return false;
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        HtmlDataTable htd = (HtmlDataTable) root.findComponent("formPrincipal").findComponent("tablaDimensiones");

        Dimensiones dimensionesTmp = (Dimensiones) htd.getRowData();

        if (relaciondimensionesasignaturasanoFacade.findByLikeAll("SELECT RDAA FROM Relaciondimensionesasignaturasano RDAA WHERE RDAA.dimensiones.iddimensiones = " + dimensionesTmp.getIddimensiones() + " AND RDAA.relacionasignaturasperiodos.idrelacionasignaturaperiodos = " + relacionasignaturaperiodosAsignado.getIdrelacionasignaturaperiodos() + " AND + RDAA.cursos.idcursos = " + cursoSeleccionado.getIdcursos()).isEmpty()) {
            return false;
        }

        return true;
    }

    //Metodo para escoger las dimensiones
    public void seleccionarDimension(Dimensiones dimensionSeleccionada) {
        if (relacionasignaturaperiodosAsignado != null) {
            //Damos el valor de la dimension seleccionada
            this.relaciondimensionesasignaturasanoAsignada = relaciondimensionesasignaturasanoFacade.findByLikeAll("SELECT RDAA FROM Relaciondimensionesasignaturasano RDAA WHERE RDAA.dimensiones.iddimensiones = " + dimensionSeleccionada.getIddimensiones() + " AND RDAA.relacionasignaturasperiodos.idrelacionasignaturaperiodos = " + relacionasignaturaperiodosAsignado.getIdrelacionasignaturaperiodos() + " AND RDAA.cursos.idcursos = " + cursoSeleccionado.getIdcursos()).get(0);

            datalistPeriodos = periodosFacade.findByLikeAll("SELECT P FROM Periodos P WHERE P.anoacademicos.idanosacademicos = " + getCurrentYear().getIdanosacademicos());

            datalistLogros = null;



            relacionLogrosDimensionesSeleccionado = null;
            relacionnotasdimensionAsignada = null;

            dataListRelacionnotasdimension = null;

            periodoSeleccionado = null;
            //Reiniciar las actividades
            dataListdataListRelacionnotaslogrosdimensionboletin = null;
//            relacionnotasrelacionlogrosdimensionSeleccionada = null;

        }
    }

    //Metodo para eliminar las dimensiones
    public void eliminarDimensiones(Dimensiones dimensiones) {
        //Eliminar las dimensiones
        dimensionesFacade.remove(dimensiones);
        datalistDimensiones.remove(dimensiones);

//        datalistDimensiones = null;
    }

    //Metodo para editar las dimensiones
    public void seleccionarEditarDimension(Dimensiones dimensiones) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        HtmlOutputText titulo = (HtmlOutputText) root.findComponent("dimensiones").findComponent("tituloPopUp");
        HtmlInputText nombreDimensionNueva = (HtmlInputText) root.findComponent("dimensiones").findComponent("nombreDimension");
        if (dimensiones == null) {
            titulo.setValue("Agregar Nueva Habilidad");
            nombreDimensionNueva.setValue("");
            this.dimensionEditar = null;
        } else {
            titulo.setValue("Editar Habilidad");
            this.dimensionEditar = dimensiones;
            nombreDimensionNueva.setValue(dimensiones.getNombre());
        }
    }

    //Metodo para agregar una nueva dimension
    public void agregarDimension() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        HtmlInputText nombreDimensionNueva = (HtmlInputText) root.findComponent("dimensiones").findComponent("nombreDimension");
//        datalistDimensiones = null;
//
        if (nombreDimensionNueva == null || nombreDimensionNueva.getValue() == null || nombreDimensionNueva.getValue().toString().trim().length() == 0) {
            throw new ValidatorException(new FacesMessage("El nombre de la dimension no puede estar vacio"));
        }

        if (dimensionEditar != null) {
            dimensionEditar.setNombre(nombreDimensionNueva.getValue().toString());
            dimensionesFacade.edit(dimensionEditar);
        } else {
            Dimensiones dimensionNueva = new Dimensiones(new Long(0));
            dimensionNueva.setNombre(nombreDimensionNueva.getValue().toString());
            dimensionesFacade.create(dimensionNueva);
            datalistDimensiones.add(dimensionNueva);
        }
    }

    //Metodo para hallar los porcentajes de las dimensiones en cada asginatura
    //Propiedades de los porcentajes
    public short getValorPorcentajeAsignatura() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIData tablaDimensiones = (UIData) root.findComponent("formPrincipal").findComponent("tablaDimensiones");
        Dimensiones dimensionValor = (Dimensiones) tablaDimensiones.getRowData();
        List<Relaciondimensionesasignaturasano> tmp = relaciondimensionesasignaturasanoFacade.findByLikeAll("SELECT R FROM Relaciondimensionesasignaturasano R WHERE R.relacionasignaturasperiodos.idrelacionasignaturaperiodos = " + relacionasignaturaperiodosAsignado.getIdrelacionasignaturaperiodos() + " AND R.dimensiones.iddimensiones = " + dimensionValor.getIddimensiones() + " AND R.cursos.idcursos = " + cursoSeleccionado.getIdcursos());



        if (tmp == null || tmp.isEmpty()) {
            return 0;
        }

        if (tmp.get(0) == null || tmp.get(0).getPorcentaje() == null) {
            return 0;
        }

        return tmp.get(0).getPorcentaje();
    }

    public void setValorPorcentajeAsignatura(short porcentajeAsigntaura) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIData tablaDimensiones = (UIData) root.findComponent("formPrincipal").findComponent("tablaDimensiones");
        Dimensiones dimensionValor = (Dimensiones) tablaDimensiones.getRowData();
        List<Relaciondimensionesasignaturasano> tmp = relaciondimensionesasignaturasanoFacade.findByLikeAll("SELECT R FROM Relaciondimensionesasignaturasano R WHERE R.relacionasignaturasperiodos.idrelacionasignaturaperiodos = " + relacionasignaturaperiodosAsignado.getIdrelacionasignaturaperiodos() + " AND R.dimensiones.iddimensiones = " + dimensionValor.getIddimensiones() + " AND R.cursos.idcursos = " + cursoSeleccionado.getIdcursos());

        if (tmp != null && !tmp.isEmpty()) {
            tmp.get(0).setPorcentaje(porcentajeAsigntaura);
            relaciondimensionesasignaturasanoFacade.edit(tmp.get(0));
        }
    }

    //Metodo para saber cu������l es la dimension seleccionado
    public boolean dimensionSeleccionada(Dimensiones dimensiones) {
        if (relaciondimensionesasignaturasanoAsignada != null && relaciondimensionesasignaturasanoAsignada.getDimensiones().getIddimensiones() == dimensiones.getIddimensiones()) {
            return true;
        }
        return false;
    }

    //###PERIODOS
    //Metodo para editar los periodos
    public void seleccionarEditarPeriodo(Periodos periodos){
    	 FacesContext facesContext = FacesContext.getCurrentInstance();
         UIViewRoot root = facesContext.getViewRoot();
         HtmlOutputText titulo = (HtmlOutputText) root.findComponent("periodos").findComponent("tituloPopUp");
         HtmlInputText nombrePeriodoNuevo = (HtmlInputText) root.findComponent("periodos").findComponent("nombrePeriodos");
         if (periodos == null) {
             titulo.setValue("Agregar Nuevo Logro");
             nombrePeriodoNuevo.setValue("");
             this.dimensionEditar = null;
         } else {
             titulo.setValue("Editar Logro");
             this.periodosEditar = periodos;
             nombrePeriodoNuevo.setValue(periodos.getNombre());
         }
    }
    
    //Metodo para agregar un periodo
    public void agregarPeriodos(){
    	 FacesContext facesContext = FacesContext.getCurrentInstance();
         UIViewRoot root = facesContext.getViewRoot();
         HtmlInputText nombrePeriodoNuevo = (HtmlInputText) root.findComponent("periodos").findComponent("nombrePeriodos");
         
         if (nombrePeriodoNuevo == null || nombrePeriodoNuevo.getValue() == null || nombrePeriodoNuevo.getValue().toString().trim().length() == 0) {
             throw new ValidatorException(new FacesMessage("El nombre del periodo no puede estar vacio"));
         }

         if (dimensionEditar != null) {
             periodosEditar.setNombre(nombrePeriodoNuevo.getValue().toString());
             dimensionesFacade.edit(dimensionEditar);
         } else {
        	 Anosacademicos anosacademicosActual = anosacademicosFacade.findByLikeAll("SELECT A FROM Anosacademicos A WHERE A.estadoactivo = 'true'").get(0);
             Periodos periodosNuevos = new Periodos(new Long(0));
             periodosNuevos.setAnoacademicos(anosacademicosActual);
             periodosNuevos.setNombre(nombrePeriodoNuevo.getValue().toString());
             periodosNuevos.setTipo(0);
             periodosFacade.create(periodosNuevos);
             datalistPeriodos.add(periodosNuevos);
         }
    }
    
    //PROPIEDADES DE LA LISTA DE LOS PERIODOS
    public List<Periodos> getDatalistPeriodos() {
//        if (datalistPeriodos == null) {
//            //Llenamos la lista de los periodos
//            datalistPeriodos = periodosFacade.findByLikeAll("SELECT P FROM Periodos P WHERE P.anoacademicos.idanosacademicos = " + getCurrentYear().getIdanosacademicos());
//        }
        return datalistPeriodos;
    }

    public void setDatalistPeriodos(List<Periodos> datalistPeriodos) {
        this.datalistPeriodos = datalistPeriodos;
    }

    //Metodo para seleccionar el Periodo
    public void seleccionarPeriodos(Periodos periodo) {
        this.periodoSeleccionado = periodo;
        datalistLogros = logrosFacade.findByLikeAll("SELECT R.logros FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = " + periodo.getIdperiodos() + " AND R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = " + relaciondimensionesasignaturasanoAsignada.getIdrelaciondimensionesasignaturasano());
        
        otrosLogros = relacionlogrosdimensionesFacade
				.findByLike("SELECT R FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = "
						+ periodo.getIdperiodos()
						+ " AND R.relaciondimensionesasignaturasano.cursos.grados.idgrados = "
						+ relaciondimensionesasignaturasanoAsignada.getCursos()
								.getGrados().getIdgrados()
						+ " AND R.relaciondimensionesasignaturasano.relacionasignaturasperiodos.idrelacionasignaturaperiodos = "
						+ relacionasignaturaperiodosAsignado
								.getIdrelacionasignaturaperiodos()
						+ " AND NOT EXISTS (SELECT RR FROM Relacionlogrosdimensiones RR WHERE RR.idrelacionlogrosdimensiones = R.idrelacionlogrosdimensiones AND RR.periodos.idperiodos = "
						+ periodo.getIdperiodos()
						+ " AND RR.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = "
						+ relaciondimensionesasignaturasanoAsignada
								.getIdrelaciondimensionesasignaturasano() + ")");


        dataListRelacionnotasdimension = relacionnotasdimensionFacade.findByLikeAll("SELECT R FROM Relacionnotasdimension R WHERE R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano= " + relaciondimensionesasignaturasanoAsignada.getIdrelaciondimensionesasignaturasano() + " AND R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos());

        relacionLogrosDimensionesSeleccionado = null;
        
        relacionnotasdimensionAsignada = null;
//        relacionLogrosDimensionesSeleccionado = null;
        //Reiniciar las actividades
        dataListdataListRelacionnotaslogrosdimensionboletin = null;
//        relacionnotasrelacionlogrosdimensionSeleccionada = null;

    }

    //Metodo para saber cu������l es el periodo seleccionado
    public boolean periodoSeleccionado(Periodos periodos) {
        if (periodoSeleccionado != null && periodoSeleccionado.getIdperiodos() == periodos.getIdperiodos()) {
            return true;
        }
        return false;
    }

    //###LOGROS
    //Propiedades de la lista de los logros
    public List<Logros> getDatalistLogros() {
        return datalistLogros;
    }

    public void setDatalistLogros(List<Logros> datalistLogros) {
        this.datalistLogros = datalistLogros;
    }

//    //Propiadades del checkbox del logro en el periodo
    public void setValorLogroPeriodo(boolean valorLogro) {
        if (periodoSeleccionado != null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            UIViewRoot root = facesContext.getViewRoot();
            HtmlDataTable htd = (HtmlDataTable) root.findComponent("formPrincipal").findComponent("tablaLogros");
            Logros logrosTmp = (Logros) htd.getRowData();

            if (relacionlogrosdimensionesFacade.findByLikeAll("SELECT RLD FROM Relacionlogrosdimensiones RLD WHERE RLD.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = " + relaciondimensionesasignaturasanoAsignada.getIdrelaciondimensionesasignaturasano() + " AND RLD.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos()
                    + " AND RLD.logros.idlogros = " + logrosTmp.getIdlogros()).isEmpty()) {
                Relacionlogrosdimensiones tmp = new Relacionlogrosdimensiones(new Long(0));
                tmp.setLogros(logrosTmp);
                tmp.setPeriodos(periodoSeleccionado);
                tmp.setRelaciondimensionesasignaturasano(relaciondimensionesasignaturasanoAsignada);
                relacionlogrosdimensionesFacade.create(tmp);
            } else {
                relacionlogrosdimensionesFacade.remove(relacionlogrosdimensionesFacade.findByLikeAll("SELECT RLD FROM Relacionlogrosdimensiones RLD WHERE RLD.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = " + relaciondimensionesasignaturasanoAsignada.getIdrelaciondimensionesasignaturasano() + " AND RLD.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos()
                        + " AND RLD.logros.idlogros = " + logrosTmp.getIdlogros()).get(0));
            }
        }
    }

    public boolean getValorLogroPeriodo() {
        if (periodoSeleccionado == null) {
            return false;
        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        HtmlDataTable htd = (HtmlDataTable) root.findComponent("formPrincipal").findComponent("tablaLogros");
        Logros logrosTmp = (Logros) htd.getRowData();

        if (relacionlogrosdimensionesFacade.findByLikeAll("SELECT RLD FROM Relacionlogrosdimensiones RLD WHERE RLD.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = " + relaciondimensionesasignaturasanoAsignada.getIdrelaciondimensionesasignaturasano() + " AND RLD.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos()
                + " AND RLD.logros.idlogros = " + logrosTmp.getIdlogros()).isEmpty()) {
            return false;
        }

        return true;
    }

    //Metodo para seleccionar un logro
    public void seleccionarLogro(Logros logro) {
        //Colocamos el logro seleccionado
        this.relacionLogrosDimensionesSeleccionado = relacionlogrosdimensionesFacade.findByLikeAll("SELECT RNGD FROM Relacionlogrosdimensiones RNGD WHERE RNGD.logros.idlogros = " + logro.getIdlogros()
                + " AND RNGD.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = " + relaciondimensionesasignaturasanoAsignada.getIdrelaciondimensionesasignaturasano() + " AND RNGD.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos()).get(0);


        dataListdataListRelacionnotaslogrosdimensionboletin = null;

//        relacionnotasrelacionlogrosdimensionSeleccionada = null;

//        System.out.print(dataListNotasRelacionLogrosDimension.size() + " olitas ");
//        datalistNotas = notasFacade.findByLikeAll("SELECT R.notas FROM Relacionnotasrelacionlogrosdimension R WHERE R.relacionlogrosdimensiones.idrelacionlogrosdimensiones = " + relacionLogrosDimensionesSeleccionado.getIdrelacionlogrosdimensiones());
//        //Llenamos la lista de las notas
//        datalistNotas = notasFacade.findAll();
    }

    //Metodo para agregar una nueva dimension
    public void agregarLogro() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        HtmlInputTextarea nombreLogroNuevo = (HtmlInputTextarea) root.findComponent("logros").findComponent("textoLogro");



        if (nombreLogroNuevo == null || nombreLogroNuevo.getValue() == null || nombreLogroNuevo.getValue().toString().trim().length() == 0) {
            throw new ValidatorException(new FacesMessage("El LOGRO no puede estar vacio"));
        }

        if (logrosEditar != null) {
            logrosEditar.setLogro(nombreLogroNuevo.getValue().toString());
            logrosFacade.edit(logrosEditar);
        } else {
            Logros logroNuevo = new Logros(new Long(0));
            logroNuevo.setLogro(nombreLogroNuevo.getValue().toString());
            logrosFacade.create(logroNuevo);

            Relacionlogrosdimensiones tmp = new Relacionlogrosdimensiones(new Long(0));
            tmp.setRelaciondimensionesasignaturasano(relaciondimensionesasignaturasanoAsignada);
            tmp.setPeriodos(periodoSeleccionado);
            tmp.setLogros(logroNuevo);
            relacionlogrosdimensionesFacade.create(tmp);
        }

        datalistLogros = logrosFacade.findByLikeAll("SELECT R.logros FROM Relacionlogrosdimensiones R WHERE R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos() + " AND R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = " + relaciondimensionesasignaturasanoAsignada.getIdrelaciondimensionesasignaturasano());
//        datalistLogros = null;
    }

    //Metodo para eliminar un logro
    public void eliminarLogro(Logros logros) {

        try {
            userTransaction.begin();
        } catch (Exception e) {
        }

        relacionlogrosdimensionesFacade.remove(relacionlogrosdimensionesFacade.findByLikeAll("SELECT RLD FROM Relacionlogrosdimensiones RLD WHERE RLD.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = " + relaciondimensionesasignaturasanoAsignada.getIdrelaciondimensionesasignaturasano() + " AND RLD.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos()
                + " AND RLD.logros.idlogros = " + logros.getIdlogros()).get(0));

//        try {
        logrosFacade.remove(logros);
//        } catch (Exception e) {
//            throw new ValidatorException(new FacesMessage("Este logro no se puede eliminar porque tiene notas que pertenecen a este logro"));
//        }

        datalistLogros.remove(logros);

        try {
            userTransaction.commit();
        } catch (Exception e) {
            throw new ValidatorException(new FacesMessage("Este logro no se puede eliminar porque tiene notas que pertenecen a este logro"));
        }

    }

    //Metodo para editar las dimensiones
    public void seleccionarEditarLogro(Logros logros) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        HtmlOutputText titulo = (HtmlOutputText) root.findComponent("logros").findComponent("tituloPopUp");
        HtmlInputTextarea nombreLogroNuevo = (HtmlInputTextarea) root.findComponent("logros").findComponent("textoLogro");
        if (logros == null) {
            titulo.setValue("Agregar Nuevo Logro");
            nombreLogroNuevo.setValue("");
            this.logrosEditar = null;
        } else {

            titulo.setValue("Editar Logro");
            this.logrosEditar = logros;
            nombreLogroNuevo.setValue(logros.getLogro());
        }
    }

    //Metodo para saber si el logro ha sido o no seleccinado
    public boolean logroSeleccionado(Logros logro) {
        if (relacionLogrosDimensionesSeleccionado != null && relacionLogrosDimensionesSeleccionado.getLogros().getIdlogros() == logro.getIdlogros()) {
            return true;
        }
        return false;
    }

//    //###NOTAS
    //Propiedades de las notas por logro
    public List<Relacionnotasdimension> getDataListNotasRelacionLogrosDimension() {
        return dataListRelacionnotasdimension;
    }

    public void setDataListNotasRelacionLogrosDimension(List<Relacionnotasdimension> dataListNotasRelacionLogrosDimension) {
        this.dataListRelacionnotasdimension = dataListNotasRelacionLogrosDimension;
    }

    //Metodo para agregar una nueva dimension
    public void agregarNotaGlobal() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        HtmlInputText nombreNotaGlobalNueva = (HtmlInputText) root.findComponent("notasGlobales").findComponent("nombreNotaGlobal");

        if (nombreNotaGlobalNueva == null || nombreNotaGlobalNueva.getValue() == null || nombreNotaGlobalNueva.getValue().toString().trim().length() == 0) {
            throw new ValidatorException(new FacesMessage("El nombre de la NOTA no puede quedar vacio"));
        }

        if (notasEditar != null) {
            notasEditar.setNombrenotas(nombreNotaGlobalNueva.getValue().toString());
            relacionnotasdimensionFacade.edit(notasEditar);
        } else {
            Relacionnotasdimension notaNueva = new Relacionnotasdimension(new Long(0));
            notaNueva.setNombrenotas(nombreNotaGlobalNueva.getValue().toString());
            notaNueva.setRelaciondimensionesasignaturasano(relaciondimensionesasignaturasanoAsignada);
            notaNueva.setPorcentaje(new Short("0"));
            notaNueva.setPeriodos(periodoSeleccionado);
            relacionnotasdimensionFacade.create(notaNueva);
            dataListRelacionnotasdimension.add(notaNueva);
        }
    }

    //Metodo para eliminar las notas
    public void eliminarNotas(Relacionnotasdimension notas) {
        try {
            userTransaction.begin();
        } catch (Exception e) {
        }
        //Eliminamos de la base de datos esta nota
        relacionnotasdimensionFacade.remove(notas);
        //Quitamos de la lista las notas
        dataListRelacionnotasdimension.remove(notas);

        try {
            userTransaction.commit();
        } catch (Exception e) {
            throw new ValidatorException(new FacesMessage("Esta nota no se puede eliminar porque hay actividades asociadas a esta"));
        }

    }

    //Metodo para seleccionar Editar las notas
    public void seleccionarEditarNotas(Relacionnotasdimension notas) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        HtmlOutputText titulo = (HtmlOutputText) root.findComponent("notasGlobales").findComponent("tituloPopUp");
        HtmlInputText nombreNotaNueva = (HtmlInputText) root.findComponent("notasGlobales").findComponent("nombreNotaGlobal");
        if (notas == null) {
            titulo.setValue("Agregar Nueva Nota");
            nombreNotaNueva.setValue("");
            this.notasEditar = null;
        } else {
            titulo.setValue("Editar Nota");
            this.notasEditar = notas;
            nombreNotaNueva.setValue(notasEditar.getNombrenotas());
        }
    }
//    short valorPorcentajeNota;

    //+++Propiedades del valor en porcentaje de la nota en el logro
    public short getValorPorcentajeNota() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIData titulo = (UIData) root.findComponent("formPrincipal").findComponent("tablaNotas");
        Relacionnotasdimension notaTmp = (Relacionnotasdimension) titulo.getRowData();
        if (notaTmp.getPorcentaje() == null) {
            return 0;
        }
        return notaTmp.getPorcentaje();
    }

    public void setValorPorcentajeNota(short nota) {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIData titulo = (UIData) root.findComponent("formPrincipal").findComponent("tablaNotas");
        Relacionnotasdimension notaTmp = (Relacionnotasdimension) titulo.getRowData();

        //Si coincide colocamos este valor y lo editamos
        if (notaTmp != null) {
            notaTmp.setPorcentaje(nota);

            relacionnotasdimensionFacade.edit(notaTmp);
        }
    }

    //Metodo para seleccionar 
    public void seleccionarNotaGlobal(Relacionnotasdimension relacionnotasrelacionlogrosdimension) {
        this.relacionnotasdimensionAsignada = relacionnotasrelacionlogrosdimension;
        dataListdataListRelacionnotaslogrosdimensionboletin = null;

    }

    //Metodo para saber si es la nota est������ seleccionada
    public boolean notaSeleccionada(Relacionnotasdimension relacionnotasrelacionlogrosdimension) {
        if (relacionnotasdimensionAsignada != null && relacionnotasdimensionAsignada.getIdrelacionnotasdimesion() == relacionnotasrelacionlogrosdimension.getIdrelacionnotasdimesion()) {
            return true;
        }
        return false;
    }

    //Metodo para obtener la nota en la que estamos trabajando
    public Relacionnotasdimension getRelacionnotasdimension() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIData titulo = (UIData) root.findComponent("formPrincipal").findComponent("tablaNotas");
        Relacionnotasdimension notaTmp = (Relacionnotasdimension) titulo.getRowData();
        return notaTmp;
    }

    //Metodo para agregar o eliminar la relacion relacionnotaslgrosdimension
    public boolean getLogroNotaDimension() {
        if (relacionLogrosDimensionesSeleccionado == null) {
            return false;
        }
        List<Relacionlogrosnotasdimension> dataListTmp;

        dataListTmp = relacionlogrosnotasdimensionFacade.findByLikeAll("SELECT R FROM Relacionlogrosnotasdimension R WHERE R.relacionlogrosdimension.idrelacionlogrosdimensiones = " + relacionLogrosDimensionesSeleccionado.getIdrelacionlogrosdimensiones() + " AND R.relacionnotasdimension.idrelacionnotasdimesion = " + getRelacionnotasdimension().getIdrelacionnotasdimesion());
        if (!dataListTmp.isEmpty()) {
            return true;
        }

        return false;
    }

    public void setLogroNotaDimension(boolean estado) {
        if (relacionLogrosDimensionesSeleccionado != null) {
            if (estado) {
                Relacionlogrosnotasdimension relacionlogrosnotasdimensionNuevo = new Relacionlogrosnotasdimension(new Long(0));
                relacionlogrosnotasdimensionNuevo.setRelacionlogrosdimension(relacionLogrosDimensionesSeleccionado);
                relacionlogrosnotasdimensionNuevo.setRelacionnotasdimension(getRelacionnotasdimension());
                relacionlogrosnotasdimensionFacade.create(relacionlogrosnotasdimensionNuevo);
            } else {
                List<Relacionlogrosnotasdimension> dataListTmp;
                dataListTmp = relacionlogrosnotasdimensionFacade.findByLikeAll("SELECT R FROM Relacionlogrosnotasdimension R WHERE R.relacionlogrosdimension.idrelacionlogrosdimensiones = " + relacionLogrosDimensionesSeleccionado.getIdrelacionlogrosdimensiones() + " AND R.relacionnotasdimension.idrelacionnotasdimesion = " + getRelacionnotasdimension().getIdrelacionnotasdimesion());
                if (!dataListTmp.isEmpty()) {
                    relacionlogrosnotasdimensionFacade.remove(dataListTmp.get(0));
                }
            }
        }
    }

//
//    //##ESTUDIANTES NOTAS
    //Propiedades de la lista de dataListRelacionnotaslogrosdimensionboletin
    public List<Relacionnotaslogrosdimensionboletin> getDataListRelacionnotaslogrosdimensionboletin() {
        if (dataListdataListRelacionnotaslogrosdimensionboletin == null) {
            if (relacionnotasdimensionAsignada != null) {
                dataListdataListRelacionnotaslogrosdimensionboletin = relacionnotaslogrosdimensionboletinFacade.findByLikeAll("SELECT R FROM Relacionnotaslogrosdimensionboletin R WHERE R.relacionnotasdimension.idrelacionnotasdimesion = " + relacionnotasdimensionAsignada.getIdrelacionnotasdimesion() + " ORDER BY R.nombre");
            }
        }
        return dataListdataListRelacionnotaslogrosdimensionboletin;
    }

    //Metodo para seleccionar una nota calificable, ya sea para crearla o si ya est������ creada entonces para editarla
    public void seleccionarNotaCalificableEditar(Relacionnotaslogrosdimensionboletin relacionnotaslogrosdimensionboletinEditar) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        HtmlOutputText titulo = (HtmlOutputText) root.findComponent("formCrearNotasCalificables").findComponent("tituloPopUp");
        HtmlInputText nombreNotaNueva = (HtmlInputText) root.findComponent("formCrearNotasCalificables").findComponent("nombreNotaCalificar");
        if (relacionnotaslogrosdimensionboletinEditar == null) {
            titulo.setValue("Agregar Actividad");
            this.relacionnotaslogrosdimensionboletinEditar = null;
            nombreNotaNueva.setValue("");
        } else {
            titulo.setValue("Editar Actividad");

            this.relacionnotaslogrosdimensionboletinEditar = relacionnotaslogrosdimensionboletinEditar;
            nombreNotaNueva.setValue(relacionnotaslogrosdimensionboletinEditar.getNombre());
        }
    }

    //Metodo para agregar una nota nueva
    public void agregarNotaCalificar() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        HtmlInputText input = (HtmlInputText) root.findComponent("formCrearNotasCalificables").findComponent("nombreNotaCalificar");
//        System.out.print(input.getValue() + " nada");
//
        if (relacionnotaslogrosdimensionboletinEditar != null) {
            relacionnotaslogrosdimensionboletinEditar.setNombre(input.getValue().toString());
            relacionnotaslogrosdimensionboletinFacade.edit(relacionnotaslogrosdimensionboletinEditar);
        } else {
            //Creamos la Relacionnotaslogrosdimensionboletin
            Relacionnotaslogrosdimensionboletin tmp = new Relacionnotaslogrosdimensionboletin(new Long(0));
            tmp.setRelacionnotasdimension(relacionnotasdimensionAsignada);
            tmp.setNombre(input.getValue().toString());
            relacionnotaslogrosdimensionboletinFacade.create(tmp);
            dataListdataListRelacionnotaslogrosdimensionboletin = null;
        }
    }

    public void seleccionarNotaCalificable(Relacionnotaslogrosdimensionboletin relacionnotaslogrosdimensionboletin) {
        this.relacionnotaslogrosdimensionboletinSeleccionada = relacionnotaslogrosdimensionboletin;
        List<Anosacademicos> dataListAnosAcademicos = anosacademicosFacade.findByLikeAll("SELECT A FROM Anosacademicos A WHERE A.estadoactivo = true");
//        List<Registromatriculas> dataListRegistromatriculas = registromatriculasFacade.findByLikeAll("SELECT R FROM Registromatriculas R WHERE R.anosacademicos.idanosacademicos = " + dataListAnosAcademicos.get(0).getIdanosacademicos() + " and R.estudiantes.usuarios.idusuarios = " + usuarioEscogido.getIdusuarios());
        dataListRegistroMatricula = registromatriculasFacade.findByLikeAll("SELECT RM FROM Registromatriculas RM WHERE RM.anosacademicos.idanosacademicos = " + dataListAnosAcademicos.get(0).getIdanosacademicos() + " and RM.cursos.idcursos = " + cursoSeleccionado.getIdcursos() + " AND RM.fecharetiro is null ORDER BY RM.estudiantes.usuarios.apellidos");
    }

    //Metodo para eliminar una actividad
    public void eliminarNota(Relacionnotaslogrosdimensionboletin relacionnotasrelacionlogrosdimension) {
        try {
            userTransaction.begin();
        } catch (Exception e) {
            System.out.print(e.getStackTrace());
        }
        try {
            Notascalificables sd;
            notascalificablesFacade.metodo("DELETE FROM Notascalificables N WHERE N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin = " + relacionnotasrelacionlogrosdimension.getIdrelacionnotaslogrosdimensionboletin());
        } catch (Exception e) {
            System.out.print(e.getStackTrace());

        }
        try {
            relacionnotaslogrosdimensionboletinFacade.remove(relacionnotasrelacionlogrosdimension);
            dataListdataListRelacionnotaslogrosdimensionboletin.remove(relacionnotasrelacionlogrosdimension);
        } catch (Exception e) {
            System.out.print(e.getStackTrace());
        }
        try {
            userTransaction.commit();
        } catch (Exception e) {
        }
    }
//    

    //###USUARIOS DE LOS CURSOS
    //Lista de los estudiantes por curso
    public List<Registromatriculas> getDataListRegistroMatricula() {
//        if(dataListRegistroMatricula != null && !dataListRegistroMatricula.isEmpty()){
//            for(Registromatriculas reg:dataListRegistroMatricula){
//                System.out.print(registromatriculasFacade.findByNative("SELECT sum(nc.valor)/count(rnldb), rnd.idrelacionnotasdimesion from notascalificables nc join relacionnotaslogrosdimensionboletin  rnldb on rnldb.idrelacionnotaslogrosdimensionboletin =  nc.relacionnotaslogrosdimensionboletin join relacionnotasdimension rnd on rnd.idrelacionnotasdimesion = rnldb.relacionnotasdimension join relaciondimensionesasignaturasano rdaa on rdaa.idrelaciondimensionesasignaturasano = rnd.relaciondimensionesasignaturasano  where registromatriculas =  " + reg.getIdregistromatriculas() +" and rnd.periodos = 1 group by rnd.idrelacionnotasdimesion"));
//            }
//        }
        return dataListRegistroMatricula;
    }

    public void setDataListRegistroMatricula(List<Registromatriculas> dataListRegistroMatricula) {
        this.dataListRegistroMatricula = dataListRegistroMatricula;
    }

    public Short getNotaCalificable() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIData tablaEstudiantes = (UIData) root.findComponent("formRegistroMatricula").findComponent("tablaEstudiantes");

        Registromatriculas registromatriculas = (Registromatriculas) tablaEstudiantes.getRowData();

        List<Notascalificables> tmp = notascalificablesFacade.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = " + registromatriculas.getIdregistromatriculas() + " AND N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin = " + relacionnotaslogrosdimensionboletinSeleccionada.getIdrelacionnotaslogrosdimensionboletin());

        if (tmp != null && !tmp.isEmpty()) {
            return tmp.get(0).getValor();
        }
        return 0;

    }
    
	// Propieades de los otros logros

	public List<Relacionlogrosdimensiones> getOtrosLogros() {
		return otrosLogros;
	}
	
	public void setOtrosLogros(List<Relacionlogrosdimensiones> otrosLogros) {
		this.otrosLogros = otrosLogros;
	}
	
	
	///#####VISTA GLOBAL DE TODAS LAS NOTAS
	//Lista de los periodos
	private List<PeriodosVG> dataListPeriodosVG;
	
	private List<Registromatriculas> dataListRegistromatriculasVG; 
	
	//Escoger la asignatura
    public void escogerAsiganturaVG_1(Relacionasignaturaperiodos asignatura) {
    	//Obtenemos la lista de usuarios
    	dataListRegistromatriculasVG = registromatriculasFacade.findByLikeAll("SELECT R FROM Registromatriculas R WHERE R.estudiantes.usuarios.estado_activo = true AND R.cursos.idcursos = " + cursoSeleccionado.getIdcursos() + " AND R.anosacademicos.idanosacademicos = " + getCurrentYear().getIdanosacademicos()  + " ORDER BY R.estudiantes.usuarios.apellidos");

    	//Query para traer los periodos
    	List<Periodos> dataListPeriodos = periodosFacade.findByLikeAll("SELECT P FROM Periodos P WHERE P.anoacademicos.idanosacademicos = " + getCurrentYear().getIdanosacademicos() +
    			" AND P.tipo = 0 order by p.fechanotas");
    	//Query para ver la relacione entre las asignaturas del year y las dimensiones
    	String queryRelaciondimensionesasignaturasanosVG = "SELECT R FROM Relaciondimensionesasignaturasano R "
    			+ "WHERE R.relacionasignaturasperiodos.idrelacionasignaturaperiodos = " + asignatura.getIdrelacionasignaturaperiodos() + " "
    			+ "AND R.cursos.idcursos = " + cursoSeleccionado.getIdcursos();
    	
    	//Llenamos la lista de las dimensiones
    	List<Relaciondimensionesasignaturasano> dataLstRelaciondimensionesasignaturasanosVG; dataLstRelaciondimensionesasignaturasanosVG = relaciondimensionesasignaturasanoFacade.findByLikeAll(queryRelaciondimensionesasignaturasanosVG);
    	
    	dataListPeriodosVG = new ArrayList<PeriodosVG>();
    	//Recorre los periodos
    	for(Periodos pepe:dataListPeriodos){
    		//Agregar el periodo y la lista de dimensiones por periodo
    		PeriodosVG periodosVG = new PeriodosVG();
    		periodosVG.setPeriodos(pepe);
//    		//Empezamos a recorrer las dimensione para poder sacar las actividades por cada dimension
    		for(Relaciondimensionesasignaturasano r:dataLstRelaciondimensionesasignaturasanosVG){
    			//Sacamos los items de cada dimension asociada al periodo
    			String queryRelacionnotasdimension = "SELECT R FROM Relacionnotasdimension R "
    					+ "WHERE R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = " + r.getIdrelaciondimensionesasignaturasano() + " "
    					+ "AND R.periodos.idperiodos = " + pepe.getIdperiodos() + " ORDER BY R.nombrenotas"; 
    			List<Relacionnotasdimension> dataListRelacionnotasdimension = relacionnotasdimensionFacade.findByLikeAll(queryRelacionnotasdimension);
//    			Recorremos cada item y sacamos las actividades que tiene este item
    			for(Relacionnotasdimension rnd:dataListRelacionnotasdimension){
    				String queryRelacionnotaslogrosdimensionboletin = "SELECT R FROM Relacionnotaslogrosdimensionboletin R "
    						+ "WHERE R.relacionnotasdimension.idrelacionnotasdimesion = " + rnd.getIdrelacionnotasdimesion();
    				List<Relacionnotaslogrosdimensionboletin> dataListdataListRelacionnotaslogrosdimensionboletin = 
    						relacionnotaslogrosdimensionboletinFacade.findByLikeAll(queryRelacionnotaslogrosdimensionboletin);
    				//Recorremos cada actividad y le agregamos las notas por cada estudiantes
    				for(Relacionnotaslogrosdimensionboletin rndb:dataListdataListRelacionnotaslogrosdimensionboletin){
    					List<Notascalificables> dataLIstNotascalificables = new ArrayList<Notascalificables>();
    					String queryNotasCalificables =  
    							"select  u.idusuarios, "
    							+ "u.nombres,  "
    							+ "u.apellidos,   "
    							+ "e.idestudiantes,  "
    							+ "rm.idregistromatriculas,  "
    							+ "case when nc.idnotascalificables is null then 0 else nc.idnotascalificables  end, "
    							+ "case when nc.valor is null then 0 else nc.valor end from registromatriculas rm, " 
    							+ rndb.getIdrelacionnotaslogrosdimensionboletin() + " "
    							+ "join estudiantes e "
    							+ "join usuarios u "
    							+ "on e.usuarios =  u.idusuarios "
    							+ "on rm.estudiantes = e.idestudiantes "
    							+ "left join notascalificables nc  "
    							+ "on nc.registromatriculas = rm.idregistromatriculas "
    							+ "and nc.relacionnotaslogrosdimensionboletin = " + rndb.getIdrelacionnotaslogrosdimensionboletin() + " "
    							+ "where rm.anosacademicos = " + getCurrentYear().getIdanosacademicos() + " "
    							+ "and rm.cursos = " + cursoSeleccionado.getIdcursos() + " "
    							+ "order by u.apellidos ";
    					List<Object[]> dataListNotasCalificablesTMP = notascalificablesFacade.findByNative(queryNotasCalificables);
    					for(Object[] o:dataListNotasCalificablesTMP){
    						//Creamos el usuario
    						Usuarios u = new Usuarios(new Long(o[0].toString()));
    						u.setNombres(o[1].toString());
    						u.setApellidos(o[2].toString());
    						//Creamos el estudiante
    						Estudiantes e = new Estudiantes(new Long(o[3].toString()));
    						e.setUsuarios(u);
    						//Creamos el registromatricula
    						Registromatriculas regm = new Registromatriculas(new Long(o[4].toString()));
    						regm.setCursos(cursoSeleccionado);
    						regm.setEstudiantes(e);
    						regm.setAnosacademicos(getCurrentYear());
    						//Creamos la nota
    						Notascalificables nc = new Notascalificables(new Long(o[5].toString()));
    						nc.setRegistromatriculas(regm);
    						nc.setRelacionnotaslogrosdimensionboletin(rndb);
    						nc.setValor(new Short(o[6].toString()));
    						dataLIstNotascalificables.add(nc);
    					}
    					//Anadimos las notas calificables que le corresponden a esta actividad
    					rndb.setNotascalificablesList(dataLIstNotascalificables);
    				}
/*					Anadimos a cada Relacionnotasdimension (tiem) la lista de de las Relacionnotaslogrosdimensionboletin (actividades) que
    				estan asociadas a ella*/
    				rnd.setRelacionnotaslogrosdimensionboletinList(dataListdataListRelacionnotaslogrosdimensionboletin);
    			}
//				Anadimos a cada Relaciondimensionesasignaturasano las Relacionnotasdimension (item) que estan asociadas a ella 
    			r.setRelacionnotasdimensionList(dataListRelacionnotasdimension);
        	}
    		periodosVG.setDataListRelaciondimensionesasignaturasanosVG(dataLstRelaciondimensionesasignaturasanosVG);
    		dataListPeriodosVG.add(periodosVG);
    	}
    }
    
  
    


	//Esto es para las notas
    public List<PeriodosVG> getDataListPeriodosVG() {
		return dataListPeriodosVG;
	}

	public void setDataListPeriodosVG(List<PeriodosVG> dataListPeriodosVG) {
		this.dataListPeriodosVG = dataListPeriodosVG;
	}


	//Lista de los estudiantes
	public List<Registromatriculas> getDataListRegistromatriculasVG() {
		return dataListRegistromatriculasVG;
	}

	public void setDataListRegistromatriculasVG(
			List<Registromatriculas> dataListRegistromatriculasVG) {
		this.dataListRegistromatriculasVG = dataListRegistromatriculasVG;
	}
	
	//Metodo para guardar las notas
	public void guardarNota(AjaxBehaviorEvent event){
		Object valor = ((UIOutput)event.getSource()).getValue();
		DataTable dataTable = (DataTable) event.getComponent().getParent().getParent();
		Object[] object= (Object[]) dataTable.getRowData();
		
		if(valor== null || !(java.util.regex.Pattern.matches("\\d+", (CharSequence) valor)) ||  Short.parseShort(valor.toString()) < 0 || Short.parseShort(valor.toString()) > 100){
			if(Long.parseLong(object[5].toString()) == 0){
				((UIOutput)event.getSource()).setValue(0);
			}else{
				Notascalificables notaTMP = notascalificablesFacade.find(new Long(object[5].toString()));
				((UIOutput)event.getSource()).setValue(notaTMP.getValor());
			}
			FacesMessage message = new FacesMessage("Error", "La nota debe estar entre 0 y 100");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}else{
			if(Long.parseLong(object[5].toString()) == 0){
				Notascalificables notascalificables = new Notascalificables(new Long(0));
				notascalificables.setFecharegistro(new Date());
				notascalificables.setRegistromatriculas(new Registromatriculas(Long.parseLong(object[4].toString())));
				notascalificables.setValor(Short.parseShort(valor.toString()));
				notascalificables.setRelacionnotaslogrosdimensionboletin(new Relacionnotaslogrosdimensionboletin(new Long(object[7].toString())));
				notascalificablesFacade.create(notascalificables);
				
				String queryNotasCalificables =  
						"select  u.idusuarios, "
						+ "u.nombres,  "
						+ "u.apellidos,   "
						+ "e.idestudiantes,  "
						+ "rm.idregistromatriculas,  "
						+ "case when nc.idnotascalificables is null then 0 else nc.idnotascalificables  end, "
						+ "case when nc.valor is null then 0 else nc.valor end, "
						+  object[7].toString() + " "
						+ "from registromatriculas rm " 
						+ "join estudiantes e "
						+ "join usuarios u "
						+ "on e.usuarios =  u.idusuarios "
						+ "on rm.estudiantes = e.idestudiantes "
						+ "left join notascalificables nc  "
						+ "on nc.registromatriculas = rm.idregistromatriculas "
						+ "and nc.relacionnotaslogrosdimensionboletin = " + object[7].toString() + " "
						+ "where rm.anosacademicos = " + getCurrentYear().getIdanosacademicos() + " "
						+ "and rm.cursos = " + cursoSeleccionado.getIdcursos() + " "
						+ "and nc.idnotascalificables = " + notascalificables.getIdnotascalificables() + " "
						+ "and rm.fecharetiro is null "
						+ "order by u.apellidos ";
				object = notascalificablesFacade.findByNative(queryNotasCalificables).get(0);
				RequestContext.getCurrentInstance().update(event.getComponent().getClientId());
			}else{
				try{
					String query  = "UPDATE Notascalificables N SET N.valor = " + Short.parseShort(valor.toString()) + " WHERE N.idnotascalificables = " + new Long(object[5].toString());
					notascalificablesFacade.metodo(query);
				}catch(Exception e){
					System.out.println(e.getStackTrace());
				}
			}
			FacesMessage message = new FacesMessage("Exito", "La nota se ha modificado exitosamente");
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	//Escoger la asignatura
    public void escogerAsiganturaVG(Relacionasignaturaperiodos asignatura) {
    	//Obtenemos la lista de usuarios
    	dataListRegistromatriculasVG = registromatriculasFacade.findByLikeAll("SELECT R FROM Registromatriculas R WHERE R.fecharetiro is null AND R.estudiantes.usuarios.estado_activo = true AND R.cursos.idcursos = " + cursoSeleccionado.getIdcursos() + " AND R.anosacademicos.idanosacademicos = " + getCurrentYear().getIdanosacademicos()  + " ORDER BY R.estudiantes.usuarios.apellidos");

    	//Query para traer los periodos
    	List<Periodos> dataListPeriodos = periodosFacade.findByLikeAll("SELECT P FROM Periodos P WHERE P.anoacademicos.idanosacademicos = " + getCurrentYear().getIdanosacademicos() +
    			" AND P.tipo = 0 order by p.fechanotas");
    	//Query para ver la relacione entre las asignaturas del year y las dimensiones
    	String queryRelaciondimensionesasignaturasanosVG = "SELECT R FROM Relaciondimensionesasignaturasano R "
    			+ "WHERE R.relacionasignaturasperiodos.idrelacionasignaturaperiodos = " + asignatura.getIdrelacionasignaturaperiodos() + " "
    			+ "AND R.cursos.idcursos = " + cursoSeleccionado.getIdcursos();
    	
    	//Llenamos la lista de las dimensiones
    	List<Relaciondimensionesasignaturasano>  dataListRelaciondimensionesasignaturasanosVG = relaciondimensionesasignaturasanoFacade.findByLikeAll(queryRelaciondimensionesasignaturasanosVG);
    	
    	dataListPeriodosVG = new ArrayList<PeriodosVG>();
    	//Recorre los periodos
    	for(Periodos pepe:dataListPeriodos){
    		//Agregar el periodo y la lista de dimensiones por periodo
    		PeriodosVG periodosVG = new PeriodosVG();
    		periodosVG.setPeriodos(pepe);
    		//Creamos una lista de RelaciondimensionesasignaturasanoVG
    		List<RelaciondimensionesasignaturasanoVG> dataListRelaciondimensionesasignaturasanoVG = new ArrayList<ConfiguracionNotas.RelaciondimensionesasignaturasanoVG>();
//    		Empezamos a recorrer las dimensione para poder sacar las actividades por cada dimension
    		for(Relaciondimensionesasignaturasano r:dataListRelaciondimensionesasignaturasanosVG){
    			RelaciondimensionesasignaturasanoVG relaciondimensionesasignaturasanoVG = new RelaciondimensionesasignaturasanoVG();
    			relaciondimensionesasignaturasanoVG.setRelaciondimensionesasignaturasano(r);
    			dataListRelaciondimensionesasignaturasanoVG.add(relaciondimensionesasignaturasanoVG);

//				Sacamos los items de cada dimension asociada al periodo
    			String queryRelacionnotasdimension = "SELECT R FROM Relacionnotasdimension R "
    					+ "WHERE R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = " + r.getIdrelaciondimensionesasignaturasano() + " "
    					+ "AND R.periodos.idperiodos = " + pepe.getIdperiodos() + " ORDER BY R.nombrenotas"; 
    			List<Relacionnotasdimension> dataListRelacionnotasdimension = relacionnotasdimensionFacade.findByLikeAll(queryRelacionnotasdimension);
    			
//    			Creamos una lista de RelaciondimensionesasignaturasanoVG
    			List<RelacionnotasdimensionVG> dataListRelacionnotasdimensionVG = new ArrayList<RelacionnotasdimensionVG>();
//    			Recorremos cada item y sacamos las actividades que tiene este item
    			for(Relacionnotasdimension rnd:dataListRelacionnotasdimension){
    				RelacionnotasdimensionVG relacionnotasdimensionVG = new RelacionnotasdimensionVG();
    				relacionnotasdimensionVG.setRelacionnotasdimension(rnd);
    				dataListRelacionnotasdimensionVG.add(relacionnotasdimensionVG);
//    				
    				String queryRelacionnotaslogrosdimensionboletin = "SELECT R FROM Relacionnotaslogrosdimensionboletin R "
    						+ "WHERE R.relacionnotasdimension.idrelacionnotasdimesion = " + rnd.getIdrelacionnotasdimesion();
    				List<Relacionnotaslogrosdimensionboletin> dataListdataListRelacionnotaslogrosdimensionboletin = 
    						relacionnotaslogrosdimensionboletinFacade.findByLikeAll(queryRelacionnotaslogrosdimensionboletin);
    				
//        			Creamos una lista de RelacionnotaslogrosdimensionboletinVG
    				List<RelacionnotaslogrosdimensionboletinVG> dataListRelacionnotaslogrosdimensionboletinVG = 
    						new ArrayList<RelacionnotaslogrosdimensionboletinVG>();
//    				Recorremos cada actividad y le agregamos las notas por cada estudiantes
    				for(Relacionnotaslogrosdimensionboletin rndb:dataListdataListRelacionnotaslogrosdimensionboletin){
    					RelacionnotaslogrosdimensionboletinVG relacionnotaslogrosdimensionboletinVG = 
    							new RelacionnotaslogrosdimensionboletinVG(); 
    					relacionnotaslogrosdimensionboletinVG.setRelacionnotaslogrosdimensionboletin(rndb);
    					dataListRelacionnotaslogrosdimensionboletinVG.add(relacionnotaslogrosdimensionboletinVG);
    					
    					String queryNotasCalificables =  
    							"select  u.idusuarios, "
    							+ "u.nombres,  "
    							+ "u.apellidos,   "
    							+ "e.idestudiantes,  "
    							+ "rm.idregistromatriculas,  "
    							+ "case when nc.idnotascalificables is null then 0 else nc.idnotascalificables  end, "
    							+ "case when nc.valor is null then 0 else nc.valor end, "
    							+ rndb.getIdrelacionnotaslogrosdimensionboletin() + " "
    							+ "from registromatriculas rm " 
    							+ "join estudiantes e "
    							+ "join usuarios u "
    							+ "on e.usuarios =  u.idusuarios "
    							+ "on rm.estudiantes = e.idestudiantes "
    							+ "left join notascalificables nc  "
    							+ "on nc.registromatriculas = rm.idregistromatriculas "
    							+ "and nc.relacionnotaslogrosdimensionboletin = " + rndb.getIdrelacionnotaslogrosdimensionboletin() + " "
    							+ "where rm.anosacademicos = " + getCurrentYear().getIdanosacademicos() + " "
    							+ "and rm.cursos = " + cursoSeleccionado.getIdcursos() + " "
    							+ "and rm.fecharetiro is null "
    							+ "order by u.apellidos ";
    					List<Object[]> dataListNotasCalificablesTMP = notascalificablesFacade.findByNative(queryNotasCalificables);
    					relacionnotaslogrosdimensionboletinVG.setDataListNotasCalificablesVG(dataListNotasCalificablesTMP);
    				}
///*					Anadimos a cada Relacionnotasdimension (tiem) la lista de de las Relacionnotaslogrosdimensionboletin (actividades) que
//    				estan asociadas a ella*/
    				relacionnotasdimensionVG.setDataListRelacionnotaslogrosdimensionboletinVG(dataListRelacionnotaslogrosdimensionboletinVG);
    			}
//				Anadimos a cada Relaciondimensionesasignaturasano las Relacionnotasdimension (item) que estan asociadas a ella 
    			relaciondimensionesasignaturasanoVG.setDataListRelacionnotasdimensionVG(dataListRelacionnotasdimensionVG);
        	}
    		periodosVG.setDataListRelaciondimensionesasignaturasanoVG(dataListRelaciondimensionesasignaturasanoVG);
    		dataListPeriodosVG.add(periodosVG);
    	}
    }
    
    
    
    
    //Clase estatica para cargar funda de notas
    public static class PeriodosVG {
    	private Periodos periodos;
    	private List<Relaciondimensionesasignaturasano> dataListRelaciondimensionesasignaturasanosVG;
    	private List<RelaciondimensionesasignaturasanoVG> dataListRelaciondimensionesasignaturasanoVG;
    	
		public Periodos getPeriodos() {
			return periodos;
		}
		public void setPeriodos(Periodos periodos) {
			this.periodos = periodos;
		}
		public List<Relaciondimensionesasignaturasano> getDataListRelaciondimensionesasignaturasanosVG() {
			return dataListRelaciondimensionesasignaturasanosVG;
		}
		public void setDataListRelaciondimensionesasignaturasanosVG(
				List<Relaciondimensionesasignaturasano> dataLstRelaciondimensionesasignaturasanosVG) {
			this.dataListRelaciondimensionesasignaturasanosVG = dataLstRelaciondimensionesasignaturasanosVG;
		}
		public List<RelaciondimensionesasignaturasanoVG> getDataListRelaciondimensionesasignaturasanoVG() {
			return dataListRelaciondimensionesasignaturasanoVG;
		}
		public void setDataListRelaciondimensionesasignaturasanoVG(
				List<RelaciondimensionesasignaturasanoVG> dataListRelaciondimensionesasignaturasanoVG) {
			this.dataListRelaciondimensionesasignaturasanoVG = dataListRelaciondimensionesasignaturasanoVG;
		}
    }
    
    //Clase estatica de Relaciondimensionesasignaturasano
    public static class RelaciondimensionesasignaturasanoVG{
    	private Relaciondimensionesasignaturasano relaciondimensionesasignaturasano;
    	private List<RelacionnotasdimensionVG> dataListRelacionnotasdimensionVG;
    	
		public Relaciondimensionesasignaturasano getRelaciondimensionesasignaturasano() {
			return relaciondimensionesasignaturasano;
		}
		public void setRelaciondimensionesasignaturasano(
				Relaciondimensionesasignaturasano relaciondimensionesasignaturasano) {
			this.relaciondimensionesasignaturasano = relaciondimensionesasignaturasano;
		}
		public List<RelacionnotasdimensionVG> getDataListRelacionnotasdimensionVG() {
			return dataListRelacionnotasdimensionVG;
		}
		public void setDataListRelacionnotasdimensionVG(
				List<RelacionnotasdimensionVG> dataListRelacionnotasdimension) {
			this.dataListRelacionnotasdimensionVG = dataListRelacionnotasdimension;
		}
    }
    
    //Clase de Relacionnotasdimension VG 
    public static class RelacionnotasdimensionVG{
    	private Relacionnotasdimension relacionnotasdimension;
    	private List<RelacionnotaslogrosdimensionboletinVG> dataListRelacionnotaslogrosdimensionboletinVG;
		public Relacionnotasdimension getRelacionnotasdimension() {
			return relacionnotasdimension;
		}
		public void setRelacionnotasdimension(
				Relacionnotasdimension relacionnotasdimension) {
			this.relacionnotasdimension = relacionnotasdimension;
		}
		public List<RelacionnotaslogrosdimensionboletinVG> getDataListRelacionnotaslogrosdimensionboletinVG() {
			return dataListRelacionnotaslogrosdimensionboletinVG;
		}
		public void setDataListRelacionnotaslogrosdimensionboletinVG(
				List<RelacionnotaslogrosdimensionboletinVG> dataListRelacionnotaslogrosdimensionboletinVG) {
			this.dataListRelacionnotaslogrosdimensionboletinVG = dataListRelacionnotaslogrosdimensionboletinVG;
		}
    }
    
    //Clase de Relacionnotaslogrosdimensionboletin VG
    public static class RelacionnotaslogrosdimensionboletinVG{
    	private Relacionnotaslogrosdimensionboletin relacionnotaslogrosdimensionboletin;
    	private List<Object[]> dataListNotasCalificablesVG;
		public Relacionnotaslogrosdimensionboletin getRelacionnotaslogrosdimensionboletin() {
			return relacionnotaslogrosdimensionboletin;
		}
		public void setRelacionnotaslogrosdimensionboletin(
				Relacionnotaslogrosdimensionboletin relacionnotaslogrosdimensionboletin) {
			this.relacionnotaslogrosdimensionboletin = relacionnotaslogrosdimensionboletin;
		}
		public List<Object[]> getDataListNotasCalificablesVG() {
			return dataListNotasCalificablesVG;
		}
		public void setDataListNotasCalificablesVG(
				List<Object[]> dataListNotasCalificablesVG) {
			this.dataListNotasCalificablesVG = dataListNotasCalificablesVG;
		}
    }
    
    ///###EDITAR LA SABANA
	private List<RelacionnotaslogrosdimensionboletinVG> dataListRelacionnotaslogrosdimensionboletinVGSeleccinado;
	private Relacionnotaslogrosdimensionboletin relacionnotaslogrosdimensionboletinTMP;
	String idSeleccionado = "";

	// Metodo para prueba
	public void seleccionarRelacionnotaslogrosdimensionboletinVG(
			AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest myRequest = (HttpServletRequest) context
				.getExternalContext().getRequest();
		UIRepeat repeat = (UIRepeat) event.getComponent().getParent();
		int index = Integer
				.parseInt(myRequest.getParameter("index").toString());
		dataListRelacionnotaslogrosdimensionboletinVGSeleccinado = ((List<RelacionnotasdimensionVG>) repeat
				.getValue()).get(index)
				.getDataListRelacionnotaslogrosdimensionboletinVG();
		idSeleccionado = event.getComponent().getParent()
				.findComponent("panelActividades").getClientId();
		// Creamos un nuevo objecto el cual se va a crear
		relacionnotaslogrosdimensionboletinTMP = new Relacionnotaslogrosdimensionboletin(
				new Long(0));
		// Agregamos la relacion de donde hereda
		// relacionnotaslogrosdimensionboletin
		relacionnotaslogrosdimensionboletinTMP
				.setRelacionnotasdimension(((List<RelacionnotasdimensionVG>) repeat
						.getValue()).get(index).getRelacionnotasdimension());
	}

	//Metodo para guardar el la actividad
	public void guardarRelacionnotaslogrosdimensionboletinVG() {
		try {
			RelacionnotaslogrosdimensionboletinVG relacionnotaslogrosdimensionboletinVG = new RelacionnotaslogrosdimensionboletinVG();
			relacionnotaslogrosdimensionboletinVG
					.setRelacionnotaslogrosdimensionboletin(relacionnotaslogrosdimensionboletinTMP);
			// Creamos el nuevo item
			relacionnotaslogrosdimensionboletinFacade
					.create(relacionnotaslogrosdimensionboletinTMP);
			String queryNotasCalificables = "select  u.idusuarios, "
					+ "u.nombres,  "
					+ "u.apellidos,   "
					+ "e.idestudiantes,  "
					+ "rm.idregistromatriculas,  "
					+ "case when nc.idnotascalificables is null then 0 else nc.idnotascalificables  end, "
					+ "case when nc.valor is null then 0 else nc.valor end, "
					+ relacionnotaslogrosdimensionboletinTMP.getIdrelacionnotaslogrosdimensionboletin() + " "
					+ "from registromatriculas rm "
					+ "join estudiantes e "
					+ "join usuarios u "
					+ "on e.usuarios =  u.idusuarios "
					+ "on rm.estudiantes = e.idestudiantes "
					+ "left join notascalificables nc  "
					+ "on nc.registromatriculas = rm.idregistromatriculas "
					+ "and nc.relacionnotaslogrosdimensionboletin = "
					+ relacionnotaslogrosdimensionboletinTMP
							.getIdrelacionnotaslogrosdimensionboletin() + " "
					+ "where rm.anosacademicos = "
					+ getCurrentYear().getIdanosacademicos() + " "
					+ "and rm.cursos = " + cursoSeleccionado.getIdcursos()
					+ "and rm.fecharetiro is null "
					+ " " + "order by u.apellidos ";
			List<Object[]> dataListNotasCalificablesTMP = notascalificablesFacade
					.findByNative(queryNotasCalificables);
			relacionnotaslogrosdimensionboletinVG
					.setDataListNotasCalificablesVG(dataListNotasCalificablesTMP);
			// Agregamos a la lista seleccionada el nuevo objeto con el nuevo
			// ITEM
			dataListRelacionnotaslogrosdimensionboletinVGSeleccinado
					.add(relacionnotaslogrosdimensionboletinVG);
			// Actualizamos el panel seleccionadao
			RequestContext.getCurrentInstance().update(idSeleccionado);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

	}

	public Relacionnotaslogrosdimensionboletin getRelacionnotaslogrosdimensionboletinTMP() {
		return relacionnotaslogrosdimensionboletinTMP;
	}

	public void setRelacionnotaslogrosdimensionboletinTMP(
			Relacionnotaslogrosdimensionboletin relacionnotaslogrosdimensionboletinVGEditar) {
		this.relacionnotaslogrosdimensionboletinTMP = relacionnotaslogrosdimensionboletinVGEditar;
	}
	
//  
}
