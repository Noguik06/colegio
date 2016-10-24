/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author ricardo
 */
@Entity
@Table(name = "modulos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modulos.findAll", query = "SELECT m FROM Modulos m"),
    @NamedQuery(name = "Modulos.findByModulo", query = "SELECT m FROM Modulos m WHERE m.modulo = :modulo"),
    @NamedQuery(name = "Modulos.findByDescripcion", query = "SELECT m FROM Modulos m WHERE m.descripcion = :descripcion"),
    @NamedQuery(name = "Modulos.findByOrden", query = "SELECT m FROM Modulos m WHERE m.orden = :orden")})
public class Modulos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "modulo", nullable = false)
    private Integer modulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;
    @Column(name = "orden")
    private Short orden;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modulos")
    private Collection<Interfaz> interfazCollection;

    public Modulos() {
    }

    public Modulos(Integer modulo) {
        this.modulo = modulo;
    }

    public Modulos(Integer modulo, String descripcion) {
        this.modulo = modulo;
        this.descripcion = descripcion;
    }

    public Integer getModulo() {
        return modulo;
    }

    public void setModulo(Integer modulo) {
        this.modulo = modulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Short getOrden() {
        return orden;
    }

    public void setOrden(Short orden) {
        this.orden = orden;
    }

    @XmlTransient
    public Collection<Interfaz> getInterfazCollection() {
        return interfazCollection;
    }

    public void setInterfazCollection(Collection<Interfaz> interfazCollection) {
        this.interfazCollection = interfazCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (modulo != null ? modulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modulos)) {
            return false;
        }
        Modulos other = (Modulos) object;
        if ((this.modulo == null && other.modulo != null) || (this.modulo != null && !this.modulo.equals(other.modulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Modulos[ modulo=" + modulo + " ]";
    }
    
}
