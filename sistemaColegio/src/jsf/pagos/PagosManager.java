/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.pagos;

import ejb.AbonosegresosFacade;
import ejb.AbonospagosFacade;
import ejb.DeudasusuariosFacade;
import ejb.PagosFacade;
import ejb.RelacionpagousuariosFacade;
import ejb.TipopagosFacade;
import ejb.UsuariosFacade;
import entities.Abonosegresos;
import entities.Abonospagos;
import entities.Deudasusuarios;
import entities.Pagos;
import entities.Relacionpagousuarios;
import entities.Tipopagos;
import entities.Usuarios;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author juannoguera
 */
@ManagedBean
@ViewScoped
public class PagosManager implements Serializable {

    @EJB
    private UsuariosFacade usuariosFacade;
    @EJB
    private TipopagosFacade tipopagosFacade;
    @EJB
    private PagosFacade pagosFacade;
    @EJB
    private RelacionpagousuariosFacade relacionpagousuariosFacade;
    @EJB
    private AbonospagosFacade abonospagosFacade;
    @EJB
    private AbonosegresosFacade abonosegresosFacade;
    @EJB
    private DeudasusuariosFacade deudasusuariosFacade;
    //###INTERFAZ PAGOS
//    //Lista de los usuarios que les voy a agregar el pago
    List<Relacionpagousuarios> dataListUsuariosBusqueda;
    //Query usado para mostrar los usuarios
    private String query = "";
    //Variable para si vuelve o no a recargar la lista
    private boolean busqueda = true;
    //Variable de los pagos
    private Pagos pagoSeleccionado;
    //##INTERFAZ TIPOS_PAGOS
    //Variable de los tipos de pagos
    private Tipopagos tipoPagosSeleccionado;
    //##INFORMES DE LOS PAGOS
    @Resource
    private UserTransaction userTransaction;
    //

    /**
     * Creates a new instance of PagosManager
     */
    public PagosManager() {
    }

