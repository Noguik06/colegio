/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "Horarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "horarios.findAll", query = "SELECT b FROM Horarios b")})
public class Horarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idhorarios")
    private Long idhorarios;
    
    
    @JoinColumn(name = "lunes", referencedColumnName = "idrelacionprofesoresasignaturaperiodo")
    @ManyToOne(optional = false)
    private Relacionprofesoresasignaturaperiodo lunes;
    
    @JoinColumn(name = "martes", referencedColumnName = "idrelacionprofesoresasignaturaperiodo")
    @ManyToOne(optional = false)
    private Relacionprofesoresasignaturaperiodo martes;
    
    @JoinColumn(name = "miercoles", referencedColumnName = "idrelacionprofesoresasignaturaperiodo")
    @ManyToOne(optional = false)
    private Relacionprofesoresasignaturaperiodo miercoles;
    
    
    @JoinColumn(name = "jueves", referencedColumnName = "idrelacionprofesoresasignaturaperiodo")
    @ManyToOne(optional = false)
    private Relacionprofesoresasignaturaperiodo jueves;
    
    
    @JoinColumn(name = "viernes", referencedColumnName = "idrelacionprofesoresasignaturaperiodo")
    @ManyToOne(optional = false)
    private Relacionprofesoresasignaturaperiodo viernes;
    
    @JoinColumn(name = "idcursos", referencedColumnName = "idcursos")
    @ManyToOne(optional = false)
    private Cursos cursos;
    
    @JoinColumn(name = "idbloqueshorarios", referencedColumnName = "idbloqueshorarios")
    @ManyToOne(optional = false)
    private Bloqueshorarios bloqueshorarios;
    
    
    public Long getIdhorarios() {
		return idhorarios;
	}
	public void setIdhorarios(Long idhorarios) {
		this.idhorarios = idhorarios;
	}
	public Relacionprofesoresasignaturaperiodo getLunes() {
		return lunes;
	}
	public void setLunes(Relacionprofesoresasignaturaperiodo lunes) {
		this.lunes = lunes;
	}
	public Relacionprofesoresasignaturaperiodo getMartes() {
		return martes;
	}
	public void setMartes(Relacionprofesoresasignaturaperiodo martes) {
		this.martes = martes;
	}
	public Relacionprofesoresasignaturaperiodo getMiercoles() {
		return miercoles;
	}
	public void setMiercoles(Relacionprofesoresasignaturaperiodo miercoles) {
		this.miercoles = miercoles;
	}
	public Relacionprofesoresasignaturaperiodo getJueves() {
		return jueves;
	}
	public void setJueves(Relacionprofesoresasignaturaperiodo jueves) {
		this.jueves = jueves;
	}
	public Relacionprofesoresasignaturaperiodo getViernes() {
		return viernes;
	}
	public void setViernes(Relacionprofesoresasignaturaperiodo viernes) {
		this.viernes = viernes;
	}
	public Cursos getCursos() {
		return cursos;
	}
	public void setCursos(Cursos cursos) {
		this.cursos = cursos;
	}
	public Bloqueshorarios getBloqueshorarios() {
		return bloqueshorarios;
	}
	public void setBloqueshorarios(Bloqueshorarios bloqueshorarios) {
		this.bloqueshorarios = bloqueshorarios;
	}

	public Horarios() {
    }

   

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idhorarios != null ? idhorarios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bloqueshorarios)) {
            return false;
        }
        Horarios other = (Horarios) object;
        if ((this.idhorarios == null && other.idhorarios != null) || (this.idhorarios != null && !this.idhorarios.equals(other.idhorarios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Bloqueshorarios[ idhorarios=" + idhorarios + " ]";
    }
    
}
