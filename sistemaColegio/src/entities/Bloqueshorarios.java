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
@Table(name = "bloqueshorarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "bloqueshorarios.findAll", query = "SELECT b FROM Bloqueshorarios b")})
public class Bloqueshorarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idbloqueshorarios")
    private Long idbloqueshorarios;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horainicio")
    @Temporal(TemporalType.TIME)
    private Date horainicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horafin")
    @Temporal(TemporalType.TIME)
    private Date horafin;
    @JoinColumn(name = "bloques", referencedColumnName = "idbloques")
    @ManyToOne(optional = false)
    private Bloques bloques;
    
    public Bloqueshorarios() {
    }

    public Bloqueshorarios(Long idbloques) {
        this.idbloqueshorarios = idbloques;
    }

    public Bloqueshorarios(Long idbloques, Date horainicio, Date horafin) {
        this.idbloqueshorarios = idbloques;
        this.horainicio = horainicio;
        this.horafin = horafin;
    }

    public Long getIdbloqueshorarios() {
        return idbloqueshorarios;
    }

    public void setIdbloqueshorarios(Long idbloques) {
        this.idbloqueshorarios = idbloques;
    }

    public Date getHorainicio() {
        return horainicio;
    }

    public void setHorainicio(Date horainicio) {
        this.horainicio = horainicio;
    }

    public Date getHorafin() {
        return horafin;
    }

    public void setHorafin(Date horafin) {
        this.horafin = horafin;
    }

	public Bloques getBloques() {
		return bloques;
	}

	public void setBloques(Bloques bloques) {
		this.bloques = bloques;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idbloqueshorarios != null ? idbloqueshorarios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bloqueshorarios)) {
            return false;
        }
        Bloqueshorarios other = (Bloqueshorarios) object;
        if ((this.idbloqueshorarios == null && other.idbloqueshorarios != null) || (this.idbloqueshorarios != null && !this.idbloqueshorarios.equals(other.idbloqueshorarios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Bloques[ idbloques=" + idbloqueshorarios + " ]";
    }
    
}
