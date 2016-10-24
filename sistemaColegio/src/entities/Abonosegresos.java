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
@Table(name = "abonosegresos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Abonosegresos.findAll", query = "SELECT a FROM Abonosegresos a"),
    @NamedQuery(name = "Abonosegresos.findByIdabonosegresos", query = "SELECT a FROM Abonosegresos a WHERE a.idabonosegresos = :idabonosegresos"),
    @NamedQuery(name = "Abonosegresos.findByFecha", query = "SELECT a FROM Abonosegresos a WHERE a.fecha = :fecha"),
    @NamedQuery(name = "Abonosegresos.findByValor", query = "SELECT a FROM Abonosegresos a WHERE a.valor = :valor")})
public class Abonosegresos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idabonosegresos")
    private Long idabonosegresos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valor")
    private Double valor;
    @JoinColumn(name = "pagos", referencedColumnName = "idpagos")
    @ManyToOne(optional = false)
    private Pagos pagos;

    public Abonosegresos() {
    }

    public Abonosegresos(Long idabonosegresos) {
        this.idabonosegresos = idabonosegresos;
    }

    public Abonosegresos(Long idabonosegresos, Date fecha) {
        this.idabonosegresos = idabonosegresos;
        this.fecha = fecha;
    }

    public Long getIdabonosegresos() {
        return idabonosegresos;
    }

    public void setIdabonosegresos(Long idabonosegresos) {
        this.idabonosegresos = idabonosegresos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Pagos getPagos() {
        return pagos;
    }

    public void setPagos(Pagos pagos) {
        this.pagos = pagos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idabonosegresos != null ? idabonosegresos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Abonosegresos)) {
            return false;
        }
        Abonosegresos other = (Abonosegresos) object;
        if ((this.idabonosegresos == null && other.idabonosegresos != null) || (this.idabonosegresos != null && !this.idabonosegresos.equals(other.idabonosegresos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Abonosegresos[ idabonosegresos=" + idabonosegresos + " ]";
    }
    
}
