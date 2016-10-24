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
@Table(name = "grados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grados.findAll", query = "SELECT g FROM Grados g"),
    @NamedQuery(name = "Grados.findByIdgrados", query = "SELECT g FROM Grados g WHERE g.idgrados = :idgrados"),
    @NamedQuery(name = "Grados.findByNombre", query = "SELECT g FROM Grados g WHERE g.nombre = :nombre"),
    @NamedQuery(name = "Grados.findByNumero", query = "SELECT g FROM Grados g WHERE g.numero = :numero")})
public class Grados implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idgrados")
    private Integer idgrados;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero")
    private short numero;
    @JoinColumn(name = "proximo", referencedColumnName = "idgrados")
    @ManyToOne(optional = false)
    private Grados proximo;
    @Column(name = "tipo")
    private int tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grados")
    private List<Cursos> cursosList;
    @OneToMany(mappedBy = "grados")
    private List<Registromatriculas> registromatriculasList;
    @OneToMany(mappedBy = "grados")
    private List<Relacionasignaturaperiodos> relacionasignaturaperiodosList;
    @OneToMany(mappedBy = "grados")
    private List<Estudiantes> estudiantesList;

    public Grados() {
    }

    public Grados(Integer idgrados) {
        this.idgrados = idgrados;
    }

    public Grados(Integer idgrados, String nombre, short numero) {
        this.idgrados = idgrados;
        this.nombre = nombre;
        this.numero = numero;
    }

    public Integer getIdgrados() {
        return idgrados;
    }

    public void setIdgrados(Integer idgrados) {
        this.idgrados = idgrados;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public short getNumero() {
        return numero;
    }

    public void setNumero(short numero) {
        this.numero = numero;
    }
    
    public Grados getProximo() {
        return proximo;
    }

    public void setProximo(Grados grados) {
        this.proximo = grados;
    }

    @XmlTransient
    public List<Cursos> getCursosList() {
        return cursosList;
    }

    public void setCursosList(List<Cursos> cursosList) {
        this.cursosList = cursosList;
    }

    @XmlTransient
    public List<Registromatriculas> getRegistromatriculasList() {
        return registromatriculasList;
    }

    public void setRegistromatriculasList(List<Registromatriculas> registromatriculasList) {
        this.registromatriculasList = registromatriculasList;
    }

    @XmlTransient
    public List<Relacionasignaturaperiodos> getRelacionasignaturaperiodosList() {
        return relacionasignaturaperiodosList;
    }

    public void setRelacionasignaturaperiodosList(List<Relacionasignaturaperiodos> relacionasignaturaperiodosList) {
        this.relacionasignaturaperiodosList = relacionasignaturaperiodosList;
    }

    public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	@XmlTransient
    public List<Estudiantes> getEstudiantesList() {
        return estudiantesList;
    }

    public void setEstudiantesList(List<Estudiantes> estudiantesList) {
        this.estudiantesList = estudiantesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idgrados != null ? idgrados.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grados)) {
            return false;
        }
        Grados other = (Grados) object;
        if ((this.idgrados == null && other.idgrados != null) || (this.idgrados != null && !this.idgrados.equals(other.idgrados))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Grados[ idgrados=" + idgrados + " ]";
    }
    
}
