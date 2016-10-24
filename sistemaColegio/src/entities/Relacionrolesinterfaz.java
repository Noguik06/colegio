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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ricardo
 */
@Entity
@Table(name = "Relacionrolesinterfaz")
@XmlRootElement
public class Relacionrolesinterfaz implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relacionrolesinterfaz", nullable = false)
    private Long idrelacionrolesinterfaz;
    @JoinColumn(name = "roles", referencedColumnName = "idroles", nullable = false)
    @ManyToOne(optional = false)
    private Roles roles;
    @JoinColumn(name = "interfaz", referencedColumnName = "idinterfaz", nullable = false)
    @ManyToOne(optional = false)
    private Interfaz interfaz;
    
    
    public Relacionrolesinterfaz() {
    }
    
    public Long getIdrelacionrolesinterfaz() {
		return idrelacionrolesinterfaz;
	}
	public void setIdrelacionrolesinterfaz(Long idrelacionrolesinterfaz) {
		this.idrelacionrolesinterfaz = idrelacionrolesinterfaz;
	}
	public Roles getRoles() {
		return roles;
	}
	public void setRoles(Roles roles) {
		this.roles = roles;
	}
	public Interfaz getInterfaz() {
		return interfaz;
	}
	public void setInterfaz(Interfaz interfaz) {
		this.interfaz = interfaz;
	}



	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelacionrolesinterfaz != null ? idrelacionrolesinterfaz.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relacionrolesinterfaz)) {
            return false;
        }
        Relacionrolesinterfaz other = (Relacionrolesinterfaz) object;
        if ((this.idrelacionrolesinterfaz == null && other.idrelacionrolesinterfaz != null) || (this.idrelacionrolesinterfaz != null && !this.idrelacionrolesinterfaz.equals(other.idrelacionrolesinterfaz))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relacionrolesinterfaz[ idrelacionrolesinterfaz =" + idrelacionrolesinterfaz + " ]";
    }
    
}
