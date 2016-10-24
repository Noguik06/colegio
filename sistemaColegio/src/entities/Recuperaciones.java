/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "recuperaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recuperaciones.findAll", query = "SELECT r FROM Recuperaciones r"),
    @NamedQuery(name = "Recuperaciones.findByIdrecuperaciones", query = "SELECT r FROM Recuperaciones r WHERE r.idrecuperaciones = :idrecuperaciones"),
    @NamedQuery(name = "Recuperaciones.findByNombre", query = "SELECT r FROM Recuperaciones r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "Recuperaciones.findByNumero", query = "SELECT r FROM Recuperaciones r WHERE r.numero = :numero")})
public class Recuperaciones implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recuperaciones")
    private List<Relacionlogrosrecuperaciones> relacionlogrosrecuperacionesList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrecuperaciones")
    private Long idrecuperaciones;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "numero")
    private Short numero;
    @OneToMany(mappedBy = "recuperaciones")
    private Collection<Relacionrecuperacionregistromatriculas> relacionrecuperacionregistromatriculasCollection;

    public Recuperaciones() {
    }

    public Recuperaciones(Long idrecuperaciones) {
        this.idrecuperaciones = idrecuperaciones;
    }

    public Long getIdrecuperaciones() {
        return idrecuperaciones;
    }

    public void setIdrecuperaciones(Long idrecuperaciones) {
        this.idrecuperaciones = idrecuperaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Short getNumero() {
        return numero;
    }

    public void setNumero(Short numero) {
        this.numero = numero;
    }

    @XmlTransient
    public Collection<Relacionrecuperacionregistromatriculas> getRelacionrecuperacionregistromatriculasCollection() {
        return relacionrecuperacionregistromatriculasCollection;
    }

    public void setRelacionrecuperacionregistromatriculasCollection(Collection<Relacionrecuperacionregistromatriculas> relacionrecuperacionregistromatriculasCollection) {
        this.relacionrecuperacionregistromatriculasCollection = relacionrecuperacionregistromatriculasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrecuperaciones != null ? idrecuperaciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recuperaciones)) {
            return false;
        }
        Recuperaciones other = (Recuperaciones) object;
        if ((this.idrecuperaciones == null && other.idrecuperaciones != null) || (this.idrecuperaciones != null && !this.idrecuperaciones.equals(other.idrecuperaciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Recuperaciones[ idrecuperaciones=" + idrecuperaciones + " ]";
    }

    @XmlTransient
    public List<Relacionlogrosrecuperaciones> getRelacionlogrosrecuperacionesList() {
        return relacionlogrosrecuperacionesList;
    }

    public void setRelacionlogrosrecuperacionesList(List<Relacionlogrosrecuperaciones> relacionlogrosrecuperacionesList) {
        this.relacionlogrosrecuperacionesList = relacionlogrosrecuperacionesList;
    }
    
}
