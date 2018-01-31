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
@Table(name = "Relacioncontenidosdimensiones")
public class Relacioncontenidosdimensiones implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelacioncontenidosdimensiones")
    private Long idrelacioncontenidosdimensiones;
    
    @JoinColumn(name = "relaciondimensionesasignaturasano", referencedColumnName = "idrelaciondimensionesasignaturasano")
    @ManyToOne
    private Relaciondimensionesasignaturasano relaciondimensionesasignaturasano;
    
    @JoinColumn(name = "periodos", referencedColumnName = "idperiodos")
    @ManyToOne
    private Periodos periodos;
    
    @JoinColumn(name = "contenidos", referencedColumnName = "idcontenidos")
    @ManyToOne
    private Contenidos contenidos;
    

    public Relacioncontenidosdimensiones() {
    }

    public Relacioncontenidosdimensiones(Long idrelacioncontenidosdimensiones) {
        this.idrelacioncontenidosdimensiones = idrelacioncontenidosdimensiones;
    }

    public Long getIdrelacioncontenidosdimensiones() {
		return idrelacioncontenidosdimensiones;
	}

	public void setIdrelacioncontenidosdimensiones(Long idrelacioncontenidosdimensiones) {
		this.idrelacioncontenidosdimensiones = idrelacioncontenidosdimensiones;
	}

	public Contenidos getContenidos() {
		return contenidos;
	}

	public void setContenidos(Contenidos contenidos) {
		this.contenidos = contenidos;
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

    


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelacioncontenidosdimensiones != null ? idrelacioncontenidosdimensiones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relacioncontenidosdimensiones)) {
            return false;
        }
        Relacioncontenidosdimensiones other = (Relacioncontenidosdimensiones) object;
        if ((this.idrelacioncontenidosdimensiones == null && other.idrelacioncontenidosdimensiones != null) || (this.idrelacioncontenidosdimensiones != null && !this.idrelacioncontenidosdimensiones.equals(other.idrelacioncontenidosdimensiones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relacioncontenidosdimensiones[ idrelacioncontenidosdimensiones=" + idrelacioncontenidosdimensiones + " ]";
    }

  
}
