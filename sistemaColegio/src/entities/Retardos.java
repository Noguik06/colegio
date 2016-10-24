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
@Table(name = "retardos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Retardos.findAll", query = "SELECT r FROM Retardos r"),
    @NamedQuery(name = "Retardos.findByIdretardos", query = "SELECT r FROM Retardos r WHERE r.idretardos = :idretardos"),
    @NamedQuery(name = "Retardos.findByTotal", query = "SELECT r FROM Retardos r WHERE r.total = :total")})
public class Retardos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idretardos")
    private Long idretardos;
    @Column(name = "total")
    private Short total;
    @JoinColumn(name = "registromatriculas", referencedColumnName = "idregistromatriculas")
    @ManyToOne
    private Registromatriculas registromatriculas;
    @JoinColumn(name = "periodos", referencedColumnName = "idperiodos")
    @ManyToOne
    private Periodos periodos;

    public Retardos() {
    }

    public Retardos(Long idretardos) {
        this.idretardos = idretardos;
    }

    public Long getIdretardos() {
        return idretardos;
    }

    public void setIdretardos(Long idretardos) {
        this.idretardos = idretardos;
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
        hash += (idretardos != null ? idretardos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Retardos)) {
            return false;
        }
        Retardos other = (Retardos) object;
        if ((this.idretardos == null && other.idretardos != null) || (this.idretardos != null && !this.idretardos.equals(other.idretardos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Retardos[ idretardos=" + idretardos + " ]";
    }
    
}
