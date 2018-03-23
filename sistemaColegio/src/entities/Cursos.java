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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "cursos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cursos.findAll", query = "SELECT c FROM Cursos c"),
    @NamedQuery(name = "Cursos.findByIdcursos", query = "SELECT c FROM Cursos c WHERE c.idcursos = :idcursos"),
    @NamedQuery(name = "Cursos.findByNombre", query = "SELECT c FROM Cursos c WHERE c.nombre = :nombre")})
public class Cursos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcursos")
    private Long idcursos;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "profesor", referencedColumnName = "idprofesores")
    @ManyToOne
    private Profesores profesor;
    @JoinColumn(name = "idbloques", referencedColumnName = "idbloques")
    @ManyToOne
    private Bloques bloques;
    @JoinColumn(name = "grados", referencedColumnName = "idgrados")
    @ManyToOne(optional = false)
    private Grados grados;
    @JoinColumn(name = "anosacademicos", referencedColumnName = "idanosacademicos")
    @ManyToOne(optional = false)
    private Anosacademicos anosacademicos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cursos")
    private List<Cronogramas> cronogramasList;
    @OneToMany(mappedBy = "cursos")
    private List<Registromatriculas> registromatriculasList;
    @OneToMany(mappedBy = "cursos")
    private List<Relacionprofesoresasignaturaperiodo> relacionprofesoresasignaturaperiodoList;
    @OneToMany(mappedBy = "cursos")
    private List<Relaciondimensionesasignaturasano> relaciondimensionesasignaturasanoList;
    @OneToMany(mappedBy = "cursos")
    private List<Relacioncontenidosdimensiones> relacioncontenidosdimensionesList;

    public Cursos() {
    }

    public Cursos(Long idcursos) {
        this.idcursos = idcursos;
    }

    public Long getIdcursos() {
        return idcursos;
    }

    public void setIdcursos(Long idcursos) {
        this.idcursos = idcursos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Profesores getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesores profesor) {
        this.profesor = profesor;
    }

    public Grados getGrados() {
        return grados;
    }

    public void setGrados(Grados grados) {
        this.grados = grados;
    }

    public Anosacademicos getAnosacademicos() {
        return anosacademicos;
    }

    public void setAnosacademicos(Anosacademicos anosacademicos) {
        this.anosacademicos = anosacademicos;
    }

    public Bloques getBloques() {
		return bloques;
	}

	public void setBloques(Bloques bloques) {
		this.bloques = bloques;
	}

	@XmlTransient
    public List<Cronogramas> getCronogramasList() {
        return cronogramasList;
    }

    public void setCronogramasList(List<Cronogramas> cronogramasList) {
        this.cronogramasList = cronogramasList;
    }

    @XmlTransient
    public List<Registromatriculas> getRegistromatriculasList() {
        return registromatriculasList;
    }

    public void setRegistromatriculasList(List<Registromatriculas> registromatriculasList) {
        this.registromatriculasList = registromatriculasList;
    }

    @XmlTransient
    public List<Relacionprofesoresasignaturaperiodo> getRelacionprofesoresasignaturaperiodoList() {
        return relacionprofesoresasignaturaperiodoList;
    }

    public void setRelacionprofesoresasignaturaperiodoList(List<Relacionprofesoresasignaturaperiodo> relacionprofesoresasignaturaperiodoList) {
        this.relacionprofesoresasignaturaperiodoList = relacionprofesoresasignaturaperiodoList;
    }

    @XmlTransient
    public List<Relaciondimensionesasignaturasano> getRelaciondimensionesasignaturasanoList() {
        return relaciondimensionesasignaturasanoList;
    }

    public void setRelaciondimensionesasignaturasanoList(List<Relaciondimensionesasignaturasano> relaciondimensionesasignaturasanoList) {
        this.relaciondimensionesasignaturasanoList = relaciondimensionesasignaturasanoList;
    }
    
    @XmlTransient
    public List<Relacioncontenidosdimensiones> getRelacioncontenidosdimensionesList() {
		return relacioncontenidosdimensionesList;
	}

	public void setRelacioncontenidosdimensionesList(
			List<Relacioncontenidosdimensiones> relacioncontenidosdimensionesList) {
		this.relacioncontenidosdimensionesList = relacioncontenidosdimensionesList;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idcursos != null ? idcursos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cursos)) {
            return false;
        }
        Cursos other = (Cursos) object;
        if ((this.idcursos == null && other.idcursos != null) || (this.idcursos != null && !this.idcursos.equals(other.idcursos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cursos[ idcursos=" + idcursos + " ]";
    }
    
}