    //Lista de los usuarios que encontré con la búsqueda
    public List<Relacionpagousuarios> getDataListUsuariosBusqueda() {
        if (pagoSeleccionado == null || pagoSeleccionado.getIdpagos() == 0) {
            return null;
        }




        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIInput inputQuery = (UIInput) root.findComponent("formPagos").findComponent("busquedaUsuariosPagos");


        if (inputQuery.getValue() != null) {
//            if (query.equals(inputQuery.getValue().toString())) {
//                if (dataListUsuariosBusqueda != null && !dataListUsuariosBusqueda.isEmpty()) {
//                    return dataListUsuariosBusqueda;
//                }
//            }
            query = inputQuery.getValue().toString();
        }else{
            return relacionpagousuariosFacade.findByLikeAll("SELECT R FROM Relacionpagousuarios R WHERE R.pagos.idpagos =  " + pagoSeleccionado.getIdpagos() + " ORDER BY R.usuarios.apellidos");
        }




        dataListUsuariosBusqueda = new ArrayList<Relacionpagousuarios>();
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

                        queryPrueba = "SELECT R FROM Relacionpagousuarios R WHERE (LOWER(R.usuarios.nombres) LIKE  '%" + valores[i].toLowerCase() + "%' OR LOWER(R.usuarios.apellidos) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(R.usuarios.numeroidentificacion) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(R.usuarios.nombredeusuario) LIKE '%" + valores[i].toLowerCase() + "%')";
                        h = true;
                    }
                    if (!b && h) {
                        queryPrueba += " AND (LOWER(R.usuarios.nombres) LIKE  '%" + valores[i].toLowerCase() + "%' OR LOWER(R.usuarios.apellidos) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(R.usuarios.numeroidentificacion) LIKE '%" + valores[i].toLowerCase() + "%' OR LOWER(R.usuarios.nombredeusuario) LIKE '%" + valores[i].toLowerCase() + "%')";
                    }
                }
                if (h) {
                    queryPrueba += " AND R.pagos.idpagos  = " + pagoSeleccionado.getIdpagos() + " ORDER BY  R.usuarios.apellidos";
                    dataListUsuariosBusqueda = relacionpagousuariosFacade.findByLikeAll(queryPrueba);
                }
            } catch (Exception e) {
                System.out.print("Error buscando los usuarios");
            }
            return dataListUsuariosBusqueda;
        } else {
            return relacionpagousuariosFacade.findByLikeAll("SELECT R FROM Relacionpagousuarios R WHERE R.pagos.idpagos =  " + pagoSeleccionado.getIdpagos() + " ORDER BY R.usuarios.apellidos");
        }
    }

    //Lista de los usuarios que encontré con la búsqueda
    public List<Usuarios> getDataListUsuariosNoBusqueda() {

        if (pagoSeleccionado == null || pagoSeleccionado.getIdpagos() == 0) {
            return null;
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIInput inputQuery = (UIInput) root.findComponent("formPagos").findComponent("busquedaUsuariosPagos");

        String query = "";

        if (inputQuery.getValue() != null) {
            query = inputQuery.getValue().toString();
        }

        List<Usuarios> dataListUsuariosBusqueda = new ArrayList<Usuarios>();
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
                    queryPrueba += " AND U.idusuarios not in (SELECT R.usuarios.idusuarios FROM Relacionpagousuarios R WHERE R.usuarios.idusuarios = U.idusuarios AND R.pagos.idpagos = " + pagoSeleccionado.getIdpagos() + ") ORDER BY  U.apellidos";
                    dataListUsuariosBusqueda = usuariosFacade.findByLikeAll(queryPrueba);
                }
            } catch (Exception e) {
                System.out.print("Error buscando los usuarios");
            }
            return dataListUsuariosBusqueda;
        }
        return dataListUsuariosBusqueda;
    }

    //Método para colocar usuarios en los pagos
    public void agregarUsuario(Usuarios usuarios) {
        Relacionpagousuarios relacionpagousuarios = new Relacionpagousuarios(new Long(0));
        relacionpagousuarios.setPagos(pagoSeleccionado);
        relacionpagousuarios.setUsuarios(usuarios);
        relacionpagousuarios.setValorreal(pagoSeleccionado.getValor());
        relacionpagousuarios.setSaldo(pagoSeleccionado.getValor());
        try {
            relacionpagousuariosFacade.create(relacionpagousuarios);
            query = "";
        } catch (Exception e) {
            System.out.print("Error " + e.getMessage());
        }
    }

    //Método para eliminar un usuario de un pago
    public void eliminarUsuario(Relacionpagousuarios relacionpagousuarios) {
        try {
            relacionpagousuariosFacade.remove(relacionpagousuarios);
            query = "";
        } catch (Exception e) {
            System.out.print("Error " + e.getMessage());
        }
    }

    //Método para saber si ha escogido algún tipo de usuario en especial
    public List<String> getDataListTiposUsuarios() {
        if (pagoSeleccionado == null || pagoSeleccionado.getIdpagos() == 0) {
            return null;
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIInput inputQuery = (UIInput) root.findComponent("formPagos").findComponent("busquedaUsuariosPagos");

        String query = "";

        if (inputQuery.getValue() != null) {
            query = inputQuery.getValue().toString();
        }

        List<Usuarios> dataListUsuariosBusqueda = new ArrayList<Usuarios>();
        if (query.trim().replaceAll(" ", "").length() > 0) {
            try {
                List<String> dataListUsuarios = new ArrayList<String>();
                String[] valores;
                valores = query.trim().toString().split(" ");

                boolean estudiantes = true;
                boolean profesores = true;
                boolean otros = true;
                boolean todos = true;

                for (int i = 0; i < valores.length; i++) {
                    if (valores[i].toLowerCase().equals("estudiantes") && estudiantes) {
                        dataListUsuarios.add("Estudiantes");
                        estudiantes = false;
                    }
                    if (valores[i].toLowerCase().equals("profesores") && profesores) {
                        dataListUsuarios.add("Profesores");
                        profesores = false;
                    }
                    if (valores[i].toLowerCase().equals("otros") && otros) {
                        dataListUsuarios.add("Otros");
                        otros = false;
                    }
                    if (valores[i].toLowerCase().equals("todos") && todos) {
                        dataListUsuarios.add("Todos");
                        todos = false;
                    }
                }
                return dataListUsuarios;
            } catch (Exception e) {
                System.out.print("Error buscando los usuarios");
            }
        }
        return null;
    }

    //Método para agregar un grupo
    public void agregarGrupo(String grupo) {
        if (grupo.toLowerCase().equals("estudiantes")) {
            for (Usuarios u : usuariosFacade.findByLikeAll("SELECT R.estudiantes.usuarios FROM Registromatriculas R WHERE R.anosacademicos.idanosacademicos "
                    + " in (SELECT A.idanosacademicos FROM Anosacademicos A WHERE A.estadoactivo = true) AND  R.estudiantes.usuarios.idusuarios not in "
                    + " (SELECT P.usuarios.idusuarios FROM Relacionpagousuarios P WHERE P.pagos.idpagos = " + pagoSeleccionado.getIdpagos() + ")")) {
                Relacionpagousuarios relacionpagousuarios = new Relacionpagousuarios(new Long(0));
                relacionpagousuarios.setPagos(pagoSeleccionado);
                relacionpagousuarios.setValorreal(pagoSeleccionado.getValor());
                relacionpagousuarios.setUsuarios(u);
                relacionpagousuarios.setSaldo(pagoSeleccionado.getValor());
                relacionpagousuariosFacade.create(relacionpagousuarios);
            }
        } else {
            if (grupo.toLowerCase().equals("profesores")) {
            } else {
                if (grupo.toLowerCase().equals("otros")) {
                } else {
                    if (grupo.toLowerCase().equals("todos")) {
                    }
                }
            }
        }
    }

    //Método para el valor real
    public double getValorReal() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIData tabla = (UIData) root.findComponent("formPagos").findComponent("tablaUsuariosBusquedaConPagos");

        return ((Relacionpagousuarios) tabla.getRowData()).getValorreal();

    }

    //Método para colocar el valor real
    public void setValorReal(double valorReal) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIData tabla = (UIData) root.findComponent("formPagos").findComponent("tablaUsuariosBusquedaConPagos");

        ((Relacionpagousuarios) tabla.getRowData()).setValorreal(valorReal);
        ((Relacionpagousuarios) tabla.getRowData()).setSaldo(valorReal);

        try {
            relacionpagousuariosFacade.edit(((Relacionpagousuarios) tabla.getRowData()));
            relacionpagousuariosFacade.edit(((Relacionpagousuarios) tabla.getRowData()));
        } catch (Exception e) {
            System.out.print("No se pudo actualizar el valor real del pago del usuario");
        }
    }

    //Método para guardar los pagos de un usuario
    public double getPagoAbono() {
        return 0;
    }

    public void setPagoAbono(double pagoAbono) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIInput inputQuery = (UIInput) root.findComponent("formPagos").findComponent("tablaUsuariosBusquedaConPagos").findComponent("valorAbonoPago");
        UIData fila = (UIData) root.findComponent("formPagos").findComponent("tablaUsuariosBusquedaConPagos");

