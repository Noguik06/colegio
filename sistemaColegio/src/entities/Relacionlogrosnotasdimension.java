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
@Table(name = "relacionlogrosnotasdimension")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relacionlogrosnotasdimension.findAll", query = "SELECT r FROM Relacionlogrosnotasdimension r"),
    @NamedQuery(name = "Relacionlogrosnotasdimension.findByIdrelacionlogrosnotasdimension", query = "SELECT r FROM Relacionlogrosnotasdimension r WHERE r.idrelacionlogrosnotasdimension = :idrelacionlogrosnotasdimension")})
public class Relacionlogrosnotasdimension implements Serializable {
    @JoinColumn(name = "relacionnotaslogrosdimensionboletin", referencedColumnName = "idrelacionnotaslogrosdimensionboletin")
    @ManyToOne
    private Relacionnotaslogrosdimensionboletin relacionnotaslogrosdimensionboletin;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelacionlogrosnotasdimension")
    private Long idrelacionlogrosnotasdimension;
    @JoinColumn(name = "relacionnotasdimension", referencedColumnName = "idrelacionnotasdimesion")
    @ManyToOne
    private Relacionnotasdimension relacionnotasdimension;
    @JoinColumn(name = "relacionlogrosdimension", referencedColumnName = "idrelacionlogrosdimensiones")
    @ManyToOne
    private Relacionlogrosdimensiones relacionlogrosdimension;

    public Relacionlogrosnotasdimension() {
    }

    public Relacionlogrosnotasdimension(Long idrelacionlogrosnotasdimension) {
        this.idrelacionlogrosnotasdimension = idrelacionlogrosnotasdimension;
    }

    public Long getIdrelacionlogrosnotasdimension() {
        return idrelacionlogrosnotasdimension;
    }

    public void setIdrelacionlogrosnotasdimension(Long idrelacionlogrosnotasdimension) {
        this.idrelacionlogrosnotasdimension = idrelacionlogrosnotasdimension;
    }

    public Relacionnotasdimension getRelacionnotasdimension() {
        return relacionnotasdimension;
    }

    public void setRelacionnotasdimension(Relacionnotasdimension relacionnotasdimension) {
        this.relacionnotasdimension = relacionnotasdimension;
    }

    public Relacionlogrosdimensiones getRelacionlogrosdimension() {
        return relacionlogrosdimension;
    }

    public void setRelacionlogrosdimension(Relacionlogrosdimensiones relacionlogrosdimension) {
        this.relacionlogrosdimension = relacionlogrosdimension;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelacionlogrosnotasdimension != null ? idrelacionlogrosnotasdimension.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relacionlogrosnotasdimension)) {
            return false;
        }
        Relacionlogrosnotasdimension other = (Relacionlogrosnotasdimension) object;
        if ((this.idrelacionlogrosnotasdimension == null && other.idrelacionlogrosnotasdimension != null) || (this.idrelacionlogrosnotasdimension != null && !this.idrelacionlogrosnotasdimension.equals(other.idrelacionlogrosnotasdimension))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relacionlogrosnotasdimension[ idrelacionlogrosnotasdimension=" + idrelacionlogrosnotasdimension + " ]";
    }

    public Relacionnotaslogrosdimensionboletin getRelacionnotaslogrosdimensionboletin() {
        return relacionnotaslogrosdimensionboletin;
    }

    public void setRelacionnotaslogrosdimensionboletin(Relacionnotaslogrosdimensionboletin relacionnotaslogrosdimensionboletin) {
        this.relacionnotaslogrosdimensionboletin = relacionnotaslogrosdimensionboletin;
    }
    
}
