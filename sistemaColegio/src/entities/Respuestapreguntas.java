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
@Table(name = "respuestapreguntas")
public class Respuestapreguntas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrespuestapreguntas")
    private Long idrespuestapreguntas;
    
    @JoinColumn(name = "idpreguntas", referencedColumnName = "idpreguntas")
    @ManyToOne
    private Preguntas preguntas;
    
    @JoinColumn(name = "idregistromatriculas", referencedColumnName = "idregistromatriculas")
    @ManyToOne
    private Registromatriculas registromatriculas;
    
    
    @JoinColumn(name = "idprofesor", referencedColumnName = "idprofesores")
    @ManyToOne
    private Profesores profesor;
    
    @Column(name="valor")
    private Integer valor;

	public Long getIdrespuestapreguntas() {
		return idrespuestapreguntas;
	}

	public void setIdrespuestapreguntas(Long idrespuestapreguntas) {
		this.idrespuestapreguntas = idrespuestapreguntas;
	}

	public Preguntas getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(Preguntas preguntas) {
		this.preguntas = preguntas;
	}

	public Registromatriculas getRegistromatriculas() {
		return registromatriculas;
	}

	public void setRegistromatriculas(Registromatriculas registromatriculas) {
		this.registromatriculas = registromatriculas;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

	public Profesores getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesores profesor) {
		this.profesor = profesor;
	}
	
	
}
