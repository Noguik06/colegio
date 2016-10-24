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
@Table(name = "relacioncorreousuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relacioncorreousuarios.findAll", query = "SELECT r FROM Relacioncorreousuarios r"),
    @NamedQuery(name = "Relacioncorreousuarios.findByIdrelacioncorreousuarios", query = "SELECT r FROM Relacioncorreousuarios r WHERE r.idrelacioncorreousuarios = :idrelacioncorreousuarios"),
    @NamedQuery(name = "Relacioncorreousuarios.findByBanderausuariospara", query = "SELECT r FROM Relacioncorreousuarios r WHERE r.banderausuariospara = :banderausuariospara")})
public class Relacioncorreousuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelacioncorreousuarios")
    private Long idrelacioncorreousuarios;
    @Basic(optional = false)
    @NotNull
    @Column(name = "banderausuariospara")
    private boolean banderausuariospara;
    @Basic(optional = false)
    @NotNull
    @Column(name = "leido")
    private boolean leido;
    @JoinColumn(name = "usuariospara", referencedColumnName = "idusuarios")
    @ManyToOne
    private Usuarios usuariospara;
    @JoinColumn(name = "correo", referencedColumnName = "idcorreos")
    @ManyToOne(optional = false)
    private Correos correo;

    public Relacioncorreousuarios() {
    }

    public Relacioncorreousuarios(Long idrelacioncorreousuarios) {
        this.idrelacioncorreousuarios = idrelacioncorreousuarios;
    }

    public Relacioncorreousuarios(Long idrelacioncorreousuarios, boolean banderausuariospara) {
        this.idrelacioncorreousuarios = idrelacioncorreousuarios;
        this.banderausuariospara = banderausuariospara;
    }

    public Long getIdrelacioncorreousuarios() {
        return idrelacioncorreousuarios;
    }

    public void setIdrelacioncorreousuarios(Long idrelacioncorreousuarios) {
        this.idrelacioncorreousuarios = idrelacioncorreousuarios;
    }

    public boolean getBanderausuariospara() {
        return banderausuariospara;
    }

    public void setBanderausuariospara(boolean banderausuariospara) {
        this.banderausuariospara = banderausuariospara;
    }

    public Usuarios getUsuariospara() {
        return usuariospara;
    }

    public void setUsuariospara(Usuarios usuariospara) {
        this.usuariospara = usuariospara;
    }

    public Correos getCorreo() {
        return correo;
    }

    public void setCorreo(Correos correo) {
        this.correo = correo;
    }
    
    public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelacioncorreousuarios != null ? idrelacioncorreousuarios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relacioncorreousuarios)) {
            return false;
        }
        Relacioncorreousuarios other = (Relacioncorreousuarios) object;
        if ((this.idrelacioncorreousuarios == null && other.idrelacioncorreousuarios != null) || (this.idrelacioncorreousuarios != null && !this.idrelacioncorreousuarios.equals(other.idrelacioncorreousuarios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relacioncorreousuarios[ idrelacioncorreousuarios=" + idrelacioncorreousuarios + " ]";
    }
    
}
