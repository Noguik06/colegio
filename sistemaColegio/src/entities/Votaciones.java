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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "votaciones")
@XmlRootElement
public class Votaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvotaciones")
    private Integer idvotaciones;
    @Column(name = "valor")
    private int valor;
    @Column(name = "tipo")
    private int tipo;
    @JoinColumn(name = "idusuarios", referencedColumnName = "idusuarios")
    @ManyToOne(optional = false)
    private Usuarios usuarios;

    public Votaciones() {
    }

    public Votaciones(Integer idvotaciones) {
        this.idvotaciones = idvotaciones;
    }
    
    public Integer getIdvotaciones() {
		return idvotaciones;
	}

	public void setIdvotaciones(Integer idvotaciones) {
		this.idvotaciones = idvotaciones;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Usuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idvotaciones != null ? idvotaciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Votaciones)) {
            return false;
        }
        Votaciones other = (Votaciones) object;
        if ((this.idvotaciones == null && other.idvotaciones != null) || (this.idvotaciones != null && !this.idvotaciones.equals(other.idvotaciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idvotaciones + "";
    }
    
}
