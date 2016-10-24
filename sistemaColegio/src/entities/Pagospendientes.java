/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "Pagospendientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "pagospendientes.findAll", query = "SELECT b FROM Pagospendientes b")})
public class Pagospendientes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpagospendientes")
    private Long idpagospendientes;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private double valor;
    
    @Column(name = "saldopendiente")
    private double saldopendiente;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
  
    
    @Column(name = "fechapagooportuno")
    @Temporal(TemporalType.DATE)
    private Date fechapagooportuno;
    
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    @JoinColumn(name = "idusuarios", referencedColumnName = "idusuarios")
    @ManyToOne(optional = false)
    private Usuarios usuarios;
    
    public Pagospendientes() {
    }

    public Pagospendientes(Long idpagospendientes) {
        this.idpagospendientes = idpagospendientes;
    }
    
	public Long getIdpagospendientes() {
		return idpagospendientes;
	}

	public void setIdpagospendientes(Long idpagospendientes) {
		this.idpagospendientes = idpagospendientes;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getFechapagooportuno() {
		return fechapagooportuno;
	}

	public void setFechapagooportuno(Date fechapagooportunpo) {
		this.fechapagooportuno = fechapagooportunpo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Usuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public double getSaldopendiente() {
		return saldopendiente;
	}

	public void setSaldopendiente(double saldopendiente) {
		this.saldopendiente = saldopendiente;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idpagospendientes != null ? idpagospendientes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagospendientes)) {
            return false;
        }
        Pagospendientes other = (Pagospendientes) object;
        if ((this.idpagospendientes == null && other.idpagospendientes != null) || (this.idpagospendientes != null && !this.idpagospendientes.equals(other.idpagospendientes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Pagospendientes[ idpagospendientes=" + idpagospendientes + " ]";
    }
    
}
