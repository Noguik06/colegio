/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "tipopagos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipopagos.findAll", query = "SELECT t FROM Tipopagos t"),
    @NamedQuery(name = "Tipopagos.findByIdtipopagos", query = "SELECT t FROM Tipopagos t WHERE t.idtipopagos = :idtipopagos"),
    @NamedQuery(name = "Tipopagos.findByNombre", query = "SELECT t FROM Tipopagos t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Tipopagos.findByTipo", query = "SELECT t FROM Tipopagos t WHERE t.tipo = :tipo")})
public class Tipopagos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipopagos")
    private Long idtipopagos;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "tipo")
    private Short tipo;
    @OneToMany(mappedBy = "tipopagos")
    private List<Pagos> pagosList;

    public Tipopagos() {
    }

    public Tipopagos(Long idtipopagos) {
        this.idtipopagos = idtipopagos;
    }

    public Long getIdtipopagos() {
        return idtipopagos;
    }

    public void setIdtipopagos(Long idtipopagos) {
        this.idtipopagos = idtipopagos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Short getTipo() {
        return tipo;
    }

    public void setTipo(Short tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<Pagos> getPagosList() {
        return pagosList;
    }

    public void setPagosList(List<Pagos> pagosList) {
        this.pagosList = pagosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipopagos != null ? idtipopagos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipopagos)) {
            return false;
        }
        Tipopagos other = (Tipopagos) object;
        if ((this.idtipopagos == null && other.idtipopagos != null) || (this.idtipopagos != null && !this.idtipopagos.equals(other.idtipopagos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Tipopagos[ idtipopagos=" + idtipopagos + " ]";
    }
    
}
