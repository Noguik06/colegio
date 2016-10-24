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
@Table(name = "notascalificables")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notascalificables.findAll", query = "SELECT n FROM Notascalificables n"),
    @NamedQuery(name = "Notascalificables.findByIdnotascalificables", query = "SELECT n FROM Notascalificables n WHERE n.idnotascalificables = :idnotascalificables"),
    @NamedQuery(name = "Notascalificables.findByValor", query = "SELECT n FROM Notascalificables n WHERE n.valor = :valor")})
public class Notascalificables implements Serializable {
    @Column(name = "fecharegistro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecharegistro;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idnotascalificables")
    private Long idnotascalificables;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private short valor;
    @JoinColumn(name = "relacionnotaslogrosdimensionboletin", referencedColumnName = "idrelacionnotaslogrosdimensionboletin")
    @ManyToOne
    private Relacionnotaslogrosdimensionboletin relacionnotaslogrosdimensionboletin;
    @JoinColumn(name = "registromatriculas", referencedColumnName = "idregistromatriculas")
    @ManyToOne
    private Registromatriculas registromatriculas;

    public Notascalificables() {
    }

    public Notascalificables(Long idnotascalificables) {
        this.idnotascalificables = idnotascalificables;
    }

    public Notascalificables(Long idnotascalificables, short valor) {
        this.idnotascalificables = idnotascalificables;
        this.valor = valor;
    }

    public Long getIdnotascalificables() {
        return idnotascalificables;
    }

    public void setIdnotascalificables(Long idnotascalificables) {
        this.idnotascalificables = idnotascalificables;
    }

    public short getValor() {
        return valor;
    }

    public void setValor(short valor) {
        this.valor = valor;
    }

    public Relacionnotaslogrosdimensionboletin getRelacionnotaslogrosdimensionboletin() {
        return relacionnotaslogrosdimensionboletin;
    }

    public void setRelacionnotaslogrosdimensionboletin(Relacionnotaslogrosdimensionboletin relacionnotaslogrosdimensionboletin) {
        this.relacionnotaslogrosdimensionboletin = relacionnotaslogrosdimensionboletin;
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
        hash += (idnotascalificables != null ? idnotascalificables.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notascalificables)) {
            return false;
        }
        Notascalificables other = (Notascalificables) object;
        if ((this.idnotascalificables == null && other.idnotascalificables != null) || (this.idnotascalificables != null && !this.idnotascalificables.equals(other.idnotascalificables))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Notascalificables[ idnotascalificables=" + idnotascalificables + " ]";
    }

    public Date getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(Date fecharegistro) {
        this.fecharegistro = fecharegistro;
    }
    
}
