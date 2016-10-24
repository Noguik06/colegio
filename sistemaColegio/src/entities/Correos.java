/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "correos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Correos.findAll", query = "SELECT c FROM Correos c"),
    @NamedQuery(name = "Correos.findByIdcorreos", query = "SELECT c FROM Correos c WHERE c.idcorreos = :idcorreos"),
    @NamedQuery(name = "Correos.findByContenido", query = "SELECT c FROM Correos c WHERE c.contenido = :contenido"),
    @NamedQuery(name = "Correos.findByAsunto", query = "SELECT c FROM Correos c WHERE c.asunto = :asunto"),
    @NamedQuery(name = "Correos.findByBanderausuariosde", query = "SELECT c FROM Correos c WHERE c.banderausuariosde = :banderausuariosde")})
public class Correos implements Serializable {
	@Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcorreos")
    private Long idcorreos;
    @Basic(optional = false)
    @NotNull 
    @Column(name = "contenido")
    private String contenido;
    @Column(name = "asunto")
    private String asunto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "banderausuariosde")
    private boolean banderausuariosde;
    @JoinColumn(name = "idusuariosde", referencedColumnName = "idusuarios")
    @ManyToOne
    private Usuarios idusuariosde;

    public Correos() {
    }

    public Correos(Long idcorreos) {
        this.idcorreos = idcorreos;
    }

    public Correos(Long idcorreos, String contenido, boolean banderausuariosde) {
        this.idcorreos = idcorreos;
        this.contenido = contenido;
        this.banderausuariosde = banderausuariosde;
    }

    public Long getIdcorreos() {
        return idcorreos;
    }

    public void setIdcorreos(Long idcorreos) {
        this.idcorreos = idcorreos;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public boolean getBanderausuariosde() {
        return banderausuariosde;
    }

    public void setBanderausuariosde(boolean banderausuariosde) {
        this.banderausuariosde = banderausuariosde;
    }

    public Usuarios getIdusuariosde() {
        return idusuariosde;
    }

    public void setIdusuariosde(Usuarios idusuariosde) {
        this.idusuariosde = idusuariosde;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcorreos != null ? idcorreos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Correos)) {
            return false;
        }
        Correos other = (Correos) object;
        if ((this.idcorreos == null && other.idcorreos != null) || (this.idcorreos != null && !this.idcorreos.equals(other.idcorreos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Correos[ idcorreos=" + idcorreos + " ]";
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
}
