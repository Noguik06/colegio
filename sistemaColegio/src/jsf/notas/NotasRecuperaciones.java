/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.notas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import jsf.notas.NotasEstudiantes.Definitivas;
import jsf.usuarios.Sesiones;

import com.sun.faces.facelets.component.UIRepeat;

import ejb.AnosacademicosFacade;
import ejb.AsignaturasFacade;
import ejb.CursosFacade;
import ejb.EstudiantesFacade;
import ejb.NotascalificablesFacade;
import ejb.PeriodosFacade;
import ejb.RecuperacionesFacade;
import ejb.RegistromatriculasFacade;
import ejb.RelacionasignaturaperiodosFacade;
import ejb.RelaciondimensionesasignaturasanoFacade;
import ejb.RelacionnotasdimensionFacade;
import ejb.RelacionnotaslogrosdimensionboletinFacade;
import ejb.RelacionrecuperacionregistromatriculasFacade;
import entities.Anosacademicos;
import entities.Asignaturas;
import entities.Cursos;
import entities.Dimensiones;
import entities.Estudiantes;
import entities.Notascalificables;
import entities.Periodos;
import entities.Recuperaciones;
import entities.Registromatriculas;
import entities.Relacionasignaturaperiodos;
import entities.Relaciondimensionesasignaturasano;
import entities.Relacionnotasdimension;
import entities.Relacionnotaslogrosdimensionboletin;
import entities.Relacionprofesoresasignaturaperiodo;
import entities.Relacionrecuperacionregistromatriculas;

/**
 *
 * @author juannoguera
 */
@ManagedBean(name = "notasRecuperaciones")
@ViewScoped
public class NotasRecuperaciones implements Serializable {

    /**
     * Creates a new instance of NewJSFManagedBean
     */
    @EJB
    private RegistromatriculasFacade registromatriculasFacade;
    @EJB
    private RelaciondimensionesasignaturasanoFacade relaciondimensionesasignaturasanoFacade;
    @EJB
    private RelacionnotasdimensionFacade relacionnotasdimensionFacade;
    @EJB
    private AsignaturasFacade asignaturasFacade;
    @EJB
    private AnosacademicosFacade anosacademicosFacade;
    @EJB
    private EstudiantesFacade estudiantesFacade;
    @EJB
    private RelacionasignaturaperiodosFacade relacionasignaturaperiodosFacade;
    @EJB
    private RelacionnotaslogrosdimensionboletinFacade relacionnotaslogrosdimensionboletinFacade;
    @EJB
    private NotascalificablesFacade notascalificablesFacade;
    @EJB
    private CursosFacade cursosFacade;
    @EJB
    private PeriodosFacade periodosFacade;
    @EJB
    private RecuperacionesFacade recuperacionesFacade;
    @EJB
    private RelacionrecuperacionregistromatriculasFacade relacionrecuperacionregistromatriculasFacade;
    //##ANOSACADEMICOS
    //Anoacademico actual
    private Anosacademicos anosacademicosActual;
    //Estudiante seleccionado
    private Estudiantes estudiantesSeleccionado;
    //##REGISTRO MATRICULA
    private Registromatriculas registromatriculas;
    //##ASIGNATURA SELECCIONADA
    private Relacionasignaturaperiodos asignaturaSeleccionada;
    //##PERIODOS
    //Bandera para ver los periodos
    private boolean banderaPeriodos = false;
    //Lista de los periodos
    private List<Periodos> dataListPeriodos;
    //Periodos escogido
    private Periodos periodoSeleccionado;
    //###LISTA DE LAS ASIGNATURAS
    private List<Relacionasignaturaperiodos> dataListAsignaturas;
    //##LISTA DE LAS ASIGNATURAS REAL
//    private List<AsignaturasEstudiantes> dataListAsignturasEstudiantes;
    private List<AsignaturasEstudiantes> dataListAsignturasEstudiantes;
    //##LISTA DE LOS CURSOS
    //Lista de los cursos que este profesor tiene acceso
    private List<Cursos> dataListCursos;
    //Curso seleccionado
    private Cursos cursoSeleccionado;
    //##LISTA DE LOS REGISTROS DE MATRICULAS
    private List<Registromatriculas> dataListRegistroMatriculas;
    //##Lista de los registros de las matricular
//    private List<RegistroMatriculasEstudiantes> dataListRegistroMatriculasEstudiantes;
    private List<RegistroMatriculasEstudiantes> dataListRegistroMatriculasEstudiantes;
    //###RECUPERACIONES
    //LISTA
    private List<Recuperaciones> dataListRecuperaciones;
    //Recuperaci��n escogida
    private Recuperaciones recuperacionEscogida;
    //Lista de los estudiantes a recuperar
    private List<RegistroMatriculasEstudiantes> dataListRegistroMatriculasRecuperar;
    //##LISTA PARA LOS HEADERS
    private List<NotasEstudiantes.PeriodosEstudiantes> dataListPeriodosEstudiantesHeader;

    public NotasRecuperaciones() {
    }

    public Anosacademicos getCurrentYear() {
        if (anosacademicosActual == null) {
            anosacademicosActual = anosacademicosFacade.findByLikeAll("SELECT A FROM Anosacademicos A WHERE A.estadoactivo = true").get(0);
        }

        return anosacademicosActual;
    }

    //Funci��n utilizada para devolver la sesi��n que estamos manejando
    public Sesiones getSesion() {
        FacesContext FCInstance = FacesContext.getCurrentInstance();
        String theBeanName = "sesiones";
        Object bean = FCInstance.getELContext().getELResolver().getValue(
                FCInstance.getELContext(), null, theBeanName);
        Sesiones sesiones = (Sesiones) bean;

        if (sesiones.getUsuarios() == null) {
            return null;
        }

        List<Estudiantes> tmp = estudiantesFacade.findByLikeAll("SELECT E FROM Estudiantes E WHERE E.usuarios.idusuarios = " + sesiones.getUsuarios().getIdusuarios());
        if (!tmp.isEmpty()) {
            estudiantesSeleccionado = tmp.get(0);
            List<Registromatriculas> tmp2 = registromatriculasFacade.findByLikeAll("SELECT R FROM Registromatriculas R WHERE R.anosacademicos.idanosacademicos =" + getCurrentYear().getIdanosacademicos() + " AND R.estudiantes.idestudiantes = " + estudiantesSeleccionado.getIdestudiantes());
            if (!tmp2.isEmpty()) {
                registromatriculas = tmp2.get(0);
            }
        }
        return sesiones;
    }

