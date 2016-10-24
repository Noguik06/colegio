package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juannoguera
 */
@Entity
@Table(name = "configuraciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configuraciones.findAll", query = "SELECT c FROM Configuraciones c"),
    @NamedQuery(name = "Configuraciones.findByIdconfiguraciones", query = "SELECT c FROM Configuraciones c WHERE c.idconfiguraciones = :idconfiguraciones"),
    @NamedQuery(name = "Configuraciones.findByPropiedad", query = "SELECT c FROM Configuraciones c WHERE c.propiedad = :propiedad"),
    @NamedQuery(name = "Configuraciones.findByValor", query = "SELECT c FROM Configuraciones c WHERE c.valor = :valor")})
public class Configuraciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idconfiguraciones")
    private Long idconfiguraciones;
    @Size(max = 2147483647)
    @Column(name = "propiedad")
    private String propiedad;
    @Size(max = 2147483647)
    @Column(name = "valor")
    private String valor;

    public Configuraciones() {
    }

    public Configuraciones(Long idconfiguraciones) {
        this.idconfiguraciones = idconfiguraciones;
    }

    public Long getIdconfiguraciones() {
        return idconfiguraciones;
    }

    public void setIdconfiguraciones(Long idconfiguraciones) {
        this.idconfiguraciones = idconfiguraciones;
    }

    public String getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(String propiedad) {
        this.propiedad = propiedad;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idconfiguraciones != null ? idconfiguraciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuraciones)) {
            return false;
        }
        Configuraciones other = (Configuraciones) object;
        if ((this.idconfiguraciones == null && other.idconfiguraciones != null) || (this.idconfiguraciones != null && !this.idconfiguraciones.equals(other.idconfiguraciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Configuraciones[ idconfiguraciones=" + idconfiguraciones + " ]";
    }
    
}
