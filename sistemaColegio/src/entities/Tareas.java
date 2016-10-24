/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "tareas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tareas.findAll", query = "SELECT t FROM Tareas t"),
    @NamedQuery(name = "Tareas.findByIdtareas", query = "SELECT t FROM Tareas t WHERE t.idtareas = :idtareas"),
    @NamedQuery(name = "Tareas.findByNombre", query = "SELECT t FROM Tareas t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Tareas.findByDescripcion", query = "SELECT t FROM Tareas t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Tareas.findByFecha", query = "SELECT t FROM Tareas t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "Tareas.findByTipo", query = "SELECT t FROM Tareas t WHERE t.tipo = :tipo")})
public class Tareas implements Serializable {
    @Lob
    @Column(name = "archivo")
    private byte[] archivo;
    @OneToMany(mappedBy = "tareas")
    private List<Relaciontareasestudiantes> relaciontareasestudiantesList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtareas")
    private Long idtareas;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "online")
    private boolean online;
    @Column(name = "fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fin;
    @Size(max = 2147483647)
    @Column(name = "tipo")
    private String tipo;
    @JoinColumn(name = "relacionprofesoresasignaturaperiodo", referencedColumnName = "idrelacionprofesoresasignaturaperiodo")
    @ManyToOne
    private Relacionprofesoresasignaturaperiodo relacionprofesoresasignaturaperiodo;

    public Tareas() {
    }

    public Tareas(Long idtareas) {
        this.idtareas = idtareas;
    }

    public Long getIdtareas() {
        return idtareas;
    }

    public void setIdtareas(Long idtareas) {
        this.idtareas = idtareas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Relacionprofesoresasignaturaperiodo getRelacionprofesoresasignaturaperiodo() {
        return relacionprofesoresasignaturaperiodo;
    }

    public void setRelacionprofesoresasignaturaperiodo(Relacionprofesoresasignaturaperiodo relacionprofesoresasignaturaperiodo) {
        this.relacionprofesoresasignaturaperiodo = relacionprofesoresasignaturaperiodo;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtareas != null ? idtareas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tareas)) {
            return false;
        }
        Tareas other = (Tareas) object;
        if ((this.idtareas == null && other.idtareas != null) || (this.idtareas != null && !this.idtareas.equals(other.idtareas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Tareas[ idtareas=" + idtareas + " ]";
    }

    @Column(name="archivo",columnDefinition="BLOB") 
    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    @XmlTransient
    public List<Relaciontareasestudiantes> getRelaciontareasestudiantesList() {
        return relaciontareasestudiantesList;
    }

    public void setRelaciontareasestudiantesList(List<Relaciontareasestudiantes> relaciontareasestudiantesList) {
        this.relaciontareasestudiantesList = relaciontareasestudiantesList;
    }
}