    public static class RegistroMatriculasEstudiantes {

        private Registromatriculas registromatriculas;
        private double valor = 0;
        private List<DimensionesEstudiantes> dataListDimensionesEstudiantes;
        private PeriodosEstudiantes periodosEstudiantes;
        private List<Relacionrecuperacionregistromatriculas> dataListRelacionrecuperacionregistromatriculas;

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }

        public List<DimensionesEstudiantes> getDataListDimensionesEstudiantes() {
            return dataListDimensionesEstudiantes;
        }

        public void setDataListDimensionesEstudiantes(List<DimensionesEstudiantes> dataListDimensionesEstudiantes) {
            this.dataListDimensionesEstudiantes = dataListDimensionesEstudiantes;
        }

        public Registromatriculas getRegistromatriculas() {
            return registromatriculas;
        }

        public void setRegistromatriculas(Registromatriculas registromatriculas) {
            this.registromatriculas = registromatriculas;
        }

        public PeriodosEstudiantes getPeriodosEstudiantes() {
            return periodosEstudiantes;
        }

        public void setPeriodosEstudiantes(PeriodosEstudiantes periodosEstudiantes) {
            this.periodosEstudiantes = periodosEstudiantes;
        }

        public List<Relacionrecuperacionregistromatriculas> getDataListRegistroMatriculasEstudiantes() {
            return dataListRelacionrecuperacionregistromatriculas;
        }

