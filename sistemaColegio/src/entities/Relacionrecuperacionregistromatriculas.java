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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "relacionrecuperacionregistromatriculas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relacionrecuperacionregistromatriculas.findAll", query = "SELECT r FROM Relacionrecuperacionregistromatriculas r"),
    @NamedQuery(name = "Relacionrecuperacionregistromatriculas.findByValor", query = "SELECT r FROM Relacionrecuperacionregistromatriculas r WHERE r.valor = :valor"),
    @NamedQuery(name = "Relacionrecuperacionregistromatriculas.findByIdrelacionrecuperacionregistromatriculas", query = "SELECT r FROM Relacionrecuperacionregistromatriculas r WHERE r.idrelacionrecuperacionregistromatriculas = :idrelacionrecuperacionregistromatriculas")})
public class Relacionrecuperacionregistromatriculas implements Serializable {
    @JoinColumn(name = "relacionasignaturaperiodos", referencedColumnName = "idrelacionasignaturaperiodos")
    @ManyToOne
    private Relacionasignaturaperiodos relacionasignaturaperiodos;
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelacionrecuperacionregistromatriculas")
    private Long idrelacionrecuperacionregistromatriculas;
    @JoinColumn(name = "registromatriculas", referencedColumnName = "idregistromatriculas")
    @ManyToOne
    private Registromatriculas registromatriculas;
    @JoinColumn(name = "recuperaciones", referencedColumnName = "idrecuperaciones")
    @ManyToOne
    private Recuperaciones recuperaciones;
    @JoinColumn(name = "periodos", referencedColumnName = "idperiodos")
    @ManyToOne
    private Periodos periodos;

    public Relacionrecuperacionregistromatriculas() {
    }

    public Relacionrecuperacionregistromatriculas(Long idrelacionrecuperacionregistromatriculas) {
        this.idrelacionrecuperacionregistromatriculas = idrelacionrecuperacionregistromatriculas;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Long getIdrelacionrecuperacionregistromatriculas() {
        return idrelacionrecuperacionregistromatriculas;
    }

    public void setIdrelacionrecuperacionregistromatriculas(Long idrelacionrecuperacionregistromatriculas) {
        this.idrelacionrecuperacionregistromatriculas = idrelacionrecuperacionregistromatriculas;
    }

    public Registromatriculas getRegistromatriculas() {
        return registromatriculas;
    }

    public void setRegistromatriculas(Registromatriculas registromatriculas) {
        this.registromatriculas = registromatriculas;
    }

    public Recuperaciones getRecuperaciones() {
        return recuperaciones;
    }

    public void setRecuperaciones(Recuperaciones recuperaciones) {
        this.recuperaciones = recuperaciones;
    }

    public Periodos getPeriodos() {
        return periodos;
    }

    public void setPeriodos(Periodos periodos) {
        this.periodos = periodos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelacionrecuperacionregistromatriculas != null ? idrelacionrecuperacionregistromatriculas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relacionrecuperacionregistromatriculas)) {
            return false;
        }
        Relacionrecuperacionregistromatriculas other = (Relacionrecuperacionregistromatriculas) object;
        if ((this.idrelacionrecuperacionregistromatriculas == null && other.idrelacionrecuperacionregistromatriculas != null) || (this.idrelacionrecuperacionregistromatriculas != null && !this.idrelacionrecuperacionregistromatriculas.equals(other.idrelacionrecuperacionregistromatriculas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relacionrecuperacionregistromatriculas[ idrelacionrecuperacionregistromatriculas=" + idrelacionrecuperacionregistromatriculas + " ]";
    }

    public Relacionasignaturaperiodos getRelacionasignaturaperiodos() {
        return relacionasignaturaperiodos;
    }

    public void setRelacionasignaturaperiodos(Relacionasignaturaperiodos relacionasignaturaperiodos) {
        this.relacionasignaturaperiodos = relacionasignaturaperiodos;
    }
    
}
