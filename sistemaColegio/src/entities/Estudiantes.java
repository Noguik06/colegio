/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "estudiantes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudiantes.findAll", query = "SELECT e FROM Estudiantes e"),
    @NamedQuery(name = "Estudiantes.findByIdestudiantes", query = "SELECT e FROM Estudiantes e WHERE e.idestudiantes = :idestudiantes"),
    @NamedQuery(name = "Estudiantes.findByCodigo", query = "SELECT e FROM Estudiantes e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "Estudiantes.findByColegioprocedencia", query = "SELECT e FROM Estudiantes e WHERE e.colegioprocedencia = :colegioprocedencia")})
public class Estudiantes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idestudiantes")
    private Long idestudiantes;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 2147483647)
    @Column(name = "colegioprocedencia")
    private String colegioprocedencia;
    @OneToMany(mappedBy = "estudiantes")
    private List<Registromatriculas> registromatriculasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estudiantes")
    private List<Relacionestudiantesacudientes> relacionestudiantesacudientesList;
    @JoinColumn(name = "usuarios", referencedColumnName = "idusuarios")
    @ManyToOne(optional = false)
    private Usuarios usuarios;
    @JoinColumn(name = "grados", referencedColumnName = "idgrados")
    @ManyToOne
    private Grados grados;

    public Estudiantes() {
    }

    public Estudiantes(Long idestudiantes) {
        this.idestudiantes = idestudiantes;
    }

    public Estudiantes(Long idestudiantes, String codigo) {
        this.idestudiantes = idestudiantes;
        this.codigo = codigo;
    }

    public Long getIdestudiantes() {
        return idestudiantes;
    }

    public void setIdestudiantes(Long idestudiantes) {
        this.idestudiantes = idestudiantes;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getColegioprocedencia() {
        return colegioprocedencia;
    }

    public void setColegioprocedencia(String colegioprocedencia) {
        this.colegioprocedencia = colegioprocedencia;
    }

    @XmlTransient
    public List<Registromatriculas> getRegistromatriculasList() {
        return registromatriculasList;
    }

    public void setRegistromatriculasList(List<Registromatriculas> registromatriculasList) {
        this.registromatriculasList = registromatriculasList;
    }

    @XmlTransient
    public List<Relacionestudiantesacudientes> getRelacionestudiantesacudientesList() {
        return relacionestudiantesacudientesList;
    }

    public void setRelacionestudiantesacudientesList(List<Relacionestudiantesacudientes> relacionestudiantesacudientesList) {
        this.relacionestudiantesacudientesList = relacionestudiantesacudientesList;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public Grados getGrados() {
        return grados;
    }

    public void setGrados(Grados grados) {
        this.grados = grados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idestudiantes != null ? idestudiantes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudiantes)) {
            return false;
        }
        Estudiantes other = (Estudiantes) object;
        if ((this.idestudiantes == null && other.idestudiantes != null) || (this.idestudiantes != null && !this.idestudiantes.equals(other.idestudiantes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Estudiantes[ idestudiantes=" + idestudiantes + " ]";
    }
    
}
