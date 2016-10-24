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
@Table(name = "dimensiones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dimensiones.findAll", query = "SELECT d FROM Dimensiones d"),
    @NamedQuery(name = "Dimensiones.findByIddimensiones", query = "SELECT d FROM Dimensiones d WHERE d.iddimensiones = :iddimensiones"),
    @NamedQuery(name = "Dimensiones.findByNombre", query = "SELECT d FROM Dimensiones d WHERE d.nombre = :nombre")})
public class Dimensiones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddimensiones")
    private Long iddimensiones;
    @Size(max = 500)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "dimensiones")
    private List<Relaciondimensionesasignaturasano> relaciondimensionesasignaturasanoList;

    public Dimensiones() {
    }

    public Dimensiones(Long iddimensiones) {
        this.iddimensiones = iddimensiones;
    }

    public Long getIddimensiones() {
        return iddimensiones;
    }

    public void setIddimensiones(Long iddimensiones) {
        this.iddimensiones = iddimensiones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Relaciondimensionesasignaturasano> getRelaciondimensionesasignaturasanoList() {
        return relaciondimensionesasignaturasanoList;
    }

    public void setRelaciondimensionesasignaturasanoList(List<Relaciondimensionesasignaturasano> relaciondimensionesasignaturasanoList) {
        this.relaciondimensionesasignaturasanoList = relaciondimensionesasignaturasanoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddimensiones != null ? iddimensiones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dimensiones)) {
            return false;
        }
        Dimensiones other = (Dimensiones) object;
        if ((this.iddimensiones == null && other.iddimensiones != null) || (this.iddimensiones != null && !this.iddimensiones.equals(other.iddimensiones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Dimensiones[ iddimensiones=" + iddimensiones + " ]";
    }
    
}