//        ((Relacionpagousuarios) fila.getRowData()).setIdrelacionpagousuarios(Math.abs(((Relacionpagousuarios) fila.getRowData()).getIdrelacionpagousuarios()));
        if (((Relacionpagousuarios) fila.getRowData()).getSaldo() - pagoAbono >= 0) {
            Abonospagos abonospagos = new Abonospagos(new Long(0));
            abonospagos.setFechaabono(new Date());
            abonospagos.setRelacionpagousuarios((Relacionpagousuarios) fila.getRowData());
            abonospagos.setValor(pagoAbono);
            abonospagosFacade.create(abonospagos);

            ((Relacionpagousuarios) fila.getRowData()).setSaldo(((Relacionpagousuarios) fila.getRowData()).getSaldo() - pagoAbono);
            relacionpagousuariosFacade.edit(((Relacionpagousuarios) fila.getRowData()));


        } else {
            System.out.print("No se puede meter más de lo que hace falta");
        }
    }
    
    //Método para eliminar un abono
    public void eliminarIngreso(Abonospagos abonospagos){
        
        abonospagosFacade.remove(abonospagos);
        
        abonospagos.getRelacionpagousuarios().setSaldo(abonospagos.getRelacionpagousuarios().getSaldo() + abonospagos.getValor());
        
        relacionpagousuariosFacade.edit(abonospagos.getRelacionpagousuarios());
        
//        relacionpagousuarios = ;
        
    }
    
    //Método para eliminar un pago a un egreso
    public void eliminarAbonoEgreso(Abonosegresos abonosegresos){
        abonosegresosFacade.remove(abonosegresos);
    }

    //Método para saber cuánto debe el usuario
    public boolean saldoActivo(Relacionpagousuarios relacionpagousuarios) {
        if (relacionpagousuarios.getSaldo() > relacionpagousuarios.getValorreal()) {
            return true;
        }

        return false;
    }
