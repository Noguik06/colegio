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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "Pensiones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "pensiones.findAll", query = "SELECT b FROM Pensiones b")})
public class Pensiones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpensiones")
    private Long idpensiones;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private double valor;
    
    @JoinColumn(name = "idregistromatriculas", referencedColumnName = "idregistromatriculas")
    @ManyToOne(optional = false)
    private Registromatriculas registromatriculas;
    
    public Pensiones() {
    }

    public Pensiones(Long idpensiones) {
        this.idpensiones = idpensiones;
    }

	public Long getIdpensiones() {
		return idpensiones;
	}

	public void setIdpensiones(Long idpensiones) {
		this.idpensiones = idpensiones;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Registromatriculas getRegistromatriculas() {
		return registromatriculas;
	}

	public void setRegistromatriculas(Registromatriculas registromatriculas) {
		this.registromatriculas = registromatriculas;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpensiones != null ? idpensiones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pensiones)) {
            return false;
        }
        Pensiones other = (Pensiones) object;
        if ((this.idpensiones == null && other.idpensiones != null) || (this.idpensiones != null && !this.idpensiones.equals(other.idpensiones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pensiones[ idPensiones=" + idpensiones + " ]";
    }
    
}
