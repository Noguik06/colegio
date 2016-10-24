/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "pagos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagos.findAll", query = "SELECT p FROM Pagos p"),
    @NamedQuery(name = "Pagos.findByIdpagos", query = "SELECT p FROM Pagos p WHERE p.idpagos = :idpagos"),
    @NamedQuery(name = "Pagos.findByNombre", query = "SELECT p FROM Pagos p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Pagos.findByValor", query = "SELECT p FROM Pagos p WHERE p.valor = :valor"),
    @NamedQuery(name = "Pagos.findByFechacracion", query = "SELECT p FROM Pagos p WHERE p.fechacracion = :fechacracion")})
public class Pagos implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pagos")
    private List<Abonosegresos> abonosegresosList;
    @Column(name = "fechainicio")
    @Temporal(TemporalType.DATE)
    private Date fechainicio;
    @Column(name = "fechafin")
    @Temporal(TemporalType.DATE)
    private Date fechafin;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpagos")
    private Long idpagos;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "fechacracion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacracion;
    @OneToMany(mappedBy = "pagos")
    private List<Relacionpagousuarios> relacionpagousuariosList;
    @JoinColumn(name = "tipopagos", referencedColumnName = "idtipopagos")
    @ManyToOne
    private Tipopagos tipopagos;

    public Pagos() {
    }

    public Pagos(Long idpagos) {
        this.idpagos = idpagos;
    }

    public Long getIdpagos() {
        return idpagos;
    }

    public void setIdpagos(Long idpagos) {
        this.idpagos = idpagos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getFechacracion() {
        return fechacracion;
    }

    public void setFechacracion(Date fechacracion) {
        this.fechacracion = fechacracion;
    }

    @XmlTransient
    public List<Relacionpagousuarios> getRelacionpagousuariosList() {
        return relacionpagousuariosList;
    }

    public void setRelacionpagousuariosList(List<Relacionpagousuarios> relacionpagousuariosList) {
        this.relacionpagousuariosList = relacionpagousuariosList;
    }

    public Tipopagos getTipopagos() {
        return tipopagos;
    }

    public void setTipopagos(Tipopagos tipopagos) {
        this.tipopagos = tipopagos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpagos != null ? idpagos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagos)) {
            return false;
        }
        Pagos other = (Pagos) object;
        if ((this.idpagos == null && other.idpagos != null) || (this.idpagos != null && !this.idpagos.equals(other.idpagos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pagos[ idpagos=" + idpagos + " ]";
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    @XmlTransient
    public List<Abonosegresos> getAbonosegresosList() {
        return abonosegresosList;
    }

    public void setAbonosegresosList(List<Abonosegresos> abonosegresosList) {
        this.abonosegresosList = abonosegresosList;
    }
    
}
