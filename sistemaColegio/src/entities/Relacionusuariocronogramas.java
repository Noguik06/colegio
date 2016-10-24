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
@Table(name = "relacionusuariocronogramas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relacionusuariocronogramas.findAll", query = "SELECT r FROM Relacionusuariocronogramas r"),
    @NamedQuery(name = "Relacionusuariocronogramas.findByIdrelacionusuariocronogramas", query = "SELECT r FROM Relacionusuariocronogramas r WHERE r.idrelacionusuariocronogramas = :idrelacionusuariocronogramas")})
public class Relacionusuariocronogramas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelacionusuariocronogramas")
    private Long idrelacionusuariocronogramas;
    @JoinColumn(name = "usuarios", referencedColumnName = "idusuarios")
    @ManyToOne(optional = false)
    private Usuarios usuarios;
    @JoinColumn(name = "cronogramas", referencedColumnName = "idcronogramas")
    @ManyToOne(optional = false)
    private Cronogramas cronogramas;

    public Relacionusuariocronogramas() {
    }

    public Relacionusuariocronogramas(Long idrelacionusuariocronogramas) {
        this.idrelacionusuariocronogramas = idrelacionusuariocronogramas;
    }

    public Long getIdrelacionusuariocronogramas() {
        return idrelacionusuariocronogramas;
    }

    public void setIdrelacionusuariocronogramas(Long idrelacionusuariocronogramas) {
        this.idrelacionusuariocronogramas = idrelacionusuariocronogramas;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public Cronogramas getCronogramas() {
        return cronogramas;
    }

    public void setCronogramas(Cronogramas cronogramas) {
        this.cronogramas = cronogramas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelacionusuariocronogramas != null ? idrelacionusuariocronogramas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relacionusuariocronogramas)) {
            return false;
        }
        Relacionusuariocronogramas other = (Relacionusuariocronogramas) object;
        if ((this.idrelacionusuariocronogramas == null && other.idrelacionusuariocronogramas != null) || (this.idrelacionusuariocronogramas != null && !this.idrelacionusuariocronogramas.equals(other.idrelacionusuariocronogramas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relacionusuariocronogramas[ idrelacionusuariocronogramas=" + idrelacionusuariocronogramas + " ]";
    }
    
}
