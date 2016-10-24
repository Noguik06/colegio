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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "abonospagos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Abonospagos.findAll", query = "SELECT a FROM Abonospagos a"),
    @NamedQuery(name = "Abonospagos.findByIdabonospagos", query = "SELECT a FROM Abonospagos a WHERE a.idabonospagos = :idabonospagos"),
    @NamedQuery(name = "Abonospagos.findByValor", query = "SELECT a FROM Abonospagos a WHERE a.valor = :valor"),
    @NamedQuery(name = "Abonospagos.findByFechaabono", query = "SELECT a FROM Abonospagos a WHERE a.fechaabono = :fechaabono")})
public class Abonospagos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idabonospagos")
    private Long idabonospagos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @Column(name = "fechaabono")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaabono;
    @JoinColumn(name = "relacionpagousuarios", referencedColumnName = "idrelacionpagousuarios")
    @ManyToOne
    private Relacionpagousuarios relacionpagousuarios;

    public Abonospagos() {
    }

    public Abonospagos(Long idabonospagos) {
        this.idabonospagos = idabonospagos;
    }

    public Long getIdabonospagos() {
        return idabonospagos;
    }

    public void setIdabonospagos(Long idabonospagos) {
        this.idabonospagos = idabonospagos;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getFechaabono() {
        return fechaabono;
    }

    public void setFechaabono(Date fechaabono) {
        this.fechaabono = fechaabono;
    }

    public Relacionpagousuarios getRelacionpagousuarios() {
        return relacionpagousuarios;
    }

    public void setRelacionpagousuarios(Relacionpagousuarios relacionpagousuarios) {
        this.relacionpagousuarios = relacionpagousuarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idabonospagos != null ? idabonospagos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Abonospagos)) {
            return false;
        }
        Abonospagos other = (Abonospagos) object;
        if ((this.idabonospagos == null && other.idabonospagos != null) || (this.idabonospagos != null && !this.idabonospagos.equals(other.idabonospagos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Abonospagos[ idabonospagos=" + idabonospagos + " ]";
    }
    
}
