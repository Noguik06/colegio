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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import sun.reflect.generics.visitor.Reifier;

/**
 *
 * @author proveedor_jnoguera
 */
@Entity
@Table(name = "relaciontareasestudiantes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relaciontareasestudiantes.findAll", query = "SELECT r FROM Relaciontareasestudiantes r"),
    @NamedQuery(name = "Relaciontareasestudiantes.findByIdrelaciontareasestudiantes", query = "SELECT r FROM Relaciontareasestudiantes r WHERE r.idrelaciontareasestudiantes = :idrelaciontareasestudiantes"),
    @NamedQuery(name = "Relaciontareasestudiantes.findByComentarios", query = "SELECT r FROM Relaciontareasestudiantes r WHERE r.comentarios = :comentarios")})
public class Relaciontareasestudiantes implements Serializable {
    @Lob
    @Column(name = "archivo")
    private byte[] archivo;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "tipo")
    private String tipo;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelaciontareasestudiantes")
    private Long idrelaciontareasestudiantes;
    @Size(max = 2147483647)
    @Column(name = "comentarios")
    private String comentarios;
    @Size(max = 2147483647)
    @Column(name = "comentariosprofesor")
    private String comentariosprofesor;
    @Column(name = "calificacion")
    private short calificacion;
    @JoinColumn(name = "tareas", referencedColumnName = "idtareas")
    @ManyToOne
    private Tareas tareas;
    @JoinColumn(name = "idregistromatriculas", referencedColumnName = "idregistromatriculas")
    @ManyToOne
    private Registromatriculas registromatriculas;

    public Relaciontareasestudiantes() {
    }

    public Relaciontareasestudiantes(Long idrelaciontareasestudiantes) {
        this.idrelaciontareasestudiantes = idrelaciontareasestudiantes;
    }

    public Long getIdrelaciontareasestudiantes() {
        return idrelaciontareasestudiantes;
    }

    public void setIdrelaciontareasestudiantes(Long idrelaciontareasestudiantes) {
        this.idrelaciontareasestudiantes = idrelaciontareasestudiantes;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getComentariosprofesor() {
		return comentariosprofesor;
	}

	public void setComentariosprofesor(String comentariosprofesor) {
		this.comentariosprofesor = comentariosprofesor;
	}

	public Short getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Short calificacion) {
		this.calificacion = calificacion;
	}

	public Tareas getTareas() {
        return tareas;
    }

    public void setTareas(Tareas tareas) {
        this.tareas = tareas;
    }

    public Registromatriculas getRegistromatriculas() {
        return registromatriculas;
    }

    public void setRegistromatriculas(Registromatriculas registromatriculas) {
        this.registromatriculas = registromatriculas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelaciontareasestudiantes != null ? idrelaciontareasestudiantes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relaciontareasestudiantes)) {
            return false;
        }
        Relaciontareasestudiantes other = (Relaciontareasestudiantes) object;
        if ((this.idrelaciontareasestudiantes == null && other.idrelaciontareasestudiantes != null) || (this.idrelaciontareasestudiantes != null && !this.idrelaciontareasestudiantes.equals(other.idrelaciontareasestudiantes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relaciontareasestudiantes[ idrelaciontareasestudiantes=" + idrelaciontareasestudiantes + " ]";
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
