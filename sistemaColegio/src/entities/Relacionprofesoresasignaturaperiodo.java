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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "relacionprofesoresasignaturaperiodo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relacionprofesoresasignaturaperiodo.findAll", query = "SELECT r FROM Relacionprofesoresasignaturaperiodo r"),
    @NamedQuery(name = "Relacionprofesoresasignaturaperiodo.findByIdrelacionprofesoresasignaturaperiodo", query = "SELECT r FROM Relacionprofesoresasignaturaperiodo r WHERE r.idrelacionprofesoresasignaturaperiodo = :idrelacionprofesoresasignaturaperiodo"),
    @NamedQuery(name = "Relacionprofesoresasignaturaperiodo.findByCantidadhorasporsemana", query = "SELECT r FROM Relacionprofesoresasignaturaperiodo r WHERE r.cantidadhorasporsemana = :cantidadhorasporsemana")})
public class Relacionprofesoresasignaturaperiodo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelacionprofesoresasignaturaperiodo")
    private Long idrelacionprofesoresasignaturaperiodo;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidadhorasporsemana")
    private int cantidadhorasporsemana;
    
    
    @JoinColumn(name = "relacionasignaturaperiodos", referencedColumnName = "idrelacionasignaturaperiodos")
    @ManyToOne(optional = false)
    private Relacionasignaturaperiodos relacionasignaturaperiodos;
    
    @JoinColumn(name = "profesores", referencedColumnName = "idprofesores")
    @ManyToOne
    private Profesores profesores;
    
    @JoinColumn(name = "cursos", referencedColumnName = "idcursos")
    @ManyToOne
    private Cursos cursos;

    @OneToMany(mappedBy = "relacionprofesoresasignaturaperiodo")
    private List<Tareas> tareasList;
    
    public Relacionprofesoresasignaturaperiodo() {
    }

    public Relacionprofesoresasignaturaperiodo(Long idrelacionprofesoresasignaturaperiodo) {
        this.idrelacionprofesoresasignaturaperiodo = idrelacionprofesoresasignaturaperiodo;
    }

    public Relacionprofesoresasignaturaperiodo(Long idrelacionprofesoresasignaturaperiodo, int cantidadhorasporsemana) {
        this.idrelacionprofesoresasignaturaperiodo = idrelacionprofesoresasignaturaperiodo;
        this.cantidadhorasporsemana = cantidadhorasporsemana;
    }

    public Long getIdrelacionprofesoresasignaturaperiodo() {
        return idrelacionprofesoresasignaturaperiodo;
    }

    public void setIdrelacionprofesoresasignaturaperiodo(Long idrelacionprofesoresasignaturaperiodo) {
        this.idrelacionprofesoresasignaturaperiodo = idrelacionprofesoresasignaturaperiodo;
    }

    public int getCantidadhorasporsemana() {
        return cantidadhorasporsemana;
    }

    public void setCantidadhorasporsemana(int cantidadhorasporsemana) {
        this.cantidadhorasporsemana = cantidadhorasporsemana;
    }

    @XmlTransient
    public List<Tareas> getTareasList() {
        return tareasList;
    }

    public void setTareasList(List<Tareas> tareasList) {
        this.tareasList = tareasList;
    }

    public Relacionasignaturaperiodos getRelacionasignaturaperiodos() {
        return relacionasignaturaperiodos;
    }

    public void setRelacionasignaturaperiodos(Relacionasignaturaperiodos relacionasignaturaperiodos) {
        this.relacionasignaturaperiodos = relacionasignaturaperiodos;
    }

    public Profesores getProfesores() {
        return profesores;
    }

    public void setProfesores(Profesores profesores) {
        this.profesores = profesores;
    }

    public Cursos getCursos() {
        return cursos;
    }

    public void setCursos(Cursos cursos) {
        this.cursos = cursos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelacionprofesoresasignaturaperiodo != null ? idrelacionprofesoresasignaturaperiodo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relacionprofesoresasignaturaperiodo)) {
            return false;
        }
        Relacionprofesoresasignaturaperiodo other = (Relacionprofesoresasignaturaperiodo) object;
        if ((this.idrelacionprofesoresasignaturaperiodo == null && other.idrelacionprofesoresasignaturaperiodo != null) || (this.idrelacionprofesoresasignaturaperiodo != null && !this.idrelacionprofesoresasignaturaperiodo.equals(other.idrelacionprofesoresasignaturaperiodo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relacionprofesoresasignaturaperiodo[ idrelacionprofesoresasignaturaperiodo=" + idrelacionprofesoresasignaturaperiodo + " ]";
    }
    
}
