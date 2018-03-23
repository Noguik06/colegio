/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "relacionasignaturaperiodos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relacionasignaturaperiodos.findAll", query = "SELECT r FROM Relacionasignaturaperiodos r"),
    @NamedQuery(name = "Relacionasignaturaperiodos.findByIdrelacionasignaturaperiodos", query = "SELECT r FROM Relacionasignaturaperiodos r WHERE r.idrelacionasignaturaperiodos = :idrelacionasignaturaperiodos")})
public class Relacionasignaturaperiodos implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelacionasignaturaperiodos")
    private Long idrelacionasignaturaperiodos;
    
    
    
    @JoinColumn(name = "grados", referencedColumnName = "idgrados")
    @ManyToOne
    private Grados grados;
    
    @JoinColumn(name = "asignaturas", referencedColumnName = "idasignaturas")
    @ManyToOne(optional = false)
    private Asignaturas asignaturas;
    
    @JoinColumn(name = "anosacademicos", referencedColumnName = "idanosacademicos")
    @ManyToOne(optional = false)
    private Anosacademicos anosacademicos;
    
    @OneToMany(mappedBy = "relacionasignaturasperiodos")
    private List<Relaciondimensionesasignaturasano> relaciondimensionesasignaturasanoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "relacionasignaturaperiodos")
    private List<Relacionprofesoresasignaturaperiodo> relacionprofesoresasignaturaperiodoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "relacionasignaturasperiodos")
    private List<Definitivasasignaturasperiodos> definitivasasignaturasperiodosList;
    
    @OneToMany(mappedBy = "relacionasignaturaperiodos")
    private Collection<Relacionrecuperacionregistromatriculas> relacionrecuperacionregistromatriculasCollection;
    
    
    @OneToMany(mappedBy = "relacionasignaturaperiodos")
    private List<Relacioncontenidosdimensiones> relacioncontenidosdimensionesList;
    
    public Relacionasignaturaperiodos() {
    }

    public Relacionasignaturaperiodos(Long idrelacionasignaturaperiodos) {
        this.idrelacionasignaturaperiodos = idrelacionasignaturaperiodos;
    }

    public Long getIdrelacionasignaturaperiodos() {
        return idrelacionasignaturaperiodos;
    }

    public void setIdrelacionasignaturaperiodos(Long idrelacionasignaturaperiodos) {
        this.idrelacionasignaturaperiodos = idrelacionasignaturaperiodos;
    }

    @XmlTransient
    public List<Relacionprofesoresasignaturaperiodo> getRelacionprofesoresasignaturaperiodoList() {
        return relacionprofesoresasignaturaperiodoList;
    }

    public void setRelacionprofesoresasignaturaperiodoList(List<Relacionprofesoresasignaturaperiodo> relacionprofesoresasignaturaperiodoList) {
        this.relacionprofesoresasignaturaperiodoList = relacionprofesoresasignaturaperiodoList;
    }

    public Grados getGrados() {
        return grados;
    }

    public void setGrados(Grados grados) {
        this.grados = grados;
    }

    public Asignaturas getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(Asignaturas asignaturas) {
        this.asignaturas = asignaturas;
    }

    public Anosacademicos getAnosacademicos() {
        return anosacademicos;
    }

    public void setAnosacademicos(Anosacademicos anosacademicos) {
        this.anosacademicos = anosacademicos;
    }

    @XmlTransient
    public List<Relaciondimensionesasignaturasano> getRelaciondimensionesasignaturasanoList() {
        return relaciondimensionesasignaturasanoList;
    }

    public void setRelaciondimensionesasignaturasanoList(List<Relaciondimensionesasignaturasano> relaciondimensionesasignaturasanoList) {
        this.relaciondimensionesasignaturasanoList = relaciondimensionesasignaturasanoList;
    }

    @XmlTransient
    public List<Relacioncontenidosdimensiones> getRelacioncontenidosdimensionesList() {
		return relacioncontenidosdimensionesList;
	}

	public void setRelacioncontenidosdimensionesList(
			List<Relacioncontenidosdimensiones> relacioncontenidosdimensionesList) {
		this.relacioncontenidosdimensionesList = relacioncontenidosdimensionesList;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelacionasignaturaperiodos != null ? idrelacionasignaturaperiodos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relacionasignaturaperiodos)) {
            return false;
        }
        Relacionasignaturaperiodos other = (Relacionasignaturaperiodos) object;
        if ((this.idrelacionasignaturaperiodos == null && other.idrelacionasignaturaperiodos != null) || (this.idrelacionasignaturaperiodos != null && !this.idrelacionasignaturaperiodos.equals(other.idrelacionasignaturaperiodos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relacionasignaturaperiodos[ idrelacionasignaturaperiodos=" + idrelacionasignaturaperiodos + " ]";
    }

    @XmlTransient
    public Collection<Relacionrecuperacionregistromatriculas> getRelacionrecuperacionregistromatriculasCollection() {
        return relacionrecuperacionregistromatriculasCollection;
    }

    public void setRelacionrecuperacionregistromatriculasCollection(Collection<Relacionrecuperacionregistromatriculas> relacionrecuperacionregistromatriculasCollection) {
        this.relacionrecuperacionregistromatriculasCollection = relacionrecuperacionregistromatriculasCollection;
    }

    @XmlTransient
    public List<Definitivasasignaturasperiodos> getDefinitivasasignaturasperiodosList() {
        return definitivasasignaturasperiodosList;
    }

    public void setDefinitivasasignaturasperiodosList(List<Definitivasasignaturasperiodos> definitivasasignaturasperiodosList) {
        this.definitivasasignaturasperiodosList = definitivasasignaturasperiodosList;
    }
    
}
