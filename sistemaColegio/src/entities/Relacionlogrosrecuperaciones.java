/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.Periodos;
import entities.Recuperaciones;
import entities.Relacionlogrosdimensiones;
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
@Table(name = "relacionlogrosrecuperaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relacionlogrosrecuperaciones.findAll", query = "SELECT r FROM Relacionlogrosrecuperaciones r"),
    @NamedQuery(name = "Relacionlogrosrecuperaciones.findByIdrelacionlogrosrecuperaciones", query = "SELECT r FROM Relacionlogrosrecuperaciones r WHERE r.idrelacionlogrosrecuperaciones = :idrelacionlogrosrecuperaciones")})
public class Relacionlogrosrecuperaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelacionlogrosrecuperaciones")
    private Long idrelacionlogrosrecuperaciones;
    @JoinColumn(name = "relacionlogrosdimensiones", referencedColumnName = "idrelacionlogrosdimensiones")
    @ManyToOne(optional = false)
    private Relacionlogrosdimensiones relacionlogrosdimensiones;
    @JoinColumn(name = "recuperaciones", referencedColumnName = "idrecuperaciones")
    @ManyToOne(optional = false)
    private Recuperaciones recuperaciones;
    @JoinColumn(name = "periodos", referencedColumnName = "idperiodos")
    @ManyToOne(optional = false)
    private Periodos periodos;

    public Relacionlogrosrecuperaciones() {
    }

    public Relacionlogrosrecuperaciones(Long idrelacionlogrosrecuperaciones) {
        this.idrelacionlogrosrecuperaciones = idrelacionlogrosrecuperaciones;
    }

    public Long getIdrelacionlogrosrecuperaciones() {
        return idrelacionlogrosrecuperaciones;
    }

    public void setIdrelacionlogrosrecuperaciones(Long idrelacionlogrosrecuperaciones) {
        this.idrelacionlogrosrecuperaciones = idrelacionlogrosrecuperaciones;
    }

    public Relacionlogrosdimensiones getRelacionlogrosdimensiones() {
        return relacionlogrosdimensiones;
    }

    public void setRelacionlogrosdimensiones(Relacionlogrosdimensiones relacionlogrosdimensiones) {
        this.relacionlogrosdimensiones = relacionlogrosdimensiones;
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
        hash += (idrelacionlogrosrecuperaciones != null ? idrelacionlogrosrecuperaciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relacionlogrosrecuperaciones)) {
            return false;
        }
        Relacionlogrosrecuperaciones other = (Relacionlogrosrecuperaciones) object;
        if ((this.idrelacionlogrosrecuperaciones == null && other.idrelacionlogrosrecuperaciones != null) || (this.idrelacionlogrosrecuperaciones != null && !this.idrelacionlogrosrecuperaciones.equals(other.idrelacionlogrosrecuperaciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "clasesAyuda.Relacionlogrosrecuperaciones[ idrelacionlogrosrecuperaciones=" + idrelacionlogrosrecuperaciones + " ]";
    }
    
}
