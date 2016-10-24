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
@Table(name = "logros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Logros.findAll", query = "SELECT l FROM Logros l"),
    @NamedQuery(name = "Logros.findByIdlogros", query = "SELECT l FROM Logros l WHERE l.idlogros = :idlogros"),
    @NamedQuery(name = "Logros.findByLogro", query = "SELECT l FROM Logros l WHERE l.logro = :logro")})
public class Logros implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlogros")
    private Long idlogros;
    @Size(max = 2147483647)
    @Column(name = "logro")
    private String logro;
    @OneToMany(mappedBy = "logros")
    private List<Relacionlogrosdimensiones> relacionlogrosdimensionesList;

    public Logros() {
    }

    public Logros(Long idlogros) {
        this.idlogros = idlogros;
    }

    public Long getIdlogros() {
        return idlogros;
    }

    public void setIdlogros(Long idlogros) {
        this.idlogros = idlogros;
    }

    public String getLogro() {
        return logro;
    }

    public void setLogro(String logro) {
        this.logro = logro;
    }

    @XmlTransient
    public List<Relacionlogrosdimensiones> getRelacionlogrosdimensionesList() {
        return relacionlogrosdimensionesList;
    }

    public void setRelacionlogrosdimensionesList(List<Relacionlogrosdimensiones> relacionlogrosdimensionesList) {
        this.relacionlogrosdimensionesList = relacionlogrosdimensionesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlogros != null ? idlogros.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Logros)) {
            return false;
        }
        Logros other = (Logros) object;
        if ((this.idlogros == null && other.idlogros != null) || (this.idlogros != null && !this.idlogros.equals(other.idlogros))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Logros[ idlogros=" + idlogros + " ]";
    }
    
}
