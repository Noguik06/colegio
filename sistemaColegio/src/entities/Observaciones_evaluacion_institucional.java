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
@Table(name = "observaciones_evaluacion_institucional")
public class Observaciones_evaluacion_institucional implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idobservaciones_evaluacion_institucional")
    private Long idobservaciones_evaluacion_institucional;
    
    @JoinColumn(name = "registromatriculas", referencedColumnName = "idregistromatriculas")
    @ManyToOne
    private Registromatriculas registromatriculas;
    
    @Column(name="observacion")
    private String observacion;

	public Long getIdobservaciones_evaluacion_institucional() {
		return idobservaciones_evaluacion_institucional;
	}

	public void setIdobservaciones_evaluacion_institucional(Long idobservaciones_evaluacion_institucional) {
		this.idobservaciones_evaluacion_institucional = idobservaciones_evaluacion_institucional;
	}

	public Registromatriculas getRegistromatriculas() {
		return registromatriculas;
	}

	public void setRegistromatriculas(Registromatriculas registromatriculas) {
		this.registromatriculas = registromatriculas;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
}
