/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "acudientes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Acudientes.findAll", query = "SELECT a FROM Acudientes a"),
    @NamedQuery(name = "Acudientes.findByIdacudientes", query = "SELECT a FROM Acudientes a WHERE a.idacudientes = :idacudientes"),
    @NamedQuery(name = "Acudientes.findByNombres", query = "SELECT a FROM Acudientes a WHERE a.nombres = :nombres"),
    @NamedQuery(name = "Acudientes.findByApellidos", query = "SELECT a FROM Acudientes a WHERE a.apellidos = :apellidos"),
    @NamedQuery(name = "Acudientes.findByTipoidentificacion", query = "SELECT a FROM Acudientes a WHERE a.tipoidentificacion = :tipoidentificacion"),
    @NamedQuery(name = "Acudientes.findByNumeroidentificacion", query = "SELECT a FROM Acudientes a WHERE a.numeroidentificacion = :numeroidentificacion"),
    @NamedQuery(name = "Acudientes.findByCiudadidentificacion", query = "SELECT a FROM Acudientes a WHERE a.ciudadidentificacion = :ciudadidentificacion"),
    @NamedQuery(name = "Acudientes.findByFechanacimiento", query = "SELECT a FROM Acudientes a WHERE a.fechanacimiento = :fechanacimiento"),
    @NamedQuery(name = "Acudientes.findBySexo", query = "SELECT a FROM Acudientes a WHERE a.sexo = :sexo"),
    @NamedQuery(name = "Acudientes.findByTelefonofijo", query = "SELECT a FROM Acudientes a WHERE a.telefonofijo = :telefonofijo"),
    @NamedQuery(name = "Acudientes.findByTelefonomovil", query = "SELECT a FROM Acudientes a WHERE a.telefonomovil = :telefonomovil"),
    @NamedQuery(name = "Acudientes.findByDirecciondomicilio", query = "SELECT a FROM Acudientes a WHERE a.direcciondomicilio = :direcciondomicilio"),
    @NamedQuery(name = "Acudientes.findByCiudaddomicilio", query = "SELECT a FROM Acudientes a WHERE a.ciudaddomicilio = :ciudaddomicilio"),
    @NamedQuery(name = "Acudientes.findByOcupacion", query = "SELECT a FROM Acudientes a WHERE a.ocupacion = :ocupacion")})
public class Acudientes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idacudientes")
    private Long idacudientes;
    @Size(max = 2147483647)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 2147483647)
    @Column(name = "apellidos")
    private String apellidos;
    @Size(max = 2147483647)
    @Column(name = "tipoidentificacion")
    private String tipoidentificacion;
    @Size(max = 2147483647)
    @Column(name = "numeroidentificacion")
    private String numeroidentificacion;
    @Size(max = 2147483647)
    @Column(name = "ciudadidentificacion")
    private String ciudadidentificacion;
    @Column(name = "fechanacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechanacimiento;
    @Size(max = 2147483647)
    @Column(name = "sexo")
    private String sexo;
    @Size(max = 2147483647)
    @Column(name = "telefonofijo")
    private String telefonofijo;
    @Size(max = 2147483647)
    @Column(name = "telefonomovil")
    private String telefonomovil;
    @Size(max = 2147483647)
    @Column(name = "direcciondomicilio")
    private String direcciondomicilio;
    @Size(max = 2147483647)
    @Column(name = "ciudaddomicilio")
    private String ciudaddomicilio;
    @Size(max = 2147483647)
    @Column(name = "ocupacion")
    private String ocupacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "acudientes")
    private List<Relacionestudiantesacudientes> relacionestudiantesacudientesList;

    public Acudientes() {
    }

    public Acudientes(Long idacudientes) {
        this.idacudientes = idacudientes;
    }

    public Long getIdacudientes() {
        return idacudientes;
    }

    public void setIdacudientes(Long idacudientes) {
        this.idacudientes = idacudientes;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipoidentificacion() {
        return tipoidentificacion;
    }

    public void setTipoidentificacion(String tipoidentificacion) {
        this.tipoidentificacion = tipoidentificacion;
    }

    public String getNumeroidentificacion() {
        return numeroidentificacion;
    }

    public void setNumeroidentificacion(String numeroidentificacion) {
        this.numeroidentificacion = numeroidentificacion;
    }

    public String getCiudadidentificacion() {
        return ciudadidentificacion;
    }

    public void setCiudadidentificacion(String ciudadidentificacion) {
        this.ciudadidentificacion = ciudadidentificacion;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefonofijo() {
        return telefonofijo;
    }

    public void setTelefonofijo(String telefonofijo) {
        this.telefonofijo = telefonofijo;
    }

    public String getTelefonomovil() {
        return telefonomovil;
    }

    public void setTelefonomovil(String telefonomovil) {
        this.telefonomovil = telefonomovil;
    }

    public String getDirecciondomicilio() {
        return direcciondomicilio;
    }

    public void setDirecciondomicilio(String direcciondomicilio) {
        this.direcciondomicilio = direcciondomicilio;
    }

    public String getCiudaddomicilio() {
        return ciudaddomicilio;
    }

    public void setCiudaddomicilio(String ciudaddomicilio) {
        this.ciudaddomicilio = ciudaddomicilio;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    @XmlTransient
    public List<Relacionestudiantesacudientes> getRelacionestudiantesacudientesList() {
        return relacionestudiantesacudientesList;
    }

    public void setRelacionestudiantesacudientesList(List<Relacionestudiantesacudientes> relacionestudiantesacudientesList) {
        this.relacionestudiantesacudientesList = relacionestudiantesacudientesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idacudientes != null ? idacudientes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Acudientes)) {
            return false;
        }
        Acudientes other = (Acudientes) object;
        if ((this.idacudientes == null && other.idacudientes != null) || (this.idacudientes != null && !this.idacudientes.equals(other.idacudientes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Acudientes[ idacudientes=" + idacudientes + " ]";
    }
    
}
