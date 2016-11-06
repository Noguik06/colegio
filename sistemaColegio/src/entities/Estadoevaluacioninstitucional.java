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
@Table(name = "estadoevaluacioninstitucional")
public class Estadoevaluacioninstitucional implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtadoevaluacioninstitucional")
    private Long idestadoevaluacioninstitucional;
    
    
    @JoinColumn(name = "registromatriculas", referencedColumnName = "idregistromatriculas")
    @ManyToOne
    private Registromatriculas registromatriculas;
    
    
    @JoinColumn(name = "profesores", referencedColumnName = "idprofesores")
    @ManyToOne
    private Profesores profesor;

	public Long getIdestadoevaluacioninstitucional() {
		return idestadoevaluacioninstitucional;
	}

	public void setIdestadoevaluacioninstitucional(Long idestadoevaluacioninstitucional) {
		this.idestadoevaluacioninstitucional = idestadoevaluacioninstitucional;
	}

	public Registromatriculas getRegistromatriculas() {
		return registromatriculas;
	}

	public void setRegistromatriculas(Registromatriculas registromatriculas) {
		this.registromatriculas = registromatriculas;
	}

	public Profesores getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesores profesor) {
		this.profesor = profesor;
	}
}
