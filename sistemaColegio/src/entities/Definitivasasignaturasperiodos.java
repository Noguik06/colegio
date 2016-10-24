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
@Table(name = "definitivasasignaturasperiodos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Definitivasasignaturasperiodos.findAll", query = "SELECT d FROM Definitivasasignaturasperiodos d"),
    @NamedQuery(name = "Definitivasasignaturasperiodos.findByIddefinitivasasignaturasperiodos", query = "SELECT d FROM Definitivasasignaturasperiodos d WHERE d.iddefinitivasasignaturasperiodos = :iddefinitivasasignaturasperiodos")})
public class Definitivasasignaturasperiodos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddefinitivasasignaturasperiodos")
    private Long iddefinitivasasignaturasperiodos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor")
    private double valor;
    @JoinColumn(name = "relacionasignaturasperiodos", referencedColumnName = "idrelacionasignaturaperiodos")
    @ManyToOne(optional = false)
    private Relacionasignaturaperiodos relacionasignaturasperiodos;
    @JoinColumn(name = "registromatricula", referencedColumnName = "idregistromatriculas")
    @ManyToOne(optional = false)
    private Registromatriculas registromatricula;
    @JoinColumn(name = "periodos", referencedColumnName = "idperiodos")
    @ManyToOne(optional = false)
    private Periodos periodos;

    public Definitivasasignaturasperiodos() {
    }

    public Definitivasasignaturasperiodos(Long iddefinitivasasignaturasperiodos) {
        this.iddefinitivasasignaturasperiodos = iddefinitivasasignaturasperiodos;
    }

    public Long getIddefinitivasasignaturasperiodos() {
        return iddefinitivasasignaturasperiodos;
    }

    public void setIddefinitivasasignaturasperiodos(Long iddefinitivasasignaturasperiodos) {
        this.iddefinitivasasignaturasperiodos = iddefinitivasasignaturasperiodos;
    }

    public Relacionasignaturaperiodos getRelacionasignaturasperiodos() {
        return relacionasignaturasperiodos;
    }

    public void setRelacionasignaturasperiodos(Relacionasignaturaperiodos relacionasignaturasperiodos) {
        this.relacionasignaturasperiodos = relacionasignaturasperiodos;
    }

    public Registromatriculas getRegistromatricula() {
        return registromatricula;
    }

    public void setRegistromatricula(Registromatriculas registromatricula) {
        this.registromatricula = registromatricula;
    }

    public Periodos getPeriodos() {
        return periodos;
    }

    public void setPeriodos(Periodos periodos) {
        this.periodos = periodos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddefinitivasasignaturasperiodos != null ? iddefinitivasasignaturasperiodos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Definitivasasignaturasperiodos)) {
            return false;
        }
        Definitivasasignaturasperiodos other = (Definitivasasignaturasperiodos) object;
        if ((this.iddefinitivasasignaturasperiodos == null && other.iddefinitivasasignaturasperiodos != null) || (this.iddefinitivasasignaturasperiodos != null && !this.iddefinitivasasignaturasperiodos.equals(other.iddefinitivasasignaturasperiodos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Definitivasasignaturasperiodos[ iddefinitivasasignaturasperiodos=" + iddefinitivasasignaturasperiodos + " ]";
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
}
