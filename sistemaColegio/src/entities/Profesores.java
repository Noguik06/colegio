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
@Table(name = "profesores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profesores.findAll", query = "SELECT p FROM Profesores p"),
    @NamedQuery(name = "Profesores.findByIdprofesores", query = "SELECT p FROM Profesores p WHERE p.idprofesores = :idprofesores")})
public class Profesores implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprofesores")
    private Long idprofesores;
    @OneToMany(mappedBy = "profesor")
    private List<Cursos> cursosList;
    @OneToMany(mappedBy = "profesores")
    private List<Relacionprofesoresasignaturaperiodo> relacionprofesoresasignaturaperiodoList;
    @JoinColumn(name = "usuarios", referencedColumnName = "idusuarios")
    @ManyToOne(optional = false)
    private Usuarios usuarios;

    public Profesores() {
    }

    public Profesores(Long idprofesores) {
        this.idprofesores = idprofesores;
    }

    public Long getIdprofesores() {
        return idprofesores;
    }

    public void setIdprofesores(Long idprofesores) {
        this.idprofesores = idprofesores;
    }

    @XmlTransient
    public List<Cursos> getCursosList() {
        return cursosList;
    }

    public void setCursosList(List<Cursos> cursosList) {
        this.cursosList = cursosList;
    }

    @XmlTransient
    public List<Relacionprofesoresasignaturaperiodo> getRelacionprofesoresasignaturaperiodoList() {
        return relacionprofesoresasignaturaperiodoList;
    }

    public void setRelacionprofesoresasignaturaperiodoList(List<Relacionprofesoresasignaturaperiodo> relacionprofesoresasignaturaperiodoList) {
        this.relacionprofesoresasignaturaperiodoList = relacionprofesoresasignaturaperiodoList;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprofesores != null ? idprofesores.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profesores)) {
            return false;
        }
        Profesores other = (Profesores) object;
        if ((this.idprofesores == null && other.idprofesores != null) || (this.idprofesores != null && !this.idprofesores.equals(other.idprofesores))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Profesores[ idprofesores=" + idprofesores + " ]";
    }
    
}
