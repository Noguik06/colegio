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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "anosacademicos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Anosacademicos.findAll", query = "SELECT a FROM Anosacademicos a"),
    @NamedQuery(name = "Anosacademicos.findByIdanosacademicos", query = "SELECT a FROM Anosacademicos a WHERE a.idanosacademicos = :idanosacademicos"),
    @NamedQuery(name = "Anosacademicos.findByFechainicio", query = "SELECT a FROM Anosacademicos a WHERE a.fechainicio = :fechainicio"),
    @NamedQuery(name = "Anosacademicos.findByFechafin", query = "SELECT a FROM Anosacademicos a WHERE a.fechafin = :fechafin"),
    @NamedQuery(name = "Anosacademicos.findByEstadoactivo", query = "SELECT a FROM Anosacademicos a WHERE a.estadoactivo = :estadoactivo")})
public class Anosacademicos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idanosacademicos")
    private Long idanosacademicos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechainicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicio;
    @Column(name = "fechafin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estadoactivo")
    private boolean estadoactivo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anosacademicos")
    private List<Cursos> cursosList;
    @OneToMany(mappedBy = "anosacademicos")
    private List<Registromatriculas> registromatriculasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anoacademicos")
    private List<Periodos> periodosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anosacademicos")
    private List<Relacionasignaturaperiodos> relacionasignaturaperiodosList;

    public Anosacademicos() {
    }

    public Anosacademicos(Long idanosacademicos) {
        this.idanosacademicos = idanosacademicos;
    }

    public Anosacademicos(Long idanosacademicos, Date fechainicio, boolean estadoactivo) {
        this.idanosacademicos = idanosacademicos;
        this.fechainicio = fechainicio;
        this.estadoactivo = estadoactivo;
    }

    public Long getIdanosacademicos() {
        return idanosacademicos;
    }

    public void setIdanosacademicos(Long idanosacademicos) {
        this.idanosacademicos = idanosacademicos;
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

    public boolean getEstadoactivo() {
        return estadoactivo;
    }

    public void setEstadoactivo(boolean estadoactivo) {
        this.estadoactivo = estadoactivo;
    }

    @XmlTransient
    public List<Cursos> getCursosList() {
        return cursosList;
    }

    public void setCursosList(List<Cursos> cursosList) {
        this.cursosList = cursosList;
    }

    @XmlTransient
    public List<Registromatriculas> getRegistromatriculasList() {
        return registromatriculasList;
    }

    public void setRegistromatriculasList(List<Registromatriculas> registromatriculasList) {
        this.registromatriculasList = registromatriculasList;
    }

    @XmlTransient
    public List<Periodos> getPeriodosList() {
        return periodosList;
    }

    public void setPeriodosList(List<Periodos> periodosList) {
        this.periodosList = periodosList;
    }

    @XmlTransient
    public List<Relacionasignaturaperiodos> getRelacionasignaturaperiodosList() {
        return relacionasignaturaperiodosList;
    }

    public void setRelacionasignaturaperiodosList(List<Relacionasignaturaperiodos> relacionasignaturaperiodosList) {
        this.relacionasignaturaperiodosList = relacionasignaturaperiodosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idanosacademicos != null ? idanosacademicos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Anosacademicos)) {
            return false;
        }
        Anosacademicos other = (Anosacademicos) object;
        if ((this.idanosacademicos == null && other.idanosacademicos != null) || (this.idanosacademicos != null && !this.idanosacademicos.equals(other.idanosacademicos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Anosacademicos[ idanosacademicos=" + idanosacademicos + " ]";
    }
    
}
