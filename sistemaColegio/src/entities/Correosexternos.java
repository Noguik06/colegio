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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "Correosexternos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Correosexternos.findAll", query = "SELECT g FROM Correosexternos g"),
    @NamedQuery(name = "Correosexternos.findByIdCorreosexternos", query = "SELECT g FROM Correosexternos g WHERE g.idcorreosexternos = :idcorreosexternos"),
    @NamedQuery(name = "Correosexternos.findByNombre", query = "SELECT g FROM Correosexternos g WHERE g.nombre = :nombre"),
    @NamedQuery(name = "Correosexternos.findByCorreo", query = "SELECT g FROM Correosexternos g WHERE g.correo = :correo")})
public class Correosexternos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcorreosexternos")
    private Long idcorreosexternos;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "correo")
    private String correo;
    @JoinColumn(name = "usuario", referencedColumnName = "idusuarios")
    @ManyToOne
    private Usuarios usuario;

    public Correosexternos() {
    }

    public Correosexternos(Long idCorreosexternos) {
        this.idcorreosexternos = idCorreosexternos;
    }

    public Long getIdCorreosexternos() {
        return idcorreosexternos;
    }

    public void setIdCorreosexternos(Long idCorreosexternos) {
        this.idcorreosexternos = idCorreosexternos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcorreosexternos != null ? idcorreosexternos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Correosexternos)) {
            return false;
        }
        Correosexternos other = (Correosexternos) object;
        if ((this.idcorreosexternos == null && other.idcorreosexternos != null) || (this.idcorreosexternos != null && !this.idcorreosexternos.equals(other.idcorreosexternos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Correosexternos[ idCorreosexternos=" + idcorreosexternos + " ]";
    }
    
}
