/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "asignaturasgrupales")
@XmlRootElement
public class Asignaturasgrupales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idasignaturasgrupales")
    private Long idasignaturasgrupales;
    
    
    @JoinColumn(name = "asignatura1", referencedColumnName = "idasignaturas")
    @ManyToOne
    private Asignaturas asignatura1;
    
    @JoinColumn(name = "asignatura2", referencedColumnName = "idasignaturas")
    @ManyToOne
    private Asignaturas asignatura2;
    
    
    @JoinColumn(name = "grado", referencedColumnName = "idgrados")
    @ManyToOne
    private Grados grado;
    
    @JoinColumn(name = "idanosacademicos", referencedColumnName = "idanosacademicos")
    @ManyToOne
    private Anosacademicos anosacademicos;
    
    

    public Asignaturasgrupales() {
    }
    public Long getIdasignaturasgrupales() {
		return idasignaturasgrupales;
	}
	public void setIdasignaturasgrupales(Long idasignaturasgrupales) {
		this.idasignaturasgrupales = idasignaturasgrupales;
	}
	public Asignaturas getAsignatura1() {
		return asignatura1;
	}
	public void setAsignatura1(Asignaturas asignatura1) {
		this.asignatura1 = asignatura1;
	}
	public Asignaturas getAsignatura2() {
		return asignatura2;
	}
	public void setAsignatura2(Asignaturas asignatura2) {
		this.asignatura2 = asignatura2;
	}
	public Grados getGrado() {
		return grado;
	}
	public void setGrado(Grados grado) {
		this.grado = grado;
	}
	public Anosacademicos getAnosacademicos() {
		return anosacademicos;
	}
	public void setAnosacademicos(Anosacademicos anosacademicos) {
		this.anosacademicos = anosacademicos;
	}
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idasignaturasgrupales != null ? idasignaturasgrupales.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asignaturasgrupales)) {
            return false;
        }
        Asignaturasgrupales other = (Asignaturasgrupales) object;
        if ((this.idasignaturasgrupales == null && other.idasignaturasgrupales != null) || (this.idasignaturasgrupales != null && !this.idasignaturasgrupales.equals(other.idasignaturasgrupales))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Asignaturasgrupales[ idasignaturasgrupales=" + idasignaturasgrupales + " ]";
    }
    
}
