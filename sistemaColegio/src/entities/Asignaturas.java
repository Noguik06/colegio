/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "asignaturas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asignaturas.findAll", query = "SELECT a FROM Asignaturas a"),
    @NamedQuery(name = "Asignaturas.findByIdasignaturas", query = "SELECT a FROM Asignaturas a WHERE a.idasignaturas = :idasignaturas"),
    @NamedQuery(name = "Asignaturas.findByNombre", query = "SELECT a FROM Asignaturas a WHERE a.nombre = :nombre")})
public class Asignaturas implements Serializable {
    @Column(name = "tipoasignatura")
    private Short tipoasignatura;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idasignaturas")
    private Long idasignaturas;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignaturas")
    private List<Relacionasignaturaperiodos> relacionasignaturaperiodosList;

    public Asignaturas() {
    }

    public Asignaturas(Long idasignaturas) {
        this.idasignaturas = idasignaturas;
    }

    public Asignaturas(Long idasignaturas, String nombre) {
        this.idasignaturas = idasignaturas;
        this.nombre = nombre;
    }

    public Long getIdasignaturas() {
        return idasignaturas;
    }

    public void setIdasignaturas(Long idasignaturas) {
        this.idasignaturas = idasignaturas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Relacionasignaturaperiodos> getRelacionasignaturaperiodosList() {
        return relacionasignaturaperiodosList;
    }

    public void setRelacionasignaturaperiodosList(List<Relacionasignaturaperiodos> relacionasignaturaperiodosList) {
        this.relacionasignaturaperiodosList = relacionasignaturaperiodosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idasignaturas != null ? idasignaturas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asignaturas)) {
            return false;
        }
        Asignaturas other = (Asignaturas) object;
        if ((this.idasignaturas == null && other.idasignaturas != null) || (this.idasignaturas != null && !this.idasignaturas.equals(other.idasignaturas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Asignaturas[ idasignaturas=" + idasignaturas + " ]";
    }

    public Short getTipoasignatura() {
        return tipoasignatura;
    }

    public void setTipoasignatura(Short tipoasignatura) {
        this.tipoasignatura = tipoasignatura;
    }
    
}
