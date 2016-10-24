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
@Table(name = "relacionboletineslogros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relacionboletineslogros.findAll", query = "SELECT r FROM Relacionboletineslogros r"),
    @NamedQuery(name = "Relacionboletineslogros.findByIdrelacionboletineslogros", query = "SELECT r FROM Relacionboletineslogros r WHERE r.idrelacionboletineslogros = :idrelacionboletineslogros")})
public class Relacionboletineslogros implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelacionboletineslogros")
    private Long idrelacionboletineslogros;
    @JoinColumn(name = "registromatriculas", referencedColumnName = "idregistromatriculas")
    @ManyToOne(optional = false)
    private Registromatriculas registromatriculas;
    @OneToMany(mappedBy = "relacionboletineslogros")
    private List<Relacionnotaslogrosdimensionboletin> relacionnotaslogrosdimensionboletinList;

    public Relacionboletineslogros() {
    }

    public Relacionboletineslogros(Long idrelacionboletineslogros) {
        this.idrelacionboletineslogros = idrelacionboletineslogros;
    }

    public Long getIdrelacionboletineslogros() {
        return idrelacionboletineslogros;
    }

    public void setIdrelacionboletineslogros(Long idrelacionboletineslogros) {
        this.idrelacionboletineslogros = idrelacionboletineslogros;
    }

    public Registromatriculas getRegistromatriculas() {
        return registromatriculas;
    }

    public void setRegistromatriculas(Registromatriculas registromatriculas) {
        this.registromatriculas = registromatriculas;
    }

    @XmlTransient
    public List<Relacionnotaslogrosdimensionboletin> getRelacionnotaslogrosdimensionboletinList() {
        return relacionnotaslogrosdimensionboletinList;
    }

    public void setRelacionnotaslogrosdimensionboletinList(List<Relacionnotaslogrosdimensionboletin> relacionnotaslogrosdimensionboletinList) {
        this.relacionnotaslogrosdimensionboletinList = relacionnotaslogrosdimensionboletinList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelacionboletineslogros != null ? idrelacionboletineslogros.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relacionboletineslogros)) {
            return false;
        }
        Relacionboletineslogros other = (Relacionboletineslogros) object;
        if ((this.idrelacionboletineslogros == null && other.idrelacionboletineslogros != null) || (this.idrelacionboletineslogros != null && !this.idrelacionboletineslogros.equals(other.idrelacionboletineslogros))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relacionboletineslogros[ idrelacionboletineslogros=" + idrelacionboletineslogros + " ]";
    }
    
}
