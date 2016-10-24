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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "relacionlogrosdimensiones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relacionlogrosdimensiones.findAll", query = "SELECT r FROM Relacionlogrosdimensiones r"),
    @NamedQuery(name = "Relacionlogrosdimensiones.findByIdrelacionlogrosdimensiones", query = "SELECT r FROM Relacionlogrosdimensiones r WHERE r.idrelacionlogrosdimensiones = :idrelacionlogrosdimensiones")})
public class Relacionlogrosdimensiones implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "relacionlogrosdimensiones")
    private List<Relacionlogrosrecuperaciones> relacionlogrosrecuperacionesList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelacionlogrosdimensiones")
    private Long idrelacionlogrosdimensiones;
    @JoinColumn(name = "relaciondimensionesasignaturasano", referencedColumnName = "idrelaciondimensionesasignaturasano")
    @ManyToOne
    private Relaciondimensionesasignaturasano relaciondimensionesasignaturasano;
    @JoinColumn(name = "periodos", referencedColumnName = "idperiodos")
    @ManyToOne
    private Periodos periodos;
    @JoinColumn(name = "logros", referencedColumnName = "idlogros")
    @ManyToOne
    private Logros logros;
    @OneToMany(mappedBy = "relacionlogrosdimension")
    private List<Relacionlogrosnotasdimension> relacionlogrosnotasdimensionList;

    public Relacionlogrosdimensiones() {
    }

    public Relacionlogrosdimensiones(Long idrelacionlogrosdimensiones) {
        this.idrelacionlogrosdimensiones = idrelacionlogrosdimensiones;
    }

    public Long getIdrelacionlogrosdimensiones() {
        return idrelacionlogrosdimensiones;
    }

    public void setIdrelacionlogrosdimensiones(Long idrelacionlogrosdimensiones) {
        this.idrelacionlogrosdimensiones = idrelacionlogrosdimensiones;
    }

    public Relaciondimensionesasignaturasano getRelaciondimensionesasignaturasano() {
        return relaciondimensionesasignaturasano;
    }

    public void setRelaciondimensionesasignaturasano(Relaciondimensionesasignaturasano relaciondimensionesasignaturasano) {
        this.relaciondimensionesasignaturasano = relaciondimensionesasignaturasano;
    }

    public Periodos getPeriodos() {
        return periodos;
    }

    public void setPeriodos(Periodos periodos) {
        this.periodos = periodos;
    }

    public Logros getLogros() {
        return logros;
    }

    public void setLogros(Logros logros) {
        this.logros = logros;
    }

    @XmlTransient
    public List<Relacionlogrosnotasdimension> getRelacionlogrosnotasdimensionList() {
        return relacionlogrosnotasdimensionList;
    }

    public void setRelacionlogrosnotasdimensionList(List<Relacionlogrosnotasdimension> relacionlogrosnotasdimensionList) {
        this.relacionlogrosnotasdimensionList = relacionlogrosnotasdimensionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelacionlogrosdimensiones != null ? idrelacionlogrosdimensiones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relacionlogrosdimensiones)) {
            return false;
        }
        Relacionlogrosdimensiones other = (Relacionlogrosdimensiones) object;
        if ((this.idrelacionlogrosdimensiones == null && other.idrelacionlogrosdimensiones != null) || (this.idrelacionlogrosdimensiones != null && !this.idrelacionlogrosdimensiones.equals(other.idrelacionlogrosdimensiones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relacionlogrosdimensiones[ idrelacionlogrosdimensiones=" + idrelacionlogrosdimensiones + " ]";
    }

    @XmlTransient
    public List<Relacionlogrosrecuperaciones> getRelacionlogrosrecuperacionesList() {
        return relacionlogrosrecuperacionesList;
    }

    public void setRelacionlogrosrecuperacionesList(List<Relacionlogrosrecuperaciones> relacionlogrosrecuperacionesList) {
        this.relacionlogrosrecuperacionesList = relacionlogrosrecuperacionesList;
    }
    
}
