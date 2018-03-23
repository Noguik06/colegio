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
@Table(name = "relaciondimensionesasignaturasano")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relaciondimensionesasignaturasano.findAll", query = "SELECT r FROM Relaciondimensionesasignaturasano r"),
    @NamedQuery(name = "Relaciondimensionesasignaturasano.findByIdrelaciondimensionesasignaturasano", query = "SELECT r FROM Relaciondimensionesasignaturasano r WHERE r.idrelaciondimensionesasignaturasano = :idrelaciondimensionesasignaturasano"),
    @NamedQuery(name = "Relaciondimensionesasignaturasano.findByPorcentaje", query = "SELECT r FROM Relaciondimensionesasignaturasano r WHERE r.porcentaje = :porcentaje")})
public class Relaciondimensionesasignaturasano implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelaciondimensionesasignaturasano")
    private Long idrelaciondimensionesasignaturasano;
    
    @JoinColumn(name = "relacionasignaturasperiodos", referencedColumnName = "idrelacionasignaturaperiodos")
    @ManyToOne
    private Relacionasignaturaperiodos relacionasignaturasperiodos;
    
    @JoinColumn(name = "dimensiones", referencedColumnName = "iddimensiones")
    @ManyToOne
    private Dimensiones dimensiones;
    
    @JoinColumn(name = "cursos", referencedColumnName = "idcursos")
    @ManyToOne
    private Cursos cursos;
    
    @Column(name = "porcentaje")
    private Short porcentaje;
    
    @OneToMany(mappedBy = "relaciondimensionesasignaturasano")
    private List<Relacionlogrosdimensiones> relacionlogrosdimensionesList;
    
    @OneToMany(mappedBy = "relaciondimensionesasignaturasano")
    private List<Relacionnotasdimension> relacionnotasdimensionList;
    
    
    
    

    public Relaciondimensionesasignaturasano() {
    }

    public Relaciondimensionesasignaturasano(Long idrelaciondimensionesasignaturasano) {
        this.idrelaciondimensionesasignaturasano = idrelaciondimensionesasignaturasano;
    }

    public Long getIdrelaciondimensionesasignaturasano() {
        return idrelaciondimensionesasignaturasano;
    }

    public void setIdrelaciondimensionesasignaturasano(Long idrelaciondimensionesasignaturasano) {
        this.idrelaciondimensionesasignaturasano = idrelaciondimensionesasignaturasano;
    }

    public Short getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Short porcentaje) {
        this.porcentaje = porcentaje;
    }

    @XmlTransient
    public List<Relacionlogrosdimensiones> getRelacionlogrosdimensionesList() {
        return relacionlogrosdimensionesList;
    }

    public void setRelacionlogrosdimensionesList(List<Relacionlogrosdimensiones> relacionlogrosdimensionesList) {
        this.relacionlogrosdimensionesList = relacionlogrosdimensionesList;
    }

	@XmlTransient
    public List<Relacionnotasdimension> getRelacionnotasdimensionList() {
        return relacionnotasdimensionList;
    }

    public void setRelacionnotasdimensionList(List<Relacionnotasdimension> relacionnotasdimensionList) {
        this.relacionnotasdimensionList = relacionnotasdimensionList;
    }

    public Relacionasignaturaperiodos getRelacionasignaturasperiodos() {
        return relacionasignaturasperiodos;
    }

    public void setRelacionasignaturasperiodos(Relacionasignaturaperiodos relacionasignaturasperiodos) {
        this.relacionasignaturasperiodos = relacionasignaturasperiodos;
    }

    public Dimensiones getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(Dimensiones dimensiones) {
        this.dimensiones = dimensiones;
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
        hash += (idrelaciondimensionesasignaturasano != null ? idrelaciondimensionesasignaturasano.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relaciondimensionesasignaturasano)) {
            return false;
        }
        Relaciondimensionesasignaturasano other = (Relaciondimensionesasignaturasano) object;
        if ((this.idrelaciondimensionesasignaturasano == null && other.idrelaciondimensionesasignaturasano != null) || (this.idrelaciondimensionesasignaturasano != null && !this.idrelaciondimensionesasignaturasano.equals(other.idrelaciondimensionesasignaturasano))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relaciondimensionesasignaturasano[ idrelaciondimensionesasignaturasano=" + idrelaciondimensionesasignaturasano + " ]";
    }
    
}
