/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "relacionpagousuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relacionpagousuarios.findAll", query = "SELECT r FROM Relacionpagousuarios r"),
    @NamedQuery(name = "Relacionpagousuarios.findByIdrelacionpagousuarios", query = "SELECT r FROM Relacionpagousuarios r WHERE r.idrelacionpagousuarios = :idrelacionpagousuarios")})
public class Relacionpagousuarios implements Serializable {
    @Column(name = "saldo")
    private Double saldo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "valorreal")
    private Double valorreal;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelacionpagousuarios")
    private Long idrelacionpagousuarios;
    @JoinColumn(name = "usuarios", referencedColumnName = "idusuarios")
    @ManyToOne
    private Usuarios usuarios;
    @JoinColumn(name = "pagos", referencedColumnName = "idpagos")
    @ManyToOne
    private Pagos pagos;
    @OneToMany(mappedBy = "relacionpagousuarios")
    private List<Abonospagos> abonospagosList;

    public Relacionpagousuarios() {
    }

    public Relacionpagousuarios(Long idrelacionpagousuarios) {
        this.idrelacionpagousuarios = idrelacionpagousuarios;
    }

    public Long getIdrelacionpagousuarios() {
        return idrelacionpagousuarios;
    }

    public void setIdrelacionpagousuarios(Long idrelacionpagousuarios) {
        this.idrelacionpagousuarios = idrelacionpagousuarios;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public Pagos getPagos() {
        return pagos;
    }

    public void setPagos(Pagos pagos) {
        this.pagos = pagos;
    }

    @XmlTransient
    public List<Abonospagos> getAbonospagosList() {
        return abonospagosList;
    }

    public void setAbonospagosList(List<Abonospagos> abonospagosList) {
        this.abonospagosList = abonospagosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelacionpagousuarios != null ? idrelacionpagousuarios.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relacionpagousuarios)) {
            return false;
        }
        Relacionpagousuarios other = (Relacionpagousuarios) object;
        if ((this.idrelacionpagousuarios == null && other.idrelacionpagousuarios != null) || (this.idrelacionpagousuarios != null && !this.idrelacionpagousuarios.equals(other.idrelacionpagousuarios))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relacionpagousuarios[ idrelacionpagousuarios=" + idrelacionpagousuarios + " ]";
    }

    public Double getValorreal() {
        return valorreal;
    }

    public void setValorreal(Double valorreal) {
        this.valorreal = valorreal;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
    
}
