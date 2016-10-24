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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "observacionesboletines")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Observacionesboletines.findAll", query = "SELECT o FROM Observacionesboletines o"),
    @NamedQuery(name = "Observacionesboletines.findByIdobservacionesboletines", query = "SELECT o FROM Observacionesboletines o WHERE o.idobservacionesboletines = :idobservacionesboletines"),
    @NamedQuery(name = "Observacionesboletines.findByObservacion", query = "SELECT o FROM Observacionesboletines o WHERE o.observacion = :observacion")})
public class Observacionesboletines implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idobservacionesboletines")
    private Long idobservacionesboletines;
    @Size(max = 2147483647)
    @Column(name = "observacion")
    private String observacion;
    @JoinColumn(name = "registromatriculas", referencedColumnName = "idregistromatriculas")
    @ManyToOne
    private Registromatriculas registromatriculas;
    @JoinColumn(name = "periodos", referencedColumnName = "idperiodos")
    @ManyToOne
    private Periodos periodos;

    public Observacionesboletines() {
    }

    public Observacionesboletines(Long idobservacionesboletines) {
        this.idobservacionesboletines = idobservacionesboletines;
    }

    public Long getIdobservacionesboletines() {
        return idobservacionesboletines;
    }

    public void setIdobservacionesboletines(Long idobservacionesboletines) {
        this.idobservacionesboletines = idobservacionesboletines;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Registromatriculas getRegistromatriculas() {
        return registromatriculas;
    }

    public void setRegistromatriculas(Registromatriculas registromatriculas) {
        this.registromatriculas = registromatriculas;
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
        hash += (idobservacionesboletines != null ? idobservacionesboletines.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Observacionesboletines)) {
            return false;
        }
        Observacionesboletines other = (Observacionesboletines) object;
        if ((this.idobservacionesboletines == null && other.idobservacionesboletines != null) || (this.idobservacionesboletines != null && !this.idobservacionesboletines.equals(other.idobservacionesboletines))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Observacionesboletines[ idobservacionesboletines=" + idobservacionesboletines + " ]";
    }
    
}
