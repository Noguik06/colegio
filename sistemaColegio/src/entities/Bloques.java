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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "Bloques")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "bloques.findAll", query = "SELECT b FROM Bloques b")})
public class Bloques implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idbloques")
    private Long idbloques;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nombre")
    private String nombre;
    
    public Bloques() {
    }

    public Bloques(Long idbloques) {
        this.idbloques = idbloques;
    }

    public Long getIdbloques() {
        return idbloques;
    }

    public void setIdbloques(Long idbloques) {
        this.idbloques = idbloques;
    }
    
    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idbloques != null ? idbloques.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bloqueshorarios)) {
            return false;
        }
        Bloques other = (Bloques) object;
        if ((this.idbloques == null && other.idbloques != null) || (this.idbloques != null && !this.idbloques.equals(other.idbloques))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Bloques[ idbloques=" + idbloques + " ]";
    }
    
}
