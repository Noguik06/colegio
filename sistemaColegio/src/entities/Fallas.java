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
@Table(name = "fallas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fallas.findAll", query = "SELECT f FROM Fallas f"),
    @NamedQuery(name = "Fallas.findByIdfallas", query = "SELECT f FROM Fallas f WHERE f.idfallas = :idfallas"),
    @NamedQuery(name = "Fallas.findByTotal", query = "SELECT f FROM Fallas f WHERE f.total = :total")})
public class Fallas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idfallas")
    private Long idfallas;
    @Column(name = "total")
    private Short total;
    @JoinColumn(name = "registromatriculas", referencedColumnName = "idregistromatriculas")
    @ManyToOne
    private Registromatriculas registromatriculas;
    @JoinColumn(name = "periodos", referencedColumnName = "idperiodos")
    @ManyToOne
    private Periodos periodos;

    public Fallas() {
    }

    public Fallas(Long idfallas) {
        this.idfallas = idfallas;
    }

    public Long getIdfallas() {
        return idfallas;
    }

    public void setIdfallas(Long idfallas) {
        this.idfallas = idfallas;
    }

    public Short getTotal() {
        return total;
    }

    public void setTotal(Short total) {
        this.total = total;
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
        hash += (idfallas != null ? idfallas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fallas)) {
            return false;
        }
        Fallas other = (Fallas) object;
        if ((this.idfallas == null && other.idfallas != null) || (this.idfallas != null && !this.idfallas.equals(other.idfallas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Fallas[ idfallas=" + idfallas + " ]";
    }
    
}