//
    //Método para buscar los pagos que hay pendientes por hacer o por cobrar

    public List<Pagos> getDataListBusquedaPagos() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIInput inputQuery = (UIInput) root.findComponent("formPagos").findComponent("inputTextBuscar");
        String query = "";
        if (inputQuery.getValue() != null) {
            query = inputQuery.getValue().toString();
        }

        List<Pagos> dataListPagos = new ArrayList<Pagos>();

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

                    if (valores[i].trim().toLowerCase().equals("ingreso")) {
                        valores[i] = "0";
                    }

                    if (valores[i].trim().toLowerCase().equals("egreso")) {
                        valores[i] = "1";
                    }

                    if (!b && !h) {
                        queryPrueba = "SELECT U FROM Pagos U WHERE (LOWER(U.nombre) LIKE  '%" + valores[i].toLowerCase() + "%'  OR CONCAT(U.tipopagos.tipo, '') = '" + valores[i].toLowerCase() + "')";
                        h = true;
                    }
                    if (!b && h) {
                        queryPrueba += " AND (LOWER(U.nombre) LIKE  '%" + valores[i].toLowerCase() + "%'  OR CONCAT(U.tipopagos.tipo, '') = '" + valores[i].toLowerCase() + "')";
                    }
                }
                if (h) {
                    queryPrueba += " ORDER BY U.nombre";
                    dataListPagos = pagosFacade.findByLike(queryPrueba);
                }
            } catch (Exception e) {
                System.out.print("Error buscando los tipos");
            }
            return dataListPagos;
        } else {
            dataListPagos = pagosFacade.findByLike("SELECT P FROM Pagos P ORDER BY P.nombre");
        }
        return dataListPagos;
    }

    //Método para poder agregsr un pago nuevo
    public void agregarNuevoPago() {
        pagoSeleccionado = new Pagos(new Long(0));
        pagoSeleccionado.setTipopagos(new Tipopagos(new Long(0)));
    }

    //Método para seleccionar un pago y editarle algo
    public void seleccionarPago(Pagos pagos) {
        if (pagos.getTipopagos() == null) {
            pagos.setTipopagos(new Tipopagos(new Long(0)));
        }
        
        //Colocamos esta variable en nulo para que no muestre pagos de ningún usuario
        relacionpagousuarios = null;
        
        this.pagoSeleccionado = pagos;
    }

    //Métodos del pago seleccionado
    public Pagos getPagoSeleccionado() {
        return pagoSeleccionado;
    }

    public void setPagoSeleccionado(Pagos pagoSeleccionado) {
        this.pagoSeleccionado = pagoSeleccionado;
    }

    //Método para saber de qué tipo es el pago
    public long getTipoPago() {
        if (pagoSeleccionado.getTipopagos() == null) {
            return 0;
        }
        return pagoSeleccionado.getTipopagos().getIdtipopagos();
    }

    //Método para colocar el tipo de pago
    public void setTipoPago(long idTipopagos) {
        pagoSeleccionado.setTipopagos(new Tipopagos(new Long(idTipopagos)));
    }

    //Método par guardar el pago
    public void guardarPagos() {
        if (pagoSeleccionado != null) {
            if (pagoSeleccionado.getIdpagos() == 0) {
                pagosFacade.create(pagoSeleccionado);
            }
            if (pagoSeleccionado.getIdpagos() != 0) {
                pagosFacade.edit(pagoSeleccionado);
            }
        } else {
        }

    }
    private double totalPagoEgreso = 0;
    //Método para saber el saldo de lo que se debe

    public double getSaldoEgreso() {
        totalPagoEgreso = (Double) abonosegresosFacade.retornarValorObject("SELECT SUM(P.valor) FROM Abonosegresos P WHERE P.pagos.idpagos = " + pagoSeleccionado.getIdpagos());
        return totalPagoEgreso;
    }

    //Método para pagar un egreso
    public void pagarEgresos() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            UIViewRoot root = facesContext.getViewRoot();
            UIInput inputQuery = (UIInput) root.findComponent("formPagos").findComponent("valorAbonoEgreso");
            Abonosegresos abonosegresos = new Abonosegresos(new Long(0));
            abonosegresos.setPagos(pagoSeleccionado);
            abonosegresos.setValor(Double.parseDouble(inputQuery.getValue().toString()));
            abonosegresos.setFecha(new Date());

            if ((Double) abonosegresosFacade.retornarValorObject("SELECT SUM(P.valor) FROM Abonosegresos P WHERE P.pagos.idpagos = " + pagoSeleccionado.getIdpagos()) + abonosegresos.getValor() <= pagoSeleccionado.getValor()) {
                abonosegresosFacade.create(abonosegresos);
            } else {
                System.out.print("No se puede meter más de lo que vale");
            }
            
            inputQuery.setValue(0);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
    private Relacionpagousuarios relacionpagousuarios;

    //Método para ver los pagos de un usuario
    public void seleccionarUsuario(Relacionpagousuarios relacionpagousuarios) {
        this.relacionpagousuarios = relacionpagousuarios;
    }

    //Función para mostrar la lista de abonos_ingresos que ha hecho un usuario
    public List<Abonospagos> getDatListAbonosIngresos() {
        if (relacionpagousuarios == null) {
            return null;
        }
        return abonospagosFacade.findByLikeAll("SELECT A FROM Abonospagos A WHERE A.relacionpagousuarios.idrelacionpagousuarios = " + relacionpagousuarios.getIdrelacionpagousuarios());
    }

    //Función para mostrar la lista de abonos_ingresos que ha hecho un usuario
    public List<Abonosegresos> getDatListAbonosEgresos() {
        if (pagoSeleccionado == null || pagoSeleccionado.getIdpagos() == 0 || pagoSeleccionado.getTipopagos().getTipo() == 0) {
            return null;
        }

        return abonosegresosFacade.findByLikeAll("SELECT A FROM Abonosegresos A WHERE A.pagos.idpagos =" + pagoSeleccionado.getIdpagos());
    }

    //###TIPOS_PAGO
    //Función para que muestre todo los tipos de pago que hay
    public List<Tipopagos> getDataListTiposPagos() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIInput inputQuery = (UIInput) root.findComponent("formPagos").findComponent("inputTextBuscar");
        String query = "";
        if (inputQuery.getValue() != null) {
            query = inputQuery.getValue().toString();
        }

        List<Tipopagos> dataListTiposPagos = new ArrayList<Tipopagos>();

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

                    if (valores[i].trim().toLowerCase().equals("ingreso")) {
                        valores[i] = "0";
                    }

                    if (valores[i].trim().toLowerCase().equals("egreso")) {
                        valores[i] = "1";
                    }

                    if (!b && !h) {


                        queryPrueba = "SELECT U FROM Tipopagos U WHERE (LOWER(U.nombre) LIKE  '%" + valores[i].toLowerCase() + "%'  OR CONCAT(U.tipo, '') = '" + valores[i].toLowerCase() + "')";
                        h = true;
                    }
                    if (!b && h) {
                        queryPrueba += " AND (LOWER(U.nombre) LIKE  '%" + valores[i].toLowerCase() + "%'  OR CONCAT(U.tipo, '') = '" + valores[i].toLowerCase() + "')";
                    }
                }
                if (h) {
                    queryPrueba += " ORDER BY U.nombre";
                    dataListTiposPagos = tipopagosFacade.findByLike(queryPrueba);
                }
            } catch (Exception e) {
                System.out.print("Error buscando los tipos");
            }
            return dataListTiposPagos;
        } else {
            dataListTiposPagos = tipopagosFacade.findByLike("SELECT P FROM Tipopagos P ORDER BY P.nombre");
        }
        return dataListTiposPagos;
    }

    //Método para seleccionar el tipo de pago
    public void seleccionarTipopago(Tipopagos tipopagos) {
        tipoPagosSeleccionado = tipopagos;
    }

    //Propiedades de seleccionarTipopago
    public Tipopagos getTipoPagosSeleccionado() {
        return tipoPagosSeleccionado;
    }

    public void setTipoPagosSeleccionado(Tipopagos tipoPagosSeleccionado) {
        this.tipoPagosSeleccionado = tipoPagosSeleccionado;
    }

    //Método para agregar un nuevo tipo de pago
    public void agregarNuevoTipo() {
        tipoPagosSeleccionado = new Tipopagos(new Long(0));
    }

    //Método para guardar o editar un tipo de pago
    public String guardarTiposPagos() {
        if (tipoPagosSeleccionado != null) {

            try {
                userTransaction.begin();
            } catch (Exception e) {
            }

            if (tipoPagosSeleccionado.getNombre().trim().replaceAll(" ", "").replaceAll(" ", "").length() > 0) {
                if (tipoPagosSeleccionado.getIdtipopagos() == 0) {
                    if (tipopagosFacade.findByLike("SELECT P FROM Tipopagos P WHERE P.nombre = '" + tipoPagosSeleccionado.getNombre() + "'").isEmpty()) {
                        try {
                            tipopagosFacade.create(tipoPagosSeleccionado);
                        } catch (Exception e) {
                            FacesMessage msg = new FacesMessage("Ya existe un registro con este nombre");
                            msg.setSeverity(msg.SEVERITY_ERROR);
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            return "";
                        }
                    } else {
                        System.out.print("Error al crear el Tipo de pago");
                    }
                } else {
                    if (tipopagosFacade.findByLike("SELECT P FROM Tipopagos P WHERE P.nombre = '" + tipoPagosSeleccionado.getNombre() + "' AND P.idtipopagos != " + tipoPagosSeleccionado.getIdtipopagos()).isEmpty()) {
                        try {
                            tipopagosFacade.edit(tipoPagosSeleccionado);
                        } catch (Exception e) {
                            System.out.print("Error tipoPagos 1" + e.getStackTrace());
                        }
                    } else {
                        FacesMessage msg = new FacesMessage("Ya existe un registro con este nombre");
                        msg.setSeverity(msg.SEVERITY_ERROR);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        return "";
                    }
                }
            } else {
                FacesMessage msg = new FacesMessage("El nombre del REGISTRO no puede estar vacío");
                msg.setSeverity(msg.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "";
            }

            try {
                userTransaction.commit();
                FacesMessage msg = new FacesMessage("El registro fue hecho exitosamente");
                msg.setSeverity(msg.SEVERITY_INFO);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception e) {
                System.out.print("Error al editar o crear el tipo de pago " + e.getMessage());
            }

            return "";
        }
        return "";
    }

    ///###INFORMES
    //TOTALES
    //Método para mostrar el total de ingresos en el mes
    public double totalIngresos() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIInput fechaInicio = (UIInput) root.findComponent("formPagos").findComponent("fechaInicio");
        UIInput fechaFin = (UIInput) root.findComponent("formPagos").findComponent("fechaFin");

        if (fechaInicio.getValue() != null) {
            return (Double) abonospagosFacade.retornarValorObject("SELECT SUM(A.valor) FROM Abonospagos A WHERE A.fechaabono >= ' " + fechaInicio.getValue().toString() + "' AND A.fechaabono <= '" + fechaFin.getValue().toString() + "'");
        }

        return 0;
    }

    //Función para mostrar el total de lo pagado en el mes
    public double totalEgresos() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIInput fechaInicio = (UIInput) root.findComponent("formPagos").findComponent("fechaInicio");
        UIInput fechaFin = (UIInput) root.findComponent("formPagos").findComponent("fechaFin");

        if (fechaInicio.getValue() != null) {
            return (Double) abonosegresosFacade.retornarValorObject("SELECT SUM(A.valor) FROM Abonosegresos A WHERE A.fecha >= ' " + fechaInicio.getValue().toString() + "' AND A.fecha <= '" + fechaFin.getValue().toString() + "'");
        }

        return 0;
    }

    //Método para mostrar el total 
    public double total() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIInput fechaInicio = (UIInput) root.findComponent("formPagos").findComponent("fechaInicio");
        UIInput fechaFin = (UIInput) root.findComponent("formPagos").findComponent("fechaFin");

        if (fechaInicio.getValue() != null) {
            return (Double) abonospagosFacade.retornarValorObject("SELECT SUM(A.valor) FROM Abonospagos A WHERE A.fechaabono >= ' " + fechaInicio.getValue().toString() + "' AND A.fechaabono <= '" + fechaFin.getValue().toString() + "'")
                    - (Double) abonosegresosFacade.retornarValorObject("SELECT SUM(A.valor) FROM Abonosegresos A WHERE A.fecha >= ' " + fechaInicio.getValue().toString() + "' AND A.fecha <= '" + fechaFin.getValue().toString() + "'");
        }

        return 0;
    }

    //Método para mostrar la lista de lo que debe cada uno de los usuarios
    public List<Deudasusuarios> totalDeudasUsuarios() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot root = facesContext.getViewRoot();
        UIInput inputQuery = (UIInput) root.findComponent("formPagos").findComponent("inputTextBuscar");
        String query = "";

        if (inputQuery.getValue() != null) {
            query = inputQuery.getValue().toString();
        }

        List<Deudasusuarios> dataListTiposPagos = new ArrayList<Deudasusuarios>();

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

