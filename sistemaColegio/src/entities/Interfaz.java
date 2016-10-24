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
import javax.persistence.EmbeddedId;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ricardo
 */
@Entity
@Table(name = "interfaz")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Interfaz.findAll", query = "SELECT i FROM Interfaz i"),
    @NamedQuery(name = "Interfaz.findByInterfaz", query = "SELECT i FROM Interfaz i WHERE i.idinterfaz= :idinterfaz"),
    @NamedQuery(name = "Interfaz.findByDescripcion", query = "SELECT i FROM Interfaz i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "Interfaz.findByOrden", query = "SELECT i FROM Interfaz i WHERE i.orden = :orden")})
public class Interfaz implements Serializable {
    private static final long serialVersionUID = 1L;    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinterfaz")
    private Long idinterfaz;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;
    @Column(name = "orden")
    private Integer orden;
    @Size(max = 200)
    @Column(name = "ruta")
    private String ruta;
    @JoinColumn(name = "modulo", referencedColumnName = "modulo")
    @ManyToOne(optional = false)
    private Modulos modulos;    

    public Interfaz() {
    }

    public Long getIdinterfaz() {
		return idinterfaz;
	}

	public void setIdinterfaz(Long idinterfaz) {
		this.idinterfaz = idinterfaz;
	}
	
	public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Modulos getModulos() {
        return modulos;
    }

    public void setModulos(Modulos modulos) {
        this.modulos = modulos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idinterfaz != null ? idinterfaz.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Interfaz)) {
            return false;
        }
        Interfaz other = (Interfaz) object;
        if ((this.idinterfaz == null && other.idinterfaz != null) || (this.idinterfaz != null && !this.idinterfaz.equals(other.idinterfaz))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Interfaz[ interfazPK=" + idinterfaz + " ]";
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
