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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "cronogramas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cronogramas.findAll", query = "SELECT c FROM Cronogramas c"),
    @NamedQuery(name = "Cronogramas.findByIdcronogramas", query = "SELECT c FROM Cronogramas c WHERE c.idcronogramas = :idcronogramas"),
    @NamedQuery(name = "Cronogramas.findByNombre", query = "SELECT c FROM Cronogramas c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Cronogramas.findByDetalles", query = "SELECT c FROM Cronogramas c WHERE c.detalles = :detalles"),
    @NamedQuery(name = "Cronogramas.findByFechainicio", query = "SELECT c FROM Cronogramas c WHERE c.fechainicio = :fechainicio"),
    @NamedQuery(name = "Cronogramas.findByFechafin", query = "SELECT c FROM Cronogramas c WHERE c.fechafin = :fechafin")})
public class Cronogramas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcronogramas")
    private Long idcronogramas;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "detalles")
    private String detalles;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechainicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechafin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafin;
    @JoinColumn(name = "cursos", referencedColumnName = "idcursos")
    @ManyToOne(optional = false)
    private Cursos cursos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cronogramas")
    private List<Relacionusuariocronogramas> relacionusuariocronogramasList;

    public Cronogramas() {
    }

    public Cronogramas(Long idcronogramas) {
        this.idcronogramas = idcronogramas;
    }

    public Cronogramas(Long idcronogramas, String nombre, Date fechainicio, Date fechafin) {
        this.idcronogramas = idcronogramas;
        this.nombre = nombre;
        this.fechainicio = fechainicio;
        this.fechafin = fechafin;
    }

    public Long getIdcronogramas() {
        return idcronogramas;
    }

    public void setIdcronogramas(Long idcronogramas) {
        this.idcronogramas = idcronogramas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
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

    public Cursos getCursos() {
        return cursos;
    }

    public void setCursos(Cursos cursos) {
        this.cursos = cursos;
    }

    @XmlTransient
    public List<Relacionusuariocronogramas> getRelacionusuariocronogramasList() {
        return relacionusuariocronogramasList;
    }

    public void setRelacionusuariocronogramasList(List<Relacionusuariocronogramas> relacionusuariocronogramasList) {
        this.relacionusuariocronogramasList = relacionusuariocronogramasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcronogramas != null ? idcronogramas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cronogramas)) {
            return false;
        }
        Cronogramas other = (Cronogramas) object;
        if ((this.idcronogramas == null && other.idcronogramas != null) || (this.idcronogramas != null && !this.idcronogramas.equals(other.idcronogramas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cronogramas[ idcronogramas=" + idcronogramas + " ]";
    }
    
}
