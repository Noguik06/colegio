/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "registromatriculas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registromatriculas.findAll", query = "SELECT r FROM Registromatriculas r"),
    @NamedQuery(name = "Registromatriculas.findByIdregistromatriculas", query = "SELECT r FROM Registromatriculas r WHERE r.idregistromatriculas = :idregistromatriculas")})
public class Registromatriculas implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "registromatricula")
    private List<Definitivasasignaturasperiodos> definitivasasignaturasperiodosList;
    @Column(name = "fecharetiro")
    @Temporal(TemporalType.DATE)
    private Date fecharetiro;
    @Column(name = "fechamatricula")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechamatricula;
    @OneToMany(mappedBy = "registromatriculas")
    private List<Retardos> retardosList;
    @OneToMany(mappedBy = "registromatriculas")
    private List<Fallas> fallasList;
    @OneToMany(mappedBy = "registromatriculas")
    private List<Observacionesboletines> observacionesboletinesList;
    @OneToMany(mappedBy = "registromatriculas")
    private Collection<Relacionrecuperacionregistromatriculas> relacionrecuperacionregistromatriculasCollection;
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idregistromatriculas")
    private Long idregistromatriculas;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "registromatriculas")
    private List<Relacionboletineslogros> relacionboletineslogrosList;
    
    @JoinColumn(name = "grados", referencedColumnName = "idgrados")
    @ManyToOne
    private Grados grados;
    
    @JoinColumn(name = "estudiantes", referencedColumnName = "idestudiantes")
    @ManyToOne
    private Estudiantes estudiantes;
    
    @JoinColumn(name = "cursos", referencedColumnName = "idcursos")
    @ManyToOne
    private Cursos cursos;
    
    @JoinColumn(name = "anosacademicos", referencedColumnName = "idanosacademicos")
    @ManyToOne
    private Anosacademicos anosacademicos;
    
    @OneToMany(mappedBy = "registromatriculas")
    private List<Notascalificables> notascalificablesList;

    public Registromatriculas() {
    }

    public Registromatriculas(Long idregistromatriculas) {
        this.idregistromatriculas = idregistromatriculas;
    }

    public Long getIdregistromatriculas() {
        return idregistromatriculas;
    }

    public void setIdregistromatriculas(Long idregistromatriculas) {
        this.idregistromatriculas = idregistromatriculas;
    }

    @XmlTransient
    public List<Relacionboletineslogros> getRelacionboletineslogrosList() {
        return relacionboletineslogrosList;
    }

    public void setRelacionboletineslogrosList(List<Relacionboletineslogros> relacionboletineslogrosList) {
        this.relacionboletineslogrosList = relacionboletineslogrosList;
    }

    public Grados getGrados() {
        return grados;
    }

    public void setGrados(Grados grados) {
        this.grados = grados;
    }

    public Estudiantes getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(Estudiantes estudiantes) {
        this.estudiantes = estudiantes;
    }

    public Cursos getCursos() {
        return cursos;
    }

    public void setCursos(Cursos cursos) {
        this.cursos = cursos;
    }

    public Anosacademicos getAnosacademicos() {
        return anosacademicos;
    }

    public void setAnosacademicos(Anosacademicos anosacademicos) {
        this.anosacademicos = anosacademicos;
    }

    @XmlTransient
    public List<Notascalificables> getNotascalificablesList() {
        return notascalificablesList;
    }

    public void setNotascalificablesList(List<Notascalificables> notascalificablesList) {
        this.notascalificablesList = notascalificablesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idregistromatriculas != null ? idregistromatriculas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registromatriculas)) {
            return false;
        }
        Registromatriculas other = (Registromatriculas) object;
        if ((this.idregistromatriculas == null && other.idregistromatriculas != null) || (this.idregistromatriculas != null && !this.idregistromatriculas.equals(other.idregistromatriculas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Registromatriculas[ idregistromatriculas=" + idregistromatriculas + " ]";
    }

    @XmlTransient
    public Collection<Relacionrecuperacionregistromatriculas> getRelacionrecuperacionregistromatriculasCollection() {
        return relacionrecuperacionregistromatriculasCollection;
    }

    public void setRelacionrecuperacionregistromatriculasCollection(Collection<Relacionrecuperacionregistromatriculas> relacionrecuperacionregistromatriculasCollection) {
        this.relacionrecuperacionregistromatriculasCollection = relacionrecuperacionregistromatriculasCollection;
    }

    @XmlTransient
    public List<Observacionesboletines> getObservacionesboletinesList() {
        return observacionesboletinesList;
    }

    public void setObservacionesboletinesList(List<Observacionesboletines> observacionesboletinesList) {
        this.observacionesboletinesList = observacionesboletinesList;
    }

    @XmlTransient
    public List<Fallas> getFallasList() {
        return fallasList;
    }

    public void setFallasList(List<Fallas> fallasList) {
        this.fallasList = fallasList;
    }

    @XmlTransient
    public List<Retardos> getRetardosList() {
        return retardosList;
    }

    public void setRetardosList(List<Retardos> retardosList) {
        this.retardosList = retardosList;
    }

    public Date getFecharetiro() {
        return fecharetiro;
    }

    public void setFecharetiro(Date fecharetiro) {
        this.fecharetiro = fecharetiro;
    }

    public Date getFechamatricula() {
        return fechamatricula;
    }

    public void setFechamatricula(Date fechamatricula) {
        this.fechamatricula = fechamatricula;
    }

    @XmlTransient
    public List<Definitivasasignaturasperiodos> getDefinitivasasignaturasperiodosList() {
        return definitivasasignaturasperiodosList;
    }

    public void setDefinitivasasignaturasperiodosList(List<Definitivasasignaturasperiodos> definitivasasignaturasperiodosList) {
        this.definitivasasignaturasperiodosList = definitivasasignaturasperiodosList;
    }
    
}