        public void setDataListRegistroMatriculasEstudiantes(List<Relacionrecuperacionregistromatriculas> dataListRegistroMatriculasEstudiantes) {
            this.dataListRelacionrecuperacionregistromatriculas = dataListRegistroMatriculasEstudiantes;
        }
    }

    public static class AsignaturasEstudiantes {

        private Asignaturas asignaturas;
        private double valor = 0;
        private List<DimensionesEstudiantes> dataListDimensionesEstudiantes;
        private List<PeriodosEstudiantes> dataListPeriodosEstudiantes;

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }

        public List<DimensionesEstudiantes> getDataListDimensionesEstudiantes() {
            return dataListDimensionesEstudiantes;
        }

        public void setDataListDimensionesEstudiantes(List<DimensionesEstudiantes> dataListDimensionesEstudiantes) {
            this.dataListDimensionesEstudiantes = dataListDimensionesEstudiantes;
        }

        public Asignaturas getAsignaturas() {
            return asignaturas;
        }

        public void setAsignaturas(Asignaturas asignaturas) {
            this.asignaturas = asignaturas;
        }

        public List<PeriodosEstudiantes> getDataListPeriodosEstudiantes() {
            return dataListPeriodosEstudiantes;
        }

        public void setDataListPeriodosEstudiantes(List<PeriodosEstudiantes> dataListPeriodosEstudiantes) {
            this.dataListPeriodosEstudiantes = dataListPeriodosEstudiantes;
        }
    }

    public class PeriodosEstudiantes {

        private double valor = 0;
        private List<DimensionesEstudiantes> dataListDimensionesEstudiantes;
        private Periodos periodos;

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }

        public List<DimensionesEstudiantes> getDataListDimensionesEstudiantes() {
            return dataListDimensionesEstudiantes;
        }

        public void setDataListDimensionesEstudiantes(List<DimensionesEstudiantes> dataListDimensionesEstudiantes) {
            this.dataListDimensionesEstudiantes = dataListDimensionesEstudiantes;
        }

        public Periodos getPeriodos() {
            return periodos;
        }

        public void setPeriodos(Periodos periodos) {
            this.periodos = periodos;
        }
    }

    public class DimensionesEstudiantes {

        private double valor = 0;
        private Dimensiones dimensiones;
        private List<NotasDimensionesEstudiantes> dataListNotasDimensionesEstudiantes;

        public Dimensiones getDimensiones() {
            return dimensiones;
        }

        public void setDimensiones(Dimensiones dimensiones) {
            this.dimensiones = dimensiones;
        }

        public List<NotasDimensionesEstudiantes> getDataListNotasDimensionesEstudiantes() {
            return dataListNotasDimensionesEstudiantes;
        }

        public void setDataListNotasDimensionesEstudiantes(List<NotasDimensionesEstudiantes> dataListNotasDimensionesEstudiantes) {
            this.dataListNotasDimensionesEstudiantes = dataListNotasDimensionesEstudiantes;
        }

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }
    }

    public class NotasDimensionesEstudiantes {

        private Relacionnotasdimension relacionnotasdimension;
        private double valor = 0;
        private List<ActividadesNotasEstudiantes> dataListActividadesNotasEstudiantes;

        public Relacionnotasdimension getRelacionnotasdimension() {
            return relacionnotasdimension;
        }

        public void setRelacionnotasdimension(Relacionnotasdimension relacionnotasdimension) {
            this.relacionnotasdimension = relacionnotasdimension;
        }

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }

        public List<ActividadesNotasEstudiantes> getDataListActividadesNotasEstudiantes() {
            return dataListActividadesNotasEstudiantes;
        }

        public void setDataListActividadesNotasEstudiantes(List<ActividadesNotasEstudiantes> dataListActividadesNotasEstudiantes) {
            this.dataListActividadesNotasEstudiantes = dataListActividadesNotasEstudiantes;
        }
    }

    public class ActividadesNotasEstudiantes {

        private Notascalificables notascalificables;
        private double valor = 0;

        public Notascalificables getNotascalificables() {
            return notascalificables;
        }

        public void setNotascalificables(Notascalificables notascalificables) {
            this.notascalificables = notascalificables;
        }

        public double getValor() {
            return valor;
        }

        public void setValor(double valor) {
            this.valor = valor;
        }
    }

    //M��todo para obtener la sesi��n de la persona que se ha logueado
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

    //##M��todo para que los profesores vean las notas de los alumnos
    //###PROPIEDADES DE LA LISTA DE CURSOS
    public List<Cursos> getDataListCursos() {
        Sesiones sesiones = getSesiones();
        if (sesiones == null || sesiones.getUsuarios() == null) {
            return null;
        }
        if (dataListCursos == null || dataListCursos.isEmpty()) {
            Relacionprofesoresasignaturaperiodo tmp;
            dataListCursos = cursosFacade.findByLikeAll("SELECT  DISTINCT(R.cursos) FROM Relacionprofesoresasignaturaperiodo R" +
            		" WHERE R.profesores.usuarios.idusuarios = " + sesiones.getUsuarios().getIdusuarios() +
            		" AND R.cursos.anosacademicos.estadoactivo = true"
            		+ " ORDER BY R.cursos.grados.numero");
        }
        return dataListCursos;
    }

    public void escogerCurso(Cursos cursos) {

        banderaPeriodos = false;

        this.cursoSeleccionado = cursos;
        dataListAsignaturas = relacionasignaturaperiodosFacade.findByLikeAll("SELECT RPA.relacionasignaturaperiodos FROM Relacionprofesoresasignaturaperiodo RPA " +
        		"WHERE RPA.cursos.grados.idgrados = " + cursos.getGrados().getIdgrados() + " " +
        		 "AND RPA.profesores.usuarios.idusuarios = " + getSesiones().getUsuarios().getIdusuarios() + " " +
        		 " AND  RPA.cursos.anosacademicos.estadoactivo = true" +
        		 " ORDER BY RPA.relacionasignaturaperiodos.asignaturas.nombre ");
//        registromatriculas.get
        this.dataListRegistroMatriculas = registromatriculasFacade.findByLikeAll("SELECT R FROM Registromatriculas R WHERE R.cursos.idcursos = " + cursos.getIdcursos() + " ORDER BY R.estudiantes.usuarios.apellidos");

        this.dataListRegistroMatriculasEstudiantes = null;

        dataListRegistroMatriculasRecuperar = null;

        periodoSeleccionado = null;

    }

    public List<Relacionasignaturaperiodos> getDataListAsignaturas() {
        return dataListAsignaturas;
    }

    public void setDataListAsignaturas(List<Relacionasignaturaperiodos> dataListAsignaturas) {
        this.dataListAsignaturas = dataListAsignaturas;
    }

    public void escogerAsignatura(Relacionasignaturaperiodos rap) {
        asignaturaSeleccionada = rap;
        periodoSeleccionado = null;
        banderaPeriodos = true;
        this.dataListRegistroMatriculasRecuperar = null;
    }

    //##PERIODOS
    //M��todo para saber si los periodos se renderizan o no
    public boolean isBanderaPeriodos() {
        return banderaPeriodos;
    }

    //Lista de los cursos
    public List<Periodos> getDataListPeriodos() {
        if (dataListPeriodos == null) {
            dataListPeriodos = periodosFacade.findByLike("SELECT P FROM Periodos P WHERE P.anoacademicos.idanosacademicos = " + getCurrentYear().getIdanosacademicos() + " ORDER BY P.fechainicio");
        }
        return dataListPeriodos;
    }

    @ManagedProperty(value="#{notasEstudiantes}")
    private NotasEstudiantes notasEstudiantes = new NotasEstudiantes();
    
    public void setNotasEstudiantes(NotasEstudiantes notasEstudiantes) {
		this.notasEstudiantes = notasEstudiantes;
	}
    
    private List<Definitivas> dataListDefinitivas;
    
    
    //M�todo para escoger el curso
    public void escogerPeriodo(Periodos periodos) {
        periodoSeleccionado = periodos;
        dataListRegistroMatriculasRecuperar = null;
        dataListRegistroMatriculasEstudiantes = null;
		if (periodos.getTipo() == 0) {
			dataListDefinitivas = null;
			dataListRegistroMatriculasEstudiantes = getDataListRegistroMatriculasEstudiantes();
		} else {
			dataListRegistroMatriculasRecuperar = null;
			notasEstudiantes.calcularDefinitivasPorAsignatura(dataListRegistroMatriculas, asignaturaSeleccionada, cursoSeleccionado);
			dataListDefinitivas = notasEstudiantes.getDataListDefinitivas();
		}
    }

    //##RECUPERACIONES
    //M�todo para la lista de los estuiantes que tienen que recuperar al final
    public List<Definitivas> getDataListDefinitivas(){
    	if(dataListDefinitivas == null)
    		return null;
    	Iterator<Definitivas> it = dataListDefinitivas.iterator();
    	while(it.hasNext()){
    		Definitivas def = it.next();
    		if(def.getDefinitiva() > 64){
    			it.remove();
    		}
    	}
    	return dataListDefinitivas;
    }
    
    public void setDataListDefinitivas(List<Definitivas> dataListDefinitivas){
    	this.dataListDefinitivas = dataListDefinitivas;
    }
    
    
    //M�todo para la lista de los estudiantes que tienen que recuperar por periodos
    public List<RegistroMatriculasEstudiantes> getDataListListEstudiantes() {
        if (dataListRegistroMatriculasRecuperar == null && periodoSeleccionado != null
        		&& periodoSeleccionado.getTipo() == 0) {
            dataListRegistroMatriculasRecuperar = new ArrayList<RegistroMatriculasEstudiantes>();


            List<Recuperaciones> dataListRecuperacionesTmp = recuperacionesFacade.findByLike("SELECT R FROM Recuperaciones R ORDER BY R.numero");

            for (RegistroMatriculasEstudiantes r : dataListRegistroMatriculasEstudiantes) {
                if (r.getPeriodosEstudiantes().getValor() < 80) {
                    r.setDataListRegistroMatriculasEstudiantes(new ArrayList<Relacionrecuperacionregistromatriculas>());
                    dataListRegistroMatriculasRecuperar.add(r);
                    boolean bandera = true;


                    for (Recuperaciones r1 : dataListRecuperacionesTmp) {
                        Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculasTMp = new Relacionrecuperacionregistromatriculas();
                        if (bandera) {
                            if (relacionrecuperacionregistromatriculasFacade.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = " + r1.getIdrecuperaciones() + " AND R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos() + " AND R.registromatriculas.idregistromatriculas = " + r.getRegistromatriculas().getIdregistromatriculas() + " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = " + asignaturaSeleccionada.getIdrelacionasignaturaperiodos()).isEmpty()) {
                                relacionrecuperacionregistromatriculasTMp.setIdrelacionrecuperacionregistromatriculas(new Long(0));
                                relacionrecuperacionregistromatriculasTMp.setPeriodos(periodoSeleccionado);
                                relacionrecuperacionregistromatriculasTMp.setRecuperaciones(r1);
                                relacionrecuperacionregistromatriculasTMp.setRegistromatriculas(r.getRegistromatriculas());
                                relacionrecuperacionregistromatriculasTMp.setValor(new Double(0));
                            } else {
                                relacionrecuperacionregistromatriculasTMp = relacionrecuperacionregistromatriculasFacade.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = " + r1.getIdrecuperaciones() + " AND R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos() + " AND R.registromatriculas.idregistromatriculas = " + r.getRegistromatriculas().getIdregistromatriculas() + " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = " + asignaturaSeleccionada.getIdrelacionasignaturaperiodos()).get(0);
                            }
                        }
                        r.getDataListRegistroMatriculasEstudiantes().add(relacionrecuperacionregistromatriculasTMp);
                    }
                }
            }
        }

        return dataListRegistroMatriculasRecuperar;
    }

    //Lista de las recuperaciones
    public List<Recuperaciones> getDataListRecuperaciones() {
        if (dataListRecuperaciones == null && periodoSeleccionado != null) {
            dataListRecuperaciones = recuperacionesFacade.findByLike("SELECT R FROM Recuperaciones R ORDER BY R.numero");
        }
        return dataListRecuperaciones;
    }

    public List<RegistroMatriculasEstudiantes> getDataListRegistroMatriculasEstudiantes() {
        if (dataListRegistroMatriculasEstudiantes == null && periodoSeleccionado != null) {

            dataListRegistroMatriculasEstudiantes = new ArrayList<RegistroMatriculasEstudiantes>();
//            
////            String query = "select case when ceil(sum(pepe2.valorperiodoasignatura)) is null then 0 else case when "
////            		+ "ceil(sum(pepe2.valorperiodoasignatura)) > 100 then 100 else "
////            		+ "ceil(sum(pepe2.valorperiodoasignatura)) end end ,  "
////            		+ "rm.idregistromatriculas, "
////            		+ "lower(u.nombres), "
////            		+ "lower(u.apellidos) "
////            		+ "from "
////            		+ "(select ((sum(pepe.valordn)*rdaa.porcentaje/100)) valorperiodoasignatura, "
////            		+ "pepe.relaciondimensionesasignaturasano, "
////            		+ "pepe.registromatriculas "
////            		+ "from "
////            		+ "(select (round(round(sum(valor)/count(1)) * rnd.porcentaje/100)) valordn, "
////            		+ "nc.registromatriculas, "
////            		+ "rnd.nombrenotas, "
////            		+ "rnd.idrelacionnotasdimesion, "
////            		+ "rnd.relaciondimensionesasignaturasano from notascalificables "
////            		+ "nc join relacionnotaslogrosdimensionboletin rnldb on "
////            		+ "rnldb.idrelacionnotaslogrosdimensionboletin = nc.relacionnotaslogrosdimensionboletin "
////            		+ "join relacionnotasdimension rnd on rnd.idrelacionnotasdimesion = rnldb.relacionnotasdimension "
////            		+ "and rnd.periodos =  " + periodoSeleccionado.getIdperiodos() + " "
////            		+ "GROUP BY nc.registromatriculas, rnd.nombrenotas, rnd.porcentaje, rnd.idrelacionnotasdimesion, rnd.relaciondimensionesasignaturasano "
////            		+ "ORDER BY rnd.idrelacionnotasdimesion) pepe join "
////            		+ "relaciondimensionesasignaturasano rdaa on rdaa.idrelaciondimensionesasignaturasano = pepe.relaciondimensionesasignaturasano "
////            		+ "join relacionasignaturaperiodos rap on rap.idrelacionasignaturaperiodos = rdaa.relacionasignaturasperiodos "
////            		+ "where rdaa.relacionasignaturasperiodos =  " + asignaturaSeleccionada.getIdrelacionasignaturaperiodos() + " "
////            		+ "and rdaa.cursos =  " + cursoSeleccionado.getIdcursos() + " "
////            		+ "group by pepe.relaciondimensionesasignaturasano, rdaa.porcentaje, pepe.registromatriculas order by pepe.registromatriculas) pepe2 "
////            		+ "right join registromatriculas rm on rm.idregistromatriculas = pepe2.registromatriculas "
////            		+ "join estudiantes e on rm.estudiantes = e.idestudiantes "
////            		+ "join usuarios u on u.idusuarios = e.usuarios where rm.cursos =  " + cursoSeleccionado.getIdcursos() + " "
////            		+ "group by rm.idregistromatriculas, u.nombres, u.apellidos order by u.apellidos";
//
//            
//            String query = "select sum(q_dimensiones.valortotal), "
//            		+ "q_dimensiones.registromatriculas, "
//            		+ "u.nombres, "
//            		+ "u.apellidos "
//            		+ "from (select round(cast(rdaa.porcentaje * ((case when sum(round(pepe.valordndos)) is null "
//            		+ "then 0 else cast (sum(round(pepe.valordndos)) as numeric ) end)) as numeric)/100) valortotal, "
//            		+ "pepe.registromatriculas "
//            		+ "from (select round(cast(rnd.porcentaje * (cast (sum(case when valor is null then 0 else valor end) as numeric)/count(1)) as numeric)/100) valordndos, "
//            		+ "rnldb.relacionnotasdimension, "
//            		+ "rnd.relaciondimensionesasignaturasano, "
//            		+ "nc.registromatriculas from notascalificables nc  "
//            		+ "right join relacionnotaslogrosdimensionboletin rnldb on "
//            		+ "rnldb.idrelacionnotaslogrosdimensionboletin = nc.relacionnotaslogrosdimensionboletin "
//            		+ "join relacionnotasdimension rnd on rnd.idrelacionnotasdimesion = rnldb.relacionnotasdimension "
//            		+ "and rnd.periodos =  " + periodoSeleccionado.getIdperiodos() + " "
//            		+ "group by rnldb.relacionnotasdimension, "
//            		+ "rnd.porcentaje, "
//            		+ "rnd.relaciondimensionesasignaturasano, "
//            		+ "nc.registromatriculas) pepe "
//            		+ "join relaciondimensionesasignaturasano rdaa on rdaa.idrelaciondimensionesasignaturasano = pepe.relaciondimensionesasignaturasano  "
//            		+ "where rdaa.relacionasignaturasperiodos =  " + asignaturaSeleccionada.getIdrelacionasignaturaperiodos()+ " "
//            		+ "and rdaa.cursos =  " + cursoSeleccionado.getIdcursos()+ " "
//            		+ "group by relaciondimensionesasignaturasano, rdaa.porcentaje,  "
//            		+ "rdaa.dimensiones, pepe.registromatriculas) q_dimensiones "
//            		+ "join registromatriculas rm on rm.idregistromatriculas = q_dimensiones.registromatriculas "
//            		+ "join estudiantes e on rm.estudiantes = e.idestudiantes "
//            		+ "join usuarios u on u.idusuarios = e.usuarios  "
//            		+ "group by q_dimensiones.registromatriculas, u.nombres, u.apellidos";
//            
//            double tmp = new BigDecimal(4.5)
//					.setScale(0,
//							BigDecimal.ROUND_HALF_UP)
//					.doubleValue();
//            
//            List<Object[]> dataListRecuperaciones = recuperacionesFacade.findoAll(query);
//            
//            for(Object[] o:dataListRecuperaciones){
//            	//Hacemos objeto tipo RegistroMatriculasEstudiantes 
//            	RegistroMatriculasEstudiantes registroMatriculasEstudiantes = new RegistroMatriculasEstudiantes();
//            	//Hacemos objeto tipo  PeriodosEstudiantes        	
//            	PeriodosEstudiantes periodosEstudiantes = new PeriodosEstudiantes();
//            	periodosEstudiantes.setPeriodos(periodoSeleccionado);
//            	periodosEstudiantes.setValor(Double.parseDouble(o[0].toString()));
//            	//
//            	registroMatriculasEstudiantes.setPeriodosEstudiantes(periodosEstudiantes);
//            	registroMatriculasEstudiantes.setRegistromatriculas(registromatriculasFacade.find(Long.parseLong(o[1].toString())));
//            	dataListRegistroMatriculasEstudiantes.add(registroMatriculasEstudiantes);
//            }

            //Sacamos la lista de los periodos
            List<Periodos> dataListPeriodosTmp = new ArrayList<Periodos>();
            dataListPeriodosTmp.add(periodoSeleccionado);

            for (Registromatriculas rm : dataListRegistroMatriculas) {

                //Creamos un nuevo objeto
                RegistroMatriculasEstudiantes registroMatriculasEstudiantes = new RegistroMatriculasEstudiantes();

                //Agregamos el nuevo objeto a la lista de registros ya creada antes
                dataListRegistroMatriculasEstudiantes.add(registroMatriculasEstudiantes);

                //Agregamos al nuevo objeto la rm
                registroMatriculasEstudiantes.setRegistromatriculas(rm);

                //Creamos una lista tipo periodosEstudiantes
                PeriodosEstudiantes periodosEstudiantes = new PeriodosEstudiantes();


                //Agregamos al registro de matr�culas la nueva lista de los periodos
                registroMatriculasEstudiantes.setPeriodosEstudiantes(periodosEstudiantes);


                //Recorremos la lista de los peeriodos
                for (Periodos p : dataListPeriodosTmp) {
                    double promedioPeriodos = 0;

                    //Sacamos la lista de dimensiones de esta asignatura
                    List<Relaciondimensionesasignaturasano> datalistDimensionesTmp = relaciondimensionesasignaturasanoFacade.findByLikeAll("SELECT R FROM Relaciondimensionesasignaturasano R WHERE R.relacionasignaturasperiodos.idrelacionasignaturaperiodos = " + asignaturaSeleccionada.getIdrelacionasignaturaperiodos() + " AND R.cursos.idcursos = " + rm.getCursos().getIdcursos());
                    List<DimensionesEstudiantes> dataListDimensionesEstudiantestmp = new ArrayList<DimensionesEstudiantes>();


                    periodosEstudiantes.setDataListDimensionesEstudiantes(dataListDimensionesEstudiantestmp);
                    periodosEstudiantes.setPeriodos(p);


                    //Recorremos la lista de las dimensiones
                    for (Relaciondimensionesasignaturasano rdaa : datalistDimensionesTmp) {

                        DimensionesEstudiantes dimensionesEstudiantesTmp = new DimensionesEstudiantes();
                        dimensionesEstudiantesTmp.setDimensiones(rdaa.getDimensiones());

                        dataListDimensionesEstudiantestmp.add(dimensionesEstudiantesTmp);

                        List<NotasDimensionesEstudiantes> dataListNotasDimensionesEstudiantes = new ArrayList<NotasDimensionesEstudiantes>();
                        dimensionesEstudiantesTmp.setDataListNotasDimensionesEstudiantes(dataListNotasDimensionesEstudiantes);

                        //Sacamos las notas que tiene que cada dimesion
                        List<Relacionnotasdimension> tmp = relacionnotasdimensionFacade.findByLikeAll("SELECT R FROM Relacionnotasdimension R WHERE  R.relaciondimensionesasignaturasano.idrelaciondimensionesasignaturasano = " + rdaa.getIdrelaciondimensionesasignaturasano() + " AND R.periodos.idperiodos = " + p.getIdperiodos());
                        double promedioDimension = 0;

                        //Validamos si no hay notas asociadas a esta dimensi��n
                        if (tmp.isEmpty()) {
                            dimensionesEstudiantesTmp.setValor(0);
                            promedioDimension = 0;
                        }

                        for (Relacionnotasdimension rnd : tmp) {
                            NotasDimensionesEstudiantes notasDimensionesEstudiantes = new NotasDimensionesEstudiantes();
                            notasDimensionesEstudiantes.setRelacionnotasdimension(rnd);
                            dataListNotasDimensionesEstudiantes.add(notasDimensionesEstudiantes);

                            List<ActividadesNotasEstudiantes> dataListActividadesNotasEstudiantes = new ArrayList<ActividadesNotasEstudiantes>();
                            notasDimensionesEstudiantes.setDataListActividadesNotasEstudiantes(dataListActividadesNotasEstudiantes);


                            double promedioActividades = 0;
                            List<Relacionnotaslogrosdimensionboletin> tmpRNLDB = relacionnotaslogrosdimensionboletinFacade.findByLikeAll("SELECT R FROM Relacionnotaslogrosdimensionboletin R WHERE R.relacionnotasdimension.idrelacionnotasdimesion = " + rnd.getIdrelacionnotasdimesion());
                            //Recorremos las actividades de las notas
                            for (Relacionnotaslogrosdimensionboletin rnld : tmpRNLDB) {


                                ActividadesNotasEstudiantes actividadesNotasEstudiantes = new ActividadesNotasEstudiantes();
                                dataListActividadesNotasEstudiantes.add(actividadesNotasEstudiantes);

                                List<Notascalificables> tmpNC = notascalificablesFacade.findByLikeAll("SELECT N FROM Notascalificables N WHERE N.registromatriculas.idregistromatriculas = " + rm.getIdregistromatriculas() + " AND " + " N.relacionnotaslogrosdimensionboletin.idrelacionnotaslogrosdimensionboletin =  " + rnld.getIdrelacionnotaslogrosdimensionboletin());

                                if (!tmpNC.isEmpty()) {
                                    actividadesNotasEstudiantes.setNotascalificables(tmpNC.get(0));
                                    actividadesNotasEstudiantes.setValor(tmpNC.get(0).getValor());
                                    promedioActividades += tmpNC.get(0).getValor();
                                } else {
                                    Notascalificables notasCalificablesTmp = new Notascalificables();
                                    notasCalificablesTmp.setRelacionnotaslogrosdimensionboletin(rnld);
                                    actividadesNotasEstudiantes.setNotascalificables(notasCalificablesTmp);
                                    actividadesNotasEstudiantes.setValor(0);
                                }
                            }

                            //Validamos que el tama��o de la lista no est�� vac��a
                            if (tmpRNLDB != null && tmpRNLDB.isEmpty()) {
                                promedioActividades = 0;
                            } else {
								promedioActividades = new BigDecimal(promedioActividades / tmpRNLDB.size()).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
                            }
                            notasDimensionesEstudiantes.setValor(promedioActividades);
                            promedioDimension = promedioDimension + new BigDecimal((promedioActividades * rnd.getPorcentaje() / 100)).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
                            dimensionesEstudiantesTmp.setValor(promedioDimension);

                        }


                        if (rdaa.getPorcentaje() != null) {
                            promedioDimension = new BigDecimal(promedioDimension * rdaa.getPorcentaje() / 100).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
                            promedioPeriodos += promedioDimension;
                            periodosEstudiantes.setValor(promedioPeriodos);
                        } else {
                            periodosEstudiantes.setValor(0);
                        }
                    }
                }
            }
        }
        return dataListRegistroMatriculasEstudiantes;
    }

    //##VALOR DEL PERIODO
    //Valor de un periodo normal
    public double getValorPeriodo() {
        if (periodoSeleccionado == null || dataListRegistroMatriculasRecuperar == null || dataListRegistroMatriculasRecuperar.isEmpty() || periodoSeleccionado == null) {
            return 0;
        }


        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIData table = (UIData) root.findComponent("formPrincipal").
                findComponent("tablaEstudiantes");
        RegistroMatriculasEstudiantes registroMatriculasEstudiantes = (RegistroMatriculasEstudiantes) table.getRowData();
        if (registroMatriculasEstudiantes.getDataListRegistroMatriculasEstudiantes() != null && !registroMatriculasEstudiantes.getDataListRegistroMatriculasEstudiantes().isEmpty()) {
            Double valor = new Double(registroMatriculasEstudiantes.getPeriodosEstudiantes().getValor());
            for (Relacionrecuperacionregistromatriculas r : registroMatriculasEstudiantes.getDataListRegistroMatriculasEstudiantes()) {
                if (valor == 0) {
                    valor = r.getValor();
                } else {

                    if (r.getIdrelacionrecuperacionregistromatriculas() != null && r.getValor() != null && r.getValor() > valor) {
                        valor = new BigDecimal(((valor + r.getValor()) / 2)).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
                    }
                }
            }
            return new BigDecimal(valor).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return registroMatriculasEstudiantes.getPeriodosEstudiantes().getValor();
    }
    
    //Valor del periodo final
    public int valorPeriodoFinal(){
    	if (periodoSeleccionado == null || dataListDefinitivas == null || dataListDefinitivas.isEmpty()) {
            return 0;
        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIData table = (UIData) root.findComponent("formPrincipal").
                findComponent("tablaEstudiantesPeriodoFinal");
        Definitivas definitivas= (Definitivas) table.getRowData();
        int definitiva = definitivas.getDefinitiva();
        if(definitiva < 65){
	        for (Recuperaciones r1 : getDataListRecuperaciones()) {
	        	List<Relacionrecuperacionregistromatriculas> rcreg = 
	        			relacionrecuperacionregistromatriculasFacade.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = "
						+ r1.getIdrecuperaciones() + " AND R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos()
						+ " AND R.registromatriculas.idregistromatriculas = "+ definitivas.getRegistromatriculas().getIdregistromatriculas()
						+ " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = "+ asignaturaSeleccionada.getIdrelacionasignaturaperiodos());
	        	if(rcreg != null && !rcreg.isEmpty()){
	        		int definitivaComp = new BigDecimal((definitiva + rcreg.get(0).getValor()) / 2).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
	        		if(definitivaComp > definitiva && definitiva < 65){
	        			definitiva = new BigDecimal((definitiva + rcreg.get(0).getValor()) / 2).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
	        		}
				}
	        }
        }
        
        return definitiva;
        
    }

    //##NOTAS DE LAS RECUPERACIONES
    //Nota de las recuperaciones de la definitiva
    public int getValorNotaRecuperacionesFinales(){
    	if (periodoSeleccionado == null || dataListDefinitivas == null || dataListDefinitivas.isEmpty() || periodoSeleccionado == null) {
            return 0;
        }
    	FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIInput input = (UIInput) root.findComponent("formPrincipal").
                findComponent("tablaEstudiantesPeriodoFinal").findComponent("tablaRecuperacionesFinales").findComponent("notaRecuperacionesFinales");
        
        Definitivas definitiva = (Definitivas) input.findComponent("estudiante").getAttributes().get("value");
        
        //Sacamos la recuperaci�n que estamos editando
        Recuperaciones recuperacionesTmp = (Recuperaciones) input.findComponent("recuperacionesFinales").getAttributes().get("value");
    	
        if (recuperacionesTmp == null) {
            return 0;
        }
        
        List<Relacionrecuperacionregistromatriculas> tmpList = relacionrecuperacionregistromatriculasFacade.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = " + recuperacionesTmp.getIdrecuperaciones() + " AND R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos() + " AND R.registromatriculas.idregistromatriculas = " + definitiva.getRegistromatriculas().getIdregistromatriculas() + " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = " + asignaturaSeleccionada.getIdrelacionasignaturaperiodos());
        
        if(tmpList.isEmpty()){
        	return 0;
        }else{
        	return tmpList.get(0).getValor().intValue();
        }
    }
    
    
    public void setValorNotaRecuperacionesFinales(int valorNotaRecuperaciones) {

        if (valorNotaRecuperaciones > -1 && valorNotaRecuperaciones < 101) {
        	System.out.print(valorNotaRecuperaciones + " VALOR ");
            FacesContext facesContext = FacesContext.getCurrentInstance();
            UIViewRoot root = facesContext.getViewRoot();
            UIInput input = (UIInput) root.findComponent("formPrincipal").
                    findComponent("tablaEstudiantesPeriodoFinal").findComponent("tablaRecuperacionesFinales").findComponent("notaRecuperacionesFinales");

            //Sacamos la definitiva
            Definitivas definitiva = (Definitivas) input.findComponent("estudiante").getAttributes().get("value");

            //Sacamos la recuperaci�n que estamos editando
            Recuperaciones recuperacionesTmp = (Recuperaciones) input.findComponent("recuperacionesFinales").getAttributes().get("value");

//            System.out.print(definitiva.getDefinitiva() + " deg ");
            
            
            List<Relacionrecuperacionregistromatriculas> tmpList = relacionrecuperacionregistromatriculasFacade.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = " + recuperacionesTmp.getIdrecuperaciones() + " AND R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos() + " AND R.registromatriculas.idregistromatriculas = " + definitiva.getRegistromatriculas().getIdregistromatriculas() + " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = " + asignaturaSeleccionada.getIdrelacionasignaturaperiodos());
            
            if(tmpList.isEmpty()){
				Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculasTmp = new Relacionrecuperacionregistromatriculas(new Long(0));
				relacionrecuperacionregistromatriculasTmp.setPeriodos(periodoSeleccionado);
				relacionrecuperacionregistromatriculasTmp.setRecuperaciones(recuperacionesTmp);
				relacionrecuperacionregistromatriculasTmp.setRegistromatriculas(definitiva.getRegistromatriculas());
				relacionrecuperacionregistromatriculasTmp.setValor(new Double(valorNotaRecuperaciones));
				relacionrecuperacionregistromatriculasTmp.setRelacionasignaturaperiodos(asignaturaSeleccionada);
				relacionrecuperacionregistromatriculasFacade.create(relacionrecuperacionregistromatriculasTmp);
            }else{
            	relacionrecuperacionregistromatriculasFacade.metodo("UPDATE Relacionrecuperacionregistromatriculas R SET R.valor = " + valorNotaRecuperaciones + " WHERE R.recuperaciones.idrecuperaciones = " + recuperacionesTmp.getIdrecuperaciones() + " AND R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos() + " AND R.registromatriculas.idregistromatriculas = " + definitiva.getRegistromatriculas().getIdregistromatriculas() + " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = " + asignaturaSeleccionada.getIdrelacionasignaturaperiodos());
            }
            
//            //Esto se hace para que se pueda ver la nota final de una
//            for (Recuperaciones r : getDataListRecuperaciones()) {
//                if (r.getIdrelacionrecuperacionregistromatriculas() != null && r.getRecuperaciones().getIdrecuperaciones() == recuperacionesTmp.getIdrecuperaciones()) {
//                    if (getValorPeriodo() < 80) {
//                        if (tmpList.isEmpty()) {
//                            Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculasTmp = new Relacionrecuperacionregistromatriculas(new Long(0));
//                            relacionrecuperacionregistromatriculasTmp.setPeriodos(periodoSeleccionado);
//                            relacionrecuperacionregistromatriculasTmp.setRecuperaciones(recuperacionesTmp);
//                            relacionrecuperacionregistromatriculasTmp.setRegistromatriculas(registroMatriculasEstudiantesTmp.getRegistromatriculas());
//                            relacionrecuperacionregistromatriculasTmp.setValor(valorNotaRecuperaciones);
//                            relacionrecuperacionregistromatriculasTmp.setRelacionasignaturaperiodos(asignaturaSeleccionada);
//                            relacionrecuperacionregistromatriculasFacade.create(relacionrecuperacionregistromatriculasTmp);
//                        } else {
//                            relacionrecuperacionregistromatriculasFacade.metodo("UPDATE Relacionrecuperacionregistromatriculas R SET R.valor = " + valorNotaRecuperaciones + " WHERE R.recuperaciones.idrecuperaciones = " + recuperacionesTmp.getIdrecuperaciones() + " AND R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos() + " AND R.registromatriculas.idregistromatriculas = " + registroMatriculasEstudiantesTmp.getRegistromatriculas().getIdregistromatriculas() + " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = " + asignaturaSeleccionada.getIdrelacionasignaturaperiodos());
//                        }
//
//                        if (getValorPeriodo() >= 80) {
//                            relacionrecuperacionregistromatriculasFacade.metodo("UPDATE Relacionrecuperacionregistromatriculas R SET R.valor = " + 0 + " WHERE R.recuperaciones.numero > " + recuperacionesTmp.getNumero() + " AND R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos() + " AND R.registromatriculas.idregistromatriculas = " + registroMatriculasEstudiantesTmp.getRegistromatriculas().getIdregistromatriculas() + " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = " + asignaturaSeleccionada.getIdrelacionasignaturaperiodos());
//                            for (Relacionrecuperacionregistromatriculas rr : registroMatriculasEstudiantesTmp.getDataListRegistroMatriculasEstudiantes()) {
//                                if (rr.getRecuperaciones().getNumero() > r.getRecuperaciones().getNumero()) {
//                                    rr.setValor(0.0);
//                                }
//                            }
//                        }
//                    }
//                    break;
//                }
//            }
        }
    }

    //Nota de las recuperaciones de un periodo normal
    public double getValorNotaRecuperaciones() {
        if (periodoSeleccionado == null || dataListRegistroMatriculasRecuperar == null || dataListRegistroMatriculasRecuperar.isEmpty() || periodoSeleccionado == null) {
            return 0;
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIInput input = (UIInput) root.findComponent("formPrincipal").
                findComponent("tablaEstudiantes").findComponent("tablaRecuperaciones").findComponent("notaRecuperaciones");

        RegistroMatriculasEstudiantes registroMatriculasEstudiantesTmp = (RegistroMatriculasEstudiantes) input.findComponent("estudiante").getAttributes().get("value");

        Recuperaciones recuperacionesTmp = (Recuperaciones) input.findComponent("recuperaciones").getAttributes().get("value");

        if (recuperacionesTmp == null) {
            return 0;
        }

        for (Relacionrecuperacionregistromatriculas r : registroMatriculasEstudiantesTmp.getDataListRegistroMatriculasEstudiantes()) {
            if (r.getIdrelacionrecuperacionregistromatriculas() != null && r.getRecuperaciones().getIdrecuperaciones() == recuperacionesTmp.getIdrecuperaciones()) {
                return r.getValor();
            }
        }


        if (relacionrecuperacionregistromatriculasFacade.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = " + recuperacionesTmp.getIdrecuperaciones() + " AND R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos() + " AND R.registromatriculas.idregistromatriculas = " + registroMatriculasEstudiantesTmp.getRegistromatriculas().getIdregistromatriculas() + " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = " + asignaturaSeleccionada.getIdrelacionasignaturaperiodos()).isEmpty()) {
            return 0;
        } else {
            return relacionrecuperacionregistromatriculasFacade.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = " + recuperacionesTmp.getIdrecuperaciones() + " AND R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos() + " AND R.registromatriculas.idregistromatriculas = " + registroMatriculasEstudiantesTmp.getRegistromatriculas().getIdregistromatriculas() + " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = " + asignaturaSeleccionada.getIdrelacionasignaturaperiodos()).get(0).getValor();
        }
    }

    public void setValorNotaRecuperaciones(double valorNotaRecuperaciones) {

        if (valorNotaRecuperaciones > -1 && valorNotaRecuperaciones < 101) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            UIViewRoot root = facesContext.getViewRoot();
            UIInput input = (UIInput) root.findComponent("formPrincipal").
                    findComponent("tablaEstudiantes").findComponent("tablaRecuperaciones").findComponent("notaRecuperaciones");

            RegistroMatriculasEstudiantes registroMatriculasEstudiantesTmp = (RegistroMatriculasEstudiantes) input.findComponent("estudiante").getAttributes().get("value");


            //Sacamos la recuperaci�n que estamos editando
            Recuperaciones recuperacionesTmp = (Recuperaciones) input.findComponent("recuperaciones").getAttributes().get("value");

            List<Relacionrecuperacionregistromatriculas> tmpList = relacionrecuperacionregistromatriculasFacade.findByLike("SELECT R FROM  Relacionrecuperacionregistromatriculas R WHERE R.recuperaciones.idrecuperaciones = " + recuperacionesTmp.getIdrecuperaciones() + " AND R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos() + " AND R.registromatriculas.idregistromatriculas = " + registroMatriculasEstudiantesTmp.getRegistromatriculas().getIdregistromatriculas() + " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = " + asignaturaSeleccionada.getIdrelacionasignaturaperiodos());




            for (Relacionrecuperacionregistromatriculas rr : registroMatriculasEstudiantesTmp.getDataListRegistroMatriculasEstudiantes()) {
                if (rr.getRecuperaciones().getNumero() > recuperacionesTmp.getNumero()) {
                    rr.setValor(0.0);
                }
            }

            //Esto se hace para que se pueda ver la nota final de una
            for (Relacionrecuperacionregistromatriculas r : registroMatriculasEstudiantesTmp.getDataListRegistroMatriculasEstudiantes()) {
                if (r.getIdrelacionrecuperacionregistromatriculas() != null && r.getRecuperaciones().getIdrecuperaciones() == recuperacionesTmp.getIdrecuperaciones()) {
                    r.setValor(0.0);

                    if (getValorPeriodo() < 80) {
                        r.setValor(valorNotaRecuperaciones);

                        if (tmpList.isEmpty()) {
                            Relacionrecuperacionregistromatriculas relacionrecuperacionregistromatriculasTmp = new Relacionrecuperacionregistromatriculas(new Long(0));
                            relacionrecuperacionregistromatriculasTmp.setPeriodos(periodoSeleccionado);
                            relacionrecuperacionregistromatriculasTmp.setRecuperaciones(recuperacionesTmp);
                            relacionrecuperacionregistromatriculasTmp.setRegistromatriculas(registroMatriculasEstudiantesTmp.getRegistromatriculas());
                            relacionrecuperacionregistromatriculasTmp.setValor(valorNotaRecuperaciones);
                            relacionrecuperacionregistromatriculasTmp.setRelacionasignaturaperiodos(asignaturaSeleccionada);
                            relacionrecuperacionregistromatriculasFacade.create(relacionrecuperacionregistromatriculasTmp);
                        } else {
                            relacionrecuperacionregistromatriculasFacade.metodo("UPDATE Relacionrecuperacionregistromatriculas R SET R.valor = " + valorNotaRecuperaciones + " WHERE R.recuperaciones.idrecuperaciones = " + recuperacionesTmp.getIdrecuperaciones() + " AND R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos() + " AND R.registromatriculas.idregistromatriculas = " + registroMatriculasEstudiantesTmp.getRegistromatriculas().getIdregistromatriculas() + " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = " + asignaturaSeleccionada.getIdrelacionasignaturaperiodos());
                        }

                        if (getValorPeriodo() >= 80) {
                            relacionrecuperacionregistromatriculasFacade.metodo("UPDATE Relacionrecuperacionregistromatriculas R SET R.valor = " + 0 + " WHERE R.recuperaciones.numero > " + recuperacionesTmp.getNumero() + " AND R.periodos.idperiodos = " + periodoSeleccionado.getIdperiodos() + " AND R.registromatriculas.idregistromatriculas = " + registroMatriculasEstudiantesTmp.getRegistromatriculas().getIdregistromatriculas() + " AND R.relacionasignaturaperiodos.idrelacionasignaturaperiodos = " + asignaturaSeleccionada.getIdrelacionasignaturaperiodos());
                            for (Relacionrecuperacionregistromatriculas rr : registroMatriculasEstudiantesTmp.getDataListRegistroMatriculasEstudiantes()) {
                                if (rr.getRecuperaciones().getNumero() > r.getRecuperaciones().getNumero()) {
                                    rr.setValor(0.0);
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    public List<NotasEstudiantes.PeriodosEstudiantes> getDataListPeriodosEstudiantesHeader() {
        return dataListPeriodosEstudiantesHeader;
    }

    public void setDataListPeriodosEstudiantesHeader(List<NotasEstudiantes.PeriodosEstudiantes> dataListPeriodosEstudiantesHeader) {
        this.dataListPeriodosEstudiantesHeader = dataListPeriodosEstudiantesHeader;
    }

    public void setDataListRegistroMatriculasEstudiantes(List<RegistroMatriculasEstudiantes> dataListRegistroMatriculasEstudiantes) {
        this.dataListRegistroMatriculasEstudiantes = dataListRegistroMatriculasEstudiantes;
    }

    public void valorRecuperacion(Recuperaciones recuperaciones, RegistroMatriculasEstudiantes registroMatriculasEstudiantes) {
    }
}
