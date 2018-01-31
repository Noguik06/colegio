/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "periodos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Periodos.findAll", query = "SELECT p FROM Periodos p"),
    @NamedQuery(name = "Periodos.findByIdperiodos", query = "SELECT p FROM Periodos p WHERE p.idperiodos = :idperiodos"),
    @NamedQuery(name = "Periodos.findByFechainicio", query = "SELECT p FROM Periodos p WHERE p.fechainicio = :fechainicio"),
    @NamedQuery(name = "Periodos.findByFechafin", query = "SELECT p FROM Periodos p WHERE p.fechafin = :fechafin"),
    @NamedQuery(name = "Periodos.findByNombre", query = "SELECT p FROM Periodos p WHERE p.nombre = :nombre")})
public class Periodos implements Serializable {
	@OneToMany(mappedBy = "periodos")
    private List<Relacioncontenidosdimensiones> relacioncontenidosdimensionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodos")
    private List<Definitivasasignaturasperiodos> definitivasasignaturasperiodosList;
    @OneToMany(mappedBy = "periodos")
    private List<Retardos> retardosList;
    @OneToMany(mappedBy = "periodos")
    private List<Fallas> fallasList;
    @OneToMany(mappedBy = "periodos")
    private List<Observacionesboletines> observacionesboletinesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodos")
    private List<Relacionlogrosrecuperaciones> relacionlogrosrecuperacionesList;
    @OneToMany(mappedBy = "periodos")
    private Collection<Relacionrecuperacionregistromatriculas> relacionrecuperacionregistromatriculasCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idperiodos")
    private Long idperiodos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechainicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicio;
    @Temporal( TemporalType.DATE)
    private Date fechanotas;
    @Column(name = "fechafin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafin;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "tipo")
    private int tipo;
    //Porcentaje que representa del total
    @Column(name = "valor")
    private int valor;
    @Column(name = "calculo")
    //Forma en que se calcula las notas finales de las materias
    private int calculo;
    @OneToMany(mappedBy = "periodos")
    private List<Relacionlogrosdimensiones> relacionlogrosdimensionesList;
    @JoinColumn(name = "anoacademicos", referencedColumnName = "idanosacademicos")
    @ManyToOne(optional = false)
    private Anosacademicos anoacademicos;
    @OneToMany(mappedBy = "periodos")
    private List<Relacionnotasdimension> relacionnotasdimensionList;

    public Periodos() {
    }

    public Periodos(Long idperiodos) {
        this.idperiodos = idperiodos;
    }

    public Periodos(Long idperiodos, Date fechainicio) {
        this.idperiodos = idperiodos;
        this.fechainicio = fechainicio;
    }

    public Long getIdperiodos() {
        return idperiodos;
    }

    public void setIdperiodos(Long idperiodos) {
        this.idperiodos = idperiodos;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Date getFechanotas() {
		return fechanotas;
	}

	public void setFechanotas(Date fechanotas) {
		this.fechanotas = fechanotas;
	}

	@XmlTransient
    public List<Relacionlogrosdimensiones> getRelacionlogrosdimensionesList() {
        return relacionlogrosdimensionesList;
    }

    public void setRelacionlogrosdimensionesList(List<Relacionlogrosdimensiones> relacionlogrosdimensionesList) {
        this.relacionlogrosdimensionesList = relacionlogrosdimensionesList;
    }
    
    @XmlTransient
    public List<Relacioncontenidosdimensiones> getRelacioncontenidosdimensionesList() {
		return relacioncontenidosdimensionesList;
	}

	public void setRelacioncontenidosdimensionesList(
			List<Relacioncontenidosdimensiones> relacioncontenidosdimensionesList) {
		this.relacioncontenidosdimensionesList = relacioncontenidosdimensionesList;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Anosacademicos getAnoacademicos() {
        return anoacademicos;
    }

    public void setAnoacademicos(Anosacademicos anoacademicos) {
        this.anoacademicos = anoacademicos;
    }

    @XmlTransient
    public List<Relacionnotasdimension> getRelacionnotasdimensionList() {
        return relacionnotasdimensionList;
    }

    public void setRelacionnotasdimensionList(List<Relacionnotasdimension> relacionnotasdimensionList) {
        this.relacionnotasdimensionList = relacionnotasdimensionList;
    }
    
    

    public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	
	public int getCalculo() {
		return calculo;
	}

	public void setCalculo(int calculo) {
		this.calculo = calculo;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idperiodos != null ? idperiodos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Periodos)) {
            return false;
        }
        Periodos other = (Periodos) object;
        if ((this.idperiodos == null && other.idperiodos != null) || (this.idperiodos != null && !this.idperiodos.equals(other.idperiodos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Periodos[ idperiodos=" + idperiodos + " ]";
    }

    @XmlTransient
    public Collection<Relacionrecuperacionregistromatriculas> getRelacionrecuperacionregistromatriculasCollection() {
        return relacionrecuperacionregistromatriculasCollection;
    }

    public void setRelacionrecuperacionregistromatriculasCollection(Collection<Relacionrecuperacionregistromatriculas> relacionrecuperacionregistromatriculasCollection) {
        this.relacionrecuperacionregistromatriculasCollection = relacionrecuperacionregistromatriculasCollection;
    }

    @XmlTransient
    public List<Relacionlogrosrecuperaciones> getRelacionlogrosrecuperacionesList() {
        return relacionlogrosrecuperacionesList;
    }

    public void setRelacionlogrosrecuperacionesList(List<Relacionlogrosrecuperaciones> relacionlogrosrecuperacionesList) {
        this.relacionlogrosrecuperacionesList = relacionlogrosrecuperacionesList;
    }

    @XmlTransient
    public List<Observacionesboletines> getObservacionesboletinesList() {
        return observacionesboletinesList;
    }

    public void setObservacionesboletinesList(List<Observacionesboletines> observacionesboletinesList) {
        this.observacionesboletinesList = observacionesboletinesList;
    }

    @XmlTransient
    public List<Fallas> getFallasList() {
        return fallasList;
    }

    public void setFallasList(List<Fallas> fallasList) {
        this.fallasList = fallasList;
    }

    @XmlTransient
    public List<Retardos> getRetardosList() {
        return retardosList;
    }

    public void setRetardosList(List<Retardos> retardosList) {
        this.retardosList = retardosList;
    }

    @XmlTransient
    public List<Definitivasasignaturasperiodos> getDefinitivasasignaturasperiodosList() {
        return definitivasasignaturasperiodosList;
    }

    public void setDefinitivasasignaturasperiodosList(List<Definitivasasignaturasperiodos> definitivasasignaturasperiodosList) {
        this.definitivasasignaturasperiodosList = definitivasasignaturasperiodosList;
    }
    
}