//
//                    if (valores[i].trim().toLowerCase().equals("ingreso")) {
//                        valores[i] = "0";
//                    }
//
//                    if (valores[i].trim().toLowerCase().equals("egreso")) {
//                        valores[i] = "1";
//                    }

                    if (!b && !h) {
                        queryPrueba = "SELECT DISTINCT (D) FROM Deudasusuarios D WHERE lower(D.usuarios.nombres) like '%" + valores[i].toString() + "%'";
                        h = true;
                    }
                    if (!b && h) {
                        queryPrueba += " AND lower(D.usuarios.nombres) like '%" + valores[i].toString() + "%'";
                    }
                }

                if (h) {
                    queryPrueba += " ORDER BY D.usuarios.nombres";
                    return deudasusuariosFacade.findByLikeAll2(queryPrueba);
                }

            } catch (Exception e) {
                System.out.print("Error buscando los tipos");
            }
            return dataListTiposPagos;
        } else {
            return deudasusuariosFacade.findByLikeAll2("SELECT DISTINCT (D) FROM Deudasusuarios D ORDER BY D.usuarios.nombres");
        }
    }
    //Método para mostar las deudas que tenga pendiente un usario
    private Usuarios usuarioEscogido;

    public Usuarios getUsuarioEscogido() {
        return usuarioEscogido;
    }
    
    public void seleccionarUsuarioInforme(Usuarios usuarios) {
        this.usuarioEscogido = usuarios;
    }

    public List<Relacionpagousuarios> getDalistPagosUsuarios() {

        if (usuarioEscogido == null) {
            return null;
        }


        return relacionpagousuariosFacade.findByLikeAll("SELECT R FROM Relacionpagousuarios R WHERE R.saldo > 0 AND R.usuarios.idusuarios =  " + usuarioEscogido.getIdusuarios() + " ORDER BY R.pagos.fechainicio, R.pagos.tipopagos.nombre");
    }
}
