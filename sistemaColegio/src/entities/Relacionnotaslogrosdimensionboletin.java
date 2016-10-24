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
@Table(name = "relacionnotaslogrosdimensionboletin")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Relacionnotaslogrosdimensionboletin.findAll", query = "SELECT r FROM Relacionnotaslogrosdimensionboletin r"),
    @NamedQuery(name = "Relacionnotaslogrosdimensionboletin.findByIdrelacionnotaslogrosdimensionboletin", query = "SELECT r FROM Relacionnotaslogrosdimensionboletin r WHERE r.idrelacionnotaslogrosdimensionboletin = :idrelacionnotaslogrosdimensionboletin"),
    @NamedQuery(name = "Relacionnotaslogrosdimensionboletin.findByNombre", query = "SELECT r FROM Relacionnotaslogrosdimensionboletin r WHERE r.nombre = :nombre")})
public class Relacionnotaslogrosdimensionboletin implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelacionnotaslogrosdimensionboletin")
    private Long idrelacionnotaslogrosdimensionboletin;
	
	@Column(name = "fechainicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicio;
    
	@Column(name = "fechafin")
    @Temporal(TemporalType.TIMESTAMP)
	private Date fechafin;
    
	@Column(name = "fechacreacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacreacion;
    
    @OneToMany(mappedBy = "relacionnotaslogrosdimensionboletin")
    private List<Relacionlogrosnotasdimension> relacionlogrosnotasdimensionList;
    
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    
    @OneToMany(mappedBy = "relacionnotaslogrosdimensionboletin")
    private List<Notascalificables> notascalificablesList;
    
    @JoinColumn(name = "relacionnotasdimension", referencedColumnName = "idrelacionnotasdimesion")
    @ManyToOne
    private Relacionnotasdimension relacionnotasdimension;
    
    @JoinColumn(name = "relacionboletineslogros", referencedColumnName = "idrelacionboletineslogros")
    @ManyToOne
    private Relacionboletineslogros relacionboletineslogros;

    public Relacionnotaslogrosdimensionboletin() {
    }

    public Relacionnotaslogrosdimensionboletin(Long idrelacionnotaslogrosdimensionboletin) {
        this.idrelacionnotaslogrosdimensionboletin = idrelacionnotaslogrosdimensionboletin;
    }

    public Long getIdrelacionnotaslogrosdimensionboletin() {
        return idrelacionnotaslogrosdimensionboletin;
    }

    public void setIdrelacionnotaslogrosdimensionboletin(Long idrelacionnotaslogrosdimensionboletin) {
        this.idrelacionnotaslogrosdimensionboletin = idrelacionnotaslogrosdimensionboletin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<Notascalificables> getNotascalificablesList() {
        return notascalificablesList;
    }

    public void setNotascalificablesList(List<Notascalificables> notascalificablesList) {
        this.notascalificablesList = notascalificablesList;
    }

    public Relacionnotasdimension getRelacionnotasdimension() {
        return relacionnotasdimension;
    }

    public void setRelacionnotasdimension(Relacionnotasdimension relacionnotasdimension) {
        this.relacionnotasdimension = relacionnotasdimension;
    }

    public Relacionboletineslogros getRelacionboletineslogros() {
        return relacionboletineslogros;
    }

    public void setRelacionboletineslogros(Relacionboletineslogros relacionboletineslogros) {
        this.relacionboletineslogros = relacionboletineslogros;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelacionnotaslogrosdimensionboletin != null ? idrelacionnotaslogrosdimensionboletin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Relacionnotaslogrosdimensionboletin)) {
            return false;
        }
        Relacionnotaslogrosdimensionboletin other = (Relacionnotaslogrosdimensionboletin) object;
        if ((this.idrelacionnotaslogrosdimensionboletin == null && other.idrelacionnotaslogrosdimensionboletin != null) || (this.idrelacionnotaslogrosdimensionboletin != null && !this.idrelacionnotaslogrosdimensionboletin.equals(other.idrelacionnotaslogrosdimensionboletin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Relacionnotaslogrosdimensionboletin[ idrelacionnotaslogrosdimensionboletin=" + idrelacionnotaslogrosdimensionboletin + " ]";
    }

    @XmlTransient
    public List<Relacionlogrosnotasdimension> getRelacionlogrosnotasdimensionList() {
        return relacionlogrosnotasdimensionList;
    }

    public void setRelacionlogrosnotasdimensionList(List<Relacionlogrosnotasdimension> relacionlogrosnotasdimensionList) {
        this.relacionlogrosnotasdimensionList = relacionlogrosnotasdimensionList;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }
    
}
