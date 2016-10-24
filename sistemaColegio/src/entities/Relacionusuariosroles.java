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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "relacionusuariosroles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relacionusuariosroles.findAll", query = "SELECT r FROM Relacionusuariosroles r"),
    @NamedQuery(name = "Relacionusuariosroles.findByIdrelacionusuariosroles", query = "SELECT r FROM Relacionusuariosroles r WHERE r.idrelacionusuariosroles = :idrelacionusuariosroles")})
public class Relacionusuariosroles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelacionusuariosroles")
    private Long idrelacionusuariosroles;
    @JoinColumn(name = "idusuarios", referencedColumnName = "idusuarios")
    @ManyToOne
    private Usuarios usuarios;
    @JoinColumn(name = "idroles", referencedColumnName = "idroles")
    @ManyToOne
    private Roles roles;

    public Relacionusuariosroles() {
    }

    public Relacionusuariosroles(Long idrelacionusuariosroles) {
        this.idrelacionusuariosroles = idrelacionusuariosroles;
    }

    public Long getIdrelacionusuariosroles() {
        return idrelacionusuariosroles;
    }

    public void setIdrelacionusuariosroles(Long idrelacionusuariosroles) {
        this.idrelacionusuariosroles = idrelacionusuariosroles;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuario(Usuarios idusuarios) {
        this.usuarios = idusuarios;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles idroles) {
        this.roles = idroles;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelacionusuariosroles != null ? idrelacionusuariosroles.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relacionusuariosroles)) {
            return false;
        }
        Relacionusuariosroles other = (Relacionusuariosroles) object;
        if ((this.idrelacionusuariosroles == null && other.idrelacionusuariosroles != null) || (this.idrelacionusuariosroles != null && !this.idrelacionusuariosroles.equals(other.idrelacionusuariosroles))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relacionusuariosroles[ idrelacionusuariosroles=" + idrelacionusuariosroles + " ]";
    }
    
}
