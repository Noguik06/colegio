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
@Table(name = "Contenidos")
public class Contenidos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontenidos")
    private Long idcontenidos;
    @Size(max = 2147483647)
    @Column(name = "contenido")
    private String contenido;
    @OneToMany(mappedBy = "contenidos")
    private List<Relacioncontenidosdimensiones> relacioncontenidosdimensionesList;

    public Contenidos() {
    }

    public Contenidos(Long idcontenidos) {
        this.idcontenidos = idcontenidos;
    }

    public Long getIdcontenidos() {
		return idcontenidos;
	}

	public void setIdcontenidos(Long idcontenidos) {
		this.idcontenidos = idcontenidos;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	@XmlTransient
	public List<Relacioncontenidosdimensiones> getRelacioncontenidosdimensionesList() {
		return relacioncontenidosdimensionesList;
	}

	public void setRelacioncontenidosdimensionesList(
			List<Relacioncontenidosdimensiones> relacioncontenidosdimensionesList) {
		relacioncontenidosdimensionesList = relacioncontenidosdimensionesList;
	}
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontenidos != null ? idcontenidos.hashCode() : 0);
        return hash;
    }

   

	@Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contenidos)) {
            return false;
        }
        Contenidos other = (Contenidos) object;
        if ((this.idcontenidos == null && other.idcontenidos != null) || (this.idcontenidos != null && !this.idcontenidos.equals(other.idcontenidos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Contenidos[ idcontenidos=" + idcontenidos + " ]";
    }
    
}
