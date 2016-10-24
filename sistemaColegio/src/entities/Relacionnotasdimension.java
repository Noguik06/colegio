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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "relacionnotasdimension")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relacionnotasdimension.findAll", query = "SELECT r FROM Relacionnotasdimension r"),
    @NamedQuery(name = "Relacionnotasdimension.findByIdrelacionnotasdimesion", query = "SELECT r FROM Relacionnotasdimension r WHERE r.idrelacionnotasdimesion = :idrelacionnotasdimesion"),
    @NamedQuery(name = "Relacionnotasdimension.findByPorcentaje", query = "SELECT r FROM Relacionnotasdimension r WHERE r.porcentaje = :porcentaje"),
    @NamedQuery(name = "Relacionnotasdimension.findByEstadoasignacion", query = "SELECT r FROM Relacionnotasdimension r WHERE r.estadoasignacion = :estadoasignacion"),
    @NamedQuery(name = "Relacionnotasdimension.findByNombrenotas", query = "SELECT r FROM Relacionnotasdimension r WHERE r.nombrenotas = :nombrenotas")})
public class Relacionnotasdimension implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelacionnotasdimesion")
    private Long idrelacionnotasdimesion;
    
    @Column(name = "porcentaje")
    private Short porcentaje;
    
    @Column(name = "estadoasignacion")
    private Boolean estadoasignacion;
    
    @Size(max = 2147483647)
    @Column(name = "nombrenotas")
    private String nombrenotas;
    
    @OneToMany(mappedBy = "relacionnotasdimension")
    private List<Relacionlogrosnotasdimension> relacionlogrosnotasdimensionList;
    
    @OneToMany(mappedBy = "relacionnotasdimension")
    private List<Relacionnotaslogrosdimensionboletin> relacionnotaslogrosdimensionboletinList;
    
    @JoinColumn(name = "relaciondimensionesasignaturasano", referencedColumnName = "idrelaciondimensionesasignaturasano")
    @ManyToOne
    private Relaciondimensionesasignaturasano relaciondimensionesasignaturasano;
    
    @JoinColumn(name = "periodos", referencedColumnName = "idperiodos")
    @ManyToOne
    private Periodos periodos;

    public Relacionnotasdimension() {
    }

    public Relacionnotasdimension(Long idrelacionnotasdimesion) {
        this.idrelacionnotasdimesion = idrelacionnotasdimesion;
    }

    public Long getIdrelacionnotasdimesion() {
        return idrelacionnotasdimesion;
    }

    public void setIdrelacionnotasdimesion(Long idrelacionnotasdimesion) {
        this.idrelacionnotasdimesion = idrelacionnotasdimesion;
    }

    public Short getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Short porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Boolean getEstadoasignacion() {
        return estadoasignacion;
    }

    public void setEstadoasignacion(Boolean estadoasignacion) {
        this.estadoasignacion = estadoasignacion;
    }

    public String getNombrenotas() {
        return nombrenotas;
    }

    public void setNombrenotas(String nombrenotas) {
        this.nombrenotas = nombrenotas;
    }

    @XmlTransient
    public List<Relacionlogrosnotasdimension> getRelacionlogrosnotasdimensionList() {
        return relacionlogrosnotasdimensionList;
    }

    public void setRelacionlogrosnotasdimensionList(List<Relacionlogrosnotasdimension> relacionlogrosnotasdimensionList) {
        this.relacionlogrosnotasdimensionList = relacionlogrosnotasdimensionList;
    }

    @XmlTransient
    public List<Relacionnotaslogrosdimensionboletin> getRelacionnotaslogrosdimensionboletinList() {
        return relacionnotaslogrosdimensionboletinList;
    }

    public void setRelacionnotaslogrosdimensionboletinList(List<Relacionnotaslogrosdimensionboletin> relacionnotaslogrosdimensionboletinList) {
        this.relacionnotaslogrosdimensionboletinList = relacionnotaslogrosdimensionboletinList;
    }

    public Relaciondimensionesasignaturasano getRelaciondimensionesasignaturasano() {
        return relaciondimensionesasignaturasano;
    }

    public void setRelaciondimensionesasignaturasano(Relaciondimensionesasignaturasano relaciondimensionesasignaturasano) {
        this.relaciondimensionesasignaturasano = relaciondimensionesasignaturasano;
    }

    public Periodos getPeriodos() {
        return periodos;
    }

    public void setPeriodos(Periodos periodos) {
        this.periodos = periodos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelacionnotasdimesion != null ? idrelacionnotasdimesion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relacionnotasdimension)) {
            return false;
        }
        Relacionnotasdimension other = (Relacionnotasdimension) object;
        if ((this.idrelacionnotasdimesion == null && other.idrelacionnotasdimesion != null) || (this.idrelacionnotasdimesion != null && !this.idrelacionnotasdimesion.equals(other.idrelacionnotasdimesion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relacionnotasdimension[ idrelacionnotasdimesion=" + idrelacionnotasdimesion + " ]";
    }
    
}
