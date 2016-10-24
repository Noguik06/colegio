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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "relacionestudiantesacudientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relacionestudiantesacudientes.findAll", query = "SELECT r FROM Relacionestudiantesacudientes r"),
    @NamedQuery(name = "Relacionestudiantesacudientes.findByParentesco", query = "SELECT r FROM Relacionestudiantesacudientes r WHERE r.parentesco = :parentesco"),
    @NamedQuery(name = "Relacionestudiantesacudientes.findByIdrelacionestudiantesacudientes", query = "SELECT r FROM Relacionestudiantesacudientes r WHERE r.idrelacionestudiantesacudientes = :idrelacionestudiantesacudientes")})
public class Relacionestudiantesacudientes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "parentesco")
    private String parentesco;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelacionestudiantesacudientes")
    private Long idrelacionestudiantesacudientes;
    @JoinColumn(name = "estudiantes", referencedColumnName = "idestudiantes")
    @ManyToOne(optional = false)
    private Estudiantes estudiantes;
    @JoinColumn(name = "acudientes", referencedColumnName = "idacudientes")
    @ManyToOne(optional = false)
    private Acudientes acudientes;

    public Relacionestudiantesacudientes() {
    }

    public Relacionestudiantesacudientes(Long idrelacionestudiantesacudientes) {
        this.idrelacionestudiantesacudientes = idrelacionestudiantesacudientes;
    }

    public Relacionestudiantesacudientes(Long idrelacionestudiantesacudientes, String parentesco) {
        this.idrelacionestudiantesacudientes = idrelacionestudiantesacudientes;
        this.parentesco = parentesco;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public Long getIdrelacionestudiantesacudientes() {
        return idrelacionestudiantesacudientes;
    }

    public void setIdrelacionestudiantesacudientes(Long idrelacionestudiantesacudientes) {
        this.idrelacionestudiantesacudientes = idrelacionestudiantesacudientes;
    }

    public Estudiantes getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(Estudiantes estudiantes) {
        this.estudiantes = estudiantes;
    }

    public Acudientes getAcudientes() {
        return acudientes;
    }

    public void setAcudientes(Acudientes acudientes) {
        this.acudientes = acudientes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelacionestudiantesacudientes != null ? idrelacionestudiantesacudientes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relacionestudiantesacudientes)) {
            return false;
        }
        Relacionestudiantesacudientes other = (Relacionestudiantesacudientes) object;
        if ((this.idrelacionestudiantesacudientes == null && other.idrelacionestudiantesacudientes != null) || (this.idrelacionestudiantesacudientes != null && !this.idrelacionestudiantesacudientes.equals(other.idrelacionestudiantesacudientes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relacionestudiantesacudientes[ idrelacionestudiantesacudientes=" + idrelacionestudiantesacudientes + " ]";
    }
    
}
